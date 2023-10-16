<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title></title>
     <link rel="shortcut icon" href="">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet"
            href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
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
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
        <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
	<!-- region revenue report scripts -->
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/js/Reports/RegionRevenueReport.js"></script>
	 <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
			
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgrid.js"></script> -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
			
			
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
			
			<!--  Chart script -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
	

	
	
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.charts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.candy.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.zune.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fusioncharts.theme.ocean.js"></script>
	<!--  MultiSelect Script -->
<link href="${pageContext.request.contextPath}/resources/css/virtual-select.min.css" rel="stylesheet"    >
 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/virtual-select.min.js"></script>	
		<!-- Tags InputScript -->
<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/js/tagsInputAutocomplete.js"></script>
			
			<!-- Google Maps Script -->
		<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	
	<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/js/Reports/RegionRevenueGoogleMaps.js"></script>
	
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
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>

  
	 <div Style=" left: 0; bottom: 0;" id="Revenue Div">

	<div class="container-fluid">     
	     <div class="row">
	     
	     <p></p>
		
		 </div>
	

			<div class="row second" >	
			
			<div class="col-md-2" style="display: flex; ">
				<div class="form-group "style="margin-left: 15px; ">
					<div class="input-group-prepend">
						<span class="input-group-text" style="color: green">Region Revenue Report</span>
					</div>
				</div>
			</div>
				
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="headerStrtDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
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
					<input type="text" id="headerEndDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
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
		 			<!-- <div class="glyph" style="padding-top:0px; padding-right: 10px;">
                           <div class="form-group">
			               <div class="input-group-prepend" data-target-input="nearest">
					       <div class="input-group-text">	
      				       <button type="button" class="btn" name="fields" id="FieldsOption" data-toggle="modal" data-target="#Modal" ><i class="fa fa-filter" style="font-size:20px"></i></button>
				         </div>
                        </div>
	                </div>
	                </div> -->
		 			<div class="glyph" style="padding-top:0px;">
			 			 <div class="dropdown"  Style="margin-right:10px; height:30px;">
	                        <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Menu	</button>
	
	                       <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
    	                   <a class="dropdown-item" id="edit" href="#">Edit </a>
    	                   <a class="dropdown-item" id="print" href="#">Print </a>
    	                   <a class="dropdown-item" id="export" href="#">Export </a>
    	                   <a class="dropdown-item" id="pdf" href="#">PDF </a>
    	                   <a class="dropdown-item" id="setup" href="#">Setup Auto Email </a>
    	                   <a class="dropdown-item" id="addCol" href="#">Add Column </a>
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
   			 <a class="nav-link " id="revenueColor-tab" style="color: gold;" data-toggle="tab" href="#revenueColor" role="tab" aria-controls="revenueColor" aria-selected="true">REVENUE COLOR</a>
  		</li>
 
	</ul>
	<div class="tab-content">
 
	
  <div class="tab-pane active" id="revenueColor" role="tabpanel" aria-labelledby="revenueColor-tab">
    					
   <table id="regionsTech">  
  
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
								
  </div>
  </div>
  </div>
   
  
  
                            <div class="modal-footer">
                            
                        </div>
                    
                </div>
            </div>
		
		    </div>
 
 
