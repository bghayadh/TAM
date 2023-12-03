
<style>

 
    li.nav-item a svg, li.dropdown a svg
    {
/*          color:gold !important; */
		  color:#6AA84F !important;
    }
    
 li.nav-item span.active svg {
    color: #FFD966 !important;
}

  .icon-button__badge { 
   padding:1px 12px; 
   background: red;  
   border-radius: 20%;
   position: relative;
   display: inline-block;
   left: -20px; 
   text-align: center;
   margin-top:-20px;
/*   top:-9px; */
   color: #ffffff;    
  }
  
    .icon-button__badge:hover {
        color:#6FA8DC !important;    
    }
  

   .all { 
   height: 25px; 
   width: 25px; 
   background-color: grey; 
   border-radius: 50%; 
   padding:1px 12px; 
   color: white; 
   margin:10px; 
   text-align:center; 
   font-size: 14px; 
   justify-content: center; 
  
   } 
   .uncmp { 
   height: 25px; 
   width: 25px; 
   background-color: red; 
   border-radius: 50%; 
   padding:1px 12px; 
   color: white; 
   text-align:center; 
   font-size: 14px; 
   justify-content: center; 
   } 
  

  
  
   #drop-menu-notification { 
 	left:-30px; 
 	overflow: auto; 
 	height: 280px; 
 	border:none; 
 } 
 
  	.dropdown-item-notification {
  	  text-align: center;
  	  color:blue  !important; 
  	}
  	
   	#drop-menu-notification a:hover {  
   	color:#008080 !important;
   	text-decoration: none;
   	 } 
    
   
   .dropdown-menu-right{margin-right: 0px;
  font-size:100%;
    
   width:200px ;
   
   }
  
   .dropdown-menu-left{
   position: absolute;
    top: 180px;
    margin-top: 0px;
   font-size:90%;
   
   width:200px ;
   margin-left: 359px;
  }  
   
 /* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .input[type=text] {
    width: 100%;
    margin-top: 0;
  }
}



/* Extra small devices (phones, 600px and down) */
@media only screen and (max-width: 600px) {
  .modal-content, .modal-dialog {
  max-height: 100%;
  max-width: 100%;
  
 }
}
/* Small devices (portrait tablets and large phones, 600px and up) */
@media only screen and (max-width: 600px) {
.modal-content, .modal-dialog {
max-height: 100%;
max-width: 100%;

}
}

/* Medium devices (landscape tablets, 768px and up) */
@media only screen and (max-width: 768px) {
 .modal-content, .modal-dialog {
  max-height: 100%;
  max-width: 100%;
  
}
.dropdown-menu-left{
   position: absolute;
    top: 180px;
    margin-top: 0px;
   font-size:90%;
   
   width:200px ;
   margin-left: 200px;
  }  
} 

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (max-width: 992px) {
  .modal-content, .modal-dialog {
  
  max-height: 100%;
        max-width: 100%;
  
 }
 .dropdown-menu-left{
   position: absolute;
    top: 180px;
    margin-top: 0px;
   font-size:90%;
   
   width:200px ;
   margin-left: 200px;
  }  
 
} 

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
 .modal-content, .modal-dialog {
 
 max-height: 100%;
 max-width: 100%;
}


}
   
 .dropdown-submenu {
    position:relative;
}
.dropdown-submenu>.dropdown-menu {
    top:0;
    left:-10rem; /* 10rem is the min-width of dropdown-menu */
    margin-top:-6px;
}

/* rotate caret on hover */
.dropdown-menu > li > a:hover:after {
    text-decoration: underline;
    transform: rotate(-90deg);
} 

.caret {
    margin-left:30px;

}  
.caret2 {
    margin-left:40px;


}    

.caret3{
	margin-left:5px;
}

.m1{
display:block;

}
.m2{
display:none;
}
.m3{
display:none;
}
.m4{
display:none;
}


@media (max-width: 1270px){
.m1{
display:none;
}
.m2{
display:block;
}
.m3{
display:none;
}
.m4{
display:none;
}
}
@media (max-width: 1100px){
.m1{
display:none;
}
.m2{
display:none;
}
.m3{
display:block;
}
.m4{
display:none;
}
}
@media (max-width: 990px){
.m1{
display:none;
}
.m2{
display:none;
}
.m3{
display:none;
}
.m4{
display:block;
}
}
@media (max-width:899px){
.m1{
display:block;
}
.m2{
display:none;
}
.m3{
display:none;
}
.m4{
display:none;
}
}

</style>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:set var = "page" value = "dashboard"/> --%>
<c:set var="page" value="${sessionScope.pg}"/>

<div class="m1">
<nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
	<a class="navbar-brand">ALM</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarmenu">
        <span class="navbar-toggler-icon"></span>
    </button>



              <div class="collapse navbar-collapse" id="navbarmenu">
                  <ul class="navbar-nav">
                  <li class="nav-item"><a href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
                <li class="nav-item dropdown">
        <a class="nav-link" style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'network'}"><span class="border-bottom active"></c:if><i class="fas fa-wifi"></i>  Network <c:if test="${page == 'network'}"></span></c:if>
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-80px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkPhysicalLayer">Physical Layer</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_StNdCell">Elements</a></li>
            <div class="dropdown-divider"></div>
         
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/WarehouseListView">Sites</a></li>
          <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeListView">Nodes</a></li>
          <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/CellListView">Cells</a></li>
         
         
        </ul>
      </li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span class="border-bottom active"></c:if><i class="fas fa-money-check"></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/Inventory" class="nav-link "><c:if test="${page == 'inventory'}"><span class="border-bottom active"></c:if><i class="fas fa-warehouse"></i> Inventory <c:if test="${page == 'inventory'}"></span></c:if></a></li>
                 <li class="nav-item"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span class="border-bottom active"></c:if><i class="fas fa-ticket-alt"></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
            <!--      <li class="nav-item"><a href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li>-->
                       <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'report'}"><span class="border-bottom active"></c:if><i class="fas fa-chart-line"></i> Report <c:if test="${page == 'report'}"></span></c:if>
        </a>
          <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-100px;">
        <!--   <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AssetAccountingVoucher">Asset Accounting Voucher</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ProfitLossAreaReport" >Area Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteProfitLossReport" >Site Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AreaSiteReport" >Area Sites Report</a></li>
          <div class="dropdown-divider"></div> -->
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Revenue Reports
           <span class="caret" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
             <!--  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportCell">Cell Revenue Report</a></li> 
             <div class="dropdown-divider"></div>-->
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteRevenueReport">Site Revenue Report</a></li>
           <!--    <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportArea">Area Revenue Report</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RegionRevenueReport">Region Revenue Report</a></li>
