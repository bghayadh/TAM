function changeFunc() {
var selectBox = document.getElementById("msChngOpt");
var selectedValue = selectBox.options[selectBox.selectedIndex].value;
var selectBox1 = document.getElementById("stackedChngOpt");
var selectedValue1 = selectBox1.options[selectBox1.selectedIndex].value;
var selectBox2 = document.getElementById("lineChngOpt");
var selectedValue2 = selectBox2.options[selectBox2.selectedIndex].value;
var selectBox3 = document.getElementById("revPieChngOpt");
var selectedValue3 = selectBox3.options[selectBox3.selectedIndex].value;
var selectBox4 = document.getElementById("revPieChngOpt");
var selectedValue4 = selectBox4.options[selectBox4.selectedIndex].value;
$("#msChngOpt").change(function(){
	if (selectedValue=="isBetween"){
		$("#msFltrDiv").show();
		$('#txtBtn').show();
		$('#minValMSFltr').show();
		$('#maxValMSFltr').show();
		$('#msRevOpt').show();

		}
		 else if (selectedValue=="null")
		{
	 			$("#msRevOpt").val('voice_revenue').trigger('change');
	 			$('#minValMSFltr').val('');
				$('#maxValMSFltr').val('');
				$('#txtBtn').hide();
				$('#minValMSFltr').hide();
				$('#maxValMSFltr').hide();
				$('#msRevOpt').hide();
				
		} 
});


$("#stackedChngOpt").change(function(){

if (selectedValue1=="isBetween"){
	$("#stackedFltrDiv").show();
	$('#txtBtn1').show();
	$('#minValStackedFltr').show();
	$('#maxValStackedFltr').show();
	$('#stackedRevOpt').show();
	}
	 else if (selectedValue1=="null")
	{
		 $("#stackedRevOpt").val('voice_revenue').trigger('change');
			$('#minValStackedFltr').val('');
			$('#maxValStackedFltr').val('');
			$('#txtBtn1').hide();
			$('#minValStackedFltr').hide();
			$('#maxValStackedFltr').hide();
			$('#stackedRevOpt').hide();
			
	} 
});
$("#lineChngOpt").change(function(){

	if (selectedValue2=="isBetween"){
		$("#lineFltrDiv").show();
		$('#txtBtn2').show();
		$('#minValLineFltr').show();
		$('#maxValLineFltr').show();
		$('#lineRevOpt').show();

		}
		 else if (selectedValue2=="null")
		{
			 $("#lineRevOpt").val('voice_revenue').trigger('change');
				$('#minValLineFltr').val('');
				$('#maxValLineFltr').val('');
				$('#txtBtn2').hide();
				$('#minValLineFltr').hide();
				$('#maxValLineFltr').hide();
				$('#lineRevOpt').hide();
				
		} 
	});
$("#revPieChngOpt").change(function(){

	if (selectedValue3=="isBetween"){
		$("#revPieFilterDiv").show();
		$('#txtBtn3').show();
		$('#minValRevPiesFltr').show();
		$('#maxValRevPiesFltr').show();
		$('#revPieRevOpt').show();

		}
		 else if (selectedValue3=="null")
		{
			 $("#revPieRevOpt").val('voice_revenue').trigger('change');
				$('#minValRevPiesFltr').val('');
				$('#maxValRevPiesFltr').val('');
				$('#txtBtn3').hide();
				$('#minValRevPiesFltr').hide();
				$('#maxValRevPiesFltr').hide();
				$('#revPieRevOpt').hide();
				
		} 
	});
	$("#regPieChngOpt").change(function(){

	if (selectedValue2=="isBetween"){
		$("#regPieFilterDiv").show();
		$('#txtBtn4').show();
		$('#regMinValFltr').show();
		$('#regMaxValFltr').show();
		$('#regPieRevOpt').show();

		}
		 else if (selectedValue2=="null")
		{
			 $("#regPieRevOpt").val('voice_revenue').trigger('change');
				$('#regMinValFltr').val('');
				$('#regMaxValFltr').val('');
				$('#txtBtn4').hide();
				$('#regMinValFltr').hide();
				$('#regMaxValFltr').hide();
				$('#regPieRevOpt').hide();
				
		} 
	});
}



