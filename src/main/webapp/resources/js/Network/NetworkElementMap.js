//////////////////////////////////////////////
/*         Map Section Network              */
//////////////////////////////////////////////



//Restore Button
function DefaultZoomControl(controlDiv2, map) {
			    const controlUI2 = document.createElement("div");
			    controlUI2.style.backgroundColor = "white";
			    controlUI2.style.border = "8px solid white";
			    controlUI2.style.borderRadius = "3px";
			    controlUI2.style.boxShadow = "0 2px 4px rgba(0,0,0,0.298039)";
			    controlUI2.style.cursor = "pointer";
			    controlUI2.style.marginLeft = "10.2px";
			    controlUI2.style.marginTop = "-8px";
			    controlUI2.style.width = "40px";
			 		    
			    controlUI2.innerHTML = '<button style="border:none;outline:none; background:white;font-size:13px;color:#585858" ><i class="fa fa-undo fa-lg "></i></button>';
			    controlUI2.title = "Reset Default Zoom";
			    controlDiv2.appendChild(controlUI2);
			    controlUI2.addEventListener("click", () => {			    	
 				map.setZoom(6);
 				var Nairobi=new google.maps.LatLng(0.796530,37.959529); 				
				map.setCenter(Nairobi);	        
			              });			  
			  }	
function LatLanMouse(map){		  
//mouse lat long
				map.addListener("mousemove", (mapsMouseEvent) => {					
				    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
				    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));	    
				  }); 
}

$(document).ready(function() {
	console.log("controlDiv2");
	const controlDiv2 = document.createElement("div");
	console.log("DefaultZoomControl");
	DefaultZoomControl(controlDiv2, map);
	console.log("Before map controls");
	map.controls[google.maps.ControlPosition.LEFT_CENTER].push(controlDiv2);
	console.log("In the end of ready");
});



