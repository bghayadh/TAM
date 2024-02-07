 var transtype_array = ["-- Select Option --","New Node","New Node on New Hardware","New Node on Existed Hardware","New Hardware on New Node","New Hardware on Existed Node","Existed Hardware on New Node","Transfer","Transfer from Slot to Slot","Transfer from Node to Node","Transfer from Site to Site","Disappear","Disappear for Maintenance","Disappear for Transfer","Maintenance","Replacement","Retirement"];
 var ElementName_array = ["-- Select Option --","Cabinet","Subrack","Slot","Board","Port","Antenna"];
 var Actions_array = ["-- Select Option --","Approved","Not Approved","Rejected"];
 var flagFrom;
 var Errorflag;
 var slctDel = [];
 var slctDelDN = [];
 

 // get the selected row index and save in RowIndex input
		function SetIndexRow(obj)
		{
			var row_index = $(obj).parent().parent().index();
			$("#RowIndex").val(row_index);
		}

 ///////get to total quantity//////
    function getSumQty_totalAT (){
        var sumQntity = 0;
        var sumtotAT = 0;
        $("#bisotab > tbody > tr").each(function(i, row){
		   sumQntity = sumQntity + parseFloat($(this).children('td[name="itemQty"]').children('input').val());
		   sumtotAT = sumtotAT + 	parseFloat($(this).children('td[name="itemTotalAt"]').children('input').val());
		      	});
		       $('#dntotqty').val(sumQntity);
		       $('#dntotamnt').val(sumtotAT);

		};

 ////calculation for qty,rate,discount,tax and net rate//////////
 function SetCalc(obj)
		{
			var row_index = $(obj).parent().parent().index();
			var column_index = $(obj).parent().index();
			var any_index_2 = $(obj).index();
			var cell = $(obj).val();

			var qty = parseFloat($(obj).parent().parent().children('td[name="itemQty"]').children('input').val());
			var rate = parseFloat($(obj).parent().parent().children('td[name="itemRate"]').children('input').val());
			var DiscountAmount = parseFloat($(obj).parent().parent().children('td[name="itemDAmout"]').children('input').val());
			var tax = parseFloat($(obj).parent().parent().children('td[name="itemTax"]').children('input').val());

			var netrate = rate - DiscountAmount;
			tax = (tax * netrate) /100;
			var total = qty * netrate;
			var totalAT = qty * (netrate+tax);



            $(obj).parent().parent().children('td[name="itemNetRate"]').children('input').val(netrate);
	 		$(obj).parent().parent().children('td[name="itemTotal"]').children('input').val(total);
	 		$(obj).parent().parent().children('td[name="itemTotalAt"]').children('input').val(totalAT);
	 		getSumQty_totalAT();
		}


		function SetCalcPopUp()
		{
			var qty = parseFloat($('#popupQty').val());
			
			var rate = parseFloat($('#popupRate').val());
			
			var DiscountAmount = parseFloat($('#popupDiscountAmount').val());
			
			var tax = parseFloat($('#popupTax').val());
			

			var netrate = rate - DiscountAmount;
			
			tax = (tax * netrate) /100;
			var total = qty * netrate;
			var totalAT = qty * (netrate+tax);

		   $('#popupNetRate').val(netrate);
			 $('#popupTotal').val(total);
			 $('#popupTotalAT').val(totalAT);
	 		getSumQty_totalAT();
		}

  //function to get selected rows
		function getselectedrows () {
			dict = [];
			slctDel = [];
			var dictObj = {};
				$("#bisotab > tbody").find('input[name="record"]').each(function(){
					if($(this).is(":checked")){
						if(flagFrom === "delete"){
					slctDel.push($(this).parent().parent().children('td[name="itemDniID"]').children('input').val());

						}else{
					   // to validate that itemcode not empty in table
					  	 if(Errorflag != '2')
					  			 {
						    var warehouse =($(this).parent().parent().children('td[name="siteID"]').children('input').val());

					   }
						dictObj.item = $(this).parent().parent().children('td[name="item"]').children('input').val();
	                    dictObj.transType = $(this).parent().parent().children('td[name="transType"]').children('select').val();
                        dictObj.notes = $(this).parent().parent().children('td[name="notes"]').children('input').val();
	                    dictObj.elementName = $(this).parent().parent().children('td[name="elementName"]').children('select').val();
	                    dictObj.address = $(this).parent().parent().children('td[name="address"]').children('input').val();
	                    dictObj.approvedby = $(this).parent().parent().children('td[name="approveStatus"]').children('input[name="approvedby"]').val();
	                    if($(this).parent().parent().children('td[name="approveStatus"]').children('select'))
	                    dictObj.aprovStatus =($(this).parent().parent().children('td[name="approveStatus"]').children('select').val());
						else
							dictObj.aprovStatus = "Completely Approved";
						     dictObj.itemModel = $(this).parent().parent().children('td[name="itemModel"]').children('input').val();
						     dictObj.itemPartNb = $(this).parent().parent().children('td[name="itemPartNb"]').children('input').val();
						     dictObj.purchaseOrder = $(this).parent().parent().children('td[name="purchaseOrder"]').children('input').val();
						     dictObj.workOrder = $(this).parent().parent().children('td[name="workOrder"]').children('input').val();
						     dictObj.itemQty = $(this).parent().parent().children('td[name="itemQty"]').children('input').val();
						     dictObj.itemRate = $(this).parent().parent().children('td[name="itemRate"]').children('input').val();
						     dictObj.itemDAmout = $(this).parent().parent().children('td[name="itemDAmout"]').children('input').val();
						     dictObj.itemTax = $(this).parent().parent().children('td[name="itemTax"]').children('input').val();
						     dictObj.itemNetRate = $(this).parent().parent().children('td[name="itemNetRate"]').children('input').val();
						     dictObj.itemTotal = $(this).parent().parent().children('td[name="itemTotal"]').children('input').val();
						     dictObj.itemTotalAt = $(this).parent().parent().children('td[name="itemTotalAt"]').children('input').val();
						     dictObj.fromSlot = $(this).parent().parent().children('td[name="fromSlot"]').children('input').val();
						     dictObj.itemToSlot = $(this).parent().parent().children('td[name="toSlot"]').children('input').val();
						     dictObj.siteID = $(this).parent().parent().children('td[name="siteID"]').children('input').val();
						     dictObj.tositeID = $(this).parent().parent().children('td[name="tositeID"]').children('input').val();
						     dictObj.FarId = $(this).parent().parent().children('td[name="FarId"]').children('input').val();
						     dictObj.itemSN = $(this).parent().parent().children('td[name="itemSN"]').children('input').val();
						     dictObj.toSN = $(this).parent().parent().children('td[name="toSN"]').children('input').val();
						     dictObj.itemDniID = $(this).parent().parent().children('td[name="itemDniID"]').children('input').val();
						     dictObj.description = $(this).parent().parent().children('td[name="description"]').children('input').val();

						dict.push(dictObj);
						dictObj = {};

					}
	                }
					else {
					        //var removeitem =$(this).parent().parent().children(':nth-child(13)').v();
							slctDel = jQuery.grep(slctDel, function(value) {
	        				return value != $(this).parent().parent().children('td[name="itemDniID"]').children('input').val();
	      					});
					  }

	            	});


		}
        // end select row

  function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}




