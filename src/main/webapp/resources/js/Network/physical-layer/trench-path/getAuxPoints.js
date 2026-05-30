function getSelectedTrenchAux(sourceLat,sourceLng){
	dict=[];
	slctDel = [];
	slctDelTrenches = [];
	dictTrenchAuxiliary=[];
	
	$("#auxiliary_trenchTable > tbody").find('input[name="record"]').each(function(){
		
	//if($(this).is(":checked")){
			
		slctDelTrenches.push($(this).parent().parent().children('td[name="auxiliaryTrench_Longitude"]').children('input').val());
	
		
		var name=$(this).parent().parent().children('td[name="auxiliaryTrench_Name"]').children('input').val();
		var lng=$(this).parent().parent().children('td[name="auxiliaryTrench_Longitude"]').children('input').val();
		var lat=$(this).parent().parent().children('td[name="auxiliaryTrench_Latitude"]').children('input').val();
		var aux_seqSorting=$(this).parent().parent().children('td[name="TrenchSeq"]').children('input').val();
		var drivingDistance = $(this).parent().parent().children('td[name="auxDrivingDistTrench"]').children('input').val();
		var geoDistance = $(this).parent().parent().children('td[name="auxGeoDistanceTrench"]').children('input').val();																									  
		if (drivingDistance == "null" || drivingDistance == null) {
			drivingDistance = 0;
		}
		if (geoDistance == "null" || geoDistance == null) {
			geoDistance = 0;	
		}
		
		distance=haversine_distance(sourceLat,sourceLng,lat,lng);
		if(name=="") {
			name = "Auxiliary_Point "+aux_seqSorting;
		}
		
			dict.push({						
				"auxTrench_Name" : name,								    
				"aux_Longitude" : lng,
				"aux_Latitude" : lat,
				"distance_From_Source":distance,
				"aux_seqSorting":aux_seqSorting,
				"drivingDistance":drivingDistance,
				"geoDistance":geoDistance
			});
	
	});
	
	if(actionTrenchContext == "Update" || trenchAction == "Append from map") {
		 if (window['trenchCheckPoints_'+selectedTrenchContext] == "checked" || window['trenchCheckRealPoints_'+selectedTrenchContext] == "checked" || checkLabel =="checked" ) {
			 showHideAllPoints(selectedTrenchContext,"trenchCheckSequence","Hide");
		 }
		 if(window['trenchCheckRealPoints_'+selectedTrenchContext] == "checked") {		
			window['trenchCheckRealPoints_'+selectedTrenchContext] = "unchecked";
		}	
		if(window['trenchCheckSequence_'+selectedTrenchContext] == "checked") {		
			window['trenchCheckSequence_'+selectedTrenchContext] = "unchecked";
		}
		if(window['trenchCheckPoints_'+selectedTrenchContext] == "checked") {		
			window['trenchCheckPoints_'+selectedTrenchContext] = "unchecked";
		}
	}
}