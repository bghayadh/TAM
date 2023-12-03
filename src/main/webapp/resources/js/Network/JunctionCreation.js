var junctionFlag = 0;
function getJunction(type,url,id,tr){
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if(junctionFlag == 0){
    	$.ajax({
    		type: "GET",
    		contentType: "application/json; charset=utf-8",
    		url: getContext()+'/getJunction',
    		data: {					
    		},
    		dataType: "json",
    		success: function (data) {
    			if (data != null) {
    				if (data.searchResult != "Failed") {
					    $("#Junction_f_CurrentPhysicalLayer input[type=checkbox]").unbind();// removed 
    				    console.log(" //////////JunctionList "+data.JunctionList);	
    				    createJunction(data.JunctionList);
    				    if ($('.AllJunctions').is(':checked') || $('#junctionCheckAllBoq').is(':checked')){
    				    	//AllJunctionCheckFilter();
    				    	junctionLayerCheckAll();
						}
    					$('#Junction_f_CurrentPhysicalLayer .TreeSpan').css("display", "inline"); // The purpose of this command is to let the background color width in mouse hovering or mouse select to be same span text width	
    				    treeCollapseFolder("#Junction_f_CurrentPhysicalLayer .folder","fast",".folder");
    					tree_prop_selection("#Junction_f_CurrentPhysicalLayer .TreeSpan");
    					MouseHoveringSpans("#Junction_f_CurrentPhysicalLayer .TreeSpan");		//>>>>>>>>>>>> Hover event in tree elements
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
	

}
function create_Junction_Marker_Click(Id,Name,Long,Lat,markers,marker_Cluster,Type,city) {
	const pos = new google.maps.LatLng(Lat,Long);
	var data="<div>" +Name+ "</div>"; 
	var mapIcon;
	var markerType="";
	
	iconJunction = {
			url: getContext()+"/resources/NetworkImages/junctionOrange.png", // url
			scaledSize: new google.maps.Size(20, 20), // scaled size		
	};
	
	 if(markers == markersJunction && marker_Cluster == markerClusterJunction) {
		 mapIcon = iconJunction;
		 markerType="Junction";
     }
	 
	 if(!markers[Id]){
			
		 Mapmarker = new google.maps.Marker({
			position: pos,
			data: data,
			icon: mapIcon,
			ID: Id,
		});
		markers.metadata = { id: Id };
		markers[Id] = Mapmarker;
		markers.push(Mapmarker);
		

		
		google.maps.event.addListener(Mapmarker, "click", function (e) {
			var IdSelected=this.ID;
			nodeFileId = $("#"+IdSelected).parents().eq(3).attr("id").split("initial_ul_")[1];
			var projectInitial=$("#initial_ul_Projects").find('>ul > #'+nodeFileId);
			var projectRel=$("#"+nodeFileId+"").find('>ul > #initial_ul_'+nodeFileId);
			var childrenInitial=$("#initial_ul_"+nodeFileId+"").find(' > ul > li');
			var children = $("#"+markerType+"_f_"+nodeFileId+"").find(' > ul > li');
			
			if(IdSelected!=IdSelectedTemp){	
				if(IdSelectedTemp!=""){
					$("#"+IdSelectedTemp+" > .TreeSpan").removeClass("selected-span");
					$("#"+IdSelectedTemp+" > .TreeSpan").css("background","");
				}
				IdSelectedTemp=IdSelected;							
			}
			childrenInitial.show('fast');
			if(!children.is(":visible")){
				children.show();
			}
			projectInitial.show('fast');
			projectRel.show('fast');

			$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");

			$("#initial_ul_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			$("#"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');	
			$("#"+markerType+"_f_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');

			offset=$("#"+IdSelected).offset().top;
			projectOffset=$("#initial_ul_"+nodeFileId).offset().top;
			offsetTotal=(offset-projectOffset);
			$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
			if(draw==true){
				createPathh(e);
			}
			
			if(deleting == true){ 
				deleteAuxPath(e);
				MarkerArrayId.push(Id);
			}
		 });	
		
	 } 

		else{
			if(markers[Id].map!=map){
				markers[Id].setMap(map);
			}
			markers[Id].setPosition(pos);
			markers[Id].data=data;
		}
	 
	$("#"+Id+" .TreeSpan").not($("#"+Id+"_f"+" .TreeSpan")).on('click', function () {

	panTo(markers[Id].getPosition().lat(), markers[Id].getPosition().lng());
	map.setZoom(11);
	if(typeof infowindow!== 'undefined'){
		infowindow.close();
	}
	else{
		infowindow = new google.maps.InfoWindow();
	}

	infowindow.setContent(markers[Id].data);
	infowindow.open(map,markers[Id]);
				
	});
}
		
		


function createJunction(JunctionList){
	markersJunction = [];
	markerClusterJunction = new MarkerClusterer();
	markerClusterJunction.setMap(map);// to be checked !!!!
	
	if(JunctionList!=null) {
		for(i=0;i<JunctionList.length;i++){
			window[""+JunctionList[i][0]]=[];
			window[""+JunctionList[i][0]]=JunctionList[i];
			allJunctionsID.push(JunctionList[i][0]);
	
			str="<ul><li id='"+JunctionList[i][0]+"' class='JUNCTION' style='display:none;width:100px;'><input type='checkbox' class='Junction' class='filter checkFilter' name='Element' ></input> <span class='TreeSpan' style='color:black;width:195px'><img src='"+getContext()+"/resources/NetworkImages/junction.png'> "+JunctionList[i][1]+"</span></li></ul>";
			$("#Junction_f_"+JunctionList[i][9]+"").append(str); 
			create_Junction_Marker_Click(JunctionList[i][0],JunctionList[i][1],JunctionList[i][7],JunctionList[i][8],markersJunction,markerClusterJunction,"Junction","");	
			JunctionCheckFilter(JunctionList[i][0]);
			CreateJunctionClickEvent(JunctionList[i][0],"");
		}		
			
	}
	AllJunctionCheckFilter();
	$(".JUNCTION > .TreeSpan").contextmenu(function(){				
		selectedJuncIdContext=$(this).parent().attr('id');
		IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("Junction_f_")[1];
		//menuName=singleJunction;	
		menuName= JunctionMenu;
		selectedManholeJct = selectedJuncIdContext;
		selectedManIdContext = "";
		openContext(selectedJuncIdContext,"",JunctionMenu,event);
		//openContext(selectedJuncIdContext,"",singleJunction,event);
		//openContext(selectedManholeJct,"",singleJunction,event);

	});
	
}

function AllJunctionCheckFilter(){
	$('.AllJunctions').bind("change",function() {
		markerClusterJunction.clearMarkers();

		console.log(" //////////Junction all");
		if ($(this).is(':checked')){
			$(this).parent().find('.Junction').each(function(){		
				$(this).prop('checked', true);
			});
			
			$("#network_tree").find(".Junction:checked" ).each(function(){
				id=$(this).parent().attr('id');
				if(markersJunction[id].getMap()==null){
					markersJunction[id].setMap(map);			
					markerClusterJunction.addMarker(markersJunction[id]);
				}		
			});	
			
			$(this).parent().find('li').each(function(){																	
				$(this).children('input:checkbox').prop('checked', true);		
			});	
			
			if($("#Junction_f_CurrentPhysicalLayer > .AllJunctions").is(":checked") ) {	
				$("#junctionCheckAllBoq").prop("checked",true);
			}	
		}else{
			
			$(this).parent().find('.Junction').each(function(){
				$(this).prop('checked', false);
			});
			
			$("#junctionCheckAllBoq").prop("checked",false);

			if($("#Junction_f_CurrentPhysicalLayer > .AllJunctions").is(":checked") ) {	
				$("#junctionCheckAllBoq").prop("checked",true);
			}	

			$("#network_tree").find(".Junction:checked" ).each(function(){

				id=$(this).parent().attr('id');
				if(markersJunction[id].getMap()==null){
					markersJunction[id].setMap(map);			
					markerClusterJunction.addMarker(markersJunction[id]);	
				}		
			});
			
			$(this).parent().find('li').each(function(){																	   
				$(this).children('input:checkbox').prop('checked', false);		
			});
			

		}
	 });
}

function JunctionCheckFilter(Id){
	$("#"+Id).children('input').on("change",function() {
	var folderID = $(this).parents().eq(4).attr('id');
	
	var JunctionId=$(this).parent().attr('id');
	console.log("change into "+JunctionId);

	if ($(this).is(':checked')){

		markersJunction[JunctionId].setMap(map);
		markerClusterJunction.addMarker(markersJunction[JunctionId]);					
		
		$(this).parent().find('li').each(function(){
			$(this).children('input:checkbox').prop('checked', true);		
		});
}
	else{
		if(folderID == "initial_ul_CurrentPhysicalLayer"){
				$("#Junction_f_CurrentPhysicalLayer > .AllJunctions").prop("checked",false);				
		}
		else {
				$("#Junction_f_"+folderID+ " > .AllJunctions").prop("checked",false);				
		}
		
		//$('.AllJunctions').prop('checked', false);				
		markersJunction[JunctionId].setMap(null);				
		markerClusterJunction.removeMarker(markersJunction[JunctionId]);

			$(this).parent().find('li').each(function(){
				$(this).children('input:checkbox').prop('checked', false);		
			});

		}				
		 if ($(this).parents().eq(2).find('.Junction:checked').length == $(this).parents().eq(2).find('.Junction').length) {
			$(this).parents().eq(2).children('input').prop('checked', true);
		 }
		 else{
			$(this).parents().eq(2).children('input').prop('checked', false);
		 }
		
		if( $("#Junction_f_CurrentPhysicalLayer").find(".Junction:checked" ).length ==0){
			$("#junctionCheckAllBoq").prop("checked",false);
	
		}
		else{
			$("#junctionCheckAllBoq").prop("checked",true);
	
		}
});
}

