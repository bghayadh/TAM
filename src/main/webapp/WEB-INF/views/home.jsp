<!DOCTYPE html>
										 
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>    
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/resources/css/Home.css" rel="stylesheet" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">

    <title>Home</title>
<style>

.module {
  width: 200px;
  height: 200px;
  background: 	chartreuse;
  position: relative;
  border: 5px 	chartreuse;  
  margin: 20px;
}
.module:after {
  content: '';
  position: absolute;
  top: -10px;
  left: -10px;
  right: -10px;
  bottom: -10px;
  background: blue;
  z-index: -1;
}			   



<!-- -------------- -->

.center {
  text-align:center;
  margin-top: 10%;
}









:root {
  --linethick: 3px;
  --linewidth: 3.8em;
}


.process_diagram li > div { background-color: #006868; color:rgb(19, 18, 18); border-style:solid; box-shadow: 1px 1px; border-color:gold; text-align:left; }

.process_diagram{margin:0 auto; padding:0px; display:block; list-style:none; text-align:center; vertical-align:middle;}
.process_diagram ol,
.process_diagram ul,
.process_diagram li {margin:0 auto; padding:0; display:block; list-style:none; text-align:center; vertical-align:middle;}

.process_diagram > li,
.process_diagram ol > li {display:table-cell;}

.process_diagram > li,
.process_diagram ol > li,
.process_diagram ul > li {padding:1.0em 0em;}


.process_diagram li > div {position:relative; margin:0 var(--linewidth); padding: 1.0em; border-width:5px; width:300px;height:180px;}


i{color: white; font-family: Noto Sans;}



 th, td {
  
  border-bottom: 1px solid gold;
  
  border-collapse: collapse;
  
  font-size: smaller;
  
}

table, th, td{
color:white;}

th, td{text-align:center;
 } 
 

 
 .button {
  background-color: #2406ad; 
  border: none;
  color: white;
  padding: 6px 12px;
  text-align: center;
  text-decoration: red;
  display: inline-block;
  margin: 0px 0px;
  cursor: pointer;
	 border-radius:5px;				
}
.button1:hover {
  background-color: #160469;;
  color: gold;
}

.button1 {font-size: 12px;}
 
  .button2 {
  background-color:  dimgrey; 
  color: white;
  text-align: center;
  display: inline-block;
  cursor: pointer;
  font-size: 14px;
  border-radius:5px;
  
}
.button3:hover {
  background-color: dimgrey;
  color: gold;
} 
</style>


</head>
	  
	
			   
	 


<body>
<%--      <c:set var = "page" value = "home"/> --%>

<%-- 	<%@ include file="header.html" %> --%>

  <c:set var="pg" value="home" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
    
  <svg xmlns="http://www.w3.org/2000/svg" 
            width="100%" 
            height="100%" preserveAspectRatio="none"
            style="position:absolute; z-index:-1; left:0; top:0; overflow:visible;">
         <defs>
           <marker id="arrowhead" viewBox="0 0 10 10" refX="9" refY="5" fill="gold"
               markerWidth="6" markerHeight="6" orient="auto-start-reverse" stroke="black">
             <path d="M 0 0 L 10 5 L 0 10 z" />
           </marker>
		   
		   
		   <marker id="dashedarrow" viewBox="0 0 10 10" refX="5" refY="5" fill="gold"
               markerWidth="6" markerHeight="6" orient="auto-start-reverse" >
			<path stroke-dasharray="10,10" d="M5 40 l215 0" />
		   </marker>
		   
		   
         </defs>
         <g fill="none" stroke="gold" stroke-width="3" marker-end="url(#arrowhead)">
         <path id="arrow1">
         </g>
         <g fill="none" stroke="gold" stroke-width="3" marker-end="url(#arrowhead)">
         <path id="arrow2">
		 </g>
		 <g fill="none" stroke="gold" stroke-width="3" marker-end="url(#arrowhead)">
         <path id="arrow3">
		 </g>
		 <g fill="none" stroke="gold" stroke-width="3" marker-end="url(#arrowhead)">
         <path id="arrow4">
		 </g>
		 <g fill="none" stroke="gold" stroke-width="3" marker-end="url(#arrowhead)">
         <path id="arrow5">
		 </g>
		 <g fill="none" stroke="gold" stroke-width="3" marker-end="url(#arrowhead)">
         <path id="arrow6">
		 </g>
		 
		  <g fill="none"  stroke="gold" stroke-width="3" stroke-dasharray="10,10" d="M5 40 l215 0" marker-end="url(#arrowhead)">
			<path id="arrow7" width="100%" height="100%">
			
			</g>
       </svg>
      
 <div style="margin-left:30px;">
  <!-- <div class="input-group-prepend">
				<span class="input-group-text">Voucher Type</span>
				
				<select   id="voucherType" name="VoucherType"  >
				
    				 <option value="none">---Voucher Type---</option>
			           <option value="pr" selected>Purchase Request</option>
			           <option value="po" >Purchase Order</option>
			           <option value="gr">Goods Received</option>
    				
  				</select>
				
				</div>	-->
 
<form class="form-inline">
<!--  <label ><i style="color:#DCF8C6">Voucher type :</i></label>-->
<div class="col-md-4" style="margin-top:1%;">
<div class="input-group-prepend">
<span class="input-group-text">Voucher type</span>
<select id="voucherType" name="VoucherType" style="width: 300px;  text-align-last:center;" >
					  <option value="none">---Voucher Type---</option>
			           <option value="pr" selected>Purchase Request</option>
			           <option value="po" >Purchase Order</option>
			           <option value="gr">Goods Received</option>
			          
		            </select>
		            </div>
		            </div>
<!--  <label><i style="color:#DCF8C6">Voucher number :</i></label>-->
<div class="col-md-5" style="margin-top:1%;">
<div class="input-group-prepend">
<span class="input-group-text"> Voucher Number</span>
<input type="text" id="vncode" name="VNCODE1" value="${Vnb2}" style="width:300px;"  class="ui-widget ui-widget-content ui-corner-all"/> 
<input type="submit" value="Search" id="subbutton" class="button2 button3" style="width:100px;" >
</div>
</div>
<!--  <input type="submit" value="Search" id="subbutton" class="button2 button3" >-->
</form>

</div>
 <ol class="process_diagram">
	<li>
		<div id="box1">
		<div>
		<b id="pR"style="color:gold;">PRQ</b>
		<!--  <a href='/PurchaseReqFormView?type=addNew' style="float:right; color:gold;"><b>Add New</b></a> -->
		<button class="button button1" style="float:right;" onclick="window.location.href='PurchaseReqFormView?type=addNew'">Add New</button>
		</div><!--  <i></br> ${PR}</i> -->
		  <div id="pr" style="margin-top:20px;clear:both;">
	<!--   <table cellpadding="2" cellspacing="2">
									<th></th><th style="width:60px;">${StatUs}</th>
									<tr><td>ID</td><td style="border-right:0px;" >${priD}</td></tr>
									<tr><td>Total Net Amount </td><td style="border-right:0px;">${nettotalno}</td></tr>
									<tr><td style="border-bottom:0px;">Total Qty</td><td style="border-right:0px;border-bottom:0px;">${totalqtyno}</td></tr>
									</table> -->	
		</div>
		</div>
		</li>
	<li>
		<ul>
			<li>
				<ol>
					<li>
							<div id="box2">
							<div>
							<b id="gR" style="color:gold;">GR</b>
							<!--  <a href='/GoodsRcvFormView?type=addNew' style="float:right; color:gold;"><b>Add New</b></a>-->
							<button class="button button1" style="float:right;" onclick="window.location.href='GoodsRcvFormView?type=addNew'">Add New</button>
							</div>
							<!-- <div><i> ${GR_1} </br> ${GR_2}</div></i> -->
							<!-- <div>${GR_1}</div> -->
								 <div id="gr" style="margin-top:20px;clear:both;">
								  <!--    <table cellpadding="2" cellspacing="2">
									<tr><th></th><th style="width:60px;">All</th><th style="border-right:0px;" >Not Completed</th></tr>
									<tr><td>Nb of GR</td><td>${GR_2}</td><td style="border-right:0px;" >${GR_2n}</td></tr>
									<tr><td>Total Net Amount </td><td>${GR_1}</td><td style="border-right:0px;">${GR_1n}</td></tr>
									<tr><td style="border-bottom:0px;">Total Qty</td><td style="border-bottom:0px;">${GR_3}</td><td style="border-right:0px;border-bottom:0px;">${GR_3n}</td></tr>
									</table> 	-->
								</div> 
							</div>
					</li>
					
					<li><div id="box3">
					<div>
					<b id="aR" style="color:gold;">AR</b>
					<!--  <a href='/AssetRegistryFormView?type=addNew'style="float:right;color:gold;"><b>Add New</b></a>-->
					<button class="button button1" style="float:right;" onclick="window.location.href='AssetRegistryFormView?type=addNew'">Add New</button>
					</div>
					<div id="ar" style="margin-top:20px;clear:both;">
				 	<!--  <table cellpadding="2" cellspacing="2">
									
									<tr><td style="width:100px;">Available </td><td style="width:100px;border-right:0px;">${ARavailable}</td></tr>
									<tr><td style="width:100px;border-bottom:0px;">Total </td><td style="width:100px;border-bottom:0px;border-right:0px;">${ARtotal}</td></tr>
									
									</table> -->
					</div>
					
					</div>
				</ol>
			</li>
			<li>								
				<ol>
					<li><div  id="box4">
					<div><b id="pO" style="color:gold;">PO</b>
					<!--  <a href='/PurchaseOrderFormView?type=addNew'style="float:right;color:gold;"><b>Add New</b></a>-->
					<button class="button button1" style="float:right;" onclick="window.location.href='PurchaseOrderFormView?type=addNew'">Add New</button>
					</div>
					<!--  <i>  ${PO_1} </br> ${PO_2}  </i> -->
					<div id="po" style="margin-top:20px;clear:both;">
					 <!--  	<table cellpadding="2" cellspacing="2">
									<tr><th></th><th style="width:60px;">All</th><th style="border-right:0px;">Not Completed</th></tr>
									<tr><td>Nb of PO</td><td>${PO_2}</td><td style="border-right:0px;">${PO_2n}</td></tr>
									<tr><td>Total Net Amount </td><td>${PO_1}</td><td style="border-right:0px;">${PO_1n}</td></tr>
									<tr><td style="border-bottom:0px;">Total Qty</td><td style="border-bottom:0px;">${PO_tq}</td><td style="border-right:0px;border-bottom:0px;">${PO_tqn}</td></tr>
									</table>   -->
									
					</div>
					 </div>
					</li>
					<li><div  id="box5">
					<div><b id="dN" style="color:gold;">DN</b>
					<!--  <a href='/DiscoveryNewFormView?type=addNew' style="float:right; color:gold;"><b>Add New</b></a>-->
					<button class="button button1" style="float:right;" onclick="window.location.href='DiscoveryNewFormView?type=addNew'">Add New</button>
					</div>
					<!--  <i> </i></div></li>  -->
					<div id="dn" style="margin-top:20px;clear:both;">
					<!--    <table cellpadding='2' cellspacing='2'>
					<tr><th></th><th style='width:60px;'>All</th><th style='border-right:0px;'>Not Completed</th></tr>
					<tr><td>Nb of DN</td><td>${DNsize}</td><td style='border-right:0px;'>${DNsizenotc}</td></tr>
					<tr><td>Total Net Amount </td><td>${DNnettot}</td><td style='border-right:0px;'>${DNnettotnotc}</td></tr>
					<tr><td style='border-bottom:0px;'>Total Qty</td><td style='border-bottom:0px;'>${DNtotqty}</td><td style='border-right:0px;border-bottom:0px;'>${DNtotqtynotc}</td></tr>
					</table>-->
					</div> 
					
				</ol>
			</li>
			<li>
				<ol>
					<li><div id="box6">
					<div><b id="cIP" style="color:gold;">CIP</b>
					 <!--   <a href='/CapitalInProgressFormView?type=addNew'style="float:right;color:gold;"><b>Add New</b></a> -->
					  <button class="button button1" style="float:right;" onclick="window.location.href='CapitalInProgressFormView?type=addNew'">Add New</button>
					 <!--   <button class="button1" onclick="window.location.href='CapitalInProgressFormView?type=addNew';" style="float:right;">Add New</button>-->
					</div>
					<!--  <i>  ${CIP_COUNT}</br> ${CIP_TOTAL}  </i> -->
					<div id="cip" style="margin-top:20px;clear:both;">
					 	<!--  <table cellpadding="2" cellspacing="2">
									
									<tr><td style="width:100px;">Available </td><td style="width:100px;border-right:0px;">${CIP_COUNT}</td></tr>
									<tr><td style="width:100px;border-bottom:0px;">Total </td><td style="width:100px;border-bottom:0px;border-right:0px;">${CIP_TOTAL}</td></tr>
									
									</table> -->
					</div>
					</div>
					</li>
					
					<li><div id="box7">
					<div><b id="fAR" style="color:gold;">FAR</b>
				<!--  	<a href='/FixedAssetRegistryFormView?type=addNew' style="float:right; color:gold;"><b>Add New</b></a> -->
					<button class="button button1" style="float:right;" onclick="window.location.href='FixedAssetRegistryFormView?type=addNew'">Add New</button>
					</div>
					<!--   <i> </i></div></li> -->
					<div id="far" style="margin-top:20px;clear:both;">
					 	<!--  <table cellpadding="2" cellspacing="2">
									
									<tr><td style="width:100px;">Available </td><td style="width:100px;border-right:0px;">${CIP_COUNT}</td></tr>
									<tr><td style="width:100px;border-bottom:0px;">Total </td><td style="width:100px;border-bottom:0px;border-right:0px;">${CIP_TOTAL}</td></tr>
									
									</table>  -->
					</div>
					 
				</ol>
			</li>
			
		</ul>
	</li>
	
	
</ol>
 
	

		 
       <script>
       
       function draw_arrows()
       {
         draw_arrow( "#box1", "rm", "#box4", "lm", "#arrow1" ); 
         draw_arrow( "#box4", "mt", "#box2", "mb", "#arrow2" ); 
		 draw_arrow( "#box4", "mb", "#box6", "mt", "#arrow3" );
		 draw_arrow( "#box5", "mt", "#box3", "mb", "#arrow4" );
		 draw_arrow( "#box5", "mb", "#box7", "mt", "#arrow5" );
		 draw_arrow( "#box7", "lm", "#box6", "rm", "#arrow6" );
		 draw_arrow( "#box1", "mt", "#box2", "lm", "#arrow7");												   											   
       }
       
       draw_arrows();
       

       $('.navbar-collapse').on('shown.bs.collapse', function () {
    	 	 
    	     	 draw_arrows();    
    	              });

       $('.navbar-collapse').on('hidden.bs.collapse', function () {
       	
       	 			draw_arrows();
       	            
       	            

       	});
    
       $(window).resize(function () {
			draw_arrows();
			
           });
       
      window.onresize = draw_arrows();
     
     
       </script> 
									  
											  	 

</body>

<script type='text/javascript'>



$(document).ready(function() {

  $("#voucherType").change(function(){
	 vncode.value = null;
 }); 




 if('${actionType}' == "SearchPage")
{
	var VoucherType = '${VoucherType}';
	$("#voucherType").val(VoucherType);

	if(VoucherType == "pr")
	{
		var pr_table = "";

		$("#pr").append(tablepr);
		$("#po").append(tablepo);
		$("#cip").append(tablecip);
		$("#gr").append(tablegr);
		$("#dn").append(tabledn);
		$("#ar").append(tablear);
		$("#far").append(tablefar);
		//$("ol>li>#box1").css("border-color", "rgb(121, 119, 250)");				
		 var NCpo = "${poNotCom}";
		 var Cpo = "${poCom}";
		var NCgr = "${grNotCom}";
		var Cgr = "${grCom}";
		var NCdn = "${dnNotComp}";
		var PRstatus ="${prStatus}";
		var Cdn = "${dnComp}";

		if(PRstatus == "completed" || PRstatus == "closed")
		{
			$("ol>li>#box1").addClass("module");
			$("ol>li>#box1").css("border-color", "chartreuse");
			$("#pR").css("color","chartreuse");
		}
		else if(PRstatus != "completed" || PRstatus != "closed")
		{
			$("ol>li>#box1").addClass("module");
			$("ol>li>#box1").css("border-color", "darkorange");
		}
			if(Cpo == 0)
				{$("ol>li>#box4").css("border-color", "gold");}
			else{
		if(Cpo != 0 && NCpo == 0)
			{
			$("ol>li>#box4").css("border-color", "chartreuse");
			$("#pO").css("color","chartreuse")
			}
		else
			{$("ol>li>#box4").css("border-color", "darkorange");}
			}
		if(Cgr == 0)
			{$("ol>li>#box2").css("border-color", "gold");
			}
		else{
		if (Cgr !=0 && NCgr == 0)
			{
			$("ol>li>#box2").css("border-color", "chartreuse");
			$("gR").css("color", "chartreuse");
			}
		else
			{$("ol>li>#box2").css("border-color", "darkorange");
			
			}
		}
		if(Cdn == 0)
			{$("ol>li>#box5").css("border-color", "gold");
			}
			
		else{
			if (NCdn == 0 && Cdn != 0)
				{ 
					$("ol>li>#box5").css("border-color", "chartreuse");
					$("#dN").css("color", "chartreuse");
				}
			else
				{$("ol>li>#box5").css("border-color", "darkorange");}
			}
		
		
	}


	if(VoucherType == "po")
	{
		var po_table = "";
		$("#pr").append(tableprinpo);
		$("#cip").append(tablecip);
		$("#gr").append(tablegr);
		$("#dn").append(tabledn);
		$("#ar").append(tablear);
		$("#far").append(tablefar);
		$("#po").append(tablepoinpo);
		$("ol>li>#box4").css("border-color", "rgb(121, 119, 250)");
		var NCgr = "${grNotCom}";
		var Cgr = "${grCom}";
		var NCdn = ("${dnNotComp}");
		var Cdn = "${dnComp}";
		var PRstatus = "${prStatus}";
		var poStatus = "${poStatus}";

		if(PRstatus == "completed" || PRstatus == "closed" )
		{
		$("ol>li>#box1").css("border-color", "chartreuse");
		$("#pR").css("color", "chartreuse");
		}
		else if(PRstatus != "completed" || PRstatus != "closed" )
			{$("ol>li>#box1").css("border-color", "darkorange");}

		if(poStatus == "completed" || poStatus == "closed")
		{
		$("ol>li>#box4").addClass("module");
		$("ol>li>#box4").css("border-color", "chartreuse");
		$("#pO").css("color", "chartreuse");
		}
		else if(poStatus != "completed" || poStatus != "closed")
		{
		$("ol>li>#box4").addClass("module");
		$("ol>li>#box4").css("border-color", "darkorange");
		}
		if(Cgr == 0)
		{$("ol>li>#box2").css("border-color", "gold");}
		else {
		if(NCgr == 0 && Cgr != 0)
		{ 
		$("ol>li>#box2").css("border-color", "chartreuse");
		$("#gR").css("color", "chartreuse");
		}
		else if(NCgr != 0)
			{$("ol>li>#box2").css("border-color", "darkorange");}
		}

		if(Cdn == 0)
		{$("ol>li>#box5").css("border-color", "gold");}
		else {
		if (NCdn == 0 && Cdn != 0)
		{ 
		$("ol>li>#box5").css("border-color", "chartreuse");
		$("#dN").css("color","chartreuse");
		}
		else if(NCdn != 0)
			{$("ol>li>#box5").css("border-color", "darkorange");}
		}
		
	}
	
	if(VoucherType == "gr")
	{
		$("#pr").append(tablepringr);
		$("#po").append(tablepoingr);
		$("#cip").append(tablecip);
		//$("#pr").append(tableprq);
		$("#dn").append(tabledningr);
		$("#ar").append(tablear);
		$("#far").append(tablefar);
		$("#gr").append(tablegringr);
		$("ol>li>#box2").css("border-color", "rgb(121, 119, 250)");
		var PRstatus = "${grPrStatus}";
		var POstatus = "${grPoStatus}";
		var grStatus = "${grStatus}";
		var dnStatus = "${grDN_Status}";
		if(PRstatus == "completed" || PRstatus == "closed" )
			{
			$("ol>li>#box1").css("border-color", "chartreuse");
			$("#pR").css("color","chartreuse");
			}
		else if(PRstatus != "completed" || PRstatus != "closed" )
		{
		$("ol>li>#box1").css("border-color", "darkorange");
		
		}
		if(POstatus == "completed" || POstatus == "closed")
			{  
			$("ol>li>#box4").css("border-color", "chartreuse");
			$("#pO").css("color","chartreuse");
			}
		else if(POstatus != "completed" || POstatus != "closed")
		{  
		$("ol>li>#box4").css("border-color", "darkorange");
		
		}
		if(grStatus == "completed" || grStatus == "closed")
		{
		$("ol>li>#box2").addClass("module");
		$("ol>li>#box2").css("border-color", "chartreuse");
		$("#gR").css("color","chartreuse");
		}
		else if(grStatus != "completed" || grStatus != "closed")
		{
		$("ol>li>#box2").addClass("module");
		$("ol>li>#box2").css("border-color", "darkorange");
		}
		
		if(dnStatus == "completed" || dnStatus == "closed")
		{
		$("ol>li>#box5").css("border-color", "chartreuse");
		$("#dN").css("color","chartreuse");
		}
		else if(dnStatus != "completed" || dnStatus != "closed")
		{
			$("ol>li>#box5").css("border-color", "darkorange");
		}
	}

	
																					  
	

	

}	


else
	 {
		//alert("defaultPage");
	 }

});

$("#vncode").autocomplete({
    source: function(request, response, event, ui) {
        		
             $.ajax({
                 type: "GET",
                 contentType: "application/json; charset=utf-8",
                 url: '${pageContext.request.contextPath}/GetallVtypehome',
                 data: {
	                 	"voucherType" : $("#voucherType").children("option:selected").val(),
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
        	    vncode.value = (ui.item ? ui.item[0] : '');
				return false;
					}
		    }).autocomplete("instance")._renderItem = function(ul, item) {
	            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    item[0] + "</span></div>")
                .appendTo(ul);
        	};
        	$("#vncode").focus(function(){
   	        	if (this.value == ""){
   	            	$(this).autocomplete("search");
   	        	}						
			});
        	



</script>
        	
        	


<script>
var tablepo ='<table cellpadding="2" cellspacing="2" width="100%" height="100%">'
	+'<tr><th></th><th>All</th><th style="border-right:0px;">Not Completed</th></tr>'
	+'<tr><th>Nb of PO</th><td>${poCom}</td><td style="border-right:0px;">${poNotCom}</td></tr>'
	+'<tr><th>Total Net Amount </th><td>${netPOCom}</td><td style="border-right:0px;">${netPoNotCom}</td></tr>'
	+'<tr><th style="border-bottom:0px;">Total Qty</th><td style="border-bottom:0px;">${totQtyPOCom}</td>'
	+'<td style="border-right:0px;border-bottom:0px;">${totQtyPoNotCom}</td></tr></table>'; 

var tablegr = '<table cellpadding="4" cellspacing="2" width="100%" height="100%">'
	+'<tr><th></th><th>All</th><th style="border-right:0px;" >Not Completed</th></tr>'
	+'<tr><th>Nb of GR</th><td>${grCom}</td><td style="border-right:0px;" >${grNotCom}</td></tr>'
	+'<tr><th>Total Net Amount </th><td>${netGrCom}</td><td style="border-right:0px;">${netGRNotCom}</td></tr>'
	+'<tr><th style="border-bottom:0px;">Total Qty</th><td style="border-bottom:0px;">${totQtyGrCom}</td>'
	+'<td style="border-right:0px;border-bottom:0px;">${totQtyGRNotCom}</td></tr></table>';

var tablecip = '<table cellpadding="2" cellspacing="2" width="100%" height="100%">'
	+'<tr><th style="width:100px;">Nb. Of CIPs </th><td style="width:100px;border-right:0px;">${cipCountAll}</td></tr>'
	+'<tr><th style="width:100px;">Valuation Rate </th><td style="width:100px;border-right:0px;">${cipNetTotAll}</td></tr>'
	+'<tr><th style="width:100px;border-bottom:0px;">Total Qty </th>'
	+'<td style="width:100px;border-bottom:0px;border-right:0px;">${cipTotQtyAll}</td></tr></table>';

var tabledn = '<table cellpadding="2" cellspacing="2" width="100%" height="100%">'
	+'<tr><th></th><th style="width:60px;">All</th><th style="border-right:0px;">Not Completed</th></tr>'
	+'<tr><th>Nb of DN</th><td>${dnComp}</td><td style="border-right:0px;">${dnNotComp}</td></tr>'
	+'<tr><th>Total Net Amount </th><td>${dnNetTotComp}</td><td style="border-right:0px;">${dnNetTotNotComp}</td></tr>'
	+'<tr><th style="border-bottom:0px;">Total Qty</th><td style="border-bottom:0px;">${dnTotQtyComp}</td>'
	+'<td style="border-right:0px;border-bottom:0px;">${dnTotQtyNotComp}</td></tr></table>';

var tablear = '<table cellpadding="2" cellspacing="2" width="100%" height="100%">'
	+'<tr><th style="width:100px;">Nb. Of ARs </th><td style="width:100px;border-right:0px;">${arCountAll}</td></tr>'
	+'<tr><th style="width:100px;border-bottom:0px;">Valuation Rate </th>'
	+'<td style="width:100px;border-bottom:0px;border-right:0px;">${arValRateAll}</td></tr></table>';

var tablefar = '<table cellpadding="2" cellspacing="2" width="100%" height="100%">'
	+'<tr><th style="width:100px;">Nb. Of FARs </th><td style="width:100px;border-right:0px;">${farCountAll}</td></tr>'
	+'<tr><th style="width:100px;border-bottom:0px;">Valuation Rate </th><td style="width:100px;border-bottom:0px;border-right:0px;">${farValRateAll}</td></tr></table>';

var tablepr ='<table cellpadding="3" cellspacing="3" width="100%" height="100%">'
	+'<tr><th></th><th style="width:60px;color:gold;border-right:0px;">${prStatus}</th></tr>'
	+'<tr><th style="color:gold;">ID</th><td style="color:gold;border-right:0px;">${prId}</td></tr>'
	+'<tr><th style="color:gold;">Total Net Amount </th><td style="color:gold;border-right:0px;">${netPrCom}</td></tr>'
	+'<tr><th style="border-bottom:0px;color:gold;">Total Qty</th><td style="border-bottom:0px;color:gold;border-right:0px;">${totQtyPrCom}</td>'
	+'</tr></table>'; 

var tablegringr ='<table cellpadding="3" cellspacing="3" width="100%" height="100%">'
	+'<tr><th></th><th style="width:60px;color:gold;border-right:0px;">${grStatus}</th></tr>'
	+'<tr><th style="color:gold;">ID</th><td style="color:gold;border-right:0px;">${grId}</td></tr>'
	+'<tr><th style="color:gold;">Total Net Amount </th><td style="color:gold;border-right:0px;">${grNetTot}</td></tr>'
	+'<tr><th style="border-bottom:0px;color:gold;">Total Qty</th><td style="border-bottom:0px;color:gold;border-right:0px;">${grTotQty}</td>'
	+'</tr></table>'; 

var tablepoinpo ='<table cellpadding="3" cellspacing="3" width="100%" height="100%">'
	+'<tr><th></th><th style="width:60px;color:gold;border-right:0px;">${poStatus}</th></tr>'
	+'<tr><th style="color:gold;">ID</th><td style="color:gold;border-right:0px;">${poId}</td></tr>'
	+'<tr><th style="color:gold;">Total Net Amount </th><td style="color:gold;border-right:0px;">${poNetTot}</td></tr>'
	+'<tr><th style="border-bottom:0px;color:gold;">Total Qty</th><td style="border-bottom:0px;color:gold;border-right:0px;">${poTotQty}</td>'
	+'</tr></table>'; 

var tablepringr ='<table cellpadding="3" cellspacing="3" width="100%" height="100%">'
	+'<tr><th></th><th style="width:60px;">${grPrStatus}</th><th style="border-right:0px;"></th></tr>'
	+'<tr><th>ID</th><td>${grPurchReqId}</td><td style="border-right:0px;"></td></tr>'
	+'<tr><th>Total Net Amount </th><td>${grPrNetTot}</td><td style="border-right:0px;"></td></tr>'
	+'<tr><th style="border-bottom:0px;">Total Qty</th><td style="border-bottom:0px;">${grPrTotQty}</td>'
	+'<td style="border-right:0px;border-bottom:0px;"></td></tr></table>'; 

var tableprinpo ='<table cellpadding="3" cellspacing="3" width="100%" height="100%">'
	+'<tr><th></th><th style="width:60px;">${prStatus}</th><th style="border-right:0px;"></th></tr>'
	+'<tr><th>ID</th><td>${prId}</td><td style="border-right:0px;"></td></tr>'
	+'<tr><th>Total Net Amount </th><td>${prNetTot}</td><td style="border-right:0px;"></td></tr>'
	+'<tr><th style="border-bottom:0px;">Total Qty</th><td style="border-bottom:0px;">${prTotQty}</td>'
	+'<td style="border-right:0px;border-bottom:0px;"></td></tr></table>'; 


var tablepoingr ='<table cellpadding="3" cellspacing="3" width="100%" height="100%">'
	+'<tr><th></th><th style="width:60px;">${grPoStatus}</th><th style="border-right:0px;"></th></tr>'
	+'<tr><th>ID</th><td>${grPurchOrdId}</td><td style="border-right:0px;"></td></tr>'
	+'<tr><th>Total Net Amount </th><td>${grOrdnetTot}</td><td style="border-right:0px;"></td></tr>'
	+'<tr><th style="border-bottom:0px;">Total Qty</th><td style="border-bottom:0px;">${grOrdtotQty}</td>'
	+'<td style="border-right:0px;border-bottom:0px;"></td></tr></table>'; 



	var tabledningr ='<table cellpadding="3" cellspacing="3" width="100%" height="100%">'
		+'<tr><th></th><th style="width:60px;">${grDN_Status}</th><th style="border-right:0px;"></th></tr>'
		+'<tr><th>ID</th><td>${grDN_Id}</td><td style="border-right:0px;"></td></tr>'
		+'<tr><th>Total Net Amount </th><td>${grDN_NetTot}</td><td style="border-right:0px;"></td></tr>'
		+'<tr><th style="border-bottom:0px;">Total Qty</th><td style="border-bottom:0px;">${grDN_TotQty}</td>'
		+'<td style="border-right:0px;border-bottom:0px;"></td></tr></table>'; 
</script>




</html>