<%@ include file="NetworkCommon.jsp" %>
<title>Vendor Node Type Site Node Cell</title>
<script>	
$('#filterr').hide();
$('#removeFilter').hide();


var lst = ${listSites};
var listVen=${listVen};
var arrayParam=${arrayParam};
var date=$("#ParsingDate").val();
console.log("date...", date);

var button ;
var data;
if(!(lst==null || lst=="")){
var wareCount=lst.length;
}


function initMap() {

	$('#nodeBtn').toggleClass('activee');
    $('#siteBtn').addClass('activee');
    $('#cellBtn').toggleClass('activee');
    $('#vendorBtn').toggleClass('activee');
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

	document.getElementById("network_tree").innerHTML ="";
	$("#network_tree").resizable({
		handles: "s", 	
	});

	
	//var directionsDisplay=new google.maps.DirectionsRenderer();
	//var directionsService=new google.maps.DirectionsService();
	
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
	CreateTree_VnNdTpStNdCell(listVen,map);
	
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Vendors' class='AllVendors' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Vendors</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

function CreateTree_VnNdTpStNdCell(listVen,map){
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Vendors' class='AllVendors' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Vendors</span></li></ul>";
	$("#network_tree").append(str);
	var dFrag = document.createDocumentFragment();
	for (n=0; n < listVen.length; n++) {
		var str = "<li class='Vendor' id='"+listVen[n]+"' style='display:none; width:100px;'> <span class='folder' onclick='VenNdTypStNdCellCore("+listVen[n]+")'><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'><i class='fa fa-copyright fa-2x'></i> "+listVen[n]+"</span>";				
		str+= "<ul><li id='" +listVen[n]+"_f' class='NodeTypeFolder parent_li' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> NodeType </span></span></li></ul></li></ul>";						   						    	
		const div = document.createElement('ul');
		div.innerHTML = str;
		dFrag.appendChild(div);		  		
	}	
	document.getElementById('initial_ul').appendChild(dFrag);	
	tree_prop_selection();
	folder();
	$(".Initial > .TreeSpan").contextmenu(function(){				
		selectedfolderVenIdContext=$(this).parent().attr('id');
		menuName=folderVen;			
		openContext(selectedfolderVenIdContext,"",folderVen,event);
	});
	$(".Vendor > .TreeSpan").contextmenu(function(){				
		selectedVenIdContext=$(this).parent().attr('id');
		//console.log("selectedVenIdContext: ",selectedVenIdContext);
		menuName=folderVen;			
		openContext(selectedVenIdContext,"",singleVen,event);
	});
}
folderVen = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			  Ven_Boq("");
			}	
		}
	]
});
singleVen = new ContextMenu({
	  'theme': 'default',
	  'items': [
		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			  Ven_Boq(selectedVenIdContext);
			}	
		}
	]
});

