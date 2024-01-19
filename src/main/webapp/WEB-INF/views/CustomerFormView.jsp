<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
    <!-- ADDED BY AHMAD TAFECH -->
	<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<!-- 

	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	 
	<script src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>
	
	 -->
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
	
		<!-- ALM GRID Scripts -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/Customer_Boq.js"></script>
		
    <style>
    
    	   /*Doaa's popup Email Div'*/
    
    #popUpDiv {
position:fixed;
top: 30%;
left: 50%;
background-color:#eeeeee;
border:5px solid #08526d;
width:400px;
height:450px;
margin-left:-150px;
margin-top:-95px;

-moz-border-radius: 16px;
-webkit-border-radius: 16px;
border-radius: 16px;
box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px#000000;

z-index: 9003;
 /*above nine thousand*/}
    .hide-row { visibility: hidden; }
    
    
    .label{ 
  display: table-caption;
  text-align: center;
  font-size: 20px;
  font-style: bold;
  }
  
.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					width: 350px;
				    z-index:9999;
					
	        		}
	        		
				/*.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
				/*	overflow-x: both; /* add padding to account for vertical scrollbar */
				/*	padding-right: 100px;
	        		}*/

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
				
				
				
				#textalert{
				
				font-size:15px;
				color:red;
				
				margin-left:10px;
				display: inline-block;
				margin-bottom:0px;
				padding-bottom:0px;
				 
				}
	    #mapCont {
        height: 610px;
        width:100%;
       }	
       .panel-body {
       border:2px solid #808080;
       border-radius: 30px 15px 15px 5px;}
       
       #mapText{
       border:hidden;
       width:110px;
       height:25px;
       border:hidden;
       background-color:#87CEEB;
       margin-left:20px;
       text-align: center;
       }
          
		.imagesize {
		  height: 350px;
		  width: 350px;
		  display: inline-block;
		  margin-top: 10px;
		}
       
        .nav-link.active {
         color: #1D3763 !important;
         }
 	
 	
.fixed-headerr {
	opacity: 1;
	filter: alpha(opacity = 100);
	background: #ebf2ef;
	position: sticky;
	top: 0;
	z-index: 15;
}
.btn-pop {
	background-color: #C2CBC0 !important;
	border-color: #C2CBC0;
}

.btn-pop:hover {
	color: #fff;
	background-color: #8696A0 !important;
	border-color: #8696A0 !important;
}	
.nav-link.active {
  background-color: #FFD966 !important;
  color: #00757c !important;
}
 	
td.hidden-td {
  display: none;
}
.createSurvey:hover{
    color: white;
	background-color: #007bff;
}


.createSurvey {
background-color:white;
color: #007bff;
border-color: #939191f5;
}

 </style>



        
       
            
</head>
<body>

<%--   <c:set var = "page" value = "Sales"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="Sales" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
   <p></p>
     
     <div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
								
							<span class="input-group-text" style="color:green">Customer ID</span>
							<input type="text" id="customerID"  value="${customerID}" class="form-control text-input"  readonly />
						</div>
					</div>
			</div>
			
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span  class="input-group-text" style="width:210px;">Status</span>
				<select id="custstat" class="form-control">
								<option value="Draft" <c:if test = "${custstat =='Draft'}" > selected </c:if> >Draft</option>
								<option value="Activated" <c:if test = "${custstat =='Activated'}" > selected </c:if>>Activated</option>
								<option value="Deactivated" <c:if test = "${custstat =='Deactivated'}" > selected </c:if>>Deactivated</option>
								<option value="Cancelled" <c:if test = "${custstat =='Cancelled'}" > selected </c:if>>Cancelled</option>
								<option value="Blocked" <c:if test = "${custstat =='Blocked'}" > selected </c:if>>Blocked</option>
								</select>
				</div>							
			</div>
			
