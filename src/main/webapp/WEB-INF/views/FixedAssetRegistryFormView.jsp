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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	
	<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->

	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	
	
	
<!--
	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	 
	<script src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script>
	
	
	-->
	
			
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	
	
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
    
    	      <script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/js/FixedAssetRegistry/FAR_BoqPopup.js"></script>  
    
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
				.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					z-index:9999;
					width: 250px;
	        		}
    
     th{text-align:center;} 
	td {text-align: center;vertical-align: middle!important;}
    .input-group-text {
				cursor: pointer;
				
				}
				
    .hide-row { display:none; }
    
				select
				{
					min-height: 38px;
				}
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
				
				.fixed-headerr{
					opacity: 1;
					filter: alpha(opacity=100);
				 	background: #ebf2ef;
				  	position: sticky;
				  	top: 0;
				  	z-index: 15;
				
					}
				
				
       	/*		.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
				/*	overflow-x: both; /* add padding to account for vertical scrollbar */
				/*	padding-right: 100px;
					z-index:9999;
	        		}*/
	 .modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
    }
    
          .tree li {
                list-style-type: none;
                margin: 0;
                padding: 10px 5px 0 5px;
                position: relative
            }

            .tree li::before,
            .tree li::after {
                content: '';
                left: -20px;
                position: absolute;
                right: auto
            }

            .tree li::before {
                border-left: 1px solid #999;
                bottom: 50px;
                height: 100%;
                top: 0;
                width: 1px
            }

            .tree li::after {
                border-top: 1px solid #999;
                height: 20px;
                top: 25px;
                width: 25px
            }

            .tree li span {
                -moz-border-radius: 5px;
                -webkit-border-radius: 5px;
                /* border: 1px solid #999; */
                border-radius: 5px;
                display: inline-block;
                padding: 3px 8px;
                text-decoration: none;
                cursor: pointer
            }

            .tree li.parent_li>span {
                cursor: pointer
            }

            .tree>ul>li::before,
            .tree>ul>li::after {
                border: 0
            }

            .tree li:last-child::before {
                /* height: 30px */
            }

            .tree li.parent_li>span:hover,
            .tree li.parent_li>span:hover+ul li span {
                /* background: #eee; */
                /* border: 1px solid #94a0b4; */
                /* color: #000 */
            }

            .tree-component {
                background: #eee;
                padding: 4px;
                text-decoration: none;
                color: #474747;
            }

            .fa-folder,
            .fa-folder-open,
            .fa-circle {
                color: grey;
            }

            .tree li ul>li {
                display: none;
            }


            .*,
            *::before,
            *::after {
                box-sizing: border-box;
            }

            #cat-tree {
                color: #595959;
                font-family: "Roboto", sans-serif;
                font-size: 16px;
                font-weight: 300;
                line-height: 1.5;
            }
    
.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 500px;
  overflow : auto;
}
  

.display-none{display: none;}
    
    

button .fa{
  font-size: 16px;
  margin-left: 10px;
  }

    .btn-tree {
                color: #474747;
                background-color: #d8d8d8;
            }

