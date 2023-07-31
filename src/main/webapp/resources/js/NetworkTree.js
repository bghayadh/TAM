
// This JavaScript File includes all the functions of the tree




//////////////////////Tree propagation functions

function Create_TreeCells(){

for (k = 0; k < listCells.length; k++) {

console.log("---------cell  Inside the Node ");

str= "<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'>";
str += "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>";

$("#"+ listCells[k][2]+"_f").append(str);


$(".Cell > span").on('click',function () {

var res=$(this ).parents()
.map(function() {
return this.id;
})
.get()
.join( "," );

parents=res.split(",,");
var selectedItem=parents[6];
console.log("selectedItem"+selectedItem);
console.log("markersSite"+markersSite);
var ware = selectedItem.substring(0, 4);


if(selectedItem!=markersSite)
{
console.log("==============///////////clicked"+selectedItem);

var selMarker="";

markerId=selectedItem;				
selMarker=markers[markerId];
var latSitee = selMarker.getPosition().lat();
var lngSitee = selMarker.getPosition().lng();					
selMarker.setIcon(icon2);
panTo(latSitee, lngSitee);
infowindow.setContent(selMarker.data);
infowindow.open(map,selMarker);


if(markersSite!="")
{
var otherMarkers=markers[markersSite];			
otherMarkers.setIcon(icon);

	}

markersSite="";	
markersSite=selectedItem;

map.setZoom(15);

}
});
}
}


function tree_PropSupplier(listSupp){

$("#"+listSupp+ "> span").on('click',
function (e) {

var children = $(this).parent('li.parent_li').find(
' > ul > li');
if (children.is(":visible")) {
children.hide('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

} else {
children.show('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
}
e.stopPropagation(i);
});

}


function tree_PropInitial_li(){

$("#initial_ul > .folder").eq(n).on('click',
function (e) {

var children = $(this).parent('li.parent_li').find(' > ul > li');
if (children.is(":visible")) {
children.hide('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

} else {
children.show('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
}
e.stopPropagation();
});
}



function tree_PropSiteFolder(n){
$('.SiteFolder > span').eq(n).on('click',
function (e) {
console.log("clicked Nodes");
var children = $(this).parent('li.parent_li').find(
' > ul > li');
if (children.is(":visible")) {
children.hide('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

} else {
children.show('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
}
e.stopPropagation();
});

}



function tree_Prop(selector){

$(selector).on('click',
function (e) {

var children = $(this).parent('li.parent_li').find(
' > ul > li');
if (children.is(":visible")) {
children.hide('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

} else {
children.show('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
}
e.stopPropagation(i);
});
}




function tree_PropFolder(FolderID){

$("#"+FolderID+"_f> span").on('click',
function (e) {
console.log("clicked Nodes");
var children = $(this).parent('li.parent_li').find(
' > ul > li');
if (children.is(":visible")) {
children.hide('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

} else {
children.show('fast');
$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
}
e.stopPropagation();
});
}




///////////////////////////	End of tree Prop functions 		   

function CreateTree_SuppStNdCell(listSupp,map){

var currentSuppCount=2;
//var currentSiteCount=2;
console.log("supp length"+listSupp.length );


var str="<ul><li id='initial_ul' class='Initial'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Suppliers</span></li></ul>";
$("#network_tree").append(str);
$("#network_tree").append("<label id='loadmore' style='margin-left:35%;margin-bottom:1%;visibility:hidden;'> Scroll to load more</label>"); 



for (n=0; n < currentSuppCount; n++) {

window["currentSuppCount"+listSupp[n][0]]=2;
var str = "<ul><li class='Supplier' id='"+listSupp[n][0]+"' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSupp[n][1]+"</span>";
str+= "<ul><li id='" +listSupp[n][0]+"_f' class='SiteFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
$("#initial_ul").append(str);


/////////////////////////////////////////////////////////////



tree_Prop("#"+listSupp[n][0]+"> span");
tree_PropInitial_li();
tree_Prop("#"+listSupp[n][0]+"_f> span");
tree_prop_general();


//////////////////////////////////////////////////////////////////// 


$("#"+listSupp[n][0]+ "> span").on('click',function () {

var tempClusterArray=[];
var selectedSupp=$(this).parent().attr('id');

for(i=0,j=0;i<markersSup.length,j<circles.length;i++,j++){

	if(markersSup[i].ID==""+selectedSupp){
		
			console.log("they are same: "+selectedSupp+"|  |"+markersSupplier+"|  ");
			console.log("circles of selected sup: "+circles.length);
			
			markersSup[i].setMap(map);
			var point=markersSup[i].position;
		
			circles[i].setMap(map);
			//
			circles[i].setCenter(point);
			
	}
	
	else{

		markersSup[i].getDraggable();
		console.log("markersSup in else stat:");
		markersSup[i].setMap(null);

		circles[i].setMap(null);
		
		circles[i].setCenter(null);
		}
	
}

 	tempClusterArray=window[""+selectedSupp];
 	
 	markerClusterSup.setMap(map);
 	markerClusterSup.addMarkers(tempClusterArray);
 	
	console.log("clusterer available and not null"+markerClusterSup.getTotalClusters()+ " having this array of markers==>"+markerClusterSup.getMarkers().length);


	map.setZoom(4);

	
	
	
	

	 $.ajax({
	  type: "GET",
	  contentType: "application/json; charset=utf-8",
	  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
	  data: {
	                "selectedSupp":selectedSupp,
	                
			 },
	         dataType: "json",
	         success: function (data) {
	        	
	             if (data != null) {
	            	var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
	            	console.log(data.listSuppSites);
	            	var listSuppSites=data.listSuppSites;
	            	if(SuppChildrenLength<listSuppSites.length){
	            		console.log("passed the children test with number of children equal to "+SuppChildrenLength+" with window[currentSuppCount+selectedSupp]"+window["currentSuppCount"+selectedSupp]+" and selected supplier "+selectedSupp);
	            	for (n = SuppChildrenLength; n < window["currentSuppCount"+selectedSupp]; n++) {			
	            		
	   				 
		           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
		           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
		           		 $("#"+listSuppSites[n][4]+"_f").append(str);

						console.log("the first for loop of 2 sites");
		           			/////////////////////////////////////////////////////////////
		           		    
						  	tree_prop_general();	

						    tree_Prop("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"> span");
						    tree_Prop("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f > span");
						    
						 	//tree_PropSite(listSuppSites[n][1]+"_"+listSuppSites[n][4]);
						 	//tree_PropFolder(listSuppSites[n][1]+"_"+listSuppSites[n][4]);
						 	
				
						/////////////////////////////////////////////////////////////////
						
		           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

		           				var res=$(this ).parents()
		           				.map(function() {
		           					return this.id;
		           					})
		           				.get()
		           				.join( "_" );

		           				parents=res.split("_SUP");
		           				var selectedItem=parents[0];
		           				console.log("selectedItem"+selectedItem);
		           				console.log("markersSite"+markersSite);
		           				var ware = selectedItem.substring(0, 4);
		           				
		           				
		           					if(selectedItem!=markersSite)
		           						{
		           							console.log("==============///////////clicked"+selectedItem);
		           				
		           						var selMarker="";
		           				
		           						markerId=selectedItem;				
		           						selMarker=markers[markerId];
		           						var latSitee = selMarker.getPosition().lat();
		           						var lngSitee = selMarker.getPosition().lng();					
		           						selMarker.setIcon(icon2);
		           						
		           						panTo(latSitee, lngSitee);
		           						infowindow.setContent(selMarker.data);
		           						infowindow.open(map,selMarker);

		           						const pos = new google.maps.LatLng(latSitee,lngSitee);
		           						
		           						
		           						circle.setCenter(pos);
		           						circle2.setCenter(pos);
		           				
		           						if(markersSite!="")
		           								{
		           								var otherMarkers=markers[markersSite];			
		           								otherMarkers.setIcon(icon);
		           				
		           									}
		           				
		           						markersSite="";	
		           						markersSite=selectedItem;
		           				
		           						map.setZoom(15);
		           					}

									
			           				 $.ajax({
				   						  type: "GET",
				   						  contentType: "application/json; charset=utf-8",
				   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
				   						  data: {
				   						                "selectedItem":selectedItem,
				   						             	"selectedSupp":selectedSupp
				   						                
				   								 },
				   						         dataType: "json",
				   						         success: function (data) {
				   						        	
				   						             if (data != null) {

					   						             var listSuppNodes=data.listSuppNodes;
					   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
					   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

					   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
							   						  		{
							   						  			str = "";
							   						  			console.log("-----Node Inside the Node typee");
							   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
							   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
							   						  	
							   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
							   						  				
							   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
							   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
							   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
							   						  	
							   						  				str = "";
							   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
							   						  				$("#"+listSuppNodes[j][0]).append(str);
							   						  							    
							   						  			}
							   						  			
							   						  			else{
							   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
							   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
							   						  			}
							   						  			

														
						  									Tree_PropagationAppendedNodes(listSuppNodes);

									         

									           			
														////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


									           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
									           				



							           						var res=$(this ).parents()
							           						.map(function() {
							           						return this.id;
							           							})
							           						.get()
							           						.join( "_" );
							           						
							           						parents=res.split("__");
							           						var selected=parents[1].split("_"+parents[3]);
							           						var selectedItem=selected.slice(0, -1);
							           						console.log("parents"+parents);
							           						console.log("selectedItem"+selectedItem+"|");
							           						console.log("markersSite"+markersSite);
							           						//var ware = selectedItem.substring(0, 4);
							           						
							           						//if(ware=="WARE"){
							           						if(selectedItem!=markersSite)
							           						{
							           							
							           						console.log("passed the if >>>....."+selectedItem);
							           						
							           						var selMarker="";
							           						
							           						markerId=selectedItem;				
							           						selMarker=markers[markerId];
							           						var latSitee = selMarker.getPosition().lat();
							           						var lngSitee = selMarker.getPosition().lng();					
							           						selMarker.setIcon(icon2);
							           						panTo(latSitee, lngSitee);
							           						infowindow.setContent(selMarker.data);
							           						infowindow.open(map,selMarker);
							           						
							           						
							           						if(selMarker!=markers[markersSite] && markersSite!="" )
							           						{
							           						var otherMarkers=markers[markersSite];			
							           						otherMarkers.setIcon(icon);
							           						
							           							}
							           						
							           						markersSite="";	
							           						markersSite=selectedItem;
							           						
							           						map.setZoom(15);
							           						}
							           						//}

															var selectedNode=$(this).parent().attr('id');
															
									           				 $.ajax({
									   						  type: "GET",
									   						  contentType: "application/json; charset=utf-8",
									   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
									   						  data: {
									   						                "selectedNode":selectedNode,
									   						                
									   								 },
									   						         dataType: "json",
									   						         success: function (data) {
									   						        	
									   						             if (data != null) {

																				if(window[""+selectedNode]!="cellCreated")
																					{
																					var listCells=data.listCells;
																					console.log("inside node children test if: "+listCells);

																					Create_TreeCells();
																			
																					window[""+selectedNode]="cellCreated";

																				}

										   						             
									   						             }
									   						         },

									   						      error: function(result) {
															             alert("Error");
															         }
															     });
									   						         
							           							
							           			});

							   						  			
							   						  		}
					   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


						   						             }


					   						             
				   						             }

				   						         },

				   						      error: function(result) {
										             alert("Error");
										         }
										     });
		   						         

		           					
		           				
		           			});
           		            
	            			}

	            	var childrenSup = $("#"+selectedSupp+"_f").find(' > ul > li');
	             	$("#network_tree").on('mousewheel DOMMouseScroll', function(e){
	            		
	        	       	var network_tree = document.getElementById('network_tree');
	        	        console.log(network_tree.scrollHeight+"  "+network_tree.scrollTop+"  "+network_tree.clientHeight);
	        	  		if(window["currentSuppCount"+selectedSupp]<listSuppSites.length && network_tree.scrollHeight - network_tree.scrollTop <= network_tree.clientHeight && childrenSup.is(":visible")){    
	        	        
	        		       	if((window["currentSuppCount"+selectedSupp]+2)<=listSuppSites.length){

	        			       console.log(">>>>>>>>>>inside if >>>>>");
	        		       
	        			        	for (n=window["currentSuppCount"+selectedSupp]; n < window["currentSuppCount"+selectedSupp] + 2; n++) {

						           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
						           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
						           		 $("#"+listSuppSites[n][4]+"_f").append(str);


						           			/////////////////////////////////////////////////////////////
						           		    
										  	 tree_prop_general();	


						           		 $("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "> span").on('click',
						           			            function (e) {
						           			            
						           			                var children = $(this).parent('li.parent_li').find(
						           			                    ' > ul > li');
						           			                if (children.is(":visible")) {
						           			                    children.hide('fast');
						           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

						           			                } else {
						           			                    children.show('fast');
						           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
						           			                }
						           			                e.stopPropagation(i);
						           			            });


						           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "_f> span").on('click',
						           		            function (e) {
						           		            console.log("clicked Nodes");
						           		                var children = $(this).parent('li.parent_li').find(
						           		                    ' > ul > li');
						           		                if (children.is(":visible")) {
						           		                    children.hide('fast');
						           		                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

						           		                } else {
						           		                    children.show('fast');
						           		                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
						           		                }
						           		                e.stopPropagation();
						           		            });

						           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

						           				var res=$(this ).parents()
						           				.map(function() {
						           					return this.id;
						           					})
						           				.get()
						           				.join( "_" );

						           				parents=res.split("_SUP");
						           				var selectedItem=parents[0];
						           				console.log("selectedItem"+selectedItem);
						           				console.log("markersSite"+markersSite);
						           				var ware = selectedItem.substring(0, 4);
						           				
						           				
						           					if(selectedItem!=markersSite)
						           						{
						           							console.log("==============///////////clicked"+selectedItem);
						           				
						           						var selMarker="";
						           				
						           						markerId=selectedItem;				
						           						selMarker=markers[markerId];
						           						var latSitee = selMarker.getPosition().lat();
						           						var lngSitee = selMarker.getPosition().lng();					
						           						selMarker.setIcon(icon2);
						           						
						           						panTo(latSitee, lngSitee);
						           						infowindow.setContent(selMarker.data);
						           						infowindow.open(map,selMarker);

						           						const pos = new google.maps.LatLng(latSitee,lngSitee);
						           						
						           						
						           						circle.setCenter(pos);
						           						circle2.setCenter(pos);
						           				
						           						if(markersSite!="")
						           								{
						           								var otherMarkers=markers[markersSite];			
						           								otherMarkers.setIcon(icon);
						           				
						           									}
						           				
						           						markersSite="";	
						           						markersSite=selectedItem;
						           				
						           						map.setZoom(15);
						           					}

													
							           				 $.ajax({
								   						  type: "GET",
								   						  contentType: "application/json; charset=utf-8",
								   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
								   						  data: {
								   						                "selectedItem":selectedItem,
								   						             	"selectedSupp":selectedSupp
								   						                
								   								 },
								   						         dataType: "json",
								   						         success: function (data) {
								   						        	
								   						             if (data != null) {

									   						             var listSuppNodes=data.listSuppNodes;
									   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
									   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

									   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
											   						  		{
											   						  			str = "";
											   						  			console.log("-----Node Inside the Node typee");
											   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
											   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
											   						  	
											   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
											   						  				
											   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
											   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
											   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
											   						  	
											   						  				str = "";
											   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
											   						  				$("#"+listSuppNodes[j][0]).append(str);
											   						  							    
											   						  			}
											   						  			
											   						  			else{
											   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
											   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
											   						  			}
											   						  			

																		
			   						  									//tree_prop_general();	
			   						  									Tree_PropagationAppendedNodes(listSuppNodes);

													         

													           			
																		////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


													           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
													           				



											           						var res=$(this ).parents()
											           						.map(function() {
											           						return this.id;
											           							})
											           						.get()
											           						.join( "_" );
											           						
											           						parents=res.split("__");
											           						var selected=parents[1].split("_"+parents[3]);
											           						var selectedItem=selected.slice(0, -1);
											           						console.log("parents"+parents);
											           						console.log("selectedItem"+selectedItem+"|");
											           						console.log("markersSite"+markersSite);
											           						//var ware = selectedItem.substring(0, 4);
											           						
											           						//if(ware=="WARE"){
											           						if(selectedItem!=markersSite)
											           						{
											           							
											           						console.log("passed the if >>>....."+selectedItem);
											           						
											           						var selMarker="";
											           						
											           						markerId=selectedItem;				
											           						selMarker=markers[markerId];
											           						var latSitee = selMarker.getPosition().lat();
											           						var lngSitee = selMarker.getPosition().lng();					
											           						selMarker.setIcon(icon2);
											           						panTo(latSitee, lngSitee);
											           						infowindow.setContent(selMarker.data);
											           						infowindow.open(map,selMarker);
											           						
											           						
											           						if(selMarker!=markers[markersSite] && markersSite!="" )
											           						{
											           						var otherMarkers=markers[markersSite];			
											           						otherMarkers.setIcon(icon);
											           						
											           							}
											           						
											           						markersSite="";	
											           						markersSite=selectedItem;
											           						
											           						map.setZoom(15);
											           						}
											           						//}

																			var selectedNode=$(this).parent().attr('id');
																			
													           				 $.ajax({
													   						  type: "GET",
													   						  contentType: "application/json; charset=utf-8",
													   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
													   						  data: {
													   						                "selectedNode":selectedNode,
													   						                
													   								 },
													   						         dataType: "json",
													   						         success: function (data) {
													   						        	
													   						             if (data != null) {

																								if(window[""+selectedNode]!="cellCreated")
																									{
																									var listCells=data.listCells;
																									console.log("inside node children test if: "+listCells);

																									Create_TreeCells();
																									window[""+selectedNode]="cellCreated";

																								}

														   						             
													   						             }
													   						         },

													   						      error: function(result) {
																			             alert("Error");
																			         }
																			     });
													   						         
											           							
											           			});

											   						  			
											   						  		}
									   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


										   						             }
				

									   						             
								   						             }

								   						         },

								   						      error: function(result) {
														             alert("Error");
														         }
														     });
						   						         

						           					
						           				
						           			});

	        			        	}

	        			        	window["currentSuppCount"+selectedSupp]+=2;

	        		       	}

	        		       	else{
		        			       console.log(">>>>>>>>>>>inside else >>>>>");
			        		       	
	        	   				for (n=window["currentSuppCount"+selectedSupp]; n < listSuppSites.length; n++) {

					           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
					           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
					           		 $("#"+listSuppSites[n][4]+"_f").append(str);


					           			/////////////////////////////////////////////////////////////
					           		    
						  					tree_prop_general();	


					           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "> span").on('click',
					           			            function (e) {
					           			            
					           			                var children = $(this).parent('li.parent_li').find(
					           			                    ' > ul > li');
					           			                if (children.is(":visible")) {
					           			                    children.hide('fast');
					           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

					           			                } else {
					           			                    children.show('fast');
					           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
					           			                }
					           			                e.stopPropagation(i);
					           			            });



					           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "_f> span").on('click',
					           		            function (e) {
					           		            console.log("clicked Nodes");
					           		                var children = $(this).parent('li.parent_li').find(
					           		                    ' > ul > li');
					           		                if (children.is(":visible")) {
					           		                    children.hide('fast');
					           		                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

					           		                } else {
					           		                    children.show('fast');
					           		                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
					           		                }
					           		                e.stopPropagation();
					           		            });

					           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

					           				var res=$(this ).parents()
					           				.map(function() {
					           					return this.id;
					           					})
					           				.get()
					           				.join( "_" );

					           				parents=res.split("_SUP");
					           				var selectedItem=parents[0];
					           				console.log("selectedItem"+selectedItem);
					           				console.log("markersSite"+markersSite);
					           				var ware = selectedItem.substring(0, 4);
					           				
					           				
					           					if(selectedItem!=markersSite)
					           						{
					           							console.log("==============///////////clicked"+selectedItem);
					           				
					           						var selMarker="";
					           				
					           						markerId=selectedItem;				
					           						selMarker=markers[markerId];
					           						var latSitee = selMarker.getPosition().lat();
					           						var lngSitee = selMarker.getPosition().lng();					
					           						selMarker.setIcon(icon2);
					           						
					           						panTo(latSitee, lngSitee);
					           						infowindow.setContent(selMarker.data);
					           						infowindow.open(map,selMarker);

					           						const pos = new google.maps.LatLng(latSitee,lngSitee);
					           						
					           						
					           						circle.setCenter(pos);
					           						circle2.setCenter(pos);
					           				
					           						if(markersSite!="")
					           								{
					           								var otherMarkers=markers[markersSite];			
					           								otherMarkers.setIcon(icon);
					           				
					           									}
					           				
					           						markersSite="";	
					           						markersSite=selectedItem;
					           				
					           						map.setZoom(15);
					           					}

												
						           				 $.ajax({
							   						  type: "GET",
							   						  contentType: "application/json; charset=utf-8",
							   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
							   						  data: {
							   						                "selectedItem":selectedItem,
							   						             	"selectedSupp":selectedSupp
							   						                
							   								 },
							   						         dataType: "json",
							   						         success: function (data) {
							   						        	
							   						             if (data != null) {

								   						             var listSuppNodes=data.listSuppNodes;
								   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
								   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

								   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
										   						  		{
										   						  			str = "";
										   						  			console.log("-----Node Inside the Node typee");
										   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
										   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
										   						  	
										   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
										   						  				
										   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
										   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
										   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
										   						  	
										   						  				str = "";
										   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
										   						  				$("#"+listSuppNodes[j][0]).append(str);
										   						  							    
										   						  			}
										   						  			
										   						  			else{
										   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
										   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
										   						  			}
										   						  			

																	
		   						  									//tree_prop_general();	
		   						  									Tree_PropagationAppendedNodes(listSuppNodes);

												         

												           			
																	////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


												           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
												           				



										           						var res=$(this ).parents()
										           						.map(function() {
										           						return this.id;
										           							})
										           						.get()
										           						.join( "_" );
										           						
										           						parents=res.split("__");
										           						var selected=parents[1].split("_"+parents[3]);
										           						var selectedItem=selected.slice(0, -1);
										           						console.log("parents"+parents);
										           						console.log("selectedItem"+selectedItem+"|");
										           						console.log("markersSite"+markersSite);
										           						//var ware = selectedItem.substring(0, 4);
										           						
										           						//if(ware=="WARE"){
										           						if(selectedItem!=markersSite)
										           						{
										           							
										           						console.log("passed the if >>>....."+selectedItem);
										           						
										           						var selMarker="";
										           						
										           						markerId=selectedItem;				
										           						selMarker=markers[markerId];
										           						var latSitee = selMarker.getPosition().lat();
										           						var lngSitee = selMarker.getPosition().lng();					
										           						selMarker.setIcon(icon2);
										           						panTo(latSitee, lngSitee);
										           						infowindow.setContent(selMarker.data);
										           						infowindow.open(map,selMarker);
										           						
										           						
										           						if(selMarker!=markers[markersSite] && markersSite!="" )
										           						{
										           						var otherMarkers=markers[markersSite];			
										           						otherMarkers.setIcon(icon);
										           						
										           							}
										           						
										           						markersSite="";	
										           						markersSite=selectedItem;
										           						
										           						map.setZoom(15);
										           						}
										           						//}

																		var selectedNode=$(this).parent().attr('id');
																		
												           				 $.ajax({
												   						  type: "GET",
												   						  contentType: "application/json; charset=utf-8",
												   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
												   						  data: {
												   						                "selectedNode":selectedNode,
												   						                
												   								 },
												   						         dataType: "json",
												   						         success: function (data) {
												   						        	
												   						             if (data != null) {

																							if(window[""+selectedNode]!="cellCreated")
																								{
																								var listCells=data.listCells;
																								console.log("inside node children test if: "+listCells);

																								Create_TreeCells();
																								window[""+selectedNode]="cellCreated";

																							}

													   						             
												   						             }
												   						         },

												   						      error: function(result) {
																		             alert("Error");
																		         }
																		     });
												   						         
										           							
										           			});

										   						  			
										   						  		}
								   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


									   						             }
			

								   						             
							   						             }

							   						         },

							   						      error: function(result) {
													             alert("Error");
													         }
													     });
					   						         

					           					
					           				
					           			});
			           		            
	        	   						}

	        	   				window["currentSuppCount"+selectedSupp]+=2;

	        	  					}


	        	  		

	            	
	             			}

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

