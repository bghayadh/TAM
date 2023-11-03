<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.6.0/dist/leaflet.js"></script>
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet.draw/0.4.2/leaflet.draw.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet.draw/0.4.2/leaflet.draw.js"></script>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
<link
	href="${pageContext.request.contextPath}/resources/css/all.min.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/ListView.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
<title>map</title>


<style>
#mapid {
 height: 600px; 
width: 900px;
 padding:30px 20px;
  text-align:center;
}
.tabs {
  width:800px;
  height:500px;
  overflow:hidden;
  
}

.tabs .tab-header {
  height:20px;
  display:flex;
  align-items:center;
}
.tabs .tab-header > div {
  width: calc(100% / 2);
  text-align:center;
  color:#888;
  font-weight:600;
  cursor:pointer;
  font-size:14px;
  text-transform:uppercase;
  outline:none;
}
.tabs .tab-header > div > i {
  display:block;
  margin-bottom:5px;
}
.tabs .tab-header > div.active1 {
  color:#00acee;
}
.tabs .tab-indicator {
  top: 3px;
  position:relative;
  width:100% ;
  height:1px;
  background:#00acee;
  left:0px;
  border-radius:5px;

}
.tabs .tab-body {
  position:relative;
  height:calc(100% - 60px);
  padding:10px 5px;
}
.tabs .tab-body > div {
  position:absolute;
  top:-200%;
  opacity:0;
  transform:scale(0.9);
  transition:opacity 500ms ease-in-out 0ms,
    transform 500ms ease-in-out 0ms;
}
.tabs .tab-body > div.active1 {
  top:0px;
  opacity:1;
  transform:scale(1);
}





.pressed{
background-color: #20B2AA; 
outline: #20B2AA;
  color:#f5f5f5;
  border:2px solid #111;
  font-size:12px;

  outline:none;
  border-radius:7px;
  cursor:pointer;
}
.SomeCheck{
background-color: #D3D3D3; 
outline: #fff;
  color:#111;
  border:2px solid #111;
  font-size:12px;

  outline:none;
  border-radius:7px;
  cursor:pointer;
}

.supBtn button:hover {
  color:#fff;
  background:#20B2AA;
}
.wrap{
  width: 500px;
  height: 400px;
  border : 2px solid gray;
}
.draggable{
 width: 900px;
  height: 500px;
  z-index: 1;
  top:50%;
  left:50%;
  transform:translate(-50%,-50%);
  box-shadow: 5px 10px 5px #ccc;
}

.suggestions {
  border-top: 2px solid #999;
  background: #fff;
  border-radius:10px;
}
.suggestions > div {
  padding: 10px;
  font-size: 16px;
  color: #111;
  border-top: 1px solid #666;
  cursor: pointer;
}
.suggestions > div:hover {
  background-color: #20B2AA;
  border-radius:10px;
  color: #fff;
}




.cb {
  display: none;
}
label {
  display: inline-block;
  position: relative;
  padding-left: 20px;
  font-size: 16px;
  line-height: 20px;
}
label:before {
  line-height: 20px;
  content: "";
  display: inline-block;
  width: 16px;
  height: 16px;
  position: absolute;
  left: 0;
  background-color: #ffffff;
  border: 1px solid #20B2AA;
}
input[type=checkbox]:checked + label:before,
label:hover:before {
  content: "\2713";
  color: #20B2AA;
  text-align: center;
  line-height: 16px;
}






body {
  
  /* font-family:"Raleway"; */
}
.center {
    position: absolute;
    z-index: 0;
  top:50%;
  left:50%;
  transform:translate(-50%,-50%);
}

