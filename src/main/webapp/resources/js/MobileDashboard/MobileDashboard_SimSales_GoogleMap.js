var selectedItems=[];
var txt;
var AllAgentsArr=[];
var checked;

//Add legend button under zoom control on map1
function LegendControlSales(controlDiv, map1) {
	
    const controlUI = document.createElement("div");
    controlUI.style.backgroundColor = "white";
    controlUI.style.border = "8px solid white";
   // controlUI.style.borderRadius = "3px";
    controlUI.style.cursor = "pointer";
    controlUI.style.marginLeft = "10px";
    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"  onClick="ShowHideDiv()"><i class="fas fa-arrow-right fa-lg "></i></button>';
    controlUI.title = "Open Legend";
    controlDiv.appendChild(controlUI);

  }
  
//Add defaultZoom button under zoom control on map1
function DefaultZoomControlSales(controlDiv2, map1) {
	
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

    	var Nairobi=new google.maps.LatLng(-0.1,36);
        map1.setCenter(Nairobi);
        map1.setZoom(5.75);
        
        if (infoWindow) {
            infoWindow.close();
        }  
        if (infoWindow2) {
            infoWindow2.close();
        }       
        
              });

  }
  

var infoWindow ;
function MaxAgentSalesControl(controlDiv3, map1) {
	
	 const controlUI3 = document.createElement("div");
	 controlUI3.style.backgroundColor = "white";
	 controlUI3.style.border = "8px solid white";
	 controlUI3.style.boxShadow = "0 5px 6px rgba(0,0,0,.3)";
	 controlUI3.style.cursor = "pointer";
	 controlUI3.style.marginTop = "11px";
	 controlUI3.style.marginLeft = "5px";
	 controlUI3.style.height = "38px";
	 controlUI3.title = "Go to Maximum Agent Sales";
	 controlDiv3.appendChild(controlUI3);


    const controlText = document.createElement("div");
    controlText.style.color = "rgb(25,25,25)";
    controlText.style.fontFamily = "Roboto,Arial,sans-serif";
    controlText.style.fontSize = "2px";
    controlText.style.lineHeight = "3px";
    controlText.style.paddingLeft = "5px";
    controlText.style.paddingRight = "5px";
    controlUI3.innerHTML = '<button style="border:none;outline:none; background:white;fontFamily:Roboto,Arial,sans-serif;font-Size:15px" >Maximum Agent Sales</button>';
    controlUI3.appendChild(controlText);
    
    controlUI3.addEventListener("click", () => {

    	 if (infoWindow) {
             infoWindow.close();
         }  
			
        if(maxCount.length >0) {            
        	var latMaxAgent=maxCount[3];
    		var lngMaxAgent=maxCount[4];
    		
            const maxAgentposition = new google.maps.LatLng(latMaxAgent,lngMaxAgent);
            var agentName="<b style='font-size:13px;'><u>Agent: </u></b>"+maxCount[2] ;
            var agentMSISDN="<b style='font-size:13px;'><u>MSISDN: </u></b>"+maxCount[5] ;
    		var agentLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+latMaxAgent;
    		var agentLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lngMaxAgent;
    		var simCount="<b style='font-size:13px;'><u>Agent Sales Count:</u> </b>"+maxCount[0];
    		 infoWindow = new google.maps.InfoWindow();

             //Set Content of InfoWindow.
             infoWindow.setContent( agentName + '<br />' + agentMSISDN + '<br />' +agentLat+ '<br />' +agentLong+ '<br />' +simCount);
             
                
                
         if  ($('#maxCount').is(':checked') == true ) {
             
	  		map1.setZoom(15);
	       	map1.setCenter(maxAgentposition);
	      //Set Position of InfoWindow.
	        infoWindow.setPosition(maxAgentposition);
	        infoWindow.open(map1);       	 

         }
         

        }		
      });
    
  }
  
 var infoWindow2;
function MinAgentSalesControl(controlDiv4, map1) {
	
    const controlUI4 = document.createElement("div");
    controlUI4.style.backgroundColor = "white";
    controlUI4.style.border = "8px solid white";
   // controlUI4.style.borderRadius = "3px";
    controlUI4.style.boxShadow = "0 5px 6px rgba(0,0,0,.3)";
    controlUI4.style.cursor = "pointer";
    controlUI4.style.marginLeft = "5px";
    controlUI4.style.marginTop = "11px";
    controlUI4.style.height = "38px";
    controlUI4.title = "Go to Minimum Agent Sales";
    controlDiv4.appendChild(controlUI4);


    const controlText = document.createElement("div");
    controlText.style.color = "rgb(25,25,25)";
    controlText.style.fontFamily = "Roboto,Arial,sans-serif";
    controlText.style.fontSize = "2px";
    controlText.style.lineHeight = "3px";
    controlText.style.paddingLeft = "5px";
    controlText.style.paddingRight = "5px";
    controlUI4.innerHTML = '<button style="border:none;outline:none; background:white;fontFamily:Roboto,Arial,sans-serif;font-Size:15px" >Minimum Agent Sales</button>';
    controlUI4.appendChild(controlText);
    

    controlUI4.addEventListener("click", () => {
    	 if (infoWindow2) {
             infoWindow2.close();
         }  
        
    	 if(minCount.length >0) {
    	 
    		 var latMinAgent=minCount[3];
     		var lngMinAgent=minCount[4];
             const minAgentposition = new google.maps.LatLng(latMinAgent,lngMinAgent);
             
            var agentName="<b style='font-size:13px;'><u>Agent: </u></b>"+minCount[2] ;
            var agentMSISDN="<b style='font-size:13px;'><u>MSISDN: </u></b>"+minCount[5];
     		var agentLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+latMinAgent;
     		var agentLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lngMinAgent;
     		var simCount="<b style='font-size:13px;'><u>Agent Sales Count:</u> </b>"+minCount[0];
     		 infoWindow2 = new google.maps.InfoWindow();

              //Set Content of InfoWindow.
              infoWindow2.setContent( agentName + '<br />' + agentMSISDN + '<br />' +agentLat+ '<br />'  +agentLong+ '<br />' +simCount);
              

             if  ($('#minCount').is(':checked') == true ) {
            	map1.setZoom(15);
    	     	map1.setCenter(minAgentposition);
    	       //Set Position of InfoWindow.
    	       infoWindow2.setPosition(minAgentposition);
    	       infoWindow2.open(map1);
     	        }	

    	 }
      });
 }
 

 
 function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

