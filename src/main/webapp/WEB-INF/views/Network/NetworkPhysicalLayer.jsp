 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="utf-8">
<title>Physical Layer</title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
<script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">
<script src="${pageContext.request.contextPath}/resources/js/Network/NetworkMapStylesLayers.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/maps.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/Physical_Layer_Tree.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/Physical_Layer_Map.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/Physical_Layer_Common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/FiberSearch.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/context-menu.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/xlsx.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jszip.js"></script>

<style>
.close {
  float: right;
  font-size: 1.5rem;
  font-weight: 700;
  line-height: 1;
  color: white !important;
  text-shadow: 0 1px 0 #fff;
  opacity: .5;
}
.btn-save{
    background-color: #C2CBC0 !important ;
    border-color: #C2CBC0;
}
.btn-save:hover {
    color: #fff;
    background-color: #8696A0 !important;
    border-color: #8696A0 !important;
}
.changecolclick{
 	background-color: #F57E25 !important; 
 	border-color: #F57E25  !important;  
}
.btn-delete:hover{
    background-color: #4d8037 !important;
    border-color: #4d8037;    
    }
.nav-link.active {
  color: #1D3763 !important;
}
.tab button.active {
    background: white;
    color: #1D3763 !important;
}
.tab button.active {
    background: #FFD966;
    color: #1D3763 !important;
}
.tablinks.active {
 	width: 16%;
}
</style>

