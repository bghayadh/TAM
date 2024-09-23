<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Node Port Mapping Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
  	<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
  	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
   <script src="${pageContext.request.contextPath}/resources/js/NodePortMapping/nodePortMapping_BoqPopup.js"></script>
    <style>

    
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
 
     .btn-pop {
	background-color: #C2CBC0 !important;
	border-color: #C2CBC0;
	3
}

.btn-pop:hover {
	color: #fff;
	background-color: #8696A0 !important;
	border-color: #8696A0 !important;
}

				.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					z-index:9999;
					width: 350px;
	        		}

				.dot {
				  height: 17px;
				  width: 17px;
				  background-color: chartreuse;
				  border-radius: 50%;
				  display: inline-block;
				 margin-top:10px; 
				}
				.dotStatus {
				  height: 15px;
				  width: 15px;
				  background-color: orange;
				  border-radius: 50%;
				  display: inline-block;
				  margin-top: 10px;
				  margin-left: 10px;
				  
				}
				.fixed-headerr{
					opacity: 1;
					filter: alpha(opacity=100);
				 	background: #ebf2ef;
				  	position: sticky;
				  	top: 0;
				  	z-index: 15;
				
					}
				
 .hide-row { display:none; }
				
#wrapper {width:100%;display: grid;max-height:100px;grid-gap: 100px;grid-column-gap:10px!important;grid-row-gap:8px!important;display:flex;
			grid-template-rows:1fr 1fr!important;grid-template-columns:1fr 1fr 1fr!important;
  					
					}					
 
#one {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#two {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#three {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#four {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#five {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}

#six {min-width:380px;margin:50px;min-height:260px;border-radius: 10px;padding:10px;}
				
.container{min-width:100%;}	

.divv1{ text-align:center;font-family: cursive;color:#DCF8C6;border-radius: 25px 25px 0px 0px!important; 
 border-style: groove;border-width: 2px;border-color: #4B8C8C; box-shadow: 5px 5px 5px rgb(75,140,140);} 

.divv2{text-align:center;justify-content: center;background-color: lightgray;border-radius: 0px 0px 25px 25px!important;font-size:13px;font-family: cursive;  
border-style: groove;border-width: 2px;border-color: #4B8C8C;box-shadow: 5px 5px 5px rgb(75,140,140);border-top:none;} 	
 	
 tr{height:40px;}	
 table{align:top;margin-left:auto;margin-right:auto;}

 .TD{border-bottom: 1pt solid black;}			
		 
.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 500px;
  overflow : auto;
}
    
.modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
}
  
.display-none{display: none;}
    
button .fa{
        font-size: 16px;
        margin-left: 10px;
}

   
button:focus { outline: none; }

 		
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
} 

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (max-width: 992px) {
  .modal-content, .modal-dialog {
  
  max-height: 100%;
        max-width: 100%;
  
 }
} 

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
 .modal-content, .modal-dialog {
 
 max-height: 100%;
 max-width: 100%;
}
}
  .nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }

</style>
    
</head>
<body>

  <c:set var="pg" value="network" scope="session"  />
<jsp:include page="header.jsp"></jsp:include>
     <!--  end of general head page -->
     <p></p>
<div class="container-fluid">
		<div class="row">
			
			<div class="col-md-3">
	           <div class="form-group">
	               <div class="input-group-prepend">
	                   <span class="input-group-text" style="width: 170px;">Node ID
	                   </span> <input type="text" id="nodeID" readonly value="${node_id}"
	                       class="form-control text-input" />
	               </div>
	           </div>
	       </div>
                    
            <div class="col-md-3">
                <div class="form-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text" style="width: 170px;">Node Name
                        </span> <input type="text" id="nodeName" readonly value="${node_name}"
                            class="form-control text-input" />
                    </div>
                </div>
            </div>
		
		 <div class="pad col-md-3 hide-row"></div>   
		 
		 <div class="col-md-3 nextprvItems">
             <div class="form-group">
                 <div class="input-group-prepend">
                     <span class="input-group-text">Other Node</span>
                     <input type="text" id="selectnav" value="${selectnav}" style="width: 430px"
                         class="form-control text-input" />
                 </div>
             </div>
         </div>
			
							<div  class="col-md-3 text-right"  >
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
						</div> 
			
			</div>

	
			<div class ="row">
			
			
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
							<span style="width:190px;" class="input-group-text">Created Date</span>
							<input type="text" id="ordcreatedate" readonly value="${creation_date}" class="form-control" />
							   <div class="input-group-append">
									
								</div>
					</div>
				</div>
			</div>
	
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
						<span class="input-group-text">Last Modify Date</span>
						<input type="text" id="ordlstmodifdate" readonly value="${update_date}" class="form-control"    />
						  <div class="input-group-append">
								
						</div>
					</div>
				</div>
			</div>
	
	 <div class="hide-row col-md-3 pad "></div> 
		
		<div class=" col-md-3 nextprvItems">
			<label id="label-1" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
			  		<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 51px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 51px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>				  		
					</ul>
				</nav>
		</div>
			
			
			
			
			<div class="col-md-3" >
		 		<div class="btn-group pull-right"  style="text-align: right;">
 					
		 			<div class="glyph" style="text-align: right;" > 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/NodePortMappingListView"'
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
		<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />

	
	</div>		
<p></p>
<div class="container-fluid">

<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: -20px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
       	
            
            
            <li id="Buttons" class="nav-item ml-auto">
            
               <div class="dropdown" Style="display:inline-block;">
	           <button disabled class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	                 aria-haspopup="true" aria-expanded="false">Actions</button>
	
	            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	            <a class="dropdown-item"  type="button" id="Approve">Approve</a>
	             <a class="dropdown-item"  type="button" id="complete">Complete</a>
	             <a class="dropdown-item"  type="button" id="Close" >Close</a>
	             <a class="dropdown-item" type="button" id="Cancel" >Cancel</a>
    	          
    	          </div>
    	         
    	        <button type="button" id="sendEmail" class="btn btn-primary BtnActive">
    	        	<i class="fa fa-envelope"></i> Send Email 
    	        </button>
                
                 <button  type="button" id="saveButton" class="btn btn-primary BtnActive">
                     <i class="fa fa-save"></i> Save
                 </button>  
                 </div>	
                
                        
		
     </ul>
     
</div>
</div>

</div>

<div class="container-fluid">
<div class="tab-content tab-pane fade show active" id="custom-tabs-one-tabContent">
<div role="tabpanel" class="tab-pane fade show active" id="custom-tabs-one-home"  aria-labelledby="custom-tabs-one-home-tab">
<p></p>
  
	<hr>


 	<!--  create port mapping table -->

		<div> 
			<form>		
			    <div class="table-responsive-sm" id="tableContainer"> 
				    <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
				        <thead>
				            <tr id="bisotr" class="fixed-headerr">
				                <th>
				                <div class="container">
				                
						          <button type="button" id="selectAll" class="main">
						          
						          <span class="sub"></span>Select</button>
						         
						          </th>
				                <th>Mac Address</th>
				                <th>Serial Number</th>
				                <th>Port Address</th>
				                <th>Port Status</th>
				                <th>Port Number</th>
				                <th>Record Type</th>
				                <th>Location Type</th>
				                <th>Location ID</th>
				                <th>Location Name</th>
				                 <th>Warehouse ID</th>
				                <th>Cable Type</th>
				                <th>TX Strand Nb</th>
				                <th>TX Strand Color</th>
				                <th>RX Strand Nb</th>
				                <th>RX Strand Color</th>
				                <th>TX Tube Nb</th>
				                <th>TX Tube Color</th>
				                <th>RX Tube Nb</th>
				                <th>RX Tube Color</th>
				                <th>Cable ID</th>
				                <th>Cable Name</th>
				                <th>Cable Length</th>
				                <th>Created Date</th>
				                <th>Last Modified Date</th>
				            </tr>
				        </thead>
				        <tbody>
				            
							
				        </tbody>
				        
				    </table>
			    </div>
				    
			    <input type="text" id="RowIndex" value="" hidden>
				<input type="button" id ="addPortRow" class="add-row" value="Add Row">
			    <button type="button" id ="deletePortRow" class="delete-row">Delete Row</button>
              </form>  
		</div>

<p></p>



<div class="row">

	<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend">
				<span style="width:200px;" class="input-group-text">Total Ports</span>
				<input type="text" id="totalPorts" value="" readonly class="form-control text-input" />
			</div>
		</div>
	</div>
	
	 <div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend">
				<span style="width:200px;" class="input-group-text">Active Ports</span>
				<input type="text" id="activePorts" value="" readonly class="form-control text-input" />
			</div>
		</div>
	 </div>
	
	 
	 <div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:200px;" class="input-group-text">Passive Ports </span>
				<input type="text" id="passivePorts" value="" readonly class="form-control text-input" />
				</div>							
		</div>
	</div>

</div>
        
           	</div>
        </div>
        