var children = $('#initial_ul').find(' > ul > li');

$("#network_tree").on('mousewheel DOMMouseScroll', function(e){

var network_tree = document.getElementById('network_tree');
console.log(network_tree.scrollHeight+"  "+network_tree.scrollTop+"  "+network_tree.clientHeight);
if(currentSuppCount<lst.length && network_tree.scrollHeight - network_tree.scrollTop <= network_tree.clientHeight && children.is(":visible")){    

if((currentSuppCount+2)<=listSupp.length){



for (n=currentSuppCount; n < currentSuppCount + 2; n++) {

window["currentSuppCount"+listSupp[n][0]]=2;
var str = "<ul><li class='Supplier' id='"+listSupp[n][0]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSupp[n][1]+"</span>";
str+= "<ul><li id='" +listSupp[n][0]+"_f' class='SiteFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
$("#initial_ul").append(str);


/////////////////////////////////////////////////////////////

tree_Prop("#"+listSupp[n][0]+"> span");
tree_PropInitial_li();
tree_Prop("#"+listSupp[n][0]+"_f> span");
tree_prop_general();


 //////////////////////////////////////////////////////////////////// 


$("#"+listSupp[n][0]+ "> span").on('click',function () {
			
		    var tempClusterArray=[];
			var selectedSupp=$(this).parent().attr('id');
			
			for(i=0,j=0;i<markersSup.length,j<circles.length;i++,j++){

					if(markersSup[i].ID==""+selectedSupp){
						
							console.log("they are same: "+selectedSupp+"|  |"+markersSupplier+"|  ");
							console.log("circles of selected sup: "+circles.length);
							
							markersSup[i].setMap(map);
							var point=markersSup[i].position;
						
							circles[i].setMap(map);
							//
							circles[i].setCenter(point);
							
					}
					
					else{
		
						markersSup[i].getDraggable();
						console.log("markersSup in else stat:");
						markersSup[i].setMap(null);

						circles[i].setMap(null);
						
						circles[i].setCenter(null);
						}
					
			}
				
				 	tempClusterArray=window[""+selectedSupp];
				 	
				 	markerClusterSup.setMap(map);
				 	markerClusterSup.addMarkers(tempClusterArray);
				 	
					console.log("clusterer available and not null"+markerClusterSup.getTotalClusters()+ " having this array of markers==>"+markerClusterSup.getMarkers().length);

			
					map.setZoom(4);

					
					
					
					

					 $.ajax({
					  type: "GET",
					  contentType: "application/json; charset=utf-8",
					  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
					  data: {
					                "selectedSupp":selectedSupp,
					                
							 },
					         dataType: "json",
					         success: function (data) {
					        	
					             if (data != null) {
					            	var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
					            	console.log(data.listSuppSites);
					            	var listSuppSites=data.listSuppSites;
					            	if(SuppChildrenLength<listSuppSites.length){
					            		console.log("passed the children test with number of children equal to "+SuppChildrenLength+" with window[currentSuppCount+selectedSupp]"+window["currentSuppCount"+selectedSupp]+" and selected supplier "+selectedSupp);
					            	for (n = SuppChildrenLength; n < window["currentSuppCount"+selectedSupp]; n++) {			
					            		
					   				 
						           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
						           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
						           		 $("#"+listSuppSites[n][4]+"_f").append(str);

										console.log("the first for loop of 2 sites");
						           			/////////////////////////////////////////////////////////////
						           		    
			   						  	tree_prop_general();	


						           		$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "> span").on('click',
						           			            function (e) {
						           			            
						           			                var children = $(this).parent('li.parent_li').find(
						           			                    ' > ul > li');
						           			                if (children.is(":visible")) {
						           			                    children.hide('fast');
						           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

						           			                } else {
						           			                    children.show('fast');
						           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
						           			                }
						           			                e.stopPropagation(i);
						           			            });




					           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "_f> span").on('click',
					           		            function (e) {
					           		            console.log("clicked Nodes");
					           		                var children = $(this).parent('li.parent_li').find(
					           		                    ' > ul > li');
					           		                if (children.is(":visible")) {
					           		                    children.hide('fast');
					           		                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

					           		                } else {
					           		                    children.show('fast');
					           		                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
					           		                }
					           		                e.stopPropagation();
					           		            });

					           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

					           				var res=$(this ).parents()
					           				.map(function() {
					           					return this.id;
					           					})
					           				.get()
					           				.join( "_" );

					           				parents=res.split("_SUP");
					           				var selectedItem=parents[0];
					           				console.log("selectedItem"+selectedItem);
					           				console.log("markersSite"+markersSite);
					           				var ware = selectedItem.substring(0, 4);
					           				
					           				
					           					if(selectedItem!=markersSite)
					           						{
					           							console.log("==============///////////clicked"+selectedItem);
					           				
					           						var selMarker="";
					           				
					           						markerId=selectedItem;				
					           						selMarker=markers[markerId];
					           						var latSitee = selMarker.getPosition().lat();
					           						var lngSitee = selMarker.getPosition().lng();					
					           						selMarker.setIcon(icon2);
					           						
					           						panTo(latSitee, lngSitee);
					           						infowindow.setContent(selMarker.data);
					           						infowindow.open(map,selMarker);

					           						const pos = new google.maps.LatLng(latSitee,lngSitee);
					           						
					           						
					           						circle.setCenter(pos);
					           						circle2.setCenter(pos);
					           				
					           						if(markersSite!="")
					           								{
					           								var otherMarkers=markers[markersSite];			
					           								otherMarkers.setIcon(icon);
					           				
					           									}
					           				
					           						markersSite="";	
					           						markersSite=selectedItem;
					           				
					           						map.setZoom(15);
					           					}

												
						           				 $.ajax({
							   						  type: "GET",
							   						  contentType: "application/json; charset=utf-8",
							   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
							   						  data: {
							   						                "selectedItem":selectedItem,
							   						             	"selectedSupp":selectedSupp
							   						                
							   								 },
							   						         dataType: "json",
							   						         success: function (data) {
							   						        	
							   						             if (data != null) {

								   						             var listSuppNodes=data.listSuppNodes;
								   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
								   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

								   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
										   						  		{
										   						  			str = "";
										   						  			console.log("-----Node Inside the Node typee");
										   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
										   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
										   						  	
										   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
										   						  				
										   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
										   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
										   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
										   						  	
										   						  				str = "";
										   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
										   						  				$("#"+listSuppNodes[j][0]).append(str);
										   						  							    
										   						  			}
										   						  			
										   						  			else{
										   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
										   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
										   						  			}
										   						  			

																	
		   						  									//tree_prop_general();	
		   						  									Tree_PropagationAppendedNodes(listSuppNodes);

												         

												           			
																	////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


												           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
												           				



										           						var res=$(this ).parents()
										           						.map(function() {
										           						return this.id;
										           							})
										           						.get()
										           						.join( "_" );
										           						
										           						parents=res.split("__");
										           						var selected=parents[1].split("_"+parents[3]);
										           						var selectedItem=selected.slice(0, -1);
										           						console.log("parents"+parents);
										           						console.log("selectedItem"+selectedItem+"|");
										           						console.log("markersSite"+markersSite);
										           						//var ware = selectedItem.substring(0, 4);
										           						
										           						//if(ware=="WARE"){
										           						if(selectedItem!=markersSite)
										           						{
										           							
										           						console.log("passed the if >>>....."+selectedItem);
										           						
										           						var selMarker="";
										           						
										           						markerId=selectedItem;				
										           						selMarker=markers[markerId];
										           						var latSitee = selMarker.getPosition().lat();
										           						var lngSitee = selMarker.getPosition().lng();					
										           						selMarker.setIcon(icon2);
										           						panTo(latSitee, lngSitee);
										           						infowindow.setContent(selMarker.data);
										           						infowindow.open(map,selMarker);
										           						
										           						
										           						if(selMarker!=markers[markersSite] && markersSite!="" )
										           						{
										           						var otherMarkers=markers[markersSite];			
										           						otherMarkers.setIcon(icon);
										           						
										           							}
										           						
										           						markersSite="";	
										           						markersSite=selectedItem;
										           						
										           						map.setZoom(15);
										           						}
										           						//}

																		var selectedNode=$(this).parent().attr('id');
																		
												           				 $.ajax({
												   						  type: "GET",
												   						  contentType: "application/json; charset=utf-8",
												   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
												   						  data: {
												   						                "selectedNode":selectedNode,
												   						                
												   								 },
												   						         dataType: "json",
												   						         success: function (data) {
												   						        	
												   						             if (data != null) {

																							if(window[""+selectedNode]!="cellCreated")
																								{
																								var listCells=data.listCells;
																								console.log("inside node children test if: "+listCells);

																								Create_TreeCells();
																								window[""+selectedNode]="cellCreated";

																							}

													   						             
												   						             }
												   						         },

												   						      error: function(result) {
																		             alert("Error");
																		         }
																		     });
												   						         
										           							
										           			});

										   						  			
										   						  		}
								   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


									   						             }
			

								   						             
							   						             }

							   						         },

							   						      error: function(result) {
													             alert("Error");
													         }
													     });
					   						         

					           					
					           				
					           			});
				           		            
					            			}

					            	var childrenSup = $("#"+selectedSupp+"_f").find(' > ul > li');
					             	$("#network_tree").on('mousewheel DOMMouseScroll', function(e){
					            		
					        	       	var network_tree = document.getElementById('network_tree');
					        	        console.log(network_tree.scrollHeight+"  "+network_tree.scrollTop+"  "+network_tree.clientHeight);
					        	  		if(window["currentSuppCount"+selectedSupp]<listSuppSites.length && network_tree.scrollHeight - network_tree.scrollTop <= network_tree.clientHeight && childrenSup.is(":visible")){    
					        	        
					        		       	if((window["currentSuppCount"+selectedSupp]+2)<=listSuppSites.length){

					        			       console.log(">>>>>>>>>>inside if >>>>>");
					        		       
					        			        	for (n=window["currentSuppCount"+selectedSupp]; n < window["currentSuppCount"+selectedSupp] + 2; n++) {

										           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
										           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
										           		 $("#"+listSuppSites[n][4]+"_f").append(str);
			
			
										           			/////////////////////////////////////////////////////////////
										           		    
			   										  	 tree_prop_general();	

			
										           		 $("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "> span").on('click',
										           			            function (e) {
										           			            
										           			                var children = $(this).parent('li.parent_li').find(
										           			                    ' > ul > li');
										           			                if (children.is(":visible")) {
										           			                    children.hide('fast');
										           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
			
										           			                } else {
										           			                    children.show('fast');
										           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
										           			                }
										           			                e.stopPropagation(i);
										           			            });
			
	
										           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "_f> span").on('click',
										           		            function (e) {
										           		            console.log("clicked Nodes");
										           		                var children = $(this).parent('li.parent_li').find(
										           		                    ' > ul > li');
										           		                if (children.is(":visible")) {
										           		                    children.hide('fast');
										           		                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
			
										           		                } else {
										           		                    children.show('fast');
										           		                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
										           		                }
										           		                e.stopPropagation();
										           		            });

										           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

										           				var res=$(this ).parents()
										           				.map(function() {
										           					return this.id;
										           					})
										           				.get()
										           				.join( "_" );

										           				parents=res.split("_SUP");
										           				var selectedItem=parents[0];
										           				console.log("selectedItem"+selectedItem);
										           				console.log("markersSite"+markersSite);
										           				var ware = selectedItem.substring(0, 4);
										           				
										           				
										           					if(selectedItem!=markersSite)
										           						{
										           							console.log("==============///////////clicked"+selectedItem);
										           				
										           						var selMarker="";
										           				
										           						markerId=selectedItem;				
										           						selMarker=markers[markerId];
										           						var latSitee = selMarker.getPosition().lat();
										           						var lngSitee = selMarker.getPosition().lng();					
										           						selMarker.setIcon(icon2);
										           						
										           						panTo(latSitee, lngSitee);
										           						infowindow.setContent(selMarker.data);
										           						infowindow.open(map,selMarker);

										           						const pos = new google.maps.LatLng(latSitee,lngSitee);
										           						
										           						
										           						circle.setCenter(pos);
										           						circle2.setCenter(pos);
										           				
										           						if(markersSite!="")
										           								{
										           								var otherMarkers=markers[markersSite];			
										           								otherMarkers.setIcon(icon);
										           				
										           									}
										           				
										           						markersSite="";	
										           						markersSite=selectedItem;
										           				
										           						map.setZoom(15);
										           					}

																	
											           				 $.ajax({
												   						  type: "GET",
												   						  contentType: "application/json; charset=utf-8",
												   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
												   						  data: {
												   						                "selectedItem":selectedItem,
												   						             	"selectedSupp":selectedSupp
												   						                
												   								 },
												   						         dataType: "json",
												   						         success: function (data) {
												   						        	
												   						             if (data != null) {

													   						             var listSuppNodes=data.listSuppNodes;
													   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
													   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

													   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
															   						  		{
															   						  			str = "";
															   						  			console.log("-----Node Inside the Node typee");
															   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
															   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
															   						  	
															   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
															   						  				
															   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
															   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
															   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
															   						  	
															   						  				str = "";
															   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
															   						  				$("#"+listSuppNodes[j][0]).append(str);
															   						  							    
															   						  			}
															   						  			
															   						  			else{
															   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
															   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
															   						  			}
															   						  			

																						
							   						  									//tree_prop_general();	
							   						  									Tree_PropagationAppendedNodes(listSuppNodes);

																	         

																	           			
																						////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


																	           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
																	           				



															           						var res=$(this ).parents()
															           						.map(function() {
															           						return this.id;
															           							})
															           						.get()
															           						.join( "_" );
															           						
															           						parents=res.split("__");
															           						var selected=parents[1].split("_"+parents[3]);
															           						var selectedItem=selected.slice(0, -1);
															           						console.log("parents"+parents);
															           						console.log("selectedItem"+selectedItem+"|");
															           						console.log("markersSite"+markersSite);
															           						//var ware = selectedItem.substring(0, 4);
															           						
															           						//if(ware=="WARE"){
															           						if(selectedItem!=markersSite)
															           						{
															           							
															           						console.log("passed the if >>>....."+selectedItem);
															           						
															           						var selMarker="";
															           						
															           						markerId=selectedItem;				
															           						selMarker=markers[markerId];
															           						var latSitee = selMarker.getPosition().lat();
															           						var lngSitee = selMarker.getPosition().lng();					
															           						selMarker.setIcon(icon2);
															           						panTo(latSitee, lngSitee);
															           						infowindow.setContent(selMarker.data);
															           						infowindow.open(map,selMarker);
															           						
															           						
															           						if(selMarker!=markers[markersSite] && markersSite!="" )
															           						{
															           						var otherMarkers=markers[markersSite];			
															           						otherMarkers.setIcon(icon);
															           						
															           							}
															           						
															           						markersSite="";	
															           						markersSite=selectedItem;
															           						
															           						map.setZoom(15);
															           						}
															           						//}

																							var selectedNode=$(this).parent().attr('id');
																							
																	           				 $.ajax({
																	   						  type: "GET",
																	   						  contentType: "application/json; charset=utf-8",
																	   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
																	   						  data: {
																	   						                "selectedNode":selectedNode,
																	   						                
																	   								 },
																	   						         dataType: "json",
																	   						         success: function (data) {
																	   						        	
																	   						             if (data != null) {

																												if(window[""+selectedNode]!="cellCreated")
																													{
																													var listCells=data.listCells;
																													console.log("inside node children test if: "+listCells);

																													Create_TreeCells();
																													window[""+selectedNode]="cellCreated";

																												}

																		   						             
																	   						             }
																	   						         },

																	   						      error: function(result) {
																							             alert("Error");
																							         }
																							     });
																	   						         
															           							
															           			});

															   						  			
															   						  		}
													   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


														   						             }
								

													   						             
												   						             }

												   						         },

												   						      error: function(result) {
																		             alert("Error");
																		         }
																		     });
										   						         

										           					
										           				
										           			});

					        			        	}

					        			        	window["currentSuppCount"+selectedSupp]+=2;

					        		       	}

					        		       	else{
						        			       console.log(">>>>>>>>>>>inside else >>>>>");
							        		       	
					        	   				for (n=window["currentSuppCount"+selectedSupp]; n < listSuppSites.length; n++) {

									           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
									           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
									           		 $("#"+listSuppSites[n][4]+"_f").append(str);
		
		
									           			/////////////////////////////////////////////////////////////
									           		    
			   						  					tree_prop_general();	

		
									           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "> span").on('click',
									           			            function (e) {
									           			            
									           			                var children = $(this).parent('li.parent_li').find(
									           			                    ' > ul > li');
									           			                if (children.is(":visible")) {
									           			                    children.hide('fast');
									           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
		
									           			                } else {
									           			                    children.show('fast');
									           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
									           			                }
									           			                e.stopPropagation(i);
									           			            });
		

		
									           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "_f> span").on('click',
									           		            function (e) {
									           		            console.log("clicked Nodes");
									           		                var children = $(this).parent('li.parent_li').find(
									           		                    ' > ul > li');
									           		                if (children.is(":visible")) {
									           		                    children.hide('fast');
									           		                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
		
									           		                } else {
									           		                    children.show('fast');
									           		                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
									           		                }
									           		                e.stopPropagation();
									           		            });

									           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

									           				var res=$(this ).parents()
									           				.map(function() {
									           					return this.id;
									           					})
									           				.get()
									           				.join( "_" );

									           				parents=res.split("_SUP");
									           				var selectedItem=parents[0];
									           				console.log("selectedItem"+selectedItem);
									           				console.log("markersSite"+markersSite);
									           				var ware = selectedItem.substring(0, 4);
									           				
									           				
									           					if(selectedItem!=markersSite)
									           						{
									           							console.log("==============///////////clicked"+selectedItem);
									           				
									           						var selMarker="";
									           				
									           						markerId=selectedItem;				
									           						selMarker=markers[markerId];
									           						var latSitee = selMarker.getPosition().lat();
									           						var lngSitee = selMarker.getPosition().lng();					
									           						selMarker.setIcon(icon2);
									           						
									           						panTo(latSitee, lngSitee);
									           						infowindow.setContent(selMarker.data);
									           						infowindow.open(map,selMarker);

									           						const pos = new google.maps.LatLng(latSitee,lngSitee);
									           						
									           						
									           						circle.setCenter(pos);
									           						circle2.setCenter(pos);
									           				
									           						if(markersSite!="")
									           								{
									           								var otherMarkers=markers[markersSite];			
									           								otherMarkers.setIcon(icon);
									           				
									           									}
									           				
									           						markersSite="";	
									           						markersSite=selectedItem;
									           				
									           						map.setZoom(15);
									           					}

																
										           				 $.ajax({
											   						  type: "GET",
											   						  contentType: "application/json; charset=utf-8",
											   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
											   						  data: {
											   						                "selectedItem":selectedItem,
											   						             	"selectedSupp":selectedSupp
											   						                
											   								 },
											   						         dataType: "json",
											   						         success: function (data) {
											   						        	
											   						             if (data != null) {

												   						             var listSuppNodes=data.listSuppNodes;
												   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
												   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

												   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
														   						  		{
														   						  			str = "";
														   						  			console.log("-----Node Inside the Node typee");
														   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
														   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
														   						  	
														   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
														   						  				
														   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
														   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
														   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
														   						  	
														   						  				str = "";
														   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
														   						  				$("#"+listSuppNodes[j][0]).append(str);
														   						  							    
														   						  			}
														   						  			
														   						  			else{
														   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
														   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
														   						  			}
														   						  			

																					
						   						  									//tree_prop_general();	
						   						  									Tree_PropagationAppendedNodes(listSuppNodes);

																         

																           			
																					////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


																           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
																           				



														           						var res=$(this ).parents()
														           						.map(function() {
														           						return this.id;
														           							})
														           						.get()
														           						.join( "_" );
														           						
														           						parents=res.split("__");
														           						var selected=parents[1].split("_"+parents[3]);
														           						var selectedItem=selected.slice(0, -1);
														           						console.log("parents"+parents);
														           						console.log("selectedItem"+selectedItem+"|");
														           						console.log("markersSite"+markersSite);
														           						//var ware = selectedItem.substring(0, 4);
														           						
														           						//if(ware=="WARE"){
														           						if(selectedItem!=markersSite)
														           						{
														           							
														           						console.log("passed the if >>>....."+selectedItem);
														           						
														           						var selMarker="";
														           						
														           						markerId=selectedItem;				
														           						selMarker=markers[markerId];
														           						var latSitee = selMarker.getPosition().lat();
														           						var lngSitee = selMarker.getPosition().lng();					
														           						selMarker.setIcon(icon2);
														           						panTo(latSitee, lngSitee);
														           						infowindow.setContent(selMarker.data);
														           						infowindow.open(map,selMarker);
														           						
														           						
														           						if(selMarker!=markers[markersSite] && markersSite!="" )
														           						{
														           						var otherMarkers=markers[markersSite];			
														           						otherMarkers.setIcon(icon);
														           						
														           							}
														           						
														           						markersSite="";	
														           						markersSite=selectedItem;
														           						
														           						map.setZoom(15);
														           						}
														           						//}

																						var selectedNode=$(this).parent().attr('id');
																						
																           				 $.ajax({
																   						  type: "GET",
																   						  contentType: "application/json; charset=utf-8",
																   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
																   						  data: {
																   						                "selectedNode":selectedNode,
																   						                
																   								 },
																   						         dataType: "json",
																   						         success: function (data) {
																   						        	
																   						             if (data != null) {

																											if(window[""+selectedNode]!="cellCreated")
																												{
																												var listCells=data.listCells;
																												console.log("inside node children test if: "+listCells);

																												Create_TreeCells();
																												window[""+selectedNode]="cellCreated";

																											}

																	   						             
																   						             }
																   						         },

																   						      error: function(result) {
																						             alert("Error");
																						         }
																						     });
																   						         
														           							
														           			});

														   						  			
														   						  		}
												   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


													   						             }
							

												   						             
											   						             }

											   						         },

											   						      error: function(result) {
																	             alert("Error");
																	         }
																	     });
									   						         

									           					
									           				
									           			});
							           		            
					        	   						}

					        	   				window["currentSuppCount"+selectedSupp]+=2;

					        	  					}


					        	  		

					            	
					             			}

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