<!-- 					<div class="pad col-md-3 hide-row"></div> -->
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Customer</span>
						<input type="text" id="selectnav" value="${selectnav}"
							style="width: 430px" class="form-control text-input" />				</div>
			</div>
		</div>

			<div  class="col-md-3 text-right"  >
								
				<i>&nbsp</i><span class="dot"></span>
				<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>		
			</div>
		</div>
		

		
		
		
		
		
		
		
		<div class="row">
		
		
				<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
					<input type="text" id="dateCustomer" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${lastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
						</div>
				</div>
		</div>
	</div>
			
			
						<div class=" col-md-3 nextprvItems">
				<label id="label-1" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
					<nav aria-label="Page navigation">
			  		<ul class="pagination">
			    		<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 53px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>
			  		</ul>
					</nav>
			</div>
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/CustomerListView"'
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
	</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-12 col-sm-12 col-lg-12">
            <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
                <li class="nav-item">
                    <a class="nav-link active" id="custom-tabs-Cust-Info-tab" data-toggle="tab" href="#custom-tabs-Custinfo-home" role="tab" aria-controls="custom-tabs-Custinfo-home" aria-selected="true" style="color: gold;">
                        Customer INFORMATION
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="custom-tabs-Services-tab" data-toggle="tab" href="#custom-tabs-Cust-Services" role="tab" aria-controls="custom-tabs-Cust-Services" aria-selected="false" style="color: gold;">
                       SERVICES
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="custom-tabs-Survey-tab" data-toggle="tab" href="#custom-tabs-Cust-Survey" role="tab" aria-controls="custom-tabs-Cust-Survey" aria-selected="false" style="color: gold;">
                        SURVEY
                    </a>
                </li>
                <li class="nav-item ml-auto">
                    <div class="dropdown" style="display:inline-block;">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                            Actions
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" type="button" id="Activatewh">Activate</a>
                            <a class="dropdown-item" type="button" id="Deactivatewh">Deactivate</a>
                            <a class="dropdown-item" type="button" id="Cancelwh">Cancel</a>
                            <a class="dropdown-item" type="button" id="Blockwh">Block</a>
                            <a class="dropdown-item" type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </a>
                        </div>
                    </div>
                    <button type="button" id="deleteButton" class="btn btn-primary BtnActive">
                        <i class="fa fa-trash"></i> Delete
                    </button>
                    <button type="button" id="saveButton" class="btn btn-primary BtnActive">
                        <i class="fa fa-save"></i> Save
                    </button>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="tab-content" id="custom-tabs-Custinfo-tabContent">
        <div class="tab-pane fade show active" id="custom-tabs-Custinfo-home" role="tabpanel" aria-labelledby="custom-tabs-Cust-Info-tab">
            <!-- Customer INFORMATION Content -->
            <p></p>
            	<table width="100%" border="0" cellpadding="0" cellspacing="0" id="customerInfo_tbl">
	<tr>
	<td width=20% valign="top" class="left_col">
			<div class="form-group">
				<div class="input-group-prepend">
						<span class="input-group-text">Customer Name</span>
						<input type="text" id="custName"  value="${customerName}" class="form-control text-input"   />
				</div>
			</div>
		</td>
		<td rowspan=8></td>
		<td rowspan=8 valign="top">
		
		  <div class="panel-body" style="height: 500px;margin-left:10%"  >
	
		  <div class="card-body"> <!-- style='border:hidden;' -->
		  <div class="btn-toolbar" style="text-align: left; margin-bottom: 5px;margin-top: auto;">
		  <div class="btn-group flex-wrap" data-toggle="buttons" style="row-gap: 2px;">
		  <div class="top-btn-filter"  >
		  <button id="CoordsButton" name="CoordsButton" class="buttonTog" style="margin-left:200px;"><i class="fas fa-map"></i> Get Coordinates</button>
		  </div>
		  <div id="txtDiv"><input id = "mapText" style="margin-left:20%;" type='text' disabled />
		 
		  </div>
		  </div>
		  <div id="mapCont" style='height: 420px;'> 
		  </div>
		  </div> 
		  </div>
	    	</div>
		</td>
		</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Customer Acronyms</span>
							<input type="text" id="acronymsName"  value="${customerAcronyms}" class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Mobile</span>
							<input type="text" id="mobile"  value="${mobile}" class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	</tr>
   		<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Telephone Number</span>
							<input type="text" id="telNumber"  value="${telNnumber}" class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Customer Ref ID</span>
							<input type="text" id="custRefId"  value="${refCustId}" class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	</tr>
   	 <tr>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Customer Type</span>
							<select name="cars" id="custType"  style="width: 175px;  text-align-last:center;"class="form-control text-input"    >
				
								<option value="Enterprise" <c:if test = "${customerType =='Enterprise'}" > selected </c:if>  >Enterprise</option>
								<option value="Personal" <c:if test = "${customerType =='Personal'}"> selected </c:if>>Personal</option>
								
							  </select>
    
                      <span class="input-group-text">Customer Category</span>
							<select name="cars" id="custCategory"  style="width: 175px;  text-align-last:center;"class="form-control text-input"    >
								
								<option value="Normal" <c:if test = "${customerCategory =='Normal'}"> selected </c:if>>Normal</option>
								<option value="Vip" <c:if test = "${customerCategory =='Vip'}" > selected </c:if>  >VIP</option>
								<option value="Loyal" <c:if test = "${customerCategory =='Loyal'}"> selected </c:if>>Loyal</option>
								<option value="Impulse" <c:if test = "${customerCategory =='Impulse'}"> selected </c:if>>Impulse</option>
								<option value="Discount" <c:if test = "${customerCategory =='Discount'}"> selected </c:if>>Discount</option>
								<option value="Need-based" <c:if test = "${customerCategory =='Need-based'}"> selected </c:if>>Need-based</option>
								<option value="Wandering" <c:if test = "${customerCategory =='Wandering'}"> selected </c:if>>Wandering</option>
								
								
							  </select>	
                    </div>
			</div>
   		</td>
   	</tr>
   		<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
                            <span class="input-group-text">Address</span>
                            <input type="text" id="loc" value="${address}" class="form-control text-input"  />
                        
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
                            <span class="input-group-text">Location ID</span>
                            <input type="text" id="locationId" value="${locationId}" class="form-control text-input"  />
                        
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest" >
      					<span class="input-group-text" style="width: 100px;">Latitude</span>
                            <input type="text" id="Lat" value="${lat}" class="form-control text-input"  style="width: 200px;"/>	
                    
      					<span class="input-group-text" style="margin-left:5px; width: 120px;">Longitude</span>
                            <input type="text" id="Lng" value="${lng}" class="form-control text-input" style="width: 200px;"/>
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest" >
      					<span class="input-group-text" style="width: 100px;">Region ID</span>
                            <input type="text" id="regionId" value="${regionId}" class="form-control text-input" style="width: 200px;" />	
                    
      					<span class="input-group-text" style="margin-left:5px; width: 120px;">Region Name</span>
                            <input type="text" id="regionName" value="${regionName}" class="form-control text-input" style="width: 200px;" />	
                    </div>
			</div>
   		</td>
   	</tr>
   	 	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest" >
      					<span class="input-group-text" style="width: 100px;">Area ID</span>
                            <input type="text" id="areaId" value="${areaId}" class="form-control text-input" style="width: 200px;" />	
                    
      					<span class="input-group-text" style="margin-left:5px; width: 120px;">Area Name</span>
                            <input type="text" id="areaName" value="${areaName}" class="form-control text-input" style="width: 200px;" />	
                    </div>
			</div>
   		</td>
   	</tr>
   		<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest" >
      					<span class="input-group-text" style="width: 100px;">City</span>
                            <input type="text" id="city" value="${city}" class="form-control text-input" style="width: 200px;" />	
                    
      					<span class="input-group-text" style="margin-left:5px; width: 120px;">Postal Address</span>
                            <input type="text" id="postalAddr" value="${postalAddress}" class="form-control text-input" style="width: 200px;" />	
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
                            <span class="input-group-text">Nationality </span>
                            <input type="text" id="nationality" value="${nationality}" class="form-control text-input"  />
                        
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
                            <span class="input-group-text">Email</span>
                            <input type="text" id="email" value="${email}" class="form-control text-input"  />
                        
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
                            <span class="input-group-text">Website</span>
                            <input type="text" id="website" value="${website}" class="form-control text-input"  />
                        
                    </div>
			</div>
   		</td>
   	</tr>
</table>
 </div>
 <div class="tab-pane fade" id="custom-tabs-Cust-Survey" role="tabpanel" aria-labelledby="custom-tabs-Survey-tab">
            <!-- Survey tab Content -->
    <div class="container">
	<div id="surveyPopup" class="modal fade custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
					<h5 id="surveyPopupHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF;">Survey Details</h5>
					<div style="float: right;">
						<button type="button" name="closeCustSurveyPopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
						<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
					</div>
				  </div>
								
				<div class="modal-body" style="height: 500px;">
					<br>
					<div class="container-fluid">
							<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<div class="input-group-prepend">
										<span class="input-group-text">Survey ID</span>
										<input type="text" readonly id="popupSurveyID" class="form-control text-input"  style="height: 37px;" />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<div class="input-group-prepend">
										<span class="input-group-text">Creation Date</span>
										<input type="text" readonly id="surveyPopupCreationDate" class="form-control text-input"  style="height: 37px;" />
									</div>
								</div>
							</div>
							</div></div>
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span class="input-group-text">Longitude</span>
												<input type="text" readonly id="surveyPopupLongitude" class="form-control text-input"  style="height: 37px;" />
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span class="input-group-text">Latitude</span>
												<input type="text" readonly id="surveyPopupLatitude" class="form-control text-input"  style="height: 37px;" />
											</div>
										</div>
									</div>													
								</div>
						</div>					
						<div class="container-fluid" >							
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="manPoint-tab" style="color: gold;" data-toggle="tab" href="#manP" role="tab" aria-controls="fiber" aria-selected="true">Manhole</a></li>
							<li class="nav-item"><a class="nav-link " id="handPoint-tab" style="color: gold;" data-toggle="tab" href="#handP" role="tab" aria-controls="source_dest" aria-selected="false">Handhole</a></li>
							<li class="nav-item"><a class="nav-link " id="dboardPoint-tab" style="color: gold;" data-toggle="tab" href="#dboardP" role="tab" aria-controls="conduit" aria-selected="false">Distribution Board</a></li>
							<li class="nav-item"><a class="nav-link " id="fPaths-tab" style="color: gold;" data-toggle="tab" href="#fPaths" role="tab" aria-controls="conduit" aria-selected="false">Fiber Paths</a></li>
							<li class="nav-item"><a class="nav-link " id="nodePoint-tab" style="color: gold;" data-toggle="tab" href="#nodeP" role="tab" aria-controls="nodes" aria-selected="false">Node</a></li>
						</ul>
						<div class="tab-content" style="min-height: 180px">
						<div class="tab-pane active" id="manP" role="tabpanel" aria-labelledby="manPoint-tab">
											<div class="container-fluid">
												<div id="findNearestManRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstManhole" style="display: block; height: 500px; overflow-y: auto;" class="searchable sortable">
														 <thead>
															<tr>
																<th style="min-width: 150px;" >Manhole ID</th>
																<th style="min-width: 150px;">ManholeName</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 150px;">Latitude</th>
																<th style="min-width: 100px;">Linear Distance(km)</th>
																<th style="min-width: 80px;">Driving Distance(km)</th>
																<th style="min-width: 80px;">Geo Distance(km)</th>
															</tr>
															 </thead>
															<tbody id="manholeSurveyRes">
															</tbody>
														</table>
													</fieldset>
												</div>
											</div>
									        <div id="manholeTotal" Style="Padding-top: 15px;"><b>Total Manholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalManhole" name="totalManhole" readonly>	 </div>
										</div>
										<div class="tab-pane" id="handP" role="tabpanel"
											aria-labelledby="handPoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findNearestHandRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstHandhole" class="searchable sortable"
															style="display: block; height: 500px; overflow-y: auto;">
															 <thead>
															<tr>
																<th style="min-width: 150px;" >Handhole ID</th>
																<th style="min-width: 150px;">HandholeName</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Linear Distance(Km)</th>
																<th style="min-width: 100px;" >Driving Distance(km)</th>
																<th style="min-width: 80px;">Geo Distance(km)</th>
															</tr>
															</thead>
															
															<tbody id="handholeSurveyRes">
															</tbody>
														</table>
													</fieldset>

												</div>
											</div>
                                             <div id="handholeTotal" Style="Padding-top: 15px;"><b>Total Handholes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalHandhole" name="totalDB" readonly>	 </div>
										</div>
										<div class="tab-pane" id="dboardP" role="tabpanel"
											aria-labelledby="dboardPoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findNearestDbRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstDB" style="display: block; height: 500px; overflow-y: auto;">
														 <thead>															
															<tr>
																<th style="min-width: 150px;" >DBoard ID</th>
																<th style="min-width: 150px;">DBoard Name</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Linear Distance(km)</th>
																<th style="min-width: 100px;">Driving Distance(km)</th>	
																<th style="min-width: 80px;">Geo Distance(km)</th>
															</tr>
															 </thead>															
															<tbody id="dbSurveyRes">
															</tbody>
														</table>
													</fieldset>
												 </div>
											</div>
                                            <div id="DbTotal" Style="Padding-top: 15px;"><b>Total Distribution Boards:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDB" name="totalDB" readonly>										
                                            </div>
										</div>
										<div class="tab-pane" id="fPaths" role="tabpanel" aria-labelledby="fPaths-tab">
											<p></p>
											<div class="container-fluid">
											<div class="row" style="height: 180px;">
										<fieldset class="field_set">
											<legend style="width: auto;" class="fieldset_legend"></legend>
											<table id="" style="display: block; height: 170px; overflow-y: auto;">
												<tr>
													<th style="min-width: 150px;" class="row-pad">Strand ID</th>
													<th style="min-width: 150px;">StrandName</th>
													<th style="min-width: 350px;">Source</th>
													<th style="min-width: 350px;">Destination</th>
												</tr>
												<tbody id="strandSurveyRes">
												</tbody>
											</table>
										</fieldset>
									</div>
									<br>
									<div class="row" style="height: 180px;">
										<fieldset class="field_set">
											<legend style="width: auto;" class="fieldset_legend"></legend>
											<table id="" style="display: block; height: 170px; overflow-y: auto;">
												<tr>
													<th style="min-width: 150px;" class="row-pad">Tube ID</th>
													<th style="min-width: 150px;">TubeName</th>
													<th style="min-width: 350px;">Source</th>
													<th style="min-width: 350px;">Destination</th>
												</tr>
												<tbody id="tubeSurveyRes">
												</tbody>
											</table>
										</fieldset>
									</div>
									<br>
									<div class="row" style="height: 180px;">
										<fieldset class="field_set">
											<legend style="width: auto;" class="fieldset_legend"></legend>
											<table id="" style="display: block; height: 170px; overflow-y: auto;">
												<tr>
													<th style="min-width: 150px;" class="row-pad">Fiber ID</th>
													<th style="min-width: 150px;">FiberName</th>
													<th style="min-width: 350px;">Source</th>
													<th style="min-width: 350px;">Destination</th>
												</tr>
												<tbody id="fiberSurveyRes"></tbody></table></fieldset></div></div></div>
								<div class="tab-pane" id="nodeP" role="tabpanel" aria-labelledby="nodePoint-tab">
											<p></p>
											<div class="container-fluid">
												<div id="findNearestNodeRes"></div>
												<div class="row" style="height: 500px;">
													<fieldset class="field_set">
														<legend style="width: auto;" class="fieldset_legend"></legend>
														<table id="findNearstNode" class="searchable sortable"
															style="display: block; height: 500px; overflow-y: auto;">
															 <thead>
															<tr>
																<th style="min-width: 150px;" >Node ID</th>
																<th style="min-width: 150px;">Node Name</th>
																<th style="min-width: 100px;">Longitude</th>
																<th style="min-width: 100px;">Latitude</th>
																<th style="min-width: 100px;">Linear Distance(Km)</th>
																<th style="min-width: 100px;">Driving Distance(km)</th>
																<th style="min-width: 80px;">Geo Distance(km)</th>
															</tr></thead>
															<tbody id="nodeSurveyRes"></tbody>
														</table>
													</fieldset>
												</div>
											</div>
                                             <div id="nodeTotal" Style="Padding-top: 15px;"><b>Total Nodes:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalNode" name="totalNodes" readonly></div>
										</div>
										</div></div>					
					</div>
				</div>
					<div class="modal-footer"></div>
			</div>
		</div>			
	</div>
 				
 			<div>
					<form>
					<br>
						<div class="table-responsive-sm">
							<table id="customerSurveysTable" class="table table-striped table-bordered table-sm" style="display: block; height: 200px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
									<th>
										<button type="button" id="selectAllSurv" class="main"><span class="sub"></span>Select</button>
									</th>
										<th>Service Type</th>
										<th>Survey ID</th>
										<th>Survey</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>
					</form>
				</div>
		</div>
 
     <div class="tab-pane fade" id="custom-tabs-Cust-Services" role="tabpanel" aria-labelledby="custom-tabs-Services-tab">
         <!-- Customer Services Content -->
				<br>
				<div>
					<form>
						<div class="table-responsive-sm">
							<table id="customerServicesTable" class="table table-striped table-bordered table-sm" style="display: block; height: 200px; overflow-y: auto;">
								<thead>
									<tr class="fixed-headerr">
										<th>
											<button type="button" id="selectAllServ" class="main"><span class="sub"></span>Select</button>
										</th>
										<th>Service Type</th>
										<th>Billing Code</th>
										<th>Circuit No</th>
										<th>Longitude</th>
										<th>Latitude</th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th hidden></th>
										<th>Survey</th>
				
									</tr>
								</thead>
								<tbody></tbody>
							</table>
						</div>

						<input type="button" class="add-row" value="Add Row" onclick="addServiceRow('addNewRow')">
						<button type="button" class="delete-row">Delete Row</button>
						<input type="text" id="RowToDeleteService" hidden value="">
					</form>
				</div>
				
				<!-- customer service popup  -->

				<div class="container">
					<div id="customerServicePopup" class="modal fade custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
							<div class="modal-content">
								<div class="modal-header" style="background-color: #2678CC;">
									<h5 id="popupHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF;"></h5>

									<button type="button" name="insertServiceBelow" onclick="insertServiceBelow()" class="btn btn-default btn-primary BtnActive btn-pop" style="color:black; position: relative; left: 50px;font-weight: bold;">
										Insert Below</button>
									<button type="button" name="insertServiceAbove" onclick="insertServiceAbove()" class="btn btn-default btn-primary BtnActive btn-pop" style="color: black; position: relative; left: 60px;font-weight: bold;">
										Insert Above</button>
									<button type="button" name="deleteBoqServiceRow" onclick="deleteBoqServiceRow()" class="btn btn-default btn-primary BtnActive btn-pop" style="color: black; position: relative; left: 70px;font-weight: bold;">
										Delete</button>
									<button name="previousServiceRow" onclick="previousServiceRow()"  class="btn btn-default btn-primary BtnActive btn-pop" style="color: black; position: relative; left: 80px;font-weight: bold;">
										Previous</button>
									<button name="nextServiceRow" onclick="nextServiceRow()" class="btn btn-default btn-primary BtnActive btn-pop" style="color: black; position: relative; left: 90px;font-weight: bold;">
										Next</button>

									<button type="button" name="closeCustServicePopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
									<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
								</div>
								
								<div class="modal-body" style="height: 500px;">
									<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
										<li class="nav-item">
										<a class="nav-link active" id="services-tab" style="color: gold;" data-toggle="tab" href="#services" role="tab" aria-controls="services" aria-selected="true">Service</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="services" role="tabpanel" aria-labelledby="services-tab">
											<p></p>
											<div class="container-fluid">
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Customer Service ID</span> 
																<input type="text" id="custServID" class="form-control text-input" readonly style="width:190px; height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Customer ID</span> 
																<input type="text" id="popupCustomerID" class="form-control text-input" readonly style="width: 675px; height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="container-fluid">
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Creation Date</span> 
																<input type="text"  readonly class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="serviceCreationDate"  style="width: 675px; height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Last Modified Date</span>
																 <input type="text" readonly class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="servicelstModfDate" style="width: 675px; height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="container-fluid">
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Service Type</span>
																<select name="popupServType" id="popupServiceType"  style="width:674px;height:37px;" class="form-control text-input" >
															       <option value='BAN' selected>BAN</option>
															       <option value='BVPN' >BVPN</option>
															       <option value='CN' >CN</option>
															       <option value='Easynet' >Easynet</option>
															       <option value='Jambonet' >Jambonet</option>
															       <option value='KEN'  >KEN</option>
															       <option value='Kenstream' >Kenstream</option>
															       <option value='KNL' >KNL</option>
															       <option value='LNK' >LNK</option>
															       <option value='MS' >MS</option>
															       <option value='Rent' >Rent</option>
															       <option value='SAT' >SAT</option>
															       <option value='TEL_ISDN' >TEL_ISDN</option>
															       </select>
															</div>
														</div>														
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
															<span class="input-group-text">Category</span>
																<select name="popupServCat" id="popupServiceCat"  style="width:674px;height:37px;" class="form-control text-input" >
															       <option value='Carrier' selected>Carrier</option>
															       <option value='Data' >Data</option>
															       <option value='ISDN' >ISDN</option>
															       <option value='KEN&STA' >KEN & STA</option>		
															 </select>
															</div>
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="container-fluid">
												<div class="row">
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Longitude</span>
																 <input type="text" id="popupLongitude" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Latitude</span>
																 <input type="text" id="popupLatitude" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Ref. ID</span>
																 <input type="text" id="popupRefID" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
										<div class="container-fluid">
												<div class="row">
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Billing Code</span>
																 <input type="text" id="popupBillingCode" class="form-control text-input" style="height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Circuit No</span>
																 <input type="text" id="popupCircuitNo" class="form-control text-input"  />
															</div>
														</div>
													</div>
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Circuit ID</span>
																 <input type="text" id="popupCircuitID" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
														<div class="container-fluid">
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Radio ID</span>
																 <input type="text" id="popupRadioID" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Radio Name</span>
																 <input type="text" id="popupRadioName" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="container-fluid">
												<div class="row">
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Link Name</span>
																 <input type="text" id="popupLinkName" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-6">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">NE Port Desc</span>
																 <input type="text" id="popupPortDesc" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
												</div>
											</div>
											<div class="container-fluid">
												<div class="row">
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Media Type</span>
																	<input type="text" id="popupMediaType" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
															<span class="input-group-text">Tx Media</span>
																<select name="popuptxMed" id="popupTxMedia"  style="height:37px;" class="form-control text-input" >
																 <option value='ULR' selected>ULR</option>
															     <option value='Fiber' >Fiber</option>	
																</select>
															</div>
															</div>
														</div>
														<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">NUM</span>
																 <input type="text" id="popupNum" class="form-control text-input"  style="height: 37px;" />
															</div>
														</div>
													</div>
													</div>
												</div>
										<div class="container-fluid">
												<div class="row">
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Status</span>
																<select name="popupStat" id="popupStatus"  style="height:37px;" class="form-control text-input" >
																	<option value='draft' selected>Draft</option>
															    	 <option value='approved' >Approved</option>
															    	 <option value='activated' >Activated</option>	
															    	  <option value='deactivated' >Deactivated</option>	
															     </select>
															</div>
														</div>
													</div>
													<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
															<span class="input-group-text">Capacity MB</span>
																 <input type="text" id="popupCapacityMB" class="form-control text-input"  style="height: 37px;" />
															</div>
															</div>
														</div>
														<div class="col-sm-4">
														<div class="form-group">
															<div class="input-group-prepend">
																<span class="input-group-text">Region ID</span>
																 <input type="text" id="popupRegionID" class="form-control text-input" style="height: 37px;" />
															</div>
														</div>
													</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer"></div>
							</div>
						</div>
					</div>
				</div>
				<!--end of popup  -->
        </div>
        

	


          <div id="popUpDiv" style="display:none;">
  <div class="email" style="margin-top:50px;" >
 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Sender</span>
						<input type="text" id="email"   class="ui-widget ui-widget-content ui-corner-all form-control" />
						<input type="text" id="password" value="${password}"  class="ui-widget ui-widget-content ui-corner-all form-control" hidden/>
					
					</div>
				</div>
	</div>
	 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width:80px;">   Email To</span>
						<input type="text" id="emailTo" class="form-control text-input" />
					
					</div>
				</div>
	</div>
	<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width:80px;">   ccEmail </span>
						<input type="text" id="ccmail" class="form-control text-input" />
					
					</div>
				</div>
	</div>
 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Subject</span>
						<input type="text" id="subject"  class="form-control text-input" />
					
					</div>
				</div>
	</div>
<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Message</span>
						<textarea  id="message"  class="form-control text-input" cols="200" rows="4" ></textarea>
					
					</div>
				</div>
	</div>
	<div class="col-md-12">
				<p></p>
				<div class="form-group"  style=" margin-left:100px; ">
	
			<button type="button" id="send"
				class="btn btn-primary BtnActive">
				<i class="fa fa-paper-plane" ></i> Send
				</button>
                        
				<button type="button" id="cancelButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-times"></i> Cancel
				</button>
								</div>
								</div>
	</div>
	</div>			
       
</body>

<script>
var boqArray =[];
var dict=[];
var slctDelArray =[];
var surveyBoqArray =[];


if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}

