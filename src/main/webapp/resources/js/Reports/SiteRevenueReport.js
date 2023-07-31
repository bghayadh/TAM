
// This file was audited by Yara
function changeFunc() {

var selectBoxMSChart = document.getElementById("changeOptionsMSChart");
var selectedValueMSChart = selectBoxMSChart.options[selectBoxMSChart.selectedIndex].value;
var selectBoxStackChart = document.getElementById("changeOptionsStackChart");
var selectedValueStackChart = selectBoxStackChart.options[selectBoxStackChart.selectedIndex].value;
var selectBoxLineChart = document.getElementById("changeOptionsLineChart");
var selectedValueLineChart = selectBoxLineChart.options[selectBoxLineChart.selectedIndex].value;
var selectBoxPieChart = document.getElementById("changeOptionsPieChart");
var selectedValuePieChart = selectBoxPieChart.options[selectBoxPieChart.selectedIndex].value;
var selectBoxStackChartMax = document.getElementById("changeOptionsStackChartMax");
var selectedValueStackChartMax = selectBoxStackChartMax.options[selectBoxStackChartMax.selectedIndex].value;
var selectBoxStackChartMin = document.getElementById("changeOptionsStackChartMin");
var selectedValueStackChartMin = selectBoxStackChart.options[selectBoxStackChartMin.selectedIndex].value;
var selectBoxLineChartMax = document.getElementById("changeOptionsLineChartMax");
var selectedValueLineChartMax = selectBoxLineChartMax.options[selectBoxLineChartMax.selectedIndex].value;
var selectBoxLineChartMin = document.getElementById("changeOptionsLineChartMin");
var selectedValueLineChartMin = selectBoxLineChartMin.options[selectBoxLineChartMin.selectedIndex].value;
$("#changeOptionsMSChart").change(function(){
	if (selectedValueMSChart =="isBetween"){
		$("#filterDivMSChart").show();
		$('#txtBtnMSChart').show();
		$('#minValMSFltr').show();
		$('#maxValMSFltr').show();
		$('#revenueOptionsMSChart').show();

		}
		 else if (selectedValueMSChart =="null")
		{
	 			$("#revenueOptionsMSChart").val('voice_revenue').trigger('change');
	 			$('#minValMSFltr').val('');
				$('#maxValMSFltr').val('');
				$('#txtBtnMSChart').hide();
				$('#minValMSFltr').hide();
				$('#maxValMSFltr').hide();
				$('#revenueOptionsMSChart').hide();
				
		} 
});


$("#changeOptionsStackChart").change(function(){

if (selectedValueStackChart =="isBetween"){
	$("#filterDivStackChart").show();
	$('#txtBtnStackChart').show();
	$('#minValStackedFltr').show();
	$('#maxValStackedFltr').show();
	$('#revenueOptionsStackChart').show();
	}
	 else if (selectedValueStackChart =="null")
	{
		 $("#revenueOptionsStackChart").val('voice_revenue').trigger('change');
			$('#minValStackedFltr').val('');
			$('#maxValStackedFltr').val('');
			$('#txtBtnStackChart').hide();
			$('#minValStackedFltr').hide();
			$('#maxValStackedFltr').hide();
			$('#revenueOptionsStackChart').hide();
			
	} 
});
$("#changeOptionsLineChart").change(function(){

	if (selectedValueLineChart =="isBetween"){
		$("#filterDivLineChart").show();
		$('#txtBtnLineChart').show();
		$('#minValLineFltr').show();
		$('#maxValLineFltr').show();
		$('#revenueOptionsLineChart').show();

		}
		 else if (selectedValueLineChart =="null")
		{
			 $("#revenueOptionsLineChart").val('voice_revenue').trigger('change');
				$('#minValLineFltr').val('');
				$('#maxValLineFltr').val('');
				$('#txtBtnLineChart').hide();
				$('#minValLineFltr').hide();
				$('#maxValLineFltr').hide();
				$('#revenueOptionsLineChart').hide();
				
		} 
	});
$("#changeOptionsPieChart").change(function(){

	if (selectedValuePieChart =="isBetween"){
		$("#filterDivPieChart").show();
		$('#txtBtnPieChart').show();
		$('#minValPiesFltr').show();
		$('#maxValPiesFltr').show();
		$('#revenueOptionsPieChart').show();

		}
		 else if (selectedValuePieChart =="null")
		{
			 $("#revenueOptionsPieChart").val('voice_revenue').trigger('change');
				$('#minValPiesFltr').val('');
				$('#maxValPiesFltr').val('');
				$('#txtBtnPieChart').hide();
				$('#minValPiesFltr').hide();
				$('#maxValPiesFltr').hide();
				$('#revenueOptionsPieChart').hide();
				
		} 
	});
//
$("#changeOptionsStackChartMax").change(function(){

	if (selectedValueStackChartMax =="isBetween"){
		$("#filterDivStackChartMax").show();
		$('#txtBtnStackChartMax').show();
		$('#minValPiesFltrMax').show();
		$('#maxValPiesFltrMax').show();
		$('#revenueOptionsStackChartMax').show();

		}
		 else if (selectedValueStackChartMax =="null")
		{
			 $("#revenueOptionsStackChartMax").val('voice_revenue').trigger('change');
				$('#minValPiesFltrMax').val('');
				$('#maxValPiesFltrMax').val('');
				$('#txtBtnStackChartMax').hide();
				$('#minValPiesFltrMax').hide();
				$('#maxValPiesFltrMax').hide();
				$('#revenueOptionsStackChartMax').hide();
				
		} 
	});
$("#changeOptionsStackChartMin").change(function(){

	if (selectedValueStackChartMin=="isBetween"){
		$("#filterDivStackChartMin").show();
		$('#txtBtnStackChartMin').show();
		$('#minValPiesFltrMin').show();
		$('#maxValPiesFltrMin').show();
		$('#revenueOptionsStackChartMin').show();

		}
		 else if (selectedValueStackChartMin=="null")
		{
			 $("#revenueOptionsStackChartMin").val('voice_revenue').trigger('change');
				$('#minValPiesFltrMin').val('');
				$('#maxValPiesFltrMin').val('');
				$('#txtBtnStackChartMin').hide();
				$('#minValPiesFltrMin').hide();
				$('#maxValPiesFltrMin').hide();
				$('#revenueOptionsStackChartMin').hide();
				
		} 
	});
$("#changeOptionsLineChartMax").change(function(){

	if (selectedValueLineChartMax =="isBetween"){
		$("#filterDivLineChartMax").show();
		$('#txtBtnLineChartMax').show();
		$('#minValPiesFltrMax').show();
		$('#maxValPiesFltrMax').show();
		$('#revenueOptionsLineChartMax').show();

		}
		 else if (selectedValueLineChartMax =="null")
		{
			 $("#revenueOptionsLineChartMax").val('voice_revenue').trigger('change');
				$('#minValPiesFltrMax').val('');
				$('#maxValPiesFltrMax').val('');
				$('#txtBtnLineChartMax').hide();
				$('#minValPiesFltrMax').hide();
				$('#maxValPiesFltrMax').hide();
				$('#revenueOptionsLineChartMax').hide();
				
		} 
	});
$("#changeOptionsLineChartMin").change(function(){

	if (selectedValueLineChartMin=="isBetween"){
		$("#filterDivLineChartMin").show();
		$('#txtBtnLineChartMin').show();
		$('#minValPiesFltrMin').show();
		$('#maxValPiesFltrMin').show();
		$('#revenueOptionsLineChartMin').show();

		}
		 else if (selectedValueLineChartMin=="null")
		{
			 $("#revenueOptionsLineChartMin").val('voice_revenue').trigger('change');
				$('#minValPiesFltrMin').val('');
				$('#maxValPiesFltrMin').val('');
				$('#txtBtnLineChartMin').hide();
				$('#minValPiesFltrMin').hide();
				$('#maxValPiesFltrMin').hide();
				$('#revenueOptionsLineChartMin').hide();
				
		} 
	});
}



