 <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<script src="${pageContext.request.contextPath}/resources/js/Network/FiberPathCreation.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/NodeCreation.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/JunctionCreation.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/SiteCreation.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/ProjectCreation.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/context-menu.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/xlsx.full.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jszip.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/BordersFindNearest.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/mapOperation.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/findNearest.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/findNearestMulty.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/Network/DBCreation.js"></script>
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
<%@ include file="/WEB-INF/views/Network/FiberSearch.jsp" %> 
<body>
	<c:set var="pg" value="network" scope="session" />
	<jsp:include page="../header.jsp"></jsp:include><p></p><br>
	<input type="text" id="fiberAuxFlag" name="fiberAuxFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="tubeAuxFlag" name="tubeAuxFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="strandAuxFlag" name="strandAuxFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="trenchAuxFlag" name="trenchAuxFlag" value="" class="form-control text-input" hidden="hidden" />	
	<input type="text" id="ductAuxFlag" name="ductAuxFlag" value="" class="form-control text-input" hidden="hidden" />	
	<input type="text" id="DBMappingFlag" name="DBMappingFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="manJctAttachmentFlag" name="manJctAttachment" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="projectAttachmentFlag" name="projectAttachmentFlag" value="" class="form-control text-input" hidden="hidden" />
	<input type="text" id="handJctAttachmentFlag" name="handJctAttachment" value="" class="form-control text-input" hidden="hidden" />
	
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
						<button type='submit' id='searchHeaderButton' class='searchButton searchHeaderButton' style='height: 35px;
    <c:if test="${findConnedted != '1'}">
        color: #ccc; 
      
        border-color: #ddd; 
        cursor: not-allowed; 
    </c:if>'
    <c:if test="${findConnedted != '1'}">
        disabled="disabled"
    </c:if>
><i class='fa fa-search'></i></button>
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
						<c:choose>
    <c:when test="${searchPopup != '1'}">
        <button type="button" class="btn btn-light" style="display: none;" data-toggle="tooltip" data-placement="top" title="Search" id="open-popup-btn"><i class="fa fa-search"></i></button>
    </c:when>
    <c:otherwise>
        <button type="button" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="Search" id="open-popup-btn"><i class="fa fa-search"></i></button>
    </c:otherwise>
</c:choose>
<button type="button" class="btn btn-light" id="tree" data-toggle="tooltip" data-placement="top" title=" Folder Tree"><i class="fas fa-sitemap"></i></button>
						<button type="button" class="btn btn-light" id="gis" data-toggle="tooltip" data-placement="top" title=" GIS"
							onclick='window.location.href = "${pageContext.request.contextPath}/GisPage"'><i class="fas fa-map-marked-alt"></i>
						</button>
 					    <button type="button" id="Fview" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="Form View" onclick='window.location.href = "${pageContext.request.contextPath}/SiteFormView"'><i class="fa fa-edit"></i></button>
						<a href="Sitelistview">
                       	<button type="submit" id="Lview" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="List View">
								<!--  onclick='window.location.href = "${pageContext.request.contextPath}/SiteListView"' -->
								<i class="fa fa-bars"></i></button></a>
					  <button type="button" class="btn btn-light" data-toggle="tooltip" data-placement="top" title="Cable Color" id="changeColorCable"  onclick="changeColor()"><i class="fa fa-paint-brush"></i></button>
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
			
			<!-- <a style="position: absolute; margin-left: 400px; display: none; z-index: 1;" id='removeFilter' href = "${pageContext.request.contextPath}/NetworkPhysicalLayer"> 
			<input type="image" src="${pageContext.request.contextPath}/resources/js/Network/RemoveFilter.png" style="position: absolute;" height="30" width="30" /></a>
			 --><a style="position: absolute; margin-left: 400px; display: none; z-index: 1; cursor: pointer;" id='removeS' href = "${pageContext.request.contextPath}/NetworkPhysicalLayer?updateModfUser=${userFullName}"> 
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
						 <c:if test="${readFiber == 1}">
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
							<td><input type="checkbox" id="strandCheckAllBoq" style="margin-left: 10px;"></td> </c:if>
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
							
					
                      <c:if test="${readManhole == 1}">
                     <td class="Icon " >
                      <img src="${pageContext.request.contextPath}/resources/NetworkImages/manholeRed.png"><span
								id="definition" style="padding-left: 4px;">MANHOLE</span></td>
							<td><input type="checkbox" id="manholeCheckAllBoq" style="margin-left: 10px;" ></td>   </c:if>
							
							
							
							
						<tr>
						  <c:if test="${readHandhole == 1}">
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/handholeYellow.png">
								<span id="definition">HANDHOLE</span></td>
							<td><input type="checkbox" id="handholeCheckAllBoq" style="margin-left: 10px;"></td></c:if>
						</tr>
						<tr>
							<td class="Icon "><img style="width: 16px; height: 16px;" src="${pageContext.request.contextPath}/resources/NetworkImages/junctionOrange.png">
								<span id="definition">JUNCTION</span></td>
							<td><input type="checkbox" id="junctionCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img style="width: 16px; height: 16px;" src="${pageContext.request.contextPath}/resources/img/NodesIcon.png">
								<span id="definition">Nodes</span></td>
							<td><input type="checkbox" id="nodesActiveCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img style="width: 20px; height: 20px;" src="${pageContext.request.contextPath}/resources/NetworkImages/redSiteIcon.png">
								<span id="definition">SITE</span></td>
							<td><input type="checkbox" id="siteCheckAllBoq" style="margin-left: 10px;"></td>
						</tr>
						<tr>
						 <c:if test="${readDB == 1}">
							<td class="Icon "><img src="${pageContext.request.contextPath}/resources/NetworkImages/electrical-panel.png">
								<span id="definition">DISTRIBUTION BOARD</span></td>
							<td><input type="checkbox" id="distBoardCheckAllBoq" style="margin-left: 10px;"></td></c:if>
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
						<ul class="nav nav-tabs" id="myTabDb" role="tablist"
							style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="projectInfo-tab" style="color: gold;" data-toggle="tab" href="#projectInfo" role="tab" aria-controls="projectInfo" aria-selected="true">Information </a></li>
							<li class="nav-item"><a class="nav-link " id="projectAttachment-tab" style="color: gold;" data-toggle="tab" href="#projectAttachment" role="tab" aria-controls="projectAttachment" aria-selected="false">Attachment</a></li>
						</ul>
						<div class="tab-content"><p></p>
						  <div class="tab-pane active" id="projectInfo" role="tabpanel" 	aria-labelledby="projectInfo-tab">
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
							
							<div class="row" style="display: none;">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color: green;"><b>Project Type</b> </span>
											 <input type="text" id="ProjectType" readonly class="form-control text-input" />
										</div>
									</div>
								</div>
							</div>
						</div>
									<div class="tab-pane" id="projectAttachment" role="tabpanel" aria-labelledby="projectAttachment-tab">
								<div style="display: flex; flex-direction: column;">
							        <form id="projectUploadForm" enctype="multipart/form-data" style="margin-right: 10px;">
							            <input type="file" id="projectFileInput" name="attachment"  required style="width: auto; display: inline-block;">
							            <button type="submit" class="btn btn-light file" style="display: inline-block; margin-left: 50px;">Upload</button>
							        </form>
										<br>
							        <div class="table-responsive-sm">
							            <table id="projectAttachmentTable" class="table table-striped table-bordered table-sm" style="display: block; height: 300px; overflow-y: auto;">
							                <thead>
							                    <tr class="fixed-headerr">
							                        <th>
							                            <button type="button" id="selectAllProjectAttachment" class="main">
							                                <span class="sub"></span>Select
							                            </button>
							                        </th>
							                        <th width="300px">Attachment Download</th>
							                        <th width="200px">Attachment Id</th>
							                        <th width="250px">Attachment Name</th>
							                        <th width="400px">Attachment Path</th>
							                    </tr>
							                </thead>
							                <tbody>
							                </tbody>
							            </table>
							        </div>
							        <button type="button" id="deleteProjectAttachmentRow" style="width:100px;">Delete Row</button>
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
						<c:if test="${saveManhole == 0}">
                        <button id="saveManhole" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px; display:none;">Save</button>
                         </c:if>
                      <c:if test="${saveManhole == 1}">
                     <button id="saveManhole" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button>
                  </c:if>
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
											<input type="text" id="ManholeName"  class="form-control text-input" <c:if test="${writeManhole == 0}">readonly</c:if> />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
											<input type="text" id="ManholeLong" class="form-control text-input" <c:if test="${writeManhole == 0}">readonly</c:if> />
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="ManholeLat" class="form-control text-input" <c:if test="${writeManhole == 0}">readonly</c:if> />
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
											<input type="text" id="ManholeModel" class="form-control text-input" <c:if test="${writeManhole == 0}">readonly</c:if> />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 140px;" class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="manholeCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 160px;"><b>Last Modified Date</b></span>
									<input type="text" id="manholeLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
						
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
							<div class="row">
								<div class="col-md-6" id="ManholeDMDiv">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DM_Name</b></span>
											<input type="text" id="manholeDMName" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-sm-6">
												<div class="form-group">
    <div class="input-group-prepend">
        <span style="width: 100px;" class="input-group-text"><b>Owner </b></span> 
        <select id="manholeOwner" class="form-control" <c:if test="${writeManhole == 0}">disabled</c:if>>
            <option value="" selected></option>
            <option value="tkl">TKL</option>
            <option value="ogn">OGN</option>
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
													<span style="width: 120px;"
														class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="manholeEngineerName" class="form-control text-input" <c:if test="${writeManhole == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> <input type="text" id="manholeInstaller" class="form-control text-input" <c:if test="${writeManhole == 0}">readonly</c:if> />
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
	
	
	
	
	
	<!--  CONTROLLER MODAL --> 
	