currentSuppCount+=2;

}

else{
for (n=currentSuppCount; n < listSupp.length; n++) {

window["currentSuppCount"+listSupp[n][0]]=2;
var str = "<ul><li class='Supplier' id='"+listSupp[n][0]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSupp[n][1]+"</span>";
str+= "<ul><li id='" +listSupp[n][0]+"_f' class='SiteFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul></li></ul>";
$("#initial_ul").append(str);


/////////////////////////////////////////////////////////////

tree_Prop("#"+listSupp[n][0]+"> span");
tree_PropInitial_li();
tree_Prop("#"+listSupp[n][0]+"_f> span");
tree_prop_general();

 //////////////////////////////////////////////////////////////////// 


$("#"+listSupp[n][0]+ "> span").on('click',function () {
			
		    var tempClusterArray=[];
			var selectedSupp=$(this).parent().attr('id');
			
			for(i=0,j=0;i<markersSup.length,j<circles.length;i++,j++){

					if(markersSup[i].ID==""+selectedSupp){
						
							console.log("they are same: "+selectedSupp+"|  |"+markersSupplier+"|  ");
							console.log("circles of selected sup: "+circles.length);
							
							markersSup[i].setMap(map);
							var point=markersSup[i].position;
						
							circles[i].setMap(map);
							//
							circles[i].setCenter(point);
							
					}
					
					else{
		
						markersSup[i].getDraggable();
						console.log("markersSup in else stat:");
						markersSup[i].setMap(null);

						circles[i].setMap(null);
						
						circles[i].setCenter(null);
						}
					
			}
				
				 	tempClusterArray=window[""+selectedSupp];
				 	
				 	markerClusterSup.setMap(map);
				 	markerClusterSup.addMarkers(tempClusterArray);
				 	
					console.log("clusterer available and not null"+markerClusterSup.getTotalClusters()+ " having this array of markers==>"+markerClusterSup.getMarkers().length);

			
					map.setZoom(4);

					
					
						

						 $.ajax({
						  type: "GET",
						  contentType: "application/json; charset=utf-8",
						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
						  data: {
						                "selectedSupp":selectedSupp,
						                
								 },
						         dataType: "json",
						         success: function (data) {
						        	
						             if (data != null) {
						            	var SuppChildrenLength=$("#" +selectedSupp+"_f").find(' > ul > li').length;	
						            	console.log(data.listSuppSites);
						            	var listSuppSites=data.listSuppSites;
						            	if(SuppChildrenLength<listSuppSites.length){
						            		console.log("passed the children test with number of children equal to "+SuppChildrenLength);
						            	for (n = SuppChildrenLength; n < window["currentSuppCount"+selectedSupp]; n++) {			
						            		
						   				 
							           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
							           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
							           		 $("#"+listSuppSites[n][4]+"_f").append(str);

											console.log("the first for loop of 2 sites");
							           			/////////////////////////////////////////////////////////////
							           		    
			   						  		tree_prop_general();	


							           		$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "> span").on('click',
							           			            function (e) {
							           			            
							           			                var children = $(this).parent('li.parent_li').find(
							           			                    ' > ul > li');
							           			                if (children.is(":visible")) {
							           			                    children.hide('fast');
							           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

							           			                } else {
							           			                    children.show('fast');
							           			                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
							           			                }
							           			                e.stopPropagation(i);
							           			            });


						           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+ "_f> span").on('click',
						           		            function (e) {
						           		            console.log("clicked Nodes");
						           		                var children = $(this).parent('li.parent_li').find(
						           		                    ' > ul > li');
						           		                if (children.is(":visible")) {
						           		                    children.hide('fast');
						           		                    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');

						           		                } else {
						           		                    children.show('fast');
						           		                   $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
						           		                }
						           		                e.stopPropagation();
						           		            });
						           			$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

						           				var res=$(this ).parents()
						           				.map(function() {
						           					return this.id;
						           					})
						           				.get()
						           				.join( "_" );

						           				parents=res.split("_SUP");
						           				var selectedItem=parents[0];
						           				console.log("selectedItem"+selectedItem);
						           				console.log("markersSite"+markersSite);
						           				var ware = selectedItem.substring(0, 4);
						           				
						           				
						           					if(selectedItem!=markersSite)
						           						{
						           							console.log("==============///////////clicked"+selectedItem);
						           				
						           						var selMarker="";
						           				
						           						markerId=selectedItem;				
						           						selMarker=markers[markerId];
						           						var latSitee = selMarker.getPosition().lat();
						           						var lngSitee = selMarker.getPosition().lng();					
						           						selMarker.setIcon(icon2);
						           						
						           						panTo(latSitee, lngSitee);
						           						infowindow.setContent(selMarker.data);
						           						infowindow.open(map,selMarker);

						           						const pos = new google.maps.LatLng(latSitee,lngSitee);
						           						
						           						
						           						circle.setCenter(pos);
						           						circle2.setCenter(pos);
						           				
						           						if(markersSite!="")
						           								{
						           								var otherMarkers=markers[markersSite];			
						           								otherMarkers.setIcon(icon);
						           				
						           									}
						           				
						           						markersSite="";	
						           						markersSite=selectedItem;
						           				
						           						map.setZoom(15);
						           					}

													
							           				 $.ajax({
								   						  type: "GET",
								   						  contentType: "application/json; charset=utf-8",
								   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
								   						  data: {
								   						                "selectedItem":selectedItem,
								   						             	"selectedSupp":selectedSupp
								   						                
								   								 },
								   						         dataType: "json",
								   						         success: function (data) {
								   						        	
								   						             if (data != null) {

									   						             var listSuppNodes=data.listSuppNodes;
									   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
									   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

									   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
											   						  		{
											   						  			str = "";
											   						  			console.log("-----Node Inside the Node typee");
											   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
											   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
											   						  	
											   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
											   						  				
											   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
											   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
											   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
											   						  	
											   						  				str = "";
											   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
											   						  				$("#"+listSuppNodes[j][0]).append(str);
											   						  							    
											   						  			}
											   						  			
											   						  			else{
											   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
											   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
											   						  			}
											   						  			

																		
			   						  									//tree_prop_general();	
			   						  									Tree_PropagationAppendedNodes(listSuppNodes);

													         

													           			
																		////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


													           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
													           				



											           						var res=$(this ).parents()
											           						.map(function() {
											           						return this.id;
											           							})
											           						.get()
											           						.join( "_" );
											           						
											           						parents=res.split("__");
											           						var selected=parents[1].split("_"+parents[3]);
											           						var selectedItem=selected.slice(0, -1);
											           						console.log("parents"+parents);
											           						console.log("selectedItem"+selectedItem+"|");
											           						console.log("markersSite"+markersSite);
											           						//var ware = selectedItem.substring(0, 4);
											           						
											           						//if(ware=="WARE"){
											           						if(selectedItem!=markersSite)
											           						{
											           							
											           						console.log("passed the if >>>....."+selectedItem);
											           						
											           						var selMarker="";
											           						
											           						markerId=selectedItem;				
											           						selMarker=markers[markerId];
											           						var latSitee = selMarker.getPosition().lat();
											           						var lngSitee = selMarker.getPosition().lng();					
											           						selMarker.setIcon(icon2);
											           						panTo(latSitee, lngSitee);
											           						infowindow.setContent(selMarker.data);
											           						infowindow.open(map,selMarker);
											           						
											           						
											           						if(selMarker!=markers[markersSite] && markersSite!="" )
											           						{
											           						var otherMarkers=markers[markersSite];			
											           						otherMarkers.setIcon(icon);
											           						
											           							}
											           						
											           						markersSite="";	
											           						markersSite=selectedItem;
											           						
											           						map.setZoom(15);
											           						}
											           						//}

																			var selectedNode=$(this).parent().attr('id');
																			
													           				 $.ajax({
													   						  type: "GET",
													   						  contentType: "application/json; charset=utf-8",
													   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
													   						  data: {
													   						                "selectedNode":selectedNode,
													   						                
													   								 },
													   						         dataType: "json",
													   						         success: function (data) {
													   						        	
													   						             if (data != null) {

																								if(window[""+selectedNode]!="cellCreated")
																									{
																									var listCells=data.listCells;
																									console.log("inside node children test if: "+listCells);

																									Create_TreeCells();
																									window[""+selectedNode]="cellCreated";

																								}

														   						             
													   						             }
													   						         },

													   						      error: function(result) {
																			             alert("Error");
																			         }
																			     });
													   						         
											           							
											           			});

											   						  			
											   						  		}
									   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


										   						             }
				

									   						             
								   						             }

								   						         },

								   						      error: function(result) {
														             alert("Error");
														         }
														     });
						   						         

						           					
						           				
						           			});
						            			}

						            	var childrenSup = $("#"+selectedSupp+"_f").find(' > ul > li');
						             	$("#network_tree").on('mousewheel DOMMouseScroll', function(e){
						            		
						        	       	var network_tree = document.getElementById('network_tree');
						        	        console.log(network_tree.scrollHeight+"  "+network_tree.scrollTop+"  "+network_tree.clientHeight);
						        	  		if(window["currentSuppCount"+selectedSupp]<listSuppSites.length && network_tree.scrollHeight - network_tree.scrollTop <= network_tree.clientHeight && childrenSup.is(":visible")){    
						        	        
						        		       	if((window["currentSuppCount"+selectedSupp]+2)<=listSuppSites.length){

						        			       console.log(">>>>>>>>>>inside if >>>>>");
						        		       
						        			        	for (n=window["currentSuppCount"+selectedSupp]; n < window["currentSuppCount"+selectedSupp] + 2; n++) {

											           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
											           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
											           		 $("#"+listSuppSites[n][4]+"_f").append(str);
				
				
											           			/////////////////////////////////////////////////////////////
											           	tree_prop_general();	
	
							   						    tree_Prop("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"> span");
							   						    tree_Prop("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f > span");

										           		$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

										           				var res=$(this ).parents()
										           				.map(function() {
										           					return this.id;
										           					})
										           				.get()
										           				.join( "_" );

										           				parents=res.split("_SUP");
										           				var selectedItem=parents[0];
										           				console.log("selectedItem"+selectedItem);
										           				console.log("markersSite"+markersSite);
										           				var ware = selectedItem.substring(0, 4);
										           				
										           				
										           					if(selectedItem!=markersSite)
										           						{
										           							console.log("==============///////////clicked"+selectedItem);
										           				
										           						var selMarker="";
										           				
										           						markerId=selectedItem;				
										           						selMarker=markers[markerId];
										           						var latSitee = selMarker.getPosition().lat();
										           						var lngSitee = selMarker.getPosition().lng();					
										           						selMarker.setIcon(icon2);
										           						
										           						panTo(latSitee, lngSitee);
										           						infowindow.setContent(selMarker.data);
										           						infowindow.open(map,selMarker);

										           						const pos = new google.maps.LatLng(latSitee,lngSitee);
										           						
										           						
										           						circle.setCenter(pos);
										           						circle2.setCenter(pos);
										           				
										           						if(markersSite!="")
										           								{
										           								var otherMarkers=markers[markersSite];			
										           								otherMarkers.setIcon(icon);
										           				
										           									}
										           				
										           						markersSite="";	
										           						markersSite=selectedItem;
										           				
										           						map.setZoom(15);
										           					}

																	
											           				 $.ajax({
												   						  type: "GET",
												   						  contentType: "application/json; charset=utf-8",
												   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
												   						  data: {
												   						                "selectedItem":selectedItem,
												   						             	"selectedSupp":selectedSupp
												   						                
												   								 },
												   						         dataType: "json",
												   						         success: function (data) {
												   						        	
												   						             if (data != null) {

													   						             var listSuppNodes=data.listSuppNodes;
													   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
													   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

													   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
															   						  		{
															   						  			str = "";
															   						  			console.log("-----Node Inside the Node typee");
															   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
															   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
															   						  	
															   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
															   						  				
															   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
															   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
															   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
															   						  	
															   						  				str = "";
															   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
															   						  				$("#"+listSuppNodes[j][0]).append(str);
															   						  							    
															   						  			}
															   						  			
															   						  			else{
															   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
															   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
															   						  			}
															   						  			

																						
							   						  									//tree_prop_general();	
							   						  									Tree_PropagationAppendedNodes(listSuppNodes);

																	         

																	           			
																						////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


																	           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
																	           				



															           						var res=$(this ).parents()
															           						.map(function() {
															           						return this.id;
															           							})
															           						.get()
															           						.join( "_" );
															           						
															           						parents=res.split("__");
															           						var selected=parents[1].split("_"+parents[3]);
															           						var selectedItem=selected.slice(0, -1);
															           						console.log("parents"+parents);
															           						console.log("selectedItem"+selectedItem+"|");
															           						console.log("markersSite"+markersSite);
															           						//var ware = selectedItem.substring(0, 4);
															           						
															           						//if(ware=="WARE"){
															           						if(selectedItem!=markersSite)
															           						{
															           							
															           						console.log("passed the if >>>....."+selectedItem);
															           						
															           						var selMarker="";
															           						
															           						markerId=selectedItem;				
															           						selMarker=markers[markerId];
															           						var latSitee = selMarker.getPosition().lat();
															           						var lngSitee = selMarker.getPosition().lng();					
															           						selMarker.setIcon(icon2);
															           						panTo(latSitee, lngSitee);
															           						infowindow.setContent(selMarker.data);
															           						infowindow.open(map,selMarker);
															           						
															           						
															           						if(selMarker!=markers[markersSite] && markersSite!="" )
															           						{
															           						var otherMarkers=markers[markersSite];			
															           						otherMarkers.setIcon(icon);
															           						
															           							}
															           						
															           						markersSite="";	
															           						markersSite=selectedItem;
															           						
															           						map.setZoom(15);
															           						}
															           						//}

																							var selectedNode=$(this).parent().attr('id');
																							
																	           				 $.ajax({
																	   						  type: "GET",
																	   						  contentType: "application/json; charset=utf-8",
																	   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
																	   						  data: {
																	   						                "selectedNode":selectedNode,
																	   						                
																	   								 },
																	   						         dataType: "json",
																	   						         success: function (data) {
																	   						        	
																	   						             if (data != null) {

																												if(window[""+selectedNode]!="cellCreated")
																													{
																													var listCells=data.listCells;
																													console.log("inside node children test if: "+listCells);

																													Create_TreeCells();
																													window[""+selectedNode]="cellCreated";

																												}

																		   						             
																	   						             }
																	   						         },

																	   						      error: function(result) {
																							             alert("Error");
																							         }
																							     });
																	   						         
															           							
															           			});

															   						  			
															   						  		}
													   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


														   						             }
								

													   						             
												   						             }

												   						         },

												   						      error: function(result) {
																		             alert("Error");
																		         }
																		     });
										   						         

										           					
										           				
										           			});

						        			        	}

						        			        	window["currentSuppCount"+selectedSupp]+=2;

						        		       	}

						        		       	else{
							        			       console.log(">>>>>>>>>>>inside else >>>>>");
								        		       	
						        	   				for (n=window["currentSuppCount"+selectedSupp]; n < listSuppSites.length; n++) {

										           		 var str = "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span>";
										           		 str+= "<ul><li id='" +listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' class='NodeFolder' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
										           		 $("#"+listSuppSites[n][4]+"_f").append(str);
			
			
										           			/////////////////////////////////////////////////////////////
										           		    
			   						  					tree_prop_general();	

							   						    tree_Prop("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"> span");
							   						    tree_Prop("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f > span");

										           		$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+" > span").on('click', function (e) {

										           				var res=$(this ).parents()
										           				.map(function() {
										           					return this.id;
										           					})
										           				.get()
										           				.join( "_" );

										           				parents=res.split("_SUP");
										           				var selectedItem=parents[0];
										           				console.log("selectedItem"+selectedItem);
										           				console.log("markersSite"+markersSite);
										           				var ware = selectedItem.substring(0, 4);
										           				
										           				
										           					if(selectedItem!=markersSite)
										           						{
										           							console.log("==============///////////clicked"+selectedItem);
										           				
										           						var selMarker="";
										           				
										           						markerId=selectedItem;				
										           						selMarker=markers[markerId];
										           						var latSitee = selMarker.getPosition().lat();
										           						var lngSitee = selMarker.getPosition().lng();					
										           						selMarker.setIcon(icon2);
										           						
										           						panTo(latSitee, lngSitee);
										           						infowindow.setContent(selMarker.data);
										           						infowindow.open(map,selMarker);

										           						const pos = new google.maps.LatLng(latSitee,lngSitee);
										           						
										           						
										           						circle.setCenter(pos);
										           						circle2.setCenter(pos);
										           				
										           						if(markersSite!="")
										           								{
										           								var otherMarkers=markers[markersSite];			
										           								otherMarkers.setIcon(icon);
										           				
										           									}
										           				
										           						markersSite="";	
										           						markersSite=selectedItem;
										           				
										           						map.setZoom(15);
										           					}

																	
											           				 $.ajax({
												   						  type: "GET",
												   						  contentType: "application/json; charset=utf-8",
												   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
												   						  data: {
												   						                "selectedItem":selectedItem,
												   						             	"selectedSupp":selectedSupp
												   						                
												   								 },
												   						         dataType: "json",
												   						         success: function (data) {
												   						        	
												   						             if (data != null) {

													   						             var listSuppNodes=data.listSuppNodes;
													   						             var siteChildren=$("#"+selectedItem+"_f") .find(' > ul > li').length;
													   						             if(window[""+selectedItem+"_"+selectedSupp]!="nodesCreated"){

													   						         	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
															   						  		{
															   						  			str = "";
															   						  			console.log("-----Node Inside the Node typee");
															   						  			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
															   						  			//console.log("lllllllllllllllllllllll"+lst[n][1]);
															   						  	
															   						  			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
															   						  				
															   						  				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
															   						  				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
															   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
															   						  	
															   						  				str = "";
															   						  				str ="<ul><li id='" + listSuppNodes[j][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
															   						  				$("#"+listSuppNodes[j][0]).append(str);
															   						  							    
															   						  			}
															   						  			
															   						  			else{
															   						  				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
															   						  				$("ul").find("#"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
															   						  			}
															   						  			

																						
							   						  									//tree_prop_general();	
							   						  									Tree_PropagationAppendedNodes(listSuppNodes);

																	         

																	           			
																						////////////////////////////////////>>>>>>>>>>>>>>>>>>>//////////////////////////


																	           			$("#"+listSuppNodes[j][0]+" > span").on('click',function () {
																	           				



															           						var res=$(this ).parents()
															           						.map(function() {
															           						return this.id;
															           							})
															           						.get()
															           						.join( "_" );
															           						
															           						parents=res.split("__");
															           						var selected=parents[1].split("_"+parents[3]);
															           						var selectedItem=selected.slice(0, -1);
															           						console.log("parents"+parents);
															           						console.log("selectedItem"+selectedItem+"|");
															           						console.log("markersSite"+markersSite);
															           						//var ware = selectedItem.substring(0, 4);
															           						
															           						//if(ware=="WARE"){
															           						if(selectedItem!=markersSite)
															           						{
															           							
															           						console.log("passed the if >>>....."+selectedItem);
															           						
															           						var selMarker="";
															           						
															           						markerId=selectedItem;				
															           						selMarker=markers[markerId];
															           						var latSitee = selMarker.getPosition().lat();
															           						var lngSitee = selMarker.getPosition().lng();					
															           						selMarker.setIcon(icon2);
															           						panTo(latSitee, lngSitee);
															           						infowindow.setContent(selMarker.data);
															           						infowindow.open(map,selMarker);
															           						
															           						
															           						if(selMarker!=markers[markersSite] && markersSite!="" )
															           						{
															           						var otherMarkers=markers[markersSite];			
															           						otherMarkers.setIcon(icon);
															           						
															           							}
															           						
															           						markersSite="";	
															           						markersSite=selectedItem;
															           						
															           						map.setZoom(15);
															           						}
															           						//}

																							var selectedNode=$(this).parent().attr('id');
																							
																	           				 $.ajax({
																	   						  type: "GET",
																	   						  contentType: "application/json; charset=utf-8",
																	   						  url: '${pageContext.request.contextPath}/FindOnClick_SuppSiteNodeCell',
																	   						  data: {
																	   						                "selectedNode":selectedNode,
																	   						                
																	   								 },
																	   						         dataType: "json",
																	   						         success: function (data) {
																	   						        	
																	   						             if (data != null) {

																												if(window[""+selectedNode]!="cellCreated")
																													{
																													var listCells=data.listCells;
																													console.log("inside node children test if: "+listCells);

																													Create_TreeCells();
																													window[""+selectedNode]="cellCreated";

																												}

																		   						             
																	   						             }
																	   						         },

																	   						      error: function(result) {
																							             alert("Error");
																							         }
																							     });
																	   						         
															           							
															           			});

															   						  			
															   						  		}
													   						         	window[selectedItem+"_"+selectedSupp]="nodesCreated";


														   						             }
								

													   						             
												   						             }

												   						         },

												   						      error: function(result) {
																		             alert("Error");
																		         }
																		     });
										   						         

										           					
										           				
										           			});
								           		            
						        	   						}

						        	   				window["currentSuppCount"+selectedSupp]+=2;

						        	  					}


						        	  		

						            	
						             			}

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

