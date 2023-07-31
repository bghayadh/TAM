<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/JsBarCode.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->

	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<!-- 
	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>	
	 -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
<!-- 	 	 
	<script src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>
-->
	
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
         
    
    <style>
				
				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				  margin-right: 10px;
				  margin-left: 10px;  
				}
				
				svg
				{
					width: 200px;
					height: 70px;
				}
	        		 
 	</style>
            
</head>
<body>

  <nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
        <a href="#" class="navbar-brand">ALM</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarmenu">
            <span class="navbar-toggler-icon"></span>
        </button>


        <div class="collapse navbar-collapse" id="navbarmenu">
            <ul class="navbar-nav">
                <li class="nav-item"><a href="/Network" class="nav-link  " style="color: #fff"><i class="fas fa-wifi" style="color: gold"></i> Network</a></li>
                <li class="nav-item"><a href="/Purchase" class="nav-link " style="color: #fff"><i class="fas fa-money-check" style="color: gold"></i> Purchasing </a></li>
                 
                <li class="nav-item"><a href="/Inventory" class="nav-link " style="color: #fff"><span class="border-bottom active"><i class="fas fa-warehouse" style="color: #20B2AA"></i> Inventory</span> </a></li>
                <li class="nav-item"><a href="/Contract" class="nav-link " style="color: #fff"><i class="fas fa-book" style="color: gold"></i> Contracts</a></li>
                <li class="nav-item"><a href="/Report" class="nav-link  " style="color: #fff"><i class="fas fa-chart-line" style="color: gold"></i> Report </a></li>
                <li class="nav-item"><a href="/Dashboard" class="nav-link " style="color: #fff"><i class="fas fa-tv" style="color: gold"></i> Dashboard </a></li>
                <li class="nav-item"><a href="/Setup" class="nav-link " style="color: #fff"><i class="fas fa-cog" style="color: gold"></i> Setup </a></li>
            </ul>
            
            
            
          <ul class="navbar-nav ml-auto ">
                        
                <!--  li class="nav-item"><a href="#" class="nav-link a1" style="margin-right: 30px;"><i class="fas fa-bell" style="color: gold"></i></a></li-->
                <li class="dropdown" id="notifactionDropdown"
				style="margin-right: 60px;"><a href="#" class="nav-link a1" data-toggle="dropdown">
					<span class="p1" id="bellCounter" data-count=""> 
					<i class="fa fa-bell" data-count="4b" style="font-size: 20px; color: gold;"></i>
					</span></a>
 	
						<ul class="dropdown-menu dropdown-menu-right customListPadding">
							<li><p>
								<a href="#">Site <span class="badge badge-danger float-right"id="siteTask"></span></a></p></li>
							<li><p>
								<a href="#">Node <span class="badge badge-danger float-right"id="nodeTask"></span></a></p></li>
							<li><p>
								<a href="#">Cell <span class="badge badge-danger float-right"id="cellTask"></span></a></p></li>
							<li><p>
								<a href="#">Task<span class="badge badge-danger float-right"id="taskCount"></span></a></p></li>
						</ul>
				</li>
               
               
               <!--  li class="nav-item"><a href="#" class="nav-link a1" style="color: white;"><i class="fas fa-user" style="color: gold   "></i> aya shi </a></li -->
 
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #ffbb33 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>
     <!--  end of general head page -->