button:focus { outline: none; }

	#Disappear, #Maintain, #RunIt 
	{
		cursor: pointer;
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
    
	        		 
 	</style>
            
</head>
<body style="background: #f1f4f7">

  
  <c:set var="pg" value="inventory" scope="session"  />
<jsp:include page="header.jsp"></jsp:include>

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
							<span class="input-group-text" style="color:green">Fixed Asset Register</span>
							<input type="text" id="farcode" readonly  value="${farID}" class="form-control text-input"  />
														
						</div>

					</div>
			</div>
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:190px;" class="input-group-text">Status</span>
				<select id="farstat" class="form-control">
								<option value="inprog" <c:if test = "${farStatus =='inprog'}" > selected </c:if> >In Progress</option>
								<option value="dismantled" <c:if test = "${farStatus =='dismantled'}" > selected </c:if>>Dismantled</option>								
								<option value="disappear" <c:if test = "${farStatus =='Disappear'}" > selected </c:if>>Disappeared</option>
								<option value="maintenance" <c:if test = "${farStatus =='Maintenance'}" > selected </c:if>>Maintenance</option>
								<option value="running" <c:if test = "${farStatus =='Running'}" > selected </c:if>>Running</option></select>

				</div>							
			</div>
			<div class="pad col-md-3 hide-row"></div>
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other FAR</span>
						<input type="text" id="selectnav" value="${selectnav}"
						 style="width: 430px" class="form-control text-input" />	
				</div>
			</div>
		</div>
		
		<div  class="col-md-3 text-right"  >
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
		</div> 
		<div class="hide-row col-md-3 pad "></div>
		
		
					
		</div>
		
		<div class="row">
		
		
	<div class="col-md-3">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
					<input type="text" id="createdate" readonly value="${farcreatedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${farlastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
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
							class="btn btn-success next">&raquo;</a></li>			  		</ul>
				</nav>
		</div>
	
	
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/FixedAssetRegistryListView"'
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
<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">DESCRIPTION</a></li>
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-itemPartnum-tab" data-toggle="tab"
            href="#custom-tabs-partNum" role="tab"
            aria-controls="#custom-tabs-partNum" aria-selected="false" style="color: gold;font-size:14px;">MODELS & PART NO</a></li>
            
            <li class="nav-item"><a class="nav-link"
				id="custom-tabs-serialNumber-tab" data-toggle="tab"
				href="#custom-tabs-serialNumber" role="tab"
				aria-controls="#custom-tabs-serialNumber" aria-selected="false" style="color: gold;font-size:14px;">SERIAL NO</a></li>
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-profile-tab" data-toggle="tab"
            href="#custom-tabs-one-profile" role="tab"
            aria-controls="custom-tabs-one-profile" aria-selected="false" style="color: gold;">MEASURMENT</a></li>
             
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-messages" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">TYPE</a></li> 
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-site" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">SITE</a></li> 
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-node" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">NODE</a></li> 
            
                       
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-finance" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;" onClick='myFunction()'>FINANCE</a></li> 
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-messages-tab" data-toggle="tab"
            href="#custom-tabs-one-relatedorders" role="tab"
            aria-controls="#custom-tabs-one-messages" aria-selected="false" style="color: gold;">RELATED ORDERS</a></li> 
       
            
            <li class="nav-item ml-auto">
                                    	<div class="dropdown" Style="display:inline-block;">
	           		<button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	                 aria-haspopup="true" aria-expanded="false">Actions</button>
	
	            	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    	          		<a class="dropdown-item" id="Disappear">Disappear</a>
    	          		<a class="dropdown-item" id="Maintain">Maintain</a>
    	          		<a class="dropdown-item" id="RunIt">Run It</a>
    	          		
    	        	</div>   
    	        	  <button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
                          
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
    
<div class="row">
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Item</span>
				<input type="text" id="itmcode" name="itmcode" value="${faritemCode}" style="width:700px;" class="form-control text-input"/>
			</div>
		</div>
	</div>
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
				<span class="input-group-text">Item Category</span>
				<input type="text" id="itmcat" value="${itemCategoryDetails}" readonly class="form-control text-input"/>
				<input type="text" id="itmcatid" value="${itemCatID}" hidden />
				<input type="text" id="itmrootcat" value="${itemRootCat}" hidden  class="form-control text-input" />
			</div>
		</div>
	</div>
</div>


 <div class="row">
	<div class="col-md-4">
			<div class="input-group-prepend">
			<span class="input-group-text">Item Name Register</span>
			<input type="text" id="itmRegName" value="${itemRegisterName}" class="form-control text-input" />
</div>

				
		</div>
	<div class="col-md-4">
	<div class="form-group">
		<div class="input-group-prepend">
			<span class="input-group-text">Item Image</span>
			<input type="text" id="itmimage" value="${faritemImage}" class="form-control text-input" />
		</div>	
		</div>	
	</div>
		<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Position</span>
					<input type="text" id="itemPosition" value="${faritemPosition}" class="form-control text-input" />
			</div>
</div>
				
		</div>
	</div>
	<div class="row"></div>
	<p></p>
	<div class="row">
	
	<div class="col-md-6">
		<div class="input-group-prepend">
			<span class="input-group-text">Item Description</span>
		</div>							
	</div>
	
</div>
<div class="row">
	<div class="col-md-6">
		<div class="input-group-prepend">
			<textarea name="itmdescrip" readonly="readonly" cols="220" rows="8" id="itmdescrip"> ${itemDesc} </textarea>
		</div>
	</div>
	
	
	<div class="col-md-6">
	</div>
</div>

</div>

<div class="tab-pane fade" id="custom-tabs-serialNumber" role="tabpanel" aria-labelledby="custom-tabs-serialNumber-tab">
	<p></p>
	<div>
		<form>
				
								
			<div class="table-responsive-sm"> 
			  <table id ="SerialNumberTab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
						     <thead>
						         <tr class="fixed-headerr">
						            <th>
								        <button type="button" id="SelectAllSerialNumber" class="main" style="width:80px;">
								        <span class="sub"></span>Select</button></th>
						                <th width="220px">Serial Number</th>
						                <th width="220px">Model</th>
										<th width="220px">Part Number</th>
										<th width="100px">Node Id</th>
										<th width="100px">Node Name</th>
										<th width="100px">Site</th>
										<th width="100px">Position</th>
										<th width="100px">ID</th>
										
										
						            </tr>
						        </thead>
								
								<tbody>
						            
									
						        </tbody>
						      
						    </table>
						    <p id="my_error" style="color:red;"></p>
					    </div>
<input type="text" id="RIndex" value="" hidden>
<input type="button" class="addserial-row" id="add_roww1" onclick="addSerialrows('addRow')" value="Add Row">
<button type="button" class="delete-row1">Delete Row</button>
                   </form>
</div>
</div>

<div class="tab-pane fade" id="custom-tabs-partNum" role="tabpanel" aria-labelledby="custom-tabs-itemPartnum-tab">

<p></p>


<div> 
  
		<form>
				
								
			<div class="table-responsive-sm"> 
			  <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
						     <thead>
						         <tr class="fixed-headerr">
						            <th>
						           
								        <button type="button" id="selectAll" class="main" style="width:80px;">
								        <span class="sub"></span>Select</button></th>
						                <th width="220px">Item Model</th>
						                <th width="220px">Item Part Number</th>
										<th width="220px">Quantity</th>
										<th width="220px">ID</th>
										<th width="100px">Primary</th>
						               
								      
								     

						            </tr>
						        </thead>
								
								<tbody>
						            
									
						        </tbody>
						      
						    </table>
						    <p id="my_error" style="color:red;"></p>
					    </div>
 <input type="text" id="RowIndex" value="" hidden>
<input type="button" class="addmodpartnb-row" onclick="addModelPartrow('addRow')" id="add_roww" value="Add Row">
<button type="button" class="delete-row">Delete Row</button>
                   </form>
		</div>



<p></p>
</div>
	<div class="tab-pane fade" id="custom-tabs-one-profile"
		role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab">
	
<p></p>



<div class="row">

	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Unit</span>
						<input type="text" id="unt" readonly value="${itemUnit}"  class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight</span>
						<input type="text" id="weit" readonly="readonly" value="${itemWeight}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight UOM</span>
						<input type="text" id="weituom" readonly="readonly" value="${itemWeightUOM}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Length</span>
						<input type="text" id="len" readonly="readonly" value="${itemLength}" class="form-control text-input"  />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Width</span>
					<input type="text" id="widths" readonly="readonly" value="${itemWidth}" class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="input-group-prepend">
					<span class="input-group-text">Height</span>
				<input type="text" id="heit" readonly="readonly" value="${itemheight}" class="form-control text-input" />
				</div>							
	</div>
	
	<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Size UOM</span>
					<input type="text" id="siseuom" readonly="readonly" value="${itemSizeUOM}" class="form-control text-input"  />
			</div>

				
		</div>
	
</div>
	</div>

	<div class="tab-pane fade" id="custom-tabs-one-messages"
		role="tabpanel" aria-labelledby="custom-tabs-one-messages-tab">

<p></p>

<div class="row">
	<!--<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Domain</span>
				<span id="domainSpan">
				<!--  <input type="text" id="domaine" value="${fardomain}" class="form-control text-input" /> -->
				<!-- <select name="cars" id="domaine">
    				<option value="mad" <c:if test = "${itemDomain =='mad'}"> selected </c:if>>Mobile Access Domain</option>
    				<option value="itd" <c:if test = "${itemDomain =='itd'}"> selected </c:if>>Transport Domain</option>
    				<option value="icd" <c:if test = "${itemDomain =='icd'}"> selected </c:if>>Core Domain</option>
    				<option value="ipd" <c:if test = "${itemDomain =='ipd'}"> selected </c:if>>Passive Domain</option>
  				</select>-->
  				<!--</span>
			</div>

				
		</div> -->
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Cable Type</span>
						<input type="text" id="cbltype" readonly="readonly" value="${itemCableType}" class="form-control text-input" />
						</div>
					</div>
	</div>


	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Type</span>
					<input type="text" id="itmtyp" readonly="readonly" value="${itemType}" class="form-control text-input"/>
				</div>
			</div>
	</div>
</div>
	<div class="row">
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Domain</span>
					<select id="farDomain" aria-label="Default select example" class="form-control">
						<option value="Transmission" <c:if test = "${farDomain =='Transmission'}" > selected </c:if> >Transmission</option>
						<option value="RAN" <c:if test = "${farDomain =='RAN'}" > selected </c:if>>RAN</option>								
						<option value="Core" <c:if test = "${farDomain =='Core'}" > selected </c:if>>Core</option>								
						<option value="Enterprise" <c:if test = "${farDomain =='Enterprise'}" > selected </c:if>>Enterprise</option>								
					</select>
					</div>
			</div>
	</div>
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Sub Domain</span>
					<select id="farSubDomain" aria-label="Default select example" class="form-control">
						<option value="" <c:if test = "${farSubDomain ==''}" > selected </c:if> ></option>
						<option value="IP" <c:if test = "${farSubDomain =='IP'}" > selected </c:if>>IP</option>								
						<option value="FiberOptic" <c:if test = "${farSubDomain =='FiberOptic'}" > selected </c:if>>Fiber Optic</option>								
						<option value="MicrowaveLink" <c:if test = "${farSubDomain =='MicrowaveLink'}" > selected </c:if>>Microwave Link</option>								
					</select>
					</div>
			</div>
	</div>
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Vendor</span>
					<select id="farVendor" aria-label="Default select example" class="form-control">
						<option value="Nokia" <c:if test = "${farVendor =='Nokia'}" > selected </c:if> >NOKIA</option>
						<option value="Huawei" <c:if test = "${farVendor =='Huawei'}" > selected </c:if>>Huawei</option>								
						<option value="zte" <c:if test = "${farVendor =='zte'}" > selected </c:if>>ZTE</option>								
						<option value="Ericsson" <c:if test = "${farVendor =='Ericsson'}" > selected </c:if>>Ericsson</option>								
						<option value="Tejas" <c:if test = "${farVendor =='Tejas'}" > selected </c:if>>Tejas</option>								
						<option value="Alcatel" <c:if test = "${farVendor =='Alcatel'}" > selected </c:if>>Alcatel</option>								
						<option value="Nec" <c:if test = "${farVendor =='Nec'}" > selected </c:if>>NEC</option>								
					</select>
					</div>
			</div>
	</div>
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Type</span>
					<select id="farType" aria-label="Default select example" class="form-control">
						<option value="" <c:if test = "${farType ==''}" > selected </c:if> ></option>
						<option value="DWDM" <c:if test = "${farType =='DWDM'}" > selected </c:if>>DWDM</option>								
						<option value="SDH" <c:if test = "${farType =='SDH'}" > selected </c:if>>SDH</option>								
					</select>
					</div>
			</div>
	</div>
	
	
	
	
	</div>
	<div class="row">
		<div class="col-md-12">
				<div class="form-group">
					<div class="checkboxes">
					  	<span>	 
		              		 <input type="checkbox" disabled="disabled" id="service1" ${sService} />Service
		              		 <input type="checkbox" disabled="disabled" id="disables" ${sDisabled}/> Disabled
	   	              		 <input type="checkbox" disabled="disabled" id="techg2g" ${sTech2G}/> 2G
							 <input type="checkbox" disabled="disabled" id="techg3g" ${sTech3G}/> 3G
		              		 <input type="checkbox" disabled="disabled" id="techg4g" ${sTech4G}/> 4G
		              		 <input type="checkbox" disabled="disabled" id="techg5g" ${sTech5G}/> 5G
	              		 </span>
					</div>
				</div>
		</div>
	</div>

</div>

<div class="tab-pane fade" id="custom-tabs-one-site" role="tabpanel" aria-labelledby="custom-tabs-one-site-tab">
     <p></p>
       <div> 
  
		<form>			
	
            <div class="table-responsive-sm"> 
			  <table id ="sitetab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
						     <thead>
						         <tr class="fixed-headerr">
						            <th>
						            
								        <button type="button" id="selectAllSite" class="main" style="width: 80px;">
								        <span class="sub"></span>Select</button></th>
								        <th width="220px">Warehouse ID</th>
						                <th width="220px">Site ID</th>
						                <th width="220px">Site Name</th>
						                <th width="220px">ID</th>
						               
										

						            </tr>
						        </thead>
								
								<tbody>
						            
									
						        </tbody>
						      
						    </table>
						    <p id="my_error" style="color:red;"></p>
					    </div>
<input type="text" id="RowIndex" value="" hidden>
					    
<input type="button" class="addsite-row" onclick="addSiterows('addRow')" id="adds_roww" value="Add Row">
<button type="button" class="deletesite-row"> Delete Row</button>
                   </form>
		</div>    
    
    </div> 

	<div class="tab-pane fade" id="custom-tabs-one-node" role="tabpanel" aria-labelledby="custom-tabs-one-node-tab">
     <p></p>


       <div> 
  
		<form>
								
	
            <div class="table-responsive-sm"> 
			  <table id ="ahmadtab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
						     <thead>
						         <tr class="fixed-headerr">
						            <th>
						            
								        <button type="button" id="selectsAll" class="main" style="width: 80px;">
								        <span class="sub"></span>Select</button></th>
						                <th width="220px">Node ID</th>
						                <th width="220px">Node Name</th>
						                <th width="220px">ID</th>
										

						            </tr>
						        </thead>
								
								<tbody>
						            
									
						        </tbody>
						      
						    </table>
						    <p id="my_error" style="color:red;"></p>
					    </div>
					     <input type="text" id="RowIndex" value="" hidden>
<input type="button" class="addnode-row" id="adds_roww" onclick="addNoderows('addRow')" value="Add Row">
<button type="button" class="deletes-row"> Delete Row</button>
                   </form>
		</div>    
    
    </div>    
	    
    
   
    
    <div class="tab-pane fade" id="custom-tabs-one-finance" role="tabpanel" aria-labelledby="custom-tabs-one-cell-tab">
		<p></p>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Initial Cost</span>
						<input type="text" id="initialCost" value="${initialCost}" class="form-control text-input" />
						<!-- to use readonly add : readonly="readonly"-->
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Net Cost</span>
						<input type="text" id="netCost"  value="${netCost}" class="form-control text-input" />
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Depreciation %</span>
						<input type="text"  id="depPercentage" value="${depPercentage}" class="form-control text-input" />
					</div>
				</div>
			 </div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Daily Depreciation Amount</span>
						<input type="text" id="dailyDepAmount" value="${dailyDepAmount}" class="form-control text-input" />
					</div>
				</div>
			 </div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Accumulated Depreciation Amount</span>
						<input type="text" id="accumDepAmount" value="${accumDepAmount}" class="form-control text-input" />
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Salvage Value</span>
						<input type="text" id="salvageValue" value="${salvageValue}" class="form-control text-input" />
					</div>
				</div>
			 </div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Accumulated Depreciation Code</span>
						<input type="text" id="accumDepCode" value="${accumDepCode}" class="form-control text-input" />
					</div>
				</div>
			 </div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Depreciation Code</span>
						<input type="text" id="DepCode" value="${DepCode}" class="form-control text-input" />
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Useful Life Months</span>
						<input type="text" id="usefulLifemonths"  value="${usefulLifeMonth}" class="form-control text-input" />
					</div>
				</div>
			 </div>
		</div>
		
		<div class="row">
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Scrap Status</span>
						<input type="text" id="scrapStatus" value="${scrapStatus}" class="form-control text-input" />
					</div>				
				</div>
			 </div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker4" data-target-input="nearest">
							<span class="input-group-text">Scrap Date</span>
								<input type="text" id="scrapDate" value="${scrapDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
							<div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker"></div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
						<span class="input-group-text">End Of Life</span>
						<input type="text" id="endoflive" value="${itemEndofLife}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
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
						<span class="input-group-text">Ownership</span>
						<input type="text" id="ownership" value="${ownership}" class="form-control text-input" />
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Valuation Rate</span>
						<input type="text" id="valuarate" value="${itemValuationRate}" class="form-control text-input"  />
					</div>
				</div>
			</div>
			<div class="col-md-4">			
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">
 Period</span>
						<input type="text" id="warrantyper" value="${itemWarrantyPer}" class="form-control text-input"  />
					</div>
				</div>
			</div>			
		</div>
    </div>
    
    <div class="tab-pane fade" id="custom-tabs-one-relatedorders" role="tabpanel" aria-labelledby="custom-tabs-one-relatedorders-tab">
			<p></p>
		<div class="row">
		<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Purchase Order ID</span>
							<input type="text" id="ordcode"  value="${ID}" readonly class="form-control text-input"   />
						</div>
					</div>
		</div>
		
		<div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Supplier ID</span>
				<input type="text" id="ordtotqty" value="${supplierID}" readonly class="form-control text-input" />
				</div>							
		</div>
		
		<div class="col-md-3">
				<div class="input-group-prepend">
				<span class="input-group-text">Supplier Name</span>
				<input type="text" id="ordtotqty" value="${supplierName}" readonly class="form-control text-input" />
				</div>							
		</div>
		
		
		
		</div>
				<!--  create table from purchaseOrderItem    -->

		<div> 
				<form>		
					    <div class="table-responsive-sm"> 
						    <table id ="tbRelatOrd" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						               
						                <th>Item</th>
						                <th>Qty</th>
						                <th>Rate</th>
						                <th>Discount Amount</th>
						                <th>Tax</th>
						                <th>Net Rate</th>
						                <th>Total</th>
						                <th>Total AT</th>
						                <th>GR</th>
						                <th>PR</th>
						                <th>AR</th>
						                <th>FAR</th>
						                
						                <th>PrOrditm</th>
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

<!-- Node Popup -->

<div class="container">
	<div id ="nodeModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #FF4F4F;" >
				<h5 id ="popupNbNode" class="modal-title" style="font-weight:bold; color: #3C1596;"></h5>
				
				<button type="button" name="insertNodeBelow"  onclick="insertNodeRowBelow()" class ="btn btn-default btn-primary BtnActive  " style="color:white;position:relative;left:50px;">Insert Below </button>
				<button type="button" name="insertNodeAbove"  onclick="insertNodeRowAbove()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:60px;">Insert Above </button>
			    <button type="button" name="deleteNodeBoqRow"  onclick="deleteNodeBoqRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:70px;">Delete</button>
			    <button name ="previousNodeRow" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:80px;">Previous</button>
	            <button name="nextNodeRow" onclick="nextNodeRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:90px;">Next</button>
				
				<button type="button" name="closeNodePopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="node-tab" style="color: gold;" data-toggle="tab" href="#node" role="tab" aria-controls="node" aria-selected="true">NODE</a>
  		</li>
	</ul>
	
<div class="tab-content">
  <div class="tab-pane active" id="node" role="tabpanel" aria-labelledby="node-tab">
  <p></p>

  <div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Node ID </span>
   					<input type="text" id="popupNodeId" value="${popupNodeId}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  </div>
</div>
<p></p>
	<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Node Name </span>
   					<input type="text" id="popupNodeName" value="${popupNodeName}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
	

  </div>
</div>
 <p></p>

  <div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >ID </span>
   					<input type="text" id="popupNodeSeqId" value="${popupNodeSeqId}" readonly style="width:180px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  </div>
</div>
  </div>
</div>
  </div>
					
<div class="modal-footer">
	<!-- <button name ="previousRowNode" class ="btn btn-default">Previous</button>
	<button name="nextRowNode" class ="btn btn-default">Next</button> -->
</div>	
								                
	</div>
</div></div></div>

<!--End Node popup -->

<!-- Site Popup -->
<div class="container">
	<div id ="siteModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #FF4F4F;" >
				<h5 id ="popupNbSite" class="modal-title" style="font-weight:bold; color: #3C1596;"></h5>
				
				<button type="button" name="insertSiteBelow"  onclick="insertSiteRowBelow()" class ="btn btn-default btn-primary BtnActive  " style="color:white;position:relative;left:50px;">Insert Below </button>
				<button type="button" name="insertSiteAbove"  onclick="insertSiteRowAbove()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:60px;">Insert Above </button>
			    <button type="button" name="deleteSiteBoqRow"  onclick="deleteSiteBoqRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:70px;">Delete</button>
			    <button name ="previousSiteRow" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:80px;">Previous</button>
	            <button name="nextSiteRow" onclick="nextSiteRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:90px;">Next</button>
				
				<button type="button" name="closeSitePopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="site-tab" style="color: gold;" data-toggle="tab" href="#site" role="tab" aria-controls="site" aria-selected="true">SITE</a>
  		</li>
	</ul>
	<div class="tab-content">
  <div class="tab-pane active" id="site" role="tabpanel" aria-labelledby="site-tab">
  <p></p>
<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Warehouse ID </span>
   					<input type="text" id="popupWareId" value="${popupWareId}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  
	</div>
</div>
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Site ID</span>
					<input type="text" id="popupSiteId" value="${popupSiteId}" style="width:674px; height:37px"  class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
				</div>
			</div>
		</div>

		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Site Name</span>
					<input type="text" id="popupSiteName" class="ui-widget ui-widget-content ui-corner-all form-control text-input" value="${popupSiteName}" style="width:675px;height:37px"   />
				</div>
			</div>
		</div>

  </div>
</div>
  <p></p>
<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >ID </span>
   					<input type="text" id="popupSiteSeqID" value="${popupSiteSeqID}" readonly style="width:180px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  
	</div>
</div>
 </div>
</div>
  </div>
  <div class="modal-footer">
	<!-- <button name ="previousRowSite" class ="btn btn-default">Previous</button>
	<button name="nextRowSite" class ="btn btn-default">Next</button> -->
</div>	
								                
	</div>
</div></div></div>

	
<!--End Site popup -->

<!-- ModelsAndPartnumbers Popup -->
<div class="container">
	<div id ="modPartModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #FF4F4F;" >
				<h5 id ="popupNbModpart" class="modal-title" style="font-weight:bold; color: #3C1596;"></h5>
				
				<button type="button" name="insertModPartBelow"  onclick="insertModPartRowBelow()" class ="btn btn-default btn-primary BtnActive  " style="color:white;position:relative;left:50px;">Insert Below </button>
				<button type="button" name="insertModPartAbove"  onclick="insertModPartRowAbove()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:60px;">Insert Above </button>
			    <button type="button" name="deleteModPartBoqRow"  onclick="deleteModPartBoqRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:70px;">Delete</button>
			    <button name ="previousModPartRow" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:80px;">Previous</button>
	            <button name="nextModPartRow" onclick="nextModPartRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:90px;">Next</button>
				
				<button type="button" name="closeModPartPopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="modPart-tab" style="color: gold;" data-toggle="tab" href="#modPart" role="tab" aria-controls="modPart" aria-selected="true">MODELS AND PART NUMBERS</a>
  		</li>
	</ul>
	<div class="tab-content">
  <div class="tab-pane active" id="modPart" role="tabpanel" aria-labelledby="modPart-tab">
  <p></p>
<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Item Model </span>
   					<input type="text" id="popupItemModel" value="${popupItemModel}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
  	</div>
 </div>
 <p></p>
<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Part Number</span>
					<input type="text" id="popupItemPartno" value="${popupItemPartno}" style="width:674px; height:37px"  class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
				</div>
			</div>
		</div>
  
	</div>
</div>
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Quantity</span>
					<input type="text" id="popupQty" class="form-control text-input" value="${popupQty}" style="width:90px; height:37px;"   />
				</div>
			</div>
		</div>
		
	</div>
</div>
<p></p>
<div class="container-fluid">
	<div class="row">
	<div class="col-sm-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text" style="width:85px">Primary</span>
					<input type='checkbox' id='popupPrimary' style=" margin:15px;"   />
				</div>
			</div>
		</div>	
		
  </div>
</div>
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-3">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
					<input type="text" id="popupModelPartID" readonly class="form-control text-input" value="${popupModelPartID}" style="width:180px; height:37px;"   />
				</div>
			</div>
		</div>
		
	</div>
</div>

 </div>
</div>
  </div>
  <div class="modal-footer">
	<!-- <button name ="previousRowModPart" class ="btn btn-default">Previous</button>
	<button name="nextRowModPart" class ="btn btn-default">Next</button> -->
</div>	
								                
	</div>
</div></div></div>

	
<!--End ModelsAndPartnumbers popup -->


<!-- SerialNumber Popup -->
<div class="container">
	<div id ="serialnoModal" class="modal fade custom-class-assignedto-modal"  tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">	
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" >
				<div class="modal-header" style="background-color: #FF4F4F;" >
				<h5 id ="popupNbSerialno" class="modal-title" style="font-weight:bold; color: #3C1596;"></h5>
				
				<button type="button" name="insertSerialBelow"  onclick="insertSerialRowBelow()" class ="btn btn-default btn-primary BtnActive  " style="color:white;position:relative;left:50px;">Insert Below </button>
				<button type="button" name="insertSerialAbove"  onclick="insertSerialRowAbove()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:60px;">Insert Above </button>
			    <button type="button" name="deleteSerialBoqRow"  onclick="deleteSerialBoqRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:70px;">Delete</button>
			    <button name ="previousSerialRow" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:80px;">Previous</button>
	            <button name="nextSerialRow" onclick="nextSerialRow()" class ="btn btn-default btn-primary BtnActive" style="color:white;position:relative;left:90px;">Next</button>
				
				<button type="button" name="closeSerialnoPopup" class ="close" data-dismiss ="modal"> <i class='fa fa-times'></i> </button>
				<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change' ></i> </a>
				</div>
	<div class="modal-body">
	<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
 		 <li class="nav-item">
   			 <a class="nav-link active" id="serialno-tab" style="color: gold;" data-toggle="tab" href="#serialno" role="tab" aria-controls="serialno" aria-selected="true">SERIAL NUMBER</a>
  		</li>
	</ul>
	<div class="tab-content">
  <div class="tab-pane active" id="serialno" role="tabpanel" aria-labelledby="serialno-tab">
  <p></p>
<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >Serial Number </span>
   					<input type="text" id="popupSerialno" value="${popupSerialno}" style="width:675px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
	</div>
</div>
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Model</span>
					<input type="text" id="popupModel" value="${popupModel}" style="width:674px; height:37px;"  class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Part Number</span>
					<input type="text" id="popupPartnum"  value="${popupPartnum}" style="width:675px;height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
				</div>
			</div>
		</div>

  </div>
</div>
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Node ID</span>
					<input type="text" id="popupSerNodeId" value="${popupSerNodeId}" style="width:674px; height:37px"  class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Node Name</span>
					<input type="text" id="popupSerNodeName" value="${popupSerNodeName}" style="width:675px;height:37px;"  class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
				</div>
			</div>
		</div>
  </div>
</div>
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Site</span>
					<input type="text" id="popupSite" value="${popupSite}" style="width:674px; height:37px"  class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Position</span>
					<input type="text" id="popupPosition" value="${popupPosition}" style="width:675px;height:37px;" class="ui-widget ui-widget-content ui-corner-all form-control text-input"   />
				</div>
			</div>
		</div>
  </div></div>
    <p></p>
<div class="container-fluid">
	<div class="row">
  		<div class="col-sm-6">
  			<div class="form-group">
  				<div class="input-group-prepend">
  					<span class="input-group-text" >ID </span>
   					<input type="text" id="popupSerialSeqID" value="${popupSerialSeqID}" readonly style="width:180px; height:37px" class="ui-widget ui-widget-content ui-corner-all form-control text-input" />
  				</div>
  			</div>
  		</div>
	</div>
</div>

 </div>
</div>
  </div>
  <div class="modal-footer">
	<!-- <button name ="previousRowSerial" class ="btn btn-default">Previous</button>
	<button name="nextRowSerial" class ="btn btn-default">Next</button> -->
</div>	
								                
	</div>
</div></div></div>

	
<!--End SerialNumber popup -->


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



 <script type='text/javascript'>

 
 $(document).on("triggerPNBoqListenersEvent", function () {
     $(function(){


   	     boqArrayPNum = [];
   	     boqArrayPNum = ${listFarPartNumber};
    	 for(i = 0; i< boqArrayPNum.length; i++){
        	 
    		 PNboqAutocomplete(i,"bisotab");
		 					 
    		 }		
						   
         });
     						  
 });


 $(document).on("triggerSeialNbBoqListenersEvent", function () {
     $(function(){


   	     boqArrayPNum = [];
   	     boqArrayPNum = ${ListFarSerialNumber};
    	 for(i = 0; i< boqArrayPNum.length; i++){
        	 
    		 SeialNBboqAutocomplete(i,"SerialNumberTab");
		 					 
    		 }		
						   
         });
     						  
 });


 $(document).on("triggerSiteBoqListenersEvent", function () {
     $(function(){


   	     boqArrayPNum = [];
   	     boqArrayPNum = ${ListSite};
    	 for(i = 0; i< boqArrayPNum.length; i++){
        	 
    		 SiteboqAutocomplete(i,"sitetab");
		 					 
    		 }		
						   
         });
     						  
 });


 $(document).on("triggerNodeBoqListenersEvent", function () {
     $(function(){


   	     boqArrayPNum = [];
   	     boqArrayPNum = ${ListNode};
    	 for(i = 0; i< boqArrayPNum.length; i++){
        	 
    		 NodeboqAutocomplete(i,"ahmadtab");
		 					 
    		 }		
						   
         });
     						  
 });
 


 </script>


 
 <script>

/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
/* $("#sendEmail").on("click", function () {
console.log("Helloooo in sendEmail onClick");
$("#popUpDiv").fadeIn();

}); */

/* $("#cancelButton").on("click", function () {
console.log("Helloooo in cancelButton onClick");
$("#email").val('');
$("#password").val('');
$("#emailTo").val('');
$("#ccmail").val('');
$("#subject").val('');
$("#message").val('');
$("#popUpDiv").fadeOut();
}); */

/* $("#send").on("click", function () {
console.log("Helloooo in send onClick");
if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
{
alert("All Fields are required");
}
else{
$("#popUpDiv").fadeOut();
}

}); */
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







if ('${docStatus}' == "addNew") {
	$("#formStatus").text("New");
	$('.dot').css({"background-color" : "orange"});	
	$(".nextprvItems").addClass("hide-row ");
	$(".pad").removeClass("hide-row ");
}



 $("#Disappear").click(  function() {	
	   
	 var Status=$("#farstat").val();
		 $("#farstat").val('disappear').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#Maintain").click(  function() {	
	   
	 var Status=$("#farstat").val();
		 $("#farstat").val('maintenance').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#RunIt").click(  function() {	
	   
	 var Status=$("#farstat").val();
		 $("#farstat").val('running').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})


	
	
	
	
	
// FUNCTION TO GET SERIAL NB BOQ SELETED ROW

 var dictSerialNumber = [];
 
function getselectedrowsSNum(){
	

	dictSerialNumber = [];
	
	$("#SerialNumberTab > tbody").find('input[name="done"]').each(function(){
		
		
		dictSerialNumber.push({
			
	    	"SNserialNumber" : $(this).parent().parent().children('td[name="SNserialNumber"]').children('input').val(),							   
	    	"SNmodel" : $(this).parent().parent().children('td[name="SNmodel"]').children('input').val(),			   
	    	"SNpartNumber" : $(this).parent().parent().children('td[name="SNpartNumber"]').children('input').val(),
	    	"SNnodeID" : $(this).parent().parent().children('td[name="SNnodeID"]').children('input').val(),
	    	"SNnodeName" : $(this).parent().parent().children('td[name="SNnodeName"]').children('input').val(),
	    	"SNsite": $(this).parent().parent().children('td[name="SNsite"]').children('input').val(),
	    	"SNposition" : $(this).parent().parent().children('td[name="SNposition"]').children('input').val(),
	    	"farSerialID" : $(this).parent().parent().children('td[name="farSerialID"]').children('input').val()
		    	
            });



		
	});

}

var serialRowindex =0;






// CASE SERIAL NB BOQ SAVED

if('${ListFarSerialNumber}' !="addNew"){
	boqArraySNum =[];
	boqArraySNum = ${ListFarSerialNumber};

	for(i = 0; i<boqArraySNum.length;i++){
		var inputSerialNb = boqArraySNum[i].inputSerialNb;
		var inputModel=boqArraySNum[i].inputModel;
		var inputpartNumber=boqArraySNum[i].inputpartNumber;
		var inputNodeID= boqArraySNum[i].inputNodeID;
		var inputNodeName=boqArraySNum[i].inputNodeName;
		var inputsite=boqArraySNum[i].inputsite;
		var inputPosition=boqArraySNum[i].inputPosition;
		var farSerialID=boqArraySNum[i].serialId;
		
		

		if(inputSerialNb == null)
		inputSerialNb="";
		else inputSerialNb=boqArraySNum[i].inputSerialNb;

		if(inputModel == null)
		inputModel="";
		else inputModel=boqArraySNum[i].inputModel;
		
		if(inputpartNumber == null)
		inputpartNumber="";
		else inputpartNumber=boqArraySNum[i].inputpartNumber;
		
		
		if(inputNodeID == null)
		inputNodeID = "";
		else inputNodeID=boqArraySNum[i].inputNodeID;

		if(inputNodeName == null)
		inputNodeName = "";
		else(inputNodeName=boqArraySNum[i].inputNodeName);

		if(inputsite == null)
		inputsite="";
		else(inputsite=boqArraySNum[i].inputsite);

		if(inputPosition == null)
		inputPosition="";
		else(inputPosition=boqArraySNum[i].inputPosition);

		var itemSNumRow ="<tr>";
		itemSNumRow = itemSNumRow + "<td><input type='checkbox' name='done'><button type='button' href='#' name='serialNoPopupMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		itemSNumRow = itemSNumRow + "<td name='SNserialNumber'><input name='inputSerialNb'  id = 'inputSerialNb"+i+"' type='text' value='"+inputSerialNb+"'style='width:200px;' class='form-control text-input'/></td>";
		itemSNumRow = itemSNumRow + "<td name='SNmodel'><input name='inputModel' id = 'inputModel"+i+"' type='text' value='"+inputModel+"' style='width:200px;' class='form-control text-input'/></td>";
		itemSNumRow = itemSNumRow + "<td name='SNpartNumber'><input name='inputpartNumber' id = 'inputpartNumber"+i+"' type='text' value='"+inputpartNumber+"' style='width:200px;' class='form-control text-input'/></td>";
		itemSNumRow = itemSNumRow + "<td name='SNnodeID'><input name='inputNodeID' id = 'inputNodeID"+i+"' type='text' value='"+inputNodeID+"'style='width:200px;' class='form-control text-input'/></td>";
		itemSNumRow = itemSNumRow + "<td name='SNnodeName'><input name='inputNodeName' id = 'inputNodeName"+i+"' type='text' value='"+inputNodeName+"'style='width:200px;' class='form-control text-input'/></td>";
		itemSNumRow = itemSNumRow + "<td name='SNsite'><input name='inputsite' id = 'inputsite"+i+"' type='text' value='"+inputsite+"'style='width:200px;' class='form-control text-input'/></td>";
		itemSNumRow = itemSNumRow + "<td name='SNposition'><input name='inputPosition' type='text' value='"+inputPosition+"'style='width:200px;' class='form-control text-input'/></td>";
		itemSNumRow = itemSNumRow + "<td name='farSerialID'><input name='farSerialID' type='text' readonly value='"+farSerialID+"'style='width:200px;' class='form-control text-input'/></td>";
		
		itemSNumRow = itemSNumRow + "</tr>";

		$("#SerialNumberTab  > tbody").append(itemSNumRow);
	}

	$(document).trigger("triggerSeialNbBoqListenersEvent");
}






// FUNCTION TO GET MODEL & PARTNB BOQ SELETED ROW
var dictModPart = [];
	
 function getselectedrowsItemPNum () {
	 dictModPart = [];
		
		var modPartObj = {};
	
		$("#bisotab > tbody").find('input[name="record"]').each(function(){

			modPartObj.itemPartNum = $(this).parent().parent().children('td[name="itemPartNum"]').children('input').val();
			modPartObj.itemModel= $(this).parent().parent().children('td[name="itemModel"]').children('input').val();									   
     		modPartObj.qtyPartNum= $(this).parent().parent().children('td[name="qtyPartNum"]').children('input').val();
			modPartObj.farItemID= $(this).parent().parent().children('td[name="farItemID"]').children('input').val();
			if($(this).parent().parent().children('td[name="primary"]').children('input').is(':checked'))
								{
			    		modPartObj.primary='1';
			    		
			    			}
			    	else{ modPartObj.primary='0'; 

			    	}

			    	dictModPart.push(modPartObj);
			    	modPartObj = {};
				  
			    

				  
			    			
	 });
	      
}









 // FUNCTION TO GET NODE BOQ SELETED ROW
 
  var dictNode = [];
  
  function getselectedrowsNode () {

	  dictNode = [];

 		$("#ahmadtab > tbody").find('input[name="records"]').each(function(){

 	 		
 			dictNode.push({											
		    	"nodeID": $(this).parent().parent().children('td[name="nodeID"]').children('input').val(),												
		    	"node_Name" : $(this).parent().parent().children('td[name="node_Name"]').children('input').val(),		
		    	"farNodeID" : $(this).parent().parent().children('td[name="farNodeID"]').children('input').val()
		    	});
		 

 			
 	

 });


 }








// FUNCTION TO GET SITE BOQ SELETED ROW  
var dictSite = [];
 function getselectedrowsSite () { 

dictSite = [];

 		$("#sitetab > tbody").find('input[name="recordsite"]').each(function(){

 	 		
 			dictSite.push({
			    	"wareID" : $(this).parent().parent().children('td[name="wareID"]').children('input').val(),
			    	"siteID" : $(this).parent().parent().children('td[name="siteID"]').children('input').val(),
			    	"siteName" : $(this).parent().parent().children('td[name="siteName"]').children('input').val(),													
			    	"farSiteID" : $(this).parent().parent().children('td[name="farSiteID"]').children('input').val()
			    	
                });		
 			

 });


 }








// FUNCTION TO GET BOQ CHECKBOX SELETED ROW

 function onlyOne(checkbox) {
	    var checkboxes = document.getElementsByName('primary')
	    checkboxes.forEach((item) => {
	        if (item !== checkbox) item.checked = false
	    });
	}











// CASE MODEL & PART NB BOQ SAVED

 if ('${listFarPartNumber}' != "addNew") {

	  boqArrayPNum = [];
	  boqArrayPNum = ${listFarPartNumber};
	  console.log(boqArrayPNum);
	  for (i = 0;i<boqArrayPNum.length;i++){
	  			console.log(i);
		   		var itemPartNum = boqArrayPNum[i].itemPartNb;
		   		var primary = boqArrayPNum[i].primary;
		   		var itemModel = boqArrayPNum[i].itemModel;
		   		var qtyPartNum = boqArrayPNum[i].qtyPartNb;
		   		var farItemID = boqArrayPNum[i].itmId;
		   		


		   	if(primary=='1')
		   	   	{primary="checked"; }
		   	else { primary="";}

		   	if(itemPartNum == null)
		   		itemPartNum = "";
		   	else itemPartNum = boqArrayPNum[i].itemPartNb;

		   	if(itemModel == null)
		   		itemModel = "";
		   	else itemModel = boqArrayPNum[i].itemModel;
		   
		   	
		 var itemPNumRow = "<tr>";
		 itemPNumRow= itemPNumRow + "<td style='text-align:center;'><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		 itemPNumRow =itemPNumRow + "<td name='itemModel'><input name='itemModel'  id = 'itemModel"+i+"'  type='text' value='" +itemModel+ "' style='width:200px;' class='form-control input-text'/></td>";
		 itemPNumRow =itemPNumRow + "<td name='itemPartNum' style='width:200px'><input name='itemPartNum' id = 'itemPartNum"+i+"' style='width:200px'  type='text' value='"+itemPartNum+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemPNumRow =itemPNumRow + "<td name='qtyPartNum' style='width:200px'><input name='qtyPartNum' style='width:200px'  type='text' value='"+qtyPartNum+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemPNumRow =itemPNumRow + "<td name='farItemID' style='width:200px'><input name='farItemID' style='width:200px'  type='text' readonly value='"+farItemID+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemPNumRow =itemPNumRow +"<td name='primary' style='width:120px'>	<input  type='checkbox' onclick='onlyOne(this)' name='primary' "+primary+" /></td>";
		 itemPNumRow =itemPNumRow + "</tr>";
		
	$("#bisotab > tbody").append(itemPNumRow);

	  }


	  $(document).trigger("triggerPNBoqListenersEvent"); 
 }









 // CASE NODE BOQ SAVED
 var nodeRowindex =0;
 if ('${ListNode}' != "addNew") {

	  boqArrayNode = [];
	  boqArrayNode = ${ListNode};

	  for (i = 0;i<boqArrayNode.length;i++){

		   		var nodeID = boqArrayNode[i].nodeID;
		   		var nodeName = boqArrayNode[i].nodeName;
		   		var farNodeID = boqArrayNode[i].nodefarId;


		   		if(nodeID == null)
			   		nodeID = "";
			   	else nodeID = boqArrayNode[i].nodeID;

			   	if(nodeName == null)
			   		nodeName = "";
			   	else nodeName = boqArrayNode[i].nodeName;
		   		
		   		var itemNodeRow = "<tr>";
		   		itemNodeRow= itemNodeRow + "<td><input type='checkbox' name='records'><button type = 'button' href='#' name='popUpNodeMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>";
		   		itemNodeRow =itemNodeRow + "<td name='nodeID'><input name='nodeID' id = 'nodeID"+i+"'  type='text' value='" +nodeID+ "' style='width:200px;' class='form-control input-text'/></td>";
		   		itemNodeRow =itemNodeRow + "<td name='node_Name' style='width:200px'><input name='node_Name' id = 'node_Name"+i+"' style='width:200px'  type='text' value='"+nodeName+"' style='width:200px;' class='form-control input-text' /> </td>";
		   		itemNodeRow =itemNodeRow + "<td name='farNodeID' style='width:200px'><input name='farNodeID' readonly style='width:200px'  type='text' value='"+farNodeID+"' style='width:200px;' class='form-control input-text' /> </td>";
                itemNodeRow =itemNodeRow + "</tr>";

				
			$("#ahmadtab > tbody").append(itemNodeRow); 
        }

	  $(document).trigger("triggerNodeBoqListenersEvent");
}







 
// CASE SITE BOQ SAVED

if ('${ListSite}' != "addNew") {

	  boqArraySite = [];
	  boqArraySite = ${ListSite};

	  for (i = 0;i<boqArraySite.length;i++){

		   		var siteID = boqArraySite[i].siteID;
		   		var siteName = boqArraySite[i].siteName;
		   		var wareID = boqArraySite[i].wareID;
		   		var farSiteID = boqArraySite[i].farsiteId;
		   		


		   		if(siteID == null)
		   			siteID = "";
			   	else siteID = boqArraySite[i].siteID;

			   	if(siteName == null)
			   		siteName = "";
			   	else siteName = boqArraySite[i].siteName;
			   	if(wareID == null)
			   		wareID = "";
			   	else wareID = boqArraySite[i].wareID;
		   		
		   		var itemSiteRow = "<tr>";
		   		itemSiteRow= itemSiteRow + "<td><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   		itemSiteRow =itemSiteRow + "<td name='wareID' style='width:200px'><input name='wareID' id = 'wareID"+i+"' style='width:200px'  type='text' value='"+wareID+"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /> </td>";		   		
		   		itemSiteRow =itemSiteRow + "<td name='siteID'><input name='siteID' id = 'siteID"+i+"'  type='text' value='" +siteID+ "' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>";
		   		itemSiteRow =itemSiteRow + "<td name='siteName' style='width:200px'><input name='siteName' id = 'siteName"+i+"' style='width:200px'  type='text' value='"+siteName+"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /> </td>";
		   		itemSiteRow =itemSiteRow + "<td name='farSiteID' style='width:200px'><input name='farSiteID' style='width:200px' readonly  type='text' value='"+farSiteID+"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /> </td>";
		   		itemSiteRow =itemSiteRow + "</tr>";

				
			$("#sitetab > tbody").append(itemSiteRow);
   }

	  $(document).trigger("triggerSiteBoqListenersEvent");   
}





            // ADD NODE BOQ ROW ON CLICK STATUS
             $(".addnode-row").click(function() {
	           $("#formStatus").text("Not Saved");
	           $('.dot').css({"background-color" : "orange"});
            });



           // ADD SITE BOQ ROW ON CLICK STATUS
            $(".addsite-row").click(function() {
	           $("#formStatus").text("Not Saved");
	           $('.dot').css({"background-color" : "orange"});
            });


          
           // ADD MODEL & PART BOQ ROW ON CLICK STATUS
			 $(".addmodpartnb-row").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});



			// ADD SERIAL NB BOQ ROW ON CLICK STATUS	 
			 $(".addserial-row").click(function() {
					$("#formStatus").text("Not Saved");
					$('.dot').css({"background-color" : "orange"});
			});

			
			 
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

			
              $("#itmcode").on('keyup change', function () {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});

  				
			$("#domaine").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				});







 // SAVE BUTTON
 			
 $("#saveButton").click(  function() {
	 
	 
	 // validate item code cannot be null
	 if ($("#itmcode").val()== '') {
	 alert('Itemcode cannot be Null');
	 return false;}
	 // validate warehouse id cannot be null	
	 if ($("#wareID").val()== '') {
		 alert('Warehouse ID cannot be Null');
		 return false;}
	 
	 // validate site id cannot be null
	 if ($("#siteID").val()== '') {
		 alert('Site ID cannot be Null');
		 return false;}

	// validate site name cannot be null
	 if ($("#siteName").val()== '') {
		 alert('Site Name cannot be Null');
		 return false;}
	
	 // validate type of valuation rate
	 /*if (($. isNumeric( $('#valuarate'). val()))== false) {
	 alert('valuation Rate is  not Numeric');
	 return false;}*/
	 
	 // validate creatd date cannot be null
	 val =$("#createdate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
	 
	  // validate modified date cannot be null
	 val =$("#lstmodifdate").val();
	 
     val1 = Date.parse(val);
     
     if (isNaN(val1) == true) {
         
          alert(' Modified Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
        
     // validate end of life date cannot be null
    /* val = $("#endoflive").val();
     val1 = Date.parse(val);
     console.log('date is  : ' +val1);
     if (isNaN(val1) == true) {
          alert('End of life Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }*/
		
     getselectedrowsItemPNum();
     getselectedrowsNode();
     
     getselectedrowsSite();
     getselectedrowsSNum();

     var itemCat = ($("#itmcat").val().split(":"))[0];
 	 var itemCatCode = ($("#itmcat").val().split(":"))[1];
     
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/FixedAssetRegistryFormSave",
			dataType : "json",
			data : {
			     
			    "farID" : $("#farcode").val(),
				"faritemCode" : $("#itmcode").val(),
				"faritemName" : $("#itmname").val(),
				"farcreatedDate" : $("#createdate").val(),
				"farlastModifiedDate" : $("#lstmodifdate").val(),
				"itemCategory" : itemCat,  
				"itemCatCode" : itemCatCode,
				"itemCatID" : $("#itmcatid").val(),
				"itemRootCat" : $("#itmrootcat").val(),
				"faritemImage" : $("#itmimage").val(),
				"faritemPosition" : $("#itemPosition").val(),
				"farStatus": $("#farstat").val(),
				"ownership" : $("#ownership").val(),
				"scrapStatus" : $("#scrapStatus").val(),
				"scrapDate" : $("#scrapDate").val(),
				"initialCost" : $("#initialCost").val(),
				"accumDepAmount" : $("#accumDepAmount").val(),
				"dailyDepAmount" : $("#dailyDepAmount").val(),
				"depPercentage" : $("#depPercentage").val(),
				"netCost" : $("#netCost").val(),
				"usefulLifemonths" : $("#usefulLifemonths").val(),
				"slctDelModPart" : slctDelModPart,
				"slctDelSerial" : slctDelSerial,
				"slctDelSite" : slctDelSite,
				"slctDelNode" : slctDelNode,
				"dictParameterFixedAssetRegistrySerialNumber" : dictSerialNumber,
				"dictParameter" :dictModPart,
				"dictParameternode" : dictNode,
				"dictParametersite" : dictSite,
				"farDomain": $("#farDomain").val(),
				"farSubDomain": $("#farSubDomain").val(),
				"farVendor": $("#farVendor").val(),
				"farType": $("#farType").val()
				
				/* "email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(), */
							
			},
			success : function(data) {
				$('#lstmodifdate').val(data.farlastModifiedDate);
				$("#farcode").val(data.farID);
				var param ="${pageContext.request.contextPath}/FixedAssetRegistryFormView?farID="+$("#farcode").val()+"&NavAction=2";
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		
	
 })

var slctDelModPart = [];
var slctDelSerial = [];
var slctDelSite = [];
var slctDelNode = [];	   
 </script>

 

 <script type='text/javascript'>



 //  ** START OF READY FUNCTION **
 
	$(document).ready(function() {
		
		var ctx = getContextPath();


		// MODEL & PARTNUMBER TAB AUTOCOMPLETE
		console.log($("#bisotab >tbody tr").length);
		if($("#bisotab >tbody tr").length > 0){
		newRowCount =  $("#bisotab >tbody tr").length-1;
		console.log("newRowCount : "+newRowCount);
  		PNboqAutocomplete(newRowCount,"bisotab");
		}
  	    // SERIAL NUMBER TAB AUTOCOMPLETE
  	    if($("#SerialNumberTab >tbody tr").length > 0){
  		SNnewRowCount =  $("#SerialNumberTab >tbody tr").length-1;
  		SeialNBboqAutocomplete(SNnewRowCount,"SerialNumberTab");
  	    }
  	    
   	    // SITE TAB AUTOCOMPLETE
   	    if($("#sitetab >tbody tr").length > 0){
  		SitenewRowCount =  $("#sitetab >tbody tr").length-1;
  		SiteboqAutocomplete(SitenewRowCount,"sitetab");
   	    }
   	    
   	    // NODE TAB AUTOCOMPLETE
   	    if($("#ahmadtab >tbody tr").length){
  		NodenewRowCount =  $("#ahmadtab >tbody tr").length-1;
  		NodeboqAutocomplete(NodenewRowCount,"ahmadtab");
   	    }	




// BUTTON NEXT & PREVIOUS ACTION

		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${FRCount}' != "addNew"){

							
					var FRCount = ${FRCount};
					
					if(($("#farcode").val()) != "" && ($("#farcode").val()) != null){

					if(SelectedIndex === FRCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									var param ="${pageContext.request.contextPath}/FixedAssetRegistryFormView?farID="+$("#farcode").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/FixedAssetRegistryFormView?farID="+$("#farcode").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/FixedAssetRegistryFormView?farID="+$("#farcode").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/FixedAssetRegistryFormView?farID="+$("#farcode").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}
					$("#label-1").text((SelectedIndex)+"/"+FRCount);







			// OTHER FAR AUTOCOMPLETE
					 $("#selectnav").autocomplete({
			    			
			    		    source: function(request, response) {
			    			    
			    		             $.ajax({
			    		                 type: "GET",
			    		                 contentType: "application/json; charset=utf-8",
			    		                 url: '${pageContext.request.contextPath}/GetAllFAR',
			    		                 data: {
			    		                	    "requestValue" : request.term,
			    						 },
			    		                 dataType: "json",
			    		                 success: function (data) {
			    		                     if (data != null) {
			    		                         response(data.listFAR);
			    		                     }
			    		                 },
			    		                 error: function(result) {
			    		                     console.log("Error");
			    		                 }
			    		             });
			    		         }, minLength:0, maxShowItems: 40, scroll:true,

			    					select: function(event, ui) {
			    						
			    						this.value = (ui.item ? ui.item[0]  + ":" + ui.item[2]  + ":" + ui.item[3] : '');
			    						
			    						var param ="${pageContext.request.contextPath}/FixedAssetRegistryFormView?farID="+(ui.item[0])+"&NavAction=2";
			    						window.location.href =param;
			           						
			           						return false;
			           					}
			           				}).autocomplete("instance")._renderItem = function(ul, item) {
			           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
			           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
			           	                 item[0] + '</span><br><span class="desc">' +
			           	                 item[2] + "," +
			           	                 item[3] + '</span></div></li>').appendTo(ul);
			           			};
			           					$("#selectnav").focus(function(){
			           		   	        	if (this.value == ""){
			           		   	            	$(this).autocomplete("search");
			           		   	        	}						
			           					});   
			    	


			           					
			
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
/* $("#email").autocomplete({
source: function(request, response) {

var password=$("#password").val();
$.ajax({
 type: "GET",
 contentType: "application/json; charset=utf-8",
 url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
 data: {
		"email" : request.term,
		//"password":request.value,
 },
 dataType: "json",
 success: function (data) {
     if (data != null) {
         response(data.ListItemEmailAccounts);
         console.log('data in $("#email").autocomplete is :  '+ data.ListItemEmailAccounts);

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
}); */

//////////////////////////////////////////////////////////////////////////////		  	

		
	
		var itemDo = '${itemDomain}';
		/*if(itemDo != "")
		{
			if(itemDo == "mad")
				DomainText = "Mobile Access Domain";
			if(itemDo == "itd")
				DomainText = "Transport Domain";
			if(itemDo == "icd")
				DomainText = "Core Domain";
			if(itemDo == "ipd")
				DomainText = "Passive Domain";
			var dropDown = '<select name="domaine" id="domaine" class="form-control"><option value="${itemDomain}">'+DomainText+'</option></select>';
			$("#domainSpan").append(dropDown);
		}*/
		

///////////////////////////////////////////







// ITEM CODE AUTOCOMPLETE

$("#itmcode").autocomplete({
	 source: function(request, response) {
          $.ajax({
              type: "GET",
              contentType: "application/json; charset=utf-8",
              url: '${pageContext.request.contextPath}/getItemCode',
              data: {
            	  requestValue : request.term,
	 			},
              dataType: "json",
              success: function (data) {
                  if (data != null) {
                      response(data.ListItemDetails);
                  }
              },
              error: function(result) {
                  console.log("Error");
              }
          });
	    }, minLength:0, maxShowItems: 40, scroll:true,
		select: function(event, ui) {
			this.value = (ui.item ? ui.item[0]  : '');
			$("#itmname").val(ui.item[1]);
			$("#itmcat").val(ui.item[5]+":"+ui.item[6]);
			$("#itmcatid").val(ui.item[7]);
			$("#itmrootcat").val(ui.item[8]);
			return false;
			
			$.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/GetItemDetailsForm',
                data: {
						ItemCode : ui.item[0],
				 },
                dataType: "json",
                success: function (data) {
                    if (data != null) {
	                     console.log(data.ItemDetails[0]);

	                     ItemName = data.ItemDetails[0][0];
	                     ItemUnit = data.ItemDetails[0][1];
	                     ItemWeight = data.ItemDetails[0][2];
	                     ItemWeituom = data.ItemDetails[0][3];
	                     ItemLen = data.ItemDetails[0][4];
	                     ItemWidth = data.ItemDetails[0][5];
	                     ItemHeight = data.ItemDetails[0][6];
	                     ItemSizeUOM = data.ItemDetails[0][7]; 
	                     Itemdesc = data.ItemDetails[0][8];
	                     ItemDomain = data.ItemDetails[0][9];
	                     ItemCabletype = data.ItemDetails[0][10];
	                     ItemItemType = data.ItemDetails[0][11];
	                     ItemusefulLife = data.ItemDetails[0][12]; 
	                     ItemuValRate = data.ItemDetails[0][13];
	                     ItemuWarPeriod = data.ItemDetails[0][14];
	                     ItemuModel = data.ItemDetails[0][16];
	                     ItemuPartNb = data.ItemDetails[0][17];
	                     tech2G = data.ItemDetails[0][18];
	                     tech3G = data.ItemDetails[0][19];
	                     tech4G = data.ItemDetails[0][20];
	                     tech5G = data.ItemDetails[0][21];
	                     ItemService = data.ItemDetails[0][22];
	                     ItemDis = data.ItemDetails[0][23];

	                     if(ItemDomain == "mad")
	         				DomainText = "Mobile Access Domain";
	         			 if(ItemDomain == "itd")
	         				DomainText = "Transport Domain";
	         			 if(ItemDomain == "icd")
	         				DomainText = "Core Domain";
	         			 if(ItemDomain == "ipd")
	         				DomainText = "Passive Domain";
	         			 /*var dropDown = '<select name="domaine" id="domaine"><option value="${itemDomain}">'+DomainText+'</option></select>';
	         			 $("#domainSpan").append(dropDown);*/

	         			 if(tech2G == 1)
	         				$('#techg2g').attr('checked','checked');
	         			 if(tech3G == 1)
	         				$('#techg3g').attr('checked','checked');
	         			 if(tech4G == 1)
	         				$('#techg4g').attr('checked','checked');
	         			 if(tech5G == 1)
	         				$('#techg5g').attr('checked','checked');
	         			if(ItemService == 1)
	         				$('#service1').attr('checked','checked');
	         			if(ItemDis == 1)
	         				$('#disables').attr('checked','checked');
	         			


	         			
	                     console.log(data.ItemDetails[0]["itemName"]);
	                     $("#itmname").val(ItemName);
	                     $("#unt").val(ItemUnit);
	                     $("#weit").val(ItemWeight);
	                     $("#weituom").val(ItemWeituom);
	                     $("#len").val(ItemLen);
	                     $("#widths").val(ItemWidth);
	                     $("#heit").val(ItemHeight);
	                     $("#siseuom").val(ItemSizeUOM);
	                     $("#itmdescrip").val(Itemdesc);
	                     $("#cbltype").val(ItemCabletype);
	                     $("#itmtyp").val(ItemItemType);
	                     $("#itmtyp").val(ItemItemType);
	                     $("#usefulLifemonths").val(ItemusefulLife);
	                     $("#valuarate").val(ItemuValRate);
	                     $("#itmModel").val(ItemuModel);
	                     $("#itmPartNo").val(ItemuPartNb);
	                     $("#warrantyper").val(ItemuWarPeriod);

                    }
                },
                error: function(result) {
                    console.log("Error");
                }
            });
			
				return false;
					}

      
				
		    }).autocomplete("instance")._renderItem = function(ul, item) {
				return $("<li class='each'>")
			    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			        item[1] + "</span><br><span class='desc'>" +
			        item[0] + "</span></div>")
			        
			    .appendTo(ul);
       	};
			$("#itmcode").focus(function(){
  	        	if (this.value == ""){
  	            	$(this).autocomplete("search");
  	        	}						
			});






			
// ITEM NAME AUTOCOMPLETE
			
$("#itmname").autocomplete({
 source: function(request, response) {
     $.ajax({
         type: "GET",
         contentType: "application/json; charset=utf-8",
         url: '${pageContext.request.contextPath}/getItemCode',
         data: {
        	 requestValue : request.term
			},
         dataType: "json",
         success: function (data) {
             if (data != null) {
                 response(data.ListItemDetails);
             }
         },
         error: function(result) {
             console.log("Error");
         }
     });
 }, minLength:0, maxShowItems: 40, scroll:true,	
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[1]  : '');
		$("#itmcode").val(ui.item[0]);
		$("#itmcat").val(ui.item[5]+":"+ui.item[6]);
		$("#itmcatid").val(ui.item[7]);
		$("#itmrootcat").val(ui.item[8]);
		
		return false;
	}
	}).autocomplete("instance")._renderItem = function(ul, item) {
		return $("<li class='each'>")
	    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	    		item[0] + "</span><br><span class='desc'>" +
	         item[1] + "</span></div>")
	        
	    .appendTo(ul);
       	};
		$("#itmname").focus(function(){
  	        	if (this.value == ""){
  	            	$(this).autocomplete("search");
  	        	}						
		});




		
	if ('${ListPRqItem}' != "addNew") {
	}
	else {
	          // set status to new and green while new record
	           $("#formStatus").text("New");
					$('.dot').css({"background-color" : "orange"});
	       }

		
	});	

