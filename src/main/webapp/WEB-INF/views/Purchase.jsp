<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Purchasing</title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap4.min.css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<!--      
    <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>
     -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap4.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/Purchasing.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">

    
     <style>

  .circle1 {
  height: 25px;
  width: 25px;
  background-color: white;
  border-radius: 50%;
 padding:1px 12px;
  color: blue;
  margin:9px;
  text-align:center;
  font-size: 14px;
  }
  .circle2 {
  
  height: 25px;
  width: 25px;
  background-color: gold;
  border-radius: 50%;
  padding:1px 12px;
  color: blue;
  text-align:center;
  font-size: 14px;
  }
  
/*     .all { */
/*   height: 25px; */
/*   width: 25px; */
/*   background-color: grey; */
/*   border-radius: 50%; */
/*  padding:1px 12px; */
/*   color: white; */
/*   margin:9px; */
/*   text-align:center; */
/*   font-size: 14px; */
/*   } */
/*   .uncmp { */
  
/*   height: 25px; */
/*   width: 25px; */
/*   background-color: gold; */
/*   border-radius: 50%; */
/*   padding:1px 12px; */
/*   color: white; */
/*   text-align:center; */
/*   font-size: 14px; */
/*   } */
  
</style>
</head>
<body>
<%--     <c:set var = "page" value = "purchasing"/> --%>

<%--  	<%@ include file="header.html" %>  --%>
  
  <c:set var="pg" value="purchasing" scope="session"  />
   <jsp:include page="header.jsp"></jsp:include>
    
  <div class="card-group ">
        <div class="mx-auto" style="margin:15px">
            <div class="card bg-light mb-3 mx-auto" style="max-width: 26rem;">
<!-- 
                <div class="card-header"><i class="fas fa-cash-register"style="color:#DCF8C6"></i> Purchasing</div>
                <div class="card-header"><i class="fas fa-shopping-basket"style="color:#DCF8C6"></i> Purchasing</div>
-->                
                <div class="card-header"><i class="fas fa-tachometer-alt"style="color:#DCF8C6"></i> Planning & Purchasing</div>
                <div class="card-body mycard">
                    <ul id="purchasing" style="text-overflow: ellipsis;white-space: nowrap;">
                        <!-- <li><a href="${pageContext.request.contextPath}/PurchaseReqListView" style="position:relative;left:-30px;"><i class="fas fa-receipt"style="color:#DCF8C6"></i> Purchase Request</a>
                        <span class="circle1">${Prq_Id_Count}</span> <span class="circle2">${Prq_Id_Count_Uncompleted}</span>
                        </li> -->
                        <li><a href="${pageContext.request.contextPath}/PurchaseOrderListView" style="position:relative;left:-30px;"><i class="fas fa-file-invoice-dollar"style="color:#DCF8C6"></i> Purchase Order  </a>
                        <span class="circle1" style="position:relative;left:-30px;" >${PoAll}</span> <span class="circle2" style="position:relative;left:-30px;">${PoUnCmp}</span></li>

                    </ul>
                </div>
            </div>
        </div>
        
      <div class="mx-auto" style="margin:15px">
            <div class="card bg-light mb-3 mx-auto"  style="max-width: 26rem;">
                <div class="card-header "><i class="fas fa-bong"style="color:#DCF8C6"></i> Vendor</div>
<!-- 
                <div class="card-header "><i class="fas fa-house-user"style="color:#DCF8C6"></i> Vender</div>
 -->                
                <div class="card-body mycard ">
                    <ul>
                    	<li><a href="${pageContext.request.contextPath}/SupplierListView" style="position:relative;left:-30px;" ><i class="far fa-copyright" style="color:#DCF8C6"></i> Supplier </a></li>
<!--                    <li><a href="${pageContext.request.contextPath}/SupplierListView" style="position:relative;left:-30px;" ><i class="fas fa-registered "style="color:#DCF8C6"></i> Supplier </a></li>
 -->
                        <li><a href="#" style="position:relative;left:-30px;"><i class="fas fa-object-group"style="color:#DCF8C6"></i> Supplier-Group </a></li>
<!--                         
                        <li><a href="#" style="position:relative;left:-30px;"><i class="fas fa-recycle"style="color:#DCF8C6"></i> Supplier-Group </a></li>
-->
                        

                    </ul>
                </div>
            </div>
        
      </div>
      </div>
    
	<script>
	
	$(document).ready(function() {

		
		var readList = ${readList};
		console.log("read is: "+readList);
        var pReq = '<li><a href="${pageContext.request.contextPath}/PurchaseReqListView" style="position:relative;left:-30px;"><i class="fas fa-receipt"style="color:#DCF8C6"></i> Purchase Request</a><span class="circle1" style="position:relative;left:-30px;" >${PrAll}</span> <span class="circle2" style="position:relative;left:-30px;">${PrUnCmp}</span></li>';
        if (readList == 1){
        		$('#purchasing').prepend(pReq);
        }
     
	});
	
	</script>
	
	
           
 </body>
 </html>