<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>SIM Agent Sales Report</title>
     <link rel="shortcut icon" href="">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
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
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
	
	    <!-- ALM GRID Scripts -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/pagination.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/almgridtable.class.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>
	    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
		
		<!--  Chart script -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.easypiechart.js"></script>
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
			
		<!-- SIM Agent sales report scripts -->
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Reports/SimAgentSalesReport.js"></script>
			
		<!-- Google Maps Script -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/Reports/SimAgentSalesGoogleMaps.js"></script>
	
	    <!-- export scripts -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf-1.5.3-jspdf.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/html2canvas-1.3.2-html2canvas.min.js"></script>
       
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
.dotPink {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#FF00FF;

}
.dotPurple {
height: 15px;
width: 15px;
border-radius: 50%;
display: inline-block;
background:#8A2BE2;

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

.noAccuChartsElement {
text-align:center;
font-family: "Lucida Console", "Courier New", monospace;
 font-size: 1.6em; 


}

</style>
<body>
<%--  <c:set var = "page" value = "report"/> --%>

<%-- 	<%@ include file="../header.html" %> --%>
  <c:set var="pg" value="report" scope="session"  />
 <%--<jsp:include page="header.jsp"></jsp:include>--%>
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
						<span class="input-group-text" style="color: green">SIM Agent Sales Report</span>
					</div>
				</div>
			</div>
				
			<div class="col-md-3">
            	<div class="form-group">
					<div class="input-group-prepend" id="datetimepicker3" data-target-input="nearest">
					<span class="input-group-text">Start Date</span>
					<input type="text" id="startdate1" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker3"   />
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
					<input type="text" id="enddate1" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker4"   />
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
		 			
		 			<div class="glyph" style="padding-top:0px;">
			 			 <div class="dropdown"  Style="margin-right:10px; height:30px;">
	                        <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Menu	</button>
	
	                       <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton" Style="width:10px;">
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
	  


<div class="row" >
				<div class="col-md-3">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Agent</span>
							<input type="text" id="agent"
								value="${agent}" class="form-control text-input" />
								
								 <div class="input-group-append" id="chooseAgentName"
                                        onclick="chooseAgentName()">
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
							<input type="text" id="area"
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
			
			
			
			<div class="row" >
			
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
			
			<div class="col-md-2">
		   <div class="form-group">
				<div class="input-group-prepend">
    	                
	<div id="Registration"></div>
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
  <table id="agentSales">
  
   <caption style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:-290px;left:20px;">Agent SIM Card Count</caption>
  
  
  <tr>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    <th style="position: relative;top: 5px;left:10px;"></th>
    
  </tr>
  
  <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="firstCountRange" onclick="ShowHideMarkers('#FFDC00');" value="#FFDC00"/></td>
    <td style="position: relative;top:30px;left:60px;"><div class="dot yellow"></div></td>
        
    
    <td style="position: relative;top: 25px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex; " >1 - 2</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="secondCountRange" onclick="ShowHideMarkers('#0080FF');" value="#0080FF"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot blue"></div></td>
    
    <td style="position: relative;top: 25px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex; " >3 - 5</label></td>
    
    
  </tr>
  
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="thirdCountRange"  onclick="ShowHideMarkers('red');" value="red"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot red"></div></td>
    
    <td style="position: relative;top: 25px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >6 - 10</label></td>
    
    
  </tr>
   <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="fourthCountRange"  onclick="ShowHideMarkers('#FF00FF');" value="#FF00FF"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot pink"></div></td>
    
    <td style="position: relative;top: 25px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >11 - 20 </label></td>
    
    
  </tr>
  
   <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox"  id="fifthCountRange"  onclick="ShowHideMarkers('#8A2BE2');" value="#8A2BE2"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot purple"></div></td>
    
    <td style="position: relative;top: 25px;left:78px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap;" >21 & Above </label></td>
    
    
  </tr>
    
    
     <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="maxCount" onclick="ShowHideMarkers('green');" value="green"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot green"></div></td>
            <td style="position: relative;top:30px;left:60px;"><div id="max-div" class="dot-max"></div></td>
    
    <td style="position: relative;top: 25px;left:-22px;"><label style="color:black;font-weight:bold;font-size:2ex;white-space: nowrap; " >Maximum Agent Sales</label></td>
    
    
  </tr>
    <tr>
     
     <td style="position: relative;top:17px;left:40px;"><input style="position: relative;top: 11px;" type="checkbox" name="legendCheckbox" id="minCount" onclick="ShowHideMarkers('#4D0207');" value="#4D0207"/></td>
        <td style="position: relative;top:30px;left:60px;"><div class="dot redDark"></div></td>
                <td style="position: relative;top:30px;left:60px;"><div id="min-div" class="dot-min"></div></td>
    
    <td style="position: relative;top: 25px;left:-22px;"><label style="color:black;font-weight:bold;font-size:2ex; white-space: nowrap;" >Minimum Agent Sales</label></td>
    
    
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
												
												<th>Agent ID 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>

												<th>Agent Name
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul class="dropdown-menu filter-dropdown-ul">

														</ul>
													</li>
												</th>
												
												<th>MSISDN
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

												<th>Region
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>

											
												<th>Total SIM Sales Count
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
											<th>Total Success SIM Registration 
													<li class="filter-dropdown dropdown">
														<button class="almgrid-filter" data-toggle="dropdown"> <i
																class="fa fa-list almgrid-filter-i"
																aria-hidden="true"></i></button>
														<ul
															class="dropdown-menu dropdown-menu-right filter-dropdown-ul">

														</ul>
													</li>
												</th>
												<th>Total Failed SIM Registration 
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
			

<div class="col-md-auto">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Total SIM Sales Count</span>
							<input readonly type="text" id="totalSimCardCount"
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
               
			<div class="container-fluid-responsive" >
			
				<div class="row" id="agent_count_chart" style="background:white !important ;border: 3px groove #FDFEFE;">
				
			<div class="col-md-12">
						

			<div class="card card-primary card-tabs cards-margin " >
			
		<!-- <a class="filter-dropdown dropdown"><button class ="buttonStyle" style="font-size: 20px; float:right; margin-top:-1px;" id="filter_chart_btn" class="almgrid-filter" > <i  class="fa fa-filter" style="border:none;"></i></button></a>  -->	
					<div class="card-body   ">
					

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group"  >
								
									
											<div class="card card-primary card-tabs cards-margin " id="chartContainerDiv">
											
												<!--  *************************** mschart tech  *************************** -->
												
	
											<div id="countChartContainer"  ></div>

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
																			<input type="text" id="startdate2" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker5"   />
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
																		  <input type="text" id="enddate2" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker6"   />
																			 <div class="input-group-append" data-target="#datetimepicker6" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
										  
																	  
																	<div style="font-weight: bold;">Agent </div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend">
																<input type="text" id="agentMsChart" value="${agentMsChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id="msChartOpenPopup" onclick="msChartOpenPopup()">
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
																		  
																	  <select id="countChartChangeOptions"  class="form-control text-input" onchange="changeFunc();">
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
																	  <div id="countChartFilterDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="countChartSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="SIM_Sales"> SIM Sales</option>
																	 
														
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="countChartTxtBtn" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValMSFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValMSFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																			<!-- -->
											
																  							  
														    	                
															 
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="countChartClearFilter">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="countChartApplyFilter">Apply</button>	
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
			
				<div class="row" id="time_count_stack" style="background:white !important ;border: 3px groove #FDFEFE;">
				
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
																			<input type="text" id="startdate3" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker7"   />
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
																		  <input type="text" id="enddate3" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker8"   />
																			 <div class="input-group-append" data-target="#datetimepicker8" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
																  
																    
																	<div style="font-weight: bold;">Agent </div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend">
																<input type="text" id="agentStackChart" value="${agentStackChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id="stackChartOpenPopup" onclick="stackChartOpenPopup()">
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
																		  
																	  <select id="stackedChartChangeOptions"  class="form-control text-input" onchange="changeFunc();">
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="stackedChartFilterDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="stackedChartSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="SIM_SALES"> SIM Sales</option>
																	 
															
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="stackedChartTxtBtn" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValStackedFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValStackedFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="stackedChartClearFilter">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="stackedChartApplyFilter">Apply</button>	
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
			
				<div class="row" id="maxTimeCountStack" style="background:white !important ;border: 3px groove #FDFEFE;">
				
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
												
	
											<div id="maxChartContainerStacked"  ></div>

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
																			<div class="input-group-prepend" id="datetimepicker15" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdate7" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker15"   />
																			   <div class="input-group-append" data-target="#datetimepicker15" data-toggle="datetimepicker">
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
																		  <div class="input-group-prepend" id="datetimepicker16" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddate7" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker16"   />
																			 <div class="input-group-append" data-target="#datetimepicker16" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Agent</span>
																<input type="text" id="maxStackedChartAgentID" value="${maxStackedChartAgentID}" class="form-control text-input" style="margin-bottom:20px;" /></div>

															 <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="maxStackedChartChangeOptions"  class="form-control text-input" onchange="changeFunc();" >
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="maxStackedChartFilterDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="maxStackedChartSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;" >
																	 <option value="SIM_SALES"> SIM Sales</option>
																	 
															
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="maxStackedChartTxtBtn" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValMaxStackedFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValMaxStackedFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="maxStackedChartClearFilter">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="maxStackedChartApplyFilter">Apply</button>	
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
		
					
	<div class="row" id="minTimeCountStack" style="background:white !important ;border: 3px groove #FDFEFE;">
				
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
												
	
											<div id="minChartContainerStacked"  ></div>

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
																			<div class="input-group-prepend" id="datetimepicker17" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdate8" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker17"   />
																			   <div class="input-group-append" data-target="#datetimepicker17" data-toggle="datetimepicker">
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
																		  <div class="input-group-prepend" id="datetimepicker18" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddate8" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker18"   />
																			 <div class="input-group-append" data-target="#datetimepicker18" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
														   <div >
													          <span><b>Agent</span>
																<input type="text" id="minStackedChartAgentID" value="${minStackedChartAgentID}" class="form-control text-input" style="margin-bottom:20px;" /></div>

															 <div style="font-weight: bold;">Filter options</div> 
																	  <div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="minStackedChartChangeOptions"  class="form-control text-input" onchange="changeFunc();" >
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="minStackedChartFilterDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="minStackedChartSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;" >
																	 <option value="SIM_SALES"> SIM Sales</option>
																	 
															
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="minStackedChartTxtBtn" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValMinStackedFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValMinStackedFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="minStackedChartClearFilter">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="minStackedChartApplyFilter">Apply</button>	
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
			
				<div class="row" id="time_count_line" style="background:white !important ;border: 3px groove #FDFEFE;" >
				
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
																			<input type="text" id="startdate4" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker9"   />
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
																		  <input type="text" id="enddate4" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker10"   />
																			 <div class="input-group-append" data-target="#datetimepicker10" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
																  
																  <div style="font-weight: bold;">Agent </div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group">
																		  <div class="input-group-prepend">
																<input type="text" id="agentLineChart" value="${agentLineChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id="lineChartOpenPopup" onclick="lineChartOpenPopup()">
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
																		  
																	  <select id="lineChartChangeOptions"  class="form-control text-input" onchange="changeFunc();" >
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="lineChartFilterDiv">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="lineChartSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;">
																	 <option value="SIM_SALES"> SIM Sales</option>
																	 
																	
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="lineChartTxtBtn" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValLineFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValLineFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																 
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex">													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN"  id="lineChartClearFilter">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="lineChartApplyFilter">Apply</button>	
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
			
		<div class="row" id="maxTimeCountLine" style="background:white !important ;border: 3px groove #FDFEFE;" >
				
			<div class="col-md-12">
			<div class="card card-primary card-tabs cards-margin " >
				<div class="card-body ">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" role="tabpanel" >
								<div class="card-group"  >
								<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
								id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
																				
							<div id="maxLineChartContainer" ></div>

							</div>
							<!--  *************************** filter for chart  *************************** -->
							<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;"> 
							
							<div id="filterChart" class= "canvas-style2" >
							<!-- startdate -->
							<div style="font-weight: bold;margin-top:10px;">Start Date</div>
							<div class="row">
							<div class="col-md-12">
							<div class="form-group">
							<div class="input-group-prepend" id="datetimepicker19" data-target-input="nearest">
							<input type="text" id="startdate9" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker19"   />
							 <div class="input-group-append" data-target="#datetimepicker19" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i></div></div>
							</div></div></div>
							</div>
							
							<!-- enddate -->
							<div style="font-weight: bold;">End Date</div>
							<div class="row">
							<div class="col-md-12">
							<div class="form-group">
							<div class="input-group-prepend" id="datetimepicker20" data-target-input="nearest">
							 <input type="text" id="enddate9" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker20"   />
							<div class="input-group-append" data-target="#datetimepicker20" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i></div> </div>
							 </div>
							 </div></div></div>
							 <div >
							 
							 <span><b>Agent</span>
							 <input type="text" id="maxLineChartAgentID" value="${maxLineChartAgentID}" class="form-control text-input" style="margin-bottom:20px;" />															  </div>

							<div style="font-weight: bold;">Filter options</div> 
							 <div class="row">
							  <div class="col-md-12">
							   <select id="maxLineChartChangeOptions"  class="form-control text-input" onchange="changeFunc();" >
								<option value="null"></option>
								<option value="isBetween">In between</option>
								</select>
								</div>
								</div>&nbsp;
								
								 <div id="maxLineChartFilterDiv">
								 <div class="row">
								 <div class="col-md-12">
								 <select id="maxLineChartSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;" >
									<option value="SIM_SALES"> SIM Sales</option>
								</select>
								</div>
								</div>
								<div class="row" style="margin-bottom:5px;">
								<div class="col-md-12">
								<span id="maxLineChartTxtBtn" style="display:none;"><b>Insert your values:</span>
								</div></div>
								<div class="row">
								<input type="text"  class="form-control text-input" id="minValMaxLineFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
								<input type="text"  class="form-control text-input" id="maxValMaxLineFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
								</div></div>
								<!-- apply button -->
								
								<div class="row flex">
								<div class="flex-item" >
								<button type="button" class="btn btn-primary BTN"  id="maxLineChartClearFilter">Clear All</button>	
								</div>
								<div class="flex-item" >
								<button type="button" class="btn btn-primary BTN advanced-filter-submit" id="maxLineChartApplyFilter">Apply</button>	
								</div>
								</div>
								</div> 
								
								<!-- </div> -->
								</div> 
								
						<!--  ***************************  end filter for chart  *************************** -->
						</div>
						</div></div></div>
		
					</div>
		
				</div>
				</div>
		
		<br>
			
		<div class="row" id="minTimeCountLine" style="background:white !important ;border: 3px groove #FDFEFE;" >
				
			<div class="col-md-12">
			<div class="card card-primary card-tabs cards-margin " >
				<div class="card-body ">
						<div class="tab-content" id="custom-tabs-one-tabContent">
							<div class="tab-pane fade show active" role="tabpanel" >
								<div class="card-group"  >
								<div class="tab-pane fade show active card card-primary card-tabs cards-margin "
								id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
																				
							<div id="minLineChartContainer" ></div>

							</div>
							<!--  *************************** filter for chart  *************************** -->
							<div class="col-md-3 "  id="filter_Chart_Sites" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC);;border: 1px groove #FDFEFE;"> 
							
							<div id="filterChart" class= "canvas-style2" >
							<!-- startdate -->
							<div style="font-weight: bold;margin-top:10px;">Start Date</div>
							<div class="row">
							<div class="col-md-12">
							<div class="form-group">
							<div class="input-group-prepend" id="datetimepicker21" data-target-input="nearest">
							<input type="text" id="startdate10" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker21"   />
							 <div class="input-group-append" data-target="#datetimepicker21" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i></div></div>
							</div></div></div>
							</div>
							
							<!-- enddate -->
							<div style="font-weight: bold;">End Date</div>
							<div class="row">
							<div class="col-md-12">
							<div class="form-group">
							<div class="input-group-prepend" id="datetimepicker22" data-target-input="nearest">
							 <input type="text" id="enddate10" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker22"   />
							<div class="input-group-append" data-target="#datetimepicker22" data-toggle="datetimepicker">
							<div class="input-group-text">
								<i class="fa fa-calendar"></i></div> </div>
							 </div>
							 </div></div></div>
							 <div >
							 
							 <span><b>Agent</span>
							 <input type="text" id="minLineChartAgentID" value="${minLineChartAgentID}" class="form-control text-input" style="margin-bottom:20px;" />															  </div>

							<div style="font-weight: bold;">Filter options</div> 
							 <div class="row">
							  <div class="col-md-12">
							   <select id="minLineChartChangeOptions"  class="form-control text-input" onchange="changeFunc();" >
								<option value="null"></option>
								<option value="isBetween">In between</option>
								</select>
								</div>
								</div>&nbsp;
								
								 <div id="minLineChartFilterDiv">
								 <div class="row">
								 <div class="col-md-12">
								 <select id="minLineChartSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;" >
									<option value="SIM_SALES"> SIM Sales</option>
								</select>
								</div>
								</div>
								<div class="row" style="margin-bottom:5px;">
								<div class="col-md-12">
								<span id="minLineChartTxtBtn" style="display:none;"><b>Insert your values:</span>
								</div></div>
								<div class="row">
								<input type="text"  class="form-control text-input" id="minValMinLineFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
								<input type="text"  class="form-control text-input" id="maxValMinLineFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
								</div></div>
								<!-- apply button -->
								
								<div class="row flex">
								<div class="flex-item" >
								<button type="button" class="btn btn-primary BTN"  id="minLineChartClearFilter">Clear All</button>	
								</div>
								<div class="flex-item" >
								<button type="button" class="btn btn-primary BTN advanced-filter-submit" id="minLineChartApplyFilter">Apply</button>	
								</div>
								</div>
								</div> 
								
								<!-- </div> -->
								</div> 
								
						<!--  ***************************  end filter for chart  *************************** -->
						</div>
						</div></div></div>
		
					</div>
		
				</div>
				</div>
				
		<br>
		
		
		<div class="row" id="countPieChart" style="background:white !important ;border: 3px groove #FDFEFE; " >
			<div class="col-md-12">
			
			<div class="card card-primary card-tabs cards-margin ">
					<div class="card-body ">
					
					<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group" style="border: 3px groove #FDFEFE;">
								
									<div class="col-md-6 " >
										<div class="card card-primary card-tabs " >
										
										<div class="card-body " >	
												<div class="tab-content" id="custom-tabs-one-tabContent">
												<div class="tab-pane fade show active"
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab"  >
											<div id="countChartPie" style= "height:400px; "></div>
											
											
											
											
											</div>
											</div>
											</div>
										
										
										
											</div>
									</div>
									
									
			<div class="col-md-6 " >
			<div   id="filter_Chart_Piez" style=" background-image: linear-gradient(to right, #EEEEEE, #CCCCCC); width:93%; margin-left:20px; ">
			<div id="filterChartPiez"  >
			<div style="font-weight: bold;margin-top:20px; " class="pos">Pie Charts Filter</div>	
			<!-- startdate -->
			
			 <div style="font-weight: bold;margin-left:50px;margin-right:50px;">Start Date</div>
				<div class="row">
				<div class="col-md-12">
				<div class="form-group" style="margin-left:50px;margin-right:50px;">
					<div class="input-group-prepend" id="datetimepicker11" data-target-input="nearest">
						<!-- <div class="input-group-text">Start Date</div> -->
				<input type="text" id="startdate5" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker11"   />
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
							<input type="text" id="enddate5" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker12"   />
							<div class="input-group-append" data-target="#datetimepicker12" data-toggle="datetimepicker">
							<div class="input-group-text">
							<i class="fa fa-calendar"></i>
							</div>
							
							</div>
							</div>
							</div>
							</div>
							</div>
				
					<div style="font-weight: bold;margin-left:50px;margin-right:50px;">Agents</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group" style="margin-left:50px;margin-right:50px;">
																		  <div class="input-group-prepend">
																<input type="text" id="agentPieChart" value="${agentPieChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id=pieChartOpenPopup onclick="pieChartOpenPopup()">
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
				<select id="countPieChangeOptions" style="margin-right:50px;margin-left:50px;width:82%;"  class="form-control text-input" onchange="changeFunc();" disabled >
					<option value="null"></option>
					<option value="isBetween">In between</option>
				</select>
				</div>
				</div>&nbsp;
				  <div id="countPieFilterDiv" style="margin-right:50px;margin-left:50px;">
					<div class="row">													  
					<div class="col-md-12">
					<select id="countPieSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;" disabled>
					<option value="SIM_SALES"> SIM Sales</option>
					
					</select>
					</div>
					</div>
					<div class="row" style="margin-bottom:5px;">
					<div class="col-md-12">
					<span id="countPieTxtBtn" style="display:none;"><b>Insert your values:</span>
						</div>
						</div>
						
						<div class="row">
						<input type="text"  class="form-control text-input" id="minValPiesFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
						<input type="text"  class="form-control text-input" id="maxValPiesFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
						</div>
					</div>
																		
			
																			  
				
				 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex" >													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="countPieClearFilter">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="countPieApplyFilter">Apply</button>	
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
		
		<div class="row" id="regStatusPieChart" style="background:white !important ;border: 3px groove #FDFEFE;" >
			<div class="col-md-12">
			
			<div class="card card-primary card-tabs cards-margin ">
					<div class="card-body ">

						<div class="tab-content" id="custom-tabs-one-tabContent">

							<div class="tab-pane fade show active" role="tabpanel" >

								<div class="card-group" style="border: 3px groove #FDFEFE;">
								
									
									<div class="col-md-6 " >
										<div class="card card-primary card-tabs "  >
											
												<!--  *************************** piechart chart tech  *************************** -->
												
											<div class="card-body " >	
												<div class="tab-content" id="custom-tabs-one-tabContent">
												<div class="tab-pane fade show active"
													id="custom-tabs-one-home" role="tabpanel"
													aria-labelledby="custom-tabs-one-home-tab">
											<div id="clientRegStatChartContainer" style= "height:480px; "  ></div>
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
																			<div class="input-group-prepend" id="datetimepicker13" data-target-input="nearest">
																				<!-- <div class="input-group-text">Start Date</div> -->
																			<input type="text" id="startdate6" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker13"   />
																			   <div class="input-group-append" data-target="#datetimepicker13" data-toggle="datetimepicker">
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
																		  <div class="input-group-prepend" id="datetimepicker14" data-target-input="nearest">
																			  <!-- <div class="input-group-text">End Date</div> -->
																		  <input type="text" id="enddate6" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker14"   />
																			 <div class="input-group-append" data-target="#datetimepicker14" data-toggle="datetimepicker">
																				  <div class="input-group-text">
																					  <i class="fa fa-calendar"></i>
																				  </div>
																			  </div>
																		  </div>
																	  </div>
																	</div>
																  </div>
																  
																  	<div style="font-weight: bold;margin-left:50px;margin-right:50px;">Agents</div>
																	<div class="row">
																	<div class="col-md-12">
																	  <div class="form-group" style="margin-left:50px;margin-right:50px;">
																		  <div class="input-group-prepend">
																<input type="text" id="agentRegStatusChart" value="${agentRegStatusChart}" class="form-control text-input" style="margin-bottom:20px;" />	
																																			
																			 <div class="input-group-append" id=regStatusChartOpenPopup onclick="regStatusChartOpenPopup()">
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
																		  
																	  <select id="regStatusPiechangeOptions" style="margin-right:50px;margin-left:50px;width:82%;"  class="form-control text-input" onchange="changeFunc();" disabled>
																	 <option value="null"></option>
																	 
																	  <option value="isBetween">In between</option>
																	 
																	  </select>
																	  
																	  </div>
																	  </div>&nbsp;
																	  <div id="regStatusPieFilterDiv" style="margin-right:50px;margin-left:50px;">
																	<div class="row">													  
																	  <div class="col-md-12">
																		  
																	  <select id="regStatusPieSimSalesOpt"  class="form-control text-input" onchange="changeSalesFunc();" style="display:none;margin-bottom:20px;" disabled>
																	 <option value="SIM_SALES"> SIM Sales</option>
																	 
																	  
																	  </select>
																	  
																	  </div>
																	  </div>
																		<div class="row" style="margin-bottom:5px;">
																		<div class="col-md-12">
																		<span id="regStatusPieTxtBtn" style="display:none;"><b>Insert your values:</span>
																		</div>
																		</div>
																		<div class="row">
																			<input type="text"  class="form-control text-input" id="minValRegStatFltr" placeholder="Min:" style="display:none;width:100px; margin-bottom:20px;margin-left:16px;" title="Not mandatory to insert a minimum value, keeping it empty bring all the lowest values"><!-- display:none; -->
														
																			<input type="text"  class="form-control text-input" id="maxValRegStatFltr" placeholder="Max:" style="display:none;width:100px; margin-bottom:20px;margin-left:53px;" title="Not mandatory to insert a maximum value, keeping it empty bring all the highest values">
																			
																		</div>
																		</div>
																  <!-- Options for the multidrop down for Registration -->
																  <div style="font-weight: bold;margin-right:50px;margin-left:50px;">Registration</div>
																  <div class="form-group" style="margin-right:50px;margin-left:50px;">
																	<div class="input-group-prepend">
																			  
																		 <select id="regType" class="form-control text-input" style="height:37px">
																		  <option value="" selected>Via IP - USSD</option>
																		   <option value="SENT_USSD" >Via USSD</option>
																		   <option value="USSD">Via IP</option>
																		  
																		 
																		   
																		</select>
																	</div>
																		
															 </div>
															 
															 <!-- apply button -->
															 <!-- <button onclick="filterChart()" >Filter Chart</button> -->
															 <div class="row flex" >													  
															 
															 <div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN " id="regStatPieClearFilter">Clear All</button>	
															</div>
													 		<div class="flex-item" >
															 <button type="button" class="btn btn-primary BTN advanced-filter-submit" id="regStatPieApplyFilter">Apply</button>	
															</div>
															</div>

															</div></div>
				<!--  ***************************  end filter for chart  *************************** -->
						</div></div></div>
						</div></div>
		</div>
		
		</div>
		</div>
		<br>
		<div id="noAccuCharts"></div>
		
		</div>
		</div>
	</div>
	<!-- end of charts-->
        
        </div>
       
      </div>
      </div>
    </div>
  </div>
