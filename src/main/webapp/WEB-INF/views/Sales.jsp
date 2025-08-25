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
    <link href="${pageContext.request.contextPath}/resources/css/Sales.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
    <title>Sales</title>
    
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
      
<%--      <c:set var = "page" value = "Sales"/> --%>
     

<%-- 	<%@ include file="header.html" %> --%>

  
   <c:set var="pg" value="Sales" scope="session"  />
 
   <jsp:include page="header.jsp"></jsp:include>

  
        
        
         <div class="card-group ">
        <div class="mx-auto">
            <div class="card bg-light mb-3 mx-auto  " style="max-width: 23rem;">
            
            <div class="card-header"><i class="fas fa-exchange-alt"style="color:#DCF8C6"></i> Reseller</div>
                <div class="card-body mycard">
                    <ul style="text-overflow: ellipsis;white-space: nowrap;" id = "reseller">
                    
                        <li class="b1"><a href="${pageContext.request.contextPath}/ShopsListView" style="position:relative;left:-30px;"><i class="fas fa-sign-in-alt"style="color:#DCF8C6"></i> Shops</a>
                          <span class="circle1" style="position:relative;left:-30px;" >${ShAll}</span> <span class="circle2" style="position:relative;left:-30px;">${ShUnCmp}</span></li> 
                        
                        <li class="b"><a href="${pageContext.request.contextPath}/AgentListView" style="position:relative;left:-30px;"><i class="fas fa-fighter-jet"style="color:#DCF8C6"></i> Agents </a>
                        <span class="circle1" style="position:relative;left:-30px;">${AgnAll}</span> <span class="circle2" style="position:relative;left:-30px;">${AgnUnCmp}</span></li> 
                       
                       
                    </ul>
                </div>
            </div>
          </div> 
            
            <div class="mx-auto">
            
            <div class="card bg-light mb-3  mx-auto " style="max-width: 23rem;">
                <div class="card-header"><i class="fas fa-cubes"style="color:#DCF8C6"></i> Location</div>
                <div class="card-body mycard  ">
                    <ul style="text-overflow: ellipsis;white-space: nowrap;">
                       <li class="b1"><a href="${pageContext.request.contextPath}/AreaListView" style="position:relative;left:-30px;"><i class="fas fa-map" style="color:#DCF8C6"></i> Area</a><span class="circle1" style="position:relative;left:-30px;">${ArAll}</span> <span class="circle2" style="position:relative;left:-30px;">${ArUnCmp}</span>
                       <li class="b"><a href="${pageContext.request.contextPath}/ClusterListView" style="position:relative;left:-30px;"><i class="fas fa-location-arrow" style="color:#DCF8C6"></i> Cluster</a><span class="circle1" style="position:relative;left:-30px;" >${ClsAll}</span> <span class="circle2" style="position:relative;left:-30px;">${ClsUnCmp}</span> 
                       <li class="b"><a href="${pageContext.request.contextPath}/RegionListView" style="position:relative;left:-30px;"><i class="fas fa-compass" style="color:#DCF8C6"></i> Region</a><span class="circle1" style="position:relative;left:-30px;" >${RgnAll}</span> <span class="circle2" style="position:relative;left:-30px;">${RgnUnCmp}</span>
                     
                    </ul>
                </div>
            </div>
            
    </div>
        
            
    </div>

</body>

	<script>
	
	$(document).ready(function() {

		
		var readList = ${readList};
		console.log("read is: "+readList);
		var client = '<li class="b"><a href="${pageContext.request.contextPath}/ClientsListView" style="position:relative;left:-30px;"><i class="fas fa-user" style="color:#DCF8C6"></i> Clients </a><span class="circle1" style="position:relative;left:-30px;">${ClnAll}</span> <span class="circle2" style="position:relative;left:-30px;">${ClnUnCmp}</span></li>';
        var imgApproval = '<li class="b"><a href="${pageContext.request.contextPath}/ClientsImageListView" style="position:relative;left:-30px;"><i class="fas fa-user" style="color:#DCF8C6"></i> Image Approval</a><span class="circle1" style="position:relative;left:-30px;">${ClnimgAll}</span> <span class="circle2" style="position:relative;left:-30px;">${ClnimgUnCmp}</span></li>';

        if(${firstlvlList} == 1 && readList == 1 && ${secondlvlList} == 1){
        	$('#reseller').append(client);
        	$('#reseller').append(imgApproval);
         }else if(readList == 1 && ${firstlvlList} == 1){
     		$('#reseller').append(imgApproval);
         }else{
        	 $('#reseller').append(client);
         }
        
        var customer='<li class="b"><a href="${pageContext.request.contextPath}/CustomerListView" style="position:relative;left:-30px;"><i class="fas fa-user"style="color:#DCF8C6"></i> Customers </a><span class="circle1" style="position:relative;left:-30px;">${CustAll}</span> <span class="circle2" style="position:relative;left:-30px;">${CustUnCmp}</span></li>'
            $('#reseller').append(customer);
     
	});
	
	</script>
</html>        