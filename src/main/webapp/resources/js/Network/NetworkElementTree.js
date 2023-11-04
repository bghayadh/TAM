// This JavaScript File includes all the functions of the tree

///////////////////////////////////////////////
/*         Tree Section Network Tree         */ 
//////////////////////////////////////////////

function filter(lst, myArray){
	var results = [];	
	for (var i=0; i < lst.length; i++) {
		if (myArray.includes(lst[i][2])){
			results.push(lst[i]);		
		}
	}
	return results;
}	
///////////////////////////////////////////////
/*               Hover Tree              */ 
//////////////////////////////////////////////

$(document).ready(function() {
	HoverClickNetworkSpans();  
});

function HoverClickNetworkSpans(){
	//$("li > span").unbind("mouseover");
	$("#network_tree li > .TreeSpan").bind("mouseover","li > .TreeSpan",function(e) {
		$(this).addClass('backgroundTree');
	}).on("mouseout",function(e) {
		$(this).removeClass('backgroundTree');
	});
}	
///////////////////////////////////////////////
/*         Draw Sites with respct to specific parent example supplier or nodetype */ 
//////////////////////////////////////////////	
function Create_TreeParent(selectedItem,ptype){	
	//console.log("tree parent");
	//console.log("selectedItem..."+selectedItem);
	
	$.ajax({
	  type: "GET",
	  contentType: "application/json; charset=utf-8",
	  url: getContext()+'/resources/js/Network/NetworkTree.js/pTree',
	  data: {
			"selectedItem":selectedItem,
			"Ptype":ptype			
				 },
	dataType: "json",
	success: function (data) {
		if (data != null) { 	 	
			//console.log("data...",data);
			var filteredP=[];
			var filteredP=filter(lst, data.listSites);
			//console.log("filteredP........",filteredP);
			CreateMap(filteredP,map);									
				}		
		},
			error: function(result) {
				 alert("Error");
		 						 }								 
		 		});
}
///////////////////////////////////////////////
/*     Tree propagation fcts */ 
//////////////////////////////////////////////
function tree_prop_general(){
		//console.log(" tree porp general in network tree");
	$('.tree li > .TreeSpan').on('click', function (e) {	
		
		$("#initial_ul").find(' > ul > li').css("background", "").removeClass("selected-span");
		$(".tree li > .TreeSpan").css("background", "").removeClass("selected-span");
		$(this).css("background-color", "#97b9cc").addClass("selected-span");
		e.stopPropagation();
	   });
	$('.tree li:has(ul)').addClass('parent_li').find(' > .tree-span').attr('title', 'Collapse this branch'); 
	$("#network_tree i").css('margin-right', '5px');
}

function tree_PropInitial_li(){
$("#initial_ul > .folder").eq(n).on('click', function (e) {   	                
var children = $(this).parent('li.parent_li').find(' > ul > li');
if (children.is(":visible")) {
children.hide();
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
} else {
    children.show();
    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
                }
                e.stopPropagation();  
                
            });
	}
/*
function tree_Prop(selector){
var children="";
$(selector).click(function(e){
	
children = $(this).parent('li.parent_li').find(' > ul > li');
if (children.is(":visible")) {
	console.log("visible")
	children.toggle();
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
} else {
	console.log("notvisible")
	children.toggle();
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
}
e.stopPropagation(); 
	           });

		}
*/
function tree_Prop(selector){
	//console.log(" tree_Prop");
	$(selector).on('click', function (e) {
				children = $(this).parent('li.parent_li').find(' > ul > li');
				//console.log(" children "+ children);
				if ($(this).parent().find('> ul > li').is(":visible")) {
					$(this).parent('li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
					children.toggle();
					//console.log(" children closed");
				} else {							
					$(this).parent('li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
					children.toggle();
					//console.log(" children opened");
				}
	
				
	});
}	

///////////////////////////////////////////////
/*     Map Panning fcts */ 
//////////////////////////////////////////////
 function panTo(newLat, newLng) {
		   	  if (panPath.length > 0) {
		   	    // We are already panning...queue this up for next move
    panQueue.push([newLat, newLng]);
  } else {
    // Lets compute the points we'll use
panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
var curLat = map.getCenter().lat();
var curLng = map.getCenter().lng();
var dLat = (newLat - curLat)/STEPS;
var dLng = (newLng - curLng)/STEPS;
for (var i=0; i < STEPS; i++) {
  panPath.push([curLat + dLat * i, curLng + dLng * i]);
}
panPath.push([newLat, newLng]);
panPath.shift();      // LAZY SYNCRONIZED LOCK
   	    setTimeout(doPan, 20);
   	  }
   	}
   function doPan() {
   	  var next = panPath.shift();
   	  if (next != null) {
   	    // Continue our current pan action
    map.panTo( new google.maps.LatLng(next[0], next[1]));
    setTimeout(doPan, 20 );
  } else {
    // We are finished with this pan - check if there are any queue'd up locations to pan to 
    var queued = panQueue.shift();
    if (queued != null) {
      panTo(queued[0], queued[1]);
    }
  }
}
   
///////////////////////////////////////////////
/*     Drawing Site Waves fcts */ 
//////////////////////////////////////////////  
var circle1;
var circle2;
var timer;
function site_wave(map,position) {

  if (circle1 && circle1.setMap)
    circle1.setMap(null);
 
        clearInterval(timer);
 
 	
  circle1 = new google.maps.Circle({
    center: position,
	fillColor: '#ff0000',
fillOpacity: 0.2,
map: map,
radius: 140,
strokeColor: 'FFFFFF',
	strokeOpacity: 1,
	strokeWeight: 2
  });
var direction = 1;

timer=setInterval(function() {
    	          var radius = circle1.getRadius();
    	          if ((radius > 100) || (radius < 200)) {
    	              direction *= -1;
    	          }
    	          circle1.setRadius(radius + direction * 50);
    	      },400); 
}
///////////////////////////////////////////////
/*     Draw Sites under Suppler parent */ 
//////////////////////////////////////////////
//var sitesNCreated=[];
function Create_TreeSites(n){

dotmarker = {
		    path: google.maps.SymbolPath.CIRCLE,
        fillOpacity: 0.9,
        strokeColor: 'transparent',
        strokeOpacity: 0.9,
        strokeWeight: 1,
        scale: 7
		};
var id=n.id
tree_prop_general();	
tree_Prop("#" + id +"> span");
tree_Prop("#" + id +"_f > span");
$("#" + id +" > span").on('click',function () {	
var res=$(this ).parents()
		.map(function() {
			return this.id;
							})
					.get()
					.join( "," );	
parents=res.split(",,");
var supplier=parents[2];
parents2=parents[0].split("_"+supplier);
var selectedItem=parents2[0];
Site_Boq(selectedItem);
	if(selectedItem!=markersSite)
		{
		var selMarker="";		
markerId=selectedItem;				
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();
position=selMarker.getPosition();				
site_wave(map,position);						
selMarker.setIcon(dotmarker);				
panTo(latSitee, lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);					
if(markersSite!="")
		{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);
			}		
markersSite="";	
		markersSite=selectedItem;		
		map.setZoom(15);
	}	
if(!sitesNCreated.includes(selectedItem))
				{
					sitesNCreated.push(selectedItem);			
	 $.ajax({

		  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppSiteNodeCell',
  data: {
                "selectedItem":selectedItem,
"selectedSupp":supplier			                
 },
 dataType: "json",
 success: function (data) {			        	
     if (data != null) {
         var listSuppNodes=data.listSuppNodes;
         var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
//Create_TreeNode(listSuppNodes,1,3);	
  Create_TreeNode_Cell(listSuppNodes,"FindOnClick_SuppSiteNodeCell",siteChildren,true,true,2,"Sup",4,"Sup",selectedItem);					            						            				         					    
         }
     },
  error: function(result) {
     alert("Error");
			         }
			     });		         
						}
	});
}	   		   

///////////////////////////////////////////////
/*     Sites BOQ Fcts */ 
//////////////////////////////////////////////

var boqList=[];
var siteList=[];

function Site_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
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
	var paramAccess = true;
}else{
	var paramAccess = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}
	if(!siteList.includes(SiteId))
	{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetBoqList',
			data: {
			    "SiteId" : SiteId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramAccess" : paramAccess,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	}else{
		$('#boq_table').empty();
		for(var i=0; i< boqList.length; i++) {
		    if(SiteId==boqList[i].SiteId){	        		 
			var tr = "<tr>"+
			"<td class='title'> "+boqList[i].key+"</td>"+
			"<td> "+boqList[i].value+" </td>"+
			"</tr>";
			$("#boq_table").append(tr);     		    			   			
						 }		
								}
		}
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 
	   	 	      
}
/////////////////////////////////////////////////////////////////////////////////
function SiteVen_Boq(SiteId,VenId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
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
	var paramAccess = true;
}else{
	var paramAccess = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}
	if(!siteList.includes(SiteId))
	{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSiteVenBoqList',
			data: {
			    "SiteId" : SiteId,
			    "VenId" : VenId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramAccess" : paramAccess,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	}else{
		$('#boq_table').empty();
		for(var i=0; i< boqList.length; i++) {
		    if(SiteId==boqList[i].SiteId){	        		 
			var tr = "<tr>"+
			"<td class='title'> "+boqList[i].key+"</td>"+
			"<td> "+boqList[i].value+" </td>"+
			"</tr>";
			$("#boq_table").append(tr);     		    			   			
						 }		
								}
		}
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 
	   	 	      
}
//////////////////////////////////////////////////////////////////////////////////
function Supp_Boq(SuppId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
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
	var paramAccess = true;
}else{
	var paramAccess = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}
	//if(!siteList.includes(SiteId))
	//{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSupBoqList',
			data: {
			    "SuppId" : SuppId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramAccess" : paramAccess,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SuppId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SuppId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 	   	 	      
}