</div> 




	


	

	
<div class="row" >
				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Region</span>
							<input type="text" id="region"
								value="${region}" class="form-control text-input" />
								
								<!--  <div class="input-group-append" id="chooseRegionName"
                                        onclick="chooseRegionName()">
                                        <div class="input-group-text" style="margin-left:10px;">
										<i class="fas fa-edit"></i>
                                        </div>
                                    </div>
                                     -->
						</div>
					</div>
				</div>

			
		
		
		<!--	</div>  -->
			
			
			
		<!-- 	<div class="row" > -->
			
	<div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                  
    	             <select id="period" class="form-control text-input" style="height:37px">
    	              <option value="null" >No Period Selected </option>
					   <option value="Daily" selected>Daily</option>
			           <option value="Weekly">Weekly</option>
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
			
			
			
		<!-- 		<div class="col-md-auto">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                
	   <div class="form-group">
				<div class="input-group-prepend">
    	                  
    	             <select id="technologies" class="form-control text-input" style="height:37px">
    	              <option value="null" selected>Technologies</option>
					   <option value="2g" >2G</option>
			           <option value="2g3g">2G and 3G</option>
			           <option value="2g3g4g">2G,3G and 4G</option>
			         
			           
		            </select>
				</div>
		 </div> 
	</div>
	</div>
			</div>
			-->
			
		
	     
	      <div class="col-md-1">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Max" name="Max">
      					<span style="margin-left: 10px;">Maximum</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	     
	      <div class="col-md-1">
          
            	<div class="form-group">
				   <div class="input-group-prepend" data-target-input="nearest">
					<div class="input-group-text">
						
      					<input type="checkbox" value="0" id="Min" name="Min">
      					<span style="margin-left: 10px;">Minimum</span>
				   </div>
                  </div>
			   </div>
			
	     </div>
	    <div class="col-md-1" style="float: right; padding-top:-100px;" >
			<div class="btn-group pull-right"  >
		 			<button type="button"  id ="clearButton"class="btn btn-light" style=" margin-top:-5px; margin-right:-15px; "  >Clear</button>
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
  <table id="regionsWithTech">
  
   <caption style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-210px;left:20px;">Regions Revenue</caption>
  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    
  </tr>
  
  <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="to500" onclick="ShowHideMarkers('#FFDC00');" value="#FFDC00"/></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>
        
    
    <td style="position: relative;top: 25px;left:-40px;"><label style="color:black;font-weight:bold;font-size:2ex; " >0$ - 499$</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="500to2000" onclick="ShowHideMarkers('#0080FF');" value="#0080FF"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot blue"></div></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot "></div></td>
    
    <td style="position: relative;top: 25px;left:-40px;"><label style="color:black;font-weight:bold;font-size:2ex; " >500$ - 2000$</label></td>
    
    
  </tr>
  
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="2000andMore"  onclick="ShowHideMarkers('red');" value="red"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td>
    
    <td style="position: relative;top: 25px;left:90px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >2001$ And Above </label></td>
    
    
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
        <div class="box" id="mapContainer"></div>
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
										<div class="input-group-prepend">
											<div id="loaderDiv" style="display: none;">
												<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /><b style="color:#800020;font-size:15px;"> Loading Data ... Please wait</b> 
											</div>
										</div>
									</div>
									<div class="col-sm-4 almgrid-global-search-box">
										Search
										<input type="text" class="form-control almgrid-global-search" />
									</div>
								</div>
								<div class="row">
								<div class="col-sm-12">
										<div class="input-group-prepend">
										<div id="alertMsgDiv" style="display: none;padding-left: 40px">
										<br>
											<b style="color:red;font-size:15px;white-space: nowrap;">The number of original fetched data is exceeding the number of allowed data to show. Please set a filter to reduce the fetched data.</b> 
											</div>
									</div></div></div>
								<div id= "tableGrid" class="table-responsive almgrid-table-div">
									<table id="gridTable" class="table table-striped table-bordered almgrid-table">
										<thead>
											<tr class="header">
												
												<th>Region Name 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>RegionID
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
											
											   
											
												
												
													
													

											<tr>
												
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
        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseThree">
         Charts
        </a>
      </h4>
    </div>
    <div id="collapseThree" class="panel-collapse collapse show" role="tabpanel" aria-labelledby="headingThree">
      <div class="panel-body" style="background:#f1f4f7 !important">
         <div class="card-body">
                 <!-- start of chart 21-06 -->
        	<!-- start second row of charts -->

			
			
			<div class="container-fluid-responsive" >
			
				<div class="row" style="background:white !important ;border: 3px groove #FDFEFE;">
				
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
											
												
																				<div class="col-md-3 "  id="filter_Chart_Regions" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;">
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2">
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepicker5" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="msStrtDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker5"   />
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
																		  <input type="text" id="msEndDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker6"   />
																			 <div class="input-group-append" data-target="#datetimepicker6" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div>
													          <span><b>Region Name</span>
																<input type="text" id="msRegName" value="${msRegName}" class="form-control text-input" style="margin-bottom:20px;" />	</div>
																	  <!-- Options for the multidrop down for regions -->
																	   <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="msChngOpt"  class="form-control text-input" onchange="changeFunc();">
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
																	  <div id="msFltrDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="msRevOpt"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtn" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValMSFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValMSFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																			<!-- -->
											
																  <!-- Options for the multidrop down for technologies -->
															<!-- 	  <div style="font-weight: bold;">Technologies</div>
								   																  
														    	                
																   <div class="form-group">
																	<div class="input-group-prepend">
																			  
																		 <select id="technologies1" class="form-control text-input" style="height:37px">
																		  <option value="null" selected>Technologies</option>
																		   <option value="2g" >2G</option>
																		   <option value="2g3g">2G and 3G</option>
																		   <option value="2g3g4g">2G,3G and 4G</option>
																		 
																		   
																		</select>
																	</div>
																		
															 </div> 
															  -->
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="msChrtClrFltr">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="msChrtApply">Apply</button>	
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
			
				<div class="row" style="background:white !important ;border: 3px groove #FDFEFE;">
				
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
											
												
																				<div class="col-md-3 "  id="filter_Chart_Regions" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;">
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepicker7" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="stackedStrtDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker7"   />
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
																		  <input type="text" id="stackedEndDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker8"   />
																			 <div class="input-group-append" data-target="#datetimepicker8" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Region Name</span>
																<input type="text" id="stackedRegName" value="${stackedRegName}" class="form-control text-input" style="margin-bottom:20px;" /></div>

											 <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="stackedChngOpt"  class="form-control text-input" onchange="changeFunc();">
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="stackedFltrDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="stackedRevOpt"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtn1" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValStackedFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValStackedFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
																<!--   <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																			  
																		 <select id="technologies2" class="form-control text-input" style="height:37px">
																		  <option value="null" selected>Technologies</option>
																		   <option value="2g" >2G</option>
																		   <option value="2g3g">2G and 3G</option>
																		   <option value="2g3g4g">2G,3G and 4G</option>
																		 
																		   
																		</select>
																	</div>
																		
															 </div>
															  -->
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="stackedChrtClrFltr">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="stackedChrtApply">Apply</button>	
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
			
				<div class="row" style="background:white !important ;border: 3px groove #FDFEFE;" >
				
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
											
												
																				<div class="col-md-3 "  id="filter_Chart_Regions" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;"> 
																					<!-- <div class="card card-primary card-tabs cards-margin" style="margin-left: 40px;margin-top: 5px;margin-bottom: 5px;"> -->
																						
																							
																							<div id="filterChart" class= "canvas-style2" >
																							
																	  <!-- startdate -->
																	  <div style="font-weight: bold;margin-top:10px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group">
																			<div class="input-group-prepend" id="datetimepicker9" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="lineStrtDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker9"   />
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
																		  <input type="text" id="lineEndDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker10"   />
																			 <div class="input-group-append" data-target="#datetimepicker10" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Region Name</span>
																<input type="text" id="lineRegName" value="${lineRegName}" class="form-control text-input" style="margin-bottom:20px;" />															  </div>

																	<div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="lineChngOpt"  class="form-control text-input" onchange="changeFunc();">
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="lineFltrDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="lineRevOpt"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtn2" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValLineFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValLineFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for technologies -->
															<!--  	  <div style="font-weight: bold;">Technologies</div>
																  <div class="form-group">
																	<div class="input-group-prepend">
																			  
																		 <select id="technologies3" class="form-control text-input" style="height:37px">
																		  <option value="null" selected>Technologies</option>
																		   <option value="2g" >2G</option>
																		   <option value="2g3g">2G and 3G</option>
																		   <option value="2g3g4g">2G,3G and 4G</option>
																		 
																		   
																		</select>
																	</div>
																		
															 </div>
															  -->
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN"  id="lineChrtClrFltr">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="lineChrtApply">Apply</button>	
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
		
		<div class="row" style="background:white !important ;border: 3px groove #FDFEFE;" >
			<div class="col-md-12">
			
			<div class="card card-primary card-tabs cards-margin ">
					<div class="card-body ">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group" style="border: 3px groove #FDFEFE;">
										<div class="col-md-7  ">
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
								<!-- 
								
									<div class="col-md-6 " >
										<div class="card card-primary card-tabs " >
											
												
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
										-->
										<div class="col-md-5 " >
																	<div   id="filter_Chart_Piez" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); width:93%; margin-left:20px;  ">
																						
																							
																							<div id="filterChartPiez"  >
																						<div style="font-weight: bold;margin-top:20px; " class="pos">Pie Charts Filter</div>	
																	  <div style="font-weight: bold;margin-left:50px;margin-right:50px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group" style="margin-left:50px;margin-right:50px;">
																			<div class="input-group-prepend" id="datetimepicker13" data-target-input="nearest">
																			<input type="text" id="regPieStrtDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker13"   />
																			   <div class="input-group-append" data-target="#datetimepicker13" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<div style="font-weight: bold;margin-left:50px;margin-right:50px;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group" style="margin-left:50px;margin-right:50px;">
																		  <div class="input-group-prepend" id="datetimepicker14" data-target-input="nearest">
																		  <input type="text" id="regPieEndDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker14"   />
																			 <div class="input-group-append" data-target="#datetimepicker14" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span style="font-weight: bold;margin-left:50px;">Region Name</span>
																<input type="text" id="regPieRegName" value="${regPieRegName}" class="form-control text-input" style="margin-bottom:20px;margin-right:50px;margin-left:50px;width:82%;" /></div>

											 					<div style="font-weight: bold;margin-right:50px;margin-left:50px;">Filter options</div> 
																	  <div class="row" >													  
																	  <div class="col-md-12">
																		  
																	  <select id="regPieChngOpt" style="margin-right:50px;margin-left:50px;width:82%;"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="regPieFilterDiv" style="margin-right:50px;margin-left:50px;">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="regPieRevOpt"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;" disabled>
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtn4" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="regMinValFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values">
														
																			<input type="text"  class="form-control text-input" id="regMaxValFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
															<!-- 	  <div style="font-weight: bold;margin-right:50px;margin-left:50px;">Technologies</div>
																  <div class="form-group" style="margin-right:50px;margin-left:50px;">
																	<div class="input-group-prepend">
																			  
																		 <select id="technologies5" class="form-control text-input" style="height:37px">
																		  <option value="null" selected>Technologies</option>
																		   <option value="2g" >2G</option>
																		   <option value="2g3g">2G and 3G</option>
																		   <option value="2g3g4g">2G,3G and 4G</option>
																		 
																		   
																		</select>
																	</div>
																		
															 </div>
															  -->
														
															 <div class="row flex" >													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="regPieClrFltr">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="regPieApply">Apply</button>	
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
		   	<!-- hereee --> 
		   				<div class="row" style="background:white !important ;border: 3px groove #FDFEFE;" >
			<div class="col-md-12">
			
			<div class="card card-primary card-tabs"  >
					<div class="card-body "   >

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group" style="border: 3px groove #FDFEFE;" >
								
									<div class="col-md-7 " >
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
										
										<!-- here needed a filter -->
										<div class="col-md-5 " >
																	<div   id="filter_Chart_Piez" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); width:93%; margin-left:20px;  ">
																						
																							
																							<div id="filterChartPiez"  >
																						<div style="font-weight: bold;margin-top:20px; " class="pos">Pie Charts Filter</div>	
																	  <div style="font-weight: bold;margin-left:50px;margin-right:50px;">Start Date</div>
																	  <div class="row">
																	  <div class="col-md-12">
																		<div class="form-group" style="margin-left:50px;margin-right:50px;">
																			<div class="input-group-prepend" id="datetimepicker11" data-target-input="nearest">
																			<input type="text" id="revPieStrtDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker11"   />
																			   <div class="input-group-append" data-target="#datetimepicker11" data-toggle="datetimepicker">
																					<div class="input-group-text">
																						<i class="fa fa-calendar"></i>
																					</div>
																				</div>
																			</div>
																		</div>
																	  </div>
																	</div>
																	
																	<div style="font-weight: bold;margin-left:50px;margin-right:50px;">End Date</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group" style="margin-left:50px;margin-right:50px;">
																		  <div class="input-group-prepend" id="datetimepicker12" data-target-input="nearest">
																		  <input type="text" id="revPieEndDate" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker12"   />
																			 <div class="input-group-append" data-target="#datetimepicker12" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span style="font-weight: bold;margin-left:50px;">Region Name</span>
																<input type="text" id="revPieRegName" value="${revPieRegName}" class="form-control text-input" style="margin-bottom:20px;margin-right:50px;margin-left:50px;width:82%;" /></div>

											 					<div style="font-weight: bold;margin-right:50px;margin-left:50px;">Filter options</div> 
																	  <div class="row" >													  
																	  <div class="col-md-12">
																		  
																	  <select id="revPieChngOpt" style="margin-right:50px;margin-left:50px;width:82%;"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="revPieFilterDiv" style="margin-right:50px;margin-left:50px;">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="revPieRevOpt"  class="form-control text-input" onchange="changeRevenueFunc();" style="display:none;margin-bottom:20px;" disabled>
																	 <option value="voice_revenue"> Voice revenue</option>
																	 
																	  <option value="sms_revenue">SMS revenue</option>
																	 <option value="data_revenue"> Data revenue</option>
																	 
																	  <option value="vas_revenue">VAS revenue</option>
																	
																	 
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="txtBtn3" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValRevPiesFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values">
														
																			<input type="text"  class="form-control text-input" id="maxValRevPiesFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																<!--  <div style="font-weight: bold;margin-right:50px;margin-left:50px;">Technologies</div>
																  <div class="form-group" style="margin-right:50px;margin-left:50px;">
																	<div class="input-group-prepend">
																			  
																		 <select id="technologies4" class="form-control text-input" style="height:37px">
																		  <option value="null" selected>Technologies</option>
																		   <option value="2g" >2G</option>
																		   <option value="2g3g">2G and 3G</option>
																		   <option value="2g3g4g">2G,3G and 4G</option>
																		 
																		   
																		</select>
																	</div>
																		
															 </div>
															  -->
														
															 <div class="row flex" >													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="revPieClrFltr">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="revPieApply">Apply</button>	
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
		
	