function changeRevenueFunc() {

	$("#revenueOptionsMSChart").change(function(){
		$('#minValMSFltr').val('');
		$('#maxValMSFltr').val('');
		});

	$("#revenueOptionsStackChart").change(function(){
		$('#minValStackedFltr').val('');
		$('#maxValStackedFltr').val('');
		});
	$("#revenueOptionsLineChart").change(function(){
		$('#minValLineFltr').val('');
		$('#maxValLineFltr').val('');
		});
	$("#revenueOptionsPieChart").change(function(){
		$('#minValPiesFltr').val('');
		$('#maxValPiesFltr').val('');
		});
	$("#changeOptionsStackChartMax").change(function(){
		$("#minValStackedFltrMax").val('');
		$("#maxValStackedFltrMax").val('');
		});
	$("#changeOptionsStackChartMin").change(function(){
		$("#minValStackedFltrMin").val('');
		$("#maxValStackedFltrMin").val('');
		});
	$("#changeOptionsLineChartMax").change(function(){
		$("#minValLineFltrMax").val('');
		$("#maxValLineFltrMax").val('');
		});
	$("#changeOptionsLineChartMin").change(function(){
		$("#minValLineFltrMin").val('');
		$("#maxValLineFltrMin").val('');
		});

	}
	
	

				  
				  
				  
