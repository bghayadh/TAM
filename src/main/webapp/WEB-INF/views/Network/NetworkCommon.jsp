<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="utf-8">
<title></title>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/clustererplus.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
	<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Network_Index.css">
	<script src="${pageContext.request.contextPath}/resources/js/Network/NetworkMapStylesLayers.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/context-menu.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/context-menu.css">
	<script src="${pageContext.request.contextPath}/resources/js/Network/NetworkElementTree.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/Network/NetworkElementMap.js"></script>
			
<style>
.tab button.active {
    background: #FFD966;
    color: #1D3763 !important;
}
.TreeSpan:hover {
   background: #d3d8ff;
}
</style>
</head>
<body>
	<c:set var="pg" value="network" scope="session" />
	<jsp:include page="../header.jsp"></jsp:include>
	<p></p>
	<div class="container-fluid" style="margin-top: -15px;">
		<div id="optionalIconsBar">
			<div class="row">
				<div class="col-md-11">
					<div class="btn-toolbar"
						style="text-align: left; margin-bottom: 15px;">
						<div class="btn-group flex-wrap" data-toggle="buttons"
							style="row-gap: 2px;">
							<div class="top-btn-filter">
								<button id="siteBtn" name="Site" class="buttonTog">
									<i class='fas fa-crosshairs' style='font-size: 15px'></i><span>
										Site</span>
								</button>
							</div>
							<div class="top-btn-filter">
								<button id="nodeBtn" name="Node" class="buttonTog">
									<i class='fas fa-server' style='font-size: 15px'></i> Node
								</button>
							</div>
							<div class="top-btn-filter">
								<button id="cellBtn" name="Cell" class="buttonTog">
									<i class='fas fa-vector-square' style="font-size: 15px"></i>
									Cell
								</button>
							</div>
							<div class="top-btn-filter">
								<button id="nodeTypeeBtn" name="Node Type" class="buttonTog">
									<i class='fa fa-cogs' style='font-size: 15px'></i> Node Type
								</button>
							</div>
							<div class="top-btn-filter">
								<button id="itemBtn" name="Item" class="buttonTog">
									<i class="fas fa-bahai" style="font-size: 15px"></i> Item
								</button>
							</div>
							<div class=" top-btn-filter">
								<button id="itemCatBtn" name="Item Category" class="buttonTog">
									<i class="fas fa-object-group" style="font-size: 12px"></i>
									Item Category
								</button>
							</div>

							<div class=" top-btn-filter">
								<button id="supplierBtn" name="Supplier" class="buttonTog">
									<i class="far fa-copyright" style="font-size: 15px"></i>
									Supplier
								</button>
							</div>
							<div class=" top-btn-filter">
								<button id="clusterBtn" name="Cluster" class="buttonTog">
									<i class="fas fa-location-arrow"></i> Cluster
								</button>
							</div>

							<div class=" top-btn-filter">
								<button id="areaBtn" name="Area" class="buttonTog">
									<i class="fas fa-map"></i> Area
								</button>
							</div>

							<div class=" top-btn-filter">
								<button id="accessDBtn" name="Access" class="buttonTog domain">
									<i class='fa fa-podcast' style='font-size: 12px'></i> Access
								</button>
							</div>
	
							<div class=" top-btn-filter">
								<button id="transpBtn" name="Transport" class="buttonTog domain">
									<i class='fa fa-link' style='font-size: 15px'></i> Transport
								</button>
							</div>
							<div class=" top-btn-filter">
								<button id="EnterpriseBtn" name="Enterprise" class="buttonTog domain">
									<i class='fa fa-building' style='font-size: 12px'></i> Enterprise
								</button>
							</div>
							<div class=" top-btn-filter">
								<button id="CoreBtn" name="Core" class="buttonTog ">
									<i class='fa fa-arrows-alt' style='font-size: 15px'></i><span
										style="margin-left: 3px"> Core</span>
								</button>
							</div>
							<div class=" top-btn-filter"
								style="height: 60px; width: fit-content;">
								<button id="poBtn" name="PO" class="buttonTog">
									<i class="fas fa-file-invoice-dollar" style='font-size: 15px'></i><span
										style="margin-left: 3px"> PO</span>
								</button>
							</div>

						</div>


					</div>
				</div>

				<div class="col-md-1"
					style="float: right; margin-left: -5px; margin-top: 5px;">
					<div class="form-group">
						<button type="button" class="btn btn-outline-success" id="submit"
							onclick="SubmitDefault()" style="width: 100%; height: 30px">Submit</button>
					</div>
				</div>

			</div>

		</div>

	</div>

	<!-- End of Optional Bar  -->
	<!--  Beginning for Search bar, Coordinates and View buttons -->
	<div class="container-fluid" style="margin-top: -20px;">

		<div class="row second" style="margin-top: -40px; margin-bottom: -3px;">
			<div class="col-md-4" style="margin-top: 9px;">
				<div class="search" id="headSearch">
					<input type="text" class="searchTerm" id=autocompliteSearch style="height: 32px;" placeholder="Search within the Network ..">
					<button type="submit" class="searchButton" style="height: 32px;">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</div>
		<div class="col-md-2" id="dropDownCheckDiv"
				style="text-align: right; margin-top: 6px;"></div>
			<div class="col-md-2" style="text-align: right; margin-top: 12px;">
				<div id="txtDiv">
					<input id="mapText" type='text' disabled
						style="height: 35px; text-align: center; margin-left: 60px;" />
				</div>
			</div>
			<div class="col-md-4" style="text-align: right; margin-top: 4px;">
				<div class="btn-group pull-right">
					<div class="glyph">
						<button type="button" class="btn btn-light" data-placement="top"
							title="Map Operations" data-toggle="modal" data-target="mapModal">
							<i class="fas fa-toolbox"></i>
						</button>
						<button type="button" class="btn btn-light" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false" data-placement="top"
							title="Map Modes">
							<i class="fas fa-tools"></i>
						</button>
						<div class="dropdown-menu">
							<a class="dropdown-item" onclick="initMap();" id="customMap"
								data-map="CustomMap">Clustered Map</a> <a class="dropdown-item"
								onclick="updateMap();" id="blankMap" data-map="BlankMap">Non
								Clustered Map</a>
						</div>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Settings" onclick="DrawList()"
							data-target="modalOrderTree" id="treepopupbtn">
							<i class="fa fa-tree"></i>
						</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search" id="open-popup-btn">
							<i class="fa fa-search"></i>
						</button>
						<button type="button" class="btn btn-light" id="tree"
							data-toggle="tooltip" data-placement="top" title=" Folder Tree">
							<i class="fas fa-sitemap"></i>
						</button>
						<button type="button" id="Fview" class="btn btn-light"
							data-toggle="tooltip" data-placement="top" title="Form View"
							onclick='window.location.href = "${pageContext.request.contextPath}/SiteFormView"'>
							<i class="fa fa-edit"></i>
						</button>
						<a href="Sitelistview">

							<button type="submit" id="Lview" class="btn btn-light"
								data-toggle="tooltip" data-placement="top" title="List View">
								<!--  onclick='window.location.href = "${pageContext.request.contextPath}/SiteListView"' -->
								<i class="fa fa-bars"></i>
							</button>
						</a>

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
			<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" />
			<div class="searchcontainer" style="margin-left: 55px; margin-bottom: 5px; margin-top: 5px;">
				<div class="search" style="width:95%">
					<input type="text" class="searchTerm" style="border-right: 3px solid #08526D; font-size: 15px;" placeholder="Search within the tree ..">
				</div>
			</div>
			<a
				style="position: absolute; margin-left: 400px; display: none; z-index: 1;"
				id='removeFilter' href=''> <input type="image"
				src="${pageContext.request.contextPath}/resources/js/Network/RemoveFilter.png"
				style="position: absolute;" height="30" width="30" />
			</a>

			<!-- Beginning for Tree in left side -->
			<div id="network_tree" class="tree well top-btn-filter"
				style="margin-top: 5px; position: static; z-index: 3;"></div>
			<input type="hidden" id="hidden_input" value=""></input>
			<!-- To place the selected option in this field -->

			<!-- End for Tree in left side -->

			<!--  Beginning for arrows, layers, options and Boq part which is below the left tree side -->

			<div id="narrowdiv">
				<button style="margin-left: 40%;" id="arrow_up">
					<i class="fa fa-arrow-up fa-lg " aria-hidden="true"
						onmouseover="changecolor(this)" title="Maximize "
						onmouseout="returncolor(this) "
						style="color: #08526D; font-size: 15px; margin-bottom: 6px;"></i>
				</button>
				<button id="arrow_down">
					<i class="fa fa-arrow-down fa-lg " aria-hidden="true"
						onmouseover="changecolor(this)" title="Minimize "
						onmouseout="returncolor(this) "
						style="color: #08526D; font-size: 15px; margin-bottom: 6px;"></i>
				</button>
				<button style='margin-left: 45%; display: none' id='arrow_up_normal'
					onclick='returnNormal()'>
					<i class='fa fa-arrow-up fa-lg ' aria-hidden='true'
						onmouseover='changecolor(this)' title="Restore Size "
						onmouseout='returncolor(this)'
						style='color: #08526D; font-size: 15px; margin-bottom: 6px;'></i>
				</button>
				<button style='margin-left: 45%; display: none'
					id='arrow_down_normal' onclick='returnNormal()'>
					<i class='fa fa-arrow-down fa-lg ' aria-hidden='true'
						onmouseover='changecolor(this)' title="Restore Size "
						onmouseout='returncolor(this)'
						style='color: #08526D; font-size: 15px; margin-bottom: 6px;'></i>
				</button>
			</div>

			<div id="Bottomdiv">
				<div class="tab">
					<button class="tablinks" onclick="opentab(event, 'Layers')"
						id="Defaultbutton" style="margin-left: 30px; color:#FFD966; font-weight:bold">Layers</button>
					<button class="tablinks" onclick="opentab(event, 'Options')"
						style="color: white; margin-left: 15px; color:#FFD966; font-weight:bold">Options</button>
					<button id="boqBtn" class="tablinks" onclick="opentab(event, 'Boq')"
						style="color: white; margin-left: 15px; color:#FFD966; font-weight:bold">BoQ</button>
				</div>
				<div id="Layers" class="tabcontent">

					<div class="search" style="margin-top: 1px;">
						<input type="text" class="searchTermLayers"
							onkeyup="FilterOptions('layers')" id="layerSearch"
							placeholder="Search within the Layers ..">
						<button type="submit" class="searchButton">
							<i class="fa fa-search"></i>
						</button>
					</div>
					<table id="LayersTable" style="margin-top: 10px;">
						<tr id="view" style="border-bottom: solid #08526D 1px;">
							<td class="Icon "></td>
							<td class="title">View</td>
						</tr>
						
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
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/poi.png"><span
								id="definition">Points Of Interest</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"
								id="poi" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/road.png"
								style="width: 16px; height: 16px;"><span id="definition">Roads</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"
								id="road" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/exchange.png"><span
								id="definition">Transit</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"
								id="transit" checked></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/sea.png"><span
								id="definition">Rivers And Sea</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"
								id="water" checked></td>
						</tr>


						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/map geography.png"><span
								id="definition">Light Geography</span></td>
							<td><input type="checkbox" id="mapgeography"
								style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/country-map (1).png"><span
								id="definition">Country Borders</span></td>
							<td><input type="checkbox" id="maplabels"
								style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/flag.png"><span
								id="definition">Country Names</span></td>
							<td><input type="checkbox" id="countrynames"
								style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/CountryProvince.png"><span
								id="definition">Country Province</span></td>
							<td><input type="checkbox" id="countryprovince"
								style="margin-left: 10px;"></td>
						</tr>