</head>
<%@ include file="/WEB-INF/views/Network/FiberSearch.html" %> 
<body>
	<c:set var="pg" value="network" scope="session" />
	<jsp:include page="../header.jsp"></jsp:include><p></p><br>
	<input type="text" id="fiberAuxFlag" name="fiberAuxFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="tubeAuxFlag" name="tubeAuxFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="strandAuxFlag" name="strandAuxFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="trenchAuxFlag" name="trenchAuxFlag" value="" class="form-control text-input" hidden="hidden" />	
	<input type="text" id="ductAuxFlag" name="ductAuxFlag" value="" class="form-control text-input" hidden="hidden" />	
	<input type="text" id="DBMappingFlag" name="DBMappingFlag" value="" class="form-control text-input" hidden="hidden" />
	<!-- End of Optional Bar  -->
	<!--  Beginning for Search bar, Coordinates and View buttons -->
	<div class="container-fluid" style="margin-top: 5px;">
		<div class="row second" style="margin-top: -40px; margin-bottom: 5px;">
			<div class="col-md-5" style="margin-top: 9px; padding-right: 0px;">
				<div class="search" id="headSearch">
					<div class='col-sm-4' style='padding-left: 0px; padding-right: 0px;'>
						<div class='form-group'>
							<div class='input-group-prepend'><select class='form-control ssss' id='selectHeaderSearch'>
								<option value=''>Sites/Clients</option><option value='site'>Sites</option><option value='client'>Clients</option></select>
							</div>
						</div>
					</div>
					<div class='col-sm-7' style='padding-right: 0px;'>
						<input type='text' id='autoCompleteHeaderSearch' style='height: 35px; width: 100%;' placeholder='Search within the Network'> <input type='text' id='headerSearchLong' hidden>
						<input type='text' id='headerSearchLat' hidden>
					</div>
					<div class='col-sm-1' style='padding-left: 0px;'>
						<button type='submit' id='searchHeaderButton' class='searchButton searchHeaderButton' style='height: 35px;'><i class='fa fa-search'></i></button>
					</div>
				</div>
			</div>
			<div class="col-md-1" id="dropDownCheckDiv"style="text-align: right; margin-top: 6px;"></div>
			<div class="col-md-2" style="text-align: right; margin-top: 12px;">
				<div id="txtDiv"><input id="mapText" type='text' disabled style="width:266px;height:35px; text-align: center; margin-left:52px;position:relative;top:-1px;" /></div>
			</div>
			<div class="col-md-4" style="text-align: right; margin-top: 4px;">
				<div class="btn-group pull-right"><div class="glyph"><button type="button" class="btn btn-light" data-placement="top"title="Map Operations" data-toggle="modal" onclick="mapOperation()"><i class="fas fa-toolbox"></i></button>
						<div class="dropdown-menu"><a class="dropdown-item" onclick="initMap();" id="customMap" data-map="CustomMap">Clustered Map</a><a class="dropdown-item" onclick="updateMap();" id="blankMap" data-map="BlankMap">Non Clustered Map</a></div>
						<button type="button" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="Search" id="open-popup-btn"><i class="fa fa-search"></i></button>
						<button type="button" class="btn btn-light" id="tree" data-toggle="tooltip" data-placement="top" title=" Folder Tree"><i class="fas fa-sitemap"></i></button>
						<button type="button" class="btn btn-light" id="gis" data-toggle="tooltip" data-placement="top" title=" GIS"
							onclick='window.location.href = "${pageContext.request.contextPath}/GisPage"'><i class="fas fa-map-marked-alt"></i>
						</button>
 					    <button type="button" id="Fview" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="Form View" onclick='window.location.href = "${pageContext.request.contextPath}/SiteFormView"'><i class="fa fa-edit"></i></button>
						<a href="Sitelistview">
                       	<button type="submit" id="Lview" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="List View">
								<!--  onclick='window.location.href = "${pageContext.request.contextPath}/SiteListView"' -->
								<i class="fa fa-bars"></i></button></a>
					  <button type="button" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="Search" id="changeColorCable"  onclick="changeColor()"><i class="fa fa-paint-brush"></i></button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- End of Search bar, Coordinates and View buttons -->
	<hr style="margin-top: -3px;">
	<!-- Beginning of Tree, Layers, Options and Boq (as left side) and GIS part as right side -->
	<div id="mainDiv">
		<div id="left" style="width: 500px">
			<input name="csrfToken" value="5965f0d244b7d32b334eff840"
				type="hidden" />
			<div class="searchcontainer"
				style="margin-left: 55px; margin-bottom: 5px; margin-top: 5px; margin-right: 20px;">
				<div class="search">
					<input type="text" class="searchTerm" style="border-right: 3px solid #08526D; font-size: 15px;" placeholder="Search within the tree ..">
				</div>
			</div>
			<!-- <a style="position: absolute; margin-left: 400px; display: none; z-index: 1;" id='removeFilter' href = "${pageContext.request.contextPath}/NetworkPhysicalLayer"> 
			<input type="image" src="${pageContext.request.contextPath}/resources/js/Network/RemoveFilter.png" style="position: absolute;" height="30" width="30" /></a>
			 --><a style="position: absolute; margin-left: 400px; display: none; z-index: 1; cursor: pointer;" id='removeS' href = "${pageContext.request.contextPath}/NetworkPhysicalLayer"> 
			<span class=''> <i class='fa fa-filter' style='color: #08526D;'></i>
			<i class='fa fa-window-minimize' style='color: #08526D; width: 8px;'></i></span></a>
			
			<!-- Beginning for Tree in left side -->
			<div id="network_tree" class="tree well top-btn-filter" style="margin-top: 5px; position: static; z-index: 3;"></div>
			<input type="hidden" id="hidden_input" value=""></input>
			<!-- To place the selected option in this field -->
			<!-- End for Tree in left side -->
			
			<!--  Beginning for arrows, layers, options and Boq part which is below the left tree side -->
			<div id="narrowdiv">
				<button style="margin-left: 40%;" id="arrow_up">
					<i class="fa fa-arrow-up fa-lg " aria-hidden="true" onmouseover="changecolor(this)" title="Maximize " onmouseout="returncolor(this) " style="color: #08526D; font-size: 15px; margin-bottom: 6px;"></i>
				</button>
				<button id="arrow_down">
					<i class="fa fa-arrow-down fa-lg " aria-hidden="true" onmouseover="changecolor(this)" title="Minimize " onmouseout="returncolor(this) " style="color: #08526D; font-size: 15px; margin-bottom: 6px;"></i>
				</button>
				<button style='margin-left: 45%; display: none' id='arrow_up_normal' onclick='returnNormal()'>
					<i class='fa fa-arrow-up fa-lg ' aria-hidden='true' onmouseover='changecolor(this)' title="Restore Size " onmouseout='returncolor(this)' style='color: #08526D; font-size: 15px; margin-bottom: 6px;'></i>
				</button>
				<button style='margin-left: 45%; display: none'
					id='arrow_down_normal' onclick='returnNormal()'>
					<i class='fa fa-arrow-down fa-lg ' aria-hidden='true' onmouseover='changecolor(this)' title="Restore Size " onmouseout='returncolor(this)' style='color: #08526D; font-size: 15px; margin-bottom: 6px;'></i>
				</button>
			</div>
			<div id="Bottomdiv">
				<div class="tab">
					<button class="tablinks" onclick="opentab(event, 'Layers')" id="Defaultbutton" style="margin-left: 33px; color:#FFD966; font-weight:bold">Layers</button>		
					<button id="boqBtn" class="tablinks" onclick="opentab(event, 'Boq')" style="color:#FFD966; font-weight:bold">BoQ</button>		
					<button id="pathBtn" class="tablinks" onclick="opentab(event, 'Path')" style="color:#FFD966; font-weight:bold">Path</button>
					<button id="distributionBoardBtn" class="tablinks" onclick="opentab(event, 'DB')" style="color:#FFD966;font-weight:bold">DB</button>
					<button id="CltSiteBtn" class="tablinks" onclick="opentab(event, 'ClientSite')" style="color:#FFD966;margin-left: 11px;font-weight:bold">Client&Site</button>
					<button class="tablinks" onclick="opentab(event, 'Options')" style="color:#FFD966;margin-left:23px;font-weight:bold">Options</button>
				</div>
				<div id="Layers" class="tabcontent">
					<div class="search" style="margin-top: 1px;">
						<input type="text" class="searchTermLayers" onkeyup="FilterOptions('layers')" id="layerSearch" placeholder="Search within the Layers ..">
						<button type="submit" class="searchButton"> <i class="fa fa-search"></i> </button>
					</div>
					<table id="LayersTable" style="margin-top: 10px;">
						<tr id="view" style="border-bottom: solid #08526D 1px;">
							<td class="Icon "></td>
							<td class="title">View</td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/fiber.png">
							<span id="definition">FIBER CABLE</span></td>
							<td><input type="checkbox" class="AllFiberCables" id="fiberCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/core.png">
							<span id="definition">FIBER TUBE</span></td>
							<td><input type="checkbox" id="tubeCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/strand.png">
							<span id="definition">FIBER STRAND</span></td>
							<td><input type="checkbox" id="strandCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img class="image" src="${pageContext.request.contextPath}/resources/NetworkImages/trench.png"
								style="opacity: 0.6;"><span id="definition" style="padding-left: 3px;">TRENCH</span></td>
							<td><input type="checkbox" id="trenchCheckAllBoq" class="AllTrenches" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img class="image" src="${pageContext.request.contextPath}/resources/NetworkImages/duct.png"
								style="opacity: 0.6;"><span id="definition" style="padding-left: 4px;">DUCT</span></td>
							<td><input type="checkbox" id="ductCheckAllBoq" class="AllDucts" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/manholeRed.png"><span
								id="definition" style="padding-left: 4px;">MANHOLE</span></td>
							<td><input type="checkbox" id="manholeCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/handholeYellow.png">
								<span id="definition">HANDHOLE</span></td>
							<td><input type="checkbox" id="handholeCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
							<tr>
							<td class="Icon "><img style="width: 16px; height: 16px;" src="${pageContext.request.contextPath}/resources/img/NodesIcon.png">
								<span id="definition">Nodes</span></td>
							<td><input type="checkbox" id="nodesActiveCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/electrical-panel.png">
								<span id="definition">DISTRIBUTION BOARD</span></td>
							<td><input type="checkbox" id="distBoardCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/google-maps.png"><span
								id="definition">Searching Zone Google Map </span></td>
							<td><input type="checkbox" style="margin-left: 10px;" id="SearchZone"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/blankmap.png"><span
								id="definition">Blank Google Map </span></td>
							<td><input type="checkbox" style="margin-left: 10px;"
								id="blank"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/google-maps.png"><span
								id="definition">Default Google Map View</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"
								id="default" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/poi.png"><span
								id="definition">Points Of Interest</span></td>
							<td><input type="checkbox" style="margin-left: 10px;" id="poi" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/road.png" style="width: 16px; height: 16px;"><span id="definition">Roads</span></td>
							<td><input type="checkbox" style="margin-left: 10px;" id="road" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/exchange.png"><span id="definition">Transit</span></td>
							<td><input type="checkbox" style="margin-left: 10px;" id="transit" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/sea.png"><span id="definition">Rivers And Sea</span></td>
							<td><input type="checkbox" style="margin-left: 10px;" id="water" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/map geography.png"><span id="definition">Light Geography</span></td>
							<td><input type="checkbox" id="mapgeography" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/country-map (1).png"><span id="definition">Country Borders</span></td>
							<td><input type="checkbox" id="maplabels" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/flag.png"><span id="definition">Country Names</span></td>
							<td><input type="checkbox" id="countrynames" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/CountryProvince.png"><span id="definition">Country Province</span></td>
							<td><input type="checkbox" id="countryprovince" style="margin-left: 10px;"></td>
						</tr>
					</table>
				</div>				
				<div id="Boq" class="tabcontent">
					<table id=boq_table>
						<c:forEach items="${BoqHashMap}" var="BoqMap">
							<tr>
								<td class="title "># ${BoqMap.key}</td>
								<td id="${BoqMap.key}">${BoqMap.value}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
               <div id="Path" class="tabcontent">
					<table id=path_table>
					<c:forEach items="${PathHashMap}" var="BoqMap">
							<tr>
								<td class="title "># ${PathMap.key}</td>
								<td id="${PathMap.key}">${PathMap.value}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div id="DB" class="tabcontent">
					<table id=DB_table>
					<c:forEach items="${DBHashMap}" var="DBMap">
							<tr>
								<td class="title "># ${DBMap.key}</td>
								<td id="${DBMap.key}">${DBMap.value}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div id="ClientSite" class="tabcontent">
					<table id=CS_table>
						<c:forEach items="${ClientSiteHashMap}" var="ClientSiteMap">
							<tr>
								<td class="title "># ${ClientSiteMap.key}</td>
								<td id="${ClientSiteMap.key}">${ClientSiteMap.value}</td>
							</tr>
						</c:forEach>
					</table>
				</div>	
				<div id="Options" class="tabcontent">
					<div class="searchcontainer" style="margin-top: 1px;">
						<div class="search">
							<input type="text" class="searchTermLayers" id="searchOption" onkeyup="FilterOptions('options')" placeholder="Search within the Options ..">
							<button type="submit" class="searchButton">
								<i class="fa fa-search"></i>
							</button>
						</div>
						<table id="optionstable" style="margin-top: 10px;">
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Groups Colors</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">KPI BenchMark</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Operator Colors</span></td></tr>
							<tr><td class="title "><input type="checkbox" checked><span style="margin-left: 10px;">Sites Labels</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Connection Labels</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Link Distance</span></td></tr>
							<tr> <td class="title "><input type="checkbox"><span style="margin-left: 10px;">Link Capacity</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Link Configuration</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">MW Links</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">EL Links</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">FO Links</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Condense Links</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Capacity Line Thickness</span></td></tr>
							<tr><td class="title "><input type="checkbox"><span style="margin-left: 10px;">Capacity Line Coloring</span></td></tr>
						</table>
					</div>
				</div>					
			</div>
			<!-- End of layers, options and Boq part -->
		</div>
		<!-- End of Left Div that contains the Tree div, arrows div, layers, options and Boq div -->
		<!--  Beginning of GIS part -->
		<div id="right">
		<div id="mapContainer"></div>
		</div>
		<!--  Ending of GIS part -->
	</div>
	<!-- Projects Model -->
	<div class="container">
		<div id="projectModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="projectHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Project</h5>
						<div style="float: right;">
							<button id="saveProject" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"> <i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content"><p></p>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color: green;"><b>ID</b> </span>
											 <input type="text" id="ProjectId" readonly class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
											<input type="text" id="ProjectName" class="form-control text-input" />
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
	<!-- Manhole Model -->
	<div class="container">
		<div id="manholeModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="manholeHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Manhole</h5>
						<div style="float: right;">
							<button id="saveManhole" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>ID</b> </span> 
											<input type="text" id="ManholeId" readonly class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
											<input type="text" id="ManholeName"  class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
											<input type="text" id="ManholeLong" class="form-control text-input" />
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="ManholeLat" class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>City</b></span>
											<input type="text" id="ManholeCity" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Model</b></span>
											<input type="text" id="ManholeModel" class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="manholeCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 123px; font-size: 12px;"><b>Last Modified Date</b></span>
									<input type="text" id="manholeLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
							<div class="row">
								<div class="col-md-6" id="ManholeDMDiv">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DM_Name</b></span>
											<input type="text" id="manholeDMName" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6" id="projectIdManhole">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Project ID</b></span> <input type="text" id="ManholeProjectId"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group" id="projectNameManhole">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Project Name</b></span>
											 <input type="text" id="ManholeProjectName" class="form-control text-input" />
										</div></div></div></div>
						</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
	</div>
	
		<!-- Nodes Model -->
	<div class="container">
		<div id="nodesModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="nodesHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Node</h5>
						<div style="float: right;">
							<button id="saveNode" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>NODE_PK</b> </span> 
											<input type="text" id="node_pk" readonly class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>UNIQUE_NODE_ID</b></span>
											<input type="text" id="uniqNodeId"  class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_ID</b></span>
											<input type="text" id="nodeId" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_NAME</b></span>
											<input type="text" id="nodeName" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_TYPE</b></span>
											<input type="text" id="nodeType" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_SOURCE</b></span>
											<input type="text" id="nodeSource" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_MODEL</b></span>
											<input type="text" id="nodeModel" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DOMAIN</b></span>
											<input type="text" id="nodeDomin" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
								
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>SITE_ID</b></span>
											<input type="text" id="siteId_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>WARE_ID</b></span>
											<input type="text" id="wareId_node" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>LONGITUDE</b></span>
											<input type="text" id="nodeLong" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="nodeLat" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>CREATION DATE</b></span>
											<input type="text" id="createData_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>UPDATE DATE</b></span>
											<input type="text" id="updateData_node" class="form-control text-input" readonly/>
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
	
		<!-- Transmission Model -->
	<div class="container">
		<div id="TransmissionModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="transNodesHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Node</h5>
						<div style="float: right;">
							<button id="saveTransmission" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>NODE_PK</b> </span> 
											<input type="text" id="transNode_pk" readonly class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>UNIQUE_NODE_ID</b></span>
											<input type="text" id="transUniqNodeId"  class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_ID</b></span>
											<input type="text" id="transNodeId" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_NAME</b></span>
											<input type="text" id="transNodeName" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_TYPE</b></span>
											<input type="text" id="transNodeType" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_SOURCE</b></span>
											<input type="text" id="transNodeSource" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_MODEL</b></span>
											<input type="text" id="transNodeModel" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DOMAIN</b></span>
											<input type="text" id="transNodeDomin" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>SITE_ID</b></span>
											<input type="text" id="transSiteId_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>WARE_ID</b></span>
											<input type="text" id="transWareId_node" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>LONGITUDE</b></span>
											<input type="text" id="transNodeLong" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="transNodeLat" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>CREATION DATE</b></span>
											<input type="text" id="transCreateData_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>UPDATE DATE</b></span>
											<input type="text" id="transUpdateData_node" class="form-control text-input" readonly/>
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
	
			<!-- Core Model -->
	<div class="container">
		<div id="CoreModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="coreNodesHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Node</h5>
						<div style="float: right;">
							<button id="saveCore" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>NODE_PK</b> </span> 
											<input type="text" id="coreNode_pk" readonly class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>UNIQUE_NODE_ID</b></span>
											<input type="text" id="coreUniqNodeId"  class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_ID</b></span>
											<input type="text" id="coreNodeId" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_NAME</b></span>
											<input type="text" id="coreNodeName" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_TYPE</b></span>
											<input type="text" id="coreNodeType" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_SOURCE</b></span>
											<input type="text" id="coreNodeSource" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_MODEL</b></span>
											<input type="text" id="coreNodeModel" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DOMAIN</b></span>
											<input type="text" id="coreNodeDomin" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>SITE_ID</b></span>
											<input type="text" id="coreSiteId_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>WARE_ID</b></span>
											<input type="text" id="coreWareId_node" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>LONGITUDE</b></span>
											<input type="text" id="coreNodeLong" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="coreNodeLat" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>CREATION DATE</b></span>
											<input type="text" id="coreCreateData_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>UPDATE DATE</b></span>
											<input type="text" id="coreUpdateData_node" class="form-control text-input" readonly/>
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
	
		<!-- Ran(Access) Model -->
	<div class="container">
		<div id="RanModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="ranNodesHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Node</h5>
						<div style="float: right;">
							<button id="saveRan" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>NODE_PK</b> </span> 
											<input type="text" id="ranNode_pk" readonly class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>UNIQUE_NODE_ID</b></span>
											<input type="text" id="ranUniqNodeId"  class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_ID</b></span>
											<input type="text" id="ranNodeId" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_NAME</b></span>
											<input type="text" id="ranNodeName" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_TYPE</b></span>
											<input type="text" id="ranNodeType" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_SOURCE</b></span>
											<input type="text" id="ranNodeSource" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>NODE_MODEL</b></span>
											<input type="text" id="ranNodeModel" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DOMAIN</b></span>
											<input type="text" id="ranNodeDomin" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>SITE_ID</b></span>
											<input type="text" id="ranSiteId_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>WARE_ID</b></span>
											<input type="text" id="ranWareId_node" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>LONGITUDE</b></span>
											<input type="text" id="ranNodeLong" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="ranNodeLat" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>CREATION DATE</b></span>
											<input type="text" id="ranCreateData_node" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>UPDATE DATE</b></span>
											<input type="text" id="ranUpdateData_node" class="form-control text-input" readonly/>
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
	
	
	<div class="container">
		<div id="DeleteModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="DeleteHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float: right;">
							<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content" >
							<p id="deletebody" style="font-weight: bold;"></p>
							<div style="text-align:center;">
								<button id="deleteFiber" class="btn btn-delete" style="color: white; font-weight:bold;  margin-right: 5px ; background-color:#6AA84F">Yes</button>
								<button id="deleteMan" class="btn btn-delete" style="color:white; font-weight:bold; margin-right: 5px;background-color:#6AA84F">Yes</button>
								<button id="deleteTrench" class="btn btn-delete" style="color:white; font-weight:bold; margin-right: 5px;background-color:#6AA84F">Yes</button>
								<button id="deleteTermination" class="btn btn-delete" style="color: white; font-weight:bold; margin-left: 5px;background-color:#6AA84F; padding-left:15px; padding-right:15px">No</button>								
							</div>								
							</div>
						</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>	
	<!-- end of  manhole/handhole/db delete modal -->
	<!-- Manhole Junction Modal -->
	<div class="container">
		<div id="manholeJunctionModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 class="modal-title" id="manholeJunctionHeader" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Junction</h5>
						<div style="float: right;">
							<button id="saveManholeJunction" class="btn btn-save" style="color: black; font-weight:bold;  margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTabDb" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="junction-tab" style="color: gold;" data-toggle="tab" href="#JCT" role="tab" aria-controls="JCT" aria-selected="true">Junction </a></li>
							<li class="nav-item"><a class="nav-link " id="jctMapping-tab" style="color: gold;" data-toggle="tab" href="#jctMapping" role="tab" aria-controls="jctMapping" aria-selected="false">Mapping</a></li>
						</ul>
						<div class="tab-content">
							<p></p>
							<div class="tab-pane active" id="JCT" role="tabpanel"
								aria-labelledby="junction-tab">
								<p></p>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text" style="color:green;"><b> ID </b></span>
												 <input type="text" id="junctionId" readonly class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-md-6 nextprvItems">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
												<input type="text" id="manholeJctName" class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Manhole ID </b></span> 
												<input type="text" id="manholeIdJct" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend" id="manholeAutocompDiv">
												<span style="width: 140px;" class="input-group-text"><b>Manhole Name </b></span>
											 <input type="text" id="manholeNameJct" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
												<input type="text" id="manholeJctLong" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
												<input type="text" id="manholeJctLat" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Capacity </b></span>
												 <input type="text" id="manholeJctCapacity" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>No of junctions
														 </b></span> <input type="text" id="manholeNumberJct"
													class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 120px;" class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="manJunctionCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 170px;"><b>Last Modified Date</b></span>
									<input type="text" id="manJunctionLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>City
												</b></span> <input type="text" id="manholeJctCity" class="form-control text-input" readonly />
											</div></div></div></div></div>
							<div class="tab-pane" id="jctMapping" role="tabpanel" aria-labelledby="jctMapping-tab"><p></p>
								<div class="container-fluid"><form>
										<div class="table-responsive-sm" id="manholeJctMappingDiv">
											<table id="manholeJctMappingTable" class="table table-striped table-bordered table-sm " style="display: block; height: 250px; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr><th></th>
														<th colspan="1"></th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="13">Side A</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="13">Side B</th></tr>
													<tr><th><button type="button" id="manholeJctSelectAll" class="main"><span class="sub"></span>Select </button></th>
														<th style="min-width: 80px" class="headcol">Sequence</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 190px">Location Type</th>
														<th style="min-width: 190px">Location ID</th>
														<th style="min-width: 190px">Location Name</th>
														<th style="min-width: 190px">Warehouse ID</th>
														<th style="min-width: 190px">Network Level</th>
														<th style="min-width: 80px">Strand #</th>
														<th style="min-width: 190px">Strand ID</th>
														<th style="min-width: 190px">Strand Name</th>
														<th style="min-width: 80px">Tube #</th>
														<th style="min-width: 190px">Tube ID</th>
														<th style="min-width: 190px">Tube Name</th>
														<th style="min-width: 190px">Fiber ID</th>
														<th style="min-width: 190px">Fiber Name</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 190px">Location Type</th>
														<th style="min-width: 190px">Location ID</th>
														<th style="min-width: 190px">Location Name</th>
														<th style="min-width: 190px">Warehouse ID</th>
														<th style="min-width: 190px">Network Level</th>
														<th style="min-width: 80px">Strand #</th>
														<th style="min-width: 190px">Strand ID</th>
														<th style="min-width: 190px">Strand Name</th>
														<th style="min-width: 80px">Tube #</th>
														<th style="min-width: 190px">Tube ID</th>
														<th style="min-width: 190px">Tube Name</th>
														<th style="min-width: 190px">Fiber ID</th>
														<th style="min-width: 190px">Fiber Name</th>
													</tr></thead><tbody></tbody></table></div>
										<button type="button" id="addJunMapBelow" >Insert Row Below</button>
										<button type="button" id="addJunMapAbove" >Insert Row Above</button>
										<!--  <button type="button" id="manholeJctAddRow">Add Row</button>-->
										<button type="button" id="manholeJctDelRow">Delete Row</button>
									</form></div></div></div></div></div></div></div></div>
	<!-- Handhole Junction Modal -->
	<div class="container">
		<div id="handholeJunctionModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 class="modal-title" id="handholeJunctionHeader" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Junction</h5>
						<div style="float: right;">
							<button id="saveHandholeJunction" class="btn btn-save" style="color: black; font-weight:bold;margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTabDb" role="tablist"
							style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="handholeJunction-tab" style="color: gold;" data-toggle="tab" href="#HJCT" role="tab" aria-controls="HJCT" aria-selected="true">Junction </a></li>
							<li class="nav-item"><a class="nav-link " id="handholeJctMapping-tab" style="color: gold;" data-toggle="tab" href="#handholeJctMapping" role="tab" aria-controls="handholeJctMapping" aria-selected="false">Mapping</a></li>
						</ul>
						<div class="tab-content">
							<p></p>
							<div class="tab-pane active" id="HJCT" role="tabpanel" 	aria-labelledby="handholeJunction-tab">
								<p></p>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text" style="color:green;"><b> ID </b></span> 
					<input type="text" id="junctionHandholeId" readonly class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-md-6 nextprvItems">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
												<input type="text" id="handholeJctName" class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Handhole ID </b></span>
												<input type="text" id="handholeIdJct" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend" id="handholeAutocompDiv">
												<span style="width: 140px;" class="input-group-text"><b>Handhole Name </b></span>
												<input type="text" id="handholeNameJct" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
												<input type="text" id="handholeJctLong" class="form-control text-input" readonly />
											</div> </div> </div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
												<input type="text" id="handholeJctLat" class="form-control text-input" readonly />
											</div></div></div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Capacity</b></span> 
												<input type="text" id="handholeJctCapacity" class="form-control text-input" />
											</div>	</div> </div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>No of junctions
														 </b></span> <input type="text" id="handholeNumberJct" class="form-control text-input" />
											</div></div></div></div>
							<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 120px;" class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="handJunctionCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 170px;"><b>Last Modified Date</b></span>
									<input type="text" id="handJunctionLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>City
												</b></span> <input type="text" id="handholeJctCity" class="form-control text-input" readonly />
											</div></div></div></div>
							</div>
							<div class="tab-pane" id="handholeJctMapping" role="tabpanel" aria-labelledby="handholeJctMapping-tab">
								<p></p>
								<div class="container-fluid"><form>
										<div class="table-responsive-sm" id="handholeJctMappingDiv">
											<table id="handholeJctMappingTable" class="table table-striped table-bordered table-sm " style="display: block; height: 250px; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr><th></th>
														<th colspan="1"></th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="13">Side A</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="13">Side B</th>
													</tr><tr>
														<th> <button type="button" id="handholeJctSelectAll" class="main"><span class="sub"></span>Select </button></th>
														<th style="min-width: 80px">Sequence</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 190px">Location Type</th>
														<th style="min-width: 190px">Location ID</th>
														<th style="min-width: 190px">Location Name</th>
														<th style="min-width: 190px">Warehouse ID</th>
														<th style="min-width: 190px">Network Level</th>
														<th style="min-width: 80px">Strand #</th>
														<th style="min-width: 190px">Strand ID</th>
														<th style="min-width: 190px">Strand Name</th>
														<th style="min-width: 80px">Tube #</th>
														<th style="min-width: 190px">Tube ID</th>
														<th style="min-width: 190px">Tube Name</th>
														<th style="min-width: 190px">Fiber ID</th>
														<th style="min-width: 190px">Fiber Name</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 190px">Location Type</th>
														<th style="min-width: 190px">Location ID</th>
														<th style="min-width: 190px">Location Name</th>
														<th style="min-width: 190px">Warehouse ID</th>
														<th style="min-width: 190px">Network Level</th>
														<th style="min-width: 80px">Strand #</th>
														<th style="min-width: 190px">Strand ID</th>
														<th style="min-width: 190px">Strand Name</th>
														<th style="min-width: 80px">Tube #</th>
														<th style="min-width: 190px">Tube ID</th>
														<th style="min-width: 190px">Tube Name</th>
														<th style="min-width: 190px">Fiber ID</th>
														<th style="min-width: 190px">Fiber Name</th>
													</tr></thead><tbody></tbody></table></div>
										<button type="button" id="handholeJctAddRow">Add Row</button>
										<button type="button" id="handholeJctDelRow">Delete Row</button>
									</form></div></div></div></div></div></div></div></div>
	<!--  Strand Modal -->
		<div class="container">
		<div id="StrandModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px">
						<h5 id="StrandHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Strand:</h5>
						<div style="float: right;">
							<button id="savefiberstrand" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="strand-tab" style="color: gold;" data-toggle="tab" href="#strand" role="tab" aria-controls="strand" aria-selected="true">Strand</a></li>
							<li class="nav-item"><a class="nav-link " id="source_dest_STRAND-tab" style="color: gold;" data-toggle="tab" href="#source_dest_STRAND" role="tab" aria-controls="source_dest_STRAND" aria-selected="false">Source_Destination</a></li>
							<li class="nav-item"><a class="nav-link " id="aux-tab-strand" style="color: gold;" data-toggle="tab" href="#auxtab" role="tab" aria-controls="auxtabstrand" aria-selected="false">Auxiliary Points</a></li>
						</ul>
						<div class="tab-content" style="min-height: 300px">
							<div class="tab-pane active" id="strand" role="tabpanel" aria-labelledby="strand-tab"><p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>ID </b> </span>
														 <input type="text" id="StrandID" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Name </b></span>
													<input type="text" id="StrandName" class="form-control text-input" />
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
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Fiber Cable </b> </span>
														 <input type="text" id="fibercableStrand" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 80px; font-size: 12px;" class="input-group-text"><b>Tube </b></span> 
														<input type="text" id="fibertubeStrand" readonly class="form-control text-input" />
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
											<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Length </b></span> 
											<input type="text" id="StrandLength" class="form-control text-input" /></div></div>
								</div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
								<span style="width: 150px; font-size: 12px;" class="input-group-text"><b>Driving Distance </b></span> <input type="text" id="strandDrivDist" class="form-control text-input" />
							</div></div></div></div></div>
														<div class="container-fluid">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 110px; font-size: 12px;" class="input-group-text"><b>Strand Number </b></span> 
												<input type="text" id="strandNumber" class="form-control text-input" /></div></div>
									</div>								
									<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
										<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Strand Color </b></span>
												<select id="strandColor" class="form-control" style="height:38px;">
													<option value="" style="background-color: white;"></option>													
													<option value="blue" style="background-color: white;color:black">blue</option>
													<option value="orange" style="background-color: white;color:black">orange</option>
													<option value="green" style="background-color: white;color:black">green</option>
													<option value="brown" style="background-color: white;color:black">brown</option>
													<option value="gray" style="background-color: white;color:black">gray</option>
													<option value="white" style="background-color: white;color:black">white</option>													
													<option value="red" style="background-color: white; color:black">red</option>
													<option value="black" style="background-color: white;color:black">black</option>
													<option value="yellow" style="background-color: white;color:black">yellow</option>
													<option value="violet" style="background-color: white;color:black">violet</option>													
													<option value="pink" style="background-color: white;color:black">pink</option>
													<option value="aqua" style="background-color: white;color:black">aqua</option>																																							
											  </select>
										</div></div>
									</div>
								</div>
							</div>
								<div class="container-fluid">									
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDivTube">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Strand Type</b></span> 
														<select id="frmstrandtype" class="form-control">
															<option value=""></option>
															<option value="g561">G.651</option>
															<option value="g562">G.652</option>
															<option value="g563">G.653</option>
														</select>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDivTube">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Strand Deployment</b></span> 
														<select id="frmstranddep" class="form-control">
															<option value="" selected></option>
															<option value="aerial">Aerial</option>
															<option value="submarine">Submarine</option>
															<option value="underground">Underground</option>
														</select>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDivTube">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Strand Network Level</b></span> 
														<input id="frmstrandnetlevel" class="form-control" disabled ></input>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDivTube">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Strand Owner</b></span> 
														<select id="frmstrandowner" class="form-control">
															<option value="" selected></option>
															<option value="tkl">TKL</option>
															<option value="nofbi">NOFBI</option>
															<option value="others">Others</option>
														</select>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
												<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Creation Date</b></span>
													 <input type="text" id="popupCreateDateStrand" class="form-control text-input" value="" readonly />
														</div>
													</div>
												</div>
										<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text"  style="width: 123px; font-size: 12px;"><b>Last Modified Date</b></span>
															<input type="text" id="popupLastModifiedDateStrand" class="form-control text-input" value="" readonly />

														</div>
													</div>
												</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 180px; font-size: 12px;" class="input-group-text"><b>Created By </b></span>
													<input type="text" id="crtdByFiberStrand" value='${userFullName}' class="form-control text-input" readonly />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Last Modified By </b></span>
													<input type="text" id="modifiedByFiberStrand" value='${userFullName}' class="form-control text-input" readonly />
												
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						<div class="tab-pane " id="source_dest_STRAND" role="tabpanel" aria-labelledby="source_dest_STRAND-tab">
						<p></p>
							<div class="container-fluid">
										<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_StrandAutoComplete" style='position: relative; margin-left: 25px' class="srcDestStrandAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="client_StrandAutoComplete" style='position: relative; margin-left:15px' class="srcDestStrandAutoComplete"></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_StrandAutoComplete" style='position: relative; margin-left:10px' class="srcDestStrandAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_StrandAutoComplete" style='position: relative; margin-left:15px' class="srcDestStrandAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_StrandAutoComplete" style='position: relative; margin-left:25px' class="srcDestStrandAutoComplete" ></span>
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
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Source </b> </span> 
													<input type="text" id="sourcestrand" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> 
													<input type="text" id="destinationstrand" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span>
													 <input type="text" id="SourceTypeStrand" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type </b></span>
													 <input type="text" id="DestinationTypeStrand" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="sourcestranddiv">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Source Lng</b> </span>
													 <input type="text" id="sourcelongstrand" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="sourcestranddiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lng</b></span>
													 <input type="text" id="destinationlongstrand" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="sourcestranddiv">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Source Lat </b> </span> 
													<input type="text" id="sourcelatstrand" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="sourcestranddiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lat</b></span> 
													<input type="text" id="destinationlatstrand" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDivStrand">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span>
													 <input type="text" id="srcCityStrand" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDivStrand">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City</b></span>
													 <input type="text" id="dstCityStrand" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane " id="auxtab" role="tabpanel"
								aria-labelledby="aux-tab-strand">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id="Site_AutocompleteStrand" value='0' style='position: relative; margin-left: 25px' class="auxPtStrandAutocomplete"></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input type='checkbox' id="Manhole_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px" class="input-group-text"><b>By Handhole </b> <input
														type='checkbox' id="Handhole_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input type='checkbox' id="DB_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px"
														class="input-group-text"><b>By Auxiliary Point </b> <input type='checkbox' id="AuxPt_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left: 15px'></span>
												</div>
											</div>
										</div>
									</div>
										<div class="col-sm-1">
											<div class="form-group">
												<div class="input-group-prepend"><div id="loaderDivStrand" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
											</div>	
										</div>
									</div>
										<div class="row">
											<div class="col-sm-5">
											<div class="form-group">
												<div class="input-group-prepend">
				<label class="file"><input type="file" style="font-size:13px" id="importAuxfileStrand" accept=".xlsx" class="btn btn-light file"></label>
												</div>
											</div>	
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<div class="input-group-prepend">
												<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b>
												 <input type='checkbox' id="AuxPt_AppendTo_Strand" value='0' style='position: relative; margin-left: 15px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend"><button id="uploadStrand" class="btn btn-primary" style=" margin-top:10px;">Import</button></div>
											</div>	
										</div>
										<div class="col-sm-1">
											<div class="form-group">
												<div class="input-group-prepend"><div id="loaderDivStrand" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
											</div>	
										</div>
									</div>
									<form>
										<div class="table-responsive-sm" id="auxiliaryDivStrand">
											<table id="auxiliaryTableStrands" class="table table-striped table-bordered table-sm" style="display: block; height: 250px; width: fit-content; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr>
														<th>
															<button type="button" id="selectAll_AuxStrandM" class="main">
																<span class="sub"></span>Select
															</button>
														</th>
														<th style="min-width: 80px">Sequence</th>
														<th width="250px">Auxiliary Name</th>
														<th width="150px">Longitude</th>
														<th width="150px">Latitude</th>
														<th width="150px">Length</th>
														<th width="200px">Driving<br> Distance (km)</th>
														<th>Geo <br>Distance(km)</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
										<button type="button" id="addStrandAuxBelow">Insert Row Below</button>
										<button type="button" id="addStrandAuxAbove">Insert Row Above</button>
										<button type="button" id="delete_AuxStrand">Delete Row</button>
										<button type="button" id="sortByDistanceStrand">Sort by distance</button>
										<button type="button"><b>Draw By </b> 
                                          <select id ="strandDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> 
                                          </button>
										<button type="button" id="setCoordinateStrandAux" >Set Coordinate</button>
										<button type="button" id="calculateDrivingDistanceStrand"> Calculate Driving Distance</button>
										<button type="button" id="calculateGeoDistanceStrand"> Calculate Geo Distance</button>
										<div> <br>
											<b>Distance from last auxiliary to destination:</b>
 										 	<input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDestStrnd" name="distanceLstAuxToDestStrnd" readonly>
											<br>
											<b>Total Distance:</b>
											<input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistanceStrand" name="totalDistanceStrand" readonly>
											<br><b>Driving Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="strandDistanceLstAuxToDestDrivg" name="strandDistanceLstAuxToDestDrivg" readonly>																					
											<br><b>Total Driving Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="strandTotalDistanceDrivg" name="strandTotalDistanceDrivg" readonly>
											<br><b>Total Geo Distance: </b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" id="totalGeoDistanceStrand" readonly></input><br><br>					
										</div>	
									</form></div>
								</div>
							</div>
							<div class="modal-footer"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Tube Popup -->
	<div class="container">
		<div id="TubeModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="TubeHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Tube:</h5>
						<div style="float: right;">
							<button id="savefibertube" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i
								class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="tube-tab" style="color: gold;" data-toggle="tab" href="#tube" role="tab" aria-controls="tube" aria-selected="true">Tube</a></li>
							<li class="nav-item"><a class="nav-link " id="source_dest_TUBE-tab" style="color: gold;" data-toggle="tab" href="#source_dest_TUBE" role="tab" aria-controls="source_dest_TUBE" aria-selected="false">Source_Destination</a></li>
							<li class="nav-item"><a class="nav-link " id="aux-tab-tube" style="color: gold;" data-toggle="tab" href="#auxtabtube" role="tab" aria-controls="auxtabstrand" aria-selected="false">Auxiliary Points</a></li>
						</ul>
						<div class="tab-content" style="min-height: 300px">
							<div class="tab-pane active" id="tube" role="tabpanel" aria-labelledby="tube-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>ID </b> </span> <input type="text" id="TubeID" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Name </b></span> <input type="text" id="TubeName" class="form-control text-input" />
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
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b> Fiber Cable </b> </span> <input type="text" id="fiberCable" readonly class="form-control text-input" />
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
											<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Length </b></span> 
											<input type="text" id="TubeLength" class="form-control text-input" /></div></div>
								</div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
								<span style="width: 150px; font-size: 12px;" class="input-group-text"><b>Driving Distance </b></span> <input type="text" id="tubeDrivDist" class="form-control text-input" />
							</div></div></div></div></div>
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Tube Number </b></span> 
												<input type="text" id="tubeNumber" class="form-control text-input" /></div></div>
									</div>								
									<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
										<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Tube Color </b></span>
												<select id="tubeColor" class="form-control" style="height:38px;">
													<option value="" style="background-color: white;"></option>													
													<option value="blue" style="background-color: white;color:black">blue</option>
													<option value="orange" style="background-color: white;color:black">orange</option>
													<option value="green" style="background-color: white;color:black">green</option>
													<option value="brown" style="background-color: white;color:black">brown</option>
													<option value="gray" style="background-color: white;color:black">gray</option>
													<option value="white" style="background-color: white;color:black">white</option>													
													<option value="red" style="background-color: white; color:black">red</option>
													<option value="black" style="background-color: white;color:black">black</option>
													<option value="yellow" style="background-color: white;color:black">yellow</option>
													<option value="violet" style="background-color: white;color:black">violet</option>													
													<option value="pink" style="background-color: white;color:black">pink</option>
													<option value="aqua" style="background-color: white;color:black">aqua</option>																																							
											   </select>
										</div></div>
									</div>
								</div>
							</div>
								<div class="container-fluid">									
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDivTube">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Tube Type</b></span> 
														<select id="frmtubetype" class="form-control">
															<option value=""></option>
															<option value="g561">G.651</option>
															<option value="g562">G.652</option>
															<option value="g563">G.653</option>
														</select>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDivTube">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Tube Deployment</b></span> 
														<select id="frmtubedep" class="form-control">
															<option value="" selected></option>
															<option value="aerial">Aerial</option>
															<option value="submarine">Submarine</option>
															<option value="underground">Underground</option>
														</select>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDivTube">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Tube Network Level</b></span> 
														<input id="frmtubenetlevel" class="form-control" disabled></input>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDivTube">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Tube Owner</b></span> 
														<select id="frmtubeowner" class="form-control">
															<option value="" selected></option>
															<option value="tkl">TKL</option>
															<option value="nofbi">NOFBI</option>
															<option value="others">Others</option>
														</select>
												</div>
											</div>
										</div>
									</div>
										<div class="row">
										<div class="col-sm-6">
												<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Creation Date</b></span>
													 <input type="text" id="popupCreateDateTube" class="form-control text-input" value="" readonly />
														</div>
													</div>
												</div>
										<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text"  style="width: 123px; font-size: 12px;"><b>Last Modified Date</b></span>
															<input type="text" id="popupLastModifiedDateTube"
																class="form-control text-input" value=""
																readonly />
														</div>
													</div>
												</div>
									</div>
										<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 180px; font-size: 12px;"class="input-group-text"><b>Created By </b></span>
													<input type="text" id="crtdByFiberTube" value='${userFullName}' class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Last Modified By </b></span>
													<input type="text" id="modifiedByFiberTube" value='${userFullName}' class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane " id="source_dest_TUBE" role="tabpanel"
								aria-labelledby="source_dest_TUBE-tab">
								<p></p>
								<div class="container-fluid">
										<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_TubeAutoComplete" style='position: relative; margin-left: 25px' class="srcDestTubeAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="client_TubeAutoComplete" style='position: relative; margin-left:15px' class="srcDestTubeAutoComplete"></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_TubeAutoComplete" style='position: relative; margin-left:10px' class="srcDestTubeAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TubeAutoComplete" style='position: relative; margin-left:15px' class="srcDestTubeAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TubeAutoComplete" style='position: relative; margin-left:25px' class="srcDestTubeAutoComplete" ></span>
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
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Source </b> </span> 
													<input type="text" id="SourceTube" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> 
													<input type="text" id="DestinationTube" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> 
													<input type="text" id="SourceTypeTube" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type </b></span> 
													<input type="text" id="DestinationTypeTube" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceTubeDiv">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Source Lng</b> </span> 
													<input type="text" id="sourcelong" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceTubeDiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lng</b></span>
													 <input type="text" id="destinationlong" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceTubeDiv">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Source Lat </b> </span>
													 <input type="text" id="sourcelat" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="DestinationTubeDiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lat</b></span> 
													<input type="text" id="destinationlat" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
									<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDivTube">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span> 
													<input type="text" id="srcCityTube" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDivTube">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City</b></span>
													 <input type="text" id="dstCityTube" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
								</div>								
							</div>
							<div class="tab-pane " id="auxtabtube" role="tabpanel" aria-labelledby="aux-tab-tube">
								<p></p>
								<div class="container-fluid">
								<div class="row">
										<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="Site_AutocompleteTube" value='0' style='position: relative; margin-left: 25px' class="auxPtTubeAutocomplete"></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="Manhole_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px" class="input-group-text"><b>By Handhole </b>
													 <input type='checkbox' id="Handhole_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;" class="input-group-text"><b>By DB</b> 
													<input type='checkbox' id="DB_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px" class="input-group-text"><b>By Auxiliary Point </b>
													 <input type='checkbox' id="AuxPt_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left: 15px'></span>
												</div>
											</div>
										</div>
									</div>
										<div class="row">
											<div class="col-sm-5">
											<div class="form-group">
												<div class="input-group-prepend">
				<label class="file"><input type="file" style="font-size:13px" id="importAuxfileTube" accept=".xlsx" class="btn btn-light file"></label>
												</div>
											</div>	
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<div class="input-group-prepend">
												<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b>
												 <input type='checkbox' id="AuxPt_AppendTo_Tube" value='0' style='position: relative; margin-left: 15px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend"><button id="uploadTube" class="btn btn-primary" style=" margin-top:10px;">Import</button></div>
											</div>	
										</div>
										<div class="col-sm-1">
											<div class="form-group">
												<div class="input-group-prepend"><div id="loaderDivTube" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
											</div>	
										</div>
									</div>
									<form>
										<div class="table-responsive-sm" id="auxiliaryDivTube">
											<table id="auxiliaryTableTubes" class="table table-striped table-bordered table-sm" style="display: block; height: 250px; width: fit-content; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr>
														<th>
															<button type="button" id="selectAll_AuxTube" class="main">
															<span class="sub"></span>Select
															</button>
														</th>
														<th style="min-width: 80px">Sequence</th>
														<th width="250px">Auxiliary Name</th>
														<th width="150px">Longitude</th>
														<th width="150px">Latitude</th>
														<th width="150px">Length</th>
														<th width="200px">Driving<br> Distance (km)</th>
														<th>Geo <br>Distance(km)</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
										<input type="text" id="Rowindexaux" value="" hidden="">
										<button type="button" id="addTubeAuxBelow">Insert Row Below</button>
										<button type="button" id="addTubeAuxAbove">Insert Row Above</button>
										<button type="button" id="delete_AuxTube">Delete Row</button>
										<button type="button" id="sortByDistanceTube">Sort by distance</button>
										<button type="button"><b>Draw By </b> 
                                          <select id ="tubeDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> 
                                          </button>
										<button type="button" id="setCoordinateTubeAux" >Set Coordinate</button>
										<button type="button" id="calculateDrivingDistanceTube"> Calculate Driving Distance</button>
										<button type="button" id="calculateGeoDistanceTube"> Calculate Geo Distance</button>
										
										<div>
										<br>
											<b>Distance from last auxiliary to destination:</b>
 										 	<input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDestTube" name="distanceLstAuxToDestTube" readonly>
											<br>
											<b>Total Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistanceTube" name="totalDistanceTube" readonly>									
											<br><b>Driving Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="tubeDistanceLstAuxToDestDrivg" name="tubeDistanceLstAuxToDestDrivg" readonly>
											<br><b>Total Driving Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="tubeTotalDistanceDrivg" name="tubeTotalDistanceDrivg" readonly>
											<br><b>Total Geo Distance: </b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" id="totalGeoDistanceTube" readonly></input><br><br>					
										</div>	
									</form>
								</div>
								</div>
							</div>
							<div class="modal-footer"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div id="manholeModalDetails" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Manholes Detail</h5>
						<div style="float:right;">
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
							<i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="container-fluid" style="min-width: 50%;">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 20%;" class="input-group-text"><b>Category</b>
												</span>
												 <input type="text" value="Manholes" style="width: 20%;" readonly class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 20%;" class="input-group-text"><b>Count</b>
												</span> <input type="text" id="numManholes" style="width: 20%;" readonly class="form-control text-input" />
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
	</div>
	<div class="container">
		<div id="handholeModalDetails"
			class="modal fade  custom-class-assignedto-modal" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalCenterTitle"
			aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 class="modal-title"
							style="font-weight: bold; color: white; position: relative; bottom: 12px;">Handhole Details</h5>
						<div style="float:right">
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
							<i class='fa fa-times'></i>
						</button>
						<a class="close modalMinimize ml-3"> <i
							class='fa fa-minus icon-to-change'></i>
						</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="container-fluid" style="min-width: 50%;">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 20%;" class="input-group-text"><b>Category</b>
												</span> <input type="text" value="Handholes" style="width: 20%;" readonly class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 20%;" class="input-group-text"><b>Count</b>
												</span> <input type="text" id="numHandholes" style="width: 20%;" readonly class="form-control text-input" />
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
	</div>
	<div class="container"> 
	<div id="dBModalDetails" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Distribution Boards Details</h5>
						<div style=float:right;">
						<button type="button" name="closePopup" class="close" data-dismiss="modal"> <i class='fa fa-times'></i></button>
						<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
						</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="container-fluid" style="min-width: 50%;">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 20%;" class="input-group-text"><b>Category</b></span>
												 <input type="text" value="Distribution Boards" style="width: 20%;" readonly class="form-control text-input" />
											</div>
										</div>
									</div></div>

								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 20%;" class="input-group-text"><b>Count</b></span>
												 <input type="text" id="numDB" style="width: 20%;" readonly class="form-control text-input" />
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
	</div>
	<menu class="menu" id="mapMenu">
		<li class="menu-item mapMenuItem">
			<button type="button" id="addManhole" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Add Manhole</span></button>
		</li>
		<li class="menu-item mapMenuItem">
			<button type="button" id="addHandhole" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Add Handhole</span></button>
		</li>
		<li class="menu-item mapMenuItem">
			<button type="button" id="addDistributionBoard" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Add Distribution Board</span> </button>
		</li>
		<li class="menu-item mapMenuItem">
			<button type="button" id="getCoordinatePoint" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Get Coordinate</span></button>
		</li>
		<li class="menu-item mapMenuItem disabled origination">
		<button type="button" id="fireOrigination" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Manual Origination</span></button>
		</li>
		<li class="menu-item mapMenuItem disabled termination">
					<button type="button" id="fireTermination" class="menu-btn"><i class="fa fa-folder-plus"></i> <span class="menu-text">Manual Termination</span></button>
		</li>
	</menu>
	<menu class="menu" id="deletePathMenu" style="width: 250px">
		<li class="menu-item delPathMenuItem">
			<button type="button" id="selectCable" class="menu-btn"><i class="fa fa-check-square"></i> <span class="menu-text">Select Cable to delete points</span></button>
		</li>
		<li class="menu-item delPathMenuItem">
			<button type="button" id="approveDelete" class="menu-btn">
				<i class="fa fa-times-circle"></i> <span class="menu-text">Approve delete points</span>
			</button>
		</li>
		<li class="menu-item delPathMenuItem">
			<button type="button" id="cancelDeletePathMenu" class="menu-btn">
				<i class="fa fa-minus-circle"></i> <span class="menu-text">Cancel</span>
			</button>
		</li>
	</menu>	
	<div id="mapOperationModal"class="modal fade  custom-class-assignedto-modal" tabindex="-1"role="dialog" aria-labelledby="exampleModalCenterTitle"aria-hidden="true"  data-keyboard="false" data-backdrop="static"><div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
	<div class="modal-content"><div class="modal-header"style="background-color: #2678CC ; height: 55px; "><h5  class="modal-title"