<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
			<p></p>
			</div>
		
		</div>
		
			
		<div class="row second">
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Item Form</span>
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus">Saved</label>							
						</div>

					</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Item Code</span>
							<input type="text" id="itmcode"  value="${itemCode}" class="form-control text-input"   />
					</div>
				</div>
			</div>
			
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Status</span>
							<input type="text" id="itmStatus"  value="${itemStatus}" class="form-control text-input"   />
					</div>
				</div>
			</div>
			
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/ItemListView"'
			        			data-placement="top" title="List View"> 
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
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">DESCRIPTION</a></li>
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-profile-tab" data-toggle="tab"
            href="#custom-tabs-one-profile" role="tab"
            aria-controls="custom-tabs-one-profile" aria-selected="false" style="color: gold;">MEASURMENT</a></li>
             
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-messages" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">TYPE</a></li>
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-finance-tab" data-toggle="tab"
            href="#custom-tabs-finance" role="tab"
            aria-controls="#custom-tabs-finance" aria-selected="false" style="color: gold;">FINANCE</a></li> 
       
			<li class="nav-item"><a class="nav-link"
            id="custom-tabs-barcode-tab" data-toggle="tab"
            href="#custom-tabs-barcode" role="tab"
            aria-controls="#custom-tabs-barcode" aria-selected="false" style="color: gold;">BARCODE</a></li> 
            
            
            
            <li class="nav-item ml-auto">
				<button type="button" id="deleteButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-trash"></i> Delete
				</button>  
                        
				<button type="button" id="saveButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-save"></i> Save
				</button>  </li>
							
     </ul>
     
</div>
</div>

</div>




<div class="container-fluid">

<div class="tab-content" id="custom-tabs-one-tabContent">

<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

<p></p>
    
