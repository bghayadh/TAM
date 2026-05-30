function AuxAppendBOQ(auxArray, insertType, OrigiTermination, Target, indx) {

    var markup = "";
    var longitude = "";
    var latitude = "";
    var length = "";
    var auxName = "";
    var auxDrivingDist = 0;
    var auxGeoDistance = 0;

    var flag = 0; // the flag is needed when append to aux from map (because 2arrays are combined in one: Auxs from DB and the aux points from map.)

    $.each(auxArray, function(i, value) {

        if (insertType == "createFromMap") {

            if (auxArray[indx].length == 11) {// to know that this array coming from db

                longitude = value[0]
                latitude = value[1]
                length = value[2]

                if (value[3] == "") {
                    auxName = "";
                }
                //Case of wareHouse
                else if (value[3] != "null") {
                    auxName = value[3] + ":" + value[5] + ":" + value[4];
                }
                else {
                    if (value[4].split("_")[0] == "MH" || value[4].split("_")[0] == "HH" || value[4].split("_")[0] == "DB") {
                        auxName = value[4] + ":" + value[5];
                    }
                    else {
                        auxName = value[5];
                    }
                }


                auxDrivingDist = value[8];
                auxGeoDistance = value[9];
            }
            else {

                if (OrigiTermination == "NoOrigiNotermination" || OrigiTermination == "terminationOnly") {
                    longitude = MarkerArray[indx + 1].position.lng();
                    latitude = MarkerArray[indx + 1].position.lat();
                }
                else if (OrigiTermination == "originationOnly" || OrigiTermination == "origi&termination") {//note
                    longitude = MarkerArray[indx].position.lng();
                    latitude = MarkerArray[indx].position.lat();
                }
                else {
                    longitude = MarkerArray[flag].position.lng();
                    latitude = MarkerArray[flag].position.lat();
                }


                if (auxArray[indx][0] == 'NULL' && auxArray[indx][1] == 'NULL' && auxArray[indx][2] == 'NULL') {
                    auxName = "Auxiliary_Point " + parseInt(indx + 1)

                }
                else {
                    auxName = auxArray[indx][0] + ":" + auxArray[indx][1];
                    //Sequance = parseInt(index)+1
                }

                flag++
            }

        }
        else {
            longitude = value[0]
            latitude = value[1]
            length = value[2]

            if (Target.Action == "Import") {
                if (value[3] == null) {
                    auxName = "null";
                }
                else {
                    auxName = value[3];
                }
            }

            else {

                //Case of insert new row
                if (value[3] == "") {
                    auxName = "";
                }
                //Case of wareHouse
                else if (value[3] != "null" && value[3] != null) {
                    auxName = value[3] + ":" + value[5] + ":" + value[4];
                }
                else if (value[5] && value[5].includes("Auxiliary_Point") == true) {
                    auxName = value[10] + ":" + value[5];
                }

                else {
                    if (value[4] && (value[4].split("_")[0] == "MH" || value[4].split("_")[0] == "HH" || value[4].split("_")[0] == "DB")) {
                        auxName = value[4] + ":" + value[5];
                    }
                    else {
                        auxName = value[5];
                    }
                }
            }
            auxDrivingDist = value[8];
            auxGeoDistance = value[9];
            auxDrivingDist = value[8] === undefined ? 0 : value[8]
            auxGeoDistance = value[9] === undefined ? 0 : value[9]
        }

        //note
        if (Target.target == "fiber") {
            markup += "<tr id='auxiliary_Row" + index + "'><td name='AuxFiber'><input class='rowAboveBelow' id='AuxFiber" + index + "' type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberSeq' class='headcol'><input name='fiberCableSeq'  class='form-control text-input' type='text' value='" + (index + 1) + "' readonly/></td>"
                + "<td name='auxiliary_Name' id='auxiliary_Name" + index + "'><input id='aux_Point" + index + "'  class='form-control text-input' type='text' style='width:250px;position:relative;' value='" + auxName + "'  /></td>"
                + "<td name='auxiliary_Longitude'><input onchange='geoDistanceFlag()' id='aux_Long" + index + "'  name='aux_Long' class='form-control text-input' type='text' style='width:200px;position:relative;' value='" + longitude + "'/></td>"
                + "<td name='auxiliary_Latitude' style='width:200px'> <input onchange='geoDistanceFlag()' id='aux_Lat" + index + "' name='aux_Lat' style='width:200px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + latitude + "' /></td>"
                + "<td name='auxiliary_Length'> <input id='aux_Len" + index + "'style='width:110px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + length + "' /></td>"
                + "<td name='auxDrivingDist'> <input id='auxDrivingDist" + index + "' value='" + auxDrivingDist + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistance'> <input id='auxGeoDistance" + index + "' value='" + auxGeoDistance + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "</tr>";

            index++;
            indx = index;


        }
        else if (Target.target == "strand") {
            markup += "<tr id='auxiliary_Row" + index + "'><td name='AuxStrand' ><input class='rowAboveBelowStrand' type='checkbox' id='AuxStrand" + index + "' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberSeq'><input name='strandSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (index + 1) + "' readonly/></td>"
                + "<td name='auxiliary_NameStrand' id='auxiliary_NameStrand" + index + "'><input id='aux_PointStrand" + index + "'  class='form-control text-input' type='text' value='" + auxName + "' style='width:200px;position:relative;'/></td>"
                + "<td name='auxiliary_Longitude'><input id='auxStrand_Long" + index + "' name='auxStrand_Long'  class='form-control text-input' type='text' value='" + longitude + "' style='width:150px;position:relative;'/></td>"
                + "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxStrand_Lat" + index + "' name='auxStrand_Lat' style='width:150px;position:relative;'  type='text' value='" + latitude + "'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliary_Length'> <input id='aux_LenStrand" + index + "'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + length + "' /></td>"
                + "<td name='auxDrivingDistStrand'> <input id='auxStrandDrivingDist" + index + "' value='" + auxDrivingDist + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceStrand'> <input id='auxStrandGeoDistance" + index + "' value='" + auxGeoDistance + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";

            index++;
            indx = index;


        }
        else if (Target.target == "trench") {

            markup += "<tr id='auxiliaryTrench_Row" + indextrench + "'><td name='AuxTrench' ><input class='rowAboveBelowTrench' type='checkbox' id='Auxtrench" + indextrench + "' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='TrenchSeq'><input name='TrenchSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (indextrench + 1) + "' readonly/></td>"
                + "<td name='auxiliaryTrench_Name' id='auxiliaryTrench_Name" + indextrench + "'><input id='aux_PointTrench" + indextrench + "'  class='form-control text-input' type='text' value='" + auxName + "' style='width:200px;position:relative;'/></td>"
                + "<td name='auxiliaryTrench_Longitude'><input id='aux_Longtrench" + indextrench + "' name='aux_Longtrench'  class='form-control text-input' type='text' value='" + longitude + "' style='width:150px;position:relative;'/></td>"
                + "<td name='auxiliaryTrench_Latitude' style='width:200px'> <input id='aux_Lattrench" + indextrench + "' name='aux_Lattrench' style='width:150px;position:relative;'  type='text' value='" + latitude + "'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliaryTrench_Length'> <input id='aux_LenTrench" + indextrench + "'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + length + "' /></td>"
                + "<td name='auxDrivingDistTrench'> <input id='auxTrenchDrivingDist" + indextrench + "' value='" + auxDrivingDist + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceTrench'> <input id='auxTrenchGeoDistance" + indextrench + "' value='" + auxGeoDistance + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";

            indextrench++;
            indx = indextrench;
        }
        else if (Target.target == "duct") {

            markup += "<tr id='auxiliaryDuct_Row" + indexduct + "'><td name='AuxDuct' ><input class='rowAboveBelowDuct' type='checkbox' id='Auxduct" + indexduct + "' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='DuctSeq'><input name='DuctSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (indexduct + 1) + "' readonly/></td>"
                + "<td name='auxiliaryDuct_Name' id='auxiliaryDuct_Name" + indexduct + "'><input id='aux_PointDuct" + indexduct + "'  class='form-control text-input' type='text' value='" + auxName + "' style='width:200px;position:relative;'/></td>"
                + "<td name='auxiliary_LongitudeDuct'><input id='aux_Longduct" + indexduct + "' name='aux_Longduct'  class='form-control text-input' type='text' value='" + longitude + "' style='width:150px;position:relative;'/></td>"
                + "<td name='auxiliary_LatitudeDuct' style='width:200px'> <input id='aux_Latduct" + indexduct + "' name='aux_Latduct' style='width:150px;position:relative;'  type='text' value='" + latitude + "'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliaryDuct_Length'> <input id='aux_LenDuct" + indexduct + "'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + length + "' /></td>"
                + "<td name='auxDrivingDistDuct'> <input id='auxDuctDrivingDist" + indexduct + "' value='" + auxDrivingDist + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceDuct'> <input id='auxDuctGeoDistance" + indexduct + "' value='" + auxGeoDistance + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td></tr>";

            indexduct++;
            indx = indexduct;
        }
        else if (Target.target == "tube") {
            markup += "<tr id='auxiliary_Row" + index + "'><td name='AuxTube' ><input class='rowAboveBelowTube' id='AuxTube" + index + "'  type='checkbox' style='position:relative;left:20px;top:10px' name='record'></td>"
                + "<td name='fiberSeq' class='headcol'><input name='tubeSeq'  class='form-control text-input' type='text' style='width:60px;position:relative;' value='" + (index + 1) + "' readonly/></td>"
                + "<td name='auxiliary_NameTube' id='auxiliary_NameTube" + index + "'><input id='aux_PointTube" + index + "'  class='form-control text-input' type='text' style='width:200px;position:relative;' value='" + auxName + "' /></td>"
                + "<td name='auxiliary_Longitude'><input id='auxTube_Long" + index + "' name='auxTube_Long' class='form-control text-input' type='text' value='" + longitude + "' style='width:150px;position:relative;' /></td>"
                + "<td name='auxiliary_Latitude' style='width:200px'> <input id='auxTube_Lat" + index + "' name='auxTube_Lat' style='width:150px;position:relative;' value='" + latitude + "'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxiliary_Length'> <input id='aux_LenTube" + index + "'style='width:150px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' value='" + length + "'  /></td>"
                + "<td name='auxDrivingDistTube'> <input id='auxTubeDrivingDist" + index + "' value='" + auxDrivingDist + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "<td name='auxGeoDistanceTube'> <input id='auxTubeGeoDistance" + index + "' value='" + auxGeoDistance + "' style='width:92px;position:relative;'  type='text'  class='form-control text-input ui-widget ui-widget-content ui-corner-all' /></td>"
                + "</tr>";

            index++;
            indx = index;
        }
    });

    if (insertType == "rowAboveChecked") {
        if ($('.' + Target.rowAboveBelow + ':checked').length > 0) {

            var indexAbove = 0;//note
            var indexAb = 0;

            $('.' + Target.rowAboveBelow).each(function() {  //note  
                if (this.checked) {
                    indexAb = indexAbove;
                }

                indexAbove++;
            });
            $("#" + Target.auxiliaryTable + " tr").eq(indexAb + 1).before(markup);

            $("#" + Target.auxiliaryTable + " >tbody").find("tr").find('td[name="' + Target.Aux + '"]').children('input').prop("checked", false);


            $("#" + Target.auxiliaryTable + " >tbody").find("tr").eq(indexAb).find('td[name="' + Target.Aux + '"]').children('input').prop("checked", true);
            var position = $('#' + Target.auxiliaryTable + ' tr:eq(' + indexAb + ')').offset().top;
            // Scroll to new added row
            $('#' + Target.auxiliaryDiv).animate({
                scrollTop: $('#' + Target.auxiliaryDiv).scrollTop() + position
            }, 500);
        }
        else {

            $("#" + Target.auxiliaryTable + " > tbody").append(markup);
            //missing lines 
        }

        newRowIndx = indx - 1;
        AuxPointAutoComplete(Target.auxPtAutocomplete, Target.aux_Point + newRowIndx, Target.aux_Long + newRowIndx, Target.aux_Lat + newRowIndx, Target.SourceLat, Target.SourceLng, Target.DestinationLat, Target.DestinationLng, Target.auxiliaryTable, newRowIndx);

        $("input[id='" + Target.aux_Long + newRowIndx + "']").focusout(function() {
            calculateDistanceSourceDestination($("#" + Target.SourceLat).val(), $("#" + Target.SourceLng).val(), $("#" + Target.DestinationLat).val(), $("#" + Target.DestinationLng).val(), Target.auxiliaryTable);
        });

        $("input[id='" + Target.aux_Lat + newRowIndx + "']").focusout(function() {
            calculateDistanceSourceDestination($("#" + Target.SourceLat).val(), $("#" + Target.SourceLng).val(), $("#" + Target.DestinationLat).val(), $("#" + Target.DestinationLng).val(), Target.auxiliaryTable);
        });

        $("input[id='" + Target.aux_Point + newRowIndx + "']").focusout(function() {
            getTotalDrivingDistance(Target.TargetId, Target.SourceLat, Target.SourceLng, Target.DestinationLat, Target.DestinationLng, Target.target, Target.totalDistanceDrivg, Target.FiberDrivDist);
        });


    }
    else if (insertType == "rowBelowChecked") {
        if ($('.' + Target.rowAboveBelow + ':checked').length > 0) {

            var indexBelow = 0;
            var indexBel = 0;

            $('.' + Target.rowAboveBelow).each(function() {
                if (this.checked) {
                    indexBel = indexBelow;
                }

                indexBelow++;
            });
            $("#" + Target.auxiliaryTable + " tr").eq(indexBel + 1).after(markup);
            $("#" + Target.auxiliaryTable + " >tbody").find("tr").find('td[name="' + Target.Aux + '"]').children('input').prop("checked", false);
            $("#" + Target.auxiliaryTable + " >tbody").find("tr").eq(indexBel + 1).find('td[name="' + Target.Aux + '"]').children('input').prop("checked", true);
            $("#" + Target.auxiliaryTable + " tr").removeClass("ativeRecord")
            $("#" + Target.auxiliaryTable + " > tbody").find("tr").eq(indexBel + 1).addClass("ativeRecord");
        }
        else {

            $("#" + Target.auxiliaryTable + " > tbody").append(markup);
            //missing lines 
        }

        newRowIndx = indx - 1;
        console.log(newRowIndx)

        AuxPointAutoComplete(Target.auxPtAutocomplete, Target.aux_Point + newRowIndx, Target.aux_Long + newRowIndx, Target.aux_Lat + newRowIndx, Target.SourceLat, Target.SourceLng, Target.DestinationLat, Target.DestinationLng, Target.auxiliaryTable, newRowIndx);

        $("input[id='" + Target.aux_Long + newRowIndx + "']").focusout(function() {
            calculateDistanceSourceDestination($("#" + Target.SourceLat).val(), $("#" + Target.SourceLng).val(), $("#" + Target.DestinationLat).val(), $("#" + Target.DestinationLng).val(), Target.auxiliaryTable);
        });

        $("input[id='" + Target.aux_Lat + newRowIndx + "']").focusout(function() {
            calculateDistanceSourceDestination($("#" + Target.SourceLat).val(), $("#" + Target.SourceLng).val(), $("#" + Target.DestinationLat).val(), $("#" + Target.DestinationLng).val(), Target.auxiliaryTable);
        });

        $("input[id='" + Target.aux_Point + newRowIndx + "']").focusout(function() {
            getTotalDrivingDistance(Target.TargetId, Target.SourceLat, Target.SourceLng, Target.DestinationLat, Target.DestinationLng, Target.target, Target.totalDistanceDrivg, Target.FiberDrivDist);
        });
    }
    else if (insertType != "rowBelowChecked" && insertType != "rowAboveChecked") {
        console.log("nothing")
        $("#" + Target.auxiliaryTable + " > tbody").append(markup);


        $("td[name='" + Target.auxiliary_Name + "']").each(function(ind) {
            AuxPointAutoComplete(Target.auxPtAutocomplete, Target.aux_Point + ind, Target.aux_Long + ind, Target.aux_Lat + ind, Target.SourceLat, Target.SourceLng, Target.DestinationLat, Target.DestinationLng, Target.auxiliaryTable, ind);
        });

        $("input[name='" + Target.aux_Long + "']").focusout(function() {
            calculateDistanceSourceDestination($("#" + Target.SourceLat).val(), $("#" + Target.SourceLng).val(), $("#" + Target.DestinationLat).val(), $("#" + Target.DestinationLng).val(), Target.auxiliaryTable);
        });

        $("input[name='" + Target.aux_Lat + "']").focusout(function() {
            calculateDistanceSourceDestination($("#" + Target.SourceLat).val(), $("#" + Target.SourceLng).val(), $("#" + Target.DestinationLat).val(), $("#" + Target.DestinationLng).val(), Target.auxiliaryTable);
        });
    }

    calculateDistanceSourceDestination($("#" + Target.SourceLat).val(), $("#" + Target.SourceLng).val(), $("#" + Target.DestinationLat).val(), $("#" + Target.DestinationLng).val(), Target.auxiliaryTable);

    $("table[id='" + Target.auxiliaryTable + "'] tr").focusin(function() {
        $("table[id='" + Target.auxiliaryTable + "'] tr").removeClass("ativeRecord")
        $(this).addClass("ativeRecord");
    });
}