var markerGroupsSales = {
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
//selectedSites=[];
//var concatenatedSites ="";
function AddAgentMarker(lst,map1,color){

var ctx = getContextPath();
	markers=[];
	//Set zoom level
	map1.setZoom(5.75);

		var Nairobi=new google.maps.LatLng(-1.286389,36.817223);	
		map1.setCenter(Nairobi);
		
		for(i=0;i<lst.length;i++){
	     
				markerId=lst[i][1];
				AgentID=lst[i][1];
				
				infowindow = new google.maps.InfoWindow();

				var latAgent=lst[i][3];
				var lngAgent=lst[i][4];
				var agentMsisdn=lst[i][5];
				const position = new google.maps.LatLng(latAgent,lngAgent);

				var AgentName=lst[i][2];	
				
				var agentName="<b style='font-size:13px;'><u>Agent: </u></b>"+AgentName;
				var agMsisdn="<b style='font-size:13px;'><u>MSISDN:</u> </b>"+agentMsisdn;
				var agentLat="<b style='font-size:13px;'><u>Latitude: </u></b>"+latAgent;
				var agentLong="<b style='font-size:13px;'><u>Longtitude:</u> </b>"+lngAgent;
				var agentCount="<b style='font-size:13px;'><u>Agent Sales Count:</u> </b>"+lst[i][0];
				
				
				
					
				var data="<div>"+agentName+"</br>"+agMsisdn+"</br>"+agentLat+"</br>"+agentLong+"</br>"+agentCount+ "</div>";			

				//Add markers on map1
				const marker = new google.maps.Marker({
			        position: position,
			        data:data,
			        ID:markerId,
			        Lat:latAgent,
			        Long:lngAgent,
			        Name:AgentName,
			        AgentId:AgentID,
			        AgentMsisdn: agentMsisdn,
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

					//Add markers to array depending on Salescolors
				    if (!markerGroupsSales[color]) markerGroupsSales[color] = [];
						  markerGroupsSales[color].push(marker);
	 
					
				google.maps.event.addListener(marker, "click", function (e) {

				     	infowindow.setContent(this.data); 
			        	infowindow.open(map1,this);

						var LatSelected=this.Lat;
						var LongSelected=this.Long;
						var NameSelected=this.Name;
						var IDSelected=this.ID;
						var MsisdnSelected=this.AgentMsisdn;
						
						
						var agntSelected = NameSelected ;
					
 						
						
						
						//document.getElementById("agent").value =IDSelected+";"+MsisdnSelected+";"+NameSelected;

						//Add sites name in text area
						var agentInput=IDSelected+";"+MsisdnSelected+";"+NameSelected;
					    var agentNameInp = agentInput.split(";");
					    agentNameInp  = agentNameInp[2]+"  ";
					    
				    		
						
				 			});
				google.maps.event.addListener(marker, "dblclick", function (e) {

			     	map1.setZoom(15);
			     	map1.setCenter(position);

			 			});
	 			
		  				}

			//Define clusters
		 if (color=="#FFDC00"){
		 	yellowCluster = new MarkerClusterer(map1,markerGroupsSales["#FFDC00"], {ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url: ctx+'/resources/clusterIcons/yellowCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
		 	},
		 	], });
		 	}
		 	
		 	else if (color=="#0080FF"){
		 	
		 	blueCluster = new MarkerClusterer(map1,markerGroupsSales["#0080FF"], {ignoreHiddenMarkers: true, 
		 	styles: [
		 	{
		 	url: ctx+'/resources/clusterIcons/blueCluster.png',
		 	height: 65,
		 	width:65,
		 	anchorText:[-5,-5]
			},
			], });
			}
			
			else if (color=="red"){
				redCluster = new MarkerClusterer(map1,markerGroupsSales["red"], {ignoreHiddenMarkers: true, 
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
					pinkCluster = new MarkerClusterer(map1,markerGroupsSales["#FF00FF"], {ignoreHiddenMarkers: true, 
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
				 purpleCluster = new MarkerClusterer(map1,markerGroupsSales["#8A2BE2"], {ignoreHiddenMarkers: true, 
					    styles: [
					        {
					          url: ctx+'/resources/clusterIcons/purpleCluster.png',
					          height: 60,
					          width:60,
					          anchorText:[-3,-3]
					        },
					      ], });
		      }
		      
			
		      
			
			 markerClusterer = new MarkerClusterer(map1,  markerGroupsSales["green"], {minimumClusterSize: 100000,ignoreHiddenMarkers: true});
			 markerClusterer = new MarkerClusterer(map1,  markerGroupsSales["#4D0207"], {minimumClusterSize: 100000,ignoreHiddenMarkers: true});
				

	
} // end of AddAgentMarker function

function ShowHideDiv(){
	$("#legendSimSales").toggle();
}

function AddSelectedAgentMarker(lst,map1) {

	if (lst.length == 0) {
	 	 $("#firstCountRange").attr('disabled', true);
	     $("#secondCountRange").attr('disabled', true);
	     $("#thirdCountRange").attr('disabled', true);
	     $("#fourthCountRange").attr('disabled', true);
	 	 $("#fifthCountRange").attr('disabled', true);
	 	 $("#maxCount").attr('disabled', true);
	 	 $("#minCount").attr('disabled', true);
	 		
		var element = document.getElementById("selectUnselectSales");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {

	  $("#maxCount").attr('disabled', true);
	  $("#minCount").attr('disabled', true);
	  $("#firstCountRange").attr('disabled', false);
	  $("#secondCountRange").attr('disabled', false);
	  $("#thirdCountRange").attr('disabled', false);
	  $("#fourthCountRange").attr('disabled', false);
	  $("#fifthCountRange").attr('disabled', false);
	       	    
		
	var element = document.getElementById("selectUnselectSales");

	 if (element.innerHTML == " "){
		 element.innerHTML = "Unselect All";
	 }
	 if (element.innerHTML == "Unselect All"){
		 element.innerHTML = "Select All";
	 }
	  if (element.innerHTML == "Select All"){
		 element.innerHTML = "Unselect All";
	 }
	 
	  maxSimCountListIndex=[];
	  minSimCountListIndex=[];
	  maxCountList=[];
	  minCountList=[] ;

	  maxCount=[];
	  minCount=[];
	
	var dataSite = lst;
	disableData = dataSite;
	
	firstCountRangeListIndex=[];
	secondCountRangeListIndex=[];
	thirdCountRangeListIndex=[];
	fourthCountRangeListIndex=[];
	fifthCountRangeListIndex=[];
	
	
	firstCountRange=[];
	secondCountRange=[];
	thirdCountRange=[];
	fourthCountRange=[];
	fifthCountRange=[];

	// add the list index arrays for count ranges 
	for (j=0;j<dataSite.length;j++){
	    if (dataSite[j][0] >= parseFloat(1) && dataSite[j][0] <= parseFloat(2) ) {
	    	firstCountRangeListIndex.push(j);
	    	firstCountRange.push(dataSite[j]);
	    	

	    }
	    else if (dataSite[j][0] >=parseFloat(3) && dataSite[j][0] <= parseFloat(5)) {
	    	secondCountRangeListIndex.push(j);
	    	secondCountRange.push(dataSite[j]);
	    	

	    }
	    else if (dataSite[j][0] >=parseFloat(6) && dataSite[j][0] <= parseFloat(10)) {
	    	thirdCountRangeListIndex.push(j);
	    	thirdCountRange.push(dataSite[j]);
	    	

	    }
	    else if (dataSite[j][0] >=parseFloat(11) && dataSite[j][0] <= parseFloat(20)) {
	    	fourthCountRangeListIndex.push(j);
	    	fourthCountRange.push(dataSite[j]);
	    	

	    }
	    else if (dataSite[j][0] >=parseFloat(21)) {
	    	fifthCountRangeListIndex.push(j);
	    	fifthCountRange.push(dataSite[j]);
	    	

	    }

	}


		 if (firstCountRangeListIndex.length > 0  ){  
	    	    AddAgentMarker(firstCountRange,map1,"#FFDC00");
	       	    $("#firstCountRange").prop('checked',true);  
	        }
		 if (secondCountRangeListIndex.length > 0  ){  
	    	    AddAgentMarker(secondCountRange,map1,"#0080FF");
	       	    $("#secondCountRange").prop('checked',true);  
	        }
		 if (thirdCountRangeListIndex.length > 0  ){  
	    	    AddAgentMarker(thirdCountRange,map1,"red");
	       	    $("#thirdCountRange").prop('checked',true);  
	        }
		 if (fourthCountRangeListIndex.length > 0  ){  
	    	    AddAgentMarker(fourthCountRange,map1,"#FF00FF");
	       	    $("#fourthCountRange").prop('checked',true);  
	        }
		 if (fifthCountRangeListIndex.length > 0  ){  
	    	    AddAgentMarker(fifthCountRange,map1,"#8A2BE2");
	       	    $("#fifthCountRange").prop('checked',true);  
	        }
	       
	    var element = document.getElementById("min-div");
		element.classList.remove("dotBlue");
		element.classList.remove("dotRed"); 
		element.classList.remove("dotYellow");
		element.classList.remove("dotPink"); 
		element.classList.remove("dotPurple"); 
		element.classList.add("dot-min");
		
		   var element = document.getElementById("max-div");
		element.classList.remove("dotBlue");
		element.classList.remove("dotRed"); 
		element.classList.remove("dotYellow");
		element.classList.remove("dotPink"); 
		element.classList.remove("dotPurple");
		element.classList.add("dot-max");
		
	//Disable all unchecked checkboxes
  
	if(firstCountRangeListIndex.length ==0) {
	  $("#firstCountRange").attr('disabled', true);
	}
	if(secondCountRangeListIndex.length ==0) {
	 	  $("#secondCountRange").attr('disabled', true);
	}
	if(thirdCountRangeListIndex.length ==0) {
	 	  $("#thirdCountRange").attr('disabled', true);
	}
	if(fourthCountRangeListIndex.length  ==0) {
	 	  $("#fourthCountRange").attr('disabled', true);
	}
	if(fifthCountRangeListIndex.length  ==0) {
	 	  $("#fifthCountRange").attr('disabled', true);
	} 
    
   
  } 
}//end of AddSelectedAgentMarker function 

maxList=[];
minList=[];
maxMinList=[];
function MaxMinSetColor(lst,map1,checkedChckbox){
	
	maxList=[];
	minList=[];
	maxMinList=[];
	maxCount=[];
	minCount=[];
	disableData =[];
	if (lst.length == 0) {
		 $("#firstCountRange").attr('disabled', true);
	     $("#secondCountRange").attr('disabled', true);
	     $("#thirdCountRange").attr('disabled', true);
	     $("#fourthCountRange").attr('disabled', true);
	 	 $("#fifthCountRange").attr('disabled', true);
	 	 $("#maxCount").attr('disabled', true);
	 	 $("#minCount").attr('disabled', true);


			var element = document.getElementById("selectUnselectSales");

			 if (element.innerHTML == "Unselect All"){
				 element.innerHTML = " ";
			 }
		
		}
		else {
		
			 $("#firstCountRange").attr('disabled', false);
		     $("#secondCountRange").attr('disabled', false);
		     $("#thirdCountRange").attr('disabled', false);
		     $("#fourthCountRange").attr('disabled', false);
		 	 $("#fifthCountRange").attr('disabled', false);
		 	 $("#maxCount").attr('disabled', false);
		 	 $("#minCount").attr('disabled', false);
		 	 var element = document.getElementById("selectUnselectSales");

			 if (element.innerHTML == " "){
				 element.innerHTML = "Unselect All";
			 }
	 		
			
			 
	  var dataSite = lst;
		
		  
	  var minSimCount = -1;
      var maxSimCount = -1;
      
	  maxSimCountListIndex=[];
	  minSimCountListIndex=[];
	  maxCountList=[];
	  minCountList=[] ;  
	  salesCountMin=[]; 
	 
	  

	if (dataSite.length>=1){

		var minSimCount = dataSite[0][0];
	    var maxSimCount = dataSite[0][0];
	    
	    for (i=0;i<dataSite.length;i++){
		    
	        //Set max sales count 
	        if (dataSite[i][0] >= maxSimCount){
	        
	        
	       	if (dataSite[i][0] != maxSimCount){
	       		maxSimCountListIndex = [];
	        	}
	        
	       		maxSimCount = dataSite[i][0];
	        	
	        	//push index of max SIM CARD COUNT
	        	maxSimCountListIndex.push(i);
	         
	            
	            }
	         //Set min sales count 
	            if (dataSite[i][0] <= minSimCount ){
	            
	    		     if (dataSite[i][0] != minSimCount){
	    		    	 minSimCountListIndex = [];
	    		      }
	    		     minSimCount = dataSite[i][0];
	              
	              //push index of min SIM CARD COUNT
	              minSimCountListIndex.push(i);
	              
	              }
	              
	              
	        }       
	          }

	 
		  firstCountRangeListIndex=[];
		  secondCountRangeListIndex=[];
		  thirdCountRangeListIndex=[];
		  fourthCountRangeListIndex=[];
		  fifthCountRangeListIndex=[];
	 
		 if (dataSite.length ==1){
		 	UnselectAllSales();
		 	AddSelectedAgentMarker(dataSite,map1);
		 	maxMinList=dataSite;

		}

		 else {
			 $("#firstCountRange").attr('disabled', true);
		     $("#secondCountRange").attr('disabled', true);
		     $("#thirdCountRange").attr('disabled', true);
		     $("#fourthCountRange").attr('disabled', true);
		 	 $("#fifthCountRange").attr('disabled', true);
		 	 $("#maxCount").attr('disabled', false);
		 	 $("#minCount").attr('disabled', false);

		 	 
	    		if(maxSimCount == minSimCount) {
	    			minSimCountListIndex =[];
	    		}


		 	// coloring max sales agent
			  for (z=0;z<maxSimCountListIndex.length;z++){
			      for (v=0;v<7;v++){
			          //Add max sales count  to max sales array
			           maxCountList.push(dataSite[maxSimCountListIndex[z]][v]);
			           maxCount.push(dataSite[maxSimCountListIndex[z]][v]);
			      }

			      salesCountMax=[maxCount];
	   maxMinList.push([dataSite[maxSimCountListIndex[z]][0],dataSite[maxSimCountListIndex[z]][1],dataSite[maxSimCountListIndex[z]][2],dataSite[maxSimCountListIndex[z]][3],dataSite[maxSimCountListIndex[z]][4],dataSite[maxSimCountListIndex[z]][5],dataSite[maxSimCountListIndex[z]][6]]);
	   
	   
	   
	   AddAgentMarker([maxCountList],map1,"green");


	 if (maxCountList[0] >= parseFloat(1) && maxCountList[0] <= parseFloat(2) ) {
		 console.log("you are here ... ");
		 var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotYellow");

    }
    else if (maxCountList[0] >= parseFloat(3) && maxCountList[0] <= parseFloat(5) ) {    	
        var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotBlue");

    }
    else if (maxCountList[0] >= parseFloat(6) && maxCountList[0] <= parseFloat(10) ) {    	
    	var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotRed");

    }

    else if (maxCountList[0] >= parseFloat(11) && maxCountList[0] <= parseFloat(20) ) {    	
    	var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotPink");

    }
    else if (maxCountList[0] >= parseFloat(21) ) {    	
    	var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotPurple");

    }
	//Clear array before adding another max sales site
	maxCountList=[];
	$("#maxCount").prop('checked',true);  
	  
	}

			//coloring min sales count
				for (w=0;w<minSimCountListIndex.length;w++){
				  for (e=0;e<7;e++){
				      //Add min sales count to min count array
		        minCountList.push(dataSite[minSimCountListIndex[w]][e]);
		        minCount.push(dataSite[minSimCountListIndex[w]][e]);
				      
				      
				  }
	salesCountMin =[minCount];
	maxMinList.push([dataSite[minSimCountListIndex[w]][0],dataSite[minSimCountListIndex[w]][1],dataSite[minSimCountListIndex[w]][2],dataSite[minSimCountListIndex[w]][3],dataSite[minSimCountListIndex[w]][4],dataSite[minSimCountListIndex[w]][5],dataSite[minSimCountListIndex[w]][6]]);
	

	 if (minCountList[0] >= parseFloat(1) && minCountList[0] <= parseFloat(2) ) {

		 var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotYellow");

   }
   else if (minCountList[0] >= parseFloat(3) && minCountList[0] <= parseFloat(5) ) {    	
       var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotBlue");

   }
   else if (minCountList[0] >= parseFloat(6) && minCountList[0] <= parseFloat(10) ) {    	
   	var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotRed");

   }

   else if (minCountList[0] >= parseFloat(11) && minCountList[0] <= parseFloat(20) ) {    	
   	var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotPink");

   }
   else if (minCountList[0] >= parseFloat(21) ) {    	
   	var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotPurple");

   }
	
	 AddAgentMarker([minCountList],map1,"#4D0207");
	//Clear array before adding another min Revenue site
	minSimCountListIndex=[] ;
	$("#minCount").prop('checked',true);  

	}
	if(minSimCountListIndex.length >0) {
	  disableData = salesCountMax.concat(salesCountMin);
	}
	else {
	  disableData =[maxCount];
	 var element = document.getElementById("min-div");
		element.classList.remove("dotBlue");
		element.classList.remove("dotRed"); 
		element.classList.remove("dotYellow");  
		element.classList.add("dot");
	  $("#minCount").prop('checked',false);  

	}
	
	} // end else
	}
	firstCountRangeListIndex=[];
	secondCountRangeListIndex=[];
	thirdCountRangeListIndex=[];
	fourthCountRangeListIndex=[];
	fifthCountRangeListIndex=[];

	}// end maxMinSetcolor fct

maxCount=[];
minCount=[];
disableData =[];

function simCountSetColor(lst,map1) {


	if (lst.length == 0) {
	 	 $("#firstCountRange").attr('disabled', true);
	     $("#secondCountRange").attr('disabled', true);
	     $("#thirdCountRange").attr('disabled', true);
	     $("#fourthCountRange").attr('disabled', true);
	 	 $("#fifthCountRange").attr('disabled', true);
	 	 $("#maxCount").attr('disabled', true);
	 	 $("#minCount").attr('disabled', true);
	 		
		var element = document.getElementById("selectUnselectSales");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = " ";
		 }
	
	}
	else {

		 $("#firstCountRange").attr('disabled', false);
	     $("#secondCountRange").attr('disabled', false);
	     $("#thirdCountRange").attr('disabled', false);
	     $("#fourthCountRange").attr('disabled', false);
	 	 $("#fifthCountRange").attr('disabled', false);
	 	 $("#maxCount").attr('disabled', false);
	 	 $("#minCount").attr('disabled', false);
	 	 var element = document.getElementById("selectUnselectSales");

		 if (element.innerHTML == " "){
			 element.innerHTML = "Unselect All";
		 }
		 
		  var dataSite = lst;
		  disableData = lst;
		  maxSimCountListIndex=[];
		  minSimCountListIndex=[];
		  firstCountRangeListIndex=[];
		  secondCountRangeListIndex=[];
		  thirdCountRangeListIndex=[];
		  fourthCountRangeListIndex=[];
		  fifthCountRangeListIndex=[];

		  maxCountList=[];
		  minCountList=[] ;   
		  firstCountRange=[];
		  secondCountRange=[];
		  thirdCountRange=[];
		  fourthCountRange=[];
		  fifthCountRange=[];

		var minSimCount = -1;
        var maxSimCount = -1;
        

		  
if (dataSite.length>1){


	var minSimCount = dataSite[0][0];
    var maxSimCount = dataSite[0][0];
    
for (i=0;i<dataSite.length;i++){
    //Set max Revenue Value
    
    if (dataSite[i][0] >= maxSimCount){
    
    
   	if (dataSite[i][0] != maxSimCount){
   		maxSimCountListIndex = [];
    	}
    
   		maxSimCount = dataSite[i][0];
    	
    	//push index of max SIM CARD COUNT
    	maxSimCountListIndex.push(i);
     
        
        }
        
        if (dataSite[i][0] <= minSimCount ){
        
		     if (dataSite[i][0] != minSimCount){
		    	 minSimCountListIndex = [];
		      }
		     minSimCount = dataSite[i][0];
          
          //push index of with min SIM CARD COUNT
          minSimCountListIndex.push(i);
          
          }
          
          
    }
		if(maxSimCount == minSimCount) {
			minSimCountListIndex =[];
		}

      }
		

// add the list index arrays for count ranges 
for (j=0;j<dataSite.length;j++){
    if (dataSite[j][0] >= parseFloat(1) && dataSite[j][0] <= parseFloat(2)) {
    	firstCountRangeListIndex.push(j);
    	firstCountRange.push(dataSite[j]);
    	

    }
    else if (dataSite[j][0] >=parseFloat(3) && dataSite[j][0] <= parseFloat(5)) {
    	secondCountRangeListIndex.push(j);
    	secondCountRange.push(dataSite[j]);
    	

    }
    else if (dataSite[j][0] >=parseFloat(6) && dataSite[j][0] <= parseFloat(10)) {
    	thirdCountRangeListIndex.push(j);
    	thirdCountRange.push(dataSite[j]);
    	

    }
    else if (dataSite[j][0] >=parseFloat(11) && dataSite[j][0] <= parseFloat(20)) {
    	fourthCountRangeListIndex.push(j);
    	fourthCountRange.push(dataSite[j]);
    	

    }
    else if (dataSite[j][0] >=parseFloat(21)) {
    	fifthCountRangeListIndex.push(j);
    	fifthCountRange.push(dataSite[j]);
    	

    }

}
console.log(maxSimCountListIndex.length);
// coloring max count
for (z=0;z<maxSimCountListIndex.length;z++){
	
    for (v=0;v<7;v++){
        //Add max Revenue site to max Revenue array
        maxCountList.push(dataSite[maxSimCountListIndex[z]][v]);
        maxCount.push(dataSite[maxSimCountListIndex[z]][v]);
    }
    AddAgentMarker([maxCountList],map1,"green");

    if (maxCountList[0] >= parseFloat(1) && maxCountList[0] <= parseFloat(2) ) {

		 var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotYellow");

    }
    else if (maxCountList[0] >= parseFloat(3) && maxCountList[0] <= parseFloat(5) ) {    	
        var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotBlue");

    }
    else if (maxCountList[0] >= parseFloat(6) && maxCountList[0] <= parseFloat(10) ) {    	
    	var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotRed");

    }

    else if (maxCountList[0] >= parseFloat(11) && maxCountList[0] <= parseFloat(20) ) {    	
    	var element = document.getElementById("max-div");
		element.classList.remove("dot-max");  
 		element.classList.add("dotPink");

    }
    else if (maxCountList[0] >= parseFloat(21) ) {
    	var element = document.getElementById("max-div");
		element.classList.remove("dot-max");
 		element.classList.add("dotPurple");

    }
	
	//Clear array before adding another max count 
	maxCountList=[];
	$("#maxCount").prop('checked',true);  
	   
}

//coloring min count
for (z=0;z<minSimCountListIndex.length;z++){
    for (v=0;v<7;v++){
        //Add max Revenue site to max Revenue array
        minCountList.push(dataSite[minSimCountListIndex[z]][v]);
        minCount.push(dataSite[minSimCountListIndex[z]][v]);
    }
    AddAgentMarker([minCountList],map1,"#4D0207");

    if (minCountList[0] >= parseFloat(1) && minCountList[0] <= parseFloat(2) ) {

		 var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotYellow");

   }
   else if (minCountList[0] >= parseFloat(3) && minCountList[0] <= parseFloat(5) ) {    	
       var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotBlue");

   }
   else if (minCountList[0] >= parseFloat(6) && minCountList[0] <= parseFloat(10) ) {    	
   	var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotRed");

   }

   else if (minCountList[0] >= parseFloat(11) && minCountList[0] <= parseFloat(20) ) {    	
   	var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotPink");

   }
   else if (minCountList[0] >= parseFloat(21) ) {    	
   	var element = document.getElementById("min-div");
		element.classList.remove("dot-min");  
		element.classList.add("dotPurple");

   }
 
    
	//Clear array before adding another min count 
	minCountList=[];
	$("#minCount").prop('checked',true);  
	   
}


    if (firstCountRangeListIndex.length > 0  ){  
    	AddAgentMarker(firstCountRange,map1,"#FFDC00");
       $("#firstCountRange").prop('checked',true);  
    }
    if (secondCountRangeListIndex.length > 0  ){  
    	AddAgentMarker(secondCountRange,map1,"#0080FF");
      $("#secondCountRange").prop('checked',true);  
    }
    if (thirdCountRangeListIndex.length > 0  ){  
        AddAgentMarker(thirdCountRange,map1,"red");
      $("#thirdCountRange").prop('checked',true);  
    }
    if (fourthCountRangeListIndex.length > 0  ){  
        AddAgentMarker(fourthCountRange,map1,"#FF00FF");
      $("#fourthCountRange").prop('checked',true);  
    }
    if (fifthCountRangeListIndex.length > 0  ){  
        AddAgentMarker(fifthCountRange,map1,"#8A2BE2");
      $("#fifthCountRange").prop('checked',true);  
    }
   
   //Disable all unchecked checkboxes
   if(maxSimCountListIndex.length  ==0) {
	 	  $("#maxCount").attr('disabled', true);
	 	 
	 	 var element = document.getElementById("max-div");
		  		element.classList.remove("dotBlue");
		  		element.classList.remove("dotRed"); 
		  		element.classList.remove("dotYellow");  
		  		element.classList.add("dot");
	}
	if(minSimCountListIndex.length  ==0) {
	 	  $("#minCount").attr('disabled', true);
	 	  var element = document.getElementById("min-div");
		  		element.classList.remove("dotBlue");
		  		element.classList.remove("dotRed"); 
		  		element.classList.remove("dotYellow");  
		  		element.classList.add("dot");
	} 
	if(firstCountRangeListIndex.length ==0) {
	  $("#firstCountRange").attr('disabled', true);
	}
	if(secondCountRangeListIndex.length ==0) {
	 	  $("#secondCountRange").attr('disabled', true);
	}
	if(thirdCountRangeListIndex.length ==0) {
	 	  $("#thirdCountRange").attr('disabled', true);
	}
	if(fourthCountRangeListIndex.length  ==0) {
	 	  $("#fourthCountRange").attr('disabled', true);
	}
	if(fifthCountRangeListIndex.length  ==0) {
	 	  $("#fifthCountRange").attr('disabled', true);
	} 
    
	}
	
} // end of set color function



function UnselectAllSales(){

	  $("input[name='legendCheckboxSales']").prop('checked',false);
		
	}


function SelectUnselectAllSales() {
	checkedColorrSales=[];

		if(maxSimCountListIndex.length >0) {
			checkedColorrSales.push("green");
			}

		if(minSimCountListIndex.length >0) {
			checkedColorrSales.push("#4D0207");
					}

		if(firstCountRangeListIndex.length >0) {
			checkedColorrSales.push("#FFDC00");
					}

		if(secondCountRangeListIndex.length  >0) {
			checkedColorrSales.push("#0080FF");
					}

		if(thirdCountRangeListIndex.length  >0) {
			checkedColorrSales.push("red");
					}

		if(fourthCountRangeListIndex.length  >0) {
			checkedColorrSales.push("#FF00FF");
					}
		if(fifthCountRangeListIndex.length  >0) {
			checkedColorrSales.push("#8A2BE2");
					}

		
		var element = document.getElementById("selectUnselectSales");

		 if (element.innerHTML == "Unselect All"){
			 element.innerHTML = "Select All";
			 
				//Add checked checkboxes to Salescolors array
				 var checkboxes = document.querySelectorAll('input[name="legendCheckboxSales"]:checked');
				 Salescolors = [];
				checkboxes.forEach((checkbox) => {
				    Salescolors.push(checkbox.value);
				});

				
				//Uncheck all checkboxes by value (by color)
				for (var l= 0; l < Salescolors.length; l++) {
				
				$('#tableSimSales input:checkbox[value="' + Salescolors[l] + '"]').prop("checked", false);
				 }

				 
			 for (var l= 0; l < Salescolors.length; l++) {
				 for (var i = 0; i < markerGroupsSales[Salescolors[l]].length; i++) {
				 	
				 	var marker = markerGroupsSales[Salescolors[l]][i];
				 	
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
					
			 if(Salescolors[l] == "#FFDC00") {
					
					if  ($('#firstCountRange').prop("checked") == false) {
						HideClusterIcon(markerGroupsSales["#FFDC00"],"#FFDC00");
						}
					}	

			//Hide blue cluster icon

			  else if(Salescolors[l] == "#0080FF") {
				  if  ($('#secondCountRange').prop("checked") == false) {
					  HideClusterIcon(markerGroupsSales["#0080FF"],"#0080FF");
					  }
				  }
			  else if(Salescolors[l] == "red") {
				  if  ($('#thirdCountRange').prop("checked") == false) {
					  HideClusterIcon(markerGroupsSales["red"],"red");
					  }
				  }

			  else if(Salescolors[l] == "#FF00FF") {
				  if  ($('#fourthCountRange').prop("checked") == false) {
					  HideClusterIcon(markerGroupsSales["#FF00FF"],"#FF00FF");
					  }
				  }
			  else if(Salescolors[l] == "#8A2BE2") {
				  if  ($('#fifthCountRange').prop("checked") == false) {
					  HideClusterIcon(markerGroupsSales["#8A2BE2"],"#8A2BE2");
					  }
				  }
			  
			  }
			  }

		  else {

			  element.innerHTML = "Unselect All";
		
		    var agntsRangeChecked = document.querySelectorAll('input[name="legendCheckboxSales"]:checked');
			
			agntsRnge = [];
			agntsRangeChecked.forEach((checkbox) => {
			    agntsRnge.push(checkbox.value);
			});
			
			if(agntsRnge.length >0){
					for(o=0;o<agntsRnge.length;o++){
					for (var i = 0; i < markerGroupsSales[agntsRnge[o]].length; i++) {
				 	
				 	var marker = markerGroupsSales[agntsRnge[o]][i];
				 	
					//Show markers

				 	if (!marker.getVisible()) {
				 	  marker.setVisible(true);
				 	} 
					//hide markers

				 	else {
				 	  marker.setVisible(false);
				 	}
				 }
					
			 if(agntsRnge[o] == "#FFDC00") {
				HideClusterIcon(markerGroupsSales["#FFDC00"],"#FFDC00");
					}	
			  else if(agntsRnge[o] == "#0080FF") {
					  HideClusterIcon(markerGroupsSales["#0080FF"],"#0080FF");
				  }
			  else if(agntsRnge[o] == "red") {
					  HideClusterIcon(markerGroupsSales["red"],"red");
				  }

			  else if(agntsRnge[o] == "#FF00FF") {
					  HideClusterIcon(markerGroupsSales["#FF00FF"],"#FF00FF");
				  }
			  else if(agntsRnge[o] == "#8A2BE2") {
					  HideClusterIcon(markerGroupsSales["#8A2BE2"],"#8A2BE2");
				  }
			  
			  }
			}
			
	  	 	 for (var l= 0; l < checkedColorrSales.length; l++) {
				$('input:checkbox[value="' + checkedColorrSales[l] + '"]').prop("checked", true);
						 }

	  	 var checkboxes = document.querySelectorAll('input[name="legendCheckboxSales"]:checked');
			 Salescolors = [];
			checkboxes.forEach((checkbox) => {
			    Salescolors.push(checkbox.value);
			});

		 for (var l= 0; l < checkedColorrSales.length; l++) {
			 for (var i = 0; i < markerGroupsSales[checkedColorrSales[l]].length; i++) {
			 	
			 	var marker = markerGroupsSales[checkedColorrSales[l]][i];


			 	if (!marker.getVisible()) {
			 	  marker.setVisible(true);
			 	} else {
			 	  marker.setVisible(false);
			 	}
			 }


			//Show yellow cluster icon
			 if(checkedColorrSales[l] == "#FFDC00") {
					if ($('#firstCountRange').prop("checked") == true) {
						ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#FFDC00");
						
						 }
					 }
			//Show blue cluster icon
			else if(checkedColorrSales[l] == "#0080FF") {
					if ($('#secondCountRange').prop("checked") == true) {
						ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#0080FF");
						 }
					 }
			else if(checkedColorrSales[l] == "red") {
					if ($('#thirdCountRange').prop("checked") == true) {
						ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"red");
						 }
					 }

			else if(checkedColorrSales[l] == "#FF00FF") {
				if ($('#fourthCountRange').prop("checked") == true) {
					ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#FF00FF");
					 }
				 }
			else if(checkedColorrSales[l] == "#8A2BE2") {
				if ($('#fifthCountRange').prop("checked") == true) {
					ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#8A2BE2");
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
markerGroupsSales = {
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


function UnselectAllSales(){

  $("input[name='legendCheckboxSales']").prop('checked',false);
	
}

function ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,color) {

	var Nairobi=new google.maps.LatLng(-1.286389,36.817223);
	var map1 = new google.maps.Map(document.getElementById("mapContainer1"), {
		center:Nairobi ,
		zoom: 6
		});

	document.getElementById("mapContainer1").innerHTML ="";

	var map1 = new google.maps.Map(document.getElementById("mapContainer1"), {

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
                                // alert(firstCountRange);
                            	 AddAgentMarker(firstCountRange,map1,"#FFDC00"); 
                                if (markerGroupsSales["#0080FF"].length>0  ){
                                   //Clear previous blue markers
                                    HideClusterIcon(markerGroupsSales["#0080FF"],"#0080FF");
                                    //Add blue markers
                                    AddAgentMarker(secondCountRange,map1,"#0080FF");
                                }
                                if (markerGroupsSales["red"].length>0  ){
                                 HideClusterIcon(markerGroupsSales["red"],"red");
                                 AddAgentMarker(thirdCountRange,map1,"red");
                                 }
                                if (markerGroupsSales["#FF00FF"].length>0  ){
                                    HideClusterIcon(markerGroupsSales["#FF00FF"],"#FF00FF");
                                    AddAgentMarker(fourthCountRange,map1,"#FF00FF");
                                }
                                if (markerGroupsSales["#8A2BE2"].length>0  ){
                                    HideClusterIcon(markerGroupsSales["#8A2BE2"],"#8A2BE2");
                                    AddAgentMarker(fifthCountRange,map1,"#8A2BE2");
                                }
                        
                                }
                        
                                else if (color=="#0080FF"){
                                	 AddAgentMarker(secondCountRange,map1,"#0080FF"); 
                                    if (markerGroupsSales["#FFDC00"].length>0  ){
                                    HideClusterIcon(markerGroupsSales["#FFDC00"],"#FFDC00");
                                    AddAgentMarker(firstCountRange,map1,"#FFDC00");
                                    }
                                    if (markerGroupsSales["red"].length>0  ){
                                    HideClusterIcon(markerGroupsSales["red"],"red");
                                    AddAgentMarker(thirdCountRange,map1,"red");
                                     }
                                    if (markerGroupsSales["#FF00FF"].length>0  ){
                                        HideClusterIcon(markerGroupsSales["#FF00FF"],"#FF00FF");
                                        AddAgentMarker(fourthCountRange,map1,"#FF00FF");
                                    }
                                    if (markerGroupsSales["#8A2BE2"].length>0  ){
                                        HideClusterIcon(markerGroupsSales["#8A2BE2"],"#8A2BE2");
                                        AddAgentMarker(fifthCountRange,map1,"#8A2BE2");
                                    }
                                  
                                    }
                        
                                 else if (color=="red"){
                                	 AddAgentMarker(thirdCountRange,map1,"red"); 
                                        if (markerGroupsSales["#FFDC00"].length>0  ){
                                        HideClusterIcon(markerGroupsSales["#FFDC00"],"#FFDC00");
                                        AddAgentMarker(firstCountRange,map1,"#FFDC00");
                                        }
                                        if (markerGroupsSales["#0080FF"].length>0  ){
                                         HideClusterIcon(markerGroupsSales["#0080FF"],"#0080FF");
                                         AddAgentMarker(secondCountRange,map1,"#0080FF"); 
                                   		}
                                        if (markerGroupsSales["#FF00FF"].length>0  ){
                                            HideClusterIcon(markerGroupsSales["#FF00FF"],"#FF00FF");
                                            AddAgentMarker(fourthCountRange,map1,"#FF00FF");
                                        }
                                        if (markerGroupsSales["#8A2BE2"].length>0  ){
                                            HideClusterIcon(markerGroupsSales["#8A2BE2"],"#8A2BE2");
                                            AddAgentMarker(fifthCountRange,map1,"#8A2BE2");
                                        }
                                       
                                
                                
                                        }
                                 else if (color=="#FF00FF"){
                                	 AddAgentMarker(fourthCountRange,map1,"#FF00FF");
                                        if (markerGroupsSales["#FFDC00"].length>0  ){
                                        HideClusterIcon(markerGroupsSales["#FFDC00"],"#FFDC00");
                                        AddAgentMarker(firstCountRange,map1,"#FFDC00");
                                        }
                                        if (markerGroupsSales["#0080FF"].length>0  ){
                                         HideClusterIcon(markerGroupsSales["#0080FF"],"#0080FF");
                                         AddAgentMarker(secondCountRange,map1,"#0080FF"); 
                                   		}
                                        if (markerGroupsSales["red"].length>0  ){
                                            HideClusterIcon(markerGroupsSales["red"],"red");
                                            AddAgentMarker(thirdCountRange,map1,"red");
                                             }
                                        
                                        if (markerGroupsSales["#8A2BE2"].length>0  ){
                                            HideClusterIcon(markerGroupsSales["#8A2BE2"],"#8A2BE2");
                                            AddAgentMarker(fifthCountRange,map1,"#8A2BE2");
                                        }
                                       
                                
                                
                                        }
                                 else if (color=="#8A2BE2"){
                                	 AddAgentMarker(fifthCountRange,map1,"#8A2BE2");
                                        if (markerGroupsSales["#FFDC00"].length>0  ){
                                        HideClusterIcon(markerGroupsSales["#FFDC00"],"#FFDC00");
                                        AddAgentMarker(firstCountRange,map1,"#FFDC00");
                                        }
                                        if (markerGroupsSales["#0080FF"].length>0  ){
                                         HideClusterIcon(markerGroupsSales["#0080FF"],"#0080FF");
                                         AddAgentMarker(secondCountRange,map1,"#0080FF"); 
                                   		}
                                        if (markerGroupsSales["#FF00FF"].length>0  ){
                                            HideClusterIcon(markerGroupsSales["#FF00FF"],"#FF00FF");
                                            AddAgentMarker(fourthCountRange,map1,"#FF00FF");
                                        }
                                       
                                        if (markerGroupsSales["red"].length>0  ){
                                            HideClusterIcon(markerGroupsSales["red"],"red");
                                            AddAgentMarker(thirdCountRange,map1,"red");
                                             }
                                        
                                
                                
                                        }
                             
                                   //Add legend button under zoom control on map1
		const centerControlDiv = document.createElement("div");
	    LegendControl(centerControlDiv, map1);
	    map1.controls[google.maps.ControlPosition.LEFT_CENTER].push(centerControlDiv);

	    const DefaultZoomDiv = document.createElement("div");
	    DefaultZoomControl(DefaultZoomDiv, map1);
	    map1.controls[google.maps.ControlPosition.LEFT_CENTER].push(DefaultZoomDiv);

	    const maxCountControlDiv = document.createElement("div");
	    MaxAgentSalesControl(maxCountControlDiv, map1);
	    map1.controls[google.maps.ControlPosition.TOP_CENTER].push(maxCountControlDiv);

	    const minCountControlDiv = document.createElement("div");
	    MinAgentSalesControl(minCountControlDiv, map1);
	    map1.controls[google.maps.ControlPosition.TOP_CENTER].push(minCountControlDiv);
	    

                                    }

function HideClusterIcon(MarkersArray,color) {

	  //  alert("color of cluster is" +color);
	    
	    if(color == "#FFDC00") {
	        for  ( i = 0; i<markerGroupsSales["#FFDC00"].length; i++) {	

	        //Remove yellow markers from map1			
	        markerGroupsSales["#FFDC00"][i].setMap(null);
	    }
	    

	    yellowCluster.clearMarkers();
	    //clear yellow markers array
	    markerGroupsSales["#FFDC00"] =[];
	    color == "" ;  
	    }

	    
	    else if (color == "#0080FF") {

	        for  ( j = 0; j<markerGroupsSales["#0080FF"].length; j++) {	
	            markerGroupsSales["#0080FF"][j].setMap(null);
	        }
	        
	        blueCluster.clearMarkers();
	        markerGroupsSales["#0080FF"] =[];
	        color == "";
	            
	    }

	    else if (color == "red") {

	        for  ( r = 0; r<markerGroupsSales["red"].length; r++) {	
	            markerGroupsSales["red"][r].setMap(null);
	        }
	        
	        redCluster.clearMarkers();
	        markerGroupsSales["red"] =[];
	            
	    }
	   else if (color == "#FF00FF") {


	    	for  ( n = 0; n<markerGroupsSales["#FF00FF"].length; n++) {	
	            markerGroupsSales["#FF00FF"][n].setMap(null);
	        }
	        
	        pinkCluster.clearMarkers();
	        markerGroupsSales["#FF00FF"] =[];
	            
	    }
	    
	    else if (color == "#8A2BE2") {

	        for  ( b = 0; b<markerGroupsSales["#8A2BE2"].length; b++) {	
	            markerGroupsSales["#8A2BE2"][b].setMap(null);
	        }
	        
	        purpleCluster.clearMarkers();
	        markerGroupsSales["#8A2BE2"] =[];
	            
	    }

	    
	}



function ShowHideMarkersSales(color) {
	  
	
	for ( x = 0; x < markerGroupsSales[color].length; x++) {
	
	var marker = markerGroupsSales[color][x];	

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
		if ($('#firstCountRange').prop("checked") == true) {
			ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#FFDC00");
			}

		
		else {

			HideClusterIcon(markerGroupsSales["#FFDC00"],"#FFDC00");
  }	

	}

	if(color == "#0080FF") {
		if ($('#secondCountRange').prop("checked") == true) {
			ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#0080FF");
			}

		
		else {

			HideClusterIcon(markerGroupsSales["#0080FF"],"#0080FF");
  }	

	}

	if(color == "red") {
		if ($('#thirdCountRange').prop("checked") == true) {
			ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"red");
			}

		
		else {

			HideClusterIcon(markerGroupsSales["red"],"red");
  }	

	}

	if(color == "#FF00FF") {
		if ($('#fourthCountRange').prop("checked") == true) {
			ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#FF00FF");
			}

		
		else {

			HideClusterIcon(markerGroupsSales["#FF00FF"],"#FF00FF");
  }	

	}
	if(color == "#8A2BE2") {
		if ($('#fifthCountRange').prop("checked") == true) {
			ShowClusterIcon(firstCountRange,secondCountRange,thirdCountRange,fourthCountRange,fifthCountRange,"#8A2BE2");
			}

		
		else {

			HideClusterIcon(markerGroupsSales["#8A2BE2"],"#8A2BE2");
  }	

	}
	
}// end showhidemarkers fct




