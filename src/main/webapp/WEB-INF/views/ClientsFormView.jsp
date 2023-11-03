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

		
    
     <!--  style used for prrqitem table  -->    
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
								
							<span class="input-group-text" style="color:green">Client ID</span>
							<input type="text" id="clientID"  value="${clientID}" class="form-control text-input"  readonly />
						</div>
					</div>
			</div>
			
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span  class="input-group-text" style="width:210px;">Status</span>
				<select id="ordstat" class="form-control">
								<option value="Draft" <c:if test = "${ordStatus =='Draft'}" > selected </c:if> >Draft</option>
								<option value="Activated" <c:if test = "${ordStatus =='Activated'}" > selected </c:if>>Activated</option>
								<option value="Deactivated" <c:if test = "${ordStatus =='Deactivated'}" > selected </c:if>>Deactivated</option>
								<option value="Cancelled" <c:if test = "${ordStatus =='Cancelled'}" > selected </c:if>>Cancelled</option>
								<option value="Blocked" <c:if test = "${ordStatus =='Blocked'}" > selected </c:if>>Blocked</option>
								</select>
				</div>							
			</div>
			
<!-- 					<div class="pad col-md-3 hide-row"></div> -->
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Client</span>
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
					<input type="text" id="dateClient" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
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
			        			onclick='window.location.href = "${pageContext.request.contextPath}/ClientsListView"'
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
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: auto;">
             <li class="nav-item"><a class="nav-link active"
						id="custom-tabs-one-home-tab" data-toggle="tab"
						href="#custom-tabs-one-home" role="tab"
						aria-controls="custom-tabs-one-home" aria-selected="true"
						style="color: gold;">INFORMATION</a></li>
            
            <li class="nav-item"><a class="nav-link"
						id="custom-tabs-Image-tab" data-toggle="tab" href="#custom-tabs-Image"
						role="tab" aria-controls="#custom-tabs-Image" aria-selected="false"
						style="color: gold;">IMAGE</a></li>
						
			<li class="nav-item">
			<a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-reglog" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">REGISTRATION LOG</a></li> 
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-tkashlog" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">TKASH REG LOG</a></li> 
         
         	 <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-imagelog" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">IMAGE APPROVAL LOG</a></li> 
            
            <li class="nav-item ml-auto">
             
        
       <div class="dropdown" Style="display:inline-block;">
	           <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	            aria-haspopup="true" aria-expanded="true">Actions</button>	
	            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	             <a class="dropdown-item"  type="button" id="Activatewh" >Activate</a>
	             <a class="dropdown-item" type="button" id="Deactivatewh" >Deactivate</a>
	             <a class="dropdown-item" type="button" id="Cancelwh" >Cancel</a>
				 <a class="dropdown-item" type="button" id="Blockwh" >Block</a>
    	    	 <a class="dropdown-item" type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </a>
           	</div> 
       </div>
    	        
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

<div class="tab-pane fade show active" id="custom-tabs-one-home"
	role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
	
	
	
	<p></p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" id="warehouseInfo_tbl">
	<tr>
	<td width=20% valign="top" class="left_col">
			<div class="form-group">
				<div class="input-group-prepend">
						<span class="input-group-text">First Name</span>
						<input type="text" id="firstName"  value="${firstName}" class="form-control text-input"   />
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
      						<span class="input-group-text">Last Name</span>
							<input type="text" id="lastName"  value="${lastName}" class="form-control text-input"   />
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
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Display Name</span>
							<input type="text" id="dspName"  value="${dspName}" class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	</tr>
   	 <tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">ID Number</span>
							<input type="text" id="idNumber"  value="${idNumber}" class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
      						<span class="input-group-text">Department</span>
							<select name="cars" id="dep"  style="width: 150px;  text-align-last:center;"class="form-control text-input"    >
				
								<option value="Enterprise" <c:if test = "${dep =='enter'}" > selected </c:if>  >Enterprise</option>
								<option value="Networks" <c:if test = "${dep =='net'}"> selected </c:if>>Networks</option>
								
							  </select>
    
                       <span class="input-group-text" style="margin-left:5px;">Agent Number</span>
                            <input type="text" id="agentNumber" value="${agentNumber}" class="form-control text-input"  style="width: 198px;"/>	
                    </div>
			</div>
   		</td>
   	</tr>
   		<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
                            <span class="input-group-text">Address</span>
                            <input type="text" id="loc" value="${loc}" class="form-control text-input"  />
                        
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest" >
      					<span class="input-group-text">Latitude</span>
                            <input type="text" id="normLat" value="${normlat}" class="form-control text-input"  style="width: 203px;"/>	
                    
      					<span class="input-group-text" style="margin-left:5px;">Longitude</span>
                            <input type="text" id="normLng" value="${normlng}" class="form-control text-input" style="width: 198px"/>
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest" >
      					<span class="input-group-text">Selling Latitude</span>
                            <input type="text" id="sellat" value="${sellat}" class="form-control text-input" style="width: 155px;" />	
                    
      					<span class="input-group-text" style="margin-left:5px;">Selling Longitude</span>
                            <input type="text" id="sellng" value="${sellng}" class="form-control text-input" style="width: 150px;" />	
                    </div>
			</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
			<div class="form-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">Description</span>
							<textarea name="suppdescrip"  cols="120" rows="5" id="descr" class="form-control text-input">${clientDesc}</textarea>
                        </div>
                    </div>
			</div>
   		</td>
   	</tr>