if ('${custSurveyList}' != "addNew") {
	surveyBoqArray = ${custSurveyList};
	  if(surveyBoqArray.length >0 ) {
		  for (i = 0;i<surveyBoqArray.length;i++){	


			  if(surveyBoqArray[i][9] !=null) {
				  
				createdDate = surveyBoqArray[i][6];
				var d = new Date(createdDate);

				// Get date components
				var mm = ("0" + (d.getMonth() + 1)).slice(-2);
				var dd = ("0" + d.getDate()).slice(-2);
				var yy = d.getFullYear();

				// Get time components
				var hours = d.getHours();
				var minutes = ("0" + d.getMinutes()).slice(-2);

				// Determine AM or PM
				var ampm = hours >= 12 ? 'PM' : 'AM';

				// Convert to 12-hour format
				hours = hours % 12;
				hours = hours ? hours : 12;

				var creationDate = dd + '-' + mm + '-' + yy + ' ' + hours + ':' + minutes + ' ' + ampm;
			
				var surveyItemRow = "<tr>";
					surveyItemRow= surveyItemRow +"<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td>"
					surveyItemRow = surveyItemRow + "<td name='serviceTypeSurvey' style='text-align:center;'><input type='text' value='" + surveyBoqArray[i][1] + "' class='form-control text-input' style='margin-right: 10px;' readonly></td>";
					surveyItemRow= surveyItemRow +"<td><input type='text' value='" + surveyBoqArray[i][9] +"' class='form-control text-input' style='width:auto' readonly></td>"
					surveyItemRow= surveyItemRow +"<td><button type = 'button'  href='#' name='popUpMenu' onclick='openSurveyPopup(this)' class='btn btn-default createSurvey'  style='position:relative;left:3px;'>Show Survey</button></td>"
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='surveyBillingCode'><input hidden type='text' value='" + surveyBoqArray[i][2] +"' class='form-control text-input' ></td>";
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='surveyCircuitNo'><input hidden type='text' value='" + surveyBoqArray[i][3] +"' class='form-control text-input' ></td>";
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='surveyLongitude'><input hidden type='text' value='" + surveyBoqArray[i][4] +"' class='form-control text-input'></td>";
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='surveyLatitude'><input hidden type='text' value='" + surveyBoqArray[i][5] +"' class='form-control text-input'></td>";
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='surveyCreationDate'><input type='text' hidden value='" +creationDate+"' class='form-control text-input'></td>";
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='custServiceIDSurvey'><input type='text' hidden value='" + surveyBoqArray[i][0] +"' class='form-control text-input'></td>";
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='surveyRefID'><input type='text' hidden value='" + surveyBoqArray[i][8] +"' class='form-control text-input' ></td>";
					surveyItemRow =surveyItemRow + "<td class='hidden-td' name='surveyID'><input type='text' hidden value='" + surveyBoqArray[i][9] +"' class='form-control text-input' ></td>";
					surveyItemRow =surveyItemRow + "</tr>";
				    $("#customerSurveysTable > tbody").append(surveyItemRow);
				
			  }	
		}
	  }
	
}
if ('${custServiceList}' != "addNew") {
    boqArray = ${custServiceList};

  if(boqArray.length >0 ) {
	for (i = 0;i<boqArray.length;i++){	
		createdDate = boqArray[i].createdDate;
		var d = new Date(createdDate);

		// Get date components
		var mm = ("0" + (d.getMonth() + 1)).slice(-2);
		var dd = ("0" + d.getDate()).slice(-2);
		var yy = d.getFullYear();

		// Get time components
		var hours = d.getHours();
		var minutes = ("0" + d.getMinutes()).slice(-2);

		// Determine AM or PM
		var ampm = hours >= 12 ? 'PM' : 'AM';

		// Convert to 12-hour format
		hours = hours % 12;
		hours = hours ? hours : 12;

		var creationDate = dd + '-' + mm + '-' + yy + ' ' + hours + ':' + minutes + ' ' + ampm;
			

		lstModfDate = boqArray[i].lastModfDate;
		var d = new Date(lstModfDate);
		var mm = ("0" + (d.getMonth() + 1)).slice(-2);
		var dd = ("0" + d.getDate()).slice(-2);
		var yy = d.getFullYear();
		// Get time components
		var hours = d.getHours();
		var minutes = ("0" + d.getMinutes()).slice(-2);
		// Determine AM or PM
		var ampm = hours >= 12 ? 'PM' : 'AM';
		// Convert to 12-hour format
		hours = hours % 12;
		hours = hours ? hours : 12;
		var lastModfDate = dd + '-' + mm + '-' + yy + ' ' + hours + ':' + minutes + ' ' + ampm; //(US)

		
		var itemRow = "<tr>";
		itemRow= itemRow +"<td style='text-align:center;width:100px'><input type='checkbox' name='record' style='margin-top:12px;'><button type = 'button' href='#' name='popUpMenu' onclick='openServicePopup(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
        itemRow = itemRow + "<td style='width:280px' name='serviceType'><select class='form-control' name='serviceType'>";
     	itemRow = itemRow + "<option value='None' selected>Select an Option</option>";
     	if (boqArray[i].serviceType === 'BAN') {
     	    itemRow = itemRow + "<option value='BAN' selected>BAN</option><option value='BVPN'>BVPN</option><option value='CN'>CN</option><option value='Easynet'>Easynet</option><option value='Jambonet'>Jambonet</option><option value='KEN'>KEN</option><option value='Kenstream'>Kenstream</option><option value='KNL'>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	} else if (boqArray[i].serviceType === 'BVPN') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' selected>BVPN</option><option value='CN'>CN</option><option value='Easynet'>Easynet</option><option value='Jambonet'>Jambonet</option><option value='KEN'>KEN</option><option value='Kenstream'>Kenstream</option><option value='KNL'>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	} else if (boqArray[i].serviceType === 'CN') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' selected>CN</option><option value='Easynet'>Easynet</option><option value='Jambonet'>Jambonet</option><option value='KEN'>KEN</option><option value='Kenstream'>Kenstream</option><option value='KNL'>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	} else if (boqArray[i].serviceType === 'Easynet') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' selected>Easynet</option><option value='Jambonet'>Jambonet</option><option value='KEN'>KEN</option><option value='Kenstream'>Kenstream</option><option value='KNL'>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'Jambonet') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' selected>Jambonet</option><option value='KEN'>KEN</option><option value='Kenstream'>Kenstream</option><option value='KNL'>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'KEN') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN' selected >KEN</option><option value='Kenstream'>Kenstream</option><option value='KNL'>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'Kenstream') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' selected>Kenstream</option><option value='KNL'>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'KNL') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' >Kenstream</option><option value='KNL' selected>KNL</option><option value='LNK'>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'LNK') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' >Kenstream</option><option value='KNL' >KNL</option><option value='LNK' selected>LNK</option><option value='MS'>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'MS') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' >Kenstream</option><option value='KNL' >KNL</option><option value='LNK' >LNK</option><option value='MS' selected>MS</option><option value='Rent'>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'Rent') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' >Kenstream</option><option value='KNL' >KNL</option><option value='LNK' >LNK</option><option value='MS' >MS</option><option value='Rent' selected>Rent</option><option value='SAT'>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	else if (boqArray[i].serviceType === 'SAT') {
     	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' >Kenstream</option><option value='KNL' >KNL</option><option value='LNK' >LNK</option><option value='MS' >MS</option><option value='Rent' >Rent</option><option value='SAT' selected>SAT</option><option value='TEL_ISDN'>TEL_ISDN</option>";
     	}
     	 else {
      	    itemRow = itemRow + "<option value='BAN'>BAN</option><option value='BVPN' >BVPN</option><option value='CN' >CN</option><option value='Easynet' >Easynet</option><option value='Jambonet' >Jambonet</option><option value='KEN'  >KEN</option><option value='Kenstream' >Kenstream</option><option value='KNL' >KNL</option><option value='LNK' >LNK</option><option value='MS' >MS</option><option value='Rent' >Rent</option><option value='SAT' >SAT</option><option value='TEL_ISDN' selected>TEL_ISDN</option>";
     	}
     	itemRow = itemRow + "</select></td>";
     	

        itemRow =itemRow + "<td name='billingCode'><input type='text' value='" + boqArray[i].billingCode +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='circuitNo'><input type='text' value='" + boqArray[i].circuitNo +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td name='longitude'><input type='text' value='" + boqArray[i].longitude +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td name='latitude'><input type='text' value='" + boqArray[i].latitude +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td class='hidden-td' name='custServiceID'><input type='text' hidden value='" + boqArray[i].id +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td class='hidden-td' name='customerID'><input type='text' hidden value='" + boqArray[i].customerID +"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td class='hidden-td' name='creationDate'><input type='text' hidden value='" +creationDate+"' class='form-control text-input'></td>";
        itemRow =itemRow + "<td class='hidden-td' name='lastModDate'><input type='text' hidden value='" +lastModfDate+"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='category'><input type='text' hidden value='" + boqArray[i].cat +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='circuitID'><input type='text' hidden value='" + boqArray[i].circuitID +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='refID'><input type='text' hidden value='" + boqArray[i].refID +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='num'><input type='text' hidden value='" + boqArray[i].num +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='mediaType'><input type='text' hidden value='" + boqArray[i].mediaType +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='txMedia'><input type='text' hidden value='" + boqArray[i].txMedia +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='status'><input type='text' hidden value='" + boqArray[i].status +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='capacityMB'><input type='text' hidden value='" + boqArray[i].capacityMB +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='regionID'><input type='text' hidden value='" + boqArray[i].regionID +"'class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='linkName'><input type='text' hidden value='" + boqArray[i].linkName +"'class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='radioID'><input type='text' hidden value='" + boqArray[i].radioID +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='radioName'><input type='text' hidden value='" + boqArray[i].radioName +"' class='form-control text-input' ></td>";
        itemRow =itemRow + "<td class='hidden-td' name='portDesc'><input type='text' hidden value='" + boqArray[i].portDesc +"'class='form-control text-input' ></td>";        
        itemRow = itemRow + "<td name='createSurvey' style='width:150px;'><button type='button' class='createSurvey' onClick='createSurvey(\"" +boqArray[i].id+ "\",\"" +boqArray[i].customerID+ "\",\"" +boqArray[i].longitude+ "\",\"" +boqArray[i].latitude+ "\",\"" +boqArray[i].refID+ "\")' >Create Survey</button></td>";

          itemRow =itemRow + "</tr>";
        $("#customerServicesTable > tbody").append(itemRow);

		
	}	
  }
}

