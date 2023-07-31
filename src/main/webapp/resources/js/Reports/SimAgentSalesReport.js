function changeFunc(){
		
var countChartSelectBox = document.getElementById("countChartChangeOptions");
var countChartSelectedValue = countChartSelectBox.options[countChartSelectBox.selectedIndex].value;

var stackedChartSelectBox = document.getElementById("stackedChartChangeOptions");
var stackedChartSelectedVal = stackedChartSelectBox.options[stackedChartSelectBox.selectedIndex].value;

var lineChartSelectBox = document.getElementById("lineChartChangeOptions");
var lineChartSelectedVal = lineChartSelectBox.options[lineChartSelectBox.selectedIndex].value;

var countPieSelectBox = document.getElementById("countPieChangeOptions");
var countPieSelectedVal = countPieSelectBox.options[countPieSelectBox.selectedIndex].value;

var regStatusPieSelectBox = document.getElementById("regStatusPiechangeOptions");
var regStatusPieSelectedVal = regStatusPieSelectBox.options[regStatusPieSelectBox.selectedIndex].value;

var maxStackedChartSelectBox = document.getElementById("maxStackedChartChangeOptions");
var maxStackedChartSelectedVal = stackedChartSelectBox.options[maxStackedChartSelectBox.selectedIndex].value;

var minStackedChartSelectBox = document.getElementById("minStackedChartChangeOptions");
var minStackedChartSelectedVal = stackedChartSelectBox.options[minStackedChartSelectBox.selectedIndex].value;

var maxLineChartSelectBox = document.getElementById("maxLineChartChangeOptions");
var maxLineChartSelectedVal = lineChartSelectBox.options[maxLineChartSelectBox.selectedIndex].value;

var minLineChartSelectBox = document.getElementById("minLineChartChangeOptions");
var minLineChartSelectedVal = lineChartSelectBox.options[minLineChartSelectBox.selectedIndex].value;

$("#countChartChangeOptions").change(function(){
	if (countChartSelectedValue=="null")
		{
	 			$("#countChartSimSalesOpt").val('SIM_Sales').trigger('change');
	 			$('#minValMSFltr').val('');
				$('#maxValMSFltr').val('');
				$('#countChartTxtBtn').hide();
				$('#minValMSFltr').hide();
				$('#maxValMSFltr').hide();
				$('#countChartSimSalesOpt').hide();
				
		} 
		else{
		$("#countChartFilterDiv").show();
		$('#countChartTxtBtn').show();
		$('#minValMSFltr').show();
		$('#maxValMSFltr').show();
		$('#countChartSimSalesOpt').show();

		}
		 
});		

$("#stackedChartChangeOptions").change(function(){

	if (stackedChartSelectedVal=="isBetween"){
		$("#stackedChartFilterDiv").show();
		$('#stackedChartTxtBtn').show();
		$('#minValStackedFltr').show();
		$('#maxValStackedFltr').show();
		$('#stackedChartSimSalesOpt').show();

		}
		 else if (stackedChartSelectedVal=="null")
		{
			 $("#stackedChartSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValStackedFltr').val('');
				$('#maxValStackedFltr').val('');
				$('#stackedChartTxtBtn').hide();
				$('#minValStackedFltr').hide();
				$('#maxValStackedFltr').hide();
				$('#stackedChartSimSalesOpt').hide();
				
		} 
	});
	
$("#maxStackedChartChangeOptions").change(function(){

	if (maxStackedChartSelectedVal=="isBetween"){
	
		$("#maxStackedChartFilterDiv").show();
		$('#maxStackedChartTxtBtn').show();
		$('#minValMaxStackedFltr').show();
		$('#maxValMaxStackedFltr').show();
		$('#maxStackedChartSimSalesOpt').show();

		}
		 else if (maxStackedChartSelectedVal=="null")
		{
			 $("#maxStackedChartSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValMaxStackedFltr').val('');
				$('#maxValMaxStackedFltr').val('');
				$('#maxStackedChartTxtBtn').hide();
				$('#minValMaxStackedFltr').hide();
				$('#maxValMaxStackedFltr').hide();
				$('#maxStackedChartSimSalesOpt').hide();
				
		} 
	});
	
$("#minStackedChartChangeOptions").change(function(){

	if (minStackedChartSelectedVal=="isBetween"){
	
		$("#minStackedChartFilterDiv").show();
		$('#minStackedChartTxtBtn').show();
		$('#minValMinStackedFltr').show();
		$('#maxValMinStackedFltr').show();
		$('#minStackedChartSimSalesOpt').show();

		}
		 else if (minStackedChartSelectedVal=="null")
		{
			 $("#minStackedChartSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValMinStackedFltr').val('');
				$('#maxValMinStackedFltr').val('');
				$('#minStackedChartTxtBtn').hide();
				$('#minValMinStackedFltr').hide();
				$('#maxValMinStackedFltr').hide();
				$('#minStackedChartSimSalesOpt').hide();
				
		} 
	});


$("#lineChartChangeOptions").change(function(){

	if (lineChartSelectedVal=="isBetween"){
		$("#lineChartFilterDiv").show();
		$('#lineChartTxtBtn').show();
		$('#minValLineFltr').show();
		$('#maxValLineFltr').show();
		$('#lineChartSimSalesOpt').show();

		}
		 else if (lineChartSelectedVal=="null")
		{
			 $("#lineChartSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValLineFltr').val('');
				$('#maxValLineFltr').val('');
				$('#lineChartTxtBtn').hide();
				$('#minValLineFltr').hide();
				$('#maxValLineFltr').hide();
				$('#lineChartSimSalesOpt').hide();
				
		} 
	});
	
$("#maxLineChartChangeOptions").change(function(){

	if (maxLineChartSelectedVal=="isBetween"){
		$("#maxLineChartFilterDiv").show();
		$('#maxLineChartTxtBtn').show();
		$('#minValMaxLineFltr').show();
		$('#maxValMaxLineFltr').show();
		$('#maxLineChartSimSalesOpt').show();

		}
		 else if (maxLineChartSelectedVal=="null")
		{
			 $("#maxLineChartSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValMaxLineFltr').val('');
				$('#maxValMaxLineFltr').val('');
				$('#maxLineChartTxtBtn').hide();
				$('#minValMaxLineFltr').hide();
				$('#maxValMaxLineFltr').hide();
				$('#maxLineChartSimSalesOpt').hide();
				
		} 
	});
	
$("#minLineChartChangeOptions").change(function(){

	if (minLineChartSelectedVal=="isBetween"){
		$("#minLineChartFilterDiv").show();
		$('#minLineChartTxtBtn').show();
		$('#minValMinLineFltr').show();
		$('#maxValMinLineFltr').show();
		$('#minLineChartSimSalesOpt').show();

		}
		 else if (minLineChartSelectedVal=="null")
		{
			 $("#minLineChartSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValMinLineFltr').val('');
				$('#maxValMinLineFltr').val('');
				$('#minLineChartTxtBtn').hide();
				$('#minValMinLineFltr').hide();
				$('#maxValMinLineFltr').hide();
				$('#minLineChartSimSalesOpt').hide();
				
		} 
	});


$("#countPieChangeOptions").change(function(){

	if (countPieSelectedVal=="isBetween"){
		$("#countPieFilterDiv").show();
		$('#countPieTxtBtn').show();
		$('#minValPiesFltr').show();
		$('#maxValPiesFltr').show();
		$('#countPieSimSalesOpt').show();

		}
		 else if (countPieSelectedVal=="null")
		{
			 $("#countPieSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValPiesFltr').val('');
				$('#maxValPiesFltr').val('');
				$('#countPieTxtBtn').hide();
				$('#minValPiesFltr').hide();
				$('#maxValPiesFltr').hide();
				$('#countPieSimSalesOpt').hide();
				
		} 
	});

$("#regStatusPiechangeOptions").change(function(){

	if (regStatusPieSelectedVal=="isBetween"){
		$("#regStatusPieFilterDiv").show();
		$('#regStatusPieTxtBtn').show();
		$('#minValRegStatFltr').show();
		$('#maxValRegStatFltr').show();
		$('#regStatusPieSimSalesOpt').show();

		}
		 else if (regStatusPieSelectedVal=="null")
		{
			 $("#regStatusPieSimSalesOpt").val('SIM_SALES').trigger('change');
				$('#minValRegStatFltr').val('');
				$('#maxValRegStatFltr').val('');
				$('#regStatusPieTxtBtn').hide();
				$('#minValRegStatFltr').hide();
				$('#maxValRegStatFltr').hide();
				$('#regStatusPieSimSalesOpt').hide();
				
		} 
	});
}	// end change fct 


function changeSalesFunc() {

	$("#countChartSimSalesOpt").change(function(){
		$('#minValMSFltr').val('');
		$('#maxValMSFltr').val('');
		});
	$("#stackedChartSimSalesOpt").change(function(){
		$('#minValStackedFltr').val('');
		$('#maxValStackedFltr').val('');
		});
	$("#maxStackedChartSimSalesOpt").change(function(){
		$('#minValMaxStackedFltr').val('');
		$('#maxValMaxStackedFltr').val('');
		});
	$("#minStackedChartSimSalesOpt").change(function(){
		$('#minValMinStackedFltr').val('');
		$('#maxValMinStackedFltr').val('');
		});
	$("#lineChartSimSalesOpt").change(function(){
		$('#minValLineFltr').val('');
		$('#maxValLineFltr').val('');
		});
	$("#maxLineChartSimSalesOpt").change(function(){
		$('#minValMaxLineFltr').val('');
		$('#maxValMaxLineFltr').val('');
		});
	$("#minLineChartSimSalesOpt").change(function(){
		$('#minValMinLineFltr').val('');
		$('#maxValMinLineFltr').val('');
		});
	$("#countPieSimSalesOpt").change(function(){
		$('#minValPiesFltr').val('');
		$('#maxValPiesFltr').val('');
		});
	$("#regStatusPieSimSalesOpt").change(function(){
		$('#minValRegStatFltr').val('');
		$('#maxValRegStatFltr').val('');
		});

	}	
	


///////CHARTSS
                  //*********************** Multi-series Column 2D

						 function AgentSales(msColumnChartObj){
							var agentFullName=[];
							var simSalesCount=[];
							
							var objSalesCount={};
							 let data =[];
							 label1 = "";
							 
							 if(msColumnChartObj !=null && msColumnChartObj.length !=0){
							 label1 = "SIM Card Sales";
							 agentFullName=msColumnChartObj[0];
							 simSalesCount=msColumnChartObj[1];
							
							 }
							 else{
								 objSalesCount.value="0";
								 agentFullName.push('No Data');
								 simSalesCount.push(objSalesCount);
									
									objSalesCount={};
									label1 = "No SIM Sales";
									}
							 
								
								
						     FusionCharts.ready(function() {
						  	   var AgentSalesParam = new FusionCharts({
						  	     type: 'scrollColumn2d',
						  	     renderAt: 'countChartContainer',
						  	     width: '100%',
						  	     height: '600',
						  	     dataFormat: 'json',

						  	     dataSource: {
					  	       "chart": {
					  	         "theme": "fusion",
					  	         "caption": "Agent SIM Sales Count",
					  	       "captionFontSize": "20",
					  	         "xAxisname": "Agents",
					  	         "xAxisNameFontSize":"22px",
					  	         "yAxisNameFontSize":"22px",
					  	         "yAxisName": "SIM Sales Count",
					  	       "bgColor": "EEEEEE,CCCCCC",
					  	        "bgratio": "60,40",
					  	        "bgAlpha": "70,80",
					  	        "bgAngle": "180",
						  	     "yAxisLineThickness":"1",
						  	     "yAxisLineColor":"#999999",
						  	     "showAlternateHGridColor":"1",
						  	   "showAlternateVGridColor":"1",
								
						       // "forceYAxisValueDecimals": "1",
						        
							  	"yAxisValueFont": "verdana,sans",
						  	     "yAxisValueFontSize": "10px",
						  	     "yAxisValueFontColor": "#ff0000",
						  	     "yAxisValueFontBold": "1",
						  	     "yAxisValueFontItalic": "1",
						  	    
					  	         "labelFontSize":"10px",
					  	         "labelAlpha":"90",
					  	         "labelFontBold":"0",
					  	        // "numberSuffix": " SIM",
					  	         "borderThickness": "1",
					  	         "plotFillAlpha": "90",
					  	         "ValueFontColor":"#030303",						  	         
						  	   	//"numVisiblePlot": "24",
						       // "labelDisplay": "rotate",
						      //  "slantLabels": "1",
					              "drawcrossline": "1",
						        "scrollheight": "15",
						        "flatScrollBars": "0",
						        "scrollShowButtons": "1",									        
						        "scrollColor": "#cccccc",
						        
						        "showHoverEffect": "1"
						  	       },
						  	       "categories": [{
						  	    	   
						  	    	   "category": (agentFullName=="No Data")? " " : agentFullName
							  	        
						  	       }],
						  	       "dataset": [{
							    	         "seriesname": label1,
							    	         "data": simSalesCount,
							    	         "color":"#FFFF00"
							    	       }
							    	       
								    	
								    	     
							    	       ]

						  	     }
						  	   });

						  	 AgentSalesParam.render();
						  	 });

} // end 2D chart

function getElement(str){
							var strObj = {};
			  
							$.each(str, function(key, value){
								if (key == 0 ) { if(str.length>1) strObj = '[{ "label" :'+' "'+value.toString()+'" '+'},';
													else strObj = '[{ "label" :'+' "'+value.toString()+'" '+'}]';}
								else if (key < (str.length - 1) && key > 0)	strObj +='{ "label" :'+' "'+value.toString()+'" '+'},'; 
								else strObj += '{ "label" :'+' "'+value.toString()+'" '+'}]'; 
								         
			  	 
						         });
					       	return strObj;
									  
					 
}


		// *************************** bar chart ***************************
				function fetchCountPerDay(StackedandLineCount, startDate, endDate) {
					var date=[];					
					var	totalSimSales = [];
					var minSimSales =[];
					var maxSimSales =[];
					
					let data = [];
					var maxLabel;
					var minLabel;
					var maxChecked = document.getElementById("Max");
					var minChecked = document.getElementById("Min");
					

					 if(StackedandLineCount !=null && StackedandLineCount.length !=0){
					 
					 
						  if (maxChecked.checked == true && minChecked.checked == false){ 
					  
						 maxLabel = "Maximum Agent Sales";
					     minLabel = "Minimum Agent Sales";
					     
					     date=StackedandLineCount[0];
					     maxSimSales = StackedandLineCount[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
					   	 minSimSales =[];
						 data= [date,maxSimSales,minSimSales];
						
						
				
						chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#007500"
				                }, 
				                
				                { "seriesname": minLabel,
			                        "data": data[2],
			                        "color":"#FF0000"
				                }
				                ];
						}
						
						
						else if (minChecked.checked == true && maxChecked.checked == false){ 
					 		maxLabel = "Maximum Agent Sales";
					     	minLabel = "Minimum Agent Sales";
					     	
					     	date=StackedandLineCount[0];
					     	minSimSales = StackedandLineCount[1];
					   		maxSimSales =[];
							data= [date,maxSimSales,minSimSales];
						
						chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#007500"
				                }, 
				                
				                { "seriesname": minLabel,
			                        "data": data[2],
			                        "color":"#FF0000"
				                }
				                ];
						
					
					}	
					else {
					
					maxLabel = "SIM Card Sales";
					
					date=StackedandLineCount[0];
					totalSimSales = StackedandLineCount[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
					data= [date,totalSimSales];
					     
					     chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#FFFF00"
				            	}
				              	];
					    
					}
										
					}
						else{

						date.push("NO SIM Card Sales");
						totalSimSales.push("0");
						
						maxLabel = "NO SIM Card Sales";
						data= [date,totalSimSales,[]];
						
						chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#FFFF00"
				            	}
				              ];
					} 
					
					
					FusionCharts.ready(function(){
						var chartObj = new FusionCharts({
			    type: 'scrollstackedcolumn2d',
			    renderAt: 'chartContainerStacked',
			    width: '100%',
			    height: '650',
			    dataFormat: 'json',
			    dataSource: {
			        "chart": {
			            "theme": "fusion",
			        
			            "caption": "Agent SIM Sales w.r.t the date {br}{br}",
			            "subcaption": ""+startDate+" - "+endDate+"",
			            "subcaptionFontSize": "14",
			            "subcaptionFontColor": "#808080",
						"captionFontSize": "20",
						//"numbersuffix": " SIM",
			            
			    	    "xAxisNameFontSize":"22px",
			    	    "yAxisNameFontSize":"22px",
			            "plottooltext": "<b>$seriesName</b><hr>$label: <b>$dataValue</b>",
			            "plottooltextFontSize":"16px",
			            "xaxisname": "Dates",
			            "yaxisname": "SIM Sales Count",
			            "drawcrossline": "1",
			            "numVisiblePlot": "5",
			            "bgColor": "EEEEEE,CCCCCC",
			            "bgratio": "60,40",
			            "bgAlpha": "70,80",
			            "bgAngle": "180",
						"yAxisLineThickness":"1",
						"yAxisLineColor":"#999999",
						"showAlternateHGridColor":"1",
						"showAlternateVGridColor":"1",
						"yAxisValueFont": "verdana,sans",
						"yAxisValueFontSize": "10px",
						"yAxisValueFontColor": "#ff0000",
						"yAxisValueFontBold": "1",
						"yAxisValueFontItalic": "1",
						"yAxisValueDecimals": "2",
				       // "forceYAxisValueDecimals": "1",
				        //"decimals":"2",
						"scrollheight": "15",
				        "flatScrollBars": "0",
				        "scrollShowButtons": "1",									        
				        "scrollColor": "#cccccc",
						"labelFontSize":"14px",
						"labelAlpha":"90",
						"labelFontBold":"0",
						"borderThickness": "1",
						"plotFillAlpha": "90",
						"ValueFontColor":"#030303",				
			        },
			        "categories": [{
			            "category": (date =="NO SIM Card Sales")? date : data[0]
			        }],
			    
			                 "dataset":chartDataSet
			          
			    }
			});
						chartObj.render();
				       });
		}//END bar chart

	
		//--------->Line chart 
		function fetchLineChartCount(StackedandLineCount) {
			var startDate=[],totalSimSales = [];
			var objCount={};
			let data = [];
			
			var minSimSales =[];
			var maxSimSales =[];
					
			var maxLabel;
			var minLabel;
		    var maxChecked = document.getElementById("Max");
			var minChecked = document.getElementById("Min");
					
			
			 if(StackedandLineCount !=null && StackedandLineCount.length !=0){
	 			
	 			 if (maxChecked.checked == true && minChecked.checked == false){ 
					  
						 maxLabel = "Maximum Agent Sales";
					     minLabel = "Minimum Agent Sales";
					     
					     startDate=StackedandLineCount[0];
					     maxSimSales = StackedandLineCount[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
					   	 minSimSales =[];
						 data= [startDate,maxSimSales,minSimSales];
						
						
				
						chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#007500"
				                }, 
				                
				                { "seriesname": minLabel,
			                        "data": data[2],
			                        "color":"#FF0000"
				                }
				                ];
						}
						
								else if (minChecked.checked == true && maxChecked.checked == false){ 
					 		maxLabel = "Maximum Agent Sales";
					     	minLabel = "Minimum Agent Sales";
					     	
					     	startDate=StackedandLineCount[0];
					     	minSimSales = StackedandLineCount[1];
					   		maxSimSales =[];
							data= [startDate,maxSimSales,minSimSales];
						
						chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#007500"
				                }, 
				                
				                { "seriesname": minLabel,
			                        "data": data[2],
			                        "color":"#FF0000"
				                }
				                ];
						
					
					}	
						
					else {
					
					maxLabel = "SIM Card Sales";
					
					startDate=StackedandLineCount[0];
					totalSimSales = StackedandLineCount[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
					data= [startDate,totalSimSales];
					     
					     chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#FFFF00"
				            	}
				              	];
					    
					}
										
					}	
				
						else{

						startDate.push("NO SIM Card Sales");
						totalSimSales.push("0");
						
						maxLabel = "NO SIM Card Sales";
						data= [startDate,totalSimSales,[]];
						
						chartDataSet = [ { "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#FFFF00"
				            	}
				              ];
					} 
					
						
						
			
					
				
			const dataSource = {
					  chart: {
					    "caption": $("#period").children("option:selected").val()+" Agent SIM Sales Count Lines ",
					    "yaxisname": "SIM Count","showhovereffect": "1","drawcrossline": "1","captionFontSize": "20",
						"xAxisNameFontSize":"15px","yAxisNameFontSize":"22px","yAxisLineThickness":"1","yAxisLineColor":"#999999","showAlternateHGridColor":"1",
						"showAlternateVGridColor":"1","yAxisValueFont": "verdana,sans","yAxisValueFontSize": "10px","yAxisValueFontColor": "#ff0000","yAxisValueFontBold": "1",
						"yAxisValueFontItalic": "1","labelFontSize":"10px","labelAlpha":"90","labelFontBold":"0","borderThickness": "1",
						"plotFillAlpha": "90","ValueFontColor":"#030303","bgColor": "EEEEEE,CCCCCC",
			  	        "bgratio": "60,40",
			  	        "bgAlpha": "70,80",
			  	        "bgAngle": "180",										    
					    "theme": "fusion"
					  },
					  categories: [
					    {
					      category: data[0]//jQuery.parseJSON(getElement(data[0]))

					    }
					  ],
					  dataset: chartDataSet
					};

					FusionCharts.ready(function() {
					  var myChart = new FusionCharts({
					    type: "scrollline2d",
					    renderAt: "lineChartContainer",
					    width: "100%",
					    height: "600",
					    dataFormat: "json",
					    dataSource
					  }).render();
					});


			}// end line chart

			
			
			
			// pie chart based on total count
			 function PieCount(chartData){
							var label1,label2,label3,label4,label5;
							var firstRngCount=0,secondRngCount=0,thirdRngCount=0,fourthRngCount=0;fifthRngCount=0,totCount=0;

									
									if(	chartData.length!=0 && chartData !=""){
									
										    label1 = "0-25";label2="26-50";label3="51-75";label4="76-100";label5="100 & Above";
										    firstRngCount =chartData[0];
										    secondRngCount=chartData[1];
										    thirdRngCount=chartData[2];
										    fourthRngCount=chartData[3];
										    fifthRngCount=chartData[4];
											totCount=chartData[5];
												
									}
										else{
											label1 = "NO Data";label2="";label3="";label4="";label5="";
											
											firstRngCount =100;
											secondRngCount=0;
											thirdRngCount=0;
											fourthRngCount=0;
											fifthRngCount=0;
											totCount=0;

										
									 
										}
														
					 		
		

							const dataSource = {
									
							  chart: {
								    caption: "Agent SIM sales count split based on total count",
								    subcaption: "For a whole-count of "+totCount+" Agents",
								    captionFontSize:"16",
								    captionOnTop: "0",
								    showvalues: "1",
						            subcaptionFontColor: "#254E7C",   
								    showpercentintooltip: "0",
								    numberprefix: "%",
								    enablemultislicing: "1",
								    theme: "zune",
								    "bgColor": "EEEEEE,CCCCCC",
							        "bgratio": "60,40",
							        "bgAlpha": "70,80",
							        "bgAngle": "180",
							  },

							  data: [
							    {
							      label: label1,
							      value: firstRngCount,
							      "color":"#FFFF00"
							      
							    },
							    {
							      label: label2,
							      value: secondRngCount,
							      "color":"#0000FF"
							    },
							    {
							      label: label3,
							      value: thirdRngCount,
							      "color":"#FF0000"
							    },
							    {
								      label: label4,
								      value: fourthRngCount,
								      //"color":"#FF0000"
								},
								{
								      label: label5,
								      value: fifthRngCount,
								     // "color":"#FF0000"
								}
							  ]
							};
							
							FusionCharts.ready(function() {
							  var myChart = new FusionCharts({
								    type: "pie2d",
								    renderAt: "countChartPie",
								    width: "100%",
								    height: "410",
								    dataFormat: "json",
								    dataSource
							  });
							  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
							  myChart.setJSONData(dataSource);		            
							  myChart.render();
							});
															  

}// end pie chart


