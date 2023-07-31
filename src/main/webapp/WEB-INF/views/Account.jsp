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
    <link href="${pageContext.request.contextPath}/resources/css/Account.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
    <title>Account</title>

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
  
    <c:set var="pg" value="account" scope="session"  />
   <jsp:include page="header.jsp"></jsp:include>
  
    
    <div class="card-group ">
        <div class="mx-auto">
            <div class="card bg-light mb-3 mx-auto" style="max-width: 24rem;">  
                <div class="card-header"><i class="fas fa-exchange-alt"style="color:#DCF8C6"></i> Accounting Masters </div>
                <div class="card-body mycard" >
                    <ul>                        
                        <li class="b1"><a href="${pageContext.request.contextPath}/CompanyListView" style="position:relative;left:-30px;"><i class=" fas fa-city"style="color:#DCF8C6"></i> Company </a>
                        <span class="circle1" >${GrAll}</span> <span class="circle2">${GrUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/WorkOrderListView" style="position:relative;left:-30px;"><i class="fas fa-fighter-jet"style="color:#DCF8C6"></i> Chart of Accounts </a>
                        <span class="circle1" >${WoAll}</span> <span class="circle2">${WoUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Accounts Settings </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/FiscalYearListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Fiscal Year </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Accounts Dimension </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Finance Book </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Accounting Period </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                         <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Payment Term </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                    </ul>
                </div>
            </div>
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Accounts Payable </div>                
                <div class="card-body mycard ">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Purchase Invoice </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Supplier </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Payment Entry </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Accounts Payable</a></li> 
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Accounts Payable Summary </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Purchase Register </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Item-wise Purchase Register </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Purchase Order Analysis </a></li> 
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Received Items To be Billed </a></li>
                                                                       
                    </ul>
                </div>
            </div>
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Multi Currency </div>                
                <div class="card-body mycard ">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Currency </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Currency Exchange   </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Exchange Rate Revaluation </a></li>
                                               
                    </ul>
                </div>
            </div>     
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Subscription Management </div>                
                <div class="card-body mycard" style="height: 275px;">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Subscription Plan </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Subscription </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Subscription Setting  </a></li>
                                              
                    </ul>
                </div>
            </div>    
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Opening and Closing </div>                
                <div class="card-body mycard" style="height: 275px;">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Opening Invoice Creation </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Chart of Accounts importer </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Period Closing Voucher  </a></li>
                                             
                    </ul>
                </div>
            </div>                            
                               
        </div>


        <div class="mx-auto">
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">  
                <div class="card-header"><i class="fas fa-exchange-alt"style="color:#DCF8C6"></i> General Ledger </div>
                <div class="card-body mycard" >
                    <ul>                        
                        <li class="b1"><a href="${pageContext.request.contextPath}/GoodsRcvListView" style="position:relative;left:-30px;"><i class="fas fa-sign-in-alt"style="color:#DCF8C6"></i> Journal Entry </a>
                        <span class="circle1" >${GrAll}</span> <span class="circle2">${GrUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/WorkOrderListView" style="position:relative;left:-30px;"><i class="fas fa-fighter-jet"style="color:#DCF8C6"></i> Journal Entry Template </a>
                        <span class="circle1" >${WoAll}</span> <span class="circle2">${WoUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> General Ledger </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Customer Ledger Summary </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Supplier Ledger Summary </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                    </ul>
                </div>
            </div>
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Reports </div>                
                <div class="card-body mycard ">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Trial Balance For Party </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Payment Period Based On Invoice Dated </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Sales Partners Commission </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Customer Credit Balance </a></li> 
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Sales Payment Summary </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Address And Contacts </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> DATAV Export </a></li>
                                                                      
                    </ul>
                </div>
            </div>
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Settings </div>                
                <div class="card-body mycard ">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Payment Gateway Account  </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Terms and Conditions Template </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Mode of Payment  </a></li>
                                                                   
                    </ul>
                </div>
            </div>     
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Share Management </div>                
                <div class="card-body mycard" style="height: 275px;">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Shareholder  </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Share Transfer </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Share Ledger </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Share Balance </a></li>                       
                    </ul>
                </div>
            </div>    
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Taxes </div>                
                <div class="card-body mycard" style="height: 275px;">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Sales Taxes and Charges Template  </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Purchase Taxes and Charges Template </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Item Tax Template </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Tax Category  </a></li> 
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Tax Rule  </a></li> 
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Tax Withholding Categosry  </a></li>                       
                    </ul>
                </div>
            </div>                            
                               
        </div>


        <div class="mx-auto">
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">  
                <div class="card-header"><i class="fas fa-exchange-alt"style="color:#DCF8C6"></i> Accounts Receivable </div>
                <div class="card-body mycard" >
                    <ul>                        
                        <li class="b1"><a href="${pageContext.request.contextPath}/GoodsRcvListView" style="position:relative;left:-30px;"><i class="fas fa-sign-in-alt"style="color:#DCF8C6"></i> Sales Invoice </a>
                        <span class="circle1" >${GrAll}</span> <span class="circle2">${GrUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/WorkOrderListView" style="position:relative;left:-30px;"><i class="fas fa-fighter-jet"style="color:#DCF8C6"></i> Customer </a>
                        <span class="circle1" >${WoAll}</span> <span class="circle2">${WoUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Payment Entry </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/GoodsRcvListView" style="position:relative;left:-30px;"><i class="fas fa-sign-in-alt"style="color:#DCF8C6"></i> Payment Request </a>
                        <span class="circle1" >${GrAll}</span> <span class="circle2">${GrUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/WorkOrderListView" style="position:relative;left:-30px;"><i class="fas fa-fighter-jet"style="color:#DCF8C6"></i> Accounts Receivable </a>
                        <span class="circle1" >${WoAll}</span> <span class="circle2">${WoUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Accounts Receivable Summary </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Sales Register </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/WorkOrderListView" style="position:relative;left:-30px;"><i class="fas fa-fighter-jet"style="color:#DCF8C6"></i> Item-wise Sales Register </a>
                        <span class="circle1" >${WoAll}</span> <span class="circle2">${WoUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Sales Order Analysis </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/DiscoveryNewListView" style="position:relative;left:-30px;"><i class="fas fa-search-plus"style="color:#DCF8C6"></i> Delivered Items To Be Billed </a>
                        <span class="circle1" >${DniAll}</span> <span class="circle2">${DniUnCmp}</span></li>
                        
                    </ul>
                </div>
            </div>
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Financial Statements </div>                
                <div class="card-body mycard ">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Trial Balance </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Profit and Loss statement </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Balance Sheet </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Cash Flow </a></li> 
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Consolidated Financial statement </a></li>                      
                    </ul>
                </div>
            </div>
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Banking and Payments </div>                
                <div class="card-body mycard ">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Bank  </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Bank Account </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Bank Clearance  </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Bank Reconciliation Tool </a></li>   
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Bank Reconciliation statement </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Payment Entry </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Payment Reconciliation   </a></li>                     
                    </ul>
                </div>
            </div>     
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Cost Center & Budgeting </div>                
                <div class="card-body mycard" style="height: 275px;">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Chart of Cost Centers </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Budget </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Accounting Dimension  </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Budget Variance Report </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Monthly Distribution </a></li>                        
                    </ul>
                </div>
            </div>    
            <div class="card bg-light mb-3 mx-auto " style="max-width: 24rem;">
                <div class="card-header "><i class="fab fa-fort-awesome"style="color:#DCF8C6"></i> Profitability </div>                
                <div class="card-body mycard" style="height: 275px;">
                    <ul>
                        <li class="b1"><a href="${pageContext.request.contextPath}/AssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-registered" style="color:#DCF8C6"></i> Gross Profit </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/AssetLifeCycle" style="position:relative;left:-30px;"><i class="fas fa-recycle" style="color:#DCF8C6"></i> Profitability Analysis </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/FixedAssetRegistryListView"style="position:relative;left:-30px;" ><i class="fas fa-money-check-alt" style="color:#DCF8C6"></i> Sales Invoice Trends </a></li>
                        <li class="b"><a href="${pageContext.request.contextPath}/CapitalInProgressListView"style="position:relative;left:-30px;" ><i class="fas fa-road" style="color:#DCF8C6"></i> Purchase Invoice Trends </a></li>                       
                    </ul>
                </div>
            </div>                            
                               
        </div>



    </div>

</body>
</html>