function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function getCustServiceArray() {
	dict = [];
	$("#customerServicesTable > tbody").find('input[name="record"]').each(function(){
		dict.push({
		         "serviceType" : $(this).parent().parent().children('td[name="serviceType"]').children('select').val(),
			     "billingCode" : $(this).parent().parent().children('td[name="billingCode"]').children('input').val(),
			     "circuitNo" : $(this).parent().parent().children('td[name="circuitNo"]').children('input').val(),
			     "longitude" : $(this).parent().parent().children('td[name="longitude"]').children('input').val(),
				 "latitude" :  $(this).parent().parent().children('td[name="latitude"]').children('input').val(),
				 "custServiceID" : $(this).parent().parent().children('td[name="custServiceID"]').children('input').val(),
				 "customerID" : $(this).parent().parent().children('td[name="customerID"]').children('input').val(),
				 "creationDate" : $(this).parent().parent().children('td[name="creationDate"]').children('input').val(),
				 "lastModDate" : $(this).parent().parent().children('td[name="lastModDate"]').children('input').val(),
				 "category" : $(this).parent().parent().children('td[name="category"]').children('input').val(),
				 "circuitID" : $(this).parent().parent().children('td[name="circuitID"]').children('input').val(),
				 "refID" : $(this).parent().parent().children('td[name="refID"]').children('input').val(),
				 "num" : $(this).parent().parent().children('td[name="num"]').children('input').val(),
				 "mediaType" : $(this).parent().parent().children('td[name="mediaType"]').children('input').val(),
				 "txMedia" : $(this).parent().parent().children('td[name="txMedia"]').children('input').val(),
				 "status" : $(this).parent().parent().children('td[name="status"]').children('input').val(),
				 "capacityMB" : $(this).parent().parent().children('td[name="capacityMB"]').children('input').val(),
				 "regionID" : $(this).parent().parent().children('td[name="regionID"]').children('input').val(),
				 "linkName" : $(this).parent().parent().children('td[name="linkName"]').children('input').val(),
				 "radioID" : $(this).parent().parent().children('td[name="radioID"]').children('input').val(),
				 "radioName" : $(this).parent().parent().children('td[name="radioName"]').children('input').val(),
				 "portDesc" : $(this).parent().parent().children('td[name="portDesc"]').children('input').val()				 
			});				 					
     });

    console.log("dit "+dict);
    
}