<div class="container">
		<div id="controllerModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="controllerHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Controller</h5>
						<div style="float: right;">
					
                     
                     <button id="saveController" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button>
                  
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
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_ControllerAutoComplete" style='position: relative; margin-left: 25px'  onchange="siteControllerChanged(this)"></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="customer_ControllerAutoComplete" style='position: relative; margin-left:15px'   onchange="customerControllerChanged(this)"> </span>
												</div></div></div>
												<div class="col-sm-3">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 170px; font-size: 12px;" class="input-group-text"><b>Custom Coordinates </b> 
													<input type="checkbox" id="customControllerCoordinates" value="0" style='position: relative; margin-left:5px ' ></span>
												</div></div></div>
												<div class="col-sm-2">
												<div class="form-group">
												<div class="input-group-prepend">
												
													<button id="getContCity" type="button" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%; margin-left:25px" onclick="getContCity()">Get City</button>
												</div></div></div></div>
								
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>ID</b> </span> 
											<input type="text" id="controllerId" readonly class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-md-6 nextprvItems">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
											<input type="text" id="ControllerName"  class="form-control text-input"  />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
									<div class="col-md-6" id="ContSite">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Site ID
												</b></span> <input type="text" id="ControllerSite" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6" id="ContSiteName">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Site Name </b></span> 
												<input type="text" id="ControllerSiteName" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6" id="ContClientId" >
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client ID
												</b></span>
												 <input type="text" id="ControllerClient" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6" id="ContClientName" >
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client Name </b></span> 
												<input type="text" id="ControllerClientName" class="form-control text-input" />
											</div></div></div></div>
							
								<div class="row">
									<div class="col-md-6" id="ContWarehouse" >
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Warehouse </b></span> 
												<input type="text" id="ControllerWarehouse" class="form-control text-input" />
											</div></div></div>
                                     <div class="col-md-6" id="ContClientPhoneNb" >
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client Phone# </b></span> 
												<input type="text" id="ControllerClientPhoneNb" class="form-control text-input" />
											</div></div></div>
								<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>City </b></span>
												 <input type="text" id="controllerCity" class="form-control text-input" readonly />
											</div></div></div></div>
							
						
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
											<input type="text" id="ControllerLong" class="form-control text-input"  />
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="ControllerLat" class="form-control text-input"  />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Serial Number</b></span>
											<input type="text" id="serialNumb" class="form-control text-input"  />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>MAC Address</b></span>
											<input type="text" id="macAddress" class="form-control text-input"  />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 140px;" class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="controllerCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 160px;"><b>Last Modified Date</b></span>
									<input type="text" id="controllerLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
						
							<div class="row">
								<div class="col-md-6" id="projectIdManhole">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>IP Address</b>
											</span> <input type="text" id="ipAddress"
												class="form-control text-input" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group" id="projectNameManhole">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Subnet Mask</b></span>
											 <input type="text" id="subnetMask" class="form-control text-input" />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6" id="ManholeDMDiv">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Default Gateway</b></span>
											<input type="text" id="gateWay" class="form-control text-input"  />
										</div>
									</div>
								</div>
								<div class="col-sm-6">
												
                                        <div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>User Name</b></span>
											<input type="text" id="userName" class="form-control text-input"  />
										</div>
									</div>
											</div>

							</div>
							<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px;"
														class="input-group-text"><b>Password </b></span> 
														<input type="text" id="password" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 150px; " class="input-group-text"><b>Number Of Panels </b></span> <input type="text" id="numbOfPanels" class="form-control text-input" <c:if test="${writeManhole == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
								</div>	
								
								<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px;"
														class="input-group-text"><b>Number Of Ports </b></span> 
														<input type="text" id="numbOfPorts" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
    <div class="input-group-prepend">
        <span style="width: 150px;" class="input-group-text"><b>Network Layer </b></span> 
        <select id="networkLayer" class="form-control" >
            <option value="choose" selected>choose</option>
            <option value="backbone">Backbone</option>
            <option value="metro">Metro</option>
            <option value="access">Access</option>
     
        </select>
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
<!-- Nodes Model -->
	<div class="container">
		<div id="nodesModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="nodesHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Node</h5>
						<div style="float: right;">
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
	<!-- Site Model -->
	<div class="container">
		<div id="sitesModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="sitesHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px">Site</h5>
						<div style="float: right;">
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
											<span style="width: 150px;" class="input-group-text" style="color:green;"><b>Warehouse ID</b> </span> 
											<input type="text" id="wareID" readonly class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>Site ID</b></span>
											<input type="text" id="siteID"  class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>Warehouse Name</b></span>
											<input type="text" id="wareName" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>City</b></span>
											<input type="text" id="siteCity" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Area</b></span>
											<input type="text" id="siteArea" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Region</b></span>
											<input type="text" id="siteRegion" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
											<input type="text" id="siteLong" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="siteLat" class="form-control text-input" readonly/>
										</div>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Creation Date</b></span>
											<input type="text" id="createData_site" class="form-control text-input" readonly />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 150px;" class="input-group-text"><b>Last Modified Date</b></span>
											<input type="text" id="lastModifiedDate_site" class="form-control text-input" readonly/>
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
								<button id="deleteManjunction" class="btn btn-delete" style="color: white; font-weight:bold; margin-left: 5px;background-color:#6AA84F; padding-left:15px; padding-right:15px;display: none" >Yes</button>								
							    <button id="deleteHandjunction" class="btn btn-delete" style="color: white; font-weight:bold; margin-left: 5px;background-color:#6AA84F; padding-left:15px; padding-right:15px;display: none" >Yes</button>								
							
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

	<div class="container">
		<div id="DeleteProjectModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="DeleteProjectHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float: right;">
							<button type="button" name="closePopup" class="close" id="closeDeleteProjectModal" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content" >
							<p id="deleteProjectBody" style="font-weight: bold;"></p>
							<div style="text-align:center;">
								<button id="deletePlanningProject" class="btn btn-delete" style="color: white; font-weight:bold;  margin-right: 5px ; background-color:#6AA84F">Yes</button>
								<button id="deleteImplementProject" class="btn btn-delete" style="color: white; font-weight:bold;  margin-right: 5px ; background-color:#6AA84F">Yes</button>								
								<button id="deleteProjectCancellation" class="btn btn-delete" style="color: white; font-weight:bold; margin-left: 5px;background-color:#6AA84F; padding-left:15px; padding-right:15px">No</button>							
							</div>								
							</div>
						</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>


		<div class="container">
		<div id="MoveModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="confirmHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float: right;">
							<button  type="button" name="closePopup" class="close" id="closeMoveModal" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content" >
							<p id="moveBody" style="font-weight: bold;color:#000000BB;"></p>							
							<div style="text-align:center;">
								<button id="moveToImplement" class="btn btn-delete" style="color: white; font-weight:bold;  margin-right: 5px ; background-color:#6AA84F">Yes</button>
								<button id="moveToCurrent" class="btn btn-delete" style="color: white; font-weight:bold;  margin-right: 5px ; background-color:#6AA84F">Yes</button>														
								<button id="moveCancel" class="btn btn-delete" style="color: white; font-weight:bold; margin-left: 5px;background-color:#6AA84F; padding-left:15px; padding-right:15px">No</button>								
							</div>								
							</div>
						</div>
					</div>
					<div class="modal-footer"></div>
				</div>
			</div>
		</div>	
		
			
		<!-- confirmation modal -->
		<div class="container">
		<div id="ConfirmModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px; ">
						<h5 id="confirmHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;"></h5>
						<div style="float: right;">
							<button  type="button" name="closePopup" class="close" id="close" onclick="ClosingConfirm()" >
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a>
						</div></div>
					<div class="modal-body">
						<div class="tab-content" >
							<p id="confirmbody" style="font-weight: bold;color:#000000BB;"></p>
							
							<table id=confirm_table>
									<tr>
										
									</tr>
							</table>
							
							
							<div style="text-align:center;">
								<button id="confirmClose" class="btn btn-delete" style="color: white; font-weight:bold; margin-left: 5px;background-color:#6AA84F; padding-left:15px; padding-right:15px">Ok</button>								
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
							<li class="nav-item"><a class="nav-link active" id="junction-tab" style="color: gold;" data-toggle="tab" href="#MJCT" role="tab" aria-controls="JCT" aria-selected="true">Junction </a></li>
							<li class="nav-item"><a class="nav-link " id="jctMapping-tab" style="color: gold;" data-toggle="tab" href="#jctMapping" role="tab" aria-controls="jctMapping" aria-selected="false">Mapping</a></li>
							<li class="nav-item"><a class="nav-link " id="manJctAttachment-tab" style="color: gold;" data-toggle="tab" href="#manJctAttachment" role="tab" aria-controls="manJctAttachment" aria-selected="false">Attachment</a></li>
						</ul>
						<div class="tab-content">
							<p></p>
							<div class="tab-pane active" id="MJCT" role="tabpanel"
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
												<span style="width: 175px;" class="input-group-text"><b>No of Junctions
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
									</div></div></div>
								    <div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
													<span style="width: 100px;" class="input-group-text"><b>Owner </b></span> <select id="manholeJctOwner" class="form-control">
															<option value="" selected></option>
															<option value="tkl">TKL</option>
															<option value="ogn">OGN</option>
															<option value="nofbi">NOFBI</option>
															<option value="others">Others</option>
														</select>
							</div></div></div> </div>
							     <div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px;"
														class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="manholeJctEngineerName" class="form-control text-input" />
										</div></div></div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> <input type="text" id="manholeJctInstaller" class="form-control text-input" />
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
										<button type="button" id="addManJunMapBelow" >Insert Row Below</button>
										<button type="button" id="addManJunMapAbove" >Insert Row Above</button>
										<!--  <button type="button" id="manholeJctAddRow">Add Row</button>-->
										<button type="button" id="manholeJctDelRow">Delete Row</button>
									</form></div></div>
				  <div class="tab-pane" id="manJctAttachment" role="tabpanel" aria-labelledby="manJctAttachment-tab">
				    <div style="display: flex; flex-direction: column;">
				        <form id="uploadForm" enctype="multipart/form-data" style="margin-right: 10px;">
				            <input type="file" id="fileInput" name="attachment"  required style="width: auto; display: inline-block;">
				            <button type="submit" class="btn btn-light file" style="display: inline-block; margin-left: 50px;">Upload</button>
				        </form>
							<br>
				        <div class="table-responsive-sm">
				            <table id="junctionAttachmentTable" class="table table-striped table-bordered table-sm" style="display: block; height: 300px; overflow-y: auto;">
				                <thead>
				                    <tr class="fixed-headerr">
				                        <th>
				                            <button type="button" id="selectAllManJctAttachment" class="main">
				                                <span class="sub"></span>Select
				                            </button>
				                        </th>
				                        <th width="300px">Attachment Download</th>
				                        <th width="200px">Attachment Id</th>
				                        <th width="250px">Attachment Name</th>
				                        <th width="400px">Attachment Path</th>
				                    </tr>
				                </thead>
				                <tbody>
				                </tbody>
				            </table>
				        </div>
				        <button type="button" id="deleteJctAttachmentRow" style="width:100px;">Delete Row</button>
				    </div>
				</div></div></div></div></div></div></div>
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
							<li class="nav-item"><a class="nav-link " id="handJctAttachment-tab" style="color: gold;" data-toggle="tab" href="#handJctAttachment" role="tab" aria-controls="handJctAttachment" aria-selected="false">Attachment</a></li>
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
												<span style="width: 175px;" class="input-group-text"><b>No of Junctions
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
											</div></div></div>
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
													<span style="width: 100px;" class="input-group-text"><b>Owner </b></span> <select id="handholeJctOwner" class="form-control">
															<option value="" selected></option>
															<option value="tkl">TKL</option>
															<option value="ogn">OGN</option>
															<option value="nofbi">NOFBI</option>
															<option value="others">Others</option>
														</select>
								</div></div></div></div>
								<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px;"
														class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="handholeJctEngineerName" class="form-control text-input" />
										</div></div></div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> <input type="text" id="handholeJctInstaller" class="form-control text-input" />
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
									</form></div></div>
 <div class="tab-pane" id="handJctAttachment" role="tabpanel" aria-labelledby="handJctAttachment-tab">
    <div style="display: flex; flex-direction: column;">
        <form id="handJctUploadForm" enctype="multipart/form-data" style="margin-right: 10px;">
            <input type="file" id="handJctFileInput" name="attachment" required style="width: auto; display: inline-block;">
            <button type="submit" class="btn btn-light file" style="display: inline-block; margin-left: 50px;">Upload</button>
        </form>
			<br>
        <div class="table-responsive-sm">
            <table id="handJunctionAttachmentTable" class="table table-striped table-bordered table-sm" style="display: block; height: 300px; overflow-y: auto;">
                <thead>
                    <tr class="fixed-headerr">
                        <th>
                            <button type="button" id="selectAllHandJctAttachment" class="main">
                                <span class="sub"></span>Select
                            </button>
                        </th>
                        <th width="300px">Attachment Download</th>
                        <th width="200px">Attachment Id</th>
                        <th width="250px">Attachment Name</th>
                        <th width="400px">Attachment Path</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <button type="button" id="deleteHandJctAttachmentRow" style="width:100px;">Delete Row</button>
    </div>
</div></div></div></div></div></div></div>
	<!-- Junction Modal -->
	<div class="container">
		<div id="JunctionModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px;">
						<h5 class="modal-title" id="JunctionHeader" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Junction</h5>
						<div style="float: right;">
							<button id="saveJunction" class="btn btn-save" style="color: black; font-weight:bold;  margin-top:-6px">Save</button>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i>
							</a>
						</div>
					</div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myJunTabDb" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="Junction-tab" style="color: gold;" data-toggle="tab" href="#JCT" role="tab" aria-controls="JCT" aria-selected="true">Junction </a></li>
							<li class="nav-item"><a class="nav-link " id="JctMapping-tab" style="color: gold;" data-toggle="tab" href="#JctMapping" role="tab" aria-controls="jctMapping" aria-selected="false">Mapping</a></li>
						</ul>
						<div class="tab-content">
							<p></p>
							<div class="tab-pane active" id="JCT" role="tabpanel"
								aria-labelledby="junction-tab">
								<p></p>
								<div class="container-fluid">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text" style="color:green;"><b> ID </b></span>
												 <input type="text" id="JunctionId" readonly class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-md-6 nextprvItems">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 150px;" class="input-group-text"><b>Name</b></span>
												<input type="text" id="JctName" class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
												<input type="text" id="JctLong" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
												<input type="text" id="JctLat" class="form-control text-input" readonly />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Capacity </b></span>
												 <input type="text" id="JctCapacity" class="form-control text-input" />
											</div></div></div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>No of Junctions
														 </b></span> <input type="text" id="NumberJct"
													class="form-control text-input" />
											</div>
										</div>
									</div>
								</div>
								<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 120px;" class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="JunctionCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 170px;"><b>Last Modified Date</b></span>
									<input type="text" id="JunctionLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>City
												</b></span> <input type="text" id="JctCity" class="form-control text-input" readonly />
											</div></div></div>
										<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
													<span style="width: 100px;" class="input-group-text"><b>Owner </b></span> <select id="JunctionOwner" class="form-control">
															<option value="" selected></option>
															<option value="tkl">TKL</option>
															<option value="ogn">OGN</option>
															<option value="nofbi">NOFBI</option>
															<option value="others">Others</option>
														</select>
													</div>
												</div>
											</div></div>
								<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px;"
														class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="JunctionEngineerName" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> <input type="text" id="JunctionInstaller" class="form-control text-input" />
												</div>
											</div>
										</div>
								</div></div></div>
							<div class="tab-pane" id="JctMapping" role="tabpanel" aria-labelledby="jctMapping-tab"><p></p>
								<div class="container-fluid"><form>
										<div class="table-responsive-sm" id="JctMappingDiv">
											<table id="JctMappingTable" class="table table-striped table-bordered table-sm " style="display: block; height: 250px; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr><th></th>
														<th colspan="1"></th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="13">Side A</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="13">Side B</th></tr>
													<tr><th><button type="button" id="JctSelectAll" class="main"><span class="sub"></span>Select </button></th>
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
										<!--  <button type="button" id="JctAddRow">Add Row</button>-->
										<button type="button" id="JctDelRow">Delete Row</button>
									</form></div></div></div></div></div></div></div></div>
	<!--  Strand Modal -->
		<div class="container">
		<div id="StrandModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
			<div class="modal-dialog modal-lg modal-dialog-scrollable modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #2678CC ; height: 55px">
						<h5 id="StrandHeader" class="modal-title" style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Strand:</h5>
						<div style="float: right;">
						<c:if test="${saveFiber == 1}">
							<button id="savefiberstrand" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button></c:if>
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
													<input type="text" id="StrandName" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
														 <input type="text" id="fibercableStrand" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 80px; font-size: 12px;" class="input-group-text"><b>Tube </b></span> 
														<input type="text" id="fibertubeStrand" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
											<input type="text" id="StrandLength" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/></div></div>
								</div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
								<span style="width: 150px; font-size: 12px;" class="input-group-text"><b>Driving Distance </b></span> <input type="text" id="strandDrivDist" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
							</div></div></div></div></div>
														<div class="container-fluid">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 110px; font-size: 12px;" class="input-group-text"><b>Strand Number </b></span> 
												<input type="text" id="strandNumber" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/></div></div>
									</div>								
									<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
										<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Strand Color </b></span>
												<select id="strandColor" class="form-control" style="height:38px;" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
														<select id="frmstrandtype" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
														<select id="frmstranddep" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
														<select id="frmstrandowner" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
													<input type='checkbox' id="site_StrandAutoComplete" style='position: relative; margin-left: 25px' class="srcDestStrandAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="customer_StrandAutoComplete" style='position: relative; margin-left:15px' class="srcDestStrandAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_StrandAutoComplete" style='position: relative; margin-left:10px' class="srcDestStrandAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_StrandAutoComplete" style='position: relative; margin-left:15px' class="srcDestStrandAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_StrandAutoComplete" style='position: relative; margin-left:25px' class="srcDestStrandAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
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
													<input type="text" id="sourcestrand" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> 
													<input type="text" id="destinationstrand" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span>
													 <input type="text" id="SourceTypeStrand" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type </b></span>
													 <input type="text" id="DestinationTypeStrand" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
													 <input type="text" id="sourcelongstrand" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="sourcestranddiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lng</b></span>
													 <input type="text" id="destinationlongstrand" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
													<input type="text" id="sourcelatstrand" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="sourcestranddiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lat</b></span> 
													<input type="text" id="destinationlatstrand" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
									<input type='checkbox' id="Site_AutocompleteStrand" value='0' style='position: relative; margin-left: 25px' class="auxPtStrandAutocomplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input type='checkbox' id="Manhole_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px" class="input-group-text"><b>By Handhole </b> <input
														type='checkbox' id="Handhole_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input type='checkbox' id="DB_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left:25px' <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px"
														class="input-group-text"><b>By Auxiliary Point </b> <input type='checkbox' id="AuxPt_AutocompleteStrand" value='0' class="auxPtStrandAutocomplete" style='position: relative; margin-left: 15px' <c:if test="${writeFiber == 0}">disabled</c:if>></span>
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
										 <c:if test="${writeFiber == 1}">
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
										</c:if>
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
											 <c:if test="${writeFiber == 1}">
										<button type="button" id="addStrandAuxBelow">Insert Row Below</button>
										<button type="button" id="addStrandAuxAbove">Insert Row Above</button>
										<button type="button" id="delete_AuxStrand">Delete Row</button></c:if>
										<button type="button" id="sortByDistanceStrand">Sort by distance</button>
										<button type="button"><b>Draw By </b> 
                                          <select id ="strandDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> 
                                          </button>
                                          <c:if test="${writeFiber == 1}">
										<button type="button" id="setCoordinateStrandAux" >Set Coordinate</button>
										</c:if>
										<button type="button" id="calculateDrivingDistanceStrand"> Calculate Driving Distance</button>
										<button type="button" id="calculateGeoDistanceStrand"> Calculate Geo Distance</button>
									<br>
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
						    <c:if test="${saveFiber == 1}">
						
							<button id="savefibertube" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button></c:if>
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
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Name </b></span> <input type="text" id="TubeName" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
								</div></div></div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b> Fiber Cable </b> </span> <input type="text" id="fiberCable" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
											<input type="text" id="TubeLength" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/></div></div>
								</div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
								<span style="width: 150px; font-size: 12px;" class="input-group-text"><b>Driving Distance </b></span> <input type="text" id="tubeDrivDist" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
							</div></div></div></div></div>
							<div class="container-fluid">
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Tube Number </b></span> 
												<input type="text" id="tubeNumber" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/></div></div></div>								
									<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
										<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Tube Color </b></span>
												<select id="tubeColor" class="form-control" style="height:38px;" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
														<select id="frmtubetype" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
															<option value=""></option>
															<option value="g561">G.651</option>
															<option value="g562">G.652</option>
															<option value="g563">G.653</option>
														</select>
										</div></div></div>
										<div class="col-md-6">
											<div class="form-group">
												<div class="input-group-prepend" id="dstDivTube">
													<span style="width: 140px; font-size: 12px;"
														class="input-group-text"><b>Tube Deployment</b></span> 
														<select id="frmtubedep" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
														<select id="frmtubeowner" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
													<input type='checkbox' id="site_TubeAutoComplete" style='position: relative; margin-left: 25px' class="srcDestTubeAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="customer_TubeAutoComplete" style='position: relative; margin-left:15px' class="srcDestTubeAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_TubeAutoComplete" style='position: relative; margin-left:10px' class="srcDestTubeAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_TubeAutoComplete" style='position: relative; margin-left:15px' class="srcDestTubeAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_TubeAutoComplete" style='position: relative; margin-left:25px' class="srcDestTubeAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if>></span>
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
													<input type="text" id="SourceTube" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> 
													<input type="text" id="DestinationTube" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> 
													<input type="text" id="SourceTypeTube" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type </b></span> 
													<input type="text" id="DestinationTypeTube" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
													<input type="text" id="sourcelong" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="SourceTubeDiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lng</b></span>
													 <input type="text" id="destinationlong" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
													 <input type="text" id="sourcelat" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend" id="DestinationTubeDiv">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Destination Lat</b></span> 
													<input type="text" id="destinationlat" readonly class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if>/>
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
											<input type='checkbox' id="Site_AutocompleteTube" value='0' style='position: relative; margin-left: 25px' class="auxPtTubeAutocomplete" <c:if test="${writeFiber == 0}">disabled</c:if>/></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="Manhole_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if>/></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px" class="input-group-text"><b>By Handhole </b>
													 <input type='checkbox' id="Handhole_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if>/></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;" class="input-group-text"><b>By DB</b> 
													<input type='checkbox' id="DB_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left:25px' <c:if test="${writeFiber == 0}">disabled</c:if>/></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px" class="input-group-text"><b>By Auxiliary Point </b>
													 <input type='checkbox' id="AuxPt_AutocompleteTube" value='0' class="auxPtTubeAutocomplete" style='position: relative; margin-left: 15px' <c:if test="${writeFiber == 0}">disabled</c:if>/></span>
												</div>
											</div>
										</div>
									</div>
									
										<div class="row">
										  <c:if test="${writeFiber == 1}">
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
									</c:if>
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
										  <c:if test="${writeFiber == 1}">
										<input type="text" id="Rowindexaux" value="" hidden="">
										<button type="button" id="addTubeAuxBelow">Insert Row Below</button>
										<button type="button" id="addTubeAuxAbove">Insert Row Above</button>
										<button type="button" id="delete_AuxTube">Delete Row</button></c:if>
										<button type="button" id="sortByDistanceTube">Sort by distance</button>
										<button type="button"><b>Draw By </b> 
                                         <select id ="tubeDrawingBy"> 
                                            <option value = "DEFAULT"></option>
                                            <option value = "DRIVING">Driving</option> 
                                            <option value = "LINEOFSITE">Line of Site</option></select> 
                                          </button>
                                           <c:if test="${writeFiber == 1}">
										<button type="button" id="setCoordinateTubeAux" >Set Coordinate</button></c:if>
										<button type="button" id="calculateDrivingDistanceTube"> Calculate Driving Distance</button>
										<button type="button" id="calculateGeoDistanceTube"> Calculate Geo Distance</button>
										<br>
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
		<div id="handholeModalDetails"	class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
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
	<c:if test="${addManhole == 1}">
		<li class="menu-item mapMenuItem">
			<button type="button" id="addManhole" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Add Manhole</span></button> 
		</li></c:if>
		<c:if test="${addHandhole == 1}">
		<li class="menu-item mapMenuItem">
			<button type="button" id="addHandhole" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Add Handhole</span></button>
		</li></c:if>
		<c:if test="${addDB == 1}">
		<li class="menu-item mapMenuItem">
			<button type="button" id="addDistributionBoard" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Add Distribution Board</span> </button>
		</li></c:if>
		<li class="menu-item mapMenuItem">
			<button type="button" id="getCoordinatePoint" class="menu-btn"> <i class="fa fa-folder-plus"></i> <span class="menu-text">Get Coordinate</span></button>
		</li>
		<li class="menu-item mapMenuItem">
			<button type="button" id="showClosePoints" class="menu-btn"> <i class="fa fa-paste"></i> <span class="menu-text">Show Close Points</span></button>
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
	<div class="modal-content" style="width: 850px;"><div class="modal-header"style="background-color: #2678CC ; height: 55px; "><h5  class="modal-title"
style="font-weight: bold; color: #E9ECEF; position: relative; bottom: 12px;">Operations</h5><div style="float: right;"><button class="btn btn-save"style="color: black; font-weight:bold; margin-top:-6px" onclick="MapOperationmarking()">Add Marker</button><button type="button"  onClick="clearMarkers()" class="btn btn-save" style=" margin-left:10px;color: black; font-weight:bold; margin-top:-6px" id="clearMarkers">Clear Markers</button>
<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()"><i class='fa fa-times'></i></button><a class="close modalMinimize ml-3"> <i
class='fa fa-minus icon-to-change'></i></a></div></div><div class="modal-body"><div class="tab-pane" ><p></p><div class="container-fluid">
<div class="row">
    <div  style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                    <b>By Site</b>
                    <input type="checkbox" id="site_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                </span>
            </div>
        </div>
    </div>

    <div style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                    <b>By Client</b>
                    <input type="checkbox" id="customer_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                </span>
            </div>
        </div>
    </div>
 
   <div style="display: none;" id="manholeSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 120px; font-size: 12px;">
                        <b>By Manhole</b>
                        <input type="checkbox" id="manhole_operationAutoComplete" style="margin-left: 10px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>


   <div style="display: none;" id="handholeSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 120px; font-size: 12px;">
                        <b>By Handhole</b>
                        <input type="checkbox" id="handhole_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>
    <div style="display: none;" id="dbSection">
        <div style="margin-right: 10px;">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" style="min-width: 90px; font-size: 12px;">
                        <b>By DB</b>
                        <input type="checkbox" id="db_operationAutoComplete" style="margin-left: 15px;" class="mapOperationAutoComplete" onclick="mapFeilds()">
                    </span>
                </div>
            </div>
        </div>
    </div>

    <div style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 95px; font-size: 12px;">
                    <b>By Place</b>
                    <input type="checkbox" id="place_operationAutoComplete" style="margin-left: 10px;" class="mapOperationAutoComplete" onclick="placeFeild()">
                </span>
            </div>
        </div>
    </div>

    <div>
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text" style="min-width: 95px; font-size: 12px;">
                    <b>Generic</b>
                    <input type="checkbox" id="generic_operationAutoComplete" style="margin-left: 10px;" class="mapOperationAutoComplete" onclick="placeFeild()">
                </span>
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
										
										
											<div class="col-sm-3"  id="setCoordOperation" style="display:none;">
											<div class="form-group">
												<button id="setCoordOperationBtn" title="Get Coordinate From Map" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%;">Set Coordinate</button>
											</div>
										</div>									
									</div>
									
									
									
										<div class="row" >
										<div  id="markerName" class="col-sm-9" style="display:none;">
											<div class="form-group" >
												<div class="input-group-prepend"  >
													<span style="width: 200px; font-size: 12px;"
														class="input-group-text"><b style="margin:20px">Marker Name </b></span>
														 	<input
														type="text" id="markerN"  class="form-control text-input"  />
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
	          <c:if test="${saveFiber == 1}">
							<button id="saveFiberPathColor" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button> </c:if>
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
						<c:if test="${saveFiber == 1}">
							<button id="saveFiberPath" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px">Save</button></c:if>
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
														type="text" id="fiberName" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div></div></div>
									</div>
								</div>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-6" id="itemDiv">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;"
														class="input-group-text"><b>Item Code </b></span> 
														<input type="text" id="ItemCodeId" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;"
														class="input-group-text"><b>Tubes Number </b></span> <input
														type="text" id="NumTubes" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
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
														class="input-group-text"><b>Length </b></span> <input type="text" id="FiberLength" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;"
														class="input-group-text"><b>Strands Number </b></span> <input type="text" id="NumStrands" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
									</div>								
									<div class="row"><div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
													<span style="width: 150px; font-size: 12px;"
														class="input-group-text"><b>Driving Distance </b></span> <input
														type="text" id="FiberDrivDist"
														class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div></div></div>
												<div class="col-sm-6">
											<div class="form-group">
    <div class="input-group-prepend">
        <span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Cable Size </b></span> 
        <select id="fiberCableSize" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
            <option value=""></option>
            <option value="2C">2C</option>
            <option value="4C">4C</option>
            <option value="8C">8C</option>
            <option value="12C">12C</option>
            <option value="16C">16C</option>
            <option value="24C">24C</option>
            <option value="48C">48C</option>
            <option value="96C">96C</option>
            <option value="144C">144C</option>
            <option value="288C">288C</option>
            <option value="576C">576C</option>
        </select>
    </div>
</div>

											</div>		
									</div>
									<div class="row">
											<div class="col-sm-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span style="width: 100px; font-size: 12px;"
														class="input-group-text"><b>Fiber Type </b></span> 
													<select id="fibertype" class="form-control"<c:if test="${writeFiber == 0}">disabled</c:if>>
    <option value=""></option>
    <option value="g561">G.651</option>
    <option value="g562">G.652</option>
    <option value="g563">G.653</option>
    <option value="g564">G.654</option>
    <option value="g565">G.655</option>
    <option value="g566">G.656</option>
    <option value="g567">G.657</option>
</select>

													</div>
												</div>
											</div>										
											<div class="col-sm-6">
												<div class="form-group">
													<div class="input-group-prepend">
														<span style="font-size: 12px;"
														class="input-group-text"><b>Fiber Deployment </b></span> <select id="fiberdeployment" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
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
														<span style="font-size: 12px;" class="input-group-text"><b>Fiber Network Level </b></span> <select id="fibernetlevel" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
    <option value="backbone" selected>Backbone</option>
    <option value="metro">Metro</option>
    <option value="access">Access</option>
</select>

													</div>
												</div>
											</div>										
											<div class="col-sm-6">
												<div class="form-group">
    <div class="input-group-prepend">
        <span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Fiber Owner </b></span> 
        <select id="fiberowner" class="form-control" <c:if test="${writeFiber == 0}">disabled</c:if>>
        <option value="" selected>Choose</option>
        <c:forEach var="owner" items="${fiberOwners}">
            <option value="${owner}">${owner}</option>
        </c:forEach>
    </select>
    </div>
</div>

											</div>
										</div>
										<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;"
														class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="fiberEngineerName" class="form-control text-input" <c:if test="${writeFiber == 0}">disabled</c:if>>
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; font-size: 12px;"
														class="input-group-text"><b>Installer </b></span> <input type="text" id="fiberInstaller" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
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
														name="Single Mode" style='position: relative; left: 20px;'<c:if test="${writeFiber == 0}">readonly</c:if> /></span>
													<span
														style="min-width: 150px; font-size: 12px; margin-left: 50px"
														class="input-group-text"><b>Multimode </b> <input
														type='checkbox' class="fiberMode" id="Multimode"
														name="Multimode" style='position: relative; left: 20px;'<c:if test="${writeFiber == 0}">readonly</c:if> /></span>
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
													<span style="width: 100px; font-size: 12px;" class="input-group-text"><b>Condiut_Id </b></span> <input type="text" id="Condiut_Id" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px; font-size: 12px;" class="input-group-text"><b>Condiut_Name </b></span> <input type="text" id="Condiut_Name" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
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
													<input type='checkbox' id="site_CableAutoComplete" style='position: relative; margin-left: 25px' class="srcDestCableAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="customer_CableAutoComplete" style='position: relative; margin-left:15px' class="srcDestCableAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;" class="input-group-text"><b>By Manhole </b> 
													<input type='checkbox' id="manhole_CableAutoComplete" style='position: relative; margin-left:10px' class="srcDestCableAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 120px; font-size: 12px;" class="input-group-text"><b>By Handhole </b> 
													<input type='checkbox' id="handhole_CableAutoComplete" style='position: relative; margin-left:15px' class="srcDestCableAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;position: relative; margin-left:25px" class="input-group-text"><b>By DB </b> 
													<input type='checkbox' id="db_CableAutoComplete" style='position: relative; margin-left:25px' class="srcDestCableAutoComplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
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
														type="text" id="Source" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group" id="DestinationDiv">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination </b></span> <input type="text" id="Destination" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Source Type</b></span> <input type="text" id="SourceType" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 140px; font-size: 12px;" class="input-group-text"><b>Destination Type </b></span> <input type="text" id="DestinationType" class="form-control text-input" <c:if test="${writeFiber == 0}">readonly</c:if> />
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
								<div class="row">
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
				<button id="getSrcCity" type="button" class="btn btn-primary" 
				        style="color: white; font-size: 13px; height: 40px; width: 40%;" 
				        onclick="getSrcCity()">Get Source City</button>

  </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <div class="input-group-prepend">
				<button id="getDstCity" type="button" class="btn btn-primary" 
				        style="color: white; font-size: 13px; height: 40px; width: 40%;" 
				        onclick="getDstCity()">Get Destination City</button>
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
									<input type='checkbox' id="Site_AutocompleteTubeFiber" value='0' style='position: relative; margin-left: 25px' class="fiberTubeAutocomplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position: relative; margin-left:-10px"
														class="input-group-text"><b>By Client </b> <input
														type='checkbox' id="customer_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left:25px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input
														type='checkbox' id="Manhole_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px"
														class="input-group-text"><b>By Handhole </b> <input
														type='checkbox' id="Handhole_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input
														type='checkbox' id="DB_AutocompleteTubeFiber" value='0' class="fiberTubeAutocomplete" style='position: relative; margin-left:25px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
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
													<c:if test="${writeFiber == 1}">
    <button type="button" id="add_Tube">Add Row</button>
    <button type="button" id="delete_Tube">Delete Row</button>
</c:if>
<c:if test="${writeFiber == 0}">
    <button type="button" id="add_Tube" disabled>Add Row</button>
    <button type="button" id="delete_Tube" disabled>Delete Row</button>
</c:if>
									</form></div></div>
							<div class="tab-pane " id="strands" role="tabpanel" aria-labelledby="strands-tab"><p></p>
								<div class="container-fluid">
									<div class="row">
										<div class="col-sm-2"><div class="form-group">
										<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id="Site_AutocompleteStrandFiber" value='0' style='position: relative; margin-left: 25px' class="fiberStrandAutocomplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position: relative; margin-left:-10px"
														class="input-group-text"><b>By Client </b> <input
														type='checkbox' id="customer_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete"
														style='position: relative; margin-left:25px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px"
														class="input-group-text"><b>By Manhole </b> <input type='checkbox' id="Manhole_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px"
														class="input-group-text"><b>By Handhole </b> <input type='checkbox' id="Handhole_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;"
														class="input-group-text"><b>By DB</b> <input type='checkbox' id="DB_AutocompleteStrandFiber" value='0' class="fiberStrandAutocomplete" style='position: relative; margin-left:25px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
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
																<c:if test="${writeFiber == 1}">
    <button type="button" id="add_Strand">Add Row</button>
    <button type="button" id="delete_Strand">Delete Row</button>
</c:if>
<c:if test="${writeFiber == 0}">
    <button type="button" id="add_Strand" disabled>Add Row</button>
    <button type="button" id="delete_Strand" disabled>Delete Row</button>
</c:if>

									</form></div></div>
							<div class="tab-pane " id="auxiliary" role="tabpanel" aria-labelledby="fiber_aux_tab"><p></p>
								<div class="container-fluid"><div class="row"><div class="col-sm-2"><div class="form-group">
									<div class="input-group-prepend">
									<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
									<input type='checkbox' id=Site_AutocompleteCable value='0' style='position: relative; margin-left: 25px' class="auxPtAutocomplete" <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
										</div> </div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 110px; font-size: 12px;position:relative; margin-left:-8px" class="input-group-text"><b>By Manhole </b> <input
														type='checkbox' id=Manhole_AutocompleteCable value='0' class="auxPtAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 115px; font-size: 12px;position: relative; margin-left:-8px" class="input-group-text"><b>By Handhole </b> <input
														type='checkbox' id="Handhole_AutocompleteCable" value='0' class="auxPtAutocomplete" style='position: relative; margin-left: 10px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 80px; font-size: 12px;position: relative; margin-left:3px;" class="input-group-text"><b>By DB</b> <input
														type='checkbox' id=DB_AutocompleteCable value='0' class="auxPtAutocomplete" style='position: relative; margin-left:25px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px;position: relative; margin-left:-10px"class="input-group-text"><b>By Auxiliary Point </b> <input
														type='checkbox' id="AuxPt_AutocompleteCable" value='0' class="auxPtAutocomplete" style='position: relative; margin-left: 15px' <c:if test="${writeFiber == 0}">disabled</c:if> /></span>
												</div>
											</div>
										</div>
									</div>
								
									<div class="row">
									  <c:if test="${writeFiber == 1}">
										<div class="col-sm-5">
    <div class="form-group">

        <div class="input-group-prepend">
          
            <label class="file">
                <input type="file" style="font-size: 13px; " id="importAuxfile" accept=".xlsx" class="btn btn-light file" >
            </label>
        </div>
    </div>
</div>

										<div class="col-sm-4">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 150px; font-size: 12px; margin-top:10px" class="input-group-text"><b>Append to the existed points</b> <input
														type='checkbox' id="AuxPt_AppendTo" value='0' style='position: relative; margin-left: 15px' ></span>
												</div>
											</div>
										</div>
										<div class="col-sm-2">
    <div class="form-group">
        <div class="input-group-prepend">
        
            <button id="upload" class="btn btn-primary" style="margin-top: 10px; " >Import</button>
        </div>
    </div>
</div>
</c:if>

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
										<c:if test="${writeFiber == 1}">

<button type="button" id="addAuxBelow" class="hide-button">Insert Row Below</button>

<button type="button" id="addAuxAbove" class="hide-button">Insert Row Above</button>

<button type="button" id="delete_Aux" class="hide-button">Delete Row</button></c:if>
<c:if test="${writeFiber == 1}">
<button type="button" id="sortingDistance" class="hide-button">Sort By Distance</button></c:if>

<button type="button">
    <b>Draw By </b> 
    <select id="drawingBy" class="hide-button">
        <option value="DEFAULT"></option>
        <option value="DRIVING">Driving</option> 
        <option value="LINEOFSITE">Line of Site</option> 
    </select> 
</button>
<c:if test="${writeFiber == 1}">
<button type="button" id="setCoordinateFiberAux" class="hide-button">Set Coordinate</button></c:if>

<button type="button" id="calculatedrivingdistance" class="hide-button">Calculate Driving Distance</button>

<button type="button" id="calculateGeodistance" class="hide-button">Calculate Geo Distance</button>
<c:if test="${writeFiber == 1}">
<button type="button" id="editManHand" class="hide-button">Grab Nearest Manhole and HandHole</button>
</c:if>
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
											<input type='checkbox' id="customer_RelatedCableAutoComplete" style='position: relative; margin-left:15px' class="relatedCableAutoComplete"></span>
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
											<input type='checkbox' id="customer_StrandOriginationAutoComplete" style='position: relative; margin-left: 25px' class="strandOriginationAutoComplete"></span>
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
		<div id="trenchModal" class="modal fade  custom-class-assignedto-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true"  data-keyboard="false" data-backdrop="static">
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
									<div class="container-fluid">
									<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
													<span style="width: 100px;" class="input-group-text"><b>Owner </b></span> <select id="trenchOwner" class="form-control">
															<option value="" selected></option><option value="tkl">TKL</option><option value="ogn">OGN</option><option value="nofbi">NOFBI</option><option value="others">Others</option></select>
							        </div></div></div></div></div>
							        <div class="container-fluid">
							        <div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px;"
														class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="trenchEngineerName" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> <input type="text" id="trenchInstaller" class="form-control text-input" />
												</div>
											</div>
										</div>
								</div>	
							  </div>
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
													<input type='checkbox' id="customer_TrenchAutoComplete" style='position: relative; margin-left:15px' class="srcDestTrenchAutoComplete"></span>
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
									<div class="container-fluid">
									<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<div class="input-group-prepend">
													<span style="width: 100px;" class="input-group-text"><b>Owner </b></span> <select id="ductOwner" class="form-control">
															<option value="" selected></option><option value="tkl">TKL</option><option value="ogn">OGN</option><option value="nofbi">NOFBI</option><option value="others">Others</option></select>
							        </div></div></div></div></div>
							        <div class="container-fluid">
							        <div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px;"
														class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="ductEngineerName" class="form-control text-input" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> <input type="text" id="ductInstaller" class="form-control text-input" />
												</div>
											</div>
										</div>
								</div>	
							  </div>
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
													<input type='checkbox' id="customer_DuctAutoComplete" style='position: relative; margin-left:15px' class="srcDestDuctAutoComplete"></span>
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
											<input type='checkbox' id="customer_TrenchOriginationAutoComplete" style='position: relative; margin-left: 25px' class="trenchOriginationAutoComplete"></span>
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
											<input type='checkbox' id="customer_DuctOriginationAutoComplete" style='position: relative; margin-left: 25px' class="ductOriginationAutoComplete"></span>
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
											<input type='checkbox' id="customer_TubeOriginationAutoComplete" style='position: relative; margin-left: 25px' class="tubeOriginationAutoComplete"></span>
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
											<input type='checkbox' id="customer_OriginationAutoComplete" style='position: relative; margin-left: 25px' class="OriginationAutoComplete"></span>
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
											<input type='checkbox' id="customer_TrenchTerminationAutoComplete" style='position: relative; margin-left: 25px' class="trenchTerminationAutoComplete"></span>
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
											<input type='checkbox' id="customer_DuctTerminationAutoComplete" style='position: relative; margin-left: 25px' class="ductTerminationAutoComplete"></span>
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
											<input type='checkbox' id="customer_TerminationAutoComplete" style='position: relative; margin-left: 25px' class="TerminationAutoComplete"></span>
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
											<input type='checkbox' id="customer_StrandTerminationAutoComplete" style='position: relative; margin-left: 25px' class="strandTerminationAutoComplete"></span>
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
											<input type='checkbox' id="customer_TubeTerminationAutoComplete" style='position: relative; margin-left: 25px' class="tubeTerminationAutoComplete"></span>
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
						
						<c:if test="${saveHandhole == 0}">
                      <button id="saveHandhole" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px; display:none;">Save</button>
							   </c:if>
                      <c:if test="${saveHandhole == 1}">
                   <button id="saveHandhole" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button>
							   </c:if>
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
											<input type="text"  id="HandholeName" class="form-control text-input" <c:if test="${writeHandhole == 0}">readonly</c:if> />
										</div></div></div></div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
											<input type="text" id="HandholeLong" class="form-control text-input" <c:if test="${writeHandhole == 0}">readonly</c:if> />
										</div></div></div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
											<input type="text" id="HandholeLat" class="form-control text-input" <c:if test="${writeHandhole == 0}">readonly</c:if> />
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
											<input type="text" id="HandholeModel" class="form-control text-input" <c:if test="${writeHandhole == 0}">readonly</c:if> />
										</div></div></div></div>
							<div class="row">
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span style="width: 140px; " class="input-group-text"><b>Creation Date</b></span>
									<input type="text" id="handholeCreateDate" class="form-control text-input" value="" readonly />
									</div></div></div>
								<div class="col-sm-6"><div class="form-group"><div class="input-group-prepend">
									<span class="input-group-text"  style="width: 160px;"><b>Last Modified Date</b></span>
									<input type="text" id="handholeLastModifiedDate" class="form-control text-input" value="" readonly />
								</div></div></div></div>
							<div class="row">
								<div class="col-md-6" id="HandholeDMDiv">
									<div class="form-group">
										<div class="input-group-prepend">
											<span style="width: 140px;" class="input-group-text"><b>DM_Name</b></span>
											<input type="text" id="HandholeDMName" class="form-control text-input" readonly />
										</div></div></div>
								<div class="col-sm-6">
										<div class="form-group">
										<div class="input-group-prepend">
    <span style="width: 100px;" class="input-group-text"><b>Owner </b></span> 
    <select id="handholeOwner" class="form-control" <c:if test="${writeHandhole == 0}"> disabled</c:if>>
        <option value="" selected></option>
        <option value="tkl">TKL</option>
        <option value="ogn">OGN</option>
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
												<span style="width: 120px;"	class="input-group-text"><b>Engineer Name </b></span> <input type="text" id="handholeEngineerName" class="form-control text-input" <c:if test="${writeHandhole == 0}">readonly</c:if> />
											</div>
										</div>
									</div>
									<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> <input type="text" id="handholeInstaller" class="form-control text-input" <c:if test="${writeHandhole == 0}">readonly</c:if> />
												</div>
							</div></div></div>	
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
						<c:if test="${saveDB == 1}">
							<button id="saveDistBoard" class="btn btn-save" style="color: black; font-weight:bold; margin-top:-6px;">Save</button></c:if>
							<button type="button" name="closePopup" class="close" onclick="ClosingConfirm()">
								<i class='fa fa-times'></i>
							</button>
							<a class="close modalMinimize ml-3"> <i class='fa fa-minus icon-to-change'></i></a></div></div>
					<div class="modal-body">
						<ul class="nav nav-tabs" id="myTabDb" role="tablist" style="background-color: #00757C;">
							<li class="nav-item"><a class="nav-link active" id="distBoard-tab" style="color: gold;" data-toggle="tab" href="#D_Board" role="tab" aria-controls="D_Board" aria-selected="true">Board </a></li>
							<li class="nav-item"><a class="nav-link " id="mapping-tab" style="color: gold;" data-toggle="tab" href="#mapping" role="tab" aria-controls="mapping" aria-selected="false">Mapping</a></li>
							<li class="nav-item"><a class="nav-link " id="mapping-tab" style="color: gold;" data-toggle="tab" href="#Kit" role="tab" aria-controls="Kit" aria-selected="false">Kit</a></li>
							<li class="nav-item"><a class="nav-link " id="mapping-tab" style="color: gold;" data-toggle="tab" href="#module" role="tab" aria-controls="Module" aria-selected="false">Module</a></li>
						</ul>
						<div class="tab-content"><p></p>
							<div class="tab-pane active" id="D_Board" role="tabpanel" aria-labelledby="distBoard-tab">
								<p></p>
								<div class="row">
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Site </b> 
													<input type='checkbox' id="site_DBAutoComplete" style='position: relative; margin-left: 25px'  <c:if test="${writeDB == 0}">disabled</c:if>></span>
												</div></div></div>
										<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 100px; font-size: 12px;" class="input-group-text"><b>By Client </b> 
													<input type='checkbox' id="customer_DBAutoComplete" style='position: relative; margin-left:15px' <c:if test="${writeDB == 0}">disabled</c:if>></span>
												</div></div></div>
												<div class="col-sm-2">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="min-width: 170px; font-size: 12px;" class="input-group-text"><b>Custom Coordinates </b> 
													<input type="checkbox" id="customCoordinates" value="0" style='position: relative; margin-left:5px ' <c:if test="${writeDB == 0}">disabled</c:if>></span>
												</div></div></div>
												
												
												<div class="col-sm-2">
												<div class="form-group">
												<div class="input-group-prepend">
												<c:if test="${writeDB == 1}">
													<button id="getDBCity" type="button" class="btn btn-primary" style="color: white; font-size: 13px; height: 40px; width: 100%; margin-left:25px" onclick="getDBCity()">Get City</button></c:if>
												</div></div></div>
												
												<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style=" width: 175px;" class="input-group-text"><b>Row Counting </b></span> 
														<select id="rowCounting" class="form-control" <c:if test="${writeDB == 0}">disabled</c:if>/>
															<option value="downToUp" selected >Down To Up</option>
															<option value="upToDown">Up To Down</option>
															
														</select>
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
												<input type="text" id="DistributionBoardName" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div></div>
								<div class="row">
									<div class="col-md-6" id="DBSite">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Site ID
												</b></span> <input type="text" id="DistributionBoardSite" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div>
									<div class="col-md-6" id="DBSiteName">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Site Name </b></span> 
												<input type="text" id="DistributionBoardSiteName" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div>
									<div class="col-md-6" id="DBClientId" style="display:none">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client ID
												</b></span>
												 <input type="text" id="DistributionBoardClient" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div>
									<div class="col-md-6" id="DBClientName" style="display:none">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client Name </b></span> 
												<input type="text" id="DistributionBoardClientName" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div></div>
								<div class="row">
									<div class="col-md-6" id="BDWarehouse">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Warehouse </b></span> 
												<input type="text" id="DistributionBoardWarehouse" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div>
                                     <div class="col-md-6" id="BDClientPhoneNb" style="display:none">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Client Phone# </b></span> 
												<input type="text" id="DistributionBoardClientPhoneNb" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Capacity
												</b></span> <input type="text" id="DistributionBoardCapacity" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div></div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Longitude</b></span>
												<input type="text" id="DistributionBoardLong" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Latitude</b></span>
												<input type="text" id="DistributionBoardLat" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div></div>			
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Rows Number </b></span>
												 <input type="text" id="DistributionBoardRowsNum" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div>
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Columns
														Number </b></span> <input type="text" id="DistributionBoardColsNum"
													class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
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
									</div></div></div>
								</div>
								<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 120px;"
														class="input-group-text"><b>Engineer Name </b></span> 
														<input type="text" id="DBEngineerName" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div></div></div>
										<div class="col-sm-6">
											<div class="form-group">
												<div class="input-group-prepend">
													<span style="width: 100px; " class="input-group-text"><b>Installer </b></span> 
													<input type="text" id="DBInstaller" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
									    </div></div></div>
								</div>	
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Creation Date </b></span>
												 <input type="text" id="boardCreationDate" class="form-control text-input" value="" readonly />
											</div></div></div>
											<div class="col-md-6" id="lastModifiedDateDB">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>Last Modified Date</b></span>
													<input type="text" id="boardLastModifiedDate" class="form-control text-input" value="" readonly />
										</div></div></div>
								</div>			
								<div class="row">
										<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>City </b></span>
												 <input type="text" id="boardCity" class="form-control text-input" readonly />
											</div></div></div>
										<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style=" width: 175px;" class="input-group-text"><b>DB Network Level </b></span> 
														<select id="DBnetlevel" class="form-control" <c:if test="${writeDB == 0}">disabled</c:if>/>
															<option value="backbone" selected >Backbone</option>
															<option value="metro">Metro</option>
															<option value="access">Access</option>
														</select>
								</div></div></div></div>
								<div class="row">
        							<div class="col-md-6" id="projectNameDB">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Project
														Name</b></span> <input type="text" id="DBProjectName"
													class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
											</div>
										</div>
									</div>
									<div class="col-md-6" id="projectIdDB">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Project ID</b></span>
													<input type="text" id="DBProjectId" class="form-control text-input" <c:if test="${writeDB == 0}">readonly</c:if>/>
										</div></div></div>
								</div>
								
								
								<div class="row">
        							<div class="col-md-6" id="distController">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Controller ID 
													</b></span> <input type="text" id="DBController"
													class="form-control text-input" />
											</div>
										</div>
									</div>
									<div class="col-md-6" id="distControllerName">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Controller Name</b></span>
													<input type="text" id="DBControllerName" class="form-control text-input" />
										</div></div></div>
								</div>
								
								
								
								
								
								
								
								
								<div class="row">
										<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 175px;" class="input-group-text"><b>DB Deployment Type  </b></span>
												 <select id="DBDeploymentType" class="form-control" <c:if test="${writeDB == 0}">disabled</c:if>/>
															<option value="wallMount" selected >Wall Mount</option>
															<option value=" floorMount"> Floor Mount</option>
															<option value="rackMount">Rack Mount</option>
														</select>
											</div></div></div>
										<div class="col-md-6">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style=" width: 175px;" class="input-group-text"><b>DB Adaptor Panel Type  </b></span> 
														<select id="DBAdaptorPanelType" class="form-control" <c:if test="${writeDB == 0}">disabled</c:if>/>
															<option value="SC" selected >SC</option>
															<option value="LC">LC</option>
															<option value="ST">ST</option>
															<option value="FC">FC</option>
															<option value="E2000">E2000</option>
															<option value="MTRJ">MTRJ</option>
														</select>
											</div></div></div>
									</div>
							
							<div class="row">
        						
									<div class="col-md-6" id="distSerialNum">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Serial Number</b></span>
													<input type="text" id="DBSerialNum" class="form-control text-input" />
										</div></div></div>
										
										<div class="col-md-6" id="distType">
										<div class="form-group">
											<div class="input-group-prepend">
												<span style="width: 140px;" class="input-group-text"><b>Type</b></span>
													<input type="text" id="DBType" class="form-control text-input" readonly/>
										</div></div></div>
								</div>
							</div>
							
							
							
									<div class="tab-pane" id="Kit" role="tabpanel"
								aria-labelledby="kit-tab">
								<p></p>
								
								<div class="container-fluid">
								
										
									<form>
										<div class="table-responsive-sm" id="DbKitDiv">
											<table id="DbKit"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 430px; overflow: auto; width:900px; margin-right:20px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														<th style=" text-align: center" >
															<button  type="button" id="selectAll_Kit" onclick="selectAllKit(this)" >
																<span class="sub"></span>Select
															</button>
														</th>
														
													
													<th width="415px" style=" text-align: center" >Serial Number
												</th>
														<th width="415px" style=" text-align: center" >Type</th>
															</tr>
												</thead>
												<tbody>
   
  </tbody>
											</table>
									
							
										</div>
											<button type="button" id="add_kit" onclick="addKit()">Add Row </button>
										        <button type="button" id="delet_kit" onclick="deleteKit()">Delete Row</button>
							
								
									</form></div></div>
							
							
							
								<div class="tab-pane" id="module" role="tabpanel"
								aria-labelledby="module-tab">
								<p></p>
								<div class="container-fluid">
										<form>
										<div class="table-responsive-sm" id="DbModuleDiv">
											<table id="DbModule"
												class="table table-striped table-bordered table-sm"
												style="display: block; height: 430px; overflow: auto; width:900px; margin-right:20px">
												<thead style="background: #E9ECEF;">
													<tr class="fixed-headerr">
														<th style=" text-align: center" >
															<button  type="button" id="selectAll_module" onclick="selectAllModule(this)" >
																<span class="sub"></span>Select
															</button>
														</th>
														
													
													<th width="118px" style=" text-align: center" >Module Position</th>
													<th width="118px" style=" text-align: center" >Kit Serial # </th>
													<th width="118px" style=" text-align: center" >Orientation</th>
													<th width="118px" style=" text-align: center" >Lowest Port #</th>
												    <th width="120px" style=" text-align: center" >Sensors Per Port #</th>
													<th width="118px" style=" text-align: center" >Sensor Count</th>
													<th width="120px" style=" text-align: center" >Occupied Sensor Mask </th>
															</tr>
												</thead>
												<tbody>
												</tbody>
											</table>
												<button type="button" id="add_module" onclick="addModule()">Add Row </button>
										        <button type="button" id="delet_module" onclick="deleteModule()">Delete Row</button>
							
										</div>
								
									</form></div></div>
							
							
							
							
							
							
							
							
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
												style="display: block; height: 450px; overflow-y: auto;">
												<thead style="background: #E9ECEF;">
													<tr>
														<th></th>
														<th colspan="6">Near Port</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="25">Far Near Port</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th colspan="24">Back Port</th>
													</tr>
													<tr>
														<th>
															<button type="button" id="selectAll_DbMapRows"
																class="main">
																<span class="sub"></span>Select
															</button>
														</th>
													<th style="min-width: 80px">Index</th>
													<th style="min-width: 90px">Module</th>
													<th style="min-width: 90px">Port #</th>
														<th style="min-width: 80px">Row #</th>
														<th style="min-width: 80px">Column #</th>
														<th style="min-width: 90px">Patch Type</th>
														<th style="background-color: #00757C" width="-10px"></th>
														<th style="min-width: 190px">Front Status</th>
														<th style="min-width: 190px">Location Type</th>
														<th style="min-width: 190px">Location ID</th>
														<th style="min-width: 190px">Location Name</th>
														<th style="min-width: 190px">Warehouse ID</th>
														<th style="min-width: 190px">Equipment </th>
														<th style="min-width: 190px">Equipment ID</th>
														<th style="min-width: 190px">Equipment Name</th>
														<th style="min-width: 190px">Equipment Type</th>
														<th style="min-width: 190px">Kit Serial Num </th>
														<th style="min-width: 190px">Module</th>
													    <th style="min-width: 190px">Port #</th>
													
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
														<th style="min-width: 190px">Equipment ID</th>
														<th style="min-width: 190px">Equipment Name</th>
														<th style="min-width: 190px">Equipment Type</th>
														<th style="min-width: 190px">Kit Serial #</th>
														<th style="min-width: 190px">Port #</th>
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
										<c:if test="${writeDB == 1}">
										<button type="button" id="add_RackRow">Add Row</button>
										<button type="button" id="delete_RackRow">Delete Row</button>
										<button type="button" id="sortByIndex">Sort By Index</button>
										<button type="button" id="assign_Cable">Assign Cable</button>
										<input name="fiber_Cable" id="BP_assignCable" class="form-control text-input" type="text" style="width:150px;display: inline-block;" <c:if test="${writeDB == 0}">readonly</c:if>/>
										<button type="button" id="assign_Tube">Assign Tube</button>
										<input name="fiber_Tube" id="BP_assignTube" class="form-control text-input" type="text" style="width:170px;display: inline-block;" <c:if test="${writeDB == 0}">readonly</c:if>/>
										<select id="selected_Port" aria-label="Default select example" class="form-select" style="height:35px; " <c:if test="${writeDB == 0}">disabled</c:if>/>
										  <option value="title"  selected>Front/back</option>
										  <option value="frontPort">Front Port</option>
										  <option value="backPort">Back Port</option>
										</select></c:if>
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
												<c:if test="${writeDB == 1}">
													<div class="input-group-prepend"><button id="uploadDB" class="btn btn-primary" style=" margin-top:10px;">Import</button></div></c:if>
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


let fiberList = [];
let auxDat = [];
let auxGrouped = [];
let geoDistances = [];
let dict = []; // global like your reference code



function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function loadFiberList() {
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
	  
    $.ajax({
        url: "${pageContext.request.contextPath}/getFiberScript",
        type: "GET",
        success: async function(response) {
            fiberList = response.fiberList || [];
            auxDat = response.auxDat || [];

            console.log("FiberList loaded:", fiberList);
            console.log("Raw Aux Data loaded:", auxDat);

            // ---- Group auxiliary data by fiber cable ----
            let auxMap = {};
            auxDat.forEach(row => {
                let fiberId = row[0];
                let lng = parseFloat(row[1]);
                let lat = parseFloat(row[2]);
                let seq = parseInt(row[3]);

                if (!auxMap[fiberId]) auxMap[fiberId] = [];
                auxMap[fiberId].push({
                    aux_Longitude: lng,
                    aux_Latitude: lat,
                    aux_seqSorting: seq
                });
            });

            auxGrouped = Object.keys(auxMap).map(fiberId => ({
                fiberId,
                auxPoints: auxMap[fiberId]
            }));

            // ---- Loop all fibers with delay ----
            for (let i = 0; i < fiberList.length; i++) {
                const fiber = fiberList[i];
                const fiberId = fiber[0];
                const sourceLng = parseFloat(fiber[1]);
                const sourceLat = parseFloat(fiber[2]);
                const destinationLng = parseFloat(fiber[3]);
                const destinationLat = parseFloat(fiber[4]);

                getSelectedFiberAux(fiberId);
                const auxPoints = dict.length ? dict : [];

                calculateGeoDistance(
                    fiberId,
                    auxPoints,
                    sourceLng,
                    sourceLat,
                    destinationLng,
                    destinationLat
                );

                console.log(`Fiber ${fiberId} processed.`);
                await delay(20);
            }

            console.log("All fibers processed. GeoDistances:", geoDistances);

            // ---- Send geoDistances to server ----
            $.ajax({
                url: "${pageContext.request.contextPath}/updateGeoDistances",
                type: "POST",
                data: { geoDistances: JSON.stringify(geoDistances) },
                success: function(response) {
                    console.log("Server Response:", response);
                	$("#loading").remove();		
    				
                },
                error: function(err) {
                    console.error("Error sending geoDistances:", err);
                }
            });
        },
        error: function(err) {
            console.error("Error loading fiber list:", err);
        }
    });
}

// fill global dict
function getSelectedFiberAux(fiberId) {
    dict = [];
    const auxEntry = auxGrouped.find(a => a.fiberId == fiberId);
    if (auxEntry) {
        auxEntry.auxPoints.forEach(aux => {
            dict.push({
                aux_Name: "Aux_Point " + aux.aux_seqSorting,
                aux_Longitude: aux.aux_Longitude,
                aux_Latitude: aux.aux_Latitude,
                aux_seqSorting: aux.aux_seqSorting
            });
        });
    }
}

function calculateGeoDistance(fiberId, aux, sourceLng, sourceLat, destinationLng, destinationLat) {
    const allAuxData = aux || [];
    let totalGeoDistance = 0.0;

    // Ensure floats
    sourceLat = parseFloat(sourceLat);
    sourceLng = parseFloat(sourceLng);
    destinationLat = parseFloat(destinationLat);
    destinationLng = parseFloat(destinationLng);

  
    if (allAuxData.length > 0) {
        for (let h = 0; h < allAuxData.length + 1; h++) {
            let latitudeSrc = (h === 0) ? sourceLat : parseFloat(allAuxData[h - 1].aux_Latitude);
            let longitudeSrc = (h === 0) ? sourceLng : parseFloat(allAuxData[h - 1].aux_Longitude);
            let latitudeDst = (h === allAuxData.length) ? destinationLat : parseFloat(allAuxData[h].aux_Latitude);
            let longitudeDst = (h === allAuxData.length) ? destinationLng : parseFloat(allAuxData[h].aux_Longitude);

          
            if (!isNaN(latitudeSrc) && !isNaN(longitudeSrc) && !isNaN(latitudeDst) && !isNaN(longitudeDst)) {
                const segmentDistance = google.maps.geometry.spherical
                    .computeDistanceBetween(
                        new google.maps.LatLng(latitudeSrc, longitudeSrc),
                        new google.maps.LatLng(latitudeDst, longitudeDst)
                    ) / 1000;

                      totalGeoDistance += segmentDistance;
            } else {
                console.warn(`⚠️ Skipped invalid coords for fiber ${fiberId} at segment ${h}`);
            }
        }
    } else {
        // No aux points, direct link
        if (!isNaN(sourceLat) && !isNaN(sourceLng) && !isNaN(destinationLat) && !isNaN(destinationLng)) {
            totalGeoDistance = google.maps.geometry.spherical
                .computeDistanceBetween(
                    new google.maps.LatLng(sourceLat, sourceLng),
                    new google.maps.LatLng(destinationLat, destinationLng)
                ) / 1000;
         
        } else {
            console.warn(`Invalid main coordinates for fiber ${fiberId}`);
        }
    }

    geoDistances.push({
        fiberId: fiberId,
        distance: parseFloat(totalGeoDistance.toFixed(3))
    });

    
}

 function updateLineOfSites() {


	 $('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
	  
	    $.ajax({
	        url: "${pageContext.request.contextPath}/updateLineOfSites",
	        type: "GET",
	        success: async function(response) {

	   	     if(response){

	   	        console.log(response);



	   	     $("#loading").remove();		
		   	     }
	           
	        },
	        error: function(err) {
	            console.error("Error loading fiber list:", err);
	        }
	    });



	 }

async function updateCities(item) {
	  $('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>');
	  
	  if (item === "fiber") {
	    try {
	      // Fetch fiber list
	      const response = await $.ajax({
	        url: `${pageContext.request.contextPath}/getFiberInfoCity`,
	        type: "GET"
	      });

	      // Check if response contains fiber list
	      if (response && response.fiberList) {
	        const fiberList = response.fiberList;
	        const geocoder = new google.maps.Geocoder();
	        const fiberCityList = [];

	        // Loop through each fiber and fetch city data
	        for (let i = 0; i < fiberList.length; i++) {
	          const fiber = fiberList[i];
	          const fiberId = fiber[0];
	          const srcLng = parseFloat(fiber[1]);
	          const srcLat = parseFloat(fiber[2]);
	          const destLng = parseFloat(fiber[3]);
	          const destLat = parseFloat(fiber[4]);

	          // Geocode for source and destination cities (await)
	          const srcCity = await getCityFromCoords(srcLat, srcLng, geocoder);
	          const destCity = await getCityFromCoords(destLat, destLng, geocoder);

	          // Add fiber city data to list
	          fiberCityList.push({
	            fiberId: fiberId,
	            srcCity: srcCity,
	            destCity: destCity
	          });

	       console.log(fiberId +" --> srcCity: " + srcCity +" and distCity: " + destCity);
	           // Add delay between requests to prevent throttling
	          
	        }

	        console.log("All fibers processed. City list:", fiberCityList);

	         $.ajax({
	          url: `${pageContext.request.contextPath}/saveFiberCities`, // POST to server
	          type: "POST",
	          data: { fiberCityList: JSON.stringify(fiberCityList) },
	          success: function(response) {
	            console.log("Server Response:", response);
	            $("#loading").remove();  // Remove loading screen after processing
	          },
	          error: function(err) {
	            console.error("Error sending fiber city data:", err);
	          }
	        });

	      } else {
	        console.warn("No fiber data found.");
	        $("#loading").remove();  // Remove loading screen if no data
	      }
	    } catch (err) {
	      console.error("Error loading fiber list:", err);
	      $("#loading").remove();  // Remove loading screen on error
	    }
	  }

	  else if (item === "manhole"){
		  try {
		      // Fetch fiber list
		      const response = await $.ajax({
		        url: `${pageContext.request.contextPath}/getManholeInfoCity`,
		        type: "GET"
		      });

		      // Check if response contains fiber list
		      if (response && response.manholeList) {
		        const manholeList = response.manholeList;
		        const geocoder = new google.maps.Geocoder();
		        const manholeCityList = [];

		        // Loop through each fiber and fetch city data
		        for (let i = 0; i < manholeList.length; i++) {
		          const manhole = manholeList[i];
		          const manholeId = manhole[0];
		          const lng = parseFloat(manhole[1]);
		          const lat = parseFloat(manhole[2]);
		          
		          // Geocode for source and destination cities (await)
		          const city = await getCityFromCoords(lat, lng, geocoder);
		       
		          // Add fiber city data to list
		          manholeCityList.push({
		        	  manholeId: manholeId,
		        	  city: city
		           
		          });

		       console.log(manholeId +" --> City: " + city);
		           // Add delay between requests to prevent throttling
		          
		        }

		        console.log("All manholes processed. City list:", manholeCityList);

		         $.ajax({
		          url: `${pageContext.request.contextPath}/saveManholeCities`, // POST to server
		          type: "POST",
		          data: { manholeCityList: JSON.stringify(manholeCityList) },
		          success: function(response) {
		            console.log("Server Response:", response);
		            $("#loading").remove();  // Remove loading screen after processing
		          },
		          error: function(err) {
		            console.error("Error sending fiber city data:", err);
		          }
		        });

		      } else {
		         $("#loading").remove();  // Remove loading screen if no data
		      }
		    } catch (err) {
		      console.error("Error loading  list:", err);
		      $("#loading").remove();  // Remove loading screen on error
		    }

		  }

	  else if (item === "handhole"){
		  try {
		      // Fetch fiber list
		
		      const response = await $.ajax({
		        url: `${pageContext.request.contextPath}/getHandholeInfoCity`,
		        type: "GET"
		      });

		      // Check if response contains fiber list
		      if (response && response.handholeList) {
			   
		        const handholeList = response.handholeList;
		        const geocoder = new google.maps.Geocoder();
		        const handholeCityList = [];

		        // Loop through each fiber and fetch city data
		        for (let i = 0; i < handholeList.length; i++) {
		          const handhole = handholeList[i];
		          const handholeId = handhole[0];
		          const lng = parseFloat(handhole[1]);
		          const lat = parseFloat(handhole[2]);
		          
		          // Geocode for source and destination cities (await)
		          const city = await getCityFromCoords(lat, lng, geocoder);
		       
		          // Add fiber city data to list
		          handholeCityList.push({
		        	  handholeId: handholeId,
		        	  city: city
		           
		          });

		       console.log(handholeId +" --> City: " + city);
		           // Add delay between requests to prevent throttling
		          
		        }

		        console.log("All handholes processed. City list:", handholeCityList);

		         $.ajax({
		          url: `${pageContext.request.contextPath}/saveHandholeCities`, // POST to server
		          type: "POST",
		          data: { handholeCityList: JSON.stringify(handholeCityList) },
		          success: function(response) {
		            console.log("Server Response:", response);
		            $("#loading").remove();  // Remove loading screen after processing
		          },
		          error: function(err) {
		            console.error("Error sending fiber city data:", err);
		          }
		        });

		      } else {
		         $("#loading").remove();  // Remove loading screen if no data
		      }
		    } catch (err) {
		      console.error("Error loading  list:", err);
		      $("#loading").remove();  // Remove loading screen on error
		    }

		  }
	}

	// Function to get city name from geocode results
	function getCityFromCoords(lat, lng, geocoder) {
	  return new Promise((resolve) => {
	    const latlng = new google.maps.LatLng(lat, lng);

	    geocoder.geocode({ latLng: latlng }, function(results, status) {
	      if (status === google.maps.GeocoderStatus.OK && results[0]) {
	        let city = "";

	        // Look for "locality" type in address components
	        for (let i = 0; i < results[0].address_components.length; i++) {
	          const comp = results[0].address_components[i];
	          if (comp.types.includes("locality")) {
	            city = comp.long_name;
	            break;
	          }
	        }

	        // Fallback to first part of formatted address if locality not found
	        if (!city) {
	          city = results[0].formatted_address.split(",")[0];
	        }

	        resolve(city);
	      } else {
	        console.warn("Geocoder failed for", lat, lng, "→", status);
	        resolve("");  // Resolve with empty string if geocoding fails
	      }
	    });
	  });
	}




// Parse them into JavaScript objects
var circleDraw = '${circleDraw}';
var squareDraw= '${squareDraw}';
var locationNum= '${locationNumber}';
var LastlocationNumber= '${LastlocationNumber}';
var rowMultyIndex='${rowMultyIndex}';
var selectedControllerId = "";
var selectedControllerName = "";
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
slctDelJct =[];
var MJctBoqIndex=0;
var HJctBoqIndex=0;
var JctBoqIndex=0;
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
var manholeSurveyArray=[];
var handholeSurveyArray=[];
var dbSurveyArray=[];
var nodeSurveyArray=[];var fiberCableSurveyArray=[];var fiberTubesSurveyArray=[];var fiberStrandsSurveyArray=[];
var manholeJctAttachmentIndex=0,handholeJctAttachmentIndex=0,projectAttachmentIndex=0;
var deletedKitIds= [];
var deletedModuleIds= [];

updateModfUser=`${userFullName}`;
var searchPopupPerm = '${searchPopup}';
var findConnedtedPerm='${findConnedted}';
var projects='${projects}';
var readManhole='${readManhole}';
var writeManhole='${writeManhole}';
var addManhole='${addManhole}';
var delManhole='${delManhole}';
var readHandhole='${readHandhole}';
var writeHandhole='${writeHandhole}';
var addHandhole='${addHandhole}';
var delHandhole='${delHandhole}';
var readFiber='${readFiber}';
var writeFiber='${writeFiber}';
var addFiber='${addFiber}';
var delFiber='${delFiber}';
var readDB='${readDB}';
var writeDB='${writeDB}';
var addDB='${addDB}';
var delDB='${delDB}';

var onlyReadManExcep= '${onlyReadManExcep}'

	var exceptionManWriteList= '${exceptionManWriteList1}';
		if(exceptionManWriteList){
	 exceptionManWriteList = JSON.parse(exceptionManWriteList);

	}
		console.log(exceptionManWriteList);
	var readExceptionMan='${readExceptionMan}';
	var treeExceptionMan='${treeExceptionMan}';
	var writeExceptionMan='${writeExceptionMan}';
	console.log(writeExceptionMan);
	var onlyReadHandExcep= '${onlyReadHandExcep}';
		var exceptionHandWriteList= '${exceptionHandWriteList1}';
		if(exceptionHandWriteList){
		 exceptionHandWriteList = JSON.parse(exceptionHandWriteList);

		}
		
		var readExceptionHand='${readExceptionHand}';
		var treeExceptionHand='${treeExceptionHand}';
		var writeExceptionHand= '${writeExceptionHand}';
		

	
		var onlyReadDBExcep= '${onlyReadDBExcep}';
			
			var exceptionDBWriteList= '${exceptionDBWriteList1}';
			if(exceptionDBWriteList){
			 exceptionDBWriteList = JSON.parse(exceptionDBWriteList);
			}
			var readExceptionDB='${readExceptionDB}';
			var treeExceptionDB='${TtreeExceptionDB}';
			var writeExceptionDB= '${writeExceptionDB}';

			
			var onlyReadFiberExcep= '${onlyReadFiberExcep}';
				
				var exceptionFiberWriteList= '${exceptionFiberWriteList1}';
				if(exceptionFiberWriteList){
				 exceptionFiberWriteList = JSON.parse(exceptionFiberWriteList);
				}
				var readExceptionFiber='${readExceptionFiber}';
				var writeExceptionFiber= '${writeExceptionFiber}';
			
document.addEventListener('DOMContentLoaded', function() {
    var elements = {
        fibersearchtab: document.getElementById('fiber-search-tab'),
        customTabsFilterTab: document.getElementById('custom-tabs-filter-tab'),
        closestSearchTab: document.getElementById('closest-search-tab'),
        MultyClosestSearchTab: document.getElementById('MultyClosest-search-tab'),
        connectedSearchtab: document.getElementById('connectedSearch-tab'),
        projects: document.getElementById('initial_ul_Projects')
    };

    function toggleElementDisplay(element, permission) {
        if (element) { // Check if element exists before accessing its style
            if (permission === '1') {
                element.style.display = 'block';
            } else {
                element.style.display = 'none';
            }
        }
    }

    toggleElementDisplay(elements.fibersearchtab, searchPopupPerm);
    toggleElementDisplay(elements.customTabsFilterTab, searchPopupPerm);
    toggleElementDisplay(elements.closestSearchTab, searchPopupPerm);
    toggleElementDisplay(elements.MultyClosestSearchTab, searchPopupPerm);
    
  
    
    toggleElementDisplay(elements.connectedSearchtab, findConnedtedPerm);
});




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
		//center: { lat: 1, lng: 38 },
		center: { lat: -19.2370074705615, lng: 29.7948325794125 },		
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
	
	restingMap();
	var x = document.getElementById("removeFilter");								 
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
	  } else if(Object.keys(${physicalLayerList})[i].includes("_")){
	  	let text = Object.keys(${physicalLayerList})[i];
		let result = text.replace("_", " ");
		result = result.charAt(0).toUpperCase() + result.slice(1);
		str="<div class='rowhashMapList' style='margin-left:-15px;' id='hashMapList'><div class='col-md-6' ><div class='input-group-prepend'><span style='font-size: 14px;width:200px;'class='input-group-text' ><b>"+result+"</b></span><input	type='text' name='filteredField' id='Filtered"+Object.keys(${physicalLayerList})[i]+"'class='form-control text-input' placeholder='"+result+"'/></div></div></div><p></p><p></p>";
		}else{
			let	result = (Object.keys(${physicalLayerList})[i]).charAt(0).toUpperCase() + (Object.keys(${physicalLayerList})[i]).slice(1);
			str="<div class='rowhashMapList' style='margin-left:-15px;' id='hashMapList'><div class='col-md-6'><div class='input-group-prepend'><span style='font-size: 14px;width:200px;'class='input-group-text' ><b>"+result+"</b></span><input	type='text' name='filteredField' id='Filtered"+Object.keys(${physicalLayerList})[i]+"'class='form-control text-input' placeholder='"+result+"'/></div></div></div><p></p><p></p>";
		}
		$("#filterSection").append(str);
	}	physicalLayerFilter(); // to build click event on Filter Submit.
	    CreateTree_PhysicalLayer(${physicalLayerList}['Project'],${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['fiber'],${physicalLayerList}['Distribution_Board'],${physicalLayerList}['controllerList'],${physicalLayerData}['fiber_Tubes'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Auxiliary'],${physicalLayerData}['tubes_Auxiliaries'],${physicalLayerData}['strands_Auxiliaries'],${physicalLayerList}['Trench'],${physicalLayerData}['trench_Auxiliary'],${physicalLayerList}['Junction_Manhole'],${physicalLayerList}['Junction_Handhole'],filterFlag,${physicalLayerList}['duct'],${physicalLayerData}['ductAuxiliary'],${physicalLayerList}['Node']);
		CreateMap_PhysicalLayer(${physicalLayerList}['Project'],${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['fiber'],${physicalLayerList}['Distribution_Board'],${physicalLayerData}['fiber_Tubes'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Auxiliary'],${physicalLayerData}['tubes_Auxiliaries'],${physicalLayerData}['strands_Auxiliaries'],${physicalLayerList}['Trench'],${physicalLayerData}['trench_Auxiliary'],${physicalLayerList}['Node']); 
		if(checkedOption == "circleRange"){
			openFindNearest(checkedOption,'${closestLatPoint}','${closestLongPoint}','${closestDisRange}','${noP}',${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['Distribution_Board'],${physicalLayerList}['controllerList'],${physicalLayerList}['fiber'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Tubes'],${physicalLayerList}['Node'],'${getRelatedPoints}','${startLng}','${endLng}','${startLat}','${endLat}','${CustomerID}','${CustomerName}', '${serviceReq}','${serviceRef}');
		}else if(checkedOption == "StartEnd"){
			openFindBetweenMarkers(checkedOption,'${startLongPoint}','${startLatPoint}','${endLongPoint}','${endLatPoint}',${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['Distribution_Board'],${physicalLayerList}['fiber'],${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Tubes'],${physicalLayerList}['Node'],'${getRelatedPoints}');
		}else if(checkedOption == "circleRange_multy"){
			openFindNearestMultySite(checkedOption,'${rowData}','${noOfPoints}','${closestDisRange}',`${ptList}`,'${ptData}','${getRelatedPoints}' , '${borderCircleLatitudes}' , '${borderCircleLongitudes}', circleDraw, squareDraw,locationNum, '${rowMultyIndex}');
		}else if(checkedOption == "connected"){
			openSearchConnected(checkedOption,'${siteId}','${selectConnectedSearch}','${connectedSearchLong}','${connectedSearchLat}','${connectedViewOnMap}',${physicalLayerData}['fiber_Strands'],${physicalLayerData}['fiber_Tubes'],${physicalLayerList}['fiber'],${physicalLayerList}['Manhole'],${physicalLayerList}['Handhole'],${physicalLayerList}['Distribution_Board'],${physicalLayerList}['controllerList'],'${distribBoardListSize}','${getRelatedPoints}', '${fpPath}','${bpPath}', ${physicalLayerList}['Node']);
		}		  
			  
		$(document).ready(function () { 
			$(function(){			 
			 $(document).trigger("triggerListenersEvent");
			});
		});

} /// End of init Map

window.onload = function () {makeAllSortable();
if(checkedOption == "circleRange"){ 
	calculateGeoDistanceNearestPoints("findNearstHandhole",handholeSurveyArray);
	calculateGeoDistanceNearestPoints("findNearstManhole",manholeSurveyArray);//call the calculateGeoDistance function here after creating the map
	calculateGeoDistanceNearestPoints("findNearstDB",dbSurveyArray);
	calculateGeoDistanceNearestPoints("findNearstNode",nodeSurveyArray);
	getAllSurveyArrays("nearFiberId",fiberCableSurveyArray);
	getAllSurveyArrays("nearTubeId",fiberTubesSurveyArray);
	getAllSurveyArrays("nearStrandId",fiberStrandsSurveyArray);
}
};	
document.addEventListener("DOMContentLoaded", function() {
    var readDB = ${readDB}; // or fetch this value from the server if necessary
    if (readDB == 1) {
        document.getElementById("dbSection").style.display = "block";
    }
    var readManhole = ${readManhole}; // or fetch this value from the server if necessary
    if (readManhole == 1) {
        document.getElementById("manholeSection").style.display = "block";
    }
    var readHandhole = ${readHandhole}; // Fetch this value from the server if necessary
    if (readHandhole == 1) {
        document.getElementById("handholeSection").style.display = "block";
    }
});


var actiondistControllerContext = ""; 
var dbContNtLevel= "";

$("#saveController").click(function () {

    // Validate longitude
    if (isNaN(document.getElementById("ControllerLong").value)) {
        alert("Incorrect Format of longitude.");
        return false;
    }

    // Validate latitude
    else if (isNaN(document.getElementById("ControllerLat").value)) {
        alert("Incorrect Format of latitude.");
        return false;
    }

    else if(document.getElementById("controllerCity").value == "" ){
		alert("City cannot be empty. ");
		return false;
    }
    ////
    else if(document.getElementById("ControllerSite").value == "" && document.getElementById('site_ControllerAutoComplete').checked){
		alert("SiteID cannot be empty. ");
		return false;
    }
	else if(document.getElementById("ControllerSiteName").value == "" && document.getElementById('site_ControllerAutoComplete').checked){
		alert("Site Name cannot be empty. ");
		return false;
    }
	else if(document.getElementById("ControllerWarehouse").value == "" && document.getElementById('site_ControllerAutoComplete').checked){
		alert("WarehouseID cannot be empty. ");
		return false;
    }
	
	else if(document.getElementById("ControllerClient").value == "" && document.getElementById('customer_ControllerAutoComplete').checked){
		alert("ClientID cannot be empty. ");
		return false;
    }
	else if(document.getElementById("ControllerClientName").value == "" && document.getElementById('customer_ControllerAutoComplete').checked){
		alert("Client Name cannot be empty. ");
		return false;
    }
	else if(document.getElementById("ControllerClientPhoneNb").value == "" && document.getElementById('customer_ControllerAutoComplete').checked){
		alert("Phone Number cannot be empty. ");
		return false;
    }

    // Check that both longitude and latitude are not empty
    else if (document.getElementById("ControllerLong").value !== "" &&
             document.getElementById("ControllerLat").value !== "") {

        let controllerId = document.getElementById("controllerId").value;
        let controllerName = document.getElementById("ControllerName").value;
        let longitude = document.getElementById("ControllerLong").value;
        let latitude = document.getElementById("ControllerLat").value;
        let serialNumber = document.getElementById("serialNumb").value;
        let macAddress = document.getElementById("macAddress").value;
        let createDate = document.getElementById("controllerCreateDate").value;
        let lastModifiedDate = document.getElementById("controllerLastModifiedDate").value;
        let ipAddress = document.getElementById("ipAddress").value;
        let subnetMask = document.getElementById("subnetMask").value;
        let gateway = document.getElementById("gateWay").value;
        let userName = document.getElementById("userName").value;
        let password = document.getElementById("password").value;
        let numberOfPanels = document.getElementById("numbOfPanels").value;
        let numberOfPorts = document.getElementById("numbOfPorts").value;
        let networkLayer = document.getElementById("networkLayer").value;
    	let ControllerSite = document.getElementById("ControllerSite").value;
		let ControllerSiteName = document.getElementById("ControllerSiteName").value;
		let ControllerWarehouse = document.getElementById("ControllerWarehouse").value;
		let ControllerClient = document.getElementById("ControllerClient").value;
		let ControllerClientName = document.getElementById("ControllerClientName").value;
		let ControllerClientPhoneNb = document.getElementById("ControllerClientPhoneNb").value;
		let ControllerCity = document.getElementById("controllerCity").value;
		
		var locationId =""; 
		var locationName = ""; 
		var location = "";
		var isSiteChecked="";
		var isClientChecked="";
		
		if (document.getElementById('site_ControllerAutoComplete').checked) {
			isSiteChecked=true;
		}
		
		if (document.getElementById('customer_ControllerAutoComplete').checked) {
			isClientChecked=true;
		}
		if(isSiteChecked == true){
			locationId = ControllerSite;
			locationName = ControllerSiteName;
			location = ControllerWarehouse;
		}else{
			locationId = ControllerClient;
			locationName = ControllerClientName;
			location = ControllerClientPhoneNb;
		}
		isSiteChecked = false;
		isClientChecked = false;
	
    	var token =  $('input[name="csrfToken"]').attr('value');
		
		
        $.ajax({
            type: "POST",
        	headers: {
				'X-CSRFToken': token 
			},
            url: getContext() + '/saveController',
            data: {
                "controllerId": controllerId,
                "controllerName": controllerName,
                "serialNumber": serialNumber,
                "macAddress": macAddress,
                "ipAddress": ipAddress,
                "subnetMask": subnetMask,
                "gateWay": gateway,
                "ControllerSite"  	:locationId,
				"ControllerSiteName" :locationName,
				"ControllerWarehouse" :location,
                "userName": userName,
                "password": password,
                "numberOfPanels": numberOfPanels,
                "numberOfPorts": numberOfPorts,
                "networkLayer": networkLayer,
                "longitude": longitude,
                "latitude": latitude,
                "city": ControllerCity,
                "createDate": createDate,
                "actiondistControllerContext": actiondistControllerContext,
                "updateModfUser": updateModfUser
            },
            dataType: "json",
            success: function (data) {
                if (data != null) {
           
                    if (data.actiondistControllerContext == "Insert") {
                        allCont.push(data.controllerId);
                		window[""+data.controllerId]=[];
                		window[""+data.controllerId]=[data.controllerId, data.lng, data.lat, data.controllerName, data.networkLayer]
                	
                    	if (networkLayer == "backbone") {
            				
            				
                    		str="<ul><li id='"+data.controllerId+"'  class='bController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";								
								$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
            					
            				$(".bController > .TreeSpan").contextmenu(function (event) {
            				         selectedControllerId = $(this).parent().attr('id');
            						 console.log(selectedControllerId);
            				         selectedControllerName = $(this).text();
            				        openContext(selectedControllerId, selectedControllerName, singleController, event);
            				    });
            			
            			}	
                    	else  	if (networkLayer == "metro") {
            				
            				
                    		str="<ul><li id='"+data.controllerId+"'  class='mController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";								
							$("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
        						
            				$(".mController > .TreeSpan").contextmenu(function (event) {
            				         selectedControllerId = $(this).parent().attr('id');
            						 console.log(selectedControllerId);
            				         selectedControllerName = $(this).text();
            				        openContext(selectedControllerId, selectedControllerName, singleController, event);
            				    });
            			
            			}
                    	else  	if (networkLayer == "access") {
            				
            				
                    		str="<ul><li id='"+data.controllerId+"'  class='aController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";								
							$("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
        					
            				$(".aController > .TreeSpan").contextmenu(function (event) {
            				         selectedControllerId = $(this).parent().attr('id');
            						 console.log(selectedControllerId);
            				         selectedControllerName = $(this).text();
            				        openContext(selectedControllerId, selectedControllerName, singleController, event);
            				    });
            				//treeCollapseFolder("#DistributionBoard_accessController__CurrentPhysicalLayer" ,".folder","fast",".folder");
                				    

            			}
                		createControllerMarkerClick(
    							data.controllerId,
    							data.controllerName,
    						    data.lng, 
    						    data.lat, 
    						    markersController,
    					        markerClusterController
    							
    						);
    					controllerCheckFilter(
    							data.controllerId,
    							markerClusterController
    							); 
    				
    					markerClusterController.addMarker(markersController[data.controllerId]);
    			    
            					
                    }
                    // Handle Update
                    else if (actiondistControllerContext == "Update") {
                    	
                    	window[""+data.controllerId]=[];
                		window[""+data.controllerId]=[data.controllerId, data.lng, data.lat, data.controllerName, data.networkLayer]
                		 var allLis = "";

                	     // loop over your list and collect all <li> from each target
                	   
                	       	if (networkLayer != dbContNtLevel){
                    	       	if(data.DBList){
                	            var distribBoardList=data.DBList;
    	                       	for(i=0;i<distribBoardList.length;i++){
    	                    		
    	                       		$("#"+distribBoardList[i][0]).remove();
    	                    			
    	                    			if (data.oldNetworkLevel == "backbone") {
    	                    			    console.log("deleting backbone:", distribBoardList[i][0]);

    	                    			    if (markersDistBoard[distribBoardList[i][0]]) {
    	                    			        let marker = markersDistBoard[distribBoardList[i][0]];
    	                    			        markerClusterBackboneDistBoard.removeMarker(marker);
    	                    			        marker.setMap(null);
    	                    			        delete markersDistBoard[distribBoardList[i][0]];
    	                    			        markerClusterBackboneDistBoard.repaint(); // ð¥ force refresh
    	                    			    }
    	                    			}
    	                    			else if (data.oldNetworkLevel == "metro") {
    	                    			    console.log("deleting metro:", distribBoardList[i][0]);

    	                    			    if (markersDistBoard[distribBoardList[i][0]]) {
    	                    			        let marker = markersDistBoard[distribBoardList[i][0]];
    	                    			        markerClusterMetroDistBoard.removeMarker(marker);
    	                    			        marker.setMap(null);
    	                    			        delete markersDistBoard[distribBoardList[i][0]];
    	                    			        markerClusterMetroDistBoard.repaint(); // 
    	                    			    }
    	                    			}
    	                    			else {
    	                    			    console.log("deleting access:", distribBoardList[i][0]);

    	                    			    if (markersDistBoard[distribBoardList[i][0]]) {
    	                    			        let marker = markersDistBoard[distribBoardList[i][0]];
    	                    			        markerClusterAccessDistBoard.removeMarker(marker);
    	                    			        marker.setMap(null);
    	                    			        delete markersDistBoard[distribBoardList[i][0]];
    	                    			        markerClusterAccessDistBoard.repaint(); // 
    	                    			    }
    	                    			}
    	                    }
                    	       	}
                    		$("#"+data.controllerId).remove();

                
                    		if(markersController[data.controllerId]){
                    			
                    			   markersController[data.controllerId].setMap(null);

                    	            markerClusterController.removeMarker(markersController[data.controllerId]);
                    	        	delete markersController[data.controllerId];       
                            		
                        		}

                        	if (data.controllerDBCount != "0"){
                        		  
      							
	                           if (networkLayer == "backbone") {
                    				
                    				
	                        	   str = "<ul><li id='" + data.controllerId + "' class='bController' style='display:none;width:100px;'>"
	                        	    + "<input type='checkbox' class='ControllerPannel checkFilter filter' />"
	                        	    + "<span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span>"
	                        	    + "<span class='TreeSpan' style='color:black;width:355px'>"
	                        	    + "<img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> "
	                        	    + data.controllerName + "/" + data.controllerId
	                        	    + "</span></li></ul>";

	                        	$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
		
                    				$(".bController > .TreeSpan").contextmenu(function (event) {
                    				         selectedControllerId = $(this).parent().attr('id');
                    						 console.log(selectedControllerId);
                    				         selectedControllerName = $(this).text();
                    				        openContext(selectedControllerId, selectedControllerName, singleController, event);
                    				    });
                    				// Correct order: selector, type, clss
                    				treeCollapseFolder("#" + data.controllerId + " .folder", "fast", ".folder");
		
                    			
                    			}	
                            	else  	if (networkLayer == "metro") {
                    				
                    				
                            		str = "<ul><li id='" + data.controllerId + "' class='mController' style='display:none;width:100px;'>"
                            	    + "<input type='checkbox' class='ControllerPannel checkFilter filter' />"
                            	    + "<span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span>"
                            	    + "<span class='TreeSpan' style='color:black;width:355px'>"
                            	    + "<img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> "
                            	    + data.controllerName + "/" + data.controllerId
                            	    + "</span></li></ul>";

                            	$("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
		
                    				$(".mController > .TreeSpan").contextmenu(function (event) {
                    				         selectedControllerId = $(this).parent().attr('id');
                    						 console.log(selectedControllerId);
                    				         selectedControllerName = $(this).text();
                    				        openContext(selectedControllerId, selectedControllerName, singleController, event);
                    				    });
                    				// Correct order: selector, type, clss
                    				treeCollapseFolder("#" + data.controllerId + " .folder", "fast", ".folder");
		
                    			}
                            	else  	if (networkLayer == "access") {
                    				
                    				
                            		str = "<ul><li id='" + data.controllerId + "' class='aController' style='display:none;width:100px;'>"
                            	    + "<input type='checkbox' class='ControllerPannel checkFilter filter' />"
                            	    + "<span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span>"
                            	    + "<span class='TreeSpan' style='color:black;width:355px'>"
                            	    + "<img class='image' src='" + getContext() + "/resources/NetworkImages/controller,.png'> "
                            	    + data.controllerName + "/" + data.controllerId
                            	    + "</span></li></ul>";

                            	$("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
	
                    				$(".aController > .TreeSpan").contextmenu(function (event) {
                    				         selectedControllerId = $(this).parent().attr('id');
                    						 console.log(selectedControllerId);

                     				         selectedControllerName = $(this).text();
                    				        openContext(selectedControllerId, selectedControllerName, singleController, event);
                    				    });
                				   	    
                    				// Correct order: selector, type, clss
                    				treeCollapseFolder("#" + data.controllerId + " .folder", "fast", ".folder");
		
                    			}	
                   			
	                   
	                           createControllerMarkerClick(
	       							data.controllerId,
	       							data.controllerName,
	       						    data.lng, 
	       						    data.lat, 
	       						    markersController,
	       					        markerClusterController
	       							
	       						);
	       					controllerCheckFilter(
	       							data.controllerId,
	       							markerClusterController
	       							); 
	       				
	       					markerClusterController.addMarker(markersController[data.controllerId]);
	       			    
	                    
	                          
	                       createDB(data.DBList);


                            	}

                        	else {
                        		
                            	if (networkLayer == "backbone") {
                    				
                    				
                            		str="<ul><li id='"+data.controllerId+"'  class='bController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";								
        								$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
                    					
                    				$(".bController > .TreeSpan").contextmenu(function (event) {
                    				         selectedControllerId = $(this).parent().attr('id');
                    						 console.log(selectedControllerId);
                    				         selectedControllerName = $(this).text();
                    				        openContext(selectedControllerId, selectedControllerName, singleController, event);
                    				    });
                    				}	
                            	else  	if (networkLayer == "metro") {
                    				
                    				
                            		str="<ul><li id='"+data.controllerId+"'  class='mController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";								
        							$("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
                						
                    				$(".mController > .TreeSpan").contextmenu(function (event) {
                    				         selectedControllerId = $(this).parent().attr('id');
                    						 console.log(selectedControllerId);
                    				         selectedControllerName = $(this).text();
                    				        openContext(selectedControllerId, selectedControllerName, singleController, event);
                    				    });
                    			}
                            	else  	if (networkLayer == "access") {
                    				
                    				
                            		str="<ul><li id='"+data.controllerId+"'  class='aController' style='width:100px;'><input type='checkbox' class='DistBoard checkFilter' checked name='filter'></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";								
        							$("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
                					
                    				$(".aController > .TreeSpan").contextmenu(function (event) {
                    				         selectedControllerId = $(this).parent().attr('id');
                    						 console.log(selectedControllerId);
                    				         selectedControllerName = $(this).text();
                    				        openContext(selectedControllerId, selectedControllerName, singleController, event);
                    				    });
                    					    

                    			}
                            	createControllerMarkerClick(
            							data.controllerId,
            							data.controllerName,
            						    data.lng, 
            						    data.lat, 
            						    markersController,
            					        markerClusterController
            							
            						);
            					controllerCheckFilter(
            							data.controllerId,
            							markerClusterController
            							); 
            				
            					markerClusterController.addMarker(markersController[data.controllerId]);
            			    

                            	}







                        	}
                    	else {

                        	if(data.controllerDBCount != "0"){


                    		$("#"+data.controllerId+"> .TreeSpan").html("<img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li>");

                        	}
                    	}
                        	 
                    }
                    var childrenInitial=$("#initial_ul_CurrentPhysicalLayer").find(' > ul > li');
					var children = $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li');
					var networkLevelFolder =  $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li');
					var controllerFolder =  $("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li >ul >li >ul >li');
				    var singleCont =$("#"+data.controllerId).find(' > ul > li'); 
				 // FIX: don't overwrite with "yourControllerId"
				    var controllerId = data.controllerId;

				    // Select either <i> or <svg>
				    var $folderIcon = $("#" + controllerId + " .folder > i, #" + controllerId + " .folder > svg");

				    if ($folderIcon.length > 0) {
				          $folderIcon.removeClass("fa-folder").addClass("fa-folder-open");
				    } else {
				        console.warn("Folder icon NOT found for controllerId:", controllerId);
				    }
					 $("#"+data.controllerId).children(':checkbox').prop( "checked", true );
						
				    
					       
			
                    $("#initial_ul_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#initial_ul_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
					$("#DistributionBoard_f_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');



					$("#DistributionBoard_f_CurrentPhysicalLayer > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
					$("#DistributionBoard_f_CurrentPhysicalLayer").find(' > ul > li > ul > li > .Parentfolder >svg ').removeClass('fa fa-folder').addClass('fa-folder-open');
				
					
											
					children.show('fast');
					childrenInitial.show('fast');
					networkLevelFolder.show('fast');
					controllerFolder.show('fast');
					singleCont.show('fast');

										// scroll to the created db
										offset=$("#"+data.controllerId).offset().top;																
										offset2=$("#initial_ul_CurrentPhysicalLayer").offset().top;
										offsettot=(offset-offset2);
										
										$("#network_tree").animate({ scrollTop: offsettot}, "slow");
				

                    MouseHoveringSpans("#" + data.controllerId + " .TreeSpan");
                    tree_prop_selection("#" + data.controllerId + " .TreeSpan");

                    map.setZoom(11);
                    panTo(data.lat, data.lng);
                   
            				
                    $("#" + data.controllerId + " > .TreeSpan").css("display", "inline");

                    // Selection highlight
                    if (IdSelectedTemp != "") {
                        $("#" + IdSelectedTemp + " > .TreeSpan").removeClass("selected-span");
                        $("#" + IdSelectedTemp + " > .TreeSpan").css("background", "");
                    }
                    $("#" + data.controllerId + " > .TreeSpan").addClass("selected-span");
                    $("#" + data.controllerId + " > .TreeSpan").css("background-color", "#97b9cc");
                    IdSelectedTemp = data.controllerId;

                 //   expandTreeFolders();
                    scrollToController(data.controllerId);
             
                    $("#controllerModal").modal('hide'); 
                    $("#"+data.controllerId).contextmenu(function(){
                    	
            			menuName=singleController;
            			selectedControllerId=$(this).attr('id');
            			selectedContName=$(this).text();
            			
            			openContext(selectedControllerId,selectedContName,singleController,event);
            		});		
                }
            },
            error: function () {
                alert("Error");
            }
        });

    

        $("#controllerModel").modal('hide');
        $("#controllerModel").find("input,textarea,select").val('')
            .end().find("input[type=checkbox], input[type=radio]").prop("checked", "");
        
    /*    if ($("#dBMapCheck_Labels").prop("checked") == true) {
            markersDistBoard[data.distributionBoardId].setLabel({
                text: DistributionBoardName,
                className: "marker-position-dB",
                color: "#5665F9"
            });
        } */

    }
    // If longitude or latitude are missing
    else {
        alert("Missing Fields !!!");
    }
});
let singleControllerMenu = [
    {
        'icon': 'folder-plus',
        'name': 'Create New Controller',
        action: () => {
            $("#controllerHeader").text("Controller: ");
            $("#controllerModal").modal('show');
            $('#controllerModal').find('input:text').val('');
            document.getElementById("site_ControllerAutoComplete").checked = true;
            $('#site_ControllerAutoComplete').val('1');
            siteControllerChanged("#site_ControllerAutoComplete");

             
            $('#controllerModal').find('input:text').val('');
            actiondistControllerContext = "Insert";

            let select = document.getElementById("networkLayer");
            let targetText = networkLayer;

            for (let i = 0; i < select.options.length; i++) {
                if (select.options[i].text === targetText) {
                    select.selectedIndex = i;
                    break;
                }
            }
            dbContNtLevel = document.getElementById("networkLayer").value;
        }
    },
    {
        'icon': 'edit',
        'name': 'Edit or View Details',
        action: () => {
            $("#controllerModal").modal('show');
            actiondistControllerContext = "Update";
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/findControllerDetails',
                data: {
                    "selectedControllerContext": selectedControllerId
                },
                dataType: "json",
                success: function (data) {
                console.log(data);
                	$("#controllerModal").find("input").val('').end();
					
					$("#controllerHeader").text("Controller: "+data.ControllerDetails[0][0]);
					
					$("#controllerId").val(selectedControllerId);
					if(data.ControllerDetails[0][9]!=null){
						if(data.ControllerDetails[0][9].split("_")[0] == "CUST"){//for check box site or client
							document.getElementById("customer_ControllerAutoComplete").checked = true;
							$('#customer_ControllerAutoComplete').val('1');
							document.getElementById("site_ControllerAutoComplete").checked = false;
							$('#site_ControllerAutoComplete').val('0');
							  document.getElementById("ContClientId").style.display = "block";
						    	document.getElementById("ContClientName").style.display = "block";
						    	document.getElementById("ContClientPhoneNb").style.display = "block";
						    	document.getElementById("ContWarehouse").style.display = "none";
						    	document.getElementById("ContSite").style.display = "none";
						    	document.getElementById("ContSiteName").style.display = "none";

							
						}else{
							document.getElementById("site_ControllerAutoComplete").checked = true;
							$('#site_ControllerAutoComplete').val('1');
							document.getElementById("customer_ControllerAutoComplete").checked = false;
							$('#customer_ControllerAutoComplete').val('0');
							  document.getElementById("ContClientId").style.display = "none";
						    	document.getElementById("ContClientName").style.display = "none";
						    	document.getElementById("ContClientPhoneNb").style.display = "none";
						    	document.getElementById("ContWarehouse").style.display = "block";
						    	document.getElementById("ContSite").style.display = "block";
						    	document.getElementById("ContSiteName").style.display = "block";

						}
					}
					//
					if(data.ControllerDetails[0][9] == null){
						document.getElementById("site_ControllerAutoComplete").checked = true;
						$('#site_ControllerAutoComplete').val('1');
						document.getElementById("customer_ControllerAutoComplete").checked = false;
						$('#customer_ControllerAutoComplete').val('0');
						  document.getElementById("ContClientId").style.display = "none";
					    	document.getElementById("ContClientName").style.display = "none";
					    	document.getElementById("ContClientPhoneNb").style.display = "none";
					    	document.getElementById("ContWarehouse").style.display = "block";
					    	document.getElementById("ContSite").style.display = "block";
					    	document.getElementById("ContSiteName").style.display = "block";
					}

					if(data.ControllerDetails[0][1]!=null){
						$("#ControllerName").val(""+data.ControllerDetails[0][1]);
					}


					if(data.ControllerDetails[0][9]!=null){
						if(data.ControllerDetails[0][9].split("_")[0] == "CUST"){
							 $("#ControllerClient").val(""+data.ControllerDetails[0][9]);
						}
						else{
							 $("#ControllerSite").val(""+data.ControllerDetails[0][9]);
						}
					}
					if(data.ControllerDetails[0][10]!=null){ 
						if(data.ControllerDetails[0][9] !=null){
							if(data.ControllerDetails[0][9].split("_")[0] == "CUST"){
								   $("#ControllerClientName").val(""+data.ControllerDetails[0][10]);
								}
								else{
									$("#ControllerSiteName").val(""+data.ControllerDetails[0][10]);
							}
					}
						else{
								$("#ControllerSiteName").val(""+data.ControllerDetails[0][10]);
						}
					}
					
					
					if(data.ControllerDetails[0][11]!=null ){
						if(data.ControllerDetails[0][9] !=null){
							if(data.ControllerDetails[0][9].split("_")[0] == "CUST"){
								   $("#ControllerClientPhoneNb").val(""+data.ControllerDetails[0][11]);
							}
								else{
									$("#ControllerWarehouse").val(""+data.ControllerDetails[0][11]);
								}
							
						}
						else{
							$("#ControllerWarehouse").val(""+data.ControllerDetails[0][11]);
						}
					}
					if(data.ControllerDetails[0][17]!=null){
						$("#controllerCity").val(""+data.ControllerDetails[0][17]);
					}

					if(data.ControllerDetails[0][15]!=null){
						$("#ControllerLong").val(""+data.ControllerDetails[0][15]);
					}
					if(data.ControllerDetails[0][16]!=null){
						$("#ControllerLat").val(""+data.ControllerDetails[0][16]);
					}
					if(data.ControllerDetails[0][2]!=null){
						$("#serialNumb").val(""+data.ControllerDetails[0][2]);
					}
					if(data.ControllerDetails[0][3]!=null){
						$("#macAddress").val(""+data.ControllerDetails[0][3]);
					}
					if(data.ControllerDetails[0][18]!=null){
						$("#controllerCreateDate").val(""+data.ControllerDetails[0][18]);
					}
					if(data.ControllerDetails[0][19]!=null){
						$("#controllerLastModifiedDate").val(""+data.ControllerDetails[0][19]);
					}
					if(data.ControllerDetails[0][4]!=null){
						$("#ipAddress").val(""+data.ControllerDetails[0][4]);
					}

					if(data.ControllerDetails[0][5]!=null){
						$("#subnetMask").val(""+data.ControllerDetails[0][5]);
					}
					if(data.ControllerDetails[0][6]!=null){
						$("#gateWay").val(""+data.ControllerDetails[0][6]);
					}

					if(data.ControllerDetails[0][7]!=null){
						$("#userName").val(""+data.ControllerDetails[0][7]);
					}

					if(data.ControllerDetails[0][8]!=null){
						$("#password").val(""+data.ControllerDetails[0][8]);
					}

					if(data.ControllerDetails[0][12]!=null){
						$("#numbOfPanels").val(""+data.ControllerDetails[0][12]);
					}

					if(data.ControllerDetails[0][13]!=null){
						$("#numbOfPorts").val(""+data.ControllerDetails[0][13]);
					}
					if (data.ControllerDetails[0][14] != null) {
					    if (data.ControllerDetails[0][14] == "backbone") {
					        $("#networkLayer").prop("selectedIndex", 1);
					    } 
					    else if (data.ControllerDetails[0][14] == "metro") {
					        $("#networkLayer").prop("selectedIndex", 2);
					    } 
					    else if (data.ControllerDetails[0][14] == "access") {
					        $("#networkLayer").prop("selectedIndex", 3);
					    }
					}
					  dbContNtLevel = document.getElementById("networkLayer").value;
			          
						
                    // handle data here
                },
                error: function (result) {
                    alert("Error");
                }
            });
        }
    }
];

if (addDB === '1') {
    singleControllerMenu.unshift({
        icon: 'folder-plus',
        name: 'Create New Board',
        action: () => {

            // Reset header and modal
            document.getElementById("DistributionBoardheader").textContent = "Distribution Board: ";
            const modal = document.getElementById("distributionBoardModal");
            $(modal).modal('show');  // keep Bootstrap modal trigger
            modal.querySelectorAll('input').forEach(input => input.value = '');
            document.querySelector("#DbMappingTable > tbody").innerHTML = '';
            document.getElementById("DBMappingFlag").value = "new DB";
            document.getElementById('DBnetlevel').disabled = true;

            // Checkboxes setup
            const siteDB = document.getElementById("site_DBAutoComplete");
            const customerDB = document.getElementById("customer_DBAutoComplete");

            siteDB.checked = true;
            siteDB.value = "1";
            customerDB.checked = false;
            customerDB.value = "0";

            // Controller info
            document.getElementById("distController").style.display = "block";
            document.getElementById("distControllerName").style.display = "block";
            document.getElementById("DBController").value = selectedControllerId;
            document.getElementById("DBControllerName").value = selectedControllerName.split("/")[0];

            // Hide/show fields
            document.getElementById("DBClientId").style.display = "none";
            document.getElementById("DBClientName").style.display = "none";
            document.getElementById("BDClientPhoneNb").style.display = "none";
            document.getElementById("BDWarehouse").style.display = "block";
            document.getElementById("DBSite").style.display = "block";
            document.getElementById("DBSiteName").style.display = "block";

            // Reset context
            selectedDistBoardContext = "";
            actiondistBoardContext = "Insert";
            document.getElementById("DBType").value = "active";

            // Fetch controller location
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/getControllerLocation',
                async: false,
                data: { "selectedControllerId": selectedControllerId },
                dataType: "json",
                success: function (data) {
                   

                    // Lat/Long
                    document.getElementById("DistributionBoardLat").value = data.ControllerLocation[0][1];
                    document.getElementById("DistributionBoardLong").value = data.ControllerLocation[0][0];

                    // WARE case
                    if (data.ControllerLocation[0][4].includes("WARE")) {
                        siteDB.checked = true;
                        siteDB.value = "1";
                        customerDB.checked = false;
                        customerDB.value = "0";

                        document.getElementById("DBClientId").style.display = "none";
                        document.getElementById("DBClientName").style.display = "none";
                        document.getElementById("BDClientPhoneNb").style.display = "none";

                        document.getElementById("BDWarehouse").style.display = "block";
                        document.getElementById("DBSite").style.display = "block";
                        document.getElementById("DBSiteName").style.display = "block";

                        document.getElementById("DistributionBoardSite").value = data.ControllerLocation[0][2];
                        document.getElementById("DistributionBoardSiteName").value = data.ControllerLocation[0][3];
                        document.getElementById("DistributionBoardWarehouse").value = data.ControllerLocation[0][4];
                    } 
                    // Client case
                    else {
                        customerDB.checked = true;
                        customerDB.value = "1";
                        siteDB.checked = false;
                        siteDB.value = "0";

                        document.getElementById("BDWarehouse").style.display = "none";
                        document.getElementById("DBSite").style.display = "none";
                        document.getElementById("DBSiteName").style.display = "none";

                        document.getElementById("DBClientId").style.display = "block";
                        document.getElementById("DBClientName").style.display = "block";
                        document.getElementById("BDClientPhoneNb").style.display = "block";

                        document.getElementById("DistributionBoardClient").value = data.ControllerLocation[0][2];
                        document.getElementById("DistributionBoardClientName").value = data.ControllerLocation[0][3];
                        document.getElementById("DistributionBoardClientPhoneNb").value = data.ControllerLocation[0][4];
                    }

                    // City
                    document.getElementById("boardCity").value = data.ControllerLocation[0][5];
        			
                    document.getElementById("boardCity").value = data.ControllerLocation[0][5];

                    let select = document.getElementById("DBnetlevel");
                    select.value = data.ControllerLocation[0][6];  
                },
                error: function () {
                    alert("Error while fetching ControllerLocation");
                }
            });

        }
    });
}



singleController = new ContextMenu({
    'theme': 'default',
    'items': singleControllerMenu
});

// === Helper functions (you can define them above or below) ===
function appendControllerToTree(data,strdb) {
	console.log(data.ControllerList);
    let str = "";
    if (data.networkLayer == "backbone") {

        
    	str="<ul><li id='"+data.controllerId+"' class='bController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";
		$("#DistributionBoard_backboneController__CurrentPhysicalLayer").append(str);
	      
    }
    else if (data.networkLayer == "metro") {
    	str="<ul><li id='"+data.controllerId+"' class='mController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";
		        $("#DistributionBoard_metroController__CurrentPhysicalLayer").append(str);
    }
    else if (data.networkLayer == "access") {
    	str="<ul><li id='"+data.controllerId+"' class='aController'  style='display:none;width:100px;'><input type='checkbox' class='ControllerPannel checkFilter' class='filter'  ></input> <span class='TreeSpan' style='color:black;width:355px'><img class='image' src='"+getContext()+"/resources/NetworkImages/controller,.png'> "+data.controllerName+"/"+data.controllerId+" </span></li></ul>";
	    $("#DistributionBoard_accessController__CurrentPhysicalLayer").append(str);
        
    }
}

function handleUpdateController(data) {
	console.log("okay");
	  console.log(dbContNtLevel);
      console.log(data.controllerList);
   
    if (data.networkLayer != dbContNtLevel) {
      
      
  
        var allLis = "";

     // loop over your list and collect all <li> from each target
   
         let containerId = "#" + data.controllerId;
         
         // find the <li> children inside that container
         $(containerId).find("li.DistributionBoard").each(function() {
             allLis += this.outerHTML;
         });
     
         console.log(allLis);
         console.log("woww");
     // wrap everything inside one <ul>
     let str = "<ul>" + allLis + "</ul>";
     $("#" + data.controllerId).remove();
     createController(data.ControllerList,data.DBList);
    } else {
        let imgPath = "";
        if (data.networkLayer == "backbone") {
            imgPath = "/resources/NetworkImages/controller,.png";
        } else if (data.networkLayer == "metro") {
            imgPath = "/resources/NetworkImages/controller,.png";
        } else if (data.networkLayer == "access") {
            imgPath = "/resources/NetworkImages/controller,.png";
        }
        $("#" + data.controllerId + "> .TreeSpan").html(
            "<img class='image' src='" + getContext() + imgPath + "'> " +
            data.controllerName + "/" + data.controllerId + " </span></li>"
        );
    }
}

function expandTreeFolders() {
    $("#initial_ul_" + IdNodeSelectedTemp + " > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#initial_ul_Projects > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#" + IdNodeSelectedTemp + " > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > .Parentfolder >svg")
        .removeClass('fa fa-folder').addClass('fa-folder-open');
    $("#DistributionBoard_f_" + IdNodeSelectedTemp + "")
        .find(' > ul > li > .Parentfolder >svg')
        .removeClass('fa fa-folder').addClass('fa-folder-open');

    $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > ul > li").show('fast');
    $("#initial_ul_" + IdNodeSelectedTemp + " > ul > li").show('fast');
    $("#DistributionBoard_f_" + IdNodeSelectedTemp + " > ul > li > ul > li").show('fast');
}

function scrollToDistributionBoard(id) {
    let offset = $("#" + id).offset().top;
    let offset2 = $("#initial_ul_CurrentPhysicalLayer").offset().top;
    let offsettot = (offset - offset2);
    $("#network_tree").animate({ scrollTop: offsettot }, "slow");
}

function scrollToController(id) {
    let offset = $("#" + id).offset().top;
    let offset2 = $("#initial_ul_CurrentPhysicalLayer").offset().top;
    let offsettot = (offset - offset2);
    $("#network_tree").animate({ scrollTop: offsettot }, "slow");
}	  
//zeinaaa
function siteControllerChanged(checkbox) {
    console.log("Site Controller changed â checked =", checkbox.checked);
    if ($(checkbox).is(":checked")) {
        document.getElementById("customer_ControllerAutoComplete").checked = false;
        $('#customer_ControllerAutoComplete').val('0');
        $(checkbox).val('1');
        isClientChecked = false;
        isSiteChecked = true;
    	document.getElementById("ContClientId").style.display = "none";
    	document.getElementById("ContClientName").style.display = "none";
    	document.getElementById("ContClientPhoneNb").style.display = "none";
    	document.getElementById("ContWarehouse").style.display = "block";
    	document.getElementById("ContSite").style.display = "block";
    	document.getElementById("ContSiteName").style.display = "block";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML
      
    }
    else {
        $(checkbox).val('0');
        isClientChecked = false;
        isSiteChecked = true;
        document.getElementById("customer_ControllerAutoComplete").checked = true;
        $('#customer_ControllerAutoComplete').val('1');
        document.getElementById("ContClientId").style.display = "block";
    	document.getElementById("ContClientName").style.display = "block";
    	document.getElementById("ContClientPhoneNb").style.display = "block";
    	document.getElementById("ContWarehouse").style.display = "none";
    	document.getElementById("ContSite").style.display = "none";
    	document.getElementById("ContSiteName").style.display = "none";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML
   }
  
}

function customerControllerChanged(checkbox) {
    console.log("Customer Controller changed â checked =", checkbox.checked);

    if ($(checkbox).is(":checked")) {
        document.getElementById("site_ControllerAutoComplete").checked = false;
        $('#site_ControllerAutoComplete').val('0');
        $(checkbox).val('1');
        isClientChecked = true;
        isSiteChecked = false;
    	document.getElementById("ContClientId").style.display = "block";
    	document.getElementById("ContClientName").style.display = "block";
    	document.getElementById("ContClientPhoneNb").style.display = "block";
    	document.getElementById("ContWarehouse").style.display = "none";
    	document.getElementById("ContSite").style.display = "none";
    	document.getElementById("ContSiteName").style.display = "none";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML
      
    } else {
        $(checkbox).val('0');
        isClientChecked = false;
        isSiteChecked = true;
        document.getElementById("site_ControllerAutoComplete").checked = true;
        $('#site_ControllerAutoComplete').val('1');
        document.getElementById("ContClientId").style.display = "none";
    	document.getElementById("ContClientName").style.display = "none";
    	document.getElementById("ContClientPhoneNb").style.display = "none";
    	document.getElementById("ContWarehouse").style.display = "block";
    	document.getElementById("ContSite").style.display = "block";
    	document.getElementById("ContSiteName").style.display = "block";
        document.getElementById("controllerCity").value = ""; // Make sure ID matches HTML
   }
}

 


	   $("#ControllerSite").autocomplete({
	   	 source: debounce(function(request, response) {
	   		var searchkey = $("#ControllerSite").val();
	   		console.log("siteid");
	   		  $.ajax({
	   	             type: "GET",
	   	             contentType: "application/json; charset=utf-8",
	   	             url:  getContext()+'/GetAllWarehouse',
	   	             data:  {					
	   					 "WareName":searchkey,
	   					 "warehouseName" : searchkey,
	   					 "SiteId":searchkey,
	   				},
	   	             dataType: "json",
	   	             success: function (data) {
	   	                 if (data != null) {
	   	                    response(data.globalList);             
	   	                 }
	   	             },
	   	             error: function(result) {
	   	                 alert("Error");
	   	             }
	   	         });		   
	   		 },1000), 
	   		 minLength:0, maxShowItems: 40, scroll:true,
	   			select: function(event, ui) {
	   								
	   			this.value = (ui.item ? ui.item[2] : '');
	   			$("#ControllerSiteName").val(ui.item[1]);
	   			$("#ControllerWarehouse").val(ui.item[0]);
	   			if (document.getElementById('customControllerCoordinates').checked) {
	   				console.log("customControllerCoordinates");
	   				if($("#ControllerLong").val() =="" && $("#ControllerLat").val() =="" ) {
	   					$("#ControllerLong").val(ui.item[3]);
	   					$("#ControllerLat").val(ui.item[4]);
	   				}
	   			}else{
	   				$("#ControllerLong").val(ui.item[3]);
	   				$("#ControllerLat").val(ui.item[4]);
	   			}
	   			
	   			$("#controllerCity").val(ui.item[5]);
	   			//$("#DistributionBoardName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
	   			if(($("#ControllerName").val()) ==""){
	   				$("#ControllerName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
	   			}
	   			return false;
	   			}
	   	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	   			 return $("<li class='each'>")
	   		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	   		        item[0] + "</span><br><span class='desc'>" +
	   		         item[1] +', '+ item[2] + "</span></div>")
	   		     .appendTo(ul);
	   	};

	   $("#ControllerSite").focus(function(){
	   	if (this.value == ""){
	   		$(this).autocomplete("search");
	   	}						
	   });	

	   $("#ControllerSiteName").autocomplete({
	   	 source: debounce(function(request, response) {
	   		var searchkey = $("#ControllerSiteName").val();
	   		console.log("name");
	   		  $.ajax({
	   	             type: "GET",
	   	             contentType: "application/json; charset=utf-8",
	   	             url:  getContext()+'/GetAllWarehouse',
	   	             data: {					
	   					 "WareName":searchkey,
	   					 "warehouseName" : searchkey,
	   					 "SiteId":searchkey,
	   				},
	   	             dataType: "json",
	   	             success: function (data) {
	   	                 if (data != null) {
	   	                    response(data.globalList);             
	   	                 }
	   	             },
	   	             error: function(result) {
	   	                 alert("Error");
	   	             }
	   	         });	 		 
	   		},1000), 
	   		 minLength:0, maxShowItems: 40, scroll:true,
	   			select: function(event, ui) {
	   										
	   			this.value = (ui.item ? ui.item[1] : '');
	   			$("#ControllerSite").val(ui.item[2]);
	   			$("#ControllerWarehouse").val(ui.item[0]);
	   			$("#ControllerLong").val(ui.item[3]);
	   			$("#ControllerLat").val(ui.item[4]);
	   			$("#controllerCity").val(ui.item[5]);
	   			//$("#DistributionBoardName").val(ui.item[1]+"_DB");
	   			if(($("#ControllerName").val()) ==""){
	   				$("#ControllerName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
	   			}

	   			return false;
	   			}
	   	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	   			 return $("<li class='each'>")
	   		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	   		        item[0] + "</span><br><span class='desc'>" +
	   		         item[1] +', '+ item[2] + "</span></div>")
	   		     .appendTo(ul);
	   	};

	   $("#ControllerSiteName").focus(function(){
	   	if (this.value == ""){
	   		$(this).autocomplete("search");
	   	}						
	   });	

	   $("#ControllerWarehouse").autocomplete({
	   	 source:debounce( function(request, response) {

	   		var searchkey = $("#ControllerWarehouse").val();
	   		  $.ajax({
	   	             type: "GET",
	   	             contentType: "application/json; charset=utf-8",
	   	             url:  getContext()+'/GetAllWarehouse',
	   	             data: {
	   	            	 "WareName":searchkey,
	   					 "warehouseName" : searchkey,
	   					 "SiteId":searchkey,
	   				 },
	   	             dataType: "json",
	   	             success: function (data) {
	   	                 if (data != null) {
	   	                    response(data.globalList);             
	   	                 }
	   	             },
	   	             error: function(result) {
	   	                 alert("Error");
	   	             }
	   	         });
	   		 },1000), 
	   		 minLength:0, maxShowItems: 40, scroll:true,
	   			select: function(event, ui) {
	   										
	   			this.value = (ui.item ? ui.item[0] : '');
	   			$("#ControllerSite").val(ui.item[2]);
	   			$("#ControllerSiteName").val(ui.item[1]);
	   			$("#ControllerLong").val(ui.item[3]);
	   			$("#ControllerLat").val(ui.item[4]);
	   			$("#controllerCity").val(ui.item[5]);
	   			//$("#DistributionBoardName").val(ui.item[1]+"_DB");
	   			if(($("#ControllerName").val()) ==""){
	   				$("#ControllerName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
	   			}

	   			return false;
	   			}
	   	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	   			 return $("<li class='each'>")
	   		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	   		        item[0] + "</span><br><span class='desc'>" +
	   		         item[1] +', '+ item[2] + "</span></div>")
	   		     .appendTo(ul);
	   	};

	   $("#ControllerWarehouse").focus(function(){
	   	if (this.value == ""){
	   		$(this).autocomplete("search");
	   	}						
	   });	

	   //client auto complete
	   $("#ControllerClient").autocomplete({
	   	 source: debounce(function(request, response) {
	   		var searchkey = $("#ControllerClient").val();
	   		console.log("id");
	   		  $.ajax({
	   	             type: "GET",
	   	             contentType: "application/json; charset=utf-8",
	   	             url:  getContext()+'/GetAllNetworkCustomer',
	   	             data:  {					
	   	            	 "search":searchkey,
	   				},
	   	             dataType: "json",
	   	             success: function (data) {
	   	                 if (data != null) {
	   	                    response(data.globalList);             
	   	                 }
	   	             },
	   	             error: function(result) {
	   	                 alert("Error");
	   	             }
	   	         });		   
	   		 },1000), 
	   		 minLength:0, maxShowItems: 40, scroll:true,
	   			select: function(event, ui) {
	   										
	   			this.value = (ui.item ? ui.item[0] : '');
	   			$("#ControllerClientName").val(ui.item[1]);
	   			$("#ControllerClientPhoneNb").val(ui.item[2]);
	   			if (document.getElementById('customControllerCoordinates').checked) {
	   				console.log("customControllerCoordinates");
	   				if($("#ControllerLong").val() =="" && $("#ControllerLat").val() =="" ) {
	   					$("#ControllerLong").val(ui.item[4]);
	   					$("#ControllerLat").val(ui.item[5]);
	   				}
	   			}else{
	   				$("#ControllerLong").val(ui.item[4]);
	   				$("#ControllerLat").val(ui.item[5]);
	   			}
	   			
	   			$("#controllerCity").val(ui.item[3]);
	   			//$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
	   			if(($("#ControllerName").val()) ==""){
	   				$("#ControllerName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
	   			}

	   			return false;
	   			}
	   	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	   			 return $("<li class='each'>")
	   		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	   	    		 item[0] + "</span><br><span class='desc'>" +
	   		         item[1] +', '+item[2] +"</span></div>")
	   		     .appendTo(ul);
	   	};

	   $("#ControllerClient").focus(function(){
	   	if (this.value == ""){
	   		$(this).autocomplete("search");
	   	}						
	   });	

	   $("#ControllerClientName").autocomplete({
	   	 source: debounce(function(request, response) {
	   		var searchkey = $("#ControllerClientName").val();
	   		console.log("name");
	   		
	   		  $.ajax({
	   	             type: "GET",
	   	             contentType: "application/json; charset=utf-8",
	   	             url:  getContext()+'/GetAllNetworkCustomer',
	   	             data:  {					
	   	            	 "search":searchkey,
	   				},
	   	             dataType: "json",
	   	             success: function (data) {
	   	                 if (data != null) {
	   	                    response(data.globalList);             
	   	                 }
	   	             },
	   	             error: function(result) {
	   	                 alert("Error");
	   	             }
	   	         });	   
	   		 },1000), 
	   		 minLength:0, maxShowItems: 40, scroll:true,
	   			select: function(event, ui) {
	   										
	   			this.value = (ui.item ? ui.item[1] : '');
	   			$("#ControllerClient").val(ui.item[0]);
	   			$("#ControllerClientPhoneNb").val(ui.item[2]);
	   			$("#ControllerLong").val(ui.item[4]);
	   			$("#ControllerLat").val(ui.item[5]);
	   			$("#controllerCity").val(ui.item[3]);
	   			//$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
	   			if(($("#ControllerName").val()) ==""){
	   				$("#ControllerName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
	   			}

	   			return false;
	   			}
	   	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	   			 return $("<li class='each'>")
	   		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	   	    		 item[1] + "</span><br><span class='desc'>" +
	   		         item[0] +', '+ item[2] + "</span></div>")
	   		     .appendTo(ul);
	   	};

	   $("#ControllerClientName").focus(function(){
	   	if (this.value == ""){
	   		$(this).autocomplete("search");
	   	}						
	   });	

	   $("#ControllerClientPhoneNb").autocomplete({
	   	 source: debounce(function(request, response) {
	   		var searchkey = $("#ControllerClientPhoneNb").val();
	   		
	   		  $.ajax({
	   	             type: "GET",
	   	             contentType: "application/json; charset=utf-8",
	   	             url:  getContext()+'/GetAllNetworkCustomer',
	   	             data:  {					
	   	            	 "search":searchkey,
	   				},
	   	             dataType: "json",
	   	             success: function (data) {
	   	                 if (data != null) {
	   	                    response(data.globalList);             
	   	                 }
	   	             },
	   	             error: function(result) {
	   	                 alert("Error");
	   	             }
	   	         });
	   		   
	   		 },1000), 
	   		 minLength:0, maxShowItems: 40, scroll:true,
	   			select: function(event, ui) {
	   										
	   			this.value = (ui.item ? ui.item[2] : '');
	   			$("#ControllerClient").val(ui.item[0]);
	   			$("#ControllerClientName").val(ui.item[1]);
	   			$("#ControllerLong").val(ui.item[4]);
	   			$("#ControllerLat").val(ui.item[5]);
	   			$("#controllerCity").val(ui.item[3]);
	   			//$("#DistributionBoardName").val(ui.item[1]+"_"+ui.item[2]+"_DB");
	   			if(($("#ControllerName").val()) ==""){
	   				$("#ControllerName").val(ui.item[1]+"_DB_"+new Date().getFullYear());
	   			}

	   			return false;
	   			}
	   	}).data( "ui-autocomplete" )._renderItem= function(ul, item) {
	   			 return $("<li class='each'>")
	   		     .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	   	    		 item[2] + "</span><br><span class='desc'>" +
	   		         item[0] +', '+ item[1] + "</span></div>")
	   		     .appendTo(ul);
	   	};

	   $("#ControllerClientPhoneNb").focus(function(){
	   	if (this.value == ""){
	   		$(this).autocomplete("search");
	   	}						
	   });


	   function getContCity(){
			//console.log("getDBCity ");
			fillCityByGeocoding("controllerCity",$("#ControllerLat").val(),$("#ControllerLong").val(),geocoder);	
			
		}

	   $('#controllerModal').on('hide.bs.modal', function () {
		    document.activeElement.blur(); // remove focus
		});



			
		

</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>
</html>