currentSuppCount+=2;

}

}

});


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


}




function CreateTree_StNdCell(lst,listNodes,listCells){

	var currentCellsCount=2;

	var str="<ul><li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li></ul>";
	$("#network_tree").empty().html(str);
	$("#network_tree").append("<label id='loadmore' style='margin-left:35%;margin-top:1%;visibility:hidden;'> Scroll to load more</label>");
	 
	for (n = 0; n < currentCellsCount; n++) {			// siteId, wareName

		 var str = "<ul><li class='Site' id='" +lst[n][2]+ "' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+lst[n][1]+"</span>";
		 str+= "<ul><li id='" +lst[n][2]+"_f' class='tree-span' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
		 $("#initial_ul").append(str);

/*		  	str="<ul><li id='" +lst[n][2]+"_f' class='tree-span' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul>";
		  
		  $("#"+lst[n][2]).append(str);
*/

		}
	var children = $('#initial_ul').find(' > ul > li');

    $("#network_tree").on('mousewheel DOMMouseScroll', function(e){
	
        var network_tree = document.getElementById('network_tree');
        console.log(network_tree.scrollHeight+"  "+network_tree.scrollTop+"  "+network_tree.clientHeight);
        
   		if(currentCellsCount<lst.length && network_tree.scrollHeight - network_tree.scrollTop <= network_tree.clientHeight && children.is(":visible")){    
         
        	if((currentCellsCount+2)<=lst.length){
        
	        	for (i=currentCellsCount; i < currentCellsCount + 2; i++) {
	        	
	           		console.log("currentCellsCount+2");
		 			var str = "<ul><li class='Site' id='" +lst[i][2]+ "'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x' style='margin-left:5px;'></i> "+lst[i][1]+"</span>";
		 			str+= "<ul><li id='" +lst[i][2]+"_f' class='tree-span' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
		 			$("#initial_ul").append(str);
	            
	            }
	            currentCellsCount+=2;
            }
            else{
	            console.log("currentCellsCount+2< lst");
	            for (i=currentCellsCount; i < lst.length-currentCellsCount; i++) {
	        		           		 
		 			var str = "<ul><li class='Site' id='" +lst[i][2]+ "' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x' style='margin-left:5px;'></i> "+lst[i][1]+"</span>";
		 			str+= "<ul><li id='" +lst[i][2]+"_f' class='tree-span' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul></li></ul>";
		 			$("#initial_ul").append(str);
			  		
	            }
	        $("#loadmore").hide();
	        currentCellsCount+=lst.length-currentCellsCount;	               
	       }
	    console.log("currentCellsCount----->"+currentCellsCount); 
	            
       } 
        
            
       if(children.is(':visible') || currentCellsCount==lst.length){
            document.getElementById("loadmore").style.visibility = "visible";
            }
                
       else{
            document.getElementById("loadmore").style.visibility = "hidden";

            }
          
       });
       
       /*
	for(j=0;j<listNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL


		{
			
			str= "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";
  					

				if(listNodes[j][5]>0 || listNodes[j][6]>0 || listNodes[j][7]>0){
					str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
					str+= "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
					$("#"+listNodes[j][4]+"_f").append(str);
				 	str="<ul><li id='" + listNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
				    $("#"+listNodes[j][0]).append(str);
					}
				else{
					str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
					//$("#"+listNodes[j][4]+"_f").append(str);
					
					}
		}

														
	for (k = 0; k < (listCells.length - 500); k++) {
	
		
	
				//$("#"+listCells[k][2]+"_f").append("<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'><span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>");
				document.getElementById(listCells[k][2]+"_f").innerHTML+="<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'><span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>";

//				console.log("node id is " +listCells[k][2]+"_f" +" and the cell id is " +listCells[k][0] + "the cell name is " +listCells[k][1]);
	}
	
	
			*/
			console.log("--------- **********************  cell  Inside the Node is " +(listCells.length - 500));																										 
			 
}