<tr>
						<td class="Icon "><img
							src="${pageContext.request.contextPath}/resources/NetworkImages/antenna (3).png"><span
							id="definition">BTS</span></td>
						<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/antenna (3).png"
								style="filter: invert(11%) sepia(96%) saturate(6351%) hue-rotate(245deg) brightness(53%) contrast(131%);"><span
								id="definition">NodeB</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>

						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/antenna (3).png"
								style="filter: invert(14%) sepia(94%) saturate(7479%) hue-rotate(3deg) brightness(109%) contrast(115%);"><span
								id="definition">ENodeB</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/hexagons.png"><span
								id="definition">2G_Cell</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/hexagons.png"
								style="filter: invert(11%) sepia(96%) saturate(6351%) hue-rotate(245deg) brightness(53%) contrast(131%);"><span
								id="definition">3G_Cell</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/hexagons.png"
								style="filter: invert(14%) sepia(94%) saturate(7479%) hue-rotate(3deg) brightness(109%) contrast(115%);"><span
								id="definition">4G_Cell</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/diamond (1).png"><span
								id="definition">BSC</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/diamond (1).png  "
								style="filter: invert(40%) sepia(83%) saturate(898%) hue-rotate(205deg) brightness(97%) contrast(99%);"><span
								id="definition">RNC</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/routervisio.png"
								style="width: 16px; height: 16px;"><span id="definition">Router</span></td>
							<td><input type="checkbox" id="router"
								style="margin-left: 10px;"></td>
						</tr>
						<tr>
							<td class="Icon "><img
								src="${pageContext.request.contextPath}/resources/NetworkImages/switchvisio.png"
								style="width: 16px; height: 16px;"><span id="definition">Ethernet
									Switch</span></td>
							<td><input type="checkbox" style="margin-left: 10px;"></td>
						</tr>
						<tr>

					</table>

				</div>


				<div id="Options" class="tabcontent">

					<div class="searchcontainer" style="margin-top: 1px;">

						<div class="search">
							<input type="text" class="searchTermLayers" id="searchOption"
								onkeyup="FilterOptions('options')"
								placeholder="Search within the Options ..">
							<button type="submit" class="searchButton">
								<i class="fa fa-search"></i>
							</button>
						</div>


						<table id="optionstable" style="margin-top: 10px;">
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Groups Colors</span></td>
							</tr>

							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">KPI BenchMark</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Operator Colors</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox" checked><span
									style="margin-left: 10px;">Sites Labels</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Connection Labels</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Link Distance</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Link Capacity</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Link Configuration</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">MW Links</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">EL Links</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">FO Links</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Condense Links</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Capacity Line Thickness</span></td>
							</tr>
							<tr>
								<td class="title "><input type="checkbox"><span
									style="margin-left: 10px;">Capacity Line Coloring</span></td>
							</tr>
						</table>

					</div>
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

			</div>
			<!-- End of layers, options and Boq part -->
		</div>
		<!-- End of Left Div that contains the Tree div, arrows div, layers, options and Boq div -->

		<!--  Beginning of GIS part -->
		<div id="right">

			<div id="mapContainer"></div>
			<div id="coords"
				style="font-size: 13px; color: black; font-family: 'Times New Roman', Times, serif; font-weight: bold; background: #fbfbfb; opacity: 0.8;"></div>
		</div>
		<!--  Ending of GIS part -->

	</div>
	<!-- End of mainDiv that contains the left side and right side -->

	<div id="modalOrderTree" class="modal fade" tabindex="-1" role="dialog">
		<div
			class="modal-dialog modal-dialog-scrollable  modal-dialog-centered ">
			<div class="modal-content">
				<div class="modal-header" style="background-color: white;">
					<h5 class="modal-title" style="margin-top: -2px;"
						id="mapoperations">Check Order For Tree Display</h5>
					<button type="button" name="closePopup" class="close"
						data-dismiss="modal" id="mapoperations">
						<i class='fa fa-times'></i>
					</button>
					<a class="close modalMinimize ml-3"> <i
						class='fa fa-minus icon-to-change' id="mapoperations"></i>
					</a>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md">
							<ul id="sortable"></ul>

						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="sortableSubmit" type="button" class="btn btn-primary"
						onclick="SubmitModel()">Submit</button>
					<button id="closeModalOrderTree" type="button"
						class="btn btn-secondary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>


	<div class="container">
		<div id="mapModal" class="modal fade" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div
				class="modal-dialog  modal-dialog-scrollable  modal-dialog-centered ">
				<div class="modal-content">
					<div class="modal-header" style="background-color: white;">
						<h5 class="modal-title" style="margin-top: -2px;"
							id="mapoperations">Map Operations</h5>
						<button type="button" name="closePopup" class="close"
							data-dismiss="modal" id="mapoperations">
							<i class='fa fa-times'></i>
						</button>
						<a class="close modalMinimize ml-3"> <i
							class='fa fa-minus icon-to-change' id="mapoperations"></i>
						</a>
					</div>
					<div class="modal-body">