style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Operations</h5><div style="float: right;"><button class="btn btn-save"style="color: black; font-weight:bold; margin-top:-6px" onclick="MapOperationmarking()">Add Marker</button><button type="button"  onClick="clearMarkers()" class="btn btn-save" style=" margin-left:10px;color: black; font-weight:bold; margin-top:-6px" id="clearMarkers">clear Markers</button>
<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"><i class='fa fa-times'></i></button><a class="close modalMinimize ml-3"> <i
class='fa fa-minus icon-to-change'></i></a></div></div><div class="modal-body"><div class="tab-pane" ><p></p><div class="container-fluid">
<div class="row"><div class="col-sm-2"><div class="form-group"><div class="input-group-prepend"><span style="min-width: 90px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
<input type='checkbox' id="site_operationAutoComplete" style='position: relative; margin-left: 15px' class="mapOperationAutoComplete" onclick="mapFeilds()"></span>
</div></div></div><div class="col-sm-2"><div class="form-group"><div class="input-group-prepend"><span style="min-width: 90px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
<input type='checkbox' id="client_operationAutoComplete" style='position: relative; margin-left:15px' class="mapOperationAutoComplete" onclick="mapFeilds()"></span>
</div></div></div><div class="col-sm-2"><div class="form-group"><div class="input-group-prepend"><span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
<input type='checkbox' id="manhole_operationAutoComplete" style='position: relative; margin-left:10px' class="mapOperationAutoComplete"  onclick="mapFeilds()"></span>
</div></div></div><div class="col-sm-2"><div class="form-group"><div class="input-group-prepend"><span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
<input type='checkbox' id="handhole_operationAutoComplete" style='position: relative; margin-left:15px' class="mapOperationAutoComplete" onclick="mapFeilds()"></span>
</div></div></div><div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 90px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_operationAutoComplete" style='position: relative; margin-left:15px' class="mapOperationAutoComplete" onclick="mapFeilds()"></span>
												</div>
											</div>
										</div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 95px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By Place </b> 
													<input type='checkbox' id="place_operationAutoComplete" style='position: relative; margin-left:10px' class="mapOperationAutoComplete" onclick="placeFeild()"  ></span>
												</div>
											</div>
										</div>										
									</div>