.popup {
  width:900px;
  height:450px;
  padding:30px 20px;
  background:#f5f5f5;
  border-radius:10px;
  box-sizing:border-box;
  text-align:center;
  opacity:0;
  top:-200%;
  left:450px; 
  box-shadow: 5px 10px 5px #ccc;
  transform:translate(-50%,-50%) scale(0.5);
  transition: opacity 300ms ease-in-out,
              top 1000ms ease-in-out,
              transform 1000ms ease-in-out;
}
.popup.active {
  opacity:1;
  top:50%;
  transform:translate(-50%,-50%) scale(1);
  transition: transform 300ms cubic-bezier(0.18,0.89,0.43,1.19);
}
.popup .icon {
  margin:5px 0px;
}
.popup .icon i.fa {
  font-size:30px;
  color:#34f234;
} 
.popup .title {
  margin:5px 0px;
  font-size:30px;
  font-weight:600;
}
.popup .description {
  color:#222;
  font-size:15px;
  padding:5px;
}
.popup .dismiss-btn {
  margin-top:15px;
}
.popup .clearall-btn {
  margin-top:15px;
}
.popup .apply-btn {
  margin-top:15px;
}
.popup .dismiss-btn button {
  padding:8px 15px;
  background:#111;
  color:#f5f5f5;
  border:2px solid #111;
  font-size:14px;
  font-weight:600;
  outline:none;
  border-radius:10px;
  cursor:pointer;
  transition: all 300ms ease-in-out;
}
.popup .clear-btn button {
  padding:4px 8px;
  background:#f5f5f5;
  color:#111;
  border:1px solid #111;
  font-size:12px;
  outline: #20B2AA;
  border-radius:7px;
  cursor:pointer;
  
}
.popup .clearall-btn button {
  padding:8px 15px;
  background:#f5f5f5;
  color:#111;
  border:2px solid #111;
  font-size:14px;
  font-weight:600;
  outline: #20B2AA;
  border-radius:10px;
  outline:none;
  cursor:pointer;
  transition: all 300ms ease-in-out;
}
.popup .apply-btn button {
  padding:8px 15px;
  background:#fff;
  color:#20B2AA;
  border:2px solid #20B2AA;
  font-size:14px;
  font-weight:600;
  outline:none;
  border-radius:10px;
  cursor:pointer;
  transition: all 300ms ease-in-out;
}
.popup .row .search-btn button {
  padding:8px 15px;
  background:#20B2AA;
  color:#fff;
  border:1px solid #111;
  font-size:12px;
  font-weight:400;
  outline:none;
  border-radius:5px;
  cursor:pointer;
  transition: all 300ms ease-in-out;
}
.popup .dismiss-btn button:hover {
  color:#111;
  background:#f5f5f5;
}
.popup .clear-btn button:hover {
  color:#f5f5f5;
  background:#111;
}
.popup .clearall-btn button:hover {
  color:#f5f5f5;
  background:#111;
}
.popup .apply-btn button:hover {
  color:#34f234;
  background:#111;
}
.popup > div {
  position:relative;
  top:10px;
  opacity:0;
}
.popup.active > div {
  top:0px;
  opacity:1;
}
.popup.active .icon {
  transition: all 300ms ease-in-out 250ms;
}
.popup.active .title {
  transition: all 300ms ease-in-out 300ms;
}
.popup.active .description {
  transition: all 300ms ease-in-out 350ms;
}
.popup.active .dismiss-btn {
  transition: all 300ms ease-in-out 400ms;
}
.popup.active .apply-btn {
  transition: all 300ms ease-in-out 400ms;
}
.tree {
	background:
		url("${pageContext.request.contextPath}/resources/images/folder.png");
	background-repeat: no-repeat;
	background-position: 2px 2px;
	width: 40px;
	height: 40px;
	background-size: 35px;
}

.gis {
	background:
		url("${pageContext.request.contextPath}/resources/images/gis3.jpg");
	background-repeat: no-repeat;
	width: 40px;
	height: 40px;
	background-size: cover;
}

ul, #myUL {
	list-style-type: none;
}

#myUL {
	margin: 0;
	padding: 0;
}

.caret {
	cursor: pointer;
	-webkit-user-select: none; /* Safari 3.1+ */
	-moz-user-select: none; /* Firefox 2+ */
	-ms-user-select: none; /* IE 10+ */
	user-select: none;
}

.caret::before {
	content: "\25B6";
	color: black;
	display: inline-block;
	margin-right: 6px;
}

.caret-down::before {
	-ms-transform: rotate(90deg); /* IE 9 */
	-webkit-transform: rotate(90deg); /* Safari */ '
	transform: rotate(90deg);
}

.nested {
	display: none;
}

.selected {
	display: block;
}

          
        .input-icons { 
            width: 100%; 
            
        } 
          
          
        .input-field { 
        
            width: 100%; 
            padding: 10px;  
            text-align: left;
        }
</style>
</head>
<body>
<%-- 	<c:set var = "page" value = "network"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="network" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>

	<p></p>
	<div class="container-fluid">


		<div class="row second">
		
			<div class="col-md-4">
			
			
	
         <div class="input-icons">
           
            
								<input type="text" id="autocompliteSearch"
								style=" width:475px; height:40px; background: #fff; border: none;"
								class="input-field"
								placeholder="Search here.." />

				
				</div>
				
			</div>
			<div class="col-md-5"></div>
			<div class="col-md-3" style="text-align: right;">
				<div class="btn-group pull-right">
					<div class="glyph">


						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search" id="open-popup-btn">
							<i class="fa fa-search"></i>
						</button>
						<button type="button" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title=" GIS"
							style="background: #da6815;">
							<i class="fas fa-map-marked-alt"></i>
						</button>


						<button type="button" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title=" Folder Tree">
						<i class="fas fa-sitemap"></i><!-- <i class="far fa-folder-open"></i>  -->
						</button>
						<button type="button" id="Fview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="Form View">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="List View"
							>
							<i class="fa fa-bars"></i>
						</button>


					</div>
				</div>
			</div>
		</div>
	</div>
	<p></p>
	<div class="container-fluid">
	<div class="card card-primary card-tabs cards-margin">
					<div class="card-body">
		<div class="row" style="z-index: 0; text-align: center;">
			
			
			<div class="col-md-2"></div>
			<div class="col-md-9" style="z-index: 0; text-align: center;">
				
				
				<div id="mapid"></div>
				
					</div>
				</div>
				<div class="row">
				
				
					<div class="col-md-2">
					<button id="convert"> Simpan GeoJson </button></div>
					<div class="col-md-6"><div id='result' value=''></div>
					</div>
				</div>
				</div>
			
		</div>
	</div>

	<!-- ************************************** popup **************************************-->



  <!-- ************************************** popup **************************************-->
  

  
  <!--********************************* tabs ********************************* -->
<div class="container-fluid " style="top: -350px;">
<div class="row">
  <div class="col-md-12">
  <div class="tabs popup draggable">
<div class="tab-header">
  <div class="active1 tabsSp">
      <i class="fa fa-search"></i> Search
    </div>
    <div class="tabsSp">
      <i class="fa fa-search"></i> Advanced Search
    </div>
