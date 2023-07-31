var selectedItems=[];
var txt;
var AllSitesArr=[];
var checked;
var warehouseSitesList =[];
var warehouseSites=[];
var warehousePrepaidSites =[];
var selectedSiteData =[];
var allWareSites =[];
var oldAllSitesName=[];
Two_g_list=[];
Three_g_list=[];
four_g_list=[];
maxRevenueList=[];
minRevenueList=[];   

//Add legend button under zoom control on map
function LegendControl(controlDiv, map) {
	
    const controlUI = document.createElement("dv");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
   // controlUI.style.borderRadius = "3px";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"  onClick="ShowHideDiv()"><i class="fas fa-arrow-right fa-lg "></i></button>';
    controlUI.title = "Open Legend";
    controlDiv.appendChild(controlUI);

  }
  
 //Add defaultZoom button under zoom control on map
function DefaultZoomControl(controlDiv2, map) {
	
    const controlUI2 = document.createElement("div");
    controlUI2.style.backgroundColor = "white";
    controlUI2.style.border = "8px solid white";
    controlUI2.style.cursor = "pointer";
    controlUI2.style.marginLeft = "10px";
    controlUI2.style.marginTop = "10px";
    controlUI2.innerHTML = '<button style="border:none;outline:none; background:white;" ><i class="fa fa-undo fa-lg "></i></button>';
    controlUI2.title = "Reset Default Zoom";
    controlDiv2.appendChild(controlUI2);

    controlUI2.addEventListener("click", () => {

    	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
        map.setCenter(Nairobi);
        map.setZoom(6);
        
        if (infoWindow) {
            infoWindow.close();
        }  
        if (infoWindow2) {
            infoWindow2.close();
        }       
        
              });

  }
  
  
  
  
  var infoWindow ;
function MaxRevenueControl(controlDiv3, map) {
	
	 const controlUI3 = document.createElement("div");
	 controlUI3.style.backgroundColor = "white";
	 controlUI3.style.border = "8px solid white";
	 controlUI3.style.boxShadow = "0 5px 6px rgba(0,0,0,.3)";
	 controlUI3.style.cursor = "pointer";
	 controlUI3.style.marginTop = "11px";
	 controlUI3.style.marginLeft = "5px";
	 controlUI3.style.height = "38px";
	 controlUI3.title = "Go to max revenue site";
	 controlDiv3.appendChild(controlUI3);


    const controlText = document.createElement("div");
    controlText.style.color = "rgb(25,25,25)";
    controlText.style.fontFamily = "Roboto,Arial,sans-serif";
    controlText.style.fontSize = "2px";
    controlText.style.lineHeight = "3px";
    controlText.style.paddingLeft = "5px";
    controlText.style.paddingRight = "5px";
    controlUI3.innerHTML = '<button style="border:none;outline:none; background:white;fontFamily:Roboto,Arial,sans-serif;font-Size:15px" >Maximum Revenue</button>';
    controlUI3.appendChild(controlText);
    
    controlUI3.addEventListener("click", () => {

    	 if (infoWindow) {
             infoWindow.close();
         }  
			
        if(maxRev.length >0) {            
        	var latMaxSite=maxRev[2];
    		var lngMaxSite=maxRev[1];
            const maxSiteposition = new google.maps.LatLng(latMaxSite,lngMaxSite);
            var siteName="<b style='font-size:13px;'><u>Site: </u></b>"+maxRev[0];
    		var siteLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+maxRev[2];
    		var siteLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+maxRev[1];
    		var siteRev="<b style='font-size:13px;'><u>Revenue:</u> </b>"+maxRev[7];
    		 infoWindow = new google.maps.InfoWindow();

             //Set Content of InfoWindow.
             infoWindow.setContent( siteLat + '<br />' + siteLong + '<br />' +siteName+ '<br />' +siteRev);
             
                
         if  ($('#legendMaxRevenue').is(':checked') == true ) {
             
	  		map.setZoom(15);
	       	map.setCenter(maxSiteposition);
	      //Set Position of InfoWindow.
	        infoWindow.setPosition(maxSiteposition);
	        infoWindow.open(map);       	 

         }
         
         
         if  ($('#enable').is(':checked') == true ) {
            if  ($('#blue').is(':checked') == true ) {
       
                map.setZoom(15);
                map.setCenter(maxSiteposition);
                //Set Position of InfoWindow.
                infoWindow.setPosition(maxSiteposition);
                infoWindow.open(map);
         	     	
         }
            else if  ($('#yellow').is(':checked') == true ) {
       
                map.setZoom(15);
                map.setCenter(maxSiteposition);
                //Set Position of InfoWindow.
                infoWindow.setPosition(maxSiteposition);
                infoWindow.open(map);
         	     	
         }
           else  if  ($('#red').is(':checked') == true ) {
       
                map.setZoom(15);
                map.setCenter(maxSiteposition);
                //Set Position of InfoWindow.
                infoWindow.setPosition(maxSiteposition);
                infoWindow.open(map);
         	     	
         }
            else if  ($('#pink').is(':checked') == true ) {
       
                map.setZoom(15);
                map.setCenter(maxSiteposition);
                //Set Position of InfoWindow.
                infoWindow.setPosition(maxSiteposition);
                infoWindow.open(map);
         	     	
         }
           else  if  ($('#purple').is(':checked') == true ) {
       
                map.setZoom(15);
                map.setCenter(maxSiteposition);
                //Set Position of InfoWindow.
                infoWindow.setPosition(maxSiteposition);
                infoWindow.open(map);
         	     	
         }
         }
    		
        }		
      });
    
  }

var infoWindow2;
function MinRevenueControl(controlDiv4, map) {
	
    const controlUI4 = document.createElement("div");
    controlUI4.style.backgroundColor = "white";
    controlUI4.style.border = "8px solid white";
   // controlUI4.style.borderRadius = "3px";
    controlUI4.style.boxShadow = "0 5px 6px rgba(0,0,0,.3)";
    controlUI4.style.cursor = "pointer";
    controlUI4.style.marginLeft = "5px";
    controlUI4.style.marginTop = "11px";
    controlUI4.style.height = "38px";
    controlUI4.title = "Go to min revenue site";
    controlDiv4.appendChild(controlUI4);


    const controlText = document.createElement("div");
    controlText.style.color = "rgb(25,25,25)";
    controlText.style.fontFamily = "Roboto,Arial,sans-serif";
    controlText.style.fontSize = "2px";
    controlText.style.lineHeight = "3px";
    controlText.style.paddingLeft = "5px";
    controlText.style.paddingRight = "5px";
    controlUI4.innerHTML = '<button style="border:none;outline:none; background:white;fontFamily:Roboto,Arial,sans-serif;font-Size:15px" >Minimum Revenue</button>';
    controlUI4.appendChild(controlText);
    

    controlUI4.addEventListener("click", () => {
    	 if (infoWindow2) {
             infoWindow2.close();
         }  
        
    	 if(minRev.length >0) {
    	 
    	 	var latMinSite=minRev[2];
    		var lngMinSite=minRev[1];
    	    const minSiteposition = new google.maps.LatLng(latMinSite,lngMinSite);
    		var siteName="<b style='font-size:13px;'><u>Site: </u></b>"+minRev[0];
    		var siteLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+minRev[2];
    		var siteLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+minRev[1];
    		var siteRev="<b style='font-size:13px;'><u>Revenue:</u> </b>"+minRev[7];
    		
    		infoWindow2 = new google.maps.InfoWindow();
    		//Set Content of InfoWindow.
    	    infoWindow2.setContent( siteLat + '<br />' + siteLong + '<br />' +siteName+ '<br />' +siteRev);
    	 


             if  ($('#legendMinRevenue').is(':checked') == true ) {
            	map.setZoom(15);
    	     	map.setCenter(minSiteposition);
    	       //Set Position of InfoWindow.
    	       infoWindow2.setPosition(minSiteposition);
    	       infoWindow2.open(map);
     	        }	
     	           
         if  ($('#enable').is(':checked') == true ) {
            if  ($('#blue').is(':checked') == true ) {
       
               map.setZoom(15);
    	     	map.setCenter(minSiteposition);
    	       //Set Position of InfoWindow.
    	       infoWindow2.setPosition(minSiteposition);
    	       infoWindow2.open(map);
         	     	
         }
             else if  ($('#yellow').is(':checked') == true ) {
       
               map.setZoom(15);
    	     	map.setCenter(minSiteposition);
    	       //Set Position of InfoWindow.
    	       infoWindow2.setPosition(minSiteposition);
    	       infoWindow2.open(map);
         	     	
         }
            else if  ($('#pink').is(':checked') == true ) {
       
               map.setZoom(15);
    	     	map.setCenter(minSiteposition);
    	       //Set Position of InfoWindow.
    	       infoWindow2.setPosition(minSiteposition);
    	       infoWindow2.open(map);
         	     	
         }
           else  if  ($('#red').is(':checked') == true ) {
       
               map.setZoom(15);
    	     	map.setCenter(minSiteposition);
    	       //Set Position of InfoWindow.
    	       infoWindow2.setPosition(minSiteposition);
    	       infoWindow2.open(map);
         	     	
         }
            else if  ($('#purple').is(':checked') == true ) {
       
               map.setZoom(15);
    	     	map.setCenter(minSiteposition);
    	       //Set Position of InfoWindow.
    	       infoWindow2.setPosition(minSiteposition);
    	       infoWindow2.open(map);
         	     	
         }
         }
     	      
    	 }
      });
    
  


  }
  
 
function resetToDefault(){

	var rowCount2 = revenueColorTable.rows.length;
		 
 	if ( rowCount2 ==5  && rowCount ==4 ) {
 		revenueColorTable.deleteRow(4);
 	}
 	
	else if ( rowCount2 ==6 && rowCount ==5 ) {
   	 revenueColorTable.deleteRow(5);
   	 revenueColorTable.deleteRow(4);
   	 }
   	 
   	 else if ( rowCount2 >=6 && rowCount >5 ) {
		revenueColorTable.deleteRow(5);
	   	revenueColorTable.deleteRow(4);
	 }
	
	else if ( rowCount2 ==3 || rowCount2 ==2 || rowCount2 ==1) {
		document.getElementById("revTable").innerHTML ="";
		document.getElementById("revTable").innerHTML = divRevTable ;
	}
	
	$('#revenueColorTable').find('input').each(function(){
		var defaultVal = $(this).data('default');
		$(this).val(defaultVal);
		});
	  
	var options = document.querySelectorAll('select option');

	  for (var i = 0; i < options.length; i++) {
	    options[i].selected = options[i].defaultSelected; 	   
	  }
	
	  rowCount =  $("#revenueColorTable tr").length;
	  var element = document.getElementById("alertMsg");
	  element.innerHTML = "";
	
    
      
	  $('#revenueColorTable tr:eq(1)').css('background','#0080FF');
	  $('#revenueColorTable tr:eq(2)').css('background','#FFDC00');
	  $('#revenueColorTable tr:eq(3)').css('background','red');
	  
 
	  selectColorChanges();
	
	}


function selectColorChanges() {

	  $('select').on('change', function () {
	  	if (this.value == "#0080FF" ) {
	  		$(this).closest('tr').css('background-color','#0080FF');
	  	}
	  	else if (this.value == "#FFDC00") {
	  		$(this).closest('tr').css('background-color','#FFDC00');
	  	}
	  	else if (this.value == "red") {
	 	   $(this).closest('tr').css('background-color','red');
	   	}
	   	else if (this.value == "#FF00FF") {
	 		$(this).closest('tr').css('background-color','#FF00FF');
	 	}
	 	else if (this.value == "#8A2BE2") {
	    	$(this).closest('tr').css('background-color','#8A2BE2');
	    }
	    else if (this.value == "-- Select Color --") {
	   		$(this).closest('tr').css('background-color','white');
	    }
	    
	    });
	 }