<br>
									<div class="row">
										<div class="col-sm-9">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceDiv">
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Search</b></span> <input
														type="text" id="Searchh"  class="form-control text-input"  />
														 <input type="text" id="placeSearch"  class="form-control text-input" style="display:none" placeholder="" />														
												</div>
											</div>
										</div>										
									</div>
												<div class="row" >
										<div class="col-md-9">
											<div class="form-group" >
												<div class="input-group-prepend"  >
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Longitude </b></span>
														 	<input type="text" id="Lng" class="form-control text-input" readonly  />
												</div>
											</div>
										</div>										
									</div>							
                               <div class="row">
										<div class="col-md-9">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px"> Latitude </b></span> <input type="text" id="Lat" class="form-control text-input" readonly />
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
		<!-- change cable color  -->
		<div id="changeCableColorModal"class="modal fade  custom-class-assignedto-modal" tabindex="-1"role="dialog" aria-labelledby="exampleModalCenterTitle"aria-hidden="true"  data-keyboard="false" data-backdrop="static"><div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
	        <div class="modal-content"><div class="modal-header"style="background-color: #2678CC ; height: 55px; ">
	         <h5  class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Cable Color</h5>
	          <div style="float: right;">
							<button id="saveFiberPathColor" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button> 
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"><i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
			  </div>
        </div>
        <div class="modal-body"><div class="tab-pane" ><p></p><div class="container-fluid">
        <div id="boqChangeColorCable" ></div>
        <form>
				<div class="table-responsive-sm">
					<table id="changeCableColorTable" class="table table-striped table-bordered table-sm " style="display: block; height: 250px; overflow-y: auto;">
						<thead style="background-color: #00757C;">
							<tr>
							<th style="min-width: 10px; color: gold;  font-size: 20px; text-align: center; " ></th>							
						    <th style="min-width: 250px; color: gold;  font-size: 20px; text-align: center; " >Cable Owner</th>
							<th style="min-width: 250px; color: gold;  font-size: 20px; text-align: center; " >Color</th>
						   
														
							</tr></thead><tbody id="colorChangecableTBody"></tbody></table></div>
										
		</form>
        </div>
		<div class="modal-footer"></div></div></div>
		</div>
		</div>
		</div>
	<!-- /////////////////////// -->
	<div class="container">
		<div id="fiberPathModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 850px;">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="fiberHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Fiber Cable</h5>
						<div style="float: right;">
							<button id="saveFiberPath" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="fiber-tab" style="color: gold;" data-toggle="tab" href="#fiber" role="tab" aria-controls="fiber" aria-selected="true">Fiber</a></li>
							<li class="nav-item"><a class="nav-link " id="source_dest-tab" style="color: gold;" data-toggle="tab" href="#source_dest" role="tab" aria-controls="source_dest" aria-selected="false">Source_Destination</a></li>
							<li class="nav-item"><a class="nav-link " id="conduit-tab" style="color: gold;" data-toggle="tab" href="#conduit" role="tab" aria-controls="conduit" aria-selected="false">Conduit</a></li>
							<li class="nav-item"><a class="nav-link " id="fiber_aux_tab" style="color: gold;" data-toggle="tab" href="#auxiliary" role="tab" aria-controls="auxiliary" aria-selected="false">Auxiliary Points</a></li>
							<li class="nav-item"><a class="nav-link " id="tubes-tab" style="color: gold;" data-toggle="tab" href="#tubes" role="tab" aria-controls="tubes" aria-selected="false">Tubes</a></li>
							<li class="nav-item"><a class="nav-link " id="strands-tab" style="color: gold;" data-toggle="tab" href="#strands" role="tab" aria-controls="strands" aria-selected="false">Strands</a></li>
							<li class="nav-item"><a class="nav-link " id="relatedcable-tab" style="color: gold;" data-toggle="tab" href="#related_cable" role="tab" aria-controls="related_cable" aria-selected="false">Related Cable</a></li>
							<li class="nav-item"><a class="nav-link " id="lastmilejunction-tab" style="color: gold;" data-toggle="tab" href="#lastmile_junction" role="tab" aria-controls="lastmile_junction" aria-selected="false">Junction</a>
							</li>
						</ul>
						<div class="tab-content" style="min-height: 300px">
							<div class="tab-pane active" id="fiber" role="tabpanel" aria-labelledby="fiber-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>ID </b> </span>
														 <input type="text" id="FiberPathId" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;"
														class="input-group-text"><b>Name </b></span> <input
														type="text" id="fiberName" class="form-control text-input" />
												</div></div></div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6" id="itemDiv">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;"
														class="input-group-text"><b>Item Code </b></span> <input type="text" id="ItemCodeId" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;"
														class="input-group-text"><b>Tubes Number </b></span> <input
														type="text" id="NumTubes" class="form-control text-input" />
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
													<span style="width: 100px; font-size: 12px;"
														class="input-group-text"><b>Length </b></span> <input type="text" id="FiberLength" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;"
														class="input-group-text"><b>Strands Number </b></span> <input type="text" id="NumStrands" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>								
									<div class="row"><div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
													<span style="width: 150px; font-size: 12px;"
														class="input-group-text"><b>Driving Distance </b></span> <input
														type="text" id="FiberDrivDist"
														class="form-control text-input" />
												</div></div></div></div>
									<div class="row">
											<div class="col-sm-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span style="width: 100px; font-size: 12px;"
														class="input-group-text"><b>Fiber Type </b></span> 
														<select
															id="fibertype" class="form-control">
															<option value=""></option>
															<option value="g561">G.651</option>
															<option value="g562">G.652</option>
															<option value="g563">G.653</option>
														</select>
													</div>
												</div>
											</div>										
											<div class="col-sm-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span style="font-size: 12px;"
														class="input-group-text"><b>Fiber Deployment </b></span> <select id="fiberdeployment" class="form-control">
															<option value="" selected></option>
															<option value="aerial">Aerial</option>
															<option value="submarine">Submarine</option>
															<option value="underground">Underground</option>
														</select>
													</div>
												</div>
											</div>
										</div>										
										<div class="row">
											<div class="col-sm-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span style="font-size: 12px;" class="input-group-text"><b>Fiber Network Level </b></span> <select id="fibernetlevel" class="form-control">
															<option value="backbone" selected >Backbone</option>
															<option value="metro">Metro</option>
															<option value="access">Access</option>
					
														</select>
													</div>
												</div>
											</div>										
											<div class="col-sm-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Fiber Owner </b></span> <select id="fiberowner" class="form-control">
															<option value="" selected></option>
															<option value="tkl">TKL</option>
															<option value="nofbi">NOFBI</option>
															<option value="others">Others</option>
														</select>
													</div>
												</div>
											</div>
										</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;"
														class="input-group-text"><b>Single Mode </b> <input
														type='checkbox' class="fiberMode" id="SingleMode"
														name="Single Mode" style='position: relative; left: 20px;'></span>
													<span
														style="min-width: 150px; font-size: 12px; margin-left: 50px"
														class="input-group-text"><b>Multimode </b> <input
														type='checkbox' class="fiberMode" id="Multimode"
														name="Multimode" style='position: relative; left: 20px;'></span>
												</div>
											</div>
										</div></div>
											<div class="row">
										<div class="col-sm-6">
												<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;"
														class="input-group-text"><b>Creation Date</b></span>
													 <input type="text" id="popupCreateDateFiber"  class="form-control text-input" value="" readonly />
														</div>
													</div>
												</div>
										<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text"  style="width: 123px; font-size: 12px;"><b>Last Modified Date</b></span>
															<input type="text" id="popupLastModifiedDateFiber" class="form-control text-input" value="" readonly />
														</div>
													</div>
												</div>
									</div>								
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 180px; font-size: 12px;"
														class="input-group-text"><b>Created By </b></span><input
														type="text" id="crtdByFiberCable" value='${userFullName}'
														class="form-control text-input" readonly />												
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Last Modified By </b></span><input
														type="text" id="modifiedByFiberCable" value='${userFullName}' class="form-control text-input" readonly />							
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane " id="conduit" role="tabpanel" aria-labelledby="conduit-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Condiut_Id </b></span> <input type="text" id="Condiut_Id" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Condiut_Name </b></span> <input type="text" id="Condiut_Name" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="source_dest" role="tabpanel"
								aria-labelledby="source_dest-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_CableAutoComplete" style='position: relative; margin-left: 25px' class="srcDestCableAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="client_CableAutoComplete" style='position: relative; margin-left:15px' class="srcDestCableAutoComplete"></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_CableAutoComplete" style='position: relative; margin-left:10px' class="srcDestCableAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_CableAutoComplete" style='position: relative; margin-left:15px' class="srcDestCableAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_CableAutoComplete" style='position: relative; margin-left:25px' class="srcDestCableAutoComplete" ></span>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceDiv">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Source</b></span> <input
														type="text" id="Source" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group" id="DestinationDiv">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> <input type="text" id="Destination" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> <input type="text" id="SourceType" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type </b></span> <input type="text" id="DestinationType" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lng </b></span> <input type="text" id="SourceLng" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lng </b></span> <input type="text" id="DestinationLng" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lat </b></span> <input type="text" id="SourceLat" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lat</b></span> <input type="text" id="DestinationLat" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDiv">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span> <input type="text" id="srcCity" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDiv">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City</b></span> <input type="text" id="dstCity" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane " id="tubes" role="tabpanel"
								aria-labelledby="tubes-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id="Site_AutocompleteTubeFiber" value='0' style='position: relative; margin-left: 25px' class="fiberTubeAutocomplete"></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position: relative; margin-left:-10px"
														class="input-group-text"><b>By Client </b> <input
														type='checkbox' id="Client_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input
														type='checkbox' id="Manhole_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px"
														class="input-group-text"><b>By Handhole </b> <input
														type='checkbox' id="Handhole_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input
														type='checkbox' id="DB_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>										
									</div>
									<form>
										<div class="table-responsive-sm">
											<table id="tubesTable"
												class="table table-striped table-bordered table-sm " style="display: block; height: 250px; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr>
														<th>
															<button type="button" id="selectAll_Tubes" class="main">
																<span class="sub"></span>Select
															</button>
														</th>
														<th style="min-width: 150px">Auxiliary points</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 100px">Tube ID</th>
														<th style="min-width: 150px">Source</th>
														<th style="min-width: 100px">Longitude</th>
														<th style="min-width: 100px">Latitude</th>
														<th style="min-width: 100px">Source City</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 150px">Destination</th>
														<th style="min-width: 100px">Longitude</th>
														<th style="min-width: 100px">Latitude</th>
														<th style="min-width: 140px">Destination City</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 150px">Tube Length</th>
														<th style="min-width: 150px">Total Length</th>
														<th style="min-width: 180px">Tube Name</th>
														<th style="min-width: 180px">Tube Type</th>
														<th style="min-width: 180px">Tube Deployment</th>
														<th style="min-width: 180px">Tube Network Level</th>
														<th style="min-width: 180px">Tube Owner</th>
														<th style="min-width: 150px">Tube Number</th>
														<th style="min-width: 150px">Tube Color</th>
														<th style="min-width: 150px">Drawing Type</th>
										</tr></thead><tbody></tbody></table></div>
										<input type="text" id="RowIndex2" value="" hidden>
										<button type="button" id="add_Tube">Add Row</button>
										<button type="button" id="delete_Tube">Delete Row</button>
									</form></div></div>
							<div class="tab-pane " id="strands" role="tabpanel" aria-labelledby="strands-tab"><p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id="Site_AutocompleteStrandFiber" value='0' style='position: relative; margin-left: 25px' class="fiberStrandAutocomplete"></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position: relative; margin-left:-10px"
														class="input-group-text"><b>By Client </b> <input
														type='checkbox' id="Client_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete"
														style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input type='checkbox' id="Manhole_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px"
														class="input-group-text"><b>By Handhole </b> <input type='checkbox' id="Handhole_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input type='checkbox' id="DB_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete" style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>	
									</div>
									<form>
										<div class="table-responsive-sm" id="strandsDiv">
											<table id="strandsTable"
												class="table table-striped table-bordered table-sm "
												style="display: block; height: 250px; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr><th>
															<button type="button" id="selectAll_Strands" class="main">
																<span class="sub"></span>Select</button>
														</th>
														<th style="min-width: 150px">Auxiliary points</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 100px">Strand Id</th>
														<th style="min-width: 100px">Tube</th>
														<th style="min-width: 150px">Source</th>
														<th style="min-width: 100px">Longitude</th>
														<th style="min-width: 100px">Latitude</th>
														<th style="min-width: 100px">Source City</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 150px">Destination</th>
														<th style="min-width: 100px">Longitude</th>
														<th style="min-width: 100px">Latitude</th>
														<th style="min-width: 150px">Destination City</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 150px">Length</th>
														<th style="min-width: 180px">Strand Name</th>
														<th style="min-width: 180px">Strand Type</th>
														<th style="min-width: 180px">Strand Deployment</th>
														<th style="min-width: 180px">Strand Network Level</th>
														<th style="min-width: 180px">Strand Owner</th>
														<th style="min-width: 180px">Strand Number</th>
														<th style="min-width: 180px">Strand Color</th>
														<th style="min-width: 150px">Drawing Type</th>
													</tr></thead><tbody></tbody></table></div>
										<button type="button" id="add_Strand">Add Row</button>
										<button type="button" id="delete_Strand">Delete Row</button>
									</form></div></div>
							<div class="tab-pane " id="auxiliary" role="tabpanel" aria-labelledby="fiber_aux_tab"><p></p>
								<div class="container-fluid"><div class="row"><div class="col-sm-2"><div class="form-group">
									<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id=Site_AutocompleteCable value='0' style='position: relative; margin-left: 25px' class="auxPtAutocomplete"></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px" class="input-group-text"><b>By Manhole </b> <input
														type='checkbox' id=Manhole_AutocompleteCable value='0' class="auxPtAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px" class="input-group-text"><b>By Handhole </b> <input
														type='checkbox' id="Handhole_AutocompleteCable" value='0' class="auxPtAutocomplete" style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;" class="input-group-text"><b>By DB</b> <input
														type='checkbox' id=DB_AutocompleteCable value='0' class="auxPtAutocomplete" style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px"class="input-group-text"><b>By Auxiliary Point </b> <input
														type='checkbox' id="AuxPt_AutocompleteCable" value='0' class="auxPtAutocomplete" style='position: relative; margin-left: 15px'></span>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-5">
											<div class="form-group">
												<div class="input-group-prepend">
