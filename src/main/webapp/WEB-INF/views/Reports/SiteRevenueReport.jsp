<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Site Revenue Report</title>
     <link rel="shortcut icon" href="">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
        <!--  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
        <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
        <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
       <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        
	    <!-- site revenue report scripts -->
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Reports/SiteRevenueReport.js"></script>
	   
	    <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
			
		<!--  Chart script -->
         <script type="text/javascript"	src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.js"></script>
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.charts.js"></script>
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.candy.js"></script>
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.zune.js"></script>
         <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.ocean.js"></script>
 
	   <!--  MultiSelect Script -->
       <link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet"    >
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>	
	   <!-- Tags InputScript -->
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tagsInputAutocomplete.js"></script>
	
	   <!-- Google Maps Script -->
	   <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	   <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Reports/SiteRevenueGoogleMaps.js"></script>
	
	   <!-- export scripts -->
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
       <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
       <!--<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
       <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>-->
	    
</head>
<style>
.pos{
position:relative;
    padding: 10px;
    text-align:center;
    font-size:20px;
    color: #4169E1;
}
.wid{
width:100%;
}
.ui-autocomplete {
	            	max-height: 270px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 0px;
					font-size:15px;
					z-index:9999;
					
	        		}
#clearButton{
background-color:white;
color:orange;

}

#clearButton:hover{
background-color:orange;
color:white;


}

.BTN{
width:90px !important;


}
.flex {
  display: flex;
  justify-content: center;
}

.flex-item + .flex-item {
  margin-left: 10px;
  margin-bottom:5px;
}

#mapContainer {
height: 700px;
}
        
.legendHeader {
overflow: hidden;
background-color: #08526d;
padding: 10px 0px;
}

.btn-color {
background-image: linear-gradient(to right top, #b3b3b3, #b6b6b7, #b8b9ba, #bbbdbd, #bdc0c0, #b1b5b5, #a5abaa, #9aa09f, #7f8685, #656e6c, #4c5655, #343f3e);
}
  
.legendContainer{
height: 800px;
position: relative;
}

.box{
width: 100%;
height: 100%;            
position: absolute;
top: 20px;
left: 0;
}

.stack-top{
z-index: 3;
margin: 70px; 
}

.dot {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
}
.dotYellow {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#FFDC00;

}

.dotBlue {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#0080ff;

}
.dotRed {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:red;

}

.green {
background:green;
}

.redDark {
background:#4D0207;
}
.blue {
background:#0080ff;
}

.yellow {
background:#FFDC00;
}


.red{
background:red; }

.pink{
background:#FF00FF;
}

.purple{
background:#8A2BE2;
}
.cadr{
border:0.01em solid grey;
}


.cadr2{
border:0.1em solid #808080;
padding:40px;
}

.title {
  margin:5px 0px;
  font-size: 25px;
  font-weight:600;
  font-family: 'Times New Roman', Times, serif;
}
.canvas-style{
height: 650px; 
}
.canvas-style2{
height: 400px !important; 

}
.canvas-style3{
height: 650px !important; 

}

/*This will style the icon button for chart*/
.buttonStyle{
    font-size: 20px;
    color:#444444;
	margin-top:10px;
	background: none;
	border: none;
}
    /*This should make them change their color when they are hovered*/
    .buttonStyle:hover {
         color:#08526d;
    }


.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 400px;
  overflow : auto;
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


.tags-input{
    width: 100%;
}

.tags-input input{
    border: none;
    min-height: 100px;
}

.tags-input input:focus{
    border: none;
    box-shadow: none;
    outline: none;
    padding-top: 0px !important;
    padding-bottom: 0px !important;

}


.tags-input .tag{
    padding-top:2px ;
    padding-bottom: 2px;
    border: 2px solid #5f6368;
    border-radius: 10px;
    padding-left: 7px;
    padding-right: 7px;
    font-size: 12px;
    color: black;
    margin: 5px;
    display: inline-block;
}

.tags-input .tag .text{
    margin-right: 5px;
}
.tags-input .tag .close{
    border-radius: 50%;
    min-height: 20px;
    padding-left: 4px;
    cursor: -webkit-grabbing;
    cursor: grabbing;
    padding-right: 4px;
    color: black;
    font-weight: bolder;
}




.bootstrap-tagsinput .tag [data-role="remove"]:after {
    content: "x";
    padding: 0px 5px;
}
   
    
</style>
<body>
<%--  <c:set var = "page" value = "report"/> --%>

<%-- 	<%@ include file="../header.html" %> --%>
  <c:set var="pg" value="report" scope="session"  />
 <jsp:include page="../header.jsp"></jsp:include>

  
	 <div Style=" left: 0; bottom: 0;" id="Revenue Div">

	<div class="container-fluid">     
	     <div class="row">
	     
	     <p></p>
		
		 </div>
	

			<div class="row second" >	
			
			<div class="col-md-2" style="display: flex; ">
				<div class="form-group "style="margin-left: 15px; ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Site Revenue Report</span>
					</div>
				</div>
			</div>
				
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="startdate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
					   <div class="input-group-append" data-target="#datetimepicker3" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
                </div>
			</div>
			
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker4" data-target-input="nearest">
					<span class="input-group-text">End Date</span>
					<input type="text" id="enddate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
					   <div class="input-group-append" data-target="#datetimepicker4" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i>
							</div>
						</div>
				</div>
                </div>
			</div>
			
				
	
			
			<div class="col-md-4" id="col3" style="text-align:right;">
		 		<div class="btn-group pull-right"  style="padding: 0px !important;">
		 			<div class="glyph" style="padding-top:0px; padding-right: 10px;">
                           <div class="form-group">
			               <div class="input-group-prepend" data-target-input="nearest">
					       <div class="input-group-text">	
      				       <button type="button" class="btn" name="fields" id="FieldsOption" data-toggle="modal" data-target="#Modal" ><i class="fa fa-filter" style="font-size:20px"></i></button>
				         </div>
                        </div>
	                </div>
	                </div>
		 			<div class="glyph" style="padding-top:0px;">
			 			 <div class="dropdown"  Style="margin-right:10px; height:30px;">
	                        <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Menu	</button>
	
	                       <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton" id = "dropRight" Style="width:10px;">
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="edit" href="#">Edit</a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="print" href="#">Print</a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item dropdown-toggle" id="export" href="#" >Export<span class="caret" Style="padding-left:30px;" ></span></a>
                             
                              <ul class="dropdown-menu dropdown-menu-left" id="dropLeft" Style="left:-12.5rem !important;  max-height: max-content !important; max-width: max-content !important;">
                               
                                <li><a class="dropdown-item" id="allExport" href="#" >All</a></li>
                             
                                 <li><a class="dropdown-item" id="mapExport" href="#" >Map</a></li>
                             
                                 <li><a class="dropdown-item" id="gridExport" href="#" >Grid</a></li>
                              
                                 <li><a class="dropdown-item" id="chartsExport" href="#" >Charts</a></li>
                             
                             </ul>
                           </li>
    	                  
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="pdf" href="#">PDF</span></a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="setup" href="#">Setup Auto Email</a> </li>
    	                   <li class="dropdown-submenu"><a class="dropdown-item" id="addCol" href="#">Add Column</a> </li>
    	                  </div>
			           </div>  
			            
		        </div>
		       
		 			<div class="glyph" Style="white-space: nowrap;overflow: hidden;">
			 			
		                  <button type="button" id="generate" class="btn btn-primary BtnActive" > Generate Report </button> 
			
			
			</div>
			</div>
		</div>
			
</div>

<div class="container-fluid" >     
	  

<!-- Modal Filter Options -->
             <div class="modal fade custom-class-assignedto-modal" id="Modal" tabindex="-1" role="dialog"
                aria-labelledby="myModalLabel" aria-hidden="true" >
                <div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered" role="document" id="model" >
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" id="myModalLabel1" style="font-weight:bold; color: #3C1596;position:relative;top:4px;">  Filter Options </h5>
                            
                            <button type="button" style="border:none;outline:none;position:relative;top:7px;"  name="closePopup" class ="close" data-dismiss ="modal" id="mapoperations"> <i class='fa fa-times'></i> </button>
                           
                            <a class="close modalMinimize ml-3" style="position:relative;top:7px;"> <i class='fa fa-minus icon-to-change' ></i> </a>
                            
                          
                           
                        </div>
  <div class="modal-body">
   		<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
  		<li class="nav-item">
   			<a class="nav-link active " id="revenueColor-tab" style="color: gold;" data-toggle="tab" href="#revenueColor" role="tab" aria-controls="revenueColor" aria-selected="true">REVENUE COLOR</a>
  		</li>
  		<li class="nav-item">
   			 <a class="nav-link " id="notExistingSite-tab" style="color: gold;" data-toggle="tab" href="#notExistingSites" role="tab" aria-controls="notExistingSites" aria-selected="false">OTHER SITES</a>
  		</li>
	    </ul>
	<div class="tab-content">
 
	
  <div class="tab-pane active" id="revenueColor" role="tabpanel" aria-labelledby="revenueColor-tab">
    					
   <table id="sitesTech">  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    
  </tr>
  
  <tr>
     
     <td style="position: relative;top:-8px;left:50px;"><input style="position: relative;top: 11px;" type="checkbox" name="enable" id="enable" /></td>
    
    <td style="position: relative;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >ENABLE</label></td>
      
     <td style="position: relative;top:-8px;left:160px;"><input style="position: relative;top: 11px;" type="checkbox" name="disable" id="disable" checked/></td>
    
    <td style="position: relative;left:170px;"><label style="color:black;font-weight:bold;font-size:2ex; " >DISABLE</label></td>
    
    
  </tr>
   
  
 
   
  </table>
  
  <div> 
<p></p>
	<form>
		<div class="table-responsive-sm " id="revTable"> 
		<h5 id ="alertMsg" style="font-weight:bold; color: red;position:relative;top:4px;right:-150px;"></h5>
		
			<table id ="revenueColorTable" class="table table-striped table-bordered table-sm" style="display:fixed;overflow-y: auto;">	
				<thead>
					<tr>
					<th  style=" background-color: #00757C; color:gold;"><div style="width:80px;"><button type="button" id="selectAll" style="margin-left:10px " class="main">
					    <span class="sub"></span>Select</button></div></th>
					 <th style=" background-color: #00757C; color:gold; width:232px ">From</th>
					 <th style=" background-color: #00757C;color:gold; width:232px ">To</th>
					 <th  style=" background-color: #00757C;color:gold; width:232px "><div style="width:200px">Color</div></th>
						              
					</tr> 
					
					<tr style='background-color: #0080FF '>
					<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td>
					<td name='from'>
					<input type='text' name='fromValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' data-default="0" value="0" />
					</td>	
					<td name='to'>
					<input type='text' name='toValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' data-default="350" value="350">
					</td>
					<td name='color'>
					<select class='ui-widget ui-corner-all form-control'>
					  <option value="#0080FF" selected>Blue</option>
              		  <option value="#FFDC00">Yellow</option>
               		  <option value="red">Red</option>
               		  <option value="#FF00FF">Pink</option>
               		   <option value="#8A2BE2">Purple</option>
               		  
               		  
					</select></td>
					
					</tr>
					
				
					
						
					<tr style='background-color: #FFDC00 '>
					<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td>
					<td name='from'>
					<input type='text' name='fromValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' data-default="351" value="351" />
					</td>	
					<td name='to'>
					<input type='text' name='toValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' data-default="700" value="700">
					</td>
					<td name='color'>
					<select class='ui-widget ui-corner-all form-control'>
					  <option value="#0080FF" >Blue</option>
              		  <option value="#FFDC00" selected>Yellow</option>
               		  <option value="red">Red</option>
               		  <option value="#FF00FF">Pink</option>
               		   <option value="#8A2BE2">Purple</option>
               		  
               		  
					</select></td>
					
					</tr>
					
						<tr style='background-color: red'>
					<td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td>
					<td name='from'>
					<input type='text' name='fromValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' data-default="701" value="701" />
					</td>	
					<td name='to'>
					<input type='text' name='toValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' data-default="999" value="999">
					</td>
					<td name='color'>
					<select class='ui-widget ui-corner-all form-control'>
					  <option value="#0080FF" >Blue</option>
              		  <option value="#FFDC00">Yellow</option>
               		  <option value="red" selected>Red</option>
               		  <option value="#FF00FF">Pink</option>
               		  <option value="#8A2BE2">Purple</option>
               		  
               		  
					</select></td>
					
					</tr>

					
					</thead> <tbody> </tbody> </table>
					</div>
					
						<button type="button"  class="btn btn-primary" style="color:white;" onclick="addRow()">Add Row</button>
						<button type="button" class="btn btn-primary deleteRow" style="color:white;" >Delete Row</button>
						<button type="button"  class="btn btn-primary" style="color:white;" onclick="applyChanges()">Apply Change</button>
						<button type="button"  class="btn btn-primary" style="color:white;" onclick="resetToDefault()">Reset</button>
				</form>
  </div></div>
  
   <div class="tab-pane" id="notExistingSites" role="tabpanel" aria-labelledby="notExistingSite-tab">
     <table id="notExistingSitesTable" style="width:70%;height:70%;margin-top:80px;">  
  		<tr>
		    <th style="position: relative;top: 15px;left:10px;"></th>
		    <th style="position: relative;top: 15px;left:10px;"></th>
		    <th style="position: relative;top: 15px;left:10px;"></th>
		    <th style="position: relative;top: 15px;left:10px;"></th>
  		</tr>
  
 	 <tr>
     <td style="position: relative;top:-8px;left:50px;"><input style="position: relative;top: 11px;" type="checkbox" name="warehouseAllSites" id="warehouseAllSites" /></td>
     <td style="position: relative;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Warehouse Sites</label></td>
     <td style="position: relative;top:-8px;left:160px;"><input style="position: relative;top: 11px;" type="checkbox" name="prepaidRevenueSites" id="prepaidRevenueSites" checked/></td>
     <td style="position: relative;left:170px;"><label style="color:black;font-weight:bold;font-size:2ex; " >Prepaid Revenue Sites</label></td>
   </tr>
   
   <tr>
    <td style="position: relative;top:-8px;left:50px;"><input style="position: relative;top: 11px;" type="checkbox" name="allSites" id="allSites" /></td>
    <td style="position: relative;left:60px;"><label style="color:black;font-weight:bold;font-size:2ex; ">All Sites</label></td>
   </tr>
  
 
   
  </table>
  </div>
  </div>
  
  <div class="modal-footer"></div>
</div></div></div> 
</div> 

	
<div class="row" >
				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Site</span>
							<input type="text" id="site"
								value="${site}" class="form-control text-input" />
								
								 <div class="input-group-append" id="chooseSiteName"
                                        onclick="chooseSiteName()">
                                        <div class="input-group-text" style="margin-left:10px;">
										<i class="fas fa-edit"></i>
                                        </div>
                                    </div>
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Area</span>
							<input type="text" id="Area"
								value="${Area}" class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Region</span>
							<input type="text" id="Region"
								value="${Region}" class="form-control text-input" />
						</div>
					</div>
				</div>
			
		<div class="col-md-3" style="float: right; padding-top:-100px;" >
			<div class="btn-group pull-right"  >
		 			<button type="button"  id ="clearButton"class="btn btn-light" style=" margin-top:-10px; margin-right:-15px; "  >Clear</button>
		 		</div>
		 	</div>
		
			</div>
			
			
			
			<div class="row second" >
			
	<div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                  
    	             <select id="period" class="form-control text-input" style="height:37px">
    	              <option value="null" >No Period Selected </option>
					   <option value="Daily" selected>Daily</option>
			           <option value="Weekly" >Weekly</option>
			           <option value="Monthly">Monthly</option>
			           <option value="Accu">Accumulated</option>
			           
		           
		            </select>
				</div>
		 </div>
			</div>
			
			
			 	  <div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                
	<div id="services"></div>
	</div>
	</div>
			</div>
			
			
			
			
		<div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                
	<div id="Prepostpaid"></div>
	</div>
	</div>
			</div>
			
		<div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                
	<div id="siteTechnologies"></div>
	</div>
	</div>
			</div>	
			

			
		
	     
	      <div class="col-md-auto">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Max" name="Max">
      					<span style="margin-left: 10px;">Maximum</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     
	      <div class="col-md-auto">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Min" name="Min">
      					<span style="margin-left: 10px;">Minimum</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     
	     	      <div class="col-md-auto">
          
            	<div class="form-group">
				   <div class="input-group-prepend" >
					<div class="input-group-text">
						
      					<input type="checkbox" value="1" id="noSiteName" name="noSiteName" checked>
      					<span style="margin-left: 10px;">No site name</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	    
			</div>
			
			
		
		
		
		
		
		
<div class="wrapper" style="margin-top:-10px;">
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
  <div class="panel panel-default" style="margin-bottom:3px;">
    <div class="panel-heading " role="tab" id="headingOne">
      <h4 class="panel-title">
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
         GIS
        </a>
      </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingOne">
      <div class="panel-body">
       <div class="legendContainer">
      <div class="card-body">
      
      
         <div class="box stack-top" id="legendDiv" style="overflow-y:scroll;position: relative;top:170px;width: 300px; float:left; height:320px;  background:white; margin:37px;display: none">
        
         <div class="legendHeader"  id="legendHeader">
   
 <h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6>
 
 <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAll()"  id="selectUnselect" >Unselect All</button>
  </div>
  
   <div id="tableDiv">
  <table id="sitesWithTech">
  
   <caption id="tableCaption" style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-210px;left:20px;">Sites Technology</caption>
  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    
  </tr>
  
  <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="2gSites" onclick="ShowHideMarkers('#FFDC00');" value="#FFDC00"/></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>
        
    
    <td style="position: relative;top: 25px;left:-40px;"><label style="color:black;font-weight:bold;font-size:2ex; " >2G Sites</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="3gSites" onclick="ShowHideMarkers('#0080FF');" value="#0080FF"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot blue"></div></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>
    
    <td style="position: relative;top: 25px;left:-40px;"><label style="color:black;font-weight:bold;font-size:2ex; " >2G, 3G Sites</label></td>
    
    
  </tr>
  
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="4gSites"  onclick="ShowHideMarkers('red');" value="red"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td>
    
    <td style="position: relative;top: 25px;left:90px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >2G, 3G, 4G Sites</label></td>
    
    
  </tr>
    
     <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="legendMaxRevenue" onclick="ShowHideMarkers('green');" value="green"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot green"></div></td>
            <td style="position: relative;top:30px;left:60px;"><div id="max-div" class="dot-max"></div></td>
    
    <td style="position: relative;top: 25px;left:-40px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap; " >Maximum Revenue</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="legendMinRevenue" onclick="ShowHideMarkers('#4D0207');" value="#4D0207"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot redDark"></div></td>
                <td style="position: relative;top:30px;left:60px;"><div id="min-div" class="dot-min"></div></td>
    
    <td style="position: relative;top: 25px;left:-40px;"><label style="color:black;font-weight:bold;font-size:2ex; white-space: nowrap;" >Minimum Revenue</label></td>
    
    
  </tr>
  
  <tr>
  
  </tr>
    
   
  </table>
  
</div>
        </div>
        </div>
         
    <div class="card-body">
        <div class="box" id="mapContainer">
         <canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
        </div>
        </div>
       </div>
      </div>
    </div>
  </div>
  
  <div class="panel panel-default" style="margin-bottom:3px;" >
    <div class="panel-heading" role="tab" id="headingTwo">
      <h4 class="panel-title">
       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          Grid Table
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingTwo">
      <div class="panel-body" >
          <!-- /.card-header -->
<div class="card-body " >
					<div class="row">
						<div class="col-sm-12">
							<div class="almgrid-container">
								<div class="row">
									<div class="col-sm-4 almgrid-pagecount-box">
										Show
										<select class="cmb-row-count almgrid-pagecount">
											<option value="10" selected>10</option>
											<option value="25">25</option>
											<option value="50">50</option>
											<option value="100">100</option>
											<option value="500">500</option>
											<option value="1000">1000</option>
										</select>
										Rows
									</div>
									<div class="col-md-4">
											<div id="loaderDiv" style="display: none;">
												<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
											</div>
									</div>
									<div class="col-sm-4 almgrid-global-search-box">
										Search
										<input type="text" class="form-control almgrid-global-search" />
									</div>
								</div>
								<div id="alertMsgDiv" style="display: none;padding-left: 40px">
								<br>
									<b style="color:red;font-size:15px;white-space: nowrap;">The number of original fetched data is exceeding the number of allowed data to show. Please set a filter to reduce the fetched data.</b> 
								</div>
								<div id= "tableGrid" class="table-responsive almgrid-table-div">
									<table id="gridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>Site 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Area
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
                                                   <th>Start Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th> End Date
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Voice Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

											
												<th>SMS Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>Data Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>VAS Revenue
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
											
											    <th>Combination Technology
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
											
												
												
													
													

											<tr>
												
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												<th><input type="text" class="almgrid-search" placeholder="Search"></th>
												
											</tr>
										</thead>
										<tbody>

										</tbody>

									</table>
								</div>

								<hr>
								<div class="pagination-div">
									<div class="row">
										<div class="col-sm-7">
											<p class="pagination-label">
												Viewing <span>0-0</span>
												of
												<span>0</span>
											</p>
										</div>
										<div class="col-sm-5 pagination-buttons">
											<nav aria-label="Page navigation">
												<ul class="pagination pagination-buttons justify-content-end">
													<li class="page-item"><button type="button"
															class="page-link pagination-previous pagination-button shadow-none">Prev</button>
													</li>
													<li class="page-item dropdown-pagination-numbers">
														<!-- <select class="form-control page-number-select shadow-none">
														</select> -->
														<div class="input-group page-number-group-div">
															<input type="text"
																class="form-control page-number-select shadow-none" />
															<input type="text"
																class="form-control page-number-span shadow-none" />
														</div>
													</li>
													<li class="page-item"><button type="button"
															class="page-link pagination-next pagination-button shadow-none">Next</button>
													</li>
												</ul>
											</nav>
										</div>
									</div>


								</div>

							</div>

						</div>
					</div>
				</div>
		
		
	<div style="display: Block;" id="totalNumbers">
		<div class="row second" style="padding-left: 15px;">								
			

<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Voice Revenue</span>
							<input readonly type="text" id="voiceRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>

<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">SMS Revenue</span>
							<input readonly type="text" id="smsRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>

				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Data Revenue</span>
							<input readonly type="text" id="dataRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
				
				
				
				
				
				
				
				
			<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">VAS Revenue</span>
							<input readonly type="text" id="grossRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
				

			
			</div>
			<div class="row second" style="padding-left: 15px;">
			
		<div class="col-md-auto">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Total Revenue</span>
							<input readonly type="text" id="totalRev"
								 class="form-control text-input" />
						</div>
					</div>
				</div>
			</div>
			
			
		</div>
      </div>
    </div>
  </div>
  <div class="panel panel-default" style="margin-bottom:3px;">
    <div class="panel-heading" role="tab" id="headingThree">
      <h4 class="panel-title">
        <a role="button" id="chartsPanel" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseThree">
         Charts
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingThree">
      <div class="panel-body" style="background:#f1f4f7 !important">
         <div class="card-body" id="chartscardBody" >
        
                 <!-- start of chart 21-06 -->
        	<!-- start second row of charts -->

			 <div id ="noCharts" style="display:none; text-align: center;vertical-align: middle;margin-top:15px;">
         
                 <h4 style="color: red;"> No Charts to Show </h4>
            </div>
			
			
			<div class="container-fluid-responsive" id= chartContainer>
			
			
				<div class="row" id="site_revenue_chart" style="background:white !important ;border: 3px groove #FDFEFE;">
				<canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
			<div class="col-md-12">
			
		

			

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
									
											<div class="card card-primary card-tabs cards-margin " id="chartContainerDiv">
											
												<!--  *************************** mschart tech  *************************** -->
												
	
											<div id="chartContainer3"  ></div>

										</div>
										
										
								

								
										
																				<!--  *************************** filter for chart  *************************** -->
											
												
																				<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;">
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2">
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepicker5" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdateMSChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker5"   />
																			   <div class="input-group-append" data-target="#datetimepicker5" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend" id="datetimepicker6" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddateMSChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker6"   />
																			 <div class="input-group-append" data-target="#datetimepicker6" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
																  	<div style="font-weight: bold;">Site Name</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend">
																<input type="text" id="siteNameMSChart" value="${siteNameMSChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id="MSChartSiteNameOpenPopup" onclick="MSChartSiteNameOpenPopup()">
																				  <div class="input-group-text">
																					  <i class="fas fa-edit"></i>
																				  </div>
																			  </div>
																		  
																		  </div>
																	  </div>
																	</div>
																  </div>
														   		  <!-- Options for the multidrop down for sites -->
																	   <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsMSChart"  class="form-control text-input" onchange="changeFunc();">
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	 <!--  <option value="contains">Contains</option>
																	
																	 <option value="isequalto" >Is Equal To</option>
																		 <option value="isempty">Is Empty</option>
																	   <option value="beginswith">Begins With</option>
																	  <option value="endswith">Ends With</option>
																	  <option value="greaterthan">Greater Than</option>
																	  <option value="lessthan">Less Than</option>  -->
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivMSChart">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsMSChart"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnMSChart" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValMSFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValMSFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																			<!-- -->
											
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;">Technologies</div>
								   																  
														    	                
															<!-- <div id="technology" class="form-group input-group-prepend wid" style="margin-left:5px;"></div> -->
																   <div class="form-group">
																	<div class="input-group-prepend">
																			  
																			<div id="msChartTechnologies"></div>

																	</div>
																		
															 </div> 
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="clearFilterMSChart">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtMSChart">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
										
										
										</div>
										</div>
										</div>
										</div>
		
		
		</div>
	
		</div>
		</div>
		
			
			
		<br>
			
				<div class="row" id="time_revenue_stack" style="background:white !important ;border: 3px groove #FDFEFE;">
				<canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
			<div class="col-md-12">
			
		

			

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
								  
													 
									
											<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
			
										  
																	 
												  
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											
												<!--  *************************** stack chart tech  *************************** -->
												
	
											<div id="chartContainerStacked"  ></div>

										</div>
										
										
								

								
										
																				<!--  *************************** filter for chart  *************************** -->
											
												
																				<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;">
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepicker7" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdateStackChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker7"   />
																			   <div class="input-group-append" data-target="#datetimepicker7" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend" id="datetimepicker8" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddateStackChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker8"   />
																			 <div class="input-group-append" data-target="#datetimepicker8" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
																  
																   <div style="font-weight: bold;">Site Name</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend">
																<input type="text" id="siteNameStackChart" value="${siteNameStackChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id="StackChartSiteNameOpenPopup" onclick="StackChartSiteNameOpenPopup()">
																				  <div class="input-group-text">
																					  <i class="fas fa-edit"></i>
																				  </div>
																			  </div>
																		  
																		  </div>
																	  </div>
																	</div>
																  </div>
														  
											 <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsStackChart"  class="form-control text-input" onchange="changeFunc();">
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivStackChart">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsStackChart"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnStackChart" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValStackedFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValStackedFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																	
																	<div id="stackChartTechnologies"></div>
																			  
																		 
																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="clearFilterStackChart">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtStackChart">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
										
										
										</div>
										</div>
										</div>
										</div>
		
		
		</div>
	
		</div>
		</div>
		
		
		<br>
		
		<div class="row" id="max_time_revenue_stack" style="background:white !important ;border: 3px groove #FDFEFE; display: none;">
			<canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
			<div class="col-md-12">
			
		

			

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
								  
													 
									
											<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
			
										  
																	 
												  
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											
												<!--  *************************** stack chart tech  *************************** -->
												
	
											<div id="MaxChartContainerStacked"  ></div>

										</div>
										
										
								

								
										
																				<!--  *************************** filter for chart  *************************** -->
											
												
																				<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;">
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepickerMax1" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdateStackChartMax" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMax1"   />
																			   <div class="input-group-append" data-target="#datetimepickerMax1" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend" id="datetimepickerMax2" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddateStackChartMax" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMax2"   />
																			 <div class="input-group-append" data-target="#datetimepickerMax2" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Site Name</span>
																<input type="text" id="siteNameStackChartMax" value="${siteNameStackChartMax}" class="form-control text-input" style="margin-bottom:20px;" disabled/></div>

											                     <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsStackChartMax"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivStackChartMax">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsStackChartMax"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnStackChartMax" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValStackedFltrMax" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValStackedFltrMax" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																			<div id="maxStackChartTechnologies"></div>  
																		 
																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="clearFilterStackChartMax">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtStackChartMax">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
										
										
										</div>
										</div>
										</div>
										</div>
		
		
		</div>
	
		</div>
		</div>
		
		
		<br>
		
		<div class="row" id="min_time_revenue_stack" style="background:white !important ;border: 3px groove #FDFEFE; display: none;">
			<canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
			<div class="col-md-12">
			
		

			

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
								  
													 
									
											<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
			
										  
																	 
												  
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											
												<!--  *************************** stack chart tech  *************************** -->
												
	
											<div id="MinChartContainerStacked"  ></div>

										</div>
										
										
								

								
										
																				<!--  *************************** filter for chart  *************************** -->
											
												
																				<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;">
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepickerMin1" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdateStackChartMin" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMin1"   />
																			   <div class="input-group-append" data-target="#datetimepickerMin1" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend" id="datetimepickerMin2" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddateStackChartMin" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMin2"   />
																			 <div class="input-group-append" data-target="#datetimepickerMin2" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Site Name</span>
																<input type="text" id="siteNameStackChartMin" value="${siteNameStackChartMin}" class="form-control text-input" style="margin-bottom:20px;" disabled/></div>

											 <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsStackChartMin"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivStackChartMin">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsStackChartMin"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnStackChartMin" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValStackedFltrMin" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValStackedFltrMin" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																		<div id="minStackChartTechnologies"></div>  	  
																		
																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="clearFilterStackChartMin">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtStackChartMin">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
										
										
										</div>
										</div>
										</div>
										</div>
		
		
		</div>
	
		</div>
		</div>
		
		<br>
			
		<div class="row" id="time_revenue_line"style="background:white !important ;border: 3px groove #FDFEFE;" >
			<canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
			<div class="col-md-12">
			
		

			

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
								  
													 
									
											<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
			
										  
																	 
												  
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											
												<!--  *************************** line chart tech  *************************** -->
												
	
											<div id="lineChartContainer" ></div>

										</div>
										
										
								

								
										
																				<!--  *************************** filter for chart  *************************** -->
											
												
																				<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;"> 
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepicker9" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdateLineChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker9"   />
																			   <div class="input-group-append" data-target="#datetimepicker9" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend" id="datetimepicker10" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddateLineChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker10"   />
																			 <div class="input-group-append" data-target="#datetimepicker10" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
																  
																     <div style="font-weight: bold;">Site Name</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend">
																<input type="text" id="siteNameLineChart" value="${siteNameLineChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id="LineChartSiteNameOpenPopup" onclick="LineChartSiteNameOpenPopup()">
																				  <div class="input-group-text">
																					  <i class="fas fa-edit"></i>
																				  </div>
																			  </div>
																		  
																		  </div>
																	  </div>
																	</div>
																  </div>
														  
																	<div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsLineChart"  class="form-control text-input" onchange="changeFunc();">
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivLineChart">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsLineChart"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnLineChart" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValLineFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValLineFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																		<div id="lineChartTechnologies"></div>

																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN"  id="clearFilterLineChart">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtLineChart">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
										
										
										</div>
										</div>
										</div>
										</div>
		
		
		</div>
		
		</div>
		</div>
	
		
		
		<br>
		<div class="row" id="max_time_revenue_line"style="background:white !important ;border: 3px groove #FDFEFE; display: none;" >
			<canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>	
			<div class="col-md-12">
			
		

			

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
								  
													 
									
											<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
			
										  
																	 
												  
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											
												<!--  *************************** line chart tech  *************************** -->
												
	
											<div id="MaxLineChartContainer" ></div>

										</div>
										
										
								

								
										
																				<!--  *************************** filter for chart  *************************** -->
											
												
																				<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;"> 
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepickerMax3" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdateLineChartMax" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMax3"   />
																			   <div class="input-group-append" data-target="#datetimepickerMax3" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend" id="datetimepickerMax4" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddateLineChartMax" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMax4"   />
																			 <div class="input-group-append" data-target="#datetimepickerMax4" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Site Name</span>
																<input type="text" id="siteNameLineChartMax" value="${siteNameLineChartMax}" class="form-control text-input" style="margin-bottom:20px;" disabled />															  </div>

																	<div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsLineChartMax"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivLineChartMax">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsLineChartMax"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnLineChartMax" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValLineFltrMax" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValLineFltrMax" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																		<div id="maxLineChartTechnologies"></div>
																			  
																		
																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN"  id="clearFilterLineChartMax">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtLineChartMax">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
										
										
										</div>
										</div>
										</div>
										</div>
		
		
		</div>
		
		</div>
		</div>
		
		
		<br>
		<div class="row" id="min_time_revenue_line"style="background:white !important ;border: 3px groove #FDFEFE; display: none;" >
			<canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>	
			<div class="col-md-12">
			
		

			

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
								  
													 
									
											<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
			
										  
																	 
												  
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											
												<!--  *************************** line chart tech  *************************** -->
												
	
											<div id="MinLineChartContainer" ></div>

										</div>
										
										
								

								
										
																				<!--  *************************** filter for chart  *************************** -->
											
												
																				<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;"> 
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepickerMin3" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdateLineChartMin" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMin3"   />
																			   <div class="input-group-append" data-target="#datetimepickerMin3" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend" id="datetimepickerMin4" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddateLineChartMin" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepickerMin4"   />
																			 <div class="input-group-append" data-target="#datetimepickerMin4" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Site Name</span>
																<input type="text" id="siteNameLineChartMin" value="${siteNameLineChartMin}" class="form-control text-input" style="margin-bottom:20px;" disabled />															  </div>

																	<div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsLineChartMin"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivMin1">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsLineChartMin"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnLineChartMin" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValLineFltrMin" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValLineFltrMin" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																	<div id="minLineChartTechnologies"></div>		  
																		 
																		
																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN"  id="clearFilterLineChartMin">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtLineChartMin">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
										
										
										</div>
										</div>
										</div>
										</div>
		
		
		</div>
		
		</div>
		</div>
		
		
		<br>
		
			<div id="pie_charts">
		    <canvas class="mt-5 w-100" id="doughnutChart" style="display:none;"></canvas>
				<div class="row" style="background:white !important ;border: 3px groove #FDFEFE;" >
			<div  class="col-md-12">
			
			<div class="card card-primary card-tabs"  >
					<div class="card-body "   >

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group" style="border: 3px groove #FDFEFE;" >
								
									<div class="col-md-6 " >
										<div class="card card-primary card-tabs "  >
											
												<!--  *************************** piechart chart tech  *************************** -->
												
											<div class="card-body " >	
												<div class="tab-content" id="custom-tabs-one-tabContent">
												<div class="tab-pane fade show active"
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											<div id="chartContainer2" class= "canvas-style2 "  ></div>
											</div>

										</div>
										</div>
										
										</div>
										
								

										</div>
										
												<div class="col-md-6  ">
										<div class="card card-primary card-tabs " >
											
												<!--  *************************** piechart chart tech  *************************** -->
												
											<div class="card-body "  >	
												<div class="tab-content" id="custom-tabs-one-tabContent">
												<div class="tab-pane fade show active"
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											<div id="chartPie2" class= "canvas-style2 "></div>
											</div>

										</div>
										</div>
										</div>
										
										
								

										</div>
										
										
										</div>
										</div>
										</div>
										</div>
		
		</div>
		
		</div>
		</div>
		
		
		<br>
						<div class="row" id="pie_charts2"style="background:white !important ;border: 3px groove #FDFEFE;" >
			<div class="col-md-12">
			
			<div class="card card-primary card-tabs cards-margin ">
					<div class="card-body ">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group" style="border: 3px groove #FDFEFE;">
								
									<div class="col-md-6 " >
										<div class="card card-primary card-tabs " >
											
												<!--  *************************** piechart chart tech  *************************** -->
												
											<div class="card-body " >	
												<div class="tab-content" id="custom-tabs-one-tabContent">
												<div class="tab-pane fade show active"
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											<div id="chartContainerPie3"  ></div>
											</div>

										</div>
										</div>
										</div>
										
										
								

										</div>
										<div class="col-md-6 " >
																	<div   id="filter_Chart_Piez" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); width:93%; margin-left:20px;  ">
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChartPiez"  >
																						<div style="font-weight: bold;margin-top:20px; " class="pos">Pie Charts Filter</div>	
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-left:50px;margin-right:50px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group" style="margin-left:50px;margin-right:50px;">
																			<div class="input-group-prepend" id="datetimepicker11" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdatePieChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker11"   />
																			   <div class="input-group-append" data-target="#datetimepicker11" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<!-- enddate -->
																	<div style="font-weight: bold;margin-left:50px;margin-right:50px;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group" style="margin-left:50px;margin-right:50px;">
																		  <div class="input-group-prepend" id="datetimepicker12" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddatePieChart" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker12"   />
																			 <div class="input-group-append" data-target="#datetimepicker12" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
												 	 			<div style="font-weight: bold;margin-left:50px;margin-right:50px;">Site Name</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group" style="margin-left:50px;margin-right:50px;">
																		  <div class="input-group-prepend">
																<input type="text" id="siteNamePieChart" value="${siteNamePieChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id="PieChartSiteNameOpenPopup" onclick="PieChartSiteNameOpenPopup()">
																				  <div class="input-group-text">
																					  <i class="fas fa-edit"></i>
																				  </div>
																			  </div>
																		  
																		  </div>
																	  </div>
																	</div>
																  </div>				  
														  
											 					<div style="font-weight: bold;margin-right:50px;margin-left:50px;">Filter options</div> 
																	  <div class="row" >													  
																	  <div class="col-md-12">
																		  
																	  <select id="changeOptionsPieChart" style="margin-right:50px;margin-left:50px;width:82%;"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="filterDivPieChart" style="margin-right:50px;margin-left:50px;">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revenueOptionsPieChart"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;" disabled>
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtnPieChart" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValPiesFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValPiesFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																  <div style="font-weight: bold;margin-right:50px;margin-left:50px;">Technologies</div>
																  <div class="form-group" style="margin-right:50px;margin-left:50px;">
																	<div class="input-group-prepend">
																	
																		<div id="pieChartTechnologies"></div>
																			  
																		
																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex" >													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="clearFilterPieChart">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="applyButtPieChart">Apply</button>	
															</div>
															</div>

																							</div> 
																					<!-- </div> -->
																					</div>  
												
																				<!--  ***************************  end filter for chart  *************************** -->
																				</div>
										
										
										</div>
										</div>
										</div>
										</div>
		
		</div>
		
		</div>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- end of chart 21-06 -->
        
        </div>
       
      </div>
      </div>
    </div>
  </div>