function changeRevenueFunc() {

	$("#msRevOpt").change(function(){
		$('#minValMSFltr').val('');
		$('#maxValMSFltr').val('');
		});

	$("#stackedRevOpt").change(function(){
		$('#minValStackedFltr').val('');
		$('#maxValStackedFltr').val('');
		});
	$("#lineRevOpt").change(function(){
		$('#minValLineFltr').val('');
		$('#maxValLineFltr').val('');
		});
	$("#revPieRevOpt").change(function(){
		$('#minValRevPiesFltr').val('');
		$('#maxValRevPiesFltr').val('');
		});
	$("#regPieRevOpt").change(function(){
		$('#regMinValFltr').val('');
		$('#regMaxValFltr').val('');
		});
	}
	
				  
				  
/*function renderRegionCharts(listRegions){
	
	RegionalRevenuePie(listRegions);
	//PieRevPerTech(listRegions);
}
 */
                    	
                    	
                    					

						 function RegionRevenue(msColumnChartObj){
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
						  	   var regionRevenueParam = new FusionCharts({
						  	     type: 'scrollColumn2d',
						  	     renderAt: 'chartContainer3',
						  	     width: '100%',
						  	     height: '600',
						  	     dataFormat: 'json',

						  	     dataSource: {
					  	       "chart": {
					  	         "theme": "fusion",
					  	         "caption": "Region Revenue",
					  	       "captionFontSize": "20",
					  	         "xAxisname": "Regions",
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

						  	 regionRevenueParam.render();
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
							// console.log("pieRevenue. "+pieRevenue );
							if(pieRevenue != "" && pieRevenue[0][4] !=0){
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
						        "labelFontSize":"14px",
								"labelAlpha":"90",
								"labelFontBold":"1",
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

				 function RegionalRevenuePie(listRegions){
								delete lbl1;delete lbl2;delete lbl3;delete lbl4;delete lbl5;delete lbl6;delete lbl7;delete lbl8;
								delete valPrcnt1;delete valPrcnt2;delete valPrcnt3;delete valPrcnt4;delete valPrcnt5;delete valPrcnt6;delete valPrcnt7;delete valPrcnt8;
								var valTotal=0 ;
								var count = 0;	
									if(	listRegions.length!=0 && listRegions !=""){
									
									$.each(listRegions, function( i, value ){
									    window['lbl'+(i+1)] = listRegions[i][0] + ':' + (listRegions[i][2].toFixed(2)).toString()+'$';
										count++;
									    //valTotal +=parseFloat(listRegions[i][2].toFixed(2));
									    window['valPrcnt'+(i+1)] = (listRegions[i][2]/listRegions[i][3])*100;
									});
						
									}
									
									
										else{
											lbl1 = "No Data";lbl2="";lbl3="";lbl4="";lbl5="";lbl6="";lbl7="";lbl8="";
											valPrcnt1 = 100;
											valPrcnt2=0;valPrcnt3=0;valPrcnt4=0;valPrcnt5=0;valPrcnt6=0;valPrcnt7=0;valPrcnt8=0;			
									 
										}
														
					 		
		

							const dataSource = {
									
							  chart: {
								    caption: "Total revenue of each region",
								    subcaption: "For a whole-count of "+count+" regions",
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
							        "labelFontSize":"14px",
									"labelAlpha":"90",
									"labelFontBold":"1",
							  },

							  data: [
							    {
							      label: (typeof lbl1 === "undefined") ? "" : lbl1,
							      value: (typeof valPrcnt1=== "undefined") ? 0 : valPrcnt1,
							      "color":"#FFFF00"
							      
							    },
							    {
							      label: (typeof lbl2 === "undefined") ? "" : lbl2,
							      value: (typeof valPrcnt2 === "undefined") ? 0 : valPrcnt2,
							      "color":"#0000FF"
							    }
							    ,
							    {
							      label: (typeof lbl3 === "undefined") ? "" : lbl3,
							      value: (typeof valPrcnt3 === "undefined") ? 0 : valPrcnt3,
							      "color":"#FF0000"
							    }
							    ,
							    {
							      label: (typeof lbl4 === "undefined") ? "": lbl4,
							      value: (typeof valPrcnt4 === "undefined") ? 0 : valPrcnt4,
							      }
							      ,
							    {
							      label: (typeof lbl5 === "undefined") ? "": lbl5,
							      value: (typeof valPrcnt5 === "undefined") ? 0 : valPrcnt5,
							    }
							    ,
							    {
							     label: (typeof lbl6 === "undefined") ? "": lbl6,
							      value: (typeof valPrcnt6 === "undefined") ? 0 : valPrcnt6,
							    }
							    ,
							    {
							      label: (typeof lbl7 === "undefined") ? "": lbl7,
							      value: (typeof valPrcnt7 === "undefined") ? 0 : valPrcnt7,
							    }
							    ,
							    {
							      label: (typeof lbl8 === "undefined") ? "": lbl8,
							      value: (typeof valPrcnt8 === "undefined") ? 0 : valPrcnt8,
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
						function fetchRevenueStacked(StackedandLineRevenue, startDate, endDate) {
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
						 function fetchRevenueStackedSecondary(StackedandLineRevenue, startDate, endDate) {
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
									for(i = 0;i<StackedandLineRevenue.length;i++){
									date=StackedandLineRevenue[i][0];
							voiceRevenue = StackedandLineRevenue[i][1];//jQuery.parseJSON(getData(StackedandLineRevenue[i][1]));
							 smsRevenue = StackedandLineRevenue[i][2];//jQuery.parseJSON(getData(StackedandLineRevenue[i][2]));
							 dataRevneue = StackedandLineRevenue[i][3];// jQuery.parseJSON(getData(StackedandLineRevenue[i][3]));
							 vasRevenue = StackedandLineRevenue[i][4];//jQuery.parseJSON(getData(StackedandLineRevenue[i][4]));
							 totalRevenue=StackedandLineRevenue[i][5];
							 data= [date,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
							 
							 }
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
							    startDate.push(($('#headerStrtDate').val().split(" "))[0].toString()); 
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
							function fetchLineChartRevenueSecondary(StackedandLineRevenue) {
								//console.log("StackedandLineRevenue."+StackedandLineRevenue );
							    var startDate=[],voiceRevenue = [],smsRevenue = [],dataRevneue = [],vasRevenue = [],totalRevenue = [];
							    var objRevenue={};

							    let data = [];

							    var label1,label2,label3,label4,label5;
							
							   if(StackedandLineRevenue !="null" && StackedandLineRevenue.length !=0){
									label1 = "Voice";label2="SMS";label3="Data";label4="VAS";label5="Total";
									for(i=0;i<StackedandLineRevenue.length;i++){
									startDate = StackedandLineRevenue[i][0];
									voiceRevenue = StackedandLineRevenue[i][1];//jQuery.parseJSON(getData(StackedandLineRevenue[i][1]));
									smsRevenue = StackedandLineRevenue[i][2];//jQuery.parseJSON(getData(StackedandLineRevenue[i][2]));
									dataRevneue = StackedandLineRevenue[i][3];//jQuery.parseJSON(getData(StackedandLineRevenue[i][3]));
									vasRevenue = StackedandLineRevenue[i][4];//jQuery.parseJSON(getData(StackedandLineRevenue[i][4]));
									totalRevenue=StackedandLineRevenue[i][5];
									data= [startDate,voiceRevenue,smsRevenue,dataRevneue,vasRevenue,totalRevenue];
									
									}
									

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

										 startDate.push(($('#headerStrtDate').val().split(" "))[0].toString()); 
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
				 		 function PieRevPerTech(chartData){
				 			
				 			
				 			var label1="",label2="",label3="",lvl1,lvl2,lvl3;
							var lowPrcnt=0,medPrcnt=0,highPrcnt=0;
							var regCount=0,lowCount=0,mediumCount=0,highCount=0;
									
									if(	chartData.length!=0 && chartData !=""){
									
										    
											
												
									}
										else{
											label1 = "NO Data";label2="";label3="";
											
											lowPrcnt =100;
											medPrcnt=0;
											highPrcnt=0;

										
									 
										}
												
					

							const dataSource = {
									
							  chart: {
							    caption: "Revenue split based on 2G, 3G and 4G",
							    captionFontSize:"16",
							    //subcaption: "For the whole-worth of "+totRevTech+"$",
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


							
							function getDataArray(siteNames,voiceRevenue,smsRevenue,dataRevenue,vasRevenue){
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

						