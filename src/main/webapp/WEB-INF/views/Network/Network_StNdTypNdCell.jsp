<%@ include file="NetworkCommon.jsp" %>
<title>Site Node Type Node Cell</title>

<script>	
$('#filterr').hide();
$('#removeFilter').hide();

var lst = ${listSites};
var Long=${Long};
var Lat=${Lat};

var listNodes=${listNodes};
var listCells=${listCells};
var listNodesType=${listNodesType};
var arrayParam=${arrayParam};
var date=$("#ParsingDate").val();


var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}

let srcCityAutocomplete, dstCityAutocomplete;
function initMap() {
	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#cellBtn').toggleClass('activee');
    $('#nodeTypeeBtn').toggleClass('activee');
    

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

	//console.log("Data is"+data);
	document.getElementById("network_tree").innerHTML ="";
	$("#network_tree").resizable({
		handles: "s", 	

	});


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

	if(!(lst==null || lst=="")) {
		map.setOptions({ minZoom: 3, maxZoom: 28});	
		CreateMap(lst,map,arrayParam,date);
		CreateTree_StNdTypNdCell(lst,map);
	}
	else {
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'>  Sites</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map


/* Start of Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
function CreateTree_StNdTypNdCell(lst,map){
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'>  Sites</span></li></ul>";
	$("#network_tree").append(str); 
	var dFrag = document.createDocumentFragment();
	for (n = 0; n < lst.length; n++){ 
		str="<li id='"+lst[n][2]+"' class='Site' style='display:none;width:100px;'><input type='checkbox' id='"+ lst[n][2]+"_SingleSite' class='StNdCell_SingleSite' unchecked onclick='showMarkersChecked("+ lst[n][2] +"_SingleSite)'></input> <span class='folder' onclick='StNdTypNdCellCoreFolder("+lst[n][2]+")'><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px' onclick='StNdTypNdCellCore("+lst[n][2]+")'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+lst[n][1]+"</span>";
		str+= "<ul><li id='" +lst[n][2]+"_f' class='NodeTypeFolder' style='display:none; margin-left:2px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'> NodeType </span></li></ul></li></ul>";
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
	var id =n.id;
	var Id= id.split("_SingleSite")[0];
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

function StNdTypNdCellCoreFolder(n) {
    var markersSite = "";
    var selectedItem = n.id;
    if(selectedItem!=markersSite) {
		var lstNodesType=[];
		for(var t=0; t<listNodesType.length; t++) {
        	if(listNodesType[t][1]==selectedItem) {
            	lstNodesType.push(listNodesType[t]);
            }
		}                    
	var NdChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;
	if(NdChildrenLength==0) { 
		if (lstNodesType != null && lstNodesType.length > 0) {          
			for(j=0;j<lstNodesType.length;j++)  {                   
				var str= "<ul><li class='NodeType' id='" + lstNodesType[j][0] +"_"+lstNodesType[j][1]+"'  class='folder' style='display:none; margin-left:-20px'>";                                                                                                                       
                str+= "<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> <i class='fa fa-cogs '></i> "+lstNodesType[j][0]+"</span></li></ul>";
				$("#"+selectedItem+"_f").append(str);
                str="<ul><li id='" +lstNodesType[j][0] +"_"+lstNodesType[j][1]+"_f' class='NodeFolder parent_li' style='display:none; margin-left:-15px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node </span></li></ul>";
                $("#" + lstNodesType[j][0] +"_"+lstNodesType[j][1]).append(str);
	            NdTyFolder(lstNodesType[j][0] +"_"+lstNodesType[j][1]);                     
			} 
		}
		tree_prop_selection("#" +selectedItem+"_f .NodeType .TreeSpan");
        Tree_PropagationAppendedNodes(selectedItem+"_f .NodeType");
			$(".NodeType > .TreeSpan").contextmenu(function(){				
				selectedFolderNodeTypeIdContext=$(this).parent().attr('id');
            	selectedNodeType = selectedFolderNodeTypeIdContext.split("_")[0];
            	menuName=folderNodeType;			
            	openContext(selectedFolderNodeTypeIdContext,"",folderNodeType,event);
            });   
            folderNodeType = new ContextMenu({
              	  'theme': 'default',
              	  'items': [
              		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
                          SiteId=selectedFolderNodeTypeIdContext.substring(selectedFolderNodeTypeIdContext.indexOf("_") + 1);
                       	 NodeT_Boq(SiteId,selectedNodeType);
              			}	
              		}
              	]
              }); 
			}        
        }
}


function NdTyFolder(id){
	$("#" + id+ " .folder").on('click',function () {                                    	
		var res=$(this ).parents().map(function() {
			return this.id;
		})
		.get()
		.join( "," );
		
		parents=res.split("_WARE");
		var selectedNodetType=parents[0];                                                                                                           
		var selectedItem=res.split(",,");
		selectedItem=selectedItem[2];
	
		var lstNodes=[];
		for(var n=0;n<listNodes.length;n++){
			if(listNodes[n][4]==selectedItem && listNodes[n][3]==selectedNodetType){
				lstNodes.push(listNodes[n]);												
			}
		}	
		if(lstNodes!=null) {												
			var NdTypeChildrenLength=$("#" + selectedNodetType+"_"+selectedItem+"_f").find(' > ul > li').length;
				if(NdTypeChildrenLength==0){																											
					Create_TreeNode_CellGeneral(lstNodes,listCells,NdTypeChildrenLength,true,selectedItem);																								               
				}
		}
	});
}                        
						
function StNdTypNdCellCore(n){
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
			if(markersSite!="")
			{
				var otherMarkers=markersSites[markersSite];					
			}		
			markersSite="";	
			markersSite=selectedItem;
			map.setZoom(15);			
		}
}

///////////////////////////////////////////////
/* End of Site NodeType Node Cell Tree Method */ 

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>