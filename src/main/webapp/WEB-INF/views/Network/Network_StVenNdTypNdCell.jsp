<%@ include file="NetworkCommon.jsp" %>
<title>Site Vendor Node Type Node Cell</title>
<script>	
$('#filterr').hide();
$('#removeFilter').hide();


var lst = ${listSites};
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
		map.setOptions({ minZoom: 3, maxZoom: 28});		
		CreateMap(lst,map,arrayParam,date);	
		CreateTree_StVenNdTpNdCell(lst,map);	
	}
	else{
		var Nairobi=new google.maps.LatLng(0.796530,37.959529);			
		map.setCenter(Nairobi);
		map.setZoom(6);
		var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StVenNdTypNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
		$("#network_tree").append(str);
		tree_prop_selection();
		folder();
	}
} /// End of init Map

function CreateTree_StVenNdTpNdCell(lst,map){
	var str="<ul><li id='initial_ul' class='Initial'><input type='checkbox' id='StVenNdTypNdCell_Sites' class='AllSites' style='margin-left: 15px' unchecked onclick='AllSitesCheckFilter()'></input> <span class='folder'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Sites</span></li></ul>";
	$("#network_tree").append(str);
	for (n = 0; n < lst.length; n++) {
			var str = "<ul><li class='Site' id='"+lst[n][2]+"' style='display:none; margin-left:-10px'><input type='checkbox' id='"+ lst[n][2]+"_SingleSite' class='StVenNdTypNdCell_SingleSite' unchecked onclick='showMarkersChecked("+ lst[n][2] +"_SingleSite)'></input> <span class='folder' onclick='StVenNdTypNdCell("+lst[n][2] +")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px' onclick='PanTreeSites("+lst[n][2]+")'><span class='tree-span' style='margin-left:-15px;'> <i class='fa fa-cogs fa-2x'></i> "+lst[n][1]+"</span></span>";
			str+= "<ul><li id='" +lst[n][2]+"_f' class='VendorFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Vendors </span></span></li></ul></li></ul>";
			$("#initial_ul").append(str);
	}
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
				$(this).parent().find('.StVenNdTypNdCell_SingleSite').each(function(){		
						$(this).prop('checked', true);
					});
				for(var x=0; x< markersSites.length; x++){		
						markersSites[markersSites[x].ID].setMap(map);			
						markerClusterSites.addMarker(markersSites[markersSites[x].ID]);
					}							
			}					
			else{		
				$(this).parent().find('.StVenNdTypNdCell_SingleSite').each(function() {
				$(this).prop('checked', false);	
			});
				for(var x=0; x< markersSites.length; x++){
					markersSites[markersSites[x].ID].setMap(null);
					markerClusterSites.removeMarker(markersSites[markersSites[x].ID]);
						}	
				}
	});
}

function showMarkersChecked(id){
	var id =id.id;
	var Id= id.split("_SingleSite")[0];
	// To be deleted	
	//$("#"+id).on('change', function() {								
		if ($("#"+id).is(":checked")) {	
			var checkboxes = document.querySelectorAll('.StVenNdTypNdCell_SingleSite');
			var allChecked = true;
			for (var i = 0; i < checkboxes.length; i++) {
			  if (!checkboxes[i].checked) {
			    allChecked = false;
			    break;
			  }
			}
			if (allChecked) {
				$('#network_tree input[type="checkbox"][id="StVenNdTypNdCell_Sites"]').prop('checked', true);
			} 		
  			markersSites[Id].setMap(map);
  			markerClusterSites.addMarker(markersSites[Id]);
  			markerClusterSites.repaint();
			}else {		
				$('#network_tree input[type="checkbox"][id="StVenNdTypNdCell_Sites"]').prop('checked', false);
				markersSites[Id].setMap(null);
				markerClusterSites.removeMarker(markersSites[Id]);
				markerClusterSites.repaint();
			}
		 // });
	}


