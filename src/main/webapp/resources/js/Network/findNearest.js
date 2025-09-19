function viewNearestPointEvent(){
		$("#viewNearestp").on('click', function(){
		$("#searchManhTBody").empty();
		$("#searchHanhTBody").empty();
		$("#searchDBoardTBody").empty();
		$("#searchNodeTBody").empty();
		$("#searchManhTBody").html("");
		$("#searchHanhTBody").html("");
		$("#searchDBoardTBody").html("");
		$("#nearStrandId").html("");
		$("#nearFiberId").html("");
		$("#nearTubeId").html("");
		$("#searchNodeTBody").html("");
		$('#removeS').show();
		$("#SearchZone").prop("checked",true);
		$("#selectAllHandhole").prop('checked', false);
		$("#selectAllManhole").prop('checked', false);
		$("#selectAllDB").prop('checked', false);
		$("#selectAllNode").prop('checked', false);
		$('#removeFilter').hide();
		/*$("#closestLongPoint").val("");
		$("#closestLatPoint").val("");
		$("#closestDisRange").val("");
		$("#startLongPoint").val("");
	    $("#startLatPoint").val("");
	    $("#endLongPoint").val("");
	    $("#endLatPoint").val("");
		//$("#totalManhole").val("");
		//$("#totalHandhole").val("");
		//$("#totalDB").val("");*/
		
		 var closestDisRange = document.getElementById("closestDisRange").value;
		 var closestLatPoint = document.getElementById("closestLatPoint").value;
		 var closestLongPoint = document.getElementById("closestLongPoint").value;
		 var startLongPoint = document.getElementById("startLongPoint").value;
		 var startLatPoint = document.getElementById("startLatPoint").value;
		 var endLongPoint = document.getElementById("endLongPoint").value;
		 var endLatPoint = document.getElementById("endLatPoint").value;
		 
		 var urlString = "";
		 
		 if($("#circleRange").prop('checked')){
		  checkedOption = "circleRange";  
		  
			  if (closestDisRange === "" || closestLongPoint === "" || closestLatPoint === "" || isNaN(closestDisRange) || isNaN(closestLongPoint) || isNaN(closestLatPoint)) {
			    alert("Please enter a number in the input field.");
			    
			  }else{
				
				if (document.getElementById("serviceReference").value !== ""){
				 serviceReq= document.getElementById("serviceReference").value.split(":")[0];
				 serviceRef= document.getElementById("serviceReference").value.split(":")[1];
				}
				else {
					serviceReq="";
					serviceRef="";
				}
				
			     urlString += "&closestLatPoint="+$("#closestLatPoint").val()+"";
			     urlString += "&closestLongPoint="+$("#closestLongPoint").val()+"";
			     urlString += "&closestDisRange="+$("#closestDisRange").val()+"";
			     urlString += "&noP="+$("#noP").val()+"";
			     urlString += "&getRelatedPoints="+$("#getRelatedPoints").val()+"";	
				 urlString += "&CustomerID="+$("#customerDetails").val()+"";	
			 	 urlString += "&serviceReq="+serviceReq+"";
		 		 urlString += "&serviceRef="+serviceRef+"";
		 		 urlString += "&updateModfUser="+updateModfUser;		 		 
		
			 	 window.location.href = getContext()+"/NetworkPhysicalLayer?Checked="+checkedOption+urlString;
			    }	
			    	
		 }else{
			 checkedOption = "StartEnd";
			var countValidInput = 0;
			if (startLongPoint !== "" && !isNaN(startLongPoint)) {
			    countValidInput++;
			}
			if (startLatPoint !== "" && !isNaN(startLatPoint)) {
			    countValidInput++;
			}
			if (endLongPoint !== "" && !isNaN(endLongPoint)) {
			    countValidInput++;
			}
			if (endLatPoint !== "" && !isNaN(endLatPoint)) {
			    countValidInput++;
			}
			
			if (countValidInput > 0) {
				 urlString += "&startLongPoint="+$("#startLongPoint").val()+"";
				 urlString += "&startLatPoint="+$("#startLatPoint").val()+"";
				 urlString += "&endLongPoint="+$("#endLongPoint").val()+"";
				 urlString += "&endLatPoint="+$("#endLatPoint").val()+"";
				 urlString += "&getRelatedPoints="+$("#getRelatedPoints").val()+"";
				 urlString += "&updateModfUser="+updateModfUser;	
				 window.location.href = getContext()+"/NetworkPhysicalLayer?Checked="+checkedOption+urlString;
            }
			else {
			    alert("Please enter at least one valid input.");
			}

		 }	    
		});		
}