</div>
	
		</div>
		
	
 <!-- Main Site Name Popup -->

<div class="container-fluid" >     	
            <div class="modal fade" id="autocompleteModal" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Sites Name</h5>
                            <button type="button" class="close" onclick="popupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Sites Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="sitesAutocomplete" /> 
                                       </span>
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
            
            
    <!-- MS Chart Site Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="MSChartSiteNamePopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Sites Name</h5>
                            <button type="button" class="close" onclick="MSChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Sites Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="msChartSitesAutocomplete" /> 
                                       </span>
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
            
   <!-- Stacked Chart Site Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="stackChartSiteNamePopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Sites Name</h5>
                            <button type="button" class="close" onclick="stackChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Sites Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="stackChartSitesAutocomplete" /> 
                                       </span>
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
<!-- Line Chart Site Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="lineChartSiteNamePopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Sites Name</h5>
                            <button type="button" class="close" onclick="lineChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Sites Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="lineChartSitesAutocomplete" /> 
                                       </span>
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
            
 <!-- Pie Chart Site Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="pieChartSiteNamePopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Sites Name</h5>
                            <button type="button" class="close" onclick="pieChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Sites Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="pieChartSitesAutocomplete" /> 
                                       </span>
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
</body>


<script>


// Changing The startdate and enddate when choosing weekly or  monthly or daily
$('#period').change(function() {

	var startDate;
	var a; var d;
    if ($(this).val() === 'Weekly') {
        
    	 startDate = $("#enddate").val();
        d = new Date(startDate);
    	d.setDate(d.getDate() - 7);
    	d.setHours( 0,0,0,0 );
        a=d.toLocaleString();
    	$("#startdate").val(a).trigger('change');
      	
    }

    else if ($(this).val() === 'Daily') {
        
   	 startDate = $("#enddate").val();
       d = new Date(startDate);
       d.setHours( 0,0,0,0 );
       a=d.toLocaleString();
   	$("#startdate").val(a).trigger('change');
     	
   }
    else if ($(this).val() === 'Monthly') {
    	 startDate = $("#enddate").val();
    	 d = new Date(startDate);
    	 d.setMonth(d.getMonth() - 3);
    	 d.setHours( 0,0,0,0 );
    	 a=d.toLocaleString();
    	$("#startdate").val(a).trigger('change');
    	
    }

});

	//show and hide the filter when clicking on icon filter							 
							
/* $("#filter_chart_btn").off().on("click", function() {
		console.log("doneee")
if ($(this).closest('#filter_Chart_Sites, #filter_chart_btn').length == 0){
    $("#filter_Chart_Sites").hide();

    }
	    
    else{
  $("#filter_Chart_Sites").toggle();
	  
    }
}); */


//change the size of cadre
/* $('#filter_chart_btn').click(function(e) {

	if ($(this).hasClass("expanded-div")){
		   $(".normal-div").removeClass("compressed-div expanded-div");
		  }
	else{
		 $(".normal-div").removeClass("compressed-div expanded-div").addClass("compressed-div");;
	   $(toGetId).removeClass("compressed-div").addClass("expanded-div");  }
	var left = $('#chartContainer3');
if(left.hasClass('col-md-9') )

{
left.removeClass("col-md-9").addClass("col-md-12");
$('#chartContainerDiv').animate({'width': '100%'});

}
else
{
left.removeClass("col-md-12").addClass("col-md-9");

}
}); */
//when clicking on "Is Equals To"


//datetimepicker for start date							   
/* var startdate = new Date();
startdate.setDate(startdate.getDate() - 2);


startdate.setHours( 0,0,0,0 );

var s = Date.parse(startdate);
$('#startdate2').datetimepicker({
    defaultDate:s
});
//datetimepicker for end date
var enddate = new Date();
enddate.setDate(enddate.getDate() - 2);

$('#enddate2').datetimepicker({
    defaultDate:enddate
});  */


// function filterChart(){
	
// 	var dataStore = new FusionCharts.DataStore();
// var dataTable = dataStore.createDataTable(data,schema);

// var filterQuery = FusionCharts.DataStore.Operators.filter((row, columns) => {
//     return row[columns.siteName] === 'Nwarik Farm';
// });

// var filteredData = dataTable.query(filterQuery);
	// var ReportArrayGlobal = ${revenueReportList};
	// for(var i=0 ;i<ReportArrayGlobal.length ;i++){
								
	// 	var filterarray = ReportArrayGlobal.filter((siteee) =>{
	// return siteee.siteName===ReportArrayGlobal[i]["siteName"];
	

	// })												 
	 
	// 							}
	
	
//const filterresult = SiteRevenueParam.dataSource.dataset[0].filter(value => value>6);
//   var filter1 = FusionCharts.DataStore.Operators.equals('siteName', 'Kayole_Rasta_Stage');
//   var dataTable = dataT.query(filter1);
//filter1 = fusionTable.CreateFilter(Fusioncharts::FilterType::Equals, "siteName", "Kayole_Rasta_Stage")

//Applying the filter on fusion table
//fusionTable.ApplyFilter(filter1)
// console.log("filterresult:" + ReportArrayGlobal );


//} 
//


	/////////////////////

$( "#legendDiv" ).draggable(); 


 


var divTablee;
var divRevTable;

listSites=[];
var listSitesClrFltr;
function initMap() {
//console.log("I am here");
	
	    chartData = ${chartData};
    	listSites = ${listSites};
    	listSitesClrFltr=chartData;
        
     renderSiteCharts(chartData);
	divTablee= document.getElementById("tableDiv").innerHTML;
	divRevTable =document.getElementById("revTable").innerHTML;
	
	// Define the Center
	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	
	
  var map = new google.maps.Map(document.getElementById("mapContainer"), {
    center:Nairobi ,
    zoom:6
    
  });

  document.getElementById("mapContainer").innerHTML ="";

	var map = new google.maps.Map(document.getElementById("mapContainer"), {

		 mapTypeControl: false,
		 center:Nairobi ,
		 zoom: 6,
		 mapTypeControl: true,
		 mapTypeControlOptions: {
			 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
			 position: google.maps.ControlPosition.TOP_CENTER,
			 },
			 zoomControl: true,
			 zoomControlOptions: {
				 position: google.maps.ControlPosition.LEFT_CENTER,
				 },


				 scaleControl: true,
				 streetViewControl: true,
				 streetViewControlOptions: {
					 position: google.maps.ControlPosition.LEFT_TOP,
					 },
					 
					 style: 'mapbox://styles/mapbox/streets-v11',
					 fullscreenControl: true,
					 });

	UnselectAll();
	setColor(listSites,map);
	
 
   	

  //Add buttons on map
	const centerControlDiv = document.createElement("div");
    LegendControl(centerControlDiv, map);
    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

    const DefaultZoomDiv = document.createElement("div");
    DefaultZoomControl(DefaultZoomDiv, map);
    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

    const maxRevControlDiv = document.createElement("div");
    MaxRevenueControl(maxRevControlDiv, map);
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

    const minRevControlDiv = document.createElement("div");
    MinRevenueControl(minRevControlDiv, map);
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
    
    ShowHideDiv();			  
	  } // Eng initMap()


function chooseSiteName() {
    $('#autocompleteModal').modal('show');
 }
function MSChartSiteNameOpenPopup() {
    $('#MSChartSiteNamePopup').modal('show');
 }
function StackChartSiteNameOpenPopup() {
    $('#stackChartSiteNamePopup').modal('show');
 }
function LineChartSiteNameOpenPopup() {
    $('#lineChartSiteNamePopup').modal('show');
 }
function PieChartSiteNameOpenPopup() {
    $('#pieChartSiteNamePopup').modal('show');
 }

function pieChartPopupClose() {
	var allSitesPieChart = document.getElementById("pieChartSitesAutocomplete").value;
	
	var siteInput = document.getElementById("siteNamePieChart").value;
	siteInput = siteInput.split(";");
	siteInput  = siteInput[0];

	//Check if siteInput exist in text input
	if(allSitesPieChart.includes(siteInput) == false ) {
	document.getElementById("siteNamePieChart").value ="";
	
}
    $('#pieChartSiteNamePopup').modal('hide');
	
}
function lineChartPopupClose() {
	var allSitesLineChart = document.getElementById("lineChartSitesAutocomplete").value;
	
	var siteInput = document.getElementById("siteNameLineChart").value;
	siteInput = siteInput.split(";");
	siteInput  = siteInput[0];

	//Check if siteInput exist in text input
	if(allSitesLineChart.includes(siteInput) == false ) {
	document.getElementById("siteNameLineChart").value ="";
	
}
    $('#lineChartSiteNamePopup').modal('hide');
	
}
function stackChartPopupClose() {
	var allSitesStackChart = document.getElementById("stackChartSitesAutocomplete").value;
	
	var siteInput = document.getElementById("siteNameStackChart").value;
	siteInput = siteInput.split(";");
	siteInput  = siteInput[0];

	//Check if siteInput exist in text input
	if(allSitesStackChart.includes(siteInput) == false ) {
	document.getElementById("siteNameStackChart").value ="";
	
}
    $('#stackChartSiteNamePopup').modal('hide');
	
}
function MSChartPopupClose() {
	var allSitesMSChart = document.getElementById("msChartSitesAutocomplete").value;
	
	var siteInput = document.getElementById("siteNameMSChart").value;
	siteInput = siteInput.split(";");
	siteInput  = siteInput[0];

	//Check if siteInput exist in text input
	if(allSitesMSChart.includes(siteInput) == false ) {
	document.getElementById("siteNameMSChart").value ="";
	
}
    $('#MSChartSiteNamePopup').modal('hide');
	
}
function popupClose() {
	var allSites = document.getElementById("sitesAutocomplete").value;
	
	var siteInput = document.getElementById("site").value;
	siteInput = siteInput.split(";");
	siteInput  = siteInput[0];

	//Check if siteInput exist in text area
	if(allSites.includes(siteInput) == false ) {
	document.getElementById("site").value ="";
	
}
    $('#autocompleteModal').modal('hide');
	
}