</div>

    <div class="tab-indicator"></div>
    
	<div class="tab-body" style="top: 10px; padding: 20px;">   
    	<div class="active1 mTabs">
   
   
  <div class="row icon">
  		<div class="col-xs-1 supBtn">
   		 <button id="popupSuppBtn" style="height: 30px;">Supplier</button>
    </div>
    
    <div class="col-md-1 supBtn">
    <button id="popupItmBtn" style="height: 30px;">Item</button>
    </div>
 
    <div class="col-md-5">
    <div class="row">
    	<div class="col-md-5 supBtn">
    
    <button id="popupCheckBtn" style="height: 25px;">Site/Node/Cell</button>
    		</div>
    	</div>
    <div class="row">
    <div class="col-xs-2" style="top: 2px;">    
    <input type="checkbox" id="checksite" name="site" value="site" class="cb">
    <label for="checksite" style="font-size: 11px;">Site</label>
    </div>
    <div class="col-xs-2" style="top: 2px;">
	<input id="checknode" type="checkbox" name="node" value="node" class="cb" >
    <label for="checknode" style="font-size: 11px;">Node</label>
    </div>
    <div class="col-xs-2" style="top: 2px;">
    <input id="checkcell" type="checkbox" name="cell" value="cell" class="cb" >
    <label for="checkcell" style="font-size: 11px;">Cell</label>
    </div>
 
    	</div>
    </div>
    <div class="col-md-3" style="text-align: right; top: 0px;">
				<div class="btn-group pull-right">
					<div class="glyph">
       <button type="button" class="btn btn-light tree"
							data-toggle="tooltip" data-placement="top" title=" Folder Tree">

						</button>
						<button type="button" id="Fview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="Form View">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="List View"
							style="background: #da6815;">
							<i class="fa fa-bars"></i>
						</button>
							</div>
					</div>
			</div>

  </div>
  <p></p>
  <div class="title">
    Search with other choices
  </div>
  <p></p>
  <div class="description">
    
 <div class="row">
			
			  <div class="col-xs-1 clear-btn">
			    <button id="clearWare">
			     clear
			    </button>
    			</div>
			
			<div class="col-md-3">
					
						<div class="input-group-prepend">
							
							<input type="text" id="wareid" value="${WareId}"  style="width:350px;" class="ui-widget ui-widget-content ui-corner-all" placeholder="WareHouse ID.."/>
						</div>
					
			</div>
			
			&nbsp
			 <div class="col-md-4">
					
						<div class="input-group-prepend">							
							<input placeholder="Warehouse Name" type="text" id="warename" value="${WareName}" style="width:500px;"  class="ui-widget ui-widget-content ui-corner-all" />
						</div>
					
			</div>
			
			
			&nbsp
			 <div class="col-md-4">
					
						<div class="input-group-prepend">							
							<input placeholder="Site ID" type="text" id="siteid" value="${SiteId}" style="width:450px;" class="ui-widget ui-widget-content ui-corner-all" />
						</div>
					
			</div>
	

	
	
 </div>
 <p></p>

	<!--************************************* 2 row ********************************** -->
	<div class="row">
  <div class="col-xs-1 clear-btn">
			    <button id="clearItm">
			     clear
			    </button>
    			</div>
<div class="col-md-3">
					
						<div class="input-group-prepend">
							
							<input placeholder="Item code.." type="text" id="itmCode" value="${ItmCode}"  style="width:350px;" class="ui-widget ui-widget-content ui-corner-all" />
						</div>
					
			</div>
			
			&nbsp
			 <div class="col-md-4">
					
						<div class="input-group-prepend">							
							<input placeholder="Item model" type="text" id="itmModel" value="${ItmModel}" style="width:500px;"  class="ui-widget ui-widget-content ui-corner-all" />
						</div>
				
			</div>
			
			
			&nbsp
			 <div class="col-md-4">
					
						<div class="input-group-prepend">							
							<input placeholder="Item part Number" type="text" id="itmPartNo" value="${ItmPartNo}" style="width:450px;" class="ui-widget ui-widget-content ui-corner-all" />
						</div>
					
			</div>	
 </div>
 <p></p>
  <!-- ***************************** 3 row ***************************** -->
    <div class="row">
     <div class="col-xs-1 clear-btn">
			    <button id="clearSupp">
			     clear
			    </button>
    			</div>
			<div class="col-md-3">
					
						<div class="input-group-prepend">
							<input placeholder="Supplier ID" type="text" id="prsuppid" value="${prsupplierID}"  style="width:350px"  class="ui-widget ui-widget-content ui-corner-all" />
						</div>
					
			</div>
			&nbsp
			<div class="col-md-4">
						<div class="input-group-prepend">
							
							<input placeholder="Supplier Name" type="text" id="supplierName" value="${supplier}" style="width:500px"class="ui-widget ui-widget-content ui-corner-all"/>
						</div>							
			</div>
	</div>
	<p></p>
    
  </div>
  
  <div class="row dismiss-btn">
  
  <div class="col-xs-2 dismiss-btn">
    <button id="dismiss-popup-btn">
      Dismiss
    </button>
    </div>
   &nbsp &nbsp
    <div class="col-xs-2 apply-btn">
    <button>Apply</button>
    </div>
    &nbsp &nbsp
    <div class="col-xs-2 clearall-btn">
    <button id="clearAll">Clear All</button>
    </div>
  </div>
   
		</div>

 <!--********************************* tabs 222 ********************************* --> 
	<div class="mTabs row icon" style="width: 100%">
	<div class="col-md-3"></div>
    <div class="col-md-3" style="text-align: right;">
				<div class="btn-group pull-right">
					<div class="glyph">
       						<button type="button" class="btn btn-light tree"
							data-toggle="tooltip" data-placement="top" title=" Folder Tree">

						</button>
						<button type="button" id="Fview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="Form View">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="List View"
							style="background: #da6815;">
							<i class="fa fa-bars"></i>
						</button>
							</div>
					</div>
			</div>

	</div>
 <!--********************************* tabs 222 ********************************* --> 

	</div> 