function AllSitesCheckFilter(){
	markerClusterSites.clearMarkers();
	//CreateMap(lst,map);
	CreateMap(lst,map,arrayParam,date);

	$('.AllVendors').bind("change",function() {	
			if ($(this).is(':checked')){
				 $('#network_tree input[type="checkbox"][class="SingleSiteVen"]').prop('checked', false);
				 $('#network_tree input[type="checkbox"][class="AllSites"]').prop('checked', false);
				 for(var x=0; x< markersSites.length; x++){	
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}									
			}					
			else{
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}

function VenNdTypStNdCellCore(id){
	console.log("Welcome to VenNdTypStNdCellCore, the id is " +id);
	var selectedVen=id.id;
	var VenChildrenLength=$("#" +selectedVen+"_f").find(' > ul > li').length;			
	if(VenChildrenLength==0){
		if(arrayParam[0]==1){
			var paramEnterprise = true;
		}
		else{
			var paramEnterprise = false;
		}

		if(arrayParam[1]==1){
			var paramTransmission = true;
		}else{
			var paramTransmission = false;
		}
		if(arrayParam[2]==1){
			var paramRAN = true;
		}else{
			var paramRAN = false;
		}
		if(arrayParam[3]==1){
			var paramCore = true;
		}else{
			var paramCore = false;
		}
		
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/FindOnClick_VenNdTSiteNodeCell',
			data: {
			        "selectedVen":selectedVen,	
		            "paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			     	"paramRAN":paramRAN,
				    "paramCore":paramCore,
				    "date":date,
			},
			dataType: "json",
			success: function (data) {							        	
				if (data != null) {
					var listNodeType=data.listNodeType;
					//console.log("listNodeType: ", listNodeType);
					if(VenChildrenLength<listNodeType.length){						            	
						for (n = 0; n<listNodeType.length; n++) {							            								   		 
							var str = "<ul><li class='NodeType' id='"+listNodeType[n][0]+"_"+listNodeType[n][1]+"' style='display:none; margin-left:-20px'><span class='folder' onclick='RequestingSites("+listNodeType[n][0]+"_"+listNodeType[n][1]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs fa-2x'></i>"+listNodeType[n][0]+"</span></span>";
							str+= "<ul><li id='" +listNodeType[n][0]+"_"+listNodeType[n][1]+"_f' class='NodeFolder parent_li' style='display:none; margin-left:-40px'><input type='checkbox' id='"+listNodeType[n][0]+"_"+listNodeType[n][1]+"_Site' class='AllSites' style='margin-left: 15px' onclick='showMarkersAllSitesOneNt("+listNodeType[n][0]+"_"+listNodeType[n][1]+"_Site)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'>Sites </span></span></li></ul></li></ul>";							
							$("#"+selectedVen+"_f").append(str);
							//tree_prop_general();	
							//tree_Prop("#"+listNodeType[n][0]+"_"+listNodeType[n][1]+"> span");
							//tree_Prop("#"+listNodeType[n][0]+"_"+listNodeType[n][1]+"_f > span");		
						     }               
						tree_prop_selection("#" +selectedVen+ "_f .NodeType .TreeSpan");
			            Tree_PropagationAppendedNodes(selectedVen+ "_f .NodeType");
			            
			            $(".NodeType > .TreeSpan").contextmenu(function(){				
			        		selectedSingleNodeTypeIdContext=$(this).parent().attr('id');
			        		//console.log("selectedSingleNodeTypeIdContext: ",selectedSingleNodeTypeIdContext);
			        		var splitArray= selectedSingleNodeTypeIdContext.split("_");
			        		selectedSingleNt=splitArray[0];
			        		//console.log("selectedSingleNt: ",selectedSingleNt);
			        		menuName=SingleNdTp;			
			        		openContext(selectedSingleNodeTypeIdContext,"",SingleNdTp,event);
			        	});
			            SingleNdTp = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			        			  NodeTVen_Boq("",selectedSingleNt,selectedVen);
			        			}	
			        		}
			        	]
			        });
					  }                                              
			     }
				 data=null;
			         	},
				         error: function(result) {
				             alert("Error");
			         }
			     });
			}
			//}
	//}); 
}


function showMarkersAllSitesOneNt(id) {
  var id = id.id;
 
  //var startIndex = id.indexOf("_") + 1;
  //var endIndex = id.lastIndexOf("_");
  var venId = id.substring(id.indexOf("_") + 1, id.lastIndexOf("_")); 
  
  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id).is(":checked");
  //var ulElement = document.getElementById(id.split("_Site")[0]);
  var liElements = document.getElementById(id.split("_Site")[0]).querySelectorAll('.SingleSiteVen > input'); // Retrieve only <li> elements with <input> children
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
	var result = liElements[i].id.split("_").slice(0, 3).join("_");	  
    liElements[i].checked = isChecked;
    if (isChecked && !markersSites[result].getMap()) {
      markersToAdd.push(markersSites[result]);
    }
  }
  if (isChecked) {
	$('#network_tree input[type="checkbox"][class="AllVendors"]').prop('checked', false);
    markerClusterSites.addMarkers(markersToAdd); // Add all markers at once
    markerClusterSites.repaint();
  } else {
    markerClusterSites.clearMarkers();
    $('#network_tree input[type="checkbox"]').prop('checked', false);
  }
}