<!-- 
						<div class="container">
							<div style="width: 100%;">
								<div class="row top-btn-filter">


									<input type="text" id="pac-input"
										class="controls input-field form-control" type="text"
										placeholder="Search for location" style="width: 200px;" />
									<div class="row top-btn-filter"
										style="margin-left: 12px; margin-top: -8px;">
										<button id="get" class="btnGet ">Get Directions</button>
										<button id="getDistance" class="btnGet">Get Distance</button>

										<div id="msg" style="margin-top: 23px; margin-left: 15px;"></div>
									</div>

									<div class="row" style="margin-bottom: 15px;">
										<input id="origin-input" class="controls" type="text"
											placeholder="Enter an origin location"
											style="width: 70%; max-width: 400px;" /> <input
											id="destination-input" class="controls" type="text"
											placeholder="Enter a destination location"
											style="width: 70%; max-width: 400px;" />
										<div id="mode-selector" class="row"
											style="width: 220px; margin-top: 15px; max-width: 400px;">
											<input type="radio" name="type" id="changemode-walking"
												checked="checked" /> <label for="changemode-walking">Walk</label>
											<input type="radio" name="type" id="changemode-driving" /> <label
												for="changemode-driving">Drive</label>
										</div>
									</div>
								</div>
							</div>
						</div> -->

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>

function panTo(newLat, newLng) {
	if (panPath.length > 0) {
		  // We are already panning...queue this up for next move
		  panQueue.push([newLat, newLng]);
	} else {
		  // Lets compute the points we'll use
		  panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
		  var curLat = map.getCenter().lat();
		  var curLng = map.getCenter().lng();
		  var dLat = (newLat - curLat)/STEPS;
		  var dLng = (newLng - curLng)/STEPS;

		  for (var i=0; i < STEPS; i++) {
			panPath.push([curLat + dLat * i, curLng + dLng * i]);
		  }
		  panPath.push([newLat, newLng]);
		  panPath.shift();      // LAZY SYNCRONIZED LOCK
		  setTimeout(doPan, 20);
		}
	  }

function tree_prop_selection(selector){
	if(selector==null) {
		selector=".tree li > .TreeSpan";
		console.log("no selector");
		}
	console.log("selector");
		//Site_Boq("");
		$(selector).on('click', function () {
			console.log("clicked");
		if ($(this).parent('li').attr('id') == "initial_ul") {
			markersSite="";
			map.setZoom(6);
			var Nairobi=new google.maps.LatLng(0.796530,37.959529);
			map.setCenter(Nairobi);
		}
	    $("li > .TreeSpan").css("background", "").removeClass("selected-span");
	    $(this).css("background-color", "#97b9cc").addClass("selected-span");
		});
		//$("#" + selector + " li > .TreeSpan").on('click', function (e) {
}
	/*
function tree_selection(selectedSpanId){
	console.log(" TREE SELECTION");
	$("#" + selectedSpanId + " li > .TreeSpan").on('click', function (e) {
		$("li > .TreeSpan").css("background", "").removeClass("selected-span");
		$(this).css("background-color", "#97b9cc").addClass("selected-span");
		e.stopPropagation();
	});
}
function initialSelection(){
	$(".tree li > .TreeSpan").on('click', function (e) {          	
		console.log("INITIAL");	
		Site_Boq("");	
		
		if ($(this).parent('li').attr('id') == "initial_ul") {
			markersSite="";
			map.setZoom(6);
			var Nairobi=new google.maps.LatLng(0.796530,37.959529);
			map.setCenter(Nairobi);
		}
	    $(".tree li > .TreeSpan").css("background", "").removeClass("selected-span");
	    $(this).css("background-color", "#97b9cc").addClass("selected-span");
	    e.stopPropagation();
	});
}
*/
function folder(){
$(".folder").on('click', function (e) { 
	console.log(">>> folder <<<");
 	var children = $(this).parent("li").find(' > ul > li');
    if (children.is(":visible")) {  	
            children.hide();
            $(this).parent('li').children('.folder').find('> svg').removeClass('fa-folder-open').addClass('fa-folder');
     } else {
            children.show();
            $(this).parent('li').children('.folder').find('> svg').removeClass('fa-folder').addClass('fa-folder-open');
     }	        
});		
}



function handleLocationError(browserHasGeolocation, infowindow, pos) {
		infowindow.setPosition(pos);
		infowindow.setContent(
			browserHasGeolocation
			? "Error: The Geolocation service failed."
			: "Error: Your browser doesn't support geolocation."
		);
		infowindow.open(map);
		console.log("@handleLocationError method");
	}
			  	  