</div>
	
		</div>
<div class="container-fluid" >     
		
	
 <!-- Agent Name Modal -->
            <div class="modal fade" id="autocompleteModal" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Agents Name</h5>
                            <button type="button" class="close" onclick="popupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Agents Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;" name="tags" data-role="tagsinput" id="agentsAutocomplete" /> 
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
            
  <!-- RegStatus Pie Chart Agent Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="regStatusChartPopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Agents</h5>
                            <button type="button" class="close" onclick="regStatusChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Agents Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="regStatusChartAgentAutocomplete" /> 
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
            
    <!-- MS Chart Agent Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="msChartPopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Agents</h5>
                            <button type="button" class="close" onclick="msChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Agents Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="msChartAgentAutocomplete" /> 
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
 <!-- Line Chart Agent Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="lineChartPopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Agents</h5>
                            <button type="button" class="close" onclick="lineChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Agents Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="lineChartAgentAutocomplete" /> 
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
 <!-- Pie Chart Agent Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="pieChartPopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Agents</h5>
                            <button type="button" class="close" onclick="pieChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Agents Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="pieChartAgentAutocomplete" /> 
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
      <!-- Stack Chart Agent Name Popup -->
<div class="container-fluid" >     
            <div class="modal fade" id="stackChartPopup" tabindex="-1" role="dialog" aria-labelledby="autocompleteModalLabel"
                aria-hidden="true">  
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content" style="width:700px !important;">
                        <div class="modal-header" style="background-color: #FF4F4F;">
                            <h5 class="modal-title" style="font-weight:bold; color: #3C1596;position:relative;top:4px;" >Agents</h5>
                            <button type="button" class="close" onclick="stackChartPopupClose()" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" style="background-color: #08526d;color:white;">Agents Name</span>
                                    
                                </div>
                            </div>

                        </div>
                         <div class="row">
                            <div class="col-sm-6 ">
                                <div class="input-group-prepend">
                                <div class="tags-input" id="myTags">
                                 <span class="tag">
                                 <input class="form-control text-input" style="margin-top:5px;"  data-role="tagsinput" id="stackChartAgentAutocomplete" /> 
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

<script >

agentSalesCountList=[];
var listAgentsClrFltr;

function initMap() {
	
	agentSalesCountList = ${agentSalesCountList};
	//RenderpiechartFunction
	
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
	    simCountSetColor(agentSalesCountList,map);

		//Add legend button under zoom control on map
		const centerControlDiv = document.createElement("div");
	    LegendControl(centerControlDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map);
	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	    const maxCountControlDiv = document.createElement("div");
	    MaxAgentSalesControl(maxCountControlDiv, map);
	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxCountControlDiv);

	    const minCountControlDiv = document.createElement("div");
	    MinAgentSalesControl(minCountControlDiv, map);
	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minCountControlDiv);
	    


	    $("#legendDiv").toggle();

}//end initMap 

function chooseAgentName() {
    $('#autocompleteModal').modal('show');
 }//end chooseAgentName fct

 
 function msChartOpenPopup() {
	    $('#msChartPopup').modal('show');
}
 function regStatusChartOpenPopup() {
	    $('#regStatusChartPopup').modal('show');
}
 function stackChartOpenPopup() {
	    $('#stackChartPopup').modal('show');
}

 function lineChartOpenPopup() {
	    $('#lineChartPopup').modal('show');
}

 function pieChartOpenPopup() {
	    $('#pieChartPopup').modal('show');
}
 function regStatusChartPopupClose() {
		var allAgents = document.getElementById("regStatusChartAgentAutocomplete").value;
		
		var agentInput = document.getElementById("agentRegStatusChart").value;
		agentInput = agentInput.split(";");
		agentInput  = agentInput[2];

		//Check if siteInput exist in text area
		if(allAgents.includes(agentInput) == false ) {
		document.getElementById("agentRegStatusChart").value ="";
		
	}
	    $('#regStatusChartPopup').modal('hide');
		
	} // end popupClose fct

 function pieChartPopupClose() {
		var allAgents = document.getElementById("pieChartAgentAutocomplete").value;
		
		var agentInput = document.getElementById("agentPieChart").value;
		agentInput = agentInput.split(";");
		agentInput  = agentInput[2];

		//Check if siteInput exist in text area
		if(allAgents.includes(agentInput) == false ) {
		document.getElementById("agentPieChart").value ="";
		
	}
	    $('#pieChartPopup').modal('hide');
		
	} // end popupClose fct

	
 function lineChartPopupClose() {
		var allAgents = document.getElementById("lineChartAgentAutocomplete").value;
		
		var agentInput = document.getElementById("agentLineChart").value;
		agentInput = agentInput.split(";");
		agentInput  = agentInput[2];

		//Check if siteInput exist in text area
		if(allAgents.includes(agentInput) == false ) {
		document.getElementById("agentLineChart").value ="";
		
	}
	    $('#lineChartPopup').modal('hide');
		
	} // end popupClose fct

 function stackChartPopupClose() {
		var allAgents = document.getElementById("stackChartAgentAutocomplete").value;
		
		var agentInput = document.getElementById("agentStackChart").value;
		agentInput = agentInput.split(";");
		agentInput  = agentInput[2];

		//Check if siteInput exist in text area
		if(allAgents.includes(agentInput) == false ) {
		document.getElementById("agentStackChart").value ="";
		
	}
	    $('#stackChartPopup').modal('hide');
		
	} // end popupClose fct

 function msChartPopupClose() {
		var allAgents = document.getElementById("msChartAgentAutocomplete").value;
		
		var agentInput = document.getElementById("agentMsChart").value;
		agentInput = agentInput.split(";");
		agentInput  = agentInput[2];

		//Check if siteInput exist in text area
		if(allAgents.includes(agentInput) == false ) {
		document.getElementById("agentMsChart").value ="";
		
	}
	    $('#msChartPopup').modal('hide');
		
	} // end popupClose fct

	