-->
            </ul>
          </li>
          
           <div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">SIM Card Sales
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAgentSalesReport">SIM Agent Sales</a></li>
            <!--  <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimShopSales">SIM Shop Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAreaSales">SIM Area Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimRegionSales">SIM Region Sales</a></li>
            -->
            </ul>
          </li>
     	<div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Network Measurements
			<span class="caret3" ></span></a>
           	<ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SpeedCoverageReport" >Average Speed and Coverage</a></li>
 
            </ul>
          </li>
          
          <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkTransactionsReport">Network Transactions</a>
          </li>
          
       <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Financial Report
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinancialReport">Item Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeAssetReport">Node Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SupplierAssetReport">Supplier Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/VendorAssetReport">Vendor Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteAssetReport">Site Asset Report</a></li>
            </ul>
          </li>
         
        </ul>
      </li>
       
                <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="${pageContext.request.contextPath}/Dashboard"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'dashboard'}"><span class="border-bottom active"></c:if><i class="fas fa-tv"></i>  Dashboard <c:if test="${page == 'dashboard'}"></span></c:if>
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-75px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >General</a></li>
        <!--  <div class="dropdown-divider"></div> -->
       <!--    <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/AssetFinance">Asset Finance</a></li> -->
          <!--  <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Asset Finance</a></li>-->
          
          <!--  <div class="dropdown-divider" ></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Inventory</a></li>-->
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/MobileDashboard" >Mobile Dashboard</a></li>
          
           <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinanceDashboard" >Finance Dashboard</a></li>
          
           <!-- <div class="dropdown-divider" href="#"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Tasks</a></li>
           <div class="dropdown-divider" href="#"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard">Auto Discovery</a></li>-->
        </ul>
      </li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
                  </ul>
        <ul class="navbar-nav ml-auto ">
  
                  <li class=" dropdown nav-item"><a href="#" class="nav-link a1" data-toggle="dropdown" style="text-decoration: none;"> 
                	<c:if test="${page == 'notification'}"><span class="border-bottom active"></c:if><i class="fas fa-bell" style="margin-right:25px; "></i><c:if test="${page == 'notification'}"></span></c:if><span class="icon-button__badge" style="margin-right: -25px; margin-top: -20px;" id ="notnum">${notnum}</span></a>   
				
				<div class="dropdown-menu dropdown-menu-right" id="drop-menu-notification">
						<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseReqListView" class="dropdown-item-notification" >Purchase Request</div><div class="dropdown-item-notification"><span class="all" id="PrAll">${PrAll}</span></a> <a href="#"><span class="uncmp" id="PrUnCmp" onclick="ShowUncompleted(${PrUnCmp},'PurchaseReqListView');">${PrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseOrderListView" class="dropdown-item-notification" >Purchase Order</div><div class="dropdown-item-notification"><span class="all" id="PoAll">${PoAll}</span></a> <a href="#"> <span class="uncmp" id="PoUnCmp" onclick="ShowUncompleted(${PoUnCmp},'PurchaseOrderListView');">${PoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/GoodsRcvListView" class="dropdown-item-notification" >Goods Received</div><div class="dropdown-item-notification"><span class="all" id="GrAll">${GrAll}</span></a> <a href="#"> <span class="uncmp" id="GrUnCmp" onclick="ShowUncompleted(${GrUnCmp},'GoodsRcvListView');">${GrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WorkOrderListView" class="dropdown-item-notification" >Work Order</div><div class="dropdown-item-notification"><span class="all" id=WoAll>${WoAll}</span></a> <a href="#"> <span class="uncmp" id="WoUnCmp" onclick="ShowUncompleted(${WoUnCmp},'WorkOrderListView');">${WoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" class="dropdown-item-notification" >Discovery New</div><div class="dropdown-item-notification"><span class="all" id="DniAll">${DniAll}</span></a> <a href="#"> <span class="uncmp" id="DniUnCmp" onclick="ShowUncompleted(${DniUnCmp},'DiscoveryNewListView');">${DniUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WarehouseListView" class="dropdown-item-notification" >Warehouse</div><div class="dropdown-item-notification"><span class="all" id="WhAll">${WhAll}</span></a> <a href="#"> <span class="uncmp" id="WhUnCmp"  onclick="ShowUncompleted(${WhUnCmp},'WarehouseListView');">${WhUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="dropdown-item-notification" >Trouble Tickets</div><div class="dropdown-item-notification"><span class="all" id="TkAll">${TkAll}</span></a> <a href="#"> <span class="uncmp" id="TkUnCmp" onclick="ShowUncompleted(${TkUnCmp},'TroubleTicketListView');">${TkUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ShopsListView" class="dropdown-item-notification" >Shops</div><div class="dropdown-item-notification"><span class="all" id="ShAll">${ShAll}</span></a> <a href="#"> <span class="uncmp" id="ShUnCmp" onclick="ShowUncompleted(${ShUnCmp},'ShopsListView');">${ShUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AgentListView" class="dropdown-item-notification" >Agents</div><div class="dropdown-item-notification"><span class="all" id="AgnAll">${AgnAll}</span></a><a href="#"><span class="uncmp" id="AgnUnCmp" onclick="ShowUncompleted(${AgnUnCmp},'AgentListView');">${AgnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClientsListView" class="dropdown-item-notification" >Clients</div><div class="dropdown-item-notification"><span class="all" id="ClnAll">${ClnAll}</span></a> <a href="#"> <span class="uncmp" id="ClnUnCmp" onclick="ShowUncompleted(${ClnUnCmp},'ClientsListView');">${ClnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AreaListView" class="dropdown-item-notification" >Area</div><div class="dropdown-item-notification"><span class="all" id="ArAll">${ArAll}</span> </a> <a href="#"><span class="uncmp" id="ArUnCmp" onclick="ShowUncompleted(${ArUnCmp},'AreaListView');">${ArUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClusterListView" class="dropdown-item-notification" >Cluster</div><div class="dropdown-item-notification"><span class="all" id="ClsAll">${ClsAll}</span> </a> <a href="#"><span class="uncmp" id="ClsUnCmp" onclick="ShowUncompleted(${ClsUnCmp},'ClusterListView');">${ClsUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/RegionListView" class="dropdown-item-notification" >Region</div><div class="dropdown-item-notification"><span class="all" id="RgnAll">${RgnAll}</span></a> <a href="#"> <span class="uncmp" id="RgnUnCmp" onclick="ShowUncompleted(${RgnUnCmp},'RegionListView');">${RgnUnCmp}</span></a></div>
					
				</div>
			</li>
  
  
  
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #073763 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="${pageContext.request.contextPath}/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>
    </div>
    <div class = "m2">
    <nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
	<a class="navbar-brand">ALM</a>
    


              <div class="collapse navbar-collapse" id="navbarmenu">
                  <ul class="navbar-nav">
                  <c:choose>
                  <c:when test="${page=='setup' }"> <li class="nav-item"><a href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li></c:when>
                  <c:when test="${page=='purchasing' }"><li class="nav-item"><a href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span class="border-bottom active"></c:if><i class="fas fa-money-check"></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></c:when>
                   <c:when test="${page=='troubleTickets' }"><li class="nav-item"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span class="border-bottom active"></c:if><i class="fas fa-ticket-alt"></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></c:when>
                  <c:otherwise><li class="nav-item"><a href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li></c:otherwise>
                  </c:choose>
                  
                <li class="nav-item dropdown">
        <a class="nav-link" style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'network'}"><span class="border-bottom active"></c:if><i class="fas fa-wifi"></i>  Network <c:if test="${page == 'network'}"></span></c:if>
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-80px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkPhysicalLayer">Physical Layer</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_Index">Elements</a></li>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_StNdCell">Elements (New)</a></li>
           <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeListView">Nodes</a></li>
         <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/CellListView">Cells</a></li>
         
        </ul>
      </li>
               <li class="nav-item"><a href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
          <!--      <li class="nav-item"><a href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li> --> 
                <li class="nav-item"><a href="${pageContext.request.contextPath}/Inventory" class="nav-link "><c:if test="${page == 'inventory'}"><span class="border-bottom active"></c:if><i class="fas fa-warehouse"></i> Inventory <c:if test="${page == 'inventory'}"></span></c:if></a></li>
                

                       <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'report'}"><span class="border-bottom active"></c:if><i class="fas fa-chart-line"></i> Report <c:if test="${page == 'report'}"></span></c:if>
        </a>
          <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-100px;">
        <!--    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AssetAccountingVoucher">Asset Accounting Voucher</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ProfitLossAreaReport" >Area Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteProfitLossReport" >Site Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AreaSiteReport" >Area Sites Report</a></li>
          <div class="dropdown-divider"></div> -->
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Revenue Reports
           <span class="caret" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
          <!--     <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportCell">Cell Revenue Report</a></li>
             <div class="dropdown-divider"></div> -->
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteRevenueReport">Site Revenue Report</a></li>
            <!--  <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportArea">Area Revenue Report</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RegionRevenueReport">Region Revenue Report</a></li>
 -->
            </ul>
          </li>
          
           <div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">SIM Card Sales
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAgentSalesReport">SIM Agent Sales</a></li>
            <!--  <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimShopSales">SIM Shop Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAreaSales">SIM Area Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimRegionSales">SIM Region Sales</a></li>
             -->
            </ul>
          </li>
     	<div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Network Measurements
			<span class="caret3" ></span></a>
           	<ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SpeedCoverageReport" >Average Speed and Coverage</a></li>
 
            </ul>
          </li>
          
          <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkTransactionsReport">Network Transactions</a>
          </li>
          
            <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Financial Report
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinancialReport">Item Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeAssetReport">Node Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SupplierAssetReport">Supplier Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/VendorAssetReport">Vendor Asset Report</a></li>
        	  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteAssetReport">Site Asset Report</a></li>
            </ul>
          </li>
          
        </ul>
      </li>
       
                <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="${pageContext.request.contextPath}/Dashboard"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'dashboard'}"><span class="border-bottom active"></c:if><i class="fas fa-tv"></i>  Dashboard <c:if test="${page == 'dashboard'}"></span></c:if>
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-75px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >General</a></li>
        <!--  <div class="dropdown-divider"></div>
         <!--   <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/AssetFinance">Asset Finance</a></li>-->
          <!-- <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Asset Finance</a></li>
          
          <div class="dropdown-divider" ></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Inventory</a></li>
          <div class="dropdown-divider"></div> -->
          
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Mobile Dashboard</a></li>
          
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinanceDashboard" >Finance Dashboard</a></li>
          
          <!--  <div class="dropdown-divider" href="#"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Tasks</a></li>
           <div class="dropdown-divider" href="#"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Auto Discovery</a></li>-->
        </ul>
      </li>
               
                 
                  
                                   <li class="nav-item dropdown" style="list-style-type:none;">
        <a class="nav-link " style="text-decoration: none;" href="${pageContext.request.contextPath}"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-arrow-down"></i><span >  Others </span>
          </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-100px;">
        <c:choose>
        <c:when test="${page=='setup' }"><li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
        </c:when>
        <c:when test="${page=='troubleTickets' }"> <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
           <div class="dropdown-divider"></div>
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
           <div class="dropdown-divider"></div>
        <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
        </c:when>
        <c:when test="${page=='purchasing' }">
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
          
        
        </c:when>
        
        
        <c:otherwise>
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
        </c:otherwise>
        </c:choose>
          
          
        </ul>
      </li>
               
                  </ul>
        <ul class="navbar-nav ml-auto ">
  
                  <li class=" dropdown nav-item"><a href="#" class="nav-link a1" data-toggle="dropdown" style="text-decoration: none;"> 
                	<c:if test="${page == 'notification'}"><span class="border-bottom active"></c:if><i class="fas fa-bell" style="margin-right:25px; "></i><c:if test="${page == 'notification'}"></span></c:if><span class="icon-button__badge" style="margin-right: -25px; margin-top: -20px;" id ="notnum">${notnum}</span></a>   
				
				<div class="dropdown-menu dropdown-menu-right" id="drop-menu-notification">
						<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseReqListView" class="dropdown-item-notification" >Purchase Request</div><div class="dropdown-item-notification"><span class="all" id="PrAll">${PrAll}</span></a> <a href="#"><span class="uncmp" id="PrUnCmp" onclick="ShowUncompleted(${PrUnCmp},'PurchaseReqListView');">${PrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseOrderListView" class="dropdown-item-notification" >Purchase Order</div><div class="dropdown-item-notification"><span class="all" id="PoAll">${PoAll}</span></a> <a href="#"> <span class="uncmp" id="PoUnCmp" onclick="ShowUncompleted(${PoUnCmp},'PurchaseOrderListView');">${PoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/GoodsRcvListView" class="dropdown-item-notification" >Goods Received</div><div class="dropdown-item-notification"><span class="all" id="GrAll">${GrAll}</span></a> <a href="#"> <span class="uncmp" id="GrUnCmp" onclick="ShowUncompleted(${GrUnCmp},'GoodsRcvListView');">${GrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WorkOrderListView" class="dropdown-item-notification" >Work Order</div><div class="dropdown-item-notification"><span class="all" id=WoAll>${WoAll}</span></a> <a href="#"> <span class="uncmp" id="WoUnCmp" onclick="ShowUncompleted(${WoUnCmp},'WorkOrderListView');">${WoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" class="dropdown-item-notification" >Discovery New</div><div class="dropdown-item-notification"><span class="all" id="DniAll">${DniAll}</span></a> <a href="#"> <span class="uncmp" id="DniUnCmp" onclick="ShowUncompleted(${DniUnCmp},'DiscoveryNewListView');">${DniUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WarehouseListView" class="dropdown-item-notification" >Warehouse</div><div class="dropdown-item-notification"><span class="all" id="WhAll">${WhAll}</span></a> <a href="#"> <span class="uncmp" id="WhUnCmp"  onclick="ShowUncompleted(${WhUnCmp},'WarehouseListView');">${WhUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="dropdown-item-notification" >Trouble Tickets</div><div class="dropdown-item-notification"><span class="all" id="TkAll">${TkAll}</span></a> <a href="#"> <span class="uncmp" id="TkUnCmp" onclick="ShowUncompleted(${TkUnCmp},'TroubleTicketListView');">${TkUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ShopsListView" class="dropdown-item-notification" >Shops</div><div class="dropdown-item-notification"><span class="all" id="ShAll">${ShAll}</span></a> <a href="#"> <span class="uncmp" id="ShUnCmp" onclick="ShowUncompleted(${ShUnCmp},'ShopsListView');">${ShUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AgentListView" class="dropdown-item-notification" >Agents</div><div class="dropdown-item-notification"><span class="all" id="AgnAll">${AgnAll}</span></a><a href="#"><span class="uncmp" id="AgnUnCmp" onclick="ShowUncompleted(${AgnUnCmp},'AgentListView');">${AgnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClientsListView" class="dropdown-item-notification" >Clients</div><div class="dropdown-item-notification"><span class="all" id="ClnAll">${ClnAll}</span></a> <a href="#"> <span class="uncmp" id="ClnUnCmp" onclick="ShowUncompleted(${ClnUnCmp},'ClientsListView');">${ClnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AreaListView" class="dropdown-item-notification" >Area</div><div class="dropdown-item-notification"><span class="all" id="ArAll">${ArAll}</span> </a> <a href="#"><span class="uncmp" id="ArUnCmp" onclick="ShowUncompleted(${ArUnCmp},'AreaListView');">${ArUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClusterListView" class="dropdown-item-notification" >Cluster</div><div class="dropdown-item-notification"><span class="all" id="ClsAll">${ClsAll}</span> </a> <a href="#"><span class="uncmp" id="ClsUnCmp" onclick="ShowUncompleted(${ClsUnCmp},'ClusterListView');">${ClsUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/RegionListView" class="dropdown-item-notification" >Region</div><div class="dropdown-item-notification"><span class="all" id="RgnAll">${RgnAll}</span></a> <a href="#"> <span class="uncmp" id="RgnUnCmp" onclick="ShowUncompleted(${RgnUnCmp},'RegionListView');">${RgnUnCmp}</span></a></div>
					
				</div>
			</li>
  
  
  
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #073763 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="${pageContext.request.contextPath}/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>
    </div>
     <div class="m3">
    <nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
	<a class="navbar-brand">ALM</a>
    


              <div class="collapse navbar-collapse" id="navbarmenu">
                  <ul class="navbar-nav">
                 <c:choose>
                  <c:when test="${page=='setup' }"> <li class="nav-item"><a href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li></c:when>
                  <c:when test="${page=='purchasing' }"><li class="nav-item"><a href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span class="border-bottom active"></c:if><i class="fas fa-money-check"></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></c:when>
                   <c:when test="${page=='troubleTickets' }"><li class="nav-item"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span class="border-bottom active"></c:if><i class="fas fa-ticket-alt"></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></c:when>
                   <c:when test="${page=='Sales' }" ><li class="nav-item"><a href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li></c:when>
                  <c:otherwise><li class="nav-item"><a href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li></c:otherwise>
                  </c:choose>
                <li class="nav-item dropdown">
        <a class="nav-link" style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'network'}"><span class="border-bottom active"></c:if><i class="fas fa-wifi"></i>  Network <c:if test="${page == 'network'}"></span></c:if>
          
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-80px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkPhysicalLayer">Physical Layer</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_Index">Elements</a></li>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_StNdCell">Elements (New)</a></li>
           <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeListView">Nodes</a></li>
          <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/CellListView">Cells</a></li>
         
        </ul>
  <!--     <li class="nav-item"><a href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li> -->  
        
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-80px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkPhysicalLayer">Physical Layer</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_Index">Elements</a></li>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_StNdCell">Elements (New)</a></li>
        </ul>
      </li>
               <li class="nav-item"><a href="${pageContext.request.contextPath}/Inventory" class="nav-link "><c:if test="${page == 'inventory'}"><span class="border-bottom active"></c:if><i class="fas fa-warehouse"></i> Inventory <c:if test="${page == 'inventory'}"></span></c:if></a></li>
               
                

                       <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'report'}"><span class="border-bottom active"></c:if><i class="fas fa-chart-line"></i> Report <c:if test="${page == 'report'}"></span></c:if>
        </a>
          <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-100px;">
      <!--     <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AssetAccountingVoucher">Asset Accounting Voucher</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ProfitLossAreaReport" >Area Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteProfitLossReport" >Site Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AreaSiteReport" >Area Sites Report</a></li>
          <div class="dropdown-divider"></div> -->
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Revenue Reports
           <span class="caret" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
            <!--   <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportCell">Cell Revenue Report</a></li>
             <div class="dropdown-divider"></div> -->
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteRevenueReport">Site Revenue Report</a></li>
        <!--      <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportArea">Area Revenue Report</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RegionRevenueReport">Region Revenue Report</a></li>
 -->
            </ul>
          </li>
          
           <div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">SIM Card Sales
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAgentSalesReport">SIM Agent Sales</a></li>
            <!--  <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimShopSales">SIM Shop Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAreaSales">SIM Area Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimRegionSales">SIM Region Sales</a></li>
             -->
            </ul>
          </li>
     	<div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Network Measurements
			<span class="caret3" ></span></a>
           	<ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SpeedCoverageReport" >Average Speed and Coverage</a></li>
 
            </ul>
          </li>
          
           <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkTransactionsReport">Network Transactions</a>
          </li>
          
        <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Financial Report
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinancialReport">Item Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeAssetReport">Node Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SupplierAssetReport">Supplier Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/VendorAssetReport">Vendor Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteAssetReport">Site Asset Report</a></li>
            </ul>
          </li>
          
        </ul>
      </li>
       
                <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="${pageContext.request.contextPath}/Dashboard"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'dashboard'}"><span class="border-bottom active"></c:if><i class="fas fa-tv"></i>  Dashboard <c:if test="${page == 'dashboard'}"></span></c:if>
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-75px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >General</a></li>
          <div class="dropdown-divider"></div>
        <!--    <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/AssetFinance">Asset Finance</a></li>-->
       <!--   <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Asset Finance</a></li>
          <div class="dropdown-divider" ></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Inventory</a></li>
          <div class="dropdown-divider"></div>-->
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/MobileDashboard" >Mobile Dashboard</a></li>
          
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinanceDashboard" >Finance Dashboard</a></li>
          
            <!--<div class="dropdown-divider" href="#"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Tasks</a></li>
           <div class="dropdown-divider" href="#"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Auto Discovery</a></li>-->
        </ul>
      </li>
               
                  
                 <li class="nav-item dropdown" style="list-style-type:none;">
        <a class="nav-link " style="text-decoration: none;" href="${pageContext.request.contextPath}"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-arrow-down"></i><span   >  Others </span>
          </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-100px;">
        <c:choose>
        <c:when test="${page=='setup' }"><li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
           <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
        </c:when>
        <c:when test="${page=='troubleTickets' }"> <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
           <div class="dropdown-divider"></div>
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
           <div class="dropdown-divider"></div>
        <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
        <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
        </c:when>
        <c:when test="${page=='purchasing' }">
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
          <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
        
        </c:when>
          
        <c:when test="${page=='Sales' }">
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li > <a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
        
        </c:when>
        
        
        <c:otherwise>
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
        </c:otherwise>
        </c:choose>
          
          
        </ul>
      </li>
               
                  </ul>
        <ul class="navbar-nav ml-auto ">
  
                  <li class=" dropdown nav-item"><a href="#" class="nav-link a1" data-toggle="dropdown" style="text-decoration: none;"> 
                	<c:if test="${page == 'notification'}"><span class="border-bottom active"></c:if><i class="fas fa-bell" style="margin-right:25px; "></i><c:if test="${page == 'notification'}"></span></c:if><span class="icon-button__badge" style="margin-right: -25px; margin-top: -20px;" id ="notnum">${notnum}</span></a>   
				
				<div class="dropdown-menu dropdown-menu-right" id="drop-menu-notification">
						<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseReqListView" class="dropdown-item-notification" >Purchase Request</div><div class="dropdown-item-notification"><span class="all" id="PrAll">${PrAll}</span></a> <a href="#"><span class="uncmp" id="PrUnCmp" onclick="ShowUncompleted(${PrUnCmp},'PurchaseReqListView');">${PrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseOrderListView" class="dropdown-item-notification" >Purchase Order</div><div class="dropdown-item-notification"><span class="all" id="PoAll">${PoAll}</span></a> <a href="#"> <span class="uncmp" id="PoUnCmp" onclick="ShowUncompleted(${PoUnCmp},'PurchaseOrderListView');">${PoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/GoodsRcvListView" class="dropdown-item-notification" >Goods Received</div><div class="dropdown-item-notification"><span class="all" id="GrAll">${GrAll}</span></a> <a href="#"> <span class="uncmp" id="GrUnCmp" onclick="ShowUncompleted(${GrUnCmp},'GoodsRcvListView');">${GrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WorkOrderListView" class="dropdown-item-notification" >Work Order</div><div class="dropdown-item-notification"><span class="all" id=WoAll>${WoAll}</span></a> <a href="#"> <span class="uncmp" id="WoUnCmp" onclick="ShowUncompleted(${WoUnCmp},'WorkOrderListView');">${WoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" class="dropdown-item-notification" >Discovery New</div><div class="dropdown-item-notification"><span class="all" id="DniAll">${DniAll}</span></a> <a href="#"> <span class="uncmp" id="DniUnCmp" onclick="ShowUncompleted(${DniUnCmp},'DiscoveryNewListView');">${DniUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WarehouseListView" class="dropdown-item-notification" >Warehouse</div><div class="dropdown-item-notification"><span class="all" id="WhAll">${WhAll}</span></a> <a href="#"> <span class="uncmp" id="WhUnCmp"  onclick="ShowUncompleted(${WhUnCmp},'WarehouseListView');">${WhUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="dropdown-item-notification" >Trouble Tickets</div><div class="dropdown-item-notification"><span class="all" id="TkAll">${TkAll}</span></a> <a href="#"> <span class="uncmp" id="TkUnCmp" onclick="ShowUncompleted(${TkUnCmp},'TroubleTicketListView');">${TkUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ShopsListView" class="dropdown-item-notification" >Shops</div><div class="dropdown-item-notification"><span class="all" id="ShAll">${ShAll}</span></a> <a href="#"> <span class="uncmp" id="ShUnCmp" onclick="ShowUncompleted(${ShUnCmp},'ShopsListView');">${ShUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AgentListView" class="dropdown-item-notification" >Agents</div><div class="dropdown-item-notification"><span class="all" id="AgnAll">${AgnAll}</span></a><a href="#"><span class="uncmp" id="AgnUnCmp" onclick="ShowUncompleted(${AgnUnCmp},'AgentListView');">${AgnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClientsListView" class="dropdown-item-notification" >Clients</div><div class="dropdown-item-notification"><span class="all" id="ClnAll">${ClnAll}</span></a> <a href="#"> <span class="uncmp" id="ClnUnCmp" onclick="ShowUncompleted(${ClnUnCmp},'ClientsListView');">${ClnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AreaListView" class="dropdown-item-notification" >Area</div><div class="dropdown-item-notification"><span class="all" id="ArAll">${ArAll}</span> </a> <a href="#"><span class="uncmp" id="ArUnCmp" onclick="ShowUncompleted(${ArUnCmp},'AreaListView');">${ArUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClusterListView" class="dropdown-item-notification" >Cluster</div><div class="dropdown-item-notification"><span class="all" id="ClsAll">${ClsAll}</span> </a> <a href="#"><span class="uncmp" id="ClsUnCmp" onclick="ShowUncompleted(${ClsUnCmp},'ClusterListView');">${ClsUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/RegionListView" class="dropdown-item-notification" >Region</div><div class="dropdown-item-notification"><span class="all" id="RgnAll">${RgnAll}</span></a> <a href="#"> <span class="uncmp" id="RgnUnCmp" onclick="ShowUncompleted(${RgnUnCmp},'RegionListView');">${RgnUnCmp}</span></a></div>
					
				</div>
			</li>
  
  
  
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #073763 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="${pageContext.request.contextPath}/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>
    </div>
    
    
    
    <div class="m4">
    <nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
	<a class="navbar-brand">ALM</a>
    


              <div class="collapse navbar-collapse" id="navbarmenu">
                  <ul class="navbar-nav">
                 <c:choose>
                  <c:when test="${page=='setup' }"> <li class="nav-item"><a href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li></c:when>
                  <c:when test="${page=='purchasing' }"><li class="nav-item"><a href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span class="border-bottom active"></c:if><i class="fas fa-money-check"></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></c:when>
                   <c:when test="${page=='troubleTickets' }"><li class="nav-item"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span class="border-bottom active"></c:if><i class="fas fa-ticket-alt"></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></c:when>
                   <c:when test="${page=='Sales' }" ><li class="nav-item"><a href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li></c:when>
                   <c:when test="${page=='account' }" ><li class="nav-item"><a href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li></c:when>
                  <c:otherwise><li class="nav-item"><a href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li></c:otherwise>
                  </c:choose>
                <li class="nav-item dropdown">
        <a class="nav-link" style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'network'}"><span class="border-bottom active"></c:if><i class="fas fa-wifi"></i>  Network <c:if test="${page == 'network'}"></span></c:if>
          
        </a>
        
        
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-80px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkPhysicalLayer">Physical Layer</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_Index">Elements</a></li>
          <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Network_StNdCell">Elements (New)</a></li>
           <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeListView">Nodes</a></li>
          <div class="dropdown-divider"></div>
           <li><a class="dropdown-item" href="${pageContext.request.contextPath}/CellListView">Cells</a></li>
         
        </ul>
      </li>
               <li class="nav-item"><a href="${pageContext.request.contextPath}/Inventory" class="nav-link "><c:if test="${page == 'inventory'}"><span class="border-bottom active"></c:if><i class="fas fa-warehouse"></i> Inventory <c:if test="${page == 'inventory'}"></span></c:if></a></li>
               
                

                       <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="#"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'report'}"><span class="border-bottom active"></c:if><i class="fas fa-chart-line"></i> Report <c:if test="${page == 'report'}"></span></c:if>
        </a>
          <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-100px;">
       <!--    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AssetAccountingVoucher">Asset Accounting Voucher</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/ProfitLossAreaReport" >Area Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteProfitLossReport" >Site Profit & Loss</a></li>
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/AreaSiteReport" >Area Sites Report</a></li>
          <div class="dropdown-divider"></div>--> 
          
           
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Revenue Reports
           <span class="caret" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
            <!--   <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportCell">Cell Revenue Report</a></li>
             <div class="dropdown-divider"></div> -->
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteRevenueReport">Site Revenue Report</a></li>
        <!--      <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RevenueReportArea">Area Revenue Report</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/RegionRevenueReport">Region Revenue Report</a></li>
 -->
            </ul>
          </li>
          
           <div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">SIM Card Sales
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAgentSalesReport">SIM Agent Sales</a></li>
            <!--  <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimShopSales">SIM Shop Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimAreaSales">SIM Area Sales</a></li>
              <div class="dropdown-divider"></div>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SimRegionSales">SIM Region Sales</a></li>
            --> 
            </ul>
          </li>
     	 <div class="dropdown-divider"></div>
          
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Network Measurements
			<span class="caret3" ></span></a>
           	<ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SpeedCoverageReport" >Average Speed and Coverage</a></li>
 
            </ul>
          </li>
          
           <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item" href="${pageContext.request.contextPath}/NetworkTransactionsReport">Network Transactions</a>
          </li>
          
        <div class="dropdown-divider"></div>
          <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" href="#">Financial Report
           <span class="caret2" ></span></a>
            <ul class="dropdown-menu dropdown-menu-left">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinancialReport">Item Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/NodeAssetReport">Node Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SupplierAssetReport">Supplier Asset Report</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/VendorAssetReport">Vendor Asset Report</a></li>
           	  <li><a class="dropdown-item" href="${pageContext.request.contextPath}/SiteAssetReport">Site Asset Report</a></li>
            </ul>
          </li>
          
        </ul>
      </li>
       
                <li class="nav-item dropdown">
        <a class="nav-link "  style="text-decoration: none;" href="${pageContext.request.contextPath}/Dashboard"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <c:if test="${page == 'dashboard'}"><span class="border-bottom active"></c:if><i class="fas fa-tv"></i>  Dashboard <c:if test="${page == 'dashboard'}"></span></c:if>
        </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-75px;">
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >General</a></li>
          <div class="dropdown-divider"></div>
         <!-- <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/AssetFinance">Asset Finance</a></li>  -->
       <!--    <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Asset Finance</a></li>
          <div class="dropdown-divider" ></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Inventory</a></li>
          <div class="dropdown-divider"></div>-->
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/MobileDashboard" >Mobile Dashboard</a></li>
          
          <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FinanceDashboard" >Finance Dashboard</a></li>
          
            <div class="dropdown-divider" href="#"></div>
         <!-- <li><a class="dropdown-item"  href="${pageContext.request.contextPath}/Dashboard">Tasks</a></li>
           <div class="dropdown-divider" href="#"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/Dashboard" >Auto Discovery</a></li>-->
        </ul>
      </li>
          
          
                  <li class="nav-item dropdown" style="list-style-type:none;">
        <a class="nav-link " style="text-decoration: none;" href="${pageContext.request.contextPath}"  id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fas fa-arrow-down"></i><span  >  Others </span>
          </a>
        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink" style="margin-right:-100px;">
        <c:choose>
        <c:when test="${page=='setup' }"><li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
           <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
           <!--  <div class="dropdown-divider" ></div>
         <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li> --> 
        </c:when>
        <c:when test="${page=='troubleTickets' }"> <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
           <div class="dropdown-divider"></div>
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
           <div class="dropdown-divider"></div>
        <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
        <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
           <!-- <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li>  -->
        </c:when>
        <c:when test="${page=='purchasing' }">
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
          <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
          <!--  <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li> --> 
        
        </c:when>
          
        <c:when test="${page=='Sales' }">
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li > <a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
          <!--  <div class="dropdown-divider" ></div>
         <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li>  --> 
        
        </c:when>
         <c:when test="${page=='account' }">
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
           <div class="dropdown-divider" ></div>
         <li > <a class="dropdown-item" href="${pageContext.request.contextPath}/home" class="nav-link"><c:if test="${page == 'home'}"><span class="border-bottom active"></c:if><i class="fas fa-home"></i> Home <c:if test="${page == 'purchasing'}"></span></c:if></a></li>
        
        </c:when>
        
        <c:otherwise>
        <li class="nav-item"><a class="dropdown-item" href="${pageContext.request.contextPath}/Setup" class="nav-link "><c:if test="${page == 'setup'}"><span class="border-bottom active"></c:if><i class="fas fa-cog"></i> Setup <c:if test="${page == 'setup'}"></span></c:if></a></li>
        <div class="dropdown-divider"></div>
          <li><a class="dropdown-item" href="${pageContext.request.contextPath}/TroubleTicketListView" class="nav-link "><c:if test="${page == 'troubleTickets'}"><span id="back"></c:if><i class="fas fa-ticket-alt" ></i> TroubleTickets <c:if test="${page == 'troubleTickets'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li> <a class="dropdown-item" href="${pageContext.request.contextPath}/Purchase" class="nav-link "><c:if test="${page == 'purchasing'}"><span id="back"></c:if><i class="fas fa-money-check" ></i> Purchasing <c:if test="${page == 'purchasing'}"></span></c:if></a></li></li>
          <div class="dropdown-divider" ></div>
          <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Sales" class="nav-link "><c:if test="${page == 'Sales'}"><span class="border-bottom active"></c:if><i class="fas fa-shopping-basket"></i> Sales <c:if test="${page == 'sales'}"></span></c:if></a></li>
           <!--   <div class="dropdown-divider" ></div>
        <li ><a class="dropdown-item" href="${pageContext.request.contextPath}/Account" class="nav-link "><c:if test="${page == 'account'}"><span class="border-bottom active"></c:if><i class="fas fa-book"></i> Account <c:if test="${page == 'account'}"></span></c:if></a></li> --> 
        </c:otherwise>
        </c:choose>
          
          
        </ul>
      </li>
               
                  </ul>
        <ul class="navbar-nav ml-auto ">
  
                  <li class=" dropdown nav-item"><a href="#" class="nav-link a1" data-toggle="dropdown" style="text-decoration: none;"> 
                	<c:if test="${page == 'notification'}"><span class="border-bottom active"></c:if><i class="fas fa-bell" style="margin-right:25px; "></i><c:if test="${page == 'notification'}"></span></c:if><span class="icon-button__badge" style="margin-right: -25px; margin-top: -20px;" id ="notnum">${notnum}</span></a>   
				
				<div class="dropdown-menu dropdown-menu-right" id="drop-menu-notification">
						<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseReqListView" class="dropdown-item-notification" >Purchase Request</div><div class="dropdown-item-notification"><span class="all" id="PrAll">${PrAll}</span></a> <a href="#"><span class="uncmp" id="PrUnCmp" onclick="ShowUncompleted(${PrUnCmp},'PurchaseReqListView');">${PrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/PurchaseOrderListView" class="dropdown-item-notification" >Purchase Order</div><div class="dropdown-item-notification"><span class="all" id="PoAll">${PoAll}</span></a> <a href="#"> <span class="uncmp" id="PoUnCmp" onclick="ShowUncompleted(${PoUnCmp},'PurchaseOrderListView');">${PoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/GoodsRcvListView" class="dropdown-item-notification" >Goods Received</div><div class="dropdown-item-notification"><span class="all" id="GrAll">${GrAll}</span></a> <a href="#"> <span class="uncmp" id="GrUnCmp" onclick="ShowUncompleted(${GrUnCmp},'GoodsRcvListView');">${GrUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WorkOrderListView" class="dropdown-item-notification" >Work Order</div><div class="dropdown-item-notification"><span class="all" id=WoAll>${WoAll}</span></a> <a href="#"> <span class="uncmp" id="WoUnCmp" onclick="ShowUncompleted(${WoUnCmp},'WorkOrderListView');">${WoUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" class="dropdown-item-notification" >Discovery New</div><div class="dropdown-item-notification"><span class="all" id="DniAll">${DniAll}</span></a> <a href="#"> <span class="uncmp" id="DniUnCmp" onclick="ShowUncompleted(${DniUnCmp},'DiscoveryNewListView');">${DniUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/WarehouseListView" class="dropdown-item-notification" >Warehouse</div><div class="dropdown-item-notification"><span class="all" id="WhAll">${WhAll}</span></a> <a href="#"> <span class="uncmp" id="WhUnCmp"  onclick="ShowUncompleted(${WhUnCmp},'WarehouseListView');">${WhUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/TroubleTicketListView" class="dropdown-item-notification" >Trouble Tickets</div><div class="dropdown-item-notification"><span class="all" id="TkAll">${TkAll}</span></a> <a href="#"> <span class="uncmp" id="TkUnCmp" onclick="ShowUncompleted(${TkUnCmp},'TroubleTicketListView');">${TkUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ShopsListView" class="dropdown-item-notification" >Shops</div><div class="dropdown-item-notification"><span class="all" id="ShAll">${ShAll}</span></a> <a href="#"> <span class="uncmp" id="ShUnCmp" onclick="ShowUncompleted(${ShUnCmp},'ShopsListView');">${ShUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AgentListView" class="dropdown-item-notification" >Agents</div><div class="dropdown-item-notification"><span class="all" id="AgnAll">${AgnAll}</span></a><a href="#"><span class="uncmp" id="AgnUnCmp" onclick="ShowUncompleted(${AgnUnCmp},'AgentListView');">${AgnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClientsListView" class="dropdown-item-notification" >Clients</div><div class="dropdown-item-notification"><span class="all" id="ClnAll">${ClnAll}</span></a> <a href="#"> <span class="uncmp" id="ClnUnCmp" onclick="ShowUncompleted(${ClnUnCmp},'ClientsListView');">${ClnUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/AreaListView" class="dropdown-item-notification" >Area</div><div class="dropdown-item-notification"><span class="all" id="ArAll">${ArAll}</span> </a> <a href="#"><span class="uncmp" id="ArUnCmp" onclick="ShowUncompleted(${ArUnCmp},'AreaListView');">${ArUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/ClusterListView" class="dropdown-item-notification" >Cluster</div><div class="dropdown-item-notification"><span class="all" id="ClsAll">${ClsAll}</span> </a> <a href="#"><span class="uncmp" id="ClsUnCmp" onclick="ShowUncompleted(${ClsUnCmp},'ClusterListView');">${ClsUnCmp}</span></a></div>
	        					<div class="dropdown-divider"></div>
	        			<div class="dropdown-item-notification"><a href="${pageContext.request.contextPath}/RegionListView" class="dropdown-item-notification" >Region</div><div class="dropdown-item-notification"><span class="all" id="RgnAll">${RgnAll}</span></a> <a href="#"> <span class="uncmp" id="RgnUnCmp" onclick="ShowUncompleted(${RgnUnCmp},'RegionListView');">${RgnUnCmp}</span></a></div>
					
				</div>
			</li>
  
  
  
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #073763 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="${pageContext.request.contextPath}/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>
    </div>
    <script>

function ShowUncompleted(int,String) {

	var param = "${pageContext.request.contextPath}/"+String;
	if (int != 0){
	 param = "${pageContext.request.contextPath}/Pending"+String;	 	
		}
	window.location.href = param;

}



$('.dropdown-menu a.dropdown-toggle').on('click', function(e) {
    if (!$(this).next().hasClass('show')) {
        $(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
    }
    var $subMenu = $(this).next(".dropdown-menu");
    $subMenu.toggleClass('show');
    $(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
        $('.dropdown-submenu .show').removeClass("show");
    });
    return false;
});




    </script>