</div>
</div>
 </div>
</div>   		
	

	<!-- ******************************************************************* -->
</body>
<script type="text/javascript">
let tabHeader = document.getElementsByClassName("tab-header")[0];
let tabIndicator = document.getElementsByClassName("tab-indicator")[0];
let tabBody = document.getElementsByClassName("tab-body")[0];

//let tabsPane = document.getElementsByName("tabsSp");
//let tabsPane = tabHeader.getElementsByTagName("div");
let tabsPane = document.getElementsByClassName("tabsSp");

for(let i=0;i<tabsPane.length;i++){
	console.log("pane "+tabsPane.length);
	console.log("tabs "+i);
  	tabsPane[i].addEventListener("click",function(){
    tabHeader.getElementsByClassName("active1")[0].classList.remove("active1");
    
    tabsPane[i].classList.add("active1");
    tabBody.getElementsByClassName("active1")[0].classList.remove("active1");
    document.getElementsByClassName("mTabs")[i].classList.add("active1");
    
   // tabIndicator.style.left = `calc(calc(100% / 2) * ${i})`;
	   //document.getElementById("indi").style.left = `calc(calc(100% / 2) * ${i})`+"%";
  });
}
$("#checksite").change(  function() {						
	var cnode = $('#checknode').is(':checked');
	var ccell = $('#checkcell').is(':checked');
	var csite = $('#checksite').is(':checked');
	if(csite == true){
		console.log("site true")
		if(cnode == true && ccell == true){
			console.log("all true");
			$("#popupCheckBtn").toggleClass("pressed");
			$("#popupCheckBtn").toggleClass("SomeCheck");
		}else{ if(cnode == false && ccell == false){
			console.log("all false");
			$("#popupCheckBtn").toggleClass("SomeCheck");
			}
			}
	}else{
		if(cnode == false && ccell == false){
			console.log("site all f");
			$("#popupCheckBtn").toggleClass("SomeCheck");
			}else{
				if(cnode == true && ccell == true){
					console.log("site f all t");
					$("#popupCheckBtn").toggleClass("pressed");
					$("#popupCheckBtn").toggleClass("SomeCheck");
					}
				}
		}
	});

$("#checknode").change(  function() {						
	var cnode = $('#checknode').is(':checked');
	var ccell = $('#checkcell').is(':checked');
	var csite = $('#checksite').is(':checked');
	if(cnode == true){
		if(csite == true && ccell == true){
			$("#popupCheckBtn").toggleClass("pressed");
			$("#popupCheckBtn").toggleClass("SomeCheck");
		}else{ if(csite == false && ccell == false){
			$("#popupCheckBtn").toggleClass("SomeCheck");
			}
			}
	}else{
		if(csite == false && ccell == false){
			$("#popupCheckBtn").toggleClass("SomeCheck");
			}else{
				if(csite == true && ccell == true){
					$("#popupCheckBtn").toggleClass("pressed");
					$("#popupCheckBtn").toggleClass("SomeCheck");
					}
				}
		}
	});
$("#checkcell").change(  function() {						
	var cnode = $('#checknode').is(':checked');
	var ccell = $('#checkcell').is(':checked');
	var csite = $('#checksite').is(':checked');
	if(cnode == true){
		if(csite == true && cnode == true){
			$("#popupCheckBtn").toggleClass("pressed");
			$("#popupCheckBtn").toggleClass("SomeCheck");
		}else{ if(csite == false && cnode == false){
			$("#popupCheckBtn").toggleClass("SomeCheck");
			}
			}
	}else{
		if(csite == false && cnode == false){
			$("#popupCheckBtn").toggleClass("SomeCheck");
			}else{
				if(csite == true && cnode == true){
					$("#popupCheckBtn").toggleClass("pressed");
					$("#popupCheckBtn").toggleClass("SomeCheck");
					}
				}
		}
	});

$("#clearAll").click(  function() {
	console.log("clear");
	 $("#wareid").val('');
	 $("#warename").val('');
	 $("#siteid").val('');
	 $("#itmCode").val('');
	 $("#itmModel").val('');
	 $("#itmPartNo").val('');
	 $("#prsuppid").val('');
	 $("#supplierName").val('');
	});

$("#clearWare").click(  function() {
	console.log("clear");
	$("#wareid").val('');
	 $("#warename").val('');
	 $("#siteid").val('');
	});
$("#clearItm").click(  function() {
	console.log("clear");
	$("#itmCode").val('');
	 $("#itmModel").val('');
	 $("#itmPartNo").val('');
	});
$("#clearSupp").click(  function() {
	console.log("clear");
	 $("#prsuppid").val('');
		$("#supplierName").val('');
	});
$("#popupSuppBtn").click(  function() {
	console.log("toggle class");
	$(this).toggleClass("pressed");
	});

$("#popupItmBtn").click(  function() {
	console.log("toggle class");
	$(this).toggleClass("pressed");
	});