///////////load data///////////////////
// load data in table
	    // when clicking on new ADD button no need to laod data
	function loadData(){

	var Myresult;

	    // get all colmns count per row
   		function count(array){
                var c = 0;
                for(i in array) // in returns key, not object
                    if(array[i] != undefined)
                        c++;
                return c;
         }

        // Fill tables rows from DB
   		for (i = 0;i<boqArray.length;i++){

   	   		var itemCode = boqArray[i].dniItemcode;
   	   		if(itemCode == null)
   	   			itemCode = "";
   	   		else
   	   			itemCode = boqArray[i].dniItemcode+":"+ boqArray[i].dniItemname;


	    	var trans_type = boqArray[i].transType;
   	   		trans_options = "<select class='form-control transType' onChange='getAllType(this)'>";
   	   		transvalue = "-- Select Option --'";
			for(j=0; j<transtype_array.length; j++)
			{
				if(trans_type == transtype_array[j])
					selected_option = "selected='selected'";
				else
					selected_option = "";

				if(transtype_array[j] != '-- Select Option --')
					transvalue = transtype_array[j];

				trans_options += "<option value='"+transvalue+"' "+selected_option+" >"+transtype_array[j]+"</option>";
			}
			trans_options += "</select>";

			var ElementName = boqArray[i].elementName;
			ElementNamevalue = "-- Select Option --'";
			Element_options = "<select class='form-control elementName'>";
			for(j=0; j<ElementName_array.length; j++)
			{
				if(ElementName == ElementName_array[j])
					selected_option = "selected='selected'";
				else
					selected_option = "";

				if(ElementName_array[j] != '-- Select Option --')
					ElementNamevalue = ElementName_array[j];

				Element_options += "<option value='"+ElementNamevalue+"' "+selected_option+" >"+ElementName_array[j]+"</option>";
			}
			Element_options += "</select>";



            var ApprovalAction = boqArray[i].approvalStatus;
            valueOption = "-- Select Option --'";
	        	Actions_options = "<select class='form-control elementName' onClick='ChangeApprove(this)'>";
				for(j=0; j<Actions_array.length; j++)
				{
					if(ApprovalAction == Actions_array[j] && ApprovalAction != 'Approved')
						selected_option = "selected='selected'";
					else
						selected_option = "";

					if(Actions_array[j] != '-- Select Option --')
						valueOption = Actions_array[j];
					Actions_options += "<option value='"+valueOption+"' "+selected_option+" >"+Actions_array[j]+"</option>";
				}
				Actions_options += "</select>";



   	   		var PurchaseOrder = boqArray[i].dniPOID;
   	   		if(PurchaseOrder == null)
   	   			PurchaseOrder = "";
   	   		else
   	   			PurchaseOrder = boqArray[i].dniPOID+":"+ boqArray[i].supplierID+":"+ boqArray[i].supplierName+":"+ boqArray[i].totalAmount;

           var WorkOrder=boqArray[i].dniWOID;
           if(WorkOrder == null)
        	   WorkOrder = "";
  	   		else
  	   		WorkOrder = boqArray[i].dniWOID+":"+ boqArray[i].purpose;


			var Site = "";
			if(boqArray[i].wareID == null)
				Site = "";
			else Site = boqArray[i].wareID+":"+ boqArray[i].wareName+":"+ boqArray[i].dniSIte;

			var toSite = "";
			if(boqArray[i].toWareId == null)
				toSite = "";
			else toSite = boqArray[i].toWareId+":"+ boqArray[i].toWareName+":"+ boqArray[i].toSite;




			var itemModelVal = boqArray[i].itemModel;
			if(boqArray[i].itemModel == null)
				itemModelVal = "";
			else itemModelVal = boqArray[i].itemModel ;

			var itemPartVal = boqArray[i].itemPartNb;
			if(boqArray[i].itemPartNb == null)
				itemPartVal = "";
			else itemPartVal = boqArray[i].itemPartNb ;




			var trans_type = boqArray[i].transType;
			var ApproveStatus = boqArray[i].dniAPPROVAL;
            var ApprovalAction = boqArray[i].approvalStatus;
            var buttonApprove;
            var approvedby;

            if(ApproveStatus == null)
             {
            	approvedby = "";
       		 	buttonApprove = "<p class='case'>Transaction Type is Required</p>";
             }
            if (ApproveStatus == "Project Manager")
            {
            	if(ApprovalAction != 'Approved')
               	{
               	 	approvedby = "Project Manager";
           		 	buttonApprove = "<p class='case'>To be Approved By Project Manager</p>";
               	}
            	else
               	{
               	  	buttonApprove = "<p class='case'>To be Approved By Asset Unit</p>";
                    approvedby = "Asset Unit";
                }
            }
        	if (ApproveStatus == "Asset Unit")
            {
            	if(ApprovalAction != 'Approved')
               	{
           			approvedby = "Asset Unit";
           			buttonApprove = "<p class='case'>To be Approved By Asset Unit</p>";
               	}
            	else
               	{
           			buttonApprove = "<p class='case'>To be Approved By Finance</p>";
                    approvedby = "Finance";
               	}

            }
            if (ApproveStatus == "Operation Manager")
            {
               if(trans_type =="Retirement")                {
                 if(ApprovalAction != 'Approved')
                 {
             	 	approvedby = "Operation Manager";
            	 	buttonApprove = "<p class='case'>To be Approved By Operation Manager</p>";
                 }
                 else
                 {
            	 	buttonApprove = "<p class='case'>To be Approved By Finance</p>";
            	  	approvedby = "Finance";
                  }
                }

               else
               {
                    if(ApprovalAction != 'Approved')
                    {
                    	approvedby = "Operation Manager";
            	    	buttonApprove = "<p class='case'>To be Approved By Operation Manager</p>";
                    }
            	    else
            	    {
            	     	buttonApprove = "<p class='case'>Completely Approved</p>";
            	     	Actions_options = "<p class='case'>Completely Approved</p>";
            	     	approvedby = "Completely Approved";
            	    }
                   }
               }
            if (ApproveStatus == "Finance")
            {
            	if(ApprovalAction != 'Approved')
               	{
          			approvedby = "Finance";
           			buttonApprove = "<p class='case'>To be Approved By Finance</p>";
           		}
            	else
           	    {
           		 	buttonApprove = "<p class='case'>Completely Approved</p>";
          	        Actions_options = "<p class='case'>Completely Approved</p>";
          	        approvedby = "Completely Approved";
           	    }
            }
            if (ApproveStatus == "Completely Approved")
            {
        	    buttonApprove = "<p class='case'>Completely Approved</p>";
   	            Actions_options = "<p class='case'>Completely Approved</p>";
   	            approvedby = "Completely Approved";
        	}

		var toNodeArray = [];
  if (boqArray[i].toNodeArray != null) {
  toNodeArray.push(boqArray[i].toNodeArray);
  }
  else{
  toNodeArray.push(null);
  }
		var fromNodeArray = [];
  if (boqArray[i].fromNodeArray != null) {
  fromNodeArray.push(boqArray[i].fromNodeArray);
  }
  else{
  fromNodeArray.push(null);
  }
 


 
                var itemRow = "<tr>";
		        itemRow= itemRow + "<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		        itemRow =itemRow + "<td name='item'><input type='text' name='itmCode' class='form-control text-input' value='"+itemCode+"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all'/></td>";
		        itemRow =itemRow + "<td name='itemModel'><input type='text' name='itmMod' class='form-control text-input'  value='"+itemModelVal+"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'></td>";
		        itemRow =itemRow + "<td name='itemPartNb'><input type='text' name='itmPartNb' class='form-control text-input' value='"+itemPartVal+"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'></td>";
		        itemRow =itemRow + "<td name='transType'>"+trans_options+"</td>";
		        itemRow =itemRow + "<td name='notes'><input type='text' class='form-control NotesInput' value='"+boqArray[i].notes+"' ></td>";
		        itemRow =itemRow + "<td name='description'><input type='text' class='form-control NotesInput' value='"+boqArray[i].description+"' >";
		        itemRow =itemRow + "<td name='elementName'>"+Element_options+"</td>";
		        itemRow =itemRow + "<td name='address'><input type='text' class='form-control inputWidth' value='"+boqArray[i].position+"' ></td>"
		        itemRow =itemRow + "<td name='buttonApprove'>"+buttonApprove+"</td>";
		        itemRow =itemRow + "<td name='approveStatus'>"+Actions_options+"<input type='text' hidden name='approvedby' value ='"+approvedby+"'></td>";
		        itemRow =itemRow + "<td name='purchaseOrder'><input type='text' class='form-control text-input' value='"+ PurchaseOrder+"' ondrop ='return false;' name='itmPO' value='"+PurchaseOrder+"' style='width:350px;' class='ui-widget ui-widget-content ui-corner-all'></td>";
		        itemRow =itemRow + "<td name='workOrder'><input type='text' class='form-control text-input' ondrop ='return false;' name='itmWO' value='"+ WorkOrder+"' style='width:350px;' class='ui-widget ui-widget-content ui-corner-all'></td>";
		        itemRow =itemRow + "<td name='fromSlot'><input type='text' class='form-control text-input' style='width:200px' value='"+boqArray[i].fromSlot+"'></td>";
		        itemRow =itemRow + "<td name='toSlot'><input type='text' class='form-control text-input' style='width:200px' value='"+boqArray[i].toSlot+"'/></td>";
		        itemRow =itemRow + "<td name='itemQty'><input type='text' class='form-control text-input inputWidth' value='"+ boqArray[i].dniQty+"'></td>";
				itemRow =itemRow + "<td name='itemRate'><input type='text' class='form-control text-input inputWidth' value='"+ boqArray[i].dniRate+"'></td>";
				itemRow =itemRow + "<td name='itemDAmout'><input type='text' class='form-control text-input inputWidth' value='"+ boqArray[i].dniDiscamount+"'></td>";
				itemRow =itemRow + "<td name='itemTax'><input type='text' class='form-control text-input inputWidth' value='"+ boqArray[i].dniTax1+"'></td>";
	 		    itemRow =itemRow + "<td name='itemNetRate'><input type='text' class='form-control text-input inputWidth' value='"+ boqArray[i].dniNetrate+"' readonly></td>";
	 		    itemRow =itemRow + "<td name='itemTotal'><input type='text' class='form-control text-input inputWidth' value='"+ boqArray[i].dniTotal+"' readonly></td>";
				itemRow =itemRow + "<td name='itemTotalAt'><input type='text' class='form-control text-input inputWidth' value='"  + boqArray[i].dniTotalat+"' readonly></td>";
				itemRow =itemRow + "<td name='siteID'><input type='text' class='form-control text-input' value='"+Site+"' name='siteID' style='width:450px;' class='ui-widget ui-widget-content ui-corner-all'></td>";
				itemRow =itemRow + "<td name='tositeID'><input type='text' class='form-control text-input' value='"+toSite+"' name='tositeID' style='width:450px;' class='ui-widget ui-widget-content ui-corner-all'></td>";
			    var farIdValue = boqArray[i].farID !== null ? boqArray[i].farID : " "; 
          
			    if (boqArray[i].transType === "Retirement") {
                itemRow += "<td name='FarId'><input type='text' class='form-control text-input' value='" + farIdValue + "' name='FarId' style='width:450px;' class='ui-widget ui-widget-content ui-corner-all'></td>";
                 } else {
                itemRow += "<td name='FarId'><input type='text' class='form-control text-input' value='" + farIdValue+ "' name='FarId' style='width:450px;' class='ui-widget ui-widget-content ui-corner-all' readonly></td>";
                 }
                itemRow =itemRow + "<td name='itemSN'><input type='text' class='form-control text-input' style='width:200px' value='"+boqArray[i].dniSN+"'></td>";
				itemRow =itemRow + "<td name='toSN'><input type='text' class='form-control text-input' style='width:200px' value='"+boqArray[i].toSerialNumber+"'></td>";
				itemRow =itemRow + "<td name='itemDniID'><input type='text' class='form-control text-input' style='width:150px' value='"+boqArray[i].dniDNID+"' readonly></td>";
			    itemRow = itemRow + "<td name='toNode'><input type='text' style='width:200px; display: none;' value='" + toNodeArray[0] + "' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";
                itemRow = itemRow + "<td name='fromNode'><input type='text' style='width:200px; display: none;' value='" + fromNodeArray[0] + "' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>";

		        itemRow =itemRow + "</tr>";
		        $("#bisotab > tbody").append(itemRow);

		   }

       }//end clicking on new ADD button no need to laod data
	  
 function deleterowsDN(){
			
				$("#bisotab > tbody").find('input[name="record"]').each(function(){
					
			    	if($(this).is(":checked")){
							
							 var ID = $(this).parent().parent().children('td[name="itemDniID"]').children('input').val();
							 if(ID != null)
								 slctDelDN.push(ID);
								 
							 $(this).parents("tr").remove();
							
		            } 
		   
			  
		        });

				 if (slctDelDN.length == 0) {
			           	alert(' Select Row(s) to Delete');
						
			         	return false;
			         }	            
			       }
  /////////////////////add row//////////////////
  function addrows(stat){
  var ctx = getContextPath();
  	   		trans_options = "<select class='ui-widget ui-corner-all form-control' onChange='getAllType(this)'>";
   	   		var transvalue = "-- Select Option --";
			for(i=0; i<transtype_array.length; i++)
			{
				if(transtype_array[i] != "-- Select Option --")
					transvalue = transtype_array[i];
				trans_options += "<option value='"+transvalue+"' >"+transtype_array[i]+"</option>";
			}
			trans_options += "</select>";

			Element_options = "<select class='ui-widget ui-corner-all form-control'>";
			var ElementNamevalue = "-- Select Option --";
			for(j=0; j<ElementName_array.length; j++)
			{
				if(ElementName_array[j] != "-- Select Option --")
					ElementNamevalue = ElementName_array[j];
				Element_options += "<option value='"+ElementNamevalue+"' >"+ElementName_array[j]+"</option>";
			}
			Element_options += "</select>";

			Actions_options = "<select class='ui-widget ui-corner-all form-control' onClick='ChangeApprove(this)'>";
			var Actionsvalue = "-- Select Option --";
			for(j=0; j<Actions_array.length; j++)
			{
				if(Actions_array[j] != "-- Select Option --")
					Actionsvalue = Actions_array[j];
				Actions_options += "<option value='"+Actionsvalue+"' >"+Actions_array[j]+"</option>";
			}
			var serialArrays = [];
           serialArrays.push(null);
 
			Actions_options += "</select>";

        	var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
              	+"<td name='item'><input type='text'  ondrop ='return false;'  name='itmCode' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control' /></td>"
              	+"<td name='itemModel'><input type='text' ondrop ='return false;' name='itmMod' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control'></td>"
              	+"<td name='itemPartNb'><input type='text' ondrop ='return false;' name='itmPartNb' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control'></td>"
              	+"<td name='transType'>"+trans_options+"</td>"
              	+"<td name='notes'><input type='text' class='form-control NotesInput'></td>"
              	+"<td name='description'><input type='text' class='form-control NotesInput'></td>"
              	+"<td name='elementName'>"+Element_options+"</td>"
              	+"<td name='address'><input type='text' class='form-control inputWidth'></td>"
              	+"<td name='buttonApprove'><p class='case'>Transaction Type is Required</p></td>"
              	+"<td name='approveStatus'>"+Actions_options+"<input  type='text' hidden name='approvedby' value =''></td>"
              	+"<td name='purchaseOrder'><input type='text' ondrop ='return false;' name='itmPO' value='' style='width:350px;' class='ui-widget ui-widget-content ui-corner-all form-control'/></td>"
              	+"<td name='workOrder'><input type='text' ondrop ='return false;' name='itmWO' value='' style='width:350px;' class='ui-widget ui-widget-content ui-corner-all form-control'/></td>"
                +"<td name='fromSlot'><input type='text' class='form-control' style='width:200px;'/></td>"
            	  +"<td name='toSlot'><input type='text' class='form-control' style='width:200px;'/></td>"
              	+"<td name='itemQty'><input type='text' name='itemQty' value= 0 class='form-control inputWidth'></td>"
              	+"<td name='itemRate'><input type='text' value= 0 class='form-control inputWidth'></td>"
              	+"<td name='itemDAmout'><input type='text'value= 0 class='form-control inputWidth'></td>"
              	+"<td name='itemTax'><input type='text'value= 0 class='form-control inputWidth'></td>"
              	+"<td name='itemNetRate'><input type='text' value=0 readonly class='form-control inputWidth'></td>"
              	+"<td name='itemTotal'><input type='text' value=0 readonly class='form-control inputWidth' ></td>"
              	+"<td name='itemTotalAt'><input type='text' value=0 readonly class='form-control inputWidth'></td>"
              	+"<td name='siteID'><input type='text' name='siteID' style='width:450px;' class='ui-widget ui-widget-content ui-corner-all form-control'/></td>"
              	+"<td name='tositeID'><input type='text' name='tositeID' style='width:450px;' class='ui-widget ui-widget-content ui-corner-all form-control'/></td>"
                +"<td name='FarId'><input type='text' name='FarId' style='width:450px;' class='ui-widget ui-widget-content ui-corner-all form-control' readonly/></td>"
              	+"<td name='itemSN'><input type='text' value='0' class='form-control' style='width:200px;'></td>"
              	+"<td name='toSN'><input type='text' value='0' class='form-control' style='width:200px;'></td>"
             	+"<td name='itemDniID'><input type='text' readonly value='0' class='form-control' style='width:160px;'></td>"
             	+"<td name='toNode'><input type='text'  value ='null' class='form-control' style='width:160px; display:none;'></td>"
             	+"<td name='fromNode'><input type='text' value ='null' style='width:200px; display:none;'  class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td></tr>";		
	       

		var rowIndex = $("#bisotab tr").length;

                if (stat == "addRow")
                {
             $("#bisotab > tbody").append(markup);
             	indexRow = rowIndex -1;
			console.log("newIndex: "+indexRow);
           }
           if(stat == "addRowBelow")
           {
             $("#bisotab > tbody tr").eq(rowindx).after(markup);
			 	var indexRow = rowindx +1;
				console.log("newIndex: "+indexRow);
           }
           if(stat == "addRowAbove")
           {
              $("#bisotab > tbody tr").eq(rowindx).before(markup);
              indexRow = rowindx ;
			console.log("newIndex: "+indexRow);	
           }


             $('#bisotab tr td input').on('focus', function() {
					SetIndexRow(this);
					
				});

             function customRenderItem(ul, item) {
	    			var t = '<span class="mcacCol1">'+ item[0] +'</span><span class="mcacCol2">'+ item[1] +'</span>',
	    				result = $('<li class="ui-menu-item" role="menuitem"></li>')
	    				.data('item.autocomplete', item)
	    				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">'+ t +'</a>')
	    				.appendTo(ul);

	    			return result;
	    		}


	   			$('#bisotab tr td input').on('input', function() {

						SetCalc(this);
					});



		var myDiv = document.getElementById("bisotab");
		if(stat == "addRow"){
	    myDiv.scrollTop = myDiv.scrollHeight;}


/*var rowIndex = $("#bisotab tr").length;
console.log("the tr count is "+rowIndex);

if(stat == "addRowBelow" || stat == "addRowAbove")
{
	if(stat == "addRowBelow"){
	var indexRow = rowindx +1;
	console.log("newIndex: "+indexRow);
	}
	if(stat == "addRowAbove"){
		 indexRow = rowindx ;
	console.log("newIndex: "+indexRow);	
	}
}
else {
	//var newRowIndex = document.getElementById('RowIndex').value;
	indexRow = rowIndex -2;
	console.log("newIndex: "+indexRow);
	
}*/
		 $('input[name ="itmCode"]').eq(indexRow).autocomplete ({
		 
 		    	    source: function(request, response, event, ui) {
     			 
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: ctx+'/getItemCode',
		                 data: {
		                	  requestValue : request.term,
                              barcode : $("#barcode").val()
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
                    $(this).parents("tr").find('input[name ="itmMod"]').val(ui.item[2]);
                    $(this).parents("tr").find('input[name ="itmPartNb"]').val(ui.item[3]);
                    
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
 			$('input[name ="itmCode"]').eq(indexRow).focus(function(){
 				if (this.value == ""){
 					$(this).autocomplete("search");
 	   	        }
 			});






         // starting for item auto complete in poq table

			/// Starting autocomplete for Item Model
			$('input[name ="itmMod"]').eq(indexRow).autocomplete({
			
			
		    	    source: function(request, response, event, ui) {
     			 
 	             $.ajax({
 	                 type: "GET",
 	                 contentType: "application/json; charset=utf-8",
 	                 url: ctx+'/getModel',
 	                 data: {
 	                	    requestValue : request.term,
                            barcode : $("#barcode").val()
 	                	 
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
 				this.value = (ui.item ? ui.item[0] : '');
 				$(this).parents("tr").find('input[name ="itmCode"]').val(ui.item[1]+ ":" + ui.item[2]);
 			    $(this).parents("tr").find('input[name ="itmPartNb"]').val(ui.item[3]);
				
 					return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
 			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" + 
 			item[0] + "</span><br><span class='desc'>" + 
            item[1] + "</span><span class='desc'>"+"," + 
            item[2] + "</span><span class='desc'>"; 
            if(item[3] != '-') 
             appendString += ","+item[3] + "</span><span class='desc'>"; 
            if(item[4] != '-') 
	         appendString += ","+item[4]; 
             appendString += "</span></div>"; 
             
          return $("<li class='each'>").append(appendString).appendTo(ul);
			};
			$('input[name ="itmMod"]').eq(indexRow).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});

			// ending the autocomplete of Item Model




			/// Starting autocomplete for ItemPartNb
			$('input[name ="itmPartNb"]').eq(indexRow).autocomplete({
			
		    	    source: function(request, response, event, ui) {
    			 
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/getPartNo',
	                 data: {
 	                	    requestValue : request.term,
                            barcode : $("#barcode").val()
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
				$(this).parents("tr").find('input[name ="itmCode"]').val(ui.item[1]+ ":" + ui.item[2]);
			    $(this).parents("tr").find('input[name ="itmMod"]').val(ui.item[3]);
				return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
			var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" + 
			 item[0] + "</span><br><span class='desc'>" + 
			 item[1] + "</span><span class='desc'>"+"," + 
			  item[2] + "</span><span class='desc'>";

			  if(item[3] != '-')
               appendString += ","+item[3] + "</span><span class='desc'>"; 
              if(item[4] != '-') 
	           appendString += ","+item[4];
               appendString += "</span></div>"; 
            return $("<li class='each'>").append(appendString).appendTo(ul);
			};
			$('input[name ="itmPartNb"]').eq(indexRow).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});

			// ending the autocomplete of ItemPartNb

			//Starting for Warehouse auto complete in poq table
			$('input[name ="tositeID"]').eq(indexRow).autocomplete({
			
		    	    source: function(request, response, event, ui) {
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetAllWarehouse',
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
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
			};
			$('input[name ="tositeID"]').eq(indexRow).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});
			
			
		$('input[name="FarId"]').each(function (i, el) {
			    $(el).autocomplete({
			        source: function (request, response, event, ui) {
			            var nodeIds = [];
			            $("#FromNodeTable > tbody > tr").find('input[name="record"]').each(function () {
			                var node_Id = $(this).parent().parent().children('td[name="NodeId"]').children('input').val();
			                nodeIds.push(node_Id);
			            });

			            var siteId = $("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input')[0].value;
			            if (nodeIds.length === 0 || siteId === "") {
			                return;
			            }

			            $.ajax({
			                type: "GET",
			                contentType: "application/json; charset=utf-8",
			                url: ctx + '/GetAllFarDn',
			                data: {
			                    "nodeID[]": nodeIds,
			                    "siteId": siteId,
			                    "Far": request.term,
			                },
			                dataType: "json",
			                success: function (data) {
			                    if (data != null) {
			                        response(data.globalList);
			                    }
			                },
			                error: function (result) {
			                    alert("Error");
			                }
			            });
			        },
			        minLength: 0,
			        maxShowItems: 40,
			        scroll: true,
			        autoFocus: true, 	
			        select: function (event, ui) {
			            this.value = (ui.item ? ui.item[0] : '');
			            return false;
			        }
			    }).autocomplete("instance")._renderItem = function (ul, item) {
			        return $("<li class='each'>")
			            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                item[0] + "</span><br><span class='desc'>" +
			                item[1] + ', ' + item[2] + "</span></div>")
			            .appendTo(ul);
			    };

			    $(el).focus(function () {
			        if (this.value == "") {
			            $(el).autocomplete("search");
			        }
			    });
			});



			$('input[name ="siteID"]').eq(indexRow).autocomplete({
			
		    	    source: function(request, response, event, ui) {
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetAllWarehouse',
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
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
			};
			$('input[name ="siteID"]').eq(indexRow).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});/////////////end autocpmplete for warhouse

			//auto complete for PO (when adding new rows)
			$('input[name ="itmPO"]').eq(indexRow).autocomplete({
			
		    	    source: function(request, response, event, ui) {

		    	    	 var RowIndex = document.getElementById('RowIndex').value;
		    	    	 var ItemArray = getAllitemCode();
		    	    	 var Item_code = ItemArray[RowIndex];
	        			 if(Item_code != "empty")
		        		 {
	        				 Item_code = Item_code.split(":");
			        		 Item_code = Item_code[0];
			             }

	        			 var ItemModelsArray = getAllItemModels();
	        			 var itemModel = ItemModelsArray[RowIndex];

	        			 var ItemPartNbsArray = getAllItemPartNbs();
	        			 var itemPartNb = ItemPartNbsArray[RowIndex];


	        			 var WoArray = getAllWorkOrders();
	        			 var WorkOrder = WoArray[RowIndex];
	        			 if(WorkOrder != "empty")
		        		 {
	        				 WoOrder = WorkOrder.split(":");
			        		 WoOrderID = WoOrder[0];
			             }
	        			 else
	        				 WoOrderID = WorkOrder;
	        			    // console.log("*/*/WoOrderID is"+WoOrderID);



	        			// console.log("itemPartNb is"+itemPartNb);

			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetAllPoByItem',
			                 data: {
			                	 	"PO" : request.term,
									"Item_code" : Item_code,
									"itemModel" : itemModel,
									"WoOrderID":WoOrderID,
									"itemPartNb" : itemPartNb

							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {

			                         response(data.ListPos);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			        }, minLength:0, maxShowItems: 40, scroll:true,
					select: function(event, ui) {
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] + ":"+ ui.item[3] : '');
						var PoID = ui.item[0];

						ItemCode = ($(this).parent().parent().children('td[name="item"]').children('input').val());
						if(ItemCode != "")
						{
							ItemCode = ItemCode.split(":");
							ItemCode = ItemCode[0];

							var qty = $(this).parent().parent().children('td[name="itemQty"]').children('input');
							var rate = $(this).parent().parent().children('td[name="itemRate"]').children('input');
							var discountAmount = $(this).parent().parent().children('td[name="itemDAmout"]').children('input');
							var tax = $(this).parent().parent().children('td[name="itemTax"]').children('input');
							var obj = $(this).parent().parent().children('td[name="purchaseOrder"]').children('input');

							$.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url:ctx+'/GetPOitemDetails',
				                 data: {
										PoID : PoID,
										ItemCode : ItemCode,
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {

						                    qty.val(1);    // fill the qty field
						                    rate.val(data.PoDetails[0][1]);  // fill the rate field
						                    discountAmount.val(data.PoDetails[0][2]);   // fill the discountAmount field
						         			tax.val(data.PoDetails[0][3]);   // fill the tax field

						         			SetCalc(obj);
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
						}
			             return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
			};
			$('input[name ="itmPO"]').eq(indexRow).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});/////////////end autocpmplete for PO




			$('input[name ="itmWO"]').eq(indexRow).autocomplete({
			
		    	    source: function(request, response, event, ui) {

		    	    	 var RowIndex = document.getElementById('RowIndex').value;
		    	    	 var ItemArray = getAllitemCode();
		    	    	 var Item_code = ItemArray[RowIndex];
	        			 if(Item_code != "empty")
		        		 {
	        				 Item_code = Item_code.split(":");
			        		 Item_code = Item_code[0];
			             }

	        			 var PoArray = getAllPurchaseOrders();
	        			 var purchaseOrder = PoArray[RowIndex];
	        			 if(purchaseOrder != "empty")
		        		 {
	        				 PrOrder = purchaseOrder.split(":");
			        		 PrOrderID = PrOrder[0];
			             }
	        			 else
	        				 PrOrderID = purchaseOrder;
	        			     //console.log("PrOrderID is "+PrOrderID);

        			     var ItemModelsArray = getAllItemModels();
	        			 var itemModel = ItemModelsArray[RowIndex];

	        			 var ItemPartNbsArray = getAllItemPartNbs();
	        			 var itemPartNb = ItemPartNbsArray[RowIndex];



			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetALLWOByItem',
			                 data: {

				                 "woId":request.term,
			                	 "ItemPartNb" :itemPartNb,
			                	 Item_code : Item_code,
			                	 "itemModel" : itemModel,
			                	 PrOrderID : PrOrderID
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.ListWO);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			        }, minLength:0, maxShowItems: 40, scroll:true,
					select: function(event, ui) {
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1]  : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] + "</span></div>")
	                .appendTo(ul);
			};
			$('input[name ="itmWO"]').eq(indexRow).focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});

			//Ending for item auto complete in poq table

        }

        //end new add row


    var approvalOpt  = "<select class='form-control'  onclick = 'ChangeApprovePopUp(this)'>"
    +"<option value = '-- Select Option --'>-- Select Option --</option>"
    +"<option value = 'Approved'>Approved</option>"
    +"<option value = 'Not Approved'>Not Approved</option>"
    +"<option value = 'Rejected'>Rejected</option>"
    +"</select>";

    var compApp = "<p class='case'>Completely Approved</p>";

		var rowindx =0;

			 //Open popup fct
 function openPop(element) {

$("#ToNodeTable >tbody tr").empty();
$("#FromNodeTable >tbody tr").empty();
	var buttonRowIndx = $(element).closest("tr");
	rowindx = (buttonRowIndx[0].rowIndex - 1);
	console.log("Row index of clicked button::" +rowindx);

//*************************************************************************************************************************
	//Send input values from Boq table  to popup
	$('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
	$('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
	$('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());

	$('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
  //console.log("option selected in transtype "+$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').children('option:selected').val());
	$('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
	$('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
	$('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
  //$("select[id = 'popupElementName'] option[value ='"+elementNameValue+"']").attr("selected","selected");
	$('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
	$('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
	$('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change
//	$('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
//  alert(""+$('#popupApprovalStatus').val());
  //$('#popupApprovalAction').append('');
  if($('#popupApprovalStatus').val() == "Completely Approved")
  {
    $('#popupApprovalAction').empty();
    $('#popupApprovalAction').append(compApp);
	$('#popupApprovalAction > p').css({"font-size": '13px', "margin-left": 'auto'});
  }
  else {
    $('#popupApprovalAction').empty();
    $('#popupApprovalAction').append(approvalOpt);
    $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());

  }

  //($('#popupApprovalAction').val()).html('"+Actions_options.val()+"');
//  $("select[id = 'popupApprovalAction'] option[value ='"+approvalActionValue+"'").attr("selected","selected");
	$('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
	$('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
	$('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
	$('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
	$('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
	$('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
	$('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
	$('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
	$('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
	$('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
	$('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
	$('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
	$('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
	$('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
	$('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
	$('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());


		 $("#DNModal").modal("show");
         var element = document.getElementById("popupNb");
           element.innerHTML = "Item # " +(rowindx+1);
           console.log(rowindx);
			 SetCalcPopUp();
   var toNode = JSON.parse($("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="toNode"]').children('input').val());
   populateNodeTable(toNode, "#ToNodeTable");
console.log(toNode);
   var fromNode = JSON.parse($("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="fromNode"]').children('input').val());
   populateNodeTable(fromNode, "#FromNodeTable");

    

    		 }

function populateNodeTable(node, tableSelector) {

    $(tableSelector + " > tbody").html("");

    if (node != null) {
        var nodeArray = (tableSelector === "#ToNodeTable") ? node.toNodeArray : node.fromNodeArray;

        $.each(nodeArray, function (i, value) {
        console.log("zeinaaa dd");
            var nodeId = (value.NodeId === null) ? '' : value.NodeId;
            var NodeName = (value.NodeName === null) ? '' : value.NodeName;
            var NodeType = (value.NodeType === null) ? '' : value.NodeType;
console.log(nodeId);
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
    	function insertRowBelow(){

	console.log("ADD NEW ROW USING INSERTBELOW BUTTON");
  addrows('addRowBelow');

	rowindx++ ;
	var belowIndex = parseInt(rowindx);
	console.log("belowIndex:" +belowIndex);
  var transactionTypeValue = $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val();
  var elementNameValue = $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val();
  var approvalActionValue = $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val();

  $('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
  $('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
  $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());
  $('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
  //  $("select[id = 'popupTransType'] option[value ='"+transactionTypeValue+"']").attr("selected","selected");
   // alert("SSSSSSSSSSSS"+transactionTypeValue);
  $('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
  $('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
  $('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
  //  $("select[id = 'popupElementName'] option[value ='"+elementNameValue+"']").attr("selected","selected");
  $('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
  $('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
  $('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change

    $('#popupApprovalAction').empty();
    $('#popupApprovalAction').append(approvalOpt);
    $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());


//  $('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
  $('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
  $('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
  $('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
  $('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
  $('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
  $('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
  $('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
  $('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
  $('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
  $('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
  $('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
  $('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
  $('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
  $('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
  $('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
  $('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());


		//Update popup Nb in header
		var element = document.getElementById("popupNb");
		element.innerHTML = "Item # " +(parseInt(belowIndex)+1);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			SetCalcPopUp();

 }// End insertRowBelow fct in popup


 // Insert Row Above fct
 function insertRowAbove(){

	console.log("ADD NEW ROW USING INSERTAbove BUTTON");
  addrows('addRowAbove');
//----------------------------------------

	console.log("aboveIndex:" +rowindx);

  $('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
  $('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
  $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());
  $('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
  $('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
  $('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
  $('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
  $('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
  $('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
  $('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change

    $('#popupApprovalAction').empty();
    $('#popupApprovalAction').append(approvalOpt);
    $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());

//  $('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
  $('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
  $('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
 $('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
  $('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
  $('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
  $('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
  $('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
  $('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
  $('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
  $('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
  $('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
  $('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
  $('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
  $('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
  $('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
  $('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());


		//Update popup Nb in header
		var element = document.getElementById("popupNb");
		element.innerHTML = "Item # " +(parseInt(rowindx)+1);

		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});

			SetCalcPopUp();

 }// End insertRowAbove fct in popup



  // Next Fct in popup
 function nextRow(){
$("#ToNodeTable >tbody tr").empty();
$("#FromNodeTable >tbody tr").empty();
	// Get total nb of rows
	var rowCount = $("#bisotab >tbody tr").length;
	console.log("rowCount in BoQ:" +rowCount);

	rowindx++ ;
	var nextIndex = parseInt(rowindx);
	console.log("Next Index" +nextIndex);

	if(rowindx >= 0 && rowindx < rowCount) {
		console.log("Welcome to Next function");

    $('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
    $('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());
    $('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
    $('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
    $('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
    $('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
    $('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
    $('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
    $('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change
    if($('#popupApprovalStatus').val() == "Completely Approved")
    {
      $('#popupApprovalAction').empty();
      $('#popupApprovalAction').append(compApp);
	  $('#popupApprovalAction > p').css({"font-size": '13px', "margin-left": 'auto'});

    }
    else {
      $('#popupApprovalAction').empty();
      $('#popupApprovalAction').append(approvalOpt);
      $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());

    }
    //$('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
    $('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
    $('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
 	$('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
    $('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
    $('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
    $('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
    $('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
    $('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
    $('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
    $('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
    $('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
    $('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
    $('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
    $('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
    $('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());

var toNode = JSON.parse($("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="toNode"]').children('input').val());
   populateNodeTable(toNode, "#ToNodeTable");

   var fromNode = JSON.parse($("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="fromNode"]').children('input').val());
   populateNodeTable(fromNode, "#FromNodeTable");


			//Update popup Nb in header
			var element = document.getElementById("popupNb");
			element.innerHTML = "Item # " +(nextIndex+1);

			 }

	// Add new row when rowindx exceed the row count
	else if (rowindx >= rowCount) {
		console.log("ADD NEW ROW USING NEXT BUTTON");
    addrows('addRow');
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});

			SetCalcPopUp();
			//console.log("AAAAAAAAa"+$("#formStatus").text());
//------------------------------------

    $('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
    $('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());
    $('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
    $('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
    $('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
    $('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
    $('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
    $('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
    $('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change

      $('#popupApprovalAction').empty();
      $('#popupApprovalAction').append(approvalOpt);
      $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());

    //$('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
    $('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
    $('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
  	$('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
    $('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
    $('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
    $('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
    $('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
    $('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
    $('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
    $('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
    $('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
    $('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
    $('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
    $('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
    $('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());

			//Update popup Nb in header
			var element = document.getElementById("popupNb");
			element.innerHTML = "Item # " +(nextIndex+1);
			
		
			}

 }// End next fct in popup



 // Delete row fct 
 function deleteBoqRow() {

	rowindx++;
	$("tr").eq(rowindx).remove();

	// Get Nb of rows after delete
	var rowCount = $("#bisotab >tbody tr").length;

	 rowindx--;

	 $("#formStatus").text("Not Saved");
	 $('.dot').css({"background-color" : "orange"});

	 SetCalcPopUp();
		var DnItemID = $("#popupDNitmID").val();
		if(DnItemID != 0) {
			slctDelDN.push(DnItemID);
        }
        
	 if (rowindx == 0 && rowCount ==0) {

		$("#DNModal").modal("hide");


	}

	else if(rowindx >= 0 && rowindx < rowCount) {

    $('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
    $('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());
    $('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
    $('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
    $('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
    $('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
    $('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
    $('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
    $('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change
    if($('#popupApprovalStatus').val() == "Completely Approved")
    {
      $('#popupApprovalAction').empty();
      $('#popupApprovalAction').append(compApp);
	  $('#popupApprovalAction > p').css({"font-size": '13px', "margin-left": 'auto'});

    }
    else {
      $('#popupApprovalAction').empty();
      $('#popupApprovalAction').append(approvalOpt);
      $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());

    }
  //  $('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
    $('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
    $('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
    $('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
    $('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
    $('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
    $('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
    $('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
    $('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
    $('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
    $('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
    $('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
    $('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
    $('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
    $('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
    $('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());


			//Update popup Nb in header
			var element = document.getElementById("popupNb");
			element.innerHTML = "Item # " +(rowindx+1);
}

    // Show previous record
	else if (rowindx >= rowCount){

		rowindx--;

    $('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
    $('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
    $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());
    $('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
    $('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
    $('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
    $('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
    $('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
    $('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
    $('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change
    if($('#popupApprovalStatus').val() == "Completely Approved")
    {
      $('#popupApprovalAction').empty();
      $('#popupApprovalAction').append(compApp);
	  $('#popupApprovalAction > p').css({"font-size": '13px', "margin-left": 'auto'});

    }
    else {
      $('#popupApprovalAction').empty();
      $('#popupApprovalAction').append(approvalOpt);
      $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());

    }
    //$('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
    $('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
    $('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
  	$('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
    $('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
    $('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
    $('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
    $('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
    $('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
    $('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
    $('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
    $('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
    $('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
    $('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
    $('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
    $('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
    $('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());


			//Update popup Nb in header
			var element = document.getElementById("popupNb");
			element.innerHTML = "Item # " +(rowindx+1);
			}

 } // End delete fct


  $(function(){
 // Prev fct in popup
 $('#DNModal').on('input', function() {

	SetCalcPopUp();

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});



  $("button[name='previousRow']").on("click", function(){
$("#ToNodeTable >tbody tr").empty();
$("#FromNodeTable >tbody tr").empty();
   	if(rowindx > 0) {
   		console.log("Welcome to previous!");
		rowindx-- ;

   		var PrevIndex = parseInt(rowindx);
   		console.log("PrevIndex" +PrevIndex);

      $('#popupItemCode').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val());
      $('#popupItemModel') .val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val());
      $('#popupItemPartno').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val());
      $('#popupTransType').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children("select").children("option:selected").val());
      $('#popupNotes').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val());
      $('#popupDescription').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val());
      $('#popupElementName').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children("select").children("option:selected").val());
      $('#popupAddress').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val());
      $('#popupApprovalStatus').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val());
      $('#case').text($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').text());//change
      if($('#popupApprovalStatus').val() == "Completely Approved")
      {
        $('#popupApprovalAction').empty();
        $('#popupApprovalAction').append(compApp);
		$('#popupApprovalAction > p').css({"font-size": '13px', "margin-left": 'auto'});

      }
      else {
        $('#popupApprovalAction').empty();
        $('#popupApprovalAction').append(approvalOpt);
        $('#popupApprovalAction > select').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());

      }
    //  $('#popupApprovalAction').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children("select").children("option:selected").val());
      $('#popupPO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val());
      $('#popupWO').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val());
       $('#popupFromSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val());
      $('#popupToSlot').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val());
      $('#popupQty').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val());
      $('#popupRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val());
      $('#popupDiscountAmount').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val());
      $('#popupTax').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val());
      $('#popupNetRate').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val());
      $('#popupTotal').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val());
      $('#popupTotalAT').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val());
      $('#popupFromSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val());
      $('#popupToSite').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val());
      $('#popupFromSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val());
      $('#popupToSN').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val());
      $('#popupDNitmID').val($("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val());


   var toNode = JSON.parse($("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="toNode"]').children('input').val());
   populateNodeTable(toNode, "#ToNodeTable");

   var fromNode = JSON.parse($("#bisotab > tbody").find("tr").eq(rowindx).find('td[name="fromNode"]').children('input').val());
   populateNodeTable(fromNode, "#FromNodeTable");

	    //Update popup Nb in header
				var element = document.getElementById("popupNb");
				element.innerHTML = "Item # " +(PrevIndex+1);
       		 }

	   	// Close popup on row 0 (end of prev fct)

       		   else if (rowindx == 0) {
       		    	$("#DNModal").modal("hide");
       	      }

   	});// end prev fct


   	//Send input values from popup to boq when any popup input change
 $('#popupItemCode,#popupItemModel, #popupItemPartno, #popupTransType,#popupNotes,#popupDescription,#popupElementName,#popupAddress,#case,#popupApprovalStatus,#popupApprovalAction,'
 +'#popupPO,#popupWO,#popupFromSlot,#popupToSlot,#popupQty,#popupRate,#popupDiscountAmount'
 +'#popupTax,#popupNetRate,#popupTotal,#popupTotalAT,#popupFromSite,#popupToSite,#popupFromSN,#popupToSN,#popupDNitmID').on(' focusout ', function() {

 	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val($('#popupItemCode').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val($('#popupItemPartno').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').val($('#popupTransType').val());
  //$("select[id = 'popupTransType'] option[value ='"+$('#popupTransType').val()+"'").attr("selected","selected");
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val($('#popupNotes').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val($('#popupDescription').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children('select').val($('#popupElementName').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val($('#popupAddress').val());
  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').children('p').text($('#case').text());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val($('#popupApprovalStatus').val());
  //$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('select').val($('#popupApprovalAction').val());
  if($('#popupApprovalStatus').val() != "Completely Approved" ){
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('select').val($('#popupApprovalAction > select').val());}

	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val($('#popupPO').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val($('#popupWO').val());
	
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val($('#popupFromSlot').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val($('#popupToSlot').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val($('#popupQty').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val($('#popupRate').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val($('#popupDiscountAmount').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val($('#popupTax').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val($('#popupNetRate').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val($('#popupTotal').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val($('#popupTotalAT').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val($('#popupFromSite').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val($('#popupToSite').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val($('#popupFromSN').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val($('#popupToSN').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val($('#popupDNitmID').val());

	SetCalcPopUp();
      }); // end fct



   	// Close popup function
   $("button[name='closePopup']").on("click", function(){

  // Send input values from popup to boq table
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val($('#popupItemCode').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val($('#popupItemPartno').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').val($('#popupTransType').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val($('#popupNotes').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val($('#popupDescription').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children('select').val($('#popupElementName').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val($('#popupAddress').val());
  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').children('p').text($('#case').text());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').children('input').val($('#popupApprovalStatus').val());
	//$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('select').val($('#popupApprovalAction').val());
  if($('#popupApprovalStatus').val() != "Completely Approved" ){
  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('select').val($('#popupApprovalAction > select').val());}
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val($('#popupPO').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val($('#popupWO').val());
	
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val($('#popupFromSlot').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val($('#popupToSlot').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val($('#popupQty').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val($('#popupRate').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val($('#popupDiscountAmount').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val($('#popupTax').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val($('#popupNetRate').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val($('#popupTotal').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val($('#popupTotalAT').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val($('#popupFromSite').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val($('#popupToSite').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val($('#popupFromSN').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val($('#popupToSN').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val($('#popupDNitmID').val());

	    $("#DNModal").modal("hide");
		SetCalcPopUp();




 }); // end close fct




// Send input values from popup to boq table and close the popup using ESC
	document.addEventListener('keydown', function(event){
    	if(event.key === "Escape"){
$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="item"]').children('input').val($('#popupItemCode').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemModel"]').children('input').val($('#popupItemModel').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemPartNb"]').children('input').val($('#popupItemPartno').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="transType"]').children('select').val($('#popupTransType').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="notes"]').children('input').val($('#popupNotes').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="description"]').children('input').val($('#popupDescription').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="elementName"]').children('input').val($('#popupElementName').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="address"]').children('input').val($('#popupAddress').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="buttonApprove"]').children('input').val($('#popupApprovalStatus').val());
	//$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('input').val($('#popupApprovalAction').val());
  if($('#popupApprovalStatus').val() != "Completely Approved" ){
  $("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="approveStatus"]').children('select').val($('#popupApprovalAction > select').val());}
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="purchaseOrder"]').children('input').val($('#popupPO').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="workOrder"]').children('input').val($('#popupWO').val());

	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="fromSlot"]').children('input').val($('#popupFromSlot').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSlot"]').children('input').val($('#popupToSlot').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemQty"]').children('input').val($('#popupQty').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemRate"]').children('input').val($('#popupRate').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDAmout"]').children('input').val($('#popupDiscountAmount').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTax"]').children('input').val($('#popupTax').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemNetRate"]').children('input').val($('#popupNetRate').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotal"]').children('input').val($('#popupTotal').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemTotalAt"]').children('input').val($('#popupTotalAT').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="siteID"]').children('input').val($('#popupFromSite').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="tositeID"]').children('input').val($('#popupToSite').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemSN"]').children('input').val($('#popupFromSN').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="toSN"]').children('input').val($('#popupToSN').val());
	$("#bisotab >tbody").find("tr").eq(rowindx).find('td[name="itemDniID"]').children('input').val($('#popupDNitmID').val());


	    $("#DNModal").modal("hide");
		SetCalcPopUp();

    }
 });// end close fct using esc



	// Resize and drag the popup
	$('.modal-content').resizable({
	handles: "e" ,

	});

	$('.modal-dialog').draggable({
	handle: ".modal-header, .modal-footer"
	});

	$('#DNModal').on('show.bs.modal', function() {
	$(this).find('.modal-body').css({
	'max-height': '100%',
	});
	});

   	//Reset the popup to original size after resizing
	$('#DNModal').on('hidden.bs.modal', function() {
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


	//----------------------------autocomplete----------------------------------------------
	var ctx = getContextPath();

	function customRenderItem(ul, item) {
		var t = '<span class="mcacCol1">'+ item[0] +'</span><span class="mcacCol2">'+ item[1] +'</span>',
			result = $('<li class="ui-menu-item" role="menuitem"></li>')
			.data('item.autocomplete', item)
			.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">'+ t +'</a>')
			.appendTo(ul);

		return result;
	}

	$('#popupItemCode').autocomplete({


			source: function(request, response, event, ui) {


				var purchaseOrder = $('#popupPO').val();
				if(purchaseOrder == "")
				{
					purchaseOrder = "empty";
				}
				 if(purchaseOrder != "empty")
				 {
					 PrOrder = purchaseOrder.split(":");
					 PrOrderID = PrOrder[0];
				 }
				 else
					 PrOrderID = purchaseOrder;


				 var itemModel = $('#popupItemModel').val()
				 if(itemModel == "")
				 {
					itemModel = "empty";
				 }

				 var itemPartNb = $('#popupItemPartno').val();
				 if(itemPartNb == "")
				 {
					itemPartNb = "empty";
				 }

				 var WorkOrder = $('#popupWO').val();
				 if(WorkOrder == "")
				 {
					WorkOrder = "empty";
				 }
				 if(WorkOrder != "empty")
				 {
					 WoOrder = WorkOrder.split(":");
					 WoOrderID = WoOrder[0];
				 }
				 else
					 WoOrderID = WorkOrder;
					// console.log("*/*/WoOrderID is"+WoOrderID);


				 $.ajax({
					 type: "GET",
					 contentType: "application/json; charset=utf-8",
					 url: ctx+'/GetAllitemInDN',
					 data: {
							"itemCode" : request.term,
							 "PrOrderID" : PrOrderID,
							 "WoOrderID":WoOrderID,
							 "itemModel" : itemModel,
							 "itemPartNb" : itemPartNb
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
				this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] : '');

			  $('#popupItemModel').val(ui.item[2]);
			  $('#popupItemPartno').val(ui.item[3]);
				ItemCode = ui.item[0];

				PrOrderValue = $('#popupPO').val();
				if(PrOrderValue != "")
				{
					PrOrderValue = PrOrderValue.split(":");
					PrOrderValue = PrOrderValue[0];

					var qty = $('#popupQty');
					var rate = $('#popupRate');
					var discountAmount = $('#popupDiscountAmount');
					var tax = $('#popupTax');
					var obj =$('#popupPO');

					$.ajax({
						 type: "GET",
						 contentType: "application/json; charset=utf-8",
						 url: ctx+'/GetPOitemDetails',
						 data: {
								PoID : PrOrderValue,
								ItemCode : ItemCode,
						 },
						 dataType: "json",
						 success: function (data) {
							 if (data != null) {

									qty.val(1);    // fill the qty field
									rate.val(data.PoDetails[0][1]);  // fill the rate field
									discountAmount.val(data.PoDetails[0][2]);   // fill the discountAmount field
									 tax.val(data.PoDetails[0][3]);   // fill the tax field

									 //SetCalc(obj);
									 SetCalcPopUp();

							 }
						 },
						 error: function(result) {
							 alert("Error");
						 }
					 });
				}

				return false;
			}
		}).autocomplete("instance")._renderItem = function(ul, item) {
				if (item[2]==null && item[3]==null){
					return $('<li class="each"></li>').data( "item.autocomplete", item )
					.append('<div class="acItem"><span class="name" style="font-weight:bold">'+

					item[0] +'</span><br><span class="desc">'  +
					item[1] +'</span></div>').appendTo(ul);

				}
				else if(item[3]==' '){
					return $('<li class="each"></li>').data( "item.autocomplete", item )
						.append('<div class="acItem"><span class="name" style="font-weight:bold">'+

						item[0] +'</span><br><span class="desc">'  +
						item[1] +'</span><span class="desc">'+";"+
						item[2] +'</span></div>').appendTo(ul);
				}
				else if (item[2]==' '){
					return $('<li class="each"></li>').data( "item.autocomplete", item )
						.append('<div class="acItem"><span class="name" style="font-weight:bold">'+

						item[0] +'</span><br><span class="desc">'  +
						item[1] +'</span><span class="desc">'+";"+
						item[3] +'</span></div>').appendTo(ul);
				}
				else{
				return $('<li class="each"></li>').data( "item.autocomplete", item )
			   .append('<div class="acItem"><span class="name" style="font-weight:bold">'+

			   item[0] +'</span><br><span class="desc">'  +
			   item[1] +'</span><span class="desc">'+";"+
			   item[2] +'</span><span class="desc">'+";"+
			   item[3] +'</span></div>').appendTo(ul);
	 }
	};
	$('#popupItemCode').focus(function(){
		if (this.value == ""){
			$(this).autocomplete("search");
		   }
	});





         // starting for item auto complete in poq table

			/// Starting autocomplete for Item Model
			$('#popupItemModel').autocomplete({

		    	    source: function(request, response, event, ui) {
 					//console.log(2222);

						 var Item_code = $('#popupItemCode').val();
						 if(Item_code == "")
						 {
							Item_code = "empty";
						 }
	        			 if(Item_code != "empty")
		        		 {
	        				 Item_code = Item_code.split(":");
			        		 Item_code = Item_code[0];
			             }


						 var itemPartNb = $('#popupItemPartno').val();
						if(itemPartNb == "")
						{
							itemPartNb = "empty";
						}

						 var purchaseOrder = $('#popupPO').val();
						 if(purchaseOrder == "")
						 {
							purchaseOrder = "empty";
						 }
	        			 if(purchaseOrder != "empty")
		        		 {
	        				 PrOrder = purchaseOrder.split(":");
			        		 PrOrderID = PrOrder[0];
			             }
	        			 else{
	        				 PrOrderID = purchaseOrder;
	        			    // console.log("PrOrderID is "+PrOrderID);
							}


							 var WorkOrder = $('#popupWO').val();
							 if(WorkOrder == "")
							 {
								WorkOrder = "empty";
							 }
		        			 if(WorkOrder != "empty")
			        		 {
		        				 WoOrder = WorkOrder.split(":");
				        		 WoOrderID = WoOrder[0];
				             }
		        			 else{
		        				 WoOrderID = WorkOrder;
		        			     //console.log("*/*/WoOrderID is"+WoOrderID);
								}



	        			// console.log("itemPartNb is"+itemPartNb);

			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: ctx+'/GetItemModels',
			                 data: {
			                	 "ItemModel" : request.term,
			                	 Item_code : Item_code,
			                	 "WoOrderID":WoOrderID,
			                	 itemPartNb: itemPartNb,
			                	 PrOrderID : PrOrderID

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
						this.value = (ui.item ? ui.item[0] : '');

						$('#popupItemCode').val(ui.item[1]+ ":"+ ui.item[2]);
						$('#popupItemPartno').val(ui.item[3]);

							return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					 if (item[3]==' '){
        				 return $('<li class="each"></li>').data( "item.autocomplete", item )
 		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">'+

 	                    item[0] +'</span><br><span class="desc">'  +
 	                    item[1] +'</span><span class="desc">'+";"+
 	                    item[2] +'</span></div>').appendTo(ul);

	        		 }
					 else{

		    	return $('<li class="each"></li>').data( "item.autocomplete", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">'+

	                    item[0] +'</span><br><span class="desc">'  +
	                    item[1] +'</span><span class="desc">'+";"+
	                    item[2] +'</span><span class="desc">'+";"+
	                    item[3] +'</span></div>').appendTo(ul);
					 }
			};
			$('#popupItemModel').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});
			//});

			// ending the autocomplete of Item Model

			/// Starting autocomplete for ItemPartNb
			$('#popupItemPartno').autocomplete({
				//$(el).autocomplete({
		    	    source: function(request, response, event, ui) {

						 var Item_code = $('#popupItemCode').val();
						 if(Item_code == "")
						 {
							Item_code = "empty";
						 }

	        			 if(Item_code != "empty")
		        		 {
	        				 Item_code = Item_code.split(":");
			        		 Item_code = Item_code[0];
			             }


						 var purchaseOrder = $('#popupPO').val();
						 if(purchaseOrder == "")
						 {
							purchaseOrder = "empty";
						 }
	        			 if(purchaseOrder != "empty")
		        		 {
	        				 PrOrder = purchaseOrder.split(":");
			        		 PrOrderID = PrOrder[0];
			             }
	        			 else
	        				 PrOrderID = purchaseOrder;


						 var itemModel = $('#popupItemModel').val();
						 if(itemModel == "")
						 {
							itemModel = "empty";
						 }


						 var WorkOrder = $('#popupWO').val();
						 if(WorkOrder == "")
						 {
							WorkOrder = "empty";
						 }
	        			 if(WorkOrder != "empty")
		        		 {
	        				 WoOrder = WorkOrder.split(":");
			        		 WoOrderID = WoOrder[0];
			             }
	        			 else
	        				 WoOrderID = WorkOrder;



			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetItemPartNbs',
			                 data: {
			                	 "ItemPartNb" : request.term,
			                	 Item_code : Item_code,
			                	 "itemModel" : itemModel,
			                	 "WoOrderID":WoOrderID,
			                	 PrOrderID : PrOrderID
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.ListPartNbs);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			        }, minLength:0, maxShowItems: 40, scroll:true,
					select: function(event, ui) {
						this.value = (ui.item ? ui.item[0] : '');
						$('#popupItemCode').val(ui.item[1]+ ":"+ ui.item[2]);
						$('#popupItemModel').val(ui.item[3]);
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					if (item[3]==' '){


       				 return $('<li class="each"></li>').data( "item.autocomplete", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">'+

	                    item[0] +'</span><br><span class="desc">'  +
	                    item[1] +'</span><span class="desc">'+";"+
	                    item[2] +'</span></div>').appendTo(ul);

	        		 }
					 else{

		    	return $('<li class="each"></li>').data( "item.autocomplete", item )
		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">'+

	                    item[0] +'</span><br><span class="desc">'  +
	                    item[1] +'</span><span class="desc">'+";"+
	                    item[2] +'</span><span class="desc">'+";"+
	                    item[3] +'</span></div>').appendTo(ul);
					 }
			};
			$('#popupItemPartno').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			//});
			});


			//auto complete for PO (when adding new rows)
			$('#popupPO').autocomplete({
		    	    source: function(request, response, event, ui) {

						 var Item_code = $('#popupItemCode').val();
						 if(Item_code == "")
						 {
							Item_code = "empty";
						 }
	        			 if(Item_code != "empty")
		        		 {
	        				 Item_code = Item_code.split(":");
			        		 Item_code = Item_code[0];
			             }

						 var itemModel = $('#popupItemModel').val();
						 if(itemModel == "")
						 {
							itemModel = "empty";
						 }

						 var itemPartNb = $('#popupItemPartno').val();
						 if(itemPartNb == "")
						 {
							itemPartNb = "empty";
						 }

						 var WorkOrder = $('#popupWO').val();
						 if (WorkOrder == "")
						 {
							WorkOrder = "empty";
						 }

	        			 if(WorkOrder != "empty")
		        		 {
	        				 WoOrder = WorkOrder.split(":");
			        		 WoOrderID = WoOrder[0];
			             }
	        			 else
	        				 WoOrderID = WorkOrder;
	        			   //  console.log("*/*/WoOrderID is"+WoOrderID);



	        			// console.log("itemPartNb is"+itemPartNb);

			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetAllPoByItem',
			                 data: {
			                	 	"PO" : request.term,
									"Item_code" : Item_code,
									"itemModel" : itemModel,
									"WoOrderID":WoOrderID,
									"itemPartNb" : itemPartNb

							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {

			                         response(data.ListPos);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			        }, minLength:0, maxShowItems: 40, scroll:true,
					select: function(event, ui) {
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] + ":"+ ui.item[3] : '');
						var PoID = ui.item[0];

						ItemCode = $('#popupItemCode').val();
						if(ItemCode != "")
						{
							ItemCode = ItemCode.split(":");
							ItemCode = ItemCode[0];

							var qty = $('#popupQty');
							var rate = $('#popupRate');
							var discountAmount = $('#popupDiscountAmount');
							var tax = $('#popupTax');
							var obj = $('#popupNetRate');

							$.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url:ctx+'/GetPOitemDetails',
				                 data: {
										PoID : PoID,
										ItemCode : ItemCode,
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {

						                    qty.val(1);    // fill the qty field
						                    rate.val(data.PoDetails[0][1]);  // fill the rate field
						                    discountAmount.val(data.PoDetails[0][2]);   // fill the discountAmount field
						         			tax.val(data.PoDetails[0][3]);   // fill the tax field

						         			//SetCalc(obj);
											 SetCalcPopUp();
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
						}
			             return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
			};
			$('#popupPO').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			//});
			});/////////////end autocpmplete for PO

			//Starting for Warehouse auto complete in poq table
			$('#popupToSite').autocomplete({
		    	    source: function(request, response, event, ui) {
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetAllWarehouse',
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
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
			};
			$('#popupToSite').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			});


			$('#popupFromSite').autocomplete({
				//$(el).autocomplete({
		    	    source: function(request, response, event, ui) {
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetAllWarehouse',
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
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1] + ":"+ ui.item[2] : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] +', '+ item[2] + "</span></div>")
	                .appendTo(ul);
			};
			$('#popupFromSite').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			//});
			});/////////////end autocpmplete for warhouse

			$('#popupWO').autocomplete({
		    	    source: function(request, response, event, ui) {

						 var Item_code = $('#popupItemCode').val();
						 if(Item_code == "")
						 {
							Item_code = "empty";
						 }
	        			 if(Item_code != "empty")
		        		 {
	        				 Item_code = Item_code.split(":");
			        		 Item_code = Item_code[0];
			             }

						 var purchaseOrder = $('#popupPO').val();
						 if(purchaseOrder == "")
						 {
							purchaseOrder = "empty";
						 }
	        			 if(purchaseOrder != "empty")
		        		 {
	        				 PrOrder = purchaseOrder.split(":");
			        		 PrOrderID = PrOrder[0];
			             }
	        			 else
	        				 PrOrderID = purchaseOrder;
	        			    // console.log("PrOrderID is "+PrOrderID);
						 var itemModel = $('#popupItemModel').val();
						 if(itemModel == "")
						 {
							itemModel = "empty";
						 }

						 var itemPartNb = $('#popupItemPartno').val();
						 if(itemPartNb == "")
						 {
							itemPartNb = "empty";
						 }



			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url:ctx+'/GetALLWOByItem',
			                 data: {

				                 "woId":request.term,
			                	 "ItemPartNb" :itemPartNb,
			                	 Item_code : Item_code,
			                	 "itemModel" : itemModel,
			                	 PrOrderID : PrOrderID
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.ListWO);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			        }, minLength:0, maxShowItems: 40, scroll:true,
					select: function(event, ui) {
						this.value = (ui.item ? ui.item[0] + ":"+ ui.item[1]  : '');
						return false;
					}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>"+
	                    item[0] + "</span><br><span class='desc'>"+
	                    item[1] + "</span></div>")
	                .appendTo(ul);
			};
			$('#popupWO').focus(function(){
				if (this.value == ""){
					$(this).autocomplete("search");
	   	        }
			//});
			});
	//---------------------------------------------------------------


	// Calculate netRate, total, totalAt, sum of TotalAt in popup
	$('#popupRate, #popupDiscountAmount,#popupQty,#popupNetRate,#popupTax').change(function() {

		if ($('#popupDiscountAmount').val() == '') {
			$('#popupDiscountAmount').val(0);
		}

		if ($('#popupTax').val() == '') {
			$('#popupTax').val(0);
		}
		if ($('#popupRate').val() == '') {
			$('#popupRate').val(0);
		}
		if ($('#popupQty').val() == '') {
			$('#popupQty').val(0);
		}
		if ($('#popupTotalAt').val() == '') {
			$('#popupTotalAt').val(0);
        }

		var popupNetRate = parseFloat($('#popupRate').val()) - parseFloat($('#popupDiscountAmount').val());
		$('#popupNetRate').val(popupNetRate);

		var popupTotal = parseFloat($('#popupQty').val()) * (popupNetRate);
		$('#popupTotal').val(popupTotal);

		var popupTax = ((parseFloat($('#popupTax').val()) * popupNetRate)/100);

		var popupTotalAt = parseFloat($('#popupQty').val()) * (popupNetRate+popupTax);

		$('#popupTotalAt').val(popupTotalAt);

	});


   	});
   	
   