<label class="file"><input type="file" style="font-size:13px" id="importAuxfile" accept=".xlsx" class="btn btn-light file"></label>
												</div>
											</div>	
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b> <input
														type='checkbox' id="AuxPt_AppendTo" value='0' style='position: relative; margin-left: 15px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend"><button id="upload" class="btn btn-primary" style=" margin-top:10px;">Import</button></div>
											</div>	
										</div>
										<div class="col-sm-1">
											<div class="form-group">
												<div class="input-group-prepend"><div id="loaderDiv" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
											</div>	
										</div>
									</div>
									<form>
										<div class="table-responsive-sm" id="auxiliaryDiv">
											<table id="auxiliaryTable"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 250px; overflow: auto; max-width:800px; margin-right:20px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														<th>
															<button type="button" id="selectAll_Aux" class="main">
																<span class="sub"></span>Select
															</button>
														</th>
														<th style="min-width: 80px" class="headcol">Sequence</th>
														<th width="250px">Auxiliary Name</th>
														<th width="150px">Longitude</th>
														<th width="150px">Latitude</th>
														<th width="150px">Length</th>
														<th width="200px">Driving<br> Distance (km)</th>
														<th>Geo <br>Distance(km)</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
										<button type="button" id="addAuxBelow">Insert Row Below</button>
										<button type="button" id="addAuxAbove">Insert Row Above</button>
										<button type="button" id="delete_Aux" >Delete Row</button>
										<button type="button" id="sortingDistance" >Sort By Distance</button>
                                        <button type="button">
                                         <b>Draw By </b> 
                                          <select id = "drawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option> 
                                          </select> 
                                          </button>
										<button type="button" id="setCoordinateFiberAux" >Set Coordinate</button>
										<button type="button" id="calculatedrivingdistance"> Calculate Driving Distance</button>
										<button type="button" id="calculateGeodistance"> Calculate Geo Distance</button>
										<button type="button" id="editManHand">Grab Nearest Manhole and HandHole</button>
										<div>
										<br>
										<br>
											<b>Distance from last auxiliary to destination:</b>
 										 	<input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDest" name="distanceLstAuxToDest" readonly>
											<br>
											<b>Total Distance:</b>
											<input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistance" name="totalDistance" readonly>										
										<br>
											<b>Driving Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDestDrivg" name="distanceLstAuxToDest" readonly>
											<br><b>Total Driving Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistanceDrivg" name="totalDistance" readonly>
											<br><b>Total Geo Distance: </b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" id="totalGeoDistance" readonly></input><br><br>					
											
										</div>	
									</form>
								</div>
							</div>
						</div>
						
							<div class="tab-pane" id="related_cable" role="tabpanel" aria-labelledby="relatedcable-tab" style="min-height: 400px">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Strand#</b></span> <input
														type="text" id="relatedCableStrandNb" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
										<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Strand Color </b></span>
												<select id="relatedCableStrandColor" class="form-control" style="height:38px;">
													<option value="" style="background-color: white;"></option>													
													<option value="blue" style="background-color: white;color:black">blue</option>
													<option value="orange" style="background-color: white;color:black">orange</option>
													<option value="green" style="background-color: white;color:black">green</option>
													<option value="brown" style="background-color: white;color:black">brown</option>
													<option value="gray" style="background-color: white;color:black">gray</option>
													<option value="white" style="background-color: white;color:black">white</option>													
													<option value="red" style="background-color: white; color:black">red</option>
													<option value="black" style="background-color: white;color:black">black</option>
													<option value="yellow" style="background-color: white;color:black">yellow</option>
													<option value="violet" style="background-color: white;color:black">violet</option>													
													<option value="pink" style="background-color: white;color:black">pink</option>
													<option value="aqua" style="background-color: white;color:black">aqua</option>																																							
											   </select>
										</div></div>
									</div>
									</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px; font-size: 12px;"
													class="input-group-text"><b>Strand ID</b></span> <input
													type="text" id="relatedCableStrandID" class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Strand Name</b></span> <input type="text" id="relatedCableStrandName" class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px; font-size: 12px;"
													class="input-group-text"><b>Tube#</b></span> <input
													type="text" id="relatedCableTubeNb" class="form-control text-input" />
											</div>
										</div>
									</div>
										<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
										<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Tube Color </b></span>
												<select id="relatedCableTubeColor" class="form-control" style="height:38px;">
													<option value="" style="background-color: white;"></option>													
													<option value="blue" style="background-color: white;color:black">blue</option>
													<option value="orange" style="background-color: white;color:black">orange</option>
													<option value="green" style="background-color: white;color:black">green</option>
													<option value="brown" style="background-color: white;color:black">brown</option>
													<option value="gray" style="background-color: white;color:black">gray</option>
													<option value="white" style="background-color: white;color:black">white</option>													
													<option value="red" style="background-color: white; color:black">red</option>
													<option value="black" style="background-color: white;color:black">black</option>
													<option value="yellow" style="background-color: white;color:black">yellow</option>
													<option value="violet" style="background-color: white;color:black">violet</option>													
													<option value="pink" style="background-color: white;color:black">pink</option>
													<option value="aqua" style="background-color: white;color:black">aqua</option>																																							
											   </select>
										</div></div>
									</div>
								</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;"
												class="input-group-text"><b>Tube ID</b></span> <input
												type="text" id="relatedCableTubeID" class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Tube Name</b></span> <input type="text" id="relatedCableTubeName" class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;"
												class="input-group-text"><b>Cable ID</b></span> <input
												type="text" id="relatedCableID" class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Cable Name</b></span> <input type="text" id="relatedCableName" class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 170px; font-size: 12px;"
												class="input-group-text"><b>Other Last Mile ID</b></span> <input
												type="text" id="otherLastMileID" class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 170px; font-size: 12px;" class="input-group-text"><b>Other Last Mile Name</b></span> <input type="text" id="otherLastMileName" class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;"
												class="input-group-text"><b>Other Side:</b></span>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_RelatedCableAutoComplete" style='position: relative; margin-left: 25px' class="relatedCableAutoComplete" ></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_RelatedCableAutoComplete" style='position: relative; margin-left:15px' class="relatedCableAutoComplete"></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_RelatedCableAutoComplete" style='position: relative; margin-left:10px' class="relatedCableAutoComplete" ></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
											<input type='checkbox' id="handhole_RelatedCableAutoComplete" style='position: relative; margin-left:15px' class="relatedCableAutoComplete" ></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
											<input type='checkbox' id="db_RelatedCableAutoComplete" style='position: relative; margin-left:25px' class="relatedCableAutoComplete" ></span>
										</div>
									</div>
								</div>
							</div>
									
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span id="relatedSiteID" style="width: 140px; font-size: 12px;" class="input-group-text relatedLocationID"><b>Site ID</b></span>
											<span id="relatedClientID" style="width: 140px; font-size: 12px; display: none;" class="input-group-text relatedLocationID"><b>Client ID</b></span>
											<span id="relatedManholeID" style="width: 140px; font-size: 12px; display: none;" class="input-group-text relatedLocationID"><b>Manhole ID</b></span>
											<span id="relatedHandholeID" style="width: 140px; font-size: 12px; display: none;" class="input-group-text relatedLocationID"><b>Handhole ID</b></span>
											<span id="relatedDistBoardID" style="width: 160px; font-size: 12px; display: none;" class="input-group-text relatedLocationID"><b>Distribution Board ID</b></span>
												 <input type="text" id="relatedCableLocationID" class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span id="relatedSiteName" style="width: 140px; font-size: 12px;" class="input-group-text relatedLocationName"><b>Site Name</b></span>
											<span id="relatedClientName" style="width: 140px; font-size: 12px; display: none;" class="input-group-text relatedLocationName"><b>Client Name</b></span>
											<span id="relatedManholeName" style="width: 140px; font-size: 12px; display: none;" class="input-group-text relatedLocationName"><b>Manhole Name</b></span>
											<span id="relatedHandholeName" style="width: 140px; font-size: 12px; display: none;" class="input-group-text relatedLocationName"><b>Handhole Name</b></span>
											<span id="relatedDistBoardName" style="width: 160px; font-size: 12px; display: none;" class="input-group-text relatedLocationName"><b>Distribution Board Name</b></span>
												 <input type="text" id="relatedCableLocationName" class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<div class="input-group-prepend">
										<span id="relatedLocationCity"style="width: 140px; font-size: 12px;"
											class="input-group-text"><b>City</b></span>
										 <span id="relatedClientPhoneNb" style="width: 140px; font-size: 12px; display: none;"
											class="input-group-text"><b>Client Number</b></span><input
											type="text" id="relatedCableCity" class="form-control text-input" readonly />
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="form-group">
									<div class="input-group-prepend">
										<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Location Type</b></span> <input 
										type="text" id="relatedCableLocationType" class="form-control text-input" readonly/>
									</div>
								</div>
							</div>
						</div>
									
						</div>
					</div>
					
						<div class="tab-pane" id="lastmile_junction" role="tabpanel" aria-labelledby="lastmilejunction-tab" style="min-height: 400px">
								<p></p>
								<div class="container-fluid">
								<form>
								<div class="table-responsive-sm" id="relatedCableJctDiv">
									<table id="relatedCableJctTable" class="table table-striped table-sm " style="display: block; height: 250px; overflow-y: auto;">
										<thead style="background: #E9ECEF;">
											<tr> 
												<th> <button type="button" id="relatedCableJctSelectAll" class="main"><span class="sub"></span>Select </button></th>
												<!--  <th style="min-width: 80px">Sequence</th>
												<th style="background-color: #00757C" width="-10px"></th> -->
												<th style="min-width: 190px">Junction ID</th>
												<th style="min-width: 190px">Junction Name</th>
												
											</tr></thead><tbody></tbody></table></div>
											
											<button type="button" id="relatedCableJctAddRow">Add Row</button>
											<button type="button" id="relatedCableJctDelRow">Delete Row</button>
									</form>
							</div>
						</div>
						
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<!-- /////////////////////// -->
	<div class="container">
		<div id="trenchModal"
			class="modal fade  custom-class-assignedto-modal" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalCenterTitle"
			aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header"
						style="background-color: #2678CC ; height: 55px;">
						<h5 id="trenchHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Trench
						</h5>
						<div style="float: right;">
							<button id="saveTrench" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="trench-tab" style="color: gold;" data-toggle="tab" href="#trench" role="tab" aria-controls="trench" aria-selected="true">Trench</a></li>
							<li class="nav-item"><a class="nav-link " id="source_dest_trench-tab" style="color: gold;" data-toggle="tab" href="#source_dest_trench" role="tab" aria-controls="source_dest_trench" aria-selected="false">Source_Destination</a></li>
							<li class="nav-item"><a class="nav-link " id="auxiliary_trench-tab" style="color: gold;" data-toggle="tab" href="#auxiliary_trench" role="tab" aria-controls="auxiliary_trench" aria-selected="false">Auxiliary Points</a></li>
						</ul>
						<div class="tab-content" style="min-height: 300px">
							<div class="tab-pane active" id="trench" role="tabpanel"
								aria-labelledby="trench-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>ID </b> </span> <input type="text" id="trenchId" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Name </b></span> <input type="text" id="trenchName" class="form-control text-input" />
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
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Max Capacity </b></span> <input type="text" id="trenchCapacity" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Ducts Number </b></span> <input type="text" id="NumDucts" class="form-control text-input" />
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
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Length </b></span>
														 <input type="text" id="trenchLength" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
								<span style="width: 150px; font-size: 12px;" class="input-group-text"><b>Driving Distance </b></span> <input type="text" id="trenchDrivDist" class="form-control text-input" />
								</div></div></div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
												<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Creation Date</b></span>
													 <input type="text" id="popupCreateDateTrench"  class="form-control text-input" value=""  readonly />
														</div>
													</div>
												</div>
										<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text"  style="width: 123px; font-size: 12px;"><b>Last Modified Date</b></span>
															<input type="text" id="popupLastModifiedDateTrench" class="form-control text-input" value="" readonly />
														</div></div></div></div></div>													
									<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 180px; font-size: 12px;" class="input-group-text"><b>Created By </b></span>
														<input type="text" id="crtdByTrench" value='${userFullName}' class="form-control text-input" readonly />												
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Last Modified By </b></span>
														<input type="text" id="modifiedByTrench" value='${userFullName}' class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div></div>
							</div>
							<div class="tab-pane" id="source_dest_trench" role="tabpanel"
								aria-labelledby="source_dest_trench-tab">
								<p></p>
									<div class="container-fluid">
										<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_TrenchAutoComplete" style='position: relative; margin-left: 25px' class="srcDestTrenchAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="client_TrenchAutoComplete" style='position: relative; margin-left:15px' class="srcDestTrenchAutoComplete"></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_TrenchAutoComplete" style='position: relative; margin-left:10px' class="srcDestTrenchAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TrenchAutoComplete" style='position: relative; margin-left:15px' class="srcDestTrenchAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TrenchAutoComplete" style='position: relative; margin-left:25px' class="srcDestTrenchAutoComplete" ></span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceTrenchDiv">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source</b></span> <input
														type="text" id="SourceTrench" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group" id="DestinationTrenchDiv">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> <input
														type="text" id="DestinationTrench" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> <input
														type="text" id="SourceTrenchType" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type </b></span> <input
														type="text" id="DestinationTrenchType" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lng </b></span>
													<input type="text" id="SourceTrenchLng" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lng </b></span> 
													<input type="text" id="DestinationTrenchLng" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lat </b></span> 
														<input type="text" id="SourceTrenchLat" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
												<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lat</b></span>
												<input type="text" id="DestinationTrenchLat" class="form-control text-input" readonly />
												</div></div></div></div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcTrenchDiv">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span>
														 <input type="text" id="srcCityTrench" class="form-control text-input" readonly />
												</div></div></div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstTrenchDiv">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City</b></span> 
														<input type="text" id="dstCityTrench" class="form-control text-input" readonly />
												</div></div></div></div></div></div>
							<div class="tab-pane " id="auxiliary_trench" role="tabpanel" aria-labelledby="auxiliary_trench-tab">
								<p></p>
								<div class="container-fluid">
								<div class="row">
										<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id="Site_AutocompleteTrench" value='0' style='position: relative; margin-left: 25px' class="auxPtTrenchAutocomplete"></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input
														type='checkbox' id="Manhole_AutocompleteTrench" value='0' class="auxPtTrenchAutocomplete"
														style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px"
														class="input-group-text"><b>By Handhole </b> <input type='checkbox' id="Handhole_AutocompleteTrench" value='0' class="auxPtTrenchAutocomplete"
														style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input type='checkbox' id="DB_AutocompleteTrench" value='0' class="auxPtTrenchAutocomplete"
														style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px" class="input-group-text"><b>By Auxiliary Point </b>
														 <input type='checkbox' id="AuxPt_AutocompleteTrench" value='0' class="auxPtTrenchAutocomplete" style='position: relative; margin-left: 15px'></span>
												</div>
											</div>
										</div>
									</div>									
							<div class="col-sm-1">
								<div class="form-group">
									<div class="input-group-prepend"><div id="loaderDivTrench" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
									</div></div></div>	
						<div class="row">
						<div class="col-sm-5">
							<div class="form-group">
								<div class="input-group-prepend">
					<label class="file"><input type="file" style="font-size:13px" id="importAuxfileTrench" accept=".xlsx" class="btn btn-light file"></label>
							</div></div></div>
							<div class="col-sm-4">
								<div class="form-group">
									<div class="input-group-prepend">
										<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b>
												 <input type='checkbox' id="AuxPt_AppendTo_Trench" value='0' style='position: relative; margin-left: 15px'></span>
												</div>
											</div></div>
							<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend"><button id="uploadTrench" class="btn btn-primary" style=" margin-top:10px;">Import</button></div>
											</div></div>
										<div class="col-sm-1">
											<div class="form-group">
												<div class="input-group-prepend"><div id="loaderDivTrench" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
											</div>	
										</div>
									</div>												
									<form>
										<div class="table-responsive-sm" id="auxiliary_trenchDiv">
											<table id="auxiliary_trenchTable" class="table table-striped table-bordered table-sm" style="display: block; height: 250px; width: fit-content; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr>
														<th>
															<button type="button" id="selectAllTrench_Aux" class="main">
																<span class="sub"></span>Select
															</button>
														</th>
														<th style="min-width: 80px">Sequence</th>
														<th width="250px">Auxiliary Name</th>
														<th width="150px">Longitude</th>
														<th width="150px">Latitude</th>
														<th width="150px">Length</th>
														<th width="200px">Driving<br> Distance (km)</th>
														<th>Geo <br>Distance(km)</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
										<button type="button" id="addTrenchAuxBelow">Insert Row Below</button>
										<button type="button" id="addTrenchAuxAbove">Insert Row Above</button>
										<button type="button" id="delete_Trench_Aux">Delete Row</button>
										<button type="button" id="sortByDistanceTrench">Sort by distance</button>
										<button type="button"><b>Draw By </b> <select id ="trenchDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> 
                                          </button>
										<button type="button" id="setCoordinateTrenchAux" >Set Coordinate</button>
										<button type="button" id="calculateDrivingDistanceTrench"> Calculate Driving Distance</button>
										<button type="button" id="calculateGeoDistanceTrench"> Calculate Geo Distance</button>
										<div>
											<br><b>Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDestTrench" name="distanceLstAuxToDestTrench" readonly>
											<br><b>Total Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistanceTrench" name="totalDistanceTrench" readonly>									
											<br><b>Driving Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="trenchDistanceLstAuxToDestDrivg" name="trenchDistanceLstAuxToDestDrivg" readonly>										
											<br><b>Total Driving Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="trenchTotalDistanceDrivg" name="trenchTotalDistanceDrivg" readonly>
											<br><b>Total Geo Distance: </b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" id="totalGeoDistanceTrench" readonly></input><br><br>																
										</div>	
									</form></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	<!-- /////////////////////// -->
	<div class="container">
		<div id="ductModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 860px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="ductHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Duct</h5>
						<div style="float: right;">
							<button id="saveDuct" class="btn btn-save" style="color: black; font-weight:bold;margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"><i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div></div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="duct-tab" style="color: gold;" data-toggle="tab" href="#duct" role="tab" aria-controls="duct" aria-selected="true">Duct</a></li>
							<li class="nav-item"><a class="nav-link " id="source_dest_duct-tab" style="color: gold;" data-toggle="tab" href="#source_dest_duct" role="tab" aria-controls="source_dest_duct" aria-selected="false">Source_Destination</a></li>
							<li class="nav-item"><a class="nav-link " id="auxiliary_duct-tab" style="color: gold;" data-toggle="tab" href="#auxiliary_duct" role="tab" aria-controls="auxiliary_duct" aria-selected="false">Auxiliary Points</a></li>
						</ul>
						<div class="tab-content" style="min-height: 300px">
							<div class="tab-pane active" id="duct" role="tabpanel"
								aria-labelledby="trench-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>ID </b> </span> 
													<input type="text" id="ductId" readonly class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Name </b></span>
													<input type="text" id="ductName" class="form-control text-input" />
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
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Trench </b> </span> 
													<input type="text" id="ductTrenchId" readonly class="form-control text-input" />
												</div>
											</div>
								</div></div></div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Num Fiber Cables </b></span>
													<input type="text" id="ductsFiberCables" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Num Tubes </b></span>
													<input type="text" id="ductsTubes" class="form-control text-input" />
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
													<span style="width: 100px; font-size: 12px;"class="input-group-text"><b>Num Strands </b></span>
													<input type="text" id="ductsStrands" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;"class="input-group-text"><b>Length </b></span> 
													<input type="text" id="ductsLength" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
								<span style="width: 150px; font-size: 12px;" class="input-group-text"><b>Driving Distance </b></span> <input type="text" id="ductDrivDist" class="form-control text-input" />
							</div></div></div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
												<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Creation Date</b></span>
													 <input type="text" id="popupCreateDateDuct" class="form-control text-input" value="" readonly />
														</div>
													</div>
												</div>

										<div class="col-sm-6">
													<div class="form-group">
														<div class="input-group-prepend">
															<span class="input-group-text"  style="width: 123px; font-size: 12px;"><b>Last Modified Date</b></span>
															<input type="text" id="popupLastModifiedDateDuct" class="form-control text-input" value="" readonly />
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
													<span style="width: 180px; font-size: 12px;"  class="input-group-text"><b>Created By </b></span>
													<input type="text" id="crtdByDuct" value='${userFullName}' class="form-control text-input" readonly />
												</div>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Last Modified By </b></span>
													<input type="text" id="modifiedByDuct" value='${userFullName}' class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div></div>
							</div>
							<div class="tab-pane" id="source_dest_duct" role="tabpanel" aria-labelledby="source_dest_duct-tab">
								<p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_DuctAutoComplete" style='position: relative; margin-left: 25px' class="srcDestDuctAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="client_DuctAutoComplete" style='position: relative; margin-left:15px' class="srcDestDuctAutoComplete"></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_DuctAutoComplete" style='position: relative; margin-left:10px' class="srcDestDuctAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_DuctAutoComplete" style='position: relative; margin-left:15px' class="srcDestDuctAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_DuctAutoComplete" style='position: relative; margin-left:25px' class="srcDestDuctAutoComplete" ></span>
												</div>
											</div>
										</div>
									</div>	
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceDuctDiv">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source</b></span> <input type="text" id="SourceDuct" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group" id="DestinationDuctDiv">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> <input
														type="text" id="DestinationDuct" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Source Type</b></span> <input type="text" id="SourceDuctType" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Destination Type </b></span> <input
														type="text" id="DestinationDuctType" class="form-control text-input" />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Source Lng </b></span> <input
														type="text" id="SourceDuctLng" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Destination Lng </b></span> <input
														type="text" id="DestinationDuctLng" class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
								<div class="row">
 								<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Source Lat </b></span> <input
														type="text" id="SourceDuctLat"
														class="form-control text-input" readonly />
												</div>
											</div>
										</div>
								<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Destination Lat</b></span> <input
														type="text" id="DestinationDuctLat"
														class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="srcDuctDiv">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Source City </b></span> <input
														type="text" id="srcCityDuct"
														class="form-control text-input" readonly />
												</div>
											</div>
										</div>

										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDuctDiv">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Destination City</b></span> <input
														type="text" id="dstCityDuct"
														class="form-control text-input" readonly />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane " id="auxiliary_duct" role="tabpanel"
								aria-labelledby="auxiliary_duct-tab">
								<p></p>
								<div class="container-fluid">
								<div class="row">
										<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id="Site_AutocompleteDuct" value='0' style='position: relative; margin-left: 25px' class="auxPtDuctAutocomplete"></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input
														type='checkbox' id="Manhole_AutocompleteDuct" value='0' class="auxPtDuctAutocomplete"
														style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px"
														class="input-group-text"><b>By Handhole </b> <input
														type='checkbox' id="Handhole_AutocompleteDuct" value='0' class="auxPtDuctAutocomplete"
														style='position: relative; margin-left: 10px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input
														type='checkbox' id="DB_AutocompleteDuct" value='0' class="auxPtDuctAutocomplete"
														style='position: relative; margin-left:25px'></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px" class="input-group-text"><b>By Auxiliary Point </b>
														 <input type='checkbox' id="AuxPt_AutocompleteDuct" value='0' class="auxPtDuctAutocomplete" style='position: relative; margin-left: 15px'></span>
												</div></div></div></div>
								<div class="col-sm-1">
								<div class="form-group">
									<div class="input-group-prepend"><div id="loaderDivDuct" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
									</div></div></div>	
							<div class="row">	
								<div class="col-sm-5">
								<div class="form-group">
									<div class="input-group-prepend">
						<label class="file"><input type="file" style="font-size:13px" id="importAuxfileDuct" accept=".xlsx" class="btn btn-light file"></label>
								</div></div></div>		
								<div class="col-sm-4">
								<div class="form-group">
									<div class="input-group-prepend">
										<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b>
												 <input type='checkbox' id="AuxPt_AppendTo_Duct" value='0' style='position: relative; margin-left: 15px'></span>
												</div></div></div>	
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend"><button id="uploadDuct" class="btn btn-primary" style=" margin-top:10px;">Import</button></div>
								</div></div>
						<div class="col-sm-1">
								<div class="form-group">
									<div class="input-group-prepend"><div id="loaderDivDuct" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
											</div></div></div>																
									<form>
										<div class="table-responsive-sm" id="auxiliary_ductDiv">
											<table id="auxiliary_ductTable" class="table table-striped table-bordered table-sm" style="display: block; height: 250px; width: fit-content; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr>
														<th>
															<button type="button" id="selectAllDuct_Aux" class="main">
																<span class="sub"></span>Select</button>
														</th>
														<th style="min-width: 80px">Sequence</th>
														<th width="250px">Auxiliary Name</th>
														<th width="150px">Longitude</th>
														<th width="150px">Latitude</th>
														<th width="150px">Length</th>
														<th width="200px">Driving<br> Distance (km)</th>
														<th>Geo <br>Distance(km)</th>
										</tr></thead><tbody></tbody></table></div>
										<button type="button" id="addDuctAuxBelow">Insert Row Below</button>
										<button type="button" id="addDuctAuxAbove">Insert Row Above</button>
										<button type="button" id="delete_Duct_Aux">Delete Row</button>
										<button type="button"><b>Draw By </b> <select id ="ductDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> </button>
										<button type="button" id="sortByDistanceDuct">Sort by distance</button>
										<button type="button" id="setCoordinateDuctAux" >Set Coordinate</button>
										<button type="button" id="calculateDrivingDistanceDuct"> Calculate Driving Distance</button>
										<button type="button" id="calculateGeoDistanceDuct"> Calculate Geo Distance</button>
										<div>
										<br><b>Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDestDuct" name="distanceLstAuxToDestDuct" readonly>
										<br><b>Total Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistanceDuct" name="totalDistanceDuct" readonly>
										<br><b>Driving Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="ductDistanceLstAuxToDestDrivg" name="ductDistanceLstAuxToDestDrivg" readonly>										
										<br><b>Total Driving Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="ductTotalDistanceDrivg" name="ductTotalDistanceDrivg" readonly>
										<br><b>Total Geo Distance: </b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" id="totalGeoDistanceDuct" readonly></input><br><br>					
										</div>	
									</form></div></div></div></div></div></div>
				<div class="modal-footer"></div></div></div></div>
	<div class="container">
		<div id="tubeModalAuxiliary" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
				<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="tubeIdHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<button id=saveTubeAux class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px; margin-left: 60%;">Save</button>
						<button type="button" name="closePopup" class="close" data-dismiss="modal"><i class='fa fa-times'></i></button>
						<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="TubeAux-tab" style="color: gold;" data-toggle="tab" href="#TubeAux" role="tab" aria-controls="TubeAux" aria-selected="true">Tube Auxiliary Points</a></li>
						</ul>
						<div class="tab-pane " id="TubeAux" role="tabpanel" aria-labelledby="TubeAux-tab">
							<p></p>
							<div class="container-fluid">
							<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="Site_FiberTubeAuxAutoComplete" style='position: relative; margin-left: 25px' class="FiberTubeAuxAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="Manhole_FiberTubeAuxAutoComplete" style='position: relative; margin-left:10px' class="FiberTubeAuxAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:8px" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="Handhole_FiberTubeAuxAutoComplete" style='position: relative; margin-left:8px' class="FiberTubeAuxAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:30px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="DB_FiberTubeAuxAutoComplete" style='position: relative; margin-left:25px' class="FiberTubeAuxAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;margin-left:25px" class="input-group-text"><b>By Auxiliary Point </b> 
													<input type='checkbox' id="AuxPt_FiberTubeAuxAutoComplete" style='position: relative; margin-left:15px' class="FiberTubeAuxAutoComplete"></span>
												</div></div></div></div>
						<div class="col-sm-1">
								<div class="form-group">
									<div class="input-group-prepend"><div id="loaderDivFiberTube" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
									</div></div></div>	
								<div class="row">	
								<div class="col-sm-5">
								<div class="form-group">
									<div class="input-group-prepend">
						<label class="file"><input type="file" style="font-size:13px" id="importAuxfileFiberTube" accept=".xlsx" class="btn btn-light file"></label>
								</div></div></div>	
						<div class="col-sm-4">
								<div class="form-group">
									<div class="input-group-prepend">
										<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b>
												 <input type='checkbox' id="AuxPt_AppendTo_FiberTube" value='0' style='position: relative; margin-left: 15px'></span>
							</div></div></div>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend"><button id="uploadFiberTube" class="btn btn-primary" style=" margin-top:10px;">Import</button></div>
								</div></div>
								<div class="col-sm-1">
								<div class="form-group">
									<div class="input-group-prepend"><div id="loaderDivFiberTube" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
											</div></div></div>	
								<form>
									<div class="table-responsive-sm" id="auxiliaryTubeDiv">
										<table id="TubeAuxTable" class="table table-striped table-bordered table-sm " style="display: block; height: 200px; overflow-y: auto;">
											<thead style="background: #E9ECEF;">
												<tr>
													<th> <button type="button" id="selectAll_TubesAux" class="main"> <span class="sub"></span>Select</button> </th>
													<th style="min-width: 80px">Sequence</th>
													<th width="250px">Auxiliary Name</th>
													<th width="150px">Longitude</th>
													<th width="150px">Latitude</th>
													<th width="150px">Length</th>
													<th width="200px">Driving<br> Distance (km)</th>
													<th>Geo <br>Distance(km)</th>
												</tr></thead><tbody></tbody></table></div>
								<button type="button" id="addTubeRowBelow">Insert Row Below</button>
									<button type="button" id="addTubeRowAbove">Insert Row Above</button>
									<button type="button" id="delete_TubeAux">Delete Row</button> 									
									<button type="button" id="sortByDistanceFiberTube">Sort by distance</button>
									<button type="button"><b>Draw By </b><select id ="fiberTubeDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> 
                                    </button>
                                    <button type="button" id="calculateDrivingDistanceFiberTube"> Calculate Driving Distance</button>
									<button type="button" id="calculateGeoDistanceFiberTube"> Calculate Geo Distance</button>
								<div><br><b>Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDestFiberTube" name="distanceLstAuxToDestTube" readonly>
								<br><b>Total Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistanceFiberTube" name="totalDistanceFiberTube" readonly>									
								<br><b>Driving Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="fiberTubeDistanceLstAuxToDestDrivg" name="fiberTubeDistanceLstAuxToDestDrivg" readonly>
								<br><b>Total Driving Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="fiberTubeTotalDistanceDrivg" name="fiberTubeTotalDistanceDrivg" readonly>
								<br><b>Total Geo Distance: </b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" id="totalGeoDistanceFiberTube" readonly></input><br><br>					
						</div></form></div></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
