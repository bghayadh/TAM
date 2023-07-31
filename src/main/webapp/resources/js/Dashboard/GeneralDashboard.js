
	function ShowHideDiv(){
	
		$("#legendDiv").toggle();
	}
	function LegendControl(controlDiv, map) {
		
	    const controlUI = document.createElement("dv");
	    controlUI.style.backgroundColor = "white";
	    controlUI.style.border = "8px solid white";
	  
	    controlUI.style.cursor = "pointer";
	   
	    controlUI.style.marginLeft = "10px";
	    controlUI.innerHTML = '<button style="border:none;outline:none; background:white;"  onClick="ShowHideDiv()"><i class="fas fa-arrow-right fa-lg "></i></button>';
	    controlUI.title = "Open Legend";
	    controlDiv.appendChild(controlUI);

	  }



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
	        
	       
	        
	              });

	  }






	
	//******************************* donuts chart *******************************		
	// 1st piechart
	 function fetchTechnologyChart(){
		
	FusionCharts.ready(function() {
		var label1,label2,label3,label4,label5,label6;
//console.log("technology"+TechnologyCount);
    if(TechnologyCount==null){
	
	label1 = "NO DATA";label2="";label3="";label4=""; label5=""; label6="";

	 var piChart = new FusionCharts({
		  
		    type: "pie2d",
		    renderAt: "pichart-container",
		    width: "100%",
		       height: "413",
		    dataFormat: "json",
		    dataSource: {
		        "chart": {
		               "caption": "Technologies Sites Count",
		               "captionFontSize": "15",
		               "captionFontColor": "#08526d",
		               "captionFontBold": "1",
		               "subCaption": "",
		               "use3DLighting": "1",
		              "showPercentValues": "1",
		               "decimals": "1",
		               
		               "theme": "fusion",
		               bgColor: "#FFFFFF",
		               bgAlpha: "50",
		               
		           },
		           "data": [
		               {
		                   "label": label1,

		                   "value": 100,
		                   "color":"#FFFF00"
		                   
		                   
		               },
		               {
		                   "label": label2,

		                   "value": 0,
		                   "color":"#1D4E08"
		               },
		               {
		                   "label": label3,

		                   "value": 0
		                  
		               },
		               {
		                   "label": label4,

		                   "value": 0,
		                   "color":"#0000FF"
		                   
		               },
		               {
		                   "label": label5,

		                   "value": 0,
		                   "color":"#FF0000"
		               },

		               {
		                   "label": label6,

		                   "value":0
		                   
		               },
		           ]
		       }
		  });
	
}
		
else if(TechnologyCount[0] == 0 && TechnologyCount[1]== 0 && TechnologyCount[2]== 0 && TechnologyCount[3]== 0 &&TechnologyCount[4]== 0 &&TechnologyCount[5]== 0){
		TechnologyCount[0] = 100;
		label1 = "NO DATA";label2="";label3="";label4=""; label5=""; label6="";
		  var piChart = new FusionCharts({
			  
			    type: "pie2d",
			    renderAt: "pichart-container",
			    width: "100%",
			       height: "413",
			    dataFormat: "json",
			    dataSource: {
			        "chart": {
			               "caption": "Technologies Sites Count",
			               "captionFontSize": "15",
			               "captionFontColor": "#08526d",
			               "captionFontBold": "1",
			               "subCaption": "",
			               "use3DLighting": "1",
			              "showPercentValues": "1",
			               "decimals": "1",
			              
			               "theme": "fusion",
			               bgColor: "#FFFFFF",
			               bgAlpha: "50",
			               
			           },
			           "data": [
			               {
			                   "label": label1,

			                   "value": TechnologyCount[0],
			                    "color":"#FFFF00"
			                   
			               },
			               {
			                   "label": label2,

			                   "value": TechnologyCount[1],
			                   "color":"#1D4E08"
			               },
			               {
			                   "label": label3,

			                   "value": TechnologyCount[2],
			                  
			                   
			               },
			               {
			                   "label": label4,
			                
			                   "value": TechnologyCount[3],
			                   "color":"#0000FF"
			               },
			               {
			                   "label": label5,

			                   "value": TechnologyCount[4],
			                  "color":"#FF0000"
			               },

			               {
			                   "label": label6,

			                   "value": TechnologyCount[5]
			                   
			               },
			           ]
			       }
			  });
		}
	
	else{label1 = "2G: Count: "+TechnologyCount[0]; label2="3G:Count: "+TechnologyCount[1];label3="4G:Count: "+TechnologyCount[2];label4="2G,3G Count: "+TechnologyCount[3];label5="2G,3G,4G: Count: "+TechnologyCount[4]; label6="No Technology, Count: "+TechnologyCount[5] ;

	  var piChart = new FusionCharts({
		  
		    type: "pie2d",
		    renderAt: "pichart-container",
		    width: "100%",
		       height: "413",
		    dataFormat: "json",
		    dataSource: {
		        "chart": {
		               "caption": "Technologies Sites Count",
		               "captionFontSize": "15",
		               "captionFontColor": "#08526d",
		               "captionFontBold": "1",
		               "subCaption": "",
		               "use3DLighting": "1",
		              "showPercentValues": "1",
		               "decimals": "1",
		               
		               "theme": "fusion",
		             
		               bgColor: "#FFFFFF",
		               bgAlpha: "50",
		               
		           },
		           "data": [
		               {
		                   "label": label1,

		                   "value": TechnologyCount[0],
		                    "color":"#FFFF00"
		                   
		               },
		               {
		                   "label": label2,

		                   "value": TechnologyCount[1],
		                   "color":"#1D4E08"
		               },
		               {
		                   "label": label3,

		                   "value": TechnologyCount[2],
		                  
		                   
		               },
		               {
		                   "label": label4,
		                
		                   "value": TechnologyCount[3],
		                   "color":"#0000FF"
		               },
		               {
		                   "label": label5,

		                   "value": TechnologyCount[4],
		                  "color":"#FF0000"
		               },

		               {
		                   "label": label6,

		                   "value": TechnologyCount[5]
		                   
		               },
		           ]
		       }
		  });



	}

  piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
  piChart.render();

});
		
}


	 function fetchTechnologyRevChart(){
		
		FusionCharts.ready(function() {
			var label1,label2,label3,label4,label5,label6;
	//console.log("technology"+TechnologyCount);
	
			
	 if(RevenueTechnology[0] == 0 && RevenueTechnology[1]== 0 && RevenueTechnology[2]== 0 ){
			RevenueTechnology[0] = 100;
			label1 = "NO DATA";label2="";label3="";label4=""; label5=""; label6="";
			  var piChart = new FusionCharts({
				  
				    type: "doughnut2d",
				    renderAt: "RevenuePerTechnology",
				    width: "100%",
				       height: "420",
				    dataFormat: "json",
				    dataSource: {
				        "chart": {
				               "caption": "Revenue Split Per Technologies",
				               "captionFontSize": "15",
				               "captionFontColor": "#08526d",
				               "captionFontBold": "1",
				               "subCaption": "",
				               "use3DLighting": "1",
				              "showPercentValues": "1",
				               "decimals": "1",
				              
				               "theme": "fusion",
				               bgColor: "#FFFFFF",
				               bgAlpha: "50",
				               
				           },
				           "data": [
				               {
				                   "label": label1,

				                   "value": RevenueTechnology[0],
				                    "color":"#FFFF00"
				                   
				               },
				               {
				                   "label": label2,

				                   "value": RevenueTechnology[1],
				                   "color":"#1D4E08"
				               },
				               {
				                   "label": label3,

				                   "value": RevenueTechnology[2],
				                   "color":"#0000FF"
				                   
				               },
				               
				           ]
				       }
				  });
			}
		
		else{label1 = "2G"; label2="2G,3G";label3="2G,3G,4G";

		  var piChart = new FusionCharts({
			  
			    type: "doughnut2d",
			    renderAt: "RevenuePerTechnology",
			       width: "100%",
			       height: "420",
			    dataFormat: "json",
			    dataSource: {
			        "chart": {
			               "caption": "Revenue Split Per Technologies",
			               "captionFontSize": "15",
			               "captionFontColor": "#08526d",
			               "captionFontBold": "1",
			               "subCaption": "",
			               "use3DLighting": "1",
			              "showPercentValues": "1",
			               "decimals": "1",
			               
			               "theme": "fusion",
			             
			               bgColor: "#FFFFFF",
			               bgAlpha: "50",
			               
			           },
			           "data": [
			               {
			                   "label": label1,

			                   "value": RevenueTechnology[0] ,
			                   "color":"#FFFF00"
			                  
			                    
			                   
			               },
			               {
			                   "label": label2,

			                   "value": RevenueTechnology[1] ,
			                   "color":"#1D4E08"
			               },
			               {
			                   "label": label3,

			                   "value": RevenueTechnology[2],
			                   "color":"#FF0000"
			                   
			               },
			               
			           ]
			       }
			  });



		}

	  piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart.render();

	});
			
	}


	

 function fetchServiceRevenueChart(){
		
		FusionCharts.ready(function() {
			var label1,label2,label3,label4;
			var voiceRevenue= [];
			var smsRevenue =[];
			var dataRevneue=[];
			var vasRevenue=[]; 
			var totRev=[];

			if(pieRevenue != "" && pieRevenue[0][4] !=0 ){
			//console.log("here");
					//if(pieRevenue[0][0] !=null && pieRevenue[0][1] !=null && pieRevenue[0][2] !=null && pieRevenue[0][3] !=null && pieRevenue[0][4] !=0){
			 		     voiceRevenue=pieRevenue[0][0];
						 smsRevenue = pieRevenue[0][1];
						 dataRevneue=pieRevenue[0][2];
						 vasRevenue=pieRevenue[0][3]; 
						 totRev=pieRevenue[0][4];

						label1 = "Voice:"+voiceRevenue.toFixed(2);label2="SMS:"+smsRevenue.toFixed(2);label3="Data:"+dataRevneue.toFixed(2);label4="VAS:"+vasRevenue.toFixed(2);
						
						
						//totRev = parseFloat(voiceRevenue+smsRevenue+dataRevneue+vasRevenue);
						
			 		//}
				}
				else{
					label1 = "NO Data";label2="";label3="";label4="";
					
					voiceRevenue =100;
					smsRevenue=0;
					dataRevneue=0;
					vasRevenue=0;
					totRev=0;
						
				}


			 var piChart = new FusionCharts({
				  
				    type: "pie2d",
				    renderAt: "RevenuePerService",
				    width: "100%",
				       height: "420",
				    dataFormat: "json",
				    dataSource: {
				        "chart": {
				               "caption": "Revenue Split Per Service",
				               "captionFontSize": "15",
				               "captionFontColor": "#08526d",
				               "captionFontBold": "1",
				               "subCaption": "",
				               "use3DLighting": "1",
				              "showPercentValues": "1",
				               "decimals": "1",
				               
				               "theme": "fusion",
				             
				               bgColor: "#FFFFFF",
				               bgAlpha: "50",
				               
				           },
				           data: [
							    {
							      label: label1,
							      value: voiceRevenue,
							      "color":"#FFFF00"
							    },
							    {
							      label: label2,
							      value: smsRevenue,
							      "color":"#0000FF"
							    },
							    {
							      label: label3,
							      value: dataRevneue,
							      "color":"#FF0000"
							    },
							    {
							      label: label4,
							      value: vasRevenue
							    }
							  ]
				       }
				  });
			
	//console.log("technology"+TechnologyCount);
	 
			
	
		

	  piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart.render();

	});
			
	}
	


	function fetchRevenuePerRegion(){
		
		FusionCharts.ready(function() {
		
			var chartData = [];
			var colors=["#FFFF00","#1D4E08",
	  		    "#4b77a9",
	  		    "#4d4dff",
	  		    "#FF0000",
	  		    "#5f255f",
	  		    "#d21243",
	  		    "#dd1c77"];
			for (var i = 0; i < revenuePerRegions.length; i++) {
			    chartData.push({
			        "label": revenuePerRegions[i][0]+" "+revenuePerRegions[i][1].toFixed(2),
			        "value": revenuePerRegions[i][1] ,
			        "color" : colors[i]            
			    })
			}
			if (revenuePerRegions=="" ){
               // console.log("/*/*/here");

               // console.log(RevenueRegions[0]);
                
				label1 = "NO DATA";

				  var piChart = new FusionCharts({
					  
					    type: "doughnut2d",
					    renderAt: "RevenuePerRegion",
					    width: "100%",
					       height: "420",
					    dataFormat: "json",
					    dataSource: {
					        "chart": {
					               "caption": "Revenue Split Per Regions",
					               "captionFontSize": "15",
					               "captionFontColor": "#08526d",
					               "captionFontBold": "1",
					               "subCaption": "",
					               "use3DLighting": "1",
					              "showPercentValues": "1",
					               "decimals": "1",
					              
					               "theme": "fusion",
					               bgColor: "#FFFFFF",
					               bgAlpha: "50",
					               
					           },
					           "data": [
					               {
					                   "label": label1,
					                   "value": 100,
					                   "color":"#FFFF00"
					                   
					               },
					              
					               
					           ]
					       }
					  });



				}


			else{


				  var piChart = new FusionCharts({
					  
					    type: "doughnut2d",
					    renderAt: "RevenuePerRegion",
					    width: "100%",
					       height: "420",
					    dataFormat: "json",
					    dataSource: {
					        "chart": {
					               "caption": "Revenue Split Per Regions",
					               "captionFontSize": "15",
					               "captionFontColor": "#08526d",
					               "captionFontBold": "1",
					               "subCaption": "",
					               "use3DLighting": "1",
					              "showPercentValues": "1",
					               "decimals": "1",
					              
					               "theme": "fusion",
					               bgColor: "#FFFFFF",
					               bgAlpha: "50",
					               
					           },
					           "data": chartData
					       }
					  });


				}

			
			
	

	 piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart.render();

	});
			
	}


 function fetchSitesPerRegion(){
		
		FusionCharts.ready(function() {
			
			var Data = [];
			var colors=["#FFFF00","#1D4E08",
	  		    "#4b77a9",
	  		    "#4d4dff",
	  		    "#FF0000",
	  		    "#5f255f",
	  		    "#d21243",
	  		    "#dd1c77"];
			for (var i = 0; i < SitesPerRegions.length; i++) {
			    Data.push({
			        "label": SitesPerRegions[i][0] +" "+SitesPerRegions[i][1],
			        "value": SitesPerRegions[i][1],
			        "color" : colors[i]            
			    })
			}
			if (SitesPerRegions.length==0){

				label1 = "NO DATA";

				  var piChart = new FusionCharts({
					  
					    type: "pie2d",
					    renderAt: "SitesPerRegion",
					    width: "100%",
					       height: "420",
					    dataFormat: "json",
					    dataSource: {
					        "chart": {
					               "caption": "Sites Count Per Regions",
					               "captionFontSize": "15",
					               "captionFontColor": "#08526d",
					               "captionFontBold": "1",
					               "subCaption": "",
					               "use3DLighting": "1",
					              "showPercentValues": "1",
					               "decimals": "1",
					              
					               "theme": "fusion",
					               bgColor: "#FFFFFF",
					               bgAlpha: "50",
					               
					           },
					           "data": [
					               {
					                   "label": label1,
					                   "value": 100,
					                    "color":"#FFFF00"
					                   
					               },
					               {
					                   "label": "",
					                   
					                    "color":"#FFFF00"
					   
					               },
					               
					           ]
					       }
					  });



				}


			else{


				  var piChart = new FusionCharts({
					  
					    type: "pie2d",
					    renderAt: "SitesPerRegion",
					    width: "100%",
					       height: "420",
					    dataFormat: "json",
					    dataSource: {
					        "chart": {
					               "caption": "Sites Count Per Regions",
					               "captionFontSize": "15",
					               "captionFontColor": "#08526d",
					               "captionFontBold": "1",
					               "subCaption": "",
					               "use3DLighting": "1",
					              "showPercentValues": "1",
					               "decimals": "1",
					              
					               "theme": "fusion",
					               bgColor: "#FFFFFF",
					               bgAlpha: "50",
					               
					           },
					           "data": Data
					       }
					  });










				}

			
			
	

	 piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart.render();

	});
			
	}
	
	/*async function fetchDonutsChartProfitAndLoss(){
		FusionCharts.ready(function() {
			var label1,label2,label3,label4;
		if(domainCount[0] == 0 && domainCount[1]== 0 && domainCount[2]== 0 && domainCount[3]== 0){
			domainCount[0] = 100;
			label1 = "NO DATA";label2="";label3="";label4="";
			}else{label1 = "2G, Count: "+domainCount[0];label2="2G,3G, Count: "+domainCount[1];label3="2G,3G,4G, Count: "+domainCount[2];label4="BLANK, Count: "+domainCount[3] ;}
	  var piChart = new FusionCharts({
		  
	    type: "pie2d",
	    renderAt: "pichart-containerPaL",
	    width: "100%",
	       height: "390",
	    dataFormat: "json",
	    dataSource: {
	        "chart": {
	               "caption": "Technologies Sites Count",
	               "captionFontSize": "15",
	               "captionFontColor": "#08526d",
	               "captionFontBold": "1",
	               "subCaption": "",
	               "use3DLighting": "1",
	              "showPercentValues": "1",
	               "decimals": "1",
	               "useDataPlotColorForLabels": "1",
	               "theme": "fusion",
	               bgColor: "#FFFFFF",
	               bgAlpha: "50",
	               
	           },
	           "data": [
	               {
	                   "label": label1,

	                   "value": domainCount[0]
	                   
	               },
	               {
	                   "label": label2,

	                   "value": domainCount[1]
	                   
	               },
	               {
	                   "label": label3,

	                   "value": domainCount[2]
	                   
	               },
	               {
	                   "label": label4,

	                   "value": domainCount[3]
	                   
	               },
	           ]
	       }
	  });
	  piChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  piChart.render();

	});
	}*/


			//******************************* end doughnut2d chart *******************************
			
			
			//******************************* easy pie chart *******************************
			
			$(function() {
				$('.chart').easyPieChart({
				//your options goes here
					barColor : '#ef1e25',
					trackColor : '#f9f9f9',
					scaleColor : false,
					//  scaleLength: 5,
					lineCap : 'round',
					lineWidth : 10,
					trackWidth : undefined,
					
					size : 200,
					rotate : 0, // in degrees
					animate : {
						duration : 1000,
						enabled : true
					},
				});
			});

		function fetchLineChartData(RevenueTotal){
				var ctxLine2 = document.getElementById('myChartLine2').getContext('2d');
				var myChartLine2 = new Chart(ctxLine2, {
				    type: 'line',
				    data: {
				        labels: ['2G','2G & 3G','2G , 3G & 4G'],
				        datasets: [{
				            label: 'Voice Revenue',
				            data: RevenueTotal[0],
				            backgroundColor: '#FFFF00',
				            borderColor: '#FFFF00',
				            borderWidth: 3,
				            fill: false
				        },
				        {
				            label: 'SMS Revenue',
				            data: RevenueTotal[1],
				            backgroundColor: '#0000FF',
				            borderColor: '#0000FF',
				            borderWidth: 3,
				            fill: false
				        },
				        {
				            label: 'Data',
				            data: RevenueTotal[2],
				            backgroundColor: '#FF0000',
				            borderColor: '#FF0000',
				            borderWidth: 3,
				            fill: false
				        },
				        {
				            label: 'VAS',
				            data: RevenueTotal[3],
				            backgroundColor: '#1D4E08',
				            borderColor: '#1D4E08',
				            borderWidth: 3,
				            fill: false
				        }
				       
				        ]
				    },
				    options: {
				    	legend: {
				    	    labels: {
				    	       fontSize: 18,
				    	       fontColor: "black",
				    	    }
				    	},
				    	 title: {
				              display: true,
				              text: 'Site Technologies Revenues Analysis',
				              fontFamily: 'sans-serif',
				              fontColor: '#08526D',
				              fontSize: 22
				          },
				    	tooltips: {
				            mode: 'index'
				        },  
				        pointLabels: { fontSize:22 },    
				        scales: {
				            yAxes: [{
				                ticks: {
				                    beginAtZero: true
				                }
				            }],
				            xAxes: [{
				                ticks: {
				                	autoSkip: false,
				                    fontSize: 15,
				                    fontColor: "black",

				                }
				            }]
				        }
				    }
				});
				
	}
	



	function fetchLineChartDataLoad(RevenueTotal){

		var voiceRevenue=[];
		var smsRevenue=[];
		var dataRevenue=[];
		var vasRevenue=[];
		var combinationTechnology=[];

		if (RevenueTotal.length!=0){
			for (var i = 0; i < RevenueTotal.length; i++) {
			   combinationTechnology.push(RevenueTotal[i][0]);
			  if (RevenueTotal[i][0]=='2G'){
				  voiceRevenue.push(RevenueTotal[i][1]);
				  smsRevenue.push(RevenueTotal[i][2]);
				  dataRevenue.push(RevenueTotal[i][3]);
				  vasRevenue.push(RevenueTotal[i][4]);
		   
				  }

			  if (RevenueTotal[i][0]=='2G_3G'){
				  voiceRevenue.push(RevenueTotal[i][1]);
				  smsRevenue.push(RevenueTotal[i][2]);
				  dataRevenue.push(RevenueTotal[i][3]);
				  vasRevenue.push(RevenueTotal[i][4]);


			  }

			  if (RevenueTotal[i][0]=='2G_3G_4G'){
				  voiceRevenue.push(RevenueTotal[i][1]);
				  smsRevenue.push(RevenueTotal[i][2]);
				  dataRevenue.push(RevenueTotal[i][3]);
				  vasRevenue.push(RevenueTotal[i][4]);


			  }
				 
			}

			


			
			}



			var ctxLine2 = document.getElementById('myChartLine2').getContext('2d');
			var myChartLine2 = new Chart(ctxLine2, {
				type: 'line',
				data: {
					labels:combinationTechnology ,
					datasets: [{
						label: 'Voice Revenue',
						data: voiceRevenue,
						backgroundColor: '#FFFF00',
						borderColor: '#FFFF00',
						borderWidth: 3,
						fill: false
					},
					{
						label: 'SMS Revenue',
						data: smsRevenue,
						backgroundColor: '#0000FF',
						borderColor: '#0000FF',
						borderWidth: 3,
						fill: false
					},
					{
						label: 'Data',
						data: dataRevenue,
						backgroundColor: '#FF0000',
						borderColor: '#FF0000',
						borderWidth: 3,
						fill: false
					},
					{
						label: 'VAS',
						data: vasRevenue,
						backgroundColor: '#1D4E08',
						borderColor: '#1D4E08',
						borderWidth: 3,
						fill: false
					}
				   
					]
				},
				options: {
					legend: {
						labels: {
						   fontSize: 18,
						   fontColor: "black",
						}
					},
					 title: {
						  display: true,
						  text: 'Site Technologies Revenues Analysis',
						  fontFamily: 'sans-serif',
						  fontColor: '#08526D',
						  fontSize: 22
					  },
					tooltips: {
						mode: 'index'
					},  
					pointLabels: { fontSize:22 },    
					scales: {
						yAxes: [{
							ticks: {
								beginAtZero: true
							}
						}],
						xAxes: [{
							ticks: {
								autoSkip: false,
								fontSize: 15,
								fontColor: "black",

							}
						}]
					}
				}
			});
		
   
}

			 function fetchtop10Revenue(){
				var siteName=[];
				var revenues=[];
			 for (var i = 0; i < SitesMore.length; i++) {
				 siteName.push(SitesMore[i][3]);
				 revenues.push(SitesMore[i][2]);
			 }
					var ctxLine = document.getElementById('top10revenue').getContext('2d');
					var myChartLine = new Chart(ctxLine, {
					    type: 'line',				    
					    data: {
					    	labels: siteName,
					        datasets: [{
					            label: 'Site Revenue',
					            data: revenues,
					            backgroundColor: 'rgba(8, 82, 109 , 0.2)',
					            borderColor: '#008000',
					            borderWidth: 3,
					            fill: false
					        },
					    
					        ]
					    },
					    options: {
					    	legend: {
					    	    labels: {
					    	       fontSize: 18,
					    	       fontColor: "black",
					    	    }
					    	},
					    	pointLabels: { fontSize:20 },
					    	  title: {
					              display: true,
					              text: 'Top 10 More Site Revenues',
					              fontFamily: 'sans-serif',
					              fontColor: '#08526D',
					              fontSize: 22
					          },
					     
					    }
					});

	
				
	}


			 function fetchtop10RevenueLess(){
				
				var ctxLine = document.getElementById('top10Less').getContext('2d');
                   var siteName=[];
                   var revenues=[];
				for (var i = 0; i < SitesLess.length; i++) {
					siteName.push(SitesLess[i][3]);
					revenues.push(SitesLess[i][2]);
				}

				var myChartLine = new Chart(ctxLine, {
					type: 'line',				    
					data: {
						labels: siteName,
						datasets: [{
							label: 'Site Revenue',
							data: revenues,
							backgroundColor: 'rgba(8, 82, 109 , 0.2)',
							borderColor: 'red',
							borderWidth: 3,
							fill: false
						},
					
						]
					},
					options: {
						legend: {
							labels: {
							   fontSize: 18,
							   fontColor: "black",
							}
						},
						pointLabels: { fontSize:20 },
						  title: {
							  display: true,
							  text: 'Least 10 Site Revenues',
							  fontFamily: 'sans-serif',
							  fontColor: '#08526D',
							  fontSize: 22
						  },
					 
					}
				});



			
			
}
			 

	
	
 

        
        

	
	
	
	




	function getContextPath() {
		   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