function popupClose() {
	var allAgents = document.getElementById("agentsAutocomplete").value;
	
	var agentInput = document.getElementById("agent").value;
	agentInput = agentInput.split(";");
	agentInput  = agentInput[2];

	//Check if siteInput exist in text area
	if(allAgents.includes(agentInput) == false ) {
	document.getElementById("agent").value ="";
	
}
    $('#autocompleteModal').modal('hide');
	
} // end popupClose fct


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
				setInputFilter(document.getElementById("minValMaxStackedFltr"), function(value) {
					  return /^-?\d*[.,]?\d*$/.test(value); });
				setInputFilter(document.getElementById("maxValMaxStackedFltr"), function(value) {
					  return /^-?\d*[.,]?\d*$/.test(value); });
				setInputFilter(document.getElementById("minValMaxLineFltr"), function(value) {
					  return /^-?\d*[.,]?\d*$/.test(value); });
					setInputFilter(document.getElementById("maxValMaxLineFltr"), function(value) {
					  return /^-?\d*[.,]?\d*$/.test(value); });
					setInputFilter(document.getElementById("minValMinLineFltr"), function(value) {
						  return /^-?\d*[.,]?\d*$/.test(value); });
						setInputFilter(document.getElementById("maxValMinLineFltr"), function(value) {
						  return /^-?\d*[.,]?\d*$/.test(value); });
						
				
				

		  		      
								  		        	


		$("#area").autocomplete({
			showHeader: true,			  
			source: function(request, response) {

				var agent = $("#agent").val();
				var agentID ="";
				if(agent == ""){
					 agentID  ="";
				  }
				  else {
						
					  agentID = agent.split(";");
					  agentID  = agentID[0];
				  }
				var region = $("#Region").val();
				var regionID ="";
				if(region == ""){
					regionID  ="";
				  }
				  else {
						
					  regionID = region.split(";");
					  regionID  = regionID[0];
				  }
				  
				$.ajax({
					type: "GET",
					contentType: "application/json; charset=utf-8",
					url: '${pageContext.request.contextPath}/GetAgentAreaAutocomplete',
					data: {
						"areaID":$("#area").val(),
						"agentID" :agentID,
						"regionID":regionID,
							
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
					area.value = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
					return false;
				}
				}).autocomplete("instance")._renderItem = function(ul, item) {
					return $("<li class='each'>")
					.append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					item[0] + "</span><br><span class='desc'>" +
					item[1]  +"</span></div>") 
					.appendTo(ul);
					};

					$("#area").focus(function(){
						if (this.value == ""){
							$(this).autocomplete("search");
							}						
					});

	$("#Region").autocomplete({
		showHeader: true,
		source: function(request, response) {
			
			var agent = $("#agent").val();
			var agentID ="";
			if(agent == ""){
				 agentID  ="";
			  }
			  else {
					
				  agentID = agent.split(";");
				  agentID  = agentID[0];
			  }
			var area = $("#area").val();
			var areaID ="";
			if(area == ""){
				areaID  ="";
			  }
			  else {
					
				  areaID = area.split(";");
				  areaID  = areaID[0];
			  }
			  $.ajax({
				  type: "GET",
				  contentType: "application/json; charset=utf-8",
				  url: '${pageContext.request.contextPath}/GetAgentRegionAutocomplete',
					 data: {
						 "Region" :$("#Region").val(),
						 "areaID":areaID,
						 "agentID":agentID,
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
			
									
									 
var date = new Date();
date.setDate(date.getDate() );


date.setHours( 0,0,0,0 );

var dateend = new Date();
dateend.setDate(dateend.getDate());

var c = Date.parse(date);
for(var i = 1; i<=10;i++){
	$('#startdate'+i).datetimepicker({
	    defaultDate:c
	});



	$('#enddate'+i).datetimepicker({
	    defaultDate:dateend
	});
}
$(document).ready(function() {


	// Changing The startdate and enddate when choosing weekly or  monthly or daily
	$('#period').change(function() {

		var startDate;
		var a; var d;
	    if ($(this).val() === 'Weekly') {
	        
	    	 startDate = $("#enddate1").val();
	        d = new Date(startDate);
	    	d.setDate(d.getDate() - 7);
	    	d.setHours( 0,0,0,0 );
	        a=d.toLocaleString();
	    	$("#startdate1").val(a).trigger('change');
	      	
	    }

	    else if ($(this).val() === 'Daily') {
	        
	   	 startDate = $("#enddate1").val();
	       d = new Date(startDate);
	       d.setHours( 0,0,0,0 );
	       a=d.toLocaleString();
	   	$("#startdate1").val(a).trigger('change');
	     	
	   }
	    else if ($(this).val() === 'Monthly') {
	    	 startDate = $("#enddate1").val();
	    	 d = new Date(startDate);
	    	 d.setMonth(d.getMonth() - 3);
	    	 d.setHours( 0,0,0,0 );
	    	 a=d.toLocaleString();
	    	$("#startdate1").val(a).trigger('change');
	    	
	    }

	}); // END period onChange fct
	
	$('#clearButton'). click(function(){  
        document.getElementById('agent').value = '';
        document.getElementById('area').value = '';
        document.getElementById('Region').value = '';
        $("#agentsAutocomplete").tagsinput('removeAll');

	}); // end clear fct

	// Options for the multidrop down
	
	 var servicesOptions = [
		  { label: 'PayG', value: 'payg' },
		  { label: "Bundle", value: 'bundle'},
		 
		];
	 
	 VirtualSelect.init({
		  ele: '#services',
		  options: servicesOptions,
		  multiple: true,
		  placeholder: 'Services'
		});
		
	 var prepostPaidOptions = [
		  { label: 'Prepaid', value: 'prepaid' },
		  { label: "Postpaid", value: 'postpaid'},
		 
		];
	 VirtualSelect.init({
		  ele: '#Prepostpaid',
		  options: prepostPaidOptions,
		  multiple: true,
		  placeholder: 'Pre/Post Paid'
		});

	 var regStatusOptions = [
		  { label: 'USSD', value: 'sentUSSD' },
		  { label: "Via IP", value: 'IP'},
		 
		];
	 VirtualSelect.init({
		  ele: '#Registration',
		  options: regStatusOptions,
		  multiple: true,
		  placeholder: 'SIM Registartion'
		});
// end options for the multidrop down




			//agent autocomplete with multiselect	
				$("#agent").autocomplete({
				showHeader: true,
				
				// without this focus fct: keyboard movements reset the input to ''
				  focus: function( event, ui ) {
				        event.preventDefault(); 
				        if (event.Keycode ==38||event.Keycode ==40 ) {
				        	var terms = split($("#agentsAutocomplete").val());
						        terms.pop();
						        terms.push( ui.item[1]);
						        selectedItems.push(terms);
						        txt=terms;
						        terms.push("");

				        //$("#sitesAutocomplete").val(terms.join( "," ));	
				        var agentInput=agent.value;
					    var agentNameInp = agentInput.split(";");
					    agentNameInp  = agentNameInp[2]+" ";
					    
				       $("#agentsAutocomplete").tagsinput('add', agentNameInp.toString() );	
				    		
				     }
				  }, // end of focus fct
	
		        source: function(request, response) {

		        	var area = $("#area").val();
					var areaID ="";
					if(area == ""){
						areaID  ="";
					  }
					  else {
							
						  areaID = area.split(";");
						  areaID  = areaID[0];
					  }

					var region = $("#Region").val();
					var regionID ="";
					if(region == ""){
						regionID  ="";
					  }
					  else {
							
						  regionID = region.split(";");
						  regionID  = regionID[0];
					  }
					  
			        
			        //Extract last agent name from input field
		          var searchText = extractLast(request.term);
	               
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllAgentIDAutocomplete',
			                 data: {
				                 
			                	 "AgentID" : searchText,
			                	 "areaID" :  areaID,	
			                	 "regionID" : regionID,	
									
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {

				                     
			                      response(data.listAgents);
			                        
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 100, scroll:true,		
		               
		        
			         
			        select: function(event, ui) {
				        
					selectedItems=[];
				    text = $("#agentsAutocomplete").val();
					text = text == null || text == undefined ? "" : text;
					
					// AllAgentsArr is equal to the internal array after removing a tag input
 					$('#agentsAutocomplete').on('itemRemoved', function(event) {						 
						AllAgentsArr = $("#agentsAutocomplete").data('tagsinput').itemsArray;
 					}); 
 					   
 					$('#agentsAutocomplete').on('itemAdded', function(event) {
 						 
						AllAgentsArr = $("#agentsAutocomplete").data('tagsinput').itemsArray;
 					}); 

					
				    checked = (text.indexOf(ui.item[1] + ' ') > -1 ? 'checked' : 'unchecked');					
					var selectedAgent = ui.item[1] +"  ";

				
					if (checked == 'checked') {
						
						var lastAgent = document.getElementById('agent').value;
						var uncheckedAgent = lastAgent.split(";");
						var uncheckedAgent  = uncheckedAgent[2];
						
			  			var uncheckedAgentID = ui.item[1]+" ";
			  				

	                    var newAllAgentsArr = [];
		                    
							for(i=0;i<AllAgentsArr.length;i++) {
								if (AllAgentsArr[i] != selectedAgent ) {
									newAllAgentsArr.push(AllAgentsArr[i]);
								}
							}
							
							AllAgentsArr = newAllAgentsArr;
							
							//console.log("newAllAgentsArr" +newAllAgentsArr);
							
							  //Remove all tags
		                    $("#agentsAutocomplete").tagsinput('removeAll');

							for(i=0;i<newAllAgentsArr.length;i++) {
				                  var agentNameItem = newAllAgentsArr[i].split(',') ;
				                
				                // Add checked tags
				                $("#agentsAutocomplete").tagsinput('add', agentNameItem.toString());
				               				                    
			                    }
			                
	                    

						
						if (ui.item[1] == selectedAgent ) {
					           document.getElementById('agent').value = '';
						}
				        }
				 
				        else { 
						     
			        var terms = split( $("#agentsAutocomplete").val() );
			        terms.pop();
			        terms.push( ui.item[1]);
			        selectedItems.push(terms);
			        txt=terms;
			        terms.push( "" );

				    agent.value = (ui.item ? ui.item[0] +";"+ ui.item[2] +";" + ui.item[1] +" "  : '');
					agent.value = this.value;
					  		      
					var agentInput=agent.value;
				    var agentNameInp = agentInput.split(";");
				    agentNameInp  = agentNameInp[2]+" ";

				    
				    
			       $("#agentsAutocomplete").tagsinput('add', agentNameInp.toString() );	
			    		

				       }
										
						return false;
							
				}
			            
				
				    }).autocomplete("instance")._renderItem = function(ul, item) {
			    
					 var text = $("#agentsAutocomplete").val();
					    text = text == null || text == undefined ? "" : text;

					   
			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" ){
			    	item[0]="No agent ID";
			    	item[1]="null";
			    	item[2]="null";
			    	
		          
				    }
			      
			       var checked = (text.indexOf(item[1] + ' ') > -1 ? 'checked' : 'unchecked');

			       return $("<li class='each'>")
		             .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[1]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
		            		 item[0] + "</span><br><span class='desc'>" +
		 	  		        item[1] + "</span><span class='desc'>" +";"+
		 	  		        item[2] + "</span></div>") .appendTo(ul);
			              
			              
		     	};

				$("#agent").focus(function(){
					
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}	
				        					
					});

				
				//ms chart agent autocomplete with multiselect	
					var msChartSelectedItems=[];
				  	var msChartText;
				  	var msChartChecked;
				  	var msChartAllAgntsArr=[];
				$("#agentMsChart").autocomplete({
				showHeader: true,
				
				// without this focus fct: keyboard movements reset the input to ''
				  focus: function( event, ui ) {
				        event.preventDefault(); 
				        if (event.Keycode ==38||event.Keycode ==40 ) {
				        	var terms = split($("#msChartAgentAutocomplete").val());
						        terms.pop();
						        terms.push( ui.item[1]);
						        msChartSelectedItems.push(terms);
						        txt=terms;
						        terms.push("");

				        var agentInput=agentMsChart.value;
					    var agentNameInp = agentInput.split(";");
					    agentNameInp  = agentNameInp[2]+" ";
					    
				       $("#msChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
				    		
				     }
				  }, // end of focus fct
	
		        source: function(request, response) {
					var areaID ="";
					var regionID ="";
					
			        //Extract last agent name from input field
		          var searchText = extractLast(request.term);
		               
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllAgentIDAutocomplete',
			                 data: {
				                 
			                	 "AgentID" : searchText,
			                	 "regionID" : regionID,	
			                	 "areaID" :areaID,
			                	 
			                	
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {

				                     
			                    response(data.listAgents);
					                        
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 100, scroll:true,		
		               
		        
			         
			        select: function(event, ui) {
				        
			        	msChartSelectedItems=[];
			        	msChartText = $("#msChartAgentAutocomplete").val();
			        	msChartText = msChartText == null || msChartText == undefined ? "" : msChartText;
					
					// AllAgentsArr is equal to the internal array after removing a tag input
 					$('#msChartAgentAutocomplete').on('itemRemoved', function(event) {						 
 						msChartAllAgntsArr = $("#msChartAgentAutocomplete").data('tagsinput').itemsArray;
 					}); 
 					   
 					$('#msChartAgentAutocomplete').on('itemAdded', function(event) {
 						 
 						msChartAllAgntsArr = $("#msChartAgentAutocomplete").data('tagsinput').itemsArray;
 					}); 

					
 					msChartChecked = (msChartText.indexOf(ui.item[1] + ' ') > -1 ? 'checked' : 'unchecked');					
					var selectedAgent = ui.item[1] +"  ";

				
					if (msChartChecked == 'checked') {
						
						var lastAgent = document.getElementById('agentMsChart').value;
						var uncheckedAgent = lastAgent.split(";");
						var uncheckedAgent  = uncheckedAgent[2];
						
			  			var uncheckedAgentID = ui.item[1]+" ";
			  				

	                    var newAllAgentsArrMsChart = [];
		                    
							for(i=0;i<msChartAllAgntsArr.length;i++) {
								if (msChartAllAgntsArr[i] != selectedAgent ) {
									newAllAgentsArrMsChart.push(msChartAllAgntsArr[i]);
								}
							}
							
							msChartAllAgntsArr = newAllAgentsArrMsChart;
							
							
							  //Remove all tags
		                    $("#msChartAgentAutocomplete").tagsinput('removeAll');

							for(i=0;i<newAllAgentsArrMsChart.length;i++) {
				                  var agentNameItem = newAllAgentsArrMsChart[i].split(',') ;
				                
				                // Add checked tags
				                $("#msChartAgentAutocomplete").tagsinput('add', agentNameItem.toString());
				               				                    
			                    }
			                
	                    

						
						if (ui.item[1] == selectedAgent ) {
					           document.getElementById('agentMsChart').value = '';
						}
				        }
				 
				        else { 
						     
			        var terms = split( $("#msChartAgentAutocomplete").val() );
			        terms.pop();
			        terms.push( ui.item[1]);
			        msChartSelectedItems.push(terms);
			        txt=terms;
			        terms.push( "" );

			        agentMsChart.value = (ui.item ? ui.item[0] +";"+ ui.item[2] +";" + ui.item[1] +" "  : '');
			        agentMsChart.value = this.value;
					  		      
					var agentInput=agentMsChart.value;
				    var agentNameInp = agentInput.split(";");
				    agentNameInp  = agentNameInp[2]+" ";

				    
				    
			       $("#msChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
			    		

				       }
										
						return false;
							
				}
			            
				
				    }).autocomplete("instance")._renderItem = function(ul, item) {
			    
					 var text = $("#msChartAgentAutocomplete").val();
					    text = text == null || text == undefined ? "" : text;

					   
			       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" ){
			    	item[0]="No agent ID";
			    	item[1]="null";
			    	item[2]="null";
			    	
		          
				    }
			      
			       var checked = (text.indexOf(item[1] + ' ') > -1 ? 'checked' : 'unchecked');

			       return $("<li class='each'>")
		             .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[1]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
		            		 item[0] + "</span><br><span class='desc'>" +
		 	  		        item[1] + "</span><span class='desc'>" +";"+
		 	  		        item[2] + "</span></div>") .appendTo(ul);
			              
			              
		     	};

				$("#agentMsChart").focus(function(){
					
		   	        	if (this.value == ""){
		   	            	$(this).autocomplete("search");
		   	        	}	
				        					
					});

				//reg Status pie chart agent autocomplete with multiselect	
				var regStatusChartSelectedItems=[];
			  	var regStatusChartText;
			  	var regStatusChartChecked;
			  	var regStatusChartAllAgntsArr=[];
			$("#agentRegStatusChart").autocomplete({
			showHeader: true,
			
			// without this focus fct: keyboard movements reset the input to ''
			  focus: function( event, ui ) {
			        event.preventDefault(); 
			        if (event.Keycode ==38||event.Keycode ==40 ) {
			        	var terms = split($("#regStatusChartAgentAutocomplete").val());
					        terms.pop();
					        terms.push( ui.item[1]);
					        regStatusChartSelectedItems.push(terms);
					        txt=terms;
					        terms.push("");

			        var agentInput=agentRegStatusChart.value;
				    var agentNameInp = agentInput.split(";");
				    agentNameInp  = agentNameInp[2]+" ";
				    
			       $("#regStatusChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
			    		
			     }
			  }, // end of focus fct

	        source: function(request, response) {
				var areaID ="";
				var regionID ="";
				
		        //Extract last agent name from input field
	          var searchText = extractLast(request.term);
	               
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllAgentIDAutocomplete',
		                 data: {
			                 
		                	 "AgentID" : searchText,
		                	 "regionID" : regionID,	
		                	 "areaID" :areaID,
		                	 
		                	
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {

			                     
		                    response(data.listAgents);
				                        
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 100, scroll:true,		
	               
	        
		         
		        select: function(event, ui) {
			        
		        	regStatusChartSelectedItems=[];
		        	regStatusChartText = $("#regStatusChartAgentAutocomplete").val();
		        	regStatusChartText = regStatusChartText == null || regStatusChartText == undefined ? "" : regStatusChartText;
				
				// AllAgentsArr is equal to the internal array after removing a tag input
					$('#regStatusChartAgentAutocomplete').on('itemRemoved', function(event) {						 
						regStatusChartAllAgntsArr = $("#regStatusChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 
					   
					$('#regStatusChartAgentAutocomplete').on('itemAdded', function(event) {
						 
						regStatusChartAllAgntsArr = $("#regStatusChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 

				
					regStatusChartChecked = (regStatusChartText.indexOf(ui.item[1] + ' ') > -1 ? 'checked' : 'unchecked');					
				var selectedAgent = ui.item[1] +"  ";

			
				if (regStatusChartChecked == 'checked') {
					
					var lastAgent = document.getElementById('agentRegStatusChart').value;
					var uncheckedAgent = lastAgent.split(";");
					var uncheckedAgent  = uncheckedAgent[2];
					
		  			var uncheckedAgentID = ui.item[1]+" ";
		  				

                    var newAllAgentsArrRegStatusChart = [];
	                    
						for(i=0;i<regStatusChartAllAgntsArr.length;i++) {
							if (regStatusChartAllAgntsArr[i] != selectedAgent ) {
								newAllAgentsArrRegStatusChart.push(regStatusChartAllAgntsArr[i]);
							}
						}
						
						regStatusChartAllAgntsArr = newAllAgentsArrRegStatusChart;
						
						
						  //Remove all tags
	                    $("#regStatusChartAgentAutocomplete").tagsinput('removeAll');

						for(i=0;i<newAllAgentsArrRegStatusChart.length;i++) {
			                  var agentNameItem = newAllAgentsArrRegStatusChart[i].split(',') ;
			                
			                // Add checked tags
			                $("#regStatusChartAgentAutocomplete").tagsinput('add', agentNameItem.toString());
			               				                    
		                    }
		                
                    

					
					if (ui.item[1] == selectedAgent ) {
				           document.getElementById('agentRegStatusChart').value = '';
					}
			        }
			 
			        else { 
					     
		        var terms = split( $("#regStatusChartAgentAutocomplete").val() );
		        terms.pop();
		        terms.push( ui.item[1]);
		        regStatusChartSelectedItems.push(terms);
		        txt=terms;
		        terms.push( "" );

		        agentRegStatusChart.value = (ui.item ? ui.item[0] +";"+ ui.item[2] +";" + ui.item[1] +" "  : '');
		        agentRegStatusChart.value = this.value;
				  		      
				var agentInput=agentRegStatusChart.value;
			    var agentNameInp = agentInput.split(";");
			    agentNameInp  = agentNameInp[2]+" ";

			    
			    
		       $("#regStatusChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
		    		

			       }
									
					return false;
						
			}
		            
			
			    }).autocomplete("instance")._renderItem = function(ul, item) {
		    
				 var text = $("#regStatusChartAgentAutocomplete").val();
				    text = text == null || text == undefined ? "" : text;

				   
		       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" ){
		    	item[0]="No agent ID";
		    	item[1]="null";
		    	item[2]="null";
		    	
	          
			    }
		      
		       var checked = (text.indexOf(item[1] + ' ') > -1 ? 'checked' : 'unchecked');

		       return $("<li class='each'>")
	             .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[1]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
	            		 item[0] + "</span><br><span class='desc'>" +
	 	  		        item[1] + "</span><span class='desc'>" +";"+
	 	  		        item[2] + "</span></div>") .appendTo(ul);
		              
		              
	     	};

			$("#agentRegStatusChart").focus(function(){
				
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}	
			        					
				});
				// count pie chart agent autocomplete with multiselect	
				var pieChartSelectedItems=[];
			  	var pieChartText;
			  	var pieChartChecked;
			  	var pieChartAllAgntsArr=[];
			$("#agentPieChart").autocomplete({
			showHeader: true,
			
			// without this focus fct: keyboard movements reset the input to ''
			  focus: function( event, ui ) {
			        event.preventDefault(); 
			        if (event.Keycode ==38||event.Keycode ==40 ) {
			        	var terms = split($("#pieChartAgentAutocomplete").val());
					        terms.pop();
					        terms.push( ui.item[1]);
					        pieChartSelectedItems.push(terms);
					        txt=terms;
					        terms.push("");

			        var agentInput=agentPieChart.value;
				    var agentNameInp = agentInput.split(";");
				    agentNameInp  = agentNameInp[2]+" ";
				    
			       $("#pieChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
			    		
			     }
			  }, // end of focus fct

	        source: function(request, response) {
				var areaID ="";
				var regionID ="";
				
		        //Extract last agent name from input field
	          var searchText = extractLast(request.term);
	               
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllAgentIDAutocomplete',
		                 data: {
			                 
		                	 "AgentID" : searchText,
		                	 "regionID" : regionID,	
		                	 "areaID" :areaID,
		                	 
		                	
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {

			                     
		                    response(data.listAgents);
				                        
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 100, scroll:true,		
	               
	        
		         
		        select: function(event, ui) {
			        
		        	pieChartSelectedItems=[];
		        	pieChartText = $("#pieChartAgentAutocomplete").val();
		        	pieChartText = pieChartText == null || pieChartText == undefined ? "" : pieChartText;
				
				// AllAgentsArr is equal to the internal array after removing a tag input
					$('#pieChartAgentAutocomplete').on('itemRemoved', function(event) {						 
						pieChartAllAgntsArr = $("#pieChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 
					   
					$('#pieChartAgentAutocomplete').on('itemAdded', function(event) {
						 
						pieChartAllAgntsArr = $("#pieChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 

				
					pieChartChecked = (pieChartText.indexOf(ui.item[1] + ' ') > -1 ? 'checked' : 'unchecked');					
				var selectedAgent = ui.item[1] +"  ";

			
				if (pieChartChecked == 'checked') {
					
					var lastAgent = document.getElementById('agentPieChart').value;
					var uncheckedAgent = lastAgent.split(";");
					var uncheckedAgent  = uncheckedAgent[2];
					
		  			var uncheckedAgentID = ui.item[1]+" ";
		  				

                    var newAllAgentsArrPieChart = [];
	                    
						for(i=0;i<pieChartAllAgntsArr.length;i++) {
							if (pieChartAllAgntsArr[i] != selectedAgent ) {
								newAllAgentsArrPieChart.push(pieChartAllAgntsArr[i]);
							}
						}
						
						pieChartAllAgntsArr = newAllAgentsArrPieChart;
						
						
						  //Remove all tags
	                    $("#pieChartAgentAutocomplete").tagsinput('removeAll');

						for(i=0;i<newAllAgentsArrPieChart.length;i++) {
			                  var agentNameItem = newAllAgentsArrPieChart[i].split(',') ;
			                
			                // Add checked tags
			                $("#pieChartAgentAutocomplete").tagsinput('add', agentNameItem.toString());
			               				                    
		                    }
		                
                    

					
					if (ui.item[1] == selectedAgent ) {
				           document.getElementById('agentPieChart').value = '';
					}
			        }
			 
			        else { 
					     
		        var terms = split( $("#pieChartAgentAutocomplete").val() );
		        terms.pop();
		        terms.push( ui.item[1]);
		        pieChartSelectedItems.push(terms);
		        txt=terms;
		        terms.push( "" );

		        agentPieChart.value = (ui.item ? ui.item[0] +";"+ ui.item[2] +";" + ui.item[1] +" "  : '');
		        agentPieChart.value = this.value;
				  		      
				var agentInput=agentPieChart.value;
			    var agentNameInp = agentInput.split(";");
			    agentNameInp  = agentNameInp[2]+" ";

			    
			    
		       $("#pieChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
		    		

			       }
									
					return false;
						
			}
		            
			
			    }).autocomplete("instance")._renderItem = function(ul, item) {
		    
				 var text = $("#pieChartAgentAutocomplete").val();
				    text = text == null || text == undefined ? "" : text;

				   
		       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" ){
		    	item[0]="No agent ID";
		    	item[1]="null";
		    	item[2]="null";
		    	
	          
			    }
		      
		       var checked = (text.indexOf(item[1] + ' ') > -1 ? 'checked' : 'unchecked');

		       return $("<li class='each'>")
	             .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[1]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
	            		 item[0] + "</span><br><span class='desc'>" +
	 	  		        item[1] + "</span><span class='desc'>" +";"+
	 	  		        item[2] + "</span></div>") .appendTo(ul);
		              
		              
	     	};

			$("#agentPieChart").focus(function(){
				
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}	
			        					
				});

				//line chart agent autocomplete with multiselect	
				var lineChartSelectedItems=[];
			  	var lineChartText;
			  	var lineChartChecked;
			  	var lineChartAllAgntsArr=[];
			$("#agentLineChart").autocomplete({
			showHeader: true,
			
			// without this focus fct: keyboard movements reset the input to ''
			  focus: function( event, ui ) {
			        event.preventDefault(); 
			        if (event.Keycode ==38||event.Keycode ==40 ) {
			        	var terms = split($("#lineChartAgentAutocomplete").val());
					        terms.pop();
					        terms.push( ui.item[1]);
					        lineChartSelectedItems.push(terms);
					        txt=terms;
					        terms.push("");

			        var agentInput=agentLineChart.value;
				    var agentNameInp = agentInput.split(";");
				    agentNameInp  = agentNameInp[2]+" ";
				    
			       $("#lineChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
			    		
			     }
			  }, // end of focus fct

	        source: function(request, response) {
				var areaID ="";
				var regionID ="";
				
		        //Extract last agent name from input field
	          var searchText = extractLast(request.term);
	               
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllAgentIDAutocomplete',
		                 data: {
			                 
		                	 "AgentID" : searchText,
		                	 "regionID" : regionID,	
		                	 "areaID" :areaID,
		                	 
		                	
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {

			                     
		                    response(data.listAgents);
				                        
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 100, scroll:true,		
	               
	        
		         
		        select: function(event, ui) {
			        
		        	lineChartSelectedItems=[];
		        	lineChartText = $("#lineChartAgentAutocomplete").val();
		        	lineChartText = lineChartText == null || lineChartText == undefined ? "" : lineChartText;
				
				// AllAgentsArr is equal to the internal array after removing a tag input
					$('#lineChartAgentAutocomplete').on('itemRemoved', function(event) {						 
						lineChartAllAgntsArr = $("#lineChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 
					   
					$('#lineChartAgentAutocomplete').on('itemAdded', function(event) {
						 
						lineChartAllAgntsArr = $("#lineChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 

				
					lineChartChecked = (lineChartText.indexOf(ui.item[1] + ' ') > -1 ? 'checked' : 'unchecked');					
				var selectedAgent = ui.item[1] +"  ";

			
				if (lineChartChecked == 'checked') {
					
					var lastAgent = document.getElementById('agentLineChart').value;
					var uncheckedAgent = lastAgent.split(";");
					var uncheckedAgent  = uncheckedAgent[2];
					
		  			var uncheckedAgentID = ui.item[1]+" ";
		  				

                    var newAllAgentsArrLineChart = [];
	                    
						for(i=0;i<lineChartAllAgntsArr.length;i++) {
							if (lineChartAllAgntsArr[i] != selectedAgent ) {
								newAllAgentsArrLineChart.push(lineChartAllAgntsArr[i]);
							}
						}
						
						lineChartAllAgntsArr = newAllAgentsArrLineChart;
						
						
						  //Remove all tags
	                    $("#lineChartAgentAutocomplete").tagsinput('removeAll');

						for(i=0;i<newAllAgentsArrLineChart.length;i++) {
			                  var agentNameItem = newAllAgentsArrLineChart[i].split(',') ;
			                
			                // Add checked tags
			                $("#lineChartAgentAutocomplete").tagsinput('add', agentNameItem.toString());
			               				                    
		                    }
		                
                    

					
					if (ui.item[1] == selectedAgent ) {
				           document.getElementById('agentLineChart').value = '';
					}
			        }
			 
			        else { 
					     
		        var terms = split( $("#lineChartAgentAutocomplete").val() );
		        terms.pop();
		        terms.push( ui.item[1]);
		        lineChartSelectedItems.push(terms);
		        txt=terms;
		        terms.push( "" );

		        agentLineChart.value = (ui.item ? ui.item[0] +";"+ ui.item[2] +";" + ui.item[1] +" "  : '');
		        agentLineChart.value = this.value;
				  		      
				var agentInput=agentLineChart.value;
			    var agentNameInp = agentInput.split(";");
			    agentNameInp  = agentNameInp[2]+" ";

			    
			    
		       $("#lineChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
		    		

			       }
									
					return false;
						
			}
		            
			
			    }).autocomplete("instance")._renderItem = function(ul, item) {
		    
				 var text = $("#lineChartAgentAutocomplete").val();
				    text = text == null || text == undefined ? "" : text;

				   
		       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" ){
		    	item[0]="No agent ID";
		    	item[1]="null";
		    	item[2]="null";
		    	
	          
			    }
		      
		       var checked = (text.indexOf(item[1] + ' ') > -1 ? 'checked' : 'unchecked');

		       return $("<li class='each'>")
	             .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[1]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
	            		 item[0] + "</span><br><span class='desc'>" +
	 	  		        item[1] + "</span><span class='desc'>" +";"+
	 	  		        item[2] + "</span></div>") .appendTo(ul);
		              
		              
	     	};

			$("#agentLineChart").focus(function(){
				
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}	
			        					
				});
			
				//stack chart agent autocomplete with multiselect	
				var stackChartSelectedItems=[];
			  	var stackChartText;
			  	var stackChartChecked;
			  	var stackChartAllAgntsArr=[];
			$("#agentStackChart").autocomplete({
			showHeader: true,
			
			// without this focus fct: keyboard movements reset the input to ''
			  focus: function( event, ui ) {
			        event.preventDefault(); 
			        if (event.Keycode ==38||event.Keycode ==40 ) {
			        	var terms = split($("#stackChartAgentAutocomplete").val());
					        terms.pop();
					        terms.push( ui.item[1]);
					        stackChartSelectedItems.push(terms);
					        txt=terms;
					        terms.push("");

			        var agentInput=agentStackChart.value;
				    var agentNameInp = agentInput.split(";");
				    agentNameInp  = agentNameInp[2]+" ";
				    
			       $("#stackChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
			    		
			     }
			  }, // end of focus fct

	        source: function(request, response) {
				var areaID ="";
				var regionID ="";
				
		        //Extract last agent name from input field
	          var searchText = extractLast(request.term);
	               
		             $.ajax({
		                 type: "GET",
		                 contentType: "application/json; charset=utf-8",
		                 url: '${pageContext.request.contextPath}/GetAllAgentIDAutocomplete',
		                 data: {
			                 
		                	 "AgentID" : searchText,
		                	 "regionID" : regionID,	
		                	 "areaID" :areaID,
		                	 
		                	
						 },
		                 dataType: "json",
		                 success: function (data) {
		                     if (data != null) {

			                     
		                    response(data.listAgents);
				                        
		                     }
		                 },
		                 error: function(result) {
		                     alert("Error");
		                 }
		             });
		         }, minLength:0, maxShowItems: 100, scroll:true,		
	               
	        
		         
		        select: function(event, ui) {
			        
		        	stackChartSelectedItems=[];
		        	stackChartText = $("#stackChartAgentAutocomplete").val();
		        	stackChartText = stackChartText == null || stackChartText == undefined ? "" : stackChartText;
				
				// AllAgentsArr is equal to the internal array after removing a tag input
					$('#stackChartAgentAutocomplete').on('itemRemoved', function(event) {						 
						stackChartAllAgntsArr = $("#stackChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 
					   
					$('#stackChartAgentAutocomplete').on('itemAdded', function(event) {
						 
						stackChartAllAgntsArr = $("#stackChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 

				
					stackChartChecked = (stackChartText.indexOf(ui.item[1] + ' ') > -1 ? 'checked' : 'unchecked');					
				var selectedAgent = ui.item[1] +"  ";

			
				if (stackChartChecked == 'checked') {
					
					var lastAgent = document.getElementById('agentStackChart').value;
					var uncheckedAgent = lastAgent.split(";");
					var uncheckedAgent  = uncheckedAgent[2];
					
		  			var uncheckedAgentID = ui.item[1]+" ";
		  				

                    var newAllAgentsArrStackChart = [];
	                    
						for(i=0;i<stackChartAllAgntsArr.length;i++) {
							if (stackChartAllAgntsArr[i] != selectedAgent ) {
								newAllAgentsArrStackChart.push(stackChartAllAgntsArr[i]);
							}
						}
						
						stackChartAllAgntsArr = newAllAgentsArrStackChart;
						
						
						  //Remove all tags
	                    $("#stackChartAgentAutocomplete").tagsinput('removeAll');

						for(i=0;i<newAllAgentsArrStackChart.length;i++) {
			                  var agentNameItem = newAllAgentsArrStackChart[i].split(',') ;
			                
			                // Add checked tags
			                $("#stackChartAgentAutocomplete").tagsinput('add', agentNameItem.toString());
			               				                    
		                    }
		                
                    

					
					if (ui.item[1] == selectedAgent ) {
				           document.getElementById('agentStackChart').value = '';
					}
			        }
			 
			        else { 
					     
		        var terms = split( $("#stackChartAgentAutocomplete").val() );
		        terms.pop();
		        terms.push( ui.item[1]);
		        stackChartSelectedItems.push(terms);
		        txt=terms;
		        terms.push( "" );

		        agentStackChart.value = (ui.item ? ui.item[0] +";"+ ui.item[2] +";" + ui.item[1] +" "  : '');
		        agentStackChart.value = this.value;
				  		      
				var agentInput=agentStackChart.value;
			    var agentNameInp = agentInput.split(";");
			    agentNameInp  = agentNameInp[2]+" ";

			    
			    
		       $("#stackChartAgentAutocomplete").tagsinput('add', agentNameInp.toString() );	
		    		

			       }
									
					return false;
						
			}
		            
			
			    }).autocomplete("instance")._renderItem = function(ul, item) {
		    
				 var text = $("#stackChartAgentAutocomplete").val();
				    text = text == null || text == undefined ? "" : text;

				   
		       if(item[0]=="null" &&item[1]=="null" &&item[2]=="null" ){
		    	item[0]="No agent ID";
		    	item[1]="null";
		    	item[2]="null";
		    	
	          
			    }
		      
		       var checked = (text.indexOf(item[1] + ' ') > -1 ? 'checked' : 'unchecked');

		       return $("<li class='each'>")
	             .append("<div class='acItem'><input type='checkbox' "+checked+" value="+item[1]+"/><span class='name' style='font-weight:bold;position:relative;left:5px'>" +
	            		 item[0] + "</span><br><span class='desc'>" +
	 	  		        item[1] + "</span><span class='desc'>" +";"+
	 	  		        item[2] + "</span></div>") .appendTo(ul);
		              
		              
	     	};

			$("#agentStackChart").focus(function(){
				
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


				// Default grid;
		         var ReportArrayGlobal = ${agentsSalesCountList};
		         var msColumnChartObj = ${msColumnChartObj};
		         var StackedandLineCount = ${StackedandLineCount};
		         var listChartAgents = ${listChartAgents};
		         chartData = ${chartData};
		        //  var chartData = ${chartData};
		         var regStatusList = ${regStatusList}
		          
		         // for export
                 var exportArrayGrid = []; 
                 
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
					 
	                    var totalSimCount=0;
			            var ArrayKeys = Object.keys(dataArray[0]);
			            
			            //var itemRow = "";
			       		var columnVal;

			       	    ////for export
  			       		var data = [];
  			       	    exportArrayGrid = [];
  		          		data.push('\r');
  			       		data.push(["Agent ID", "Agent Name", "MSISDN","Start Date", "End Date","Region","Total SIM Sales Count","Total Success SIM Registration","Total Failed SIM Registration"]);
  			       		
			            for (var i = 0; i < dataArray.length; i++) {
				           
							//itemRow += "<tr class='filterRows'>";
							// for export 
		          			data.push('\r');
		          			
			               for (var j = 0; j < ArrayKeys.length; j++) {
			               	
			            	   columnVal = ArrayKeys[j];
			            	   // for export 
  			            	   data.push(dataArray[i][ArrayKeys[j]]);
			                
			                   //itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>"; 
			                   if(columnVal =="totalCount")   totalSimCount+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
			                             
		          		   }
		          		   // itemRow += "</tr>";
		          		}
			          
						$('#totalSimCardCount').val(totalSimCount);
		          	    //$(tableBody).append(itemRow);

		          	    /// for export
	                    exportArrayGrid.push(data);
		          	   }
		          			        else{
											 
		          			  		
									$('#totalSimCardCount').val('0');
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

		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

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
               	 //draw charts for the agent on load
               	 $("#maxTimeCountStack").css("display", "none");
				 $("#minTimeCountStack").css("display", "none");
				 $("#maxTimeCountLine").css("display", "none");
				 $("#minTimeCountLine").css("display", "none");
				  
				 $('#time_count_line').css({'margin-top':'-40px'}); 
				 $('#countPieChart').css({'margin-top':'-40px'}); 
				 
					 
                 stDate =  $("#startdate1").val();
                 endDate = $("#enddate1").val();
               		AgentSales(msColumnChartObj);
               		fetchCountPerDay(StackedandLineCount,stDate,endDate);
               		fetchLineChartCount(StackedandLineCount);
					//PieCount(chartData);
					agentRegPie(regStatusList);
					
					PieCount(chartData);
               		

                 
	  $("#generate").click(  function() {
		  
		  $("#regType").val('').trigger('change');
		 
		  var selectedPeriodOption = $("#period").children("option:selected").val();

		  var startDate = document.getElementById("startdate1").value;
		  var endDate = document.getElementById("enddate1").value;
		
		  var date1 = new Date(startDate);
		  var date2 = new Date(endDate);
		   
		
		var maxCheckBox = document.getElementById("Max");
		var minCheckBox = document.getElementById("Min");

		 $("#countChartChangeOptions").val('null').trigger('change');
		 $("#minValMSFltr").val('');
		 $("#maxValMSFltr").val('');
		 $("#countChartFilterDiv").hide();

		 $('#maxStackedChartChangeOptions').attr('disabled', true);
		 $('#minStackedChartChangeOptions').attr('disabled', true);
		 $('#maxLineChartChangeOptions').attr('disabled', true);
		 $('#minLineChartChangeOptions').attr('disabled', true);
		 $('#maxStackedChartAgentID').attr('disabled', true);
		 $('#minStackedChartAgentID').attr('disabled', true);
		 $('#minLineChartAgentID').attr('disabled', true);
		 $('#maxLineChartAgentID').attr('disabled', true);

		 $('#agentMsChart').val('');
		 $('#agentStackChart').val('');
		 $('#agentLineChart').val('');
		 $('#agentPieChart').val('');
		 $('#agentRegStatusChart').val('');

		//Remove all tags
         $("#msChartAgentAutocomplete").tagsinput('removeAll');			
		 // AllAgentsArr is equal to the internal array after removing a tag input
		 $('#msChartAgentAutocomplete').on('itemRemoved', function(event) {						 
			msChartAllAgntsArr = $("#msChartAgentAutocomplete").data('tagsinput').itemsArray;
		 });

		//Remove all tags
         $("#stackChartAgentAutocomplete").tagsinput('removeAll');			
		 // AllAgentsArr is equal to the internal array after removing a tag input
		 $('#stackChartAgentAutocomplete').on('itemRemoved', function(event) {						 
			stackChartAllAgntsArr = $("#stackChartAgentAutocomplete").data('tagsinput').itemsArray;
		 }); 
		 
		//Remove all tags
         $("#lineChartAgentAutocomplete").tagsinput('removeAll');			
		 // AllAgentsArr is equal to the internal array after removing a tag input
		 $('#lineChartAgentAutocomplete').on('itemRemoved', function(event) {						 
			lineChartAllAgntsArr = $("#lineChartAgentAutocomplete").data('tagsinput').itemsArray;
		}); 

		//Remove all tags
         $("#pieChartAgentAutocomplete").tagsinput('removeAll');			
		 // AllAgentsArr is equal to the internal array after removing a tag input
		 $('#pieChartAgentAutocomplete').on('itemRemoved', function(event) {						 
			pieChartAllAgntsArr = $("#pieChartAgentAutocomplete").data('tagsinput').itemsArray;
		 }); 
		 
		//Remove all tags
         $("#regStatusChartAgentAutocomplete").tagsinput('removeAll');			
		 // AllAgentsArr is equal to the internal array after removing a tag input
		 $('#regStatusChartAgentAutocomplete').on('itemRemoved', function(event) {						 
			regStatusChartAllAgntsArr = $("#regStatusChartAgentAutocomplete").data('tagsinput').itemsArray;
		}); 	
		  // validation for a weekly report
		
       if (selectedPeriodOption=='null'&&maxCheckBox.checked == false && minCheckBox.checked == false ){

          alert("Please choose a period or check Maximum or Minimum");
          
               return false;
           }
	
       else{



    	   $("#gridTable").remove();
    	   $("#tableGrid").append('<table id="gridTable" class="table table-striped table-bordered almgrid-table">'
					+'<thead><tr class="header"><th>Agent ID<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> '
					+'<i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul"></ul></li></th>'
						+'<th>Agent Name<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
						+'</ul></li></th><th>MSISDN<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
						+'</ul></li></th><th>Start Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button><ul class="dropdown-menu filter-dropdown-ul">'
						+'</ul></li></th><th> End Date<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button><ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">'
						+'</ul></li></th><th>Region<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i	class="fa fa-list almgrid-filter-i"	aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul">	</ul></li></th>'
						+'<th>Total SIM Count<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'						
						+'<th>Total Success SIM Registration<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
						+'<th>Total Failed SIM Registration<li class="filter-dropdown dropdown"><button class="almgrid-filter" data-toggle="dropdown"> <i class="fa fa-list almgrid-filter-i" aria-hidden="true"></i></button>'
						+'<ul class="dropdown-menu dropdown-menu-right filter-dropdown-ul"></ul></li></th>'	
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


    	   UnselectAll();
    	   DeleteMarkers();

    	   var element = document.getElementById("selectUnselect");
 			if (element.innerHTML == "Select All"){
 				 element.innerHTML = "Unselect All";
 			}


 		 //Convert the elements of AllSitesArr that contains the agents name in popup into a string
 		 var allAgentsName = AllAgentsArr.join();			 
		
		 var regionName = document.getElementById("Region").value;
		 var regionName;
		  		 

		  if(regionName == ""){
				 region  ="";
			}

		else {
			region = regionName.split(";");
			region  = region[1];
		}


		  
			 if((selectedPeriodOption=='Daily' || selectedPeriodOption=='Weekly' || selectedPeriodOption=='Monthly' ) && (maxCheckBox.checked == true || minCheckBox.checked == true) ){
				
				 $('#agentStackChart').attr('disabled', true);
				 $('#agentLineChart').attr('disabled', true);
				 $('#agentMsChart').attr('disabled', true);
				 $('#agentPieChart').attr('disabled', true);
				 $('#agentRegStatusChart').attr('disabled', true);				 
				 
			}
			 else {
				 $('#agentStackChart').attr('disabled', false);
				 $('#agentLineChart').attr('disabled', false);
				 $('#agentMsChart').attr('disabled', false);
				 $('#agentPieChart').attr('disabled', false);
				 $('#agentRegStatusChart').attr('disabled', false);				 
				 
			}
				
		  // Maximum
            if (maxCheckBox.checked == true){
          	  var maxChecked = "Max";
  	        
  	         
             }
            // Minimum
            if (minCheckBox.checked == true){
          	  var minChecked = "Min";
  	         
             }

   		 $.ajax({
  		 	  type: "GET",
  		 	  contentType: "application/json; charset=utf-8",
  		 	  url: '${pageContext.request.contextPath}/GetSimCardSalesPerAgent',
  		 	  data: {
  				 	
  				 	"region":region,	
  					"startDate" : startDate,
  					"endDate" : endDate,
  				  	"period":selectedPeriodOption,
					"allAgentsName" :allAgentsName,
  									
  								
  		 				 },

  		 	dataType: "json",
  		 	success: function (data) {
  				
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
  		 		//Add legend button under zoom control on map
  		 		const centerControlDiv = document.createElement("div");
  		 		LegendControl(centerControlDiv, map);
  		 		map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

  		 		const DefaultZoomDiv = document.createElement("div");
  		 	    DefaultZoomControl(DefaultZoomDiv, map);
  		 	    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

  		 	    const maxCountControlDiv = document.createElement("div");
  		 		 MaxAgentSalesControl(maxCountControlDiv, map);
  		 	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxCountControlDiv);

  		 	    const minCountControlDiv = document.createElement("div");
  		 	 	MinAgentSalesControl(minCountControlDiv, map);
  		 	    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minCountControlDiv);
  		 	    
  		 		}
  		 		  

  		 	 if (allAgentsName != ""  ) {
  		 		if(maxCheckBox.checked == false && minCheckBox.checked == false){
					UnselectAll();
					AddSelectedAgentMarker(data.agentSalesCountList,map);
  		 		}

  		 	 else if (maxCheckBox.checked == true && minCheckBox.checked == false ){
					UnselectAll();			
					MaxMinSetColor(data.agentSalesCountList,map,"max");
					agentSalesCountList=maxList;
				}

  		 	else if (maxCheckBox.checked == false && minCheckBox.checked == true  ){
				UnselectAll();	
			     MaxMinSetColor(data.agentSalesCountList,map,"min");
			     agentSalesCountList=minList;
			}
			else if (maxCheckBox.checked == true && minCheckBox.checked == true ){
				UnselectAll();	
			    MaxMinSetColor(data.agentSalesCountList,map,"maxMin");
			    agentSalesCountList=maxMinList;
			}
				
			 }
		 	 else if (region !="" ) {
		 		if(maxCheckBox.checked == false && minCheckBox.checked == false){
					UnselectAll();
					simCountSetColor(data.agentSalesCountList,map);
		 		}
		 		 else if (maxCheckBox.checked == true && minCheckBox.checked == false ){
						UnselectAll();			
						MaxMinSetColor(data.agentSalesCountList,map,"max");
						agentSalesCountList=maxList;
					}

	  		 	else if (maxCheckBox.checked == false && minCheckBox.checked == true  ){
					UnselectAll();	
				     MaxMinSetColor(data.agentSalesCountList,map,"min");
				     agentSalesCountList=minList;
				}
				else if (maxCheckBox.checked == true && minCheckBox.checked == true ){
					UnselectAll();	
				    MaxMinSetColor(data.agentSalesCountList,map,"maxMin");
				    agentSalesCountList=maxMinList;
				}
			 }
			 else {
				 if(maxCheckBox.checked == false && minCheckBox.checked == false && (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu" )  ){
					//console.log("Here we go");
					UnselectAll();
					simCountSetColor(data.agentSalesCountList,map);
				 }

				 else if (maxCheckBox.checked == true && minCheckBox.checked == false && (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu")){
					UnselectAll();			
					MaxMinSetColor(data.agentSalesCountList,map,"max");
					agentSalesCountList=maxList;

					}
					else if (maxCheckBox.checked == false && minCheckBox.checked == true &&  (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu") ){
						UnselectAll();	
					     MaxMinSetColor(data.agentSalesCountList,map,"min");
					     agentSalesCountList=minList;
					}
					else if (maxCheckBox.checked == true && minCheckBox.checked == true &&  (selectedPeriodOption =="Daily" || selectedPeriodOption =="null" || selectedPeriodOption =="Weekly" || selectedPeriodOption =="Monthly" || selectedPeriodOption =="Accu") ){
						UnselectAll();	
					    MaxMinSetColor(data.agentSalesCountList,map,"maxMin");
					    agentSalesCountList=maxMinList;
					}
				}

  			
  		 		 },
  		 		 error: function(result) {
  		 			 alert("Error");
  		 			 }
  		 		 });
 	   //Grid  
 		$.ajax({
				type : "GET",
				contentType: "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/GenerateAgentSalesReport",
				dataType : "json",
				data : {
					
  					"startDate" : startDate,
  					"endDate" : endDate,
  				  	"period":selectedPeriodOption,
					"Max": maxChecked,
					"Min": minChecked,
					"region":region,
					"allAgentsName" :allAgentsName,
				},
				success : function(data) {
					
					 if (data != null) {
                      ReportArrayGlobal = data.agentsSalesCountList;
                       
                      if (ReportArrayGlobal.length == 0) { 
                     	
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

             			       ////for export
              			       		
             			        var totalSimCount=0;
      			                var ArrayKeys = Object.keys(dataArray[0]);
      			              //  var itemRow = "";
      			       		    var columnVal;

      			       		    var data = [];
          			       	    exportArrayGrid = [];
          		          	    data.push('\r');
          			       		data.push(["Agent ID", "Agent Name", "MSISDN","Start Date", "End Date","Region","Total SIM Sales Count","Total Success SIM Registration","Total Failed SIM Registration"]);
          			       	
      			                for (var i = 0; i < dataArray.length; i++) {
      				           
      							  //  itemRow += "<tr class='filterRows'>";
															
      							    // for export 
      			          			data.push('\r');
      							
      			                   for (var j = 0; j < ArrayKeys.length; j++) {
      			               	
      			            	      columnVal = ArrayKeys[j];
      			            	      // for export 
      	  			            	  data.push(dataArray[i][ArrayKeys[j]]);
      				                 
      			            	     // itemRow += "<td >" + dataArray[i][ArrayKeys[j]] + "</td>"; 
			                          if(columnVal =="totalCount")   totalSimCount+= parseFloat(dataArray[i][ArrayKeys[j]]);			                     
      			                   }

     		          			   //itemRow += "</tr>";
      		          		    }
      			                 /// for export
    		                     exportArrayGrid.push(data);
      			 		 
      			 				 $('#totalSimCardCount').val(totalSimCount);		
      		          	         //$(tableBody).append(itemRow);

      		          	        }
      		          			 else{
      											 
      		          			        $('#totalSimCardCount').val('0');
      									
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

      		          	        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:ArrayKeys,selectCheckbox:this.selectCheckbox  });

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

 	// for the chart
 		$.ajax({
			type : "GET",
			contentType: "application/json; charset=utf-8",
			url : "${pageContext.request.contextPath}/GenerateCountCharts",
			dataType : "json",
			data : {
			    "startDate" : $("#startdate1").val(),
			    "endDate" : $("#enddate1").val(),
                "region": region,
                "period": selectedPeriodOption,
				"Max": maxChecked,
				"Min": minChecked,
				"allAgentsName" :allAgentsName,
			},
			success : function(data) {
				

				 if (data != null) {
					 
					  msColumnChartObj=jQuery.parseJSON(data.msColumnChartObj);
			  		  regStatusList=jQuery.parseJSON(data.regStatusList);
			  		  chartData = jQuery.parseJSON(data.chartData);
			  		  //var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
					 
			}

				 if(selectedPeriodOption == "Accu"){
			  		  var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
					 
						if (maxCheckBox.checked == false && minCheckBox.checked == false ){

							agentRegPie(regStatusList);
							AgentSales(msColumnChartObj);
							PieCount(chartData);
							

						  $("#time_count_stack").css("display", "none");
						  $("#time_count_line").css("display", "none");
						  $("#agent_count_chart").css("display", "block");
						  $('#countPieChart').css("display", "block");
						  $('#regStatusPieChart').css("display", "block");

						$('#countPieChart').css({'margin-top':'-130px'}); 

						  $("#maxTimeCountStack").css("display", "none");
						  $("#minTimeCountStack").css("display", "none");
						  $("#maxTimeCountLine").css("display", "none");
						  $("#minTimeCountLine").css("display", "none");

						  var element = document.getElementById("noAccuCharts");
							element.innerHTML = "";
								
						  
						}
						else {
							//PieCount(chartData);
							 $("#time_count_stack").css("display", "none");
							  $("#time_count_line").css("display", "none");
							  $("#agent_count_chart").css("display", "none");
							  $('#countPieChart').css("display", "none");
							  $('#regStatusPieChart').css("display", "none");
							  $('#countPieChart').css({'margin-top':'-50px'}); 

							  $("#maxTimeCountStack").css("display", "none");
							  $("#minTimeCountStack").css("display", "none");
							  $("#maxTimeCountLine").css("display", "none");
							  $("#minTimeCountLine").css("display", "none");

						var element = document.getElementById("noAccuCharts");
				  		element.classList.add("noAccuChartsElement");
						element.innerHTML = '<p style=" color:#ff0000;border:3px; border-style:solid; border-color:#999999; padding: 3em;margin-top: -200px;">No chart to be shown. </p>';
													  		  
						}
						
					}

				 
				
					
				 else if(selectedPeriodOption == "null"){	
					 					
						//PieCount(chartData);
					$("#time_count_stack").css("display", "none");
					$("#time_count_line").css("display", "none");
					$("#agent_count_chart").css("display", "none");
					$('#countPieChart').css("display", "none");
					$('#regStatusPieChart').css("display", "none");
					$('#countPieChart').css({'margin-top':'-50px'}); 

					$("#maxTimeCountStack").css("display", "none");
					$("#minTimeCountStack").css("display", "none");
					$("#maxTimeCountLine").css("display", "none");
					$("#minTimeCountLine").css("display", "none");	

					var element = document.getElementById("noAccuCharts");
			  		element.classList.add("noAccuChartsElement");
					element.innerHTML = '<p style=" color:#ff0000;border:3px; border-style:solid; border-color:#999999; padding: 3em;margin-top: -200px;">No chart to be shown. </p>';
										
						
					}

				 else if(selectedPeriodOption == "Weekly"){
					if (maxCheckBox.checked == true && minCheckBox.checked == true ){

						if (agent!="") {
							$("#maxTimeCountStack").css("display", "block");
							$("#minTimeCountStack").css("display", "block");
							$('#maxTimeCountStack').css({'margin-top':'-50px'}); 
							$("#maxTimeCountLine").css("display", "block");
							$("#minTimeCountLine").css("display", "block");
							$('#maxTimeCountLine').css({'margin-top':'-30px'}); 

							$("#time_count_stack").css("display", "none");
							$("#time_count_line").css("display", "none");
							$("#agent_count_chart").css("display", "none");
							$('#countPieChart').css("display", "none");
							$('#regStatusPieChart').css("display", "none");

							var element = document.getElementById("noAccuCharts");
							element.innerHTML = "";
							var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
							var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

							fetchMaxCountPerDay(maxStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchMinCountPerDay(minStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchMaxLineChartCount(maxStackedandLineCount);
							fetchMinLineChartCount(minStackedandLineCount);							
							}

						else {
							$("#maxTimeCountStack").css("display", "none");
							$("#minTimeCountStack").css("display", "none");
							$("#maxTimeCountLine").css("display", "none");
							$("#minTimeCountLine").css("display", "none");

							$("#time_count_stack").css("display", "block");
							$("#time_count_line").css("display", "block");
							$("#agent_count_chart").css("display", "none");
							$('#countPieChart').css("display", "none");
							$('#regStatusPieChart').css("display", "none");
											    
							var element = document.getElementById("noAccuCharts");
							element.innerHTML = "";
												
							var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
							var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

							fetchMaxMinCountPerDay(maxStackedandLineCount,minStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchMaxMinLineChartCount(maxStackedandLineCount,minStackedandLineCount);
						}

						$('#stackedChartChangeOptions').attr('disabled', true);
						$('#lineChartChangeOptions').attr('disabled', true);
						}

					else if (maxCheckBox.checked == true || minCheckBox.checked == true ){
					
					var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
						fetchCountPerDay(StackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
						fetchLineChartCount(StackedandLineCount);
										
						$("#time_count_stack").css("display", "block");
						$("#time_count_line").css("display", "block");
						$("#agent_count_chart").css("display", "none");
						$('#countPieChart').css("display", "none");
						$('#regStatusPieChart').css("display", "none");

						$("#maxTimeCountStack").css("display", "none");
						$("#minTimeCountStack").css("display", "none");
						$("#maxTimeCountLine").css("display", "none");
						$("#minTimeCountLine").css("display", "none");

						$('#time_count_line').css({'margin-top':'-30px'}); 
						$('#time_count_stack').css({'margin-top':'-10px'}); 
											
						var element = document.getElementById("noAccuCharts");
							element.innerHTML = "";

						$('#stackedChartChangeOptions').attr('disabled', true);
						$('#lineChartChangeOptions').attr('disabled', true);
						}

					else {

						var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
						fetchCountPerDay(StackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
						fetchLineChartCount(StackedandLineCount);
										
						agentRegPie(regStatusList);
						AgentSales(msColumnChartObj);
						PieCount(chartData);

						$("#time_count_stack").css("display", "block");
						$("#time_count_line").css("display", "block");
						$("#agent_count_chart").css("display", "block");
						$('#countPieChart').css("display", "block");
						$('#regStatusPieChart').css("display", "block");
										    						    
						$("#maxTimeCountStack").css("display", "none");
						$("#minTimeCountStack").css("display", "none");
						$("#maxTimeCountLine").css("display", "none");
						$("#minTimeCountLine").css("display", "none");

						var element = document.getElementById("noAccuCharts");
							element.innerHTML = "";

						$('#countPieChart').css({'margin-top':'-30px'}); 
							//$('#regStatusPieChart').css({'margin-top':'5px'}); 
						$('#stackedChartChangeOptions').attr('disabled', false);
						$('#lineChartChangeOptions').attr('disabled', false);
						}
				}

				 else if(selectedPeriodOption == "Monthly"){
					 if (maxCheckBox.checked == true && minCheckBox.checked == true ){

							if (agent!="") {
								$("#maxTimeCountStack").css("display", "block");
								$("#minTimeCountStack").css("display", "block");
								$('#maxTimeCountStack').css({'margin-top':'-50px'}); 
								$("#maxTimeCountLine").css("display", "block");
								$("#minTimeCountLine").css("display", "block");
								$('#maxTimeCountLine').css({'margin-top':'-30px'}); 

								$("#time_count_stack").css("display", "none");
								$("#time_count_line").css("display", "none");
								$("#agent_count_chart").css("display", "none");
								$('#countPieChart').css("display", "none");
								$('#regStatusPieChart').css("display", "none");

								var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";
								var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
								var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

								fetchMaxCountPerDay(maxStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
								fetchMinCountPerDay(minStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
								fetchMaxLineChartCount(maxStackedandLineCount);
								fetchMinLineChartCount(minStackedandLineCount);							
								}

							else {
								$("#maxTimeCountStack").css("display", "none");
								$("#minTimeCountStack").css("display", "none");
								$("#maxTimeCountLine").css("display", "none");
								$("#minTimeCountLine").css("display", "none");

								$("#time_count_stack").css("display", "block");
								$("#time_count_line").css("display", "block");
								$("#agent_count_chart").css("display", "none");
								$('#countPieChart').css("display", "none");
								$('#regStatusPieChart').css("display", "none");
												    
								var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";
													
								var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
								var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

								fetchMaxMinCountPerDay(maxStackedandLineCount,minStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
								fetchMaxMinLineChartCount(maxStackedandLineCount,minStackedandLineCount);
							}

							$('#stackedChartChangeOptions').attr('disabled', true);
							$('#lineChartChangeOptions').attr('disabled', true);
							}

						else if (maxCheckBox.checked == true || minCheckBox.checked == true ){
						
						var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
							fetchCountPerDay(StackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchLineChartCount(StackedandLineCount);
											
							$("#time_count_stack").css("display", "block");
							$("#time_count_line").css("display", "block");
							$("#agent_count_chart").css("display", "none");
							$('#countPieChart').css("display", "none");
							$('#regStatusPieChart').css("display", "none");

							$("#maxTimeCountStack").css("display", "none");
							$("#minTimeCountStack").css("display", "none");
							$("#maxTimeCountLine").css("display", "none");
							$("#minTimeCountLine").css("display", "none");

							$('#time_count_line').css({'margin-top':'-30px'}); 
							$('#time_count_stack').css({'margin-top':'-10px'}); 
												
							var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";

							$('#stackedChartChangeOptions').attr('disabled', true);
							$('#lineChartChangeOptions').attr('disabled', true);
							}

						else {

							var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
							fetchCountPerDay(StackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchLineChartCount(StackedandLineCount);
											
							agentRegPie(regStatusList);
							AgentSales(msColumnChartObj);
							PieCount(chartData);

							$("#time_count_stack").css("display", "block");
							$("#time_count_line").css("display", "block");
							$("#agent_count_chart").css("display", "block");
							$('#countPieChart').css("display", "block");
							$('#regStatusPieChart').css("display", "block");
											    						    
							$("#maxTimeCountStack").css("display", "none");
							$("#minTimeCountStack").css("display", "none");
							$("#maxTimeCountLine").css("display", "none");
							$("#minTimeCountLine").css("display", "none");

							var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";

							$('#countPieChart').css({'margin-top':'-30px'}); 
								//$('#regStatusPieChart').css({'margin-top':'5px'}); 
							$('#stackedChartChangeOptions').attr('disabled', false);
							$('#lineChartChangeOptions').attr('disabled', false);
							}
						 
				 }	

				 else if(selectedPeriodOption == "Daily"){
					 
						if (maxCheckBox.checked == true && minCheckBox.checked == true ){

							if (agent!="") {

								$("#maxTimeCountStack").css("display", "block");
								$("#minTimeCountStack").css("display", "block");
								$('#maxTimeCountStack').css({'margin-top':'-50px'}); 
								$("#maxTimeCountLine").css("display", "block");
						  		$("#minTimeCountLine").css("display", "block");
								$('#maxTimeCountLine').css({'margin-top':'-30px'}); 
								  
								$("#time_count_stack").css("display", "none");
								$("#time_count_line").css("display", "none");
							    $("#agent_count_chart").css("display", "none");
							    $('#countPieChart').css("display", "none");
							    $('#regStatusPieChart').css("display", "none");

							    var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";
								
							 	var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
							  	var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

								fetchMaxCountPerDay(maxStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
								fetchMinCountPerDay(minStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
								fetchMaxLineChartCount(maxStackedandLineCount);
								fetchMinLineChartCount(minStackedandLineCount);							

								}
							else {
								
								$("#maxTimeCountStack").css("display", "none");
							    $("#minTimeCountStack").css("display", "none");
							    $("#maxTimeCountLine").css("display", "none");
								$("#minTimeCountLine").css("display", "none");
								  
							    $("#time_count_stack").css("display", "block");
								$("#time_count_line").css("display", "block");
							    $("#agent_count_chart").css("display", "none");
							    $('#countPieChart').css("display", "none");
							    $('#regStatusPieChart').css("display", "none");
							    
							    var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";
								
						  	var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
						  	var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

							fetchMaxMinCountPerDay(maxStackedandLineCount,minStackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchMaxMinLineChartCount(maxStackedandLineCount,minStackedandLineCount);
							}

			  	 			$('#stackedChartChangeOptions').attr('disabled', true);
			  	 			$('#lineChartChangeOptions').attr('disabled', true);
			  	 			
							
						}
						else if (maxCheckBox.checked == true || minCheckBox.checked == true ){
							
					  		var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
							fetchCountPerDay(StackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchLineChartCount(StackedandLineCount);
						
							$("#time_count_stack").css("display", "block");
							$("#time_count_line").css("display", "block");
						    $("#agent_count_chart").css("display", "none");
						    $('#countPieChart').css("display", "none");
						    $('#regStatusPieChart').css("display", "none");

						    $("#maxTimeCountStack").css("display", "none");
							$("#minTimeCountStack").css("display", "none");
							$("#maxTimeCountLine").css("display", "none");
							$("#minTimeCountLine").css("display", "none");

							$('#time_count_line').css({'margin-top':'-30px'}); 
							$('#time_count_stack').css({'margin-top':'-10px'}); 
							
							 var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";

				  	 	 $('#stackedChartChangeOptions').attr('disabled', true);
				  	 	$('#lineChartChangeOptions').attr('disabled', true);
								
								
						}
						else {
							
					  		var StackedandLineCount = jQuery.parseJSON(data.StackedandLineCount);
							fetchCountPerDay(StackedandLineCount, $("#startdate1").val(), $("#enddate1").val());
							fetchLineChartCount(StackedandLineCount);
						
							agentRegPie(regStatusList);
							AgentSales(msColumnChartObj);
							PieCount(chartData);
							
							$("#time_count_stack").css("display", "block");
							$("#time_count_line").css("display", "block");
						    $("#agent_count_chart").css("display", "block");
						    $('#countPieChart').css("display", "block");
						    $('#regStatusPieChart').css("display", "block");
						    						    
						    $("#maxTimeCountStack").css("display", "none");
							$("#minTimeCountStack").css("display", "none");
							$("#maxTimeCountLine").css("display", "none");
							$("#minTimeCountLine").css("display", "none");
							 var element = document.getElementById("noAccuCharts");
								element.innerHTML = "";
								
							//$('#time_count_stack').css({'margin-top':'10px'}); 
							//$('#time_count_line').css({'margin-top':'-30px'}); 
							//$('#agent_count_chart').css({'margin-top':'-5px'}); 
							$('#countPieChart').css({'margin-top':'-30px'}); 
							//$('#regStatusPieChart').css({'margin-top':'5px'}); 
							 

				  	 		$('#stackedChartChangeOptions').attr('disabled', false);
				  	 		$('#lineChartChangeOptions').attr('disabled', false);
								
								 
						}
					}					
							
							
						
					
  },
  
  error : function(error) {
		console.log("The error is " + error);
	}
});	  
			
			var fltrdStrtDt = $("#startdate1").val();
			var fltrdEndDt = $("#enddate1").val();
			var fltrdAgntID = $('#agent').val();
			
			 $("#startdate2").val(fltrdStrtDt).trigger('change');
			 $("#startdate3").val(fltrdStrtDt).trigger('change');
			 $("#startdate4").val(fltrdStrtDt).trigger('change');
			 $("#startdate5").val(fltrdStrtDt).trigger('change');
			 $("#startdate6").val(fltrdStrtDt).trigger('change');
			 $("#startdate7").val(fltrdStrtDt).trigger('change');
			 $("#startdate8").val(fltrdStrtDt).trigger('change');
			 $("#startdate9").val(fltrdStrtDt).trigger('change');
			 $("#startdate10").val(fltrdStrtDt).trigger('change');
			 
			 $("#enddate2").val(fltrdEndDt).trigger('change');
			 $("#enddate3").val(fltrdEndDt).trigger('change');
			 $("#enddate4").val(fltrdEndDt).trigger('change');
			 $("#enddate5").val(fltrdEndDt).trigger('change');
			 $("#enddate6").val(fltrdEndDt).trigger('change');
			 $("#enddate7").val(fltrdEndDt).trigger('change');
			 $("#enddate8").val(fltrdEndDt).trigger('change');
			 $("#enddate9").val(fltrdEndDt).trigger('change');
			 $("#enddate10").val(fltrdEndDt).trigger('change');
					
		
			/*if(maxCheckBox.checked == false && minCheckBox.checked == false){
				$('#agentMsChart').val(fltrdAgntID).trigger('change');
				$('#agentStackChart').val(fltrdAgntID).trigger('change');
				$('#agentLineChart').val(fltrdAgntID).trigger('change');
				$('#agentPieChart').val(fltrdAgntID).trigger('change');
				$('#agentRegStatusChart').val(fltrdAgntID).trigger('change');
			}*/
			
			 $('#minValMSFltr').val('');
			 $('#maxValMSFltr').val('');
			 $('#minValStackedFltr').val('');
			 $('#maxValStackedFltr').val('');
			 $('#minValLineFltr').val('');
			 $('#maxValLineFltr').val('');
			 $('#minValPiesFltr').val('');
			 $('#maxValPiesFltr').val('');
			 $('#minValRegStatFltr').val('');
			 $('#maxValRegStatFltr').val('');
			 $('#minValMaxStackedFltr').val('');
			 $('#maxValMaxStackedFltr').val('');
			 $('#minValMinStackedFltr').val('');
			 $('#maxValMinStackedFltr').val('');
			 $('#minValMaxLineFltr').val('');
			 $('#maxValMaxLineFltr').val('');
			 $('#minValMinLineFltr').val('');
			 $('#maxValMinLineFltr').val('');
			 
           
			

}
   });// end of generate function

	  

			 $("#countChartChangeOptions").val('null').trigger('change');
			 $("#stackedChartChangeOptions").val('null').trigger('change');
			 $("#lineChartChangeOptions").val('null').trigger('change');
			 $("#countPieChangeOptions").val('null').trigger('change');
			 $("#regStatusPiechangeOptions").val('null').trigger('change');
			 
			 
		$('#countChartApplyFilter,#stackedChartApplyFilter,#lineChartApplyFilter,#countPieApplyFilter,#regStatPieApplyFilter').click(function(){
			var stDate,endDate;
			var selectBox;
			var selectedValue;
			var selectSalesBox ;
			var selectedSalesValue;
			var minFltrdVal,maxFltrdVal;
			var id = this.id;
		 
			var maxCheckBox = document.getElementById("Max");
			var minCheckBox = document.getElementById("Min");
				 
			     var maxChecked,minChecked;
				 // Maximum
	               if (maxCheckBox.checked == true){
	            	   maxChecked= "Max";	     	         
	                }
	               // Minimum
	               if (minCheckBox.checked == true){
	             	  minChecked = "Min";
	     	         
	                }
			 if (id == 'countChartApplyFilter') {
				 stDate =  $("#startdate2").val();
				 endDate = $("#enddate2").val();

				 if (maxCheckBox.checked == true || minCheckBox.checked == true ){
					 
						//Remove all tags
	                    $("#msChartAgentAutocomplete").tagsinput('removeAll');
		  				// AllAgentsArr is equal to the internal array after removing a tag input
						$('#msChartAgentAutocomplete').on('itemRemoved', function(event) {						 
							msChartAllAgntsArr = $("#msChartAgentAutocomplete").data('tagsinput').itemsArray;
						});
						 
						//Disable the input fields and select options
		  				$('#agentMsChart').val('').trigger('change');
		  	 			$('#agentMsChart').attr('disabled', true);
		  	 			$('#countChartSimSalesOpt').attr('disabled', true);
		  	 			$('#maxValMSFltr').attr('disabled', true);
		  	 			$('#minValMSFltr').attr('disabled', true);
	  		  	               
	               }
	             else {
	            	 $('#agentMsChart').attr('disabled', false);
	            	 $('#countChartSimSalesOpt').attr('disabled', false);
		  	 		 $('#maxValMSFltr').attr('disabled', false);
		  	 		 $('#minValMSFltr').attr('disabled', false);
	               }

				 //Convert the elements of AllSitesArr that contains the agents name in popup into a string
				var allAgentsNameChart = msChartAllAgntsArr.join();
   
				 selectBox = document.getElementById("countChartChangeOptions");
				 selectedValue = selectBox.options[selectBox.selectedIndex].value;
				 selectSalesBox = document.getElementById("countChartSimSalesOpt");
				 selectedSalesValue = selectSalesBox.options[selectSalesBox.selectedIndex].value;
				 minFltrdVal =$('#minValMSFltr').val();
				 maxFltrdVal =$('#maxValMSFltr').val();
					 
			    }
			    
			 else if(id == 'stackedChartApplyFilter'){
				 
				 stDate =  $("#startdate3").val();
				 endDate = $("#enddate3").val();

	               if (maxCheckBox.checked == true || minCheckBox.checked == true ){
	            	
	  				//Remove all tags
	                 $("#stackChartAgentAutocomplete").tagsinput('removeAll');
	  				// AllAgentsArr is equal to the internal array after removing a tag input
					$('#stackChartAgentAutocomplete').on('itemRemoved', function(event) {						 
						stackChartAllAgntsArr = $("#stackChartAgentAutocomplete").data('tagsinput').itemsArray;
					}); 
						  			  
					//Disable the input fields and select options
	  				$('#agentStackChart').val('').trigger('change');
	  	 			$('#agentStackChart').attr('disabled', true);	
	  	 			$('#stackedChartChangeOptions').attr('disabled', true);
	  	 			$('#stackedChartSimSalesOpt').attr('disabled', true);
	  	 			$('#maxValStackedFltr').attr('disabled', true);
	  	 			$('#minValStackedFltr').attr('disabled', true);
	  	 			  	 			
	               }
	               else {
		               
		             $('#agentStackChart').attr('disabled', false);
					 $('#stackedChartChangeOptions').attr('disabled', false);
					 $('#stackedChartSimSalesOpt').attr('disabled', false);
		  	 		 $('#maxValStackedFltr').attr('disabled', false);
		  	 		 $('#minValStackedFltr').attr('disabled', false);
	             
	               }
	             //Convert the elements of AllSitesArr that contains the agents name in popup into a string
				 var allAgentsNameChart = stackChartAllAgntsArr.join();
	   			 
				 selectBox = document.getElementById("stackedChartChangeOptions");
				 selectedValue = selectBox.options[selectBox.selectedIndex].value;
				 selectSalesBox = document.getElementById("stackedChartSimSalesOpt");
				 selectedSalesValue = selectSalesBox.options[selectSalesBox.selectedIndex].value;
				 minFltrdVal =$('#minValStackedFltr').val();
				 maxFltrdVal = $('#maxValStackedFltr').val();
			}

			 else if(id == 'lineChartApplyFilter'){
				 
				 stDate =  $("#startdate4").val();
				 endDate = $("#enddate4").val();

				  if (maxCheckBox.checked == true || minCheckBox.checked == true ){
		  				//Remove all tags
	                    $("#lineChartAgentAutocomplete").tagsinput('removeAll');
	  				
		  				// AllAgentsArr is equal to the internal array after removing a tag input
						$('#lineChartAgentAutocomplete').on('itemRemoved', function(event) {						 
							lineChartAllAgntsArr = $("#lineChartAgentAutocomplete").data('tagsinput').itemsArray;
						}); 
						
						//Disable the input fields and select options
		  				$('#agentLineChart').val('').trigger('change');
		  	 			$('#agentLineChart').attr('disabled', true);
		  	 			$('#lineChartChangeOptions').attr('disabled', true);
		  	 			$('#lineChartSimSalesOpt').attr('disabled', true);
		  	 			$('#maxValLineFltr').attr('disabled', true);
		  	 			$('#minValLineFltr').attr('disabled', true);
		  		  	               
		               }
		               else {
			             $('#agentLineChart').attr('disabled', false);
			  	 	     $('#lineChartChangeOptions').attr('disabled', false);
			  	 	  	 $('#lineChartSimSalesOpt').attr('disabled', false);
		  	 			 $('#maxValLineFltr').attr('disabled', false);
		  	 			 $('#minValLineFltr').attr('disabled', false);
						 
		               }
					//Convert the elements of AllSitesArr that contains the agents name in popup into a string
					 var allAgentsNameChart = lineChartAllAgntsArr.join();
		   			 
				
					  selectBox = document.getElementById("lineChartChangeOptions");
					  selectedValue = selectBox.options[selectBox.selectedIndex].value;
					  selectSalesBox = document.getElementById("lineChartSimSalesOpt");
					  selectedSalesValue = selectSalesBox.options[selectSalesBox.selectedIndex].value;
					  minFltrdVal =$('#minValLineFltr').val();
					  maxFltrdVal = $('#maxValLineFltr').val();
				}
			 
			 
			 else if(id == 'countPieApplyFilter'){

				 stDate =  $("#startdate5").val();
				 endDate = $("#enddate5").val();

				 if (maxCheckBox.checked == true || minCheckBox.checked == true ){
						//Remove all tags
	                    $("#pieChartAgentAutocomplete").tagsinput('removeAll');
	  				
		  				// AllAgentsArr is equal to the internal array after removing a tag input
						$('#pieChartAgentAutocomplete').on('itemRemoved', function(event) {						 
							pieChartAllAgntsArr = $("#pieChartAgentAutocomplete").data('tagsinput').itemsArray;
						}); 
						
			  			$('#agentPieChart').val('').trigger('change');
			  	 		$('#agentPieChart').attr('disabled', true);
			       	}
				     else {
					    $('#agentPieChart').attr('disabled', false);
					}
		               
					//Convert the elements of AllSitesArr that contains the agents name in popup into a string
					 var allAgentsNameChart = pieChartAllAgntsArr.join();
		   			 
					  selectBox = document.getElementById("countPieChangeOptions");
					  selectedValue = selectBox.options[selectBox.selectedIndex].value;
					  selectSalesBox = document.getElementById("countPieSimSalesOpt");
					  selectedSalesValue = selectSalesBox.options[selectSalesBox.selectedIndex].value;


			}

			 else if(id == 'regStatPieApplyFilter'){

				
				 stDate =  $("#startdate6").val();
				 endDate = $("#enddate6").val();

				  if (maxCheckBox.checked == true || minCheckBox.checked == true ){
					  
						//Remove all tags
	                    $("#regStatusChartAgentAutocomplete").tagsinput('removeAll');
	  				
		  				// AllAgentsArr is equal to the internal array after removing a tag input
						$('#regStatusChartAgentAutocomplete').on('itemRemoved', function(event) {						 
							regStatusChartAllAgntsArr = $("#regStatusChartAgentAutocomplete").data('tagsinput').itemsArray;
						}); 
			  			$('#agentRegStatusChart').val('').trigger('change');
			  	 		$('#agentRegStatusChart').attr('disabled', true);
		       	}
			     else {
		       		$('#agentRegStatusChart').attr('disabled', false);
		            
				}
	               
				//Convert the elements of AllSitesArr that contains the agents name in popup into a string
				var allAgentsNameChart = regStatusChartAllAgntsArr.join();
		   			 
				 
				  selectBox = document.getElementById("regStatusPiechangeOptions");
				  selectedValue = selectBox.options[selectBox.selectedIndex].value;
				  
				  selectSalesBox = document.getElementById("regStatusPieSimSalesOpt");
				  selectedSalesValue = selectSalesBox.options[selectSalesBox.selectedIndex].value;

				  selectRangeBox = document.getElementById("regType");
				  var regType = selectRangeBox.options[selectRangeBox.selectedIndex].value;
					
			}
		   
		
				
            if(selectedValue == "null" || (selectedValue == "isBetween" && minFltrdVal == '' && maxFltrdVal == '')){

  				$.ajax({
				type : "GET",
				contentType: "application/json; charset=utf-8",
				url : "${pageContext.request.contextPath}/FilteringMethodSIM",
				dataType : "json",
				data : {
				    "startDate" :stDate,
				    "endDate" : endDate,
                    "region": document.getElementById("Region").value,
                    "period": $("#period").children("option:selected").val(),
					"Max": maxChecked,
					"Min": minChecked,
					"filterID" : id,
  					"regType":regType,
  					"allAgentsNameChart":allAgentsNameChart,
						
				},
				success : function(data) {
					
					 if (data != null) {
	                        	 
	                        	 if (id == 'countChartApplyFilter') {
	                        		var countChartData =jQuery.parseJSON(data.msColumnChartObj);
	                        		AgentSales(countChartData)
								}

								else if(id == 'stackedChartApplyFilter'){
									
									 stDate =  $("#startdate3").val();
		                             endDate = $("#enddate3").val();
		                             
									//Max Min are both checked
									if (maxCheckBox.checked == true && minCheckBox.checked == true ){

										var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
									  	var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

									  	fetchMaxMinCountPerDay(maxStackedandLineCount,minStackedandLineCount,stDate, endDate);
													
									} // end max-min both checked

									//MAX or MIN is checked
									else if (maxCheckBox.checked == true || minCheckBox.checked == true ){
										stackedChartCount = jQuery.parseJSON(data.StackedandLineCount);
										fetchCountPerDay(stackedChartCount, stDate, endDate);
									} 

									else {
										stackedChartCount = jQuery.parseJSON(data.StackedandLineCount);
										fetchCountPerDay(stackedChartCount, stDate, endDate);

									}

			                    }

						
			                  else if(id == 'lineChartApplyFilter'){
			                	  
									//Max Min are both checked
									if (maxCheckBox.checked == true && minCheckBox.checked == true ){

										var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
									  	var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

									  	fetchMaxMinLineChartCount(maxStackedandLineCount,minStackedandLineCount);
													
									} // end max-min both checked

									//MAX or MIN is checked
									else if (maxCheckBox.checked == true || minCheckBox.checked == true ){
										stackedChartCount = jQuery.parseJSON(data.StackedandLineCount);
										fetchLineChartCount(stackedChartCount);
									} 

									else {
										stackedChartCount = jQuery.parseJSON(data.StackedandLineCount);
										fetchLineChartCount(stackedChartCount);

									}
				                 
			                  }
	                        	 
			                  else if(id == 'countPieApplyFilter'){
			                		countPieChartData=data.chartData;
								  	PieCount(countPieChartData);
					  				
			                  }
			                 else if(id == 'regStatPieApplyFilter'){
				                      
				             	if(data.regStatusList.length > 0) {
				               		clientRegStatusList=jQuery.parseJSON(data.regStatusList);  
			                     	agentRegPie(clientRegStatusList);
			                    }

			                    else {
				                    agentRegPie(data.regStatusList);
					           }
	                        	 
			             }    	 
					 }

					 else{
						 alert("There is no data to display between these two selected dates for this site, or please check all the conditions selected in the upper side");
	     						   
	     			}


	  },
	  
	  error : function(error) {
			console.log("The error is " + error);
		}
   }); // end of first ajax
		}
              else if(selectedValue == "isBetween" && (minFltrdVal != '' || maxFltrdVal != '')){

            	  $.ajax({
      				type : "GET",
      				contentType: "application/json; charset=utf-8",
      				url : "${pageContext.request.contextPath}/FilteringMethodSIM",
      				dataType : "json",
      				data : {
      				  "startDate" : stDate,
					    "endDate" : endDate,
	                    "region": document.getElementById("Region").value,
	                    "period": $("#period").children("option:selected").val(),
					    "selectedValue" :selectedValue,
					    "countOption" : selectedSalesValue,
					    "minVal":(minFltrdVal == "")? 'null' : minFltrdVal ,
					    "maxVal":(maxFltrdVal == "")? 'null' : maxFltrdVal,
						"Max": maxChecked,
						"Min": minChecked,
	  					"filterID" : id,
	  					"allAgentsNameChart":allAgentsNameChart,
      				},

      				success : function(data) {
    					
   					 if (data != null) {
   					
                    	 if (id == 'countChartApplyFilter') {
                    		 countChartData=jQuery.parseJSON(data.msColumnChartObj);
                    		 AgentSales(countChartData);
	                	}
                    	 else if(id == 'stackedChartApplyFilter'){
								
							 stDate =  $("#startdate3").val();
                             endDate = $("#enddate3").val();
                             
							//Max Min are both checked
							if (maxCheckBox.checked == true && minCheckBox.checked == true ){
								var maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
							  	var minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);

							  	fetchMaxMinCountPerDay(maxStackedandLineCount,minStackedandLineCount,stDate, endDate);
								
											
							} // end max-min both checked

							//MAX or MIN is checked
							else if (maxCheckBox.checked == true || minCheckBox.checked == true ){

								stackedChartCount = jQuery.parseJSON(data.StackedandLineCount);
								fetchCountPerDay(stackedChartCount, stDate, endDate);
								
							} // end max OR min is checked 

							else {
								stackedChartCount = jQuery.parseJSON(data.StackedandLineCount);
								fetchCountPerDay(stackedChartCount, stDate, endDate);

							}

	                    }
                    	 else if(id == 'lineChartApplyFilter'){
			                  //Max Min are both checked
								if (maxCheckBox.checked == true && minCheckBox.checked == true ){
									var maxLineChartCount = jQuery.parseJSON(data.maxStackedandLineCount);
									var minLineChartCount = jQuery.parseJSON(data.minStackedandLineCount);

									fetchMaxMinLineChartCount(maxLineChartCount,minLineChartCount);
													
								} // end max-min both checked
								
								else if (maxCheckBox.checked == true && minCheckBox.checked == true ){

									 LineChartCount = jQuery.parseJSON(data.StackedandLineCount);   
			                    	 fetchLineChartCount(LineChartCount);			
								} // end max OR min is checked

								else {
					                      
									 LineChartCount = jQuery.parseJSON(data.StackedandLineCount);   
			                    	 fetchLineChartCount(LineChartCount);	
		                        }
		                  }
   					 }
   	     					else{
   	     						  alert("There is no data to display between these two selected dates for this site, or please check all the conditions selected in the upper side");
   	     						   
   	     					}
   	                         
   		                			
   		     					 

   				

   	  },
   	  
   	  error : function(error) {
   			console.log("The error is " + error);
   		}
      }); // end of second ajax
                  

	                }
		
			
			}); // end of apply filter fct

//Apply Filter for max and min StackedLine Chart
$('#maxStackedChartApplyFilter,#minStackedChartApplyFilter,#maxLineChartApplyFilter,#minLineChartApplyFilter').click(function(){
				var stDate,endDate;
				var id = this.id;
				
							
	if(id == 'maxStackedChartApplyFilter'){
		stDate =  $("#startdate7").val();
		endDate = $("#enddate7").val();		

		$('#maxStackedChartChangeOptions').attr('disabled', true);
		$('#maxStackedChartSimSalesOpt').attr('disabled', true);
		$('#maxValMaxStackedFltr').attr('disabled', true);
		$('#minValMaxStackedFltr').attr('disabled', true);
	  	      
	}

	else if(id == 'minStackedChartApplyFilter'){

		stDate =  $("#startdate8").val();
		endDate = $("#enddate8").val();		
	}

	else if(id == 'maxLineChartApplyFilter'){

		stDate =  $("#startdate9").val();
		endDate = $("#enddate9").val();		
	}

	else if(id == 'minLineChartApplyFilter'){

		stDate =  $("#startdate10").val();
		endDate = $("#enddate10").val();					
		}
	 //if(selectedValue == "null" || (selectedValue == "isBetween" && minFltrdVal == '' && maxFltrdVal == '')){

	  				$.ajax({
					type : "GET",
					contentType: "application/json; charset=utf-8",
					url : "${pageContext.request.contextPath}/FilteringMaxMinChart",
					dataType : "json",
					data : {
					    "startDate" :stDate,
					    "endDate" : endDate,
	                    "period": $("#period").children("option:selected").val(),
						"filterID" : id,
							
					},
					success : function(data) {
						
						 if (data != null) {

							 if(id == 'maxStackedChartApplyFilter'){						
								maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
								fetchMaxCountPerDay(maxStackedandLineCount, $("#startdate7").val(), $("#enddate7").val());
							}

							else if(id == 'minStackedChartApplyFilter'){
								minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);
	 							fetchMinCountPerDay(minStackedandLineCount, $("#startdate8").val(), $("#enddate8").val());
							}

							else if(id == 'maxLineChartApplyFilter'){
								maxStackedandLineCount = jQuery.parseJSON(data.maxStackedandLineCount);
								fetchMaxLineChartCount(maxStackedandLineCount);									
							}

							else if(id == 'minLineChartApplyFilter'){
								minStackedandLineCount = jQuery.parseJSON(data.minStackedandLineCount);									
								fetchMinLineChartCount(minStackedandLineCount);	
							} 

						 	else{
							 	alert("There is no data to display between these two selected dates for this site, or please check all the conditions selected in the upper side");
		     			 	}
						 }
			     	},

			     	error : function(error) {
					console.log("The error is " + error);
					}
		}); // end of ajax
	 //} 


				
}); // end of apply filter fct

		// clear filter of the chart
		$('#countChartClearFilter').click(function(){
		     // msColumnChartObj; 
			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
	 		$("#startdate2").val(s).trigger('change');
 			$("#enddate2").val(enddate).trigger('change');
 			
 			$("#countChartChangeOptions").val('null').trigger('change');
 			$('#agentMsChart').val('').trigger('change');
 			$("#minValMSFltr").val('');
 			$("#maxValMSFltr").val('');
 			$("#countChartSimSalesOpt").val('SIM_Sales').trigger('change');
 			$("#countChartFilterDiv").hide();

 			//Remove all tags
            $("#msChartAgentAutocomplete").tagsinput('removeAll');			
			// AllAgentsArr is equal to the internal array after removing a tag input
			$('#msChartAgentAutocomplete').on('itemRemoved', function(event) {						 
				msChartAllAgntsArr = $("#msChartAgentAutocomplete").data('tagsinput').itemsArray;
			}); 

 			var maxCheckBox = document.getElementById("Max");
			var minCheckBox = document.getElementById("Min");
				 
			 if (maxCheckBox.checked == true || minCheckBox.checked == true ){
  	 			$('#agentMsChart').attr('disabled', true);
             }
             else {
             	$('#agentMsChart').attr('disabled', false);
             }
             
 			AgentSales("");
			
			});

		$('#stackedChartClearFilter').click(function(){
			StackedandLineCount; 

			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
 			$("#startdate3").val(s).trigger('change');
 			$("#enddate3").val(enddate).trigger('change');
 			
 			$("#stackedChartChangeOptions").val('null').trigger('change');
 			$('#agentStackChart').val('').trigger('change');
 			$("#minValStackedFltr").val('');
 			$("#maxValStackedFltr").val('');
 			$("#stackedChartSimSalesOpt").val('SIM_SALES').trigger('change');
 			$("#stackedChartFilterDiv").hide();

 			//Remove all tags
            $("#stackChartAgentAutocomplete").tagsinput('removeAll');			
			// AllAgentsArr is equal to the internal array after removing a tag input
			$('#stackChartAgentAutocomplete').on('itemRemoved', function(event) {						 
				stackChartAllAgntsArr = $("#stackChartAgentAutocomplete").data('tagsinput').itemsArray;
			}); 
			
 			var maxCheckBox = document.getElementById("Max");
			var minCheckBox = document.getElementById("Min");
				 
			 if (maxCheckBox.checked == true || minCheckBox.checked == true ){
  	 			$('#agentStackChart').attr('disabled', true);
  	 			$('#stackedChartChangeOptions').attr('disabled', true);
  	 			
             }
             else {
             	$('#agentStackChart').attr('disabled', false);
             	$('#stackedChartChangeOptions').attr('disabled', false);
				
             }
             
			 fetchCountPerDay("",$("#startdate1").val(), $("#enddate1").val());
	 			

			
			});

		$('#maxStackedChartClearFilter').click(function(){
			StackedandLineCount; 

			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
 			 $("#startdate7").val(s).trigger('change');
 			 $("#enddate7").val(enddate).trigger('change');
 			$("#maxStackedChartChangeOptions").val('null').trigger('change');
 			$('#maxStackedChartAgentID').val('').trigger('change');
 			$("#minValMaxStackedFltr").val('');
 			$("#maxValMaxStackedFltr").val('');
 			$("#maxStackedChartSimSalesOpt").val('SIM_SALES').trigger('change');
 			$("#maxStackedChartFilterDiv").hide();
 			
 			$('#maxStackedChartAgentID').attr('disabled', true);

 			fetchMaxCountPerDay("",$("#startdate7").val(), $("#enddate7").val());
	 			

			
			});
		$('#minStackedChartClearFilter').click(function(){
			StackedandLineCount; 

			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
 			 $("#startdate8").val(s).trigger('change');
 			 $("#enddate8").val(enddate).trigger('change');
 			$("#minStackedChartChangeOptions").val('null').trigger('change');
 			$('#minStackedChartAgentID').val('').trigger('change');
 			$("#minValMinStackedFltr").val('');
 			$("#maxValMinStackedFltr").val('');
 			$("#minStackedChartSimSalesOpt").val('SIM_SALES').trigger('change');
 			$("#minStackedChartFilterDiv").hide();

 			$('#minStackedChartAgentID').attr('disabled', true);
 			

 			fetchMinCountPerDay("",$("#startdate8").val(), $("#enddate8").val());
	 					
			});
		
		$('#lineChartClearFilter').click(function(){
			//StackedandLineCount; 

			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
 			 $("#startdate4").val(s).trigger('change');
 			 $("#enddate4").val(enddate).trigger('change');
 			 
 			$("#lineChartChangeOptions").val('null').trigger('change');
 			$('#agentLineChart').val('').trigger('change');
 			$("#minValLineFltr").val('');
 			$("#maxValLineFltr").val('');
 			$("#lineChartSimSalesOpt").val('SIM_SALES').trigger('change');
 			$("#lineChartFilterDiv").hide();
 			
 			//Remove all tags
            $("#lineChartAgentAutocomplete").tagsinput('removeAll');			
			// AllAgentsArr is equal to the internal array after removing a tag input
			$('#lineChartAgentAutocomplete').on('itemRemoved', function(event) {						 
				lineChartAllAgntsArr = $("#lineChartAgentAutocomplete").data('tagsinput').itemsArray;
			}); 
			
 			var maxCheckBox = document.getElementById("Max");
			var minCheckBox = document.getElementById("Min");
				 
			 if (maxCheckBox.checked == true || minCheckBox.checked == true ){
  	 			$('#agentLineChart').attr('disabled', true);
	  	 		$('#lineChartChangeOptions').attr('disabled', true);
				
             }
             else {
             	$('#agentLineChart').attr('disabled', false);
	  	 		$('#lineChartChangeOptions').attr('disabled', false);
				
             }
 			
      	  fetchLineChartCount("");

			
			});

		$('#maxLineChartClearFilter').click(function(){
			
			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
 			 $("#startdate9").val(s).trigger('change');
 			 $("#enddate9").val(enddate).trigger('change');
 			$("#maxLineChartChangeOptions").val('null').trigger('change');
 			$('#maxLineChartAgentID').val('').trigger('change');
 			$("#minValMaxLineFltr").val('');
 			$("#maxValMaxLineFltr").val('');
 			$("#maxLineChartSimSalesOpt").val('SIM_SALES').trigger('change');
 			$("#maxLineChartFilterDiv").hide();

 			$('#maxLineChartAgentID').attr('disabled', false);
 			
 			
 			fetchMaxLineChartCount("");

			
			});
		$('#minLineChartClearFilter').click(function(){
			
			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
 			$("#startdate10").val(s).trigger('change');
 			$("#enddate10").val(enddate).trigger('change');
 			$("#minLineChartChangeOptions").val('null').trigger('change');
 			$('#minLineChartAgentID').val('').trigger('change');
 			$("#minValMinLineFltr").val('');
 			$("#maxValMinLineFltr").val('');
 			$("#minLineChartSimSalesOpt").val('SIM_SALES').trigger('change');
 			$("#minLineChartFilterDiv").hide();
 			
 			$('#minLineChartAgentID').attr('disabled', true);
 			
 			
 			fetchMinLineChartCount("");

			
			});
		$('#countPieClearFilter').click(function(){
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
 			 $("#startdate5").val(s).trigger('change');
 			 $("#enddate5").val(enddate).trigger('change');
 					
 			 $("#countPieChangeOptions").val('null').trigger('change');
 			 $('#agentPieChart').val('').trigger('change');
 			 $("#countPieSimSalesOpt").val('SIM_SALES').trigger('change');
 			 $("#countPieFilterDiv").hide();

  			//Remove all tags
             $("#pieChartAgentAutocomplete").tagsinput('removeAll');			
 			// AllAgentsArr is equal to the internal array after removing a tag input
 			$('#pieChartAgentAutocomplete').on('itemRemoved', function(event) {						 
 				pieChartAllAgntsArr = $("#pieChartAgentAutocomplete").data('tagsinput').itemsArray;
 			}); 
 			
 			var maxCheckBox = document.getElementById("Max");
			var minCheckBox = document.getElementById("Min");
				 
			 if (maxCheckBox.checked == true || minCheckBox.checked == true ){
  	 			$('#agentPieChart').attr('disabled', true);
             }
             else {
             	$('#agentPieChart').attr('disabled', false);
             }

 			PieCount("");
 			
			});

		$('#regStatPieClearFilter').click(function(){
			//regStatusList; 
		    
			var startdate = new Date();
			startdate.setDate(startdate.getDate() - 2);
			startdate.setHours( 0,0,0,0 );
			var s = Date.parse(startdate);
			
			var enddate = new Date();
			enddate.setDate(enddate.getDate() - 2);
			enddate = Date.parse(enddate);
 			 $("#startdate6").val(s).trigger('change');
 			 $("#enddate6").val(enddate).trigger('change');
 			 $("#regType").val('').trigger('change');
				
 			 $("#regStatusPiechangeOptions").val('null').trigger('change');
 			 $('#agentRegStatusChart').val('').trigger('change');
 			 $("#minValRegStatFltr").val('');
 			 $("#maxValRegStatFltr").val('');
 			 $("#regStatusPieSimSalesOpt").val('SIM_SALES').trigger('change');
 			 $("#regStatusPieFilterDiv").hide();

 			//Remove all tags
             $("#regStatusChartAgentAutocomplete").tagsinput('removeAll');			
 			// AllAgentsArr is equal to the internal array after removing a tag input
 			$('#regStatusChartAgentAutocomplete').on('itemRemoved', function(event) {						 
 				regStatusChartAllAgntsArr = $("#regStatusChartAgentAutocomplete").data('tagsinput').itemsArray;
 			}); 

 			var maxCheckBox = document.getElementById("Max");
			var minCheckBox = document.getElementById("Min");
				 
			 if (maxCheckBox.checked == true || minCheckBox.checked == true ){
  	 			$('#agentRegStatusChart').attr('disabled', true);
             }
             else {
             	$('#agentRegStatusChart').attr('disabled', false);
             }
			agentRegPie("");

 			
			
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
			// as image
			exportDivToImage("mapContainer");
		});

		$('#chartsExport').click(function(){
        	//as image
			
			if($('#time_count_stack').is(':visible')){exportDivToImage("time_count_stack");}
			if($('#time_count_line').is(':visible')){exportDivToImage("time_count_line");}
			if($('#countPieChart').is(':visible')){exportDivToImage("countPieChart");}
			if($('#agent_count_chart').is(':visible')){exportDivToImage("agent_count_chart");}
			if($('#regStatusPieChart').is(':visible')){exportDivToImage("regStatusPieChart");}
			if($('#maxTimeCountStack').is(':visible')){exportDivToImage("maxTimeCountStack");}
			if($('#minTimeCountStack').is(':visible')){exportDivToImage("minTimeCountStack");}
			if($('#maxTimeCountLine').is(':visible')){exportDivToImage("maxTimeCountLine");}
			if($('#minTimeCountLine').is(':visible')){exportDivToImage("minTimeCountLine");}
		});

        $('#allExport').click(function(){
            //as excel
            exportGrid();

			// as image
        	exportDivToImage("mapContainer");
        	if($('#time_count_stack').is(':visible')){exportDivToImage("time_count_stack");}
			if($('#time_count_line').is(':visible')){exportDivToImage("time_count_line");}
			if($('#countPieChart').is(':visible')){exportDivToImage("countPieChart");}
			if($('#agent_count_chart').is(':visible')){exportDivToImage("agent_count_chart");}
			if($('#regStatusPieChart').is(':visible')){exportDivToImage("regStatusPieChart");}
			if($('#maxTimeCountStack').is(':visible')){exportDivToImage("maxTimeCountStack");}
			if($('#minTimeCountStack').is(':visible')){exportDivToImage("minTimeCountStack");}
			if($('#maxTimeCountLine').is(':visible')){exportDivToImage("maxTimeCountLine");}
			if($('#minTimeCountLine').is(':visible')){exportDivToImage("minTimeCountLine");}
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

    	

        function exportDivToImage(div){
        	html2canvas(document.getElementById(div), {
                useCORS: true,
           }).then(function (canvas) {
            var imageURL = canvas.toDataURL("image/jpeg");
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
		
			
});
</script>
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
</html>