<div class="container">
		<div id="strandModalAuxiliary" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="strandIdHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>

						<button id="saveStrandAux" class="btn btn-save" style="color: black; font-weight:bold;margin-top:-6px; margin-left: 60%;">Save</button>
						<button type="button" name="closePopup" class="close" data-dismiss="modal">
							<i class='fa fa-times'></i></button>
						<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i> </a>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTab" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="StrandAux-tab" style="color: gold;" data-toggle="tab" href="#StrandAux" role="tab" aria-controls="#StrandAux" aria-selected="true">Strand Auxiliary Points</a></li>
						</ul>
						<div class="tab-pane " id="StrandAux" role="tabpanel" aria-labelledby="StrandAux-tab">
							<p></p>
							<div class="container-fluid">
							<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="Site_FiberStrandAuxAutoComplete" style='position: relative; margin-left: 25px' class="FiberStrandAuxAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="Manhole_FiberStrandAuxAutoComplete" style='position: relative; margin-left:10px' class="FiberStrandAuxAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:8px" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="Handhole_FiberStrandAuxAutoComplete" style='position: relative; margin-left:8px' class="FiberStrandAuxAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:30px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="DB_FiberStrandAuxAutoComplete" style='position: relative; margin-left:25px' class="FiberStrandAuxAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;margin-left:25px" class="input-group-text"><b>By Auxiliary Point </b> 
													<input type='checkbox' id="AuxPt_FiberStrandAuxAutoComplete" style='position: relative; margin-left:15px' class="FiberStrandAuxAutoComplete"></span>
												</div></div></div></div>
								<div class="col-sm-1"><div class="form-group">
									<div class="input-group-prepend"><div id="loaderDivFiberStrand" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
									</div></div></div>	
								<div class="row"><div class="col-sm-5"><div class="form-group">
								<div class="input-group-prepend">
						<label class="file"><input type="file" style="font-size:13px" id="importAuxfileFiberStrand" accept=".xlsx" class="btn btn-light file"></label></div></div></div>									
						<div class="col-sm-4"><div class="form-group"><div class="input-group-prepend">
							<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b>
							<input type='checkbox' id="AuxPt_AppendTo_FiberStrand" value='0' style='position: relative; margin-left: 15px'></span></div></div></div>		
						<div class="col-sm-2"><div class="form-group">
							<div class="input-group-prepend"><button id="uploadFiberStrand" class="btn btn-primary" style=" margin-top:10px;">Import</button></div></div></div>		
							<div class="col-sm-1"><div class="form-group">
							<div class="input-group-prepend"><div id="loaderDivFiberStrand" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div></div></div></div>	
								<form>
									<div class="table-responsive-sm" id="auxiliaryStrandDiv">
										<table id="StrandAuxTable" class="table table-striped table-bordered table-sm " style="display: block; height: 200px; overflow-y: auto;">
											<thead style="background: #E9ECEF;">
												<tr><th><button type="button" id="selectAll_StrandsAux" class="main"><span class="sub"></span>Select</button></th>
													<th style="min-width: 80px">Sequence</th>
													<th width="250px">Auxiliary Name</th>
													<th width="150px">Longitude</th>
													<th width="150px">Latitude</th>
													<th width="150px">Length</th>
													<th width="200px">Driving<br> Distance (km)</th>
													<th>Geo <br>Distance(km)</th>
												</tr></thead><tbody></tbody></table></div>
									<button type="button" id="addStrandRowBelow">Insert Row Below</button>
									<button type="button" id="addStrandRowAbove">Insert Row Above</button>
									<button type="button" id="delete_StrandAux">Delete Row</button>
									<button type="button" id="sortByDistanceFiberStrand">Sort by distance</button>
									<button type="button"><b>Draw By </b><select id ="fiberStrandDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> </button>
                                    <button type="button" id="calculateDrivingDistanceFiberStrand"> Calculate Driving Distance</button>
									<button type="button" id="calculateGeoDistanceFiberStrand"> Calculate Geo Distance</button>
                                    <div><br><b>Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="distanceLstAuxToDestFiberStrand" name="distanceLstAuxToDestFiberStrand" readonly>
									<br><b>Total Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="totalDistanceFiberStrand" name="totalDistanceFiberStrand" readonly>									
									<br><b>Driving Distance from last auxiliary to destination:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="fiberStrandDistanceLstAuxToDestDrivg" name="fiberStrandDistanceLstAuxToDestDrivg" readonly>
									<br><b>Total Driving Distance:</b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" type="text" id="fiberStrandTotalDistanceDrivg" name="fiberStrandTotalDistanceDrivg" readonly>
									<br><b>Total Geo Distance: </b><input style="border: none;outline:none;font-size:16px;color:#DC143C;" id="totalGeoDistanceFiberStrand" readonly></input><br><br>					
						</div></form></div></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
<div class="container"><div id="strandOriginationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered"><div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="strandOriginationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Strand Origination:</h5>
						<div style="float: right;">
							<button id="saveStrandOriginSource" class="btn btn-save" style="color: black; font-weight:bold;margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal"> <i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i> </a>
						</div></div>
					<div class="modal-body"><div class="tab-content"><div class="container-fluid"><div class="row"><p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_StrandOriginationAutoComplete" style='position: relative; margin-left: 30px'  class="strandOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_StrandOriginationAutoComplete" style='position: relative; margin-left: 25px' class="strandOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_StrandOriginationAutoComplete" style='position: relative; margin-left: 10px' class="strandOriginationAutoComplete" ></span>
										</div></div></div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_StrandOriginationAutoComplete" style='position: relative; margin-left:15px' class="strandOriginationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_StrandOriginationAutoComplete" style='position: relative; margin-left:25px' class="strandOriginationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="strandOriginationSourceDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source</b></span> 
											<input type="text" id="strandOriginationSource" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> 
											<input type="text" id="strandOriginationSourceType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span> 
											<input type="text" id="strandOriginationSrcCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lng </b></span> 
											<input type="text" id="strandOriginationSourceLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lat </b></span> 
											<input type="text" id="strandOriginationSourceLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>

<div class="container">
	<div id="trenchOriginationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="trenchOriginationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Trench Origination:</h5>
						<div style="float: right;">
							<button id="saveTrenchOriginSource" class="btn btn-save" style="color: black; font-weight:bold;margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal"> <i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i> </a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="container-fluid">
							<div class="row"><p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_TrenchOriginationAutoComplete" style='position: relative; margin-left: 30px'  class="trenchOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_TrenchOriginationAutoComplete" style='position: relative; margin-left: 25px' class="trenchOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_TrenchOriginationAutoComplete" style='position: relative; margin-left: 10px' class="trenchOriginationAutoComplete" ></span>
										</div>
									</div>
								</div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TrenchOriginationAutoComplete" style='position: relative; margin-left:15px' class="trenchOriginationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TrenchOriginationAutoComplete" style='position: relative; margin-left:25px' class="trenchOriginationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="trenchOriginationSourceDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source</b></span> 
											<input type="text" id="trenchOriginationSource" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> 
											<input type="text" id="trenchOriginationSourceType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span> 
											<input type="text" id="trenchOriginationSrcCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lng </b></span> 
											<input type="text" id="trenchOriginationSourceLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lat </b></span> 
											<input type="text" id="trenchOriginationSourceLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
		<div class="modal-footer"></div></div></div></div></div>
<div class="container">
	<div id="ductOriginationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="ductOriginationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Duct Origination:</h5>
						<div style="float: right;">
							<button id="saveDuctOriginSource" class="btn btn-save" style="color: black; font-weight:bold;margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal"> <i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i> </a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="container-fluid">
							<div class="row"><p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_DuctOriginationAutoComplete" style='position: relative; margin-left: 30px'  class="ductOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_DuctOriginationAutoComplete" style='position: relative; margin-left: 25px' class="ductOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_DuctOriginationAutoComplete" style='position: relative; margin-left: 10px' class="ductOriginationAutoComplete" ></span>
										</div>
									</div>
								</div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_DuctOriginationAutoComplete" style='position: relative; margin-left:15px' class="ductOriginationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_DuctOriginationAutoComplete" style='position: relative; margin-left:25px' class="ductOriginationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="ductOriginationSourceDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source</b></span> 
											<input type="text" id="ductOriginationSource" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> 
											<input type="text" id="ductOriginationSourceType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span> 
											<input type="text" id="ductOriginationSrcCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lng </b></span> 
											<input type="text" id="ductOriginationSourceLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lat </b></span> 
											<input type="text" id="ductOriginationSourceLat" class="form-control text-input" readonly />
										</div></div></div></div><div class="row"></div></div></div>
		<div class="modal-footer"></div></div></div></div></div>