var map;
var marker;
let markers = [];



function initMap() {
	var lat;
	var lng;
	var kenya=new google.maps.LatLng(0.300 , 37.761);
	if($("#Lat").val()!="" && $("#Lng").val()!="" ){
       lat=parseFloat($("#Lat").val());
       lng=parseFloat($("#Lng").val());
	}else{
		lat = 0.300;
		lng = 37.761;
		}
      const icon = {
      	    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
      	    scaledSize: new google.maps.Size(50, 50), // scaled size

      	};
    	
  // The map, centered on Central Park
  const center = {lat , lng};
  const options = {zoom: 6, scaleControl: true, center: kenya};
  map = new google.maps.Map(
  document.getElementById('mapCont'), options);

  let infoWindow;
  var infowindow1;
  var position1 = center;
  addMarker(position1);

  function addMarker(position) {
	  var customerName = $("#custName").val();
	  var address = $("#loc").val();
	  var contentOfInfoWindow = "<b>Customer Name </b>: "+customerName+"<p><p>"+   "<b>Address</b> : "+address;
	  var ctx = getContextPath();
	   marker  = new google.maps.Marker({
		    position,
		    map,
		    size: new google.maps.Size(36, 50),
		    scaledSize: new google.maps.Size(36, 50),
		 		    
	 	 	icon: {
		 	    url: ctx+'/resources/images/google-maps-client.png',
		 	    scaledSize: new google.maps.Size(50, 50),
		 	    anchorPoint : {x: 0, y: 50, g: true},
	 	    		}
		  });

       infowindow1 = new google.maps.InfoWindow({
          content: contentOfInfoWindow
        });

  	  markers.push(marker);
  	  marker.setMap(map);
  	  infowindow1.open(map,markers[0]);
  	  
  	//set listener to open infoWindow and set the marker
  	  marker.addListener("click", () => {
        if(infoWindow) infoWindow.close();
  	  	infowindow1.close();
		infowindow1.open(map, marker);
	  });
  	}
	

	 $("#mapText").val(kenya);
		  
	  map.addListener("mousemove", (mapsMouseEvent) => {

	    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
	    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));
	    	    
	  });


      map.addListener("click", (mapsMouseEvent) => {
         if(infowindow1) infowindow1.close();
         if(infoWindow) infoWindow.close();
         myLatlng=JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2);
         infoWindow = new google.maps.InfoWindow({
         position: mapsMouseEvent.latLng,
         content:myLatlng,
          });
        
         infoWindow.open(map);
           
  google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {
	     infoWindow.close();
	 	 var Lat,Lng;
	 	
      	setTimeout(function() {
      	    	deleteMarkers();
      	    	Lat = $("#Lat").val();
      	    	Lng = $("#Lng").val();
      
      	 position1 = new google.maps.LatLng(Lat, Lng);

      	addMarker(position1);
      	    }, 1);
		document.getElementById("Lat").value=JSON.parse(myLatlng).lat;
		document.getElementById("Lng").value=JSON.parse(myLatlng).lng;
      			 
 			}));
      });

	// Shows any markers currently in the array.
	function showMarkers() {
	  setMapOnAll(map);
	  
	}

	// Deletes all markers in the array by removing references to them.
	function deleteMarkers() {
		marker.setMap(null);
	    markers = [];
	}
  	

	}