var nodeCheck= false;
$("#popupCheckBtn").click(  function() {
	console.log("toggle class");
	$(this).toggleClass("pressed");
	var cnode = $('#checknode').is(':checked');
	var ccell = $('#checkcell').is(':checked');
	var csite = $('#checksite').is(':checked');
	if(cnode == true && ccell == true && csite == true){
		nodeCheck = false;
		$("#checksite").prop('checked', false);
		$("#checknode").prop('checked', false);
		$("#checkcell").prop('checked', false);
		}else
	if(nodeCheck){
		nodeCheck = false;
		$("#checksite").prop('checked', false);
		$("#checknode").prop('checked', false);
		$("#checkcell").prop('checked', false);
		}else{
			nodeCheck = true;
	$("#checksite").prop('checked', true);
	$("#checknode").prop('checked', true);
	$("#checkcell").prop('checked', true);
		}
	});
 $(function(){
        $('.draggable').draggable({
          revertDuration: 1000,
          start : function(event , ui){
        	  //ui.position.left = Math.min( 100, ui.position.left );
        	  //$(this).css('top','50%');
            
           // $(this).css({transform: "none", top: $(this).offset().top+"px", left:$(this).offset().left+"1000 px"});
          },
          drag : function(event, ui){
           // $('h1').html(ui.position.left + ' ' + ui.position.top);
        	  $(this).css({'background-color': '#C9D5D5'});
          }
          ,
          stop : function(event, ui){
            $(this).css('background-color','#f5f5f5');
          }
        });
      });
var mymap = L.map('mapid').setView([ 33.8547, 35.8623 ], 8);
//var mymap = L.map('mapid', {drawControl: true}).setView([33.8547, 35.8623], 8);
var marker = [];
var markersLayer = new L.LayerGroup();
$("#popupApply").click(function() {
	// if filtering come from popup
					console.log("lv clicked");
					document.getElementsByClassName("popup")[0].classList
					.remove("active");
					//************************** select respect to warehouse ***************************
					var wareid = $("#wareid").val();
					var warename = $("#warename").val();
					var siteid = $("#siteid").val();
					var prsuppid = $("#prsuppid").val();
					var supplierName = $("#supplierName").val();
					var itmCode = $("#itmCode").val();
					var itmModel = $("#itmModel").val();
					if(wareid != '' && warename != '' ){						
						console.log("warehouse search");
						searchFetch(wareid,null,null);
						}
					//******************************************************************************
					else{
						if(supplierName != '' && prsuppid !=''){
							searchFetch(null,prsuppid,null);
							console.log("supp search");
							}else{
								if(itmCode != '' && itmModel !=''){
									searchFetch(null,null,itmCode);
									}
								}
						}
				});