function CreateTree_Cell(lst){		
	
	var currentCellsCount=250;
	var str="<ul><li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li>	</ul>";
	
	$("#network_tree").append(str);
	$("#network_tree").append("<label id='loadmore' style='margin-left:35%;margin-top:1%;visibility:hidden;'> Scroll to load more</label>");
	 
	for (n = 0; n <currentCellsCount ; n++) {			// CellId, CellName
		
		 var str= "<ul><li class='Site' id='" +lst[n][1]+ "' style='display:none'> <span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+lst[n][0]+"</span></li></ul>";
		
		 $("#initial_ul").append(str);
		}
	//console.log(">>>>CELLSSS>>>>>>>"+(lst.length));
		  		
		  		
	var children = $('#initial_ul').find(' > ul > li');
	
	
	$("#network_tree").on('mousewheel DOMMouseScroll', function(e){
	
        var network_tree = document.getElementById('network_tree');
        console.log(network_tree.scrollHeight+"  "+network_tree.scrollTop+"  "+network_tree.clientHeight);
        
   		if(currentCellsCount<lst.length && network_tree.scrollHeight - network_tree.scrollTop <= network_tree.clientHeight && children.is(":visible")){    
         
        	if((currentCellsCount+50)<lst.length){
        
	        	for (i=currentCellsCount; i < currentCellsCount + 50; i++) {
	        	
	           		 
					var str= "<ul><li class='Site' id='" +lst[i][1]+ "'> <span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+lst[i][0]+"</span></li></ul>";		 
					  			  		
			  		$("#initial_ul").append(str);
	            
	            }
	            currentCellsCount+=50;
            }
            else{
	            
	            for (i=currentCellsCount; i < lst.length-currentCellsCount; i++) {
	        		           		 
					var str= "<ul><li class='Site' id='" +lst[i][1]+ "'> <span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+lst[i][0]+"</span></li></ul>";		 
				 
			  		$("#initial_ul").append(str);
			  		
	            }
	        $("#loadmore").hide();
	        currentCellsCount+=lst.length-currentCellsCount;	               
	       }
	    console.log("currentCellsCount----->"+currentCellsCount); 
	            
       } 
        
            
       if(children.is(':visible') || currentCellsCount==lst.length){
            document.getElementById("loadmore").style.visibility = "visible";
            }
                
       else{
            document.getElementById("loadmore").style.visibility = "hidden";

            }
          
       });

	}
		