function SiteSupp_Boq(SiteId,SuppId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
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
	var paramAccess = true;
}else{
	var paramAccess = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}
	if(!siteList.includes(SiteId))
	{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSiteSupBoqList',
			data: {
			    "SiteId" : SiteId,
			    "SuppId" : SuppId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramAccess" : paramAccess,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	}else{
		$('#boq_table').empty();
		for(var i=0; i< boqList.length; i++) {
		    if(SiteId==boqList[i].SiteId){	        		 
			var tr = "<tr>"+
			"<td class='title'> "+boqList[i].key+"</td>"+
			"<td> "+boqList[i].value+" </td>"+
			"</tr>";
			$("#boq_table").append(tr);     		    			   			
						 }		
								}
		}
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 
	   	 	      
}
//////////////////////////////////////////////////////////////////////////////////
function Ven_Boq(VenId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
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
	var paramAccess = true;
}else{
	var paramAccess = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}
	//if(!siteList.includes(SiteId))
	//{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetVenBoqList',
			data: {
			    "VenId" : VenId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramAccess" : paramAccess,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(VenId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ VenId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});

	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 
	   	 	      
}
//////////////////////////////////////////////////////////////////////////////////
function SitePO_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
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
		var paramAccess = true;
	}else{
		var paramAccess = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
	
	//if(!siteList.includes(SiteId))
	//{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetBoqSitePoList',
			data: {
			    "SiteId" : SiteId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramAccess":paramAccess,
			    "paramCore":paramCore,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
		 /*
	}else{
		$('#boq_table').empty();
		for(var i=0; i< boqList.length; i++) {
		    if(SiteId==boqList[i].SiteId){	        		 
			var tr = "<tr>"+
			"<td class='title'> "+boqList[i].key+"</td>"+
			"<td> "+boqList[i].value+" </td>"+
			"</tr>";
			$("#boq_table").append(tr);     		    			   			
						 }		
								}
		}
		*/
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 	   	 	      
}
//////////////////////////////////////////////////////////////////
var boqList=[];
var sitePList=[];

function POSite_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
	//if(!sitePList.includes(SiteId))
	//{	
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
		var paramAccess = true;
	}else{
		var paramAccess = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}

		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetPOSiteBoqList',
			data: {
				"POID" : SiteId,
				"paramEnterprise": paramEnterprise,
				"paramTransmission":paramTransmission,
				"paramAccess":paramAccess,
				"paramCore":paramCore,
				},
			success : function(data)
			{
				$('#boq_table').empty();
				sitePList.push(SiteId);	          	 
				$.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
			alert("Error");
			}			
		});
	//}
		/*
	else{
		$('#boq_table').empty();
		for(var i=0; i< boqList.length; i++) {
			if(SiteId==boqList[i].SiteId){	        		 
				var tr = "<tr>"+
				"<td class='title'> "+boqList[i].key+"</td>"+
				"<td> "+boqList[i].value+" </td>"+
				"</tr>";
				$("#boq_table").append(tr);     		    			   			
			}		
		}
	}
	*/
	$("#boqBtn").removeClass().addClass("tablinks active");
	$("#Defaultbutton").removeClass().addClass("tablinks");
	$("#optionBtn").removeClass().addClass("tablinks");
	Boq.css({ display:'block'});
	Layers.css({ display:'none'});
	Options.css({ display:'none'});
	Boq.tab('show');	 
}


///////////////////////////////////////////////
/*     Nodes BOQ Fcts */ 
//////////////////////////////////////////////
var siteNList=[];
var boqNList=[];