function StVenNdTypNdCell(id){
	var siteId= id.id;
	var siteChildrenLength=$("#" +siteId+"_f").find(' > ul > li').length;
	if(siteChildrenLength==0){
			
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
			  url: getContext()+'/findStVenNdTypNdCell_Ven',
			  data: {
			          "siteId":siteId,
			          "paramEnterprise": paramEnterprise,
					  "paramTransmission":paramTransmission,
			   		  "paramRAN":paramRAN,
				      "paramCore":paramCore,
				      "date":date,
			 		},
			 dataType: "json",
			 success: function (data) {	
				 if (data != null) {	
					var listVendors=data.listVendors;
					if(siteChildrenLength<listVendors.length){
						var dFrag = document.createDocumentFragment(); 
						for (j = 0; j < listVendors.length; j++){								
						     str="<li id='"+ listVendors[j][0] +"_"+listVendors[j][1]+"' class='SingleVendor' style='display:none; margin-left:-30px'><span class='folder' onclick='RequestingNodeType("+ listVendors[j][0] +"_"+listVendors[j][1]+")'><i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listVendors[j][0]+"</span>";
						     str+= "<ul><li id='" + listVendors[j][0] +"_"+listVendors[j][1]+"_f' class='NodeTypeFolder' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node Type </span></span></li></ul></li>";						
						     const div = document.createElement('ul');
							div.innerHTML = str;
							dFrag.appendChild(div);          
						}
						document.getElementById(siteId+"_f").appendChild(dFrag);	
						tree_prop_selection("#" +siteId+"_f .SingleVendor .TreeSpan");
			            Tree_PropagationAppendedNodes(siteId+"_f .SingleVendor");
			            
			            var idSite;
			            $(".SingleVendor > .TreeSpan").contextmenu(function(){				
			        		selectedSingleVendorIdContext=$(this).parent().attr('id'); //venId_ware
			        		var index = selectedSingleVendorIdContext.indexOf("WARE_2");
			        		if (index !== -1) {
			        			idVen= selectedSingleVendorIdContext.substring(0, index-1);
			        		}
			        		siteId = selectedSingleVendorIdContext.substring(index);
			        		
			        		menuName=SingleVendor;			
			        		openContext(selectedSingleVendorIdContext,"",SingleVendor,event);
			        	});
			            SingleVendor = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
			        			  VenSite_Boq(idVen,siteId);
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
}

function RequestingNodeType(id) {
	var index = id.id.indexOf('WARE_2');
	if (index !== -1) {
		var selectedVen = id.id.substring(0, index-1);
	}
	var selectedItem = id.id.substring(index);
	
	var VendorChildrenLength=$("#" +selectedVen +"_" + selectedItem+"_f").find(' > ul > li').length;		
	if(VendorChildrenLength==0){
		
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
					for(j=0;j<listNodesType.length;j++)	{
						console.log("listNodesType[j][0] is " +listNodesType[j][0] + " listNodesType[j][1] is " +listNodesType[j][1] + " selectedVen is " +selectedVen);
						var str= "<ul><li class='NodeType' id='" + selectedVen + "_" + listNodesType[j][0] +"_"+listNodesType[j][1]+"' style='display:none;margin-left:-20px;' class='folder'>";								  															
						str+="<span class='folder' onclick='VenNdCellCore(" + selectedVen + "_" + listNodesType[j][0] +"_"+listNodesType[j][1]+")'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
						str+= "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs'></i>"+listNodesType[j][0]+"</span></span></li></ul>";
						$("#"+selectedVen +"_" + selectedItem+"_f").append(str);
						str="<ul><li id='" + selectedVen + "_" + listNodesType[j][0] +"_"+listNodesType[j][1]+"_f' class='NodeFolder' style='display:none; margin-left:-20px'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Node </span></span></li></ul>";				
						$("#" + selectedVen + "_" + listNodesType[j][0] +"_"+listNodesType[j][1]).append(str);								
				        
				        var selectedSingleNt;
				        var selectedSite;
			            $(".NodeType > .TreeSpan").contextmenu(function(){				
			        		selectedSingleNtIdContext=$(this).parent().attr('id');
			        		var index = selectedSingleNtIdContext.indexOf('WARE_2');
			        		if (index !== -1) {
			        			selectedSingleNt = selectedSingleNtIdContext.substring(0, index-1);
			        		}
			        		var selectedItem = selectedSingleNtIdContext.substring(index);	
			        		menuName=SingleNt;	
			        		openContext(selectedSingleNtIdContext,"",SingleNt,event);
			        	});
			            SingleNt = new ContextMenu({
			        	  'theme': 'default',
			        	  'items': [
			        		  {'icon': 'braille', 'name': 'Show BoQ', action: () => {
			        			  NodeTVen_Boq(selectedItem,selectedSingleNt,selectedVen);
			        			  selectedItem="";
			        			}	
			        		}
			        	]
			        });
					}
					tree_prop_selection("#" +selectedVen +"_" + selectedItem +"_f .NodeType .TreeSpan");
			        Tree_PropagationAppendedNodes(selectedVen +"_" + selectedItem +"_f .NodeType");
				}
				data= null;
			},
			error: function(result) {
				alert("Error");
									}
			});    	
	}
}

function PanTreeSites(id){
	var selectedItem = id.id;
		if(selectedItem!=markersSite)
			{
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


function VenNdCellCore(id){
	var indexSite = id.id.indexOf('WARE_2');
	var indexNdType = id.id.indexOf('_')
	console.log("indexSite is ", indexSite + " indexNdType is " +indexNdType);
	if (indexSite !== -1 && indexNdType !== -1) {
		var selectedNodetType = id.id.substring(indexNdType+1, indexSite-1);
		console.log("selectedNodetType is " +selectedNodetType);
	}
	var selectedItem = id.id.substring(indexSite);
	console.log("selectedItem is " +selectedItem);
	var selectedVenn = $("#" + id.id).closest("li.SingleVendor").attr("id"); 
	var selectedVen= selectedVenn.split("_")[0];
	console.log("selectedVen is " , selectedVen);
	
	var NdTypeChildrenLength=$("#" + selectedVen + "_" + selectedNodetType+"_"+selectedItem+"_f").find(' > ul > li').length;
	console.log("NdTypeChildrenLength is " ,NdTypeChildrenLength);
	
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
						Create_TreeNode_Cell_Vendor(listNodes,listCells,NdTypeChildrenLength, true,selectedItem, selectedVen);
			            tree_prop_selection("#" +selectedVen + "_"+selectedNodetType+"_"+selectedItem+"_f .Node .TreeSpan");
				}
					data= null;
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