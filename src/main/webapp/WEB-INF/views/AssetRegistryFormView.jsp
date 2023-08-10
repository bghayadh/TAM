 <!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	
	<!-- To be Removed 
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	-->
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	
	
	<!-- To be Removed 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
	-->
	
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<!-- To be Removed 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	-->
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/AssetRegistry/AR_BoqPopup.js"></script>
	
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
				.fixed-headerr{
					opacity: 1;
					filter: alpha(opacity=100);
				 	background: #ebf2ef;
				  	position: sticky;
				  	top: 0;
				  	z-index: 15;
				
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
				
				
				
     /*  			.ui-autocomplete {
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

            .container {
                margin: 0 auto;
                padding: 0 24px;
                max-width: 960px;
            }

            /* primary header */

            .primary-header {
                padding: 24px 0;
                text-align: center;
                border-bottom: solid 2px #cfcfcf;
            }

            .primary-header__title {
                color: #393939;
                font-size: 36px;
            }

            .primary-header__title small {
                font-size: 18px;
                color: #898989;
            }

            /* content */

            .content {
                padding: 48px 0;
                border-bottom: solid 2px #cfcfcf;
            }

            .content__footer {
                margin-top: 12px;
                text-align: center;
            }

            /* footer */

            .primary-footer {
                padding: 24px 0;
                color: #898989;
                font-size: 14px;
                text-align: center;
            }

            /* tasks */

            .tasks {
                /* list-style: none;
  margin: 0;
  padding: 0;*/
            }

            .task {
                /*display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: solid 1px #dfdfdf;*/
            }

            .task:last-child {
                border-bottom: red;
                width: 205px;
            }

            /* context menu */

            .context-menu {
                display: none;
                position: absolute;
                z-index: 10;
                padding: 12px 0;
                width: 240px;
                background-color: #fff;
                border: solid 1px #dfdfdf;
                box-shadow: 1px 1px 2px #cfcfcf;
            }

            .context-menu--active {
                display: block;
            }

            .context-menu__items {
                list-style: none;
                margin: 0;
                padding: 0;
            }

            .context-menu__item {
                display: block;
                margin-bottom: 4px;
            }

            .context-menu__item:last-child {
                margin-bottom: 0;
            }

            .context-menu__link {
                display: block;
                padding: 4px 12px;
                color: #0066aa;
                text-decoration: none;
            }

            .context-menu__link:hover {
                color: #fff;
                background-color: #0066aa;
            }

            .btn-tree {
                color: #474747;
                background-color: #d8d8d8;
            }

            .selected-span {
                color: #08526d;
                font-weight: bold;
            }

            .last-node::before {
                height: 26px !important;
            }
        	
        	
        	 .modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
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
							<span class="input-group-text" style="color:green">Asset Register</span>
							<input type="text" id="arcode" readonly  value="${arID}" class="form-control text-input"  />						
						</div>

					</div>
			</div>
			<div class="col-md-3">
				<div class="input-group-prepend">
				<span style="width:190px;" class="input-group-text">Status</span>
				<select id="arstat" class="form-control">
								<option value="inprog" <c:if test = "${arStatus =='inprog'}" > selected </c:if> >In Progress</option>
								<option value="dismantled" <c:if test = "${arStatus =='dismantled'}" > selected </c:if>>Dismantled</option>
								<option value="Disappear" <c:if test = "${arStatus =='Disappear'}" > selected </c:if>>Disappeared </option>
								<option value="Maintenance" <c:if test = "${arStatus =='Maintenance'}" > selected </c:if>>Maintenance</option>
								<option value="Running" <c:if test = "${arStatus =='Running'}" > selected </c:if>>Running</option></select>
				</div>							
			</div>
			<div class="pad col-md-3 hide-row"></div>
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other AR</span>
						<input type="text" id="selectnav" value="${selectnav}"
						style="width: 430px" class="form-control text-input" />		
				</div>
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
					<input type="text" id="createdate" readonly value="${arcreatedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${arlastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
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
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/AssetRegistryListView"'
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
            id="custom-tabs-onenode-tab" data-toggle="tab"
            href="#custom-tabs-one-node" role="tab"
            aria-controls="#custom-tabs-one-node" aria-selected="false" style="color: gold;">NODE</a></li> 
            
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
				<input type="text" id="itmcode"  value="${aritemCode}" style="width:600px;" class="form-control text-input"/>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Name</span>
				<input type="text" id="itmname" value="${aritemName}" class="form-control text-input" />
			
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
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Name Register</span>
					<input type="text" id="itemNameReg" value="${itemNameReg}" class="form-control text-input" />
			</div>
		</div>				
	</div>
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Item Image</span>
					<input type="text" id="itmimage" value="${aritemImage}" class="form-control text-input" />
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Position</span>
					<input type="text" id="itemPosition" value="${aritemPosition}" class="form-control text-input" />
			</div>
		</div>
	</div>
</div>
	<div class="row">	
	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Warranty Period</span>
					<input type="text" id="warrantyper" value="${arwarrantyPeriod}" class="form-control text-input"  />
				</div>
			</div>
	</div>
	
	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">End Of Life</span>
					<input type="text" id="endoflive" value="${arendOfLife}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
					   <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
			</div>
	</div>
	
	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Valuation Rate</span>
				<input type="text" id="valuarate" value="${arvaluationRate}" class="form-control text-input"  />
				</div>
			</div>
	</div>
  </div>
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
		<textarea name="itmdescrip" cols="120" rows="7" id="itmdescrip" class="form-control text-input"> ${aritemDescription} </textarea>
		</div>
	</div>
	
	
	<div class="col-md-6">
	</div>
</div>



	</div>
	
	
	<div class="tab-pane fade" id="custom-tabs-partNum" role="tabpanel" aria-labelledby="custom-tabs-itemPartnum-tab">

<p></p>

<form>				
	<div class="table-responsive-sm"> 
		<table id ="partmodTab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
		<thead>
			<tr class="fixed-headerr">
		    	<th>
		       	<button type="button" id="selectAll-modelpart" class="main" style="width:80px;">
				<span class="sub"></span>Select</button>
		      	</th>
		        <th width="220px">Item Model</th>
		        <th width="220px">Item Part Number</th>
				<th width="220px">Quantity</th>
				<th width="220px">ID</th>
				<th width="100px">Primary</th>
			</tr>
		</thead>
		<tbody></tbody>
		</table>
		<p id="my_error" style="color:red;"></p>
	</div>
	<input type="button" class="add-row" id="add_roww" onclick="addModelPartrow('addRow')"  value="Add Row">
	<button type="button" class="delete-row-modelpart">Delete Row</button>
</form>

<p></p>
</div>

 <div class="tab-pane fade" id="custom-tabs-serialNumber" role="tabpanel" aria-labelledby="custom-tabs-serialNumber-tab">

<p></p>
<div> 
<form>
	<div class="table-responsive-sm"> 
		<table id ="ARserialNumberTab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
			<thead>
	        <tr class="fixed-headerr">
	        	<th>
			        <button type="button" id="selectAll-serial" class="main" style="width:80px;">
			        <span class="sub"></span>Select</button>
			    </th>
                <th width="220px">Serial Number</th>
                <th width="220px">Model</th>
				<th width="220px">Part Number</th>
				<th width="220px">Node ID</th>
				<th width="220px">Node Name</th>
				<th width="220px">Site</th>
				<th width="220px">Position</th>
				<th width="220px">ID</th>
	        </tr>
	        </thead>
			<tbody></tbody>
		</table>
		<p id="my_error" style="color:red;"></p>
	</div>
	<input type="button" class="add-rowARserialNb" id="add_rowsSerialNb" onclick="addSerialrows('addRow')" value="Add Row">
	<button type="button" class="delete-row-serial">Delete Row</button>
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
						<input type="text" id="unt" value="${arunit}"  class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight</span>
						<input type="text" id="weit" value="${arweight}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Weight UOM</span>
						<input type="text" id="weituom" value="${arweightUOM}" class="form-control text-input" />
					</div>
				</div>
	</div>
	
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Length</span>
						<input type="text" id="len" value="${arlength}" class="form-control text-input"  />
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Width</span>
					<input type="text" id="widths" value="${arwidth}" class="form-control text-input"/>
					</div>
				</div>
	</div>
	
	<div class="col-md-4">
				<div class="input-group-prepend">
					<span class="input-group-text">Height</span>
				<input type="text" id="heit" value="${arheight}" class="form-control text-input" />
				</div>							
	</div>
	
	<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Size UOM</span>
					<input type="text" id="siseuom" value="${arsizeUOM}" class="form-control text-input"  />
			</div>

				
		</div>
	
</div>
	</div>

	<div class="tab-pane fade" id="custom-tabs-one-messages" role="tabpanel" aria-labelledby="custom-tabs-one-messages-tab">

<p></p>

<div class="row">
	<div class="col-md-4">
			<div class="input-group-prepend">
				<span class="input-group-text">Domain</span>
				<!--  <input type="text" id="domaine" value="${ardomain}" class="form-control text-input" /> -->
				<select name="cars" id="domaine" class="form-control">
    				<option value="mad" <c:if test = "${ardomain =='mad'}"> selected </c:if>>Mobile Access Domain</option>
    				<option value="itd" <c:if test = "${ardomain =='itd'}"> selected </c:if>>Transport Domain</option>
    				<option value="icd" <c:if test = "${ardomain =='icd'}"> selected </c:if>>Core Domain</option>
    				<option value="ipd" <c:if test = "${ardomain =='ipd'}"> selected </c:if>>Passive Domain</option>
  				</select>
			</div>

				
		</div>
	
	<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Cable Type</span>
						<input type="text" id="cbltype" value="${arcableType}" class="form-control text-input" />
						</div>
					</div>
	</div>


	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Item Type</span>
					<input type="text" id="itmtyp" value="${aritemType}" class="form-control text-input"/>
				</div>
			</div>
	</div>
</div>
	
	<div class="row">
		<div class="col-md-12">
				<div class="form-group">
					<div class="checkboxes">
					  	<span>	 
		              		 <input type="checkbox" id="servise1" ${arservice} />Service
		              		 <input type="checkbox" id="disables" ${ardisabled}/> Disabled
	   	              		 <input type="checkbox" id="techg2g" ${artech2G}/> 2G
							 <input type="checkbox" id="techg3g" ${artech3G}/> 3G
		              		 <input type="checkbox" id="techg4g" ${artech4G}/> 4G
		              		 <input type="checkbox" id="techg5g" ${artech5G}/> 5G
	              		 </span>
					</div>
				</div>
		</div>
	</div>

</div>

<p></p>
<div class="tab-pane fade" id="custom-tabs-one-site" role="tabpanel" aria-labelledby="custom-tabs-one-site-tab">
<form>			
	<div class="table-responsive-sm"> 
		<table id ="sitetab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
	    <thead>
	    	<tr class="fixed-headerr">
	            <th>
	            	<button type="button" id="selectAll-site" class="main" style="width:80px;">
			        <span class="sub"></span>Select</button>
			    </th>
		        <th width="220px">Warehouse ID</th>
                <th width="220px">Site ID</th>
                <th width="220px">Site Name</th>
                <th width="220px">ID</th>
	    	</tr>
		</thead>
		<tbody></tbody>
		</table>
		<p id="my_error" style="color:red;"></p>
	</div>
	<input type="button" class="addsite-row" onclick="addSiterows('addRow')" id="adds_roww" value="Add Row">
	<button type="button" class="delete-row-site"> Delete Row</button>
</form>
</div> 

<div class="tab-pane fade" id="custom-tabs-one-node" role="tabpanel" aria-labelledby="custom-tabs-onenode-tab">

<p></p>

<form>			
	<div class="table-responsive-sm"> 
		<table id ="partnodeTab" class="table table-striped table-bordered table-sm" style="display:block; height:300px; overflow-y: auto;">
		<thead>
        	<tr class="fixed-headerr">
            <th>
            	<button type="button" id="selectAll-node" class="main" style="width:80px;">
		        <span class="sub"></span>Select</button>
		    </th>
            <th width="220px">Node Id</th>
            <th width="220px">Node Name</th>
            <th width="220px">ID</th>
            </tr>
        </thead>
		<tbody></tbody>
		</table>
		<p id="my_error" style="color:red;"></p>
	</div>
	<input type="button" class="add-rowNode" onclick="addNoderows('addRow')" id="add_rowwNode" value="Add Row">
	<button type="button" class="delete-row-node">Delete Row</button>
</form>
		

<p></p>
    
    <!--  <div class="tab-pane fade" id="custom-tabs-one-cell" role="tabpanel" aria-labelledby="custom-tabs-one-cell-tab">
		
    </div>-->
  </div>  
     <div class="tab-pane fade" id="custom-tabs-one-relatedorders" role="tabpanel" aria-labelledby="custom-tabs-one-relatedorders-tab">
		<p></p>
		<div class="row">
		<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Purchase Order</span>
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
						    <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						               
						                <th>Item</th>
						                <th>Item Model</th>
						                 <th>Item Part Number</th>
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
						                 <th>CIP</th>
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
	<!--  <button name ="previousRowNode" class ="btn btn-default">Previous</button>
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
	<!--  <button name ="previousRowSite" class ="btn btn-default">Previous</button>
	<button name="nextRowSite" class ="btn btn-default">Next</button> -->
</div>	
								                
	</div>
</div></div></div>

	
<!--End Site popup -->

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
  </div>
  </div>
<p></p>
<div class="container-fluid">
	<div class="row">
		<div class="col-sm-6">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
					<input type="text" id="popupSerialID" value="${popupSerialID}" readonly style="width:150px; height:37px"  class="ui-widget ui-widget-content ui-corner-all form-control text-input"  />
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
					<span class="input-group-text" style="width:85px">ID</span>
					<input type="text" id="popupModePartNumID" class="form-control text-input" readonly value="${popupModPartNumID}" style="width:180px; height:37px;"   />
				</div>
			</div>
		</div>	
		
  </div>
</div>

 </div>
</div>
  </div>
  <div class="modal-footer">
<!-- 	<button name ="previousRowModPart" class ="btn btn-default">Previous</button>
	<button name="nextRowModPart" class ="btn btn-default">Next</button> -->
</div>	
								                
	</div>
</div></div></div>

	
<!--End ModelsAndPartnumbers popup -->

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
 
 
 <script type='text/javascript'>

 
 $(document).on("triggerPNBoqListenersEvent", function () {
     $(function(){


   	     boqArrayPNum = [];
   	     boqArrayPNum = ${listArPartNumber};
    	 for(i = 0; i< boqArrayPNum.length; i++){
        	 
    		 PNboqAutocomplete(i,"partmodTab");
		 					 
    		 }		
						   
         });
     						  
 });


 $(document).on("triggerSeialNbBoqListenersEvent", function () {
     $(function(){


   	     boqArrayPNum = [];
   	     boqArrayPNum = ${ListArSerialNumber};
    	 for(i = 0; i< boqArrayPNum.length; i++){
        	 
    		 SeialNBboqAutocomplete(i,"ARserialNumberTab");
		 					 
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
        	 
    		 NodeboqAutocomplete(i,"partnodeTab");
		 					 
    		 }		
						   
         });
     						  
 });
 
 </script>	
 
 
 
 <script>
 
 if ('${docStatus}' == "addNew") {
		$("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});	
		$(".nextprvItems").addClass("hide-row");
		$(".pad").removeClass("hide-row ");
	}
	 
 $("#Disappear").click(  function() {	
	   
	 var Status=$("#arstat").val();
		 $("#arstat").val('Disappear').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#Maintain").click(  function() {	
	   
	 var Status=$("#arstat").val();
		 $("#arstat").val('Maintenance').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#RunIt").click(  function() {	
	   
	 var Status=$("#arstat").val();
		 $("#arstat").val('Running').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})

 var dictModPart = [];

 function getselectedrowsItemPNum () {

	 dictModPart = [];
	
		var modPartObj = {};
		
	
		$("#partmodTab > tbody").find('input[name="record"]').each(function(){

								   
			modPartObj.itemPartNum = $(this).parent().parent().children('td[name="itemPartNum"]').children('input').val();
			modPartObj.itemModel= $(this).parent().parent().children('td[name="itemModel"]').children('input').val();									   
     		modPartObj.qtyPartNum= $(this).parent().parent().children('td[name="qtyPartNum"]').children('input').val();
			modPartObj.arItemID= $(this).parent().parent().children('td[name="arItemID"]').children('input').val();
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
////////////all seleted site rows//////
var dictSite = [];
 function getselectedrowsSite () { 

dictSite = [];
					
 		$("#sitetab > tbody").find('input[name="recordsite"]').each(function(){



                    dictSite.push({
 			    	"wareID" : $(this).parent().parent().children('td[name="wareID"]').children('input').val(),
 			    	"siteID" : $(this).parent().parent().children('td[name="siteID"]').children('input').val(),
 			    	"siteName" : $(this).parent().parent().children('td[name="siteName"]').children('input').val(),													
 			    	"arSiteID" : $(this).parent().parent().children('td[name="arSiteID"]').children('input').val()
 			    	
                    });																								   
 		 });
 }
 
/////////select rows added by mohammad/////
 var dictNode = [];


 function getselectedrowsNodeNum () {

	 dictNode = [];

		$("#partnodeTab > tbody").find('input[name="record1"]').each(function(){
				 
			    	dictNode.push({											
			    	"nodeID": $(this).parent().parent().children('td[name="nodeID"]').children('input').val(),												
			    	"node_Name" : $(this).parent().parent().children('td[name="node_Name"]').children('input').val(),		
			    	"arNodeID" : $(this).parent().parent().children('td[name="arNodeID"]').children('input').val()
			    	});
			 
			 
  

		});
		      
}



 //////////////////////////////////////
 
 /////////select all rows serial number added by khouloud/////
 var dictSerialNumber = [];

	
 function getselectedrowsSerialNB() {

dictSerialNumber = [];
						 
	
		$("#ARserialNumberTab > tbody").find('input[name="chekbox_rowSerialNb"]').each(function(){

                    dictSerialNumber.push({
	
			    	"SNserialNumber" : $(this).parent().parent().children('td[name="SNserialNumber"]').children('input').val(),							   
			    	"SNmodel" : $(this).parent().parent().children('td[name="SNmodel"]').children('input').val(),			   
			    	"SNpartNumber" : $(this).parent().parent().children('td[name="SNpartNumber"]').children('input').val(),
			    	"SNnodeID" : $(this).parent().parent().children('td[name="SNnodeID"]').children('input').val(),
			    	"SNnodeName" : $(this).parent().parent().children('td[name="SNnodeName"]').children('input').val(),
			    	"SNsite": $(this).parent().parent().children('td[name="SNsite"]').children('input').val(),
			    	"SNposition" : $(this).parent().parent().children('td[name="SNposition"]').children('input').val(),
			    	"arSerialID" : $(this).parent().parent().children('td[name="arSerialID"]').children('input').val()
  			    	
                    });

		
	  
																													 
					});
	
	      
}
///////// End select all rows serial number added by khouloud/////
 

 function onlyOne(checkbox) {
	    var checkboxes = document.getElementsByName('primary')
	    checkboxes.forEach((item) => {
	        if (item !== checkbox) item.checked = false
	    })
	}





// LOADING MODEL & PART NB TAB 

 if ('${listArPartNumber}' != "addNew") {

	  boqArrayPNum = [];
	  boqArrayPNum = ${listArPartNumber};

	  
	  for (i = 0;i<boqArrayPNum.length;i++){
	  	
		   		var itemPartNum = boqArrayPNum[i].itemPartNb;
		   		var primary = boqArrayPNum[i].primary;
		   		var itemModel = boqArrayPNum[i].itemModel;
		   		var qtyPartNum = boqArrayPNum[i].qtyPartNb;
			    var arItemID = boqArrayPNum[i].itmId;		


		   	if(primary=='1')
		   	   	{primary="checked"; }
		   	else { primary="";}

		   	if(itemPartNum == null)
		   		{itemPartNum = "";}
		   	else {itemPartNum = boqArrayPNum[i].itemPartNb;}

		   	if(itemModel == null)
		   		{sitemModel = "";}
		   	else {itemModel = boqArrayPNum[i].itemModel;}
		   
		   	
		 var itemPNumRow = "<tr>";
		 itemPNumRow= itemPNumRow + "<td><input type='checkbox' name='record'><button type = 'button' href='#' name='popUpMenu' onclick='openModPartPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		 itemPNumRow =itemPNumRow + "<td name='itemModel'><input name='itemModel' id = 'itemModel"+i+"'  type='text' value='" +itemModel+ "' style='width:200px;' class='form-control input-text'/></td>";
		 itemPNumRow =itemPNumRow + "<td name='itemPartNum' style='width:200px'><input name='itemPartNum' id = 'itemPartNum"+i+"' style='width:200px'  type='text' value='"+itemPartNum+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemPNumRow =itemPNumRow + "<td name='qtyPartNum' style='width:200px'><input name='qtyPartNum' style='width:200px'  type='text' value='"+qtyPartNum+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemPNumRow =itemPNumRow + "<td name='arItemID' style='width:200px'><input name='arItemID' readonly style='width:200px'  type='text' value='"+arItemID+"' style='width:200px;' class='form-control input-text' /> </td>";																																																					
		 itemPNumRow =itemPNumRow +"<td name='primary' style='width:120px'>	<input  type='checkbox' onclick='onlyOne(this)' name='primary' "+primary+" /></td>";
		 itemPNumRow =itemPNumRow + "</tr>";

	  
	$("#partmodTab > tbody").append(itemPNumRow);

	  }

	  
	$(document).trigger("triggerPNBoqListenersEvent"); 
		
	  
}






/// NODE TAB LOAD

if ('${ListNode}' != "addNew") {

	  boqArrayNode = [];
	  boqArrayNode = ${ListNode};

	  for (i = 0;i<boqArrayNode.length;i++){

		   		var nodeID = boqArrayNode[i].nodeID;
		   		var nodeName = boqArrayNode[i].nodeName;
				var arNodeID = boqArrayNode[i].nodearId;							   


		   		if(nodeID == null)
			   		nodeID = "";
			   	else nodeID = boqArrayNode[i].nodeID;

			   	if(nodeName == null)
			   		nodeName = "";
			   	else nodeName = boqArrayNode[i].nodeName;
		   		
		   		var itemNodeRow = "<tr>";
		   		itemNodeRow= itemNodeRow + "<td style='text-align:center;'><input type='checkbox' name='record1'><button type = 'button' href='#' name='popNodeUpMenu' onclick='openNodePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   		itemNodeRow =itemNodeRow + "<td name='nodeID'><input name='nodeID' id = 'nodeID"+i+"'  type='text' value='" +nodeID+ "' style='width:200px;' class='form-control input-text'/></td>";
		   		itemNodeRow =itemNodeRow + "<td name='node_Name' style='width:200px'><input name='node_Name'  id = 'node_Name"+i+"'  style='width:200px'  type='text' value='"+nodeName+"' style='width:200px;' class='form-control input-text' /> </td>";
				itemNodeRow =itemNodeRow + "<td name='arNodeID'><input name='arNodeID'  type='text' value='"+arNodeID+"' readonly style='width:200px;' class='form-control input-text' /> </td>";																																										
                itemNodeRow =itemNodeRow + "</tr>";

				
			$("#partnodeTab > tbody").append(itemNodeRow); 
   }

	  $(document).trigger("triggerNodeBoqListenersEvent");
}






var siteRowindex =0;
////////////////list site BOQ//////////
if ('${ListSite}' != "addNew") {

	  boqArraySite = [];
	  boqArraySite = ${ListSite};

	  for (i = 0;i<boqArraySite.length;i++){

		   		var siteID = boqArraySite[i].siteID;
		   		var siteName = boqArraySite[i].siteName;
		   		var wareID = boqArraySite[i].wareID;
				var arSiteID = boqArraySite[i].arsiteId;							   


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
		   		itemSiteRow= itemSiteRow + "<td style='text-align:center;'><input type='checkbox' name='recordsite'><button type = 'button' href='#' name='popUpSiteMenu' onclick='openSitePopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		   		itemSiteRow =itemSiteRow + "<td name='wareID' style='width:200px'><input name='wareID' id = 'wareID"+i+"' style='width:200px'  type='text' value='"+wareID+"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /> </td>";
		   		itemSiteRow =itemSiteRow + "<td name='siteID'><input name='siteID' id = 'siteID"+i+"' style='width:200px;'  type='text' value='" +siteID+ "' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input'/></td>";
		   		itemSiteRow =itemSiteRow + "<td name='siteName' style='width:200px'><input name='siteName' id = 'siteName"+i+"' style='width:200px'  type='text' value='"+siteName+"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /> </td>";
				itemSiteRow =itemSiteRow + "<td name='arSiteID'><input name='arSiteID' readonly style='width:200px'  type='text' value='"+arSiteID+"' style='width:400px;' class='ui-widget ui-widget-content ui-corner-all form-control text-input' /> </td>";
		   		itemSiteRow =itemSiteRow + "</tr>";

				
			$("#sitetab > tbody").append(itemSiteRow);
   }

	  $(document).trigger("triggerSiteBoqListenersEvent");
}

/////////////////////////////////////////

/////////list serial number added by khouloud/////

var serialRowindex =0;
if ('${ListArSerialNumber}' != "addNew") {

	  boqArraySNum = [];
	  boqArraySNum = ${ListArSerialNumber};

	  for (i = 0;i<boqArraySNum.length;i++){
	  	
		   		var inputSerialNb = boqArraySNum[i].serialNumber;
		   		var inputModel = boqArraySNum[i].model;
		   		var inputpartNumber = boqArraySNum[i].partNumber;
		   		var inputNodeID = boqArraySNum[i].snodeID;
		   		var inputNodeName = boqArraySNum[i].snodeName;
		   		var inputsite = boqArraySNum[i].site;
		   		var inputPosition = boqArraySNum[i].position;
				var arSerialID = boqArraySNum[i].serialId;								 
		   		


		   

		   	if(inputSerialNb == null)
		   		inputSerialNb = "";
		   	else inputSerialNb = boqArraySNum[i].serialNumber;

		   	if(inputModel == null)
		   		inputModel = "";
		   	else inputModel = boqArraySNum[i].model;

		   	if(inputpartNumber == null)
		   		inputpartNumber = "";
		   	else inputpartNumber = boqArraySNum[i].partNumber;

		   	if(inputNodeID == null)
		   		inputNodeID = "";
		   	else inputNodeID = boqArraySNum[i].snodeID;

		   	if(inputNodeName == null)
		   		inputNodeName = "";
		   	else inputNodeName = boqArraySNum[i].snodeName;

		   	if(inputsite == null)
		   		inputsite = "";
		   	else inputsite = boqArraySNum[i].site;

		   	if(inputPosition == null)
		   		inputPosition = "";
		   	else inputPosition = boqArraySNum[i].position;

		   	
		   
		   	
		 var itemSNumRow = "<tr>";
		 itemSNumRow= itemSNumRow + "<td style='text-align:center;'><input type='checkbox' name='chekbox_rowSerialNb'><button type = 'button' href='#' name='popUpMenu' onclick='openSerialPopUp(this)' class='btn btn-default'  style='position:relative;left:3px;'><i class='fas fa-desktop'></i></button></td>"
		 itemSNumRow =itemSNumRow + "<td name='SNserialNumber'><input name='inputSerialNb'  id = 'inputSerialNb"+i+"'   type='text' value='" +inputSerialNb+ "' style='width:200px;' class='form-control ui-widget ui-widget-content ui-corner-all  input-text'/></td>";
		 itemSNumRow =itemSNumRow + "<td name='SNmodel' style='width:200px'><input name='inputModel' id = 'inputModel"+i+"' style='width:200px'  type='text' value='"+inputModel+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemSNumRow =itemSNumRow + "<td name='SNpartNumber' style='width:200px'><input name='inputpartNumber' id = 'inputpartNumber"+i+"'  style='width:200px'  type='text' value='"+inputpartNumber+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemSNumRow =itemSNumRow + "<td name='SNnodeID' style='width:200px'><input name='inputNodeID'  id = 'inputNodeID"+i+"' style='width:200px'  type='text' value='"+inputNodeID+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemSNumRow =itemSNumRow + "<td name='SNnodeName' style='width:200px'><input name='inputNodeName' id = 'inputNodeName"+i+"' style='width:200px'  type='text' value='"+inputNodeName+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemSNumRow =itemSNumRow + "<td name='SNsite' style='width:200px'><input name='inputsite' id = 'inputsite"+i+"'  style='width:200px'  type='text' value='"+inputsite+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemSNumRow =itemSNumRow + "<td name='SNposition' style='width:200px'><input name='inputPosition' style='width:200px'  type='text' value='"+inputPosition+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemSNumRow =itemSNumRow + "<td name='arSerialID' style='width:200px'><input name='arSerialID' style='width:200px' readonly type='text' value='"+arSerialID+"' style='width:200px;' class='form-control input-text' /> </td>";
		 itemSNumRow =itemSNumRow + "</tr>";
		
	$("#ARserialNumberTab > tbody").append(itemSNumRow);
	  
	  }


	  $(document).trigger("triggerSeialNbBoqListenersEvent");   
	  
}










 	
 ////////////////////////end add site rows///////////////////////


 $(".add-row").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});

 $(".add-rowARserialNb").click(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});
 $(".add-rowNode").click(function() {
	 
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
	});

///////////site BOQ////
 $(".addsite-row").click(function() {
    $("#formStatus").text("Not Saved");
    $('.dot').css({"background-color" : "orange"});
 });
///////////////////////////////serial number ///////////////////
 $(".add-row2").click(function() {
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
				
			$("#itmdescrip").on('keyup change', function () {
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
 
 
 $("#saveButton").click(  function() {
	 flagFrom = "save";
	 
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
        }
        
     // validate end of life date cannot be null
     val = $("#endoflive").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
         
          alert('End of life Date is not valid');
          return false;
          //$("#txtDate").val($.datepicker.formatDate("mm-dd-yy", new Date()));
        }
	 
	 var arservice ;
	  if (servise1.checked == true){
		  arservice = '1';
		  } else
		  
			  {		
			  
			  arservice = '0';}
	  
	 var ardisabled ;
	 if (disables.checked == true){
		 ardisabled = '1';
		  } else
			  {				
			  
			  ardisabled = '0';}
	 
	 var artech2 ;
	 if (techg2g.checked == true){
		 artech2 = '1';
		  } else
			  {artech2 = '0';}
	 
     var checkt3 = document.getElementById("techg3g");
	 var artech3 ;
	 if (techg3g.checked == true){
		 artech3 = '1';
		  } else
			  {artech3 = '0';}
			  
     var checkt4 = document.getElementById("techg4g");
	 var artech4 ;
	 if (techg4g.checked == true){
		 artech4 = '1';
		  } else
			  {artech4 = '0';}

	 var checkt5 = document.getElementById("techg5g");
	 var artech5 ;
	 if (techg5g.checked == true){
		 artech5 = '1';
		  } else
			  {

			  artech5 = '0';}

	 
     
     
     getselectedrowsItemPNum();
     
     
     getselectedrowsNodeNum();
	 
      getselectedrowsSite();
     //////////////////////////////
     
     
	 getselectedrowsSerialNB();
     //////////////////////////////
     
	 var itemCat = ($("#itmcat").val().split(":"))[0];
 	 var itemCatCode = ($("#itmcat").val().split(":"))[1];
 	 
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/AssetRegistryFormSave",
			dataType : "json",
			data : {

				"dictParameteritemPartnum" : dictModPart,
				"dictParameternode" : dictNode,
				"dictParametersite" : dictSite,
				"dictParameterserialNumber": dictSerialNumber,
				"slctDelNode" : slctDelNode,
				"slctDelSite" : slctDelSite,
				"slctDelSerial" : slctDelSerial,
				"slctDelModPart" : slctDelModPart,
			    "arID" : $("#arcode").val(),
				"aritemCode" : $("#itmcode").val(),
				"arcreatedDate" : $("#createdate").val(),
				"arlastModifiedDate" : $("#lstmodifdate").val(),
				"aritemName" : $("#itmname").val(),
				"aritemPosition" : $("#itemPosition").val(),
				"arunit" : $("#unt").val(),
				"aritemDescription" : $("#itmdescrip").val(),
				"ardomain" : $("#domaine").val(),
				"arStatus": $("#arstat").val(),
				"itemCategory" : itemCat,  
				"itemCatCode" : itemCatCode,
				"itemCatID" : $("#itmcatid").val(),
				"itemRootCat" : $("#itmrootcat").val(),  
				"aritemType" : $("#itmtyp").val(),
				"arcableType" : $("#cbltype").val(),
				"arweight" : $("#weit").val(),
				"arweightUOM" : $("#weituom").val(),
				"arlength" : $("#len").val(),
				"arwidth" : $("#widths").val(),
				"arheight" : $("#heit").val(),
				"arsizeUOM" : $("#siseuom").val(),
				"arservice" : arservice,	 
				"arendOfLife" : $("#endoflive").val(),
				"arvaluationRate" : $("#valuarate").val(),
				"ardisabled" : ardisabled,	
				"aritemImage" : $("#itmimage").val(),
				"arwarrantyPeriod" : $("#warrantyper").val()	,
				"artech2G" : artech2,	
				"artech3G" : artech3,	
				"artech4G" : artech4,
				"artech5G" : artech5,
				"itemNameReg" : $("#itemNameReg").val(),
				/* "email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(), */
											
			},
			success : function(data) {
				console.log("The returned data is " +data.BassamTest);
				$('#lstmodifdate').val(data.arlastModifiedDate);
				$("#arcode").val(data.arID);
				var param ="${pageContext.request.contextPath}/AssetRegistryFormView?arID="+$("#arcode").val()+"&NavAction=2";
				location.replace(param);
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
		
	
 })
 var slctDelModPart = []; 
 var slctDelNode = [];
 var slctDelSite = []; 
 var slctDelSerial = []; 

			




 

	    		    
											
 </script>
 
 
 <script type='text/javascript'>
	$(document).ready(function() {
		
		var ctx = window.location.pathname;

/**
		// MODEL & PARTNUMBER TAB AUTOCOMPLETE
		newRowCount =  $("#partmodTab >tbody tr").length-1;
  		PNboqAutocomplete(newRowCount,"partmodTab");

   	    // SERIAL NUMBER TAB AUTOCOMPLETE
  		SNnewRowCount =  $("#ARserialNumberTab >tbody tr").length-1;
  		SeialNBboqAutocomplete(SNnewRowCount,"ARserialNumberTab");

   	    // SITE TAB AUTOCOMPLETE
  		SitenewRowCount =  $("#sitetab >tbody tr").length-1;
  		SiteboqAutocomplete(SitenewRowCount,"sitetab");

   	    // NODE TAB AUTOCOMPLETE
  		NodenewRowCount =  $("#partnodeTab >tbody tr").length-1;
  		NodeboqAutocomplete(NodenewRowCount,"partnodeTab");




/*
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
$("#email").autocomplete({
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
     console.log("Error");
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

*/

 if ('${docStatus}' != "addNew" ){
	 
	aritem = ${ListARItem};
console.log(aritem);
console.log("zeinaaaaaaaa");
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
	var len=aritem.length;
	console.log("The lenght is " + len);
	var itemCodeId;   
	var collen= count(aritem[0]);
	console.log("The count is " + collen);
	   var i =0;
	   var itemRow = "<tr>";
	   //itemRow= itemRow + "<td><input type='checkbox' name='record'></td>"
	   //console.log(itemRow);
	   for (j = 0; j < collen; j++) {
	   	var tt1= aritem[i][j];
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
	   $("#bisotab > tbody").append(itemRow);
	}
	else {
		
	   // set status to new and green while new record
	    $("#formStatus").text("New");
		 $('.dot').css({"background-color" : "orange"});
	}

	});
			

        // autocomplete for itemCode in Description Tab
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


// autocomplete for itemName in Description Tab
$("#itmname").autocomplete({
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
										

		

		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${ARCount}' != "addNew"){

							
					var ARCount = ${ARCount};
					
					if(($("#arcode").val()) != "" && ($("#arcode").val()) != null){

					if(SelectedIndex === ARCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									
									var param ="${pageContext.request.contextPath}/AssetRegistryFormView?arID="+$("#arcode").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/AssetRegistryFormView?arID="+$("#arcode").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/AssetRegistryFormView?arID="+$("#arcode").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/AssetRegistryFormView?arID="+$("#arcode").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}
					$("#label-1").text((SelectedIndex)+"/"+ARCount);

					 $("#selectnav").autocomplete({
			    			
			    		    source: function(request, response) {
			    			    
			    		             $.ajax({
			    		                 type: "GET",
			    		                 contentType: "application/json; charset=utf-8",
			    		                 url: '${pageContext.request.contextPath}/GetAllAR',
			    		                 data: {
			    								"requestValue" : request.term,
			    						 },
			    		                 dataType: "json",
			    		                 success: function (data) {
			    		                     if (data != null) {
			    		                         response(data.listAR);
			    		                     }
			    		                 },
			    		                 error: function(result) {
			    		                     console.log("Error");
			    		                 }
			    		             });
			    		         }, minLength:0, maxShowItems: 40, scroll:true,

			    					select: function(event, ui) {
			    						
			    						this.value = (ui.item ? ui.item[0]  + ":" + ui.item[2]  + ":" + ui.item[3] : '');
			    						var param ="${pageContext.request.contextPath}/AssetRegistryFormView?arID="+(ui.item[0])+"&NavAction=2";
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
			           					});   //// ENd of Autocomplete for Area ID
			    	
		
    	
		


 // to add New in orange of a new record using add button from AssetregistryList	
	if ('${ListPRqItem}' != "addNew") {
	}
	else {
	          // set status to new and green while new record
	           $("#formStatus").text("New");
					$('.dot').css({"background-color" : "orange"});
	       }	

	if ('${ListPRqItem}' != "addNew") {
	}
	else {
	          // set status to new and green while new record
	           $("#formStatus").text("New");
					$('.dot').css({"background-color" : "orange"});
	       }

</script>


 
 
 </html>