<div class="container">
	<div id ="poModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
			<h5 id ="popupNb" class="modal-title" style="font-weight:bold; color: #E9ECEF ;position: relative; bottom: 12px;"></h5>
				<div style="float: right;">
				<button  name="insertBelow"  onclick="insertRowBelow()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -30px; font-weight: bold; margin-top: -7px;"">Insert Below </button>
				<button  name="insertAbove"  onclick="insertRowAbove()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -20px; font-weight: bold; margin-top: -7px;"">Insert Above </button>
				<button  name="deleteBoqRow" onclick="deleteBoqRow()"   class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: -10px; font-weight: bold; margin-top: -7px;"">Delete</button>
				<button  name ="previousRow" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 0px; font-weight: bold; margin-top: -7px;"">Previous</button>
	            <button  name="nextRow" onclick="nextRow()" class ="btn btn-default btn-primary BtnActive btn-pop" style="color:black;position:relative;left: 10px; font-weight: bold; margin-top: -7px;"">Next</button> 
				<button type="button" name="closeModPartPopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
									<a class="close modalMinimize ml-3"> <i
										class='fa fa-minus icon-to-change'></i></a>
				</div>
				</div>
	<div class="modal-body">
		<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
	 		 <li class="nav-item">
	   			 <a class="nav-link active" id="port-tab" style="color: gold;" data-toggle="tab" href="#port" role="tab" aria-controls="port" aria-selected="true">Port Mapping</a>
	  		</li>
		</ul>
	            
	
		<div class="tab-content">
		  <div class="tab-pane active" id="port" role="tabpanel" aria-labelledby="port-tab">
			  <p></p>
			<div class="container-fluid">
				<div class="row">
			  		<div class="col-sm-6">
			  			<div class="form-group">
			  				<div class="input-group-prepend">
			  					<span class="input-group-text" >Port ID </span>
			   					<input type="text" id="popupportMappingID" value="${popupItemCode}" readonly style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
			  				</div>
			  			</div>
			  		</div>
			  		<div class="col-sm-6">
			  			<div class="form-group">
			  				<div class="input-group-prepend">
			  					<span class="input-group-text" >Record Type </span>
			   					<input type="text" id="popupportRecordType" value="${popupItemCode}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
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
								<span class="input-group-text" >MAC Address</span>
								<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupMacAddress" value="${popupItemModel}" style="width:675px; height:37px"  />					
							</div>
						</div>
					</div>
			
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Serial Number</span>
								<input type="text" class="ui-widget ui-widget-content ui-corner-all form-control text-input" id="popupSerialNb" value="${popupItemPart}" style="width:674px; height:37px"  />
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
								<span class="input-group-text" >Port Address</span>
									<input type="text" id="popupPortAddress" class="form-control text-input" value="${popupCat1}" style="width:674px; height:37px" />
							</div>
						</div>
					</div>
					
				<div class="col-sm-6">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" >Port Status</span>
								<input type="text" id="popupPortStatus" class="form-control text-input" value="${popupCat2}" style="width:674px; height:37px" />
										
						</div>
					</div>
				</div>
										
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" >Port Number</span>
							<input type="number" id="popupPortNb" class="form-control text-input" value="${popupCat2}" style="width:674px; height:37px" />
									
					</div>
				</div>
			</div>
			
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Location Type</span>
						 <select id="popupLocType" class="form-control" style="width:674px; height:37px;">
							 <option value=''></option>
							 <option value='Customer'>Customer</option>
							 <option value='Site'>Site</option>	
						</select>	
					</div>
				</div>
			</div>
							
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Location ID</span>
							<input type="text" id="popupLocID" class="form-control text-input" value="${popupCat4}" style="width:674px; height:37px"  />  
									
					</div>
				</div>
			</div>
			
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Location Name</span>
							<input type="text" id="popupLocName" class="form-control text-input" value="${popupSeq}" style="width:674px; height:37px"  />  
									
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Warehouse ID</span>
							<input type="text" id="popupWareID" class="form-control text-input" value="${popupBarcode}" style="width:674px; height:37px"  />  
									
					</div>
				</div>
			</div>
			
			<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">Cable Type</span>
									<select id="popupCableType" class="form-control" style="width:674px; height:37px;">
										 <option value='Outdoor'>Outdoor</option>
										 <option value='Indoor'>Indoor</option>
										 
									</select>			
							</div>
						</div>
					</div>
			
				</div> </div>							
			<div class="container-fluid">
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
							<span class="input-group-text" >TX Strand NB</span>
							<input type="text" id="popupTXStrandNb" class="form-control text-input" value="${popupQty}" style="width:674px; height:37px" />
						</div>
				    </div>
				  </div>
				  
				  <div class="col-sm-6">
				    <div class="form-group">
				        <div class="input-group-prepend">
				            <span class="input-group-text">TX Strand Color</span>
				            <select id="popupTXStrandColor" class="form-control" style="width:674px; height:37px;">
				                <option value="" style="background-color: white;"></option>
				                <option value="blue" style="background-color: white; color:black;">blue</option>
				                <option value="orange" style="background-color: white; color:black;">orange</option>
				                <option value="green" style="background-color: white; color:black;">green</option>
				                <option value="brown" style="background-color: white; color:black;">brown</option>
				                <option value="gray" style="background-color: white; color:black;">gray</option>
				                <option value="white" style="background-color: white; color:black;">white</option>
				                <option value="red" style="background-color: white; color:black;">red</option>
				                <option value="black" style="background-color: white; color:black;">black</option>
				                <option value="yellow" style="background-color: white; color:black;">yellow</option>
				                <option value="violet" style="background-color: white; color:black;">violet</option>
				                <option value="pink" style="background-color: white; color:black;">pink</option>
				                <option value="aqua" style="background-color: white; color:black;">aqua</option>
				            </select>
				        </div>
				    </div>
				</div>
					
			</div> 
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<div class="input-group-prepend">
						<span class="input-group-text" >RX Strand NB</span>
						<input type="text" id="popupRXStrandNb" class="form-control text-input" value="${popupQty}" style="width:674px; height:39px" />
					</div>
			    </div>
			  </div>
							
				<div class="col-sm-6">
					<div class="form-group">
						<div class="input-group-prepend">
							 <span class="input-group-text">RX Strand Color</span>
				            <select id="popupRXStrandColor" class="form-control" style="width:674px; height:37px;">
				                <option value="" style="background-color: white;"></option>
				                <option value="blue" style="background-color: white; color:black;">blue</option>
				                <option value="orange" style="background-color: white; color:black;">orange</option>
				                <option value="green" style="background-color: white; color:black;">green</option>
				                <option value="brown" style="background-color: white; color:black;">brown</option>
				                <option value="gray" style="background-color: white; color:black;">gray</option>
				                <option value="white" style="background-color: white; color:black;">white</option>
				                <option value="red" style="background-color: white; color:black;">red</option>
				                <option value="black" style="background-color: white; color:black;">black</option>
				                <option value="yellow" style="background-color: white; color:black;">yellow</option>
				                <option value="violet" style="background-color: white; color:black;">violet</option>
				                <option value="pink" style="background-color: white; color:black;">pink</option>
				                <option value="aqua" style="background-color: white; color:black;">aqua</option>
				            </select>
								
						</div>
					</div>
				</div>
			</div> 
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
							<span class="input-group-text" >TX Tube NB</span>
							<input type="text" id="popupTXTubeNb" class="form-control text-input" value="${popupQty}" style="width:674px; height:37px" />
						</div>
				    </div>
				  </div>
								
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text" >TX Tube Color</span>
					            <select id="popupTXTubeColor" class="form-control" style="width:674px; height:37px;">
					                <option value="" style="background-color: white;"></option>
					                <option value="blue" style="background-color: white; color:black;">blue</option>
					                <option value="orange" style="background-color: white; color:black;">orange</option>
					                <option value="green" style="background-color: white; color:black;">green</option>
					                <option value="brown" style="background-color: white; color:black;">brown</option>
					                <option value="gray" style="background-color: white; color:black;">gray</option>
					                <option value="white" style="background-color: white; color:black;">white</option>
					                <option value="red" style="background-color: white; color:black;">red</option>
					                <option value="black" style="background-color: white; color:black;">black</option>
					                <option value="yellow" style="background-color: white; color:black;">yellow</option>
					                <option value="violet" style="background-color: white; color:black;">violet</option>
					                <option value="pink" style="background-color: white; color:black;">pink</option>
					                <option value="aqua" style="background-color: white; color:black;">aqua</option>
					            </select>	
							</div>
						</div>
					</div>
				</div> 
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
							<span class="input-group-text" >RX Tube NB</span>
							<input type="text" id="popupRXTubeNb" class="form-control text-input" value="${popupQty}" style="width:674px; height:37px" />
						</div>
				    </div>
				  </div>
								
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text" >RX Tube Color</span>
								<select id="popupRXTubeColor" class="form-control" style="width:674px; height:37px;">
					                <option value="" style="background-color: white;"></option>
					                <option value="blue" style="background-color: white; color:black;">blue</option>
					                <option value="orange" style="background-color: white; color:black;">orange</option>
					                <option value="green" style="background-color: white; color:black;">green</option>
					                <option value="brown" style="background-color: white; color:black;">brown</option>
					                <option value="gray" style="background-color: white; color:black;">gray</option>
					                <option value="white" style="background-color: white; color:black;">white</option>
					                <option value="red" style="background-color: white; color:black;">red</option>
					                <option value="black" style="background-color: white; color:black;">black</option>
					                <option value="yellow" style="background-color: white; color:black;">yellow</option>
					                <option value="violet" style="background-color: white; color:black;">violet</option>
					                <option value="pink" style="background-color: white; color:black;">pink</option>
					                <option value="aqua" style="background-color: white; color:black;">aqua</option>

					            </select>		
							</div>
						</div>
					</div>
				</div> 
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
							<span class="input-group-text" >Cable ID</span>
							<input type="text" id="popupCableID" class="form-control text-input" value="${popupQty}" style="width:674px; height:39px" />
						</div>
				    </div>
				  </div>
								
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text" >Cable Name</span>
								<input type="text" id="popupCableName" class="form-control text-input" value="${popupDiscountAmount}" style="width:674px;" />
									
							</div>
						</div>
					</div>
				</div> 
				<div class="row">
					<div class="col-sm-6">
						<div class="form-group">
							<div class="input-group-prepend">
							<span class="input-group-text" >Cable Length</span>
							<input type="number" id="popupCableLength" class="form-control text-input" value="${popupQty}" style="width:674px; height:37px" />
						</div>
				    </div>
				  </div>
				</div>
				
			</div>
									         
							
		  </div>
	</div>

  
  </div>
					
		<div class="modal-footer">
			
		</div>	
								                
	</div>
			
 		</div>
	</div>
</div>
        

  <div id="popUpDiv" style="display:none;">
	  <div class="sendEmail" style="margin-top:50px;" >
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
						<span class="input-group-text"> Title</span>
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
 var listActivePort = [];	
 var listPassivePort = [];	
