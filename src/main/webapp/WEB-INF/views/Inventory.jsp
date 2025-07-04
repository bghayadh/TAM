<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/Inventory.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
    <title>Inventory</title>

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
  font-size: 14px;
  text-align:center;
  }
  
</style>

</head>


<body>
<%--       <%@ include file="Header.jsp" %> --%>
<%--      <c:set var = "page" value = "inventory"/> --%>

<%--  	<%@ include file="header.html" %>  --%>

 
    <c:set var="pg" value="inventory" scope="session"  />
   <jsp:include page="header.jsp"></jsp:include>
  
    
    <div class="card-group ">
        <div class="mx-auto">
            <div class="card bg-light mb-3 mx-auto  " style="max-width: 23rem;">
            
            <!-- 
            
                <div class="card-header"><i class="fas fa-cash-register"style="color:#DCF8C6"></i> Transactions</div>
 			-->
                <div class="card-header"><i class="fas fa-exchange-alt"style="color:#DCF8C6"></i> Transactions</div>
                <div class="card-body mycard">
                    <ul style="text-overflow: ellipsis;white-space: nowrap;">
<!-- 
                        <li class="b1"><a href="${pageContext.request.contextPath}/GoodsRcvListView" style="position:relative;left:-30px;"><i class="fas fa-receipt"style="color:#DCF8C6"></i> Goods Received</a></li>
 -->                        
                        <li class="b1"><a href="${pageContext.request.contextPath}/GoodsRcvListView" style="position:relative;left:-30px;"><i class="fas fa-sign-in-alt"style="color:#DCF8C6"></i> Goods Received</a>
                        <span class="circle1" style="position:relative;left:-30px;">${GrAll}</span> <span class="circle2" style="position:relative;left:-30px;">${GrUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/WorkOrderListView" style="position:relative;left:-30px;"><i class="fas fa-fighter-jet"style="color:#DCF8C6"></i> Work Order </a>
                        <span class="circle1" style="position:relative;left:-30px;">${WoAll}</span> <span class="circle2" style="position:relative;left:-30px;">${WoUnCmp}</span></li>
                        <!-- 
                        <li class="b"><a href="${pageContext.request.contextPath}/WorkOrderListView" style="position:relative;left:-30px;"><i class="fas fa-file-invoice-dollar"style="color:#DCF8C6"></i> Work Order </a></li>
                         -->
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Discovery New Item </a>
                        <span class="circle1" style="position:relative;left:-30px;">${DniAll}</span> <span class="circle2" style="position:relative;left:-30px;">${DniUnCmp}</span></li>
                        <!-- 
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-file-invoice-dollar"style="color:#DCF8C6"></i> Discovery New Item </a></li>
                       -->
                                  
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewApprovalListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Discovery New </a>
                        <span class="circle1" style="position:relative;left:-30px;">${DniAll}</span> <span class="circle2" style="position:relative;left:-30px;">${DniUnCmp}</span></li>
                       <li class="b"><a href="${pageContext.request.contextPath}/SimTransactionListView" style="position:relative;left:-30px;"><i class="fas fa-arrows-alt-h"style="color:#DCF8C6"></i> Sim Card Transaction </a></li>
                    </ul>
                </div>
            </div>
            <div class="card bg-light mb-3 mx-auto " style="max-width: 23rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Asset Life Cycle</div>                
                <div class="card-body mycard ">
                    <ul style="text-overflow: ellipsis;white-space: nowrap;">
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetBalance" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Asset Balance </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLedger" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Asset Ledger </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Asset Network Transaction </a></li>
					    
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="mx-auto">
            
            <div class="card bg-light mb-3  mx-auto " style="max-width: 23rem;">
                <div class="card-header"><i class="fas fa-cubes"style="color:#DCF8C6"></i> Items and Location</div>
                <div class="card-body mycard  ">
                    <ul style="text-overflow: ellipsis;white-space: nowrap;">
                        <li class="b1"><a href="${pageContext.request.contextPath}/ItemListView" style="position:relative;left:-30px;"><i class="fas fa-bahai"style="color:#DCF8C6"></i> Item </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/ItemCategoryTreeView" style="position:relative;left:-30px;"><i class="far fa-object-group"style="color:#DCF8C6"></i> Item Category</a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/SerialNumberListView" style="position:relative;left:-30px;"><i class="fas fa-list-ol"style="color:#DCF8C6"></i> Serial No</a></li>
						<li class="b"><a href="${pageContext.request.contextPath}/SimCardListView" style="position:relative;left:-30px;"><i class="fas fa-sim-card"style="color:#DCF8C6"></i> Sim Card </a></li>
						<li class="b"><a href="${pageContext.request.contextPath}/WarehouseListView" style="position:relative;left:-30px;"><i class="fas fa-boxes"style="color:#DCF8C6"></i> Warehouse</a>
                        <span class="circle1" style="position:relative;left:-30px;">${WhAll}</span> <span class="circle2" style="position:relative;left:-30px;">${WhUnCmp}</span></li>
                        
                    </ul>
                </div>
            </div>

            <div class="card bg-light mb-3 mx-auto " style="max-width: 23rem;">
                <div class="card-header"><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Asset</div>
                <div class="card-body mycard">
                    <ul style="text-overflow: ellipsis;white-space: nowrap;">
                    	<li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Asset Register </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Fixed Asset Register</a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Capital In Progress</a></li>
                    </ul>
                 </div> 
             </div> 

</div>

            
    </div>

</body>
</html>
