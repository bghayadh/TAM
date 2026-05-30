function getSelectedFiberCableRows(sourceLat,sourceLng,fiberId){
	console.log("getSelectedFiberCableRows");
	dict = [];	
	dictTubes=[];
	dictStrands=[];
	dictTubesAuxiliary=[];
	dictStrandsAuxiliary=[];
	var fiberID ="";

	$("#auxiliaryTable > tbody").find('input[name="record"]').each(function(){

			var name=$(this).parent().parent().children('td[name="auxiliary_Name"]').children('input').val();
			var lng=$(this).parent().parent().children('td[name="auxiliary_Longitude"]').children('input').val();
			var lat=$(this).parent().parent().children('td[name="auxiliary_Latitude"]').children('input').val();
			var aux_seqSorting=$(this).parent().parent().children('td[name="fiberSeq"]').children('input').val();
			var driving_distance = $(this).parent().parent().children('td[name="auxDrivingDist"]').children('input').val();
			var geo_distance = $(this).parent().parent().children('td[name="auxGeoDistance"]').children('input').val();																									  
			//console.log("name::::: "+name);
			if (driving_distance == "null" || driving_distance == null) 
				driving_distance = 0;
			if (geo_distance == "null" || geo_distance == null)
				geo_distance = 0;				
			distance=haversine_distance(sourceLat,sourceLng,lat,lng);
			
			if(name=="") {
				name = "Auxiliary_Point "+aux_seqSorting;
			}
			dict.push({
			"aux_Name" : name,								    
			"aux_Longitude" : lng,
			"aux_Latitude" : lat,
			"distance_From_Source":distance,
			"driving_distance":driving_distance,
			"geo_distance":geo_distance,
			"aux_seqSorting":aux_seqSorting
			
			});
		
	});
	
	if(actionFiberContext === "Update") {
		 if (window['fiberCheckPoints_'+selectedFiberContext] == "checked" || window['fiberCheckRealPoints_'+selectedFiberContext] == "checked" || checkLabel =="checked" ) {
			 showHideAllPoints(selectedFiberContext,"fiberCheckSequence","Hide");
		 }
	
	if(window['fiberCheckRealPoints_'+selectedFiberContext] == "checked") {		
		window['fiberCheckRealPoints_'+selectedFiberContext] = "unchecked";
	 }	
	 if(window['fiberCheckSequence_'+selectedFiberContext] == "checked") {		
		window['fiberCheckSequence_'+selectedFiberContext] = "unchecked";
	  }
	  if(window['fiberCheckPoints_'+selectedFiberContext] == "checked") {		
		  window['fiberCheckPoints_'+selectedFiberContext] = "unchecked";
	  } 
		 
	}
	
	
	
	$("#tubesTable > tbody").find('input[name="record"]').each(function(){
	
			var tubeId=$(this).parent().parent().children('td[name="tubeId"]').children('input').val();
			var tubeSource=$(this).parent().parent().children('td[name="tubeSource"]').children('input').val();
			var tubeDestination=$(this).parent().parent().children('td[name="tubeDestination"]').children('input').val();
			var srcLng=$(this).parent().parent().children('td[name="tubeSource_Longitude"]').children('input').val();
			var srcLat=$(this).parent().parent().children('td[name="tubeSource_Latitude"]').children('input').val();
			var destLng=$(this).parent().parent().children('td[name="tubeDestination_Longitude"]').children('input').val();
			var destLat=$(this).parent().parent().children('td[name="tubeDestination_Latitude"]').children('input').val();
			var tubeName=$(this).parent().parent().children('td[name="tubeName"]').children('input').val();
			var tubetype=$(this).parent().parent().children('td[name="tubetype"]').children('select').val();
			var tubedeployment=$(this).parent().parent().children('td[name="tubedeployment"]').children('select').val();
			var tubenetlevel=$(this).parent().parent().children('td[name="tubenetlevel"]').children('select').val();
			var tubeowner=$(this).parent().parent().children('td[name="tubeowner"]').children('select').val();
			var srcCity=$(this).parent().parent().children('td[name="tubeSource_City"]').children('input').val();
			var dstCity=$(this).parent().parent().children('td[name="tubeDestination_City"]').children('input').val();
			var tubeLength=$(this).parent().parent().children('td[name="tubeLength"]').children('input').val();
			var tubeNumber=$(this).parent().parent().children('td[name="fiberTubeNumber"]').children('input').val();
			var tubeColor=$(this).parent().parent().children('td[name="fiberTubeColor"]').children('select').val();
			var drawType=$(this).parent().parent().children('td[name="drawingTypeFiberTube"]').children('select').val();
			var totalGeoDist=$(this).parent().parent().children('td[name="fiberTubeTotalGeoDistance"]').children('input').val();
			var totalDrivDist=$(this).parent().parent().children('td[name="fiberTubeTotalDrivDistance"]').children('input').val();
			var distanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberTubeDistLstAuxToDest"]').children('input').val();
			var drivDistanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberTubeDrivDistLstAuxToDest"]').children('input').val();
			
			dictTubes.push({
			"tubeId":tubeId,
			"tubeSource" : tubeSource,
			"tubeDestination" : tubeDestination,		    
			"tubeSource_Longitude" : srcLng,
			"tubeSource_Latitude" : srcLat,
			"tubeDestination_Longitude" : destLng,
			"tubeDestination_Latitude" : destLat,
			"tubeName":tubeName,
			"tubetype":tubetype,
			"tubedeployment":tubedeployment,
			"tubenetlevel":tubenetlevel,
			"tubeowner":tubeowner,
			"SourceCity": srcCity,
			"DestCity":dstCity,
			"tubeLength":tubeLength,
			"tubeNumber":tubeNumber,
			"tubeColor":tubeColor,
			"drawingType":drawType,
			"totalLength":tubeLength,
			"distanceLstAuxToDest":distanceLstAuxToDest,
			"totalDrivDistance":totalDrivDist,
			"totalGeoDistance":totalGeoDist,
			"drivDistanceLstAuxToDest":drivDistanceLstAuxToDest
			});
			showHideAllPoints(tubeId,"tubeCheckSequence","Hide");
			
			if(typeof window["Auxpts_Tubes"+tubeId] !=='undefined'){
				//showHideTubeStrandAuxPoints(window["Auxpts_Tubes"+tubeId],"","","","","",tubeId,"tubeCheckSequence","Hide","Auxiliary");
				allAuxDictTube=allAuxDictTube.concat(window["Auxpts_Tubes"+tubeId]); 
					

			}else{
				allAuxDictTube=allAuxDictTube.concat(window["Auxpts_Tubes_Save"+tubeId]); 
			}
	});

	$("#strandsTable > tbody").find('input[name="record"]').each(function(){
	
			var strandId=$(this).parent().parent().children('td[name="Strand_ID"]').children('input').val();
			var tubeId=$(this).parent().parent().children('td[name="Tube_ID"]').children('input').val();
			var strandSource=$(this).parent().parent().children('td[name="strandSource"]').children('input').val();
			var strandDestination=$(this).parent().parent().children('td[name="strandDestination"]').children('input').val();
			var srcLng=$(this).parent().parent().children('td[name="strandSource_Longitude"]').children('input').val();
			var srcLat=$(this).parent().parent().children('td[name="strandSource_Latitude"]').children('input').val();
			var destLng=$(this).parent().parent().children('td[name="strandDestination_Longitude"]').children('input').val();
			var destLat=$(this).parent().parent().children('td[name="strandDestination_Latitude"]').children('input').val();
			var StrandName=$(this).parent().parent().children('td[name="strandName"]').children('input').val();
			var strandtype=$(this).parent().parent().children('td[name="strandtype"]').children('select').val();
			var stranddeployment=$(this).parent().parent().children('td[name="stranddeployment"]').children('select').val();
			var strandnetlevel=$(this).parent().parent().children('td[name="strandnetlevel"]').children('select').val();
			var strandowner=$(this).parent().parent().children('td[name="strandowner"]').children('select').val();
			var strandLength=$(this).parent().parent().children('td[name="strandLength"]').children('input').val();
			var srcCity=$(this).parent().parent().children('td[name="strandSource_City"]').children('input').val();
			var dstCity=$(this).parent().parent().children('td[name="strandDestination_City"]').children('input').val();
			var strandNumber=$(this).parent().parent().children('td[name="fiberStrandNumber"]').children('input').val();
			var strandColor=$(this).parent().parent().children('td[name="fiberStrandColor"]').children('select').val();
			var drawType=$(this).parent().parent().children('td[name="drawingTypeFiberStrand"]').children('select').val();
			var totalGeoDist=$(this).parent().parent().children('td[name="fiberStrandTotalGeoDistance"]').children('input').val();
			var totalDrivDist=$(this).parent().parent().children('td[name="fiberStrandTotalDrivDistance"]').children('input').val();
			var distanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberStrandDistLstAuxToDest"]').children('input').val();
			var drivDistanceLstAuxToDest =$(this).parent().parent().children('td[name="fiberStrandDrivDistLstAuxToDest"]').children('input').val();			
			
			dictStrands.push({
			"tubeId":tubeId,
			"strandId":strandId,
			"strandSource" : strandSource,
			"strandDestination" : strandDestination,
			"strandSource_Longitude" : srcLng,
			"strandSource_Latitude" : srcLat,
			"strandDestination_Longitude" : destLng,
			"strandDestination_Latitude" : destLat,
			"StrandName":StrandName,
			"strandtype":strandtype,
			"stranddeployment":stranddeployment,
			"strandnetlevel":strandnetlevel,
			"strandowner":strandowner,
			"strandLength":strandLength,
			"SourceCity": srcCity,
			"DestCity":dstCity,
			"strandNumber":strandNumber,
			"strandColor":strandColor,
			"distanceLstAuxToDest":distanceLstAuxToDest,
			"totalDrivDistance":totalDrivDist,
			"totalGeoDistance":totalGeoDist,
			"drivDistanceLstAuxToDest":drivDistanceLstAuxToDest
			
			});
			showHideAllPoints(strandId,"strandCheckSequence","Hide");
			
			if(typeof window["Auxpts_Strands"+strandId] !=='undefined'){
				//showHideTubeStrandAuxPoints(window["Auxpts_Strands"+strandId],"","","","","",strandId,"strandCheckSequence","Hide","Auxiliary");
				allAuxDictStrand=allAuxDictStrand.concat(window["Auxpts_Strands"+strandId]);								
			}else{

				allAuxDictStrand=allAuxDictStrand.concat(window["Auxpts_Strands_Save"+strandId]);
			}
	});
}