<div class="container-fluid" >     
		
	
 <!-- Region Name Modal -->
            <div class="modal fade" id="autocompleteModal" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Regions Name</h5>
                            <button type="button" class="close" onclick="popupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Regions Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="regionsAutocomplete" /> 
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

							

	/////////////////////

$( "#legendDiv" ).draggable(); 


 


var divTablee;
var divRevTable;

var listRegionsClrFltr;
function initMap() {

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

	
    	
		regLatLng = ${LatLngReg};	
    
		listRegions = ${listRegions};
		listRegionsClrFltr=listRegions;

	UnselectAll();
	setColor(listRegions,regLatLng,map);
	
 
   	

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
					   

  
	  }



var date = new Date();
date.setDate(date.getDate() - 2);


date.setHours( 0,0,0,0 );

var dateend = new Date();
dateend.setDate(dateend.getDate() - 2);

var c = Date.parse(date);

$('#headerStrtDate, #msStrtDate, #stackedStrtDate, #lineStrtDate, #revPieStrtDate, #regPieStrtDate').datetimepicker({
    defaultDate:c
});
$('#headerEndDate, #msEndDate, #stackedEndDate, #lineEndDate, #revPieEndDate, #regPieEndDate').datetimepicker({
    defaultDate:dateend
});



$(document).ready(function() {



	$('#clearButton'). click(function(){
        document.getElementById('region').value = '';
			});

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

	/* var techOption = [
		  { label: '2G', value: '2g' },
		  { label: "2G and 3G", value: '2g3g'},
		  { label: '2G,3G and 4G', value: '2g3g4g' },
		 
		];
		*/
	/* VirtualSelect.init({
		  ele: '#technology',
		  options: techOption,
		  multiple: true,
		  placeholder: 'Technology selection'
		});
*/
	/* var myOptions3 = [
		  { label: '2G', value: '2g' },
		  { label: "3G", value: '3g'},
		  { label: "4G", value: '4g'},
		  { label: "5G", value: '5g'},
		 
		];
	 VirtualSelect.init({
		  ele: '#technologies',
		  options: myOptions3,
		  multiple: true,
		  placeholder: 'Technologies'
		});
		*/


		  
		// Default grid;

		    var ReportArrayGlobal = ${revenueReportList};
            var msColumnChartObj = ${msColumnChartObj};
            
            var StackedandLineRevenue = ${StackedandLineRevenue};
            var pieRevenue = ${pieRevenue};

                if (ReportArrayGlobal.length > 0) { 
                	 console.log("*******Enter ");
                }
			    else{
			    	 console.log("*******Enter else ");
			    	 
				}     
                     

               	 var almgrid = new AlmgridTable({
                        tableId: "gridTable",
                        dataArray: ReportArrayGlobal,
                        selectCheckbox: false,
                        drawTableGrid :  function (tableId, dataArray) {
       				    
       			        var tableBody = document.querySelector("#" + tableId + " tbody");
       			        var columnLinkNb = this.columnLinkNb;
       			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
       			        var gridContainerId = tableId + "_container";
       			        $(gridContainer).attr('id', gridContainerId);
       			        $(tableBody).empty();
       			        if (dataArray.length > 0) {
					 
	                    var dataRev=0,smsRev=0,voiceRev=0,grossRev=0;
			            var ArrayKeys = Object.keys(dataArray[0]);
			            
			            var itemRow = "";
			       		var columnVal;
			           for (var i = 0; i < dataArray.length; i++) {
				           
							itemRow += "<tr class='filterRows'>";
							
			               for (var j = 0; j < ArrayKeys.length; j++) {
			               	
			            	 columnVal = ArrayKeys[j];
			            	 
			                 

			                	
			                       itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>"; 
			                       if(columnVal =="smsRevenue")   smsRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
			                       if(columnVal =="dataRevenue")   dataRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
			                       if(columnVal =="voiceRevenue")   voiceRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
			                       if(columnVal =="vasRevenue")   grossRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
		          			                  
							   
		          			               }
		          							 itemRow += "</tr>";
		          							}
			          
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
										
		          	            $(tableBody).append(itemRow);

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

		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb,dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

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
               	listRegions = ${listRegions};
            	listRegionsClrFltr=listRegions;
               	 //draw charts for the regions
                 stDate =  $("#headerStrtDate").val();
                 endDate = $("#headerEndDate").val();
                			RegionRevenue(msColumnChartObj);
							fetchRevenueStacked(StackedandLineRevenue,stDate,endDate);
							fetchLineChartRevenue(StackedandLineRevenue);
							PieRevenue(pieRevenue);
							RegionalRevenuePie(listRegions);
                  
		
	//var ReportArrayGlobal = [];
	
	  $("#generate").click(  function() {

		  //var selectedTechOption = $("#technologies").children("option:selected").val();
		  var selectedPeriodOption = $("#period").children("option:selected").val();

		  var startDate = document.getElementById("headerStrtDate").value;
		  //console.log("startDate:" +startDate );
		  var endDate = document.getElementById("headerEndDate").value;
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
		//console.log(selectedPeriodOption);

		  // validation fior a weekly report
		
       if (selectedPeriodOption=='null'&&checkBox1.checked == false && checkBox2.checked == false ){

          alert("Please choose a period or check Maximum or Minimum");
          
               return false;
           }
	
       else{



    	   $("#gridTable").remove();
    	   $("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table">'
					+'<thead><tr class="header"><th>Region Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> '
					+'<i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
						+'<th>RegionID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
						+'</ul></li></th><th>Start Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
						+'</ul></li></th><th> End Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
						+'</ul></li></th><th>Voice Revenue<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">	</ul></li></th>'
						+'<th>SMS Revenue<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'						
						+'<th>Data Revenue<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th><th>VAS Revenue<li class="filter-dropdown dropdown">'
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
						+'</tr></thead><tbody></tbody></table>');	  
		

			   

			  //document.getElementById('regionsAutocomplete').value = "";
			  //concatenatedRegions ="";
			  //selectedRegions=[];
				  


			  		 DeleteMarkers();
						$('input[type="checkbox"]', '#revenueColorTable').prop('checked', false);
				  		 

			  		 if(document.getElementById("tableDiv").innerHTML == newTableDiv){
			  			 defaultLegend();

			  		 }

			  		 var element = document.getElementById("selectUnselect");
			  			if (element.innerHTML == "Select All"){
			  				 element.innerHTML = "Unselect All";
			  			}

		 var regionName = document.getElementById("region").value;

		

		  //var regionName;
		  if(regionName == ""){
			  regionName  ="";
		  }
		  else {
		    regionName  = regionName.split(";");
			regionName  = regionName[0];
			console.log("regionName:" +regionName );
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
//1212
   		 $.ajax({
  		 	  type: "GET",
  		 	  contentType: "application/json; charset=utf-8",
  		 	  url: '${pageContext.request.contextPath}/GetRevenuePerRegion',
  		 	  data: {
  				 	"region" : regionName,	
  				 	//"area": areaName,
  					"startDate" : startDate,
  					"endDate" : endDate,
  				  	"technologies" : "null",//selectedTechOption , 
  				  	"period":selectedPeriodOption
  									
  								
  		 				 },

  		 	dataType: "json",
  		 	success: function (data) {
  				regLatLng=data.LatLngReg;
  				listRegions = jQuery.parseJSON(data.listRegions);
  				//console.log("list regions is "+listRegions);
  				//regLatLngClrFltr=regLatLng;
  		 		if (data != null) {
  		 		
  		 			    //chartData = ${chartData};
  		 		    	//listRegions = ${listRegions};
  		 		    	listRegionsClrFltr=listRegions;
  		 				//renderRegionCharts(listRegions);
  		 				//regLatLng = ${LatLngReg};
  		 				
  		 	//_draw_reDrawGenerate(listRegions,regLatLng);
  		 				  		 		   	  
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
  		 		    
  		 		  if ( regionName != "" ) {
  	  		 		  console.log("hereee");
							UnselectAll();
							AddSelectedRegionColor(listRegions,regLatLng,map);
							}
		 		  
					    else {
					         

					    	if(checkBox1.checked == false && checkBox2.checked == false && (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu" )){
					          //  console.log("Here we go");
					            UnselectAll();
					            setColor(listRegions,regLatLng,map);
					            }

					        else if (checkBox1.checked == true && checkBox2.checked == false && (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu")){
					            UnselectAll();			
					            MaxMinSetColor(listRegions,regLatLng,map,"max");
					            //listRegions=maxList;

					            }
					        else if (checkBox1.checked == false && checkBox2.checked == true &&  (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu")){
					            UnselectAll();			
					            MaxMinSetColor(listRegions,regLatLng,map,"min");
					            //listRegions=minList;

					            }
					        else if (checkBox1.checked == true && checkBox2.checked == true &&  (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu")){
					            UnselectAll();	
					            MaxMinSetColor(listRegions,regLatLng,map,"maxMin");
					            //listRegions=maxMinList;
						           

					            }
					  
  							
  					    }
							
  				
  		 		}
  		 			

  	  		 	},
  		 		 error: function(result) {
  		 			 alert("Error");
  		 			 }
  		 		 });
 	     
 		$.ajax({
				type : "GET",
				contentType: "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/GenerateRegionRevenueReport",
				dataType : "json",
				data : {
				    "startDate" : $("#headerStrtDate").val(),
				    "endDate" : $("#headerEndDate").val(),
				    "region":regionName,
                 "technologyRegions": regionName,
                 "period": selectedPeriodOption,
		            "Payment Method" : $("#Payment Method").children("option:selected").val(),
				    "technologies" :"null",//selectedTechOption ,
					"Max": maxChecked,
					"Min": minChecked,
				},
				success : function(data) {
					
					 if (data != null) {
				       // ReportArrayGlobal = [];
                      ReportArrayGlobal = data.revenueReportList; 
                      //console.log("the global array returned "+ReportArrayGlobal);
                      if (ReportArrayGlobal.length> 0) { 
                     	 console.log("*******Enter ");
                      }
  					else{
  						  alert("There is no data to display between these two selected dates");
  						   
  					}  

                     	 var almgrid = new AlmgridTable({
                              tableId: "gridTable",
                              dataArray: ReportArrayGlobal,
                              selectCheckbox: false,
                              drawTableGrid :  function (tableId, dataArray) {
             				    
             			        var tableBody = document.querySelector("#" + tableId + " tbody");
             			        var columnLinkNb = this.columnLinkNb;
             			        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
             			        var gridContainerId = tableId + "_container";
             			        $(gridContainer).attr('id', gridContainerId);
             			        $(tableBody).empty();
             			        if (dataArray.length > 0) {
      					 
      	                       var dataRev=0,smsRev=0,voiceRev=0,grossRev=0;
      			            var ArrayKeys = Object.keys(dataArray[0]);
      			            var itemRow = "";
      			       		var columnVal;
      			           for (var i = 0; i < dataArray.length; i++) {
      				           
      							itemRow += "<tr class='filterRows'>";
      							
      			               for (var j = 0; j < ArrayKeys.length; j++) {
      			               	
      			            	 columnVal = ArrayKeys[j];
      			            	 
      			                  /* if (j == 0) {
      			                       if (this.selectCheckbox == true) {
      			                           itemRow += "<td class='table-select-all'><input type='checkbox' class='table-select-checkbox' value='" + dataArray[i][ArrayKeys[j]] + "'></td>";
      			                       }
      			                   } else {*/
      			                       itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>"; 
      			                       if(columnVal =="smsRevenue")   smsRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
      			                       if(columnVal =="dataRevenue")   dataRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
      			                       if(columnVal =="voiceRevenue")   voiceRev+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
      			                       if(columnVal =="vasRevenue")   grossRev+= parseFloat(dataArray[i][ArrayKeys[j]]);				                     
      		          			   // }
      							   
      		          			               }
      		          							 itemRow += "</tr>";
      		          							}
      			          
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
      										
      		          	            $(tableBody).append(itemRow);

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

      		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb,dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

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
               

				}
						
	  },
	  
	  error : function(error) {
			console.log("The error is " + error);
		}
  });


 		 $.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateRegionRevenueCharts",
			dataType : "json",
			data : {
			    "startDate" : $("#headerStrtDate").val(),
			    "endDate" : $("#headerEndDate").val(),
			    "region":regionName,
             "period": selectedPeriodOption,
	            "Payment Method" : $("#Payment Method").children("option:selected").val(),
				"Max": maxChecked,
				"Min": minChecked,
			},
			success : function(data) {
				
				
				 if (data != null) {
		  			 listRegions = jQuery.parseJSON(data.listRegions);
		  				pieRevenue = data.pieRevenue;
						
		  		 		RegionalRevenuePie(listRegions);

						
		  		  msColumnChartObj=jQuery.parseJSON(data.msColumnChartObj);
                  pieRevenue=jQuery.parseJSON(data.pieRevenue);
                  //pieRevenue=data.pieRevenue;
                  StackedandLineRevenue = jQuery.parseJSON(data.StackedandLineRevenue);
                  if (msColumnChartObj.length> 0 && pieRevenue.length> 0 && StackedandLineRevenue.length> 0) { 
                 	 console.log("*******Enter ");
                  }
					else{
						//  alert("There is no data to display between these two selected dates for charts!!");
						   
					}     
			}
				 if(selectedPeriodOption == "Weekly"){
					 fetchRevenueStackedSecondary(StackedandLineRevenue, $("#headerStrtDate").val(), $("#headerEndDate").val());
						fetchLineChartRevenueSecondary(StackedandLineRevenue);
						}
				 else{
					 fetchRevenueStacked(StackedandLineRevenue, $("#headerStrtDate").val(), $("#headerEndDate").val());
						fetchLineChartRevenue(StackedandLineRevenue);
						}
					RegionRevenue(msColumnChartObj);
					PieRevenue(pieRevenue);
					
  },
  
  error : function(error) {
		console.log("The error is " + error);
	}
}); 
			var fltrdStrtDt = $("#headerStrtDate").val();
			var fltrdEndDt = $("#headerEndDate").val();
			var fltrdRegionNme = $('#region').val();
			//var fltrdTech = $("#technologies").children("option:selected").val();
			
			 $("#msStrtDate").val(fltrdStrtDt).trigger('change');
			 $("#stackedStrtDate").val(fltrdStrtDt).trigger('change');
			 $("#lineStrtDate").val(fltrdStrtDt).trigger('change');
			 $("#revPieStrtDate").val(fltrdStrtDt).trigger('change');
			 $("#regPieStrtDate").val(fltrdStrtDt).trigger('change');
			 
			 
			 $("#msEndDate").val(fltrdEndDt).trigger('change');
			 $("#stackedEndDate").val(fltrdEndDt).trigger('change');
			 $("#lineEndDate").val(fltrdEndDt).trigger('change');
			 $("#revPieEndDate").val(fltrdEndDt).trigger('change');
			 $("#regPieEndDate").val(fltrdEndDt).trigger('change');
			 
			 
					
			$('#msRegName').val(fltrdRegionNme).trigger('change');
			$('#stackedRegName').val(fltrdRegionNme).trigger('change');
			$('#lineRegName').val(fltrdRegionNme).trigger('change');
			$('#revPieRegName').val(fltrdRegionNme).trigger('change');
			$('#regPieRegName').val(fltrdRegionNme).trigger('change');


			var map;
			var bounds;
			var regionArr;
			//var boundsObj;
			function _draw_reDrawGenerate(listRegions,regLatLng){
				//markers = [];
				  var Path =null;
			   	  var latArray=[],lngArray=[]
			   	 regionPool = [];
			      bounds = new google.maps.LatLngBounds();
			     var polygons = [];
			    // boundsObj ={};
			    // boundsArr = [];
				var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
		     		
		 		    document.getElementById("mapContainer").innerHTML ="";
		 		// document.getElementById( "" ).remove();

		 			 map = new google.maps.Map(document.getElementById("mapContainer"), {

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
			   	  
			     
	  		 		   	  if(regLatLng.length>0 && listRegions.length>0){
	  		 					   	 
	  		 		   	$.each(listRegions, function( key, value ) {
	  				      regionPool = [];
	  				      regionArr = [];
	  				      latArray = [];
	  				      lngArray = [];
	  				    regionRevenue = [];
	  					$.each(regLatLng, function( key2, value2 ) {
	  						if(listRegions[key][0] == regLatLng[key2][1]){
	  							latArray.push(parseFloat(jQuery.parseJSON(regLatLng[key2][3]).Lat));
	  					   		lngArray.push(parseFloat(jQuery.parseJSON(regLatLng[key2][4]).Lng));
	  		   		
	  			   	  }    	
	  				});
	  				      bounds = new google.maps.LatLngBounds();
	  						
	  					$.each(latArray, function( i, value2 ) {     //interating among latArray is the same like iterating inside lngArray .
	  						
	  					regionPool.push(new google.maps.LatLng(latArray[i],lngArray[i]));
	  		            bounds.extend(regionPool[regionPool.length-1]);
	  					
	  						
	  					});
	  		            
	  		            	
	  		          regionArr.push(listRegions[key][0]);
	  		          regionRevenue.push(listRegions[key][2]);
	  					
	  					console.log("it is my center !!!!! ==> "+bounds.getCenter());
	  					
	  					
	  				Path = new google.maps.Polygon({
	  				    path: regionPool,
	  				    strokeColor: '#4986E7',
	  				    strokeOpacity: 1,
	  				    strokeWeight: 2,
	  				    fillColor: '#4986E7',
	  				    fillOpacity: 0.1,
	  				    clickable:true,
	  				    
	  				  });

	  			
	  					polygons.push(Path);

	  					let contentString =
	  					    "<b>Region Name</b> : <b> "+[regionArr]+"</b><br>";
	  					   contentString += "<br><b>Total revenue</b>: "+regionRevenue[0].toFixed(2)+"$<br>"+
	  					   					"<b>Region central coordinates</b> : <br> Latitude: "+bounds.getCenter().lat().toFixed(4)+
	  					   					"<br><b>Longitude</b>: "+bounds.getCenter().lng().toFixed(4);
	  				   
	  		          polygons[polygons.length-1].setMap(map);

	  		          const marker = new google.maps.Marker({
	  									    position: bounds.getCenter(),
	  									    map,
	  									    title:'"'+[regionArr]+'"',
	  									    visible:false,
	  									  });
	  					  
	  		  		const infoWindow = new google.maps.InfoWindow({
	  		  			content :contentString, 
	  			    		});
	  		  		
	  		  		Path.addListener("click", () => {
	  		  			infoWindow.open({
	  			    			anchor:marker,
	  			    			map,
	  			    		    shouldFocus: true,
	  				    			
	  			    			});
	  					$("#region").val(jQuery.parseJSON(marker.title));
		    			
	  		  		  });

	  		  		Path.addListener("mouseout", () => {
	  		  			infoWindow.close({
	  			    			anchor:marker,
	  			    			map,
	  			    		    shouldFocus: false,
	  				    			
	  			    			});
	  		  		  });
	  		  		  
	  						});
	  				
			}
	  		 		   else if(regLatLng.length>0 && listRegions.length == 0){
		  		 		   var dupElmnts = [];
	  		 			$.each(regLatLng, function( i, v ) {
		  		 			dupElmnts.push(regLatLng[i][1]);

		  		 			});
	  					     var unique = dupElmnts.filter(function(itm, i, dupElmnts) {
	  					         return i == dupElmnts.indexOf(itm);
	  					     });
	  					     
	  					   $.each(unique, function( key, value ) {
	 	  				      regionPool = [];
	 	  				      regionArr = [];
	 	  				      latArray = [];
	 	  				      lngArray = [];
	 	  				    regionRevenue = [];
	 	  					$.each(regLatLng, function( key2, value2 ) {
	 	  						if(unique[key] == regLatLng[key2][1]){
	 	  							latArray.push(parseFloat(jQuery.parseJSON(regLatLng[key2][3]).Lat));
	 	  					   		lngArray.push(parseFloat(jQuery.parseJSON(regLatLng[key2][4]).Lng));
	 	  		   		
	 	  			   	  }    	
	 	  				});
	 	  				      bounds = new google.maps.LatLngBounds();
	 	  						
	 	  					$.each(latArray, function( i, value2 ) {     //interating among latArray is the same like iterating inside lngArray .
	 	  						
	 	  					regionPool.push(new google.maps.LatLng(latArray[i],lngArray[i]));
	 	  		            bounds.extend(regionPool[regionPool.length-1]);
	 	  					
	 	  						
	 	  					});
	 	  		            
	 	  		            	
	 	  		          regionArr.push(unique[key]);
	 	  		          regionRevenue.push(0);
	 	  					
	 	  					console.log("it is my center !!!!! ==> "+bounds.getCenter());
	 	  					
	 	  					
	 	  				Path = new google.maps.Polygon({
	 	  				    path: regionPool,
	 	  				    strokeColor: '#4986E7',
	 	  				    strokeOpacity: 1,
	 	  				    strokeWeight: 2,
	 	  				    fillColor: '#4986E7',
	 	  				    fillOpacity: 0.1,
	 	  				    clickable:true,
	 	  				    
	 	  				  });

	 	  			
	 	  					polygons.push(Path);

	 	  					let contentString =
	 	  					    "<b>Region Name</b> : <b> "+[regionArr]+"</b><br>";
	 	  					   contentString += "<br><b>Total revenue</b>: "+regionRevenue[0].toFixed(2)+"$<br>"+
	 	  					   					"<b>Region central coordinates</b> : <br> Latitude: "+bounds.getCenter().lat().toFixed(4)+
	 	  					   					"<br><b>Longitude</b>: "+bounds.getCenter().lng().toFixed(4);
	 	  				   
	 	  		          polygons[polygons.length-1].setMap(map);

	 	  		          const marker = new google.maps.Marker({
	 	  									    position: bounds.getCenter(),
	 	  									    map,
	 	  									    title:'"'+[regionArr]+'"',
	 	  									    visible:false,
	 	  									  });
	 	  					  
	 	  		  		const infoWindow = new google.maps.InfoWindow({
	 	  		  			content :contentString, 
	 	  			    		});
	 	  		  		
	 	  		  		Path.addListener("click", () => {
	 	  		  			infoWindow.open({
	 	  			    			anchor:marker,
	 	  			    			map,
	 	  			    		      shouldFocus: true,
	 	  				    			
	 	  			    			});
	 	  					$("#region").val(jQuery.parseJSON(marker.title));
  			    			
	 	  		  		  });
	 	  		  		Path.addListener("mouseout", () => {
	 	  		  			infoWindow.close({
	 	  			    			anchor:marker,
	 	  			    			map,
	 	  			    		      shouldFocus: false,
	 	  				    			
	 	  			    			});
	 	  		  		  });
	 	  		  		  
	 	  						});
	  					     console.log(unique);
	  					   	  }		   			  
			
			   	
				}	

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
			/*  $("#technologies1").val(fltrdTech).trigger('change');
			 
			 $("#technologies2").val(fltrdTech).trigger('change');
			 $("#technologies3").val(fltrdTech).trigger('change');
			 $("#technologies4").val(fltrdTech).trigger('change'); */
			 
			 
			 
			 $('#minValMSFltr').val('');
			 $('#maxValMSFltr').val('');
			 $('#minValStackedFltr').val('');
			 $('#maxValStackedFltr').val('');
			 $('#minValLineFltr').val('');
			 $('#maxValLineFltr').val('');
			 $('#minValRevPiesFltr').val('');
			 $('#maxValRevPiesFltr').val('');
			 $('#regMinValFltr').val('');
			 $('#regMaxValFltr').val('');
			 
           }
			


   });



		
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
	  				  
	  				  
	  				
	  		  $("#region").autocomplete({
	  				showHeader: true,
	  				
	  		        
	  		        source: function(request, response) {

	  		         
	  			             $.ajax({
	  			                 type: "GET",
	  			                 contentType: "application/json; charset=utf-8",
	  			                 url: '${pageContext.request.contextPath}/GetAllRegionsAutoComplete',
	  			                 data: {
	  			                	 "Region" : $("#region").val(),
	  									
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
	  			         }, minLength:0, maxShowItems: 100, scroll:true,		
	  		               
	  		        
	  				select: function(event, ui) {
	  					
	  					region.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
	  					
	  						return false;
	  							}
	  				    }).autocomplete("instance")._renderItem = function(ul, item) {

	  			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  			    	item[0]="No region name";
	  			    	item[1]="null";
	  			    	item[2]="null";
	  			    	item[3]="null";
	  			    	
	  		           
	  				    }
	  			            return $("<li class='each'>")
	  		                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	  			                    item[0] + "</span><br><span class='desc'>" +
	  			                    item[1] + "</span><span class='desc'>" +";"+
	  			                    item[2] + "</span></div>") .appendTo(ul);
	  			                    	};
	  		        	$("#region").focus(function(){
		  		        	if (this.value == ""){
		  		            	$(this).autocomplete("search");
		  		        	}						
		  			});

	  		        	$("#msRegName").focus(function(){
		                    	if (this.value == ""){
		                        	$(this).autocomplete("search");
		                    	}						
		            	});
		              $("#msRegName").autocomplete({
		            		showHeader: true,
		            		
		                    
		                    source: function(request, response) {

		                     
		            	             $.ajax({
		            	                 type: "GET",
		            	                 contentType: "application/json; charset=utf-8",
		            	                 url: '${pageContext.request.contextPath}/GetAllRegionAutocomplete',
		            	                 data: {
		            	                	 "region" : $("#msRegName").val(),
		            							
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
		            	         }, minLength:0, maxShowItems: 100, scroll:true,		
		                           
		                    
		            		select: function(event, ui) {
		            			
		            			msRegName.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
		            			
		            				return false;
		            					}
		            		    }).autocomplete("instance")._renderItem = function(ul, item) {

		            	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
		            	    	item[0]="No region name";
		            	    	item[1]="null";
		            	    	item[2]="null";
		            	    	item[3]="null";
		            	    	
		                       
		            		    }
		            	            return $("<li class='each'>")
		                            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		                    item[0] + "</span><br><span class='desc'>" +
		                    item[1] + "</span><span class='desc'>" +";"+
		                    item[2] + "</span></div>") .appendTo(ul);
		                    	};
		                    	
	  		        	$("#stackedRegName").focus(function(){
	  		            	if (this.value == ""){
	  		                	$(this).autocomplete("search");
	  		            	}						
	  		    	});
	  		      $("#stackedRegName").autocomplete({
	  		    		showHeader: true,
	  		    		
	  		            
	  		            source: function(request, response) {

	  		             
	  		    	             $.ajax({
	  		    	                 type: "GET",
	  		    	                 contentType: "application/json; charset=utf-8",
	  		    	                 url: '${pageContext.request.contextPath}/GetAllRegionAutocomplete',
	  		    	                 data: {
	  		    	                	 "region" : $("#stackedRegName").val(),
	  		    							
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
	  		    	         }, minLength:0, maxShowItems: 100, scroll:true,		
	  		                   
	  		            
	  		    		select: function(event, ui) {
	  		    			
	  		    			stackedRegName.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
	  		    			
	  		    				return false;
	  		    					}
	  		    		    }).autocomplete("instance")._renderItem = function(ul, item) {

	  		    	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  		    	    	item[0]="No region name";
	  		    	    	item[1]="null";
	  		    	    	item[2]="null";
	  		    	    	item[3]="null";
	  		    	    	
	  		               
	  		    		    }
	  		    	            return $("<li class='each'>")
	  		                    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	  				                    item[0] + "</span><br><span class='desc'>" +
	  				                    item[1] + "</span><span class='desc'>" +";"+
	  				                    item[2] + "</span></div>") .appendTo(ul);
	  				                    	};
	  		            	$("#lineRegName").focus(function(){
	  		                	if (this.value == ""){
	  		                    	$(this).autocomplete("search");
	  		                	}						
	  		        	});
	  		          $("#lineRegName").autocomplete({
	  		        		showHeader: true,
	  		        		
	  		                
	  		                source: function(request, response) {

	  		                 
	  		        	             $.ajax({
	  		        	                 type: "GET",
	  		        	                 contentType: "application/json; charset=utf-8",
	  		        	                 url: '${pageContext.request.contextPath}/GetAllRegionAutocomplete',
	  		        	                 data: {
	  		        	                	 "region" : $("#lineRegName").val(),
	  		        							
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
	  		        	         }, minLength:0, maxShowItems: 100, scroll:true,		
	  		                       
	  		                
	  		        		select: function(event, ui) {
	  		        			
	  		        			lineRegName.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
	  		        			
	  		        				return false;
	  		        					}
	  		        		    }).autocomplete("instance")._renderItem = function(ul, item) {

	  		        	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  		        	    	item[0]="No region name";
	  		        	    	item[1]="null";
	  		        	    	item[2]="null";
	  		        	    	item[3]="null";
	  		        	    	
	  		                   
	  		        		    }
	  		        	            return $("<li class='each'>")
	  		                        .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		  				                    item[0] + "</span><br><span class='desc'>" +
		  				                    item[1] + "</span><span class='desc'>" +";"+
		  				                    item[2] + "</span></div>") .appendTo(ul);
		  				                    	};
	  		                	
	  		            		$("#revPieRegName").focus(function(){
	  		                    	if (this.value == ""){
	  		                        	$(this).autocomplete("search");
	  		                    	}						
	  		            	});
	  		              $("#revPieRegName").autocomplete({
	  		            		showHeader: true,
	  		            		
	  		                    
	  		                    source: function(request, response) {

	  		                     
	  		            	             $.ajax({
	  		            	                 type: "GET",
	  		            	                 contentType: "application/json; charset=utf-8",
	  		            	                 url: '${pageContext.request.contextPath}/GetAllRegionAutocomplete',
	  		            	                 data: {
	  		            	                	 "region" : $("#revPieRegName").val(),
	  		            							
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
	  		            	         }, minLength:0, maxShowItems: 100, scroll:true,		
	  		                           
	  		                    
	  		            		select: function(event, ui) {
	  		            			
	  		            			revPieRegName.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
	  		            			
	  		            				return false;
	  		            					}
	  		            		    }).autocomplete("instance")._renderItem = function(ul, item) {

	  		            	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
	  		            	    	item[0]="No region name";
	  		            	    	item[1]="null";
	  		            	    	item[2]="null";
	  		            	    	item[3]="null";
	  		            	    	
	  		                       
	  		            		    }
	  		            	            return $("<li class='each'>")
	  		                            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	  		  				                    item[0] + "</span><br><span class='desc'>" +
	  		  				                    item[1] + "</span><span class='desc'>" +";"+
	  		  				                    item[2] + "</span></div>") .appendTo(ul);
	  		  				                    	};
	  		                    	
	  		                    	$("#regPieRegName").focus(function(){
		  		                    	if (this.value == ""){
		  		                        	$(this).autocomplete("search");
		  		                    	}						
		  		            	});
		  		              $("#regPieRegName").autocomplete({
		  		            		showHeader: true,
		  		            		
		  		                    
		  		                    source: function(request, response) {

		  		                     
		  		            	             $.ajax({
		  		            	                 type: "GET",
		  		            	                 contentType: "application/json; charset=utf-8",
		  		            	                 url: '${pageContext.request.contextPath}/GetAllRegionAutocomplete',
		  		            	                 data: {
		  		            	                	 "region" : $("#revPieRegName").val(),
		  		            							
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
		  		            	         }, minLength:0, maxShowItems: 100, scroll:true,		
		  		                           
		  		                    
		  		            		select: function(event, ui) {
		  		            			
		  		            			regPieRegName.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
		  		            			
		  		            				return false;
		  		            					}
		  		            		    }).autocomplete("instance")._renderItem = function(ul, item) {

		  		            	       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" &&item[3]=="null"){
		  		            	    	item[0]="No region name";
		  		            	    	item[1]="null";
		  		            	    	item[2]="null";
		  		            	    	item[3]="null";
		  		            	    	
		  		                       
		  		            		    }
		  		            	            return $("<li class='each'>")
		  		                            .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		  		  				                    item[0] + "</span><br><span class='desc'>" +
		  		  				                    item[1] + "</span><span class='desc'>" +";"+
		  		  				                    item[2] + "</span></div>") .appendTo(ul);
		  		  				                    	};
		  		                    	
		
	
$('.panel-collapse').on('show.bs.collapse', function () {
        
	    $(this).siblings('.panel-heading').removeClass('active');
	  });

	  $('.panel-collapse').on('hide.bs.collapse', function () {
	    $(this).siblings('.panel-heading').addClass('active');
	  });


			function split(val) {
					   return val.split( / \s*/ );
					}
			function extractLast(term) {
					   return split(term).pop();
					}
	

				
		

						

								$('#msChrtClrFltr').click(function(){
								      msColumnChartObj; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
		 				 			 $("#msStrtDate").val(s).trigger('change');
						 			 $("#msEndDate").val(enddate).trigger('change');
						 			 $("#msChngOpt").val('null').trigger('change');
						 			 $('#msRegName').val('').trigger('change');
						 			//document.querySelector('#technology').reset();
						 			 //$("#technologies1").val('null').trigger('change');		
						 			$("#minValMSFltr").val('');
						 			$("#maxValMSFltr").val('');
						 			$("#msRevOpt").val('voice_revenue').trigger('change');
						 			$("#msFltrDiv").hide();
		                			RegionRevenue(msColumnChartObj);

									});
								$('#stackedChrtClrFltr').click(function(){
									StackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			 $("#stackedStrtDate").val(s).trigger('change');
						 			 $("#stackedEndDate").val(enddate).trigger('change');
						 			$("#stackedChngOpt").val('null').trigger('change');
						 			$('#stackedRegName').val('').trigger('change');
						 			// $("#technologies2").val('null').trigger('change');
						 			$("#minValStackedFltr").val('');
						 			$("#maxValStackedFltr").val('');
						 			$("#stackedRevOpt").val('voice_revenue').trigger('change');
						 			$("#stackedFltrDiv").hide();
						 			
						 			fetchRevenueStacked(StackedandLineRevenue, $("#headerStrtDate").val(), $("#headerEndDate").val());

									
									});
								$('#lineChrtClrFltr').click(function(){
									StackedandLineRevenue; 

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			 $("#lineStrtDate").val(s).trigger('change');
						 			 $("#lineEndDate").val(enddate).trigger('change');
						 			$("#lineChngOpt").val('null').trigger('change');
						 			$('#lineRegName').val('').trigger('change');
						 			//$("#technologies3").val('null').trigger('change');
						 			$("#minValLineFltr").val('');
						 			$("#maxValLineFltr").val('');
						 			$("#lineRevOpt").val('voice_revenue').trigger('change');
						 			$("#lineFltrDiv").hide();
						 			
						 			fetchLineChartRevenue(StackedandLineRevenue);

									
									});
								$('#revPieClrFltr').click(function(){
									//chartData; 
								      //listRegionsClrFltr;
								      //msColumnChartObj;
								      pieRevenue;

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			 $("#revPieStrtDate").val(s).trigger('change');
						 			 $("#revPieEndDate").val(enddate).trigger('change');
											
						 			 $("#revPieChngOpt").val('null').trigger('change');
						 			 $('#revPieRegName').val('').trigger('change');
						 			 //$("#technologies4").val('null').trigger('change');
						 			$("#minValRevPiesFltr").val('');
						 			$("#maxValRevPiesFltr").val('');
						 			$("#revPieRevOpt").val('voice_revenue').trigger('change');
						 			$("#revPieFilterDiv").hide();
						 			//chart drawing
						 			PieRevenue(pieRevenue);
					  		 		

									
									});
								$('#regPieClrFltr').click(function(){
								      listRegionsClrFltr;

									var startdate = new Date();
									startdate.setDate(startdate.getDate() - 2);
									startdate.setHours( 0,0,0,0 );
									var s = Date.parse(startdate);
									
									var enddate = new Date();
									enddate.setDate(enddate.getDate() - 2);
									enddate = Date.parse(enddate);
						 			 $("#regPieStrtDate").val(s).trigger('change');
						 			 $("#regPieEndDate").val(enddate).trigger('change');
											
						 			 $("#regPieChngOpt").val('null').trigger('change');
						 			 $('#regPieRegName').val('').trigger('change');
						 			 //$("#technologies4").val('null').trigger('change');
						 			$("#regMinValFltr").val('');
						 			$("#regMaxValFltr").val('');
						 			$("#regPieRevOpt").val('voice_revenue').trigger('change');
						 			$("#regPieFilterDiv").hide();
						 			RegionalRevenuePie(listRegionsClrFltr);
					  		 		

									
									});

								

								
								$('#msChrtApply, #stackedChrtApply, #lineChrtApply, #revPieApply, #regPieApply').click(function(){
									 var selectedRevValue;
									 var technologies1,tech;
									 var selectTechBox;
									 var val1,val2;
									 var id = this.id;
									 var checkBox1 = document.getElementById("Max");
									 var checkBox2 = document.getElementById("Min");
										 
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
									 if (id == 'msChrtApply') {
										 stDate =  $("#msStrtDate").val();
										 endDate = $("#msEndDate").val();
										 regionName = ($('#msRegName').val().split(";"))[0];
										  selectBox = document.getElementById("msChngOpt");
											 selectedValue = selectBox.options[selectBox.selectedIndex].value;
											 selectRevBox = document.getElementById("msRevOpt");

											// selectTechBox = document.getElementById("technologies1");
											 technologies1 = null;//selectTechBox.options[selectTechBox.selectedIndex].value;
											  selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											// tech = ($('#technology').val().length == 0)? "null" :$('#technology').val() ;
											// technologies1 = retStringFrmArray(tech);
											 val1 =$('#minValMSFltr').val();
											 val2 =$('#maxValMSFltr').val();
											 
									    }
									 else if(id == 'stackedChrtApply'){
										 stDate =  $("#stackedStrtDate").val();
										 endDate = $("#stackedEndDate").val();
										 regionName = ($('#stackedRegName').val().split(";"))[0];
										  selectBox = document.getElementById("stackedChngOpt");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("stackedRevOpt");
										 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
										 //selectTechBox = document.getElementById("technologies2");
										 technologies1 = null;//selectTechBox.options[selectTechBox.selectedIndex].value;
										 val1 =$('#minValStackedFltr').val();
										 val2 = $('#maxValStackedFltr').val();
											 }
									 else if(id == 'lineChrtApply'){
										 stDate =  $("#lineStrtDate").val();
										 endDate = $("#lineEndDate").val();
										 regionName = ($('#lineRegName').val().split(";"))[0];
										  selectBox = document.getElementById("lineChngOpt");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("lineRevOpt");
											 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											 //selectTechBox = document.getElementById("technologies3");
											 technologies1 = null;//selectTechBox.options[selectTechBox.selectedIndex].value;
											 val1 =$('#minValLineFltr').val();
											 val2 = $('#maxValLineFltr').val();
											 }
									 else if(id == 'revPieApply'){
										 stDate =  $("#revPieStrtDate").val();
										 endDate = $("#revPieEndDate").val();
										 regionName = ($('#revPieRegName').val().split(";"))[0];
										  selectBox = document.getElementById("revPieChngOpt");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("revPieRevOpt");
											 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											 //selectTechBox = document.getElementById("technologies4");
											 technologies1 = null;//selectTechBox.options[selectTechBox.selectedIndex].value;
											 val1 =$('#minValRevPiesFltr').val();
											 val2 = $('#maxValRevPiesFltr').val();
													
											 }
									 else if(id == 'regPieApply'){
										 stDate =  $("#regPieStrtDate").val();
										 endDate = $("#regPieEndDate").val();
										 regionName = ($('#regPieRegName').val().split(";"))[0];
										  selectBox = document.getElementById("regPieChngOpt");
										  selectedValue = selectBox.options[selectBox.selectedIndex].value;
										  selectRevBox = document.getElementById("regPieRevOpt");
											 selectedRevValue = selectRevBox.options[selectRevBox.selectedIndex].value;
											 //selectTechBox = document.getElementById("technologies3");
											 technologies1 = null;//selectTechBox.options[selectTechBox.selectedIndex].value;
											 val1 =$('#regMinValFltr').val();
											 val2 = $('#regMaxValFltr').val();
											 }
								
										
						                if(selectedValue == "null" || (selectedValue == "isBetween" && val1 == '' && val2 == '')){
						    		$.ajax({
										type : "GET",
										contentType: "application/json; charset=utf-8",
										url : "${pageContext.request.contextPath}/FilteringRegionMethod",
										dataType : "json",
										data : {
										    "startDate" :stDate,
										    "endDate" : endDate,
										    "region": regionName,
						                    //"area": document.getElementById("area").value,
						                    //"technologyRegions": document.getElementById("Region").value,
						                   // "period": "Daily",//$("#period").children("option:selected").val(),
								            "Payment Method" : $("#Payment Method").children("option:selected").val(),
										    "technologies" :technologies1,
											"Max": maxChecked,
											"Min": minChecked,
						  					"filterID" : id, 		
											

										},
										success : function(data) {
											
											 if (data != null) {
												
												
												 
							                        	 console.log("*******Enter ");
							                        	 
							                        	 if (id == 'msChrtApply') {
							                        		 msColumnChartObj2=jQuery.parseJSON(data.msColumnChartObj);
									                		RegionRevenue(msColumnChartObj2);}
									                         else if(id == 'stackedChrtApply'){
									                        	 stDate =  $("#stackedStrtDate").val();
									                             endDate = $("#stackedEndDate").val();
																 StackedandLineRevenue2 = jQuery.parseJSON(data.StackedandLineRevenue);
									                        	 fetchRevenueStacked(StackedandLineRevenue2, stDate, endDate);}
									                         else if(id == 'lineChrtApply'){
																 StackedandLineRevenue2 = jQuery.parseJSON(data.StackedandLineRevenue);   
									                        	 fetchLineChartRevenue(StackedandLineRevenue2);
									                        	 }
									                         else if(id == 'revPieApply'){
									                        	 pieRevenue2=jQuery.parseJSON(data.pieRevenue);
									                        	 PieRevenue(pieRevenue2);
									                        	 }
									                         else if(id == 'regPieApply'){
									                        	 listRegions2=jQuery.parseJSON(data.listRegions);
									                        	 RegionalRevenuePie(listRegions2);
									                        	 }
											 }
							     					else{
							     						  alert("There is no data to display between these two selected dates for this region, or please check all the conditions selected in the upper side");
							     						   
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
												url : "${pageContext.request.contextPath}/FilteringRegionMethod",
												dataType : "json",
												data : {
												    "startDate" : stDate,
												    "endDate" : endDate,
												    "region": regionName,
								                   // "area": document.getElementById("area").value,
								                    //"technologyRegions": document.getElementById("Region").value,
								                    //"period": "Daily",//$("#period").children("option:selected").val(),
										            "Payment Method" : $("#Payment Method").children("option:selected").val(),
												    "technologies" :technologies1,
												    "selectedValue" :selectedValue,
												    "revenueOption" : selectedRevValue,
												    "minVal":(val1 == "")? 'null' : val1 ,
												    "maxVal":(val2 == "")? 'null' : val2,
													"Max": maxChecked,
													"Min": minChecked,
								  					"filterID" : id, 		
													

												},
												success : function(data) {
													
													 if (data != null) {
														 
														 
														 
														 
									                        	 console.log("*******Data Enter ");
									                        	 if (id == 'msChrtApply') {
									                        		 msColumnChartObj2=jQuery.parseJSON(data.msColumnChartObj);
											                			RegionRevenue(msColumnChartObj2);}
											                         else if(id == 'stackedChrtApply'){
											                        	 stDate =  $("#stackedStrtDate").val();
											                             endDate = $("#stackedEndDate").val();
											                             StackedandLineRevenue2=jQuery.parseJSON(data.StackedandLineRevenue);
											                        	 fetchRevenueStacked(StackedandLineRevenue2, stDate, endDate);}
											                         else if(id == 'lineChrtApply'){
											                        	 StackedandLineRevenue2=jQuery.parseJSON(data.StackedandLineRevenue);
											                        	 fetchLineChartRevenue(StackedandLineRevenue2);}
											                         else if(id == 'revPieApply'){
											                        	 pieRevenue2=jQuery.parseJSON(data.pieRevenue);
											                        	 PieRevenue(pieRevenue2);}
											                         else if(id == 'regPieApply'){
											                        	 listRegions2=jQuery.parseJSON(data.listRegions);
											                        	 RegionalRevenuePie(listRegions2);}
													 }
									     					else{
									     						  alert("There is no data to display between these two selected dates for this region, or please check all the conditions selected in the upper side");
									     						   
									     					}
									                         
										     					 

												

									  },
									  
									  error : function(error) {
											console.log("The error is " + error);
										}
								     });

							                }
								
									
									});
					


						
});
</script>

<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>