// To open the find nearest popup and drawing the circle, the rectangle and append the nearest points 
function  openFindNearest(checkedOption,closestLatPoint,closestLongPoint,closestDisRange,noP,arrayManhole,arrayHandhole,arrayDB,controllerList,arrayFibers,arrayStrands,arrayTubes,arrayNodes,getRelatedPoints,strtLng,endLng,strtLat,endLat,customerID,customerName,serviceReq,serviceRef){ 	
console.log(arrayDB);
	
		$("#StartEnd").prop("checked",false);
	 document.getElementById("closestLongDiv").style.display = "block";
	 document.getElementById("closestLatDiv").style.display = "block";
	 document.getElementById("closestDistanceRange").style.display = "block";
	 document.getElementById("setCoordDiv").style.display = "block";
	 document.getElementById("NoOfPoints").style.display = "block";
	 document.getElementById("StartLatDiv").style.display = "none";
	 document.getElementById("EndLatDiv").style.display = "none";
	 document.getElementById("StartLongDiv").style.display = "none";
	 document.getElementById("EndLongDiv").style.display = "none";
	 document.getElementById("setEndPointDiv").style.display = "none";
	 document.getElementById("setStartPointDiv").style.display = "none";
	 $('a[href="#closest"]').click();
	 $("#fiberCitySearch").modal("show");
    $("#circleRange").prop("checked",true);
    
    	

		if(closestLatPoint != '' &&  closestLongPoint != '' && closestDisRange != ''){
			var finalArrayFibers = [];
			$("#noP").val(noP);
			$("#closestLatPoint").val(closestLatPoint);
			$("#closestLongPoint").val(closestLongPoint);
			$("#closestDisRange").val(closestDisRange);
			showPointsType =getRelatedPoints;
			
		     if(getRelatedPoints == '1') {
				$("#getRelatedPoints").prop('checked', true);
			 }
			else {
				$("#getRelatedPoints").prop('checked', false);
			}
			$("#customerDetails").val(customerID);
			$("#customerName").val(customerName);
		
				
				$("#serviceReference").val(serviceReq);
				$("#serviceReferenceId").val(serviceRef);
			
			
					 appendNearestManholesTable(arrayManhole);
					 appendNearestHandholesTable(arrayHandhole);					        			
					 appendNearestDBoardTable(arrayDB);
					 finalArrayFibers.push(arrayStrands);
					 finalArrayFibers.push(arrayTubes);
					 finalArrayFibers.push(arrayFibers);
					 appendNearestFiberPathsTable(finalArrayFibers);
					 appendNearestNodesTable(arrayNodes);					        			

					 $("#totalManhole").val(arrayManhole.length);
					 $("#totalHandhole").val(arrayHandhole.length);
					 $("#totalDB").val(arrayDB.length);
					 $("#totalNode").val(arrayNodes.length);

					 const myLatLng = { lat: parseFloat(closestLatPoint), lng: parseFloat(closestLongPoint) };
				   
					//restMAP();
					//$('#filterr').show();

																
					new google.maps.Marker({
							 position: myLatLng,
						     map,
							 title: "Marked",
					});
					//var circleRadius = closestDisRange * 1.609344;
					console.log("circleRadius is "+circleRadius);
					//var circleRadius = closestDisRange *1.609344 *1.609344;
					var circleRadius = closestDisRange;
					// Drawing find nearest circle
					var circ = new google.maps.Circle({
					         strokeColor: "blue",
					         strokeOpacity: 0.8,
					         strokeWeight: 2,
					         fillColor: "blue",
					         fillOpacity: 0.1,
					         map,
					         center: myLatLng,
					         radius: circleRadius * 1000,
					         clickable:false					         
					       });		
						displayZoneMap(circ);
				        map.setCenter(myLatLng);
					    map.fitBounds(circ.getBounds());
					    
					    
				// Drawing the find nearest rectangle lines.	
				startlangPath =[new google.maps.LatLng(strtLat,strtLng), new google.maps.LatLng(endLat,strtLng)];
				drawLine("#FF0000",startlangPath);
				startlatgPath =[new google.maps.LatLng(strtLat,strtLng), new google.maps.LatLng(strtLat,endLng)];
				drawLine("#FF0000",startlatgPath);
				endlangPath =[new google.maps.LatLng(endLat,endLng), new google.maps.LatLng(strtLat,endLng)];
				drawLine("#006400",endlangPath);
				endlatgPath =[new google.maps.LatLng(endLat,endLng), new google.maps.LatLng(endLat, strtLng)];
				drawLine("#006400",endlatgPath);
				
				
		     		
		      

		} else {
			alert("Please Fill All Fields");
		}
		closestLatPoint='';
		closestLongPoint='';
		closestDisRange='';
}
$("#saveSurvey").on('click',function(){
	
	if(document.getElementById("customerDetails").value == "" ){
		alert("Customer cannot be empty. ");
		return false;
    }
	else if(document.getElementById("serviceReference").value == "" ){
		alert("Service Reference cannot be empty. ");
		return false;
    }
	else if(document.getElementById("serviceAppNo").value == "" ){
		alert("Service Application Number cannot be empty. ");
		return false;
    }
	else {	
			
		customerID = document.getElementById("customerDetails").value;
		serviceReference = document.getElementById("serviceReference").value.split(":")[1];
		serviceRequest = document.getElementById("serviceReference").value.split(":")[0];
		longitude = document.getElementById("closestLongPoint").value;
		latitude = document.getElementById("closestLatPoint").value;
		surveyID = document.getElementById("surveyID").value;
		serviceAppNo = document.getElementById("serviceAppNo").value;
		var token =  $('input[name="csrfToken"]').attr('value');

		$.ajax({
			type: "POST",
			url: getContext()+'/saveSurvey',
			headers: {
				'X-CSRFToken': token 
			},
			data: {

				"customerID":customerID,
				"serviceReference":serviceReference,
				"serviceRequest":serviceRequest,
				"longitude":longitude,
				"latitude":latitude,
				"dictParameter":manholeSurveyArray,
				"dictParameterHandholeSurv":handholeSurveyArray,
				"dictParameterDbSurv":dbSurveyArray,
				"dictParameterNodeSurv":nodeSurveyArray,
				"dictParameterCableSurv":fiberCableSurveyArray,
				"dictParameterTubeSurv":fiberTubesSurveyArray,
				"dictParameterStrandSurv":fiberStrandsSurveyArray,
				"surveyID":surveyID,
				"serviceAppNo":serviceAppNo,
				"updateModfUser" : updateModfUser,

			},
			beforeSend: function() {
				$("#saveSurveyLoaderDiv").show();
			},
			dataType: "json",
			success: function (data) {
				$("#saveSurveyLoaderDiv").hide();
				$("#surveyID").val(data.surveyID);	
				document.getElementById('updateOnMySD').disabled = false;
		},
		error: function (result) {
			alert("Error");
		}
		});							   
}
});

	$("#updateOnMySD").on('click',function(){
		
		document.getElementById("updateonMySDResult").innerText = "";
		document.getElementById("updateonMySDResult").style.display = "none";
		
	 $("#updatingOnMySDLoaderDiv").show();


	if(document.getElementById("surveyNearestPt").value == "" || document.getElementById("surveyNearestPt").value == null ){
		alert("Please select a nearest point before updating on MySD . ");
		return false;
    }
	else if(document.getElementById("unitPrice").value == "" ){
		alert("Please add unit price before updating on MySD .");
		return false;
    }
	else if(isNaN(document.getElementById("unitPrice").value) ==true ){
		alert("Incorrect Format of Unit Price.");
		return false;
	}
	else {
		var currentUrl = window.location.href;
		var surveyId = document.getElementById("surveyID").value;		
		var surveyNearestPoint = document.getElementById("surveyNearestPt").value;
		var serviceAppNumber = document.getElementById("serviceAppNo").value;	

		var unitPrice = document.getElementById("unitPrice").value;	
		var dist = surveyNearestPoint.split(":")[1];
		var totalPrice = unitPrice * dist;
		
		
		var token =  $('input[name="csrfToken"]').attr('value');

		$.ajax({
			type: "POST",
			url: getContext()+'/updateOnMySD',
			headers: {
				'X-CSRFToken': token 
			},
			data: {
				"serviceAppNumber":serviceAppNumber,
				"surveyID":surveyId,
				"currentUrl":currentUrl,
				"nearestPoint":surveyNearestPoint,
				"totalPrice":totalPrice
			},
			dataType: "json",
			success: function (data) {
				$("#updatingOnMySDLoaderDiv").hide();

				var div = document.getElementById("updateonMySDResult");
				div.style.display = "block";
				div.innerText = ""+data.updateOnMySDStatus;
				div.style.fontWeight = "bold";
				div.style.color = "red";
			},
		error: function (result) {
			alert("Error");
			$("#updatingOnMySDLoaderDiv").hide();

		}
		});							   
	}
	});
	

		
			
			
