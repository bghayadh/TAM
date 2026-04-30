var junctionFlag = 0;
function getJunction(type, url, id, tr) {
    $('body').append('<div id="loading"><img id="loading-image" src="' + getContext() + '/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if (junctionFlag == 0) {
        $.ajax({
            type: "GET",
            contentType: "application/json; charset=utf-8",
            url: getContext() + '/getJunction',
            data: {
            },
            dataType: "json",
            success: function(data) {
                if (data != null) {
                    if (data.searchResult != "Failed") {
                        $("#Junction_f_CurrentPhysicalLayer input[type=checkbox]").unbind();// removed 	
                        createJunction(data.JunctionList);
                        if ($('#Junction_f_CurrentPhysicalLayer .AllJunctions').is(':checked') || $('#junctionCheckAllBoq').is(':checked')) {
                            junctionLayerCheckAll();
                        }
                        $('#Junction_f_CurrentPhysicalLayer .TreeSpan').css("display", "inline"); // The purpose of this command is to let the background color width in mouse hovering or mouse select to be same span text width
                        var children = $("#Junction_f_CurrentPhysicalLayer").find(' > ul > li');
                        children.show("fast");
                        $(this).parent('').children(".folder").find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
                        junctionFlag = 1;
                    }
                    $("#loading").remove();
                }
            },
            error: function(result) {
                alert("Error");
            }
        });
    }
    else {
        $("#loading").remove();
    }


}
function create_Junction_Marker_Click(Id, Name, Long, Lat, markers, marker_Cluster, Type, city) {
    const pos = new google.maps.LatLng(Lat, Long);
    var data = "<div>" + Name + "</div>";
    var mapIcon;
    var markerType = "";

    iconJunction = {
        url: getContext() + "/resources/NetworkImages/junctionOrange.png", // url
        scaledSize: new google.maps.Size(20, 20), // scaled size		
    };

    markerClusterJunction.setOptions({
        minimumClusterSize: 2,
        styles: [
            {
                url: getContext() + '/resources/clusterIcons/orangeCluster.png',
                height: 60,
                width: 60,
                anchorText: [-3, -3]
            },
        ],
        calculator: function(markers, numStyles) {
            if (markers.length >= 1) return { text: markers.length, index: 1 };
        }
    });



    if (markers == markersJunction && marker_Cluster == markerClusterJunction) {
        mapIcon = iconJunction;
        markerType = "Junction";
    }

    if (!markers[Id]) {

        Mapmarker = new google.maps.Marker({
            position: pos,
            data: data,
            icon: mapIcon,
            ID: Id,
        });
        markers.metadata = { id: Id };
        markers[Id] = Mapmarker;
        markers.push(Mapmarker);



        google.maps.event.addListener(Mapmarker, "click", function(e) {
            var IdSelected = this.ID;
            nodeFileId = $("#" + IdSelected).parents().eq(3).attr("id").split("initial_ul_")[1];
            var projectInitial = $("#initial_ul_Projects").find('>ul > #' + nodeFileId);
            var projectRel = $("#" + nodeFileId + "").find('>ul > #initial_ul_' + nodeFileId);
            var childrenInitial = $("#initial_ul_" + nodeFileId + "").find(' > ul > li');
            var children = $("#" + markerType + "_f_" + nodeFileId + "").find(' > ul > li');

            if (IdSelected != IdSelectedTemp) {
                if (IdSelectedTemp != "") {
                    $("#" + IdSelectedTemp + " > .TreeSpan").removeClass("selected-span");
                    $("#" + IdSelectedTemp + " > .TreeSpan").css("background", "");
                }
                IdSelectedTemp = IdSelected;
            }
            childrenInitial.show('fast');
            if (!children.is(":visible")) {
                children.show();
            }
            projectInitial.show('fast');
            projectRel.show('fast');

            $("#" + IdSelected + " > .TreeSpan").addClass("selected-span");
            $("#" + IdSelected + " > .TreeSpan").css("background-color", "#97b9cc");

            $("#initial_ul_" + nodeFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + nodeFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
            $("#" + markerType + "_f_" + nodeFileId + " > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');

            offset = $("#" + IdSelected).offset().top;
            projectOffset = $("#initial_ul_" + nodeFileId).offset().top;
            offsetTotal = (offset - projectOffset);
            $("#network_tree").animate({ scrollTop: offsetTotal }, "slow");
            if (draw == true) {
                createPathh(e);
            }

            if (deleting == true) {
                deleteAuxPath(e);
                MarkerArrayId.push(Id);
            }
        });

    }

    else {
        if (markers[Id].map != map) {
            markers[Id].setMap(map);
        }
        markers[Id].setPosition(pos);
        markers[Id].data = data;
    }

    $("#" + Id + " .TreeSpan").not($("#" + Id + "_f" + " .TreeSpan")).on('click', function() {

        panTo(markers[Id].getPosition().lat(), markers[Id].getPosition().lng());
        map.setZoom(11);
        if (typeof infowindow !== 'undefined') {
            infowindow.close();
        }
        else {
            infowindow = new google.maps.InfoWindow();
        }

        infowindow.setContent(markers[Id].data);
        infowindow.open(map, markers[Id]);

    });
}




function createJunction(JunctionList) {
    /*	markersJunction = [];
        markerClusterJunction = new MarkerClusterer();
        markerClusterJunction.setMap(map);// to be checked !!!!*/

    if (JunctionList != null) {
        for (i = 0;i < JunctionList.length;i++) {
            window["" + JunctionList[i][0]] = [];
            window["" + JunctionList[i][0]] = JunctionList[i];
            allJunctionsID.push(JunctionList[i][0]);

            str = "<ul><li id='" + JunctionList[i][0] + "' class='JUNCTION' style='display:none;width:100px;'><input type='checkbox' class='Junction' class='filter checkFilter' name='Element' ></input> <span class='TreeSpan' style='color:black;width:195px'><img src='" + getContext() + "/resources/NetworkImages/junction.png'> " + JunctionList[i][1] + "</span></li></ul>";
            $("#Junction_f_" + JunctionList[i][9] + "").append(str);
            create_Junction_Marker_Click(JunctionList[i][0], JunctionList[i][1], JunctionList[i][7], JunctionList[i][8], markersJunction, markerClusterJunction, "Junction", "");
            JunctionCheckFilter(JunctionList[i][0]);
            CreateJunctionClickEvent(JunctionList[i][0], "");
        }

    }
    AllJunctionCheckFilter();
    $(".JUNCTION > .TreeSpan").contextmenu(function() {
        selectedJuncIdContext = $(this).parent().attr('id');
        IdNodeSelectedTemp = $(this).parents().eq(2).attr('id').split("Junction_f_")[1];
        //menuName=singleJunction;	
        menuName = singleJunctionMenu;
        selectedManholeJct = selectedJuncIdContext;
        selectedManIdContext = "";
        selectedJct = selectedJuncIdContext;
        openContext(selectedJuncIdContext, "", singleJunctionMenu, event);
        //openContext(selectedJuncIdContext,"",singleJunction,event);
        //openContext(selectedManholeJct,"",singleJunction,event);

    });

    /*
     * $("#Junction_f_CurrentPhysicalLayer > .TreeSpan,  .Junction_f_projects > .TreeSpan").contextmenu(function(){				
        selectedJuncIdContext=$(this).parent().attr('id');
        IdNodeSelectedTemp=$(this).parent().attr('id').split("Junction_f_")[1];	
        menuName= JunctionMenu;
        selectedJct = selectedJuncIdContext;
        openContext(selectedJuncIdContext,"",JunctionMenu,event);
    	

    });
    */



}

function AllJunctionCheckFilter() {
    $('.AllJunctions').bind("change", function() {
        markerClusterJunction.clearMarkers();

        console.log(" //////////Junction all");
        if ($(this).is(':checked')) {
            $(this).parent().find('.Junction').each(function() {
                $(this).prop('checked', true);
            });

            $("#network_tree").find(".Junction:checked").each(function() {
                id = $(this).parent().attr('id');
                if (markersJunction[id].getMap() == null) {
                    markersJunction[id].setMap(map);
                    markerClusterJunction.addMarker(markersJunction[id]);
                }
            });

            $(this).parent().find('li').each(function() {
                $(this).children('input:checkbox').prop('checked', true);
            });

            if ($("#Junction_f_CurrentPhysicalLayer > .AllJunctions").is(":checked")) {
                $("#junctionCheckAllBoq").prop("checked", true);
            }
        } else {

            $(this).parent().find('.Junction').each(function() {
                $(this).prop('checked', false);
            });

            $("#junctionCheckAllBoq").prop("checked", false);

            if ($("#Junction_f_CurrentPhysicalLayer > .AllJunctions").is(":checked")) {
                $("#junctionCheckAllBoq").prop("checked", true);
            }

            $("#network_tree").find(".Junction:checked").each(function() {

                id = $(this).parent().attr('id');
                if (markersJunction[id].getMap() == null) {
                    markersJunction[id].setMap(map);
                    markerClusterJunction.addMarker(markersJunction[id]);
                }
            });

            $(this).parent().find('li').each(function() {
                $(this).children('input:checkbox').prop('checked', false);
            });


        }
    });
}

function JunctionCheckFilter(Id) {
    $("#" + Id).children('input').on("change", function() {
        var folderID = $(this).parents().eq(4).attr('id');

        var JunctionId = $(this).parent().attr('id');
        console.log("change into " + JunctionId);

        if ($(this).is(':checked')) {

            markersJunction[JunctionId].setMap(map);
            markerClusterJunction.addMarker(markersJunction[JunctionId]);

            $(this).parent().find('li').each(function() {
                $(this).children('input:checkbox').prop('checked', true);
            });
        }
        else {
            if (folderID == "initial_ul_CurrentPhysicalLayer") {
                $("#Junction_f_CurrentPhysicalLayer > .AllJunctions").prop("checked", false);
            }
            else {
                $("#Junction_f_" + folderID + " > .AllJunctions").prop("checked", false);
            }

            //$('.AllJunctions').prop('checked', false);				
            markersJunction[JunctionId].setMap(null);
            markerClusterJunction.removeMarker(markersJunction[JunctionId]);

            $(this).parent().find('li').each(function() {
                $(this).children('input:checkbox').prop('checked', false);
            });

        }
        if ($(this).parents().eq(2).find('.Junction:checked').length == $(this).parents().eq(2).find('.Junction').length) {
            $(this).parents().eq(2).children('input').prop('checked', true);
        }
        else {
            $(this).parents().eq(2).children('input').prop('checked', false);
        }

        if ($("#Junction_f_CurrentPhysicalLayer").find(".Junction:checked").length == 0) {
            $("#junctionCheckAllBoq").prop("checked", false);

        }
        else {
            $("#junctionCheckAllBoq").prop("checked", true);

        }
    });
}

// for mapping
function getSelectedRowsJctMapping() {
    insertJctDictArr = [];
    updateJctDictArr = [];
    if (actionJct == "Update") {

        $("#JctMappingTable > tbody").find('input[name="record"]').each(function() {

            var JctSeq = $(this).parent().parent().children('td[name="JctSeq"]').children('input').val();
            var JctStrandIdSideA = $(this).parent().parent().children('td[name="JctStrandIdSideA"]').children('input').val();
            var JctStrandNameSideA = $(this).parent().parent().children('td[name="JctStrandNameSideA"]').children('input').val();
            var JctTubeIdSideA = $(this).parent().parent().children('td[name="JctTubeIdSideA"]').children('input').val();
            var JctTubeNameSideA = $(this).parent().parent().children('td[name="JctTubeNameSideA"]').children('input').val();
            var JctFiberIdSideA = $(this).parent().parent().children('td[name="JctFiberIdSideA"]').children('input').val();
            var JctFiberNameSideA = $(this).parent().parent().children('td[name="JctFiberNameSideA"]').children('input').val();

            var JctStrandIdSideB = $(this).parent().parent().children('td[name="JctStrandIdSideB"]').children('input').val();
            var JctStrandNameSideB = $(this).parent().parent().children('td[name="JctStrandNameSideB"]').children('input').val();
            var JctTubeIdSideB = $(this).parent().parent().children('td[name="JctTubeIdSideB"]').children('input').val();
            var JctTubeNameSideB = $(this).parent().parent().children('td[name="JctTubeNameSideB"]').children('input').val();
            var JctFiberIdSideB = $(this).parent().parent().children('td[name="JctFiberIdSideB"]').children('input').val();
            var JctFiberNameSideB = $(this).parent().parent().children('td[name="JctFiberNameSideB"]').children('input').val();


            var JctLocationTypeSideA = $(this).parent().parent().children('td[name="JctLocationTypeSideA"]').children('select').val();
            var JctLocationIdSideA = $(this).parent().parent().children('td[name="JctLocationIdSideA"]').children('input').val();
            var JctLocationNameSideA = $(this).parent().parent().children('td[name="JctLocationNameSideA"]').children('input').val();
            var JctWarehouseIdSideA = $(this).parent().parent().children('td[name="JctWarehouseIdSideA"]').children('input').val();
            //
            var JctStrandNBSideA = $(this).parent().parent().children('td[name="JctStrandNBSideA"]').children('input').val();
            var JctTubeNBSideA = $(this).parent().parent().children('td[name="JctTubeNBSideA"]').children('input').val();
            var JctNetworkLevelSideA = $(this).parent().parent().children('td[name="JctNetworkLevelSideA"]').children('select').val();

            var JctStrandNBSideB = $(this).parent().parent().children('td[name="JctStrandNBSideB"]').children('input').val();
            var JctTubeNBSideB = $(this).parent().parent().children('td[name="JctTubeNBSideB"]').children('input').val();
            var JctNetworkLevelSideB = $(this).parent().parent().children('td[name="JctNetworkLevelSideB"]').children('select').val();

            var JctLocationTypeSideB = $(this).parent().parent().children('td[name="JctLocationTypeSideB"]').children('select').val();
            var JctLocationIdSideB = $(this).parent().parent().children('td[name="JctLocationIdSideB"]').children('input').val();
            var JctLocationNameSideB = $(this).parent().parent().children('td[name="JctLocationNameSideB"]').children('input').val();
            var JctWarehouseIdSideB = $(this).parent().parent().children('td[name="JctWarehouseIdSideB"]').children('input').val();

            var jctBoq = [JctSeq, JctStrandIdSideA, JctStrandNameSideA, JctTubeIdSideA, JctTubeNameSideA, JctFiberIdSideA, JctFiberNameSideA,
                JctStrandIdSideB, JctStrandNameSideB, JctTubeIdSideB, JctTubeNameSideB, JctFiberIdSideB, JctFiberNameSideB, JctLocationTypeSideA,
                JctLocationIdSideA, JctLocationNameSideA, JctWarehouseIdSideA, JctStrandNBSideA, JctTubeNBSideA, JctNetworkLevelSideA, JctStrandNBSideB,
                JctTubeNBSideB, JctNetworkLevelSideB, JctLocationTypeSideB, JctLocationIdSideB, JctLocationNameSideB, JctWarehouseIdSideB];


            if (window["JCT_Mapper" + selectedJct]) {

                var jctMappingId = $(this).parent().parent().attr('id');

                index = window["JCT_Mapper" + selectedJct].findIndex(x => x == "" + JctSeq + "," + JctStrandIdSideA + "," + JctStrandNameSideA + "," + JctTubeIdSideA + "," + JctTubeNameSideA + "," + JctFiberIdSideA + "," + JctFiberNameSideA + "," +
                    JctStrandIdSideB + "," + JctStrandNameSideB + "," + JctTubeIdSideB + "," + JctTubeNameSideB + "," + JctFiberIdSideB + "," + JctFiberNameSideB + "," + JctLocationTypeSideA + "," + JctLocationIdSideA + "," + JctLocationNameSideA
                    + "," + JctWarehouseIdSideA + "," + JctStrandNBSideA + "," + JctTubeNBSideA + "," + JctNetworkLevelSideA + "," + JctStrandNBSideB + "," + JctTubeNBSideB + "," + JctNetworkLevelSideB + "," + JctLocationTypeSideB + "," + JctLocationIdSideB + "," + JctLocationNameSideB + "," + JctWarehouseIdSideB);

                if (index == -1 && !window["MAP_JCT" + jctMappingId]) {

                    insertJctDictArr.push({

                        "JctSeq": JctSeq,
                        "JctLocationTypeSideA": JctLocationTypeSideA,
                        "JctLocationIdSideA": JctLocationIdSideA,
                        "JctLocationNameSideA": JctLocationNameSideA,
                        "JctWarehouseIdSideA": JctWarehouseIdSideA,
                        "JctStrandIdSideA": JctStrandIdSideA,
                        "JctStrandNameSideA": JctStrandNameSideA,
                        "JctTubeIdSideA": JctTubeIdSideA,
                        "JctTubeNameSideA": JctTubeNameSideA,
                        "JctFiberIdSideA": JctFiberIdSideA,
                        "JctFiberNameSideA": JctFiberNameSideA,
                        "JctStrandIdSideB": JctStrandIdSideB,
                        "JctStrandNameSideB": JctStrandNameSideB,
                        "JctTubeIdSideB": JctTubeIdSideB,
                        "JctTubeNameSideB": JctTubeNameSideB,
                        "JctFiberIdSideB": JctFiberIdSideB,
                        "JctFiberNameSideB": JctFiberNameSideB,
                        "JctStrandNBSideA": JctStrandNBSideA,
                        "JctTubeNBSideA": JctTubeNBSideA,
                        "JctNetworkLevelSideA": JctNetworkLevelSideA,
                        "JctStrandNBSideB": JctStrandNBSideB,
                        "JctTubeNBSideB": JctTubeNBSideB,
                        "JctNetworkLevelSideB": JctNetworkLevelSideB,
                        "JctLocationTypeSideB": JctLocationTypeSideB,
                        "JctLocationIdSideB": JctLocationIdSideB,
                        "JctLocationNameSideB": JctLocationNameSideB,
                        "JctWarehouseIdSideB": JctWarehouseIdSideB
                    });

                }

                else if (index == -1 && window["MAP_JCT" + jctMappingId] || (JctSeq != window["MAP_JCT" + jctMappingId][0] || JctStrandIdSideA != window["MAP_JCT" + jctMappingId][1] || JctStrandNameSideA != window["MAP_JCT" + jctMappingId][2] || JctTubeIdSideA != window["MAP_JCT" + jctMappingId][3]
                    || JctTubeNameSideA != window["MAP_JCT" + jctMappingId][4] || JctFiberIdSideA != window["MAP_JCT" + jctMappingId][5] || JctFiberNameSideA != window["MAP_JCT" + jctMappingId][6] || JctStrandIdSideB != window["MAP_JCT" + jctMappingId][7]
                    || JctStrandNameSideB != window["MAP_JCT" + jctMappingId][8] || JctTubeIdSideB != window["MAP_JCT" + jctMappingId][9] || JctTubeNameSideB != window["MAP_JCT" + jctMappingId][10]
                    || JctFiberIdSideB != window["MAP_JCT" + jctMappingId][11] || JctFiberNameSideB != window["MAP_JCT" + jctMappingId][12] || JctLocationTypeSideA != window["MAP_JCT" + jctMappingId][17] || JctLocationIdSideA != window["MAP_JCT" + jctMappingId][18] || JctLocationNameSideA != window["MAP_JCT" + jctMappingId][19] || JctWarehouseIdSideA != window["MAP_JCT" + jctMappingId][20]
                    || JctStrandNBSideA != window["MAP_JCT" + jctMappingId][21] || JctTubeNBSideA != window["MAP_JCT" + jctMappingId][22] || JctNetworkLevelSideA != window["MAP_JCT" + jctMappingId][23] || JctStrandNBSideB != window["MAP_JCT" + jctMappingId][24] || JctTubeNBSideB != window["MAP_JCT" + jctMappingId][25] || JctNetworkLevelSideB != window["MAP_JCT" + jctMappingId][26]
                    || JctLocationTypeSideB != window["MAP_JCT" + jctMappingId][27] || JctLocationIdSideB != window["MAP_JCT" + jctMappingId][28] || JctLocationNameSideB != window["MAP_JCT" + jctMappingId][29] || JctWarehouseIdSideB != window["MAP_JCT" + jctMappingId][30])) {

                    // Update to be done
                    updateJctDictArr.push({
                        "JctSeq": JctSeq,
                        "JctLocationTypeSideA": JctLocationTypeSideA,
                        "JctLocationIdSideA": JctLocationIdSideA,
                        "JctLocationNameSideA": JctLocationNameSideA,
                        "JctWarehouseIdSideA": JctWarehouseIdSideA,
                        "JctStrandIdSideA": JctStrandIdSideA,
                        "JctStrandNameSideA": JctStrandNameSideA,
                        "JctTubeIdSideA": JctTubeIdSideA,
                        "JctTubeNameSideA": JctTubeNameSideA,
                        "JctFiberIdSideA": JctFiberIdSideA,
                        "JctFiberNameSideA": JctFiberNameSideA,
                        "JctStrandIdSideB": JctStrandIdSideB,
                        "JctStrandNameSideB": JctStrandNameSideB,
                        "JctTubeIdSideB": JctTubeIdSideB,
                        "JctTubeNameSideB": JctTubeNameSideB,
                        "JctFiberIdSideB": JctFiberIdSideB,
                        "JctFiberNameSideB": JctFiberNameSideB,
                        "JctStrandNBSideA": JctStrandNBSideA,
                        "JctTubeNBSideA": JctTubeNBSideA,
                        "JctNetworkLevelSideA": JctNetworkLevelSideA,
                        "JctStrandNBSideB": JctStrandNBSideB,
                        "JctTubeNBSideB": JctTubeNBSideB,
                        "JctNetworkLevelSideB": JctNetworkLevelSideB,
                        "JctLocationTypeSideB": JctLocationTypeSideB,
                        "JctLocationIdSideB": JctLocationIdSideB,
                        "JctLocationNameSideB": JctLocationNameSideB,
                        "JctWarehouseIdSideB": JctWarehouseIdSideB,
                        "jctMappingId": jctMappingId
                    });

                }
            }
            else {
                insertJctDictArr.push({
                    "JctSeq": JctSeq,
                    "JctLocationTypeSideA": JctLocationTypeSideA,
                    "JctLocationIdSideA": JctLocationIdSideA,
                    "JctLocationNameSideA": JctLocationNameSideA,
                    "JctWarehouseIdSideA": JctWarehouseIdSideA,
                    "JctStrandIdSideA": JctStrandIdSideA,
                    "JctStrandNameSideA": JctStrandNameSideA,
                    "JctTubeIdSideA": JctTubeIdSideA,
                    "JctTubeNameSideA": JctTubeNameSideA,
                    "JctFiberIdSideA": JctFiberIdSideA,
                    "JctFiberNameSideA": JctFiberNameSideA,
                    "JctStrandIdSideB": JctStrandIdSideB,
                    "JctStrandNameSideB": JctStrandNameSideB,
                    "JctTubeIdSideB": JctTubeIdSideB,
                    "JctTubeNameSideB": JctTubeNameSideB,
                    "JctFiberIdSideB": JctFiberIdSideB,
                    "JctFiberNameSideB": JctFiberNameSideB,
                    "JctStrandNBSideA": JctStrandNBSideA,
                    "JctTubeNBSideA": JctTubeNBSideA,
                    "JctNetworkLevelSideA": JctNetworkLevelSideA,
                    "JctStrandNBSideB": JctStrandNBSideB,
                    "JctTubeNBSideB": JctTubeNBSideB,
                    "JctNetworkLevelSideB": JctNetworkLevelSideB,
                    "JctLocationTypeSideB": JctLocationTypeSideB,
                    "JctLocationIdSideB": JctLocationIdSideB,
                    "JctLocationNameSideB": JctLocationNameSideB,
                    "JctWarehouseIdSideB": JctWarehouseIdSideB
                });
            }

        });

    }
    else {

        $("#JctMappingTable > tbody").find('input[name="record"]').each(function() {

            var JctSeq = $(this).parent().parent().children('td[name="JctSeq"]').children('input').val();
            var JctStrandIdSideA = $(this).parent().parent().children('td[name="JctStrandIdSideA"]').children('input').val();
            var JctStrandNameSideA = $(this).parent().parent().children('td[name="JctStrandNameSideA"]').children('input').val();
            var JctTubeIdSideA = $(this).parent().parent().children('td[name="JctTubeIdSideA"]').children('input').val();
            var JctTubeNameSideA = $(this).parent().parent().children('td[name="JctTubeNameSideA"]').children('input').val();
            var JctFiberIdSideA = $(this).parent().parent().children('td[name="JctFiberIdSideA"]').children('input').val();
            var JctFiberNameSideA = $(this).parent().parent().children('td[name="JctFiberNameSideA"]').children('input').val();

            var JctStrandIdSideB = $(this).parent().parent().children('td[name="JctStrandIdSideB"]').children('input').val();
            var JctStrandNameSideB = $(this).parent().parent().children('td[name="JctStrandNameSideB"]').children('input').val();
            var JctTubeIdSideB = $(this).parent().parent().children('td[name="JctTubeIdSideB"]').children('input').val();
            var JctTubeNameSideB = $(this).parent().parent().children('td[name="JctTubeNameSideB"]').children('input').val();
            var JctFiberIdSideB = $(this).parent().parent().children('td[name="JctFiberIdSideB"]').children('input').val();
            var JctFiberNameSideB = $(this).parent().parent().children('td[name="JctFiberNameSideB"]').children('input').val();

            var JctLocationTypeSideA = $(this).parent().parent().children('td[name="JctLocationTypeSideA"]').children('select').val();
            var JctLocationIdSideA = $(this).parent().parent().children('td[name="JctLocationIdSideA"]').children('input').val();
            var JctLocationNameSideA = $(this).parent().parent().children('td[name="JctLocationNameSideA"]').children('input').val();
            var JctWarehouseIdSideA = $(this).parent().parent().children('td[name="JctWarehouseIdSideA"]').children('input').val();

            var JctStrandNBSideA = $(this).parent().parent().children('td[name="JctStrandNBSideA"]').children('input').val();
            var JctTubeNBSideA = $(this).parent().parent().children('td[name="JctTubeNBSideA"]').children('input').val();
            var JctNetworkLevelSideA = $(this).parent().parent().children('td[name="JctNetworkLevelSideA"]').children('select').val();

            var JctStrandNBSideB = $(this).parent().parent().children('td[name="JctStrandNBSideB"]').children('input').val();
            var JctTubeNBSideB = $(this).parent().parent().children('td[name="JctTubeNBSideB"]').children('input').val();
            var JctNetworkLevelSideB = $(this).parent().parent().children('td[name="JctNetworkLevelSideB"]').children('select').val();

            var JctLocationTypeSideB = $(this).parent().parent().children('td[name="JctLocationTypeSideB"]').children('select').val();
            var JctLocationIdSideB = $(this).parent().parent().children('td[name="JctLocationIdSideB"]').children('input').val();
            var JctLocationNameSideB = $(this).parent().parent().children('td[name="JctLocationNameSideB"]').children('input').val();
            var JctWarehouseIdSideB = $(this).parent().parent().children('td[name="JctWarehouseIdSideB"]').children('input').val();

            insertJctDictArr.push({
                "JctSeq": JctSeq,
                "JctLocationTypeSideA": JctLocationTypeSideA,
                "JctLocationIdSideA": JctLocationIdSideA,
                "JctLocationNameSideA": JctLocationNameSideA,
                "JctWarehouseIdSideA": JctWarehouseIdSideA,
                "JctStrandIdSideA": JctStrandIdSideA,
                "JctStrandNameSideA": JctStrandNameSideA,
                "JctTubeIdSideA": JctTubeIdSideA,
                "JctTubeNameSideA": JctTubeNameSideA,
                "JctFiberIdSideA": JctFiberIdSideA,
                "JctFiberNameSideA": JctFiberNameSideA,
                "JctStrandIdSideB": JctStrandIdSideB,
                "JctStrandNameSideB": JctStrandNameSideB,
                "JctTubeIdSideB": JctTubeIdSideB,
                "JctTubeNameSideB": JctTubeNameSideB,
                "JctFiberIdSideB": JctFiberIdSideB,
                "JctFiberNameSideB": JctFiberNameSideB,
                "JctStrandNBSideA": JctStrandNBSideA,
                "JctTubeNBSideA": JctTubeNBSideA,
                "JctNetworkLevelSideA": JctNetworkLevelSideA,
                "JctStrandNBSideB": JctStrandNBSideB,
                "JctTubeNBSideB": JctTubeNBSideB,
                "JctNetworkLevelSideB": JctNetworkLevelSideB,
                "JctLocationTypeSideB": JctLocationTypeSideB,
                "JctLocationIdSideB": JctLocationIdSideB,
                "JctLocationNameSideB": JctLocationNameSideB,
                "JctWarehouseIdSideB": JctWarehouseIdSideB
            });

        });
    }

}