function CreateMap(lst,map){	
	//console.log("create map");
	var start = performance.now();
	panPath = [];   // An array of points the current panning action will use
	panQueue = [];  // An array of subsequent panTo actions to take
	STEPS = 80;
	markersSites=[];
	markerClusterSites = new MarkerClusterer();
	markerClusterSites.setMap(map); // to be checked !!!!
	LatLanMouse(map);
	iconSite = {
		//url: getContext()+"/resources/clusterIcons/blueCluster.png", // url
		//scaledSize: new google.maps.Size(20, 20), // scaled size
			path: google.maps.SymbolPath.CIRCLE,
	        fillOpacity: 0.9,
	        strokeColor: 'transparent',
	        strokeOpacity: 0.9,
	        strokeWeight: 1,
	        scale: 7,
	        fillColor:'blue'
	};

	markerClusterSites.setOptions( {					  					
		minimumClusterSize: 2,
		styles: [
		         {
		        	 url: getContext()+'/resources/clusterIcons/blueCluster.png',
			         height: 60,
			         width:60,
			         anchorText:[-3,-3]
			      },
		],
		calculator: function(markersSites, numStyles) {
		if (markersSites.length >= 1) return {text: markersSites.length, index: 1}; // red
		}                   
	});

	var end = performance.now();
	
	 var start2 = performance.now();
			map.setZoom(6);
			var Nairobi=new google.maps.LatLng(0.796530,37.959529); 				
			map.setCenter(Nairobi);
			//LatLanMouse(map);
			//markerClusterSites.setMap(map);
			
		if(lst!=null){
			//console.log("lst in map>> ", lst);
			str="";
			for(i=0;i<lst.length;i++){
				//markerId=lst[i][2];	
				infowindow = new google.maps.InfoWindow();
				//var lat=lst[i][3];
				//var lng=lst[i][4];
				const pos = new google.maps.LatLng(lst[i][3],lst[i][4]);
				//var siteName=lst[i][1];
				var siteName="<b style='font-size:13px;'><u>Site: </u></b>  "+lst[i][1];
				
				if (lst[i][2].includes("WARE")) {
			        //console.log("Found 'WARE'");
			        var listNodesCount="<b style='font-size:13px;'> Number of Nodes: </b>"+lst[i][5];
					var listCellsCount=lst[i][6]+lst[i][7]+lst[i][8];
					listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+listCellsCount;
					var data="<div>"+siteName+"</br>"+listNodesCount+"</br>"+listCellsCount+"</div>";
				 }else if (lst[i][2].includes("NODE")) {
			       // console.log("Found 'NODE'");
			        var nodeType= "<b style='font-size:13px;'><u>Node Type: </u></b>  "+lst[i][11];	
			        var nodeName= "<b style='font-size:13px;'><u>Node Name: </u></b>  "+lst[i][10];	
					var listCellsCount=lst[i][6]+lst[i][7]+lst[i][8];
					listCellsCount="<b style='font-size:13px;'>Number of Cells: </b>"+listCellsCount;
					var data="<div>"+siteName+"</br>"+nodeType+"</br>"+nodeName+"</br>"+listCellsCount+"</div>";
				 } else if (lst[i][2].includes("CELL")) {
			        //console.log("Found 'CELL'");
			        var nodeName= "<b style='font-size:13px;'><u>Node Name: </u></b>  "+lst[i][12];	
			        var cellName= "<b style='font-size:13px;'><u>Cell Name: </u></b>  "+lst[i][10];	
					var technology= "<b style='font-size:13px;'><u>Technology: </u></b>  "+lst[i][2].split("_")[1];	
					var data="<div>"+siteName+"</br>"+nodeName+"</br>"+cellName+"</br>"+technology+"</div>";
				}	
				create_Marker_Click(lst[i][2],siteName,lst[i][4],lst[i][3],markersSites,markerClusterSites,data);
			}
		}
		var end2 = performance.now();
}
function create_Marker_Click(Id,Name,Long,Lat,markers,marker_Cluster,data) {
	//console.log("create merker click");
	const pos = new google.maps.LatLng(Lat,Long);
	//var mapIcon;
	// if(markers == markersSites && marker_Cluster == markerClusterSites) {
	//			 mapIcon = iconSite;
	//		 }
//	 if(!markers[Id]){	
		 var marker = new google.maps.Marker({
			position: pos,
			data: data,
			icon: iconSite,
			title: Name,
			ID: Id,
			map: null,
			color:'blue',
		});
		markers.metadata = { id: Id };
		markers[Id] = marker;
		markers.push(marker);
		//console.log("markers >>>>", markers);
		
		google.maps.event.addListener(marker, "click", function (e) {
			console.log("---clicked---");
			var IdSelected = this.ID;
			window.infowindow.setContent(this.data);
	        window.infowindow.open(map, this);
	    	console.log("IdSelected-------"+IdSelected);
	        
	        var liElement = $("#network_tree").find('li[id*="' + IdSelected + '"]'); // Select the <li> element with the ID IdSelected
	    	//console.log("liElement-------",liElement);
	        var spanElement = liElement.find('.TreeSpan')[0];
	       // console.log("spanElement-------",spanElement);
	        
	        $("#initial_ul > .folder > svg").addClass('fa-folder-open').removeClass('fa-folder'); 
	    	$("#initial_ul").find(' > ul > li').show();
	        
	        liElement.closest('.NodeType').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');
	        liElement.closest('.NodeType').find(' > ul > li').show();
		    
	        liElement.closest('.NodeType').find('.NodeTypeFolder').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.NodeType').find('.NodeTypeFolder').find(' > ul > li').show();
		    
	     //Supplier pages
		    liElement.closest('.Supplier').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');
	        liElement.closest('.Supplier').find(' > ul > li').show();
		   
	        liElement.closest('.Supplier').find('.SiteFolder').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.Supplier').find('.SiteFolder').find(' > ul > li').show();
	     
	        liElement.closest('.Supplier').find('.NodeTypeFolder').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.Supplier').find('.NodeTypeFolder').find(' > ul > li').show();
	        
	        liElement.closest('.Supplier').find('.NodeFolder').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.Supplier').find('.NodeFolder').find(' > ul > li').show();
	        
	       // PO pages 
	        liElement.closest('.PO').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.PO').find(' > ul > li').show();
	        
	        liElement.closest('.PO').find('.SiteFolder').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.PO').find('.SiteFolder').find(' > ul > li').show();
	        
	        liElement.closest('.PO').find('.Item').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.PO').find('.Item').find(' > ul > li').show();
	        
	        liElement.closest('.PO').find('.Items').find('> .folder > svg').removeClass('fa fa-folder').addClass('fa-folder-open');	
	        liElement.closest('.PO').find('.Items').find(' > ul > li').show();
	        
    		$(".TreeSpan").removeClass("selected-span").css("background", "");
    		// $("#" + IdSelected + " > .TreeSpan").addClass("selected-span").css("background-color", "#97b9cc");	
    		$(spanElement).addClass("selected-span").css("background-color", "#97b9cc");	
    		 offset=$(spanElement).offset().top;
 			 projectOffset=$("#initial_ul").offset().top;
 			 offsetTotal=(offset-projectOffset);
 			$("#network_tree").animate({ scrollTop: offsetTotal}, "slow");
	    	//}
	    	/*
	    	if(liClass=="Site"){
	    		console.log("site");
	 		    $("#" + IdSelected + " > .TreeSpan").addClass("selected-span").css("background-color", "#97b9cc");	
	 				offset=$("#"+IdSelected).offset().top;
	 				projectOffset=$("#initial_ul").offset().top;
	 				offsetTotal=(offset-projectOffset);
	           	  //  var container = $('div#network_tree'),
	 		      //  scrollTo = $("#"+IdSelected);
	           	    $("#network_tree").animate({ scrollTop: offsetTotal}, "slow");	
	    	}else{
	    		console.log("single site");
	    		var siteElement = $("#network_tree").find('li .SingleSite');
	    		var siteElementSup = $("#network_tree").find('li .SingleSiteSupp');
			    var hi;
			    var hiSup;
			    
			    if(siteElementSup.length===0){
			    	console.log("siteElementSup.length===0");
			    	for(var i=0; i<siteElement.length;i++){	    	
				    	var nodeType = siteElement[i].id.split("_").slice(-1).join("_");	
				    	var selectedItem = siteElement[i].id.slice(0, siteElement[i].id.indexOf("_" +nodeType)); 		    	
				    	if(selectedItem==IdSelected){
				    		hi=siteElement[i].id;
				    	}
				    }	
			    	$("#" + hi + " >.TreeSpan").addClass("selected-span").css("background-color", "#97b9cc");
	               	 var offset = $("#"+hi).offset().top;
	                 var projectOffset = $("#initial_ul").offset().top;
	                 var offsetTotal = offset - projectOffset;
	              //   var container = $("div#network_tree");
	              //   var scrollTo = $("#"+hi);;
	                 $("#network_tree").animate({ scrollTop: offsetTotal }, "slow");	
	                 
			    }
			    if(siteElement.length===0){		 
			    	console.log("siteElement.length===0");
			    	for(var i=0; i<siteElementSup.length;i++){	    	
				    	if (siteElementSup[i].id.includes("SUPP")) { //WARE_SUPP
					    	var supp = siteElementSup[i].id.split("_").slice(-3).join("_");	
					    	var selectedItem = siteElementSup[i].id.slice(0, siteElementSup[i].id.indexOf("_" + supp));
					    	if(selectedItem == IdSelected){
					    		hiSup=siteElementSup[i].id;
					    	}
				    	}else{ //WARE_NODETYPE
				    		var nodeType= siteElementSup[i].id.split("_").slice(-2, -1)[0];
					    	var selectedItem = siteElementSup[i].id.slice(0, siteElementSup[i].id.indexOf("_" + nodeType));
					    	if(selectedItem == IdSelected){
					    		hiSup=siteElementSup[i].id.split("_SingleSite")[0];
					    	}
				    	}			    	
			    }                
	                 $("#" + hiSup + " >.TreeSpan").addClass("selected-span").css("background-color", "#97b9cc");
	               	 var offset = $("#"+hiSup).offset().top;
	                 var projectOffset = $("#initial_ul").offset().top;
	                 var offsetTotal = offset - projectOffset;
	              //   var container = $("div#network_tree");
	              //   var scrollTo = $("#"+hi);;
	                 $("#network_tree").animate({ scrollTop: offsetTotal }, "slow");
		//	    }
	    //	}			               
		 });				
	// } 
	*/
	 /*
								else{
									if(markers[Id].map!=map){
										markers[Id].setMap(map);
									}
									markers[Id].setPosition(pos);
									markers[Id].data=data;
								}
	*/
		});
				}


/*
function SitesCheckFilter(Id) {
    $("#network_tree").find("input:checked").each(function () {
        if ($(this).hasClass('SingleSite')) {
        	if ($(this).is(":checked") ) {
        		if (markersSites[Id] && !markersSites[Id].getMap()) {
                    if (markersSites[Id]) {
                        markersSites[Id].setMap(map);
                        markerClusterSites.addMarker(markersSites[Id]);
                        markerClusterSites.repaint();
                    }
        		}
        	}
        }	   
    });
}
*/