function applyChanges(){

	$("#enable").prop('checked',true);   
	$("#disable").prop('checked',false); 


	 document.getElementById('sitesAutocomplete').value = "";
     concatenatedSites ="";
     selectedSites=[];

	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	
	document.getElementById("mapContainer").innerHTML ="";
	var map = new google.maps.Map(document.getElementById("mapContainer"), {

			 mapTypeControl: false,
			 center:Nairobi ,
		      zoom: 6,
			 
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

		//Add legend button under zoom control on map
		const centerControlDiv = document.createElement("div");
	  LegendControl(centerControlDiv, map);
	  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	  const DefaultZoomDiv = document.createElement("div");
	  DefaultZoomControl(DefaultZoomDiv, map);
	  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	  const maxRevControlDiv = document.createElement("div");
	  MaxRevenueControl(maxRevControlDiv, map);
	  map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

	  const minRevControlDiv = document.createElement("div");
	  MinRevenueControl(minRevControlDiv, map);
	  map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
	  
		UnselectAll();
		DeleteMarkers();


		RevenueSetColor(disableData,map);

		
	}





function ShowHideDiv(){
	$("#legendDiv").toggle();
}
function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}


var markerGroups = {
		  "green": [],
		  "red": [],
		 "#FF8C00": [],
		  "#0080FF": [],
		  "#FFDC00": [],
		  "#4D0207": [],
		  "#FF00FF":[],
		  "#8A2BE2":[]

		};
var yellowCluster;
var blueCluster;
var redCluster;
var pinkCluster;
var purpleCluster;

markers=[];
selectedSites=[];
var concatenatedSites ="";
function AddSiteMarkers(lst,map,color){

var ctx = getContextPath();

	  
	markers=[];
	
	//Set zoom level
	map.setZoom(6);

		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);	
		map.setCenter(Nairobi);
		
		for(i=0;i<lst.length;i++){
	     
				markerId=lst[i][8];
				SiteID=lst[i][8];
				
				infowindow = new google.maps.InfoWindow();

				var latSite=lst[i][2];
				var lngSite=lst[i][1];
				const position = new google.maps.LatLng(latSite,lngSite);

				var SiteName=lst[i][0];	
				var SiteRev=lst[i][7];			         
				
				var siteName="<b style='font-size:13px;'><u>Site: </u></b>"+SiteName;
				var siteLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+lst[i][2];
				var siteLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lst[i][1];
				var siteRev="<b style='font-size:13px;'><u>Revenue:</u> </b>"+SiteRev;
				
					
				var data="<div>"+siteName+"</br>"+siteLat+"</br>"+siteLong+ "</br>"+siteRev+ "</div>";			

				//Add markers on map
				const marker = new google.maps.Marker({
			        position: position,
			        data:data,
			        ID:markerId,
			        Lat:latSite,
			        Long:lngSite,
			        Name:SiteName,
			        SiteId:SiteID,
			        color:color,
			      
			        icon: {
			        	  path: google.maps.SymbolPath.CIRCLE,
					        fillColor: color,
					        fillOpacity: 0.9,
					        strokeColor: 'transparent',
					        strokeOpacity: 0.9,
					        strokeWeight: 1,
					        scale: 7	   
			            
			          }
			        
			         	    });

		 	    
				 	marker.metadata = { id: markerId };
				    markers[color] = marker;
				    markers.push(marker);

					//Add markers to array depending on colors
				    if (!markerGroups[color]) markerGroups[color] = [];
						  markerGroups[color].push(marker);
	 
					
				google.maps.event.addListener(marker, "click", function (e) {

				     	infowindow.setContent(this.data); 
			        	infowindow.open(map,this);

						var LatSelected=this.Lat;
						var LongSelected=this.Long;
						var NameSelected=this.Name;
						var IDSelected=this.ID;
			
			
						
						var siteSelected = NameSelected ;
						
						//console.log("AllSitesArr" +AllSitesArr);  
						
					
					// AllSitesArr is equal to the internal array after removing a tag input
 					$('#sitesAutocomplete').on('itemRemoved', function(event) {	
 					//AllSitesArr =[];					 
 						AllSitesArr = $("#sitesAutocomplete").data('tagsinput').itemsArray;
 					}); 
 					
 					$('#sitesAutocomplete').on('itemAdded', function(event) {
 						 
 						AllSitesArr = $("#sitesAutocomplete").data('tagsinput').itemsArray;
 					}); 
 				
 					//console.log("AllSitesArr" +AllSitesArr);  
 				
						document.getElementById("site").value =NameSelected+";"+IDSelected;
						
						//Add sites name in text area
						var siteInput=site.value;
					    var siteNameInp = siteInput.split(";");
					    siteNameInp  = siteNameInp[0]+" ";
					    
				       $("#sitesAutocomplete").tagsinput('add', siteNameInp.toString() );	
				    		
						// $("#sitesAutocomplete").val('');
						// $("#sitesAutocomplete").val(newSitesInpVal);
						 //	console.log($("#sitesAutocomplete").val());  
						 						 
						 
				 			});
				google.maps.event.addListener(marker, "dblclick", function (e) {

			     	map.setZoom(15);
			     	map.setCenter(position);

			 			});
	 			
		  				}

			//Define clusters
		 if (color=="#FFDC00"){
		 	yellowCluster = new MarkerClusterer(map,markerGroups["#FFDC00"], {ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url:  ctx+'/resources/clusterIcons/yellowCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
		 	},
		 	], });
		 	}
		 	
		 	else if (color=="#0080FF"){
		 	
		 	blueCluster = new MarkerClusterer(map,markerGroups["#0080FF"], {ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url:  ctx+'/resources/clusterIcons/blueCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
			},
			], });
			}
			
			else if (color=="red"){
				redCluster = new MarkerClusterer(map,markerGroups["red"], {ignoreHiddenMarkers: true, 
				styles: [
				{
				url: ctx+'/resources/clusterIcons/redCluster.png',
				height: 60,
				width:60,
				anchorText:[-3,-3]
				},
				], });
				
				}
				
				else if (color=="#FF00FF"){
					pinkCluster = new MarkerClusterer(map,markerGroups["#FF00FF"], {ignoreHiddenMarkers: true, 
					styles: [
					{
					url: ctx+'/resources/clusterIcons/pinkCluster.png',
					height: 60,
					width:60,
					anchorText:[-3,-3]
					},
					], });
		      }


			else if (color=="#8A2BE2"){
				 purpleCluster = new MarkerClusterer(map,markerGroups["#8A2BE2"], {ignoreHiddenMarkers: true, 
					    styles: [
					        {
					          url: ctx+'/resources/clusterIcons/purpleCluster.png',
					          height: 60,
					          width:60,
					          anchorText:[-3,-3]
					        },
					      ], });
		      }
		      
			
		      
			
			 markerClusterer = new MarkerClusterer(map,  markerGroups["green"], {minimumClusterSize: 10000000,ignoreHiddenMarkers: true});
			 markerClusterer = new MarkerClusterer(map,  markerGroups["#4D0207"], {minimumClusterSize: 10000000,ignoreHiddenMarkers: true});
				
}