// ** END OF READY FUNCTION **


	
	
	
	
// CASE SAVED STATUS 	
 if ('${docStatus}' != "addNew" ){
		
	console.log("The docstatus is " +'${docstatus}');

		

	faritem = ${ListFARItem};


	var sumTotal  ;
	var sumQty ;
	var slctsave = [];
	var slctDel = [];
	var newslct =[];
	var ressplit;
	var itmcode;
	var itmname;
	var itmqty;
	var itmrate;
	var itmdisc;
	var itmtax1;
	var itmnetrate;
	var itmtot;
	var itmtotAT;
	var itmnewarehouse;
	var prGR;
	var prPR;
	var prAR;
	var prCIP;
	var itmnordid;

	   
	// get all colmns count per row
		function count(array){
	       var c = 0;
	       for(i in array) // in returns key, not object
	           if(array[i] != undefined)
	               c++;
	       return c;
	}

	// Fill tables rows from DB
	var len=faritem.length;
	console.log("The lenght is " + len);
	var itemCodeId;   
	var collen= count(faritem[0]);
	console.log("The count is " + collen);
	   var i =0;
	   var itemRow = "<tr>";
	   //itemRow= itemRow + "<td><input type='checkbox' name='record'></td>"
	   //console.log(itemRow);
	   for (j = 0; j < collen; j++) {
	   	var tt1= faritem[i][j];
	   	if (j == 0) {
				itemRow =itemRow + "<td><input type='text' name='itmCode' value='" + tt1 +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control' readonly/></td>";
			}
			if (j == 9) {
			    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			if (j == 8) {
			    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			if (j == 7) {
			    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }

			if (j == 1) {
				itemRow =itemRow + "<td><input type='text' name='itmModel' value='" + tt1 +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control' readonly/></td>";
			}
			if (j == 2) {
				itemRow =itemRow + "<td><input type='text' name='itmPartNo' value='" + tt1 +"' style='width:200px;' class='ui-widget ui-widget-content ui-corner-all form-control' readonly/></td>";
			}
		    
			if (j == 3) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			if (j == 4) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			if (j == 5) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			if (j == 6) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			//if (j == 8) {    
			//	itemRow =itemRow + "<td><input type='text' name='itmWare' value='" + tt1 +"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all' readonly/></td>";
			 //   }
		   if (j ==10 ) {
			    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }  
	   	if (j == 11) {
			    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }    
			    if (j == 12) {
			    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			    if (j == 13) {
			    itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			if (j == 14) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			
			if (j == 15) {    
				itemRow =itemRow + "<td><input type='text' value='" + tt1 +"' style='width:200px;' class='form-control' readonly></td>";
			    }
			
			    
			
	   }
	   itemRow =itemRow + "</tr>";
	   $("#tbRelatOrd > tbody").append(itemRow);
	}
	else {
		
	console.log("the add new option and the value of the doc status is "+'${docStatus}');
	   // set status to new and green while new record
	    $("#formStatus").text("New");
		 $('.dot').css({"background-color" : "orange"});
	}

	

	 
</script>
<script type="text/javascript">



           // FINANCE TAB FUNCTION
			function myFunction(){

				var useful_life_month = $("#usefulLifemonths").val();
				var initialCost = $("#initialCost").val();

				if(useful_life_month != "" && initialCost != "")
				{
					var useful_life_month = useful_life_month * 30 ;
					
					var DailyDepr = initialCost/useful_life_month ;
					DailyDepr = DailyDepr.toFixed(2);  // toFixed() method converts a number into a string, rounding to a specified number of decimals.
			        var dailyDeprAmount = document.getElementById("dailyDepAmount").value ;

			        dailyDeprAmount = DailyDepr;
			       
			        
			        var today = new Date();
			        var date = (today.getMonth()+1)+'/'+today.getDate()+'/'+ today.getFullYear();
			        var time = today.getHours() + ":" + today.getMinutes() ;
			        var dateTime = date+' '+time;
			        var a = $("#createdate").val();
			        var b = Date.parse(a); 
			        var c = Date.parse(dateTime);
			        var minutes = 1000 * 60;
			        var hours = minutes * 60;
			        var days = hours * 24;
			        var diffDays = (c - b); 
			        var y = Math.round(diffDays / days);
			        var AD = y * DailyDepr;    
			        document.getElementById("accumDepAmount").value = AD;

			        var DP = 0;
				    if(initialCost != 0)
					{
				    	DP = (document.getElementById("accumDepAmount").value/document.getElementById("initialCost").value)* 100;
				    	DP = DP.toFixed(2);
				    	/*
				    	// ADDED BY AHMAD
				    	document.getElementById("dailyDepAmount").value = dailyDeprAmount;
				        document.getElementById("accumDepAmount").value = AD;
				        */
					}
			        
			         document.getElementById("depPercentage"). value = DP;
			        

			        var netCost = (document.getElementById("initialCost").value) - (document.getElementById("accumDepAmount").value);
			        
			        if(netCost <= 0.0){
			        	document.getElementById("netCost"). value = 0;
				        }
			        else {
				        document.getElementById("netCost"). value = netCost;
			        }
			      
			        
				}
			}

		</script>

 

	
 </html>