function  Node_Boq(WareId,NodeId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');

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
		var paramAccess = true;
	}else{
		var paramAccess = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}

	if(!siteNList.includes(NodeId))
	{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNodeBoqList',
			data: {
			    "WareId":WareId,
			    "NodeId":NodeId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramAccess":paramAccess,
			    "paramCore":paramCore,
			    },
			success : function(data)
			    {
				$('#boq_table').empty();
				siteNList.push(NodeId); 
				$.each(data , function( key, value ) {	
					boqNList.push({ NodeId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
			});
		}else{
			$('#boq_table').empty();
			for(var i=0; i< boqNList.length; i++) {
			if(NodeId==boqNList[i].NodeId){	        		 
				var tr = "<tr>"+
				"<td class='title'> "+boqNList[i].key+"</td>"+
				"<td> "+boqNList[i].value+" </td>"+
				"</tr>";
				$("#boq_table").append(tr);     		    			   			
								 }		
				}
	}				
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}

///////////////////////////////////////////////
/*     Node type BOQ Fcts */ 
//////////////////////////////////////////////
var siteNTList=[];
var boqNTList=[];

function  NodeT_Boq(SiteId,NodeTId){
	
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');

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
		var paramAccess = true;
	}else{
		var paramAccess = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
	//if(!siteNTList.includes(NodeTId))
	//{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNtypeBoqList',
			data: {
			    "SiteId":SiteId,
			    "NodeTId":NodeTId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramAccess":paramAccess,
			    "paramCore":paramCore,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteNTList.push(NodeTId); 
				$.each(data , function( key, value ) {	
					boqNTList.push({ NodeTId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
				});
			//}else{
		 /*
				console.log(" siteNTList");
				 $('#boq_table').empty();
				 for(var i=0; i< boqNTList.length; i++) {
					if(NodeTId==boqNTList[i].NodeTId){	        		 
						var tr = "<tr>"+
						"<td class='title'> "+boqNTList[i].key+"</td>"+
						"<td> "+boqNTList[i].value+" </td>"+
						"</tr>";
						$("#boq_table").append(tr);     		    			   			
										 }		
												}	
												*/				
	//}				
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}


/*     Node type supp BOQ Fcts */
function  NodeTSup_Boq(SiteId,NodeTId,SuppId){

	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');

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
		var paramAccess = true;
	}else{
		var paramAccess = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
	//if(!siteNTList.includes(NodeTId))
	//{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNtypeSupBoqList',
			data: {
			    "SiteId":SiteId,
			    "NodeTId":NodeTId,
			    "SuppId":SuppId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramAccess":paramAccess,
			    "paramCore":paramCore,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteNTList.push(NodeTId); 
				$.each(data , function( key, value ) {	
					boqNTList.push({ NodeTId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
				});
		
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}

/*     Node type vendor BOQ Fcts */
function NodeTVen_Boq(SiteId,NodeTId,VendorId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');

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
		var paramAccess = true;
	}else{
		var paramAccess = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
	//if(!siteNTList.includes(NodeTId))
	//{
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNtypeVenBoqList',
			data: {
			    "SiteId":SiteId,
			    "NodeTId":NodeTId,
			    "VendorId":VendorId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramAccess":paramAccess,
			    "paramCore":paramCore,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteNTList.push(NodeTId); 
				$.each(data , function( key, value ) {	
					boqNTList.push({ NodeTId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
				});
			
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}
///////////////////////////////////////////////
/*     PO BOQ Fcts */ 
//////////////////////////////////////////////

var boqList=[];
//var sitePList=[];

function PO_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
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
		var paramAccess = true;
	}else{
		var paramAccess = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
	
	//if(!sitePList.includes(SiteId))
	//{		
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetPOBoqList',
			data: {
				"POID" : SiteId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramAccess":paramAccess,
			    "paramCore":paramCore,
			},
			success : function(data)
			{
				$('#boq_table').empty();
				sitePList.push(SiteId);	          	 
				$.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
			alert("Error");
			}			
		});
	//}
	/*
	else{
		$('#boq_table').empty();
		for(var i=0; i< boqList.length; i++) {
			if(SiteId==boqList[i].SiteId){	        		 
				var tr = "<tr>"+
				"<td class='title'> "+boqList[i].key+"</td>"+
				"<td> "+boqList[i].value+" </td>"+
				"</tr>";
				$("#boq_table").append(tr);     		    			   			
			}		
		}
	}
	*/
	$("#boqBtn").removeClass().addClass("tablinks active");
	$("#Defaultbutton").removeClass().addClass("tablinks");
	$("#optionBtn").removeClass().addClass("tablinks");
	Boq.css({ display:'block'});
	Layers.css({ display:'none'});
	Options.css({ display:'none'});
	Boq.tab('show');	 
}


///////////////////
function getContext() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

///////////////////////////////////////////////
/*        Start of Site Tree Method         */ 
//////////////////////////////////////////////
function CreateTree_Site(lst,map){
Site_Boq("");
//HoverClickNetworkSpans();               	
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li></ul>";
$("#network_tree").append(str);
$("#network_tree").append("<label id='loadmore' style='margin-left:35%;margin-bottom:1%;visibility:hidden;'> Scroll to load more</label>");	 

 var dFrag = document.createDocumentFragment();
 for (n = 0; n < lst.length; n++){ 
		str="<li id='"+lst[n][2]+"' class='Site' style='display:none;width:100px;'><span class='folder' onclick='SiteCore("+lst[n][2]+")'><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+lst[n][1]+"</span>";
   const div = document.createElement('ul');
   div.innerHTML = str;
   dFrag.appendChild(div);
} 
// Add fragment to a list: 
 document.getElementById('initial_ul').appendChild(dFrag);	  			      
}             

function SiteCore(n)
{
	
	dotmarker = {
		    path: google.maps.SymbolPath.CIRCLE,
        fillOpacity: 0.9,
        strokeColor: 'transparent',
        strokeOpacity: 0.9,
        strokeWeight: 1,
        scale: 7
		};
	
	 var selectedItem=n.id;
	         tree_PropInitial_li();  
            var markersSite="";
 $("#"+selectedItem+" > span").on('click',
 function (e) {
		 Site_Boq(selectedItem); 
	
if(selectedItem!=markersSite){
	var selMarker="";				
		markerId=selectedItem;
		selMarker=markers[markerId];
		var latSitee = selMarker.getPosition().lat();
		var lngSitee = selMarker.getPosition().lng();
		position=selMarker.getPosition();
site_wave(map,position);					
		selMarker.setIcon(dotmarker);
		panTo(latSitee, lngSitee);					
		window.infowindow.setContent(selMarker.data);
		window.infowindow.open(map,selMarker);
		const pos = new google.maps.LatLng(latSitee,lngSitee);					
		if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);
							
	}
markersSite="";	
					markersSite=selectedItem;				
					map.setZoom(15);                          
                    }                        
                       });
                    }

function panTo(newLat, newLng) {
	  if (panPath.length > 0) {
	    panQueue.push([newLat, newLng]);
	  } else {
	    panPath.push("LAZY SYNCRONIZED LOCK");  // make length non-zero - 'release' this before calling setTimeout
var curLat = map.getCenter().lat();
var curLng = map.getCenter().lng();
var dLat = (newLat - curLat)/STEPS;
var dLng = (newLng - curLng)/STEPS;
for (var i=0; i < STEPS; i++) {
  panPath.push([curLat + dLat * i, curLng + dLng * i]);
}
panPath.push([newLat, newLng]);
panPath.shift();      // LAZY SYNCRONIZED LOCK
	    setTimeout(doPan, 20);
	  }
	}
function doPan() {
	  var next = panPath.shift();
	  if (next != null) {
	    map.panTo( new google.maps.LatLng(next[0], next[1]));
	    setTimeout(doPan, 20 );
	  } else {
	    var queued = panQueue.shift();
	    if (queued != null) {
	      panTo(queued[0], queued[1]);
	    }
	 }
}
///////////////////////////////////////////////
/*        End of Site Tree Method         */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
//////////////////////////////////////////////
///////////////////////////////////////////////
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of NodeType Site  Node Cell Tree Method */ 
//////////////////////////////////////////////

function CreateTree_NdTypStNdCell(listNodesType,listSites,map){
	//var NSitesCreated=[];
	//var sitesNCreated=[];
	Site_Boq("");
/////////////  /////////////  ////////////	/////////
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Node Type</span></li></ul>";
$("#network_tree").append(str);
for (n = 0; n < listNodesType.length; n++) {
if(listNodesType[n][1]>0){
var str = "<ul><li class='NodeType' id='"+listNodesType[n][0]+"' style='display:none'><span class='folder'></span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs fa-2x'></i>"+listNodesType[n][0]+"</span>";
str+= "<ul><li id='" +listNodesType[n][0]+"_f' class='NodeTypeFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Site </span></li></ul></li></ul>";
$("#initial_ul").append(str);
}
tree_prop_general();
tree_Prop("#"+listNodesType[n][0]+ "> span");
tree_Prop("#"+listNodesType[n][0]+ "_f > span");
NdTypStNdCellCore(n);
}	
/////////////  /////////////  ////////////	/////////
function NdTypStNdCellCore(n){	
$("#"+listNodesType[n][0]+"> span").on('click',
function (e) {
var selectedNodetType=$(this).parent().attr('id');	
NodeT_Boq("",selectedNodetType);	
Create_TreeParent(selectedNodetType	,"Nodetype");
if(!NSitesCreated.includes(selectedNodetType))
	{
NSitesCreated.push(selectedNodetType);
$.ajax({
		  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/findNodeTypeSiteNode_Cells',
  data: {
                "selectedNodetType":selectedNodetType,							                
 },
 dataType: "json",
 success: function (data) {	
     if (data != null) {
//var NodeTypeChildren=$("#" +selectedNodetType+"_f").find(' > ul > li');
var NodeTypeChildrenLength=$("#" +selectedNodetType+"_f").find(' > ul > li').length;
var listSites=data.listSites;			
if(NodeTypeChildrenLength<listSites.length){
var dFrag = document.createDocumentFragment();
for (j = 0; j < listSites.length; j++){
str="<li id='"+ listSites[j][2] +"_"+listSites[j][3]+"' class='Site'style='display:none'><span onclick='StNdCellCore2("+ listSites[j][2] +"_"+listSites[j][3]+")' ><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSites[j][1]+"</span>";
str+= "<ul><li id='" + listSites[j][2] +"_"+listSites[j][3]+"_f' class='NodeFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";

const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);          
}
document.getElementById(selectedNodetType+"_f").appendChild(dFrag);		
}                                     
		}	             
 },
 error: function(result) {
     alert("Error");
		         }
		     });										
	}
});   		        			
}	  					        		
}

function StNdCellCore2(n){
var id=n.id;
tree_prop_general();
tree_Prop("#" + id +"> span");
tree_Prop("#" + id +"_f > span");
$("#" + id +" > span").on('click',function () {								
var res=$(this ).parents()
.map(function() {
	return this.id;
					})
			.get()
			.join( "," );	
parents=res.split(",,");
var selectedNodetType=parents[2];
parents2=parents[0].split("_"+selectedNodetType);
var selectedItem=parents2[0];
	Site_Boq(selectedItem);		
				
if(selectedItem!=markersSite)
	{
var selMarker="";	
markerId=selectedItem;				
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
selMarker.setIcon(dotmarker);
position=selMarker.getPosition();
site_wave(map,position);
panTo(latSitee, lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);									
if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);										
		}
markersSite="";	
				map.setZoom(15);
				}
if(!sitesNCreated.includes(selectedItem))
		{
	sitesNCreated.push(selectedItem);				
		 $.ajax({
			  type: "GET",
  contentType: "application/json; charset=utf-8",
 url:getContext()+'/resources/js/Network/NetworkTree.js/findNodeTypeSiteNode_Cells',
  data: {
"selectedItem":selectedItem,
"selectedNodetType":selectedNodetType,														                
 },
 dataType: "json",
 success: function (data) {														        	
     if (data != null) {
		var listNodes=data.listNodes;
	
var SiteChildrenLength=$("#" + selectedItem+"_"+selectedNodetType+"_f").find(' > ul > li').length;																	
						if(SiteChildrenLength==0){
Create_TreeNode_Cell(listNodes,"findNodeTypeSiteNode_Cells",SiteChildrenLength,true,true,2,"N",4,"null",selectedItem);					            												    
				}
     	}
 },
 error: function(result) {
     alert("Error");
									         }
									     });
								}
					});     
}

///////////////////////////////////////////////
/* End of NodeType Site  Node Cell Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of NodeType Node Tree Method */ 
//////////////////////////////////////////////
function CreateTree_NodeTypeNode(listNodesType,map){
	Site_Boq("");
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Node Type</span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n=0; n < listNodesType.length; n++) {
 var str = "<li class='NodeType' id='"+listNodesType[n][0]+"' style='display:none'><span  onclick='NdTpNdCore("+listNodesType[n][0]+")' > <i class='fa fa-cogs fa-2x'></i>"+listNodesType[n][0]+"</span>";
 str+= "<ul><li id='" +listNodesType[n][0]+"_f' class='NodeFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
 const div = document.createElement('ul');
 div.innerHTML = str;
 dFrag.appendChild(div);		  		
}
document.getElementById('initial_ul').appendChild(dFrag);	
}	

   function NdTpNdCore(n)
    {

    var NodeCreated=[];
    var selectedItem=n.id;
    tree_Prop("#"+selectedItem+ "> span");
    tree_Prop("#"+selectedItem+ "_f > span");
    $("#"+selectedItem+ "> span").on('click',function () {			
     var selectedNdTyp=$(this).parent().attr('id');
 NodeT_Boq("",selectedNdTyp);
 ////////////////
Create_TreeParent(selectedNdTyp,"Nodetype");						
 /////////////////
	if(!NodeCreated.includes(selectedNdTyp))
	{
		NodeCreated.push(selectedNdTyp);
 
	 $.ajax({
			  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/findNodeType_Nodes',
  data: {
                "selectedItem":selectedNdTyp,
 },
 dataType: "json",
 success: function (data) {							        	
     if (data != null) {
    	var NdTpChildrenLength=$("#" +selectedNdTyp+"_f").find(' > ul > li').length;		
var listNodes=data.listNodes;

var dFrag = document.createDocumentFragment();

if(NdTpChildrenLength<listNodes.length){							            	
for (n = 0; n < listNodes.length; n++)      //  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
{	
	 //var str = "<li class='Node' id='"+listNodes[n][4]+"' style='display:none'><span onclick='NodeTree1("+listNodes[n][4]+")' > <i class='fa fa-server'></i>"+listNodes[n][2]+"</span>";
	 var str = "<li class='Node' id='"+listNodes[n][4]+"' style='display:none'><span class='folder'  onclick='NodeTree1("+listNodes[n][4]+")' > <i class='fa fa-server'></i>"+listNodes[n][2]+"</span>";

	 
	 const div = document.createElement('ul');
	div.innerHTML = str;
	dFrag.appendChild(div);													
	}	
document.getElementById(selectedNdTyp+"_f").appendChild(dFrag);																																					
     	}								            		                     
     }
 },
 error: function(result) {
     alert("Error");
				         }
				     });
		}
			});		
		}
   var markersSite="";
   function NodeTree1(n){
	   
	    dotmarker = {
			    path: google.maps.SymbolPath.CIRCLE,
	        fillOpacity: 0.9,
	        strokeColor: 'transparent',
	        strokeOpacity: 0.9,
	        strokeWeight: 1,
	        scale: 7
			};   
	   
	   var tst=Array.from(n);
	   selectedItem=tst[0].id;
	  
   $("#"+selectedItem).on('click',
   function (e) {	

   if(selectedItem!=markersSite)
   {
   	console.log("==============///////////clicked"+selectedItem);
   var selMarker="";
   markerId=selectedItem;	
   Site_Boq(selectedItem);			
   selMarker=markers[markerId];
   var latSitee = selMarker.getPosition().lat();
   var lngSitee = selMarker.getPosition().lng();					
   selMarker.setIcon(dotmarker);
   position=selMarker.getPosition();
   site_wave(map,position);
   panTo(latSitee, lngSitee);
   infowindow.setContent(selMarker.data);
   infowindow.open(map,selMarker);
   if(markersSite!="")
   {
   	var otherMarkers=markers[markersSite];			
   	otherMarkers.setIcon(dotmarker);																				
   }
   markersSite="";	
   	markersSite=selectedItem;		
   	map.setZoom(15);
   	}
   });
   } 
                  
///////////////////////////////////////////////
/* End of NodeType Node Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of CELL Tree Method */ 
//////////////////////////////////////////////
//Cells Tree
function CreateTree_Cell(lst,map){
	
	var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Cells</span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n = 0; n < lst.length; n++) {  
  var str = "<ul><li class='Cell' id='" +lst[n][0]+ "' style='display:none' ><span class='folder' onclick='CellPan("+lst[n][3]+")' style='display:none'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-vector-square fa-2x'></i>"+lst[n][1]+"</span>";
  const div = document.createElement('ul');
  div.innerHTML = str;
  dFrag.appendChild(div);

}
document.getElementById('initial_ul').appendChild(dFrag);	

function CellPan(n){
 var selectedItem=n.id;
$("#"+selectedItem+" > span").on('click',
		             function (e) {	
if(selectedItem!=markersSite)
{
	console.log("==============///////////clicked"+SiteName);
var selMarker="";
markerId=selectedItem;	
Site_Boq(selectedItem);			
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
selMarker.setIcon(dotmarker);
position=selMarker.getPosition();
site_wave(map,position);
panTo(latSitee, lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);
if(markersSite!="")
{
	var otherMarkers=markers[markersSite];			
	otherMarkers.setIcon(dotmarker);																				
}
markersSite="";	
	markersSite=selectedItem;		
	map.setZoom(15);
	}
});
}   	 	      	 	      
}
///////////////////////////////////////////////
/* End of CELL Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of Node Tree Method */ 
//////////////////////////////////////////////

function CreateTree_Nodes(lst,map){
Site_Boq("");             	
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Nodes</span></li></ul>";
$("#network_tree").append(str);

 var dFrag = document.createDocumentFragment();
 for (n = 0; n < lst.length; n++){ 
		//str="<li id='"+lst[n][4]+"'  style='display:none;width:100px;'><span class='folder' onclick='NodeTree("+lst[n][4]+")' ><i class='fa fa-server fa-2x'></i> "+lst[n][2]+"</span>";
		 var str = "<li class='Node' id='"+lst[n][4]+"' style='display:none'><span class='folder'  onclick='NodeTree("+lst[n][4]+")' > <i class='fa fa-server'></i>"+lst[n][2]+"</span>";

		const div = document.createElement('ul');
   
   div.innerHTML = str;
   dFrag.appendChild(div);
} 
// Add fragment to a list: 
 document.getElementById('initial_ul').appendChild(dFrag);	  
 
} 
 var markersSite="";
 function NodeTree(n){
 var tst=Array.from(n);
 selectedItem=tst[0].id;
 $("#"+selectedItem+" > span").on('click',
 function (e) {	

 if(selectedItem!=markersSite)
 {
 var selMarker="";
 markerId=selectedItem;	
 Site_Boq(selectedItem);			
 selMarker=markers[markerId];
 var latSitee = selMarker.getPosition().lat();
 var lngSitee = selMarker.getPosition().lng();					
 selMarker.setIcon(dotmarker);
 position=selMarker.getPosition();
 site_wave(map,position);
 panTo(latSitee, lngSitee);
 infowindow.setContent(selMarker.data);
 infowindow.open(map,selMarker);
 if(markersSite!="")
 {
 	var otherMarkers=markers[markersSite];			
 	otherMarkers.setIcon(dotmarker);																				
 }
 markersSite="";	
 	markersSite=selectedItem;		
 	map.setZoom(15);
 	}
 });
 } 
///////////////////////////////////////////////
/* End of Node Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of Node Cell Tree Method */ 
//////////////////////////////////////////////
 function CreateTree_NdCell(lst,map) {
 Site_Boq("");             	
 var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Nodes</span></li></ul>";
 $("#network_tree").append(str);
  var dFrag = document.createDocumentFragment();
  for (n = 0; n < lst.length; n++){ 
	 var str = "<li class='Node' id='"+lst[n][4]+"' style='display:none'><span class='folder'  onclick='NdCellCore("+lst[n][4]+")' > <i class='fa fa-server'></i>"+lst[n][2]+"</span>";
 str+= "<ul><li id='" +lst[n][4]+"_f' class='CellFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul></li></ul>"; 
 const div = document.createElement('ul'); 
    div.innerHTML = str;
    dFrag.appendChild(div);
 } 
 // Add fragment to a list: 
  document.getElementById('initial_ul').appendChild(dFrag);	   
 } 

  var NCellCreated=[];  
  var markersSite="";
  function NdCellCore(n)
   {
	 
	  dotmarker = {
			    path: google.maps.SymbolPath.CIRCLE,
	        fillOpacity: 0.9,
	        strokeColor: 'transparent',
	        strokeOpacity: 0.9,
	        strokeWeight: 1,
	        scale: 7
			};
	   
	   var tst=Array.from(n);
		
	 	 if (tst.length > 0) {
	         selectedItem=tst[0].id;
	       }
		 else {
			 var selectedItem=n.id;
		 }
	   tree_prop_general();
	   tree_Prop("#"+selectedItem+"> span");
	   tree_Prop("#"+selectedItem+"_f > span");
	   
	   $("#"+selectedItem+ "> span").on('click',function () {
 var selectedNode=$(this).parent().attr('id');
 if(selectedItem!=markersSite)
 {
 
 var selMarker="";
 markerId=selectedItem;	
 Site_Boq(selectedItem);			
 selMarker=markers[markerId];
 var latSitee = selMarker.getPosition().lat();
 var lngSitee = selMarker.getPosition().lng();					
 selMarker.setIcon(dotmarker);
 position=selMarker.getPosition();
 site_wave(map,position);
 panTo(latSitee, lngSitee);
 infowindow.setContent(selMarker.data);
 infowindow.open(map,selMarker);
 if(markersSite!="")
 {
 	var otherMarkers=markers[markersSite];			
 	otherMarkers.setIcon(dotmarker);																				
 }
 markersSite="";	
 	markersSite=selectedItem;		
 	map.setZoom(15);
 	}
if(!NCellCreated.includes(selectedNode))
	{
	NCellCreated.push(selectedNode);
	 $.ajax({
			  type: "GET",
 contentType: "application/json; charset=utf-8",
 url: getContext()+'/resources/js/Network/NetworkTree.js/findNode_Cells',
 data: {
               "selectedNode":selectedNode,
},
dataType: "json",
success: function (data) {							        	
    if (data != null) {
   	var NdChildrenLength=$("#" +selectedNode+"_f").find(' > ul > li').length;		
var listCells=data.listCells;
var dFrag = document.createDocumentFragment();
if(NdChildrenLength<listCells.length){							            	
for(j=0;j<listCells.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
	{												
var str= "<ul><li class='Cells' id='" + listCells[j][0] +"' style='display:none' class='folder'>";					  				
str += "<span class='tree-span' style='margin-left:21px;'><i class='fa fa-vector-square'></i>"+listCells[j][1]+"</span></li></ul>";
const div = document.createElement('ul'); 
div.innerHTML = str;
dFrag.appendChild(div);
$("#"+selectedNode+"_f").append(str);
																																					
			}
tree_prop_general();
document.getElementById(selectedNode+"_f").appendChild(dFrag);
    	}								            		                     
    }
},
error: function(result) {
    alert("Error");
						         }
						     });
				}
					});		
				} 
  
///////////////////////////////////////////////
/* End of Node Cell Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
function CreateTree_NdTpNdCell(listNodesType,map){
Site_Boq("");
//var NodeCreated=[];
function NdTpNdCellCore(n)
{
	tree_prop_general();	
	tree_Prop("#"+listNodesType[n][0]+ "> span");
	tree_Prop("#"+listNodesType[n][0]+ "_f > span");
 
 $("#"+listNodesType[n][0]+" > span").on('click',
 function (e) {					
 var selectedNdTyp=$(this).parent().attr('id');
 NodeT_Boq("",selectedNdTyp);
 ////////////////
Create_TreeParent(selectedNdTyp,"Nodetype");						
 /////////////////
if(!NodeCreated.includes(selectedNdTyp))
{
NodeCreated.push(selectedNdTyp); 
	 $.ajax({
			  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/findNodeType_Nodes',
  data: {
                "selectedItem":selectedNdTyp,
 },
 dataType: "json",
 success: function (data) {							        	
     if (data != null) {
    	var NdTpChildrenLength=$("#" +selectedNdTyp+"_f").find(' > ul > li').length;		
var listNodes=data.listNodes;
var dFrag = document.createDocumentFragment();
if(NdTpChildrenLength<listNodes.length){							            	
for (j = 0; j < listNodes.length; j++)      //  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
{										
str = "<li class='Node' id='"+listNodes[j][4]+"' style='display:none'><span  onclick='NdCellCore("+listNodes[j][4]+")' > <i class='fa fa-server'></i>"+listNodes[j][2]+"</span>";
str+= "<ul><li id='" +listNodes[j][4]+"_f' class='CellFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul></li></ul>"; 
const div = document.createElement('ul');
	div.innerHTML = str;
	dFrag.appendChild(div);													
	}												
document.getElementById(selectedNdTyp+"_f").appendChild(dFrag);																																					
     	}								            		                     
     }
 },
 error: function(result) {
     alert("Error");
				         }
				     });
		}
			});		
		}

var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Node Type</span></li></ul>";
$("#network_tree").append(str);
for (n = 0; n < listNodesType.length; n++) {
	 var str = "<ul><li class='NodeType' id='"+listNodesType[n][0]+"' style='display:none'><span class='folder'> </span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs fa-2x'></i>"+listNodesType[n][0]+"</span>";
str+= "<ul><li id='" +listNodesType[n][0]+"_f' class='NodeFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";

$("#initial_ul").append(str);
NdTpNdCellCore(n);			 	
	}	
	}			
///////////////////////////////////////////////
/* End of NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of Site PO Item Tree Method */ 
//////////////////////////////////////////////

function CreateTree_StPoItem(lst,map){
Site_Boq("");
var str="<ul ><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li></ul>";
$("#network_tree").append(str);
$("#network_tree").append("<label id='loadmore' style='margin-left:35%;margin-bottom:1%;visibility:hidden;'> Scroll to load more</label>");	 
var dFrag = document.createDocumentFragment();
for (n = 0; n < lst.length; n++){ 
str="<li id='"+lst[n][2]+"' class='Site' style='display:none;width:100px;'><span class='folder' onclick='StPoItemCore("+lst[n][2]+")' ><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+lst[n][1]+"</span>";
str+= "<ul><li id='" +lst[n][2]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> PO </span></li></ul></li></ul>";
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);
} 
// Add fragment to a list: 
document.getElementById('initial_ul').appendChild(dFrag);		
}


function StPoItemCore(n){ 
	//var POCreated=[];
	//var itemCreated=[];
	console.log(n);
	 var selectedItem=n.id;
		tree_prop_general();	
		tree_Prop("#"+selectedItem+ "> span");
		tree_Prop("#"+selectedItem+ "_f > span");
$("#"+selectedItem+" > span").on('click',
     function (e) {    					
Site_Boq(selectedItem);
if(selectedItem!=markersSite)
	{
var selMarker="";    					
markerId=selectedItem;
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
selMarker.setIcon(dotmarker);
position=selMarker.getPosition();
site_wave(map,position);
panTo(latSitee, lngSitee); 						
window.infowindow.setContent(selMarker.data);
window.infowindow.open(map,selMarker);
const pos = new google.maps.LatLng(latSitee,lngSitee);
if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);
	}
map.setZoom(15);
if(!POCreated.includes(selectedItem))
{
	POCreated.push(selectedItem); 
 $.ajax({
		  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/findSitePO_Items',
  data: {
                "selectedItem":selectedItem,
        "POAlreadyCreated":"false"
 },
 dataType: "json",
 success: function (data) {   									        	
     if (data != null) {
var SiteChildrenLength=$("#" +selectedItem+"_f").find(' > ul > li').length;	
	var listPO=data.listPO;
	console.log(listPO);
  if(SiteChildrenLength<listPO.length){       
for(j=0;j<listPO.length;j++){  													
var str= "<ul><li class='PO' id='" + listPO[j][0] +"' style='display:none' class='folder'>";   										  					

var str = "<ul><li class='PO' id='"+listPO[j][0]+"_"+listPO[j][1]+"' style='display:none'><span class='folder' style='color: #08526D'></span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-file-invoice-dollar fa-2x'></i>"+listPO[j][0]+"</span>";
 str+= "<ul><li id='" +listPO[j][1]+"_"+listPO[j][0]+"_f' class='ItemFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Item </span></li></ul></li></ul>";
 $("#"+listPO[j][1]+"_f").append(str);
	 													
tree_prop_general();	
tree_Prop("#"+listPO[j][0]+"_"+listPO[j][1]+"> span");
tree_Prop("#"+listPO[j][0]+"_"+listPO[j][1]+"_f > span");

$("#"+listPO[j][0]+"_"+listPO[j][1]+"> span").on('click',function () {																					
var res=$(this ).parents()
  				.map(function() {
	    			return this.id;
	  						})
	  			.get()
	  			.join( "," );
parents=res.split(",,");
parents2=res.split("_");
var selectedItem=parents[2];
parents2=res.split("_");
var selectedPo=parents2[0];   												 															

PO_Boq(selectedPo);
var ware = selectedItem.substring(0, 4);
if(selectedItem!=markersSite)
		{
var selMarker="";													
markerId=selectedItem;				
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();				
selMarker.setIcon(dotmarker);
panTo(latSitee, lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);													
if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(icon);																
	}
map.setZoom(15);																						
	}
if(!itemCreated.includes(selectedPo))
{
	itemCreated.push(selectedPo); 
$.ajax({
	  type: "GET",
contentType: "application/json; charset=utf-8",
 url:getContext()+'/resources/js/Network/NetworkTree.js/findSitePO_Items',
  data: {
                "selectedPO":selectedPo,
"POAlreadyCreated":"True",
"selectedItem":selectedItem  														                
 },
 dataType: "json",
success: function (data) {        	
 if (data != null) {
var PoChildrenLength=$("#" +selectedPo+"_f").find(' > ul > li').length;
var listItem=data.itemList;	
if(PoChildrenLength<listItem.length){ 
for (k = 0; k <listItem.length; k++) {
var str="<ul><li class='Items' id='" + listItem[k][0] + "' style='display:none'><span class='tree-span' ><i class='fa fa-bahai  fa-2x'></i>"+listItem[k][1]+"</span></li></ul>";
$("#"+listItem[k][3]+"_"+listItem[k][2]+"_f").append(str);
tree_prop_general();	
tree_Prop("#"+listItem[k][3]+"_"+listItem[k][2]+"> span");
tree_Prop("#"+listItem[k][3]+"_"+listItem[k][2]+"_f > span");
$("#"+ listItem[k][0] +" > span").on('click',function () {   							
var res=$(this ).parents()
.map(function() {
return this.id;
	})
.get()
.join( "," );
parents=res.split(",,");
var selectedItem=parents[4];
var ware = selectedItem.substring(0, 4);
if(ware=="WARE"){
	if(selectedItem!=markersSite)
		{
 			console.log("==============///////////clicked"+selectedItem);
			var selMarker=""; 																							
													markerId=selectedItem;				
													selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
													selMarker.setIcon(dotmarker);
													panTo(latSitee, lngSitee);
				infowindow.setContent(selMarker.data);
				infowindow.open(map,selMarker);																						
													if(markersSite!="")
								{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(icon); 																								
							} 																						
							map.setZoom(15);
						}
					}
				});
}  		 }          	 
         }
 },
 error: function(result) {
 alert("Error");
     }
 });
}
});
		}	
 }
 }                                             
 },
 error: function(result) {
     alert("Error");
					         }
					     });
	}
					}   				 
				});                       
	}