<div class="container">
	<div id="tubeOriginationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="tubeOriginationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Tube Origination:</h5>
						<div style="float: right;">
							<button id="saveTubeOriginSource" class="btn btn-save" style="color: black; font-weight:bold;margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal"> <i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i> </a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="container-fluid">
							<div class="row">
							<p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_TubeOriginationAutoComplete" style='position: relative; margin-left: 30px'  class="tubeOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_TubeOriginationAutoComplete" style='position: relative; margin-left: 25px' class="tubeOriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_TubeOriginationAutoComplete" style='position: relative; margin-left: 10px' class="tubeOriginationAutoComplete" ></span>
										</div></div></div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TubeOriginationAutoComplete" style='position: relative; margin-left:15px' class="tubeOriginationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TubeOriginationAutoComplete" style='position: relative; margin-left:25px' class="tubeOriginationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="tubeOriginationSourceDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source</b></span> 
											<input type="text" id="tubeOriginationSource" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> 
											<input type="text" id="tubeOriginationSourceType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span> 
											<input type="text" id="tubeOriginationSrcCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lng </b></span> 
											<input type="text" id="tubeOriginationSourceLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lat </b></span> 
											<input type="text" id="tubeOriginationSourceLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
	<div class="container">
	<div id="OriginationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header"
						style="background-color: #2678CC ; height: 55px">
						<h5 id="originationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Fiber Origination:</h5>
						<div style="float: right;">
							<button id="saveOriginSource" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>

							<button type="button" name="closePopup" class="close" data-dismiss="modal"> <i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i> </a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content">
							<div class="container-fluid">
							<div class="row"><p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_OriginationAutoComplete" style='position: relative; margin-left: 30px'  class="OriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_OriginationAutoComplete" style='position: relative; margin-left: 25px' class="OriginationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_OriginationAutoComplete" style='position: relative; margin-left: 10px' class="OriginationAutoComplete" ></span>
										</div></div></div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_OriginationAutoComplete" style='position: relative; margin-left:15px' class="OriginationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_OriginationAutoComplete" style='position: relative; margin-left:25px' class="OriginationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="OriginationSourceDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source</b></span> 
											<input type="text" id="originationSource" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> 
											<input type="text" id="OriginationSourceType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source City </b></span> 
											<input type="text" id="OriginationSrcCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lng </b></span> 
											<input type="text" id="OriginationSourceLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Lat </b></span> 
											<input type="text" id="OriginationSourceLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
	<div class="container">
		<div id="trenchTerminationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="trenchTerminationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Trench Termination:</h5>
						<div style="float: right;">
							<button id="saveTrenchTermination" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
								<i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content"><p></p>
							<div class="container-fluid">
							<div class="row"><p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_TrenchTerminationAutoComplete" style='position: relative; margin-left: 30px'  class="trenchTerminationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_TrenchTerminationAutoComplete" style='position: relative; margin-left: 25px' class="trenchTerminationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_TrenchTerminationAutoComplete" style='position: relative; margin-left: 10px' class="trenchTerminationAutoComplete" ></span>
										</div></div></div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TrenchTerminationAutoComplete" style='position: relative; margin-left:15px' class="trenchTerminationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TrenchTerminationAutoComplete" style='position: relative; margin-left:25px' class="trenchTerminationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="TerminationDestinationDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination</b></span> 
											<input type="text" id="trenchTerminationDestination" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type</b></span>
											 <input type="text" id="trenchTerminationDestType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City </b></span>
											 <input type="text" id="trenchTerminationDestCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lng </b></span> 
											<input type="text" id="trenchTerminationDestLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lat </b></span> 
											<input type="text" id="trenchTerminationDestLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
<div class="container">
		<div id="ductTerminationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="ductTerminationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Duct Termination:</h5>
						<div style="float: right;">
							<button id="saveDuctTermination" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
								<i class='fa fa-times'></i></button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content"><p></p>
							<div class="container-fluid">
							<div class="row"><p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_DuctTerminationAutoComplete" style='position: relative; margin-left: 30px'  class="ductTerminationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_DuctTerminationAutoComplete" style='position: relative; margin-left: 25px' class="ductTerminationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_DuctTerminationAutoComplete" style='position: relative; margin-left: 10px' class="ductTerminationAutoComplete" ></span>
										</div></div></div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_DuctTerminationAutoComplete" style='position: relative; margin-left:15px' class="ductTerminationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_DuctTerminationAutoComplete" style='position: relative; margin-left:25px' class="ductTerminationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="DuctDestinationDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination</b></span> 
											<input type="text" id="ductTerminationDestination" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type</b></span>
											 <input type="text" id="ductTerminationDestType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City </b></span>
											 <input type="text" id="ductTerminationDestCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lng </b></span> 
											<input type="text" id="ductTerminationDestLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lat </b></span> 
											<input type="text" id="ductTerminationDestLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
	<div class="container">
		<div id="TerminationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="terminationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Fiber Termination:</h5>
						<div style="float: right;">
							<button id="saveTerminationDest" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">0Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content"><p></p><div class="container-fluid"><div class="row"><p></p>
							<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_TerminationAutoComplete" style='position: relative; margin-left: 30px'  class="TerminationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_TerminationAutoComplete" style='position: relative; margin-left: 25px' class="TerminationAutoComplete"></span>
										</div></div></div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_TerminationAutoComplete" style='position: relative; margin-left: 10px' class="TerminationAutoComplete" ></span>
										</div></div></div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TerminationAutoComplete" style='position: relative; margin-left:15px' class="TerminationAutoComplete" ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TerminationAutoComplete" style='position: relative; margin-left:25px' class="TerminationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="TerminationDestinationDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination</b></span> 
											<input type="text" id="terminationDestination" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type</b></span>
											 <input type="text" id="TerminationDestType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City </b></span>
											 <input type="text" id="TerminationDestCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lng </b></span> 
											<input type="text" id="TerminationDestLng" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lat </b></span> 
											<input type="text" id="TerminationDestLat" class="form-control text-input" readonly />
									</div></div></div></div>
					<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
		<div class="container">
		<div id="strandTerminationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="strandTerminationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Strand Termination:</h5>
						<div style="float: right;">
							<button id="saveStrandTermination" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="container-fluid">
							<div class="row">
							<p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_StrandTerminationAutoComplete" style='position: relative; margin-left: 30px'  class="strandTerminationAutoComplete"></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_StrandTerminationAutoComplete" style='position: relative; margin-left: 25px' class="strandTerminationAutoComplete"></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_StrandTerminationAutoComplete" style='position: relative; margin-left: 10px' class="strandTerminationAutoComplete" ></span>
										</div>
									</div>
								</div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_StrandTerminationAutoComplete" style='position: relative; margin-left:15px' class="strandTerminationAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_StrandTerminationAutoComplete" style='position: relative; margin-left:25px' class="strandTerminationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="TerminationDestinationDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination</b></span> 
											<input type="text" id="strandTerminationDestination" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type</b></span>
											 <input type="text" id="strandTerminationDestType" class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City </b></span>
											 <input type="text" id="strandTerminationDestCity" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lng </b></span> 
											<input type="text" id="strandTerminationDestLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lat </b></span> 
											<input type="text" id="strandTerminationDestLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
	<div class="container">
		<div id="tubeTerminationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 830px">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 id="tubeTerminationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Tube Termination:</h5>
						<div style="float: right;">
							<button id="saveTubeTermination" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="container-fluid">
							<div class="row">
							<p></p>
							<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
											<input type='checkbox' id="site_TubeTerminationAutoComplete" style='position: relative; margin-left: 30px'  class="tubeTerminationAutoComplete"></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
											<input type='checkbox' id="client_TubeTerminationAutoComplete" style='position: relative; margin-left: 25px' class="tubeTerminationAutoComplete"></span>
										</div>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
											<input type='checkbox' id="manhole_TubeTerminationAutoComplete" style='position: relative; margin-left: 10px' class="tubeTerminationAutoComplete" ></span>
										</div>
									</div>
								</div>
									<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TubeTerminationAutoComplete" style='position: relative; margin-left:15px' class="tubeTerminationAutoComplete" ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TubeTerminationAutoComplete" style='position: relative; margin-left:25px' class="tubeTerminationAutoComplete" ></span>
												</div></div></div></div></div>
								<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend" id="TerminationDestinationDiv">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination</b></span> 
											<input type="text" id="tubeTerminationDestination" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type</b></span>
											 <input type="text" id="tubeTerminationDestType" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination City </b></span>
											 <input type="text" id="tubeTerminationDestCity" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lng </b></span> 
											<input type="text" id="tubeTerminationDestLng" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Lat </b></span> 
											<input type="text" id="tubeTerminationDestLat" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row"></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
	<div class="container">
		<div id="handholeModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="handholeHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Handhole: </h5>
						<div style="float: right;">
							<button id="saveHandhole" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<p></p>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>ID </b> </span> 
											<input type="text" id="HandholeId" readonly class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
											<input type="text"  id="HandholeName" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
											<input type="text" id="HandholeLong" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="HandholeLat" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>City</b></span>
											<input type="text" id="HandholeCity" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Model</b></span>
											<input type="text" id="HandholeModel" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="handholeCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 123px; font-size: 12px;"><b>Last Modified Date</b></span>
									<input type="text" id="handholeLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
							<div class="row">
								<div class="col-md-6" id="HandholeDMDiv">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DM_Name</b></span>
											<input type="text" id="HandholeDMName" class="form-control text-input" readonly />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6" id="projectIdHandhole">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Project ID</b></span> 
											<input type="text" id="HandholeProjectId" class="form-control text-input" />
										</div></div></div>
								<div class="col-md-6" id="projectNameHandhole">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Project Name</b></span>
											 <input type="text" id="HandholeProjectName" class="form-control text-input" />
										</div></div></div></div></div></div>
					<div class="modal-footer"></div></div></div></div></div>
	<div class="container">
	<div id="distributionBoardModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" style="width: 970px;">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px">
						<h5 class="modal-title" id="DistributionBoardheader" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float: right;">
							<button id="saveDistBoard" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a></div></div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTabDb" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="distBoard-tab" style="color: gold;" data-toggle="tab" href="#D_Board" role="tab" aria-controls="D_Board" aria-selected="true">Board </a></li>
							<li class="nav-item"><a class="nav-link " id="mapping-tab" style="color: gold;" data-toggle="tab" href="#mapping" role="tab" aria-controls="mapping" aria-selected="false">Mapping</a></li>
						</ul>
						<div class="tab-content"><p></p>
							<div class="tab-pane active" id="D_Board" role="tabpanel" aria-labelledby="distBoard-tab">
								<p></p>
								<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_DBAutoComplete" style='position: relative; margin-left: 25px'  ></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="client_DBAutoComplete" style='position: relative; margin-left:15px' ></span>
												</div></div></div></div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text" style="color:green;"><b> ID </b> </span> 
												<input type="text" id="DistributionBoardId" readonly class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6 nextprvItems">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
												<input type="text" id="DistributionBoardName" class="form-control text-input" />
											</div></div></div></div>
								<div class="row">
									<div class="col-md-6" id="DBSite">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Site ID
												</b></span> <input type="text" id="DistributionBoardSite" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6" id="DBSiteName">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Site Name </b></span> 
												<input type="text" id="DistributionBoardSiteName" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6" id="DBClientId" style="display:none">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client ID
												</b></span>
												 <input type="text" id="DistributionBoardClient" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6" id="DBClientName" style="display:none">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client Name </b></span> <input type="text" id="DistributionBoardClientName" class="form-control text-input" />
											</div></div></div></div>
								<div class="row">
									<div class="col-md-6" id="BDWarehouse">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Warehouse </b></span> <input type="text" id="DistributionBoardWarehouse" class="form-control text-input" />
											</div></div></div>
                                     <div class="col-md-6" id="BDClientPhoneNb" style="display:none">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client Phone# </b></span> <input type="text" id="DistributionBoardClientPhoneNb" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Capacity
												</b></span> <input type="text" id="DistributionBoardCapacity" class="form-control text-input" />
											</div></div></div></div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
												<input type="text" id="DistributionBoardLong" class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
												<input type="text" id="DistributionBoardLat" class="form-control text-input" />
											</div></div></div></div>			
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Rows Number </b></span> <input type="text" id="DistributionBoardRowsNum" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Columns
														Number </b></span> <input type="text" id="DistributionBoardColsNum"
													class="form-control text-input" />
											</div></div></div></div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Connected Front Ports </b></span> 
														<input type="text" id="DistributionBoardFront" class="form-control text-input" readonly/>
											</div></div></div>
                   					<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Connected Back Ports </b></span> 
													<input type="text" id="DistributionBoardBack" class="form-control text-input" readonly/>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Creation Date </b></span>
												 <input type="text" id="boardCreationDate" class="form-control text-input" value="" readonly />
											</div></div></div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>City </b></span>
												 <input type="text" id="boardCity" class="form-control text-input" readonly />
											</div></div></div></div>
											
								<div class="row">
									<div class="col-md-6" id="lastModifiedDateDB">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Last Modified Date</b></span>
													<input type="text" id="boardLastModifiedDate" class="form-control text-input" value="" readonly />
										</div></div></div>
										<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="font-size: 12px;" class="input-group-text"><b>DB Network Level </b></span> 
														<select id="DBnetlevel" class="form-control">
															<option value="backbone" selected >Backbone</option>
															<option value="metro">Metro</option>
															<option value="access">Access</option>
														</select>
											</div></div></div>
									<div class="col-md-6" id="projectIdDB">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Project ID</b></span>
													<input type="text" id="DBProjectId" class="form-control text-input" />
										</div></div></div></div>
								<div class="row">
        							<div class="col-md-6" id="projectNameDB">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Project
														Name</b></span> <input type="text" id="DBProjectName"
													class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="mapping" role="tabpanel"
								aria-labelledby="mapping-tab">
								<p></p>
								<div class="container-fluid">
									<div class="form-group">
										<div class="input-group-prepend"><div id="loaderDivDB" style="display: none; margin-top:11px;"><img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" width="40px" /></div>
										</div>	
									</div>
									<form>
										<div class="table-responsive-sm" id="DbMappingDiv">
											<table id="DbMappingTable"
												class="table table-striped table-bordered table-sm "
												style="display: block; height: 250px; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr>
														<th></th>
														<th colspan="3"></th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="22">Front Port</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="22">Back Port</th>
													</tr>
													<tr>
														<th>
															<button type="button" id="selectAll_DbMapRows"
																class="main">
																<span class="sub"></span>Select
															</button>
														</th>
													<th style="min-width: 80px">Index</th>
														<th style="min-width: 80px">Row #</th>
														<th style="min-width: 80px">Column #</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 190px">Front Status</th>
														<th style="min-width: 190px">Location Type</th>
														<th style="min-width: 190px">Location ID</th>
														<th style="min-width: 190px">Location Name</th>
														<th style="min-width: 190px">Warehouse ID</th>
														<th style="min-width: 190px">Equipment </th>
														<th style="min-width: 190px">Equipment Type</th>
														<th style="min-width: 190px">Equipment ID</th>
														<th style="min-width: 190px">Equipment Name</th>
                                                        <th style="min-width: 190px">Address</th>
                                                        <th style="min-width: 190px">Junction ID</th>
														<th style="min-width: 190px">Junction Name</th>
                                                        <th style="min-width: 80px">Strand #</th>
                                                        <th style="min-width: 110px">Strand Color</th>
                                                        <th style="min-width: 190px">Strand ID</th>
														<th style="min-width: 190px">Strand Name</th>
														<th style="min-width: 80px">Tube #</th>
														<th style="min-width: 110px">Tube Color</th>
														<th style="min-width: 190px">Tube ID</th>
														<th style="min-width: 190px">Tube Name</th>
														<th style="min-width: 190px">Fiber ID</th>
														<th style="min-width: 190px">Fiber Name</th>	
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 190px">Back Status</th>
														<th style="min-width: 190px">Location Type</th>
														<th style="min-width: 190px">Location ID</th>
														<th style="min-width: 190px">Location Name</th>
														<th style="min-width: 190px">Warehouse ID </th>
														<th style="min-width: 190px">Equipment </th>
														<th style="min-width: 190px">Equipment Type</th>
														<th style="min-width: 190px">Equipment ID</th>
														<th style="min-width: 190px">Equipment Name</th>
                                                        <th style="min-width: 190px">Address</th>
                                                        <th style="min-width: 190px">Junction ID</th>
														<th style="min-width: 190px">Junction Name</th>
                                                        <th style="min-width: 80px">Strand #</th>	
                                                        <th style="min-width: 110px">Strand Color</th>
														<th style="min-width: 190px">Strand ID</th>
														<th style="min-width: 190px">Strand Name</th>
														<th style="min-width: 80px">Tube #</th>
														<th style="min-width: 110px">Tube Color</th>
														<th style="min-width: 190px">Tube ID</th>
														<th style="min-width: 190px">Tube Name</th>
														<th style="min-width: 190px">Fiber ID</th>
														<th style="min-width: 190px">Fiber Name</th>
													</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
										<button type="button" id="add_RackRow">Add Row</button>
										<button type="button" id="delete_RackRow">Delete Row</button>
										<button type="button" id="sortByIndex">Sort By Index</button>
										<button type="button" id="assign_Cable">Assign Cable</button>
										<input name="fiber_Cable" id="BP_assignCable" class="form-control text-input" type="text" style="width:150px;display: inline-block;"/>
										<button type="button" id="assign_Tube">Assign Tube</button>
										<input name="fiber_Tube" id="BP_assignTube" class="form-control text-input" type="text" style="width:170px;display: inline-block;"/>
										<select id="selected_Port" aria-label="Default select example" class="form-select" style="height:35px;">
										  <option value="title"  selected>Front/back</option>
										  <option value="frontPort">Front Port</option>
										  <option value="backPort">Back Port</option>
										</select>
									</form></div></div></div></div></div></div></div></div>
									
							<div class="container">
								<div id="distributionBoardLoaderModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
									<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
										<div class="modal-content">
											<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
												<h5 id="DBLoaderHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
												<div style="float: right;">
													<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
														<i class='fa fa-times'></i>
													</button>
													<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
												</div></div>
											<div class="modal-body">
												<div class="tab-content" >
											
											<div class="row">
											<div class="col-sm-5">
												<div class="form-group">
													<div class="input-group-prepend">
														<label class="file"><input type="file" style="font-size:13px" id="importDBFile" accept=".xlsx" class="btn btn-light file"></label>
													</div>
												</div>	
											</div>
											<div class="col-sm-5">
												<div class="form-group">
													<div class="input-group-prepend"><button id="uploadDB" class="btn btn-primary" style=" margin-top:10px;">Import</button></div>
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
								
				<div class="container">
					<div id="LoaderConfirmationModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
						<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
							<div class="modal-content">
								<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
									<h5 id="LoaderConfirmationHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
									<div style="float: right;">
										<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
											<i class='fa fa-times'></i>
										</button>
										<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
									</div></div>
								<div class="modal-body">
									<div class="tab-content" >
										<p id="LoaderConfirmationbody" style="font-weight: bold;"></p>
										<p id="IgnoredDBBody" style="font-weight: bold;"></p>
										<div style="text-align:center;">
								<button id="closeconfirmation" class="btn btn-delete" style="color: white; font-weight:bold;  margin-right: 5px ; background-color:#6AA84F">Ok</button>
										</div>								
										</div>
									</div>
								</div>
								<div class="modal-footer"></div>
							</div>
						</div>
					</div>
	<div class="container" style="width: auto">
		<div id="dB_MappingModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" id="contentMappingModal">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 class="modal-title" id="dB_TitleId" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float:right">
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>	
							<a class="close modalMinimize ml-3 btn-text-right"> <i class='fa fa-minus icon-to-change'></i></a>
				 	</div>	
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="dB_tabContentPortsMap" style="min-width: 700px; min-height: 600px"></div>
						</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="BackPortAssignedPortsModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" style="width: 750px;">
				<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
					<h5 class="modal-title" id="backPortHeader" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>				
					<div style="float:right;">					
						<button type="button" name="closePopup" class="close" data-dismiss="modal">
							<i class='fa fa-times'></i> </button>
						<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
					</div>
				</div>
				<div class="modal-body" style="height: auto; width: auto;">
					<div class="tab-content">
						<div id="dB_tabAssignedPorts" style="float: center; border: solid black 1px; padding: 10px; border-radius: 25px; background: #E9ECEF; color: #ffd700; border: 2px solid #ffd700; width: auto; height: 400px;">
							<div>
								<p style="font-size: 20px; color: #00757C">
									<b>Back Port</b>
								</p>
								<div style="column-count: 2;">
									<label id="backStatus" style="color: #DC143C;"></label><br />
									<label id="backPortLocationType" style="color: #DC143C;"></label><br/> 
									<label id="backPortLocationID" style="color: #DC143C;"></label><br/>
									<label id="backPortLocationName" style="color: #DC143C;"></label><br/>
									<label id="backPortEquipment" style="color: #DC143C;"></label><br/>
									<label id="backPortEquipmentType" style="color: #DC143C;"></label><br/>
									<label id="backPortEquipmentID" style="color: #DC143C;"></label><br/> 
									<label id="backPortEquipmentName" style="color: #DC143C;"></label><br/> 
									<label id="backPortStrandNb" style="color: #DC143C;"></label><br/>
									<label id="backPortStrandID" style="color: #DC143C;"></label><br/> 
									<label id="backPortStrandName" style="color: #DC143C;"></label><br/>
									<label id="backPortTubeNb" style="color: #DC143C;"></label><br/>
									<label id="backPortTubeId" style="color: #DC143C;"></label><br/>
									<label id="backPortTubeName" style="color: #DC143C;"></label><br/>
									<label id="backPortFiberId" style="color: #DC143C;"></label><br/>
									<label id="backPortFiberName" style="color: #DC143C;"></label><br/> 
								</div> </div></div></div></div>
				<div class="modal-footer" style="background-color: #E9ECEF;"></div></div></div></div>
	<div id="frontPortAssignedPortsModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" style="width: 750px;">
				<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
					<h5 class="modal-title" id="frontPortHeader" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>	
					<div  style="float:right;">
					<button type="button" name="closePopup" class="close" data-dismiss="modal">
						<i class='fa fa-times'></i> </button>
					<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>		
					</div></div>
				<div class="modal-body" style="height: auto; width: auto;">
					<div class="tab-content">
						<div id="frontPort_tabAssignedPorts" style="float: center; border: solid black 1px; padding: 10px; border-radius: 25px; background: #E9ECEF; color: #ffd700; border: 2px solid #ffd700; width: auto; height: 400px;">
							<div>
								<p style="font-size: 20px; color: #00757C">
									<b>Front Port</b>
								</p>
								<div style="column-count: 2;">
									<label id="frontStatus" style="color: #DC143C;"></label><br />
									<label id="frontPortLocationType" style="color: #DC143C;"></label><br/> 
									<label id="frontPortLocationID" style="color: #DC143C;"></label><br/>
									<label id="frontPortLocationName" style="color: #DC143C;"></label><br/>
									<label id="frontPortEquipment" style="color: #DC143C;"></label><br/>
									<label id="frontPortEquipmentType" style="color: #DC143C;"></label><br/>
									<label id="frontPortEquipmentID" style="color: #DC143C;"></label><br/>
									<label id="frontPortEquipmentName" style="color: #DC143C;"></label><br/>
									<label id="frontPortStrandNb" style="color: #DC143C;"></label><br/>
									<label id="frontPortStrandID" style="color: #DC143C;"></label><br/> 
									<label id="frontPortStrandName" style="color: #DC143C;"></label><br/>
									<label id="frontPortTubeNb" style="color: #DC143C;"></label><br/>
									<label id="frontPortTubeId" style="color: #DC143C;"></label><br/>
									<label id="frontPortTubeName" style="color: #DC143C;"></label><br/>
									<label id="frontPortFiberId" style="color: #DC143C;"></label><br/>
									<label id="frontPortFiberName" style="color: #DC143C;"></label><br/> 
							</div></div></div></div></div>
				<div class="modal-footer" style="background-color: #E9ECEF;"></div></div></div></div>
	<div class="container" style="width: auto">
		<div id="junction_MappingModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" id="contentJctMappingModal">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 class="modal-title" id="junction_TitleId" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float:right;">
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a></div></div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="junction_tabContentPortsMap" style="min-width: 700px; min-height: 600px"></div>
						</div></div>
					<div class="modal-footer"></div></div></div></div></div>
	<div id="AssignedJctPortsModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered " >
			<div class="modal-content" style="width: 600px" >
				<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
					<h5 class="modal-title" id="AssignedJct_TitleId" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
					<div style="float:right">
						<button type="button" name="closePopup" class="close" data-dismiss="modal">
							<i class='fa fa-times'></i>
						</button>
						<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
						</a>
					</div></div>
				<div class="modal-body" style="height: auto; width: auto;">
					<div class="tab-content">
						<div id="jct_tabAssignedPorts" style="float: center; border: solid black 1px; padding: 10px; border-radius: 25px; background: #E9ECEF; color: #ffd700; border: 2px solid #ffd700; width: fit-content; height: 400px;">
							<div>
								<p style="font-size: 20px; color: #00757C">
									<b> Junction Details:</b>
								</p>
								<label id="JctKey" style="color: #DC143C;">Select a
									junction to view details !</label><br /><label id="JctStrandSideA"
									style="color: #DC143C;"></label><br /><label id="JctTubeSideA"
									style="color: #DC143C;"></label><br /><label id="JctFiberSideA"
									style="color: #DC143C;"></label><br /> <label id="JctKeySideB"
									style="color: #DC143C;"></label><br /><label id="JctStrandSideB"
									style="color: #DC143C;"></label><br /><label id="JctTubeSideB"
									style="color: #DC143C;"></label><br /><label id="JctFiberSideB"
									style="color: #DC143C;"></label><br /> 
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer" style="background-color: #E9ECEF;">
				</div>
			</div>
		</div>
	</div>
	<div class="container" style="width: auto">
		<div id="handJunctionMappingModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content" id="contentHandJctMappingModal">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 class="modal-title" id="handJunctionTitleId" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float:right">
							<button type="button" name="closePopup" class="close" data-dismiss="modal">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="handJunctionTabContentPortsMap" style="min-width: 700px; min-height: 600px"></div>
						</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="AssignedHandJctPortsModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
		<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
			<div class="modal-content" style="width: 600px">
				<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
					<h5 class="modal-title" id="AssignedHandJct_TitleId" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 5px;"></h5>
					<button type="button" name="closePopup" class="close" data-dismiss="modal">
						<i class='fa fa-times'></i>
					</button>
					<a class="close modalMinimize ml-3"> <i
						class='fa fa-minus icon-to-change'></i>
					</a>
				</div>
				<div class="modal-body" style="height: auto; width: auto;">
					<div class="tab-content">
					<div id="Hjct_tabAssignedPorts" style="float: center; border: solid black 1px; padding: 10px; border-radius: 25px; background: #E9ECEF; color: #ffd700; border: 2px solid #ffd700; width: fit-content; height: 400px;">
							<div>
								<p style="font-size: 20px; color: #00757C">
									<b> Junction Details:</b>
								</p>
								<label id="HandJctKey" style="color: #DC143C;">Select a
									junction to view details !</label><br /><label id="handholeJctStrandSideA"
									style="color: #DC143C;"></label><br /><label id="handholeJctTubeSideA"
									style="color: #DC143C;"></label><br /><label id="handholeJctFiberSideA"
									style="color: #DC143C;"></label><br /> <label id="handholeJctKeySideB"
									style="color: #DC143C;"></label><br /><label id="handholeJctStrandSideB"
									style="color: #DC143C;"></label><br /><label id="handholeJctTubeSideB"
									style="color: #DC143C;"></label><br /><label id="handholeJctFiberSideB"
									style="color: #DC143C;"></label><br /> 								
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer" style="background-color: #E9ECEF;">
				</div>
			</div>
		</div>
	</div>