$('#allSites').click(function(){
	if($(this). is(":checked")){
		document.getElementById("prepaidRevenueSites").checked = false;
		$('#prepaidRevenueSites').val('0');

		document.getElementById("warehouseAllSites").checked = false;
		$('#warehouseAllSites').val('0');
		
		$(this).val('1');		
	      
  	 var Nairobi=new google.maps.LatLng(-1.286389,36.817223);            		
  	 document.getElementById("mapContainer").innerHTML ="";
     var map = new google.maps.Map(document.getElementById("mapContainer"), {

    	  mapTypeControl: false,
  		  center:Nairobi ,
  		  zoom: 6,
  			 mapTypeControl: true,
  			 mapTypeControlOptions: {
  				 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
  				 position: google.maps.ControlPosition.TOP_CENTER,
  				 },
  				 zoomControl: true,
  				 zoomControlOptions: {
  					 position: google.maps.ControlPosition.LEFT_CENTER,
  					 },

  					 scaleControl: true,
  					 streetViewControl: true,
  					 streetViewControlOptions: {
  						 position: google.maps.ControlPosition.LEFT_TOP,
  						 },
  						 
  						 style: 'mapbox://styles/mapbox/streets-v11',
  						 fullscreenControl: true,
  						 });

  		//Add legend button under zoom control on map
  		const centerControlDiv = document.createElement("div");
  	    LegendControl(centerControlDiv, map);
  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

  	    const DefaultZoomDiv = document.createElement("div");
  	    DefaultZoomControl(DefaultZoomDiv, map);
  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

  	    const maxRevControlDiv = document.createElement("div");
  	    MaxRevenueControl(maxRevControlDiv, map);
  	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

  	    const minRevControlDiv = document.createElement("div");
  	    MinRevenueControl(minRevControlDiv, map);
  	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);


  	  if(warehouseSites.length ==0 && warehousePrepaidSites.length == 0){
			alert("No data to apply changes on it");

			  }
	else {

  	  newTableDiv =  document.getElementById("tableDiv").innerHTML;

	//clear legend div content & add the new content
    document.getElementById("legendHeader").innerHTML ="";
    document.getElementById("legendHeader").innerHTML='<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6> <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAllWareSites()"  id="selectUnselect" >Unselect All</button>';
    
    document.getElementById("tableDiv").innerHTML ="";
    document.getElementById("tableDiv").innerHTML='<table id="mytable"><h5 style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:10px;left:20px;">All Sites</h5><tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th></tr></table>';

		colors=["#8A2BE2","#FFDC00","#0080FF","red","green","#4D0207"];
		legendId=["allWarehouseSites","2gSites","3gSites","4gSites","legendMaxRevenue","legendMinRevenue"];
		legendLabel=["Warehouse Sites","2G Sites","2G, 3G Sites","2G, 3G, 4G Sites","Maximum Revenue","Minimum Revenue"];
		dotColors=["purple","yellow","blue","red","green","redDark"];
		dotId=["","","","","max-div","min-div"];
		dotClass=["","","","","dot-max","dot-min"];

  for(var h=0;h<6;h++) {

        var markup = "<tr> <td style='position: relative;top:17px;left:40px;'><input style='position: relative;top: -10px;' type='checkbox' name='legendCheckbox' id='"+legendId[h]+"'  value='"+colors[h]+"' onclick='ShowHideAllWarehouseMarkers(value)' /></td>"
    +"<td style='position: relative;top:8px;left:60px;'><div  class='dot "+dotColors[h]+"'></div></td>"
    +"<td style='position: relative;top:8px;left:60px;'><div id='"+dotId[h]+"'  class='"+dotClass[h]+"' ></div></td>"
    +"<td style='position: relative;top: 5px;left:70px;'><label style='color:black;font-weight:bold;font-size:2ex;' > "+legendLabel[h]+"</label></td></tr>";

		 $("#mytable").append(markup);                  
    }
	  var maxCheckBox = document.getElementById("Max");
	  var minCheckBox = document.getElementById("Min");
	
	  if(oldAllSitesName =="") {
     	 if(maxCheckBox.checked == false && minCheckBox.checked == false){
  			    UnselectAll();
            	DeleteMarkers();
            	setColor(warehousePrepaidSites ,map);
 	            AddSiteMarkers(warehouseSites,map,"#8A2BE2");     			
     	}

     	else if (maxCheckBox.checked == true && minCheckBox.checked == false){
	            UnselectAll();	
        		DeleteMarkers();
	            MaxMinSetColor(warehousePrepaidSites,map,"max");
	            AddSiteMarkers(warehouseSites,map,"#8A2BE2");
	    }

     	else if (maxCheckBox.checked == false && minCheckBox.checked == true){
	            UnselectAll();	
        		DeleteMarkers();
	            MaxMinSetColor(warehousePrepaidSites,map,"min");
	            AddSiteMarkers(warehouseSites,map,"#8A2BE2");
	            
	          }
     	else if (maxCheckBox.checked == true && minCheckBox.checked == true){
	            UnselectAll();	
        		DeleteMarkers();
	            MaxMinSetColor(warehousePrepaidSites,map,"maxMin");
	            AddSiteMarkers(warehouseSites,map,"#8A2BE2");
	     }
     	
        if(warehouseSites.length>0) {
			  $("#allWarehouseSites").attr('disabled', false);
              $("#allWarehouseSites").prop('checked',true);  
	     }
	     else {
			  $("#allWarehouseSites").attr('disabled', true);

	 	}
             newTableDiv =document.getElementById("tableDiv").innerHTML;
             
       }
      //Site name is selected
	  else {
		  if(maxCheckBox.checked == false && minCheckBox.checked == false){
			UnselectAll();
          	DeleteMarkers();
        	AddSelectedSiteColor(warehousePrepaidSites,map);
	        AddSiteMarkers(warehouseSites,map,"#8A2BE2");     			
   	     }

   	else if (maxCheckBox.checked == true && minCheckBox.checked == false){
	            UnselectAll();	
      		    DeleteMarkers();
	            MaxMinSetColor(warehousePrepaidSites,map,"max");
	            AddSiteMarkers(warehouseSites,map,"#8A2BE2");
	    }

   	else if (maxCheckBox.checked == false && minCheckBox.checked == true){
	            UnselectAll();	
      			DeleteMarkers();
	            MaxMinSetColor(warehousePrepaidSites,map,"min");
	            AddSiteMarkers(warehouseSites,map,"#8A2BE2");
	            
	          }
   	else if (maxCheckBox.checked == true && minCheckBox.checked == true){
	            UnselectAll();	
      		    DeleteMarkers();
	            MaxMinSetColor(warehousePrepaidSites,map,"maxMin");
	            AddSiteMarkers(warehouseSites,map,"#8A2BE2");
	     }
   	
      if(warehouseSites.length>0) {
			$("#allWarehouseSites").attr('disabled', false);
            $("#allWarehouseSites").prop('checked',true);  
	     }
	     else {
			  $("#allWarehouseSites").attr('disabled', true);

	 	}
           newTableDiv =document.getElementById("tableDiv").innerHTML;
		}

	}
	}
	else if($(this). is(":not(:checked)")){
		$(this).val('0');

	} 
	    
 }); // End ("#allSites") Click Event
 
$('#warehouseAllSites'). click(function(){
	if($(this).is(":checked")){
		document.getElementById("allSites").checked = false;
		$('#allSites').val('0');
		
		document.getElementById("prepaidRevenueSites").checked = false;
		$('#prepaidRevenueSites').val('0');

		$(this).val('1');
		
		 var Nairobi=new google.maps.LatLng(-1.286389,36.817223);            		
	  	 document.getElementById("mapContainer").innerHTML ="";
	      var map = new google.maps.Map(document.getElementById("mapContainer"), {

	    	  mapTypeControl: false,
	  		  center:Nairobi ,
	  		  zoom: 6,
	  			 mapTypeControl: true,
	  			 mapTypeControlOptions: {
	  				 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
	  				 position: google.maps.ControlPosition.TOP_CENTER,
	  				 },
	  				 zoomControl: true,
	  				 zoomControlOptions: {
	  					 position: google.maps.ControlPosition.LEFT_CENTER,
	  					 },

	  					 scaleControl: true,
	  					 streetViewControl: true,
	  					 streetViewControlOptions: {
	  						 position: google.maps.ControlPosition.LEFT_TOP,
	  						 },
	  						 
	  						 style: 'mapbox://styles/mapbox/streets-v11',
	  						 fullscreenControl: true,
	  						 });

	  		//Add legend button under zoom control on map
	  		const centerControlDiv = document.createElement("div");
	  	    LegendControl(centerControlDiv, map);
	  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	  	    const DefaultZoomDiv = document.createElement("div");
	  	    DefaultZoomControl(DefaultZoomDiv, map);
	  	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	  	    const maxRevControlDiv = document.createElement("div");
	  	    MaxRevenueControl(maxRevControlDiv, map);
	  	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

	  	    const minRevControlDiv = document.createElement("div");
	  	    MinRevenueControl(minRevControlDiv, map);
	  	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);

	  	  if(allWareSites.length ==0){
				alert("No data to apply changes on it");

	    }
		else {
			newTableDiv =  document.getElementById("tableDiv").innerHTML;
			defaultLegend();
		    document.getElementById("tableCaption").innerHTML="Warehouse Sites";			
		    newTableDiv =document.getElementById("tableDiv").innerHTML;
		
		  var maxCheckBox = document.getElementById("Max");
		  var minCheckBox = document.getElementById("Min");
		  var allSitesName = AllSitesArr.join();
		  UnselectAll();
      	  DeleteMarkers();
      	  
         AddSelectedSiteColor(allWareSites,map);  
	  
			}
		
	}
	else if($(this). is(":not(:checked)")){
		$(this).val('0');

	}
});

$('#prepaidRevenueSites'). click(function(){
	if($(this).is(":checked")){
		
		document.getElementById("allSites").checked = false;
		$('#allSites').val('0');
		
		document.getElementById("warehouseAllSites").checked = false;
		$('#warehouseAllSites').val('0');

		$(this).val('1');

		var Nairobi=new google.maps.LatLng(-1.286389,36.817223); 		
	    document.getElementById("mapContainer").innerHTML ="";

		var map = new google.maps.Map(document.getElementById("mapContainer"), {
			 mapTypeControl: false,
			 center:Nairobi ,
		      zoom: 6,
			 mapTypeControl: true,
			 mapTypeControlOptions: {
				 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
				 position: google.maps.ControlPosition.TOP_CENTER,
				 },
				 zoomControl: true,
				 zoomControlOptions: {
					 position: google.maps.ControlPosition.LEFT_CENTER,
					 },

					 scaleControl: true,
					 streetViewControl: true,
					 streetViewControlOptions: {
						 position: google.maps.ControlPosition.LEFT_TOP,
						 },
						 
						 style: 'mapbox://styles/mapbox/streets-v11',
						 fullscreenControl: true,
						 });

		//Add legend button under zoom control on map
		const centerControlDiv = document.createElement("div");
	    LegendControl(centerControlDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	    const maxRevControlDiv = document.createElement("div");
	    MaxRevenueControl(maxRevControlDiv, map);
	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

	    const minRevControlDiv = document.createElement("div");
	    MinRevenueControl(minRevControlDiv, map);
	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);

		defaultLegend();
	    
	    if (selectedSiteData.length == 0) {
            alert('No data to apply changes on it');
            $("#2gSites").attr('disabled', true);
    	 	$("#3gSites").attr('disabled', true);
    	 	$("#4gSites").attr('disabled', true);
    	 	$("#legendMaxRevenue").attr('disabled', true);
    	 	$("#legendMinRevenue").attr('disabled', true);
    	 	var element = document.getElementById("selectUnselect");
    		if (element.innerHTML == "Unselect All"){
    			element.innerHTML = " ";
    		 }
        }
    
	    else {
		  var maxCheckBox = document.getElementById("Max");
		  var minCheckBox = document.getElementById("Min");
			  
		  if(oldAllSitesName =="") {
	     	 if(maxCheckBox.checked == false && minCheckBox.checked == false){
	  			    UnselectAll();
	            	DeleteMarkers();
	            	setColor(selectedSiteData ,map);
	     	}

	     	else if (maxCheckBox.checked == true && minCheckBox.checked == false){
		            UnselectAll();	
	        		DeleteMarkers();
		            MaxMinSetColor(selectedSiteData,map,"max");
		    }

	     	else if (maxCheckBox.checked == false && minCheckBox.checked == true){
		            UnselectAll();	
	        		DeleteMarkers();
		            MaxMinSetColor(selectedSiteData,map,"min");		            
		          }
	     	else if (maxCheckBox.checked == true && minCheckBox.checked == true){
		            UnselectAll();	
	        		DeleteMarkers();
		            MaxMinSetColor(selectedSiteData,map,"maxMin");
		     }
	     	
	        if(warehouseSites.length>0) {
				  $("#allWarehouseSites").attr('disabled', false);
	              $("#allWarehouseSites").prop('checked',true);  
		     }
		     else {
				  $("#allWarehouseSites").attr('disabled', true);

		 	}
	             newTableDiv =document.getElementById("tableDiv").innerHTML;
	             
	       }
	      //Site name is selected
		  else {
			  if(maxCheckBox.checked == false && minCheckBox.checked == false){
				UnselectAll();
	          	DeleteMarkers();
	        	AddSelectedSiteColor(selectedSiteData,map);
	   	}

	   	else if (maxCheckBox.checked == true && minCheckBox.checked == false){
		            UnselectAll();	
	      		    DeleteMarkers();
		            MaxMinSetColor(selectedSiteData,map,"max");
		    }

	   	else if (maxCheckBox.checked == false && minCheckBox.checked == true){
			UnselectAll();	
	      	DeleteMarkers();
		    MaxMinSetColor(selectedSiteData,map,"min");
		            
		 }
	   	else if (maxCheckBox.checked == true && minCheckBox.checked == true){
			UnselectAll();	
	        DeleteMarkers();
		    MaxMinSetColor(selectedSiteData,map,"maxMin");
		     }
		  }
 		
	    }
	
	}
	else if($(this). is(":not(:checked)")){
		$(this).val('0');

	}     
 });


