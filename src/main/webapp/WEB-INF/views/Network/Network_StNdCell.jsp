<%@ include file="NetworkCommon.jsp" %>
<title>Site Node Cell</title>
<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};
var Long=${Long};
var Lat=${Lat};
var listNodes=${listNodes};
var listCells=${listCells};
var arrayParam=${arrayParam};
var date=$("#ParsingDate").val();


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}


function initMap() {

	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#cellBtn').toggleClass('activee');

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

	document.getElementById("network_tree").innerHTML ="";
	$("#network_tree").resizable({
		handles: "s", 	
	});
		
	//New Map//
						 		
 map = new google.maps.Map(document.getElementById("mapContainer"), {
		mapTypeControl: false,
		zoom:3,
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
	
	//-----> Create the DIV to hold the control and call the CenterControl()
//-----> constructor passing in this DIV.

const centerControlDiv = document.createElement("div");
CenterControl(centerControlDiv, map);
map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);

	if(!(lst==null || lst=="")) {
		map.setOptions({ minZoom: 3, maxZoom: 28});	
		CreateMap(lst,map,arrayParam,date);
		CreateTree_StNdCell(lst,map);	
	}
	else{
		console.log("hi lst null");
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked'></input> <span class='folder' id='test1'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

/*     Strat of Site Node Cell Tree Method   */ 
//////////////////////////////////////////////
function CreateTree_StNdCell(lst,map) {
	console.log("Welcome to CreateTree_StNdCell");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder' id='test1'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
	$("#network_tree").append(str);	
	var dFrag = document.createDocumentFragment();
	for (n = 0; n < lst.length; n++){ 
		str="<li id='"+lst[n][2]+"' class='Site' style='display:none;width:100px;'><input type='checkbox' id='"+ lst[n][2]+"_SingleSite' class='StNdCell_SingleSite' unchecked onclick='showMarkersChecked("+ lst[n][2] +"_SingleSite)'></input> <span class='folder' onclick='StNdCellCoreFolder("+lst[n][2]+")' ><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px' onclick='StNdCellCore("+lst[n][2]+")'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+lst[n][1]+"</span>";
		str+= "<ul><li id='" +lst[n][2]+"_f' class='NodeFolder' style='display:none; margin-left:5px'><span class='folder' > <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node </span></li></ul></li></ul>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);
	} 
	//Add fragment to a list: 
	document.getElementById('initial_ul').appendChild(dFrag);
	tree_prop_selection();
	folder();
	
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedFolderSiteIdContext=$(this).parent().attr('id');
		menuName=folderSite;			
		openContext(selectedFolderSiteIdContext,"",folderSite,event);
	});
	
	$(".Site > .TreeSpan").contextmenu(function(){				
		selectedSiteIdContext=$(this).parent().attr('id');
		menuName=singleSite;			
		openContext(selectedSiteIdContext,"",singleSite,event);
	});
}

folderSite = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Site_Boq("");
			}	
		}
	]
});
singleSite = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				Site_Boq(selectedSiteIdContext);
			}	
		}
	]
});


function ZoomOutMap(){				    	
		map.setZoom(6);
		var Nairobi=new google.maps.LatLng(0.796530,37.959529); 				
		//var Nairobi=new google.maps.LatLng(29.378586, 47.990341);
		map.setCenter(Nairobi);
		LatLanMouse(map);	
}

function AllSitesCheckFilter(){
	$('.AllSites').bind("change",function() {	
			markerClusterSites.clearMarkers();
			if ($(this).is(':checked')){
				$(this).parent().find('.StNdCell_SingleSite').each(function(){		
						$(this).prop('checked', true);
					});
				for(var x=0; x< markersSites.length; x++){		
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}							
			}					
			else{		
				$(this).parent().find('.StNdCell_SingleSite').each(function() {
				$(this).prop('checked', false);	
			});
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}


function showMarkersChecked(n){
	console.log(" showMarkersChecked");
	var id =n.id;
	var Id= id.split("_SingleSite")[0]; // This Id is is used for markersSite[]
		if ($("#"+id).is(":checked")) {	
			var checkboxes = document.querySelectorAll('.StNdCell_SingleSite');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
			  if (!checkboxes[i].checked) {
			    allChecked = false;
			    break;
			  }
			}
			if (allChecked) {
				$('#network_tree input[type="checkbox"][id="StNdCell_Sites"]').prop('checked', true);
			} 		
	  			markersSites[Id].setMap(map);
	  			markerClusterSites.addMarker(markersSites[Id]);
	  			markerClusterSites.repaint();
			}else {		
				$('#network_tree input[type="checkbox"][id="StNdCell_Sites"]').prop('checked', false);
				markersSites[Id].setMap(null);
				markerClusterSites.removeMarker(markersSites[Id]);
				markerClusterSites.repaint();
			}
	}


function StNdCellCore(n) {
	console.log("Welcome to creating nodes at site node cell, the method is: StNdCellCore");
	var selectedItem=n.id;
	if(selectedItem!=markersSite) {
		var selMarker="";					
		markerId=selectedItem;
		selMarker=markersSites[markerId];
		var latSitee = selMarker.getPosition().lat();
		var lngSitee = selMarker.getPosition().lng();					
		position=selMarker.getPosition();
		panTo(latSitee, lngSitee);					
		const pos = new google.maps.LatLng(latSitee,lngSitee);					
		if(markersSite!="") {
			var otherMarkers=markersSites[markersSite];								
		}
		markersSite="";	
		markersSite=selectedItem;
		map.setZoom(15);	
	}	
}

function StNdCellCoreFolder(n) {
	var selectedItem = n.id;
	markersSite="";
	if(selectedItem!=markersSite) {				
		var SiteChildren=$("#" +selectedItem+"_f").find(' > ul > li');
		var SiteChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;
		var lstNodes=[];
		for(var n=0; n<listNodes.length; n++) {
			if(listNodes[n][4]==selectedItem) {
				lstNodes.push(listNodes[n]);
			}
		}	
		var lstCells=[];
		for(var n=0; n<listCells.length; n++){
			if(listCells[n][3]==selectedItem){
				lstCells.push(listCells[n]);
			}
		}
		if(lstNodes!=null) {
			if(SiteChildrenLength<lstNodes.length){
				Create_TreeNode_CellGeneral(lstNodes,lstCells,SiteChildrenLength,false,selectedItem);
			}	
		}
	}
}

///////////////////////////////////////////////
/*     End of Site Node Cell Tree Method   */ 

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>