// get all colmns count per row
function count(array){
    var c = 0;
    for(i in array) // in returns key, not object
        if(array[i] != undefined)
            c++;
    return c;
}







$("#sendEmail").on("click", function () {

    $("#popUpDiv").fadeIn();
   
    });

$("#cancelButton").on("click", function () {

	 $("#email").val('');
	 $("#emailTo").val('');
	 $("#ccmail").val('');
	 $("#subject").val('');
	 $("#message").val('');
     $("#popUpDiv").fadeOut();
   
    });

$("#send").on("click", function () {

	if( $("#email").val()=='' || $("#emailTo").val()=='' || $("#subject").val()=='' || $("#message").val()=='' )
		{
		alert("All Fields are required");
		}
	else{
		
		 $("#popUpDiv").fadeOut();
	}
   
    });
/////////////////////////////////////////
if ('${ListCustomer}' == "addNew") {
	 $("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});
}


$("#custstat").change(  function() {

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
})

$("#custCategory").change(  function() {

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
})

	$("#custType").change(  function() {
	
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		
	})


$("#Activatewh").click(  function() {	
	   
	 var Status=$("#custstat").val();
		 $("#custstat").val('Activated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#Deactivatewh").click(  function() {	
	   
	 var Status=$("#custstat").val();
		 $("#custstat").val('Deactivated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
		 $("#Cancelwh").click(  function() {	
	   
	 var Status=$("#custstat").val();
		 $("#custstat").val('Cancelled').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
		 $("#Blockwh").click(  function() {	
	   
	 var Status=$("#custstat").val();
		 $("#custstat").val('Blocked').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})




$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});



$('body').on('click', '#selectAllServ', function  () {

	if ($(this).hasClass('allChecked')) {
			$('input[name="record"]', '#customerServicesTable').prop('checked', false);
		} else {
			$('input[name="record"]', '#customerServicesTable').prop('checked', true);
			}
			$(this).toggleClass('allChecked');
	
})

$('body').on('click', '#selectAllSurv', function  () {

	if ($(this).hasClass('allChecked')) {
			$('input[name="record"]', '#customerSurveysTable').prop('checked', false);
		} else {
			$('input[name="record"]', '#customerSurveysTable').prop('checked', true);
			}
			$(this).toggleClass('allChecked');
	
})

$("#saveButton").click(  function() {
	
	if ($("#custName").val()== '') {
		 alert('Customer Name cannot be NULL');
		 return false;}

     if (($.isNumeric($("#mobile").val()))== false) {
		 alert('Mobile is not numeric');
		 return false;}

	val =$("#dateCustomer").val();
   	val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
        }
	 
	  // validate modified date cannot be null
	 val =$("#lstmodifdate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
        }

 	 var validationCheck = validateCustomerServiceInputs(); //To check invalid input in services
 	 if(validationCheck =="false") {
        getCustServiceArray();
		
			$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/CustomerFormSave",
					dataType : "json",
					data : {
						"status":document.getElementById("custstat").value,
						"customerID" : $("#customerID").val(),
						"custRefId" : $("#custRefId").val(),
						"CustomertName" : $("#custName").val(),
						"AcronymsName" :$("#acronymsName").val(),
						"Mobile" : $("#mobile").val(),
						"Address" : $("#loc").val(),
						"LocationId" : $("#locationId").val(),
						"createdate": $("#dateCustomer").val(),
						"lastModifiedDate": $("#lstmodifdate").val(),
						"email": $("#email").val(),
						"password":$("#password").val(),
				  	    "emailTo": $("#emailTo").val(),
					    "ccmail": $("#ccmail").val(),
					    "subject": $("#subject").val(),
					    "message": $("#message").val(),
					    "Lat": $("#Lat").val(),
					    "Lng": $("#Lng").val(),
					    "RegionId": $("#regionId").val(),
					    "RegionName": $("#regionName").val(),
					    "AreaId": $("#areaId").val(),
					    "AreaName": $("#areaName").val(),
					    "City": $("#city").val(),
					    "PostalAddr": $("#postalAddr").val(),
					    "Nationality": $("#nationality").val(),
					    "Website": $("#website").val(),
					    "CustomerCategory":document.getElementById("custCategory").value,
					    "CustomerType":document.getElementById("custType").value,
					    "TelNumber":$("#telNumber").val(),
					    "dictParameter":dict,
   						"slctDelOrd":slctDelArray
					    
											
				},
					success : function(data) {
						$('#lstmodifdate').val(data.lstmodifdate)

						$('#customerID').val(data.customerID);
						var param ="${pageContext.request.contextPath}/CustomerFormView?customerID="+$("#customerID").val()+"&NavAction=2";
						location.replace(param);
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

 	 }

	});


$("#deleteButton").click(  function() {

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/CustomerDelete",
			dataType : "json",
			data : {
				"customerID" : $("#customerID").val()
			},
			success : function(data) {
				location.replace("${pageContext.request.contextPath}/CustomerListView");
				
			},
			error : function(error) {
		    	location.replace("${pageContext.request.contextPath}/CustomerListView");
			}
		});
	
		
		
	 
 })


	if('${SelectedIndex}' != "addNew"){
					var SelectedIndex = '${SelectedIndex}';
					if('${CustomerCount}' != "addNew"){

						
				var CustomerCount = '${CustomerCount}';
				
				if(($("#customerID").val()) != "" && ($("#customerID").val()) != null){

				if(SelectedIndex === CustomerCount){
					
	        		document.getElementById("btnLast").style.opacity = 0.5;
	        		$("#btnLast").hasClass("disabled");
	        		document.getElementById("btnLast").style.pointerEvents = "none";
	        		
	        		document.getElementById("btnNexta").style.opacity = 0.5;
	        		document.getElementById("btnNexta").style.pointerEvents = "none";

					
					$("#btnNexta").hasClass("disabled");
					
					}else{
						
						if(!$("#btnNexta").hasClass("disabled")){
							
							$("#btnNext").click(function(){
								
								var param ="${pageContext.request.contextPath}/CustomerFormView?customerID="+$("#customerID").val()+"&NavAction=1";

								window.location.href =param;
					
							});
				
						}
						if(!$("#btnLst").hasClass("disabled")){
	        				
	        				$("#btnLst").click(function(){
	        					
								var param ="${pageContext.request.contextPath}/CustomerFormView?customerID="+$("#customerID").val()+"&NavAction=4";
	        					window.location.href =param;
	        		
	        				});
	        	
	        			}
					}
				
				if(SelectedIndex == 1){ //first record in database
					
	        		document.getElementById("btnFirst").style.opacity = 0.5;
	        		$("#btnFirst").hasClass("disabled");
	        		document.getElementById("btnFirst").style.pointerEvents = "none";
	        		
	        		document.getElementById("btnPrva").style.opacity = 0.5;
	        		$("#btnPrva").hasClass("disabled");
	        		document.getElementById("btnPrv").style.pointerEvents = "none";
				
				}else{
					if(!$("#btnPrva").hasClass("disabled")){
						
						$("#btnPrv").click(function(){
							
							var param ="${pageContext.request.contextPath}/CustomerFormView?customerID="+$("#customerID").val()+"&NavAction=0";
							window.location.href =param;
							
						 });
					}
					$("#btnFrst").click(function(){

	        			if(!$("#btnFrst").hasClass("disabled")){
	        					
							var param ="${pageContext.request.contextPath}/CustomerFormView?customerID="+$("#customerID").val()+"&NavAction=3";
	        				window.location.href =param;
	        						
	        				}
	        				 });

				}
				
				}}



					
			}
				$("#label-1").text((SelectedIndex)+"/"+CustomerCount);


		            $("#selectnav").autocomplete({
	    			
	    		    source: function(request, response) {
	    			    
	    		             $.ajax({
	    		                 type: "GET",
	    		                 contentType: "application/json; charset=utf-8",
	    		                 url: '${pageContext.request.contextPath}/GetAllCustomer',
	    		                 data: {
	    								"customer" : $("#selectnav").val(),
	    						 },
	    		                 dataType: "json",
	    		                 success: function (data) {
	    		                     if (data != null) {
	    		                         response(data.ListCustomer);
	    		                     }
	    		                 },
	    		                 error: function(result) {
	    		                     alert("Error");
	    		                 }
	    		             });
	    		         }, minLength:0, maxShowItems: 40, scroll:true,

	    					select: function(event, ui) {
	    						
	    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
	    						
	    						var param ="${pageContext.request.contextPath}/CustomerFormView?customerID="+(ui.item[0])+"&NavAction=2";
	    						window.location.href =param;
	           						
	           						return false;
	           					}
	           				}).autocomplete("instance")._renderItem = function(ul, item) {
	           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
	           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	           	                 item[1] + '</span><br><span class="desc">' +
	           	              	 item[2] + '</span><br><span class="desc">' +
	           	                 item[0] + '</span></div></li>').appendTo(ul);
	           			};
	           					$("#selectnav").focus(function(){
	           		   	        	if (this.value == ""){
	           		   	            	$(this).autocomplete("search");
	           		   	        	}						
	           					});   //// ENd of Autocomplete for Area ID
	    	 	

    


</script>
     <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap">
	</script>
    

</html>