$('#enable'). click(function(){
	if($(this). is(":checked")){
		 document.getElementById("disable").checked = false;
		$('#disable').val('0');
		$(this).val('1');

		document.getElementById('sitesAutocomplete').value = "";
	     concatenatedSites ="";
	     selectedSites=[];

	 		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
		
 		
	    document.getElementById("mapContainer").innerHTML ="";

		var map = new google.maps.Map(document.getElementById("mapContainer"), {

			 mapTypeControl: false,
			 center:Nairobi ,
		      zoom: 6,
			 
			 mapTypeControl: true,
			 mapTypeControlOptions: {
				 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
				 position: google.maps.ControlPosition.TOP_CENTER,
				 },
				 zoomControl: true,
				 zoomControlOptions: {
					 position: google.maps.ControlPosition.LEFT_CENTER,
					 },

					 scaleControl: true,
					 streetViewControl: true,
					 streetViewControlOptions: {
						 position: google.maps.ControlPosition.LEFT_TOP,
						 },
						 
						 style: 'mapbox://styles/mapbox/streets-v11',
						 fullscreenControl: true,
						 });

		//Add legend button under zoom control on map
		const centerControlDiv = document.createElement("div");
	    LegendControl(centerControlDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	    const maxRevControlDiv = document.createElement("div");
	    MaxRevenueControl(maxRevControlDiv, map);
	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

	    const minRevControlDiv = document.createElement("div");
	    MinRevenueControl(minRevControlDiv, map);
	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
	    
		UnselectAll();
		DeleteMarkers();

		RevenueSetColor(disableData,map);



		
	}
	else if($(this). is(":not(:checked)")){
		$(this).val('0');

				}     
 });

$('#disable'). click(function(){
	if($(this). is(":checked")){
		 document.getElementById("enable").checked = false;
		$('#enable').val('0');
		$(this).val('1');

		$('input[type="checkbox"]', '#revenueColorTable').prop('checked', false);

		document.getElementById('sitesAutocomplete').value = "";
	     concatenatedSites ="";
	     selectedSites=[];

	 		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
			
 		
		    document.getElementById("mapContainer").innerHTML ="";

			var map = new google.maps.Map(document.getElementById("mapContainer"), {

				 mapTypeControl: false,
				 center:Nairobi ,
			      zoom: 6,
				 
				 mapTypeControl: true,
				 mapTypeControlOptions: {
					 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
					 position: google.maps.ControlPosition.TOP_CENTER,
					 },
					 zoomControl: true,
					 zoomControlOptions: {
						 position: google.maps.ControlPosition.LEFT_CENTER,
						 },

						 scaleControl: true,
						 streetViewControl: true,
						 streetViewControlOptions: {
							 position: google.maps.ControlPosition.LEFT_TOP,
							 },
							 
							 style: 'mapbox://styles/mapbox/streets-v11',
							 fullscreenControl: true,
							 });

			//Add legend button under zoom control on map
			const centerControlDiv = document.createElement("div");
		    LegendControl(centerControlDiv, map);
		    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

		    const DefaultZoomDiv = document.createElement("div");
		    DefaultZoomControl(DefaultZoomDiv, map);
		    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

		    const maxRevControlDiv = document.createElement("div");
		    MaxRevenueControl(maxRevControlDiv, map);
		    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

		    const minRevControlDiv = document.createElement("div");
		    MinRevenueControl(minRevControlDiv, map);
		    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
		    
			UnselectAll();
			DeleteMarkers();

			defaultLegend();
     		setColor(disableData,map);

		
	}
	else if($(this). is(":not(:checked)")){
		$(this).val('0');

				}     
 });

// Set the default Date
var date = new Date();
date.setDate(date.getDate() - 2);


date.setHours( 0,0,0,0 );

var dateend = new Date();
dateend.setDate(dateend.getDate() - 2);

var c = Date.parse(date);
/*for(var i = 1; i<=5;i++){
	$('#startdate'+i).datetimepicker({
	    defaultDate:c
	});



	$('#enddate'+i).datetimepicker({
	    defaultDate:dateend
	});
}*/
$('#startdate').datetimepicker({
    defaultDate:c
});
$('#enddate').datetimepicker({
    defaultDate:dateend
});

$('#startdateMSChart').datetimepicker({
    defaultDate:c
});
$('#enddateMSChart').datetimepicker({
    defaultDate:dateend
});

$('#startdateStackChart').datetimepicker({
    defaultDate:c
});
$('#enddateStackChart').datetimepicker({
    defaultDate:dateend
});

$('#startdateLineChart').datetimepicker({
    defaultDate:c
});
$('#enddateLineChart').datetimepicker({
    defaultDate:dateend
});

$('#startdatePieChart').datetimepicker({
    defaultDate:c
});
$('#enddatePieChart').datetimepicker({
    defaultDate:dateend
});

//start and end date for max and min
$('#startdateStackChartMax').datetimepicker({
	    defaultDate:c
	});

$('#enddateStackChartMax').datetimepicker({
	    defaultDate:dateend
	});

$('#startdateLineChartMax').datetimepicker({
    defaultDate:c
});

$('#enddateLineChartMax').datetimepicker({
    defaultDate:dateend
});

$('#startdateStackChartMin').datetimepicker({
    defaultDate:c
});

$('#enddateStackChartMin').datetimepicker({
    defaultDate:dateend
});

$('#startdateLineChartMin').datetimepicker({
defaultDate:c
});

$('#enddateLineChartMin').datetimepicker({
defaultDate:dateend
});

$('#clearButton'). click(function(){  
	 document.getElementById('site').value = '';
	 document.getElementById('Area').value = '';
	 document.getElementById('Region').value = '';
	    });


$(document).ready(function() {
// Options for the multidrop down
	
	 var myOptions = [
		  { label: 'PayG', value: 'payg' },
		  { label: "Bundle", value: 'bundle'},
		 
		];

	 VirtualSelect.init({
		  ele: '#Prepostpaid',
		  options: myOptions,
		  multiple: true,
		  placeholder: 'Services'
		});
		
	 var myOptions2 = [
		  { label: 'Prepaid', value: 'prepaid' },
		  { label: "Postpaid", value: 'postpaid'},
		 
		];
	 VirtualSelect.init({
		  ele: '#services',
		  options: myOptions2,
		  multiple: true,
		  placeholder: 'Pre/Post Paid'
		});

	 var techOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#siteTechnologies',
		  options: techOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});

	 var msChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#msChartTechnologies',
		  options: msChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});

	 var stackChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#stackChartTechnologies',
		  options: stackChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});

	 var maxStackChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#maxStackChartTechnologies',
		  options: maxStackChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});

	 var minStackChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#minStackChartTechnologies',
		  options: minStackChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});

	 var lineChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#lineChartTechnologies',
		  options: lineChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});

	 var maxLineChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#maxLineChartTechnologies',
		  options: maxLineChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});
	 var minLineChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#minLineChartTechnologies',
		  options: minLineChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});
	 var pieChartTechOption = [
		  { label: '2G', value: '2G' },
		  { label: "2G and 3G", value: '2G_3G'},
		  { label: '2G,3G and 4G', value: '2G_3G_4G' },
		 
		];
	 VirtualSelect.init({
		  ele: '#pieChartTechnologies',
		  options: pieChartTechOption,
		  multiple: true,
		  placeholder: 'Technology Selection'
		});
		// Default grid;

		    var ReportArrayGlobal = ${revenueReportList};
            var listChartSites = ${listChartSites};
            var msColumnChartObj = ${msColumnChartObj};
            var StackedandLineRevenue = ${StackedandLineRevenue};
            var pieRevenue = ${pieRevenue}
            // var revenueReport = data.revenueReportList;
            // console.log("revenue is "+ReportArrayGlobal);    

             /*   if (ReportArrayGlobal.length > 0) { 
                	 /*console.log("*******Enter "); */
               /* }
			    else{
			    	/* console.log("*******Enter else ");*/
			/*	}    */ 
                 // for export
                 var exportArrayGrid = []; 
                    
               	 var almgrid = new AlmgridTable({
                        tableId: "gridTable",
                        dataArray: ReportArrayGlobal,
                        selectCheckbox: false,
                        drawTableGrid :  function (tableId, dataArray) {
                        //let almgrid = this;
       			        var tableBody = document.querySelector("#" + tableId + " tbody");
       			        var columnLinkNb = this.columnLinkNb;
       			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
       			        var gridContainerId = tableId + "_container";
       			        $(gridContainer).attr('id', gridContainerId);
       			        $(tableBody).empty();
       			        if (dataArray.length > 0) {
					 
	                    var dataRev=0,smsRev=0,voiceRev=0,grossRev=0;
			            var ArrayKeys = Object.keys(dataArray[0]);
			            
			        //    var itemRow = "";
			       		var columnVal;
			       		
  			       		////for export
  			       		var data = [];
  			       	    exportArrayGrid = [];
  		          		data.push('\r');
  			       		data.push(["Site", "Area", "Start Date", "End Date","Voice Revenue","SMS Revenue","Data Revenue","VAS Revenue","Combination Technology"]);
  			       		
			           for (var i = 0; i < dataArray.length; i++) {
				           
							//itemRow += "<tr class='filterRows'>";
							// for export 
		          			data.push('\r');
		          			
			               for (var j = 0; j < ArrayKeys.length; j++) {
			               	
			            	 columnVal = ArrayKeys[j];
			            	// for export 
  			            	 data.push(dataArray[i][ArrayKeys[j]]);
			                 
			               //  itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>"; 
			                 if(columnVal =="smsRevenue")   smsRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
			                 if(columnVal =="dataRevneue")   dataRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
			                 if(columnVal =="voiceRevenue")   voiceRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
			                 if(columnVal =="vasRevenue")   grossRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
		          			                  
							   
		          			}
		          			  //itemRow += "</tr>";
		          			}

			                /// for export
		                    exportArrayGrid.push(data);
		                   
			 		          var roundedsms = Math.round((smsRev + Number.EPSILON) * 100) / 100;
			 		          var roundedvoice=Math.round((voiceRev + Number.EPSILON) * 100) / 100;
			 		          var roundedgross=Math.round((grossRev + Number.EPSILON) * 100) / 100;
			 		          var roundedData=Math.round((dataRev + Number.EPSILON) * 100) / 100;
			 		          var roundedtotal=Math.round((smsRev+voiceRev+grossRev+dataRev + Number.EPSILON) * 100) / 100;
			 		
			 		 
							 $('#smsRev').val(roundedsms);
							 $('#dataRev').val(roundedData);
							 $('#voiceRev').val(roundedvoice);
							 $('#grossRev').val(roundedgross);
							 $('#totalRev').val(roundedtotal);
										
		          	        // $(tableBody).append(itemRow);

		          	        }
		          		    else{
											 
		          			  		$('#smsRev').val('0');
									$('#dataRev').val('0');
									$('#voiceRev').val('0');
									$('#grossRev').val('0');
									$('#totalRev').val('0');
	      					  }
				  
		         	        // Method for pagination almgrid-pagecount-box
		          	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
		          	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

		          	        // For global search textbox
		          	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

		          	        var paginationId = tableId + "_pagination";


		          	        // Page Rows number
		          	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
		          	        nbRows = parseInt(nbRows);
		          	        
		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox });

		          	        // Drawing for the first time
		          	        if (this.initFlag == 0) {
		          	            var tables = document.getElementsByClassName('almgrid-table');
		          	            for (var i = 0; i < tables.length; i++) {
		          	                resizableGrid(tables[i]);
		          	            }
																	 
		          	        }
		          	        this.initFlag++;
		          	       },
                         });
                
               	 //draw charts for the sites
                 stDate =  $("#startdate").val();
                 endDate = $("#enddate").val();
                			SiteRevenue(msColumnChartObj);
							PieRevenue(pieRevenue);
							fetchRevenuePerTech(StackedandLineRevenue,stDate,endDate);
							fetchLineChartRevenue(StackedandLineRevenue);
                  
        
	//var ReportArrayGlobal = [];
	var tempPrepaidRevSitesList =[];
    var wareAllSites =[]; 
	//Generate Report Clicked
	  $("#generate").click(  function() {
       var selectedTechOption = $("#technologies").children("option:selected").val();
		  var selectedPeriodOption = $("#period").children("option:selected").val();

		  var startDate = document.getElementById("startdate").value;
		  //console.log("startDate:" +startDate );
		  var endDate = document.getElementById("enddate").value;
		//  console.log("endDate:" +endDate );

		  var date1 = new Date(startDate);
		  var date2 = new Date(endDate);
		   
		// To calculate the time difference of two dates
		 /* var Difference_In_Time = date2.getTime() - date1.getTime();
		   
		  // To calculate the no. of days between two dates
		  var Difference_In_Days = Difference_In_Time / (1000 * 3600 * 24);
		   
		  //To display the final no. of days (result)
		  console.log("Total number of days between dates"+ date1 + " and "+ date2 + " is:" + Difference_In_Days);*/
	      var checkBox1 = document.getElementById("Max");
	      var checkBox2 = document.getElementById("Min");
	      var nullSiteCheckbox = document.getElementById("noSiteName");

  		  defaultLegend();
	      
		//console.log(selectedPeriodOption);
		
		//closing side filters filter option
		 $("#changeOptionsMSChart").val('null').trigger('change');
		// $("#technologiesMSChart").val('null').trigger('change');	
		document.querySelector('#msChartTechnologies').toggleSelectAll(false);							 					
		 $("#minValMSFltr").val('');
		 $("#maxValMSFltr").val('');
	     $("#revenueOptionsMSChart").val('voice_revenue').trigger('change');
		 $("#filterDivMSChart").hide();

		 $("#changeOptionsStackChart").val('null').trigger('change');
		 //$("#technologiesStackChart").val('null').trigger('change');
		 document.querySelector('#stackChartTechnologies').toggleSelectAll(false);							 					
		 $("#minValStackedFltr").val('');
		 $("#maxValStackedFltr").val('');
		 $("#revenueOptionsStackChart").val('voice_revenue').trigger('change');
		 $("#filterDivStackChart").hide();

		 $("#changeOptionslineChart").val('null').trigger('change');
		 //$("#technologieslineChart").val('null').trigger('change');
		 document.querySelector('#lineChartTechnologies').toggleSelectAll(false);							 					
		 $("#minValLineFltr").val('');
		 $("#maxValLineFltr").val('');
		 $("#revenueOptionslineChart").val('voice_revenue').trigger('change');
		 $("#filterDivlineChart").hide();

		  $("#changeOptionsPieChart").val('null').trigger('change');
	      //$("#technologiesPieChart").val('null').trigger('change');
	      document.querySelector('#pieChartTechnologies').toggleSelectAll(false);							 					
		  $("#minValPiesFltr").val('');
		  $("#maxValPiesFltr").val('');
		  $("#revenueOptionsPieChart").val('voice_revenue').trigger('change');
		  $("#filterDivPieChart").hide();

		  $("#changeOptionsStackChartMax").val('null').trigger('change');
	     // $("#technologiesStackChartMax").val('null').trigger('change');
	      document.querySelector('#maxStackChartTechnologies').toggleSelectAll(false);							 					
		  $("#minValPiesFltrMax").val('');
		  $("#maxValPiesFltrMax").val('');
		  $("#revenueOptionsStackChartMax").val('voice_revenue').trigger('change');
		  $("#filterDivStackChartMax").hide();

		  $("#changeOptionsStackChartMin").val('null').trigger('change');
	      //$("#technologiesStackChartMin").val('null').trigger('change');
	      document.querySelector('#minStackChartTechnologies').toggleSelectAll(false);							 					
		  $("#minValPiesFltrMin").val('');
		  $("#maxValPiesFltrMin").val('');
		  $("#revenueOptionsStackChartMin").val('voice_revenue').trigger('change');
		  $("#filterDivStackChartMin").hide();

		  $("#changeOptionsLineChartMax").val('null').trigger('change');
	      //$("#technologiesLineChartMax").val('null').trigger('change');
	      document.querySelector('#maxLineChartTechnologies').toggleSelectAll(false);							 					
		  $("#minValPiesFltrMax").val('');
		  $("#maxValPiesFltrMax").val('');
		  $("#revenueOptionsLineChartMax").val('voice_revenue').trigger('change');
		  $("#filterDivLineChartMax").hide();

		  $("#changeOptionsLineChartMin").val('null').trigger('change');
	     // $("#technologiesMin").val('null').trigger('change');
	     document.querySelector('#minLineChartTechnologies').toggleSelectAll(false);
		  $("#minValPiesFltrMin").val('');
		  $("#maxValPiesFltrMin").val('');
		  $("#revenueOptionsMin").val('voice_revenue').trigger('change');
		  $("#filterDivLineChartMin").hide();
		
		
		  	$("#msChartSitesAutocomplete").tagsinput('removeAll');			
	 		// AllSitesArr is equal to the internal array after removing a tag input
				$('#msChartSitesAutocomplete').on('itemRemoved', function(event) {						 
					msChartAllSitesArr = $("#msChartSitesAutocomplete").data('tagsinput').itemsArray;
				});
				$("#stackChartSitesAutocomplete").tagsinput('removeAll');			
				// AllSitesArr is equal to the internal array after removing a tag input
					$('#stackChartSitesAutocomplete').on('itemRemoved', function(event) {						 
						stackChartAllSitesArr = $("#stackChartSitesAutocomplete").data('tagsinput').itemsArray;
					}); 
					
					$("#lineChartSitesAutocomplete").tagsinput('removeAll');			
	  				// AllSitesArr is equal to the internal array after removing a tag input
	  				$('#lineChartSitesAutocomplete').on('itemRemoved', function(event) {						 
	  					lineChartAllSitesArr = $("#lineChartSitesAutocomplete").data('tagsinput').itemsArray;
	  				});
	  				
	  				$("#pieChartSitesAutocomplete").tagsinput('removeAll');			
	  				// AllSitesArr is equal to the internal array after removing a tag input
	  				$('#pieChartSitesAutocomplete').on('itemRemoved', function(event) {						 
	  					pieChartAllSitesArr = $("#pieChartSitesAutocomplete").data('tagsinput').itemsArray;
	  				});

		  // validation fior a weekly report
		
       if (selectedPeriodOption=='null'&&checkBox1.checked == false && checkBox2.checked == false ){

          alert("Please choose a period or check Maximum or Minimum");
          
               return false;
           }
	
       else{



    	   $("#gridTable").remove();
    	   $("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table">'
					+'<thead><tr class="header"><th>Site<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> '
					+'<i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
						+'<th>Area<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
						+'</ul></li></th><th>Start Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
						+'</ul></li></th><th> End Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
						+'</ul></li></th><th>Voice Revenue<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">	</ul></li></th>'
						+'<th>SMS Revenue<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'						
						+'<th>Data Revenue<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>VAS Revenue<li class="filter-dropdown dropdown">'
						+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button>'
						+'<ul	class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>Combination Technology<li class="filter-dropdown dropdown">'
						+'<button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button>'
						+'<ul	class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'
						
						+'<tr>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'<th><input type="text" class="almgrid-search" placeholder="Search"></th>'
						+'</tr></thead><tbody></tbody></table>');	  

			  var selectedTech = $("#siteTechnologies").val();
	        
			  var arrSelected = [];
			  for (let i = 0; i < selectedTech.length; i++) {
				  arrSelected.push(selectedTech[i]);
				}
			var siteSelectedTech = arrSelected.toString();

  

			  $("#enable").prop('checked',false);   
			  $("#disable").prop('checked',true); 
			  $("#allSites").prop('checked',false);   
			  $("#prepaidRevenueSites").prop('checked',true);
			  $("#warehouseAllSites").prop('checked',false);   
			  
		
			  //document.getElementById('sitesAutocomplete').value = "";
			  concatenatedSites ="";
			  selectedSites=[];
			  DeleteMarkers();//Google map Function
			  $('input[type="checkbox"]', '#revenueColorTable').prop('checked', false);
				  		 

               var element = document.getElementById("selectUnselect");
			   if (element.innerHTML == "Select All"){
			  		element.innerHTML = "Unselect All";
			  	}

			  			
		//Convert the elements of AllSitesArr that contains the sites name in popup into a string
		 var allSitesName = AllSitesArr.join();
		 
		 var siteName = document.getElementById("site").value;
		  var area = document.getElementById("Area").value;
		  var region = document.getElementById("Region").value;

		  var areaName;
		  if(area == ""){
			  areaName  ="";
		  }
		  else {
			console.log("areaName before :" +area );	
		    areaName = area.split(";");
			areaName  = areaName[1];
			console.log("areaName after:" +areaName );
		  }

		  var regionName;
		  if(region == ""){
			  regionName  ="";
		  }
		  else {
		    regionName  = region .split(";");
			regionName  = regionName[1];
			console.log("regionName:" +regionName );
		  }
			
		  if(siteName == ""){
			  siteName  ="";
			}
			else {
				siteName  != "";
					    	    		 
				siteName  = siteName .split(";");
				siteName  = siteName[0];
				console.log("siteName:" +siteName );

				
			}

		   
			 /// Maximum
            if (checkBox1.checked == true){
          	  var maxChecked = "Max";
  	          console.log("Checkbox5 is checked." );
  	         
             }
            /// Minimum
            if (checkBox2.checked == true){
          	  var minChecked = "Min";
  	          console.log("Checkbox6 is checked." );
  	         
             }
            if (nullSiteCheckbox.checked == true){
            	  var nullSiteChecked = "nullSite";
    	         
               }
            
            
//Google Map Call AJax method
   		 $.ajax({
  		 	  type: "GET",
  		 	  contentType: "application/json; charset=utf-8",
  		 	  url: '${pageContext.request.contextPath}/GetRevenuePerSite',
  		 	  data: {
  				 	"allSitesName" :allSitesName,
  				 	"area": areaName,
  				 	"region":regionName,
  					"startDate" : startDate,
  					"endDate" : endDate,
  				  	"period":selectedPeriodOption,
  				  	"selectedTech":siteSelectedTech,
  									
  								
  		 				 },

  		 	dataType: "json",
  		 	success: function (data) {
  				listSites=data.listSites;
  				listSitesClrFltr=listSites;
  		 		if (data != null) {
  		 		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
  		 				     		
  		 		    document.getElementById("mapContainer").innerHTML ="";

  		 			var map = new google.maps.Map(document.getElementById("mapContainer"), {

  		 				 mapTypeControl: false,
  		 				 center:Nairobi ,
  					      zoom: 6,
  		  				 
  		 				 mapTypeControl: true,
  		 				 mapTypeControlOptions: {
  		 					 style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
  		 					 position: google.maps.ControlPosition.TOP_CENTER,
  		 					 },
  		 					 zoomControl: true,
  		 					 zoomControlOptions: {
  		 						 position: google.maps.ControlPosition.LEFT_CENTER,
  		 						 },

  		 						 scaleControl: true,
  		 						 streetViewControl: true,
  		 						 streetViewControlOptions: {
  		 							 position: google.maps.ControlPosition.LEFT_TOP,
  		 							 },
  		 							 
  		 							 style: 'mapbox://styles/mapbox/streets-v11',
  		 							 fullscreenControl: true,
  		 							 });
  		 		//Add buttons on map
  		 			const centerControlDiv = document.createElement("div");
  		 		    LegendControl(centerControlDiv, map);
  		 		    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

  		 		    const DefaultZoomDiv = document.createElement("div");
  		 		    DefaultZoomControl(DefaultZoomDiv, map);
  		 		    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

  		 		    const maxRevControlDiv = document.createElement("div");
  		 		    MaxRevenueControl(maxRevControlDiv, map);
  		 		    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

  		 		    const minRevControlDiv = document.createElement("div");
  		 		    MinRevenueControl(minRevControlDiv, map);
  		 		    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
  		 		    
  		 		 warehouseSites=[];
		 		 warehousePrepaidSites=[];
			 		 
		 		  if (allSitesName != "" ) {
					wareAllSites=[];
					allWareSites=[];

		 			if(checkBox1.checked == false && checkBox2.checked == false){
						UnselectAll();
						AddSelectedSiteColor(data.listSites,map);

						selectedSiteData=data.listSites;
						warehouseSites = data.listWarehouseSites;
						tempPrepaidRevSitesList=data.listSites;
						wareAllSites = data.listAllWarehouseSites;
						
		 			}
		 		 else if (checkBox1.checked == true && checkBox2.checked == false){
		 			UnselectAll();			
		            MaxMinSetColor(data.listSites,map,"max");
		            listSites=maxList;

			        warehouseSites = data.listWarehouseSites;
					selectedSiteData=data.listSites;
					tempPrepaidRevSitesList=data.listSites;
					wareAllSites = data.listAllWarehouseSites;
					
					
	  		 	}
		 		 else if (checkBox1.checked == false && checkBox2.checked == true){
		 			UnselectAll();			
		            MaxMinSetColor(data.listSites,map,"min");
		            listSites=minList;

					selectedSiteData=data.listSites;
		            warehouseSites = data.listWarehouseSites;
					tempPrepaidRevSitesList=data.listSites;
					wareAllSites = data.listAllWarehouseSites;
					
					
	  		 	}
		 		 else if (checkBox1.checked == true && checkBox2.checked == true){
		 			UnselectAll();	
		            MaxMinSetColor(data.listSites,map,"maxMin");
		            listSites=maxMinList;

			        warehouseSites = data.listWarehouseSites;
					selectedSiteData=data.listSites;
					tempPrepaidRevSitesList=data.listSites;
					wareAllSites = data.listAllWarehouseSites;
			       
	  		 	}

		 		//Get difference between the 2 arrays
		 		if(tempPrepaidRevSitesList.length>0){
					count=0;
					for(i=0;i<tempPrepaidRevSitesList.length;i++){
						for(j=0;j<warehouseSites.length;j++){
							if( tempPrepaidRevSitesList[i][0] != warehouseSites[j][0]) {
								count++;
								}
						}
						if(count == warehouseSites.length){
							warehousePrepaidSites.push(tempPrepaidRevSitesList[i]);
							}
						count =0;
						}
		 		}

				if(wareAllSites.length>0){
					var tempSites =[]; 
					   
					for(i=0;i<wareAllSites.length;i++) {
						if(!tempSites.includes(wareAllSites[i][0])) {
						tempSites.push(wareAllSites[i][0]);
						allWareSites.push(wareAllSites[i]);
						
						}
					
					}	
	    		}
					
				}
		 		  else  if (areaName !="" ) {
		 			 wareAllSites=[];
					 allWareSites=[];
						
		 			 if(checkBox1.checked == false && checkBox2.checked == false){
							UnselectAll();
							console.log("data is " +data.listWarehouseSites);
							setColor(listSites,map);
						    warehouseSites = data.listWarehouseSites;
							//warehousePrepaidSites=data.listSites;
							selectedSiteData=data.listSites;
							wareAllSites = data.listAllWarehouseSites;
							}
		 			 else if (checkBox1.checked == true && checkBox2.checked == false){
	  		 				UnselectAll();			
				            MaxMinSetColor(data.listSites,map,"max");
				            listSites=maxList;

					         warehouseSites = data.listWarehouseSites;
							//warehousePrepaidSites=data.listSites;
							selectedSiteData=data.listSites;
							wareAllSites = data.listAllWarehouseSites;
	  	  		 		 }
	  		 		 else if (checkBox1.checked == false && checkBox2.checked == true){
	  		 			  	UnselectAll();			
				            MaxMinSetColor(data.listSites,map,"min");
				            listSites=minList;

				            warehouseSites = data.listWarehouseSites;
							//warehousePrepaidSites=data.listSites;
							selectedSiteData=data.listSites;
							wareAllSites = data.listAllWarehouseSites;

	  	  		 		 }
	  		 		 else if (checkBox1.checked == true && checkBox2.checked == true){
	  		 			    UnselectAll();	
				            MaxMinSetColor(data.listSites,map,"maxMin");
				            listSites=maxMinList;
				            warehouseSites = data.listWarehouseSites;
							//warehousePrepaidSites=data.listSites;
							selectedSiteData=data.listSites;
							wareAllSites = data.listAllWarehouseSites;
					          
	  	  		 		 }
  	  		 		 if(allSitesName != "" ) {
  						tempPrepaidRevSitesList=data.listSites;

  						 
  	  	  		 		if(tempPrepaidRevSitesList.length>0){
  	  						count=0;
  	  						for(i=0;i<tempPrepaidRevSitesList.length;i++){
  	  							for(j=0;j<warehouseSites.length;j++){
  	  								if( tempPrepaidRevSitesList[i][0] != warehouseSites[j][0]) {
  	  									count++;
  	  									}
  	  							}
  	  							if(count == warehouseSites.length){
  	  								warehousePrepaidSites.push(tempPrepaidRevSitesList[i]);
  	  								}
  	  							count =0;
  	  							}
  	  			 		}
  	  	  		     }
  	  		 		 else {
					warehousePrepaidSites=data.listSites;
  	  	  		 	
  	  	  	  	}
		 			if(wareAllSites.length>0){
						var tempSites =[]; 
						   
						for(i=0;i<wareAllSites.length;i++) {
							if(!tempSites.includes(wareAllSites[i][0])) {
							tempSites.push(wareAllSites[i][0]);
							allWareSites.push(wareAllSites[i]);
							
							}
						
						}	
		    		}
				}
		 		 
			else {
				wareAllSites=[];
			    allWareSites=[];
					
			   if(checkBox1.checked == false && checkBox2.checked == false && (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu" )  ){
		         
		            UnselectAll();
		            setColor(listSites,map);
					warehousePrepaidSites=data.listSites;
					selectedSiteData=data.listSites;
					wareAllSites = data.listAllWarehouseSites;
		         }

			   else if (checkBox1.checked == true && checkBox2.checked == false && (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu") ){

		            UnselectAll();			
		            MaxMinSetColor(data.listSites,map,"max");
		            listSites=maxList;
					warehousePrepaidSites=maxList;
					selectedSiteData=data.listSites;
					wareAllSites = data.listAllWarehouseSites;
				}
			   else if (checkBox1.checked == false && checkBox2.checked == true &&  (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu") ){

		            UnselectAll();			
		            MaxMinSetColor(data.listSites,map,"min");
		            listSites=minList;
					warehousePrepaidSites=minList;
					selectedSiteData=data.listSites;
					wareAllSites = data.listAllWarehouseSites;
		       }
			   else if (checkBox1.checked == true && checkBox2.checked == true &&  (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu") ){

		            UnselectAll();	
		            MaxMinSetColor(data.listSites,map,"maxMin");
		            listSites=maxMinList;
					warehousePrepaidSites=maxMinList;
					selectedSiteData=data.listSites;
					wareAllSites = data.listAllWarehouseSites;
					
		        }
			    warehouseSites = data.listWarehouseSites;
				
				if(wareAllSites.length>0){
					var tempSites =[]; 
					   
					for(i=0;i<wareAllSites.length;i++) {
						if(!tempSites.includes(wareAllSites[i][0])) {
						tempSites.push(wareAllSites[i][0]);
						allWareSites.push(wareAllSites[i]);
						
						}
					
					}	
	    		}
				}
 		
		 		}
		 			

	  		 	},
		 		 error: function(result) {
		 			 alert("Error");
		 			 }
		 		 });
 // for the grid	     
 		$.ajax({
				type : "GET",
				contentType: "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/GenerateRevenueReport",
				dataType : "json",
				data : {
				    "startDate" : $("#startdate").val(),
				    "endDate" : $("#enddate").val(),
				    "site":siteName,
                    "area": areaName,
                    "technologyRegions": regionName,
                    "period": selectedPeriodOption,
		            "Payment Method" : $("#Payment Method").children("option:selected").val(),
				    "technologies" :selectedTechOption ,
					"Max": maxChecked,
					"Min": minChecked,
  				 	"allSitesName" :allSitesName,
  				 	"nullSite":nullSiteChecked,
  				 	"selectedTech":siteSelectedTech,
					
				},
				success : function(data) {
					
					 if (data != null) {
				       // ReportArrayGlobal = [];
                      ReportArrayGlobal = data.revenueReportList; 
                      //console.log("the global array returned "+ReportArrayGlobal);
                      if (ReportArrayGlobal.length == 0) { 
                     
                    	  alert("There is no data to display between these two selected dates");
                      }
  					

                     	 var almgrid = new AlmgridTable({
                              tableId: "gridTable",
                              dataArray: ReportArrayGlobal,
                              selectCheckbox: false,
                              drawTableGrid :  function (tableId, dataArray) {

               				   // let almgrid = this;
             			        var tableBody = document.querySelector("#" + tableId + " tbody");
             			        var columnLinkNb = this.columnLinkNb;
             			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
             			        var gridContainerId = tableId + "_container";
             			        $(gridContainer).attr('id', gridContainerId);
             			        $(tableBody).empty();
             			        if (dataArray.length > 0) {
      					 
      	                       var dataRev=0,smsRev=0,voiceRev=0,grossRev=0;
      			               var ArrayKeys = Object.keys(dataArray[0]);
      			               //var itemRow = "";
      			       		   var columnVal;
      			       		  
      			       		   ////for export
      			       		    var data = [];
      			       	       exportArrayGrid = [];
      			       		   data.push('\r');
      			       		   data.push(["Site", "Area", "Start Date", "End Date","Voice Revenue","SMS Revenue","Data Revenue","VAS Revenue","Combination Technology"]);
      			       		
      			       		
      			           for (var i = 0; i < dataArray.length; i++) {
      				           
      						//	itemRow += "<tr class='filterRows'>";
      							 // for export 
  		          				data.push('\r');
      							
      			               for (var j = 0; j < ArrayKeys.length; j++) {
      			               	
      			            	 columnVal = ArrayKeys[j];
      			                 // for export 
      			            	 data.push(dataArray[i][ArrayKeys[j]]);
      			            	 
      			                  /* if (j == 0) {
      			                       if (this.selectCheckbox == true) {
      			                           itemRow += "<td class='table-select-all'><input type='checkbox' class='table-select-checkbox' value='" + dataArray[i][ArrayKeys[j]] + "'></td>";
      			                       }
      			                   } else {*/
      			                      // itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>"; 
      			                       if(columnVal =="smsRevenue")   smsRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
      			                       if(columnVal =="dataRevneue")   dataRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
      			                       if(columnVal =="voiceRevenue")   voiceRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
      			                       if(columnVal =="vasRevenue")   grossRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
      		          			   // }
      							   
      		          			   }
      		          			// itemRow += "</tr>";
      		          		   }

      			                /// for export
			                   exportArrayGrid.push(data);
      			          
      			 		          var roundedsms = Math.round((smsRev + Number.EPSILON) * 100) / 100;
      			 		          var roundedvoice=Math.round((voiceRev + Number.EPSILON) * 100) / 100;
      			 		          var roundedgross=Math.round((grossRev + Number.EPSILON) * 100) / 100;
      			 		          var roundedData=Math.round((dataRev + Number.EPSILON) * 100) / 100;
      			 		          var roundedtotal=Math.round((smsRev+voiceRev+grossRev+dataRev + Number.EPSILON) * 100) / 100;
      			 		
      			 		 
      										$('#smsRev').val(roundedsms);
      										$('#dataRev').val(roundedData);
      										$('#voiceRev').val(roundedvoice);
      										$('#grossRev').val(roundedgross);
      										$('#totalRev').val(roundedtotal);
      										
      		          	            //$(tableBody).append(itemRow);

      		          	        }
      		          			        else{
      											 
      		          			  		$('#smsRev').val('0');
      									$('#dataRev').val('0');
      									$('#voiceRev').val('0');
      									$('#grossRev').val('0');
      									$('#totalRev').val('0');
      	      					  }
      				  
      		         	        // Method for pagination almgrid-pagecount-box
      		          	        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
      		          	        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

      		          	        // For global search textbox
      		          	        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

      		          	        var paginationId = tableId + "_pagination";


      		          	        // Page Rows number
      		          	        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
      		          	        nbRows = parseInt(nbRows);

      		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox });

      		          	        // Drawing for the first time
      		          	        if (this.initFlag == 0) {
      		          	            var tables = document.getElementsByClassName('almgrid-table');
      		          	            for (var i = 0; i < tables.length; i++) {
      		          	                resizableGrid(tables[i]);
      		          	            }
      																	 
      		          	        }
      		          	        this.initFlag++;
      		          	       },
                               });
                      
                     	
                        
                    //draw charts for the areas and sites
                     // renderAllCharts();

				}
						/*SiteRevenue(ReportArrayGlobal);
						PieRevenue(ReportArrayGlobal);
						fetchRevenuePerTech(ReportArrayGlobal, $("#startdate1").val(), $("#enddate1").val());
						fetchLineChartRevenue(ReportArrayGlobal);*/
	  },
	  
	  error : function(error) {
			console.log("The error is " + error);
		}
  });

// for the charts
 		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateRevenueCharts",
			dataType : "json",
			data : {
			    "startDate" : $("#startdate").val(),
			    "endDate" : $("#enddate").val(),
			    "site":siteName,
                "area": areaName,
                "technologyRegions": regionName,
                "period": selectedPeriodOption,
	            "Payment Method" : $("#Payment Method").children("option:selected").val(),
			    "technologies" :selectedTechOption ,
				"Max": maxChecked,
				"Min": minChecked,
				"allSitesName":allSitesName,
				 "nullSite":nullSiteChecked,
				 "selectedTech":siteSelectedTech,
					
			},
			success : function(data) {
				
				
				 if (data != null) {
					    listChartSites=data.listChartSites;
					    //console.log("*******listChartSites "+listChartSites);
		  				listSitesClrFltr=data.chartData;
		  				var chartData = data.chartData;
		  				pieRevenue = data.pieRevenue;
						
		  		 		PieTech(chartData);
		  		 		PieRevPerTech(chartData);
	
                  //msColumnChartObj=data.msColumnChartObj;jQuery.parseJSON(data.msColumnChartObj);//msColumnChartObj)[0]
		  		  msColumnChartObj=jQuery.parseJSON(data.msColumnChartObj);
                  pieRevenue=jQuery.parseJSON(data.pieRevenue);
                  //pieRevenue=data.pieRevenue;
                  //console.log("before StackedandLineRevenue data "+data.StackedandLineRevenue );
                  var StackedandLineRevenue = jQuery.parseJSON(data.StackedandLineRevenue);
                  var MaxStackedandLineRevenue = jQuery.parseJSON(data.MaxStackedandLineRevenue);
                  var MinStackedandLineRevenue = jQuery.parseJSON(data.MinStackedandLineRevenue);
                  //console.log("StackedandLineRevenue data "+StackedandLineRevenue );
                 /* if (msColumnChartObj.length> 0 && pieRevenue.length> 0 && StackedandLineRevenue.length> 0) { 
                 	 console.log("*******Enter ");
                  }
					else{
						//  alert("There is no data to display between these two selected dates for charts!!");
						   
					}   */  
			}
				 if(selectedPeriodOption == "Accu" || selectedPeriodOption == "null"){
					 if(checkBox1.checked == true || checkBox2.checked == true){
						 $("#time_revenue_stack").css("display", "none");
						 $("#time_revenue_line").css("display", "none");
						 $("#site_revenue_chart").css("display", "none");
						 $('#pie_charts').css("display", "none");
						 $("#max_time_revenue_stack").css("display", "none");
						 $("#max_time_revenue_line").css("display", "none");
						 $("#min_time_revenue_stack").css("display", "none");
						 $("#min_time_revenue_line").css("display", "none");
						 $("#chartContainer").css("display", "none");
						 $("#noCharts").css("display", "block");
						
						 
						 

						 

							/*  $('#collapseThree').on('hide.bs.collapse', function () {
							    $(this).siblings('.panel-heading').addClass('active');
							  });*/
							  
					     /*$('#collapseThree').siblings('.panel-heading').addClass('active');
					     $('#collapseThree').siblings('.panel-collapse collapse').removeClass('show');
					     $("#collapseThree").css("height", "0px");
					     $("#collapseThree").css("display", "none");
					     $("#chartsPanel").attr("aria-expanded","false");
					     $("#chartsPanel").addClass('collapsed');*/
					     
							 
					 }
					 
					  /*if(checkBox1.checked == true && checkBox2.checked == true){
						// console.log("hereeeeee");
						 $("#time_revenue_stack").css("display", "none");
						 $("#time_revenue_line").css("display", "none");
						 $("#site_revenue_chart").css("display", "none");
						 $('#pie_charts').css("display", "none");
						 $("#max_time_revenue_stack").css("display", "none");
						 $("#max_time_revenue_line").css("display", "none");
						 $("#min_time_revenue_stack").css("display", "none");
						 $("#min_time_revenue_line").css("display", "none");
					 }
					  else if(checkBox1.checked == true ){
						 $("#time_revenue_stack").css("display", "none");
						 $("#time_revenue_line").css("display", "none");
						 $("#site_revenue_chart").css("display", "none");
						 $('#pie_charts').css("display", "block");
						 $('#pie_charts').css({'margin-top':'-150px'}); 
						 //$("#div.container-fluid-responsive").css({'padding':'0px'}); 
						 $("#max_time_revenue_stack").css("display", "none");
						 $("#max_time_revenue_line").css("display", "none");
						 $("#min_time_revenue_stack").css("display", "none");
						 $("#min_time_revenue_line").css("display", "none");
						 
					 }
					  else if(checkBox2.checked == true ){
							 $("#time_revenue_stack").css("display", "none");
							 $("#time_revenue_line").css("display", "none");
							 $("#site_revenue_chart").css("display", "none");
							 $('#pie_charts').css({'margin-top':'-150px'}); 
							 //$("#div.container-fluid-responsive").css({'padding':'0px'}); 
							 $("#max_time_revenue_stack").css("display", "none");
							 $("#max_time_revenue_line").css("display", "none");
							 $("#min_time_revenue_stack").css("display", "none");
							 $("#min_time_revenue_line").css("display", "none");
							 
						 }*/
					 else{
						 /*($('#collapseThree').siblings('.panel-heading').removeClass('active');
					     $('#collapseThree').siblings('.panel-collapse collapse').addClass('show');
					     $("#collapseThree").css("height", "198px");
					     $("#chartsPanel").attr("aria-expanded","true");
					     $("#chartsPanel").removeClass('collapsed');
						 $("#collapseThree").css("display", "block");
						 
						 $('#collapseThree').on('show.bs.collapse', function () {
						        
						$(this).siblings('.panel-heading').removeClass('active');
						});

					   $('#collapseThree').on('hide.bs.collapse', function () {
							$(this).siblings('.panel-heading').addClass('active');
							  });*/
					    $("#noCharts").css("display", "none");

						 
						// console.log("herecuuuuu");
						 $("#chartContainer").css("display", "block");
						$("#time_revenue_stack").css("display", "none");
						$("#time_revenue_line").css("display", "none");
					    $("#site_revenue_chart").css("display", "block");
					    $("#pie_charts").css("display", "block");
					    $('#pie_charts').css({'margin-top':'-120px'}); 
					    $("#max_time_revenue_stack").css("display", "none");
						 $("#max_time_revenue_line").css("display", "none");
						 $("#min_time_revenue_stack").css("display", "none");
						 $("#min_time_revenue_line").css("display", "none"); 

						 SiteRevenue(msColumnChartObj);
						 PieRevenue(pieRevenue);  
					    }  
					}
				
					/*else if(selectedmaxLineChartContainerOption == "Weekly" || selectedPeriodOption == "Monthly" ){
					 fetchRevenuePerTechSecondary(StackedandLineRevenue, $("#startdate1").val(), $("#enddate1").val());
						fetchLineChartRevenueSecondary(StackedandLineRevenue);
						$("#time_revenue_stack").css("display", "block");
						$("#time_revenue_line").css("display", "block");
						 $("#site_revenue_chart").css("display", "block");
						 $('#pie_charts').css({'margin-top':'0px'}); 
						}*/
                 
				
				 else{
					// console.log("here Yaraa");
					 $("#noCharts").css("display", "none");
					 $("#chartContainer").css("display", "block");
					 if(checkBox1.checked == true || checkBox2.checked == true){
							//console.log("***Max or min charts");
							 $("#time_revenue_stack").css("display", "none");
							 $("#time_revenue_line").css("display", "none");
							 $("#site_revenue_chart").css("display", "none");
							 $('#pie_charts').css("display", "none");
							 if(checkBox1.checked == true && checkBox2.checked == false){
								//console.log("StackedandLineRevenue jsp"+StackedandLineRevenue );
								
								  	 
								 $("#max_time_revenue_stack").css("display", "block");
								 $("#max_time_revenue_line").css("display", "block");
								 $('#max_time_revenue_stack').css({'margin-top':'-40px'});
								 $('#max_time_revenue_line').css({'margin-top':'-40px'});
								 $("#min_time_revenue_stack").css("display", "none");
								 $("#min_time_revenue_line").css("display", "none");
								 //$('#pie_charts').css("display", "block"); 
								 //$('#pie_charts').css({'margin-top':'-10px'});
								
								 fetchRevenuePerTechMaxMin(MaxStackedandLineRevenue, $("#startdate").val(), $("#enddate").val(),"Max");
								 fetchLineChartRevenueMaxMin(MaxStackedandLineRevenue,"Max");
							 }
							 else if(checkBox1.checked == false && checkBox2.checked == true){
								
								 $("#max_time_revenue_stack").css("display", "none");
								 $("#max_time_revenue_line").css("display", "none");
								 $("#min_time_revenue_stack").css("display", "block");
								 $("#min_time_revenue_line").css("display", "block");
								 $('#min_time_revenue_stack').css({'margin-top':'-65px'});
								 $('#min_time_revenue_line').css({'margin-top':'-50px'});
								 //$('#pie_charts').css("display", "block"); 
								 //$('#pie_charts').css({'margin-top':'10px'});
								 
								 fetchRevenuePerTechMaxMin(MinStackedandLineRevenue, $("#startdate").val(), $("#enddate").val(),"Min");
								 fetchLineChartRevenueMaxMin(MinStackedandLineRevenue,"Min");
							 }
							 else if(checkBox1.checked == true && checkBox2.checked == true){
								// $('#pie_charts').css("display", "none");
								 $('#max_time_revenue_stack').css({'margin-top':'-45px'});
								 $("#max_time_revenue_stack").css("display", "block");
								 
								 $("#min_time_revenue_stack").css("display", "block");
								 $('#min_time_revenue_stack').css({'margin-top':'0px'});
								 $('#max_time_revenue_line').css({'margin-top':'-20px'});
								 $("#max_time_revenue_line").css("display", "block");
								 $("#min_time_revenue_line").css("display", "block");
								 $('#min_time_revenue_line').css({'margin-top':'0px'});
								 fetchRevenuePerTechMaxMin(MaxStackedandLineRevenue, $("#startdate").val(), $("#enddate").val(),"Max");
								 fetchLineChartRevenueMaxMin(MaxStackedandLineRevenue,"Max");
								 fetchRevenuePerTechMaxMin(MinStackedandLineRevenue, $("#startdate").val(), $("#enddate").val(),"Min");
								 fetchLineChartRevenueMaxMin(MinStackedandLineRevenue,"Min");
							}
							
						}
					 else{
						 $("#time_revenue_stack").css("display", "block");
						 $("#time_revenue_line").css("display", "block");
						 $('#time_revenue_line').css({'margin-top':'-50px'}); 
						 $("#site_revenue_chart").css("display", "block");
						 $("#pie_charts").css("display", "block");
						 $('#pie_charts').css({'margin-top':'-50px'}); 
						 $("#max_time_revenue_stack").css("display", "none");
					     $("#min_time_revenue_stack").css("display", "none");
						 $("#max_time_revenue_line").css("display", "none");
						 $("#min_time_revenue_line").css("display", "none");

						 SiteRevenue(msColumnChartObj);
						 PieRevenue(pieRevenue);
						 fetchRevenuePerTech(StackedandLineRevenue, $("#startdate").val(), $("#enddate").val());
						 fetchLineChartRevenue(StackedandLineRevenue);
					
							
					 }
					}
/*
				// disble site in max  and min cases if site selected global
				 if(siteName == ""){
					 $('#siteNameStackChartMax').removeAttr('disabled');
					 $('#siteNameLineChartMax').removeAttr('disabled');
					 $('#siteNameStackChartMin').removeAttr('disabled');
					 $('#siteNameLineChartMin').removeAttr('disabled');
					 
				 }
				else {
					 $('#siteNameStackChartMax').attr('disabled', 'disabled');
					 $('#siteNameLineChartMax').attr('disabled', 'disabled');
					 $('#siteNameStackChartMin').attr('disabled', 'disabled');
					 $('#siteNameLineChartMin').attr('disabled', 'disabled');
				}
*/					
					
  },
  
  error : function(error) {
		console.log("The error is " + error);
	}
});
 		var fltrdStrtDt = $("#startdate").val();
		var fltrdEndDt = $("#enddate").val();
		var fltrdSiteNme = $('#site').val();
		//var fltrdTech = $("#technologies").children("option:selected").val();
		var fltrdTech = siteSelectedTech;
		
		 $("#startdateMSChart").val(fltrdStrtDt).trigger('change');
		 $("#startdateStackChart").val(fltrdStrtDt).trigger('change');
		 $("#startdateLineChart").val(fltrdStrtDt).trigger('change');
		 $("#startdatePieChart").val(fltrdStrtDt).trigger('change');
		 $("#startdateStackChartMax").val(fltrdStrtDt).trigger('change');
		 $("#startdateLineChartMax").val(fltrdStrtDt).trigger('change');
		 $("#startdateStackChartMin").val(fltrdStrtDt).trigger('change');
		 $("#startdateLineChartMin").val(fltrdStrtDt).trigger('change');
			 
			 $("#enddateMSChart").val(fltrdEndDt).trigger('change');
			 $("#enddateStackChart").val(fltrdEndDt).trigger('change');
			 $("#enddateLineChart").val(fltrdEndDt).trigger('change');
			 $("#enddatePieChart").val(fltrdEndDt).trigger('change');
			 $("#enddateStackChartMax").val(fltrdEndDt).trigger('change');
			 $("#enddateLineChartMax").val(fltrdEndDt).trigger('change');
			 $("#enddateStackChartMin").val(fltrdEndDt).trigger('change');
			 $("#enddateLineChartMin").val(fltrdEndDt).trigger('change');
					
			$('#siteNameMSChart').val(fltrdSiteNme).trigger('change');
			$('#siteNameStackChart').val(fltrdSiteNme).trigger('change');
			$('#siteNameLineChart').val(fltrdSiteNme).trigger('change');
			$('#siteNamePieChart').val(fltrdSiteNme).trigger('change');
			/*$('#siteNameStackChartMax').val(fltrdSiteNme).trigger('change');
			$('#siteNameLineChartMax').val(fltrdSiteNme).trigger('change');
			$('#siteNameStackChartMin').val(fltrdSiteNme).trigger('change');
			$('#siteNameLineChartMin').val(fltrdSiteNme).trigger('change');*/
			
			/*   var value; 
			 if(fltrdTech == "2g"){
				 value = ['2g'];
				 }
			 else if(fltrdTech == "2g3g"){
				 value = ['2g3g'];
				 }
			 else if(fltrdTech == "2g3g4g"){
				 value = ['2g3g4g'];
				 }
			 document.querySelector('#technology').setValue(value); */
		//	console.log("fltrdTech" +arrSelected);
				
			document.querySelector('#msChartTechnologies').setValue(arrSelected);
			document.querySelector('#stackChartTechnologies').setValue(arrSelected);
			document.querySelector('#lineChartTechnologies').setValue(arrSelected);
			document.querySelector('#pieChartTechnologies').setValue(arrSelected);
			document.querySelector('#maxStackChartTechnologies').setValue(arrSelected);
			document.querySelector('#minStackChartTechnologies').setValue(arrSelected);
			document.querySelector('#maxLineChartTechnologies').setValue(arrSelected);
			document.querySelector('#minLineChartTechnologies').setValue(arrSelected);
				
					
			// $("#technologiesMSChart").val(fltrdTech).trigger('change');
			// $("#technologiesStackChart").val(fltrdTech).trigger('change');
			// $("#technologiesLineChart").val(fltrdTech).trigger('change');
			// $("#technologiesPieChart").val(fltrdTech).trigger('change');
			// $("#technologiesStackChartMax").val(fltrdTech).trigger('change');
			// $("#technologiesStackChartMin").val(fltrdTech).trigger('change');
			// $("#technologiesLineChartMax").val(fltrdTech).trigger('change');
			// $("#technologiesLineChartMin").val(fltrdTech).trigger('change');

		
			 
			 $('#minValMSFltr').val('');
			 $('#maxValMSFltr').val('');
			 $('#minValStackedFltr').val('');
			 $('#maxValStackedFltr').val('');
			 $('#minValLineFltr').val('');
			 $('#maxValLineFltr').val('');
			 $('#minValPiesFltr').val('');
			 $('#maxValPiesFltr').val('');
			 /*$('#minValStackedFltrMax').val('');
			 $('#maxValStackedFltrMax').val('');
			 $('#minValStackedFltrMin').val('');
			 $('#maxValStackedFltrMin').val('');
			 $('#minValLineFltrMax').val('');
			 $('#maxValLineFltrMax').val('');
			 $('#minValLineFltrMin').val('');
			 $('#maxValLineFltrMin').val('');
			 */
           }
			


   });// end of the generate 



		
	  function setInputFilter(textbox, inputFilter) {
	  	  ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
	  	    textbox.addEventListener(event, function() {
	  	      if (inputFilter(this.value)) {
	  	        this.oldValue = this.value;
	  	        this.oldSelectionStart = this.selectionStart;
	  	        this.oldSelectionEnd = this.selectionEnd;
	  	      } else if (this.hasOwnProperty("oldValue")) {
	  	        this.value = this.oldValue;
	  	        this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	  	      } else {
	  	        this.value = "";
	  	      }
	  	    });
	  	  });
	  	}


	  	// input filters listeners.

	  	setInputFilter(document.getElementById("minValMSFltr"), function(value) {
	  	  return /^-?\d*[.,]?\d*$/.test(value); });
	  	setInputFilter(document.getElementById("maxValMSFltr"), function(value) {
	  	  return /^-?\d*[.,]?\d*$/.test(value); });
	  	setInputFilter(document.getElementById("minValStackedFltr"), function(value) {
	  		  return /^-?\d*[.,]?\d*$/.test(value); });
	  		setInputFilter(document.getElementById("maxValStackedFltr"), function(value) {
	  		  return /^-?\d*[.,]?\d*$/.test(value); });
	  		setInputFilter(document.getElementById("minValLineFltr"), function(value) {
	  			  return /^-?\d*[.,]?\d*$/.test(value); });
	  			setInputFilter(document.getElementById("maxValLineFltr"), function(value) {
	  			  return /^-?\d*[.,]?\d*$/.test(value); });
	  			setInputFilter(document.getElementById("minValPiesFltr"), function(value) {
	  				  return /^-?\d*[.,]?\d*$/.test(value); });
	  				setInputFilter(document.getElementById("maxValPiesFltr"), function(value) {
	  				  return /^-?\d*[.,]?\d*$/.test(value); });
	  				/*setInputFilter(document.getElementById("minValStackedFltrMax"), function(value) {
	  		  		  return /^-?\d*[.,]?\d*$/.test(value); });
	  		  		setInputFilter(document.getElementById("maxValStackedFltrMax"), function(value) {
	  		  		  return /^-?\d*[.,]?\d*$/.test(value); });
	  		  	     setInputFilter(document.getElementById("minValStackedFltrMin"), function(value) {
	  	  		       return /^-?\d*[.,]?\d*$/.test(value); });
	  	  		     setInputFilter(document.getElementById("maxValStackedFltrMin"), function(value) {
	  	  		       return /^-?\d*[.,]?\d*$/.test(value); });
	  	  	          setInputFilter(document.getElementById("minValLineFltrMax"), function(value) {
	  			        return /^-?\d*[.,]?\d*$/.test(value); });
	  		        	setInputFilter(document.getElementById("maxValLineFltrMax"), function(value) {
	  			           return /^-?\d*[.,]?\d*$/.test(value); });
	  			         setInputFilter(document.getElementById("minValLineFltrMin"), function(value) {
		  			      return /^-?\d*[.,]?\d*$/.test(value); });
		  			      setInputFilter(document.getElementById("maxValLineFltrMin"), function(value) {
		  			        return /^-?\d*[.,]?\d*$/.test(value); });*/
	  				  
		  			      // MS Chart site name autocomplete with multiselect 	  				  
			  				
				  		var msChartSelectedItems=[];
				  		var msChartText;
				  		var msChartChecked;
				  		var msChartAllSitesArr=[];
			  		 	 $("#siteNameMSChart").autocomplete({
			  				showHeader: true,
			  				
			  			// without this focus fct: keyboard movements reset the input to ''
			  			  focus: function( event, ui ) {
			  			        event.preventDefault(); 
			  			        if (event.Keycode ==38||event.Keycode ==40 ) {
			  			        	var terms = split($("#msChartSitesAutocomplete").val());
			  					        terms.pop();
			  					        terms.push( ui.item[0]);
			  					      msChartSelectedItems.push(terms);
			  					        txt=terms;
			  					        terms.push("");

			  			        var siteInput=siteNameMSChart.value;
			  				    var siteNameInp = siteInput.split(";");
			  				    siteNameInp  = siteNameInp[0]+" ";
			  				    
			  			       $("#msChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
			  			    		
			  			     }
			  			  }, // end of focus fct
			  		  					  		        
			  		        source: function(request, response) {
			  		        //Extract last site name from input field
			  		          var searchText = extractLast(request.term);
			  		         
			  			             $.ajax({
			  			                 type: "GET",
			  			                 contentType: "application/json; charset=utf-8",
			  			                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
			  			                 data: {
			  			                	 "SiteId" : searchText,
			  									
			  							 },
			  			                 dataType: "json",
			  			                 success: function (data) {
			  			                     if (data != null) {	  				                     
			  			                         response(data.listSites);
			  			                       
			  			                     }
			  			                 },
			  			                 error: function(result) {
			  			                     alert("Error");
			  			                 }
			  			             });
			  			         }, minLength:0, maxShowItems: 100, scroll:true,		
			  		               
			  		        
			  				select: function(event, ui) {

			  					msChartSelectedItems=[];
			  				    msChartText = $("#msChartSitesAutocomplete").val();
			  				    msChartText = msChartText == null || msChartText == undefined ? "" : msChartText;

			  				// AllSitesArr is equal to the internal array after removing a tag input
			  					$('#msChartSitesAutocomplete').on('itemRemoved', function(event) {						 
			  						msChartAllSitesArr = $("#msChartSitesAutocomplete").data('tagsinput').itemsArray;
			  					}); 

			  					$('#msChartSitesAutocomplete').on('itemAdded', function(event) {
			  						 
			  						msChartAllSitesArr = $("#msChartSitesAutocomplete").data('tagsinput').itemsArray;
			  					});


			  					msChartChecked = (msChartText.indexOf(ui.item[0] + ' ') > -1 ? 'checked' : 'unchecked');

			  					var selectedSite = ui.item[0] +" ";
			  					
			  					if (msChartChecked == 'checked') {
				  					
			  						var lastSite = document.getElementById('siteNameMSChart').value;
			  						var uncheckedSite = lastSite.split(";");
			  						var uncheckedSite  = uncheckedSite[0];
			  					
			  			  			var uncheckedSiteName = ui.item[0]+" ";
			  	                   var newAllSitesArrMsChart = [];

			  	                 for(i=0;i<msChartAllSitesArr.length;i++) {
			 						if (msChartAllSitesArr[i] != selectedSite) {
			 							newAllSitesArrMsChart.push(msChartAllSitesArr[i]);
			 						}
			 					}	

			  	               msChartAllSitesArr = newAllSitesArrMsChart;
			  	               
			  	             //Remove all tags
			                   $("#msChartSitesAutocomplete").tagsinput('removeAll');

								for(i=0;i<newAllSitesArrMsChart.length;i++) {
					                  var siteNameItem = newAllSitesArrMsChart[i].split(',') ;
					                // Add checked tags
					                $("#msChartSitesAutocomplete").tagsinput('add', siteNameItem.toString());
					               				                    
				                    }
								if (ui.item[0] == selectedSite ) {
							           document.getElementById('siteNameMSChart').value = '';
								}
						        }
						 
						        else {  
						        	 var terms = split( $("#msChartSitesAutocomplete").val() );
						 	        terms.pop();
						 	        terms.push( ui.item[0]);
						 	       msChartSelectedItems.push(terms);
						 	        txt=terms;
						 	        terms.push( "" );
						 					          
			  					
			  					siteNameMSChart.value = (ui.item ? ui.item[0] +";"+ ui.item[1] +" "  : '');
			  					siteNameMSChart.value = this.value;

			  					var siteInput=siteNameMSChart.value;
			  				    var siteNameInp = siteInput.split(";");
			  				    siteNameInp  = siteNameInp[0]+" ";
			  				    
			  			       $("#msChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
						        }
			  					
			  					return false;
			  					}
			  				    }).autocomplete("instance")._renderItem = function(ul, item) {
			  				    
					  		 		var text = $("#msChartSitesAutocomplete").val();
								    text = text == null || text == undefined ? "" : text;
								  
			  			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
			  			    	item[0]="No site name";
			  			    	item[1]="null";
			  			    	item[2]="null";
			  			    	item[3]="null";
			  			    	
			  		           
			  				    }
			  			       var checked = (text.indexOf(item[0] + ' ') > -1 ? 'checked' : 'unchecked');
			  				    
			  			            return $("<li class='each'>")
			  		                .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[0]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
			  		        item[0] + "</span><br><span class='desc'>" +
			  		        item[1] + "</span><span class='desc'>" +";"+
			  		        item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
			  		        	};
			  		        	$("#siteNameMSChart").focus(function(){
				  		        	if (this.value == ""){
				  		            	$(this).autocomplete("search");
				  		        	}						
				  					});



	  		        	var stackChartSelectedItems=[];
				  		var stackChartText;
				  		var stackChartChecked;
				  		var stackChartAllSitesArr=[];
			  		 	 $("#siteNameStackChart").autocomplete({
			  				showHeader: true,
			  				
			  			// without this focus fct: keyboard movements reset the input to ''
			  			  focus: function( event, ui ) {
			  			        event.preventDefault(); 
			  			        if (event.Keycode ==38||event.Keycode ==40 ) {
			  			        	var terms = split($("#stackChartSitesAutocomplete").val());
			  					        terms.pop();
			  					        terms.push( ui.item[0]);
			  					        stackChartSelectedItems.push(terms);
			  					        txt=terms;
			  					        terms.push("");

			  			        var siteInput=siteNameStackChart.value;
			  				    var siteNameInp = siteInput.split(";");
			  				    siteNameInp  = siteNameInp[0]+" ";
			  				    
			  			       $("#stackChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
			  			    		
			  			     }
			  			  }, // end of focus fct
			  		  					  		        
			  		        source: function(request, response) {
			  		        //Extract last site name from input field
			  		          var searchText = extractLast(request.term);
			  		         
			  			             $.ajax({
			  			                 type: "GET",
			  			                 contentType: "application/json; charset=utf-8",
			  			                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
			  			                 data: {
			  			                	 "SiteId" : searchText,
			  									
			  							 },
			  			                 dataType: "json",
			  			                 success: function (data) {
			  			                     if (data != null) {	  				                     
			  			                         response(data.listSites);
			  			                       
			  			                     }
			  			                 },
			  			                 error: function(result) {
			  			                     alert("Error");
			  			                 }
			  			             });
			  			         }, minLength:0, maxShowItems: 100, scroll:true,		
			  		               
			  		        
			  				select: function(event, ui) {

			  					stackChartSelectedItems=[];
			  				    stackChartText = $("#stackChartSitesAutocomplete").val();
			  				    stackChartText = stackChartText == null || stackChartText == undefined ? "" : stackChartText;

			  				// AllSitesArr is equal to the internal array after removing a tag input
			  					$('#stackChartSitesAutocomplete').on('itemRemoved', function(event) {						 
			  						stackChartAllSitesArr = $("#stackChartSitesAutocomplete").data('tagsinput').itemsArray;
			  					}); 

			  					$('#stackChartSitesAutocomplete').on('itemAdded', function(event) {
			  						 
			  						stackChartAllSitesArr = $("#stackChartSitesAutocomplete").data('tagsinput').itemsArray;
			  					});


			  					stackChartChecked = (stackChartText.indexOf(ui.item[0] + ' ') > -1 ? 'checked' : 'unchecked');

			  					var selectedSite = ui.item[0] +" ";
			  					
			  					if (stackChartChecked == 'checked') {
				  					
			  						var lastSite = document.getElementById('siteNameStackChart').value;
			  						var uncheckedSite = lastSite.split(";");
			  						var uncheckedSite  = uncheckedSite[0];
			  					
			  			  			var uncheckedSiteName = ui.item[0]+" ";
			  	                   var newAllSitesArrStackChart = [];

			  	                 for(i=0;i<stackChartAllSitesArr.length;i++) {
			 						if (stackChartAllSitesArr[i] != selectedSite) {
			 							newAllSitesArrStackChart.push(stackChartAllSitesArr[i]);
			 						}
			 					}	

			  	               stackChartAllSitesArr = newAllSitesArrStackChart;
			  	               
			  	             //Remove all tags
			                   $("#stackChartSitesAutocomplete").tagsinput('removeAll');

								for(i=0;i<newAllSitesArrStackChart.length;i++) {
					                  var siteNameItem = newAllSitesArrStackChart[i].split(',') ;
					                // Add checked tags
					                $("#stackChartSitesAutocomplete").tagsinput('add', siteNameItem.toString());
					               				                    
				                    }
								if (ui.item[0] == selectedSite ) {
							           document.getElementById('siteNameStackChart').value = '';
								}
						        }
						 
						        else {  
						        	 var terms = split( $("#stackChartSitesAutocomplete").val() );
						 	        terms.pop();
						 	        terms.push( ui.item[0]);
						 	       stackChartSelectedItems.push(terms);
						 	        txt=terms;
						 	        terms.push( "" );
						 					          
			  					
						 	       siteNameStackChart.value = (ui.item ? ui.item[0] +";"+ ui.item[1] +" "  : '');
						 	      siteNameStackChart.value = this.value;

			  					var siteInput=siteNameStackChart.value;
			  				    var siteNameInp = siteInput.split(";");
			  				    siteNameInp  = siteNameInp[0]+" ";
			  				    
			  			       $("#stackChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
						        }
			  					
			  					return false;
			  					}
			  				    }).autocomplete("instance")._renderItem = function(ul, item) {
			  				    
					  		 		var text = $("#stackChartSitesAutocomplete").val();
								    text = text == null || text == undefined ? "" : text;
								  
			  			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
			  			    	item[0]="No site name";
			  			    	item[1]="null";
			  			    	item[2]="null";
			  			    	item[3]="null";
			  			    	
			  		           
			  				    }
			  			       var checked = (text.indexOf(item[0] + ' ') > -1 ? 'checked' : 'unchecked');
			  				    
			  			            return $("<li class='each'>")
			  		                .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[0]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
			  		        item[0] + "</span><br><span class='desc'>" +
			  		        item[1] + "</span><span class='desc'>" +";"+
			  		        item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
			  		        	};
			  		        	$("#siteNameStackChart").focus(function(){
				  		        	if (this.value == ""){
				  		            	$(this).autocomplete("search");
				  		        	}						
				  					});

			  	  		      
	  		            	

		  		        	var lineChartSelectedItems=[];
					  		var lineChartText;
					  		var lineChartChecked;
					  		var lineChartAllSitesArr=[];
				  		 	 $("#siteNameLineChart").autocomplete({
				  				showHeader: true,
				  				
				  			// without this focus fct: keyboard movements reset the input to ''
				  			  focus: function( event, ui ) {
				  			        event.preventDefault(); 
				  			        if (event.Keycode ==38||event.Keycode ==40 ) {
				  			        	var terms = split($("#lineChartSitesAutocomplete").val());
				  					        terms.pop();
				  					        terms.push( ui.item[0]);
				  					        lineChartSelectedItems.push(terms);
				  					        txt=terms;
				  					        terms.push("");

				  			        var siteInput=siteNameLineChart.value;
				  				    var siteNameInp = siteInput.split(";");
				  				    siteNameInp  = siteNameInp[0]+" ";
				  				    
				  			       $("#lineChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
				  			    		
				  			     }
				  			  }, // end of focus fct
				  		  					  		        
				  		        source: function(request, response) {
				  		        //Extract last site name from input field
				  		          var searchText = extractLast(request.term);
				  		         
				  			             $.ajax({
				  			                 type: "GET",
				  			                 contentType: "application/json; charset=utf-8",
				  			                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
				  			                 data: {
				  			                	 "SiteId" : searchText,
				  									
				  							 },
				  			                 dataType: "json",
				  			                 success: function (data) {
				  			                     if (data != null) {	  				                     
				  			                         response(data.listSites);
				  			                       
				  			                     }
				  			                 },
				  			                 error: function(result) {
				  			                     alert("Error");
				  			                 }
				  			             });
				  			         }, minLength:0, maxShowItems: 100, scroll:true,		
				  		               
				  		        
				  				select: function(event, ui) {

				  					lineChartSelectedItems=[];
				  				    lineChartText = $("#lineChartSitesAutocomplete").val();
				  				    lineChartText = lineChartText == null || lineChartText == undefined ? "" : lineChartText;

				  				// AllSitesArr is equal to the internal array after removing a tag input
				  					$('#lineChartSitesAutocomplete').on('itemRemoved', function(event) {						 
				  						lineChartAllSitesArr = $("#lineChartSitesAutocomplete").data('tagsinput').itemsArray;
				  					}); 

				  					$('#lineChartSitesAutocomplete').on('itemAdded', function(event) {
				  						 
				  						lineChartAllSitesArr = $("#lineChartSitesAutocomplete").data('tagsinput').itemsArray;
				  					});


				  					lineChartChecked = (lineChartText.indexOf(ui.item[0] + ' ') > -1 ? 'checked' : 'unchecked');

				  					var selectedSite = ui.item[0] +" ";
				  					
				  					if (lineChartChecked == 'checked') {
					  					
				  						var lastSite = document.getElementById('siteNameLineChart').value;
				  						var uncheckedSite = lastSite.split(";");
				  						var uncheckedSite  = uncheckedSite[0];
				  					
				  			  			var uncheckedSiteName = ui.item[0]+" ";
				  	                   var newAllSitesArrLineChart = [];

				  	                 for(i=0;i<lineChartAllSitesArr.length;i++) {
				 						if (lineChartAllSitesArr[i] != selectedSite) {
				 							newAllSitesArrLineChart.push(lineChartAllSitesArr[i]);
				 						}
				 					}	

				  	               lineChartAllSitesArr = newAllSitesArrLineChart;
				  	               
				  	             //Remove all tags
				                   $("#lineChartSitesAutocomplete").tagsinput('removeAll');

									for(i=0;i<newAllSitesArrLineChart.length;i++) {
						                  var siteNameItem = newAllSitesArrLineChart[i].split(',') ;
						                // Add checked tags
						                $("#lineChartSitesAutocomplete").tagsinput('add', siteNameItem.toString());
						               				                    
					                    }
									if (ui.item[0] == selectedSite ) {
								           document.getElementById('siteNameLineChart').value = '';
									}
							        }
							 
							        else {  
							        	 var terms = split( $("#lineChartSitesAutocomplete").val() );
							 	        terms.pop();
							 	        terms.push( ui.item[0]);
							 	       lineChartSelectedItems.push(terms);
							 	        txt=terms;
							 	        terms.push( "" );
							 					          
				  					
							 	       siteNameLineChart.value = (ui.item ? ui.item[0] +";"+ ui.item[1] +" "  : '');
							 	      siteNameLineChart.value = this.value;

				  					var siteInput=siteNameLineChart.value;
				  				    var siteNameInp = siteInput.split(";");
				  				    siteNameInp  = siteNameInp[0]+" ";
				  				    
				  			       $("#lineChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
							        }
				  					
				  					return false;
				  					}
				  				    }).autocomplete("instance")._renderItem = function(ul, item) {
				  				    
						  		 		var text = $("#lineChartSitesAutocomplete").val();
									    text = text == null || text == undefined ? "" : text;
									  
				  			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
				  			    	item[0]="No site name";
				  			    	item[1]="null";
				  			    	item[2]="null";
				  			    	item[3]="null";
				  			    	
				  		           
				  				    }
				  			       var checked = (text.indexOf(item[0] + ' ') > -1 ? 'checked' : 'unchecked');
				  				    
				  			            return $("<li class='each'>")
				  		                .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[0]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
				  		        item[0] + "</span><br><span class='desc'>" +
				  		        item[1] + "</span><span class='desc'>" +";"+
				  		        item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
				  		        	};
				  		        	$("#siteNameLineChart").focus(function(){
					  		        	if (this.value == ""){
					  		            	$(this).autocomplete("search");
					  		        	}						
					  					});
			  		        	
	  		         
	  		            		$("#siteNamePieChart").focus(function(){
	  		                    	if (this.value == ""){
	  		                        	$(this).autocomplete("search");
	  		                    	}						
	  		            	});

			  		        	var pieChartSelectedItems=[];
						  		var pieChartText;
						  		var pieChartChecked;
						  		var pieChartAllSitesArr=[];
					  		 	 $("#siteNamePieChart").autocomplete({
					  				showHeader: true,
					  				
					  			// without this focus fct: keyboard movements reset the input to ''
					  			  focus: function( event, ui ) {
					  			        event.preventDefault(); 
					  			        if (event.Keycode ==38||event.Keycode ==40 ) {
					  			        	var terms = split($("#pieChartSitesAutocomplete").val());
					  					        terms.pop();
					  					        terms.push( ui.item[0]);
					  					      pieChartSelectedItems.push(terms);
					  					        txt=terms;
					  					        terms.push("");

					  			        var siteInput=siteNamePieChart.value;
					  				    var siteNameInp = siteInput.split(";");
					  				    siteNameInp  = siteNameInp[0]+" ";
					  				    
					  			       $("#pieChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
					  			    		
					  			     }
					  			  }, // end of focus fct
					  		  					  		        
					  		        source: function(request, response) {
					  		        //Extract last site name from input field
					  		          var searchText = extractLast(request.term);
					  		         
					  			             $.ajax({
					  			                 type: "GET",
					  			                 contentType: "application/json; charset=utf-8",
					  			                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
					  			                 data: {
					  			                	 "SiteId" : searchText,
					  									
					  							 },
					  			                 dataType: "json",
					  			                 success: function (data) {
					  			                     if (data != null) {	  				                     
					  			                         response(data.listSites);
					  			                       
					  			                     }
					  			                 },
					  			                 error: function(result) {
					  			                     alert("Error");
					  			                 }
					  			             });
					  			         }, minLength:0, maxShowItems: 100, scroll:true,		
					  		               
					  		        
					  				select: function(event, ui) {

					  					pieChartSelectedItems=[];
					  					pieChartText = $("#pieChartSitesAutocomplete").val();
					  					pieChartText = pieChartText == null || pieChartText == undefined ? "" : pieChartText;

					  				// AllSitesArr is equal to the internal array after removing a tag input
					  					$('#pieChartSitesAutocomplete').on('itemRemoved', function(event) {						 
					  						pieChartAllSitesArr = $("#pieChartSitesAutocomplete").data('tagsinput').itemsArray;
					  					}); 

					  					$('#pieChartSitesAutocomplete').on('itemAdded', function(event) {
					  						 
					  						pieChartAllSitesArr = $("#pieChartSitesAutocomplete").data('tagsinput').itemsArray;
					  					});


					  					pieChartChecked = (pieChartText.indexOf(ui.item[0] + ' ') > -1 ? 'checked' : 'unchecked');

					  					var selectedSite = ui.item[0] +" ";
					  					
					  					if (pieChartChecked == 'checked') {
						  					
					  						var lastSite = document.getElementById('siteNamePieChart').value;
					  						var uncheckedSite = lastSite.split(";");
					  						var uncheckedSite  = uncheckedSite[0];
					  					
					  			  			var uncheckedSiteName = ui.item[0]+" ";
					  	                   var newAllSitesArrPieChart = [];

					  	                 for(i=0;i<pieChartAllSitesArr.length;i++) {
					 						if (pieChartAllSitesArr[i] != selectedSite) {
					 							newAllSitesArrPieChart.push(pieChartAllSitesArr[i]);
					 						}
					 					}	

					  	               pieChartAllSitesArr = newAllSitesArrPieChart;
					  	               
					  	             //Remove all tags
					                   $("#pieChartSitesAutocomplete").tagsinput('removeAll');

										for(i=0;i<newAllSitesArrPieChart.length;i++) {
							                  var siteNameItem = newAllSitesArrPieChart[i].split(',') ;
							                // Add checked tags
							                $("#pieChartSitesAutocomplete").tagsinput('add', siteNameItem.toString());
							               				                    
						                    }
										if (ui.item[0] == selectedSite ) {
									           document.getElementById('siteNamePieChart').value = '';
										}
								        }
								 
								        else {  
								        	 var terms = split( $("#pieChartSitesAutocomplete").val() );
								 	        terms.pop();
								 	        terms.push( ui.item[0]);
								 	       pieChartSelectedItems.push(terms);
								 	        txt=terms;
								 	        terms.push( "" );
								 					          
					  					
								 	       siteNamePieChart.value = (ui.item ? ui.item[0] +";"+ ui.item[1] +" "  : '');
								 	      siteNamePieChart.value = this.value;

					  					var siteInput=siteNamePieChart.value;
					  				    var siteNameInp = siteInput.split(";");
					  				    siteNameInp  = siteNameInp[0]+" ";
					  				    
					  			       $("#pieChartSitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
								        }
					  					
					  					return false;
					  					}
					  				    }).autocomplete("instance")._renderItem = function(ul, item) {
					  				    
							  		 		var text = $("#pieChartSitesAutocomplete").val();
										    text = text == null || text == undefined ? "" : text;
										  
					  			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
					  			    	item[0]="No site name";
					  			    	item[1]="null";
					  			    	item[2]="null";
					  			    	item[3]="null";
					  			    	
					  		           
					  				    }
					  			       var checked = (text.indexOf(item[0] + ' ') > -1 ? 'checked' : 'unchecked');
					  				    
					  			            return $("<li class='each'>")
					  		                .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[0]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
					  		        item[0] + "</span><br><span class='desc'>" +
					  		        item[1] + "</span><span class='desc'>" +";"+
					  		        item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
					  		        	};
					  		        	$("#siteNamePieChart").focus(function(){
						  		        	if (this.value == ""){
						  		            	$(this).autocomplete("search");
						  		        	}						
						  					});
				  		        	
			  		         
			  		            		
	  		                    	
	  		                    	$("#siteNameStackChartMax").focus(function(){
	  			  		            	if (this.value == ""){
	  			  		                	$(this).autocomplete("search");
	  			  		            	}						
	  			  		    	});
	  			  		      $("#siteNameStackChartMax").autocomplete({
	  			  		    		showHeader: true,
	  			  		    		
	  			  		            
	  			  		            source: function(request, response) {

	  			  		             
	  			  		    	             $.ajax({
	  			  		    	                 type: "GET",
	  			  		    	                 contentType: "application/json; charset=utf-8",
	  			  		    	                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
	  			  		    	                 data: {
	  			  		    	                	 "SiteId" : $("#siteNameStackChartMax").val(),
	  			  		    							
	  			  		    					 },
	  			  		    	                 dataType: "json",
	  			  		    	                 success: function (data) {
	  			  		    	                     if (data != null) {

	  			  		    		                     
	  			  		    	                         response(data.listSites);
	  			  		    	                       
	  			  		    	                     }
	  			  		    	                 },
	  			  		    	                 error: function(result) {
	  			  		    	                     alert("Error");
	  			  		    	                 }
	  			  		    	             });
	  			  		    	         }, minLength:0, maxShowItems: 100, scroll:true,		
	  			  		                   
	  			  		            
	  			  		    		select: function(event, ui) {
	  			  		    			
	  			  		    			siteNameStackChartMax.value = (ui.item ? ui.item[0] +";"+ ui.item[1]  : '');
	  			  		    			
	  			  		    				return false;
	  			  		    					}
	  			  		    		    }).autocomplete("instance")._renderItem = function(ul, item) {

	  			  		    	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  			  		    	    	item[0]="No site name";
	  			  		    	    	item[1]="null";
	  			  		    	    	item[2]="null";
	  			  		    	    	item[3]="null";
	  			  		    	    	
	  			  		               
	  			  		    		    }
	  			  		    	            return $("<li class='each'>")
	  			  		                    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	  			  		            item[0] + "</span><br><span class='desc'>" +
	  			  		            item[1] + "</span><span class='desc'>" +";"+
	  			  		            item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
	  			  		            	};

	  			  		            $("#siteNameStackChartMin").focus(function(){
	  			  		            	if (this.value == ""){
	  			  		                	$(this).autocomplete("search");
	  			  		            	}						
	  			  		    	});
	  			  		      $("#siteNameStackChartMin").autocomplete({
	  			  		    		showHeader: true,
	  			  		    		
	  			  		            
	  			  		            source: function(request, response) {

	  			  		             
	  			  		    	             $.ajax({
	  			  		    	                 type: "GET",
	  			  		    	                 contentType: "application/json; charset=utf-8",
	  			  		    	                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
	  			  		    	                 data: {
	  			  		    	                	 "SiteId" : $("#siteNameStackChartMin").val(),
	  			  		    							
	  			  		    					 },
	  			  		    	                 dataType: "json",
	  			  		    	                 success: function (data) {
	  			  		    	                     if (data != null) {

	  			  		    		                     
	  			  		    	                         response(data.listSites);
	  			  		    	                       
	  			  		    	                     }
	  			  		    	                 },
	  			  		    	                 error: function(result) {
	  			  		    	                     alert("Error");
	  			  		    	                 }
	  			  		    	             });
	  			  		    	         }, minLength:0, maxShowItems: 100, scroll:true,		
	  			  		                   
	  			  		            
	  			  		    		select: function(event, ui) {
	  			  		    			
	  			  		    			siteNameStackChartMin.value = (ui.item ? ui.item[0] +";"+ ui.item[1]  : '');
	  			  		    			
	  			  		    				return false;
	  			  		    					}
	  			  		    		    }).autocomplete("instance")._renderItem = function(ul, item) {

	  			  		    	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  			  		    	    	item[0]="No site name";
	  			  		    	    	item[1]="null";
	  			  		    	    	item[2]="null";
	  			  		    	    	item[3]="null";
	  			  		    	    	
	  			  		               
	  			  		    		    }
	  			  		    	            return $("<li class='each'>")
	  			  		                    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	  			  		            item[0] + "</span><br><span class='desc'>" +
	  			  		            item[1] + "</span><span class='desc'>" +";"+
	  			  		            item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
	  			  		            	};
	  			  		            	
	  			  		            	$("#siteNameLineChartMax").focus(function(){
	  			  		                	if (this.value == ""){
	  			  		                    	$(this).autocomplete("search");
	  			  		                	}						
	  			  		        	});
	  				  		        	
	  			  		          $("#siteNameLineChartMax").autocomplete({
	  			  		        		showHeader: true,
	  			  		        		
	  			  		                
	  			  		                source: function(request, response) {

	  			  		                 
	  			  		        	             $.ajax({
	  			  		        	                 type: "GET",
	  			  		        	                 contentType: "application/json; charset=utf-8",
	  			  		        	                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
	  			  		        	                 data: {
	  			  		        	                	 "SiteId" : $("#siteNameLineChartMax").val(),
	  			  		        							
	  			  		        					 },
	  			  		        	                 dataType: "json",
	  			  		        	                 success: function (data) {
	  			  		        	                     if (data != null) {

	  			  		        		                     
	  			  		        	                         response(data.listSites);
	  			  		        	                       
	  			  		        	                     }
	  			  		        	                 },
	  			  		        	                 error: function(result) {
	  			  		        	                     alert("Error");
	  			  		        	                 }
	  			  		        	             });
	  			  		        	         }, minLength:0, maxShowItems: 100, scroll:true,		
	  			  		                       
	  			  		                
	  			  		        		select: function(event, ui) {
	  			  		        			
	  			  		        			siteNameLineChartMax.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
	  			  		        			
	  			  		        				return false;
	  			  		        					}
	  			  		        		    }).autocomplete("instance")._renderItem = function(ul, item) {

	  			  		        	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  			  		        	    	item[0]="No site name";
	  			  		        	    	item[1]="null";
	  			  		        	    	item[2]="null";
	  			  		        	    	item[3]="null";
	  			  		        	    	
	  			  		                   
	  			  		        		    }
	  			  		        	            return $("<li class='each'>")
	  			  		                        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	  			  		                item[0] + "</span><br><span class='desc'>" +
	  			  		                item[1] + "</span><span class='desc'>" +";"+
	  			  		                item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
	  			  		                	};	

	  			  		                $("#siteNameLineChartMin").focus(function(){
	  			  		                	if (this.value == ""){
	  			  		                    	$(this).autocomplete("search");
	  			  		                	}						
	  			  		        	});
	  				  		        	
	  			  		          $("#siteNameLineChartMin").autocomplete({
	  			  		        		showHeader: true,
	  			  		        		
	  			  		                
	  			  		                source: function(request, response) {

	  			  		                 
	  			  		        	             $.ajax({
	  			  		        	                 type: "GET",
	  			  		        	                 contentType: "application/json; charset=utf-8",
	  			  		        	                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
	  			  		        	                 data: {
	  			  		        	                	 "SiteId" : $("#siteNameLineChartMin").val(),
	  			  		        							
	  			  		        					 },
	  			  		        	                 dataType: "json",
	  			  		        	                 success: function (data) {
	  			  		        	                     if (data != null) {

	  			  		        		                     
	  			  		        	                         response(data.listSites);
	  			  		        	                       
	  			  		        	                     }
	  			  		        	                 },
	  			  		        	                 error: function(result) {
	  			  		        	                     alert("Error");
	  			  		        	                 }
	  			  		        	             });
	  			  		        	         }, minLength:0, maxShowItems: 100, scroll:true,		
	  			  		                       
	  			  		                
	  			  		        		select: function(event, ui) {
	  			  		        			
	  			  		        			siteNameLineChartMin.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
	  			  		        			
	  			  		        				return false;
	  			  		        					}
	  			  		        		    }).autocomplete("instance")._renderItem = function(ul, item) {

	  			  		        	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  			  		        	    	item[0]="No site name";
	  			  		        	    	item[1]="null";
	  			  		        	    	item[2]="null";
	  			  		        	    	item[3]="null";
	  			  		        	    	
	  			  		                   
	  			  		        		    }
	  			  		        	            return $("<li class='each'>")
	  			  		                        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	  			  		                item[0] + "</span><br><span class='desc'>" +
	  			  		                item[1] + "</span><span class='desc'>" +";"+
	  			  		                item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
	  			  		                	};	


		