//The CenterControl adds a control to the map that recenters the map
//This constructor takes the control DIV as an argument.
function CenterControl(controlDiv, map) {
//Set CSS for the control border.
const controlUI = document.createElement("div");
controlUI.style.backgroundColor = "#fff";
controlUI.style.border = "2px solid #fff";
controlUI.style.borderRadius = "3px";
controlUI.style.boxShadow = "0 2px 6px rgba(0,0,0,.3)";
controlUI.style.cursor = "pointer";
controlUI.style.marginTop = "10px";
controlUI.style.textAlign = "center";
controlUI.title = "Click to recenter the map";
controlDiv.appendChild(controlUI);
//Set CSS for the control interior.
const controlText = document.createElement("div");
controlText.style.color = "rgb(25,25,25)";
controlText.style.fontFamily = "Roboto,Arial,sans-serif";
controlText.style.fontSize = "16px";
controlText.style.lineHeight = "36px";
controlText.style.paddingLeft = "5px";
controlText.style.paddingRight = "5px";
controlText.innerHTML = "Center Map";
controlUI.appendChild(controlText);
//Setup the click event listeners: simply set the map to Chicago.
controlUI.addEventListener("click", () => {
map.setCenter({lat: 33.8547, lng: 35.8623});


});
}


	/* Beginning for tree events and changing the styles */
	 
	 $("#network_tree > ul > li").hover(
	    function() {
	        $("#network_tree > ul > li").css("background","brown");

	    });
	 
	


	///////////////////////////////////////////////

	function Tree_PropagationAppendedNodes(selector) {
    $("#" + selector + " .folder").on('click', function (e) {
        //console.log("clicked folder Nodes and Cells");
        var parentLi = $(this).closest('li');
        var children = parentLi.find(' > ul > li');

        if (children.is(":visible")) {
            children.hide('fast');
            $(this).find('svg').addClass('fa-folder').removeClass('fa-folder-open');
        } else {
            children.show('fast');
            $(this).find('svg').addClass('fa-folder-open').removeClass('fa-folder');
        }

        e.stopPropagation();
    });

    $("#network_tree i").css('margin-right', '5px');
}


	/****************** Tree End ****************/



	    	






	// add conditions for default google maps and its styles 
	$('#default').click(function () {

	setdefaultoptions(map);
		
	})

	$('#blank').click(function () {

	setblankoptions(map);
		
	})

	$('#poi').click(function () {
		SetPoiOptions(map);
		
	})

	$('#road').click(function () {

		SetRoadOptions(map);

	})

	$('#transit').click(function () {
		SetTransitOptions(map);

	})


	$('#water').click(function () {

		SetWaterOptions(map);
		
	})

	// end of custom map styles function by yara 


	// start of blank map styles by yara  


	$('#mapgeography').click(function () {
		SetLightGeographyOptions(map);
	 
	})
	$('#maplabels').click(function () {
		SetCountryBorders(map);	
			 
		})
			
			
	$('#countrynames').click(function () {
				SetCountryNames(map);
			
		})
			
			
	$('#countryprovince').click(function () {
				SetCountryProvice(map);
			
			})

	/******************************  Tree Endd*****************************/

	// Functions to change the  color of arrows 
	 function changecolor(x) {
	    
	      x.style.color='gold';
	    
	   	}

	   function returncolor(x) {
	      x.style.color ='#08526D';
	     
	   	}


	// Function to open the tab

	function opentab(evt, functionality) {
	  var i, tabcontent, tablinks;
	  tabcontent = document.getElementsByClassName("tabcontent");
	  for (i = 0; i < tabcontent.length; i++) {
	    tabcontent[i].style.display = "none";
	  }
	  tablinks = document.getElementsByClassName("tablinks");
	  for (i = 0; i < tablinks.length; i++) {
	    tablinks[i].className = tablinks[i].className.replace(" active", "");
	  }
	  document.getElementById(functionality).style.display = "block";
	  evt.currentTarget.className += " active";
	}

	function returnNormal() {
		$("#network_tree").animate({height: "289px"});
		$(".searchcontainer").css("display", "block");
		$(".tabcontent").animate({height: "250px"});
		$('#arrow_down').show();
		$('#arrow_up').show();
		$('#arrow_up_normal').hide();
		$('#arrow_down_normal').hide();
		}

	function FilterOptions(x){

		 var input, filter, table, tr, td, i, txtValue;
		 var str=x;


		 
			 if(str=='options'){
		  input = document.getElementById("searchOption");
		  filter = input.value.toUpperCase();
		  table = document.getElementById("optionstable");
		  tr = table.getElementsByTagName("tr");

		  // Loop through all table rows, and hide those who don't match the search query
		  for (i = 0; i < tr.length; i++) {
		    td = tr[i].getElementsByTagName("td")[0];
		    if (td) {
		      txtValue = td.textContent || td.innerText;
		      if (txtValue.toUpperCase().indexOf(filter) > -1) {
		        tr[i].style.display = "";
		      } else {
		        tr[i].style.display = "none";
		      }
		    }
		  }
			 }

			 else if(str=='layers'){

				 input = document.getElementById("layerSearch");
				  filter = input.value.toUpperCase();
				  table = document.getElementById("LayersTable");
				  tr = table.getElementsByTagName("tr");
	              var div=table.rows[0];
				  // Loop through all table rows, and hide those who don't match the search query
				  for (i = 0; i < tr.length; i++) {
				    td = tr[i].getElementsByTagName("td")[0];
				    if (td) {
				      txtValue = td.textContent || td.innerText;
				      if (txtValue.toUpperCase().indexOf(filter) > -1) {
				    	 
				         tr[i].style.display = "";
				        
				      } else {
				        tr[i].style.display = "none";
				        
				      }
				    }

				    tr[0].style.display="";

				 }
			 }
		 }


	$(document).ready(function () {


	    
	    $('#tree').toggleClass('orange');
	    $('#gis').toggleClass('orange');

	/* To change the color for the Optional Icons and the Submit buttons */    
	    $('.buttonTog').click(function() { 
	        $(this).toggleClass('activee');       
	    });

	 
	// To let the layer tab opened when the page is loaded


	document.getElementById("Defaultbutton").click();


	$("#left").resizable({
		handles: "e" 
	});

	$("#network_tree").resizable({
		handles: "s", 	

	});

	$('.modal-content').resizable({
		handles: "e" ,
		
		});
	//$('.modal-content').css({'min-width': '700px', 'min-height': '200px'});
	    

	$('.modal-dialog').draggable({
		handle: ".modal-header, .modal-footer"
		});



	$('#mapModal').on('hidden.bs.modal', function() {
		    $(this).find('.modal-content').css({'width': '', 'height': ''});
		})
		
		
		$('#modalOrderTree').on('hidden.bs.modal', function() {
		    $(this).find('.modal-content').css({'width': '', 'height': ''});
		})
		
		
		
	//reset popup position after it has been dragged and closed

	$('body').on('hidden.bs.modal', function() { 
		$('.modal-dialog').css({'top': '', 'left':''});
	})


	$(".modalMinimize").on("click", function(){
	  
	  $(".modal-body").slideToggle();
	  $(".modal-footer").slideToggle();
	  
	  //Toggle between minimize/maximize icons
		var iconToChange = $('.icon-to-change');
		  if(iconToChange.hasClass('fa-window-restore')){
			 iconToChange.removeClass('fa-window-restore')
		       .addClass('fa-minus')
		  }
		  else{
			 iconToChange.addClass('fa-window-restore')
		       .removeClass('fa-minus')
		  }    		         
	  }); // end minimize/maximize fct



	    //Later use  
	   $('#arrow_up').click(function() { 

		 $("#network_tree").animate({height: "0px"});
		 $(".searchcontainer").css("display", "none");
		 $(".tabcontent").animate({height: "613px"});
	   	$('#arrow_down').hide();
	   	$('#arrow_up').hide();
	   	$('#arrow_down_normal').show();
	   	       
	    });	
	    $('#arrow_down').click(function() {
	    	$(".searchcontainer").css("display", "block");
	    	$("#network_tree").animate({height: "572px"});
	    	$('#arrow_down').hide();
	    	$('#arrow_up').hide();
	    	$('#arrow_up_normal').show();
	    	
	    
	    });

	   
			var TreeArray=[];

			TreeArray.push("Site");//>>>
			TreeArray.push("Node");////>>>> Site/Node/Cell Case
			TreeArray.push("Cell");//>>>

			/* Global Auto Complete Search */
			
			$("#autocompliteSearch").autocomplete({
				 source: function(request, response) {
					 var searchkey = $("#autocompliteSearch").val();
					  $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetSearchEngine',
			                 data: {
				                    "searchkey":searchkey,
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                       response(data.listSearch);			                     
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
					 }, 
					 minLength:0, maxShowItems: 40, scroll:true,
			   
			    
			});
			$("#autocompliteSearch").focus(function(){
		        	if (this.value == ""){
		            	$(this).autocomplete("search");
		        	}						
			});
				

	//Auto Complete For searchoption

			$( "#sortable" ).sortable({
			    cancel: ".unsortable_sup",
			    items: "li:not(.unsortable)",
			});
			$( "#sortable" ).disableSelection();

			
	});

	//Draw List of Popup Model
	function DrawList() {

		 var select=[];
	    $("#sortable").empty();
	    
	    $(".buttonTog").each(function () {
	        if ($(this).hasClass("activee")) {
	            var buttonname = $(this).attr('name');
	            var buttonid = $(this).attr('id');
					select.push(buttonid);				
	        }
	    });  
	    select=select.toString();
	    var sort = DefaultSort(select);
	    if(sort!=null){
	    var order = sort[0];
	    var name = sort[1]; 
	    console.log(name)
	   for(i=0;i<order.length && i<name.length;i++){ 
		    
		 var li_element= "<li class='ui-state-default' id= li_"+ order[i] + " ><span class='ui-icon ui-icon-arrowthick-2-n-s' style='color:#000' ></span> "+ name[i] + "</li>"; 
	    $("#sortable").append(li_element);      
	    }  
    
	   $('#li_cellBtn').addClass('unsortable');
	   $('#li_cellBtn').css("color","black").css("opacity",".65");
	   $('#li_nodeBtn').addClass('unsortable');
	   $('#li_nodeBtn').css("color","black").css("opacity",".65");
	   $('#li_supplierBtn').addClass('unsortable_sup');
	   $('#li_supplierBtn').css("color","black").css("opacity",".65");
	   var poSel=["PO","Item","Site","Node Type","Node"];
	   var suppSel=['Supplier', 'Site', 'Node', 'Cell'];
	   
	   if((name.length == poSel.length) && name.every(el => poSel.includes(el)))
		   {
		   $('#li_poBtn').addClass('unsortable');
		   $('#li_poBtn').css("color","black").css("opacity",".65");
		   $('#li_itemBtn').addClass('unsortable');
		   $('#li_itemBtn').css("color","black").css("opacity",".65");
		   $('#li_siteBtn').addClass('unsortable_sup');
		   $('#li_siteBtn').css("color","black").css("opacity",".65");
		   $('#li_nodeTypeeBtn').addClass('unsortable_sup');
		   $('#li_nodeTypeeBtn').css("color","black").css("opacity",".65");
		   }
	  
	   if((name.length == suppSel.length) && name.every(el => suppSel.includes(el)))
	   {
	   $('#li_siteBtn').addClass('unsortable_sup');
	   $('#li_siteBtn').css("color","black").css("opacity",".65");
	   }
	   
	   
	   //$( '#li_cellBtn' ).disableSelection();
	$("#modalOrderTree").modal('show'); 
	    }	
	} // End of DrawList function 

	//Sort List w.r.to selection to display in popup Model
	function DefaultSort(arr) {	
		 switch (arr)
		 {
		case "siteBtn,nodeBtn,cellBtn":
			{	
				var order =["siteBtn","nodeBtn","cellBtn"];
				var name = ["Site","Node","Cell"];
			
				return [order,name];
			
			} break;
		/////////////////////////////////////////////////
		case"siteBtn,nodeBtn,cellBtn,EnterpriseBtn":
		{	
			var order =["siteBtn","nodeBtn","cellBtn"];
			var name = ["Site","Node","Cell","Enterprise"];
		
			return [order,name];
		
		} break;
		/////////////////////////////////////////////////
		case "nodeBtn,cellBtn,nodeTypeeBtn":
		{
		var order =["nodeBtn","cellBtn","nodeTypeeBtn"];
		var name = ["Node Type","Node","Cell"];

		return [order,name];
		}
		break;
		/////////////////////////////////////////////
		case "nodeBtn,cellBtn,nodeTypeeBtn,EnterpriseBtn":
		{
		var order =["nodeBtn","cellBtn","nodeTypeeBtn"];
		var name = ["Node Type","Node","Cell","Enterprise"];

		return [order,name];
		}
		break;
		case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn":
		//case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,EnterpriseBtn":
		{	
			var order =["siteBtn","nodeTypeeBtn","nodeBtn","cellBtn"];
			var name = ["Site","Node Type","Node","Cell"];
		
			return [order,name];
		
		} break;
		/////////////////////////////////////////////////
		
		case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,EnterpriseBtn":
		{	
			var order =["siteBtn","nodeTypeeBtn","nodeBtn","cellBtn"];
			var name = ["Site","Node Type","Node","Cell","Enterprise"];
		
			return [order,name];
		
		} break;
		
		/////////////////////////////////////////////////
		case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn":
		{	
			var order =["siteBtn","nodeTypeeBtn","nodeBtn","cellBtn"];
			var name = ["Site","Node Type","Node","Cell"];
		
			return [order,name];
		
		} break;
		
		case "siteBtn,nodeBtn,cellBtn,nodeTypeeBtn,supplierBtn":
		{	
			var order =["supplierBtn","siteBtn","nodeTypeeBtn","nodeBtn","cellBtn"];
			var name = ["Supplier","Site","Node Type","Node","Cell"];
		
			return [order,name];
		
		} break;

		case "siteBtn,nodeBtn,cellBtn,supplierBtn":
		{	
			var order =["supplierBtn","siteBtn","nodeBtn","cellBtn"];
			var name = ["Supplier","Site","Node","Cell"];
		
			return [order,name];
		
		} break;

		case "siteBtn,itemBtn,poBtn":
		case "poBtn":
		{	
			var order =["poBtn","siteBtn","itemBtn"];
			var name = ["PO","Site","Item"];
		
			return [order,name];
		
		} break;

		case "siteBtn,nodeBtn,nodeTypeeBtn,itemBtn,poBtn":
			{

			var order =["poBtn","itemBtn","siteBtn","nodeTypeeBtn","nodeBtn"];
			var name = ["PO","Item","Site","Node Type","Node"];

			return [order,name];
			}break;
		
		default:
		{			
					alert("Selection is not available");
		}
			
		 }	
	 }
	
	// sumbit through button directly not popup
	function SubmitDefault()
	{
		$('#removeFilter').hide();
		
		var select=[];	
		
		 $(".buttonTog").each(function () {
		        if ($(this).hasClass("activee")) {
		            var buttonid = $(this).attr('id');
						select.push(buttonid);				
		        }
		    });  
		   
		    select=select.toString();
		    console.log(select);
		 Sumbitselection(select);
		}  

	var optionaltreeorder=[];

	function SubmitModel() {
	  //  console.log("SUBMIT POPUP");
	    var optionaltreeorder1 = [];
	    
	    $('#sortable li').each(function () {
	        optionaltreeorder1.push(this.id);
	    });
	    
	 	const activeeDomainButtons = document.querySelectorAll(".activee.domain");
	    for (let i = 0; i < activeeDomainButtons.length; i++) {
	      const button = activeeDomainButtons[i];
	      optionaltreeorder1.push("li_"+button.id);
	    }

	  	//console.log("optionaltreeorder1....",optionaltreeorder1);
	    optionaltreeorder1 = optionaltreeorder1.toString();
	   // console.log("optionaltreeorder......", optionaltreeorder1);
	    $('#modalOrderTree').modal('hide');
	    Sumbitselection(optionaltreeorder1);
	}


						
	//when typing in search 
		$('.searchTerm').each(function(k, v){
		var obj = $(this);
		obj.keyup(function(){
			
			document.getElementById("mapContainer").innerHTML ="";
			document.getElementById("network_tree").innerHTML ="";									
			document.getElementById("hidden_input").value="SNC";
			 	 map = new google.maps.Map(document.getElementById("mapContainer"), {
				 mapTypeControl: false,
				 center: { lat: -33.8688, lng: 151.2195 },
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
	//when search term > or equal 3 characters 		
		if(obj.val().length >= 3){
		// Execute submission
		var srch=obj.val();
		var filteredsites = search(srch, lst,1);
		var filterednodes = search(srch, listNodes,2);
		var filteredcells = search(srch, listCells,1)

		Dupsites=[];
		var str="<ul>	<li id='initial_ul' class='folder' style='color: #08526D'><span class='folder' ><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li>	</ul>";
		$("#network_tree").append(str);
		var str="";	
		var siteFrag = document.createDocumentFragment();
		for (i = 0; i < filteredsites.length; i++) {			// siteId, wareName	 
		if (!Dupsites.includes(filteredsites[i][2])){
		Dupsites.push(filteredsites[i][2]);		
		var str = "<li class='Site' id='" +filteredsites[i][2]+ "' style='color: #FFC300' ><span class='tree-span' onclick='filteredSitescore("+filteredsites[i][2]+")' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'  style='margin-left:5px;'></i> "+filteredsites[i][1]+"</span>";	
		str+= "<ul><li  id='" +filteredsites[i][2]+"_f' class='NodeFolder' style='display:none;color: #08526D'  ><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		siteFrag.appendChild(div);	
		}
		}
		document.getElementById('initial_ul').appendChild(siteFrag);
		DupNsites=[];
		var nodeFrag = document.createDocumentFragment();
		for (n = 0; n < filterednodes.length; n++){
		for (i = 0; i < lst.length; i++) {	// siteId, wareName
		if (lst[i][2]==filterednodes[n][4]){
		if (!DupNsites.includes(lst[i][2])){
		DupNsites.push(lst[i][2]);
		if(DupNsites!=Dupsites){				
		Dupsites.push(lst[i][2]);
		
		var str = "<li class='Site' id='" +lst[i][2]+ "'><span class='tree-span' onclick='filteredSitespan("+lst[i][2]+")' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x' style='margin-left:5px;'></i> "+lst[i][1]+"</span>";
		str+= "<ul><li id='" +lst[i][2]+"_f' class='NodeFolder' ><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span>";
		
		
		str += "<ul><li class='Node' id='"+filterednodes[n][4]+"' style='color: #FFC300'><span class='folder'  onclick='filteredNodescore("+filterednodes[n][4]+")' > <i class='fa fa-server'></i>"+filterednodes[n][2]+"</span>";	
		str+= "<ul><li  id='"+filterednodes[n][0]+"_f' class='CellFolder' style='display:none;color: #08526D'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul></li></ul></li></ul></li></ul></li></ul>";
		tree_prop_general();
		tree_PropInitial_li();
		const div = document.createElement('ul');
		div.innerHTML = str;
		nodeFrag.appendChild(div);
		}
		}
		}
		}	
		}
		document.getElementById('initial_ul').appendChild(nodeFrag);
		
		var cellFrag = document.createDocumentFragment();
		var str="";
		for(p=0;p<filteredcells.length;p++) {
			for (n = 0; n < lst.length; n++) {	
			if (lst[n][2]==filteredcells[p][3]){				
			if(!Dupsites.includes(lst[n][2])){				
			Dupsites.push(lst[n][2]);	
			
			str = "<ul><li class='Site' id='" +lst[n][2]+ "'><span class='folder'> </span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x' style='margin-left:5px;'></i> "+lst[n][1]+"</span>";
			str+= "<ul><li id='" +lst[n][2]+"_fr' class='NodeFolder'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";

			for (j = 0; j < listNodes.length; j++) {
			if (lst[j][2]==listNodes[j][4]){
			str+= "<ul><li class='Nodes' id='" +listNodes[j][0]+"_"+listNodes[j][4]+ "'><span class='folder'> </span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x' style='margin-left:5px;'></i> "+listNodes[j][2]+"</span>";
			str+= "<ul><li id='"+listNodes[j][4]+"_"+listNodes[j][0]+"_f' class='CellFolder'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul></li></ul>";
			}	
			}	
			str+= "<ul><li class='Cells' id='"+filteredcells[p][0]+"_"+filteredcells[p][1]+"' style='color: #FFC300'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x' style='margin-left:5px;'></i> "+filteredcells[p][1]+"</span>";
			Tree_PropagationAppendedCells();
			}
			}
			}
			const div = document.createElement('ul');
			div.innerHTML = str;
			cellFrag.appendChild(div);
			}
			document.getElementById('initial_ul').appendChild(cellFrag);

		//draw map for whole searched sites
		var allDrawnSites=[];
		for (var i=0; i < lst.length; i++) {		
			for(var j=0; j < Dupsites.length; j++) {
			if (lst[i][2]==Dupsites[j]){
			allDrawnSites.push(lst[i]);		
			}
			}
			}
		CreateMap_StNdCell(allDrawnSites,map);
		
		
		}


		
		//Search bar empty after searching finished 
		 if(obj.val().length == 0){ 
			//document.getElementById("mapContainer").innerHTML ="";
			document.getElementById("network_tree").innerHTML ="";									
			document.getElementById("hidden_input").value="SNC";
			CreateMap_StNdCell(lst,map);
			CreateTree_StNdCell(lst,map);
		 }		
		});
		});
	//fct for filtered sites, panning and node cell retrieve
		function filteredSitescore(n)
		{
	     var tst=Array.from(n);
	    console.log(tst);
		 
	 	 
		 if (tst.length > 0) {
	         selectedItem=tst[0].id;
	       }
		 else {
			 var selectedItem=n.id;
		 }
		 tree_Prop("#"+selectedItem+ "> span");
		tree_Prop("#"+selectedItem+ "_f > span");	
		var sitesNCreated=[];
		markersSite="";
		$("#"+selectedItem+" > span").on('click',
		function (e) {					
		Site_Boq(selectedItem);	
		if(selectedItem!=markersSite)
		{
		var selMarker="";					
		markerId=selectedItem;
		selMarker=markers[markerId];
		var latSitee = selMarker.getPosition().lat();
		var lngSitee = selMarker.getPosition().lng();					
		selMarker.setIcon(icon2);
		position=selMarker.getPosition();
		site_wave(map,position);
		panTo(latSitee, lngSitee);					
		window.infowindow.setContent(selMarker.data);
		window.infowindow.open(map,selMarker);
		const pos = new google.maps.LatLng(latSitee,lngSitee);					
		if(markersSite!="")
		{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(icon2);					
		}
		markersSite="";	
		markersSite=selectedItem;				
		map.setZoom(15);		
		var x = 'findSiteNodes_Cells';
		var y=['"selectedItem":selectedItem,"NodesAlreadyCreated":"false"'];
		if(!sitesNCreated.includes(selectedItem))
		{
		sitesNCreated.push(selectedItem);
		$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/resources/js/Network/NetworkTree.js/'+x,
		data: {
		  "selectedItem":selectedItem,
		},
		dataType: "json",
		success: function (data) {							        	
		if (data != null) {
			var SiteChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;		
			var listNodes=data.listNodes;
			if(listNodes!=null) {
			if(SiteChildrenLength<listNodes.length){
		Create_TreeNode_Cell(listNodes,"findSiteNodes_Cells",SiteChildrenLength,false,true,2,"null",4,"null",selectedItem);
			nodeCreated=true;
			}	
			}
		}
		},
		error: function(result) {
		alert("Error");
		}
		});
		}	
		}
		});	
		}
		/////////////////////////////
	//fct for sites panning  only
			function filteredSitespan(n)
		{
	     var tst=Array.from(n);
		
	 	 if (tst.length > 0) {
	         selectedItem=tst[0].id;
	       }
		 else {
			 var selectedItem=n.id;
		 }
	 	 
	 	 var sitesNCreated=[];
		markersSite="";
		$("#"+selectedItem+" > span").on('click',
		function (e) {					
		Site_Boq(selectedItem);	
		if(selectedItem!=markersSite)
		{
		var selMarker="";					
		markerId=selectedItem;
		selMarker=markers[markerId];
		var latSitee = selMarker.getPosition().lat();
		var lngSitee = selMarker.getPosition().lng();					
		selMarker.setIcon(icon2);
		position=selMarker.getPosition();
		site_wave(map,position);
		panTo(latSitee, lngSitee);					
		window.infowindow.setContent(selMarker.data);
		window.infowindow.open(map,selMarker);
		const pos = new google.maps.LatLng(latSitee,lngSitee);					
		if(markersSite!="")
		{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(icon2);					
		}
		markersSite="";	
		markersSite=selectedItem;				
		map.setZoom(15);			
		}
		});	
		}
			
	//Nodes panning and cell retreieve	
		 var NCellCreated=[];  
	  var markersSite="";
	  function filteredNodescore(n)
	   {f
		   var tst=Array.from(n);
			
		 	 if (tst.length > 0) {
		         selectedItem=tst[0].id;
		       }
			 else {
				 var selectedItem=n.id;
			 }
		   console.log("SiteName"+selectedItem);
		   tree_Prop("#"+selectedItem+ "> span");
			tree_Prop("#"+selectedItem+ "_f > span");
		   $("#"+selectedItem+ "> span").on('click',function () {
	 var selectedNode=$(this).parent().attr('id');
	 if(selectedItem!=markersSite)
	 {
	 	console.log("==============///////////clicked"+selectedItem);
	 var selMarker="";
	 markerId=selectedItem;	
	 Site_Boq(selectedItem);			
	 selMarker=markers[markerId];
	 var latSitee = selMarker.getPosition().lat();
	 var lngSitee = selMarker.getPosition().lng();					
	 selMarker.setIcon(icon2);
	 position=selMarker.getPosition();
	 site_wave(map,position);
	 panTo(latSitee, lngSitee);
	 infowindow.setContent(selMarker.data);
	 infowindow.open(map,selMarker);
	 if(markersSite!="")
	 {
	 	var otherMarkers=markers[markersSite];			
	 	otherMarkers.setIcon(icon2);																				
	 }
	 markersSite="";	
	 	markersSite=selectedItem;		
	 	map.setZoom(15);
	 	}
	if(!NCellCreated.includes(selectedNode))
		{
		NCellCreated.push(selectedNode);
		 $.ajax({
				  type: "GET",
	 contentType: "application/json; charset=utf-8",
	 url: getContext()+'/resources/js/Network/NetworkTree.js/findNode_Cells',
	 data: {
	               "selectedNode":selectedNode,
	},
	dataType: "json",
	success: function (data) {							        	
	    if (data != null) {
	   	var NdChildrenLength=$("#" +selectedNode+"_f").find(' > ul > li').length;		
	console.log("listCells"+data.listCells);
	var listCells=data.listCells;
	var dFrag = document.createDocumentFragment();
	if(NdChildrenLength<listCells.length){							            	
	for(j=0;j<listCells.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
		{												
	var str= "<ul><li class='Cells' id='" + listCells[j][0] +"' style='display:none' class='folder'>";					  				
	str += "<span class='tree-span' style='margin-left:21px;'><i class='fa fa-vector-square'></i>"+listCells[j][1]+"</span></li></ul>";
	const div = document.createElement('ul'); 
	div.innerHTML = str;
	dFrag.appendChild(div);
	$("#"+selectedNode+"_f").append(str);
																																						
				}
	tree_prop_general();
	document.getElementById(selectedNode+"_f").appendChild(dFrag);
	    	}								            		                     
	    }
	},
	error: function(result) {
	    alert("Error");
							         }
							     });
					}
						});		
					} 
	  //TO BE OPTIMIZED
		//Search Tree
		//Search Tree method
		//Search Site - node - cell arrays and return search terms
		 function search(nameKey, myArray,index){
			var results = [];					
			for (var i=0; i < myArray.length; i++) {
			if (myArray[i][index].toLowerCase().includes(nameKey.toLowerCase())){
			results.push(myArray[i]);		
			}
			}
			return results;
			}		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
//TO BE DELETED
		function tree_prop_general(){
	console.log(" tree porp general in network tree");
	$('.tree li > .TreeSpan').on('click', function (e) {		
		$("#initial_ul").find(' > ul > li').css("background", "").removeClass("selected-span");
		$(".tree li > .TreeSpan").css("background", "").removeClass("selected-span");
		$(this).css("background-color", "#97b9cc").addClass("selected-span");
		e.stopPropagation();
	});
/*	$('.tree li:has(ul)').addClass('parent_li').find(' > .tree-span').attr(
	'title', 'Collapse this branch'); */ 
//	$("#network_tree i").css('margin-right', '5px');
}
		
		/* To draw the route between two points based on Get Direction button click */
	function routeDrawing(source, destination, directionsDisplay, directionsService){

		var request={
			origin:source,
			destination:destination,
			travelMode:'DRIVING'
		};
		
		directionsService.route(request,function(result,status){	
			if(status=='OK'){
				directionsDisplay.setDirections(result);
			}	
		});
	}
		
	//Drawing a line offside between 2 nodes
	function drawStraightPath(Long1,Lat1,long2,lat2,map){ 
		var path = [
			new google.maps.LatLng(Long1,Lat1),
			new google.maps.LatLng(long2,lat2)
			];
		var pathOptions = { path: path, strokeColor: "red", strokeWeight: 2 }
		var myPath = new google.maps.Polyline(pathOptions);
		myPath.setMap(map);
		//drawStraightPath({33.585599,35.384544},{33.88894,35.49442});
	}	
	
	function Tree_Propagation(){
		 $('.tree li > span').on('click', function (e) {

	     	$("#initial_ul").find(' > ul > li').css("background", "").removeClass("selected-span");
	        
	         $(".tree li > span").css("background", "").removeClass("selected-span");

	         $(this).css("background-color", "#97b9cc").addClass("selected-span");



	         e.stopPropagation();
	     });
		
	        $('.tree li:has(ul)').addClass('parent_li').find(' > .tree-span').attr(
	                'title', 'Collapse this branch'); 

	            $('.tree li.parent_li > .tree-span').on('click',
	                function (e) {
	                    var children = $(this).parent('li.parent_li').find(
	                        ' > ul > li');
	                    if (children.is(":visible")) {
	                        children.hide('fast');
	                        $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

	                    } else {
	                        children.show('fast');
	                       $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
	                    }
	                    e.stopPropagation();
	                });

	            $('.folder').on('click', function (e) {
	                        var children = $(this).parent('li.parent_li').find(' > ul > li');
	                        if (children.is(":visible")) {
	                            children.hide('fast');
	                            $(this).attr('title', 'Expand this branch').find(
	                            ' > svg').addClass('fa-folder').removeClass(
	                                'fa-folder-open');

	                        } else {
	                            children.show('fast');
	                            $(this).attr('title', 'Collapse this branch').find(
	                            ' > svg').addClass('fa-folder-open')
	                            .removeClass('fa-folder');
	                           
	                        }

	                        e.stopPropagation();
	                    });

	            $("#network_tree i").css('margin-right', '5px');

	            var last_ul = $(".parent_li").find("ul:last-child");
	            var last_li = $(last_ul).find("li:last-child");
	            $(last_ul).addClass("last-ul");
	            $(".last-ul").children("li").addClass("last-node");


	            $("#left").resizable({
	            	handles: "e" 
	            });

	            $("#network_tree").resizable({handles: "s", 	            		             
	            });	                    
	}



	function Tree_PropagationInit(i){

		$("#initial_ul > .folder").eq(i).on('click',
	            function (e) {
	            
	                var children = $(this).parent('li.parent_li').find(
	                    ' > ul > li');
	                if (children.is(":visible")) {
	                    children.hide('fast');
	                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

	                } else {
	                    children.show('fast');
	                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
	                }
	                e.stopPropagation();
	            });



	    $('.Site > .folder').eq(i).on('click',
	            function (e) {
	           
	                var children = $(this).parent('li.parent_li').find(
	                    ' > ul > li');
	                if (children.is(":visible")) {
	                    children.hide('fast');
	                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

	                } else {
	                    children.show('fast');
	                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
	                }
	                e.stopPropagation();
	            });
	    
	    
	    $('.tree li > span').eq(i).on('click', function (e) {
			// console.log(" .tree network index new");

		     	$("#initial_ul").find(' > ul > li').css("background", "").removeClass("selected-span");	        
		         $(".tree li > span").css("background", "").removeClass("selected-span");
		         $(this).css("background-color", "#97b9cc").addClass("selected-span");
		         e.stopPropagation();
		     });


		 $('.Site > .tree-span').eq(i).on('click',
		            function (e) {
		            
		                var children = $(this).parent('li.parent_li').find(
		                    ' > ul > li');
		                if (children.is(":visible")) {
		                    children.hide('fast');
		                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

		                } else {
		                    children.show('fast');
		                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
		                }
		                e.stopPropagation(i);
		            });


		     $('.NodeFolder > .folder').eq(i).on('click',
	                function (e) {
	                
	                    var children = $(this).parent('li.parent_li').find(
	                        ' > ul > li');
	                    if (children.is(":visible")) {
	                        children.hide('fast');
	                        $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

	                    } else {
	                        children.show('fast');
	                       $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
	                    }
	                    e.stopPropagation();
	                });

		     $('.NodeTypeFolder > .folder').eq(i).on('click',
		                function (e) {
		                
		                    var children = $(this).parent('li.parent_li').find(
		                        ' > ul > li');
		                    if (children.is(":visible")) {
		                        children.hide('fast');
		                        $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

		                    } else {
		                        children.show('fast');
		                       $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
		                    }
		                    e.stopPropagation();
		                });

		     
		 	$('.tree li:has(ul)').addClass('parent_li').find(' > .tree-span').attr(
			        'title', 'Collapse this branch'); 

			 $("#network_tree i").css('margin-right', '5px');

	}
	
	function Tree_PropagationAppendedCells(){

		console.log(" tree porp appended cell in network index new");
			$('.tree li > .TreeSpan').on('click', function (e) {

		     	$("#initial_ul").find(' > ul > li').css("background", "").removeClass("selected-span");
		        
		         $(".tree li > .TreeSpan").css("background", "").removeClass("selected-span");

		         $(this).css("background-color", "#97b9cc").addClass("selected-span");

		         e.stopPropagation();
		     });


			
			
		$('.tree li:has(ul)').addClass('parent_li').find(' > .tree-span').attr(
		        'title', 'Collapse this branch'); 

		 $("#network_tree i").css('margin-right', '5px');
		
	}
	 function updateMap(){

		    // deselect all the choices checked before
		      $("#default").prop('checked', false);
		      $("#landscape").prop('checked', false);
		      $("#water").prop('checked', false);
		      $("#transit").prop('checked', false);
		      $("#poi").prop('checked', false);
		      $("#road").prop('checked', false);

			 button = document.getElementById('blankMap');
			 data = button.getAttribute('data-map');
			 //console.log("Data is"+data);
		     $('#nodeBtn').removeClass('activee');
		     $('#cellBtn').removeClass('activee');
		    
			   var centerlatlng = new google.maps.LatLng(Lat, Long);
			   var myOptions = {
			       zoom: 7,
			       center: centerlatlng,
			       disableDefaultUI : true,
			       zoomControl: true,
					zoomControlOptions: {
						position: google.maps.ControlPosition.LEFT_CENTER,
					},
			       scaleControl: true,
			       
			   };
		 map = new google.maps.Map(document.getElementById("mapContainer"), myOptions);

			   map.setOptions({ styles: style});
			   const controlDiv2 = document.createElement("div");
			   DefaultZoomControl(controlDiv2, map);
			   map.controls[google.maps.ControlPosition.LEFT_CENTER].push(controlDiv2);
			   LatLanMouse(map);
		var icon={
					   
					   path: google.maps.SymbolPath.CIRCLE,
				        fillColor: 'green',
				        fillOpacity: 0.9,
				        strokeColor: 'green',
				        strokeOpacity: 0.9,
				        strokeWeight: 1,
				        scale: 4	   
			   };
			   if(!(lst=="" || lst==null)){
			   for(i=0;i<lst.length;i++){

				    markerId=lst[i][2];
				    
					var latSite=lst[i][3];
					var lngSite=lst[i][4];
					const pos = new google.maps.LatLng(latSite,lngSite);

					
					const marker = new google.maps.Marker({
					        position: pos,
					        map:map,
					        icon:icon,
					        ID:markerId
					       
					         	    });}
			   }
			 }
	 
	//Function to reset map and tree before functions submitting	
		var sitesPNtCreated=[];
		var sitesNtCreated=[];
		var sitesNCreated=[];
		var NSitesCreated=[];
		var NdNCreated=[];
		var NtNodeNCreated=[];
		var NodeCreated=[];
		var POCreated=[];
		var itemCreated=[];
		var SupSCreated=[];
		var SupNtCreated=[];
		var NtSiteCreated=[];
		var CellCreated=[];
		
	function resetMAP(){
		sitesPNtCreated=[];
		sitesNtCreated=[];
		sitesNCreated=[];
		NdNCreated=[];
		NtNodeNCreated=[];
		sitesNtCreated=[];
		NSitesCreated=[];
		NodeCreated=[];
		POCreated=[];
		itemCreated=[];
		SupSCreated=[];
		SupNtCreated=[];
		NtSiteCreated=[];
		CellCreated=[];
		$(document).ready(function() {
			HoverClickNetworkSpans();  
		});

	    	document.getElementById("mapContainer").innerHTML ="";
			document.getElementById("network_tree").innerHTML ="";
			document.getElementById("autocompliteSearch").value = "";
			// $('#boq_table').empty();
			map = new google.maps.Map(document.getElementById("mapContainer"), {
										 mapTypeControl: false,
										 center: { lat: -33.8688, lng: 151.2195 },
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
									map.setOptions({ minZoom: 3, maxZoom: 28});																					
			document.getElementById("hidden_input").value="SNC";
			const controlDiv2 = document.createElement("div");
			DefaultZoomControl(controlDiv2, map);
			map.controls[google.maps.ControlPosition.LEFT_CENTER].push(controlDiv2);
		}

//routeDrawing
//drawStraightPath
//Tree_Propagation
//Tree_PropagationInit
//Tree_PropagationAppendedCells
//updateMap
//resetMAP

	
	</script>
</html>