function selectAsNearest(e){
					if (typeof(e) == "object") {
						var ID = $(e).parent().parent().children('td[name="ID"]').children('input').val();
						var distLinear = $(e).parent().parent().children('td[name="linearDistance"]').children('input').val();
						surveyNearestPoint = ID;
						$("#surveyNearestPt").val(surveyNearestPoint);
						$("#surveyNearestPtDis").val(distLinear);
					}			
			}
			


function getDrivingDistanceClosePoint(e) {
					if (typeof(e) == "object") {
						var directionsService = new google.maps.DirectionsService();
						
						var lat = $(e).parent().parent().children('td[name="LATT"]').children('input').val();
						var lng = $(e).parent().parent().children('td[name="LONGG"]').children('input').val();
						
						const originept = {lat: parseFloat(parseFloat($("#closePtsLat").val())), lng: parseFloat(parseFloat($("#closePtsLong").val()))};
						const nextpt = {lat: parseFloat(lat), lng: parseFloat(lng)};
						var request  = {
							origin: originept,
							destination: nextpt,
							travelMode  : google.maps.DirectionsTravelMode.DRIVING
						}
						directionsService.route(request, function(response, status) {
							if ( status == google.maps.DirectionsStatus.OK ) {
								var resultt= ( response.routes[0].legs[0].distance.value) ;

							   }
							else {
								resultt= "no Root";
								//alert("no resultt ");
							}
							$(e).parent().parent().children('td[name="DDistanceB"]').children('button').hide();
							$(e).parent().parent().children('td[name="DDistance"]').children('input').show();
							$(e).parent().parent().children('td[name="DDistance"]').children('label').empty();
							$(e).parent().parent().children('td[name="DDistance"]').children('label').append(resultt);
							makeAllSortable();
						  });
						  
					} else {
						return false;
					}
}
function appendNearestFiberPathsTable(result){
		var markupNearStrand="";
		var markupNearTube="";
		var markupNearFiber="";		
		document.getElementById("findNearestStrandRes").innerHTML = "";
				
			if (result[0].length==0){
				document.getElementById("findNearestStrandRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
				//markupDBoard ="<tr style='height:20px;'><td>There is no result<td></tr>"
			}
		else {
			
			
														
			result[0].forEach((res) => 
				markupNearStrand +="<tr ><td style='min-width:150px;' class='row-pad' name='ID'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+res[0]+"' readonly></input ></td>"+
			"<td style='min-width:150px;' name='name'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+res[13]+"' readonly></input ></td>"+
			"<td style='min-width:350px;' name='source'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='source' style='border: none;' value='"+res[6]+"' readonly></input ></td>"+
			"<td style='min-width:350px;' name='destination'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='destination' style='border: none;' value='"+res[9]+"' readonly></input ></td></tr>"
				);
		}
		$("#nearStrandId").append(markupNearStrand);
		
		if (result[1].length==0){
						document.getElementById("findNearestTubeRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
						//markupDBoard ="<tr style='height:20px;'><td>There is no result<td></tr>"
					}else {
			result[1].forEach((res) => 
				markupNearTube +="<tr ><td style='min-width: 150px;' class='row-pad' name='ID'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='ID' style='border: none;' value='"+res[0]+"' readonly></input ></td>"+
			"<td style='min-width: 150px;' name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'t name='name' style='border: none;' value='"+res[13]+"' readonly></input ></td>"+
			"<td style='min-width: 350px;' name='source' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='source' style='border: none;' value='"+res[6]+"' readonly></input ></td>"+
			"<td style='min-width: 350px;' name='destination' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='destination' style='border: none;' value='"+res[9]+"' readonly></input ></td></tr>"
			);
		}						  
		$("#nearTubeId").append(markupNearTube);
		
		if (result[2].length==0){
						document.getElementById("findNearestFiberRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
						//markupDBoard ="<tr style='height:20px;'><td>There is no result<td></tr>"
					}else {
			result[2].forEach((res) => 
				 markupNearFiber +="<tr ><td style='min-width: 150px;' class='row-pad' name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+res[4]+"' readonly></input ></td>"+
			"<td style='min-width: 150px;' name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+res[13]+"' readonly></input ></td>"+
			"<td style='min-width: 350px;' name='source' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='source' style='border: none;' value='"+res[6]+"' readonly></input ></td>"+
			"<td style='min-width: 350px;' name='destination' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='destination' style='border: none;' value='"+res[9]+"' readonly></input ></td></tr>"
			);
		}						  
		$("#nearFiberId").append(markupNearFiber);
																	
	}
			
function appendNearestDBoardTable(result){
		var markupDBoard="";
		document.getElementById("findNearestDbRes").innerHTML = "";
			
		if (result.length==0){
			document.getElementById("findNearestDbRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
			//markupDBoard ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}
		else { 
				for(var i =0 ; i<result.length;i++){
					//alert(result[i]);
					if($("#StartEnd").is(":checked")){
							
						
						
						
						
						markupDBoard +="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td>"+
													"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
													"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
													"<td  name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
													"<td   name='LATT' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
													"<td    style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='' readonly></input ></td>"+
													"<td    style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='' readonly></input ></td>"+
													"<td    style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='' readonly></input ></td>"+
													"<td    style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='' readonly></input ></td>"+
													"<td    style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='' readonly></input ></td>";
															
				    }
				    else{
				    	if(result[i][10] == null || result[i][10]==""){
							//markupDBoard +="<tr ><td style='min-width:250px;' class='row-pad'>"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][3]+"</td><td  name='LONGG' style='min-width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td><td style='min-width:150px;'  name='LATT'><input name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='min-width:50px;'>"+result[i][8]/1.60934+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='SomeDeleteRowFunction(this)'>Get Distance</button> </td></tr>"
							markupDBoard +="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td>"+
							"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
							"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
							"<td  name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
							"<td   name='LATT' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
							"<td name='linearDistance' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='linearDistance' style='border: none;' value='"+result[i][9]+"' readonly></input ></td>"+
							"<td style='width:300px; height:30px; vertical-align: top;' name='DDistance'>"+
							    "<button type='button' style='width:75px; font-size:9px;' name='DDistanceB' onclick='getDrivingDistance(this)'>Get Distance</button>"+
							    "<label name='DDistance' style='border: none; width:80px; font-size:14px; margin-left:10px;' id='dDistanceResult'></label>"+
							"</td>"+
                         	"<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px;' id='geoDistance'></label></td>"+
							"<td style='width:300px; height:30px;vertical-align: top;' name='selectPointAsNearest'><button  type='button' style='width:100px;font-size:10px;margin-left:20px' name='selectPointAsNearest' class='btn btn-primary'  onclick='selectAsNearest(this)'>Select As Nearest</button></td></tr>"
						}else{
							markupDBoard 							+="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='DBBOQ' id=BOQ_"+result[i][0]+" ></td>"+
														"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
														"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
														"<td  name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
														"<td   name='LATT' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
															"<td style='min-width:50px;'>"+result[i][8]+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][10])+"</label></td>"+
							"<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px;' id='geoDistance'></label></td></tr>"
						}				    	
				    }
				}
		}
		$("#searchDBoardTBody").append(markupDBoard);
		if($("#circleRange").is(":checked")){
			drivingDistance("findNearstDB");
		}
        makeAllSortable();
		
		$("#selectAllDB").click(function(){
			if($(this).is(":checked")){
			console.log("entered "+ $(this).attr('id'));
			$('input[type="checkbox"]', '#findNearstDB').prop('checked', true);
			$(".DistBoard").prop('checked', true);
			$(".AllDistBoards").prop("checked",true);
			$(".BackboneDB").prop("checked",true);
			$(".MetroDB").prop("checked",true);
			$(".AccessDB").prop("checked",true);
			markerClusterBackboneDistBoard.clearMarkers(); 
			markerClusterMetroDistBoard.clearMarkers(); 
			markerClusterAccessDistBoard.clearMarkers(); 

			$("#network_tree").find(".DistBoard:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersDistBoard[id].setMap(map);
				if(window[""+id][8]=="backbone") {
					markerClusterBackboneDistBoard.addMarker(markersDistBoard[id]);
				}
				else if(window[""+id][8]=="metro") {
					markerClusterMetroDistBoard.addMarker(markersDistBoard[id]);

				}
				else if(window[""+id][8]=="access") {
					markerClusterAccessDistBoard.addMarker(markersDistBoard[id]);
				}			
													
			});	
			}
			else{
				$('input[type="checkbox"]', '#findNearstDB').prop('checked', false);
				$(".DistBoard").prop('checked', false);
				$(".AllDistBoards").prop("checked",false);
				$(".BackboneDB").prop("checked",false);
				$(".MetroDB").prop("checked",false);
				$(".AccessDB").prop("checked",false);
				markerClusterBackboneDistBoard.clearMarkers(); 
				markerClusterMetroDistBoard.clearMarkers(); 
				markerClusterAccessDistBoard.clearMarkers(); 

			}
			
		});
		
	$('.DBBOQ').click(function(){		
				console.log("entered "+ $(this).attr('id').split("BOQ_")[1]);
				var DBdId = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+DBdId).children('input:checkbox').prop('checked', true);
					markersDistBoard[DBdId].setMap(map);
					if(window[""+DBdId][8]=="backbone") {
						markerClusterBackboneDistBoard.addMarker(markersDistBoard[DBdId]);
					}
					else if(window[""+DBdId][8]=="metro") {
						markerClusterMetroDistBoard.addMarker(markersDistBoard[DBdId]);
	
					}
					else if(window[""+DBdId][8]=="access") {
						markerClusterAccessDistBoard.addMarker(markersDistBoard[DBdId]);
					}						
				}
				else{
					$("#"+DBdId).children('input:checkbox').prop('checked', false);
					markersDistBoard[DBdId].setMap(null);
					if(window[""+DBdId][8]=="backbone") {
						markerClusterBackboneDistBoard.removeMarker(markersDistBoard[DBdId]);
					}
					else if(window[""+DBdId][8]=="metro") {
						markerClusterMetroDistBoard.removeMarker(markersDistBoard[DBdId]);
	
					}
					else if(window[""+DBdId][8]=="access") {
						markerClusterAccessDistBoard.removeMarker(markersDistBoard[DBdId]);
					}					
				}
		});
				
																	
	}
   