function RequestingSites(id) {
	var selectedVen = id.id.substring(id.id.indexOf("_") + 1);
	var SelectedNodeType = id.id.substring(0, id.id.indexOf("_"));
	var NdTypeChildrenLength=$("#" +SelectedNodeType+"_" +selectedVen+"_f").find(' > ul > li').length;							            	
	if(NdTypeChildrenLength==0){
		if(arrayParam[0]==1){
		var paramEnterprise = true;
		}else{
			var paramEnterprise = false;
		}
		if(arrayParam[1]==1){
			var paramTransmission = true;
		}else{
			var paramTransmission = false;
		}
		if(arrayParam[2]==1){
			var paramRAN = true;
		}else{
			var paramRAN = false;
		}
		if(arrayParam[3]==1){
			var paramCore = true;
		}else{
			var paramCore = false;
		}
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/FindOnClick_VenNdTSiteNodeCell',
			data: {
				"selectedVen":selectedVen,
				"SelectedNodeType":SelectedNodeType,
		        "paramEnterprise": paramEnterprise,
				"paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
				"paramCore":paramCore,
				"date":date,
			},
			dataType: "json",
			success: function (data) {	    
				if (data != null) {
					var listVenSites = data.listVenSites;
					if(NdTypeChildrenLength<listVenSites.length){
						var dFrag = document.createDocumentFragment();
						for (n = 0; n < listVenSites.length; n++) {	
//							str="<li id='"+listVenSites[n][1]+"_"+listVenSites[n][5]+"' class='SingleSiteVen' style='width:100px;display:none; margin-left:-20px'><input type='checkbox' id='" + listVenSites[n][1]+"_"+listVenSites[n][5] +"_SingleSite' class='SingleSiteVen' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listVenSites[n][1]+"_"+listVenSites[n][5] + "_SingleSite)'></input><span class='folder' onclick='Create_Sites("+listVenSites[n][1]+"_"+listVenSites[n][5]+")' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'onclick='PanTreeSites("+listVenSites[n][1]+"_"+listVenSites[n][5]+")'><i class='fas fa-crosshairs fa-2x'></i><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listVenSites[n][0]+"</span>";
							str="<li id='"+listVenSites[n][1]+"_"+listVenSites[n][5]+"' class='SingleSiteVen' style='width:100px;display:none; margin-left:-20px'><input type='checkbox' id='" + listVenSites[n][1]+"_"+listVenSites[n][5] +"_SingleSite' class='SingleSiteVen' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listVenSites[n][1]+"_"+listVenSites[n][5] + "_SingleSite)'></input><span class='folder' onclick='Create_Sites("+listVenSites[n][1]+"_"+listVenSites[n][5]+")' ><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'onclick='PanTreeSites("+listVenSites[n][1]+"_"+listVenSites[n][5]+")'><i class='fas fa-crosshairs fa-2x'></i>"+listVenSites[n][0]+"</span>";							
							str+= "<ul><li id='" +listVenSites[n][1]+"_"+listVenSites[n][5]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Nodes </span></span></li></ul></li></ul>";
							const div = document.createElement('ul');
							div.innerHTML = str;
							dFrag.appendChild(div);										  
						}
							document.getElementById(SelectedNodeType+"_" +selectedVen+"_f").appendChild(dFrag);
							tree_prop_selection("#" +SelectedNodeType+"_" +selectedVen+"_f .SingleSiteVen .TreeSpan");
				            Tree_PropagationAppendedNodes(SelectedNodeType+"_" +selectedVen+"_f .SingleSiteVen");
				            
				            $(".SingleSiteVen > .TreeSpan").contextmenu(function(){				
				        		selectedSingleSiteIdContext=$(this).parent().attr('id');
				        		var splitArray= selectedSingleSiteIdContext.split("_");
				        		idSite=splitArray[0] + "_" + splitArray[1] + "_" + splitArray[2];	
				        		menuName=SingleSite;			
				        		openContext(selectedSingleSiteIdContext,"",SingleSite,event);
				        	});
				            SingleSite = new ContextMenu({
				        	  'theme': 'default',
				        	  'items': [
				        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
				        			  SiteVen_Boq(idSite,selectedVen);
				        			}	
				        		}
				        	]
				        });
						 			}
					 			}
					data=null;
				},
				error: function(result) {
				     alert("Error");
							         }
						     });
		}
					//}
}