<div class="row">

	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Name</span>
				<input type="text" id="itmname" value="${itemName}"  class="form-control text-input" />
			</div>
		</div>	
	</div>

	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Model</span>
				<input type="text" id="itmModel" value="${itemModel}" class="form-control text-input"/>
			</div>
		</div>	
	</div>
	
	
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
					<input type="text" id="createdate" readonly value="${createdDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
			</div>
		</div>
	
	</div>
 </div>
	<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Category Code</span>
					<input type="text" id="itmCatCode" value="${itmCatCode}" style="width:700px;" class="ui-widget ui-widget-content ui-corner-all"/>
				</div>
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Part No.</span>
					<input type="text" id="itmPartNo" value="${itemPartNo}" class="form-control text-input"/>
				</div>
			</div>	
		</div>
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${lastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
			</div>
		</div>
 	</div>
	<div class="row">
		<div class="col-md-6">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Category</span>
					<input type="text" id="itmcat" value="${itemCategory}" class="form-control text-input" />
			</div>
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">SKU No.</span>
					<input type="text" id="itmSkuNo" value="${itemSkuNo}" class="form-control text-input"/>
				</div>
			</div>			
		</div>
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">UUID</span>
					<input type="text" id="itmUUID" value="${itemUUID}" class="form-control text-input"/>
				</div>
			</div>
		</div>
	</div>
	
<div class="row">	
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Manufacturer</span>
				<input type="text" id="itemManufact" value="${itemManufact}" class="form-control text-input"  />
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">OS</span>
				<input type="text" id="itmOS" value="${itemOS}" class="form-control text-input"/>
			</div>
		</div>
	</div>
		
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Physical RAM</span>
				<input type="text" id="itmPhysRAM" value="${itemPhysRAM}" class="form-control text-input"/>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Processor Type</span>
				<input type="text" id="itemProcType" value="${itemProcType}" class="form-control text-input"  />
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Processor Vendor</span>
				<input type="text" id="itmProcVendor" value="${itemProcVendor}" class="form-control text-input"/>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-sm-6">
		<div class="input-group-prepend">
			<span class="input-group-text">Item Description</span>
			<!-- 
				<textarea name="itmdescrip" cols="120" rows="7" id="itmdescrip"> ${itemDescription} </textarea>
			-->
		</div>
	</div>
	<div class="col-md-6">
		<div class="input-group-prepend">
			<span class="input-group-text">Item Image</span>
				<input type="text" id="itmimage" value="${itemImage}" class="form-control text-input" />
		</div>
	</div>		
</div>
<div class="row">
	<div class="col-sm-6">
			<div class="input-group-prepend">
				<textarea name="suppdescrip" cols="220" rows="8" id="itmdescrip"> ${itemDescription} </textarea>
			</div>	
	</div>
</div>
<p></p>

<div class="row">
	<div class="col-md-12">
		<div class="form-group">
			<div class="checkboxes">
				<span><input type="checkbox" id="disabled" ${disabled}/> Disabled
				</span>
			</div>
		</div>
	</div>
</div>
</div>

<div class="tab-pane fade" id="custom-tabs-one-profile" role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab">
	
<p></p>


<div class="row">

	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Unit</span>
						<input type="text" id="unt" value="${unit}"  class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight</span>
						<input type="text" id="weit" value="${weight}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight UOM</span>
						<input type="text" id="weituom" value="${weightUOM}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Length</span>
						<input type="text" id="len" value="${length}" class="form-control text-input"  />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Width</span>
					<input type="text" id="widths" value="${width}" class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="input-group-prepend">
					<span class="input-group-text">Height</span>
				<input type="text" id="heit" value="${height}" class="form-control text-input" />
				</div>							
	</div>
	
	<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Size UOM</span>
					<input type="text" id="siseuom" value="${sizeUOM}" class="form-control text-input"  />
			</div>

				
		</div>
	
</div>
	</div>

<div class="tab-pane fade" id="custom-tabs-one-messages" role="tabpanel" aria-labelledby="custom-tabs-one-messages-tab">

<p></p>

<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Kind</span>
					<input type="text" id="itmKind" value="${itemKind}" class="form-control text-input"/>
				</div>
			</div>	
		</div>

	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Cable Type</span>
						<input type="text" id="cbltype" value="${cableType}" class="form-control text-input"/>
						</div>
					</div>
	</div>


	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Type</span>
					<input type="text" id="itmtyp" value="${itemType}" class="form-control text-input"/>
				</div>
			</div>
	</div>
</div>
	
<div class="row">
	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Mode</span>
					<input type="text" id="itmMode" value="${itemMode}" class="form-control text-input"/>
				</div>
			</div>
	</div>

	<div class="col-md-4">
		<div class="input-group-prepend">
			<span class="input-group-text">Domain</span>
			<!--  <input type="text" id="domaine" value="${domain}" class="form-control text-input" /> -->
			<select name="cars" id="domaine">
    			<option value="mad" <c:if test = "${domain =='mad'}"> selected </c:if>>Mobile Access Domain</option>
    			<option value="itd" <c:if test = "${domain =='itd'}"> selected </c:if>>Transport Domain</option>
    			<option value="icd" <c:if test = "${domain =='icd'}"> selected </c:if>>Core Domain</option>
    			<option value="ipd" <c:if test = "${domain =='ipd'}"> selected </c:if>>Passive Domain</option>
  			</select>
		</div>
	</div>
	<div class="col-md-4">
		<div class="form-group">
		<div class="checkboxes">
			<span><input type="checkbox" style="margin-top: 10px;" id="servise" ${service} /> Service</span>
		</div>
		</div>	
	</div>
	</div>
	
<div class="row">
	<div class="col-md-12">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Supported Technologies:</span>
			<div class="checkboxes" style="margin-top: 10px; margin-left: 10px;">
				<span>
	   	        	<input type="checkbox" id="techg2g" ${tech2G}/> 2G
					<input type="checkbox" id="techg3g" ${tech3G}/> 3G
		            <input type="checkbox" id="techg4g" ${tech4G}/> 4G
		            <input type="checkbox" id="techg5g" ${tech5G}/> 5G
				</span>
			</div>
			</div>
		</div>
	</div>
</div>
</div>

<div class="tab-pane fade" id="custom-tabs-finance" role="tabpanel" aria-labelledby="custom-tabs-finance-tab">
<p></p>
<div class="row">
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Warranty Period</span>
				<input type="text" id="warrantyper" value="${warrantyPeriod}" class="form-control text-input"  />
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Useful Life Months</span>
			<input type="text" id="useLifeMonths" value="${useLifeMonths}" class="form-control text-input"  />
			</div>
		</div>
	</div>

	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
				<span class="input-group-text">End Of Life</span>
				<input type="text" id="endoflive" value="${endOfLife}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
					<div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
					<div class="input-group-text"><i class="fa fa-calendar"></i></div>
					</div>
				</div>
			</div>					
		</div>
	
	
</div>
<div class="row">
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Valuation Rate</span>
				<input type="text" id="valuarate" value="${valuationRate}" class="form-control text-input"  />
			</div>
		</div>		
	</div>
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Depreciation Code</span>
				<input type="text" id="deprec_Code" value="${deprec_Code}" class="form-control text-input"  />
			</div>
		</div>		
	</div>
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Accumulated Depreciation Code</span>
				<input type="text" id="accumDeprec_Code" value="${accumDeprec_Code}" class="form-control text-input"  />
			</div>
		</div>		
	</div>
</div>

</div>
	   <div class="tab-pane fade" id="custom-tabs-barcode" role="tabpanel" aria-labelledby="custom-tabs-barcode-tab">

<p></p>


<div> 
  
		<form>
				
								
			<div class="table-responsive-sm"> 
			  <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						     <thead>
						         <tr>
						            <th>
								        <button type="button" id="selectAll" class="main">
								        <span class="sub"></span>Select</button></th>
						                <th width="220px">Barcode No</th>
						                <th width="220px">Barcode Shape</th>
						                <th width="120px">
								         <button type="button" id="Action_b" class="main">
								         <span class="sub"></span>Action</button></th>

						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
<input type="button" class="add-row" value="Add Row">
<button type="button" class="delete-row">Delete Row</button>
                   </form>
		</div>

<p></p>



	
	
	
	</div>
	



</div>
</div>
           
 </body>
 
 
 <script>
 
     		$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
   
			 $("#datetimepicker3").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
			$("#itmdescrip").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
			
            $("#itmcat").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
				
			$("#domaine").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});
  
        
  
 
 $("#saveButton").click(  function() {
	 console.log('saved now');
	 
	 // validate item code cannot be null
	 if ($("#itmcode").val()== '') {
	 alert('Itemcode cannot be Null');
	 return false;}
	 
	 
	 // validate type of valuation rate
	 if (($. isNumeric( $('#valuarate'). val()))== false) {
	 alert('valuation Rate is  not Numeric');
	 return false;}
	 
	 // validate creatd date cannot be null
	 val =$("#createdate").val();
     val1 = Date.parse(val);
     //console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
	 
	  // validate modified date cannot be null
	 val =$("#lstmodifdate").val();
     val1 = Date.parse(val);
     //console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
        
     // validate end of life date cannot be null
     val = $("#endoflive").val();
     val1 = Date.parse(val);
     console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert('End of life Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
			
	 var itemCode = document.getElementById("itmcode").value;
	 var createdDate = document.getElementById("createdate").value;
	 var lastModifiedDate = document.getElementById("lstmodifdate").value;
	 var itemName = document.getElementById("itmname").value;
	 var unit = document.getElementById("unt").value;
	 var itemDescription = document.getElementById("itmdescrip").value;
	 var domain = document.getElementById("domaine").value;
	 var itemCategory = document.getElementById("itmcat").value;
	 var itemType = document.getElementById("itmtyp").value;
	 var cableType = document.getElementById("cbltype").value;
	 var weight = document.getElementById("weit").value;
	 var weightUOM = document.getElementById("weituom").value;
	 var length = document.getElementById("len").value;
	 var width = document.getElementById("widths").value;
	 var height = document.getElementById("heit").value;
	 var sizeUOM = document.getElementById("siseuom").value;
	 var checkBox = document.getElementById("servise");
	 var service ;
	 if (checkBox.checked == true){
		  service = '1';
		  } else
			  {service = '0';}
	  
	 var endOfLife = document.getElementById("endoflive").value;
	 var valuationRate = document.getElementById("valuarate").value;
	 var checkBox2 = document.getElementById("disabled");
	 var disabled;
	 if (checkBox2.checked == true){
		 disabled = '1';
		  } else
			  {disabled = '0';}
	 
	 var itemImage = document.getElementById("itmimage").value;
	 var warrantyPeriod = document.getElementById("warrantyper").value;
     var checkt2 = document.getElementById("techg2g");
	 var tech2 ;
	 if (techg2g.checked == true){
		 tech2 = '1';
		  } else
			  {tech2 = '0';}
	 
     var checkt3 = document.getElementById("techg3g");
	 var tech3 ;
	 if (techg3g.checked == true){
		 tech3 = '1';
		  } else
			  {tech3 = '0';}
			  
     var checkt4 = document.getElementById("techg4g");
	 var tech4 ;
	 if (techg4g.checked == true){
		 tech4 = '1';
	 } else
	 {tech4 = '0';}

//     var checkt5 = document.getElementById("techg5g");
	 var tech5;
	 if (techg5g.checked == true){
		 tech5 = '1';
	 } else
	 {tech5 = '0';}
	 

	 console.log("itmcode" +itemCode);
	 console.log("createdDate" +createdDate);
	 console.log("lastModifiedDate" +lastModifiedDate);
	 console.log("itemName" +itemName);
	 console.log("unit" +unit);
	 console.log("itemDescription" +itemDescription);
	 console.log("domain" +domain);
	 console.log("itemCategory" +itemCategory);
	 console.log("itemType" +itemType);
	 console.log("cableType" +cableType);
	 console.log("weight" +weight);
	 console.log("weightUOM" +weightUOM);
	 console.log("length" +length);
	 console.log("width" +width);
	 console.log("height" +height);
	 console.log("sizeUOM" +sizeUOM);
	 console.log("service" +service);
	 console.log("endOfLife" +endOfLife);
	 console.log("valuationRate" +valuationRate);
	 console.log("disabled" +disabled);
	 console.log("itemImage" +itemImage);
	 console.log("warrantyPeriod" +warrantyPeriod);
	 console.log("tech2: " +tech2);
	 console.log("tech3: " +tech3);
	 console.log("tech4: " +tech4);
	 console.log("tech5: " +tech5);
	 
	 var itemCat = $("#itmcat").val();
	 var n = itemCat.indexOf(";");
     var res = itemCat.substring(0, n);
     
     
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ItemFormSave",
			dataType : "json",
			data : {
				"itemCode" : $("#itmcode").val(),
				"createdDate" : $("#createdate").val(),
				"lastModifiedDate" : $("#lstmodifdate").val(),
				"itemName" : $("#itmname").val(),
				"itemModel" : $("#itmModel").val(),
				"unit" : $("#unt").val(),
				"itemDescription" : $("#itmdescrip").val(),
				"itemImage" : $("#itmimage").val(),
				"domain" : $("#domaine").val(),
				"itemCategory" : $("#itmcat").val(),
				"itmCatCode" : $("#itmCatCode").val(),
				"itmPartNo" : $("#itmPartNo").val(),
				"itmSkuNo" : $("#itmSkuNo").val(),
				"itmUUID" : $("#itmUUID").val(),
				"itemManufact" : $("#itemManufact").val(),
				"itmOS" : $("#itmOS").val(),
				"itmPhysRAM" : $("#itmPhysRAM").val(),
				"itemProcType" : $("#itemProcType").val(),
				"itmProcVendor" : $("#itmProcVendor").val(),
				"itmKind" : $("#itmKind").val(),
				"itemType" : $("#itmtyp").val(),
				"itmMode" : $("#itmMode").val(),
				"cableType" : $("#cbltype").val(),
				"weight" : $("#weit").val(),
				"weightUOM" : $("#weituom").val(),
				"length" : $("#len").val(),
				"width" : $("#widths").val(),
				"height" : $("#heit").val(),
				"sizeUOM" : $("#siseuom").val(),
				"service" : service,
				"tech2G" : tech2,
				"tech3G" : tech3,
				"tech4G" : tech4,
				"tech5G" : tech5,
				"endOfLife" : $("#endoflive").val(),
				"valuationRate" : $("#valuarate").val(),
				"disabled" : disabled,
				"warrantyPeriod" : $("#warrantyper").val(),
				"useLifeMonths" : $("#useLifeMonths").val(),
				"deprec_Code" : $("#deprec_Code").val(),
				"accumDeprec_Code" : $("#accumDeprec_Code").val()				
			},
			success : function(data) {
				console.log("The returned data is " +data.BassamTest);
				$('#lstmodifdate').val(data.lastModifiedDate);
				var param ="${pageContext.request.contextPath}/ItemFormView?itemcode="+$("#itmcode").val();
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		
	
 })
 $("#deleteButton").click(  function() {
	 console.log('delete now');
	 var itemCode = document.getElementById("itmcode").value;
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ItemFormViewDelete",
			dataType : "json",
			data : {
				"itemCode" : $("#itmcode").val()
			},
			success : function(data) {
				//console.log("The returned data is " +data.BassamTest);
				//window.history.back();
			    location.replace("${pageContext.request.contextPath}/ItemListView");
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })
                $(".add-row").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
				
			$(".delete-row").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	
			
			$("#bisotab > tbody").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});	


			
					

			$(".add-row").click(function(){
	            console.log("add row clicked");

	   	var markup = "<tr><td><input type='checkbox' name='record'></td><td name='itbarcodeno'>"
				+"<input name='barcodeno'   type='text' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all'/></td>"
				+"<td name='barcodeshape' style='width:200px'> <svg name='barcode'></svg>    </td>"
				+"<td name='action'><input type='button' name= 'generate' value= Generate ></td></tr>";
	            $("#bisotab > tbody").append(markup);


	          
		        
	           $("input[name='barcodeno']").each(function(){
		           
	        	   var input = $(this).parent().parent().children().children('input[name=barcodeno]');
	               $(this).keypress(function(e) {
	    
	                   if(e.which == 13) {
	                       alert('You pressed enter!');
	                       var input2=input.val();
	                       alert(input2);
	                       var barcodeinput =$(this).parent().parent().children().children('svg[name=barcode]');
	                       barcodeinput.JsBarcode(input2, {
	        	               width:0.5,  
	        	               height:25,
	        	               lineColor:"#000", 
	        	               
	        	           });
	                       // eza fi action waraha biwa2fa
	                       return false;
	                   }
	               });
	               });

	            function getRandomString(length) {
	    		    var randomChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
	    		    var result = '';
	    		    for ( var i = 0; i < length; i++ ) {
	    		        result += randomChars.charAt(Math.floor(Math.random() * randomChars.length));
	    		    }
	    		    return result;
	    		    }
	          

	            $('#bisotab tr td input[name=generate]').on('click', function() {
	            var hash=getRandomString(10);
	            $(this).parent().parent().children().children('input[name=barcodeno]').val(hash);

	            var barcodeinput =$(this).parent().parent().children().children('svg[name=barcode]');
	       
	           barcodeinput.JsBarcode(hash, {
	               width:0.5,  
	               height:25,
	               lineColor:"#000", 
	               
	           });
	             });
	             
	          //  $('#bisotab tr td input[name=barcodeno]').on('keypress',function(event){ 
	            	// if (event.wich == 16) {
	     		       //   alert('hi');
	     		             //event.preventDefault();
	     		           
	     		         //var barcodesh =$(this).parent().parent().children().children('svg[name=barcode]').val();
	     		         	       
	     		         	        //  barcodesh.JsBarcode(input, {
	     		         	         //     width:0.5,  
	     		         	          //    height:25,
	     		         	          //    lineColor:"#000", 
	     		         	               
	     		         	         //  });
	            //	 }
	           // });
	             
	             
	           
	           
	            
	        	            
	            });

			

	         // Find and remove selected table rows
	            $(".delete-row").click(function(){
	              console.log("Welcome to delete");
	               getselectedrows ();
                   console.log("The slctDel array values are: " +slctDel);
	    			
	    			if (slctDel.length == 0) {
	    				alert('Select Row(s) to Delete');
	    				return false;
	    			}
	    			$("#bisotab > tbody").find('input[name="record"]').each(function(){
	    				if($(this).is(":checked")){
	    					$(this).parents("tr").remove();
	    			    }
	    			});
	    		     
	            });
	    		// end delete row
	    		$('body').on('click', '#selectAll', function  () {
					   
      					if ($(this).hasClass('allChecked')) {
         				$('input[type="checkbox"]', '#bisotab').prop('checked', false);
      						} else {
       						$('input[type="checkbox"]', '#bisotab').prop('checked', true);
       						}
       						$(this).toggleClass('allChecked');
       				
     					});

				

        
	       //mona

 </script>						 
 </script>
<!--  
 <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
 <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
 
   -->
 
 <style>
       		.ui-autocomplete {
            	max-height: 100px;
				overflow-y: auto; /* prevent horizontal scrollbar */
				overflow-x: hidden; /* add padding to account for vertical scrollbar */
				padding-right: 100px;
        	} 
    	</style>
 <script type='text/javascript'>
	$(document).ready(function() {

		/*		
		function customRenderItem(ul, item) {
			var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
				result = $('<li class="ui-menu-item" role="menuitem"></li>')
				.data('item.autocomplete', item)
				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
				.appendTo(ul);

			return result;
		}  */

		//var columns = [{name: 'ID', minWidth: '100px'}, {name: 'Name', minWidth:'100px'}, {name: 'Salary', minWidth:'70px'}],
			//colors = [['100', 'Bilal','120'], ['101', 'Bassam','110'], ['102', 'Salem','105'],['103', 'Sima','170'], ['104', 'Mary','160'], ['105', 'Roy','105']];
       	//var columns = [{name: 'Cat Code', minWidth: '150px'}, {name: 'Cat Name', minWidth:'150px'}, {name: 'Cat ABV', minWidth:'100px'}];	
       	var columns = [{name: 'Cat Code', minWidth: '250px'}];
				
		$("#itmcat").autocomplete({
		showHeader: true,
		columns: columns,
        //source: colors, minLength:0, maxShowItems: 4, scroll:true, 
        source: function(request, response) {
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllCategory',
	                 data: {
							"itemCategory" : $("#itmcat").val(),
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListItemCategory);
	                         //console.log('data is ;'+ data.ListItemCategory);
	                         //colors = data.ListItemCategory;
	                         //console.log('colors ;'+ colors);
	                     
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 4, scroll:true,		
               
        
		     select: function(event, ui) {
                $("#itmcat").val(ui.item[0]);
               // $("#itmcat").val(ui.item[0] +";"+ ui.item[1] );
                return false;
            }
        }).autocomplete("instance")._renderItem = function(ul, item) {
            return $("<li class='each'>")
                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                    //item[0] + "</span><br><span class='desc'>" +
                   // item[1] + "</span><br><span class='desc'>" +
                    //item[2] + "</span></div>")
                    item[0] + "</span><br><span class='desc'>" +
                    item[1] +', '+ item[2] + "</span></div>")
                .appendTo(ul);
        };
		 $("#itmcat").focus(function(){
        if (this.value == ""){
            $(this).autocomplete("search");
        }
    });
	
	// to add New in orange of a new record using add button from itemList	
	if ('${ListPRqItem}' != "addNew") {
	}
	else {
	          // set status to new and green while new record
	           $("#formStatus").text("New");
					$('.dot').css({"background-color" : "orange"});
	       }	
		
		
		
		
});

 
</script>
 
 

	
 </html>