///////////////////////////////////////////////
/* End of Site PO Item Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of PO Site Item Tree Method */ 
//////////////////////////////////////////////
var PoSelectedTemp="";	
function CreateTree_PoStItem(listPO,map){
//Site_Boq("");
PO_Boq("");
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> PO </span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n = 0; n < listPO.length; n++) {	
str="<li id='"+listPO[n][0]+"' class='PO' style='display:none;width:100px;'><span onclick='PoStItemCore("+listPO[n][0]+")' ><i class='fa fa-file-invoice-dollar fa-2x'></i> "+listPO[n][0]+"</span>";
str+= "<ul><li id='" +listPO[n][0]+"_f' class='SiteFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);
} 
// Add fragment to a list: 
document.getElementById('initial_ul').appendChild(dFrag);		
}

function PoStItemCore(n)
{ 
var POSCreated=[];
var selectedItem=n.id;
tree_prop_general();
tree_Prop("#"+selectedItem+ "> span");
tree_Prop("#"+selectedItem+ "_f > span");
var tempClusterArray=[];
$("#"+selectedItem+" > span").on('click',
 function (e) {                           
var selectedPo=$(this).parent().attr('id');
PO_Boq(selectedPo);
Create_TreeParent(selectedPo,"PO");			 				     						
 $.ajax({
type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/findPOSt_Items',
 data: {
   "selectedItem":selectedPo,
"POAlreadyCreated":"false"
},
dataType: "json",
success: function (data) {		        	
if (data != null) {
var POChildrenLength=$("#" +selectedPo+"_f").find(' > ul > li').length;	
var listSites=data.listSites;
var dFrag = document.createDocumentFragment(); 					
  for (n = 0; n < listSites.length; n++) {	
  str="<li id='"+listSites[n][2]+"_"+listSites[n][3]+"' class='Site parent_li'style='display:none'><span onclick='PoStItemCore2("+listSites[n][2]+"_"+listSites[n][3]+")' ><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSites[n][1]+"</span>";
  str+= "<ul><li id='" +listSites[n][2]+"_"+listSites[n][3]+"_f' class='ItemFolder parent_li' style='display:none' ><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Item </span></li></ul></li></ul>";						   					

const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);                                                      
        }
document.getElementById(selectedItem+"_f").appendChild(dFrag);		 
 }
 },
 error: function(result) {
     alert("Error");
	         }
	     });                 
});       
   } 