//Site Tree

function CreateTree_Site(lst){		

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li>	</ul>";
	$("#network_tree").append(str);
	 
	for (n = 0; n < lst.length; n++) {			// siteId, wareName

		 var str="";
		 
		 str += "<ul><li class='Site' id='" +lst[n][1]+ "' style='display:none'>";
		 
			  
			  str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+lst[n][0]+"</span></li></ul>";
		  		$("#initial_ul").append(str);
		  		}
		}


// Node Type --- Node

function CreateTree_NodeTypeNode(listNodesType,listNodes){

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Node Type</span></li>	</ul>";
	$("#network_tree").append(str); 
	 str = "";
							
	for (p=0;p<listNodesType.length;p++) {		

		str = "";		 
		str += "<ul><li class='NodeType' id='" +listNodesType[p]+ "' style='display:none'>";

			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs' ></i>"+listNodesType[p]+"</span></li></ul>";
			$("#initial_ul").append(str);
		
			str = "";
			    str ="<ul><li id='" +listNodesType[p]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul>";
			    $("#"+listNodesType[p]).append(str); 
		
	}

		for(j=0;j<listNodes.length;j++)//  NODE_PK,NODE_NAME,NODE_MODEL
		{
			str = "";
			console.log("-----Node Inside the Node typee");
			str += "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";
				
				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][1]+"</span></li></ul>";
				$("ul").find("#"+listNodes[j][2]+"_f").append(str);
			
		}
	

}



/*********************** Site---------NodeType-----Node -------Cell **********************/

function CreateTree_StNdTypNdCell(lst,listNodesType,listNodes,listCells){

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li>	</ul>";
	$("#network_tree").append(str); 


	 str = "";
								
	for (n = 0; n < lst.length; n++) {		

		str = "";		 
		str += "<ul><li class='Site' id='" +lst[n][2]+ "' style='display:none'>";

		if(lst[n][5]>0){
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+lst[n][1]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
		
			str = "";
			str="<ul><li id='" +lst[n][2]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Node Type </span></li></ul>";  	
		  
		  	$("#"+lst[n][2]).append(str);  
		}
		else{
			str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+lst[n][1]+"</span></li></ul>";
	  		$("#initial_ul").append(str);
	   	}
	}

	if(listNodes.length>0 && listNodesType.length>0){
		
	 for(p=0;p<listNodesType.length;p++) 
		{
				str = "";
				console.log("-----NodeTypee Inside the Sitee");
	
				str="<ul><li class='NodeType' id='" +listNodesType[p][0]+"_"+listNodesType[p][1]+"' style='display:none' class='folder'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";	
				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs' ></i>"+listNodesType[p][0]+"</span></li></ul>";
	
				$("ul").find("#"+listNodesType[p][1]+"_f").append(str);
	
				str = "";
			    str ="<ul><li id='" +listNodesType[p][0]+"_"+listNodesType[p][1]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul>";
			    $("#"+listNodesType[p][0]+"_"+listNodesType[p][1]).append(str);
		}
			
		console.log(""+listNodesType[p]);
	
		for(j=0;j<listNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
		{
			str = "";
			console.log("-----Node Inside the Node typee");
			str += "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";
			//console.log("lllllllllllllllllllllll"+lst[n][1]);
	
			if(listNodes[j][5]>0 || listNodes[j][6]>0 || listNodes[j][7]>0){
				
				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listNodes[j][3]+"_"+listNodes[j][4]+"_f").append(str);
	
				str = "";
				str ="<ul><li id='" + listNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
				$("#"+listNodes[j][0]).append(str);
							    
			}
			
			else{
				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listNodes[j][3]+"_"+listNodes[j][4]+"_f").append(str);
			}
		}
	
		for (k = 0; k < listCells.length; k++) {
			str = "";
			console.log("---------cell  Inside the Node ");
	
			str += "<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'>";
			str += "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>";
	
			$("ul").find("#"+listCells[k][2]+"_f").append(str);
			//  console.log("NNNNNoooooooodeeeeeee********"+listNodes[j][0]+"//////CCCCCCCeeeeeeeeellllllll"+listCells[k][0]);
		}
	}
}
	


