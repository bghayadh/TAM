<%-- AreaSearchPopup.jsp : standalone, non-modal floating panel for the Area Search --%>
<div id="areaSearchPopup" style="display: none;">

	<div class="areaSearchHeader">
		<h5 class="areaSearchTitle">Area Search</h5>
		<div class="areaSearchHeaderBtns">
			<button type="button" id="areaSearchMinBtn" class="areaSearchClose" title="Minimize"><i class="fa fa-minus areaSearchIconMin"></i><i class="fas fa-window-maximize areaSearchIconRestore"></i></button>
			<button type="button" id="areaSearchCloseBtn" class="areaSearchClose" title="Close"><i class="fa fa-times"></i></button>
		</div>
	</div>

	<div class="areaSearchBody">

		<div class="areaSearchToolbar">
			<button type="button" id="areaSearchDrawBtn" class="btn btn-primary btn-sm"><i class="fas fa-vector-square"></i> Select Area (no Ctrl)</button>
			<button type="button" id="areaSearchClearBtn" class="btn btn-secondary btn-sm">Clear</button>
			<span id="areaSearchInfo"></span>
		</div>
		<div class="areaSearchNote">Hold <b>Ctrl</b> and drag on the map to select an area. Only the markers currently displayed on the map are listed.</div>

		<ul class="nav nav-tabs" id="areaSearchInnerTabs" role="tablist">
			<li class="nav-item"><a class="nav-link active" id="areaSearchMan-tab" data-toggle="tab" href="#areaSearchMan" role="tab" aria-controls="areaSearchMan" aria-selected="true">Manhole</a></li>
			<li class="nav-item"><a class="nav-link" id="areaSearchHand-tab" data-toggle="tab" href="#areaSearchHand" role="tab" aria-controls="areaSearchHand" aria-selected="false">Handhole</a></li>
		</ul>

		<div class="tab-content areaSearchTabContent">

			<div class="tab-pane active" id="areaSearchMan" role="tabpanel" aria-labelledby="areaSearchMan-tab">
				<div class="areaSearchTableWrap">
					<table id="areaSearchManhole" class="table table-striped table-bordered table-sm">
						<thead>
							<tr>
								<th style="min-width: 30px; text-align: center;"><input type="checkbox" id="areaSearchSelectAllMan"></th>
								<th style="min-width: 80px; text-align: center;">Manhole ID</th>
								<th style="min-width: 180px; text-align: center;">Manhole Name</th>
								<th style="min-width: 110px; text-align: center;">Longitude</th>
								<th style="min-width: 110px; text-align: center;">Latitude</th>
								<th style="min-width: 160px; text-align: center;">Project Name</th>
							</tr>
						</thead>
						<tbody id="areaSearchManTBody"></tbody>
					</table>
				</div>
				<div class="areaSearchTotal"><b>Total Manholes:</b><input type="text" id="areaSearchTotalMan" readonly></div>
			</div>

			<div class="tab-pane" id="areaSearchHand" role="tabpanel" aria-labelledby="areaSearchHand-tab">
				<div class="areaSearchTableWrap">
					<table id="areaSearchHandhole" class="table table-striped table-bordered table-sm">
						<thead>
							<tr>
								<th style="min-width: 30px; text-align: center;"><input type="checkbox" id="areaSearchSelectAllHand"></th>
								<th style="min-width: 80px; text-align: center;">Handhole ID</th>
								<th style="min-width: 180px; text-align: center;">Handhole Name</th>
								<th style="min-width: 110px; text-align: center;">Longitude</th>
								<th style="min-width: 110px; text-align: center;">Latitude</th>
								<th style="min-width: 160px; text-align: center;">Project Name</th>
							</tr>
						</thead>
						<tbody id="areaSearchHandTBody"></tbody>
					</table>
				</div>
				<div class="areaSearchTotal"><b>Total Handholes:</b><input type="text" id="areaSearchTotalHand" readonly></div>
			</div>

		</div>
	</div>
</div>