function AddSelectedSiteColor(lst,map) {

if (lst.length == 0) {
	 	  $("#2gSites").attr('disabled', true);
	 	  $("#3gSites").attr('disabled', true);
	 	  $("#4gSites").attr('disabled', true);
	 	  $("#legendMaxRevenue").attr('disabled', true);
	 	  $("#legendMinRevenue").attr('disabled', true);
	 		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {

	 	

 	  $("#legendMaxRevenue").attr('disabled', true);
 	  $("#legendMinRevenue").attr('disabled', true);
 	  $("#2gSites").attr('disabled', false);
	  $("#3gSites").attr('disabled', false);
	  $("#4gSites").attr('disabled', false);
 		
	var element = document.getElementById("selectUnselect");

	 if (element.innerHTML == " "){
		 element.innerHTML = "Unselect All";
	 }
	 if (element.innerHTML == "Unselect All"){
		 element.innerHTML = "Select All";
	 }
	  if (element.innerHTML == "Select All"){
		 element.innerHTML = "Unselect All";
	 }
	 
	maxRevenueListIndex=[];
	minRevenueListIndex=[];
	maxRevenueList=[];
	minRevenueList=[] ;

	maxRev=[];
	minRev=[];
	var dataSite = lst;
	disableData = dataSite;
	
	twoGListIndex=[];
	threeGListIndex=[];
	fourGListIndex=[];
	
	Two_g_list=[];
	Three_g_list=[];
	four_g_list=[];

	    for (j=0;j<dataSite.length;j++){
	        if (dataSite[j][3] == '1' && dataSite[j][4] == '0' &&  dataSite[j][5] == '0' && dataSite[j][6] == '0'  ) {
	        	twoGListIndex.push(j);
	        	Two_g_list.push(dataSite[j]);
	        	

	        }
	       else if (dataSite[j][3] == '1' && dataSite[j][4] == '1' &&  dataSite[j][5] == '0' && dataSite[j][6] == '0'  ) {
	        	threeGListIndex.push(j);
	        	Three_g_list.push(dataSite[j]);
	        	

	        }
	        else if (dataSite[j][3] == '1' && dataSite[j][4] == '1' &&  dataSite[j][5] == '1' && dataSite[j][6] == '0'  ) {
	        	fourGListIndex.push(j);
	        	four_g_list.push(dataSite[j]);
	        	

	        }
	    }



	        if (twoGListIndex.length > 0  ){  
	        
	            AddSiteMarkers(Two_g_list,map,"#FFDC00");
	            $("#2gSites").prop('checked',true);  
	        }
	        if (threeGListIndex.length > 0  ){  
	        
	            AddSiteMarkers(Three_g_list,map,"#0080FF");
	            $("#3gSites").prop('checked',true);  
	        }
	        if (fourGListIndex.length > 0  ){  
	        
	            AddSiteMarkers(four_g_list,map,"red");
	            $("#4gSites").prop('checked',true);  
	        }
	    var element = document.getElementById("min-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
  		
  		   var element = document.getElementById("max-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
  		
  		//Disable all unchecked checkboxes
		if(twoGListIndex.length ==0) {
		 	  $("#2gSites").attr('disabled', true);
		}
		if(threeGListIndex.length  ==0) {
		 	  $("#3gSites").attr('disabled', true);
		}
		if(fourGListIndex.length  ==0) {
		 	  $("#4gSites").attr('disabled', true);
		}
     
    } 
	}

function ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSitesList,color) {

	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	var map = new google.maps.Map(document.getElementById("mapContainer"), {
		center:Nairobi ,
		zoom: 6
		});

	document.getElementById("mapContainer").innerHTML ="";

	var map = new google.maps.Map(document.getElementById("mapContainer"), {

                 mapTypeControl: false,
                 center:Nairobi ,
                 zoom: 6,
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

                             if (color=="#FFDC00"){
                                AddSiteMarkers(Two_g_list,map,"#FFDC00"); 
                                if (markerGroups["#0080FF"].length>0  ){
                                   //Clear previous blue markers
                                    HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
                                    //Add blue markers
                                AddSiteMarkers(Three_g_list,map,"#0080FF");
                                }
                                if (markerGroups["red"].length>0  ){
                                 HideClusterIcon(markerGroups["red"],"red");
                                 AddSiteMarkers(four_g_list,map,"red");
                                 }
                                if (markerGroups["#8A2BE2"].length>0  ){
                                	HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
                                	AddSiteMarkers(warehouseSitesList,map,"#8A2BE2");
                                 }
                        
                                }
                        
                                else if (color=="#0080FF"){
                                    AddSiteMarkers(Three_g_list,map,"#0080FF"); 
                                    if (markerGroups["#FFDC00"].length>0  ){
                                    HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
                                    AddSiteMarkers(Two_g_list,map,"#FFDC00");
                                    }
                                    if (markerGroups["red"].length>0  ){
                                    HideClusterIcon(markerGroups["red"],"red");
                                    AddSiteMarkers(four_g_list,map,"red");
                                     }
                                    if (markerGroups["#8A2BE2"].length>0  ){
                                    	HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
                                    	AddSiteMarkers(warehouseSitesList,map,"#8A2BE2");
                                     }
                                  
                                    }

                                else if (color=="#8A2BE2"){
                                    AddSiteMarkers(warehouseSitesList,map,"#8A2BE2"); 
                                    if (markerGroups["#FFDC00"].length>0  ){
                                    HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
                                    AddSiteMarkers(Two_g_list,map,"#FFDC00");
                                    }
                                    if (markerGroups["red"].length>0  ){
                                    HideClusterIcon(markerGroups["red"],"red");
                                    AddSiteMarkers(four_g_list,map,"red");
                                     }
                                    if (markerGroups["#0080FF"].length>0  ){
                                         HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
                                     	AddSiteMarkers(Three_g_list,map,"#0080FF");
                                     }
                                  
                                    }
                        
                                 else if (color=="red"){
                                        AddSiteMarkers(four_g_list,map,"red"); 
                                        if (markerGroups["#FFDC00"].length>0  ){
                                        HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
                                        AddSiteMarkers(Two_g_list,map,"#FFDC00");
                                        }
                                        if (markerGroups["#0080FF"].length>0  ){
                                         HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
                                         AddSiteMarkers(Three_g_list,map,"#0080FF");
                                         }
                                        if (markerGroups["#8A2BE2"].length>0  ){
                                        	HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
                                        	AddSiteMarkers(warehouseSitesList,map,"#8A2BE2");
                                         }
                                
                                        }
                                    //Add buttons on map
                                	const centerControlDiv = document.createElement("div");
                                    LegendControl(centerControlDiv, map);
                                    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

                                    const DefaultZoomDiv = document.createElement("div");
                                    DefaultZoomControl(DefaultZoomDiv, map);
                                    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

                                    const maxRevControlDiv = document.createElement("div");
                                    MaxRevenueControl(maxRevControlDiv, map);
                                    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

                                    const minRevControlDiv = document.createElement("div");
                                    MinRevenueControl(minRevControlDiv, map);
                                    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
                 }

maxList=[];
minList=[];
maxMinList=[];
function MaxMinSetColor(lst,map,checkedChckbox){
	maxList=[];
	minList=[];
	maxMinList=[];
	maxRev=[];
	minRev=[];
	disableData =[];

	if (lst.length == 0) {
		$("#2gSites").attr('disabled', true);
	 	$("#3gSites").attr('disabled', true);
	 	$("#4gSites").attr('disabled', true);
	 	$("#legendMaxRevenue").attr('disabled', true);
	 	$("#legendMinRevenue").attr('disabled', true);
	 		
		var element = document.getElementById("selectUnselect");
		if (element.innerHTML == "Unselect All"){
			element.innerHTML = " ";
		 }
	
	}
	else {

		$("#2gSites").attr('disabled', false);
	 	$("#3gSites").attr('disabled', false);
	 	$("#4gSites").attr('disabled', false);
	 	$("#legendMaxRevenue").attr('disabled', false);
	 	$("#legendMinRevenue").attr('disabled', false);

		var element = document.getElementById("selectUnselect");
		if (element.innerHTML == " "){
			element.innerHTML = "Unselect All";
		}

		var dataSite = lst;
		var maxRevenue = -1;
		var min_Revenue = -1;
		maxRevenueListIndex=[];
		minRevenueListIndex=[];
		maxRevenueList=[];
		minRevenueList=[] ;

		if (dataSite.length>=1){
			var min_Revenue = dataSite[0][7];
		 	var maxRevenue = dataSite[0][7];

		 	for (i=0;i<dataSite.length;i++){
		    //Set max Revenue Value
		    if (dataSite[i][7] >= maxRevenue){
		      if (dataSite[i][7] != maxRevenue){
		     		 maxRevenueListIndex = [];
		     }
      
      		maxRevenue = dataSite[i][7];
       		// creating a list with max Revenue value
      		maxRevenueListIndex.push(i);
          }
        //Set min Revenue Value
          if (dataSite[i][7] <= min_Revenue){
 			if (dataSite[i][7] != min_Revenue){
		      	minRevenueListIndex = [];
		      }
            min_Revenue = dataSite[i][7];
            // creating a list with min Revenue value
            minRevenueListIndex.push(i);
                        
            }
      }        
  
  if(checkedChckbox =="max") {
	  
	  
	  $("#2gSites").attr('disabled', true);
 	  $("#3gSites").attr('disabled', true);
 	  $("#4gSites").attr('disabled', true);
 	  $("#legendMaxRevenue").attr('disabled', false);
 	  $("#legendMinRevenue").attr('disabled', true);

 	  twoGListIndex=[];
	  threeGListIndex=[];
	  fourGListIndex=[];
	  minRevenueListIndex=[];

	  // coloring max revenue
	  for (z=0;z<maxRevenueListIndex.length;z++){
	      for (v=0;v<9;v++){
	          //Add max Revenue site to max Revenue array
	          maxRevenueList.push(dataSite[maxRevenueListIndex[z]][v]);
	          maxRev.push(dataSite[maxRevenueListIndex[z]][v]);
	      }
	      disableData =[maxRev];
          maxList.push([dataSite[maxRevenueListIndex[z]][0],dataSite[maxRevenueListIndex[z]][1],dataSite[maxRevenueListIndex[z]][2],dataSite[maxRevenueListIndex[z]][3],dataSite[maxRevenueListIndex[z]][4],dataSite[maxRevenueListIndex[z]][5],dataSite[maxRevenueListIndex[z]][6],dataSite[maxRevenueListIndex[z]][7],dataSite[maxRevenueListIndex[z]][8]]);
         // disableData =maxRev;
	  AddSiteMarkers([maxRevenueList],map,"green");
	  
	   if (maxRevenueList[3] == '1' && maxRevenueList[4] == '0' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {

		var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotYellow");
  
      }
     else if (maxRevenueList[3] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotBlue");

      }
      else if (maxRevenueList[3] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '1' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotRed");

      }
     
     var element = document.getElementById("min-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
     
	  //Clear array before adding another max Revenue site
	  maxRevenueList=[];
	  $("#legendMaxRevenue").prop('checked',true); 
	  
	  
	  } 		

	 }
  if(checkedChckbox =="min") {

	  $("#2gSites").attr('disabled', true);
 	  $("#3gSites").attr('disabled', true);
 	  $("#4gSites").attr('disabled', true);
 	  $("#legendMaxRevenue").attr('disabled', true);
 	  $("#legendMinRevenue").attr('disabled', false);

 	  twoGListIndex=[];
	  threeGListIndex=[];
	  fourGListIndex=[];
	  maxRevenueListIndex=[];
	  
	//coloring min revenue
	for (w=0;w<minRevenueListIndex.length;w++){
	  for (e=0;e<9;e++){
	      //Add min Revenue  site to min Revenue array
	      minRevenueList.push(dataSite[minRevenueListIndex[w]][e]);
	      minRev.push(dataSite[minRevenueListIndex[w]][e]);
	      
	  }
      disableData =[minRev];
	  
      minList.push([dataSite[minRevenueListIndex[w]][0],dataSite[minRevenueListIndex[w]][1],dataSite[minRevenueListIndex[w]][2],dataSite[minRevenueListIndex[w]][3],dataSite[minRevenueListIndex[w]][4],dataSite[minRevenueListIndex[w]][5],dataSite[minRevenueListIndex[w]][6],dataSite[minRevenueListIndex[w]][7],dataSite[minRevenueListIndex[w]][8]]);
	  
	  AddSiteMarkers([minRevenueList],map,"#4D0207");
		
	 if (minRevenueList[3] == '1' && minRevenueList[4] == '0' &&  minRevenueList[5] == '0' && minRevenueList[6] == '0' ) {

		var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotYellow");
  
      }
     else if (minRevenueList[3] == '1' && minRevenueList[4] == '1' &&  minRevenueList[5] == '0' && minRevenueList[6] == '0' ) {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotBlue");

      }
     else if (minRevenueList[3] == '1' && minRevenueList[4] == '1' &&  minRevenueList[5] == '1' && minRevenueList[6] == '0' ) {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotRed");

      }
	
	var element = document.getElementById("max-div");
  		element.classList.remove("dotBlue");
  		element.classList.remove("dotRed"); 
  		element.classList.remove("dotYellow");  
  		element.classList.add("dot");
     
	//Clear array before adding another min Revenue site
	minRevenueList=[] ;
	  $("#legendMinRevenue").prop('checked',true); 
		
	
	}
	 
  }
 if(checkedChckbox =="maxMin") {
	  twoGListIndex=[];
	  threeGListIndex=[];
	  fourGListIndex=[];
	  
	 if (dataSite.length ==1){
	 	UnselectAll();
	 	AddSelectedSiteColor(dataSite,map);
		maxMinList=dataSite;
	}

	 else {
	  $("#2gSites").attr('disabled', true);
	  $("#3gSites").attr('disabled', true);
	  $("#4gSites").attr('disabled', true);
	  $("#legendMaxRevenue").attr('disabled', false);
	  $("#legendMinRevenue").attr('disabled', false);
	 
	  if(maxRevenue == min_Revenue) {
			minRevenueListIndex =[];
		}
	
// coloring max revenue
for (z=0;z<maxRevenueListIndex.length;z++){
   for (v=0;v<9;v++){
       //Add max Revenue site to max Revenue array
       maxRevenueList.push(dataSite[maxRevenueListIndex[z]][v]);
       maxRev.push(dataSite[maxRevenueListIndex[z]][v]);
       
   }

	   revenueMax=[maxRev];
	   maxMinList.push([dataSite[maxRevenueListIndex[z]][0],dataSite[maxRevenueListIndex[z]][1],dataSite[maxRevenueListIndex[z]][2],dataSite[maxRevenueListIndex[z]][3],dataSite[maxRevenueListIndex[z]][4],dataSite[maxRevenueListIndex[z]][5],dataSite[maxRevenueListIndex[z]][6],dataSite[maxRevenueListIndex[z]][7],dataSite[maxRevenueListIndex[z]][8]]);
	   AddSiteMarkers([maxRevenueList],map,"green");

      if (maxRevenueList[3] == '1' && maxRevenueList[4] == '0' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {

		 var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotYellow");
  
      }
     else if (maxRevenueList[3] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotBlue");

      }
      else if (maxRevenueList[3] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '1' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotRed");

      }
	//Clear array before adding another max Revenue site
	maxRevenueList=[];
	$("#legendMaxRevenue").prop('checked',true);  
	  
	}

	//coloring min revenue
	for (w=0;w<minRevenueListIndex.length;w++){
	for (e=0;e<9;e++){
	   //Add min Revenue  site to min Revenue array
	   minRevenueList.push(dataSite[minRevenueListIndex[w]][e]);
	   minRev.push(dataSite[minRevenueListIndex[w]][e]);
	   
	}
	revenueMin =[minRev];
	maxMinList.push([dataSite[minRevenueListIndex[w]][0],dataSite[minRevenueListIndex[w]][1],dataSite[minRevenueListIndex[w]][2],dataSite[minRevenueListIndex[w]][3],dataSite[minRevenueListIndex[w]][4],dataSite[minRevenueListIndex[w]][5],dataSite[minRevenueListIndex[w]][6],dataSite[minRevenueListIndex[w]][7],dataSite[minRevenueListIndex[w]][8]]);
	
	
	 if (minRevenueList[3] == '1' && minRevenueList[4] == '0' &&  minRevenueList[5] == '0' && minRevenueList[6] == '0' ) {
	
			 var element = document.getElementById("min-div");
	  		element.classList.remove("dot-min");  
	   		element.classList.add("dotYellow");
	  
	      }
	      else if (minRevenueList[3] == '1' && minRevenueList[4] == '1' &&  minRevenueList[5] == '0' && minRevenueList[6] == '0' ) {
	      	var element = document.getElementById("min-div");
	  		element.classList.remove("dot-min");  
	   		element.classList.add("dotBlue");
	
	      }
	     else if (minRevenueList[3] == '1' && minRevenueList[4] == '1' &&  minRevenueList[5] == '1' && minRevenueList[6] == '0' ) {
	      	var element = document.getElementById("min-div");
	  		element.classList.remove("dot-min");  
	   		element.classList.add("dotRed");
	
	      }
	
	AddSiteMarkers([minRevenueList],map,"#4D0207");
	//Clear array before adding another min Revenue site
	minRevenueList=[] ;
	$("#legendMinRevenue").prop('checked',true);  
	
	}
	if(minRevenueListIndex.length >0) {
	disableData = revenueMax.concat(revenueMin);
	}
	else {
	  disableData =[revenueMax];
	  var element = document.getElementById("min-div");
	  		element.classList.remove("dotBlue");
	  		element.classList.remove("dotRed"); 
	  		element.classList.remove("dotYellow");  
	  		element.classList.add("dot");
	  $("#legendMinRevenue").prop('checked',false);  

			}
	}
	}//end if lngth
}
}
	 
}


maxRev=[];
minRev=[];
disableData =[];

function setColor(lst,map){
	maxRev=[];
	minRev=[];
	disableData =[];
	
	if (lst.length == 0) {
	 	  $("#2gSites").attr('disabled', true);
	 	  $("#3gSites").attr('disabled', true);
	 	  $("#4gSites").attr('disabled', true);
	 	  $("#legendMaxRevenue").attr('disabled', true);
	 	  $("#legendMinRevenue").attr('disabled', true);
	 	  
	 		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {

		  $("#2gSites").attr('disabled', false);
	 	  $("#3gSites").attr('disabled', false);
	 	  $("#4gSites").attr('disabled', false);
	 	  $("#legendMaxRevenue").attr('disabled', false);
	 	  $("#legendMinRevenue").attr('disabled', false);
	 	 var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == " "){
			 element.innerHTML = "Unselect All";
		 }
		 
		  var dataSite = lst;
		  disableData = lst;
		  maxRevenueListIndex=[];
		  minRevenueListIndex=[];
		  twoGListIndex=[];
		  threeGListIndex=[];
		  fourGListIndex=[];

		  maxRevenueList=[];
		  minRevenueList=[] ;   
		  Two_g_list=[];
		  Three_g_list=[];
		  four_g_list=[];
		  var min_Revenue = -1;
          var maxRevenue = -1;
          
			var element = document.getElementById("min-div");
			element.classList.remove("dotYellow");
			element.classList.remove("dotRed");
			element.classList.remove("dotBlue");
  			element.classList.add("dot-min");  
  			
  			var element = document.getElementById("max-div");
			element.classList.remove("dotRed");
			element.classList.remove("dotBlue");
			element.classList.remove("dotYellow");
  			element.classList.add("dot-max");  
   		
		  
if (dataSite.length>1){


	var min_Revenue = dataSite[0][7];
    var maxRevenue = dataSite[0][7];
  for (i=0;i<dataSite.length;i++){
      //Set max Revenue Value
      if (dataSite[i][7] >= maxRevenue){
      
      
     	if (dataSite[i][7] != maxRevenue){
     		 maxRevenueListIndex = [];
      	}
      
      	maxRevenue = dataSite[i][7];
      	
      	// creating a list with max Revenue value
      	maxRevenueListIndex.push(i);
       
          
          }
          if (dataSite[i][7] <= min_Revenue){
          
		     if (dataSite[i][7] != min_Revenue){
		      	minRevenueListIndex = [];
		      }
            min_Revenue = dataSite[i][7];
            
            // creating a list with min Revenue value
            minRevenueListIndex.push(i);
            
            }
            
      }
      if(maxRevenue == min_Revenue) {
			minRevenueListIndex =[];
		}
      

        }


 // creating the list index lists for 2g 3g 4g 5g 
  for (j=0;j<dataSite.length;j++){
      if (dataSite[j][3] == '1' && dataSite[j][4] == '0' &&  dataSite[j][5] == '0' && dataSite[j][6] == '0' && dataSite[j][7] != maxRevenue && dataSite[j][7] != min_Revenue ) {
      	twoGListIndex.push(j);
      	Two_g_list.push(dataSite[j]);
      	

      }
      else if (dataSite[j][3] == '1' && dataSite[j][4] == '1' &&  dataSite[j][5] == '0' && dataSite[j][6] == '0' && dataSite[j][7] != maxRevenue && dataSite[j][7] != min_Revenue ) {
      	threeGListIndex.push(j);
       	Three_g_list.push(dataSite[j]);
      	

      }
      else if (dataSite[j][3] == '1' && dataSite[j][4] == '1' &&  dataSite[j][5] == '1' && dataSite[j][6] == '0' && dataSite[j][7] != maxRevenue && dataSite[j][7] != min_Revenue ) {
      	fourGListIndex.push(j);
      	four_g_list.push(dataSite[j]);
      	

      }

  }
  
  // coloring max revenue
  for (z=0;z<maxRevenueListIndex.length;z++){
      for (v=0;v<9;v++){
          //Add max Revenue site to max Revenue array
          maxRevenueList.push(dataSite[maxRevenueListIndex[z]][v]);
          maxRev.push(dataSite[maxRevenueListIndex[z]][v]);
      }
  AddSiteMarkers([maxRevenueList],map,"green");
  
      if (maxRevenueList[3] == '1' && maxRevenueList[4] == '0' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {

		 var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotYellow");
  
      }
     else if (maxRevenueList[3] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '0' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotBlue");

      }
      else if (maxRevenueList[3] == '1' && maxRevenueList[4] == '1' &&  maxRevenueList[5] == '1' && maxRevenueList[6] == '0' ) {
      	var element = document.getElementById("max-div");
  		element.classList.remove("dot-max");  
   		element.classList.add("dotRed");

      }

  

   
      
  //Clear array before adding another max Revenue site
  maxRevenueList=[];
  $("#legendMaxRevenue").prop('checked',true);  
     
  }
  
//coloring min revenue
for (w=0;w<minRevenueListIndex.length;w++){
  for (e=0;e<9;e++){
      //Add min Revenue  site to min Revenue array
      minRevenueList.push(dataSite[minRevenueListIndex[w]][e]);
      minRev.push(dataSite[minRevenueListIndex[w]][e]);
  }
AddSiteMarkers([minRevenueList],map,"#4D0207");


 if (minRevenueList[3] == '1' && minRevenueList[4] == '0' &&  minRevenueList[5] == '0' && minRevenueList[6] == '0' ) {

		 var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotYellow");
  
      }
      else if (minRevenueList[3] == '1' && minRevenueList[4] == '1' &&  minRevenueList[5] == '0' && minRevenueList[6] == '0' ) {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotBlue");

      }
     else if (minRevenueList[3] == '1' && minRevenueList[4] == '1' &&  minRevenueList[5] == '1' && minRevenueList[6] == '0' ) {
      	var element = document.getElementById("min-div");
  		element.classList.remove("dot-min");  
   		element.classList.add("dotRed");

      }

	//Clear array before adding another min Revenue site
	minRevenueList=[] ;
	$("#legendMinRevenue").prop('checked',true);  

}


      if (twoGListIndex.length > 0  ){  
          AddSiteMarkers(Two_g_list,map,"#FFDC00");
          $("#2gSites").prop('checked',true);  
      }
      if (threeGListIndex.length > 0  ){  
          AddSiteMarkers(Three_g_list,map,"#0080FF");
          $("#3gSites").prop('checked',true);  
      }
      if (fourGListIndex.length > 0  ){  
          AddSiteMarkers(four_g_list,map,"red");
          $("#4gSites").prop('checked',true);  
      }

	//Disable all unchecked checkboxes
	if(maxRevenueListIndex.length ==0) {
	  $("#legendMaxRevenue").attr('disabled', true);
	}
	if(minRevenueListIndex.length ==0) {
	 	  $("#legendMinRevenue").attr('disabled', true);
	}
	if(twoGListIndex.length ==0) {
	 	  $("#2gSites").attr('disabled', true);
	}
	if(threeGListIndex.length  ==0) {
	 	  $("#3gSites").attr('disabled', true);
	}
	if(fourGListIndex.length  ==0) {
	 	  $("#4gSites").attr('disabled', true);
	}
	
      
	}
	
	
}


function defaultLegend(){

	document.getElementById("tableDiv").innerHTML ="";
 	document.getElementById("tableDiv").innerHTML =divTablee;
 	
 	document.getElementById("legendHeader").innerHTML ="";
	document.getElementById("legendHeader").innerHTML='<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6> <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAll()"  id="selectUnselect" >Unselect All</button>';
	
	}

firstRangeListIndex=[];
secondRangeListIndex=[];
thirdRangeListIndex=[];
fourthRangeListIndex=[];
fifthRangeListIndex=[];


firstRangeList=[];
secondRangeList=[];
thirdRangeList=[];
fourthRangeList=[];
fifthRangeList=[];
revenueColorArr=[];
var newTableDiv;


function RevenueSetColor(disableData,map){
        DeleteMarkers();
    
        if (disableData.length == 0) {
            alert('No data to apply changes on it');
    
        }
    
        else {
    
            UnselectAllRev();
            
          var dataSite = disableData;
          maxRev =[];
          minRev =[];
    
          
         fromToColor=[];
         listFromToColor=[];
         tocolor=[];
         $("#revenueColorTable tr").find('input[name="record"]').each(function(){
    
    
                var fromRange = $(this).parent().parent().children('td[name="from"]').children('input').val();
                var toRange = $(this).parent().parent().children('td[name="to"]').children('input').val();
                var color = $(this).parent().parent().children('td[name="color"]').children('select').val();
            
                fromToColor.push(fromRange,toRange,color);	
                listFromToColor.push([fromRange,toRange,color]);	    	
                tocolor.push(toRange); 	
              
              });
        
    if (fromToColor[0] == ""){
        fromToColor[0] = "0";
            listFromToColor[0][0] ="0";
        }
    
    
    maxRevenueListIndex=[];
    minRevenueListIndex=[];
    toMaxRange=0;
   
    var maxRevenue = -1;
    
                
         
      firstRangeListIndex=[];
      secondRangeListIndex=[];
      thirdRangeListIndex=[];
      fourthRangeListIndex=[];
      fifthListIndex=[];
      
      firstRangeList=[];
      secondRangeList=[];
      thirdRangeList=[];
      fourthRangeList=[];
      fifthRangeList=[];
    
    
      for (var k=0;k<listFromToColor.length;k++){
    
           if(parseFloat(listFromToColor[k][0]) > parseFloat(listFromToColor[k][1]) ){
               alert("Please choose a correct range");
                return false;
               }
               
            if(isNaN((listFromToColor[k][0])) || isNaN((listFromToColor[k][1])) ){
               alert("Please choose a numerical range");
              return false;
               }
               
            if(listFromToColor[k][2] == "-- Select Color --" ){
               alert("Please choose a color");
              return false;
               }
             
             
                 for (var t=0; t<listFromToColor.length;t++){
                if (k != t){
                    if ((parseFloat(listFromToColor[t][0]) == parseFloat(listFromToColor[k][0])) || (parseFloat(listFromToColor[t][1]) == parseInt(listFromToColor[k][1])) ){
                        alert("Please choose two different ranges");
                return false;
                }
            }
    
        }
        
           for (var t=0; t<listFromToColor.length;t++){
                if (k != t){
                    if (listFromToColor[t][2] == listFromToColor[k][2] ){
                        alert("The color shouldn't be the same , please choose another color");
                return false;
    
                }
    
            }
        }
               
            }
            
    
      for (var r=0; r<listFromToColor.length-1;r++){
            for (var t=0; t<listFromToColor.length-1;t++){
                if (r != t){
                    if (listFromToColor[t+1][0] == "" || listFromToColor[r+1][0] == "" || listFromToColor[t][1] == ""  || listFromToColor[r][1] == ""){
                            alert("Please fill two ranges");
                return false;
                }
            }
        }
        }
    
    
            for (j=0;j<dataSite.length;j++){
                if(fromToColor[0] !=undefined && fromToColor[1] !=undefined ) {
                
                    //Check if there is only one row with empty ToValue 
                    if (listFromToColor.length ==1 && fromToColor[1] =="" ) {
                        if (parseInt(dataSite[j][7]) >= parseFloat(fromToColor[0]) ) {
                            firstRangeListIndex.push(j);
                            firstRangeList.push(dataSite[j]);
                            
                        }
                    } 
                    //ELSE Check if the revenue between first range in table
                    else if (parseInt(dataSite[j][7]) >= parseFloat(fromToColor[0]) && parseInt(dataSite[j][7]) <= parseFloat(fromToColor[1])) {
                        firstRangeListIndex.push(j);
                        firstRangeList.push(dataSite[j]);
                        
                    }
                    
                }
    
                 if(fromToColor[3] !=undefined && fromToColor[4] !=undefined ) {
                    //Check if toValue is empty in 2nd row(last row)
                    if (listFromToColor.length ==2 && fromToColor[4] =="" ) {
                        if (parseInt(dataSite[j][7]) >= parseFloat(fromToColor[3]) ) {
    
                            secondRangeListIndex.push(j);
                            secondRangeList.push(dataSite[j]);
                            
                        }
                    } 
                    //ELSE Check if the revenue between second range in table
                    else if (parseInt(dataSite[j][7]) >= parseFloat(fromToColor[3]) && parseInt(dataSite[j][7]) <= parseFloat(fromToColor[4])) {
                          secondRangeListIndex.push(j);
                          secondRangeList.push(dataSite[j]);
                          
                      }
                    
                    
                }
                 if(fromToColor[6] !=undefined && fromToColor[7] !=undefined ) {
                    //Check if toValue is empty in 3rd row(last row)
                    if (listFromToColor.length ==3 && fromToColor[7] =="" ) {
                        if (parseInt(dataSite[j][7]) >= parseFloat(fromToColor[6]) ) {
    
                            thirdRangeListIndex.push(j);
                            thirdRangeList.push(dataSite[j]);
                            
                        }
                    } 
                    //ELSE Check if the revenue between third range in table
                    else if (parseInt(dataSite[j][7]) >=parseFloat(fromToColor[6]) && parseInt(dataSite[j][7]) <= parseFloat(fromToColor[7])) {
                          thirdRangeListIndex.push(j);
                          thirdRangeList.push(dataSite[j]);
                          
                      }
                    
                    
                }
                 if(fromToColor[9] !=undefined && fromToColor[10] !=undefined ) {
                    //Check if toValue is empty in 4th row(last row)
                    if (listFromToColor.length ==4 && fromToColor[10] =="" ) {
                        if (parseInt(dataSite[j][7]) >= parseFloat(fromToColor[9]) ) {
                            fourthRangeListIndex.push(j);
                            fourthRangeList.push(dataSite[j]);
                            
                        }
                    } 
                    //ELSE Check if the revenue between 4th range in table
                    else if (parseInt(dataSite[j][7]) >=parseFloat(fromToColor[9] )&& parseInt(dataSite[j][7]) <= parseFloat(fromToColor[10])) {
                          fourthRangeListIndex.push(j);
                           fourthRangeList.push(dataSite[j]);
                      }
    
                    
                }
                 if(fromToColor[12] !=undefined && fromToColor[13] !=undefined ) {
                    //Check if toValue is empty in 5th row(last row)
                    if (listFromToColor.length ==5 && fromToColor[13] =="" ) {
                        if (parseInt(dataSite[j][7]) >= parseFloat(fromToColor[12]) ) {
    
                            fifthRangeListIndex.push(j);
                            fifthRangeList.push(dataSite[j]);
                            
                        }
                    }  
                    //ELSE Check if the revenue between 5th range in table
                    else if (parseInt(dataSite[j][7]) >=parseFloat(fromToColor[12]) && parseInt(dataSite[j][7]) <= parseFloat(fromToColor[13]) ){
                          fifthRangeListIndex.push(j);
                          fifthRangeList.push(dataSite[j]);
                          
                      }
                }
                
                } // end of main loop
    
    
                 
                   if (firstRangeList.length >=1 || secondRangeList.length >=1 || thirdRangeList.length >=1 ||fourthRangeList.length >=1 || fifthRangeList.length >=1 ){
               
                  if (tocolor[tocolor.length-1] == "" ){
	         	 for (i=0;i<dataSite.length;i++){
	                 if (dataSite[i][7] >= maxRevenue ){
	                     maxRevenue = dataSite[i][7];
	                 }
	             }

	         	 var min_Revenue = maxRevenue;
			      for (k=0;k<dataSite.length;k++){
						for(var x=0;x<listFromToColor.length;x++) {
						      
			          //Set min Revenue Value
			          if (dataSite[k][7] <=min_Revenue && (dataSite[k][7] >=listFromToColor[x][0])){
			              min_Revenue = dataSite[k][7];
			              }
			          }
			      }
                    
                   }
                  else {
                   for (var i=0;i<tocolor.length;i++){	 
		        	  
	              if (parseFloat(tocolor[i]) >= parseFloat(toMaxRange) ){
	            	  toMaxRange=tocolor[i];
	              }
                  }
                  
    		for (i=0;i<dataSite.length;i++){
					for(var x=0;x<listFromToColor.length;x++) {
						  
			      //Set max Revenue Value
			      if (dataSite[i][7] >= maxRevenue && (dataSite[i][7] >=listFromToColor[x][0]) && (dataSite[i][7] <=listFromToColor[x][1]) && (dataSite[i][7] <=toMaxRange )){
			      	
			      	
			      	maxRevenue = dataSite[i][7];
			          
			      }
			       }
			      }
	         

	          var min_Revenue = maxRevenue;
		      for (k=0;k<dataSite.length;k++){
					for(var x=0;x<listFromToColor.length;x++) {
					      
		          //Set min Revenue Value
		          if (dataSite[k][7] <= min_Revenue && (dataSite[k][7] >=listFromToColor[x][0]) && (dataSite[k][7] <=listFromToColor[x][1])){
		              min_Revenue = dataSite[k][7];
		              }
		          }
		      }
	          }
	 
			      for (s=0;s<dataSite.length;s++){
			    	  
			    	  if (parseFloat(dataSite[s][7]) == parseFloat(maxRevenue)){
			    	  	maxRevenueListIndex.push(s);
			    	  }
			    	}

			    	  // creating a list with min Revenue value
			    	  for (n=0;n<dataSite.length;n++){
			    	  
			    	      if (dataSite[n][7] == min_Revenue){
			    	      	minRevenueListIndex.push(n);
			    	      }
			    	  }

			    	  for (z=0;z<maxRevenueListIndex.length;z++){
			    	      for (v=0;v<9;v++){
			    	          maxRev.push(dataSite[maxRevenueListIndex[z]][v]);
			    	      }
			    	  }
			    	for (w=0;w<minRevenueListIndex.length;w++){
			    	  for (e=0;e<9;e++){
			    	      minRev.push(dataSite[minRevenueListIndex[w]][e]);
			    	  }
			    	}


		      }
                 //console.log("MAX " +maxRev);
                  //console.log("MIN " +minRev);
               revenueColorArr=[];
                    for(var x=0;x<listFromToColor.length;x++) {
                        revenueColorArr.push(listFromToColor[x][2]);
                        
                    }
                    
                
            $('input[type="checkbox"]', '#revenueColorTable').prop('checked', true);
    
    
        
            //Clear div of alert msg
             var element = document.getElementById("alertMsg");
              element.innerHTML = "";
    
            newTableDiv =  document.getElementById("tableDiv").innerHTML;
            
            //clear legend div content & add the new content
            document.getElementById("legendHeader").innerHTML ="";
            document.getElementById("legendHeader").innerHTML='<h6 style="color:white;font-weight:bold; font-size:3ex;display:inline-block;position: relative;top:5px;left:10px;">Legends</h6> <button  style=" background-color:transparent;color:white;font-weight:bold;outline:none;border: none;position: relative;left:100px;top:2px;" onClick="SelectUnselectAllRev()"  id="selectUnselect" >Unselect All</button>';
            
            document.getElementById("tableDiv").innerHTML ="";
            document.getElementById("tableDiv").innerHTML='<table id="mytable"><h5 style="color:#08526d ;font-weight:bold; font-size:2.5ex;position: relative; top:10px;left:20px;">Sites Revenue</h5><tr><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th><th style="position: relative;top: 5px;left:10px;"></th></tr></table>';
    
                    for(var x=0;x<listFromToColor.length;x++) {
                    
                        if(listFromToColor[x][2] =="#0080FF") {
                            listFromToColor[x][2] = "blue";
                        }
                        else if(listFromToColor[x][2] =="#FFDC00") {
                            listFromToColor[x][2] = "yellow";
                        }
                        else if(listFromToColor[x][2] =="red") {
                            listFromToColor[x][2] = "red";
                        }
                        else if(listFromToColor[x][2] =="#FF00FF") {
                            listFromToColor[x][2] = "pink";
                        }
                        else if(listFromToColor[x][2] =="#8A2BE2") {
                            listFromToColor[x][2] = "purple";
                        }
                        
                        if(listFromToColor[x][1] == "") {
                            var markup = "<tr> <td style='position: relative;top:17px;left:40px;'><input style='position: relative;top: -10px;' type='checkbox' name='revenueCheckbox' id='"+listFromToColor[x][2]+"'  value='"+revenueColorArr[x]+"' onclick='ShowHideRevMarkers(id)' /></td>"
                              +"<td style='position: relative;top:8px;left:60px;'><div  class='dot "  +listFromToColor[x][2]+ "'></div></td>"
                              +"<td style='position: relative;top: 5px;left:70px;'><label style='color:black;font-weight:bold;font-size:2ex;' > "+listFromToColor[x][0]+ "$" + " " + "& Above" + "</label></td></tr>";
                        
                        $("#mytable").append(markup);
                        }
                        else{
                            
                         var markup = "<tr> <td style='position: relative;top:17px;left:40px;'><input style='position: relative;top: -10px;' type='checkbox' name='revenueCheckbox' id='"+listFromToColor[x][2]+"'  value='"+revenueColorArr[x]+"' onclick='ShowHideRevMarkers(id)' /></td>"
                          +"<td style='position: relative;top:8px;left:60px;'><div  class='dot "  +listFromToColor[x][2]+ "'></div></td>"
                          +"<td style='position: relative;top: 5px;left:70px;'><label style='color:black;font-weight:bold;font-size:2ex;' > "+listFromToColor[x][0]+ "$" + " - " +listFromToColor[x][1]+ "$" + "</label></td></tr>";
                    
                    $("#mytable").append(markup);
                        }
                    }
                    newTableDiv =document.getElementById("tableDiv").innerHTML;
    
                    if (firstRangeList.length > 0  ){  
                        AddSiteMarkers(firstRangeList,map,fromToColor[2]);
                        if(fromToColor[2] =="#0080FF") {				
                            fromToColor[2] = "blue";
                    }
                        else if(fromToColor[2] =="#FFDC00") {				
                            fromToColor[2] = "yellow";
                    }
                        else if(fromToColor[2] =="#8A2BE2") {				
                            fromToColor[2] = "purple";
                    }
                        else if(fromToColor[2] =="#FF00FF") {				
                            fromToColor[2] = "pink";
                    }
                    
                          $("#"+fromToColor[2]+"").prop('checked',true);  
                                                           
                      }
                    if (secondRangeList.length > 0  ){  
                        
                        AddSiteMarkers(secondRangeList,map,fromToColor[5]);
                          if(fromToColor[5] =="#0080FF") {				
                                fromToColor[5] = "blue";
                        }
                         else if(fromToColor[5] =="#FFDC00") {				
                                fromToColor[5] = "yellow";
                        }
                        else if(fromToColor[5] =="#8A2BE2") {				
                                fromToColor[5] = "purple";
                        }
                        else if(fromToColor[5] =="#FF00FF") {				
                                fromToColor[5] = "pink";
                        }
                        
                          $("#"+fromToColor[5]+"").prop('checked',true);  
                          
                      }
                    if (thirdRangeList.length > 0  ){  
                        AddSiteMarkers(thirdRangeList,map,fromToColor[8]);
                          if(fromToColor[8] =="#0080FF") {				
                                fromToColor[8] = "blue";
                        }
                          else if(fromToColor[8] =="#FFDC00") {				
                                fromToColor[8] = "yellow";
                        }
                            else if(fromToColor[8] =="#8A2BE2") {				
                                fromToColor[8] = "purple";
                        }
                            else if(fromToColor[8] =="#FF00FF") {				
                                fromToColor[8] = "pink";
                        }
                        
                          $("#"+fromToColor[8]+"").prop('checked',true);  
                          
                      }
    
                    if (fourthRangeList.length > 0  ){ 
                        AddSiteMarkers(fourthRangeList,map,fromToColor[11]); 
                        if(fromToColor[11] =="#0080FF") {				
                            fromToColor[11] = "blue";
                    }
                        else if(fromToColor[11] =="#FFDC00") {				
                            fromToColor[11] = "yellow";
                    }
                        else if(fromToColor[11] =="#8A2BE2") {				
                            fromToColor[11] = "purple";
                    }
                        else if(fromToColor[11] =="#FF00FF") {				
                            fromToColor[11] = "pink";
                    }
    
                          $("#"+fromToColor[11]+"").prop('checked',true);  
                            
                        
                    }
    
                    if (fifthRangeList.length > 0  ){
                        AddSiteMarkers(fifthRangeList,map,fromToColor[14]);  
                        if(fromToColor[14] =="#0080FF") {				
                            fromToColor[14] = "blue";
                    }
                        else if(fromToColor[14] =="#FFDC00") {				
                            fromToColor[14] = "yellow";
                    }
                        else if(fromToColor[14] =="#8A2BE2") {				
                            fromToColor[14] = "purple";
                    }
                        else if(fromToColor[14] =="#FF00FF") {				
                            fromToColor[14] = "pink";
                    }
    
                      $("#"+fromToColor[14]+"").prop('checked',true);  
                        
                    }
                    //Disable all unchecked checkboxes
					if(firstRangeList.length ==0) {
					  if(fromToColor[2] =="#0080FF") {				
                            fromToColor[2] = "blue";
                    }
                        else if(fromToColor[2] =="#FFDC00") {				
                            fromToColor[2] = "yellow";
                    }
                        else if(fromToColor[2] =="#8A2BE2") {				
                            fromToColor[2] = "purple";
                    }
                        else if(fromToColor[2] =="#FF00FF") {				
                            fromToColor[2] = "pink";
                    }
                    
                          $("#"+fromToColor[2]+"").attr('disabled', true); 
                             
					}
					
					if(secondRangeList.length ==0) {
						if(fromToColor[5] =="#0080FF") {				
                                fromToColor[5] = "blue";
                        }
                         else if(fromToColor[5] =="#FFDC00") {				
                                fromToColor[5] = "yellow";
                        }
                        else if(fromToColor[5] =="#8A2BE2") {				
                                fromToColor[5] = "purple";
                        }
                        else if(fromToColor[5] =="#FF00FF") {				
                                fromToColor[5] = "pink";
                        }
                        
                          $("#"+fromToColor[5]+"").attr('disabled', true);  
                          
					}
					
					if(thirdRangeList.length ==0) {
					  if(fromToColor[8] =="#0080FF") {				
                                fromToColor[8] = "blue";
                        }
                          else if(fromToColor[8] =="#FFDC00") {				
                                fromToColor[8] = "yellow";
                        }
                            else if(fromToColor[8] =="#8A2BE2") {				
                                fromToColor[8] = "purple";
                        }
                            else if(fromToColor[8] =="#FF00FF") {				
                                fromToColor[8] = "pink";
                        }
                     
					 	    $("#"+fromToColor[8]+"").attr('disabled', true);
					}
					
					if(fourthRangeList.length  ==0) {
					       if(fromToColor[11] =="#0080FF") {				
                            fromToColor[11] = "blue";
                    }
                        else if(fromToColor[11] =="#FFDC00") {				
                            fromToColor[11] = "yellow";
                    }
                        else if(fromToColor[11] =="#8A2BE2") {				
                            fromToColor[11] = "purple";
                    }
                        else if(fromToColor[11] =="#FF00FF") {				
                            fromToColor[11] = "pink";
                    }
					 	  $("#"+fromToColor[11]+"").attr('disabled', true);
					}
					
					if(fifthRangeList.length  ==0) {
					 if(fromToColor[14] =="#0080FF") {				
                            fromToColor[14] = "blue";
                    }
                        else if(fromToColor[14] =="#FFDC00") {				
                            fromToColor[14] = "yellow";
                    }
                        else if(fromToColor[14] =="#8A2BE2") {				
                            fromToColor[14] = "purple";
                    }
                        else if(fromToColor[14] =="#FF00FF") {				
                            fromToColor[14] = "pink";
                    }
					 	  $("#"+fromToColor[14]+"").attr('disabled', true);
					}
                            
                      
        }   
    }


var colorsArray = ["-- Select Color --","Blue","Yellow","Red","Pink","Purple"];
var colorsHexArray = ["-- Select Color --","#0080FF","#FFDC00","red","#FF00FF","#8A2BE2"];
var rowCount=0;
var mainRevenueColor;
function addRow(){

	rowCount =  $("#revenueColorTable tr").length;

	
if(rowCount <=5) {
	colorOptions = "<select class='ui-widget ui-corner-all form-control'>";
	var colorOptionValue = "-- Select Color --";
	for(j=0; j<colorsArray.length; j++)
	{
		if(colorsArray[j] != "-- Select Color --" && colorsHexArray[j] != "-- Select Color --")
			colorOptionValue = colorsHexArray[j];
		colorOptions += "<option value='"+colorOptionValue+"' >"+colorsArray[j]+"</option>";
	}
	colorOptions += "</select>";

	var markup = "<tr><td style='text-align:center;'><input type='checkbox' name='record' style='margin-top:12px;'></td>"
      	+"<td name='from'><input type='text'  name='fromValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control' /></td>"
      	+"<td name='to'><input type='text' name='toValue' style='width:150px;' class='ui-widget ui-widget-content ui-corner-all form-control'></td>"
      	+"<td name='color'>"+colorOptions+"</td></tr>";

    $("#revenueColorTable").append(markup);


    selectColorChanges();
}
else {

	  var element = document.getElementById("alertMsg");
	  element.innerHTML = "No more allowed rows to add";

	  rowCount ==6;
}


	
}




fromToColor2 =[];
function SelectUnselectAllRev() {
	fromToColor2 =[];
	fromToColor2 = fromToColor;
	checkedColor=[];
	if(firstRangeListIndex.length >0) {
		if(fromToColor[2] =="blue") {				
	        fromToColor[2] = "#0080FF";
	}
	    else if(fromToColor[2] =="purple") {				
	        fromToColor[2] = "#8A2BE2";
	}
	   else if(fromToColor[2] =="pink") {				
	        fromToColor[2] = "#FF00FF";
	}

	   else if(fromToColor[2] =="yellow") {				
	        fromToColor[2] = "#FFDC00";
	}
	   
	    checkedColor.push(fromToColor[2]);
	    
	}

	if(secondRangeListIndex.length >0) {
	    
	    if(fromToColor[5] =="blue") {				
	        fromToColor[5] = "#0080FF";
	}
	    else if(fromToColor[5] =="purple") {				
	        fromToColor[5] = "#8A2BE2";
	}
	    else if(fromToColor[5] =="pink") {				
	        fromToColor[5] = "#FF00FF";
	}
	    else if(fromToColor[5] =="yellow") {				
	        fromToColor[5] = "#FFDC00";
	}
	    
	    checkedColor.push(fromToColor[5]);
	    }

	if(thirdRangeListIndex.length >0) {
	    
	    
	    if(fromToColor[8] =="blue") {				
	        fromToColor[8] = "#0080FF";
	}
	    else if(fromToColor[8] =="purple") {				
	        fromToColor[8] = "#8A2BE2";
	}
	    else if(fromToColor[8] =="pink") {				
	        fromToColor[8] = "#FF00FF";
	}
	    else if(fromToColor[8] =="yellow") {				
	        fromToColor[8] = "#FFDC00";
	}
	    
	    checkedColor.push(fromToColor[8]);
	    }

	if(fourthRangeListIndex.length >0) {
	    
	    if(fromToColor[11] =="blue") {				
	        fromToColor[11] = "#0080FF";
	}
	    else if(fromToColor[11] =="purple") {				
	        fromToColor[11] = "#8A2BE2";
	}
	    else if(fromToColor[11] =="pink") {				
	        fromToColor[11] = "#FF00FF";
	}

	    else if(fromToColor[11] =="yellow") {				
	        fromToColor[11] = "#FFDC00";
	}
	    checkedColor.push(fromToColor[11]);
	    }

	if(fifthRangeListIndex.length >0) {
	    
	    
	    if(fromToColor[14] =="blue") {				
	        fromToColor[14] = "#0080FF";
	}
	   else if(fromToColor[14] =="purple") {				
	        fromToColor[14] = "#8A2BE2";
	}
	    else if(fromToColor[14] =="pink") {				
	        fromToColor[14] = "#FF00FF";
	}
	   else if(fromToColor[14] =="yellow") {				
	        fromToColor[14] = "#FFDC00";
	}
	    
	    checkedColor.push(fromToColor[14]);
	    }



	var element = document.getElementById("selectUnselect");

	if (element.innerHTML == "Unselect All"){
		element.innerHTML = "Select All";


	   //Uncheck all checkboxes by value (by color)
	   for (var l= 0; l < checkedColor.length; l++) {
	   
	   
	   $('input:checkbox[value="' + checkedColor[l] + '"]').prop("checked", false);
	    }

	    
	for (var l= 0; l < checkedColor.length; l++) {
	    for (var i = 0; i < markerGroups[checkedColor[l]].length; i++) {

	            
	        var marker = markerGroups[checkedColor[l]][i];
	        //console.log(" markerGroups[color][i]"+markerGroups[checkedColor[l]][i]);
	        
	       //Show markers

	        if (!marker.getVisible()) {
	          marker.setVisible(true);
	        } 

	       //hide markers

	        else {
	          marker.setVisible(false);
	        }
	    }

	  //Hide yellow cluster icon
	    if(checkedColor[l] == "#FFDC00") {
	   	 color = "";
	        color = "#FFDC00";
	           if  ($('#yellow').prop("checked") == false) {
	               HideClusterIcon(markerGroups[color],color);
	               }
	           }	

	   //Hide blue cluster icon
	     else if(checkedColor[l] == "#0080FF") {
	   	  color = "";
	         color = "#0080FF";
	         if  ($('#blue').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     else if(checkedColor[l] == "red") {
	   	  color = "";
	         color = "red";
	         if  ($('#red').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     else if(checkedColor[l] == "#FF00FF") {
	   	  color = "";
	         color = "#FF00FF";
	         if  ($('#pink').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     else if(checkedColor[l] == "#8A2BE2") {

	   	  color = "";
	         color = "#8A2BE2";
	         if  ($('#purple').prop("checked") == false) {
	             HideClusterIcon(markerGroups[color],color);
	             }
	         }
	     }
	     }

	else {
	    element.innerHTML = "Unselect All";
	    
	    var sitesChecked = document.querySelectorAll('input[name="revenueCheckbox"]:checked');
			
			sitesRnge = [];
			sitesChecked.forEach((checkbox) => {
			    sitesRnge.push(checkbox.value);
			});
			if(sitesRnge.length >0){
					for(o=0;o<sitesRnge.length;o++){
					for (var i = 0; i < markerGroups[sitesRnge[o]].length; i++) {
				 	
				 	var marker = markerGroups[sitesRnge[o]][i];
				 	
					//Show markers
				 	if (!marker.getVisible()) {
				 	  marker.setVisible(true);
				 	} 
					//hide markers

				 	else {
				 	  marker.setVisible(false);
				 	}
				 }
					
			 if(sitesRnge[o] == "#FFDC00") {
				HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
					}	
			  else if(sitesRnge[o] == "#0080FF") {
					  HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
				  }
			  else if(sitesRnge[o] == "red") {
					  HideClusterIcon(markerGroups["red"],"red");
				  }
				  
		   else if(sitesRnge[o] == "#FF00FF") {	         
	             HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
	         }
				else if(sitesRnge[o] == "#8A2BE2") {
				   	HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
				  }
			  
			  }
			}

	          for (var l= 0; l < checkedColor.length; l++) {
	          $('input:checkbox[value="' + checkedColor[l] + '"]').prop("checked", true);
	                   }

	       
	      for (var l= 0; l < checkedColor.length; l++) {
	          for (var i = 0; i < markerGroups[checkedColor[l]].length; i++) {
	              
	              var marker = markerGroups[checkedColor[l]][i];
	              //console.log(" markerGroups[checkedColor][i]"+markerGroups[checkedColor[l]][i]);


	              if (!marker.getVisible()) {
	                marker.setVisible(true);
	              } else {
	                marker.setVisible(false);
	              }
	          }
	         }
	         ShowClusterIconRev(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,fromToColor);


	         

	     }
	  }
	              
   
             

function ShowClusterIconRev(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,color) {

	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
    var map = new google.maps.Map(document.getElementById("mapContainer"), {
		center:Nairobi ,
		zoom: 6
		});

    document.getElementById("mapContainer").innerHTML ="";
	var map = new google.maps.Map(document.getElementById("mapContainer"), {
		mapTypeControl: false,
	    center:Nairobi ,
		zoom: 6,
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

	if (firstRangeList.length>0){
		AddSiteMarkers(firstRangeList,map,fromToColor[2]);
		}
	if (secondRangeList.length>0){
		AddSiteMarkers(secondRangeList,map,fromToColor[5]);
        }               
    if (thirdRangeList.length>0){
        AddSiteMarkers(thirdRangeList,map,fromToColor[8]);
        }
    if (fourthRangeList.length>0){
         AddSiteMarkers(fourthRangeList,map,fromToColor[11]);
        }
    if (fifthRangeList.length>0){
        AddSiteMarkers(fifthRangeList,map,fromToColor[14]);
       } 
     //Add buttons on map
     const centerControlDiv = document.createElement("div");
	 LegendControl(centerControlDiv, map);
	 map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	 const DefaultZoomDiv = document.createElement("div");
	 DefaultZoomControl(DefaultZoomDiv, map);
	 map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	 const maxRevControlDiv = document.createElement("div");
	 MaxRevenueControl(maxRevControlDiv, map);
	 map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);
	 
	 const minRevControlDiv = document.createElement("div");
	 MinRevenueControl(minRevControlDiv, map);
	 map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);

	 }		

function SelectUnselectAll() {
	checkedColorr=[];

		if(maxRevenueListIndex.length >0) {
			checkedColorr.push("green");
			}

		if(minRevenueListIndex.length >0) {
			checkedColorr.push("#4D0207");
					}

		if(twoGListIndex.length >0) {
			checkedColorr.push("#FFDC00");
					}

		if(threeGListIndex.length  >0) {
			checkedColorr.push("#0080FF");
					}

		if(fourGListIndex.length  >0) {
			checkedColorr.push("red");
		}
		

		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = "Select All";
			 
				//Add checked checkboxes to colors array
				 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
				 colors = [];
				checkboxes.forEach((checkbox) => {
				    colors.push(checkbox.value);
				});

				
				//Uncheck all checkboxes by value (by color)
				for (var l= 0; l < colors.length; l++) {
				
				$('input:checkbox[value="' + colors[l] + '"]').prop("checked", false);
				 }

				 
			 for (var l= 0; l < colors.length; l++) {
				 for (var i = 0; i < markerGroups[colors[l]].length; i++) {
				 	
				 	var marker = markerGroups[colors[l]][i];
				 	//console.log(" markerGroups[color][i]"+markerGroups[colors[l]][i]);
				 	
					//Show markers

				 	if (!marker.getVisible()) {
				 	  marker.setVisible(true);
				 	} 
					//hide markers

				 	else {
				 	  marker.setVisible(false);
				 	}
				 }


			//Hide yellow cluster icon
					
			 if(colors[l] == "#FFDC00") {
					
					if  ($('#2gSites').prop("checked") == false) {
						HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
						}
					}	

			//Hide blue cluster icon

			  else if(colors[l] == "#0080FF") {
				  if  ($('#3gSites').prop("checked") == false) {
					  HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
					  }
				  }
			  else if(colors[l] == "red") {
				  if  ($('#4gSites').prop("checked") == false) {
					  HideClusterIcon(markerGroups["red"],"red");
					  }
				  }
			  
			  }
			  }

		  else {

			  element.innerHTML = "Unselect All";
			  
			 var sitesChecked = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			
			sitesRnge = [];
			sitesChecked.forEach((checkbox) => {
			    sitesRnge.push(checkbox.value);
			});
			
			if(sitesRnge.length >0){
					for(o=0;o<sitesRnge.length;o++){
					for (var i = 0; i < markerGroups[sitesRnge[o]].length; i++) {
				 	
				 	var marker = markerGroups[sitesRnge[o]][i];
				 	
					//Show markers

				 	if (!marker.getVisible()) {
				 	  marker.setVisible(true);
				 	} 
					//hide markers

				 	else {
				 	  marker.setVisible(false);
				 	}
				 }
					
			 if(sitesRnge[o] == "#FFDC00") {
				HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
					}	
			  else if(sitesRnge[o] == "#0080FF") {
					  HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
				  }
			  else if(sitesRnge[o] == "red") {
					  HideClusterIcon(markerGroups["red"],"red");
				  }

			  
			  }
			}
			

	  	 	 for (var l= 0; l < checkedColorr.length; l++) {
				$('input:checkbox[value="' + checkedColorr[l] + '"]').prop("checked", true);
						 }

	  	 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			 colors = [];
			checkboxes.forEach((checkbox) => {
			    colors.push(checkbox.value);
			});

		 for (var l= 0; l < checkedColorr.length; l++) {
			 for (var i = 0; i < markerGroups[checkedColorr[l]].length; i++) {
			 	
			 	var marker = markerGroups[checkedColorr[l]][i];


			 	if (!marker.getVisible()) {
			 	  marker.setVisible(true);
			 	} else {
			 	  marker.setVisible(false);
			 	}
			 }


			//Show yellow cluster icon
			 if(checkedColorr[l] == "#FFDC00") {
					if ($('#2gSites').prop("checked") == true) {
						ShowClusterIcon(Two_g_list,Three_g_list,four_g_list,"#FFDC00");
						 }
					 }
			//Show blue cluster icon
			else if(checkedColorr[l] == "#0080FF") {
					if ($('#3gSites').prop("checked") == true) {
						ShowClusterIcon(Two_g_list,Three_g_list,four_g_list,"#0080FF");
						 }
					 }
			else if(checkedColorr[l] == "red") {
					if ($('#4gSites').prop("checked") == true) {
						ShowClusterIcon(Two_g_list,Three_g_list,four_g_list,"red");
						 }
					 }
			
			 }
		 }
		 }
		 
function DeleteMarkers() {

	if( markers.length >0){
	for  (var i = 0; i < markers.length; i++) {
		var marker = markers[i];
		marker.setMap(null);
	}
markerGroups = {
					  "green": [],
					  "red": [],
					 "#FF8C00": [],
					  "#0080FF": [],
					  "#FFDC00": [],
					  "#4D0207": [],
					  "#FF00FF":[],
					  "#8A2BE2":[]

					};

	}
	
	  	 
	}


function UnselectAll(){

  $("input[name='legendCheckbox']").prop('checked',false);
	
}

function UnselectAllRev(){

	  $("input[name='revenueCheckbox']").prop('checked',false);
		
	}

function ShowClusterIcon(Two_g_list,Three_g_list,four_g_list,color) {

	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	var map = new google.maps.Map(document.getElementById("mapContainer"), {
		center:Nairobi ,
		zoom: 6
		});

	document.getElementById("mapContainer").innerHTML ="";

	var map = new google.maps.Map(document.getElementById("mapContainer"), {

                 mapTypeControl: false,
                 center:Nairobi ,
                 zoom: 6,
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

                             if (color=="#FFDC00"){
                                AddSiteMarkers(Two_g_list,map,"#FFDC00"); 
                                if (markerGroups["#0080FF"].length>0  ){
                                   //Clear previous blue markers
                                    HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
                                    //Add blue markers
                                AddSiteMarkers(Three_g_list,map,"#0080FF");
                                }
                                if (markerGroups["red"].length>0  ){
                                 HideClusterIcon(markerGroups["red"],"red");
                                 AddSiteMarkers(four_g_list,map,"red");
                                 }
                             
                        
                                }
                        
                                else if (color=="#0080FF"){
                                    AddSiteMarkers(Three_g_list,map,"#0080FF"); 
                                    if (markerGroups["#FFDC00"].length>0  ){
                                    HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
                                    AddSiteMarkers(Two_g_list,map,"#FFDC00");
                                    }
                                    if (markerGroups["red"].length>0  ){
                                    HideClusterIcon(markerGroups["red"],"red");
                                    AddSiteMarkers(four_g_list,map,"red");
                                     }
                                  
                                    }
                        
                                 else if (color=="red"){
                                        AddSiteMarkers(four_g_list,map,"red"); 
                                        if (markerGroups["#FFDC00"].length>0  ){
                                        HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
                                        AddSiteMarkers(Two_g_list,map,"#FFDC00");
                                        }
                                        if (markerGroups["#0080FF"].length>0  ){
                                         HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
                                         AddSiteMarkers(Three_g_list,map,"#0080FF");
                                         }
                                       
                                
                                
                                        }
                                    //Add buttons on map
                                	const centerControlDiv = document.createElement("div");
                                    LegendControl(centerControlDiv, map);
                                    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

                                    const DefaultZoomDiv = document.createElement("div");
                                    DefaultZoomControl(DefaultZoomDiv, map);
                                    map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

                                    const maxRevControlDiv = document.createElement("div");
                                    MaxRevenueControl(maxRevControlDiv, map);
                                    map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

                                    const minRevControlDiv = document.createElement("div");
                                    MinRevenueControl(minRevControlDiv, map);
                                    map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
                                    }

function ShowHideMarkers(color) {
	  
	//console.log("markers.length" +markerGroups[color].length);
	//console.log("color is"+color);
//console.log("markers is"+markerGroups[color])
	for ( x = 0; x < markerGroups[color].length; x++) {
	
	var marker = markerGroups[color][x];	

	//Show markers using checkboxes in popup
	if (!marker.getVisible()) {
	  marker.setVisible(true);

	}
	//hide markers using checkboxes in popup
	else {
	
	  if (infoWindow) {
            infoWindow.close();
        }  
        if (infoWindow2) {
            infoWindow2.close();
        }
        if (infowindow) {
            infowindow.close();
        }
	marker.setVisible(false); 
	}
		}


// Show/Hide ClusterIcon
	if(color == "#FFDC00") {
		if ($('#2gSites').prop("checked") == true) {
			ShowClusterIcon(Two_g_list,Three_g_list,four_g_list,"#FFDC00");
			}

		
		else {

			HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
  }	

	}

	if(color == "#0080FF") {
		if ($('#3gSites').prop("checked") == true) {
			ShowClusterIcon(Two_g_list,Three_g_list,four_g_list,"#0080FF");
			}

		
		else {

			HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
  }	

	}

	if(color == "red") {
		if ($('#4gSites').prop("checked") == true) {
			ShowClusterIcon(Two_g_list,Three_g_list,four_g_list,"red");
			}

		
		else {

			HideClusterIcon(markerGroups["red"],"red");
  }	

	}



}
function HideClusterIcon(MarkersArray,color) {

  //  alert("color of cluster is" +color);
    
    if(color == "#FFDC00") {
        for  ( i = 0; i<markerGroups["#FFDC00"].length; i++) {	

        //Remove yellow markers from map			
        markerGroups["#FFDC00"][i].setMap(null);
    }
    

    yellowCluster.clearMarkers();
    //clear yellow markers array
    markerGroups["#FFDC00"] =[];
    color == "" ;  
    }

 
    else if (color == "#0080FF") {

        for  ( j = 0; j<markerGroups["#0080FF"].length; j++) {	
            markerGroups["#0080FF"][j].setMap(null);
        }
        
        blueCluster.clearMarkers();
        markerGroups["#0080FF"] =[];
        color == "";
            
    }

    else if (color == "red") {

        for  ( r = 0; r<markerGroups["red"].length; r++) {	
            markerGroups["red"][r].setMap(null);
        }
        
        redCluster.clearMarkers();
        markerGroups["red"] =[];
            
    }
   else if (color == "#FF00FF") {


    	for  ( n = 0; n<markerGroups["#FF00FF"].length; n++) {	
            markerGroups["#FF00FF"][n].setMap(null);
        }
        
        pinkCluster.clearMarkers();
        markerGroups["#FF00FF"] =[];
            
    }
    
    else if (color == "#8A2BE2") {

        for  ( b = 0; b<markerGroups["#8A2BE2"].length; b++) {	
            markerGroups["#8A2BE2"][b].setMap(null);
        }
        
        purpleCluster.clearMarkers();
        markerGroups["#8A2BE2"] =[];
            
    }

    
}

function ShowHideRevMarkers(color) {


   if (color=="blue"){
       color="#0080FF"
   }
   if (color=="purple"){
       color="#8A2BE2"
   }
   if (color=="pink"){
       color="#FF00FF"
   }
   if(color =="yellow") {				
      color = "#FFDC00";
}

 
   for ( x = 0; x < markerGroups[color].length; x++) {
   
   var marker = markerGroups[color][x];	

   //Show markers using checkboxes in popup
   if (!marker.getVisible()) {
     marker.setVisible(true);

   }
   //hide markers using checkboxes in popup
   else {
   marker.setVisible(false); 
   }
       }


// Show/Hide ClusterIcon
   if(color == "#FFDC00") {
       if ($('#yellow').prop("checked") == true) {
           ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#FFDC00");
           }
       else {
           HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
 }	

   }

   else if(color == "#0080FF") {
       if ($('#blue').prop("checked") == true) {
           ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#0080FF");
           }

       
       else {
           HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
 }	

   }

  else if(color == "red") {
       if ($('#red').prop("checked") == true) {
    	   ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"red");
           }

       
       else {
           HideClusterIcon(markerGroups["red"],"red");
 }	

   }

   else if(color == "#8A2BE2") {
       if ($('#purple').prop("checked") == true) {
    	   ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#8A2BE2");
           }

       
       else {
           HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
 }	

   }

  else if(color == "#FF00FF") {
       if ($('#pink').prop("checked") == true) {
    	   ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,"#FF00FF");
           }

       else {
           HideClusterIcon(markerGroups["#FF00FF"],"#FF00FF");
 }	

   }


}
function ShowRevClusterIcon(firstRangeList,secondRangeList,thirdRangeList,fourthRangeList,fifthRangeList,color) {
  
    var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
    var map = new google.maps.Map(document.getElementById("mapContainer"), {
        center:Nairobi ,
        zoom: 6
        });

    document.getElementById("mapContainer").innerHTML ="";

    var map = new google.maps.Map(document.getElementById("mapContainer"), {

                 mapTypeControl: false,
                 center:Nairobi ,
                 zoom: 6,
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

color_group=[fromToColor[2],fromToColor[5],fromToColor[8],fromToColor[11],fromToColor[14]];
for ( x2 = 0; x2 < 5; x2++) {
    if (color_group[x2]!=undefined){
        if(color_group[x2]=="yellow"){
            color_group[x2]="#FFDC00";
        }
        else if(color_group[x2]=="red"){
            color_group[x2]="red";
        }
        else if(color_group[x2]=="blue"){
            color_group[x2]="#0080FF";
        }
        else if(color_group[x2]=="purple"){
            color_group[x2]="#8A2BE2";
        }
        else if(color_group[x2]=="pink"){
            color_group[x2]="#FF00FF";
        }        
    }
}
var color1="" ;                        
color_index=0;
for ( x1 = 0; x1 < 5; x1++) {
    if (color==color_group[x1]){
        color_index=x1;
    }
}

if (color_index==0){
    AddSiteMarkers(firstRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddSiteMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddSiteMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddSiteMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddSiteMarkers(fifthRangeList,map,color_group[4]);

    }
}

}


else if (color_index==1){
    AddSiteMarkers(secondRangeList,map,color_group[color_index]);
    if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddSiteMarkers(firstRangeList,map,color_group[0]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddSiteMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddSiteMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddSiteMarkers(fifthRangeList,map,color_group[4]);

    }
}

}

else if (color_index==2){
    AddSiteMarkers(thirdRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddSiteMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddSiteMarkers(firstRangeList,map,color_group[0]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddSiteMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddSiteMarkers(fifthRangeList,map,color_group[4]);

    }
}

}


else if (color_index==3){
    AddSiteMarkers(fourthRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddSiteMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddSiteMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddSiteMarkers(firstRangeList,map,color_group[0]);

    }
}
if (color_group[4]!=undefined){

    if (markerGroups[color_group[4]].length >0){
        HideClusterIcon(markerGroups[color_group[4]],color_group[4]);
        AddSiteMarkers(fifthRangeList,map,color_group[4]);

    }
}

}


else if (color_index==4){
    AddSiteMarkers(fifthRangeList,map,color_group[color_index]);
    if (color_group[1]!=undefined){

    if (markerGroups[color_group[1]].length >0){
        HideClusterIcon(markerGroups[color_group[1]],color_group[1]);
        AddSiteMarkers(secondRangeList,map,color_group[1]);

    }
}
if (color_group[2]!=undefined){

    if (markerGroups[color_group[2]].length >0){
        HideClusterIcon(markerGroups[color_group[2]],color_group[2]);
        AddSiteMarkers(thirdRangeList,map,color_group[2]);

    }
}
if (color_group[3]!=undefined){

    if (markerGroups[color_group[3]].length >0){
        HideClusterIcon(markerGroups[color_group[3]],color_group[3]);
        AddSiteMarkers(fourthRangeList,map,color_group[3]);

    }
}
if (color_group[0]!=undefined){

    if (markerGroups[color_group[0]].length >0){
        HideClusterIcon(markerGroups[color_group[0]],color_group[0]);
        AddSiteMarkers(firstRangeList,map,color_group[0]);

    }
}

}


//Add buttons on map
  const centerControlDiv = document.createElement("div");
  LegendControl(centerControlDiv, map);
  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

  const DefaultZoomDiv = document.createElement("div");
  DefaultZoomControl(DefaultZoomDiv, map);
  map.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

  const maxRevControlDiv = document.createElement("div");
  MaxRevenueControl(maxRevControlDiv, map);
  map.controls[google.maps.ControlPosition.TOP_CENTER].push(maxRevControlDiv);

  const minRevControlDiv = document.createElement("div");
  MinRevenueControl(minRevControlDiv, map);
  map.controls[google.maps.ControlPosition.TOP_CENTER].push(minRevControlDiv);
  }
function ShowHideAllWarehouseMarkers(color) {
	  
	console.log("color is"+color);
	for ( x = 0; x < markerGroups[color].length; x++) {
	
	var marker = markerGroups[color][x];	

	//Show markers using checkboxes in popup
	if (!marker.getVisible()) {
	  marker.setVisible(true);

	}
	//hide markers using checkboxes in popup
	else {
	
	  if (infoWindow) {
            infoWindow.close();
        }  
        if (infoWindow2) {
            infoWindow2.close();
        }
        if (infowindow) {
            infowindow.close();
        }
	marker.setVisible(false); 
	}
		}


// Show/Hide ClusterIcon
	if(color == "#FFDC00") {
		if ($('#2gSites').prop("checked") == true) {
			ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"#FFDC00");
			}

		
		else {

			HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
  }	

	}

	if(color == "#0080FF") {
		if ($('#3gSites').prop("checked") == true) {
			ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"#0080FF");
			}

		
		else {

			HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
  }	

	}

	if(color == "red") {
		if ($('#4gSites').prop("checked") == true) {
			ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"red");
			}

		
		else {

			HideClusterIcon(markerGroups["red"],"red");
  }	

	}
	
	if(color == "#8A2BE2") {
		if ($('#allWarehouseSites').prop("checked") == true) {
			ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"#8A2BE2");
			}

		
		else {

			HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
  }	

	}

}


function SelectUnselectAllWareSites() {
	checkedColorr=[];

		if(maxRevenueListIndex.length >0) {
			checkedColorr.push("green");
			}

		if(minRevenueListIndex.length >0) {
			checkedColorr.push("#4D0207");
					}

		if(twoGListIndex.length >0) {
			checkedColorr.push("#FFDC00");
					}

		if(threeGListIndex.length  >0) {
			checkedColorr.push("#0080FF");
					}

		if(fourGListIndex.length  >0) {
			checkedColorr.push("red");
		}
		if(warehouseSites.length  >0) {
			checkedColorr.push("#8A2BE2");
		}
		
		var element = document.getElementById("selectUnselect");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = "Select All";
			 
				//Add checked checkboxes to colors array
				 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
				 colors = [];
				checkboxes.forEach((checkbox) => {
				    colors.push(checkbox.value);
				});
				console.log("colors.length" +colors);

				
				//Uncheck all checkboxes by value (by color)
				for (var l= 0; l < colors.length; l++) {
				
				$('input:checkbox[value="' + colors[l] + '"]').prop("checked", false);
				 }

				 
			 for (var l= 0; l < colors.length; l++) {
				 for (var i = 0; i < markerGroups[colors[l]].length; i++) {
				 	
				 	var marker = markerGroups[colors[l]][i];
				 	console.log("ZZZZ markerGroups[color][i]"+markerGroups[colors[l]][i]);
				 	
					//Show markers

				 	if (!marker.getVisible()) {
				 	  marker.setVisible(true);
				 	} 
					//hide markers

				 	else {
				 	  marker.setVisible(false);
				 	}
				 }


			//Hide yellow cluster icon
					
			 if(colors[l] == "#FFDC00") {
					
					if  ($('#2gSites').prop("checked") == false) {
						HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
						}
					}	

			//Hide blue cluster icon

			  else if(colors[l] == "#0080FF") {
				  if  ($('#3gSites').prop("checked") == false) {
					  HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
					  }
				  }
			  else if(colors[l] == "red") {
				  if  ($('#4gSites').prop("checked") == false) {
					  HideClusterIcon(markerGroups["red"],"red");
					  }
				  }
			   else if(colors[l] == "#8A2BE2") {
				   
				  if  ($('#allWarehouseSites').prop("checked") == false) {
					  HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
					  }
				  }
			  }
			  }

		  else {

			  element.innerHTML = "Unselect All";

 			var sitesChecked = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			
			sitesRnge = [];
			sitesChecked.forEach((checkbox) => {
			    sitesRnge.push(checkbox.value);
			});
			if(sitesRnge.length >0){
					for(o=0;o<sitesRnge.length;o++){
					for (var i = 0; i < markerGroups[sitesRnge[o]].length; i++) {
				 	
				 	var marker = markerGroups[sitesRnge[o]][i];
				 	
					//Show markers

				 	if (!marker.getVisible()) {
				 	  marker.setVisible(true);
				 	} 
					//hide markers

				 	else {
				 	  marker.setVisible(false);
				 	}
				 }
					
			 if(sitesRnge[o] == "#FFDC00") {
				HideClusterIcon(markerGroups["#FFDC00"],"#FFDC00");
					}	
			  else if(sitesRnge[o] == "#0080FF") {
					  HideClusterIcon(markerGroups["#0080FF"],"#0080FF");
				  }
			  else if(sitesRnge[o] == "red") {
					  HideClusterIcon(markerGroups["red"],"red");
				  }
				else if(sitesRnge[o] == "#8A2BE2") {
				   	HideClusterIcon(markerGroups["#8A2BE2"],"#8A2BE2");
				  }
			  
			  }
			}
			
	  	 	 for (var l= 0; l < checkedColorr.length; l++) {
				$('input:checkbox[value="' + checkedColorr[l] + '"]').prop("checked", true);
						 }

	  	 var checkboxes = document.querySelectorAll('input[name="legendCheckbox"]:checked');
			 colors = [];
			checkboxes.forEach((checkbox) => {
			    colors.push(checkbox.value);
			});

		 for (var l= 0; l < checkedColorr.length; l++) {
			 for (var i = 0; i < markerGroups[checkedColorr[l]].length; i++) {
			 	
			 	var marker = markerGroups[checkedColorr[l]][i];


			 	if (!marker.getVisible()) {
			 	  marker.setVisible(true);
			 	} else {
			 	  marker.setVisible(false);
			 	}
			 }


			//Show yellow cluster icon
			 if(checkedColorr[l] == "#FFDC00") {
					if ($('#2gSites').prop("checked") == true) {
						ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"#FFDC00");
						 }
					 }
			//Show blue cluster icon
			else if(checkedColorr[l] == "#0080FF") {
					if ($('#3gSites').prop("checked") == true) {
						ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"#0080FF");
						 }
					 }
			else if(checkedColorr[l] == "red") {
					if ($('#4gSites').prop("checked") == true) {
						ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"red");
						 }
			}
			
			else if(checkedColorr[l] == "#8A2BE2") {
					if ($('#allWarehouseSites').prop("checked") == true) {
						ShowClusterIconAllWarehouse(Two_g_list,Three_g_list,four_g_list,warehouseSites,"#8A2BE2");
						 }
					 }
			
			 }
		 }
		 }


 $(function(){
 
$(".deleteRow").click(function () {
	slctDel = [];
	
	$("#revenueColorTable").find('input[name="record"]').each(function () {
   	if ($(this).is(":checked")) {
       	slctDel.push($(this).parent().parent().children('td[name="color"]').children('select').val());
          // console.log("The selected delete is " + slctDel);
		 }
     });
        
        
        for (i = 0; i <= slctDel.length; ++i) {
        	if (slctDel.length == 0) {
           	alert(' Select Row(s) to Delete');
               return false;
            }
          }

         $("#revenueColorTable").find('input[name="record"]').each(function () {
         	if ($(this).is(":checked")) {
           	$(this).parents("tr").remove();
           	
            }

           });

         
          
         
}); // end delete row fct 


$('.modal-dialog').draggable({
	handle: ".modal-header, .modal-footer"
	});

//Reset popup position after it has been dragged and closed
$('body').on('hidden.bs.modal', function() {
$('.modal-dialog').css({'top': '', 'left':''});
})



//Select all checkbox in  table
$('body').on('click', '#selectAll', function  () {
	if ($(this).hasClass('allChecked')) {
		$('input[type="checkbox"]', '#revenueColorTable').prop('checked', false);
	} 
	else {
		$('input[type="checkbox"]', '#revenueColorTable').prop('checked', true);
	}
	
	$(this).toggleClass('allChecked');
	       				
}) // end select checkbox fct 



$('.modal-content').resizable({
    handles: "e"
});

//Reset the popup to original size after resizing 
$('#Modal').on('hidden.bs.modal', function() {
$(this).find('.modal-content').css({'width': '700px', 'height': ''});
})

// Minimize and Maximize fct for popup
	$(".modalMinimize").on("click", function(){
	
	$(".modal-body").slideToggle();
	$(".modal-footer").slideToggle();
	
	//Toggle between minimize/maximize icons
	var iconToChange = $('.icon-to-change');
		if(iconToChange.hasClass('fa-window-restore')){
     		iconToChange.removeClass('fa-window-restore')
    		            .addClass('fa-minus')
		}
		else{
     		iconToChange.addClass('fa-window-restore')
    		             .removeClass('fa-minus')
		}    		         
	}); // end minimize/maximize fct

      
 $('select').on('change', function () {
 	if (this.value == "#0080FF" ) {
 		$(this).closest('tr').css('background-color','#0080FF');
	        
	 } 
	else if (this.value == "#FFDC00") {
	  	 $(this).closest('tr').css('background-color','#FFDC00');
	}
	else if (this.value == "red") {
	 	 $(this).closest('tr').css('background-color','red');
	   } 

	else if (this.value == "#FF00FF") {
	 	 $(this).closest('tr').css('background-color','#FF00FF');
	   
	   	    } 

	else if (this.value == "#8A2BE2") {
	    	 $(this).closest('tr').css('background-color','#8A2BE2');
		    
	   	    } 
	  else if (this.value == "-- Select Color --") {
	   	 $(this).closest('tr').css('background-color','white');
		  } 

	   
	       
	});

 });
 
      
  