//****************************************  Supp Site NodeType Node Cell  **************************************//


function CreateTree_SuppStNdTypNdCell(listSupp,listSuppSites,nodeTypeSuppResult,listSuppNodes,listCells){
	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Suppliers</span></li>	</ul>";
	$("#network_tree").append(str); 

	var str = "";
	
	for (n = 0; n < listSupp.length; n++) {			// siteId, wareName

		str = "";		 
		str += "<ul><li class='Supplier' id='"+listSupp[n][0]+"' style='display:none'>";

		//if(listSupp[n][2]>0){
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='far fa-copyright'></i>"+listSupp[n][1]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
		
			str = "";
			str="<ul><li id='"+listSupp[n][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul>";  	
		  
		  	$("#"+listSupp[n][0]).append(str);  
		/*}
		else{
			str += "<span class='tree-span' style='margin-left:21px;'><i class='far fa-copyright fa-2x'></i>"+listSupp[n][1]+"</span></li></ul>";
	  		$("#initial_ul").append(str);
	   	}*/
	}

	var listStiesInSupp=[];
	for (n = 0; n < listSuppSites.length; n++) {
		
		// if(!listStiesInSupp.includes(listSuppSites[n][1]+"_"+listSuppSites[n][4])){
			// listStiesInSupp.push(listSuppSites[n][1]+"_"+listSuppSites[n][4]); 
			str = "";		 
			str += "<ul><li class='Site' id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"' style='display:none'>";

		//if(listSuppSites[n][5]>0){
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][0]+"</span></li></ul>"; 
			$("#"+listSuppSites[n][4]+"_f").append(str);
		
			str = "";
			str="<ul><li id='"+listSuppSites[n][1]+"_"+listSuppSites[n][4]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Node Type </span></li></ul>";  	
		  
		  	$("#"+listSuppSites[n][1]+"_"+listSuppSites[n][4]).append(str);  
		/*}
		else{
			str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][1]+"</span></li></ul>";
	  		$("#"+listSuppSites[n][6]+"_f").append(str);
	   	}*/
	//}
}


	 for(p=0;p<nodeTypeSuppResult.length;p++) // Node model,Site ID
		{
				str = "";
				console.log("-----NodeTypee Inside the Sitee");
	
				str="<ul><li class='NodeType' id='"+nodeTypeSuppResult[p][0]+"_"+nodeTypeSuppResult[p][1]+"_"+nodeTypeSuppResult[p][2]+"' style='display:none' class='folder'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";	
				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs' ></i>"+nodeTypeSuppResult[p][0]+"</span></li></ul>";
	
				$("ul").find("#"+nodeTypeSuppResult[p][1]+"_"+nodeTypeSuppResult[p][2]+"_f").append(str);
	
				str = "";
			    str ="<ul><li id='" +nodeTypeSuppResult[p][0]+"_"+nodeTypeSuppResult[p][1]+"_"+nodeTypeSuppResult[p][2]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul>";
			    $("#"+nodeTypeSuppResult[p][0]+"_"+nodeTypeSuppResult[p][1]+"_"+nodeTypeSuppResult[p][2]).append(str);
		}


	


	 for(j=0;j<listSuppNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
		{
			str = "";
			console.log("-----Node Inside the Node typee");
			str += "<ul><li class='Node' id='" +listSuppNodes[j][0] +"' style='display:none' class='folder'>";
			//console.log("lllllllllllllllllllllll"+lst[n][1]);
	
			if(listSuppNodes[j][5]>0 || listSuppNodes[j][6]>0 || listSuppNodes[j][7]>0){
				
				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listSuppNodes[j][3]+"_"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
	
				str = "";
				str ="<ul><li id='" + listSuppNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
				$("#"+listSuppNodes[j][0]).append(str);
							    
			}
			
			else{
				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listSuppNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listSuppNodes[j][3]+"_"+listSuppNodes[j][4]+"_"+listSuppNodes[j][8]+"_f").append(str);
			}
		}


console.log("cccccccccccc"+listCells.length);
		for (k = 0; k < listCells.length; k++) {
			str = "";
			console.log("---------cell  Inside the Node ");
	
			str += "<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'>";
			str += "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>";
	
			$("ul").find("#"+listCells[k][2]+"_f").append(str);
			//  console.log("NNNNNoooooooodeeeeeee********"+listNodes[j][0]+"//////CCCCCCCeeeeeeeeellllllll"+listCells[k][0]);
		}

		
}





function CreateTree_StSupNdTpNdCell(listSuppSites,SuppList,nodeTypeStSuppResult,listNodes,listCells){
	

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li>	</ul>";
	$("#network_tree").append(str); 


	for (n = 0; n < listSuppSites.length; n++) {			// siteId, wareName

		 var str="";
		 
		 str += "<ul><li class='Site' id='" +listSuppSites[n][2]+ "' style='display:none'>";
		 
		  
		 // if(listSuppSites[n][5]>0 & listSuppSites[n][6]!=""){
			  
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][1]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
			str="";
		  	str+="<ul><li id='" +listSuppSites[n][2]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Suppliers </span></li></ul>";
		  
		  $("#"+listSuppSites[n][2]).append(str);
		  
		 /* }
		  else{
			  
			  str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][1]+"</span></li></ul>";
		  		$("#initial_ul").append(str);
		  		}*/
		}
	

	for (n = 0; n < SuppList.length; n++) {			

		str = "";		 
		str += "<ul><li class='Supplier' id='" +SuppList[n][0]+"_"+SuppList[n][1]+"' style='display:none'>";

		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='far fa-copyright'></i>"+SuppList[n][2]+"</span></li></ul>"; 
			$("#"+SuppList[n][0]+"_f").append(str);
		
			str = "";
			str="<ul><li id='"+SuppList[n][1]+"_"+SuppList[n][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Node type </span></li></ul>";  	
		  
		  	$("#"+SuppList[n][0]+"_"+SuppList[n][1]).append(str);  
		
	}

	 for(p=0;p<nodeTypeStSuppResult.length;p++) // Node model,Site ID
		{
				str = "";
				console.log("-----NodeTypee Inside the Sitee");
	
				str="<ul><li class='NodeType' id='" +nodeTypeStSuppResult[p][0]+"_"+nodeTypeStSuppResult[p][1]+"_"+nodeTypeStSuppResult[p][2]+"' style='display:none' class='folder'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";	
				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs' ></i>"+nodeTypeStSuppResult[p][0]+"</span></li></ul>";
	
				$("ul").find("#"+nodeTypeStSuppResult[p][2]+"_"+nodeTypeStSuppResult[p][1]+"_f").append(str);
	
				str = "";
			    str ="<ul><li id='"+nodeTypeStSuppResult[p][0]+"_"+nodeTypeStSuppResult[p][1]+"_"+nodeTypeStSuppResult[p][2]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul>";
			    $("#"+nodeTypeStSuppResult[p][0]+"_"+nodeTypeStSuppResult[p][1]+"_"+nodeTypeStSuppResult[p][2]).append(str);
		}


	 for(j=0;j<listNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
		{
			str = "";
			console.log("-----Node Inside the Node typee");
			str += "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";
			//console.log("lllllllllllllllllllllll"+lst[n][1]);
	
			if(listNodes[j][5]>0 || listNodes[j][6]>0 || listNodes[j][7]>0){
				
				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listNodes[j][3]+"_"+listNodes[j][4]+"_"+listNodes[j][8]+"_f").append(str);
	
				str = "";
				str ="<ul><li id='" + listNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
				$("#"+listNodes[j][0]).append(str);
							    
			}
			
			else{
				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listNodes[j][3]+"_"+listNodes[j][4]+"_"+listNodes[j][8]+"_f").append(str);
			}
		}

		for (k = 0; k < listCells.length; k++) {
			str = "";
			console.log("---------cell  Inside the Node ");
	
			str += "<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'>";
			str += "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>";
	
			$("ul").find("#"+listCells[k][2]+"_f").append(str);
		}

}


//*******************************************	Site NodeType Supplier Node Cell	*********************************************//