</table>


	</div>
	
	<div class="tab-pane fade" id="custom-tabs-Image" role="tabpanel"
				aria-labelledby="custom-tabs-Image-tab">

				<p></p>
				<div>

					<form>



				<H1 align="center"> CLIENT Image </H1>
				<H6 align="center" id="noImageClient"> No Image Uploaded </H1>
<div style="text-align: center;">


				<img src="" alt= "My test image" id="clientImage" class="imagesize">
				</div>
				
								<p></p>
								<H1 align="center"> CLIENT FRONT ID </H1>
								<H6 align="center" id="noImageFId"> No Image Uploaded </H1>
				
				
				<div style="text-align: center;">
				
				<img src="" alt="My test image" id="CLIENT_FRONT_ID" class="imagesize">
				
								</div>
				
								<p></p>
								<H1 align="center"> CLIENT BACK ID </H1>
								<H6 align="center"  id="noImageBId"> No Image Uploaded </H1>
				
				<div style="text-align: center;">
				
				<img src="" alt="My test image" id="CLIENT_BACK_ID" class="imagesize">

										</div>
						
						<p></p>
								<H1 align="center"> SIGNATURE </H1>
								<H6 align="center"  id="noSIGNATURE"> No Image Uploaded </H1>
				
				<div style="text-align: center;">
				
				<img src="" alt="My test image" id="SIGNATURE" class="imagesize">

										</div>



					</form>
				</div>

				<p></p>
			</div>
			
			<!-- Registration Log form -->
		<div class="tab-pane fade" id="custom-tabs-one-reglog" role="tabpanel" aria-labelledby="custom-tabs-one-reglog-tab">
		<p></p>
		<div class="row">
		<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Registration Status</span>
							<input type="text" id="regStatus"  value="${regStatus}" readonly class="form-control text-input"   />
						</div>
					</div>
		</div>
		
		
		
		</div>
				

		<div> 
				<form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="regtab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						               
						                <th>Date</th>
						                <th>Status</th>
						                <th>Response Code</th>
						                <th>Response Message</th>
						                <th>Agent Number</th>
						                <th>Agent Name</th>
						                <th>Mobile Number</th>
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
						
                   </form>
		</div>

    </div>
		<!-- TKASH Registration Log form -->
		<div class="tab-pane fade" id="custom-tabs-one-tkashlog" role="tabpanel" aria-labelledby="custom-tabs-one-reglog-tab">
		<p></p>
		<div class="row">
		<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">T-Kash Registration Status</span>
							<input type="text" id="tkashStatus"  value="${tkashStatus}" readonly class="form-control text-input" style=" width:auto;"  />
						</div>
					</div>
		</div>
		
		
		
		</div>
				

		<div> 
				<form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="tkashtab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						               
						                <th>Date</th>
						                <th>Status</th>
						                <th>Response Code</th>
						                <th>Response Message</th>
						                <th>Agent Number</th>
						                <th>Agent Name</th>
						                <th>Mobile Number</th>
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
						
                   </form>
		</div>

    </div>
			
		<!-- Image Approval Log form -->
		<div class="tab-pane fade" id="custom-tabs-one-imagelog" role="tabpanel" aria-labelledby="custom-tabs-one-reglog-tab">
		<p></p>
		<div class="row">
		<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Image Approval Status</span>
							<input type="text" id="approvalStatus"  value="${approvalStatus}" readonly class="form-control text-input" style=" width:auto;"  />
						</div>
					</div>
		</div>
		
		
		
		</div>
				

		<div> 
				<form>
				
								
					    <div class="table-responsive-sm"> 
						    <table id ="imageapprovaltab" class="table table-striped table-bordered table-sm" style="display:block; height:600px; overflow-y: auto;">
						        <thead>
						            <tr>
						               
						                <th>Date</th>
						                <th>Status</th>
						                <th>Reason of Rejection</th>
						                <th>Response Code</th>
						                <th>Response Message</th>
						                <th>Mobile Number</th>
						                <th>Client ID Number</th>
						                <th>Username</th>
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
						
                   </form>
		</div>

    </div>	
			
			
