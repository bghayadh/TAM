<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/Contract.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap4.min.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
	rel="stylesheet" />
   <script
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	<script
	src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
     
    <script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/slick.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/slick-theme.css" />
    <script src="${pageContext.request.contextPath}/resources/js/slick.js"></script>
    
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebarResize.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/loading.css">

<meta charset="ISO-8859-1">
<title>Network Tree</title>
<style type="text/css">

.overlay {
    background: #e9e9e9;            
    position: absolute;   
    top: 0;                 
    right: 0;                
    bottom: 0;
    left: 0;
    opacity: 0.5;
}
/* warehouse style */
#map {
        height: 590px;
        width: 500px;
       }
       
       select
       {
       		width:260px;
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
	
	.table thead th
	{
		vertical-align:middle;
	}
	
	.table thead th div
	{
		width:200px !important;
	}
	
	#warehouseInfo_tbl, #warehouseInfo_tbl td
	{
		padding: 0;
		margin: 0;
		vertical-align: top;
	}
	
	.left_col
	{
		height:60px
	}
				
body{
padding-bottom: 0px;
}				

.jstree-default .jstree-clicked {
  background: white;
}
.jstree-default .jstree-anchor {
	color: black;
}
.jstree-default .jstree-anchor:hover {
	color: black;
}

.ui-autocomplete {
            	max-height: 100px;
				overflow-y: auto; /* prevent horizontal scrollbar */
				overflow-x: hidden; /* add padding to account for vertical scrollbar */
				padding-right: 100px;
        	}
 
 .hide-row { 
transition: all 300ms ease-in-out;
display:none !important; }

/* ************* */

.overlay {
    background: #e9e9e9;            
    position: absolute;   
    top: 0;                 
    right: 0;                
    bottom: 0;
    left: 0;
    opacity: 0.5;
}
/* warehouse style */
#map {
        height: 590px;
        width: 500px;
       }
       
       select
       {
       		width:260px;
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
	
	.table thead th
	{
		vertical-align:middle;
	}
	
	.table thead th div
	{
		width:200px !important;
	}
	
	#warehouseInfo_tbl, #warehouseInfo_tbl td
	{
		padding: 0;
		margin: 0;
		vertical-align: top;
	}
	
	.left_col
	{
		height:60px
	}
				
body{
padding-bottom: 0px;
}				

.jstree-default .jstree-clicked {
  background: white;
}
.jstree-default .jstree-anchor {
	color: black;
}
.jstree-default .jstree-anchor:hover {
	color: black;
}

.ui-autocomplete {
            	max-height: 100px;
				overflow-y: auto; /* prevent horizontal scrollbar */
				overflow-x: hidden; /* add padding to account for vertical scrollbar */
				padding-right: 100px;
        	}
 
 .hide-row { 
transition: all 300ms ease-in-out;
display:none !important; }
</style>
</head>
<body>
<!-- nav -->
<%-- 	<c:set var = "page" value = "network"/> --%>

<%-- 	<%@ include file="../header.html" %> --%>
	  <c:set var="pg" value="network" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
	

<div class="container-fluid">

<div class="panel-container ">

            <div class="panel-left" style="height: 100%;">
       <div class="row">
	<!-- 	<div class="col-md-4">
				<button type="button" class="btn btn-success btn-sm" onclick="demo_create();"><i class="glyphicon glyphicon-asterisk"></i> Create</button>
				<button type="button" class="btn btn-warning btn-sm" onclick="demo_rename();"><i class="glyphicon glyphicon-pencil"></i> Rename</button>
				<button type="button" class="btn btn-danger btn-sm" onclick="demo_delete();"><i class="glyphicon glyphicon-remove"></i> Delete</button>
			</div>
	-->
		</div>
		
		<div class="row">
			<div class="col-md-4">
			<input type="text" value="" style="box-shadow:inset 0 0 4px #eee;  margin:0; padding:6px 12px; border-radius:4px; border:1px solid silver; font-size:1.1em;" id="treeSearch" placeholder="Search" />
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-4 treesc">
				<div id="networkTree"  style="margin-top:1em; min-height:200px;"></div>
			</div>
		</div>
            </div>

            <div class="splitter">
            </div>

            <div class="panel-right" id="displayPage">
            <div id="mapView">
            <div id="networkMap" style="width: 600px; height: 600px;" class="hide-row"></div>
            </div>
            <div id="spinner" class="overlay hide-row">
            <div  class="loader ">Loading...</div>
            </div>
			<div id="wareView" class="hide-row">
             <%@ include file="NetworkView/NetworkWarehouseFormView.jsp" %>
             </div>
            <div id="areaView" class="hide-row">
             <%@ include file="NetworkView/NetworkAreaFormView.jsp" %>
             </div>
            </div>
        </div>


    </div>


</body>


<!-- end container -->
	
	<script src="${pageContext.request.contextPath}/resources/js/jstree.min.js"></script>
	
	<script type="text/javascript">
	
	$(document).ready(function() {
		
		 $(".panel-left").resizable({
			   handleSelector: ".splitter",
			   handles: 'e, w',
			   minWidth:250,
			   resizeHeight: false
			 });
		});
	/*function demo_create() {
		var ref = $('#jstree_demo').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = sel[0];
		sel = ref.create_node(sel, {"type":"file"});
		if(sel) {
			ref.edit(sel);
		}
	};*/
	var exception = '${exception}';
	if(exception.length != 0){
		alert(exception);
		}
	var treeView = ${treeView};
	
	
	if(treeView.length == 0){
		alert("order not correct");
		}
	function demo_rename() {
		var ref = $('#jstree_demo').jstree(true),
			sel = ref.get_selected();
		if(!sel.length) { return false; }
		sel = sel[0];
		ref.edit(sel);
	};
/* 
 .on("changed.jstree", function (e, data) {
		if(data.selected.length) {
			alert('The selected node is: ' + data.instance.get_node(data.selected[0]).text);//or .text for get the text
var currentNode = $("#networkTree").jstree("get_selected");
			console.log(currentNode);
	   var childrens = $("#networkTree").jstree("get_children_dom",currentNode[0]);
console.log(childrens);
for(var i=0;i<childrens.length;i++)
	   {
	   alert(childrens[i].text);
	   }
			}
	})
 */
 var wholeID;
 $(function () {
	var to = false;
	$('#treeSearch').keyup(function () {
		if(to) { clearTimeout(to); }
		to = setTimeout(function () {
			var v = $('#treeSearch').val();
			$('#networkTree').jstree(true).search(v);
		}, 250);
	});
	
	$('#networkTree')
	.on("changed.jstree", function (e, data) {
			if(data.selected.length) {
				var id = data.instance.get_node(data.selected[0]).id;
				console.log(id);
				wholeID=id;
				var generalid = id.split("%$*");
				var ware = "WAREHOUSE";
				var area = "AREA";
				if(generalid[1] == ware){
					$("#spinner").removeClass("hide-row");
					$("#wareView").removeClass("hide-row");
					$("#areaView").addClass("hide-row");
					setupPanel("GetNetworkWarehouse",generalid[0]);					 
					}else if(generalid[1] == area){
						$("#areaView").removeClass("hide-row");
						$("#wareView").addClass("hide-row");						
						$("#spinner").removeClass("hide-row");
						setupPanel("GetNetworkArea",generalid[0]);					 
						}
			}
		})
	.jstree({
		"core" : {
			"themes": {
                'responsive': true
            },
			"check_callback" : true,				
			'data' : treeView		    
		},
		//*******************
		"contextmenu":{         
    		"items": function($node) {
		        var tree = $("#networkTree").jstree(true);
		        return {
		            "FormView": {
		                "separator_before": false,
		                "separator_after": false,
		                "label": "Display In Form View",
		                "icon": "/resources/img/folderClose.png",
		                "action": function (data) { 
		                	//console.log(wholeID);
		                }
		            },
		            "GoogleMap": {
		                "separator_before": false,
		                "separator_after": false,
		                "label": "Display In Google Map",
		                "action": function (obj) { 
		                    
		                }
		            },     
		            "Create": {
		                "separator_before": false,
		                "separator_after": false,
		                "label": "Create",
		                "action": function (obj) { 
		                    $node = tree.create_node($node);
		                    tree.edit($node);
		                }
		            },
		            "Rename": {
		                "separator_before": false,
		                "separator_after": false,
		                "label": "Rename",
		                "action": function (obj) { 
		                    tree.edit($node);
		                }
		            },                    
		            "Remove": {
		                "separator_before": false,
		                "separator_after": false,
		                "label": "Remove",
		                "action": function (obj) { 
		                    tree.delete_node($node);
		                }
		            }
		        };
    		}
		},
		//******************
		
		"plugins" : [ "dnd", "search",  "types","contextmenu", "state" ],//"wholerow" plugins for remove the lines
	//	"ui" : { "select_multiple_modifier": "on","selected_parent_close": "deselect" },
		 
	});

	

	
	/*$("#networkTree a").live("click", function(e) 
		    {
		        nodeid = $(this).parent().attr("id").split("_")[1];
		        $("#node_"+nodeid+" >a").css("color","red");
		    });*/
 });
 function setupPanel(method,id){
	  $.ajax({
         type: "GET",
         contentType: "application/json; charset=utf-8",
         url: '${pageContext.request.contextPath}/'+method,
         data: {
                   "id":id
			 },
         dataType: "json",
         success: function (data) {
        	 $("#spinner").addClass("hide-row");
             if (data != null) {
                 if(method == "GetNetworkWarehouse"){
                     setupWarehouse(data);
                     }else if(method == "GetNetworkArea"){
                         setupArea(data);
                         }	                     
             }
         },
         error: function(result) {
        	 
        	 $("#spinner").addClass("hide-row");
            console.log(result);
         }
     });
	}

	function setupArea(data){
		$("#createdate").val(data.creationDate);
		$("#lstmodifdate").val(data.lastModifiedDate);
		$("#areaId").val(data.AreaId);
		$("#areaname").val(data.areaName);
		$("#arealng").val(data.areaLong);
		$("#arealatt").val(data.areaLat);

		//****************************************
		


		 /*
		 ****************************************************************
		 */

	
		 //**************************************************************
		function count(array){
		    var c = 0;
		    for(i in array) // in returns key, not object
		        if(array[i] != undefined)
		            c++;
		    return c;
		}


		boqArray = [];
		boqArray = data.ListAreaFinance;
		if(boqArray !=null){
		for (i = 0;i<boqArray.length;i++){
		 var itemRow = "<tr>";
		 itemRow= itemRow + "<td><input type='checkbox' name='record'></td>"
		 itemRow =itemRow + "<td name='startDate'><input type='date' name='startdate' value='" + boqArray[i].startDate + "' style='width:400px;' /></td>";

		 itemRow =itemRow + "<td name='endDate'><input type='date' name='enddate' value='" + boqArray[i].endDate + "' style='width:400px;' /></td>";
		 itemRow =itemRow + "<td name='no2gSites'><input type='text'  value='" + boqArray[i].number2gSites +"' style='width:250px;'></td>";
		 itemRow =itemRow + "<td name='profitAndLoss2g'><input type='text'  value='" + boqArray[i].pl2g +"' style='width:250px;'></td>";
		 itemRow =itemRow + "<td name='no2g3gSites'><input type='text'  value='" + boqArray[i].number2g3gSites +"' style='width:250px;'></td>";
		 itemRow =itemRow + "<td name='profitAndLoss2g3g'><input type='text'  value='" + boqArray[i].pl2g3g +"' style='width:250px;'></td>";
		 itemRow =itemRow + "<td name='no2g3g4gSites'><input type='text'  value='" + boqArray[i].number2g3g4gSites +"' style='width:250px;'></td>";
		 itemRow =itemRow + "<td name='profitAndLoss2g3g4g'><input type='text'  value='" + boqArray[i].pl2g3g4g +"' style='width:250px;'></td>";
		 itemRow =itemRow + "<td name='totalSites'><input type='text'  value='" + boqArray[i].totalSites +"' style='width:250px;' ></td>";
		 itemRow =itemRow + "<td name='totalSumProfit'><input type='text'  value='" + boqArray[i].sumProfitLoss +"' style='width:250px;' ></td>";
		 itemRow =itemRow + "<td name='id'><input type='text'  value='" + boqArray[i].id +"' style='width:250px;' readonly></td>";
			
		 itemRow =itemRow + "</tr>";
		 $("#yaratab > tbody").append(itemRow);


		}
	}

		

		//***************************************************************************
			
			
		}
	function setupWarehouse(data){
		
		$("#wareID").val(data.wareID);
		$("#wcreationDate").val(data.wcreationDate);
		$("#wlastModifieddate").val(data.wlastModifieddate);
		$("#warepname").val(data.warehouseName);
		$("#warcity").val(data.wareCity);
		$("#warelng").val(data.wareLong);
		$("#warlatt").val(data.wareLat);
		if(data.wareSite == "checked"){
		$("#warsite").prop('checked', true);
		}
		$("#siteId").val(data.siteId);
		$("#areaID").val(data.areaID);
		if(data.tech2G == "checked"){
			$("#techg2g").prop('checked', true);
			}
		if(data.tech3G == "checked"){
			$("#techg3g").prop('checked', true);
			}
		if(data.tech4G == "checked"){
			$("#techg4g").prop('checked', true);
			}
		if(data.tech5G == "checked"){
			$("#techg5g").prop('checked', true);
			}
		$("#wareAddress").val(data.wareAddress); 
		$("#siteMode").val(data.siteMode); 
		var existing_newtown = data.existing_newtown;
		$(function () {
			  $('#existing_newtown option').filter(function() {
			    return this.textContent == existing_newtown;
			  }).prop('selected', true);
			});	
		var showcase = data.showcase;
		$(function () {
			  $('#showcase option').filter(function() {
			    return this.textContent == showcase;
			  }).prop('selected', true);
			});	
		$("#siteOwner").val(data.siteOwner);
		$("#towerCoID").val(data.towerCoID);
		var towerType = data.towerType;
		$(function () {
			  $('#showcase option').filter(function() {
			    return this.textContent == towerType;
			  }).prop('selected', true);
			});	
		$("#towerHeight").val(data.towerHeight);
		$("#buildingHeight").val(data.buildingHeight);
		var shared = data.shared;
		$(function () {
			  $('#showcase option').filter(function() {
			    return this.textContent == shared;
			  }).prop('selected', true);
			});
		var icrCategory = data.icrCategory;
		$(function () {
			  $('#showcase option').filter(function() {
			    return this.textContent == icrCategory;
			  }).prop('selected', true);
			});
		var transmission = data.transmission;
		$(function () {
			  $('#showcase option').filter(function() {
			    return this.textContent == transmission;
			  }).prop('selected', true);
			});
		$("#hubSite").val(data.hubSite);
		$("#locationNotes").val(data.locationNotes);
		$("#shelter").val(data.shelter);
		$("#shelterID").val(data.shelterID);
		$("#shelterType").val(data.shelterType);
		$("#shelterVendor").val(data.shelterVendor);
		if(data.wshelter == "checked"){
			$("#wshelter").prop('checked', true);}
		$("#InitialCost").val(data.InitialCost);
		$("#accumPer").val(data.accumPer);
		$("#totalNetCost").val(data.totalNetCost); 
		initMap();
		    boqArray = [];
		    boqArray = data.ListPRqItem;
			for (i = 0;i<boqArray.length;i++)
			{

				startDate = boqArray[i].startDate;
				//var start_date = new Date(startDate).toLocaleDateString("en-US",{year:"numeric",month:"2-digit", day:"2-digit"});
				//var start_date = new Date(startDate).toLocaleDateString('en-US');

				var d = new Date(startDate);
				var mm = ("0" + (d.getMonth() + 1)) .slice(-2);
				var dd = ("0" + d.getDate()).slice(-2);
				var yy = d.getFullYear();
				var start_date = yy + '-' + mm + '-' + dd; //(US)

				endDate = boqArray[i].endDate;
				//var end_date = new Date(endDate).toLocaleDateString("en-US");
				//var end_date = new Date(endDate).toLocaleDateString("en-US",{year:"numeric",month:"2-digit", day:"2-digit"});
				//var end_date = new Date(endDate).toLocaleDateString('en-US');
				var d = new Date(endDate);
				var mm = ("0" + (d.getMonth() + 1)) .slice(-2);
				var dd = ("0" + d.getDate()).slice(-2);
				var yy = d.getFullYear();
				var end_date = yy + '-' + mm + '-' + dd; //(US)
				
				var tech_value = "<div style='width:180px'>";
				if(boqArray[i].tech2G == 1)
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_2g' checked /> 2G ";
				else
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_2g' /> 2G ";


				if(boqArray[i].tech3G == 1)
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_3g' checked /> 3G ";
				else
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_3g' /> 3G ";


				if(boqArray[i].tech4G == 1)
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_4g' checked /> 4G ";
				else
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_4g' /> 4G ";


				if(boqArray[i].tech5G == 1)
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_5g' checked /> 5G ";
				else
					tech_value += "<input style='margin-top:10px;' type='checkbox' name='tech_5g' /> 5G ";

			    tech_value += "</div>";
				
				var itemRow = "<tr>";
		        itemRow= itemRow + "<td style='text-align:center;'><input style='margin-top:10px;' type='checkbox' name='record'></td>";
		        itemRow =itemRow + "<td name='startDate'><input type='date' style='width:200px;' value='" + start_date +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='endDate'><input type='date' style='width:200px;'  value='" + end_date +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='population'><input type='text' value='" + boqArray[i].population +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='technologies'>"+tech_value +"</td>";
		        itemRow =itemRow + "<td name='2gUtilization'><input type='text' value='" + boqArray[i].utilization2G +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='3gUtilization'><input type='text' value='" + boqArray[i].utilization3G +"' class='form-control text-input'></td>";
		        itemRow =itemRow + "<td name='4gUtilization'><input type='text' value='" + boqArray[i].utilization4G +"' class='form-control text-input'></td>";
		        itemRow =itemRow + "<td name='5gUtilization'><input type='text' value='" + boqArray[i].utilization5G +"' class='form-control text-input'></td>";
		        itemRow =itemRow + "<td name='Availability2G'><input type='text' value='" + boqArray[i].availability2G +"' class='form-control text-input'></td>";
		        itemRow =itemRow + "<td name='Availability3G'><input type='text' value='" + boqArray[i].availability3G +"' class='form-control text-input'></td>";
		        itemRow =itemRow + "<td name='Availability4G'><input type='text' value='" + boqArray[i].availability4G +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='Availability5G'><input type='text' value='" + boqArray[i].availability5G +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='grossADS'><input type='text' value='" + boqArray[i].grossADS +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='countOfSSO'><input type='text' value='" + boqArray[i].countOfSSO +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='customerBase'><input type='text' value='" + boqArray[i].custBase +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='data'><input type='text' value='" + boqArray[i].data +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='voice'><input type='text' value='" + boqArray[i].voice +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='smsRevenue'><input type='text' value='" + boqArray[i].smsRevenu +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='grossRevenue'><input type='text' value='" + boqArray[i].grossRevenu +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='dataTraffic'><input type='text' value='" + boqArray[i].datatraffic +"'class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='totalSMSTrafficOG'><input type='text' value='" + boqArray[i].totalSmsTraOG +"'class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='totalVoiceTrafficOG'><input type='text' value='" + boqArray[i].totalVoiceTraOG +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='totalVoiceTrafficIC'><input type='text' value='" + boqArray[i].totalVoiceTraIC +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='totalSMSTrafficIC'><input type='text' value='" + boqArray[i].totalSmsTraIC +"'class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='totalOpexMon'><input type='text' value='" + boqArray[i].totalOpexMon +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='ProfitAndLoss'><input type='text' value='" + boqArray[i].profitAndLoss +"' class='form-control text-input' ></td>";
		        itemRow =itemRow + "<td name='ProfitLossID'><input type='text' value='" + boqArray[i].id +"' readonly class='form-control text-input' ></td>";
		        

		        itemRow =itemRow + "</tr>";
		        $("#bisotab > tbody").append(itemRow);
			}			 
		}
	
	</script>
				
</body>
</html>