function searchFetch(wareid,prsuppid,itmCode){	
	var from;
	var id;
	if(wareid != null){
		from = "warehouse";
		id = wareid;
		}
	if(prsuppid != null){
		from = "supplier";
		id = prsuppid;
		}
	if(itmCode != null){
		itmCode = itmCode.split(":");
		id = itmCode[0];
		console.log(id);
		from = "item";
		}
	$.ajax({
		type : "GET",
		contentType : "application/json; charset=utf-8",
		url : '${pageContext.request.contextPath}/getSearchMapsLocations',
		data : {
			"from": from,
			"id":id
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				if(data.from == "warehouse"){
					setSearchMap(data.from,data.maps);
					}else{
						if(data.from == "supplier"){
							setSearchMap(data.from,data.maps);
							}else{
								if(data.from == "item"){
									setSearchMap(data.from,data.maps);
									}
								}
						}
			}
		},
		error : function(result) {
			alert("Error");
		}
	});
}
function setSearchMap(from,array) {

	//var marker = L.marker([34.40464357107094,36.123046875]).addTo(mymap);	
	//mymap.off();
	//markersLayer.clearLayers();
	//mymap.removeLayer(marker);
	for(i=0;i<marker.length;i++) {
    mymap.removeLayer(marker[i]);
    }
	if (array.length != 0) {
		if(from == "warehouse"){
					for (var i = 0; i < array.length; i++) {						
						var singleM = L.marker([ array[2], array[3] ]).addTo(mymap);
						singleM.bindPopup("<b>Warehouse id: " + array[0]
						+ " !</b><br> Warehouse name: " + array[1] +"");
						
						marker.push(singleM);
					
					}
		
				}else{
					if(from == "supplier"){
						console.log(array.length);
						console.log(array.valueOf());
						for (var i = 0; i < array.length; i++) {						
							var singleM = L.marker([ array[i][3], array[i][4] ]).addTo(mymap);
							singleM.bindPopup("<b>Supplier id: " + array[i][0]
							+ " !</b><br>Supplier name: " + array[i][1] +"<br> Site id: " + array[i][2] +"");
							
							marker.push(singleM);
						
						}
						}else{
							if(from == "item"){
								for (var i = 0; i < array.length; i++) {						
									var singleM = L.marker([ array[i][4], array[i][5] ]).addTo(mymap);
									singleM.bindPopup("<b>Item Code: " + array[i][0]
									+ " !</b><br>Item name: " + array[i][1] +"<br>Supplier name: " + array[i][2] +"<br> Site id: " + array[i][3] +"");
									
									marker.push(singleM);
								
								}
								}
							}
					}
			
	} else {
		marker = L.marker([ 33.8547, 35.8623 ]).addTo(mymap);
		marker.bindPopup("<b>Nothing to show!</b><br>");
	}
	
	}
	var maps = [];

	
	var popOpen = false;
	document.getElementById("open-popup-btn").addEventListener("click",function(){
		  
		  if(popOpen){
			 
			 // $("#open-popup-btn").css('background', '#FFF');			
			  document.getElementsByClassName("popup")[0].classList.remove("active");	
			  popOpen = false;
			  
				  var styleObject = $('#open-popup-btn').prop('style'); 

				  styleObject.removeProperty('background-color');
				 // $("#open-popup-btn").removeClass("search_Toggle");
				  $("#open-popup-btn").toggleClass("btn-light");
				  $("#open-popup-btn").toggleClass("btn-danger");
				  document.getElementById("open-popup-btn").blur();		 
			  
			  }else{
				  
				  //$("#open-popup-btn").addClass("search_Toggle");
				  document.getElementsByClassName("popup")[0].classList.add("active");	
				  //$("#open-popup-btn").toggleClass("search_Toggle");			  
				  $("#open-popup-btn").toggleClass("btn-light");
				  $("#open-popup-btn").toggleClass("btn-danger");
				  $("#open-popup-btn").css('background', '#da6815');
				  popOpen = true;
				  }
		  
		});

	document.getElementById("dismiss-popup-btn").addEventListener("click",function() {
				document.getElementsByClassName("popup")[0].classList
						.remove("active");
				var styleObject2 = $('#open-popup-btn').prop('style');
				styleObject2.removeProperty('background-color');
				  //$("#open-popup-btn").removeClass("search_Toggle");
				  $("#open-popup-btn").toggleClass("btn-light");
				  $("#open-popup-btn").toggleClass("btn-danger");
				  popOpen = false;
			});

	

	
	$("#Lview").click(function() {
		console.log("lv clicked");
		$.ajax({
					type : "GET",
					contentType : "application/json; charset=utf-8",
					url : '${pageContext.request.contextPath}/getMapsSuppliersLocations',
					data : {

					},
					dataType : "json",
					success : function(data) {
						if (data != null) {

						}
					},
					error : function(result) {
						alert("Error");
					}
				});
	});

	$(document).ready(function() {

							$.ajax({
								type : "GET",
								contentType : "application/json; charset=utf-8",
								url : '${pageContext.request.contextPath}/getMapsLocations',
								data : {

								},
								dataType : "json",
								success : function(data) {
									if (data != null) {
										maps = data.maps;
									}
									setMap();
								},
								error : function(result) {
									alert("Error");
								}
							});
						
						async function setMap() {
							
							//https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}
							//https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png
							L.tileLayer(
											'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
											{
												attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
												//attribution : '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
												
											}).addTo(mymap);

							//var marker = L.marker([34.40464357107094,36.123046875]).addTo(mymap);
							var drawnItems = new L.FeatureGroup();
						     mymap.addLayer(drawnItems);
						     var drawControl = new L.Control.Draw({
						         edit: {
						             featureGroup: drawnItems
						         }
						     });
						     mymap.addControl(drawControl);

						     mymap.on('draw:created', function (event) {
						    	    var layer = event.layer,
						    	        feature = layer.feature = layer.feature || {};
						    	    
						    	    feature.type = feature.type || "Feature";
						    	    var props = feature.properties = feature.properties || {};
						    	    drawnItems.addLayer(layer);
						    	    
						    	});

						     document.getElementById("convert").addEventListener("click", function () {
						    	    var hasil = $('#result').html(JSON.stringify(drawnItems.toGeoJSON()));
						    	    var cookieValue = document.getElementById('result').innerHTML;
						    	    if (cookieValue === '{"type":"FeatureCollection","features":[]}') {
						    	      alert("Empty...!");
						    	    } else {
						    	    	ajax_simpan();
						    	    }
						    	});
						     
							if (maps.length != 0) {
								var singleM ;
								if (maps.length <= 10) {
									var myIcon;
									var image;
									for (var i = 0; i < maps.length; i++) {		
										image = "marker"+(i+1);				
										myIcon = L.icon({								
										    iconUrl: '${pageContext.request.contextPath}/resources/markers/'+image+'.png',
										    iconSize: [70, 85],
										    iconAnchor: [22, 94],
										    popupAnchor: [-3, -76],	
										   									  
										});
									   singleM = L.marker([ maps[i][2], maps[i][3] ], {icon: myIcon},{riseOnHover: true}).addTo(mymap);
										singleM.bindPopup("<b>Site id: " + maps[i][0]
												+ " !</b><br> site name: " + maps[i][1] + " !").on('mouseover', function(e) {
												    this.openPopup();
												    this.setOpacity(1.0);
												  })
												  .on('mouseout', function(e) {
												    this.closePopup();
												    this.setOpacity(0.5)
												  }).on('click', function(e){
													  if(mymap.getZoom() < 10){
													  mymap.setView( e.latlng , 10);
													  }else{
														  mymap.setView( e.latlng , 8);
														  }
													  });
										marker.push(singleM);
									}
									}else{
								for (var i = 0; i < maps.length; i++) {
									singleM = L.marker([ maps[i][2], maps[i][3] ]).addTo(mymap);
									singleM.bindPopup("<b>Site id: " + maps[i][0]
											+ " !</b><br> site name: " + maps[i][1] + " !") .on('mouseover', function(e) {
											    this.openPopup();
											    this.setOpacity(1.0);
											  })
											  .on('mouseout', function(e) {
											    this.closePopup();
											    this.setOpacity(0.5)
											  });
									marker.push(singleM);
								}
								}
							} else {
								marker = L.marker([ 33.8547, 35.8623 ]).addTo(mymap);
								marker.bindPopup("<b>No Site!</b><br>");
							}
							/*var circle = L.circle([51.508, -0.11], {
							    color: 'red',
							    fillColor: '#f03',
							    fillOpacity: 0.5,
							    radius: 500
							}).addTo(mymap);*/
							function onMapClick(e) {
							   //alert("You clicked the map at " + e.latlng);
								//L.map('mapid').setView( e.latlng, 10);
							}

							mymap.on('click', onMapClick);
						}
						//************************************* autocomplite search code *************************************

						const suggestionsPanel = document
								.querySelector('.suggestions');
						var searchkey = $("#autocompliteSearch").val();

						$("#autocompliteSearch").autocomplete(
										{

											source : function(request, response) {
												var searchkey = $(
														"#autocompliteSearch")
														.val();
												$.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetSearchEngine',
															data : {
																"searchkey" : searchkey,
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.listSearch);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 1,
											maxShowItems : 10,
											scroll : true,
											autoFocus : true

										});
						//*************************************************************************************
						$("#wareid").autocomplete(
										{

											source : function(request, response) {

												var siteId = $("#siteid").val();
												var wareName = $("#warename")
														.val();

												$.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {
																"WareName" : wareName,
																"warehouseName" : $(
																		"#wareid")
																		.val(),
																"SiteId" : siteId,
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListItemWarehouse);
																	//console.log('data is ;'+ data.ListItemWarehouse);
																	//colors = data.ListItemCategory;
																	//console.log('colors ;'+ colors);

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {
												wareid.value = (ui.item ? ui.item[0]
														: '');
												warename.value = ui.item[1];
												siteid.value = ui.item[2];

												return false;
											}

										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+
											//item[0] + "</span><br><span class='desc'>" +
											// item[1] + "</span><br><span class='desc'>" +
											//item[2] + "</span></div>")
											item[0]
											+ "</span><br><span class='desc'>"
											+ item[1] + ', ' + item[2]
											+ "</span></div>").appendTo(ul);
						};
						$("#wareid").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						//*************************************************************************************

						$("#siteid").autocomplete(
										{
											showHeader : true,

											source : function(request, response) {

												var warehouse = $("#wareid")
														.val();
												var warehouseName = $(
														"#warename").val();

												$.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {
																  "warehouseName" : $(
																	"#siteid")
																	.val(),				 
															// It will be modified

															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {

																	response(data.ListItemWarehouse);
																	// it will have some modification here 
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {

												siteid.value = (ui.item ? ui.item[2]
														: '');

												$.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {
																warehouseName : ui.item[1],
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {

																	console

																	WareId = data.ListItemWarehouse[2];

																	if (data.ListItemWarehouse.length == 1) {
																		console
																				.log("/*/*Entered here");

																		$(
																				"#wareid")
																				.val(
																						WareId);
																		warename.value = ui.item[1];

																	}

																	else {

																		$(
																				"#wareid")
																				.val(
																						"");

																	}

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});

												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+
											//item[0] + "</span><br><span class='desc'>" +
											// item[1] + "</span><br><span class='desc'>" +
											//item[2] + "</span></div>")
											item[0]
											+ "</span><br><span class='desc'>")
									.appendTo(ul);
						};
						$("#siteid").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						//*************************************************************************************
						$("#warename").autocomplete(
										{
											showHeader : true,

											source : function(request, response) {

												var warehouse = $("#wareid")
														.val();
												var siteid = $("#siteid").val();

												$.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {
																"warehouseName" :  $("#warename").val(),				 

															// It will be modified

															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {

																	response(data.ListItemWarehouse);
																	// it will have some modification here 
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {

												warename.value = (ui.item ? ui.item[1]
														: '');

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllWarehouse',
															data : {
																warehouseName : ui.item[1],
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {

																	console
																			.log("The list is "
																					+ data.ListItemWarehouse[0]);

																	WareId = data.ListItemWarehouse[0];
																	console
																			.log("/*/-WareId is"
																					+ WareId);

																	if (data.ListItemWarehouse.length == 1) {
																		console
																				.log("/*/*Entered here");

																		$(
																				"#wareid")
																				.val(
																						WareId);
																		siteid.value = ui.item[2];

																	}

																	else {

																		$(
																				"#wareid")
																				.val(
																						"");

																	}

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});

												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+
											//item[0] + "</span><br><span class='desc'>" +
											// item[1] + "</span><br><span class='desc'>" +
											//item[2] + "</span></div>")
											item[1]
											+ "</span><br><span class='desc'>")
									.appendTo(ul);
						};
						$("#warename").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						//*************************************************************************************
						$("#prsuppid").autocomplete(
										{

											source : function(request, response) {

												var supplierName = $(
														"#supplierName").val();

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllSupplier',
															data : {
																"supplierId" : $(
																		"#prsuppid")
																		.val(),
																"supplierName" : supplierName,
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListGetSupplier);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {
												prsuppid.value = (ui.item ? ui.item[0]
														: '');
												supplierName.value = ui.item[1];
												//prsuppaddress.value= ui.item[2];
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0]
											+ "</span><br><span class='desc'>"
											+ item[1] + "</span></div>")
									.appendTo(ul);
						};
						$("#prsuppid").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						//*************************************************************************************

						$("#supplierName").autocomplete(
										{

											source : function(request, response) {

												var psupplierId = $("#prsuppid")
														.val();
												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllSupplierName',
															data : {
																"supplierId" : psupplierId,
																"supplierName" : $(
																		"#supplierName")
																		.val(),
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListSupplierName);

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 40,
											scroll : true,

											select : function(event, ui) {
												supplierName.value = (ui.item ? ui.item[0]
														: '');
												//prsuppaddress.value = ui.item[1];

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetSuppID',
															data : {
																SuppName : ui.item[0],
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	console
																			.log("Entered here");
																	console
																			.log("The list is "
																					+ data.ListSuppIds[0]);

																	SuppID = data.ListSuppIds[0];

																	if (data.ListSuppIds.length == 1) {
																		console
																				.log("/*/*Entered here");

																		$(
																				"#prsuppid")
																				.val(
																						SuppID);
																		prsuppaddress.value = ui.item[1];

																	}

																	else {

																		$(
																				"#prsuppid")
																				.val(
																						"");
																		$(
																				"#prsuppaddress")
																				.val(
																						"");

																	}

																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});

												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[0] + "</span></div>")
									.appendTo(ul);
						};
						$("#supplierName").focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});
						//*************************************************************************************
						$('#itmCode').autocomplete(
										{

											source : function(request,
													response, event, ui) {

												var itemModel = document
														.getElementById('itmModel').value;
												var itemPartNb = document
														.getElementById('itmPartNo').value;
												var itemCode = document
														.getElementById('itmCode').value;
												if (itemModel == ""
														|| itemModel.length == 0) {
													itemModel = "empty";
												}
												if (itemPartNb == ""
														|| itemPartNb.length == 0) {
													itemPartNb = "empty";
												}

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",

															/* url: '${pageContext.request.contextPath}/GetAllitemPrREQ',*/
															url : '${pageContext.request.contextPath}/GetAllItemModelPopup',
															data : {
																"Item_Model" : request.term,
																"Item_code" : "",
																"Item_PartNum" : "1",
																
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListItemModelPopup);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 4,
											scroll : true,
											select : function(event, ui) {
												itmCode.value = (ui.item ? ui.item[1]
														+ ":" + ui.item[2]
														: '');
												itmModel.value = ui.item[0];
												itmPartNo.value = ui.item[3];
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $('<li class="each"></li>').data(
									"item.autocomplete", item).append(
									'<div class="acItem"><span class="name" style="font-weight:bold">'
											+

											item[1]
											+ '</span><br><span class="desc">'
											+ item[2] + '</span></div>')
									.appendTo(ul);
						};
						$('#itmModel').focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

						$('#itmModel').autocomplete(
										{
											//				
											source : function(request,
													response, event, ui) {
												var itemPartNb = document
														.getElementById('itmPartNo').value;
												var Item_code = $('#itmCode')
														.val();
												if (Item_code == "") {
													Item_code = "empty";
												}
												if (Item_code != "empty") {
													Item_code = Item_code
															.split(":");
													Item_code = Item_code[0];
												}
												if (itemPartNb == ""
														|| itemPartNb.length == 0) {
													itemPartNb = "empty";
												}

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",

															url : '${pageContext.request.contextPath}/GetAllItemModelPopup',
															data : {
								 			                	"Item_Model" : request.term,
								 			                	Item_code : Item_code,
																"itemPartNb" : itemPartNb
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListItemModelPopup);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 10,
											scroll : true,
											select : function(event, ui) {
												itmModel.value = (ui.item ? ui.item[0]
														: '');

												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							//																		}).data('ui-autocomplete')._renderItem = function(ul, item) {
							return $('<li class="each"></li>').data(
									"item.autocomplete", item).append(
									'<div class="acItem"><span class="name" style="font-weight:bold">'
											+

											item[0]
											+ '</span><br><span class="desc">'
											+ item[1] + '</span></div>')
									.appendTo(ul);
						};
						$('#itmModel').focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});
						//*************************************************************************************		
						//GetItemPartNumbersBOQ before
						
						$('#itmPartNo').autocomplete(
										{
											source : function(request,
													response, event, ui) {

												var Item_code = $('#itmCode')
														.val();
												if (Item_code == "") {
													Item_code = "empty";
												}
												if (Item_code != "empty") {
													Item_code = Item_code
															.split(":");
													Item_code = Item_code[0];
												}

												var itemModel = document
														.getElementById('itmModel').value;

												if (itemModel == ""
														|| itemModel.length == 0) {
													itemModel = "empty";
												}

												$
														.ajax({
															type : "GET",
															contentType : "application/json; charset=utf-8",
															url : '${pageContext.request.contextPath}/GetAllItemModelPopup',
															data : {
																"Item_Model" : request.term,
																Item_code : Item_code,
																							
															},
															dataType : "json",
															success : function(
																	data) {
																if (data != null) {
																	response(data.ListItemModelPopup);
																}
															},
															error : function(
																	result) {
																alert("Error");
															}
														});
											},
											minLength : 0,
											maxShowItems : 10,
											scroll : true,
											select : function(event, ui) {
												itmPartNo.value = (ui.item ? ui.item[3]
														: '');
												return false;
											}
										}).autocomplete("instance")._renderItem = function(
								ul, item) {
							return $("<li class='each'>").append(
									"<div class='acItem'><span class='name' style='font-weight:bold'>"
											+ item[3] + "</span></div>")
									.appendTo(ul);
						};
						$('itmPartNo').focus(function() {
							if (this.value == "") {
								$(this).autocomplete("search");
							}
						});

					});

	//*************************************************************************************
</script>
</html>