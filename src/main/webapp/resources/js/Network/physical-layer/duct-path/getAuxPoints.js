function getSelectedDuctAux(sourceLat, sourceLng) {
    dict = [];
    $("#auxiliary_ductTable > tbody").find('input[name="record"]').each(function() {

        var name = $(this).parent().parent().children('td[name="auxiliaryDuct_Name"]').children('input').val();
        var lng = $(this).parent().parent().children('td[name="auxiliary_LongitudeDuct"]').children('input').val();
        var lat = $(this).parent().parent().children('td[name="auxiliary_LatitudeDuct"]').children('input').val();
        var aux_seqSorting = $(this).parent().parent().children('td[name="DuctSeq"]').children('input').val();
        var drivingDistance = $(this).parent().parent().children('td[name="auxDrivingDistDuct"]').children('input').val();
        var geoDistance = $(this).parent().parent().children('td[name="auxGeoDistanceDuct"]').children('input').val();
        if (drivingDistance == "null" || drivingDistance == null) {
            drivingDistance = 0;
        }
        if (geoDistance == "null" || geoDistance == null) {
            geoDistance = 0;
        }

        if (name == "") {
            name = "Auxiliary_Point " + aux_seqSorting;
        }

        distance = haversine_distance(sourceLat, sourceLng, lat, lng);

        dict.push({
            "auxDuct_Name": name,
            "aux_Longitude": lng,
            "aux_Latitude": lat,
            "distance_From_Source": distance,
            "aux_seqSorting": aux_seqSorting,
            "drivingDistance": drivingDistance,
            "geoDistance": geoDistance
        });
    });

    if (actionDuctContext == "Update" || ductAction == "Append from map") {
        if (window['ductCheckPoints_' + selectedDuctContext] == "checked" || window['ductCheckRealPoints_' + selectedDuctContext] == "checked" || checkLabel == "checked") {
            showHideAllPoints(selectedDuctContext, "ductCheckSequence", "Hide");
        }
        if (window['ductCheckRealPoints_' + selectedDuctContext] == "checked") {
            window['ductCheckRealPoints_' + selectedDuctContext] = "unchecked";
        }
        if (window['ductCheckSequence_' + selectedDuctContext] == "checked") {
            window['ductCheckSequence_' + selectedDuctContext] = "unchecked";
        }
        if (window['ductCheckPoints_' + selectedDuctContext] == "checked") {
            window['ductCheckPoints_' + selectedDuctContext] = "unchecked";
        }
    }
}