function CreateTree_StNdTpSupNdCell(suplistSites,sup_NodeTypeResult,sup_ListSupp,listNodes,listCells){


	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li>	</ul>";
	$("#network_tree").append(str); 

	var str = "";

	for (n = 0; n < suplistSites.length; n++) {			// siteId, wareName

		str = "";		 
		str += "<ul><li class='Site' id='" +suplistSites[n][2]+ "' style='display:none'>";

		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+suplistSites[n][1]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
		
			str = "";
			str="<ul><li id='" +suplistSites[n][2]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Node Type </span></li></ul>";  	
		  
		  	$("#"+suplistSites[n][2]).append(str);  
		
		
	}

	for(p=0;p<sup_NodeTypeResult.length;p++) 
	{
			

			//if(sup_NodeTypeResult[p][2]!=null){
			str = "";
			console.log("-----NodeTypee Inside the Sitee");

			str="<ul><li class='NodeType' id='" +sup_NodeTypeResult[p][0]+"_"+sup_NodeTypeResult[p][1]+"' style='display:none' class='folder'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";	
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs' ></i>"+sup_NodeTypeResult[p][0]+"</span></li></ul>";

			$("ul").find("#"+sup_NodeTypeResult[p][1]+"_f").append(str);
				
			str = "";
		    str ="<ul><li id='" +sup_NodeTypeResult[p][0]+"_"+sup_NodeTypeResult[p][1]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Suppliers </span></li></ul>";
		    $("#"+sup_NodeTypeResult[p][0]+"_"+sup_NodeTypeResult[p][1]).append(str);
			/*}
			else{		str = "";
			console.log("-----NodeTypee Inside the Sitee");

			str="<ul><li class='NodeType' id='" +sup_NodeTypeResult[p][0]+"_"+sup_NodeTypeResult[p][1]+"' style='display:none' class='folder'>";	
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fa fa-cogs' ></i>"+sup_NodeTypeResult[p][0]+"</span></li></ul>";

			$("ul").find("#"+sup_NodeTypeResult[p][1]+"_f").append(str);
				}*/
	}


	for (n = 0; n < sup_ListSupp.length; n++) {			

		str = "";		 
		str += "<ul><li class='Supplier' id='" +sup_ListSupp[n][0]+"_"+sup_ListSupp[n][3]+"' style='display:none'>";

		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='far fa-copyright'></i>"+sup_ListSupp[n][1]+"</span></li></ul>"; 
			$("#"+sup_ListSupp[n][2]+"_"+sup_ListSupp[n][3]+"_f").append(str);
		
			str = "";
			str="<ul><li id='"+sup_ListSupp[n][0]+"_"+sup_ListSupp[n][3]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul>";  	
		  
		  	$("#"+sup_ListSupp[n][0]+"_"+sup_ListSupp[n][3]).append(str);  
		
	}

	for(j=0;j<listNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
	{
		str = "";
		console.log("-----Node Inside the Node typee");
		str += "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";
		//console.log("lllllllllllllllllllllll"+lst[n][1]);

		if(listNodes[j][5]>0 || listNodes[j][6]>0 || listNodes[j][7]>0){
			
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
			$("ul").find("#"+listNodes[j][8]+"_"+listNodes[j][4]+"_f").append(str);

			str = "";
			str ="<ul><li id='" + listNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
			$("#"+listNodes[j][0]).append(str);
						    
		}
		
		else{
			str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
			$("ul").find("#"+listNodes[j][8]+"_"+listNodes[j][4]+"_f").append(str);
		}
	}

	for (k = 0; k < listCells.length; k++) {
		str = "";
		console.log("---------cell  Inside the Node ");

		str += "<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'>";
		str += "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>";

		$("ul").find("#"+listCells[k][2]+"_f").append(str);
	}

	}



function CreateTree_StSupNdCell(listSuppSites,SuppList,listNodes,listCells)
{


	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li>	</ul>";
	$("#network_tree").append(str); 


	for (n = 0; n < listSuppSites.length; n++) {			// siteId, wareName

		 var str="";
		 
		 str += "<ul><li class='Site' id='" +listSuppSites[n][2]+ "' style='display:none'>";
		 
		  
		 // if(listSuppSites[n][5]>0 & listSuppSites[n][6]!=""){
			  
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][1]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
			str="";
		  	str+="<ul><li id='" +listSuppSites[n][2]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Suppliers </span></li></ul>";
		  
		  $("#"+listSuppSites[n][2]).append(str);
		  
		 /* }
		  else{
			  
			  str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][1]+"</span></li></ul>";
		  		$("#initial_ul").append(str);
		  		}*/
		}
	

	for (n = 0; n < SuppList.length; n++) {			

		str = "";		 
		str += "<ul><li class='Supplier' id='" +SuppList[n][0]+"_"+SuppList[n][1]+"' style='display:none'>";

		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='far fa-copyright'></i>"+SuppList[n][2]+"</span></li></ul>"; 
			$("#"+SuppList[n][0]+"_f").append(str);
		
			str = "";
			str="<ul><li id='"+SuppList[n][1]+"_"+SuppList[n][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes</span></li></ul>";  	
		  
		  	$("#"+SuppList[n][0]+"_"+SuppList[n][1]).append(str);  
		
	}


	 for(j=0;j<listNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
		{
			str = "";
			console.log("-----Node Inside the Node typee");
			str += "<ul><li class='Node' id='" + listNodes[j][0] +"' style='display:none' class='folder'>";
			//console.log("lllllllllllllllllllllll"+lst[n][1]);
	
			if(listNodes[j][5]>0 || listNodes[j][6]>0 || listNodes[j][7]>0){
				
				str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
				str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listNodes[j][8]+"_"+listNodes[j][4]+"_f").append(str);
	
				str = "";
				str ="<ul><li id='" + listNodes[j][0] +"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Cells </span></li></ul>";
				$("#"+listNodes[j][0]).append(str);
							    
			}
			
			else{
				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[j][2]+"</span></li></ul>";
				$("ul").find("#"+listNodes[j][8]+"_"+listNodes[j][4]+"_f").append(str);
			}
		}

		for (k = 0; k < listCells.length; k++) {
			str = "";
			console.log("---------cell  Inside the Node ");
	
			str += "<ul><li class='Cell' id='" + listCells[k][0] + "' style='display:none' class='folder'>";
			str += "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listCells[k][1]+"</span></li></ul>";
	
			$("ul").find("#"+listCells[k][2]+"_f").append(str);
		}
	
	
}

function CreateTree_AreaSite(areaList,listAreaSites){


	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Areas</span></li>	</ul>";
	$("#network_tree").append(str); 


	for (n = 0; n < areaList.length; n++) {			// siteId, wareName

		 var str="";
		 
		 str += "<ul><li class='Area' id='" +areaList[n][0]+ "' style='display:none'>";
		 
		  
		 // if(listSuppSites[n][5]>0 & listSuppSites[n][6]!=""){
			  
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-map'></i>"+areaList[n][1]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
			str="";
		  	str+="<ul><li id='" +areaList[n][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Sites </span></li></ul>";
		  
		  $("#"+areaList[n][0]).append(str);
		  
		 /* }
		  else{
			  
			  str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSuppSites[n][1]+"</span></li></ul>";
		  		$("#initial_ul").append(str);
		  		}*/
		}
	


	for (n = 0; n < listAreaSites.length; n++) {			// siteId, wareName

		 var str="";
		 
		 str += "<ul><li class='Site' id='" +listAreaSites[n][1]+ "' style='display:none'>";
		 
		  
		 // if(listSuppSites[n][5]>0 & listSuppSites[n][6]!=""){
			  
			
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listAreaSites[n][0]+"</span></li></ul>"; 
			$("#"+listAreaSites[n][4]+"_f").append(str);
			
		
		}
	
	
}

/*
function CreateTree_POStItem(listPO,listSites,listItem){		

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> PO </span></li>	</ul>";
	$("#network_tree").append(str);
	 
	for (n = 0; n < listPO.length; n++) {			// POId

		 var str="";
		 
		 str += "<ul><li class='PO' id='"+listPO[n]+"' style='display:none'>";
		 
		  	str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
            
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listPO[n]+"</span></li></ul>"; 
			
			$("#initial_ul").append(str);
			str="";
            
		  	str+="<ul><li id='"+listPO[n]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Site </span></li></ul>";
		  
		  $("#"+listPO[n]).append(str);
		  

		}


	for (j = 0; j < listSites.length; j++) {
			str="";			
				
                    //Site ID 0
					str += "<ul><li class='Site' id='" +listSites[j][0]+ "_" + listSites[j][2] +"' style='display:none' class='folder'>";			
                    
					str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
                    //Site Name 1
					str+= "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listSites[j][1]+"</span></li></ul>";
                    //Site ID 0
					$("ul").find("#"+listSites[j][2]+"_f").append(str);
					str="";
				 	str="<ul><li id='" + listSites[j][2] + "_" + listSites[j][0] + "_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Item </span></li></ul>";
				    $("#" + listSites[j][0] + "_" + listSites[j][2] ).append(str);
				}

															
	for (k = 0; k < listItem.length; k++) { 
    //ItemID 0, ItemName 1, Site ID 2,PO_ID3

		 	str="";

			console.log("---------item  Inside the Sites ");
			str+= "<ul><li class='Item' id='" + listItem[k][0] + "' style='display:none' class='folder'>";
			str+= "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listItem[k][1]+ "," + listItem[k][4] + "</span></li></ul>";
			$("ul").find("#"+listItem[k][3]+"_"+listItem[k][2] + "_f").append(str);
				//  console.log("Sites********"+listSites[j][0]+"//////Item"+listItem[k][0]);
			}																													 
			 
}

*/
function CreateTree_StPOItem(listSites,listPO,listItem){		

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> Sites</span></li>	</ul>";
	$("#network_tree").append(str);
	 
	for (n = 0; n < listSites.length; n++) {			//SITE_ID 0, Site Name 1, PO, Item 2

		 var str="";
		 //ID
		 str += "<ul><li class='Site' id='" +listSites[n][0]+ "' style='display:none'>";
		 
		  
		  if(listSites[n][2]>0){
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
            //Name
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSites[n][1]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
			str="";
            //ID
		  	str+="<ul><li id='" +listSites[n][0]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> PO </span></li></ul>";
		  //ID
		  $("#"+listSites[n][0]).append(str);
		  
		  }
		  else{
			  //Name
			  str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-crosshairs fa-2x'></i>"+listSites[n][1]+"</span></li></ul>";
		  		$("#initial_ul").append(str);
		  		}
		}


	for(j=0;j<listPO.length;j++){//  POID 0, Site ID 1, Item 2
			str="";			
				console.log("-----Items Inside POID");
                    //POID 0
					str += "<ul><li class='PO' id='" + listPO[j][0] + "_" + listPO[j][1] +"' style='display:none' class='folder'>";			
                   
					str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
                    //PO Name 1
					str+= "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listPO[j][0]+"</span></li></ul>";
                    //POID 0
					$("ul").find("#"+listPO[j][1]+"_f").append(str);
					str="";
				 	str="<ul><li id='" + listPO[j][1] + "_" + listPO[j][0] + "_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Item </span></li></ul>";
				    $("#"+ listPO[j][0] + "_" + listPO[j][1] ).append(str);
					
				}

															
	for (k = 0; k < listItem.length; k++) { //ItemID 0, ItemName 1, POID 2,SITEID 3

		 	str="";
			console.log("---------item  Inside the POID ");
			str+= "<ul><li class='Item' id='" + listItem[k][0] + "' style='display:none' class='folder'>";
			str+= "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listItem[k][1]+ "," + listItem[k][4] + "</span></li></ul>";
			$("ul").find("#"+listItem[k][3]+"_"+listItem[k][2] + "_f").append(str);
				//  console.log("PO********"+listPO[j][0]+"//////Item"+listItem[k][0]);
			}																													 
			 
}


function CreateTree_POItemSt(listPO,listItem,listSites){		

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> PO </span></li>	</ul>";
	$("#network_tree").append(str);
	 
	for (n = 0; n < listPO.length; n++) {			// POId

		 var str="";	 
		 str += "<ul><li class='PO' id='"+listPO[n]+"' style='display:none'>";
		  	str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listPO[n]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
			str="";
		  	str+="<ul><li id='"+listPO[n]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Items </span></li></ul>";
		  $("#"+listPO[n]).append(str);
		}


	for (j = 0; j < listItem.length; j++) {
			str="";			
				console.log("-----Items Inside POs");
                    //Item ID 0
					str += "<ul><li class='Item' id='" + listItem[j][0] + "_" + listItem[j][2] +"' style='display:none' class='folder'>";			                
					str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
                    //Item Name 1,Item Model 3
					str+= "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listItem[j][1]+ "," + listItem[j][3] +"</span></li></ul>";
                    //PO ID 0
					$("ul").find("#"+listItem[j][2]+"_f").append(str);
					str="";
				 	str="<ul><li id='" + listItem[j][2] + "_" + listItem[j][0] + "_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Site </span></li></ul>";
				    $("#" + listItem[j][0] + "_" + listItem[j][2] ).append(str);
				}

															
	for (k = 0; k < listSites.length; k++) { 
    //SITEID 0, SITEName 1, ITEMMODEL 2,PO_ID3

		 	str="";

			console.log("---------Sites  Inside the Item  ");
			str+= "<ul><li class='Site' id='" + listSites[k][0] + "' style='display:none' class='folder'>";
			str+= "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listSites[k][1]+"</span></li></ul>";
			$("ul").find("#"+listSites[k][3]+"_"+listSites[k][2] + "_f").append(str);
				
			}																													 
			 
}



function CreateTree_POItStNdtpNd(listPO,listItem,listSites,listNodesType,listNodes){
	

	var str="<ul>	<li id='initial_ul' class='folder'><span class='folder'><i class='fa fa-folder' style='color: #08526D'></i> PO </span></li>	</ul>";
	$("#network_tree").append(str);
	 
	for (n = 0; n < listPO.length; n++) {			// POId

		for (n = 0; n < listPO.length; n++) {			// POId

		 var str="";	 
		 str += "<ul><li class='PO' id='"+listPO[n]+"' style='display:none'>";
		  	str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-crosshairs fa-2x'></i>"+listPO[n]+"</span></li></ul>"; 
			$("#initial_ul").append(str);
			str="";
		  	str+="<ul><li id='"+listPO[n]+"_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Items </span></li></ul>";
		  $("#"+listPO[n]).append(str);
		}}


	for (j = 0; j < listItem.length; j++) {
			str="";			
				console.log("-----Site Inside Item");
                    //Site ID 0
					str += "<ul><li class='Item' id='" + listItem[j][0] + "_" + listItem[j][2] +"' style='display:none' class='folder'>";			
                    
					str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
                    //Item Name 1
					str+= "<span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+listItem[j][1]+ "," + listItem[j][3] +"</span></li></ul>";
                    //Item ID 0
					$("ul").find("#"+listItem[j][2]+"_f").append(str);
					str="";
				 	str="<ul><li id='" + listItem[j][2] + "_" + listItem[j][0] + "_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Site </span></li></ul>";
				    $("#" + listItem[j][0] + "_" + listItem[j][2] ).append(str);
				}

															
	for (k = 0; k < listSites.length; k++) { 
    //SITEID 0, SITEName 1, ITEMMODEL 2,PO_ID3

            
		 	str="";
            console.log("-----NodeType Inside Sites");
            
			str+= "<ul><li class='Site' id='" + listSites[k][2] + "_" + listSites[k][0] + "' style='display:none' class='folder'>";
            str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str+= "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listSites[k][1]+"</span></li></ul>";
			$("ul").find("#"+listSites[k][3]+"_"+listSites[k][2] + "_f").append(str);
            
            str="";
				 	str="<ul><li id='" + listSites[k][0] + "_" + listSites[k][2] + "_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Node Type </span></li></ul>";
				    $("#" + listSites[k][2] + "_" + listSites[k][0] ).append(str);

			}	
            
            for (l = 0; l < listNodesType.length; l++) { 
    //NdTypeModel 0, SITEName 1, Ware ID, Item Code 2
   
		 	str="";
            console.log("-----NodeType Inside Sites");
            
			str+= "<ul><li class='NodeType' id='" + listNodesType[l][0] + "_" + listNodesType[l][1] + "' style='display:none' class='folder'>";
            str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str+= "<span class='tree-span' ><i class='fas fa-vector-square fa-2x'></i>"+listNodesType[l][0]+"</span></li></ul>";
			$("ul").find("#"+listNodesType[l][1]+"_"+listNodesType[l][2] + "_f").append(str);
            
            str="";
				 	str="<ul><li id='" + listNodesType[l][0] + "_f' style='display:none'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i> Nodes </span></li></ul>";
				    $("#" + listNodesType[l][0] + "_" + listNodesType[l][1] ).append(str);

			}
            
            for(p=0;p<listNodes.length;p++)//  NODE_PK,NODE_NAME,NODE_MODEL
		{
			str = "";
			console.log("-----Node Inside the Node typee");
			str += "<ul><li class='Node' id='" + listNodes[p][2] +"' style='display:none' class='folder'>";
				
				str += "<span class='tree-span' style='margin-left:21px;'><i class='fas fa-server'></i>"+listNodes[p][0]+"</span></li></ul>";
				$("ul").find("#" +listNodes[p][1] + "_f").append(str);
			
		}
			 
}