function showMarkerSingleSite(id) {
  	 const selectedSiteId = id.id.split('_').slice(0, 3).join('_');
	 var nodeTypeId= id.id.split('_').slice(3).join('_').split("_SingleSite")[0];
	  	  	
	var liElements =  $("#" + id.id).closest("li.NodeFolder").attr("id");
	var venId = liElements.split("_").slice(1, 4)[0];
	
	    if ($("#" + id.id).is(":checked")) {
	     	//console.log("checked");
	     	var checkboxes = $('[id$="'+nodeTypeId+'_SingleSite"]');
	  		var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	     if (allChecked) {
	    	document.getElementById(nodeTypeId+"_"+venId+'_Site').checked = true;
		}
	      markersSites[selectedSiteId].setMap(map);
	      markerClusterSites.addMarker(markersSites[selectedSiteId]);
	      markerClusterSites.repaint();
	    }else{
		      //console.log("unchecked");
		      document.getElementById(nodeTypeId+"_"+venId+'_Site').checked = false;		
		      markersSites[selectedSiteId].setMap(null);
		      markerClusterSites.removeMarker(markersSites[selectedSiteId]);
		      markerClusterSites.repaint();
	    }
	}



function PanTreeSites(id){
	var selectedItem = id.id.split('_').slice(0, 3).join('_');
		//Site_Boq(selectedItem);
			if(selectedItem!=markersSite)
				{
				var selMarker="";		
				markerId=selectedItem;				
				selMarker=markersSites[markerId];
				var latSitee = selMarker.getPosition().lat();
				var lngSitee = selMarker.getPosition().lng();
				position=selMarker.getPosition();							
				panTo(latSitee, lngSitee);
				//infowindow.setContent(selMarker.data);
				//infowindow.open(map,selMarker);					
				if(markersSite!="")
					{	
					var otherMarkers=markersSites[markersSite];			
					}		
				markersSite="";	
				markersSite=selectedItem;		
				map.setZoom(15);
				//console.log("PANNED");
			}	
	}

function Create_Sites(id){
	
	var splitArray = id.id.split("_");
	var selectedItem = splitArray[0] + "_" + splitArray[1] + "_" + splitArray[2];
	var selectedNodeType = splitArray[3];
	
	//var parentLi = $("#" + id.id).closest("li.Supplier");		  
	var selectedVen = $("#" + id.id).closest("li.Vendor").attr("id");
	var siteChildren=$("#"+selectedItem+"_"+selectedNodeType+"_f") .find(' > ul > li').length;
	if(siteChildren == 0){
		if(arrayParam[0]==1){
			var paramEnterprise = true;
		}else{
			var paramEnterprise = false;
		}
		if(arrayParam[1]==1){
			var paramTransmission = true;
		}else{
			var paramTransmission = false;
		}
		if(arrayParam[2]==1){
			var paramRAN = true;
		}else{
			var paramRAN = false;
		}
		if(arrayParam[3]==1){
			var paramCore = true;
		}else{
			var paramCore = false;
		}
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/FindOnClick_VenNdTSiteNodeCell',
			data: {
				"selectedItem":selectedItem,
				"selectedVen":selectedVen,
				"SelectedNodeType":selectedNodeType,
				"paramEnterprise": paramEnterprise,
				"paramTransmission":paramTransmission,
				"paramRAN":paramRAN,
				"paramCore":paramCore,
				"date":date,
			},
			dataType: "json",
			success: function (data) {			        	
				if (data != null) {
					var listVenNodes=data.listVenNodes;
					var listCells=data.listCells;
					if(siteChildren<listVenNodes.length){		
						Create_TreeNode_CellGeneral(listVenNodes,listCells,siteChildren, true,selectedItem);
						Tree_PropagationAppendedNodes(selectedItem+"_"+selectedNodeType+"_f  .Node");
			            tree_prop_selection("#" + selectedItem+"_"+selectedNodeType+"_f .Node .TreeSpan");
					}
				}
				data = null;
			},
			error: function(result) {
				alert("Error");
			}
		});   
	}
} 
///////////////////////////////////////////////
/* End of Supp NodeType Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////

</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>