function appendNearestNodesTable(result){
		var markupNode="";		
		document.getElementById("findNearestNodeRes").innerHTML = "";
		
		if (result.length==0){
			document.getElementById("findNearestNodeRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
		}
		else {
			for(var i =0 ; i<result.length;i++){
				if($("#StartEnd").is(":checked")){
																		
					
					markupNode +="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='nodeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
											"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][7]+"' readonly></input ></td>"+
											"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
											"<td name='Node_Type' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='Node_Type' style='border: none;' value='"+result[i][2].split(':')[0]+"' readonly></input ></td>"+
											"<td  name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][5]+"' readonly></input ></td>"+
                                            "<td  style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'t  style='border: none;' value='' readonly></input ></td>"+
											"<td  name='LATT'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'t name='LATT' style='border: none;' value='"+result[i][6]+"' readonly></input ></td>"+
											"<td  name='LATT'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'t name='LATT' style='border: none;' value='"+result[i][6]+"' readonly></input ></td>"+
											"<td  name='LATT'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'t name='LATT' style='border: none;' value='"+result[i][6]+"' readonly></input ></td>";
				}
				else{
				    	if(result[i][10] == null || result[i][10]==""){
							markupNode +="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='nodeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
							"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][7]+"' readonly></input ></td>"+
							"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
							"<td name='Node_Type' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='Node_Type' style='border: none;' value='"+result[i][2].split(':')[0]+"' readonly></input ></td>"+
							"<td  name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][5]+"' readonly></input ></td>"+
							"<td  name='LATT'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'t name='LATT' style='border: none;' value='"+result[i][6]+"' readonly></input ></td>"+
							"<td name='linearDistance' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='linearDistance' style='border: none;' value='"+result[i][9]+"' readonly></input ></td>"+
							"<td style='width:300px; height:30px; vertical-align: top;' name='DDistance'>"+
							 "<label name='DDistance' style='border: none; width:80px; font-size:14px;' id='dDistanceResult'></label>"+
							   "<button type='button' style='width:75px; font-size:9px; margin-left:10px;' name='DDistanceB' onclick='getDrivingDistance(this)'>Get Distance</button>"+
							"</td>"
	"<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px;' id='geoDistance'></label></td><td style='width:300px; height:30px;vertical-align: top;' name='selectPointAsNearest'><button type='button' style='width:100px;font-size:10px;margin-left:20px' name='selectPointAsNearest' class='btn btn-primary' class='btn btn-primary'  onclick='selectAsNearest(this)'>Select As Nearest</button></td></tr>"

						}else{
							markupNode +="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='nodeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
														"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][7]+"' readonly></input ></td>"+
														"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
														"<td name='Node_Type' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='Node_Type' style='border: none;' value='"+result[i][2].split(':')[0]+"' readonly></input ></td>"+
														"<td  name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][5]+"' readonly></input ></td>"+
														"<td  name='LATT'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'t name='LATT' style='border: none;' value='"+result[i][6]+"' readonly></input ></td>"+
														"<td name='linearDistance' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='linearDistance' style='border: none;' value='"+result[i][9]+"' readonly></input ></td>"+
												"<td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][10])+"</label></td>"+
							"<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px;' id='geoDistance'></label></td></tr>"
						}
				 }

		}
	

		}
		$("#searchNodeTBody").append(markupNode);
		if($("#circleRange").is(":checked")){
			drivingDistance("findNearstNode");
		}
		makeAllSortable();
		
		$("#selectAllNode").click(function(){
			if($(this).is(":checked")){
			$('input[type="checkbox"]', '#findNearstNode').prop('checked', true);
			$(".Nodes").prop('checked', true);
			$(".AllNodeActive").prop("checked",true);
			$(".TransmissionSDH").prop("checked",true);
			$(".TransmissionGPON").prop("checked",true);
			$(".TransmissionDWDM").prop("checked",true);
			$(".EntrepriseMSAN").prop("checked",true);
			$(".EntrepriseSwitch").prop("checked",true);
			

			markerClusterMSANNodes.clearMarkers();
			markerClusterDWDMNodes.clearMarkers();
			markerClusterSDHNodes.clearMarkers();
			markerClusterGPONNodes.clearMarkers();
			markerClusterEntSwitchNodes.clearMarkers();

			 $("#network_tree").find(".Nodes:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersNodeActive[id].setMap(map);			

				if(window[""+id][8]=="MSAN") {
					markerClusterMSANNodes.addMarker(markersNodeActive[id]);
				}
				else if(window[""+id][8]=="DWDM") {
					markerClusterDWDMNodes.addMarker(markersNodeActive[id]);
				}
				else if(window[""+id][8]=="SDH") {
					markerClusterSDHNodes.addMarker(markersNodeActive[id]);
				}
				else if(window[""+id][8]=="GPON") {
					markerClusterGPONNodes.addMarker(markersNodeActive[id]);
				}	
				else if(window[""+id][8]=="SWITCH") {
					markerClusterEntSwitchNodes.addMarker(markersNodeActive[id]);
				}	
		     });	
			}
			else{
				$('input[type="checkbox"]', '#findNearstNode').prop('checked', false);
				$(".Nodes").prop('checked', false);
				$(".AllNodeActive").prop("checked",false);
				$(".TransmissionSDH").prop("checked",false);
				$(".TransmissionGPON").prop("checked",false);
				$(".TransmissionDWDM").prop("checked",false);
				$(".EntrepriseMSAN").prop("checked",false);
				$(".EntrepriseSwitch").prop("checked",false);

				markerClusterMSANNodes.clearMarkers();
				markerClusterDWDMNodes.clearMarkers();
				markerClusterSDHNodes.clearMarkers();
				markerClusterGPONNodes.clearMarkers();
				markerClusterEntSwitchNodes.clearMarkers();
			}
			
		});
		
		// checking single row checbox from boq
		$('.nodeBOQ').click(function(){
				console.log("entered "+ $(this).attr('id').split("BOQ_")[1]);
				var Id = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+Id).children('input:checkbox').prop('checked', true);
					markersNodeActive[Id].setMap(map);
					if(window[""+Id][8]=="MSAN") {
						markerClusterMSANNodes.addMarker(markersNodeActive[Id]);
					}
					else if(window[""+Id][8]=="DWDM") {
						markerClusterDWDMNodes.addMarker(markersNodeActive[Id]);
					}
					else if(window[""+Id][8]=="SDH") {
						markerClusterSDHNodes.addMarker(markersNodeActive[Id]);
					}
					else if(window[""+Id][8]=="GPON") {
						markerClusterGPONNodes.addMarker(markersNodeActive[Id]);
					}	
					else if(window[""+Id][8]=="SWITCH") {
						markerClusterEntSwitchNodes.addMarker(markersNodeActive[Id]);
					}
				}
				else{
					$("#"+Id).children('input:checkbox').prop('checked', false);
					markersNodeActive[Id].setMap(null);	
					if(window[""+Id][8]=="MSAN") {
						markerClusterMSANNodes.removeMarker(markersNodeActive[Id]);
					}
					else if(window[""+Id][8]=="DWDM") {
						markerClusterDWDMNodes.removeMarker(markersNodeActive[Id]);
					}
					else if(window[""+Id][8]=="SDH") {
						markerClusterSDHNodes.removeMarker(markersNodeActive[Id]);
					}
					else if(window[""+Id][8]=="GPON") {
						markerClusterGPONNodes.removeMarker(markersNodeActive[Id]);
					}	
					else if(window[""+Id][8]=="SWITCH") {
						markerClusterEntSwitchNodes.removeMarker(markersNodeActive[Id]);
					}
				}			
		});

	}
	function appendNearestHandholesTable(result){
		var markupHandh="";		
		document.getElementById("findNearestHandRes").innerHTML = "";
		
		handholeSurveyArray =[];

		
		if (result.length==0){
			document.getElementById("findNearestHandRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
			//markupHandh ="<tr style='height:20px;'><td>There is no result<td></tr>"
		}
		else {
			for(var i =0 ; i<result.length;i++){
			//alert(result[i]);
				if($("#StartEnd").is(":checked")){
					markupHandh +="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
										"<td name='ID'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
										"<td style='min-width:250px;' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
										"<td name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
										"<td  name='LATT' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
										"<td  style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='' readonly></input ></td>"+
										"<td  style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='' readonly></input ></td>"+
										"<td  style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='' readonly></input ></td>"+
										"<td  style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='' readonly></input ></td>"+
										"<td  style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='' readonly></input ></td>";
																													
			    }
			    else{
			    	if(result[i][8] == null || result[i][8]==""){
						//markupHandh +="<tr style='height: 30px;'><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='SomeDeleteRowFunction(this)'>Get Distance</button> </td></tr>"
						markupHandh +="<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
																"<td name='ID'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
																"<td style='min-width:250px;' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
																"<td name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
																"<td  name='LATT' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
						"<td style='min-width:250px;' name='linearDistance'><input  class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='linearDistance' style='border: none;' value='"+result[i][7]+"' readonly></input ></td>"+
						"<td style='width:300px; height:30px; vertical-align: top;' name='DDistance'>"+
																		    "<button type='button' style='width:75px; font-size:9px;' name='DDistanceB' onclick='getDrivingDistance(this)'>Get Distance</button>"+
																		    "<label name='DDistance' style='border: none; width:80px; font-size:14px; margin-left:10px;' id='dDistanceResult'></label>"+
																		"</td>"+
											                         "<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px; margin-right=100px;' id='geoDistance'></label></td>"+
											"<td style='min-width:150px; height:30px;vertical-align: top;' name='selectPointAsNearest'><button type='button' style='width:100px;font-size:10px;margin-left:20px' name='selectPointAsNearest' class='btn btn-primary' onclick='selectAsNearest(this)'>Select As Nearest</button></td></tr>"

					}else{
						//markupHandh +="<tr style='height: 30px;' ><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][10])+"</label></td></tr>"
						markupHandh +=						"<tr style='height: 30px;'><td style='text-align: center;'><input type='checkbox' class='HandholeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
																						"<td name='ID'style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
																						"<td style='min-width:250px;' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
						"<td name='LONGG' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
						"<td  name='LATT' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
						"<td style='width:100px;'>"+(result[i][7])+"</td>"+
						"<td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][8])+"</label></td>"+
						"<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px;' id='geoDistance'></label></td></tr>"					
					}
			    	
			    }
				


		}
	

		}
	
		$("#searchHanhTBody").append(markupHandh);
		if($("#circleRange").is(":checked")){
			drivingDistance("findNearstHandhole");
		}
		makeAllSortable();
		
		$("#selectAllHandhole").click(function(){
			if($(this).is(":checked")){
			console.log("entered "+ $(this).attr('id'));
			$('input[type="checkbox"]', '#findNearstHandhole').prop('checked', true);
			$(".Handhole").prop('checked', true);
			$(".AllHandholes").prop("checked",true);
			markerClusterHandhole.clearMarkers();
			 $("#network_tree").find(".Handhole:checked" ).each(function(){
				id=$(this).parent().attr('id');
				markersHandhole[id].setMap(map);			
				markerClusterHandhole.addMarker(markersHandhole[id]);
								
		     });	
			}
			else{
				$('input[type="checkbox"]', '#findNearstHandhole').prop('checked', false);
				$(".Handhole").prop('checked', false);
				$(".AllHandholes").prop("checked",false);
				markerClusterHandhole.clearMarkers();
			}
			
		});
		
		// checking single row checbox from boq
		$('.HandholeBOQ').click(function(){
				console.log("entered "+ $(this).attr('id').split("BOQ_")[1]);
				var HandId = $(this).attr('id').split("BOQ_")[1];
				if ($(this).is(':checked')){
					$("#"+HandId).children('input:checkbox').prop('checked', true);
					markersHandhole[HandId].setMap(map);
					markerClusterHandhole.addMarker(markersHandhole[HandId]);
				}
				else{
					$("#"+HandId).children('input:checkbox').prop('checked', false);
					markersHandhole[HandId].setMap(null);				
		    		markerClusterHandhole.removeMarker(markersHandhole[HandId]);
				}			
		});

	}
	function appendNearestManholesTable(result){
			var markupManh="";
			document.getElementById("findNearestManRes").innerHTML = "";
			
			manholeSurveyArray =[];
									
			if (result.length==0){
			document.getElementById("findNearestManRes").innerHTML = '<p style=" color:#ff0000;font-size: 1.4em;">There is no result</p>';
			//markupManh ="<tr style='height:20px;'><td>There is no result<td></tr>"
			}
			else {for(var i =0 ; i<result.length;i++){
				//alert(result[i]);
				if($("#StartEnd").is(":checked")){
					markupManh +=					"<tr style='height: 30px;'>"+
										"<td style='min-width:50px; text-align: center;'><input type='checkbox' class='ManholeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
										"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
										"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
										"<td name='LONGG' style='min-width:200px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
										"<td style='min-width:200px;' ><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
										"<td style='min-width:200px;' ><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  style='border: none;' value='' readonly></input ></td>"+
										"<td style='min-width:200px;' '><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  style='border: none;' value='' readonly></input ></td>"+
										"<td style='min-width:200px;' ><input class='form-control text-input' style='width:100%; position:relative; background-color: white;'  style='border: none;' value='' readonly></input ></td>"+
										"<td style='min-width:200px;' ><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' style='border: none;' value='' readonly></input ></td>";
																				
									
									
									
											    }
			    else{
				
			    	if(result[i][9] == null || result[i][9]==""){
						
						
						
					
					//markupManh +="<tr style='height: 30px;'><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td name='LONGG' style='width:150px;'><input name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td><td style='width:150px;' name='LATT'><input name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td  style='width:300px; height:30px;vertical-align: top;' name='DDistance'><label name='DDistance'  style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'></label></td> <td style='width:300px; height:30px;vertical-align: top;' name='DDistanceB'><button type='button' style='width:75px;font-size:9px; ' name='DDistanceB'  onclick='SomeDeleteRowFunction(this)'>Get Distance</button> </td></tr>"
					markupManh +="<tr style='height: 30px;'>"+
					"<td style='min-width:50px;text-align: center;'><input type='checkbox' class='ManholeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
					"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
					"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
					"<td name='LONGG' style='min-width:200px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
					"<td style='min-width:200px;' name='LATT'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
					"<td style='min-width:200px;' name='linearDistance' ><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='linearDistance' style='border: none;' value='"+result[i][7]+"' readonly></input ></td>"+
					"<td style='width:300px; height:30px; vertical-align: top;' name='DDistance'>"+
												    "<button type='button' style='width:75px; font-size:9px;' name='DDistanceB' onclick='getDrivingDistance(this)'>Get Distance</button>"+
												    "<label name='DDistance' style='border: none; width:80px; font-size:14px; margin-left:10px;' id='dDistanceResult'></label>"+
												"</td>"+
					                         "<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px; margin-right=100px;' id='geoDistance'></label></td>"+
					"<td style='min-width:150px; height:30px;vertical-align: top;' name='selectPointAsNearest'><button type='button' style='width:100px;font-size:10px;margin-left:20px' name='selectPointAsNearest' class='btn btn-primary' onclick='selectAsNearest(this)'>Select As Nearest</button></td></tr>"

				    }else{
					//markupManh +="<tr style='height: 30px;' ><td><input type='checkbox' style='width:100px' ></td><td  >"+result[i][0]+"</td><td style='min-width:250px;'>"+result[i][1]+"</td><td style='width:150px;'>"+result[i][2]+"</td><td style='width:150px;'>"+result[i][3]+"</td><td style='width:100px;'>"+(result[i][9]/1.60934)+"</td><td style='min-width:90px;'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][10])+"</label></td></tr>"
						markupManh +=					"<tr style='height: 30px;'>"+
										"<td style='min-width:50px;text-align: center;'><input type='checkbox' class='ManholeBOQ' id=BOQ_"+result[i][0]+" ></td>"+
										"<td name='ID' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='ID' style='border: none;' value='"+result[i][0]+"' readonly></input ></td>"+
										"<td name='name' style='min-width:250px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='name' style='border: none;' value='"+result[i][1]+"' readonly></input ></td>"+
										"<td name='LONGG' style='min-width:200px;'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LONGG' style='border: none;' value='"+result[i][2]+"' readonly></input ></td>"+
										"<td style='min-width:200px;' name='LATT'><input class='form-control text-input' style='width:100%; position:relative; background-color: white;' name='LATT' style='border: none;' value='"+result[i][3]+"' readonly></input ></td>"+
										"<td style='width:100px;'>"+(result[i][7])+"</td>"+
						"<td style='min-width:90px;' name='DDistance'> <label name='DDistance' style='border: none;width:80px;font-size: 14px;' id='dDistanceResult'>"+(result[i][8])+"</label></td>"+
						"<td name='geoDistance'><label name='geoDistance' style='border: none;width:80px;font-size: 14px;' id='geoDistance'></label></td></tr>"
				    }
			}


			}
			}						  
			$("#searchManhTBody").append(markupManh);
			if($("#circleRange").is(":checked")){
				drivingDistance("findNearstManhole");
			}
			makeAllSortable();
			//autoFitTable('findNearstManhole');


			$("#selectAllManhole").click(function(){
				if($(this).is(":checked")){
				console.log("entered "+ $(this).attr('id'));
				
				$('input[type="checkbox"]', '#findNearstManhole').prop('checked', true);
				$(".Manhole").prop('checked', true);
				$(".AllManholes").prop("checked",true);
				markerClusterManhole.clearMarkers();
				$("#network_tree").find(".Manhole:checked" ).each(function(){
					id=$(this).parent().attr('id');
					markersManhole[id].setMap(map);			
					markerClusterManhole.addMarker(markersManhole[id]);
							
				});
				}
				else{
					$('input[type="checkbox"]', '#findNearstManhole').prop('checked', false);
					$(".Manhole").prop('checked', false);
					$(".AllManholes").prop("checked",false);
					markerClusterManhole.clearMarkers();
				}
			});
			
			// checking single row checbox from boq
			$('.ManholeBOQ').click(function(){
					console.log("entered "+ $(this).attr('id').split("BOQ_")[1]);
					var ManId = $(this).attr('id').split("BOQ_")[1];
					if ($(this).is(':checked')){
						$("#"+ManId).children('input:checkbox').prop('checked', true);
						markersManhole[ManId].setMap(map);
						markerClusterManhole.addMarker(markersManhole[ManId]);
					}
					else{
						$("#"+ManId).children('input:checkbox').prop('checked', false);
			    		markersManhole[ManId].setMap(null);				
						markerClusterManhole.removeMarker(markersManhole[ManId]);
					}
				
			});

		}				