function agentRegPie(regCount){

	var label1,label2;
	var ussdReg= [];
	var ipReg =[];
	
	var totCount=[];
	if(regCount != "" &&  regCount[2]!=0){
     
		ussdReg=regCount[0];
 		ipReg = regCount[1];
 		totCount = regCount[2];

 		label1 = "USSD";label2="Via IP";
			
	}
	else{
		label1 = "No Data";label2="";
		
		ussdReg =100;
		ipReg=0;
		totCount=0;
		
			
	}


	const dataSource = {
			
	  chart: {
	    caption: "Agent SIM sales count split based on SIM registration",
	    subcaption: "For a total of "+totCount+" Clients",
	    captionFontSize:"16",
	    captionOnTop: "0",
	    showvalues: "1",
        subcaptionFontColor: "#254E7C",   
	    showpercentintooltip: "0",
	    numberprefix: "%",
	    enablemultislicing: "1",
	    theme: "zune",
	    "bgColor": "EEEEEE,CCCCCC",
        "bgratio": "60,40",
        "bgAlpha": "70,80",
        "bgAngle": "180",
	  },

	  data: [
	    {
	      label: label1,
	      value: ussdReg,
	      "color":"#FFFF00"
	    },
	    {
	      label: label2,
	      value: ipReg,
	      "color":"#0000FF"
	    }
	  ]
	};
	
	FusionCharts.ready(function() {
	  var myChart = new FusionCharts({
	    type: "pie2d",
	    renderAt: "clientRegStatChartContainer",
	    width: "100%",
	    height: "480",
	    dataFormat: "json",
	    dataSource
	  });
	  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
	  myChart.setJSONData(dataSource);		            
	  myChart.render();
	});
									  

	}
	
	
		
	// *************************** bar chart for daily Max Min***************************
	function fetchMaxMinCountPerDay(maxStackedandLineCount,minStackedandLineCount, startDate, endDate) {
				
					var date=[];
					
					var	totalSimSales = [];
					var MinSimSales =[];
					var MaxSimSales =[];
					
					let data = [];
					var maxLabel;
					var minLabel;
					


				if( (maxStackedandLineCount !=null || minStackedandLineCount !=null) && (maxStackedandLineCount.length !=0 || minStackedandLineCount.length !=0) ){
							
					     maxLabel = "Maximum Agent Sales";
					     minLabel = "Minimum Agent Sales";
					     
					     date=maxStackedandLineCount[0];
					     MaxSimSales = maxStackedandLineCount[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
					    
					    MinSimSales = minStackedandLineCount[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
					    
						data= [date,MaxSimSales,MinSimSales];
					}else{
						date.push("NO SIM Card Sales");
						MaxSimSales.push("0");
						MinSimSales.push("0");
						
						
						maxLabel = "NO SIM Card Sales";
						data= [date,MaxSimSales,MinSimSales];
						} 
					
					
					
					FusionCharts.ready(function(){
						var chartObj = new FusionCharts({
			    type: 'scrollstackedcolumn2d',
			    renderAt: 'chartContainerStacked',
			    width: '100%',
			    height: '650',
			    dataFormat: 'json',
			    dataSource: {
			        "chart": {
			            "theme": "fusion",
			        
			            "caption": "Agent SIM Sales w.r.t the date {br}{br}",
			            "subcaption": ""+startDate+" - "+endDate+"",
			            "subcaptionFontSize": "14",
			            "subcaptionFontColor": "#808080",
						"captionFontSize": "20",
						//"numbersuffix": " SIM",
			            
			    	    "xAxisNameFontSize":"22px",
			    	    "yAxisNameFontSize":"22px",
			            "plottooltext": "<b>$seriesName</b><hr>$label: <b>$dataValue</b>",
			            "plottooltextFontSize":"16px",
			            "xaxisname": "Dates",
			            "yaxisname": "SIM Sales Count",
			            "drawcrossline": "1",
			            "numVisiblePlot": "5",
			            "bgColor": "EEEEEE,CCCCCC",
			            "bgratio": "60,40",
			            "bgAlpha": "70,80",
			            "bgAngle": "180",
						"yAxisLineThickness":"1",
						"yAxisLineColor":"#999999",
						"showAlternateHGridColor":"1",
						"showAlternateVGridColor":"1",
						"yAxisValueFont": "verdana,sans",
						"yAxisValueFontSize": "10px",
						"yAxisValueFontColor": "#ff0000",
						"yAxisValueFontBold": "1",
						"yAxisValueFontItalic": "1",
						"yAxisValueDecimals": "2",
				       // "forceYAxisValueDecimals": "1",
				        //"decimals":"2",
						"scrollheight": "15",
				        "flatScrollBars": "0",
				        "scrollShowButtons": "1",									        
				        "scrollColor": "#cccccc",
						"labelFontSize":"14px",
						"labelAlpha":"90",
						"labelFontBold":"0",
						"borderThickness": "1",
						"plotFillAlpha": "90",
						"ValueFontColor":"#030303",				
			        },
			        "categories": [{
			            "category": (date =="NO SIM Card Sales")? date : data[0]
			        }],
			    
			                "dataset": [
				                {
			                        "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#007500"

				                },
				                 {
			                        "seriesname": minLabel,
			                        "data": data[2],
									"color":"#FF0000"
				                }
			                   
			                   
			                ]
			            
			              
			          
			    }
			});
						chartObj.render();
				       });
	}//END bar chart for max/min
	
	
	
		//--------->Line chart for daily max min sales
		function fetchMaxMinLineChartCount(maxStackedandLineCount,minStackedandLineCount) {
			
			var startDate=[],MaxSimSales = [],MinSimSales = [];
			let data = [];

			var maxLabel;
			var minLabel;
			
			if( (maxStackedandLineCount !=null || minStackedandLineCount !=null) && (maxStackedandLineCount.length !=0 || minStackedandLineCount.length !=0) ){
								
			
							
					maxLabel = "Maximum Agent Sales";
					minLabel = "Minimum Agent Sales";
									     
					startDate = maxStackedandLineCount[0];
					MaxSimSales=maxStackedandLineCount[1];
					MinSimSales=minStackedandLineCount[1];
					
			 data= [startDate,MaxSimSales,MinSimSales];
				 

			}

				else{
					maxLabel = "No SIM Card Sales";
					
				startDate = [];
				MaxSimSales = [];		
				MaxSimSales =0;
				MinSimSales = [];		
				MinSimSales =0;
				
			    startDate.push(($('#startdate1').val().split(" "))[0].toString()); 
			     data= [startDate,MaxSimSales,MinSimSales];
				 
				}
				
			const dataSource = {
					  chart: {
					    "caption": $("#period").children("option:selected").val()+" Agent SIM Sales Count Lines ",
					    "yaxisname": "SIM Count","showhovereffect": "1","drawcrossline": "1","captionFontSize": "20",
						"xAxisNameFontSize":"15px","yAxisNameFontSize":"22px","yAxisLineThickness":"1","yAxisLineColor":"#999999","showAlternateHGridColor":"1",
						"showAlternateVGridColor":"1","yAxisValueFont": "verdana,sans","yAxisValueFontSize": "10px","yAxisValueFontColor": "#ff0000","yAxisValueFontBold": "1",
						"yAxisValueFontItalic": "1","labelFontSize":"10px","labelAlpha":"90","labelFontBold":"0","borderThickness": "1",
						"plotFillAlpha": "90","ValueFontColor":"#030303","bgColor": "EEEEEE,CCCCCC",
			  	        "bgratio": "60,40",
			  	        "bgAlpha": "70,80",
			  	        "bgAngle": "180",										    
					    "theme": "fusion"
					  },
					  categories: [
					    {
					      category: data[0]//jQuery.parseJSON(getElement(data[0]))

					    }
					  ],
					  dataset: [
					    {
					      seriesname: maxLabel,
					      data: data[1],
					      "color":"#007500"

					    },   {
							"seriesname": minLabel,
							"data": data[2],
							"color":"#FF0000"
					 }
					    
					  ]
					};

					FusionCharts.ready(function() {
					  var myChart = new FusionCharts({
					    type: "scrollline2d",
					    renderAt: "lineChartContainer",
					    width: "100%",
					    height: "600",
					    dataFormat: "json",
					    dataSource
					  }).render();
					});


			}// end line chart for max/min		
	
	
	
	
	// *************************** Stacked chart for daily Min when max and min are BOTH SELECTED ***************************
	function fetchMaxCountPerDay(maxStackedandLineCount, startDate, endDate) {
				
					var date=[];
					var MaxSimSales =[];
					let data = [];
					var maxLabel;

				if( maxStackedandLineCount !=null && maxStackedandLineCount.length !=0 ){
							
					     maxLabel = "Maximum Agent Sales";
					    
					     date=maxStackedandLineCount[0];
					     MaxSimSales = maxStackedandLineCount[1];
						 data= [date,MaxSimSales];
					}
					else{
						maxLabel = "NO SIM Card Sales";
						
						date.push("NO SIM Card Sales");
						MaxSimSales.push("0");						
						data= [date,MaxSimSales];
					} 
					
					
					
					FusionCharts.ready(function(){
					
					var chartObj = new FusionCharts({
				    type: 'scrollstackedcolumn2d',
				    renderAt: 'maxChartContainerStacked',
				    width: '100%',
				    height: '650',
				    dataFormat: 'json',
				    dataSource: {
				        "chart": {
				            "theme": "fusion",
				        
				            "caption": "Maximum Agent SIM Sales w.r.t the date {br}{br}",
				            "subcaption": ""+startDate+" - "+endDate+"",
				            "subcaptionFontSize": "14",
				            "subcaptionFontColor": "#808080",
							"captionFontSize": "20",
							"xAxisNameFontSize":"22px",
				    	    "yAxisNameFontSize":"22px",
				            "plottooltext": "<b>$seriesName</b><hr>$label: <b>$dataValue</b>",
				            "plottooltextFontSize":"16px",
				            "xaxisname": "Dates",
				            "yaxisname": "SIM Sales Count",
				            "drawcrossline": "1",
				            "numVisiblePlot": "5",
				            "bgColor": "EEEEEE,CCCCCC",
				            "bgratio": "60,40",
				            "bgAlpha": "70,80",
				            "bgAngle": "180",
							"yAxisLineThickness":"1",
							"yAxisLineColor":"#999999",
							"showAlternateHGridColor":"1",
							"showAlternateVGridColor":"1",
							"yAxisValueFont": "verdana,sans",
							"yAxisValueFontSize": "10px",
							"yAxisValueFontColor": "#ff0000",
							"yAxisValueFontBold": "1",
							"yAxisValueFontItalic": "1",
							"yAxisValueDecimals": "2",
							"scrollheight": "15",
					        "flatScrollBars": "0",
					        "scrollShowButtons": "1",									        
					        "scrollColor": "#cccccc",
							"labelFontSize":"14px",
							"labelAlpha":"90",
							"labelFontBold":"0",
							"borderThickness": "1",
							"plotFillAlpha": "90",
							"ValueFontColor":"#030303",				
				        },
			        "categories": [{
			            "category": (date =="NO SIM Card Sales")? date : data[0]
			        }],
			    
			                "dataset": [
				                {
			                        "seriesname": maxLabel,
			                        "data": data[1],
			                        "color":"#007500"

				                }
			                ]			              
			          
			    }
			});
						chartObj.render();
				       });
	}//END stacked chart for maximum
	
	
		
	// *************************** Stacked chart for daily Min when max and min are BOTH SELECTED ***************************
	function fetchMinCountPerDay(minStackedandLineCount, startDate, endDate) {
				
					var date=[];					
					var MinSimSales =[];
					let data = [];
					var minLabel;

				if( minStackedandLineCount !=null && minStackedandLineCount.length !=0 ){
					     minLabel = "Minimum Agent Sales";
					     
					     date=minStackedandLineCount[0];
					     MinSimSales = minStackedandLineCount[1];
					     data= [date,MinSimSales];
					}
					else{
						minLabel = "NO SIM Card Sales";
						
						date.push("NO SIM Card Sales");
						MinSimSales.push("0");						
						data= [date,MinSimSales];
					} 
					
					
					FusionCharts.ready(function(){
					
				var chartObj = new FusionCharts({
			    type: 'scrollstackedcolumn2d',
			    renderAt: 'minChartContainerStacked',
			    width: '100%',
			    height: '650',
			    dataFormat: 'json',
			    dataSource: {
			        "chart": {
			            "theme": "fusion",
			            "caption": "Minimum Agent SIM Sales w.r.t the date {br}{br}",
			            "subcaption": ""+startDate+" - "+endDate+"",
			            "subcaptionFontSize": "14",
			            "subcaptionFontColor": "#808080",
						"captionFontSize": "20",
			    	    "xAxisNameFontSize":"22px",
			    	    "yAxisNameFontSize":"22px",
			            "plottooltext": "<b>$seriesName</b><hr>$label: <b>$dataValue</b>",
			            "plottooltextFontSize":"16px",
			            "xaxisname": "Dates",
			            "yaxisname": "SIM Sales Count",
			            "drawcrossline": "1",
			            "numVisiblePlot": "5",
			            "bgColor": "EEEEEE,CCCCCC",
			            "bgratio": "60,40",
			            "bgAlpha": "70,80",
			            "bgAngle": "180",
						"yAxisLineThickness":"1",
						"yAxisLineColor":"#999999",
						"showAlternateHGridColor":"1",
						"showAlternateVGridColor":"1",
						"yAxisValueFont": "verdana,sans",
						"yAxisValueFontSize": "10px",
						"yAxisValueFontColor": "#ff0000",
						"yAxisValueFontBold": "1",
						"yAxisValueFontItalic": "1",
						"yAxisValueDecimals": "2",
						"scrollheight": "15",
				        "flatScrollBars": "0",
				        "scrollShowButtons": "1",									        
				        "scrollColor": "#cccccc",
						"labelFontSize":"14px",
						"labelAlpha":"90",
						"labelFontBold":"0",
						"borderThickness": "1",
						"plotFillAlpha": "90",
						"ValueFontColor":"#030303",				
			        },
			        "categories": [{
			            "category": (date =="NO SIM Card Sales")? date : data[0]
			        }],
			                "dataset": [
				                {
			                        "seriesname": minLabel,
			                        "data": data[1],
			                        "color":"#FF0000"

				                }
			                ]
			    }
			});
						chartObj.render();
				       });
	}//END min stacked chart for max AND min condition
	
	
	
	
		//--------->line chart for max when max and min are BOTH checked	
		function fetchMaxLineChartCount(maxStackedandLineCount) {
			
			var startDate=[],MaxSimSales = [];
			let data = [];

			var maxLabel;
			
			
			if( maxStackedandLineCount !=null && maxStackedandLineCount.length !=0 ){	
					
					maxLabel = "Maximum Agent Sales";
					startDate = maxStackedandLineCount[0];
					MaxSimSales=maxStackedandLineCount[1];
					data= [startDate,MaxSimSales];
			}
			else{
			
				maxLabel = "No SIM Card Sales";
				startDate = [];
				MaxSimSales = [];		
				MaxSimSales =0;
			    startDate.push(($('#startdate1').val().split(" "))[0].toString()); 
			    data= [startDate,MaxSimSales];
				 
			}
				
			const dataSource = {
					  chart: {
					    "caption": $("#period").children("option:selected").val()+" Maximum Agent SIM Sales Count Lines ",
					    "yaxisname": "SIM Count","showhovereffect": "1","drawcrossline": "1","captionFontSize": "20",
						"xAxisNameFontSize":"15px","yAxisNameFontSize":"22px","yAxisLineThickness":"1","yAxisLineColor":"#999999","showAlternateHGridColor":"1",
						"showAlternateVGridColor":"1","yAxisValueFont": "verdana,sans","yAxisValueFontSize": "10px","yAxisValueFontColor": "#ff0000","yAxisValueFontBold": "1",
						"yAxisValueFontItalic": "1","labelFontSize":"10px","labelAlpha":"90","labelFontBold":"0","borderThickness": "1",
						"plotFillAlpha": "90","ValueFontColor":"#030303","bgColor": "EEEEEE,CCCCCC",
			  	        "bgratio": "60,40",
			  	        "bgAlpha": "70,80",
			  	        "bgAngle": "180",										    
					    "theme": "fusion"
					  },
					  categories: [
					    {
					      category: data[0]
					    }
					  ],
					  dataset: [
					    {
					      seriesname: maxLabel,
					      data: data[1],
					      "color":"#007500"

					    }
					    
					  ]
					};

					FusionCharts.ready(function() {
					  var myChart = new FusionCharts({
					    type: "scrollline2d",
					    renderAt: "maxLineChartContainer",
					    width: "100%",
					    height: "600",
					    dataFormat: "json",
					    dataSource
					  }).render();
					});


			}// end line chart for max when max and min are BOTH checked	
	
	//--------->line chart for min when max and min are BOTH checked	
		function fetchMinLineChartCount(minStackedandLineCount) {
			
			var startDate=[],MinSimSales = [];
			let data = [];

			var minLabel;
			
			if( minStackedandLineCount !=null && minStackedandLineCount.length !=0 ){	
					
					minLabel = "Minimum Agent Sales";
					startDate = minStackedandLineCount[0];
					MinSimSales=minStackedandLineCount[1];
					data= [startDate,MinSimSales];
			}
			else{
			
				minLabel = "No SIM Card Sales";
				startDate = [];
				MinSimSales = [];		
				MinSimSales =0;
			    startDate.push(($('#startdate1').val().split(" "))[0].toString()); 
			    data= [startDate,MinSimSales];
				 
			}
				
			const dataSource = {
					  chart: {
					    "caption": $("#period").children("option:selected").val()+" Minimum Agent SIM Sales Count Lines ",
					    "yaxisname": "SIM Count","showhovereffect": "1","drawcrossline": "1","captionFontSize": "20",
						"xAxisNameFontSize":"15px","yAxisNameFontSize":"22px","yAxisLineThickness":"1","yAxisLineColor":"#999999","showAlternateHGridColor":"1",
						"showAlternateVGridColor":"1","yAxisValueFont": "verdana,sans","yAxisValueFontSize": "10px","yAxisValueFontColor": "#ff0000","yAxisValueFontBold": "1",
						"yAxisValueFontItalic": "1","labelFontSize":"10px","labelAlpha":"90","labelFontBold":"0","borderThickness": "1",
						"plotFillAlpha": "90","ValueFontColor":"#030303","bgColor": "EEEEEE,CCCCCC",
			  	        "bgratio": "60,40",
			  	        "bgAlpha": "70,80",
			  	        "bgAngle": "180",										    
					    "theme": "fusion"
					  },
					  categories: [
					    {
					      category: data[0]
					    }
					  ],
					  dataset: [
					    {
					      seriesname: minLabel,
					      data: data[1],
					      "color":"#FF0000"

					    }
					    
					  ]
					};

					FusionCharts.ready(function() {
					  var myChart = new FusionCharts({
					    type: "scrollline2d",
					    renderAt: "minLineChartContainer",
					    width: "100%",
					    height: "600",
					    dataFormat: "json",
					    dataSource
					  }).render();
					});


			}// end line chart for min when max and min are BOTH checked	
	
				