function PoStItemCore2(n)
{
	
var markersSite="";
var selectedId=n.id;
tree_prop_general();
tree_Prop("#"+selectedId+ "> span");
tree_Prop("#"+selectedId+ "_f > span");                     
$("#"+selectedId+ "> span").on('click',function () {
var res=$(this ).parents()
		.map(function() {
		return this.id;
		})
		.get()
		.join( "," );
parents=res.split(",,");

var selectedPO=parents[2];
parents2=parents[0].split("_"+selectedPO);
var selectedItem=parents2[0];
//Site_Boq(selectedItem);
if(selectedItem!=markersSite)
{
var selMarker="";			
markerId=selectedItem;
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
selMarker.setIcon(dotmarker);
position=selMarker.getPosition();
site_wave(map,position);
panTo(latSitee, lngSitee);			
window.infowindow.setContent(selMarker.data);
window.infowindow.open(map,selMarker);
const pos = new google.maps.LatLng(latSitee,lngSitee);			
if(markersSite!="")
{
var otherMarkers=markers[markersSite];			
otherMarkers.setIcon(dotmarker);			
}		
map.setZoom(15);

if(!sitesPNtCreated.includes(selectedItem))
{
	console.log(sitesPNtCreated);
	sitesPNtCreated.push(selectedItem);
$.ajax({
type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/findPOSt_Items',
data: {
"selectedItem":selectedPO,
"POAlreadyCreated":"True",
"selectedSite":selectedItem
	 },
dataType: "json",
success: function (data) {					        	

if (data != null) {
var listItem=data.itemList;
console.log(listItem);
var siteChildren=$("#"+selectedItem+ "_"+ selectedPO+ "_f") .find(' > ul > li').length;
if(siteChildren<listItem.length){
for (k = 0; k <listItem.length; k++) {
var tr="<ul><li class='Items' id='" + listItem[k][0] + "' style='display:none'><span class='tree-span' ><i class='fa fa-bahai fa-2x'></i>"+listItem[k][1]+"</span></li></ul>";
$("#"+listItem[k][3]+"_"+listItem[k][2]+"_f").append(tr);
console.log("Done");
tree_prop_general();	
tree_Prop("#"+ listItem[k][0] +"> span");
tree_Prop("#"+ listItem[k][0] +"_f > span");	 			            
$("#"+ listItem[k][0] +" > span").on('click',function () { 																			
var res=$(this ).parents()
.map(function() {
return this.id;
	})
.get()
.join( "," );
parents=res.split(",,");
var selectedtemp=parents[2].split("_");
var selectedItem=selectedtemp[0];
//Site_Boq(selectedItem);
if(selectedItem!=markersSite)
{
var selMarker="";
markerId=selectedItem;				
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();	
position=selMarker.getPosition();
site_wave(map,position);				
selMarker.setIcon(dotmarker);
panTo(latSitee,lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);																							
if(markersSite!="")
{
var otherMarkers=markers[markersSite];			
otherMarkers.setIcon(dotmarker);										
	}
markersSite="";	
markersSite=selectedItem;																				
map.setZoom(15);
}   
});
}			
}
  }

},
error: function(result) {
alert("Error");
	 }
 });
}    
}
});
}
///////////////////////////////////////////////
/* End of PO Site Item Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of PO Item Site Tree Method */ 
//////////////////////////////////////////////
function CreateTree_PoItemSt(listPO,map){
	PO_Boq("");
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> PO </span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n = 0; n < listPO.length; n++) {	
	
	str="<li id='"+listPO[n][0]+"' class='Site' style='display:none;width:100px;'><span onclick='PoItemStCore("+listPO[n][0]+")' ><i class='fa fa-file-invoice-dollar fa-2x'></i> "+listPO[n][0]+"</span>";
	str+= "<ul><li id='" +listPO[n][0]+"_f' class='SiteFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Item </span></li></ul></li></ul>";	
PoStItemCore(n); 
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);
}
document.getElementById('initial_ul').appendChild(dFrag);
}
function St(k){
var selectedId=k.id;
tree_prop_general();
tree_Prop("#"+selectedId+ "> span");
tree_Prop("#"+selectedId+ "_f > span");      
   $("#"+ selectedId +" > span").on('click',function () { 	
		var res=$(this ).parents()
				.map(function() {
				return this.id;
					})
				.get()
				.join( "," );
				parents=selectedId;
				var selectedtemp=parents[2].split("_");
				var selectedItem=selectedtemp[0];  
	   
 //var selectedItem=$(this).parent().attr('id'); 
 Site_Boq(selectedItem); 

 if(selectedItem!=markersSite)
 {
 console.log("==============///////////clicked"+selectedItem);	
 var selMarker="";
 markerId=selectedItem;				
 selMarker=markers[markerId];
 var latSitee = selMarker.getPosition().lat();
 var lngSitee = selMarker.getPosition().lng();					
 selMarker.setIcon(dotmarker);
 position=selMarker.getPosition();
			site_wave(map,position);
 panTo(latSitee,lngSitee);
 infowindow.setContent(selMarker.data);
 infowindow.open(map,selMarker);
																						
		if(markersSite!="")
{
var otherMarkers=markers[markersSite];			
otherMarkers.setIcon(dotmarker);										
				}
markersSite="";	
	 	markersSite=selectedItem;																				
	 	map.setZoom(15);
				}   
	 		}); 
    }    
function PoItemStCore(n)
{
 var selectedId=n.id;
 PO_Boq(selectedId);
tree_Prop("#"+selectedId+ "> span");
tree_Prop("#"+selectedId+ "_f > span");
var tempClusterArray=[];
$("#"+selectedId+" > span").on('click',
  function (e) {                           
var selectedPo=$(this).parent().attr('id');
Create_TreeParent(selectedPo,"PO");				       					 						
  $.ajax({
 				type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/findPOItems_sites',
  data: {
    "selectedPo":selectedPo,
"POAlreadyCreated":"false"
 },
 dataType: "json",
 success: function (data) {	        	
if (data != null) {    
       var POChildrenLength=$("#" +selectedPo+"_f").find(' > ul > li').length;	
   var listItem=data.listItem;	 		                                          
        if(POChildrenLength<listItem.length+1){						
   for (n = POChildrenLength; n < listItem.length; n++) {	        
var str = "<ul><li class='Item' id='"+listItem[n][0]+"_"+listItem[n][2]+"' style='display:none'><span class='folder'> </span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-bahai fa-2x'></i>"+listItem[n][1]+"</span>";
 str+= "<ul><li id='" +listItem[n][0]+"_"+listItem[n][2]+"_f' class='ItemFolder' ><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
 $("#"+listItem[n][2]+"_f").append(str); 	
 tree_Prop("#"+listItem[n][0]+"_"+listItem[n][2]+"> span");
 tree_Prop("#"+listItem[n][0]+"_"+listItem[n][2]+"_f > span");            
 var markersSite="";                           
  $("#"+listItem[n][0]+"_"+listItem[n][2]+" > span").on('click',
     function (e) {	 
  
 var res=$(this ).parents()
				.map(function() {
				return this.id;
				})
				.get()
				.join( "," );
 parents=res.split(",,");
 var selectedPO=parents[2];
 parents=res.split("_");
     var selectedItem=parents[0];
     Site_Boq(selectedItem);
                  
   $.ajax({
 				type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/findPOItems_sites',
			data: {     
  
             "selectedPo":selectedPo,
"POAlreadyCreated":"True",
"selectedItem":selectedItem
				 },
 dataType: "json",
success: function (data) {
if (data != null) {
var listSites=data.listSites;
 var ItemChildren=$("#"+selectedItem+ "_"+ selectedPO+ "_f") .find(' > ul > li').length;
   if(ItemChildren<listSites.length){
	   var dFrag = document.createDocumentFragment();
	for (k = 0; k <listSites.length; k++) {
var str="<ul><li class='Sites' id='" + listSites[k][2] +"_"+ listSites[k][3] + "' ><span class='tree-span' onclick='St("+ listSites[k][2] +"_"+ listSites[k][3] +")'><i class='fa fa-crosshairs fa-2x'></i>"+listSites[k][1]+"</span></li></ul>";
$("#"+selectedItem+"_"+selectedPo+"_f").append(str); 
//document.getElementById(selectedItem+"_"+selectedPo+"_f").appendChild(str);
				 
}	                  
       }
       }
	 },
 error: function(result) {
  alert("Error");
 						 }
 						 });	 																
 						});                                                       
                 }
        }
}
 },
 error: function(result) {
     alert("Error");
	         }
	     });
                        
});              
    }
///////////////////////////////////////////////
/* End of PO Site Item Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of PO Item Site NDTYP ND Tree Method */ 
//////////////////////////////////////////////

function CreateTree_PoItemStNdTpNd(listPO,map){
	PO_Boq("");
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> PO </span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n = 0; n < listPO.length; n++) {	          
var str = "<ul><li class='PO' id='" +listPO[n][0]+ "' style='display:none'><span class='folder' onclick='PoItemStCore("+listPO[n][0]+")' > <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-file-invoice-dollar fa-2x'></i>"+listPO[n][0]+"</span>";
str+= "<ul><li id='"+listPO[n][0]+"_f' class='SiteFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
$("#initial_ul").append(str);
PoStItemCore(n); 
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);
}
document.getElementById('initial_ul').appendChild(dFrag);
}


