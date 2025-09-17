<%@ include file="NetworkCommon.jsp" %>
<title>Vendor Site Node Type Node Cell</title>
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
	CreateMap(lst,map,arrayParam,date);
	CreateTree_VnStNdTypNdCell(listVen,map);
	
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

/* Start of Supp Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
//var SupSCreated=[];
function CreateTree_VnStNdTypNdCell(listVen,map){
	//Site_Boq("");
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='Vendors' class='AllVendors' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Vendors</span></li></ul>";
	$("#network_tree").append(str);
	var dFrag = document.createDocumentFragment();
	for (n=0; n < listVen.length; n++) {
		var str = "<li class='Vendor' id='"+listVen[n]+"' style='display:none; width:100px;'> <span class='folder' onclick='VenStNdTypNdCellCore("+listVen[n]+")'><i class='fa fa-folder' style='color: #08526D'></i></span> <span class='TreeSpan' style='width:395px'><i class='fa fa-copyright fa-2x'></i>  "+listVen[n]+"</span>";				
		str+= "<ul><li id='" +listVen[n]+"_f' class='SiteFolder parent_li' style='display:none; margin-left:-40px'><input type='checkbox' id='"+listVen[n]+"_Site' class='AllSites' style='margin-left: 15px' onclick='showMarkersAllSitesOneNt("+listVen[n]+"_Site)'></input><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites </span></li></ul></li></ul>";
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


function showMarkersAllSitesOneNt(id) {
  var id = id.id;
  var venId = id.split("_Site")[0];
  
  markerClusterSites.clearMarkers();
  var isChecked = $("#" + id).is(":checked");
  //var ulElement = document.getElementById(suppId);
  var liElements = document.getElementById(venId).querySelectorAll('.SingleSiteVen > input'); // Retrieve only <li> elements with <input> children
  var markersToAdd = []; // Array to store markers that need to be added

  for (var i = 0; i < liElements.length; i++) {
    liElements[i].checked = isChecked;
    if (isChecked && !markersSites[liElements[i].id.split("_" + venId)[0]].getMap()) {
      markersToAdd.push(markersSites[liElements[i].id.split("_" + venId)[0]]);
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


function VenStNdTypNdCellCore(id){ 
	var selectedVen = id.id;
	var VenChildrenLength=$("#" +selectedVen+"_f").find(' > ul > li').length;	
	if(VenChildrenLength==0){
		
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
				//url: getContext()+'/FindOnClick_SuppSiteNodeCell',
				url: getContext()+'/FindOnClick_VenSiteNodeCell',
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
						var listVenSites=data.listVenSites;
						//console.log("listVenSites   ",listVenSites);
						if(VenChildrenLength<listVenSites.length){
							var dFrag = document.createDocumentFragment();
							for (n = 0; n < listVenSites.length; n++) {		
								var str = "<li class='SingleSiteVen' id='"+listVenSites[n][1]+"_"+listVenSites[n][4]+"' style='display:none; margin-left:-20px'><input type='checkbox' id='" + listVenSites[n][1]+"_"+listVenSites[n][4] +"_SingleSite' class='SingleSiteVen' style='margin-left: 15px' onclick='showMarkerSingleSite("+ listVenSites[n][1]+"_"+listVenSites[n][4] + "_SingleSite)'></input><span class='folder' onclick='StNdTpNdCellCore("+listVenSites[n][1]+"_"+listVenSites[n][4]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px' onclick='PanTreeSites("+listVenSites[n][1]+"_"+listVenSites[n][4]+")'><i class='fas fa-crosshairs fa-2x'></i>"+listVenSites[n][0]+"</span>";
								str+= "<ul><li id='" +listVenSites[n][1]+"_"+listVenSites[n][4]+"_f' class='NodeTypeFolder' style='display:none; margin-left:10px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> NodeType </span></span></li></ul></li></ul>";						   						    	
								const div = document.createElement('ul');
								div.innerHTML = str;
								dFrag.appendChild(div);   
							}  
						}
						document.getElementById(selectedVen+"_f").appendChild(dFrag);	
						tree_prop_selection("#" +selectedVen+"_f .SingleSiteVen .TreeSpan");
			            Tree_PropagationAppendedNodes(selectedVen+"_f .SingleSiteVen");
			            
			            $(".SingleSiteVen > .TreeSpan").contextmenu(function(){				
			        		selectedSingleSiteIdContext=$(this).parent().attr('id');
			        		var splitArray= selectedSingleSiteIdContext.split("_");
			         		idSite=splitArray[0] + "_" + splitArray[1] + "_" + splitArray[2];
			        		idVen = splitArray[3];
			        		menuName=SingleSite;			
			        		openContext(selectedSingleSiteIdContext,"",SingleSite,event);
			        	});
			            SingleSite = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			        				SiteVen_Boq(idSite,idVen);
			        			}	
			        		}
			        	]
			        });
				}
					 data=null;
				},
				error: function(result) {
					alert("Error");
				}
			});	
	}
}



function showMarkerSingleSite(id) {
	var id=id.id;
	var selectedSiteId = id.split('_').slice(0, 3).join('_');
	var vendorId= id.split('_').slice(3).join('_').split("_SingleSite")[0];
	
	    if ($("#" + id).is(":checked")) {
	     	//console.log("checked");
	     	var checkboxes = $('[id$="'+vendorId+'_SingleSite"]');
	      	var allChecked = true;
	      	for (var i = 0; i < checkboxes.length; i++) {
	        if (!checkboxes[i].checked) {
	         	allChecked = false;
	          	break;
	        }
	      }
	      if (allChecked) {
	    	document.getElementById(vendorId+'_Site').checked = true;
			 }
	      markersSites[selectedSiteId].setMap(map);
	      markerClusterSites.addMarker(markersSites[selectedSiteId]);
	      markerClusterSites.repaint();
	    }else{
		      document.getElementById(vendorId+'_Site').checked = false;		
		      markersSites[selectedSiteId].setMap(null);
		      markerClusterSites.removeMarker(markersSites[selectedSiteId]);
		      markerClusterSites.repaint();
	    }
	}


function PanTreeSites(id){
	var selectedItem = id.id.split('_').slice(0, 3).join('_');
	if(selectedItem!=markersSite) {
			var selMarker="";		
			markerId=selectedItem;				
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
			markersSite=selectedItem;		
			map.setZoom(15);
		}	
}

function StNdTpNdCellCore(id)
{
	var selectedItem = id.id.split('_').slice(0, 3).join('_');
	var selectedVen = id.id.split('_').slice(3).join('_');
	
	var SiteChildrenLength=$("#" +selectedItem	+"_" + selectedVen + "_f").find(' > ul > li').length;	
	if(SiteChildrenLength==0)
	{
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
				url: getContext()+'/FindOnClick_VenStNdTypNdCell',
				data: {
					"selectedItem":selectedItem,
					"selectedVen":selectedVen	,
					"paramEnterprise": paramEnterprise,
					"paramTransmission":paramTransmission,
			   		"paramRAN":paramRAN,
				    "paramCore":paramCore,
				    "date":date,
				},
				dataType: "json",
				success: function (data) {					        	
				if (data != null) {					            		
					var listNodesType=data.listNodesType;
					//console.log("listNodesType : ", listNodesType);
					for(j=0;j<listNodesType.length;j++)	
						{												
						var str= "<ul><li class='NodeType' id='" + listNodesType[j][0] +"_"+listNodesType[j][1]+"' style='display:none;margin-left:-20px;' class='folder'>";								  															
						str+="<span class='folder' onclick='VenNdCellCore(" + listNodesType[j][0] +"_"+listNodesType[j][1]+")'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
						str+= "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs'></i>"+listNodesType[j][0]+"</span></span></li></ul>";						
						$("#"+selectedItem+"_"+selectedVen+"_f").append(str);
						str="<ul><li id='" +listNodesType[j][0] +"_"+listNodesType[j][1]+"_f' class='NodeFolder' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node </span></span></li></ul>";				
						$("#" + listNodesType[j][0] +"_"+listNodesType[j][1]).append(str);								
						tree_prop_selection("#" +selectedItem+ "_" +selectedVen +"_f .NodeType .TreeSpan");
				        Tree_PropagationAppendedNodes(selectedItem+ "_" +selectedVen +"_f .NodeType");
				        
				        var selectedSingleNt;
				        var selectedSite;
			            $(".NodeType > .TreeSpan").contextmenu(function(){				
			        		selectedSingleNtIdContext=$(this).parent().attr('id');
			        		var index = selectedSingleNtIdContext.indexOf("WARE_2");
			    			if (index !== -1) {
			    				selectedSingleNt = selectedSingleNtIdContext.substring(0, index).slice(0, -1);
			    			  	selectedSite = selectedSingleNtIdContext.substring(index);
			    			}
			    			menuName=SingleNt;	
			        		openContext(selectedSingleNtIdContext,"",SingleNt,event);
			        	});
			            SingleNt = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {
			        			  //console.log("selectedItem......",selectedItem);
			        			  NodeTVen_Boq(selectedSite,selectedSingleNt,selectedVen);
			        			  selectedItem="";
			        			}	
			        		}
			        	]
			        });
					}
				}
				data= null;
			},
			error: function(result) {
				alert("Error");
									}
			});
		//}    	
	}
//	});
}

function VenNdCellCore(id){
	
	var selectedNodetType = id.id.split("_")[0];
	var selectedItem = id.id.substring(selectedNodetType.length + 1);
	var selectedVen = $(id).closest("li").closest(".Vendor").attr("id");
	   
		var NdTypeChildrenLength=$("#" + selectedNodetType+"_"+selectedItem+"_f").find(' > ul > li').length;
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
				url:getContext()+'/FindOnClick_VenStNdTypNdCell',
				data: {
					"selectedItem":selectedItem,
					"selectedNodetType":selectedNodetType,
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
						var listNodes=data.listNodes;
						var listCells=data.listCells;
						Create_TreeNode_CellGeneral(listNodes,listCells,NdTypeChildrenLength, true,selectedItem);
			            Tree_PropagationAppendedNodes(selectedNodetType+ "_" +selectedItem+"_f  .Node");
			            tree_prop_selection("#" + selectedNodetType+ "_" +selectedItem+"_f .Node .TreeSpan");
				}
					data= null;
				},
				error: function(result) {
					alert("Error");
				}
			});
		}
		//}
	//});
}
///////////////////////////////////////////////
/* End of Supp Site NodeType Node Cell Tree Method */ 

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&libraries=places&callback=initMap&amp;v=3.43&amp"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/maplabel-compiled.js"></script>