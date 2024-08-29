var siteFlag = 0;
function getSite(type,url,id,tr){
	$('body').append('<div id="loading"><img id="loading-image" src="'+getContext()+'/resources/images/ajax-loader.gif" alt="Loading..." /><span>Loading, please wait.</span></div>')
    if(siteFlag == 0){
    	$.ajax({
    		type: "GET",
    		contentType: "application/json; charset=utf-8",
    		url: getContext()+'/getSite',
    		data: {					
    		},
    		dataType: "json",
    		success: function (data) {
    			if (data != null) {
    				if (data.searchResult != "Failed") {
					    $("#Site_f_CurrentPhysicalLayer input[type=checkbox]").unbind();// removed 
    				   // console.log(" //////////SiteList "+data.SiteList);
					    console.log("markersSite "+markersSite.length)
    				    createSite(data.SiteList);
    				    if ($('#Site_f_CurrentPhysicalLayer .AllSites').is(':checked') || $('#siteCheckAllBoq').is(':checked')){
    				    	//AllSiteCheckFilter();
    				    	siteLayerCheckAll();
						}
    					$('#Site_f_CurrentPhysicalLayer .TreeSpan').css("display", "inline"); // The purpose of this command is to let the background color width in mouse hovering or mouse select to be same span text width	
    				    treeCollapseFolder("#Site_f_CurrentPhysicalLayer .folder","fast",".folder");
    					tree_prop_selection("#Site_f_CurrentPhysicalLayer .TreeSpan");
    					MouseHoveringSpans("#Site_f_CurrentPhysicalLayer .TreeSpan");		//>>>>>>>>>>>> Hover event in tree elements
    					var children = $("#Site_f_CurrentPhysicalLayer").find(' > ul > li');
    					children.show();
    					$(this).parent('').children(".folder").find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
    					siteFlag = 1;	
    					console.log("allSiteID "+allSiteID.length)
    					console.log("markersSite "+markersSite.length)
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
function create_Site_Marker_Click(Id,Name,Long,Lat,markers,marker_Cluster,Type,city) {
	const pos = new google.maps.LatLng(Lat,Long);
	var data="<div>" +Name+ "</div>"; 
	var mapIcon;
	var markerType="";
	
	iconSite = {
			url: getContext()+"/resources/NetworkImages/redSiteIcon.png", // url
			scaledSize: new google.maps.Size(35, 35), // scaled size		
	};
	
	markerClusterSite.setOptions({								
		minimumClusterSize: 2,
		styles: [
		         {
		        	 url: getContext()+'/resources/clusterIcons/siteCluster.png',
			         height: 80,
			         width:80,
			         //textColor: 'white', // Add text color
			         textSize: 12, // Font size in pixels
			         anchorText:[-30,-25]
			      },
		],
		calculator: function(markers, numStyles) {
			if (markers.length >= 1) return {text: markers.length,index:1}; 
		}                   
	});
	
	
	
	 if(markers == markersSite && marker_Cluster == markerClusterSite) {
		 mapIcon = iconSite;
		 markerType="Site";
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
			nodeFileId = "CurrentPhysicalLayer";
			console.log("nodeFileId "+nodeFileId)
			//var projectInitial=$("#initial_ul_Projects").find('>ul > #'+nodeFileId);
			//var projectRel=$("#"+nodeFileId+"").find('>ul > #initial_ul_'+nodeFileId);
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
			//projectInitial.show('fast');
			//projectRel.show('fast');

			$("#"+IdSelected+" > .TreeSpan").addClass("selected-span");
			$("#"+IdSelected+" > .TreeSpan").css("background-color", "#97b9cc");

			$("#initial_ul_"+nodeFileId+" > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
			//$("#initial_ul_Projects > .Parentfolder >svg").removeClass('fa fa-folder').addClass('fa-folder-open');
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
		
		


function createSite(SiteList){
/*	markersSite = [];
	markerClusterSite = new MarkerClusterer();
	markerClusterSite.setMap(map);// to be checked !!!!*/
	markersSite = [];
	markerClusterSite.clearMarkers();
	if(SiteList!=null) {
		for(i=0;i<SiteList.length;i++){
			window[""+SiteList[i][0]]=[];
			window[""+SiteList[i][0]]=SiteList[i];
			//allSiteID.push(SiteList[i][0]);
	
			str="<ul><li id='"+SiteList[i][0]+"' class='SITE' style='display:none;width:100px;'><input type='checkbox' class='Site' class='filter checkFilter' name='Element' ></input> <span class='TreeSpan' style='color:black;width:195px'><img src='"+getContext()+"/resources/NetworkImages/redSiteIcon.png' style='width:17px; height:17px;'> "+SiteList[i][1]+"</span></li></ul>";
			$("#Site_f_CurrentPhysicalLayer").append(str); 
			//if(!allSiteID.includes(SiteList[i][0])) {
				allSiteID.push(SiteList[i][0]);
				create_Site_Marker_Click(SiteList[i][0],SiteList[i][1],SiteList[i][4],SiteList[i][5],markersSite,markerClusterSite,"Site","");	
				SiteCheckFilter(SiteList[i][0]);
				CreateSiteClickEvent(SiteList[i][0],"");
				//}
			}		
			
	}
	AllSiteCheckFilter();
	$(".SITE > .TreeSpan").contextmenu(function(){				
		selectedSiteIdContext=$(this).parent().attr('id');
		IdNodeSelectedTemp=$(this).parents().eq(2).attr('id').split("Site_f_")[1];
		//menuName=singleJunction;	
		//menuName= singleJunctionMenu;
		//selectedManholeJct = selectedSiteIdContext;
		//selectedManIdContext = "";
		selectedSite = selectedSiteIdContext;
		//openContext(selectedSiteIdContext,"",singleJunctionMenu,event);
		//openContext(selectedSiteIdContext,"",singleJunction,event);
		//openContext(selectedManholeJct,"",singleJunction,event);

	});
	
	/*
	 * $("#Site_f_CurrentPhysicalLayer > .TreeSpan,  .Junction_f_projects > .TreeSpan").contextmenu(function(){				
		selectedSiteIdContext=$(this).parent().attr('id');
		IdNodeSelectedTemp=$(this).parent().attr('id').split("Junction_f_")[1];	
		menuName= JunctionMenu;
		selectedJct = selectedSiteIdContext;
		openContext(selectedSiteIdContext,"",JunctionMenu,event);
		

	});
	*/
	
	
	
}

function AllSiteCheckFilter(){
	$('.AllSites').bind("change",function() {
		markerClusterSite.clearMarkers();

		console.log(" //////////Site all");
		if ($(this).is(':checked')){
			$(this).parent().find('.Site').each(function(){		
				$(this).prop('checked', true);
			});
			
			$("#network_tree").find(".Site:checked" ).each(function(){
				id=$(this).parent().attr('id');
				if(markersSite[id].getMap()==null){
					markersSite[id].setMap(map);			
					markerClusterSite.addMarker(markersSite[id]);
				}		
			});	
			
			$(this).parent().find('li').each(function(){																	
				$(this).children('input:checkbox').prop('checked', true);		
			});	
			
			if($("#Site_f_CurrentPhysicalLayer > .AllSites").is(":checked") ) {	
				$("#siteCheckAllBoq").prop("checked",true);
			}	
		}else{
			
			$(this).parent().find('.Site').each(function(){
				$(this).prop('checked', false);
			});
			
			$("#siteCheckAllBoq").prop("checked",false);

			if($("#Site_f_CurrentPhysicalLayer > .AllSites").is(":checked") ) {	
				$("#siteCheckAllBoq").prop("checked",true);
			}	

			$("#network_tree").find(".Site:checked" ).each(function(){

				id=$(this).parent().attr('id');
				if(markersSite[id].getMap()==null){
					markersSite[id].setMap(map);			
					markerClusterSite.addMarker(markersSite[id]);	
				}		
			});
			
			$(this).parent().find('li').each(function(){																	   
				$(this).children('input:checkbox').prop('checked', false);		
			});
			

		}
	 });
}

function SiteCheckFilter(Id){
	$("#"+Id).children('input').on("change",function() {
	var folderID = $(this).parents().eq(4).attr('id');
	
	var SiteId=$(this).parent().attr('id');
	console.log("change into "+SiteId);

	if ($(this).is(':checked')){
		if(markersSite[SiteId].getMap() ==null) {	
			markersSite[SiteId].setMap(map);
			markerClusterSite.addMarker(markersSite[SiteId]);	
		}
		
		$(this).parent().find('li').each(function(){
			$(this).children('input:checkbox').prop('checked', true);		
		});
}
	else{
		if(folderID == "initial_ul_CurrentPhysicalLayer"){
				$("#Site_f_CurrentPhysicalLayer > .AllSites").prop("checked",false);				
		}
		else {
				$("#Site_f_"+folderID+ " > .AllSites").prop("checked",false);				
		}
		
		//$('.AllSites').prop('checked', false);				
		markersSite[SiteId].setMap(null);				
		markerClusterSite.removeMarker(markersSite[SiteId]);

			$(this).parent().find('li').each(function(){
				$(this).children('input:checkbox').prop('checked', false);		
			});

		}				
		 if ($(this).parents().eq(2).find('.Site:checked').length == $(this).parents().eq(2).find('.Site').length) {
			$(this).parents().eq(2).children('input').prop('checked', true);
		 }
		 else{
			$(this).parents().eq(2).children('input').prop('checked', false);
		 }
		
		if( $("#Site_f_CurrentPhysicalLayer").find(".Site:checked" ).length ==0){
			$("#siteCheckAllBoq").prop("checked",false);
	
		}
		else{
			$("#siteCheckAllBoq").prop("checked",true);
	
		}
});
}



function siteLayerCheckAll(){

	$("#Site_f_CurrentPhysicalLayer > .AllSites").prop("checked",true);	
	
	$("#Site_f_CurrentPhysicalLayer").find('li').each(function(){		
		
		var siteID=$(this).attr('id');
		$("#"+siteID).children(':checkbox').prop( "checked", true );
		if(markersSite[siteID].getMap() == null ){
			markersSite[siteID].setMap(map);
			markerClusterSite.addMarker(markersSite[siteID]);
		}
			
		});
	
	if( $("#Site_f_CurrentPhysicalLayer").find(".Site:checked" ).length == 0 ){
		$("#siteCheckAllBoq").prop("checked",false);			
	}
	else{
		$("#siteCheckAllBoq").prop("checked",true);			
	}


}


function siteLayerUnCheckAll(){

	$("#Site_f_CurrentPhysicalLayer > .AllSites").prop("checked",false);	
	markerClusterSite.clearMarkers();
	$("#Site_f_CurrentPhysicalLayer").find('li').each(function(){			
		var siteID=$(this).attr('id');					
		markersSite[siteID].setMap(null);	
		$("#"+siteID).children(':checkbox').prop( "checked", false );
	});
		
		$("#network_tree").find(".Site:checked" ).each(function(){

		id=$(this).parent().attr('id');
		if(markersSite[id].getMap()==null){
			markersSite[id].setMap(map);
			markerClusterSite.addMarker(markersSite[siteID]);			
		}		
	});			

}

function CreateSiteClickEvent (siteID,physicalLayer){
	$("#"+siteID+"> .TreeSpan").on('click',function(){		
		panTo(window[""+siteID][5], window[""+siteID][4]);
		map.setZoom(11);	
	});
}