function PoItemStCore1(n)
{
 var selectedId=n.id;
 PO_Boq(selectedId);
tree_Prop("#"+selectedId+ "> span");
tree_Prop("#"+selectedId+ "_f > span");
var tempClusterArray=[];
$("#"+selectedId+" > span").on('click',
  function (e) {                           
var selectedPo=$(this).parent().attr('id');
Create_TreeParent(selectedPo,"PO");				       					 						
  $.ajax({
 				type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/findPOItems_sites',
  data: {
    "selectedPo":selectedPo,
"POAlreadyCreated":"false"
 },
 dataType: "json",
 success: function (data) {	        	
if (data != null) {    
       var POChildrenLength=$("#" +selectedPo+"_f").find(' > ul > li').length;	
   var listItem=data.listItem;	 	
	                                          
        if(POChildrenLength<listItem.length+1){						
   for (n = POChildrenLength; n < listItem.length; n++) {	        
var str = "<ul><li class='Item' id='"+listItem[n][0]+"_"+listItem[n][2]+"' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-bahai fa-2x'></i>"+listItem[n][1]+"</span>";
 str+= "<ul><li id='" +listItem[n][0]+"_"+listItem[n][2]+"_f' class='SiteFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
 $("#"+listItem[n][2]+"_f").append(str); 	
tree_Prop("#"+listItem[n][0]+"_"+listItem[n][2]+"> span");
tree_Prop("#"+listItem[n][0]+"_"+listItem[n][2]+"_f > span");                
 var markersSite="";                           
  $("#"+listItem[n][0]+"_"+listItem[n][2]+" > span").on('click',
     function (e) {	 
 var res=$(this ).parents()
				.map(function() {
				return this.id;
				})
				.get()
				.join( "," );
 parents=res.split(",,");
 var selectedPO=parents[2];
 parents=res.split("_");
     var selectedItem=parents[0];
     Site_Boq(selectedItem);
	                  
   $.ajax({
 				type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/findPOItems_sites',
			data: {     
  
             "selectedPo":selectedPo,
"POAlreadyCreated":"True",
"selectedItem":selectedItem
				 },
 dataType: "json",
success: function (data) {
if (data != null) {
var listSites=data.listSites;
 var ItemChildren=$("#"+selectedItem+ "_"+ selectedPO+ "_f") .find(' > ul > li').length;
	
   if(ItemChildren<listSites.length){
	   var dFrag = document.createDocumentFragment();
	for (k = 0; k <listSites.length; k++) {
var str="<ul><li class='Sites' id='" + listSites[k][2] +"_"+ listSites[k][3] + "' style='display:none'><span class='tree-span' onclick='StNdTypNd("+ listSites[k][2] +"_"+ listSites[k][3] +")'><i class='fa fa-crosshairs fa-2x'></i>"+listSites[k][1]+"</span></li></ul>";
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);
 				 
}
document.getElementById(selectedItem+"_"+selectedPo+"_f").appendChild(dFrag);	                  
       }
       }
	 },
 error: function(result) {
  alert("Error");
 						 }
 						 });	 																
 						});                                                       
                 }
        }
}
 },
 error: function(result) {
     alert("Error");
	         }
	     });
                        
});              
    }

var PoSelectedTemp="";
function StNdTypNd(listSites,selectedPo,Itemselected,k)
    {
  					tree_prop_general();	
	 				tree_Prop("#"+ listSites[k][2] +"_"+listSites[k][3] +"> span");
tree_Prop("#"+ listSites[k][2] +"_"+listSites[k][3] +"_f > span");	 			      
      
   $("#"+ listSites[k][2] +"_"+listSites[k][3] +" > span").on('click',function () { 																		
var res=$(this ).parents()
		.map(function() {
		return this.id;
		})
		.get()
		.join( "," );
 parents=res.split("_");
selectedItem=parents[0]; 
  Site_Boq(selectedItem);
 if(selectedItem!=markersSite)
 {
 var selMarker="";
 markerId=selectedItem;				
 selMarker=markers[markerId];
 var latSitee = selMarker.getPosition().lat();
 var lngSitee = selMarker.getPosition().lng();	
 position=selMarker.getPosition();
			site_wave(map,position);				
 selMarker.setIcon(dotmarker);
 panTo(latSitee,lngSitee);
 infowindow.setContent(selMarker.data);
 infowindow.open(map,selMarker);																							
		if(markersSite!="")
{
var otherMarkers=markers[markersSite];			
otherMarkers.setIcon(dotmarker);										
				}
markersSite="";	
 	markersSite=selectedItem;																				
 	map.setZoom(15);
			} 
$.ajax({
						  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/findPOItems_sites',
  data: {
                "selectedPo":selectedPo,
  "POAlreadyCreated":"True",
  "selectedItem":Itemselected,
"SelectedSite":selectedItem
	 },
dataType: "json",
    							 success: function (data) {
    							if (data != null) {					            		
							 var listNodesType=data.listNodesType;
    						for(j=0;j<listNodesType.length;j++)	
												{                                          
 var str= "<ul><li class='NodeType' id='" + listNodesType[j][0] +"_"+listNodesType[j][1]+"' style='display:none' class='folder'>";		str+="<span class='folder'> <i class='fa fa-cogs' style='color: #08526D'></i></span>";
str+= "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodesType[j][0]+"</span></li></ul>";
$("#"+selectedItem+"_"+Itemselected+"_f").append(str);
str="<ul><li id='" +listNodesType[j][0] +"_"+listNodesType[j][1]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Node </span></li></ul>";
$("#" + listNodesType[j][0] +"_"+listNodesType[j][1]).append(str);                                                    
tree_prop_general();	
tree_Prop("#" + listNodesType[j][0] +"_"+listNodesType[j][1]+"> span");
tree_Prop("#" + listNodesType[j][0] +"_"+listNodesType[j][1]+"_f > span");	

$("#" + listNodesType[j][0] +"_"+listNodesType[j][1]+" > span").on('click',function () {
var res=$(this ).parents()
			  				.map(function() {
				    			return this.id;
				  								})
				  						.get()
				  						.join( "_" );

parents=res.split("_");
var selectedNodetType=parents[0];													
console.log("selectedNodetType"+selectedNodetType);	
selectedItem=parents[1];
Site_Boq(selectedItem);
var ware = selectedItem.substring(0, 4);				
		if(selectedItem!=markersSite)
			{
				console.log("==============///////////clicked"+selectedItem);
var selMarker="";	
													markerId=selectedItem;				
													selMarker=markers[markerId];
													var latSitee = selMarker.getPosition().lat();
													var lngSitee = selMarker.getPosition().lng();	
																	
													selMarker.setIcon(dotmarker);
													position=selMarker.getPosition();
site_wave(map,position);
													panTo(latSitee, lngSitee);
													infowindow.setContent(selMarker.data);
													infowindow.open(map,selMarker);									
													if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);										
		}
markersSite="";	
											markersSite=selectedItem;
											map.setZoom(15);
											}
 $.ajax({
 			type: "GET",
  contentType: "application/json; charset=utf-8",
 url:getContext()+'/resources/js/Network/NetworkTree.js/findPOItems_sites',
  data: {
                "selectedPo":selectedPo,
  "POAlreadyCreated":"True",
  "selectedItem":Itemselected,
"SelectedSite":selectedItem,
"selectedNodetType":selectedNodetType														                
 },
 dataType: "json",
		  success: function (data) {														        	
												             if (data != null) {
																var listNodes=data.listNodes;
var NdTypeChildrenLength=$("#" + selectedNodetType+"_"+selectedItem+"_f").find(' > ul > li').length;						
				if(NdTypeChildrenLength==0){
for(j=0;j<listNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
																{															     	
var str= "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";																										        		
str+="<span class='folder'> <i class='fa fa-server ' style='color: #08526D'></i></span>";
str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
$("ul").find("#"+selectedNodetType+"_"+selectedItem+"_f").append(str);								        																			        																								   	
tree_prop_general();	
	tree_Prop("#"+ listNodes[j][0] +"> span");																										
$("#"+listNodes[j][0]+" > span").on('click',function () {
			var res=$(this ).parents()
			.map(function() {
    			return this.id;
  								})
  							.get()
  							.join( ",," );

			parents=res.split("_");

var selectedItem=parents[1];
Site_Boq(selectedItem);
if(selectedItem!=markersSite)
															{
var selMarker="";
								markerId=selectedItem;				
								selMarker=markers[markerId];
								var latSitee = selMarker.getPosition().lat();
								var lngSitee = selMarker.getPosition().lng();					
												selMarker.setIcon(dotmarker);
												position=selMarker.getPosition();
site_wave(map,position);
								panTo(latSitee, lngSitee);
								infowindow.setContent(selMarker.data);
								infowindow.open(map,selMarker);
								if(markersSite!="")
{
				var otherMarkers=markers[markersSite];			
				otherMarkers.setIcon(dotmarker);
																			}
				markersSite="";	
	markersSite=selectedItem;
	map.setZoom(15);
					}														
										});
		        					}
	        				}
	             	}
	         },																           				  					 
error: function(result) {
	             alert("Error");
														         }
				 });
				});		
						
						}
						 }
						},
 error: function(result) {
					             alert("Error");
							         }
     });
    });
    }