// collapse active class	
$('.panel-collapse').on('show.bs.collapse', function () {
        
	    $(this).siblings('.panel-heading').removeClass('active');
	  });

	  $('.panel-collapse').on('hide.bs.collapse', function () {
	    $(this).siblings('.panel-heading').addClass('active');
	  });


	        
	// site autocomplete with multiselect			
	  
		 $("#site").autocomplete({
				showHeader: true,
				
				// without this focus fct: keyboard movements reset the input to ''
				  focus: function( event, ui ) {
				        event.preventDefault(); 
				        if (event.Keycode ==38||event.Keycode ==40 ) {
				        	var terms = split($("#sitesAutocomplete").val());
						        terms.pop();
						        terms.push( ui.item[0]);
						        selectedItems.push(terms);
						        txt=terms;
						        terms.push("");

				        //$("#sitesAutocomplete").val(terms.join( "," ));	
				        var siteInput=site.value;
					    var siteNameInp = siteInput.split(";");
					    siteNameInp  = siteNameInp[0]+" ";
					    
				       $("#sitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
				    		
				     }
				  }, // end of focus fct
	
		        source: function(request, response) {
			        //Extract last site name from input field
		          var searchText = extractLast(request.term);
	               
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllSiteAutocomplete',
			                 data: {
				                 
			                	 "SiteId" : searchText,
			                	 "Area" :  $("#Area").val(),	
			                	 "Region" : $("#Region").val(),	
									
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {

				                     
			                      response(data.listSites);
			                        
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 100, scroll:true,		
		               
		        
				select: function(event, ui) {
			         
					selectedItems=[];
				    text = $("#sitesAutocomplete").val();
					text = text == null || text == undefined ? "" : text;

					// AllSitesArr is equal to the internal array after removing a tag input
 					$('#sitesAutocomplete').on('itemRemoved', function(event) {						 
 						AllSitesArr = $("#sitesAutocomplete").data('tagsinput').itemsArray;
 					}); 
 					   
 					$('#sitesAutocomplete').on('itemAdded', function(event) {
 						 
 						AllSitesArr = $("#sitesAutocomplete").data('tagsinput').itemsArray;
 					}); 
					
				    checked = (text.indexOf(ui.item[0] + ' ') > -1 ? 'checked' : 'unchecked');

					var selectedSite = ui.item[0] +" ";
				    
					

					if (checked == 'checked') {
						
						var lastSite = document.getElementById('site').value;
						var uncheckedSite = lastSite.split(";");
						var uncheckedSite  = uncheckedSite[0];
					
			  				var uncheckedSiteName = ui.item[0]+" ";

		                    var newAllSitesArr = [];
							for(i=0;i<AllSitesArr.length;i++) {
								if (AllSitesArr[i] != selectedSite) {
									newAllSitesArr.push(AllSitesArr[i]);
								}
							}
							
							AllSitesArr = newAllSitesArr;

							  //Remove all tags
		                    $("#sitesAutocomplete").tagsinput('removeAll');

							for(i=0;i<newAllSitesArr.length;i++) {
				                  var siteNameItem = newAllSitesArr[i].split(',') ;
				                // Add checked tags
				                $("#sitesAutocomplete").tagsinput('add', siteNameItem.toString());
				               				                    
			                    }
			                
	                    

						
						if (ui.item[0] == selectedSite ) {
					           document.getElementById('site').value = '';
						}
				        }
				 
				        else { 
						     
			        var terms = split( $("#sitesAutocomplete").val() );
			        terms.pop();
			        terms.push( ui.item[0]);
			        selectedItems.push(terms);
			        txt=terms;
			        terms.push( "" );


				    site.value = (ui.item ? ui.item[0] +";"+ ui.item[1] +" "  : '');
					site.value = this.value;
					  		      
					var siteInput=site.value;
				    var siteNameInp = siteInput.split(";");
				    siteNameInp  = siteNameInp[0]+" ";
				    
			       $("#sitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
			    		

				       }
										
						return false;
							
				}
			         
				
				    }).autocomplete("instance")._renderItem = function(ul, item) {
			    
					 var text = $("#sitesAutocomplete").val();
					    text = text == null || text == undefined ? "" : text;
					    
					        
			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
			    	item[0]="No site name";
			    	item[1]="null";
			    	item[2]="null";
			    	item[3]="null";
			    	
		          
				    }
			      
			       var checked = (text.indexOf(item[0] + ' ') > -1 ? 'checked' : 'unchecked');
				      
			            return $("<li class='each'>")
		             .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[0]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
		       item[0] + "</span><br><span class='desc'>" +
		       item[1] + "</span><span class='desc'>" +";"+
		       item[2] + ";"+ item[3] +"</span></div>") .appendTo(ul);
			              
			              
		     	};

				$("#site").focus(function(){
					
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}	
				        					
					});
				
				function split(val) {
					   return val.split( / \s*/ );
					}
					function extractLast(term) {
					   return split(term).pop();
					}
	

				$("#Area").autocomplete({
					showHeader: true,
					
			        
			        source: function(request, response) {

		               
				             $.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url: '${pageContext.request.contextPath}/GetAllAreasRPT',
				                 data: {
									"areaID":$("#Area").val(),
										
										
								 },
				                 dataType: "json",
				                 success: function (data) {
				                     if (data != null) {

					                     
				                         response(data.listAreas);
				                       
				                     }
				                 },
				                 error: function(result) {
				                     alert("Error");
				                 }
				             });
				         }, minLength:0, maxShowItems: 40, scroll:true,		
			               
			        
					select: function(event, ui) {
						
						Area.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
						
							return false;
								}
					    }).autocomplete("instance")._renderItem = function(ul, item) {
				            return $("<li class='each'>")
			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
			                        item[0] + "</span><br><span class='desc'>" +
			                        item[1]  +"</span></div>"
		              ) .appendTo(ul);
			        	};
						$("#Area").focus(function(){
			   	        	if (this.value == ""){
			   	            	$(this).autocomplete("search");
			   	        	}						
						});

						$("#Region").autocomplete({
							showHeader: true,
							
					        
					        source: function(request, response) {

				               
						             $.ajax({
						                 type: "GET",
						                 contentType: "application/json; charset=utf-8",
						                 url: '${pageContext.request.contextPath}/GetAllRegionsRPT',
						                 data: {
											Region :$("#Region").val(),
												
												
										 },
						                 dataType: "json",
						                 success: function (data) {
						                     if (data != null) {

							                     
						                         response(data.listRegions);
						                       
						                     }
						                 },
						                 error: function(result) {
						                     alert("Error");
						                 }
						             });
						         }, minLength:0, maxShowItems: 40, scroll:true,		
					               
					        
							select: function(event, ui) {
								
								Region.value = (ui.item ? ui.item[0] +";"+ ui.item[1]  +";"+ ui.item[2]   : '');
								
									return false;
										}
							    }).autocomplete("instance")._renderItem = function(ul, item) {
						            return $("<li class='each'>")
					                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					                        item[0] + "</span><br><span class='desc'>" +
					                        item[1] + "</span><span class='desc'>" +";"+
					                        item[2]  +"</span></div>") .appendTo(ul);
					        	};
								$("#Region").focus(function(){
					   	        	if (this.value == ""){
					   	            	$(this).autocomplete("search");
					   	        	}						
								});


								
                               // clear filter of the chart
						

								$('#clearFilterMSChart').click(function(){
								      //msColumnChartObj; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
		 				 			 $("#startdateMSChart").val(s).trigger('change');
						 			 $("#enddateMSChart").val(enddate).trigger('change');
						 			 $("#changeOptionsMSChart").val('null').trigger('change');
						 			 $('#siteNameMSChart').val('').trigger('change');
						 			//document.querySelector('#technology').reset();
						 			//$("#msChartTechnologies").val('').trigger('change');
									document.querySelector('#msChartTechnologies').toggleSelectAll(false);							 					
						 			$("#minValMSFltr").val('');
						 			$("#maxValMSFltr").val('');
						 			$("#revenueOptionsMSChart").val('voice_revenue').trigger('change');

						 			//Remove all tags
							         $("#msChartSitesAutocomplete").tagsinput('removeAll');			
							 		// AllSitesArr is equal to the internal array after removing a tag input
					  				$('#msChartSitesAutocomplete').on('itemRemoved', function(event) {						 
					  					msChartAllSitesArr = $("#msChartSitesAutocomplete").data('tagsinput').itemsArray;
					  				});
							 			
						 			$("#filterDivMSChart").hide();						 			
		                			//SiteRevenue(msColumnChartObj);
						 			SiteRevenue("");

									
									});
								$('#clearFilterStackChart').click(function(){
									StackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			 $("#startdateStackChart").val(s).trigger('change');
						 			 $("#enddateStackChart").val(enddate).trigger('change');
						 			$("#changeOptionsStackChart").val('null').trigger('change');
						 			$('#siteNameStackChart').val('').trigger('change');
						 			//$("#technologiesStackChart").val('null').trigger('change');
						 			document.querySelector('#stackChartTechnologies').toggleSelectAll(false);							 					
						 			$("#minValStackedFltr").val('');
						 			$("#maxValStackedFltr").val('');
						 			$("#revenueOptionsStackChart").val('voice_revenue').trigger('change');

						 			$("#stackChartSitesAutocomplete").tagsinput('removeAll');			
					  				// AllSitesArr is equal to the internal array after removing a tag input
					  				$('#stackChartSitesAutocomplete').on('itemRemoved', function(event) {						 
					  					stackChartAllSitesArr = $("#stackChartSitesAutocomplete").data('tagsinput').itemsArray;
					  				}); 
					  				
						 			$("#filterDivStackChart").hide();
						 			
						 			fetchRevenuePerTech(StackedandLineRevenue, $("#startdate").val(), $("#enddate").val());

									
									});
								$('#clearFilterLineChart').click(function(){
									StackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			 $("#startdateLineChart").val(s).trigger('change');
						 			 $("#enddateLineChart").val(enddate).trigger('change');
						 			$("#changeOptionsLineChart").val('null').trigger('change');
						 			$('#siteNameLineChart').val('').trigger('change');
						 			// $("#technologiesLineChart").val('null').trigger('change');
						 			document.querySelector('#lineChartTechnologies').toggleSelectAll(false);							 					
						 			$("#minValLineFltr").val('');
						 			$("#maxValLineFltr").val('');
						 			$("#revenueOptionsLineChart").val('voice_revenue').trigger('change');

						 			$("#lineChartSitesAutocomplete").tagsinput('removeAll');			
					  				// AllSitesArr is equal to the internal array after removing a tag input
					  				$('#lineChartSitesAutocomplete').on('itemRemoved', function(event) {						 
					  					lineChartAllSitesArr = $("#lineChartSitesAutocomplete").data('tagsinput').itemsArray;
					  				});
					  				
						 			$("#filterDivLineChart").hide();
						 			
						 			fetchLineChartRevenue(StackedandLineRevenue);

									
									});
								$('#clearFilterPieChart').click(function(){
									//chartData; 
								      //listSitesClrFltr;
								      //msColumnChartObj;
								      //pieRevenue;

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			 $("#startdatePieChart").val(s).trigger('change');
						 			 $("#enddatePieChart").val(enddate).trigger('change');
											
						 			 $("#changeOptionsPieChart").val('null').trigger('change');
						 			 $('#siteNamePieChart').val('').trigger('change');
						 			// $("#technologiesPieChart").val('null').trigger('change');
						 			document.querySelector('#pieChartTechnologies').toggleSelectAll(false);							 					
						 			
						 			$("#minValPiesFltr").val('');
						 			$("#maxValPiesFltr").val('');
						 			$("#revenueOptionsPieChart").val('voice_revenue').trigger('change');

						 			$("#pieChartSitesAutocomplete").tagsinput('removeAll');			
					  				// AllSitesArr is equal to the internal array after removing a tag input
					  				$('#pieChartSitesAutocomplete').on('itemRemoved', function(event) {						 
					  					pieChartAllSitesArr = $("#pieChartSitesAutocomplete").data('tagsinput').itemsArray;
					  				});

					  				
						 			$("#filterDivPieChart").hide();
						 			//123
						 			//PieRevenue(pieRevenue);
						 			//PieTech(listSitesClrFltr);
					  		 		//PieRevPerTech(listSitesClrFltr);
					  		 		PieRevenue("");
						 			PieTech("");
					  		 		PieRevPerTech("");

									
									});
                                // max stack chart
								$('#clearFilterStackChartMax').click(function(){
									//MaxStackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			$("#startdateStackChartMax").val(s).trigger('change');
						 			$("#enddateStackChartMax").val(enddate).trigger('change');
						 			$("#changeOptionsStackChartMax").val('null').trigger('change');
						 			//if($("#site").val() == ""){}
							 		$('#siteNameStackChartMax').val('').trigger('change');
						 			//$("#technologiesStackChartMax").val('null').trigger('change');
						 			document.querySelector('#maxStackChartTechnologies').toggleSelectAll(false);							 					
						 			
						 			$("#minValStackedFltrMax").val('');
						 			$("#maxValStackedFltrMax").val('');
						 			$("#revenueOptionsStackChartMax").val('voice_revenue').trigger('change');
						 			$("#filterDivStackChartMax").hide();
						 			
						 			fetchRevenuePerTechMaxMin(" ", $("#startdate").val(), $("#enddate").val(),"Max");

									
									});
								// for min stack chart
								$('#clearFilterStackChartMin').click(function(){
									//MinStackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			$("#startdateStackChartMin").val(s).trigger('change');
						 			$("#enddateStackChartMin").val(enddate).trigger('change');
						 			$("#changeOptionsStackChartMin").val('null').trigger('change');
						 			//if($("#site").val() == ""){}
							 	    $('#siteNameStackChartMin').val('').trigger('change');
						 			//$("#technologiesStackChartMin").val('null').trigger('change');
						 		document.querySelector('#minStackChartTechnologies').toggleSelectAll(false);							 					
						 			
						 			$("#minValStackedFltrMin").val('');
						 			$("#maxValStackedFltrMin").val('');
						 			$("#revenueOptionsStackChartMin").val('voice_revenue').trigger('change');
						 			$("#filterDivStackChartMin").hide();
						 			
						 			fetchRevenuePerTechMaxMin(" ", $("#startdate").val(), $("#enddate").val(), "Min");

									
									});
								// max line stack chart
								$('#clearFilterLineChartMax').click(function(){
									//MaxStackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			$("#startdateLineChartMax").val(s).trigger('change');
						 			$("#enddateLineChartMax").val(enddate).trigger('change');
						 			$("#changeOptionsLineChartMax").val('null').trigger('change');
						 			//if($("#site").val() == ""){}
							 		$('#siteNameLineChartMax').val('').trigger('change');
						 			//$("#technologiesLineChartMax").val('null').trigger('change');
						 			document.querySelector('#maxLineChartTechnologies').toggleSelectAll(false);							 					
						 			
						 			$("#minValLineFltrMax").val('');
						 			$("#maxValLineFltrMax").val('');
						 			$("#revenueOptionsLineChartMax").val('voice_revenue').trigger('change');
						 			$("#filterDivLineChartMax").hide();
						 			
						 			fetchLineChartRevenueMaxMin(" ","Max");

									
									});

								// min line stack chart
								$('#clearFilterLineChartMin').click(function(){
									//MinStackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			$("#startdateLineChartMin").val(s).trigger('change');
						 			$("#enddateLineChartMin").val(enddate).trigger('change');
						 			$("#changeOptionsLineChartMin").val('null').trigger('change');
						 			//if($("#site").val() == ""){}
							 		$('#siteNameLineChartMin').val('').trigger('change');
						 			//$("#technologiesLineChartMin").val('null').trigger('change');
						 			document.querySelector('#minLineChartTechnologies').toggleSelectAll(false);							 					
						 			
						 			$("#minValLineFltrMin").val('');
						 			$("#maxValLineFltrMin").val('');
						 			$("#revenueOptionsLineChartMin").val('voice_revenue').trigger('change');
						 			$("#filterDivLineChartMin").hide();
						 			
						 			fetchLineChartRevenueMaxMin(" ","Min");

									
									});

								function retStringFrmArray(tech){
									if(tech == "null") return "null";
									else{
									if(tech.length == 2){
										if((tech[0]=="2g" && tech[1]=="2g3g") || (tech[0]=="2g3g" && tech[1]=="2g")){ return "2g&2g3g";}
										else if((tech[0]=="2g" && tech[1]=="2g3g4g") || (tech[0]=="2g3g4g" && tech[1]=="2g")){ return "2g&2g3g4g";}
										else if((tech[0]=="2g3g" && tech[1]=="2g3g4g") || (tech[0]=="2g3g4g" && tech[1]=="2g3g")){ return "2g3g&2g3g4g";}
											} 
									else if(tech.length == 3){
										return "2g&2g3g&2g3g4g"
										}
									else {
										return tech[0];
										}
									}
									
									}

								
								$('#applyButtMSChart, #applyButtStackChart, #applyButtLineChart, #applyButtPieChart, #applyButtStackChartMax, #applyButtStackChartMin, #applyButtLineChartMax, #applyButtLineChartMin').click(function(){
									var stDate,endDate;
									var siteName;
									var selectBox;
									var selectedValue;
									var selectRevBox ;
									var selectedRevValue;
									 var technologies1,tech;
									 var selectTechBox;
									 var val1,val2;
									 var id = this.id;
									 var checkBox1 = document.getElementById("Max");
									 var checkBox2 = document.getElementById("Min");
									 var siteSelectedTech ;
									 
								      var nullSiteCheckbox = document.getElementById("noSiteName");
								      if (nullSiteCheckbox.checked == true){
						            	  var nullSiteChecked = "nullSite";
						    	         
						               }
								     
								   var maxChecked,minChecked;
										 /// Maximum
							               if (checkBox1.checked == true){
							            	   maxChecked= "Max";
							     	          console.log("Checkbox5 is checked." );
							     	         
							                }
							               
							               /// Minimum
							               if (checkBox2.checked == true){
							             	  minChecked = "Min";
							     	          console.log("Checkbox6 is checked." );
							     	         
							                }
							              
									 if (id == 'applyButtMSChart') {
										 stDate =  $("#startdateMSChart").val();
										 endDate = $("#enddateMSChart").val();
										 siteName = ($('#siteNameMSChart').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsMSChart");
											 selectedValue = selectBox.options[selectBox.selectedIndex].value;
											 selectRevBox = document.getElementById("revenueOptionsMSChart");

											// selectTechBox = document.getElementById("technologiesMSChart");
											// technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
											  selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											// tech = ($('#technology').val().length == 0)? "null" :$('#technology').val() ;
											// technologies1 = retStringFrmArray(tech);
											 val1 =$('#minValMSFltr').val();
											 val2 =$('#maxValMSFltr').val();

									    //Convert the elements of AllSitesArr that contains the sites name in popup into a string
										var allSitesNameChart = msChartAllSitesArr.join();


										 var selectedTech = $("#msChartTechnologies").val();
										  var arrSelected = [];
										   for (let i = 0; i < selectedTech.length; i++) {
											 arrSelected.push(selectedTech[i]);
											}
										    siteSelectedTech = arrSelected.toString();
								      
												 
											 
									    }
									 else if(id == 'applyButtStackChart'){
										 stDate =  $("#startdateStackChart").val();
										// console.log("st date is "+stDate);
										 endDate = $("#enddateStackChart").val();
										// console.log("end date is "+endDate);
										 siteName = ($('#siteNameStackChart').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsStackChart");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revenueOptionsStackChart");
										 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
										// selectTechBox = document.getElementById("technologiesStackChart");
										 //technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
										 val1 =$('#minValStackedFltr').val();
										 val2 = $('#maxValStackedFltr').val();

										 //Convert the elements of AllSitesArr that contains the sites name in popup into a string
										 var allSitesNameChart = stackChartAllSitesArr.join();

										 var selectedTech = $("#stackChartTechnologies").val();
										  var arrSelected = [];
										   for (let i = 0; i < selectedTech.length; i++) {
											 arrSelected.push(selectedTech[i]);
											}
										    siteSelectedTech = arrSelected.toString();
								      
											 }
									 else if(id == 'applyButtLineChart'){
										 stDate =  $("#startdateLineChart").val();
										 endDate = $("#enddateLineChart").val();
										 siteName = ($('#siteNameLineChart').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsLineChart");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revenueOptionsLineChart");
											 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											 //selectTechBox = document.getElementById("technologiesLineChart");
											// technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
											 val1 =$('#minValLineFltr').val();
											 val2 = $('#maxValLineFltr').val();
											 
											  //Convert the elements of AllSitesArr that contains the sites name in popup into a string
											 var allSitesNameChart = lineChartAllSitesArr.join();

											 var selectedTech = $("#lineChartTechnologies").val();
											  var arrSelected = [];
											   for (let i = 0; i < selectedTech.length; i++) {
												 arrSelected.push(selectedTech[i]);
												}
											    siteSelectedTech = arrSelected.toString();
									       
											 }
									 else if(id == 'applyButtPieChart'){
										 stDate =  $("#startdatePieChart").val();
										 endDate = $("#enddatePieChart").val();
										 siteName = ($('#siteNamePieChart').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsPieChart");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revenueOptionsPieChart");
											 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											// selectTechBox = document.getElementById("technologiesPieChart");
											// technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
											 val1 =$('#minValPiesFltr').val();
											 val2 = $('#maxValPiesFltr').val();
											 
											 //Convert the elements of AllSitesArr that contains the sites name in popup into a string
											 var allSitesNameChart = pieChartAllSitesArr.join();
											 var selectedTech = $("#pieChartTechnologies").val();
											  var arrSelected = [];
											   for (let i = 0; i < selectedTech.length; i++) {
												 arrSelected.push(selectedTech[i]);
												}
											    siteSelectedTech = arrSelected.toString();
									       
										
											 if(selectedValue == "null" || (selectedValue == "isBetween" && val1 == '' && val2 == '')){
													$.ajax({
											  		 	  type: "GET",
											  		 	  contentType: "application/json; charset=utf-8",
											  		 	  url: '${pageContext.request.contextPath}/GetSiteRevCharts',
											  		 	  data: {
											  				 	"siteName" : siteName,	
											  				 	//"area": document.getElementById("Area").value,
											  					"startDate" : stDate,
											  					"endDate" : endDate,
											  				  	"technologies" :technologies1, 
											  				  	//"period":$("#period").children("option:selected").val(),
											  				    "Max": maxChecked,
																"Min": minChecked,
																"allSitesNameChart":allSitesNameChart,
											  				 	"nullSite":nullSiteChecked,
											  				 	"selectedTech":siteSelectedTech,
																
											  		 				 },

											  		 	dataType: "json",
											  		 	success: function (data) {
											  		 		//listChartSites=data.listChartSites;
											  				

											  		 		if (data != null) {
											  		 			var chartData = data.chartData;
											  		 			PieTech(chartData);
												  		 		PieRevPerTech(chartData);
											  		 		}
											  		 			
											  		 		
											  		 		 },
											  		 		 error: function(result) {
											  		 			 alert("Error");
											  		 			 }
											  		 		 });
									 }
													
											 }

									 else if(id == 'applyButtStackChartMax'){
										 stDate =  $("#startdateStackChartMax").val();
										 endDate = $("#enddateStackChartMax").val();
										 siteName = ($('#siteNameStackChartMax').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsStackChartMax");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revenueOptionsStackChartMax");
										 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
										 //selectTechBox = document.getElementById("technologiesStackChartMax");
										// technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
										 val1 =$('#minValStackedFltrMax').val();
										 val2 = $('#maxValStackedFltrMax').val();

										 var selectedTech = $("#maxStackChartTechnologies").val();
										  var arrSelected = [];
										   for (let i = 0; i < selectedTech.length; i++) {
											 arrSelected.push(selectedTech[i]);
											}
										    siteSelectedTech = arrSelected.toString();
											 }
									 else if(id == 'applyButtStackChartMin'){
										 stDate =  $("#startdateStackChartMin").val();
										 endDate = $("#enddateStackChartMin").val();
										 siteName = ($('#siteNameStackChartMin').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsStackChartMin");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revenueOptionsStackChartMin");
										 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
										// selectTechBox = document.getElementById("technologiesStackChartMin");
										 //technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
										 val1 =$('#minValStackedFltrMin').val();
										 val2 = $('#maxValStackedFltrMin').val();

										 var selectedTech = $("#minStackChartTechnologies").val();
										  var arrSelected = [];
										   for (let i = 0; i < selectedTech.length; i++) {
											 arrSelected.push(selectedTech[i]);
											}
										    siteSelectedTech = arrSelected.toString();
											 }
									 else if(id == 'applyButtLineChartMax'){
										 stDate =  $("#startdateLineChartMax").val();
										 endDate = $("#enddateLineChartMax").val();
										 siteName = ($('#siteNameLineChartMax').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsLineChartMax");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revenueOptionsLineChartMax");
											 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											// selectTechBox = document.getElementById("technologiesLineChartMax");
											// technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
											 val1 =$('#minValLineFltrMax').val();
											 val2 = $('#maxValLineFltrMax').val();

											 var selectedTech = $("#maxLineChartTechnologies").val();
											  var arrSelected = [];
											   for (let i = 0; i < selectedTech.length; i++) {
												 arrSelected.push(selectedTech[i]);
												}
											    siteSelectedTech = arrSelected.toString();
									       
											 }

									 else if(id == 'applyButtLineChartMin'){
										 stDate =  $("#startdateLineChartMin").val();
										 endDate = $("#enddateLineChartMin").val();
										 siteName = ($('#siteNameLineChartMin').val().split(";"))[0];
										  selectBox = document.getElementById("changeOptionsLineChartMin");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revenueOptionsLineChartMin");
											 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											// selectTechBox = document.getElementById("technologiesLineChartMin");
										//	 technologies1 = selectTechBox.options[selectTechBox.selectedIndex].value;
											 val1 =$('#minValLineFltrMin').val();
											 val2 = $('#maxValLineFltrMin').val();

											 var selectedTech = $("#minLineChartTechnologies").val();
											  var arrSelected = [];
											   for (let i = 0; i < selectedTech.length; i++) {
												 arrSelected.push(selectedTech[i]);
												}
											    siteSelectedTech = arrSelected.toString();
									       
											 }
								
										
						                if(selectedValue == "null" || (selectedValue == "isBetween" && val1 == '' && val2 == '')){
						    		$.ajax({
										type : "GET",
										contentType: "application/json; charset=utf-8",
										url : "${pageContext.request.contextPath}/FilteringMethod",
										dataType : "json",
										data : {
										    "startDate" :stDate,
										    "endDate" : endDate,
										    "site": siteName,
						                    //"area": document.getElementById("Area").value,
						                    //"technologyRegions": document.getElementById("Region").value,
						                    "period": $("#period").children("option:selected").val(),
								            "Payment Method" : $("#Payment Method").children("option:selected").val(),
										    "technologies" :technologies1,
											"Max": maxChecked,
											"Min": minChecked,
						  					"filterID" : id, 
						  					"allSitesNameChart":allSitesNameChart,	
						  				 	"nullSite":nullSiteChecked,
						  				 	"selectedTech":siteSelectedTech,
						  					
											

										},
										success : function(data) {
											
											 if (data != null) {
												
												
												 
							                        	// console.log("*******Enter ");
							                        	 
							                        	 if (id == 'applyButtMSChart') {
							                        		 msColumnChartObj2=jQuery.parseJSON(data.msColumnChartObj);
									                		SiteRevenue(msColumnChartObj2);}
									                         else if(id == 'applyButtStackChart'){
									                        	 stDate =  $("#startdateStackChart").val();
									                             endDate = $("#enddateStackChart").val();
																 StackedandLineRevenue2 = jQuery.parseJSON(data.StackedandLineRevenue);
									                        	 fetchRevenuePerTech(StackedandLineRevenue2, stDate, endDate);}
									                         else if(id == 'applyButtLineChart'){
																 StackedandLineRevenue2 = jQuery.parseJSON(data.StackedandLineRevenue);   
									                        	 fetchLineChartRevenue(StackedandLineRevenue2);
									                        	 }
									                         else if(id == 'applyButtPieChart'){
									                        	 pieRevenue2=jQuery.parseJSON(data.pieRevenue);
									                        	 PieRevenue(pieRevenue2);
									                        	 }
									                         else if(id == 'applyButtStackChartMax'){
									                        	 stDate =  $("#startdateStackChartMax").val();
									                             endDate = $("#enddateStackChartMax").val();
																 StackedandLineRevenue2 = jQuery.parseJSON(data.MaxStackedandLineRevenue);
																 fetchRevenuePerTechMaxMin(StackedandLineRevenue2, stDate, endDate,"Max");}
									                         else if(id == 'applyButtStackChartMin'){
									                        	 stDate =  $("#startdateStackChartMin").val();
									                             endDate = $("#enddateStackChartMin").val();
																 StackedandLineRevenue2 = jQuery.parseJSON(data.MinStackedandLineRevenue);
																 fetchRevenuePerTechMaxMin(StackedandLineRevenue2, stDate, endDate,"Min");}
									                         else if(id == 'applyButtLineChartMax'){
																 StackedandLineRevenue2 = jQuery.parseJSON(data.MaxStackedandLineRevenue);   
																 fetchLineChartRevenueMaxMin(StackedandLineRevenue2,"Max");
									                        	 }
									                         else if(id == 'applyButtLineChartMin'){
																 StackedandLineRevenue2 = jQuery.parseJSON(data.MinStackedandLineRevenue);   
																 fetchLineChartRevenueMaxMin(StackedandLineRevenue2,"Min");
									                        	 }
											 }
							     					else{
							     						  alert("There is no data to display between these two selected dates for this site, or please check all the conditions selected in the upper side");
							     						   
							     					}
							                         
								                			
								     					 

										

							  },
							  
							  error : function(error) {
									console.log("The error is " + error);
								}
						     });
								}
						                else if(selectedValue == "isBetween" && (val1 != '' || val2 != '')){

						                	$.ajax({
												type : "GET",
												contentType: "application/json; charset=utf-8",
												url : "${pageContext.request.contextPath}/FilteringMethod",
												dataType : "json",
												data : {
												    "startDate" : stDate,
												    "endDate" : endDate,
												    "site": siteName,
								                    //"area": document.getElementById("Area").value,
								                    //"technologyRegions": document.getElementById("Region").value,
								                    "period": $("#period").children("option:selected").val(),
										            "Payment Method" : $("#Payment Method").children("option:selected").val(),
												    "technologies" :technologies1,
												    "selectedValue" :selectedValue,
												    "revenueOption" : selectedRevValue,
												    "minVal":(val1 == "")? 'null' : val1 ,
												    "maxVal":(val2 == "")? 'null' : val2,
													"Max": maxChecked,
													"Min": minChecked,
								  					"filterID" : id, 	
								  					"allSitesNameChart":allSitesNameChart,	
								  				 	"selectedTech":siteSelectedTech,
								  					
								  						
													

												},
												success : function(data) {
													
													 if (data != null) {
														   //    	 console.log("*******Data Enter ");
									                        	 if (id == 'applyButtMSChart') {
									                        		 msColumnChartObj2=jQuery.parseJSON(data.msColumnChartObj);
											                			SiteRevenue(msColumnChartObj2);}
											                         else if(id == 'applyButtStackChart'){
											                        	 stDate =  $("#startdateStackChart").val();
											                             endDate = $("#enddateStackChart").val();
											                             StackedandLineRevenue2=jQuery.parseJSON(data.StackedandLineRevenue);
											                        	 fetchRevenuePerTech(StackedandLineRevenue2, stDate, endDate);}
											                         else if(id == 'applyButtLineChart'){
											                        	 StackedandLineRevenue2=jQuery.parseJSON(data.StackedandLineRevenue);
											                        	 fetchLineChartRevenue(StackedandLineRevenue2);}
											                         else if(id == 'applyButtPieChart'){
											                        	 pieRevenue2=jQuery.parseJSON(data.pieRevenue);
											                        	 PieRevenue(pieRevenue2);}
											                         else if(id == 'applyButtStackChartMax'){
											                        	 stDate =  $("#startdateStackChartMax").val();
											                             endDate = $("#enddateStackChartMax").val();
																		 StackedandLineRevenue2 = jQuery.parseJSON(data.MaxStackedandLineRevenue);
																		 fetchRevenuePerTechMaxMin(StackedandLineRevenue2, stDate, endDate,"Max");}
											                         else if(id == 'applyButtStackChartMin'){
											                        	 stDate =  $("#startdateStackChartMin").val();
											                             endDate = $("#enddateStackChartMin").val();
																		 StackedandLineRevenue2 = jQuery.parseJSON(data.MinStackedandLineRevenue);
																		 fetchRevenuePerTechMaxMin(StackedandLineRevenue2, stDate, endDate,"Min");}
											                         else if(id == 'applyButtLineChartMax'){
																		 StackedandLineRevenue2 = jQuery.parseJSON(data.MaxStackedandLineRevenue);   
																		 fetchLineChartRevenueMaxMin(StackedandLineRevenue2,"Max");
											                        	 }
											                         else if(id == 'applyButtLineChartMin'){
																		 StackedandLineRevenue2 = jQuery.parseJSON(data.MinStackedandLineRevenue);   
																		 fetchLineChartRevenueMaxMin(StackedandLineRevenue2,"Min");
											                        	 }
													 }
									     					else{
									     						  alert("There is no data to display between these two selected dates for this site, or please check all the conditions selected in the upper side");
									     						   
									     					}
									                         
										     					 

												

									  },
									  
									  error : function(error) {
											console.log("The error is " + error);
										}
								     });

							                }
								
									
									});



                                //exporting 
								
								
								$('#export').on('click', function(e) {
									
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
								// close ul for export after selecting export option
								$('#gridExport, #mapExport, #chartsExport, #allExport').click(function(){
										$(this).parents('#dropLeft').removeClass("show");
                                });
									
								// clicking outsie menu div close ul     
								var specifiedElement = document.getElementById('notifactionDropdown');

								//I'm using "click" but it works with any event
								document.addEventListener('click', function(event) {
								   var isClickInside = specifiedElement.contains(event.target);

									if (!isClickInside) {
									   $('#dropLeft').removeClass("show");
								    }
								});	
										
								
								$('#gridExport').click(function(){      
									exportGrid();
								});

								$('#mapExport').click(function(){
									//as excel
                                	//exportDivToExcel("mapContainer");
                                	// as image
									exportDivToImage("mapContainer");
								});

								$('#chartsExport').click(function(){
									// as excel
									/*if($('#site_revenue_chart').is(':visible')){exportDivToExcel("site_revenue_chart"); }
									if($('#time_revenue_stack').is(':visible')){exportDivToExcel("time_revenue_stack");}
									if($('#time_revenue_line').is(':visible')){exportDivToExcel("time_revenue_line");}
									if($('#pie_charts').is(':visible')){exportDivToExcel("pie_charts");}
									if($('#max_time_revenue_stack').is(':visible')){exportDivToExcel("max_time_revenue_stack");}
									if($('#min_time_revenue_stack').is(':visible')){exportDivToExcel("min_time_revenue_stack");}
									if($('#max_time_revenue_line').is(':visible')){exportDivToExcel("max_time_revenue_line");}
									if($('#min_time_revenue_line').is(':visible')){exportDivToExcel("min_time_revenue_line");}
									*/
                                	//as image
									
									if($('#site_revenue_chart').is(':visible')){exportDivToImage("site_revenue_chart"); }
									if($('#time_revenue_stack').is(':visible')){exportDivToImage("time_revenue_stack");}
									if($('#time_revenue_line').is(':visible')){exportDivToImage("time_revenue_line");}
									if($('#pie_charts').is(':visible')){exportDivToImage("pie_charts");}
									if($('#max_time_revenue_stack').is(':visible')){exportDivToImage("max_time_revenue_stack");}
									if($('#min_time_revenue_stack').is(':visible')){exportDivToImage("min_time_revenue_stack");}
									if($('#max_time_revenue_line').is(':visible')){exportDivToImage("max_time_revenue_line");}
									if($('#min_time_revenue_line').is(':visible')){exportDivToImage("min_time_revenue_line");}
								});

                                $('#allExport').click(function(){
                                    //as excel
                                    exportGrid();

                                	/*exportDivToExcel("mapContainer");
                                	if($('#site_revenue_chart').is(':visible')){exportDivToExcel("site_revenue_chart"); }
									if($('#time_revenue_stack').is(':visible')){exportDivToExcel("time_revenue_stack");}
									if($('#time_revenue_line').is(':visible')){exportDivToExcel("time_revenue_line");}
									if($('#pie_charts').is(':visible')){exportDivToExcel("pie_charts");}
									if($('#max_time_revenue_stack').is(':visible')){exportDivToExcel("max_time_revenue_stack");}
									if($('#min_time_revenue_stack').is(':visible')){exportDivToExcel("min_time_revenue_stack");}
									if($('#max_time_revenue_line').is(':visible')){exportDivToExcel("max_time_revenue_line");}
									if($('#min_time_revenue_line').is(':visible')){exportDivToExcel("min_time_revenue_line");}*/
									
									// as image
                                	exportDivToImage("mapContainer");
                                	if($('#site_revenue_chart').is(':visible')){exportDivToImage("site_revenue_chart"); }
									if($('#time_revenue_stack').is(':visible')){exportDivToImage("time_revenue_stack");}
									if($('#time_revenue_line').is(':visible')){exportDivToImage("time_revenue_line");}
									if($('#pie_charts').is(':visible')){exportDivToImage("pie_charts");}
									if($('#max_time_revenue_stack').is(':visible')){exportDivToImage("max_time_revenue_stack");}
									if($('#min_time_revenue_stack').is(':visible')){exportDivToImage("min_time_revenue_stack");}
									if($('#max_time_revenue_line').is(':visible')){exportDivToImage("max_time_revenue_line");}
									if($('#min_time_revenue_line').is(':visible')){exportDivToImage("min_time_revenue_line");}
								});


								
                            	function exportGrid() {
                            		 
                            		 // console.log("the exportArrayGrid is  "+exportArrayGrid);
                            		 
                            		  downloadCSVFile(exportArrayGrid, "grid");

                            		  function downloadCSVFile(csv, filename) {
                            			var csv_file, download_link;

                            			csv_file = new Blob([csv], {type: "text/csv"});

                            			download_link = document.createElement("a");

                            			download_link.download = filename;

                            			download_link.href = window.URL.createObjectURL(csv_file);

                            			download_link.style.display = "none";

                            			document.body.appendChild(download_link);

                            			download_link.click();
                            		}
                            		
                            	}

                            /*	function exportDivToExcel(div){
                            		// Convert the div to image (canvas)
                            		/*html2canvas(document.getElementById("site_revenue_chart"), {
                            		    onrendered: function (canvas) {*/
                            	/*	   	html2canvas(document.getElementById(div), {
                                                useCORS: true,
                                          }).then(function (canvas) {
                            		        var img = canvas.toDataURL("image/jpeg", 0.9); //image data of canvas
                                            var fileName = div;
                            		        var a = document.createElement("a"); //Create <a>
                            		        //a.href = "data:image/png;base64," + ImageBase64; 
                            		        a.href = img;
                            		        a.download = fileName + ".jpeg"; //File name Here
                            		       // a.click();
                            		       
                            		        var uri = 'data:application/vnd.ms-excel;base64,'
                            		            , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><img src="{table}" alt="grafica" /></body></html>'
                            		            , base64 = function (s) { return window.btoa(unescape(encodeURIComponent(s))) }
                            		            , format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) }
                            		        
                            		       var ctx = { worksheet: name || 'export', table: fileName + ".jpeg" }
                            		        //window.location.href = uri + base64(format(template, ctx))
                            		        var link = document.createElement("a");
                            		        link.href = uri + base64(format(template, ctx));
                            		        link.download = fileName+".xls";
                            		        
                            		        link.click();

                            		      /*  var imgurl = "https://image.flaticon.com/icons/svg/60/60752.svg"
                            		        	var HTMLtext="<table><tr><td>Image export</td><td><img src='"+img+"'style='width:500px;height:600px;'></img></td></tr></table>";
                            		        	//window.location.href = 'data:application/vnd.ms-excel,' + encodeURIComponent(HTMLtext);
                            		        	//sa = window.location.href;
                            		        	//     return (sa);
                            		        	var link = document.createElement("a");
                            		        	     link.href = 'data:application/vnd.ms-excel,' + encodeURIComponent(HTMLtext);
                                     		        link.download = fileName+".xls";
                                     		        
                                     		        link.click();*/
                                     		    
                                     		       /*WorksheetImage img = new WorksheetImage(image);
                                     		       img.TopLeftCornerCell = worksheet.Rows[rowIndex].Cells[0];
                                     		       img.TopLeftCornerPosition = new PointF(0.0F, 0.0F);
                                     		       img.BottomRightCornerCell = worksheet.Rows[rowIndex + 8].Cells[2];
                                     		       img.BottomRightCornerPosition = new PointF(100.0F, 100.0F);
                                     		       worksheet.Shapes.Add(img);*/  
                                     		     
                            		    //}
                            		//});
                                //}

                                function exportDivToImage(div){
                                	html2canvas(document.getElementById(div), {
                                        useCORS: true,
                                   }).then(function (canvas) {
                                    var imageURL = canvas.toDataURL("image/jpeg");
                                    /*let a = document.createElement("a");
                                    a.href = imageURL;
                                    a.download = imageURL;
                                    //a.namespaceURI = "map";
                                    a.click();*/
                                    let link = document.createElement("a");
                          		    link.setAttribute("href", imageURL);
                          		    link.setAttribute("download", div+".jpeg"); // Choose the file name
                          		    link.click(); // Download your excel file
                          		   
                                   });
                                }

								// print div
								$('#print').click(function(){
									printDiv("mapContainer");
									/*printDiv("site_revenue_chart");
									printDiv("time_revenue_stack");
									printDiv("time_revenue_line");
									printDiv("pie_charts");*/
								});
								
								function printDiv(div) {
                                           var tab = document.getElementById(div);
                                           var win = window.open('', '', 'height=700,width=700');
                                           win.document.write(tab.outerHTML);
                                           win.document.close();
                                           win.print();
                                }

								/*$('#pdf').click(function(){
								html2canvas(document.getElementById('mapContainer'), {useCORS: true}).then(function(canvas) {
					            	    var contentWidth = canvas.width;
							            var contentHeight = canvas.height;

							            //One page pdf shows the height of canvas generated by html page;
							            var pageHeight = contentWidth / 592.28 * 841.89;
							            //html page height without pdf generation
							            var leftHeight = contentHeight;
							            //Page offset
							            var position = 0;
							            //a4 paper size [595.28841.89], width and height of image in pdf of canvas generated by html page
							            var imgWidth = 595.28; 
							            var imgHeight = 592.28/contentWidth * contentHeight;
							            //Return picture dataURL, parameters: picture format and sharpness (0-1)
						                var pageData = canvas.toDataURL('image/jpeg', 1.0);

						               //Direction is vertical by default, dimension ponits, format A4 [595.28841.89]
						               var pdf = new jsPDF('', 'pt', 'a4');
						            
						               //There are two heights to distinguish, one is the actual height of the html page, and the height of the generated pdf page (841.89)
						              //When the content does not exceed the display range of one page of pdf, paging is not required

						               if (leftHeight < pageHeight) {
							            	console.log("1");
							                pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight );
							            } else {
							            	console.log("2");
							                while(leftHeight > 0) {
							                    pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
							                    leftHeight -= pageHeight;
							                    position -= 841.89;
							                    //Avoid adding blank pages
							                    if(leftHeight > 0) {
							                      pdf.addPage();
							                    }
							                }
							            }
							            pdf.save('stone.pdf');
						                
						              //}
						            });

							});*/

								
						
});
</script>

<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>