function renderSiteCharts(chartData){
	
	PieTech(chartData);
	PieRevPerTech(chartData);
}
 
                    	
                    	
                    							//*********************** Multi-series Column 2D

						 function SiteRevenue(msColumnChartObj){
							//console.log("msColumnChartObj. "+msColumnChartObj );
							var siteNames=[];
							var voiceRevenue=[];
							var smsRevenue=[];
							var dataRevenue=[];
							var vasRevenue=[];
							var totalRevenue=[];
							var objRevenue={};
							 let data =[];
							 label1 = "";label2="";label3="";label4="";label5="";
							 
							 if(msColumnChartObj !=null && msColumnChartObj.length !=0){
							 label1 = "Voice";label2="SMS";label3="Data";label4="VAS";label5="Total";
							 siteNames=msColumnChartObj[0];
							 voiceRevenue=msColumnChartObj[1];
							 smsRevenue=msColumnChartObj[2];
							 dataRevenue=msColumnChartObj[3];
							 vasRevenue=msColumnChartObj[4];
							 totalRevenue=msColumnChartObj[5];
							 
							/* siteNames = siteNames;//jQuery.parseJSON(getElement(siteNames));
							 voiceRevenue = voiceRevenue;//jQuery.parseJSON(getData(voiceRevenue));
							 smsRevenue = smsRevenue;//jQuery.parseJSON(getData(smsRevenue));
							 dataRevenue = dataRevenue;//jQuery.parseJSON(getData(dataRevenue));
							 vasRevenue = vasRevenue;//jQuery.parseJSON(getData(vasRevenue));
							 */
							 }
							 else{
							 		objRevenue.value="0";
									siteNames.push('No Data');
									voiceRevenue.push(objRevenue);
									smsRevenue.push(objRevenue);
									dataRevenue.push(objRevenue);
									vasRevenue.push(objRevenue);
									totalRevenue.push(objRevenue);
									objRevenue={};
									label1 = "No Voice";label2="No SMS";label3="No Data";label4="No VAS";label5="Zero Total";
									}
							 
								
								
						     FusionCharts.ready(function() {
						  	   var SiteRevenueParam = new FusionCharts({
						  	     type: 'scrollColumn2d',
						  	     renderAt: 'chartContainer3',
						  	     width: '100%',
						  	     height: '600',
						  	     dataFormat: 'json',

						  	     dataSource: {
					  	       "chart": {
					  	         "theme": "fusion",
					  	         "caption": "Site Revenue",
					  	       "captionFontSize": "20",
					  	         "xAxisname": "Sites",
					  	         "xAxisNameFontSize":"22px",
					  	         "yAxisNameFontSize":"22px",
					  	         "yAxisName": "Value (in USD)",
					  	       "bgColor": "EEEEEE,CCCCCC",
					  	        "bgratio": "60,40",
					  	        "bgAlpha": "70,80",
					  	        "bgAngle": "180",
						  	     "yAxisLineThickness":"1",
						  	     "yAxisLineColor":"#999999",
						  	     "showAlternateHGridColor":"1",
						  	   "showAlternateVGridColor":"1",
								"yAxisValueDecimals": "3",
						        "forceYAxisValueDecimals": "1",
						        "decimals":"3",
							  	"yAxisValueFont": "verdana,sans",
						  	     "yAxisValueFontSize": "10px",
						  	     "yAxisValueFontColor": "#ff0000",
						  	     "yAxisValueFontBold": "1",
						  	     "yAxisValueFontItalic": "1",
						  	    
					  	         "labelFontSize":"10px",
					  	         "labelAlpha":"90",
					  	         "labelFontBold":"0",
					  	         "numberPrefix": "$",
					  	         "borderThickness": "1",
					  	         "plotFillAlpha": "90",
					  	         "ValueFontColor":"#030303",
					  	         
						  	   	"numVisiblePlot": "24",
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
						  	    	   
						  	    	   "category": (siteNames=="No Data")? " " : siteNames
							  	        
						  	       }],
						  	       "dataset": [{
							    	         "seriesname": label1,
							    	         "data": voiceRevenue,
							    	         "color":"#FFFF00"
							    	       },
							    	       {
							    	         "seriesname": label2,
								    	     "data": smsRevenue,
								    	     "color":"#0000FF"
								    	     },
							    	       {
							    	         "seriesname": label3,
								    	     "data": dataRevenue,
								    	     "color":"#FF0000"
								    	     },
								    	    {
								    	     "seriesname": label4,
									    	  "data": vasRevenue
									    	 },
								    	     {
									    	  "seriesname": label5,
										      "data": totalRevenue
										    	 }
								    	     
							    	       ]

						  	     }
						  	   });

						  	 SiteRevenueParam.render();
						  	 });

							}

																					  

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
							function getData(arr){
							var strObj = {};
			  
							$.each(arr, function(key, value){
								if (key == 0 ) { if(arr.length>1) strObj = '[{ "value" :'+' "'+value.toString()+'" '+'},';
													else strObj = '[{ "value" :'+' "'+value.toString()+'" '+'}]';}
								else if (key < (arr.length - 1) && key > 0)	strObj +='{ "value" :'+' "'+value.toString()+'" '+'},'; 
								else strObj += '{ "value" :'+' "'+value.toString()+'" '+'}]'; 
								         
			  	 
						         });
					       	return strObj;
									  
					 
							}

						//*************************** Multi-variate chart compaire ***************************
						
						var obj1 = {};
						var obj2 = {};
						var obj3 = {};
						var obj4 = {};
						
						
						 function PieRevenue(pieRevenue){
						 
							var label1,label2,label3,label4;
							var voiceRevenue= [];
							var smsRevenue =[];
							var dataRevneue=[];
							var vasRevenue=[]; 
							var totRev=[];
							
							if(pieRevenue != "" && pieRevenue[0][4] !=0 ){
							//	console.log("here");
								//if(pieRevenue[0][0] !=null && pieRevenue[0][1] !=null && pieRevenue[0][2] !=null && pieRevenue[0][3] !=null && pieRevenue[0][4] !=0){
						 		     voiceRevenue=pieRevenue[0][0];
									 smsRevenue = pieRevenue[0][1];
									 dataRevneue=pieRevenue[0][2];
									 vasRevenue=pieRevenue[0][3]; 
									 totRev=pieRevenue[0][4];

									label1 = "Voice";label2="SMS";label3="Data";label4="VAS";
									
									
									//totRev = parseFloat(voiceRevenue+smsRevenue+dataRevneue+vasRevenue);
									voiceRevenue=(voiceRevenue/totRev)*100;
									smsRevenue=(smsRevenue/totRev)*100;
									dataRevneue=(dataRevneue/totRev)*100;
									vasRevenue=(vasRevenue/totRev)*100;
									totRev = totRev.toFixed(2);
						 		//}
							}
							else{
								label1 = "No Data";label2="";label3="";label4="";
								
								voiceRevenue =100;
								smsRevenue=0;
								dataRevneue=0;
								vasRevenue=0;
								totRev=0;
									
							}
						
						 



							const dataSource = {
									
							  chart: {
							    caption: "Revenue split based on Voice, SMS, Data and VAS",
							    subcaption: "For a net-worth of "+totRev+"$",
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
							};
							
							FusionCharts.ready(function() {
							  var myChart = new FusionCharts({
							    type: "pie2d",
							    renderAt: "chartContainer2",
							    width: "100%",
							    height: "400",
							    dataFormat: "json",
							    dataSource
							  });
							  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
							  myChart.setJSONData(dataSource);		            
							  myChart.render();
							});
															  

							}

				 function PieTech(chartData){
					       // console.log("chartData."+chartData );
							var label1,label2,label3;
							var site2g=0,site2g3g=0,site2g3g4g=0,totTech=0;
									
									if(	chartData.length!=0 && chartData !=""){
									/*if(chartData[7] == 0){
									label1 = "NO Data";label2="";label3="";
									
									site2g =100;
									site2g3g=0;
									site2g3g4g=0;
									totTech=0;
									}
									else{
									
								    label1 = "2G";label2="2G,3G";label3="2G,3G and 4G";
									site2g =chartData[4];
									site2g3g=chartData[5];
									site2g3g4g=chartData[6];
									totTech=chartData[7];
									}*/
									//}
										    label1 = "2G";label2="2G_3G";label3="2G_3G_4G";
											site2g =chartData[4];
											site2g3g=chartData[5];
											site2g3g4g=chartData[6];
											//console.log("total."+chartData[7] );
											totTech=chartData[7];
												
									}
										else{
											label1 = "No Data";label2="";label3="";
											
											site2g =100;
											site2g3g=0;
											site2g3g4g=0;
											totTech=0;

										
									 
										}
														
					 		
		

							const dataSource = {
									
							  chart: {
								    caption: "Technology split based on 2G, 3G and 4G",
								    subcaption: "For a whole-count of "+totTech+" sites",
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
							      value: site2g,
							      "color":"#FFFF00"
							      
							    },
							    {
							      label: label2,
							      value: site2g3g,
							      "color":"#0000FF"
							    },
							    {
							      label: label3,
							      value: site2g3g4g,
							      "color":"#FF0000"
							    }
							  ]
							};
							
							FusionCharts.ready(function() {
							  var myChart = new FusionCharts({
								    type: "pie2d",
								    renderAt: "chartPie2",
								    width: "100%",
								    height: "400",
								    dataFormat: "json",
								    dataSource
							  });
							  myChart.configure("ChartNoDataText","No data to load. Please check the data source.");
							  myChart.setJSONData(dataSource);		            
							  myChart.render();
							});
															  

							}
						// *************************** bar chart compaire ***************************
						function fetchRevenuePerTech(StackedandLineRevenue, startDate, endDate) {
							//console.log("StackedandLineRevenue."+StackedandLineRevenue );
							var date=[];
							var revObj ={};
							
							var	voiceRevenue = [];
							var smsRevenue = [];
							var dataRevneue = [];
							var vasRevenue = [];
							var totalRevenue = [];
							let data = [];
							var label1,label2,label3,label4,label5;


							
							 if(StackedandLineRevenue !=null && StackedandLineRevenue.length !=0){
									
								 //data = getRevenueArray(date, voiceRevenue, smsRevenue, dataRevneue, vasRevenue);
							     label1 = "Voice";label2="SMS";label3="Data";label4="VAS";label5="Total";
							     date=StackedandLineRevenue[0];
							     voiceRevenue = StackedandLineRevenue[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
							     smsRevenue = StackedandLineRevenue[2];//jQuery.parseJSON(getData(StackedandLineRevenue[2]));
							     dataRevneue = StackedandLineRevenue[3];//jQuery.parseJSON(getData(StackedandLineRevenue[3]));
							     vasRevenue = StackedandLineRevenue[4];//jQuery.parseJSON(getData(StackedandLineRevenue[4]));
							     totalRevenue = StackedandLineRevenue[5];
								data= [date,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
							}else{
								//console.log(" No revenue for stacked");
								date.push("No data");
								voiceRevenue.push("0");
								smsRevenue.push("0");
								dataRevneue.push("0");
								vasRevenue.push("0");
								totalRevenue.push("0");
								label1 = "No data";label2="";label3="";label4="";label5="";
								data= [date,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
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
					        
					            "caption": "Voice, SMS, Data and VAS Revenue w.r.t the date {br}{br}",
					            "subcaption": ""+startDate+" - "+endDate+"",
					            "subcaptionFontSize": "14",
					            "subcaptionFontColor": "#808080",
								"captionFontSize": "20",
								"numbersuffix": "$",
					            
					    	    "xAxisNameFontSize":"22px",
					    	    "yAxisNameFontSize":"22px",
					            "plottooltext": "<b>$seriesName</b><hr>$label: <b>$dataValue</b>",
					            "plottooltextFontSize":"16px",
					            "xaxisname": "Dates",
					            "yaxisname": "Revenue (in USD)",
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
								"yAxisValueDecimals": "3",
						        "forceYAxisValueDecimals": "1",
						        "decimals":"3",
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
					            "category": (date =="No data")? date : data[0]//jQuery.parseJSON(getElement(data[0]))
					        }],
					    
					                "dataset": [
						                {
					                        "seriesname": label1,
					                        "data": data[1],
					                        "color":"#FFFF00"

						                },
					                    {
					                        "seriesname": label2,
					                        "data":data[2],
					                        "color":"#0000FF"
							                       
					                    },
					                    {
					                        "seriesname": label3,
					                        "data":data[3],
					                        "color":"#FF0000"
							                      
					                    },
					                    {
					                        "seriesname": label4,
					                        "data":data[4]
					                    },
					                    {
					                        "seriesname": label5,
					                        "data":data[5],
					                       // "color":"#FF0000"
							                      
					                    }
					                ]
					            
					              
					          
					    }
					});
								chartObj.render();
						       });
							}
						 function fetchRevenuePerTechMaxMin(StackedandLineRevenue, startDate, endDate,option) {
							//console.log("StackedandLineRevenue."+StackedandLineRevenue );
							var date=[];
							var revObj ={};
							
							var	voiceRevenue = [];
							var smsRevenue = [];
							var dataRevneue = [];
							var vasRevenue = [];
							var totalRevenue = [];
							let data = [];
							var label1,label2,label3,label4,label5;


							
							 if(StackedandLineRevenue !=null && StackedandLineRevenue.length !=0){
									
								 //data = getRevenueArray(date, voiceRevenue, smsRevenue, dataRevneue, vasRevenue);
									label1 = "Voice";label2="SMS";label3="Data";label4="VAS";label5="Total";
									//console.log("the stacked "+StackedandLineRevenue);
									/*for(i = 0;i<StackedandLineRevenue.length;i++){
									    date=StackedandLineRevenue[i][0];
							            voiceRevenue = StackedandLineRevenue[i][1];//jQuery.parseJSON(getData(StackedandLineRevenue[i][1]));
							            smsRevenue = StackedandLineRevenue[i][2];//jQuery.parseJSON(getData(StackedandLineRevenue[i][2]));
							            dataRevneue = StackedandLineRevenue[i][3];// jQuery.parseJSON(getData(StackedandLineRevenue[i][3]));
							            vasRevenue = StackedandLineRevenue[i][4];//jQuery.parseJSON(getData(StackedandLineRevenue[i][4]));
							            totalRevenue=StackedandLineRevenue[i][5];
							            data= [date,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
							 
							       }*/
									date=StackedandLineRevenue[0];
						            voiceRevenue = StackedandLineRevenue[1];//jQuery.parseJSON(getData(StackedandLineRevenue[i][1]));
						            smsRevenue = StackedandLineRevenue[2];//jQuery.parseJSON(getData(StackedandLineRevenue[i][2]));
						            dataRevneue = StackedandLineRevenue[3];// jQuery.parseJSON(getData(StackedandLineRevenue[i][3]));
						            vasRevenue = StackedandLineRevenue[4];//jQuery.parseJSON(getData(StackedandLineRevenue[i][4]));
						            totalRevenue=StackedandLineRevenue[5];
						            data= [date,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
							}else{
								//console.log(" No revenue for stacked");
								date.push("No data");
								voiceRevenue.push("0");
								smsRevenue.push("0");
								dataRevneue.push("0");
								vasRevenue.push("0");
								totalRevenue.push("0");
								label1 = "No data";label2="";label3="";label4="";label5="";
								data= [date,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
								} 

							FusionCharts.ready(function(){
								var chartObj = new FusionCharts({
					    type: 'scrollstackedcolumn2d',
					    renderAt: option+"ChartContainerStacked",
					    width: '100%',
					    height: '650',
					    dataFormat: 'json',
					    dataSource: {
					        "chart": {
					            "theme": "fusion",
					        
					            "caption": option+" Voice, SMS, Data and VAS Revenue w.r.t the date {br}{br}",
					            "subcaption": ""+startDate+" - "+endDate+"",
					            "subcaptionFontSize": "14",
					            "subcaptionFontColor": "#808080",
								"captionFontSize": "20",
								"numbersuffix": "$",
					            
					    	    "xAxisNameFontSize":"22px",
					    	    "yAxisNameFontSize":"22px",
					            "plottooltext": "<b>$seriesName</b><hr>$label: <b>$dataValue</b>",
					            "plottooltextFontSize":"16px",
					            "xaxisname": "Dates",
					            "yaxisname": "Revenue (in USD)",
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
								"yAxisValueDecimals": "3",
						        "forceYAxisValueDecimals": "1",
						        "decimals":"3",
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
					            "category": (date =="No data")? date : data[0]//jQuery.parseJSON(getElement(data[0]))
					        }],
					    
					                "dataset": [
						                {
					                        "seriesname": label1,
					                        "data": data[1],
					                        "color":"#FFFF00"

						                },
					                    {
					                        "seriesname": label2,
					                        "data":data[2],
					                        "color":"#0000FF"
							                       
					                    },
					                    {
					                        "seriesname": label3,
					                        "data":data[3],
					                        "color":"#FF0000"
							                      
					                    },
					                    {
					                        "seriesname": label4,
					                        "data":data[4]
					                    },
					                    {
					                        "seriesname": label5,
					                        "data":data[5],
					                        //"color":"#FF0000"
							                      
					                    }
					                ]
					            
					              
					          
					    }
					});
								chartObj.render();
						       });
							}

						 function fetchLineChartRevenue(StackedandLineRevenue) {
							//console.log("StackedandLineRevenue."+StackedandLineRevenue );
							var startDate=[],voiceRevenue = [],smsRevenue = [],dataRevneue = [],vasRevenue = [],totalRevenue = [];
							var objRevenue={};

							let data = [];

							var label1,label2,label3,label4,label5;
							
							if(StackedandLineRevenue !="null" && StackedandLineRevenue.length !=0){
					 			//console.log("passssss");

							
									label1 = "Voice";label2="SMS";label3="Data";label4="VAS";label5="Total";
									startDate=StackedandLineRevenue[0]
									voiceRevenue = StackedandLineRevenue[1];//jQuery.parseJSON(getData(StackedandLineRevenue[1]));
									smsRevenue = StackedandLineRevenue[2];//jQuery.parseJSON(getData(StackedandLineRevenue[2]));
									dataRevneue = StackedandLineRevenue[3];//jQuery.parseJSON(getData(StackedandLineRevenue[3]));
									vasRevenue = StackedandLineRevenue[4];//jQuery.parseJSON(getData(StackedandLineRevenue[4]));
									totalRevenue = StackedandLineRevenue[5];
							 data= [startDate,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];

							}


								else{
									label1 = "No Voice";label2="No SMS";label3="No Data";label4="No VAS";label5="Zero Total";
									
								//console.log(" No revenue to fitch along a period");
								startDate = [];
								voiceRevenue = [];
								smsRevenue = [];
								dataRevneue=[];
								vasRevenue=[];
								totalRevenue = [];
							    startDate.push(($('#startdate').val().split(" "))[0].toString()); 
							    data= [startDate,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
										 
								}
								
							const dataSource = {
									  chart: {
									    "caption": $("#period").children("option:selected").val()+" revenue lines for {br} Voice, SMS, Data and VAS",
									    "yaxisname": "Revenues","showhovereffect": "1","numbersuffix": "$","drawcrossline": "1","captionFontSize": "20",
										"xAxisNameFontSize":"15px","yAxisNameFontSize":"22px","yAxisLineThickness":"1","yAxisLineColor":"#999999","showAlternateHGridColor":"1",
										"showAlternateVGridColor":"1","yAxisValueFont": "verdana,sans","yAxisValueFontSize": "10px","yAxisValueFontColor": "#ff0000","yAxisValueFontBold": "1",
										"yAxisValueFontItalic": "1","labelFontSize":"10px","labelAlpha":"90","labelFontBold":"0","borderThickness": "1","yAxisValueDecimals": "3",
						        		"forceYAxisValueDecimals": "1",
						        		"decimals":"3",
										"plotFillAlpha": "90","ValueFontColor":"#030303","bgColor": "EEEEEE,CCCCCC",
							  	        "bgratio": "60,40",
							  	        "bgAlpha": "70,80",
							  	        "bgAngle": "180",										    
									    "plottooltext": "<b>$dataValue</b> of revenue were on $seriesName",
									    "theme": "fusion"
									  },
									  categories: [
									    {
									      category: data[0]//jQuery.parseJSON(getElement(data[0]))

									    }
									  ],
									  dataset: [
									    {
									      seriesname: label1,
									      data: data[1],
									      "color":"#FFFF00"

									    },
									    {
									      seriesname: label2,
									      data: data[2],
									    "color":"#0000FF"
									    },
									    {
									      seriesname: label3,
									      data: data[3],
									    "color":"#FF0000"
									    },
									    {
									      seriesname: label4,
									      data: data[4] 

									    },
									    {
										   seriesname: label5,
										   data: data[5],
										    //"color":"#FF0000"
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

							}
							function fetchLineChartRevenueMaxMin(StackedandLineRevenue,option) {
								//console.log("StackedandLineRevenue.2 "+StackedandLineRevenue );
							    var startDate=[],voiceRevenue = [],smsRevenue = [],dataRevneue = [],vasRevenue = [],totalRevenue = [];
							    var objRevenue={};

							    let data = [];

							    var label1,label2,label3,label4,label5;
							
							   if(StackedandLineRevenue !="null" && StackedandLineRevenue.length !=0){
									label1 = "Voice";label2="SMS";label3="Data";label4="VAS";label5="Total";
									/*for(i=0;i<StackedandLineRevenue.length;i++){
									startDate = StackedandLineRevenue[i][0];
									voiceRevenue = StackedandLineRevenue[i][1];//jQuery.parseJSON(getData(StackedandLineRevenue[i][1]));
									smsRevenue = StackedandLineRevenue[i][2];//jQuery.parseJSON(getData(StackedandLineRevenue[i][2]));
									dataRevneue = StackedandLineRevenue[i][3];//jQuery.parseJSON(getData(StackedandLineRevenue[i][3]));
									vasRevenue = StackedandLineRevenue[i][4];//jQuery.parseJSON(getData(StackedandLineRevenue[i][4]));
									totalRevenue=StackedandLineRevenue[i][5];
									data= [startDate,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
									
									}*/
									
									startDate = StackedandLineRevenue[0];
									voiceRevenue = StackedandLineRevenue[1];//jQuery.parseJSON(getData(StackedandLineRevenue[i][1]));
									smsRevenue = StackedandLineRevenue[2];//jQuery.parseJSON(getData(StackedandLineRevenue[i][2]));
									dataRevneue = StackedandLineRevenue[3];//jQuery.parseJSON(getData(StackedandLineRevenue[i][3]));
									vasRevenue = StackedandLineRevenue[4];//jQuery.parseJSON(getData(StackedandLineRevenue[i][4]));
									totalRevenue=StackedandLineRevenue[5];
									data= [startDate,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
									

							}


								else{
									label1 = "No Voice";label2="No SMS";label3="No Data";label4="No VAS";label5="Zero Total";
									
								//console.log(" No revenue to fitch along a period");
								startDate = [];
								voiceRevenue = [];
								smsRevenue = [];
								dataRevneue=[];
								vasRevenue=[];
								totalRevenue =[];

										 startDate.push(($('#startdate').val().split(" "))[0].toString()); 
								  		 data= [startDate,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
										 
								}
								
							const dataSource = {
									  chart: {
									    "caption": $("#period").children("option:selected").val()+" "+option+" revenue lines for {br} Voice, SMS, Data and VAS",
									    "yaxisname": "Revenues","showhovereffect": "1","numbersuffix": "$","drawcrossline": "1","captionFontSize": "20",
										"xAxisNameFontSize":"15px","yAxisNameFontSize":"22px","yAxisLineThickness":"1","yAxisLineColor":"#999999","showAlternateHGridColor":"1",
										"showAlternateVGridColor":"1","yAxisValueFont": "verdana,sans","yAxisValueFontSize": "10px","yAxisValueFontColor": "#ff0000","yAxisValueFontBold": "1",
										"yAxisValueFontItalic": "1","labelFontSize":"10px","labelAlpha":"90","labelFontBold":"0","borderThickness": "1","yAxisValueDecimals": "3",
								        "forceYAxisValueDecimals": "1",
								        "decimals":"3",
										"plotFillAlpha": "90","ValueFontColor":"#030303","bgColor": "EEEEEE,CCCCCC",
							  	        "bgratio": "60,40",
							  	        "bgAlpha": "70,80",
							  	        "bgAngle": "180",										    
									    "plottooltext": "<b>$dataValue</b> of revenue were on $seriesName",
									    "theme": "fusion"
									  },
									  categories: [
									    {
									      category: data[0]//jQuery.parseJSON(getElement(data[0]))

									    }
									  ],
									  dataset: [
									    {
									      seriesname: label1,
									      data: data[1],
									      "color":"#FFFF00"

									    },
									    {
									      seriesname: label2,
									      data: data[2],
									    "color":"#0000FF"
									    },
									    {
									      seriesname: label3,
									      data: data[3],
									    "color":"#FF0000"
									    },
									    {
									      seriesname: label4,
									      data: data[4] 

									    },
									    {
										   seriesname: label5,
										   data: data[5],
										   //"color":"#FF0000"
										  }
									  ]
									};

									FusionCharts.ready(function() {
									  var myChart = new FusionCharts({
									    type: "scrollline2d",
									    //renderAt: "maxlineChartContainer",
									    renderAt: option+"LineChartContainer",
									    width: "100%",
									    height: "600",
									    dataFormat: "json",
									    dataSource
									  }).render();
									});


							}
				 		 function PieRevPerTech(chartData){
				 			//console.log("chartData."+chartData );
							
							var label1,label2,label3;
							var revSite2g=0,revSite2g3g=0,revSite2g3g4g=0,totRevTech=0;
									if(	chartData.length !=0 && chartData != ""){
									/*if(chartData[3] == 0){
									label1 = "NO Data";label2="";label3="";
									
									revSite2g =100;
									revSite2g3g=0;
									revSite2g3g4g=0;
									totRevTech=0;
									}
									else{
									
									label1 = "Rev per 2G";label2="Rev per 2G,3G";label3="Rev per 2G,3G and 4G";
									revSite2g =chartData[0];
									revSite2g3g=chartData[1];
									revSite2g3g4g=chartData[2];
									totRevTech=chartData[3];
									}*/
									//}

										label1 = "2G";label2="2G_3G";label3="2G_3G_4G";
										revSite2g =chartData[0];
										revSite2g3g=chartData[1];
										revSite2g3g4g=chartData[2];
										totRevTech=chartData[3];
									}
									else{
										
	                                    label1 = "No Data";label2="";label3="";
										
										revSite2g =100;
										revSite2g3g=0;
										revSite2g3g4g=0;
										totRevTech=0;
									
									}
					 	/*	if(listSites !=null && listSites.length !=0){

								label1 = "Rev per 2G";label2="Rev per 2G,3G";label3="Rev per 2G,3G and 4G";
								
								for(var i=0;i<listSites.length;i++){
									if(listSites[i][3] == "1" && listSites[i][4] == "0" && listSites[i][5] == "0"){
										revSite2g+=parseFloat(listSites[i][7]);// parseFloat(listSites[i][3])
								}else{
									revSite2g+= 0;
									}
								if(listSites[i][3] == "1" && listSites[i][4] == "1" && listSites[i][5] == "0"){
									revSite2g3g+= parseFloat(listSites[i][7]) ; //parseFloat(listSites[i][4])

								}else{
									revSite2g3g+= 0;
								}
								if(listSites[i][3] == "1" && listSites[i][4] == "1" && listSites[i][5] == "1"){
									
									revSite2g3g4g+= parseFloat(listSites[i][7]);

								}else{
									revSite2g3g4g+= 0;
								}
								
								}
								totRevTech = parseFloat(revSite2g+revSite2g3g+revSite2g3g4g);
								if(totRevTech == 0) {
									revSite2g=100;
									revSite2g3g=0;
									revSite2g3g4g=0;
									label1 = "NO Data";label2="";label3="";
									 }
								else{
								revSite2g=(revSite2g/totRevTech)*100;
								revSite2g3g=(revSite2g3g/totRevTech)*100;
								revSite2g3g4g=(revSite2g3g4g/totRevTech)*100;
								}
								totRevTech=totRevTech.toFixed(2);
								
								}else{
									label1 = "NO Data";label2="";label3="";
									
									revSite2g =100;
									revSite2g3g=0;
									revSite2g3g4g=0;
									totRevTech=0;
								}
						 

								*/

							const dataSource = {
									
							  chart: {
							    caption: "Revenue split based on 2G, 3G and 4G",
							    captionFontSize:"16",
							    subcaption: "For the whole-worth of "+totRevTech+"$",
					            subcaptionFontColor: "#254E7C",   
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
						        "bgAngle": "180",							  },

							  data: [
							    {
							      label: label1,
							      value: revSite2g,
							      "color":"#FFFF00"
							    },
							    {
							      label: label2,
							      value: revSite2g3g,
							      "color":"#0000FF"
							    },
							    {
							      label: label3,
							      value: revSite2g3g4g,
							      "color":"#FF0000"							    }
							  ]
							};
							
							FusionCharts.ready(function() {
							  var myChart = new FusionCharts({
							    type: "pie2d",
							    renderAt: "chartContainerPie3",
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


							
						/*	function getDataArray(siteNames,voiceRevenue,smsRevenue,dataRevenue,vasRevenue){
								var totVoiceRevenue,totSmsRevenue,totDataRevneue,totVasRevenue;
								var sitesArr=[], voiceArr =[], smsArr= [], dataArr=[], vasArr=[];
								var obj = {};
								obj.arrObj = [];
								var obj2={},obj3={},obj4={},obj5={};

								const arr = siteNames; 

								const filtered = arr.filter((v, i) => arr.indexOf(v) !== i);
								const unique = new Set(filtered);
																
								$.each(siteNames, function(key, value){
										totVoiceRevenue=0,totSmsRevenue=0,totDataRevneue=0,totVasRevenue=0;	
										obj2={};
										obj3={};
										obj4={};
										obj5={};	
																		
										 if((jQuery.inArray(siteNames[key], Array.from(unique)) < 0) && (Array.from(unique).length >= 0) )
											{
											totVoiceRevenue=parseFloat(voiceRevenue[key].value);
											totSmsRevenue=parseFloat(smsRevenue[key].value);
											totDataRevneue=parseFloat(dataRevenue[key].value);
											totVasRevenue=parseFloat(vasRevenue[key].value);
											
											obj2.value=parseFloat(totVoiceRevenue).toString();
											obj3.value=parseFloat(totSmsRevenue).toString();
											obj4.value=parseFloat(totDataRevneue).toString();
											obj5.value=parseFloat(totVasRevenue).toString();
											
											
											sitesArr.push(siteNames[key]);
											voiceArr.push(obj2);
											smsArr.push(obj3);
											dataArr.push(obj4);
											vasArr.push(obj5);

											} 
										
									});
								if(Array.from(unique).length > 0){
								for (index in Array.from(unique)){
									totVoiceRevenue=0,totSmsRevenue=0,totDataRevneue=0,totVasRevenue=0;	
									obj2={};
									obj3={};
									obj4={};
									obj5={};
									
									$.each(siteNames, function(key, value){
										if(Array.from(unique)[index] == siteNames[key]){
											
											totVoiceRevenue+=parseFloat(voiceRevenue[key].value);
											totSmsRevenue+=parseFloat(smsRevenue[key].value);
											totDataRevneue+=parseFloat(dataRevenue[key].value);
											totVasRevenue+=parseFloat(vasRevenue[key].value);
											}
										
										});
									obj2.value=parseFloat(totVoiceRevenue).toString();
									obj3.value=parseFloat(totSmsRevenue).toString();
									obj4.value=parseFloat(totDataRevneue).toString();
									obj5.value=parseFloat(totVasRevenue).toString();
									
									
									sitesArr.push(Array.from(unique)[index]);
									voiceArr.push(obj2);
									smsArr.push(obj3);
									dataArr.push(obj4);
									vasArr.push(obj5);

									}
						}
								obj.arrObj.push(sitesArr,voiceArr,smsArr,dataArr,vasArr);
								return obj.arrObj;
								}
							
							// getting Array for the revenue data period .... HAjouz 9.7.2021
							function getRevenueArray(startDate, voiceRevenue, smsRevenue, dataRevneue, vasRevenue){
								var obj = {};
								obj.arrObj = [];
								var splittedDate;
								var lastFormat;
								var obj2={},obj3={},obj4={},obj5={};
								var totVoiceRevenue=0,totSmsRevenue=0,totDataRevneue=0,totVasRevenue=0;
								var voiceArr =[], smsArr= [], dataArr=[], vasArr=[], periodArr=[];
								$.each(startDate, function(key, value){
									splittedDate = startDate[key].split("-");
									lastFormat= parseInt(splittedDate[1])+"/"+parseInt(splittedDate[2])+"/"+splittedDate[0];

									
											if(startDate[key]==startDate[key-1] && startDate[key]==startDate[key+1]){
											totVoiceRevenue+=parseFloat(voiceRevenue[key]);
											totSmsRevenue+=parseFloat(smsRevenue[key]);
											totDataRevneue+=parseFloat(dataRevneue[key]);
											totVasRevenue+=parseFloat(vasRevenue[key]);
											
											}else{
												if(startDate[key]==startDate[key+1] && startDate[key]!=startDate[key-1]){
													totVoiceRevenue=0,totSmsRevenue=0,totDataRevneue=0,totVasRevenue=0;
													totVoiceRevenue=parseFloat(voiceRevenue[key]);
													totSmsRevenue=parseFloat(smsRevenue[key]);
													totDataRevneue=parseFloat(dataRevneue[key]);
													totVasRevenue=parseFloat(vasRevenue[key]);
													//TotalRevenue = totVoiceRevenue+totSmsRevenue+totDataRevneue+totVasRevenue;

													
													 }
												else if(startDate[key]==startDate[key-1] && startDate[key]!=startDate[key+1]){
													//totVoiceRevenue=0,totSmsRevenue=0,totDataRevneue=0,totVasRevenue=0;
													totVoiceRevenue+=parseFloat(voiceRevenue[key]);
													totSmsRevenue+=parseFloat(smsRevenue[key]);
													totDataRevneue+=parseFloat(dataRevneue[key]);
													totVasRevenue+=parseFloat(vasRevenue[key]);
																						 

													obj2.value=parseFloat(totVoiceRevenue).toString();
													obj3.value=parseFloat(totSmsRevenue).toString();
													obj4.value=parseFloat(totDataRevneue).toString();
													obj5.value=parseFloat(totVasRevenue).toString();
													
													
													periodArr.push(lastFormat);
													voiceArr.push(obj2);
													smsArr.push(obj3);
													dataArr.push(obj4);
													vasArr.push(obj5);
													
													 }
													else{
														totVoiceRevenue=parseFloat(voiceRevenue[key]);
														totSmsRevenue=parseFloat(smsRevenue[key]);
														totDataRevneue=parseFloat(dataRevneue[key]);
														totVasRevenue=parseFloat(vasRevenue[key]);
														//TotalRevenue = totVoiceRevenue+totSmsRevenue+totDataRevneue+totVasRevenue;
														
														obj2.value=parseFloat(totVoiceRevenue).toString();
														obj3.value=parseFloat(totSmsRevenue).toString();
														obj4.value=parseFloat(totDataRevneue).toString();
														obj5.value=parseFloat(totVasRevenue).toString();
														
														
														periodArr.push(lastFormat);
														voiceArr.push(obj2);
														smsArr.push(obj3);
														dataArr.push(obj4);
														vasArr.push(obj5);
														} 
												
												}
											
											obj2={};
											obj3={};
											obj4={};
											obj5={};
			 
									

		 
										});
								obj.arrObj.push(periodArr,voiceArr,smsArr,dataArr,vasArr);
								return obj.arrObj;
								}
						//getting JSon for the revenue data period ..... HAjouz 22.6.2021
						function getRevenueJSon(startDate, voiceRevenue, smsRevenue, dataRevneue, vasRevenue)
							{
							var obj = {};
							obj.arrObj = [];
							var splittedDate;
							var lastFormat;
							var totVoiceRevenue=0,totSmsRevenue=0,totDataRevneue=0,totVasRevenue=0,TotalRevenue=0;
							$.each(startDate, function(key, value){
								splittedDate = startDate[key].split("-");
								lastFormat= parseInt(splittedDate[1])+"/"+parseInt(splittedDate[2])+"/"+splittedDate[0];
										if(startDate[key]==startDate[key+1]){
										totVoiceRevenue+=parseFloat(voiceRevenue[key]);
										totSmsRevenue+=parseFloat(smsRevenue[key]);
										totDataRevneue+=parseFloat(dataRevneue[key]);
										totVasRevenue+=parseFloat(vasRevenue[key]);
										}else{
											if(startDate[key]==startDate[key-1]){
												totVoiceRevenue+=parseFloat(voiceRevenue[key]);
												totSmsRevenue+=parseFloat(smsRevenue[key]);
												totDataRevneue+=parseFloat(dataRevneue[key]);
												totVasRevenue+=parseFloat(vasRevenue[key]);
												TotalRevenue = totVoiceRevenue+totSmsRevenue+totDataRevneue+totVasRevenue;
												obj.value=[lastFormat,parseFloat(totVoiceRevenue),parseFloat(totSmsRevenue),parseFloat(totDataRevneue),parseFloat(totVasRevenue),parseFloat(TotalRevenue)];
												obj.arrObj.push(obj.value);
												}else{
													totVoiceRevenue=parseFloat(voiceRevenue[key]);
													totSmsRevenue=parseFloat(smsRevenue[key]);
													totDataRevneue=parseFloat(dataRevneue[key]);
													totVasRevenue=parseFloat(vasRevenue[key]);
													TotalRevenue = totVoiceRevenue+totSmsRevenue+totDataRevneue+totVasRevenue;
													
													obj.value=[lastFormat,parseFloat(totVoiceRevenue),parseFloat(totSmsRevenue),parseFloat(totDataRevneue),parseFloat(totVasRevenue),parseFloat(TotalRevenue)];
													obj.arrObj.push(obj.value);
													}

											}
								

								
									});

							
							//console.log("arrObject is: " +obj.arrObj);
							
							return obj.arrObj;		
						}
*/