</div>



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
if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}

function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}
var map;
var marker;
let markers = [];

function initMap() {
	var lat;
	var lng;
	var kenya=new google.maps.LatLng(0.300 , 37.761);
	if($("#normLat").val()!="" && $("#normLng").val()!="" ){
       lat=parseFloat($("#normLat").val());
       lng=parseFloat($("#normLng").val());
	}else{
		lat = 0.300;
		lng = 37.761;
		}
      const icon = {
      	    url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
      	    scaledSize: new google.maps.Size(50, 50), // scaled size

      	};
  // The map, centered on Central Park
  const center = {lat , lng };
  const options = {zoom: 6, scaleControl: true, center: kenya};
  map = new google.maps.Map(
  document.getElementById('mapCont'), options);

  let infoWindow;
  var infowindow1;
  var position1 = center;
  addMarker(position1);

  function addMarker(position) {
	  var clientName = $("#firstName").val()+" "+$("#lastName").val();
	  var address = $("#loc").val();
	  var contentOfInfoWindow = "<b>Client Name </b>: "+clientName+"<p><p>"+   "<b>Address</b> : "+address;
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
	 	 var normLatt,normLng;
	 	
      	setTimeout(function() {
      	    	deleteMarkers();
      	    	normLatt = $("#normLat").val();
      	    	normLng = $("#normLng").val();
      
      	 position1 = new google.maps.LatLng(normLatt, normLng);

      	addMarker(position1);
      	    }, 1);
		document.getElementById("normLat").value=JSON.parse(myLatlng).lat;
		document.getElementById("normLng").value=JSON.parse(myLatlng).lng;
      			 
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


if ('${CLIENT_IMAGE}' != "addNew") {

	
	 document.getElementById('noImageClient').style.display = "none";
	 document.getElementById('clientImage').src = "/SimRegPhotos/ClientPic/${CLIENT_IMAGE}.jpg";
}else{
	  document.getElementById('clientImage').style.display = 'none';		 
	 }
if ('${CLIENT_FRONT_ID}' != "addNew") {

	 document.getElementById('noImageFId').style.display = "none";
	 document.getElementById('CLIENT_FRONT_ID').src = "/SimRegPhotos/ClientPic/${CLIENT_FRONT_ID}.jpg";
}else{
	 document.getElementById('CLIENT_FRONT_ID').style.display = 'none';		 
	 }
if ('${CLIENT_BACK_ID}' != "addNew") {

	 document.getElementById('noImageBId').style.display = "none";
	 document.getElementById('CLIENT_BACK_ID').src = "/SimRegPhotos/ClientPic/${CLIENT_BACK_ID}.jpg";
}else{
	 document.getElementById('CLIENT_BACK_ID').style.display = 'none';		 
	 }

if ('${SIGNATURE}' != "addNew") {
	 document.getElementById('noSIGNATURE').style.display = "none";
	 document.getElementById('SIGNATURE').src = "/SimRegPhotos/ClientPic/${SIGNATURE}.jpg";
}else{
	 document.getElementById('SIGNATURE').style.display = 'none';
	 }

// get all colmns count per row
function count(array){
    var c = 0;
    for(i in array) // in returns key, not object
        if(array[i] != undefined)
            c++;
    return c;
}


if('${regStatusLog}' != 'addNew'){
	regStatusLog=${regStatusLog};
	var regRowCount= count(regStatusLog[0]);
	var itemActionRow = "<tr>";
	for(i=0;i<regStatusLog.length;i++){

		for(j=0;j<regRowCount;j++){
			
			
	   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+regStatusLog[i][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
		   	 
			}

			itemActionRow=itemActionRow+"</tr>";
		}
	$("#regtab > tbody").append(itemActionRow)

}

if('${tkashStatusLog}' != 'addNew'){
	tkashStatusLog=${tkashStatusLog};
	var tkashRowCount= count(tkashStatusLog[0]);
	var itemActionRow = "<tr>";
	for(i=0;i<tkashStatusLog.length;i++){

		for(j=0;j<tkashRowCount;j++){
		
			
	   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+tkashStatusLog[i][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
		   	 
			}

			itemActionRow=itemActionRow+"</tr>";
		}
	$("#tkashtab > tbody").append(itemActionRow)

}

if('${imageLog}' != 'addNew'){
	imageLog=${imageLog};
	var imagelogRowCount= count(imageLog[0]);
	var itemActionRow = "<tr>";
	for(i=0;i<imageLog.length;i++){

		for(j=0;j<imagelogRowCount;j++){
		
			
	   		itemActionRow =itemActionRow + "<td style='width:auto'><input  style='width:auto'  type='text' value='"+imageLog[i][j]+"' style='width:auto;' class='form-control input-text' readonly/> </td>";
		   	 
			}

			itemActionRow=itemActionRow+"</tr>";
		}
	$("#imageapprovaltab > tbody").append(itemActionRow)

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
if ('${ListClient}' == "addNew") {
	 $("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});
}


$("#ordstat").click(  function() {

	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	
})


$("#Activatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Activated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#Deactivatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Deactivated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
		 $("#Cancelwh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Cancelled').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
		 $("#Blockwh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Blocked').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})




$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
	});




$("#saveButton").click(  function() {
	
	if ($("#firstName").val()== '') {
		 alert('First Name cannot be NULL');
		 return false;}

    if ($("#lastName").val()== '') {
		 alert('Last Name cannot be NULL');
		 return false;}
     if (($.isNumeric($("#mobile").val()))== false) {
		 alert('Mobile is not numeric');
		 return false;}

	val =$("#dateClient").val();
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
		
			$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/ClientsFormSave",
					dataType : "json",
					data : {
						"status":document.getElementById("ordstat").value,
						"clientID" : $("#clientID").val(),
						"DisplayName" : $("#dspName").val(),
						"FirstName" : $("#firstName").val(),
						"LastName" :$("#lastName").val(),
						"Mobile" : $("#mobile").val(),
						"Department" : $("#dep").val(),
						"Location" : $("#loc").val(),
						"createdate": $("#dateClient").val(),
						"lastModifiedDate": $("#lstmodifdate").val(),
						"clientDesc":$("#descr").val(),
						"email": $("#email").val(),
						"password":$("#password").val(),
				  	    "emailTo": $("#emailTo").val(),
					    "ccmail": $("#ccmail").val(),
					    "subject": $("#subject").val(),
					    "message": $("#message").val(),
					    "normLat": $("#normLat").val(),
					    "normLng": $("#normLng").val(),
					    "agentNumber": $("#agentNumber").val(),
					    "clientIdNumber":$("#idNumber").val(),
											
				},
					success : function(data) {
						$('#lstmodifdate').val(data.lstmodifdate)

						$('#clientID').val(data.clientID);
						var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=2";
						location.replace(param);
					
					},
					error : function(error) {
						console.log("The error is " + error);
					}
				});

	});


$("#deleteButton").click(  function() {

		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ClientsDelete",
			dataType : "json",
			data : {
				"clientID" : $("#clientID").val()
			},
			success : function(data) {
				location.replace("${pageContext.request.contextPath}/ClientsListView");
				
			},
			error : function(error) {
		    	location.replace("${pageContext.request.contextPath}/ClientsListView");
			}
		});
	
		
		
	 
 })


	if('${SelectedIndex}' != "addNew"){
					var SelectedIndex = ${SelectedIndex};
					if('${ClientsCount}' != "addNew"){

						
				var ClientsCount = ${ClientsCount};
				
				if(($("#clientID").val()) != "" && ($("#clientID").val()) != null){

				if(SelectedIndex === ClientsCount){
					
	        		document.getElementById("btnLast").style.opacity = 0.5;
	        		$("#btnLast").hasClass("disabled");
	        		document.getElementById("btnLast").style.pointerEvents = "none";
	        		
	        		document.getElementById("btnNexta").style.opacity = 0.5;
	        		document.getElementById("btnNexta").style.pointerEvents = "none";

					
					$("#btnNexta").hasClass("disabled");
					
					}else{
						
						if(!$("#btnNexta").hasClass("disabled")){
							
							$("#btnNext").click(function(){
								
								var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=1";

								window.location.href =param;
					
							});
				
						}
						if(!$("#btnLst").hasClass("disabled")){
	        				
	        				$("#btnLst").click(function(){
	        					
								var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=4";
	        					window.location.href =param;
	        		
	        				});
	        	
	        			}
					}
				
				if(SelectedIndex === 1){ //first record in database
					
	        		document.getElementById("btnFirst").style.opacity = 0.5;
	        		$("#btnFirst").hasClass("disabled");
	        		document.getElementById("btnFirst").style.pointerEvents = "none";
	        		
	        		document.getElementById("btnPrva").style.opacity = 0.5;
	        		$("#btnPrva").hasClass("disabled");
	        		document.getElementById("btnPrv").style.pointerEvents = "none";
				
				}else{
					if(!$("#btnPrva").hasClass("disabled")){
						
						$("#btnPrv").click(function(){
							
							var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=0";
							window.location.href =param;
							
						 });
					}
					$("#btnFrst").click(function(){

	        			if(!$("#btnFrst").hasClass("disabled")){
	        					
							var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+$("#clientID").val()+"&NavAction=3";
	        				window.location.href =param;
	        						
	        				}
	        				 });

				}
				
				}}



					
			}
				$("#label-1").text((SelectedIndex)+"/"+ClientsCount);


		            $("#selectnav").autocomplete({
	    			
	    		    source: function(request, response) {
	    			    
	    		             $.ajax({
	    		                 type: "GET",
	    		                 contentType: "application/json; charset=utf-8",
	    		                 url: '${pageContext.request.contextPath}/GetAllClients',
	    		                 data: {
	    								"client" : $("#selectnav").val(),
	    						 },
	    		                 dataType: "json",
	    		                 success: function (data) {
	    		                     if (data != null) {
	    		                         response(data.ListClient);
	    		                     }
	    		                 },
	    		                 error: function(result) {
	    		                     alert("Error");
	    		                 }
	    		             });
	    		         }, minLength:0, maxShowItems: 40, scroll:true,

	    					select: function(event, ui) {
	    						
	    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
	    						
	    						var param ="${pageContext.request.contextPath}/ClientsFormView?clientID="+(ui.item[0])+"&NavAction=2";
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
	    	



////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
$("#email").autocomplete({
source: function(request, response) {


$.ajax({
type: "GET",
contentType: "application/json; charset=utf-8",
url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
data: {
	"email" : request.term,
	
},
dataType: "json",
success: function (data) {
 if (data != null) {
     response(data.ListItemEmailAccounts);

 }
},
error: function(result) {
 alert("Error");
}
});

}, minLength:0, maxShowItems: 40, scroll:true,

select: function(event, ui) {

this.value = (ui.item ? ui.item[0]  : '');
password.value = ui.item[1];

return false;

}

}).autocomplete("instance")._renderItem = function(ul, item) {
return $("<li class='each'>")
.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
item[0] + "</span></div>")
.appendTo(ul);
};


$("#email").focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
}						
});
$("#password").focus(function(){
if (this.value == ""){
	$(this).autocomplete("search");
}						
});

//////////////////////////////////////END OF EMAIL AUTOCOMPLETE////////////////////////////////////////		  	

    



</script>
     <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap">
	</script>
    

</html>