///////////////////////////////////////////////
/* End of PO Site Item NDTYP ND Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of Supp Site Node Cell Tree Method */ 
//////////////////////////////////////////////
//var SupSCreated=[];
function CreateTree_SuppStNdCell(listSupp,map){
	Site_Boq("");
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Suppliers</span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n=0; n < listSupp.length; n++) {
 var str = "<li class='Supplier' id='"+listSupp[n][0]+"' style='display:none'><span onclick='SuppStNdCellCore1("+listSupp[n][0]+")' > <i class='fa fa-copyright fa-2x'></i>"+listSupp[n][1]+"</span>";
 str+= "<ul><li id='" +listSupp[n][0]+"_f' class='SiteFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
 const div = document.createElement('ul');
 div.innerHTML = str;
 dFrag.appendChild(div);
		  		
}	
document.getElementById('initial_ul').appendChild(dFrag);	
}
function SuppStNdCellCore1(n){  
var selectedItem=n.id;
tree_prop_general();
tree_Prop("#"+selectedItem+ "> span");
tree_Prop("#"+selectedItem+ "_f > span");
$("#"+selectedItem+ "> span").on('click',function () {
var selectedSupp=$(this).parent().attr('id');
Create_TreeParent(selectedSupp,"Supp");
	if(!SupSCreated.includes(selectedSupp))
	{
SupSCreated.push(selectedSupp);
	$.ajax({
	  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppSiteNodeCell',
  data: {
                "selectedSupp":selectedSupp,							                
 },
 dataType: "json",
 success: function (data) {							        	
   if (data != null) {
  var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
var listSuppSites=data.listSuppSites;
if(SuppChildrenLength<listSuppSites.length){
var dFrag = document.createDocumentFragment();
for (n = 0; n < listSuppSites.length; n++) {
var str = "<li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' style='display:none'><span onclick='Create_TreeSites("+listSuppSites[n][1]+"_"+listSuppSites[n][4]+")' ><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
// var str = "<li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' ><span class='folder' onclick='Create_TreeSites("+listSuppSites[n][1]+"_"+listSuppSites[n][4]+")'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";						   						    	
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);   
   }  
 	}
document.getElementById(selectedItem+"_f").appendChild(dFrag);	
 }
},
 error: function(result) {
     alert("Error");
	         }
	     });			
}
}); 
}
///////////////////////////////////////////////
/* End of Supp Site Node Cell Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of Supp Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
//var SupSCreated=[];
function CreateTree_SuppStNdTypNdCell(listSupp,map){
	Site_Boq("");
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Suppliers</span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n=0; n < listSupp.length; n++) {
 var str = "<li class='Supplier' id='"+listSupp[n][0]+"' style='display:none'><span onclick='SuppStNdTypNdCellCore("+listSupp[n][0]+")' > <i class='fa fa-copyright fa-2x'></i>"+listSupp[n][1]+"</span>";
 str+= "<ul><li id='" +listSupp[n][0]+"_f' class='SiteFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
 const div = document.createElement('ul');
 div.innerHTML = str;
 dFrag.appendChild(div);		  		
}	
document.getElementById('initial_ul').appendChild(dFrag);	
}

function SuppStNdTypNdCellCore(n){ 
	dotmarker = {
		    path: google.maps.SymbolPath.CIRCLE,
        fillOpacity: 0.9,
        strokeColor: 'transparent',
        strokeOpacity: 0.9,
        strokeWeight: 1,
        scale: 7
		};
	var selectedItem=n.id;
	tree_Prop("#"+selectedItem+ "> span");
tree_Prop("#"+selectedItem+ "_f > span");
$("#"+selectedItem+ "> span").on('click',function () {
var selectedSupp=$(this).parent().attr('id');
Create_TreeParent(selectedSupp,"Supp");
	if(!SupSCreated.includes(selectedSupp))
	{
SupSCreated.push(selectedSupp);
	$.ajax({
	  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppSiteNodeCell',
  data: {
                "selectedSupp":selectedSupp,							                
 },
 dataType: "json",
 success: function (data) {							        	
   if (data != null) {
  var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
var listSuppSites=data.listSuppSites;
if(SuppChildrenLength<listSuppSites.length){
var dFrag = document.createDocumentFragment();
for (n = 0; n < listSuppSites.length; n++) {
var str = "<li class='Site parent_li' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' style='display:none'><span  onclick='StNdTpNdCellCore("+listSuppSites[n][1]+"_"+listSuppSites[n][4]+")' ><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeTypeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> NodeType </span></li></ul></li></ul>";						   						    	
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);   
   }  
 	}
document.getElementById(selectedItem+"_f").appendChild(dFrag);	
 }
},
 error: function(result) {
     alert("Error");
	         }
	     });			
}
}); 
}

function StNdTpNdCellCore(n)
{
//var sitesNtCreated=[];
//var NdNCreated=[];
var selectedId=n.id;
tree_prop_general();
tree_Prop("#"+selectedId+ "> span");
tree_Prop("#"+selectedId+ "_f > span");                     
$("#"+selectedId+ "> span").on('click',function () {

	var res=$(this ).parents()
			.map(function() {
				return this.id;
								})
						.get()
						.join( "," );	
	parents=res.split(",,");
	var selectedSupp=parents[2];
	parents2=parents[0].split("_"+selectedSupp);
	var selectedItem=parents2[0];
Site_Boq(selectedItem);
console.log("markersSite"+markersSite);
if(selectedItem!=markersSite)
	{
	var selMarker="";			
	markerId=selectedItem;
	selMarker=markers[markerId];
	var latSitee = selMarker.getPosition().lat();
	var lngSitee = selMarker.getPosition().lng();					
	selMarker.setIcon(dotmarker);
	position=selMarker.getPosition();
site_wave(map,position);
	panTo(latSitee, lngSitee);			
	window.infowindow.setContent(selMarker.data);
	window.infowindow.open(map,selMarker);
	const pos = new google.maps.LatLng(latSitee,lngSitee);			
	if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);			
	}		
map.setZoom(15);
if(!sitesNtCreated.includes(selectedItem))
	{
		sitesNtCreated.push(selectedItem);
	 $.ajax({
		  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppStNdTypNdCell',
  data: {
                "selectedItem":selectedItem,
"selectedSupp":selectedSupp							                
		 },
 dataType: "json",
	         success: function (data) {					        	
	             if (data != null) {					            		
	            	var listNodesType=data.listNodesType;
	            	for(j=0;j<listNodesType.length;j++)	
						{												
var str= "<ul><li class='NodeType' id='" + listNodesType[j][0] +"_"+listNodesType[j][1]+"' style='display:none' class='folder'>";								  															
str+="<span class='folder'> </span>";
str+= "<span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs'></i>"+listNodesType[j][0]+"</span></li></ul>";
$("#"+selectedItem+"_"+selectedSupp+"_f").append(str);
str="<ul><li id='" +listNodesType[j][0] +"_"+listNodesType[j][1]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Node </span></li></ul>";
$("#" + listNodesType[j][0] +"_"+listNodesType[j][1]).append(str);								
					tree_prop_general();	
tree_Prop("#" + listNodesType[j][0] +"_"+listNodesType[j][1]+"> span");
tree_Prop("#" + listNodesType[j][0] +"_"+listNodesType[j][1]+"_f > span");
$("#" + listNodesType[j][0] +"_"+listNodesType[j][1]+" > span").on('click',function () {																														
  var res=$(this).parents()
			.map(function() {
			return this.id;
				})
			.get()
		.join( "_" );
parents=res.split("_");
var selectedNodetType=parents[0];
NodeT_Boq(selectedItem,selectedNodetType);
			if(selectedItem!=markersSite)
				{
var selMarker="";	
markerId=selectedItem;				
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
selMarker.setIcon(dotmarker);
position=selMarker.getPosition();
site_wave(map,position);
panTo(latSitee, lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);									
if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);										
		}
markersSite="";	
		markersSite=selectedItem;
		map.setZoom(15);
		}
if(!NdNCreated.includes(selectedNodetType))
{
	NdNCreated.push(selectedNodetType);		
 $.ajax({
	  type: "GET",
  contentType: "application/json; charset=utf-8",
url:getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppStNdTypNdCell',
  data: {
                "selectedItem":selectedItem,
"selectedNodetType":selectedNodetType,
"selectedSupp":selectedSupp														                
 },
 dataType: "json",
	success: function (data) {														        	
if (data != null) {
		var listNodes=data.listNodes;
var NdTypeChildrenLength=$("#" + selectedNodetType+"_"+selectedItem+"_f").find(' > ul > li').length;																	
			if(NdTypeChildrenLength==0){
 Create_TreeNode_Cell(listNodes,"FindOnClick_SuppStNdTypNdCell",NdTypeChildrenLength,true,true,4,"Sup",6,"Sup",selectedItem);					            												    
				}
     	}
 },
 error: function(result) {
     alert("Error");
					         }
					     });
				}
	});
		}
}	             
 },
 error: function(result) {
     alert("Error");
			         }
			     });
		}    
			
    }
});
}
///////////////////////////////////////////////
/* End of Supp Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* End of Supp NodeType Site  Node Cell Tree Method */ 
//////////////////////////////////////////////
function CreateTree_SupNdTpStNdCell(listSupp,map){
	Site_Boq("");
var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Suppliers</span></li></ul>";
$("#network_tree").append(str);
var dFrag = document.createDocumentFragment();
for (n=0; n < listSupp.length; n++) {
 var str = "<li class='Supplier' id='"+listSupp[n][0]+"' style='display:none'><span  onclick='SuppNdTypStNdCellCore("+listSupp[n][0]+")' > <i class='fa fa-copyright fa-2x'></i>"+listSupp[n][1]+"</span>";
 str+= "<ul><li id='" +listSupp[n][0]+"_f' class='NodeTypeFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> NodeType </span></li></ul></li></ul>";
 const div = document.createElement('ul');
 div.innerHTML = str;
 dFrag.appendChild(div);		  		
}	
document.getElementById('initial_ul').appendChild(dFrag);	
}
//var SupNtCreated=[];
//var NtSiteCreated=[];
function SuppNdTypStNdCellCore(n){
	var selectedItem=n.id;
	tree_Prop("#"+selectedItem+ "> span");
tree_Prop("#"+selectedItem+ "_f > span");
$("#"+selectedItem+ "> span").on('click',function () {
var selectedSupp=$(this).parent().attr('id');
Create_TreeParent(selectedSupp,"Supp");
if(!SupNtCreated.includes(selectedSupp))
{
SupNtCreated.push(selectedSupp);
$.ajax({
type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppNdTSiteNodeCell',
data: {
        "selectedSupp":selectedSupp,							                
},
dataType: "json",
success: function (data) {							        	
if (data != null) {
var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
var listNodeType=data.listNodeType;
/////////////////
if(SuppChildrenLength<listNodeType.length){						            	
for (n = 0; n<listNodeType.length; n++) {							            								   		 
var str = "<ul><li class='NodeType' id='"+listNodeType[n][0]+"_"+listNodeType[n][1]+"' style='display:none'><span class='folder'></span><span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs fa-2x'></i>"+listNodeType[n][0]+"</span>";
str+= "<ul><li id='" +listNodeType[n][0]+"_"+listNodeType[n][1]+"_f' class='NodeFolder parent_li' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
$("#"+selectedSupp+"_f").append(str);
tree_prop_general();	
tree_Prop("#"+listNodeType[n][0]+"_"+listNodeType[n][1]+"> span");
tree_Prop("#"+listNodeType[n][0]+"_"+listNodeType[n][1]+"_f > span");				                                                   	           
$("#"+listNodeType[n][0]+"_"+listNodeType[n][1]+ "> span").on('click',function () {
var res=$(this).parents()
.map(function() {
	return this.id;
	})
.get()
.join( "_" );
parents=res.split("_");
var SelectedNodeType=parents[0]; 
if(!NtSiteCreated.includes(SelectedNodeType))
					{
					NtSiteCreated.push(SelectedNodeType);
				 $.ajax({
				  type: "GET",
contentType: "application/json; charset=utf-8",
url: getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppNdTSiteNodeCell',
data: {
        "selectedSupp":selectedSupp,
        "SelectedNodeType":SelectedNodeType
},
dataType: "json",
success: function (data) {	    
if (data != null) {
var NdTypeChildrenLength=$("#" +SelectedNodeType+"_" +selectedSupp+"_f").find(' > ul > li').length;							            	
var listSuppSites=data.listSuppSites;
if(NdTypeChildrenLength<listSuppSites.length){
	var dFrag = document.createDocumentFragment();
for (n = 0; n < listSuppSites.length; n++) {							            								   		 
str="<li id='"+listSuppSites[n][1]+"_"+listSuppSites[n][5]+"' class='Site' style='width:100px;display:none'><span class='folder' onclick='Create_Sites1("+listSuppSites[n][1]+"_"+listSuppSites[n][5]+")' ><img src='"+getContext()+"/resources/NetworkImages/site.png'> "+listSuppSites[n][0]+"</span>";
str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][5]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
//$("#"+SelectedNodeType+"_" +selectedSupp+"_f").append(str);
const div = document.createElement('ul');
div.innerHTML = str;
dFrag.appendChild(div);										  
  		            }
document.getElementById(SelectedNodeType+"_" +selectedSupp+"_f").appendChild(dFrag);						            	
 			}
 			}
},
 error: function(result) {
     alert("Error");
			         }
			     });			
			}
}); 
     }               
  }                                              
             }
         	},
	         error: function(result) {
	             alert("Error");
         }
     });			
}
	}); 
}