</body>

<script>
var newHandHole= [];
var newManHole= [];
var newFiber= [];
var newDb= [];
var newTrench= [];
index=0;
indexTube=0;
dBBoqIndex=0;
junBoqIndex=0;
indexSite=0;
tubeListFromBoq=[];
indexStrand=0;
countStrandsBoq=0;
actionFiberContext="";
auxLatLng=[];
coordsDiv="";
allAuxDictTube=[];
allAuxDictStrand=[];
StrandRefTube=[];
indexStrandAux=0;
indexTubeAux=0;
countTubesBoq=0;	
slctDel=[];
dict = [];
indextrench=0;
indexduct=0;
slctDelMJct=[];
slctDelHJct=[];
var MJctBoqIndex=0;
var HJctBoqIndex=0;
var indexTubeForAuxs;
var idTubeForAuxs;
var indexStrandForAuxs;
var idStrandForAuxs;
var button;
var data;
var keys =[];
var Emptydiv=null;
var projectID;	
var projectModel=[];
var manholeModel=[];
var handholeModel=[];
var fiberModel=[];
var dboardModel=[];
var fiberTubesModel=[];
var fiberStrandsModel=[];
var fiberAuxModel=[];
var tubeAuxModel=[];
var strandAuxModel=[];
var trenchModel=[];
var trenchAuxModel=[];
var junManModel=[];
var junHanModel=[];
//var Hashmap=[];
var hashMapList=[];
var createdUser ;
var lstModfUser;
var updateModfUser;
var MenuMap;
let srcCityAutocomplete, dstCityAutocomplete;

updateModfUser=`${userFullName}`;
//function on map clustring
function initMap() {

 $("#default").prop('checked', true);
 $("#landscape").prop('checked', true);
 $("#water").prop('checked', true);
 $("#transit").prop('checked', true);
 $("#poi").prop('checked', true);
 $("#road").prop('checked', true);
 $("#blank").prop('checked', false);
 $("#mapgeography").prop('checked', false);
 $("#maplabels").prop('checked', false);
 $("#countrynames").prop('checked', false);
 $("#countryprovince").prop('checked', false);
 
	button = document.getElementById('customMap');
	data = button.getAttribute('data-map');

	//console.log("Data is"+data);
	document.getElementById("network_tree").innerHTML ="";
	$("#network_tree").resizable({
		handles: "s", 	

	});

	var directionsDisplay=new google.maps.DirectionsRenderer();
	var directionsService=new google.maps.DirectionsService();

	  createdUser = $("#crtdByFiberCable").val();
	  lstModfUser = $("#modifiedByFiberCable").val();
	//New Map//
						          			map = new google.maps.Map(document.getElementById("mapContainer"), {
						          						center: { lat: 1, lng: 38 },
						          				 		mapTypeControl: true,
						          				 			mapTypeId: google.maps.MapTypeId.ROADMAP,
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

						          				 			fullscreenControl: true,
						          				 });
map.setOptions({ minZoom: 3, maxZoom: 28});
//new AutocompleteDirectionsHandler(map);
directionsDisplay.setMap(map);
/*
		    const input = document.getElementById("pac-input");
	        const searchBox = new google.maps.places.SearchBox(input);
	        // Bias the SearchBox results towards current map's viewport.
	        map.addListener("bounds_changed", () => {
	          searchBox.setBounds(map.getBounds());
	        });
	        let markerss = [];
	        // Listen for the event fired when the user selects a prediction and retrieve
	        // more details for that place.
	        searchBox.addListener("places_changed", () => {
	          const places = searchBox.getPlaces();

	          if (places.length == 0) {
		        alert("Not found!!");
	            return;
	          }
	          // Clear out the old markers.
	          markerss.forEach((marker) => {
	            marker.setMap(null);
	          });
	          markerss = [];
	          
	          // For each place, get the icon, name and location.
	          const bounds = new google.maps.LatLngBounds();
	          places.forEach((place) => {
	            if (!place.geometry) {
	              console.log("Returned place contains no geometry");
	              return;
	            }
	            const icon = {
	              url: place.icon,
	              size: new google.maps.Size(71, 71),
	              origin: new google.maps.Point(0, 0),
	              anchor: new google.maps.Point(17, 34),
	              scaledSize: new google.maps.Size(50, 50),
	            };
	            // Create a marker for each place.
	            markerss.push(
	              new google.maps.Marker({
	                map,
	                icon,
	                title: place.name,
	                position: place.geometry.location,
	              })
	            );

	            if (place.geometry.viewport) {
	              // Only geocodes have viewport.
	              bounds.union(place.geometry.viewport);
	            } else {
	              bounds.extend(place.geometry.location);
	            }
	          });
	          map.fitBounds(bounds);
	        });

	var markerId;
	var icon='https://img.icons8.com/ultraviolet/48/000000/google-maps-new.png';           	
	var icon1='https://img.icons8.com/color/48/000000/gps-device.png';	   
	var icon2 = {
			url:"http://maps.google.com/mapfiles/ms/icons/blue.png", // url
			scaledSize: new google.maps.Size(50, 50), // scaled size

		};
		  const srcInput = document.getElementById('srcCitySearchNames');
		  const dstInput = document.getElementById('dstCitySearchNames');
		  const autoCompleteOptions = {
		      componentRestrictions: { country: ['ke'] },
		      fields: ['place_id', 'geometry', 'name'],
		      
		     types: ['(cities)']
		    };

		    srcCityAutocomplete = new google.maps.places.Autocomplete(srcInput, autoCompleteOptions);
		    dstCityAutocomplete  = new google.maps.places.Autocomplete(dstInput, autoCompleteOptions);
		    srcCityAutocomplete.addListener('place_changed', onSrcPlaceChanged);
		    dstCityAutocomplete.addListener('place_changed', onDstPlaceChanged);
*/
		restingMap();

//		$('#filterr').show();
//		var x = document.getElementById("removeFilter");								 
		$("#open-popup-btn").removeAttr('disabled');
		console.log("Inside the success of physical layer");
		var keys =[];
		var Emptydiv=null;
		var projectID;
		filterFlag = ${filterFlag};
		checkedOption = '${checkedOption}';
		$("#filterSection").empty();
		 // This loop to build the filter tab information which is existed in the search popup.
		for (i = 0;i<Object.keys(${physicalLayerList}).length;i++){
			  if(Object.keys(${physicalLayerList})[i]=="Project"){
					str="<div class='row' style='margin-left:-15px;'  ><div class='col-md-6'><div class='input-group-prepend'><span style='font-size: 14px;width:200px;'class='input-group-text' ><b>Project</b></span><input	type='text' name='filteredField' id='FilteredProject' class='form-control text-input' placeholder='Project'/></div></div></div><p></p><p></p>";
				  }else if(Object.keys(${physicalLayerList})[i].includes("_")){
						let text = Object.keys(${physicalLayerList})[i];
						let result = text.replace("_", " ");
						result = result.charAt(0).toUpperCase() + result.slice(1);
						str="<div class='rowhashMapList' style='margin-left:-15px;' id='hashMapList'><div class='col-md-6' ><div class='input-group-prepend'><span style='font-size: 14px;width:200px;'class='input-group-text' ><b>"+result+"</b></span><input	type='text' name='filteredField' id='Filtered"+Object.keys(${physicalLayerList})[i]+"'class='form-control text-input' placeholder='"+result+"'/></div></div></div><p></p><p></p>";
					  }else{
						let	result = (Object.keys(${physicalLayerList})[i]).charAt(0).toUpperCase() + (Object.keys(${physicalLayerList})[i]).slice(1);
							  
						str="<div class='rowhashMapList' style='margin-left:-15px;' id='hashMapList'><div class='col-md-6'><div class='input-group-prepend'><span style='font-size: 14px;width:200px;'class='input-group-text' ><b>"+result+"</b></span><input	type='text' name='filteredField' id='Filtered"+Object.keys(${physicalLayerList})[i]+"'class='form-control text-input' placeholder='"+result+"'/></div></div></div><p></p><p></p>";
					  }
		$("#filterSection").append(str);

		  }	 physicalLayerFilter(); // to build click event on Filter Submit.
	      CreateTree_PhysicalLayer(${physicalLayerList}['Project'],${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['fiber'],${physicalLayerList}['Distribution_Board'],${physicalLayerData}['fiber_Tubes'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Auxiliary'],${physicalLayerData}['tubes_Auxiliaries'],${physicalLayerData}['strands_Auxiliaries'],${physicalLayerList}['Trench'],${physicalLayerData}['trench_Auxiliary'],${physicalLayerList}['Junction_Manhole'],${physicalLayerList}['Junction_Handhole'],filterFlag,${physicalLayerList}['duct'],${physicalLayerData}['ductAuxiliary'],${physicalLayerList}['NodeActiveList']);
		  CreateMap_PhysicalLayer(${physicalLayerList}['Project'],${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['fiber'],${physicalLayerList}['Distribution_Board'],${physicalLayerData}['fiber_Tubes'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Auxiliary'],${physicalLayerData}['tubes_Auxiliaries'],${physicalLayerData}['strands_Auxiliaries'],${physicalLayerList}['Trench'],${physicalLayerData}['trench_Auxiliary'],${physicalLayerList}['NodeActiveList']); 
		  if(checkedOption == "circleRange"){
			  openFindNearest(checkedOption,'${closestLatPoint}','${closestLongPoint}','${closestDisRange}','${noP}',${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['Distribution_Board'],${physicalLayerList}['fiber'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Tubes'],${physicalLayerList}['Node'],${physicalLayerList}['Transmission'],'${getRelatedPoints}');
		  }else if(checkedOption == "StartEnd"){
			  openFindBetweenMarkers(checkedOption,'${startLongPoint}','${startLatPoint}','${endLongPoint}','${endLatPoint}',${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['Distribution_Board'],${physicalLayerList}['fiber'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Tubes'],${physicalLayerList}['Node'],${physicalLayerList}['Transmission'],'${getRelatedPoints}');
		  }else if(checkedOption == "circleRange_multy"){
				 openFindNearestMultySite(checkedOption,'${rowData}','${noOfPoints}','${closestDisRange}','${ptList}','${ptData}','${getRelatedPoints}');
		  }else if(checkedOption == "connected"){
			  openSearchConnected(checkedOption,'${siteId}','${selectConnectedSearch}','${connectedSearchLong}','${connectedSearchLat}','${connectedViewOnMap}',${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Tubes'],${physicalLayerList}['fiber'],${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['Distribution_Board'],'${distribBoardListSize}','${getRelatedPoints}');
		   }		  
			  
		$(document).ready(function () { 
			$(function(){			 
			 $(document).trigger("triggerListenersEvent");
				
			});

		  });

} /// End of init Map

window.onload = function () {makeAllSortable();};	

</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>
</html>