var portBoqIndex = 0; 
var portRow ="";
var slctDelPort=[];
var allPortList =[];
	$(document).ready(function() {
		

		$(document).on('change', ':input', function(){
		    unsaved = true;
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
		});
		//console.log("list active "+${listActivePort})
		//console.log("list passive "+${listPassivePort})
		
		 listActivePort = ${ listActivePort };
		 listPassivePort = ${ listPassivePort };
		 console.log("listActivePort length "+ listActivePort.length)
		  console.log("listPassivePort tlength "+ listPassivePort.length)
		 
		 getActiveRecord();
		 console.log("allPortList length "+ allPortList.length)
		 //console.log("allPortList "+ JSON.stringify(allPortList, null, 2))
		 sortAllPortListByPortNb();
		 //console.log("allPortList after sorting  "+ JSON.stringify(allPortList, null, 2))
			//console.log("allPortList length "+ allPortList[0].portNb)
		
		 /* 1- make another case reveres to this
		 	2- make case when no passive 
		 	3- make case when no active  */
				 /*allPortList.push({
				    "port_Mapping_ID" : ,  
				     "MACAddress" : ,
				     "serialNb" : ,
				     "portAddress" : ,
					 "portStatus" :  ,
					 "portNb" : ,
					 "recordType" : ,
			 });*/

		 var locationTypeOptions= 
			 "<option value=''></option>"
			+"<option value='Customer'>Customer</option>"
			+"<option value='Site'>Site</option>";


		var cableTypeOptions =
			"<option value='Outdoor'>Outdoor</option>"
		+"<option value='Indoor'>Indoor</option>";
		
		var colorOptions='<option value='+'""'+' style='+'"background-color: white;"'+'></option>'+	
				'<option value='+'"blue"'+' style='+'"background-color: white; color:black"'+'>blue</option>'+
				'<option value='+'"orange"'+' style='+'"background-color: white; color:black"'+'>orange</option>'+
				'<option value='+'"green"'+' style='+'"background-color: white; color:black"'+'>green</option>'+
				'<option value='+'"brown"'+' style='+'"background-color: white; color:black"'+'>brown</option>'+
				'<option value='+'"gray"'+' style='+'"background-color: white; color:black"'+'>gray</option>'+
				'<option value='+'"white"'+' style='+'"background-color: white; color:black"'+'>white</option>'+
				'<option value='+'"red"'+' style='+'"background-color: white; color:black"'+'>red</option>'+
				'<option value='+'"black"'+' style='+'"background-color: white; color:black"'+'>black</option>'+
				'<option value='+'"yellow"'+' style='+'"background-color: white; color:black"'+'>yellow</option>'+
				'<option value='+'"violet"'+' style='+'"background-color: white; color:black"'+'>violet</option>'+
				'<option value='+'"pink"'+' style='+'"background-color: white; color:black"'+'>pink</option>'+
				'<option value='+'"aqua"'+' style='+'"background-color: white; color:black"'+'>aqua</option>';
				
				var  portMacAddress	;
		     	var  macFieldStatus ;
		     	
	     		var  portSerialNb ;
	     		var  serialNbFieldStatus ;
	     		
	     		var  portAddress ;
	     		var  portAddressFieldStatus ;
	     		
	     		var  portStatus   ; 
	     		var  portStatusFieldStatus ;  
		
		
		 for (i = 0; i < allPortList.length; i++) {

     		      	         
     		  portMacAddress	="";
	     	  macFieldStatus ="";
	     	
     		  portSerialNb ="";
     		  serialNbFieldStatus ="";
     		
     		  portAddress ="";
     		  portAddressFieldStatus ="";
     		
     		  portStatus  ="" ; 
     		  portStatusFieldStatus ="";  

			//mac
     		if (allPortList[i].activeMACAddress !== "" && allPortList[i].activeMACAddress !== null && allPortList[i].activeMACAddress !== "null") {
     			portMacAddress = allPortList[i].activeMACAddress;
     			macFieldStatus="read"
     		
         		} 
     		else{
     			portMacAddress = allPortList[i].passiveMACAddress;
         		} 
			//serial nb
			if (allPortList[i].activeSerialNb !== "" && allPortList[i].activeSerialNb !== null && allPortList[i].activeSerialNb !== "null") {
     			portSerialNb = allPortList[i].activeSerialNb;
     			serialNbFieldStatus="read"
     		
         		} 
     		else{
     			portSerialNb = allPortList[i].passiveSerialNb;
         		} 
     		
     		//port address
     		if (allPortList[i].activePortAddress !== "" && allPortList[i].activePortAddress !== null && allPortList[i].activePortAddress !== "null") {
     			portAddress = allPortList[i].activePortAddress;
     			portAddressFieldStatus="read"
     		
         		} 
     		else{
     			portAddress = allPortList[i].passivePortAddress;
         		} 

     		//port status
     		if (allPortList[i].activePortStatus !== "" && allPortList[i].activePortStatus !== null && allPortList[i].activePortStatus !== "null") {
     			portStatus = allPortList[i].activePortStatus;
     			portStatusFieldStatus="read"
     		
         		} 
     		else{
     			portStatus = allPortList[i].passivePortStatus;
         		} 

     		 
     		   

			 portRow = "<tr id='"+allPortList[i].port_Mapping_ID+"'>"
			     + "<td><input type='checkbox' name='record'><button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			     +"<td name='MACAddress'><input name='MACAddress' value='"+portMacAddress+"' id='MACAddress"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='serialNb'><input name='serialNb' value='"+portSerialNb+"' id='serialNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='portAddress'><input name='portAddress' value='"+portAddress+"' id='portAddress"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='portStatus'><input name='portStatus' value='"+portStatus+"' id='portStatus"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='portNb'><input name='portNb' value='"+allPortList[i].portNb+"' id='portNb"+portBoqIndex+"' class='form-control' type='number' style='width:190px;position:relative;'/></td>"


			     +"<td name='recordType'><input name='recordType' value='"+allPortList[i].recordType+"' id='recordType"+portBoqIndex+"' class='form-control' type='text' readonly style='width:190px;position:relative;'/></td>"
				+"<td name='locationType'>"
				+"<select class='form-control' style='width:190px;position:relative;' name='locationType' id='locationType"+portBoqIndex+"'>"+locationTypeOptions+"</select>"
				    + "</td>"
				
				 +"<td name='locationID'><input name='locationID' value='"+allPortList[i].locationID+"' id='locationID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='locationName'><input name='locationName' value='"+allPortList[i].locationName+"' id='locationName"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='wareID'><input name='wareID' value='"+allPortList[i].wareID+"' id='wareID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"

				 +"<td name='cableType'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='cableType' id='cableType"+portBoqIndex+"'>"+cableTypeOptions+"</select>"
				+ "</td>"
					    
			     +"<td name='txStrandNb'><input name='txStrandNb' value='"+allPortList[i].txStrandNb+"' id='txStrandNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='txStrandColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='txStrandColor' id='txStrandColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				
			     +"<td name='rxStrandNb'><input name='rxStrandNb' value='"+allPortList[i].rxStrandNb+"' id='rxStrandNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='rxStrandColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='rxStrandColor' id='rxStrandColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				
			     +"<td name='txTubeNb'><input name='txTubeNb' value='"+allPortList[i].txTubeNb+"' id='txTubeNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='txTubeColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='txTubeColor' id='txTubeColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				
			     +"<td name='rxTubeNb'><input name='rxTubeNb' value='"+allPortList[i].rxTubeNb+"' id='rxTubeNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='rxTubeColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='rxTubeColor' id='rxTubeColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				 
			     +"<td name='cableID'><input name='cableID' value='"+allPortList[i].cableID+"' id='cableID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='cableName'><input name='cableName' value='"+allPortList[i].cableName+"' id='cableName"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='cableLength'><input name='cableLength' value='"+allPortList[i].cableLength+"' id='cableLength"+portBoqIndex+"' class='form-control' type='number' style='width:190px;position:relative;'/></td>"
			     +"<td name='createdDate'><input name='createdDate' value='"+allPortList[i].createdDate+"' id='createdDate"+portBoqIndex+"' class='form-control'  type='text' readonly style='width:250px;position:relative;'/></td>"
			     +"<td name='lastModifiedDate'><input name='lastModifiedDate' value='"+allPortList[i].lastModifiedDate+"' id='lastModifiedDate"+portBoqIndex+"' class='form-control' type='text' readonly style='width:250px;position:relative;'/></td>"

			     + "</tr>";

			     $("#bisotab > tbody").append(portRow);
			    
			     if(macFieldStatus ==="read"){
			    	 $('#MACAddress' + portBoqIndex).attr('readonly', true);

				     }
			    
			     if(serialNbFieldStatus ==="read"){
			    	 $('#serialNb' + portBoqIndex).attr('readonly', true);

				     }

	     		if(portAddressFieldStatus ==="read"){
			    	 $('#portAddress' + portBoqIndex).attr('readonly', true);

				     }

	     		if(portStatusFieldStatus ==="read"){
			    	 $('#portStatus' + portBoqIndex).attr('readonly', true);

				     }

			     if(allPortList[i].locationType !=null){
						$("#locationType"+portBoqIndex).val(allPortList[i].locationType);
						if(allPortList[i].locationType =='Customer'){
							$('#wareID' + portBoqIndex).attr('readonly', true);
							$('#wareID' + portBoqIndex).val('');
						}
					}

			     

			     if(allPortList[i].txStrandColor !=null){
						$("#txStrandColor"+portBoqIndex).val(allPortList[i].txStrandColor);
					}

			     if(allPortList[i].rxStrandColor !=null){
						$("#rxStrandColor"+portBoqIndex).val(allPortList[i].rxStrandColor);
					}

			     if(allPortList[i].txTubeColor !=null){
						$("#txTubeColor"+portBoqIndex).val(allPortList[i].txTubeColor);
					}

			     if(allPortList[i].rxTubeColor !=null){
						$("#rxTubeColor"+portBoqIndex).val(allPortList[i].rxTubeColor);
					}
					
			     if(allPortList[i].cableType !=null){
						$("#cableType"+portBoqIndex).val(allPortList[i].cableType);
			     }
						
				if(allPortList[i].cableType ==="Indoor" || allPortList[i].cableType ===null || allPortList[i].cableType === "null" || allPortList[i].cableType ===""){
		    		 $('#txStrandNb' + portBoqIndex).attr('readonly', true);
		    		 $('#rxStrandNb' + portBoqIndex).attr('readonly', true);
		    		 $('#txTubeNb' + portBoqIndex).attr('readonly', true);
		    		 $('#rxTubeNb' + portBoqIndex).attr('readonly', true);
		    		 $('#txStrandColor' + portBoqIndex).prop('disabled', true);
		    		 $('#rxStrandColor' + portBoqIndex).prop('disabled', true);
		    		 $('#txTubeColor' + portBoqIndex).prop('disabled', true);
		    		 $('#rxTubeColor' + portBoqIndex).prop('disabled', true);
		    		 $('#cableID' + portBoqIndex).attr('readonly', true);

		    		 $('#txStrandColor'+portBoqIndex).val('');
			    	 $('#txStrandNb'+portBoqIndex).val('');
			    	 $('#rxStrandColor'+portBoqIndex).val('');
			    	 $('#rxStrandNb'+portBoqIndex).val('');

			    	 $('#txTubeColor'+portBoqIndex).val('');
			    	 $('#txTubeNb'+portBoqIndex).val('');
			    	 $('#rxTubeColor'+portBoqIndex).val('');
			    	 $('#rxTubeNb'+portBoqIndex).val('');

			    	 $('#cableID'+portBoqIndex).val('');
		    		
			    	 
			    	 }
					

			     $("#locationType"+portBoqIndex).change(function(){
			    	 var thisID = $(this).attr("id");
					var indexFor = thisID.replace('locationType','');
					if($(this).val()=="Customer"){
						 $('#wareID' + indexFor).attr('readonly', true);
						}
					else {
						 $('#wareID' + indexFor).attr('readonly', false);
						}
			    	 $('#locationID'+indexFor).val('');
			    	 $('#locationName'+indexFor).val('');
			    	 $('#wareID'+indexFor).val('');
			    								 
					});

			     $("#cableType"+portBoqIndex).change(function(){
			    	 var thisID = $(this).attr("id");
					var indexFor = thisID.replace('cableType','');
			    	 $('#txStrandColor'+indexFor).val('');
			    	 $('#txStrandNb'+indexFor).val('');
			    	 $('#rxStrandColor'+indexFor).val('');
			    	 $('#rxStrandNb'+indexFor).val('');

			    	 $('#txTubeColor'+indexFor).val('');
			    	 $('#txTubeNb'+indexFor).val('');
			    	 $('#rxTubeColor'+indexFor).val('');
			    	 $('#rxTubeNb'+indexFor).val('');

			    	 $('#cableID'+indexFor).val('');
			    	 $('#cableName'+indexFor).val('');
			    	 $('#cableLength'+indexFor).val('');
			    	 if($(this).val()==="Indoor" || $(this).val()===null|| $(this).val()==="null"|| $(this).val()===""){
			    		 $('#txStrandNb' + indexFor).attr('readonly', true);
			    		 $('#rxStrandNb' + indexFor).attr('readonly', true);
			    		 $('#txTubeNb' + indexFor).attr('readonly', true);
			    		 $('#rxTubeNb' + indexFor).attr('readonly', true);
			    		 $('#txStrandColor' + indexFor).prop('disabled', true);
			    		 $('#rxStrandColor' + indexFor).prop('disabled', true);
			    		 $('#txTubeColor' + indexFor).prop('disabled', true);
			    		 $('#rxTubeColor' + indexFor).prop('disabled', true);
			    		 $('#cableID' + indexFor).attr('readonly', true);
			    		
				    	 
				    	 }
			    	 if($(this).val()==="Outdoor"){
			    		 $('#txStrandNb' + indexFor).attr('readonly', false);
			    		 $('#rxStrandNb' + indexFor).attr('readonly', false);
			    		 $('#txTubeNb' + indexFor).attr('readonly', false);
			    		 $('#rxTubeNb' + indexFor).attr('readonly', false);
			    		
			    		 $('#txStrandColor' + indexFor).prop('disabled', false);
			    		 $('#rxStrandColor' + indexFor).prop('disabled', false);
			    		 $('#txTubeColor' + indexFor).prop('disabled', false);
			    		 $('#rxTubeColor' + indexFor).prop('disabled', false);
			    		 $('#cableID' + indexFor).attr('readonly', false);
			    		 
				    	 
				    	 }
			    	 
			    								 
					});

			     $("#txStrandColor"+portBoqIndex).change(function(){
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('txStrandColor','');
						colorId="txStrandColor"+indexFor;
						numberId="txStrandNb"+indexFor;
						tubeStrandPortMappingSetColor(colorId,numberId);	 
					});

			     $("#rxStrandColor"+portBoqIndex).change(function(){
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('rxStrandColor','');
						colorId="rxStrandColor"+indexFor;
						numberId="rxStrandNb"+indexFor;
						tubeStrandPortMappingSetColor(colorId,numberId);	 
					});

			     $("#txTubeColor"+portBoqIndex).change(function(){
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('txTubeColor','');
						colorId="txTubeColor"+indexFor;
						numberId="txTubeNb"+indexFor;
						tubeStrandPortMappingSetColor(colorId,numberId);	 
					});


			     $("#rxTubeColor"+portBoqIndex).change(function(){
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('rxTubeColor','');
						colorId="rxTubeColor"+indexFor;
						numberId="rxTubeNb"+indexFor;
						tubeStrandPortMappingSetColor(colorId,numberId);	 
					});

			     document.getElementById("txStrandNb"+portBoqIndex).addEventListener ("input" ,function() {
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('txStrandNb','');
						colorId="txStrandColor"+indexFor;
						numberId="txStrandNb"+indexFor;
						number=document.getElementById(numberId).value;
						strandTubePortMappingSetColor(number,colorId);
					
					});

			     document.getElementById("rxStrandNb"+portBoqIndex).addEventListener ("input" ,function() {
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('rxStrandNb','');
						colorId="rxStrandColor"+indexFor;
						numberId="rxStrandNb"+indexFor;
						number=document.getElementById(numberId).value;
						strandTubePortMappingSetColor(number,colorId);
					
					});

			     document.getElementById("rxTubeNb"+portBoqIndex).addEventListener ("input" ,function() {
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('rxTubeNb','');
						colorId="rxTubeColor"+indexFor;
						numberId="rxTubeNb"+indexFor;
						number=document.getElementById(numberId).value;
						strandTubePortMappingSetColor(number,colorId);
					
					});

			     document.getElementById("txTubeNb"+portBoqIndex).addEventListener ("input" ,function() {
						var thisID = $(this).attr("id");
						//var indexFor = parseInt(thisID.substr(thisID.length-1));
						var indexFor = thisID.replace('txTubeNb','');
						colorId="txTubeColor"+indexFor;
						numberId="txTubeNb"+indexFor;
						number=document.getElementById(numberId).value;
						strandTubePortMappingSetColor(number,colorId);
					
					});


			     tubeStrandPortMappingSetColor("txStrandColor"+portBoqIndex,"txStrandNb"+portBoqIndex);
			     tubeStrandPortMappingSetColor("rxStrandColor"+portBoqIndex,"rxStrandNb"+portBoqIndex);
			     tubeStrandPortMappingSetColor("txTubeColor"+portBoqIndex,"txTubeNb"+portBoqIndex);
			     tubeStrandPortMappingSetColor("rxTubeColor"+portBoqIndex,"rxTubeNb"+portBoqIndex);
			     autoCompleteForPortMapping(portBoqIndex,"bisotab","wareID","locationID","locationName","locationType","cableID","cableName","cableType","txStrandNb","rxStrandNb","txStrandColor","rxStrandColor","txTubeNb","rxTubeNb","txTubeColor","rxTubeColor");
			     
					portBoqIndex++;



/*
				 + "<td name='itemCode'><input type='text' name='itmCode' value='" + boqArray[i].itemCode +":"+ boqArray[i].itemName + "' style='width:300px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>"
				 + "<td name='itemModel'><input name='itmModel' type='text' value='" + itemModel + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>"
				 + "<td name='itemPartNo'><input name='itmPartNo' type='text' value='" + itemPartNumber + "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /></td>"		 			
				 + "<td hidden name='poBarCode'><input type='text' style='width:200px;' hidden value='" + barcode +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='qty'><input name='qty' type='text' style='width:200px;' value='" + boqArray[i].qty +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='rate'><input name='rate' type='text' style='width:200px;' value='" + boqArray[i].rate +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='discountAmount'><input  name='discountAmount' type='text' style='width:200px;' value='" + boqArray[i].discountAmount +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='tax1'><input name='tax1' type='text' style='width:200px;' value='" + boqArray[i].tax1 +"' class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
			     + "<td name='netRate'><input type='text' style='width:200px;' value='" + boqArray[i].netRate +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
			     + "<td name='total'><input type='text' style='width:200px;' value='" + boqArray[i].total +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='totalAt'><input type='text' style='width:200px;' value='"  + boqArray[i].totalAt +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='grpQty'><input type='text' style='width:200px;' value='" + boqArray[i].grQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='prQty'><input type='text' style='width:200px;' value='" + boqArray[i].prQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='arQty'><input type='text' style='width:200px;' value='" + boqArray[i].arQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='cipQty'><input type='text' style='width:200px;' value='" + boqArray[i].cipQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='farQty'><input type='text' style='width:200px;' value='" + boqArray[i].farQty +"' readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='porItemId'><input type='text' style='width:200px;' value='" + boqArray[i].pordItemId +"'readonly class='ui-widget ui-widget-content ui-corner-all form-control text-input'><input name='poItemStatus' type='text' value='" + (boqArray[i].poItemStatus !== null ? boqArray[i].poItemStatus : "0") + "'hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"
				 + "<td name='serialNo'><input type='text'  style='width:200px;' value=" + serialArrays[0] +" hidden class='ui-widget ui-widget-content ui-corner-all form-control text-input'></td>"		
				 */
			    

			 }
		 updateContainerHeight();
		 var activeCounter =0;
		 var passiveCounter =0;
		 $("#bisotab > tbody").find('input[name="record"]').each(function(){
			 var recorddType = $(this).parent().parent().children('td[name="recordType"]').children('input').val();
			 if(recorddType==="active"){
				 activeCounter++
				 }
			 else if(recorddType==="passive"){
				 passiveCounter++
			 }
			 
		});	
		 $("#totalPorts").val(allPortList.length);
		 $("#activePorts").val(activeCounter);
		 $("#passivePorts").val(passiveCounter);

		 
	        
		    //function to  get selected rows for save
			function getSelectedRows () {

				dict = [];
				
				$("#bisotab > tbody").find('input[name="record"]').each(function(){

				
				
					    dict.push({
					    "port_Mapping_ID" : $(this).parent().parent().attr('id'),  
					     "MACAddress" : $(this).parent().parent().children('td[name="MACAddress"]').children('input').val(),
					     "serialNb" : $(this).parent().parent().children('td[name="serialNb"]').children('input').val(),
					     "portAddress" : $(this).parent().parent().children('td[name="portAddress"]').children('input').val(),
						 "portStatus" :  $(this).parent().parent().children('td[name="portStatus"]').children('input').val(),
						 "portNb" : $(this).parent().parent().children('td[name="portNb"]').children('input').val(),
						 "recordType" : $(this).parent().parent().children('td[name="recordType"]').children('input').val(),


						 "locationType":$(this).parent().parent().children('td[name="locationType"]').children('select').val(),
						 "locationID" : $(this).parent().parent().children('td[name="locationID"]').children('input').val(),
						 "locationName" : $(this).parent().parent().children('td[name="locationName"]').children('input').val(),
						 "wareID" : $(this).parent().parent().children('td[name="wareID"]').children('input').val(),

						 "cableType":$(this).parent().parent().children('td[name="cableType"]').children('select').val(),
						 "txStrandNb" : $(this).parent().parent().children('td[name="txStrandNb"]').children('input').val(),
						 "txStrandColor":$(this).parent().parent().children('td[name="txStrandColor"]').children('select').val(),

						 "rxStrandNb" : $(this).parent().parent().children('td[name="rxStrandNb"]').children('input').val(),
						 "rxStrandColor":$(this).parent().parent().children('td[name="rxStrandColor"]').children('select').val(),

						 "txTubeNb" : $(this).parent().parent().children('td[name="txTubeNb"]').children('input').val(),
						 "txTubeColor":$(this).parent().parent().children('td[name="txTubeColor"]').children('select').val(),

						 "rxTubeNb" : $(this).parent().parent().children('td[name="rxTubeNb"]').children('input').val(),
						 "rxTubeColor":$(this).parent().parent().children('td[name="rxTubeColor"]').children('select').val(),

						 "cableID" : $(this).parent().parent().children('td[name="cableID"]').children('input').val(),
						 "cableName" : $(this).parent().parent().children('td[name="cableName"]').children('input').val(),
						 "cableLength" : $(this).parent().parent().children('td[name="cableLength"]').children('input').val(),
						 "creationDate" : $(this).parent().parent().children('td[name="creationDate"]').children('input').val()
						 			 
						 });				 					
	           		  
	            	});
	              
			}  // end of getSelectedRows
			
			var token =  $('input[name="csrfToken"]').attr('value');
			$("#saveButton").click(function () {


				var AlertPortNb="";
				var AlertCableLength="";		
				$("#bisotab > tbody").find('input[name="record"]').each(function(){
				
					var portNb=$(this).parent().parent().children('td[name="portNb"]').children('input').val();
					var cableLength=$(this).parent().parent().children('td[name="cableLength"]').children('input').val();
					
					 if (portNb === '' || !isInteger(portNb)) {
						 AlertPortNb="alertPortNb";
					 	return false; // breaks
					}
					else if (cableLength === '' || !isDecimal(cableLength)) {
						AlertCableLength="alertCableLength";
					 	return false; // breaks
					}
			});
				
				if(AlertPortNb !="alertPortNb" && AlertCableLength !="alertCableLength"){
				getSelectedRows();
				//console.log("dicddt "+dict)
				//console.log(JSON.stringify(dict, null, 2));
			//console.log(JSON.stringify(slctDelPort, null, 2));
				$.ajax({
					type: "POST",
					headers: {
						'X-CSRFToken': token 
					},
					url : "${pageContext.request.contextPath}/NodePortMappingFormViewSave",
					data: {
						    "nodeID"   :$("#nodeID").val(),
							"nodeName" :$("#nodeName").val(),
							"dictParameter":dict,
							"dictParameterDel":slctDelPort,
						   
					},
					dataType: "json",
					success: function (data) {
						
						
						var param = "${pageContext.request.contextPath}/NodePortMappingFormView?NodeID=" + $("#nodeID").val() +"&NavAction=2";
						location.replace(param);
							},
						    error: function (result) {
							alert("Error");
							}
						}); 


			}

				else if(AlertPortNb =="alertPortNb"){
					alert("Port Number must be a Number");
					}
				else if(AlertCableLength =="alertCableLength"){
					alert("Cable Length must be a Number");
					}

				
				});

			$("#addPortRow").on('click',function(){
				var locationTypeOptions= 
					 "<option value=''></option>"
					+"<option value='Customer'>Customer</option>"
					+"<option value='Site'>Site</option>";


				var cableTypeOptions =
				"<option value='Outdoor'>Outdoor</option>"
				+"<option value='Indoor'>Indoor</option>";
				
				var colorOptions='<option value='+'""'+' style='+'"background-color: white;"'+'></option>'+	
						'<option value='+'"blue"'+' style='+'"background-color: white; color:black"'+'>blue</option>'+
						'<option value='+'"orange"'+' style='+'"background-color: white; color:black"'+'>orange</option>'+
						'<option value='+'"green"'+' style='+'"background-color: white; color:black"'+'>green</option>'+
						'<option value='+'"brown"'+' style='+'"background-color: white; color:black"'+'>brown</option>'+
						'<option value='+'"gray"'+' style='+'"background-color: white; color:black"'+'>gray</option>'+
						'<option value='+'"white"'+' style='+'"background-color: white; color:black"'+'>white</option>'+
						'<option value='+'"red"'+' style='+'"background-color: white; color:black"'+'>red</option>'+
						'<option value='+'"black"'+' style='+'"background-color: white; color:black"'+'>black</option>'+
						'<option value='+'"yellow"'+' style='+'"background-color: white; color:black"'+'>yellow</option>'+
						'<option value='+'"violet"'+' style='+'"background-color: white; color:black"'+'>violet</option>'+
						'<option value='+'"pink"'+' style='+'"background-color: white; color:black"'+'>pink</option>'+
						'<option value='+'"aqua"'+' style='+'"background-color: white; color:black"'+'>aqua</option>';
				
				

			 portRow = "<tr>"
			     + "<td><input type='checkbox' name='record'><button type='button'  href='#' name='popUpMenu' onclick='openPop(this)' class='btn btn-default' style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
			     +"<td name='MACAddress'><input name='MACAddress' value='' id='MACAddress"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='serialNb'><input name='serialNb' value='' id='serialNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='portAddress'><input name='portAddress' value='' id='portAddress"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='portStatus'><input name='portStatus' value='' id='portStatus"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='portNb'><input name='portNb' value='' id='portNb"+portBoqIndex+"' class='form-control' type='number' style='width:190px;position:relative;'/></td>"


			     +"<td name='recordType'><input name='recordType' value='passive' id='recordType"+portBoqIndex+"' class='form-control' type='text' readonly style='width:190px;position:relative;'/></td>"
				+"<td name='locationType'>"
				+"<select class='form-control' style='width:190px;position:relative;' name='locationType' id='locationType"+portBoqIndex+"'>"+locationTypeOptions+"</select>"
				    + "</td>"
				
				 +"<td name='locationID'><input name='locationID' value='' id='locationID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='locationName'><input name='locationName' value='' id='locationName"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='wareID'><input name='wareID' value='' id='wareID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"

				 +"<td name='cableType'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='cableType' id='cableType"+portBoqIndex+"'>"+cableTypeOptions+"</select>"
				+ "</td>"
					    
			     +"<td name='txStrandNb'><input name='txStrandNb' value='' id='txStrandNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='txStrandColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='txStrandColor' id='txStrandColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				
			     +"<td name='rxStrandNb'><input name='rxStrandNb' value='' id='rxStrandNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='rxStrandColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='rxStrandColor' id='rxStrandColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				
			     +"<td name='txTubeNb'><input name='txTubeNb' value='' id='txTubeNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='txTubeColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='txTubeColor' id='txTubeColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				
			     +"<td name='rxTubeNb'><input name='rxTubeNb' value='' id='rxTubeNb"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
				 +"<td name='rxTubeColor'>"
					+"<select class='form-control' style='width:190px;position:relative;' name='rxTubeColor' id='rxTubeColor"+portBoqIndex+"'>"+colorOptions+"</select>"
				+ "</td>"
				 
			     +"<td name='cableID'><input name='cableID' value='' id='cableID"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='cableName'><input name='cableName' value='' id='cableName"+portBoqIndex+"' class='form-control' type='text' style='width:190px;position:relative;'/></td>"
			     +"<td name='cableLength'><input name='cableLength' value='' id='cableLength"+portBoqIndex+"' class='form-control' type='number' style='width:190px;position:relative;'/></td>"
			     +"<td name='createdDate'><input name='createdDate' value='' id='createdDate"+portBoqIndex+"' class='form-control'  type='text' readonly style='width:250px;position:relative;'/></td>"
			     +"<td name='lastModifiedDate'><input name='lastModifiedDate' value='' id='lastModifiedDate"+portBoqIndex+"' class='form-control' type='text' readonly style='width:250px;position:relative;'/></td>"

			     + "</tr>";

			     $("#bisotab > tbody").append(portRow);
			     
			     addRowEvents();

			     portBoqIndex++;

	});


			$("#deletePortRow").click(function () {
				 slctDelPort = [];  // Initialize the array

			   
			    $("#bisotab").find('input[name="record"]').each(function () {
			        let portMappingID = $(this).parent().parent().attr('id'); // Get the ID of the parent row
			        
			        if ($(this).is(":checked")) {
			            slctDelPort.push({"portMappingID": portMappingID});
			        }
			    });

				 
			    if (slctDelPort.length === 0) {
			        alert('Select Row(s) to Delete');
			        return false;
			    }

			    
			    $("#bisotab").find('input[name="record"]').each(function () {
			        if ($(this).is(":checked")) {
			            $(this).closest("tr").remove();  // Use closest to find the row and remove it
			        }
			    });
			});

			$('body').on('click', '#selectAll', function  () {
				if ($(this).hasClass('allChecked')) {
					$('input[type="checkbox"]', '#bisotab').prop('checked', false);
				} 
				else {
					$('input[type="checkbox"]', '#bisotab').prop('checked', true);
				}
				
				$(this).toggleClass('allChecked');
									
			});	
	
		
           	
    			     
            if('${SelectedIndex}' != "addNew"){
				var SelectedIndex = ${SelectedIndex};
				if('${nodeCount}' != "addNew"){

					
			var nodeCount = ${nodeCount};
			
			if(($("#nodeID").val()) != "" && ($("#nodeID").val()) != null){

			if(SelectedIndex === nodeCount){
				
        		document.getElementById("btnLast").style.opacity = 0.5;
        		$("#btnLast").hasClass("disabled");
        		document.getElementById("btnLast").style.pointerEvents = "none";
        		
        		document.getElementById("btnNexta").style.opacity = 0.5;
        		document.getElementById("btnNexta").style.pointerEvents = "none";

				
				$("#btnNexta").hasClass("disabled");
				
				}else{
					
					if(!$("#btnNexta").hasClass("disabled")){
						
						$("#btnNext").click(function(){
							
							var param ="${pageContext.request.contextPath}/NodePortMappingFormView?NodeID="+$("#nodeID").val()+"&NavAction=1";

							window.location.href =param;
				
						});
			
					}
					if(!$("#btnLst").hasClass("disabled")){
        				
        				$("#btnLst").click(function(){
        					
							var param ="${pageContext.request.contextPath}/NodePortMappingFormView?NodeID="+$("#nodeID").val()+"&NavAction=4";
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
						
						var param ="${pageContext.request.contextPath}/NodePortMappingFormView?NodeID="+$("#nodeID").val()+"&NavAction=0";
						window.location.href =param;
						
					 });
				}
				$("#btnFrst").click(function(){

        			if(!$("#btnFrst").hasClass("disabled")){
        					
						var param ="${pageContext.request.contextPath}/NodePortMappingFormView?NodeID="+$("#nodeID").val()+"&NavAction=3";
        				window.location.href =param;
        						
        				}
        				 });

			}
			
			}}
		}
			$("#label-1").text((SelectedIndex)+"/"+nodeCount);

			 $("#selectnav").autocomplete({
	        	  source: debounce(function(request, response, event, ui) {
	        	    $.ajax({
	        	      type: "GET",
	        	      contentType: "application/json; charset=utf-8",
	        	      url: '${pageContext.request.contextPath}/GetAllNode',
	        	      data: {
	        	        "Node": $("#selectnav").val(),
	        	      },
	        	      dataType: "json",
	        	      success: function(data) {
	        	        if (data != null) {
	        	          response(data.ListNode);
	        	        }
	        	      },
	        	      error: function(result) {
	        	        alert("Error");
	        	      }
	        	    });
	        	  }, 900),
	        	  minLength: 0,
	        	  maxShowItems: 40,
	        	  scroll: true,
	        	  select: function(event, ui) {
	        	    this.value = ui.item ? ui.item[5] + ":" + ui.item[0] : '';

	        	    var param = '${pageContext.request.contextPath}/NodePortMappingFormView?NodeID=' + ui.item[5]+"&NavAction=2";
	        	    window.location.href = param;
	        	    return false;
	        	  }
	        	}).autocomplete("instance")._renderItem = function(ul, item) {
	           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
	           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
	           	                 item[5] + '</span><br><span class="desc">' +
	           	                 item[0] + '</span></div></li>').appendTo(ul);
	           			};
     					$("#selectnav").focus(function(){
     		   	        	if (this.value == ""){
     		   	            	$(this).autocomplete("search");
     		   	        	}						
     					});


     					function debounce(fn, delay) {
     		      		    var timer;
     		      		    return function() {
     		      		      var args = [].slice.call(arguments);
     		      		      var context = this;
     		      		      if (timer) {
     		      		        window.clearTimeout(timer);
     		      		      }
     		      		      timer = window.setTimeout(function() {
     		      		        fn.apply(context, args);
     		      		      }, delay);
     		      		    };
     		      		  };
/*
     		      		function tubeStrandPortMappingSetColor(colorID,numberID) {

     		      			if (document.getElementById(colorID).value=="blue"){
     		      			   	 document.getElementById(colorID).style.backgroundColor = "blue"; 
     		      			   	 document.getElementById(colorID).style.color = "white";
     		      			     document.getElementById(numberID).value= "1";
     		      			    }
     		      			else if (document.getElementById(colorID).value=="orange"){
     		      			     document.getElementById(colorID).style.backgroundColor = "orange"; 
     		      			     document.getElementById(colorID).style.color = "white";
     		      			     document.getElementById(numberID).value= "2";
     		      			    }
     		      			else if (document.getElementById(colorID).value=="green"){
     		      			   	 document.getElementById(colorID).style.backgroundColor = "green";
     		      			   	 document.getElementById(colorID).style.color = "white";
     		      			   	 document.getElementById(numberID).value= "3";
     		      			   	 }
     		      			 else if(document.getElementById(colorID).value=="brown") {
     		      			   	 document.getElementById(colorID).style.backgroundColor = "brown";
     		      			   	 document.getElementById(colorID).style.color = "white";
     		      			   	 document.getElementById(numberID).value= "4";
     		      			    }
     		      			 else if(document.getElementById(colorID).value=="gray") {
     		      			   	 document.getElementById(colorID).style.backgroundColor = "gray"; 
     		      			   	 document.getElementById(colorID).style.color = "white";
     		      			   	 document.getElementById(numberID).value= "5";
     		      			    }
     		      			 else if(document.getElementById(colorID).value=="white") {
     		      			   	 document.getElementById(colorID).style.backgroundColor = "white"; 
     		      			   	 document.getElementById(colorID).style.color = "black";
     		      			   	 document.getElementById(numberID).value= "6";
     		      			    }	
     		      			 else if(document.getElementById(colorID).value=="red"){
     		      			     document.getElementById(colorID).style.backgroundColor = "red";
     		      				 document.getElementById(colorID).style.color = "white";
     		      				 document.getElementById(numberID).value= "7";
     		      			 }
     		      			else if(document.getElementById(colorID).value=="black") {
     		      			  	 document.getElementById(colorID).style.backgroundColor = "black";
     		      			  	 document.getElementById(colorID).style.color = "white";
     		      			  	 document.getElementById(numberID).value= "8";
     		      			   } 
     		      			else if(document.getElementById(colorID).value=="yellow") {
     		      			  	 document.getElementById(colorID).style.backgroundColor = "yellow";
     		      			  	 document.getElementById(colorID).style.color = "black";
     		      			  	 document.getElementById(numberID).value= "9";
     		      			   } 	
     		      			else if(document.getElementById(colorID).value=="violet") {
     		      				 document.getElementById(colorID).style.backgroundColor = "violet"; 
     		      				 document.getElementById(colorID).style.color = "white";
     		      				 document.getElementById(numberID).value= "10";
     		      			  }         
     		      			else if(document.getElementById(colorID).value=="pink") {
     		      				 document.getElementById(colorID).style.backgroundColor = "pink";
     		      				 document.getElementById(colorID).style.color = "black";
     		      				 document.getElementById(numberID).value= "11";
     		      			  }
     		      			else if(document.getElementById(colorID).value=="aqua") {
     		      				 document.getElementById(colorID).style.backgroundColor = "aqua";
     		      				 document.getElementById(colorID).style.color = "black";
     		      				 document.getElementById(numberID).value= "12";
     		      			 }
     		      		}

     		      		function strandTubePortMappingSetColor(strandTubeNumber,ID) {
     		      			
     		      		 if (strandTubeNumber=="1"){
     		      			 document.getElementById(ID).value = "blue";
     		      		   	 document.getElementById(ID).style.backgroundColor = "blue"; 
     		      		   	 document.getElementById(ID).style.color = "white";
     		      		 }
     		      		 else if (strandTubeNumber=="2"){
     		      			 document.getElementById(ID).value ="orange";
     		      		     document.getElementById(ID).style.backgroundColor = "orange"; 
     		      		     document.getElementById(ID).style.color = "white";	  
     		      		 }
     		      		 else if (strandTubeNumber=="3"){
     		      			 document.getElementById(ID).value ="green";
     		      		   	 document.getElementById(ID).style.backgroundColor = "green";
     		      		   	 document.getElementById(ID).style.color = "white";
     		      		 }
     		      		 else if (strandTubeNumber=="4"){
     		      			 document.getElementById(ID).value ="brown";
     		      		   	 document.getElementById(ID).style.backgroundColor = "brown";
     		      		   	 document.getElementById(ID).style.color = "white";
     		      		 }
     		      		 else if (strandTubeNumber=="5"){
     		      			 document.getElementById(ID).value ="gray";
     		      		   	 document.getElementById(ID).style.backgroundColor = "gray"; 
     		      		   	 document.getElementById(ID).style.color = "white";  
     		      		 }
     		      		 else if (strandTubeNumber=="6"){
     		      			 document.getElementById(ID).value ="white";
     		      		   	 document.getElementById(ID).style.backgroundColor = "white"; 
     		      		   	 document.getElementById(ID).style.color = "black";
     		      		 }
     		      		 else if (strandTubeNumber=="7"){
     		      			 document.getElementById(ID).value ="red";
     		      	         document.getElementById(ID).style.backgroundColor = "red";
     		      	    	 document.getElementById(ID).style.color = "white";
     		      		 }
     		      		 else if (strandTubeNumber=="8"){
     		      			 document.getElementById(ID).value ="black";
     		      	      	 document.getElementById(ID).style.backgroundColor = "black";
     		      	      	 document.getElementById(ID).style.color = "white";
     		      		 }
     		      		 else if (strandTubeNumber=="9"){
     		      			 document.getElementById(ID).value ="yellow";
     		      	      	 document.getElementById(ID).style.backgroundColor = "yellow";
     		      	      	 document.getElementById(ID).style.color = "black";
     		      		 }
     		      		 else if (strandTubeNumber=="10"){
     		      			 document.getElementById(ID).value ="violet";
     		      	   		 document.getElementById(ID).style.backgroundColor = "violet"; 
     		      	   		 document.getElementById(ID).style.color = "white";
     		      		 }
     		      		 else if (strandTubeNumber=="11"){
     		      			 document.getElementById(ID).value ="pink";
     		      	   		 document.getElementById(ID).style.backgroundColor = "pink";
     		      	   		 document.getElementById(ID).style.color = "black"; 	
     		      		 }
     		      		 else if (strandTubeNumber=="12"){
     		      			 document.getElementById(ID).value ="aqua";
     		      	  		 document.getElementById(ID).style.backgroundColor = "aqua";
     		      	  		 document.getElementById(ID).style.color = "black";
     		      		 }
     		      		 else if (strandTubeNumber >12 || strandTubeNumber==""){
     		      			 document.getElementById(ID).value ="";
     		      	  		 document.getElementById(ID).style.backgroundColor = "";
     		      	  		 document.getElementById(ID).style.color = "";	
     		      		 }			 

     		      	} */

     		      	 function updateContainerHeight() {
     				       // Get the table and container elements
     				       var table = document.getElementById("bisotab");
     				       //console.log(table.offsetHeight);
     				       
     				       var container = document.getElementById("tableContainer");
     				     // console.log(container.offsetHeight);
     				       var tr = document.getElementById("bisotr");
     				       //console.log(tr.offsetHeight);
     				      // console.log(listActivePort.length);
     				       
     				       table.style.height = table.offsetHeight + (tr.offsetHeight * (allPortList.length)) + "px";
     				      // console.log("new height : "+table.offsetHeight)
     				     }
					
     		      	function isInteger(str) {
     		      	  return /^\+?(0|[1-9]\d*)$/.test(str);
     		      	}

     		      	function isDecimal(str) {
  					  // Regular expression to match positive and negative floating-point numbers
  					  return /^-?\+?\d*\.?\d+$/.test(str);
  					}
/*					//optimized getActiveRecord using hashmap   
     		      	function getActiveRecord() {
     		      	    // Create hash maps for quick lookups
     		      	    const activeMapByMAC = new Map();
     		      	    const activeMapBySerial = new Map();
     		      	    const activeMapByAddress = new Map();
     		      	    const passiveMapByMAC = new Map();
     		      	    const passiveMapBySerial = new Map();
     		      	    const passiveMapByAddress = new Map();

     		      	    // Populate hash maps with listActivePort records
     		      	    for (const record of listActivePort) {
     		      	        const [mac, serial, address] = [record[0], record[1], record[2]];
     		      	        if (!activeMapByMAC.has(mac)) activeMapByMAC.set(mac, []);
     		      	        if (!activeMapBySerial.has(serial)) activeMapBySerial.set(serial, []);
     		      	        if (!activeMapByAddress.has(address)) activeMapByAddress.set(address, []);

     		      	        activeMapByMAC.get(mac).push(record);
     		      	        activeMapBySerial.get(serial).push(record);
     		      	        activeMapByAddress.get(address).push(record);
     		      	    }

     		      	    // Populate hash maps with listPassivePort records
     		      	    for (const record of listPassivePort) {
     		      	        const [mac, serial, address] = [record[1], record[2], record[3]];
     		      	        if (!passiveMapByMAC.has(mac)) passiveMapByMAC.set(mac, []);
     		      	        if (!passiveMapBySerial.has(serial)) passiveMapBySerial.set(serial, []);
     		      	        if (!passiveMapByAddress.has(address)) passiveMapByAddress.set(address, []);

     		      	        passiveMapByMAC.get(mac).push(record);
     		      	        passiveMapBySerial.get(serial).push(record);
     		      	        passiveMapByAddress.get(address).push(record);
     		      	    }

     		      	    // Array to store the results
     		      	    const allPortList = [];

     		      	    // Match records from listActivePort with listPassivePort
     		      	    for (const activeRecord of listActivePort) {
     		      	        const [activeMAC, activeSerial, activeAddress] = [activeRecord[0], activeRecord[1], activeRecord[2]];
     		      	        let matchFound = false;

     		      	        // Check for match in passiveMapByMAC
     		      	        if (passiveMapByMAC.has(activeMAC)) {
     		      	            for (const passiveRecord of passiveMapByMAC.get(activeMAC)) {
     		      	                matchActiveRecord(activeRecord, passiveRecord, "activePassive");
     		      	                matchFound = true;
     		      	                break; // Assuming only one match is needed
     		      	            }
     		      	        }

     		      	        // Check for match in passiveMapBySerial if not found by MAC
     		      	        if (!matchFound && passiveMapBySerial.has(activeSerial)) {
     		      	            for (const passiveRecord of passiveMapBySerial.get(activeSerial)) {
     		      	                matchActiveRecord(activeRecord, passiveRecord, "activePassive");
     		      	                matchFound = true;
     		      	                break; // Assuming only one match is needed
     		      	            }
     		      	        }

     		      	        // Check for match in passiveMapByAddress if not found by MAC or Serial
     		      	        if (!matchFound && passiveMapByAddress.has(activeAddress)) {
     		      	            for (const passiveRecord of passiveMapByAddress.get(activeAddress)) {
     		      	                matchActiveRecord(activeRecord, passiveRecord, "activePassive");
     		      	                matchFound = true;
     		      	                break; // Assuming only one match is needed
     		      	            }
     		      	        }

     		      	        if (!matchFound) {
     		      	            matchActiveRecord(activeRecord, [], "noPassive");
     		      	        }
     		      	    }

     		      	    // Handle unmatched passive records
     		      	    for (const passiveRecord of listPassivePort) {
     		      	        const [passiveMAC, passiveSerial, passiveAddress] = [passiveRecord[1], passiveRecord[2], passiveRecord[3]];
     		      	        let matchFound = false;

     		      	        if (activeMapByMAC.has(passiveMAC) || activeMapBySerial.has(passiveSerial) || activeMapByAddress.has(passiveAddress)) {
     		      	            matchFound = true;
     		      	        }

     		      	        if (!matchFound) {
     		      	            matchActiveRecord([], passiveRecord, "noActive");
     		      	        }
     		      	    }

     		      	    // Return or use the result array
     		      	    return allPortList;
     		      	}*/

     		      	function getActiveRecord() {
     		      	    if (listActivePort.length > listPassivePort.length && listPassivePort.length != 0) {
     		      	        console.log("In condition 11");

     		      	        // Iterate over active ports
     		      	        for (var i = 0; i < listActivePort.length; i++) {
     		      	            var matchFound = false; // Flag to indicate if a match is found

     		      	            // Iterate over passive ports
     		      	            for (var j = 0; j < listPassivePort.length; j++) {
     		      	                // Check for match by MAC address
     		      	                if (listActivePort[i][0] === listPassivePort[j][1] && listActivePort[i][0]!=null && listActivePort[i][0]!="null" && listActivePort[i][0]!="" && listActivePort[i][0]!="0") {
     		      	                	console.log("case mac1")
     		      	                    matchActiveRecord(listActivePort[i], listPassivePort[j], "activePassive");
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }

     		      	                // Check for match by Serial Number
     		      	                if (!matchFound && listActivePort[i][1] === listPassivePort[j][2] && listActivePort[i][1]!=null && listActivePort[i][1]!="null" && listActivePort[i][1]!="" && listActivePort[i][1]!="0") {
     		      	                	console.log("case SN1")
     		      	                    matchActiveRecord(listActivePort[i], listPassivePort[j], "activePassive");
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }

     		      	                // Check for match by Port Address
     		      	                if (!matchFound && listActivePort[i][2] === listPassivePort[j][3] && listActivePort[i][2]!=null && listActivePort[i][2]!="null" && listActivePort[i][2]!="" && listActivePort[i][2]!="0") {
     		      	                	console.log("case portaddress1")
     		      	                    matchActiveRecord(listActivePort[i], listPassivePort[j], "activePassive");
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }
     		      	            }

     		      	            // If no match is found after checking all passive ports
     		      	            if (!matchFound) {
     		      	            	console.log("case no active match with passive case 5")
     		      	                matchActiveRecord(listActivePort[i], [], "noPassive");
     		      	            }
     		      	        }

     		      	        // Handle records in passive list that were not matched
     		      	        for (var j = 0; j < listPassivePort.length; j++) {
     		      	            var matchFound = false; // Flag to indicate if a match is found

     		      	            for (var i = 0; i < listActivePort.length; i++) {
     		      	                if ((listActivePort[i][0] === listPassivePort[j][1] && listActivePort[i][0]!=null && listActivePort[i][0]!="null" && listActivePort[i][0]!="" && listActivePort[i][0]!="0") ||
     		      	                    (listActivePort[i][1] === listPassivePort[j][2] && listActivePort[i][1]!=null && listActivePort[i][1]!="null" && listActivePort[i][1]!="" && listActivePort[i][1]!="0")||
     		      	                    (listActivePort[i][2] === listPassivePort[j][3] && listActivePort[i][2]!=null && listActivePort[i][2]!="null" && listActivePort[i][2]!="" && listActivePort[i][2]!="0")) {
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }
     		      	            }

     		      	            // If no match is found after checking all active ports
     		      	            if (!matchFound) {
     		      	            	console.log("case no passive match with active 1")
     		      	                matchActiveRecord([], listPassivePort[j], "noActive");
     		      	            }
     		      	        }
     		      	    } else if (listPassivePort.length >= listActivePort.length && listActivePort.length != 0) {
     		      	        console.log("In condition 22");

     		      	        // Iterate over passive ports
     		      	        for (var i = 0; i < listPassivePort.length; i++) {
     		      	            var matchFound = false; // Flag to indicate if a match is found

     		      	            // Iterate over active ports
     		      	            for (var j = 0; j < listActivePort.length; j++) {
     		      	                // Check for match by MAC address
     		      	                if (listActivePort[j][0] === listPassivePort[i][1] && listActivePort[j][0]!=null && listActivePort[j][0]!="null" && listActivePort[j][0]!="" && listActivePort[j][0]!="0") {
     		      	                	console.log("case 1")
     		      	                    matchActiveRecord(listActivePort[j], listPassivePort[i], "activePassive");
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }

     		      	                // Check for match by Serial Number
     		      	                if (!matchFound && listActivePort[j][1] === listPassivePort[i][2] && listActivePort[j][1]!=null && listActivePort[j][1]!="null" && listActivePort[j][1]!="" && listActivePort[j][1]!="0") {
     		      	                	console.log("case 2")
     		      	                    matchActiveRecord(listActivePort[j], listPassivePort[i], "activePassive");
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }

     		      	                // Check for match by Port Address
     		      	                if (!matchFound && listActivePort[j][2] === listPassivePort[i][3] && listActivePort[j][2]!=null && listActivePort[j][2]!="null" && listActivePort[j][2]!="" && listActivePort[j][2]!="0") {
     		      	                	console.log("case 3")
     		      	                    matchActiveRecord(listActivePort[j], listPassivePort[i], "activePassive");
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }
     		      	            }

     		      	            // If no match is found after checking all active ports
     		      	            if (!matchFound) {
     		      	            	console.log("case 4")
     		      	                matchActiveRecord([], listPassivePort[i], "noActive");
     		      	            }
     		      	        }

     		      	        // Handle records in active list that were not matched
     		      	        for (var j = 0; j < listActivePort.length; j++) {
     		      	            var matchFound = false; // Flag to indicate if a match is found

     		      	            for (var i = 0; i < listPassivePort.length; i++) {
     		      	                if ((listActivePort[j][0] === listPassivePort[i][1] && listActivePort[j][0]!=null && listActivePort[j][0]!="null" && listActivePort[j][0]!="" && listActivePort[j][0]!="0")||
     		      	                    (listActivePort[j][1] === listPassivePort[i][2] && listActivePort[j][1]!=null && listActivePort[j][1]!="null" && listActivePort[j][1]!="" && listActivePort[j][1]!="0")||
     		      	                    (listActivePort[j][2] === listPassivePort[i][3] && listActivePort[j][2]!=null && listActivePort[j][2]!="null" && listActivePort[j][2]!="" && listActivePort[j][2]!="0")) {
     		      	                    matchFound = true; // Set flag to true when match is found
     		      	                    break; // Exit the inner loop
     		      	                }
     		      	            }

     		      	            // If no match is found after checking all passive ports
     		      	            if (!matchFound) {
     		      	            	console.log("case 5")
     		      	                matchActiveRecord(listActivePort[j], [], "noPassive");
     		      	            }
     		      	        }
     		      	    } else if (listActivePort.length > 0 && listPassivePort.length === 0) {
     		      	        console.log("No passive records");
     		      	        for (var i = 0; i < listActivePort.length; i++) {
     		      	            matchActiveRecord(listActivePort[i], [], "noPassive");
     		      	        }
     		      	    } else if (listPassivePort.length > 0 && listActivePort.length === 0) {
     		      	        console.log("No active records");
     		      	        for (var i = 0; i < listPassivePort.length; i++) {
     		      	            matchActiveRecord([], listPassivePort[i], "noActive");
     		      	        }
     		      	    }
     		      	}

     		      	function matchActiveRecord(activeRow, passiveRow, condition) {
     		      	 //console.log("activeRow "+ JSON.stringify(allPortList))
     		      	 console.log("activeRow "+ JSON.stringify(activeRow))
     		      	  console.log("passiveRow "+ JSON.stringify(passiveRow))
     		      	   console.log("condition "+ condition)
     		      	    if (condition === "activePassive") {
     		      	        allPortList.push({
     		      	            "port_Mapping_ID": passiveRow[0],
     		      	            "activeMACAddress": activeRow[0],
     		      	            "activeSerialNb": activeRow[1],
     		      	            "activePortAddress": activeRow[2],
     		      	            "activePortStatus": activeRow[3],
     		      	            "passiveMACAddress": passiveRow[1],
     		      	            "passiveSerialNb": passiveRow[2],
     		      	            "passivePortAddress": passiveRow[3],
     		      	            "passivePortStatus": passiveRow[4],
     		      	            "portNb": passiveRow[5],
     		      	            "recordType": "active",
     		      	            "locationType": passiveRow[7],
     		      	            "locationID": passiveRow[8],
     		      	            "locationName": passiveRow[9],
     		      	            "wareID": passiveRow[10],
     		      	            "cableID": passiveRow[11],
     		      	            "cableName": passiveRow[12],
     		      	            "txStrandNb": passiveRow[13],
     		      	            "txStrandColor": passiveRow[17],
     		      	            "rxStrandNb": passiveRow[15],
     		      	            "rxStrandColor": passiveRow[19],
     		      	            "txTubeNb": passiveRow[14],
     		      	            "txTubeColor": passiveRow[18],
     		      	            "rxTubeNb": passiveRow[16],
     		      	            "rxTubeColor": passiveRow[20],
     		      	            "cableLength": passiveRow[21],
     		      	            "cableType": passiveRow[22],
     		      	         	"createdDate": passiveRow[23],
     		      	     		"lastModifiedDate": passiveRow[24]
     		      	        });
     		      	    } else if (condition === "noPassive") {
     		      	        allPortList.push({
     		      	            "port_Mapping_ID": "",
     		      	            "activeMACAddress": activeRow[0],
     		      	            "activeSerialNb": activeRow[1],
     		      	            "activePortAddress": activeRow[2],
     		      	            "activePortStatus": activeRow[3],
     		      	            "passiveMACAddress": "",
     		      	            "passiveSerialNb": "",
     		      	            "passivePortAddress": "",
     		      	            "passivePortStatus": "",
     		      	            "portNb": "",
     		      	            "recordType": "active",
     		      	            "locationType": "",
     		      	            "locationID": "",
     		      	            "locationName": "",
     		      	            "wareID": "",
     		      	            "cableID": "",
     		      	            "cableName": "",
     		      	            "txStrandNb": "",
     		      	            "txStrandColor": "",
     		      	            "rxStrandNb": "",
     		      	            "rxStrandColor": "",
     		      	            "txTubeNb": "",
     		      	            "txTubeColor": "",
     		      	            "rxTubeNb": "",
     		      	            "rxTubeColor": "",
     		      	            "cableLength": "",
     		      	            "cableType": "",
     		      	         "createdDate":"",
  		      	     		"lastModifiedDate": ""
     		      	        });
     		      	    } else if (condition === "noActive") {
     		      	        allPortList.push({
     		      	            "port_Mapping_ID": passiveRow[0],
     		      	            "activeMACAddress": "",
     		      	            "activeSerialNb": "",
     		      	            "activePortAddress": "",
     		      	            "activePortStatus": "",
     		      	            "passiveMACAddress": passiveRow[1],
     		      	            "passiveSerialNb": passiveRow[2],
     		      	            "passivePortAddress": passiveRow[3],
     		      	            "passivePortStatus": passiveRow[4],
     		      	            "portNb": passiveRow[5],
     		      	            "recordType": "passive",
     		      	            "locationType": passiveRow[7],
     		      	            "locationID": passiveRow[8],
     		      	            "locationName": passiveRow[9],
     		      	            "wareID": passiveRow[10],
     		      	            "cableID": passiveRow[11],
     		      	            "cableName": passiveRow[12],
     		      	            "txStrandNb": passiveRow[13],
     		      	            "txStrandColor": passiveRow[17],
     		      	            "rxStrandNb": passiveRow[15],
     		      	            "rxStrandColor": passiveRow[19],
     		      	            "txTubeNb": passiveRow[14],
     		      	            "txTubeColor": passiveRow[18],
     		      	            "rxTubeNb": passiveRow[16],
     		      	            "rxTubeColor": passiveRow[20],
     		      	            "cableLength": passiveRow[21],
     		      	            "cableType": passiveRow[22],
     		      	         	"createdDate": passiveRow[23],
  		      	     			"lastModifiedDate": passiveRow[24]
     		      	        });
     		      	    }
     		      	}

		function sortAllPortListByPortNb() {
		    allPortList.sort((a, b) => {
		        // Convert the port numbers to numbers for correct numerical comparison
		        const portNbA = parseInt(a.portNb, 10);
		        const portNbB = parseInt(b.portNb, 10);
		
		        // If either portNb is NaN, handle it accordingly
		        if (isNaN(portNbA) && isNaN(portNbB)) return 0; // Both are NaN, so they're equal
		        if (isNaN(portNbA)) return 1; // NaN should come after a valid number
		        if (isNaN(portNbB)) return -1; // Valid number should come before NaN
		
		        // Compare the two port numbers
		        return portNbA - portNbB;
		    });
		}

	


     		      		  
					
		 					
}); //end ready document

</script>
 </html>
      						