function Create_Sites1(n){
//var sitesNCreated=[];
tree_prop_general();
/////////////////

var selectedId=n.id;
//console.log(selectedSupp);
tree_prop_general();
tree_Prop("#"+selectedId+ "> span");
tree_Prop("#"+selectedId+ "_f > span");                     
$("#"+selectedId+ "> span").on('click',function () {

	var res=$(this ).parents()
			.map(function() {
				return this.id;
								})
						.get()
						.join( "," );	
	parents=res.split(",,");
	var selectedSupp=parents[4];
	parents2=selectedId.split("_");
	var SelectedNodeType=parents2[3];
	parents2=parents[0].split("_"+SelectedNodeType);
	var selectedItem=parents2[0];

Site_Boq(selectedItem);		
if(selectedItem!=markersSite)
				{
	 			var selMarker="";					
		markerId=selectedItem;
		selMarker=markers[markerId];
		var latSitee = selMarker.getPosition().lat();
		var lngSitee = selMarker.getPosition().lng();					
		selMarker.setIcon(dotmarker);
		position=selMarker.getPosition();
site_wave(map,position);
		panTo(latSitee, lngSitee);					
		window.infowindow.setContent(selMarker.data);
		window.infowindow.open(map,selMarker);
		const pos = new google.maps.LatLng(latSitee,lngSitee);					
		if(markersSite!="")
	{
		var otherMarkers=markers[markersSite];			
		otherMarkers.setIcon(dotmarker);					
	}
markersSite="";	
			markersSite=selectedItem;				
			map.setZoom(15);
}
if(!sitesNCreated.includes(selectedItem))
			{
				sitesNCreated.push(selectedItem);
 $.ajax({
	  type: "GET",
  contentType: "application/json; charset=utf-8",
  url: getContext()+'/resources/js/Network/NetworkTree.js/FindOnClick_SuppNdTSiteNodeCell',

  data: {
                "selectedItem":selectedItem,
				"selectedSupp":selectedSupp,
				"SelectedNodeType":SelectedNodeType               
 },
 dataType: "json",
 success: function (data) {			        	
     if (data != null) {
         var listSuppNodes=data.listSuppNodes;
         var siteChildren=$("#"+selectedItem+"_"+SelectedNodeType+"_f") .find(' > ul > li').length;
////////////////////////////////////////////
if(siteChildren<listSuppNodes.length){
Create_TreeNode_Cell(listSuppNodes,"FindOnClick_SuppNdTSiteNodeCell",siteChildren,true,true,2,"Sup",4,"Sup",selectedItem);					            												    
}
         }
     },
  error: function(result) {
     alert("Error");
			         }
			     });
						}         
	});
} 
///////////////////////////////////////////////
/* End of Supp NodeType Site NodeType Node Cell Tree Method */ 
//////////////////////////////////////////////
///////////////////////////////////////////////
/* Start of Node Cell Common Tree Method */ 
//////////////////////////////////////////////
function Create_TreeNode_Cell(listNodes,MethodN,ChildrenLength,concat,panT,NdInex1,NdInex2,CellIndex1,CellIndex2,SiteName) {
	//console.log("Create_TreeNode_Cell ");
//var CellCreated=[];
for(j=ChildrenLength;j<listNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
{																        	
var str= "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";
if(concat==true)
{
	if(listNodes[j][5]>0 || listNodes[j][6]>0 || listNodes[j][7]>0){																        				
		str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
		str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></span></li></ul>";
		$("ul").find("#"+listNodes[j][3]+"_"+listNodes[j][4]+"_f").append(str);								        																	        				
		str ="<ul><li id='" + listNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
		$("#"+listNodes[j][0]).append(str);																	        							    
	}																	        			
	else{
		str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></span></li></ul>";
		$("ul").find("#"+listNodes[j][3]+"_"+listNodes[j][4]+"_f").append(str);
	}
}
else{
	if(listNodes[j][5]>0 || listNodes[j][6]>0 || listNodes[j][7]>0){																        				
		str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
		str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></span></li></ul>";
		$("ul").find("#"+listNodes[j][4]+"_f").append(str);								        																	        				
		str ="<ul><li id='" + listNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
		$("#"+listNodes[j][0]).append(str);																	        							    
	}																	        			
	else{
		str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></span></li></ul>";
		$("ul").find("#"+listNodes[j][4]+"_f").append(str);
	}
}	
Tree_PropagationAppendedNodes(listNodes);		
$("#"+listNodes[j][0]+" > .folder").on('click',function () {	
	//console.log("Create_TreeNode_Cell clicking span node");
if(panT==true)	{																																				
if(SiteName!=markersSite)
	{
console.log("==============///////////clicked"+SiteName);
/*
var selMarker="";
markerId=SiteName;				
selMarker=markersSites[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
//selMarker.setIcon(dotmarker);
position=selMarker.getPosition();
//site_wave(map,position);
panTo(latSitee, lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);
if(markersSite!="")
	{
		var otherMarkers=markersSites[markersSite];			
		//otherMarkers.setIcon(dotmarker);																				
			}
markersSite="";	
markersSite=SiteName;		
map.setZoom(15);
*/
}																					
}					
var selectedNode=$(this).parent().attr('id');
if(SiteName!="null"){
	Node_Boq(SiteName,selectedNode);
}
if(!CellCreated.includes(selectedNode))
{
	CellCreated.push(selectedNode);
	var NdChildrenLength=$("#" + selectedNode+"_f").find(' > ul > li').length;															
	if(NdChildrenLength==0){	
$.ajax({
type: "GET",
contentType: "application/json; charset=utf-8",
 url: getContext()+'/resources/js/Network/NetworkTree.js/'+MethodN,
data: {
 "selectedNode":selectedNode,																													 },
dataType: "json",
success: function (data) {	
	//console.log("method--> "+MethodN);
	if (data != null) {
		var listCells=data.listCells;
		for (k = 0; k <listCells.length; k++) {
			var str="<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none'><span class='TreeSpan' style='width:395px'><span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></span></li></ul>";
			$("#"+listCells[k][2]+"_f").append(str);
					//Tree_PropagationAppendedCells();
			$(".Cell  > span").on('click',function () {
				//console.log("Create_TreeNode_Cell clicking span cell");
				if(panT==true)	{	
				if(SiteName!=markersSite)
					{
						console.log("==============///////////clicked"+SiteName);
						var selMarker="";
						/*
						markerId=SiteName;				
						selMarker=markersSites[markerId];
						var latSitee = selMarker.getPosition().lat();
						var lngSitee = selMarker.getPosition().lng();					
						//selMarker.setIcon(dotmarker);
						position=selMarker.getPosition();
						//site_wave(map,position);
						panTo(latSitee, lngSitee);
						infowindow.setContent(selMarker.data);
						infowindow.open(map,selMarker);
						if(markersSite!="")
						{
							var otherMarkers=markersSites[markersSite];			
							//otherMarkers.setIcon(dotmarker);																					
									}
						markersSite="";	
						markersSite=SiteName;
						map.setZoom(15);
						*/
				}
					}	
				tree_selection(selectedNode);
			});
		}
	     }
 },								         	
 error: function(result) {
     alert("Error");
	         }
	});  
}
}		
});
}
}
///////////////////////////////////////////////
/* End of Node Cell Common Tree Method */ 
//////////////////////////////////////////////
/*
 
dotmarker = {
	    path: google.maps.SymbolPath.CIRCLE,
    fillOpacity: 0.9,
    strokeColor: 'transparent',
    strokeOpacity: 0.9,
    strokeWeight: 1,
    scale: 7
	};*/

function openContext(id,name,menuName,event){
	const time = menuName.isOpen() ? 100 : 0;
	menuName.hide();
	setTimeout(() => { menuName.show(event.pageX, event.pageY) }, time);
	document.addEventListener('contextmenu', hideContext, true);
	document.addEventListener('click', hideContext, true);
	//HideContextMenuGoolge(MenuMap);
	window["menuName"]=menuName;		
	event.preventDefault();
}

function hideContext(){
	if(window["menuName"]!=""){
		window["menuName"].hide();
		document.removeEventListener('contextmenu', hideContext);
		document.removeEventListener('click', hideContext);		
	}
}


function Create_TreeNode_CellGeneral(lstNodes,lstCells,ChildrenLength,concat,SiteName) {

	for(j=ChildrenLength;j<lstNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
	{																        	
	var str= "<ul><li class='Node' id='" + lstNodes[j][0] +"' style='display:none; margin-left:-18px' class='folder'>";
	if(concat==true)
	{	
		if(lstNodes[j][5]>0 || lstNodes[j][6]>0 || lstNodes[j][7]>0){		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][3]+"_"+lstNodes[j][4]+"_f").append(str);								        																	        				
			str ="<ul><li id='" + lstNodes[j][0] +"_f' style='display:none; margin-left:-10px;'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
			$("#"+lstNodes[j][0]).append(str);
		}																	        			
		else{
			str += "<span class='TreeSpan' style='width:395px; margin-left:18px;'><span class='tree-span'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][3]+"_"+lstNodes[j][4]+"_f").append(str);
		}
	}
	else{
		if(lstNodes[j][5]>0 || lstNodes[j][6]>0 || lstNodes[j][7]>0){		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][4]+"_f").append(str);								        																	        				
			str ="<ul><li id='" + lstNodes[j][0] +"_f' style='display:none; margin-left:-10px;'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
			$("#"+lstNodes[j][0]).append(str);				
		}																	        			
		else{
			str += "<span class='TreeSpan' style='width:395px; margin-left:18px;'><span class='tree-span'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][4]+"_f").append(str);
		}
	}		

	$("#"+lstNodes[j][0]+" > .folder").on('click',function () {	
		var selectedNode=$(this).parent().attr('id');
		//Node_Boq(SiteName,selectedNode);
		var NdChildrenLength=$("#" + selectedNode+"_f").find(' > ul > li').length;															
		if(NdChildrenLength==0){		
			var lstCellsFiltered=[];
			for(var c=0;c<lstCells.length;c++){
				if(lstCells[c][2]==selectedNode){
					lstCellsFiltered.push(lstCells[c]);
				}
			}
				for (k = 0; k <lstCellsFiltered.length; k++) {
					var str="<ul><li class='Cell' id='" + lstCellsFiltered[k][0] + "' style='display:none; margin-left:-15px;''><span class='TreeSpan' style='width:395px'><span class='tree-span'>  <i class='fas fa-vector-square fa-2x'></i> "+lstCellsFiltered[k][1]+" </span></span></li></ul>";
					$("#"+lstCellsFiltered[k][2]+"_f").append(str);
				}
			tree_prop_selection("#" +selectedNode+"_f .Cell .TreeSpan");
				}	
		});	
	
	$(".Node > .TreeSpan").contextmenu(function(){				
		selectedNodeIdContext=$(this).parent().attr('id');
		menuName=singleNode;			
		openContext(selectedNodeIdContext,"",singleNode,event);
	});
	singleNode = new ContextMenu({
		  'theme': 'default',
		  'items': [
			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
					Node_Boq(SiteName,selectedNodeIdContext);
				}	
				   }
			]
	});
	}
	
	Tree_PropagationAppendedNodes(SiteName+"_f .Node");
	tree_prop_selection("#" +SiteName+"_f .Node .TreeSpan");
	//console.log("the end");	
}
