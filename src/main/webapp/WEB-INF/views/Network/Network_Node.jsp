<%@ include file="NetworkCommon.jsp" %>
<title>Network Node</title>
<script>	
$('#filterr').hide();
$('#removeFilter').hide();


var lst = ${listNodes};
var arrayParam = ${arrayParam};
var date=$("#ParsingDate").val();

var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}


let srcCityAutocomplete, dstCityAutocomplete;
function initMap() {

	$('#nodeBtn').toggleClass('activee');
	 
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

const centerControlDiv = document.createElement("div");
CenterControl(centerControlDiv, map);
map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);

	if(!(lst==null || lst=="")){
	map.setOptions({ minZoom: 3, maxZoom: 28});	
	CreateMap(lst,map,arrayParam,date);
	CreateTree_Nodes(lst,map);
		}
	
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Nodes' class='AllNodes' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
	
	////////////////////////
} /// End of init Map

///////////////////////////////////////////////
/* Start of Node Tree Method */ 
//////////////////////////////////////////////
var nodeId;
var SiteId;
function CreateTree_Nodes(lst,map){         
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Nodes' class='AllNodes' style='margin-left: 15px' onclick='AllSitesCheckFilter()'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes</span></li></ul>";
	$("#network_tree").append(str);
	 var dFrag = document.createDocumentFragment();
	 for (n = 0; n < lst.length; n++){
		var str = "<li class='Node' id='"+lst[n][2]+"_"+lst[n][9]+"' style='display:none'><input type='checkbox' id='" +lst[n][2]+"_"+lst[n][9] +"_SingleNode' class='SingleNode' onclick=\"showMarkerSingleSite('"+lst[n][2]+"_"+lst[n][9] +"_SingleNode')\"></input><span class='TreeSpan' style='width:395px;margin-left: 5px' onclick=\"PanTreeSites('"+lst[n][2]+"_"+lst[n][9] +"')\"> <i class='fa fa-server'></i> "+lst[n][10]+"</span></li>";
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);
	} 
	// Add fragment to a list: 
	 
	 document.getElementById('initial_ul').appendChild(dFrag);	  
	 tree_prop_selection();
	 folder();
	 
		$(".Initial > .TreeSpan").contextmenu(function(){				
			selectedfolderNodeIdContext=$(this).parent().attr('id');
			menuName=folderNode;			
			openContext(selectedfolderNodeIdContext,"",folderNode,event);
		});
		
    	$(".Node > .TreeSpan").contextmenu(function(){				
    		selectedNodeIdContext=$(this).parent().attr('id');
    		var index = selectedNodeIdContext.indexOf("WARE_2");
			if (index !== -1) {
				  nodeId = selectedNodeIdContext.substring(0, index).slice(0, -1);
				  SiteId = selectedNodeIdContext.substring(index);
				} else{
					  var lastIndex = selectedNodeIdContext.lastIndexOf("_");
					  nodeId = selectedNodeIdContext.substring(0, lastIndex);
					  SiteId =null;	
				}
    		menuName=singleNode;			
    		openContext(selectedNodeIdContext,"",singleNode,event);
    	});   	
	}

	folderNode = new ContextMenu({
		  'theme': 'default',
		  'items': [
			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
					Site_Boq("");
				}	
			}
		]
	});
	singleNode = new ContextMenu({
		  'theme': 'default',
		  'items': [
			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {		
					Node_Boq(SiteId,nodeId);
				}	
				   }
			]
	});

	function AllSitesCheckFilter(){
		$('.AllNodes').bind("change",function() {	
			markerClusterSites.clearMarkers();
				if ($(this).is(':checked')){
					$('#network_tree input[type="checkbox"][class="SingleNode"]').prop('checked', true);
					for(var x=0; x< markersSites.length; x++){	
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}									
				}					
				else{
				$('#network_tree input[type="checkbox"][class="SingleNode"]').prop('checked', false);					
					for(var x=0; x< markersSites.length; x++){
						markersSites[markersSites[x].ID].setMap(null);
						markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
							}	
					}
		});
	}

	
	function showMarkerSingleSite(id) {
		var Id= id.split("_SingleNode")[0];
		var index = id.indexOf("WARE_2");
		var indexnull = id.indexOf("SingleNode");
		if (index !== -1) {
			nodeId = id.substring(0, index).slice(0, -1);
		}else{
			var secondLastIndex = id.lastIndexOf("_", id.lastIndexOf("_") - 1); // Find the second last index of '_'
			var nodeId = id.substring(0, secondLastIndex); // Get the substring from 0 to the second last index
		}
		
		if ($("#" + id).is(":checked")) {
			var checkboxes = $('[id$="_SingleNode"]');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
				if (!checkboxes[i].checked) {
					allChecked = false;
					break;
				}
			}
			if (allChecked) {
				document.getElementById('Nodes').checked = true;
			}
			markersSites[nodeId].setMap(map);			
			markerClusterSites.addMarker(markersSites[nodeId]);
			markerClusterSites.repaint();
		}else{
			document.getElementById('Nodes').checked = false;		
			markersSites[nodeId].setMap(null);
			markerClusterSites.removeMarker(markersSites[nodeId]);
			markerClusterSites.repaint();
		}
	}


	function PanTreeSites(id){
		var index = id.indexOf("WARE_2");
		if (index !== -1) {
			nodeId = id.substring(0, index).slice(0, -1);
		}else{
			var lastIndex = id.lastIndexOf("_");
			var nodeId = id.substring(0, lastIndex);
		}
		
		if(nodeId!=markersSite)
		{
			var selMarker="";		
			markerId=nodeId;				
			selMarker=markersSites[markerId];
			var latSitee = selMarker.getPosition().lat();
			var lngSitee = selMarker.getPosition().lng();
			position=selMarker.getPosition();							
			panTo(latSitee, lngSitee);
			if(markersSite!="")
			{	
				var otherMarkers=markersSites[markersSite];			
			}		
			markersSite="";	
			markersSite=nodeId;		
			map.setZoom(15);
		}	
	} 
///////////////////////////////////////////////
/* End of Node Tree Method */ 

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>