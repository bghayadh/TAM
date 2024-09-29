var ctx = getContextPath();
var allPortList =[];
function viewMapping(nodeID,row) {
	 allPortList =[];
	console.log("viewMapping "+nodeID);
	$.ajax({
		  type: "GET",
		  contentType: "application/json; charset=utf-8",
		  url: ctx+'/findNodeMappingDetails',
		  async:false,
		  data: {
			  "NodeID":nodeID 
				},
		  dataType: "json",
		  success : function(data) {
		  //match active and passive port data
		getMappingActiveRecord(data.listActivePort,data.listPassivePort);
		 //sort data based on port number
		 sortMappingAllPortListByPortNb();
		 
		 viewDiagram(allPortList,nodeID);
		 
		 //console.log("allPortList length "+ allPortList.length)
		console.log("allPortList "+ JSON.stringify(allPortList, null, 2))
					
		  },
	error : function(error) {
	console.log("The error is " + error);
	}
});
}


function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}


	function getMappingActiveRecord(listActivePort,listPassivePort) {
	    if (listActivePort.length > listPassivePort.length && listPassivePort.length != 0) {

	        // Iterate over active ports
	        for (var i = 0; i < listActivePort.length; i++) {
	            var matchFound = false; // Flag to indicate if a match is found

	            // Iterate over passive ports
	            for (var j = 0; j < listPassivePort.length; j++) {
	                // Check for match by MAC address
	                if (listActivePort[i][0] === listPassivePort[j][1] && listActivePort[i][0]!=null && listActivePort[i][0]!="null" && listActivePort[i][0]!="" && listActivePort[i][0]!="0") {
	                    matchActiveRecord(listActivePort[i], listPassivePort[j], "activePassive");
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }

	                // Check for match by Serial Number
	                if (!matchFound && listActivePort[i][1] === listPassivePort[j][2] && listActivePort[i][1]!=null && listActivePort[i][1]!="null" && listActivePort[i][1]!="" && listActivePort[i][1]!="0") {
	                    matchActiveRecord(listActivePort[i], listPassivePort[j], "activePassive");
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }

	                // Check for match by Port Address
	                if (!matchFound && listActivePort[i][2] === listPassivePort[j][3] && listActivePort[i][2]!=null && listActivePort[i][2]!="null" && listActivePort[i][2]!="" && listActivePort[i][2]!="0") {
	                    matchActiveRecord(listActivePort[i], listPassivePort[j], "activePassive");
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }
	            }

	            // If no match is found after checking all passive ports
	            if (!matchFound) {
	                matchActiveRecord(listActivePort[i], [], "noPassive");
	            }
	        }

	        // Handle records in passive list that were not matched
	        for (var j = 0; j < listPassivePort.length; j++) {
	            var matchFound = false; // Flag to indicate if a match is found

	            for (var i = 0; i < listActivePort.length; i++) {
	                if ((listActivePort[i][0] === listPassivePort[j][1] && listActivePort[i][0]!=null && listActivePort[i][0]!="null" && listActivePort[i][0]!="" && listActivePort[i][0]!="0") ||
	                    (listActivePort[i][1] === listPassivePort[j][2] && listActivePort[i][1]!=null && listActivePort[i][1]!="null" && listActivePort[i][1]!="" && listActivePort[i][1]!="0")||
	                    (listActivePort[i][2] === listPassivePort[j][3] && listActivePort[i][2]!=null && listActivePort[i][2]!="null" && listActivePort[i][2]!="" && listActivePort[i][2]!="0")) {
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }
	            }

	            // If no match is found after checking all active ports
	            if (!matchFound) {
	                matchActiveRecord([], listPassivePort[j], "noActive");
	            }
	        }
	    } else if (listPassivePort.length >= listActivePort.length && listActivePort.length != 0) {

	        // Iterate over passive ports
	        for (var i = 0; i < listPassivePort.length; i++) {
	            var matchFound = false; // Flag to indicate if a match is found

	            // Iterate over active ports
	            for (var j = 0; j < listActivePort.length; j++) {
	                // Check for match by MAC address
	                if (listActivePort[j][0] === listPassivePort[i][1] && listActivePort[j][0]!=null && listActivePort[j][0]!="null" && listActivePort[j][0]!="" && listActivePort[j][0]!="0") {
	                    matchActiveRecord(listActivePort[j], listPassivePort[i], "activePassive");
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }

	                // Check for match by Serial Number
	                if (!matchFound && listActivePort[j][1] === listPassivePort[i][2] && listActivePort[j][1]!=null && listActivePort[j][1]!="null" && listActivePort[j][1]!="" && listActivePort[j][1]!="0") {
	                    matchActiveRecord(listActivePort[j], listPassivePort[i], "activePassive");
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }

	                // Check for match by Port Address
	                if (!matchFound && listActivePort[j][2] === listPassivePort[i][3] && listActivePort[j][2]!=null && listActivePort[j][2]!="null" && listActivePort[j][2]!="" && listActivePort[j][2]!="0") {
	                    matchActiveRecord(listActivePort[j], listPassivePort[i], "activePassive");
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }
	            }

	            // If no match is found after checking all active ports
	            if (!matchFound) {
	                matchActiveRecord([], listPassivePort[i], "noActive");
	            }
	        }

	        // Handle records in active list that were not matched
	        for (var j = 0; j < listActivePort.length; j++) {
	            var matchFound = false; // Flag to indicate if a match is found

	            for (var i = 0; i < listPassivePort.length; i++) {
	                if ((listActivePort[j][0] === listPassivePort[i][1] && listActivePort[j][0]!=null && listActivePort[j][0]!="null" && listActivePort[j][0]!="" && listActivePort[j][0]!="0")||
	                    (listActivePort[j][1] === listPassivePort[i][2] && listActivePort[j][1]!=null && listActivePort[j][1]!="null" && listActivePort[j][1]!="" && listActivePort[j][1]!="0")||
	                    (listActivePort[j][2] === listPassivePort[i][3] && listActivePort[j][2]!=null && listActivePort[j][2]!="null" && listActivePort[j][2]!="" && listActivePort[j][2]!="0")) {
	                    matchFound = true; // Set flag to true when match is found
	                    break; // Exit the inner loop
	                }
	            }

	            // If no match is found after checking all passive ports
	            if (!matchFound) {
	                matchActiveRecord(listActivePort[j], [], "noPassive");
	            }
	        }
	    } else if (listActivePort.length > 0 && listPassivePort.length === 0) {
	        for (var i = 0; i < listActivePort.length; i++) {
	            matchActiveRecord(listActivePort[i], [], "noPassive");
	        }
	    } else if (listPassivePort.length > 0 && listActivePort.length === 0) {
	        for (var i = 0; i < listPassivePort.length; i++) {
	            matchActiveRecord([], listPassivePort[i], "noActive");
	        }
	    }
	}
	
	function matchActiveRecord(activeRow, passiveRow, condition) {
  	    if (condition === "activePassive") {
  	        allPortList.push({
  	            "port_Mapping_ID": passiveRow[0],
  	            "activeMACAddress": activeRow[0],
  	            "activeSerialNb": activeRow[1],
  	            "activePortAddress": activeRow[2],
  	            "activePortStatus": activeRow[3],
  	            "passiveMACAddress": passiveRow[1],
  	            "passiveSerialNb": passiveRow[2],
  	            "passivePortAddress": passiveRow[3],
  	            "passivePortStatus": passiveRow[4],
  	            "portNb": passiveRow[5],
  	            "recordType": "active",
  	            "locationType": passiveRow[7],
  	            "locationID": passiveRow[8],
  	            "locationName": passiveRow[9],
  	            "wareID": passiveRow[10],
  	            "cableID": passiveRow[11],
  	            "cableName": passiveRow[12],
  	            "txStrandNb": passiveRow[13],
  	            "txStrandColor": passiveRow[17],
  	            "rxStrandNb": passiveRow[15],
  	            "rxStrandColor": passiveRow[19],
  	            "txTubeNb": passiveRow[14],
  	            "txTubeColor": passiveRow[18],
  	            "rxTubeNb": passiveRow[16],
  	            "rxTubeColor": passiveRow[20],
  	            "cableLength": passiveRow[21],
  	            "cableType": passiveRow[22],
  	         	"createdDate": passiveRow[23],
  	     		"lastModifiedDate": passiveRow[24]
  	        });
  	    } else if (condition === "noPassive") {
  	        allPortList.push({
  	            "port_Mapping_ID": "",
  	            "activeMACAddress": activeRow[0],
  	            "activeSerialNb": activeRow[1],
  	            "activePortAddress": activeRow[2],
  	            "activePortStatus": activeRow[3],
  	            "passiveMACAddress": "",
  	            "passiveSerialNb": "",
  	            "passivePortAddress": "",
  	            "passivePortStatus": "",
  	            "portNb": "",
  	            "recordType": "active",
  	            "locationType": "",
  	            "locationID": "",
  	            "locationName": "",
  	            "wareID": "",
  	            "cableID": "",
  	            "cableName": "",
  	            "txStrandNb": "",
  	            "txStrandColor": "",
  	            "rxStrandNb": "",
  	            "rxStrandColor": "",
  	            "txTubeNb": "",
  	            "txTubeColor": "",
  	            "rxTubeNb": "",
  	            "rxTubeColor": "",
  	            "cableLength": "",
  	            "cableType": "",
  	         "createdDate":"",
	     		"lastModifiedDate": ""
  	        });
  	    } else if (condition === "noActive") {
  	        allPortList.push({
  	            "port_Mapping_ID": passiveRow[0],
  	            "activeMACAddress": "",
  	            "activeSerialNb": "",
  	            "activePortAddress": "",
  	            "activePortStatus": "",
  	            "passiveMACAddress": passiveRow[1],
  	            "passiveSerialNb": passiveRow[2],
  	            "passivePortAddress": passiveRow[3],
  	            "passivePortStatus": passiveRow[4],
  	            "portNb": passiveRow[5],
  	            "recordType": "passive",
  	            "locationType": passiveRow[7],
  	            "locationID": passiveRow[8],
  	            "locationName": passiveRow[9],
  	            "wareID": passiveRow[10],
  	            "cableID": passiveRow[11],
  	            "cableName": passiveRow[12],
  	            "txStrandNb": passiveRow[13],
  	            "txStrandColor": passiveRow[17],
  	            "rxStrandNb": passiveRow[15],
  	            "rxStrandColor": passiveRow[19],
  	            "txTubeNb": passiveRow[14],
  	            "txTubeColor": passiveRow[18],
  	            "rxTubeNb": passiveRow[16],
  	            "rxTubeColor": passiveRow[20],
  	            "cableLength": passiveRow[21],
  	            "cableType": passiveRow[22],
  	         	"createdDate": passiveRow[23],
	     			"lastModifiedDate": passiveRow[24]
  	        });
  	    }
  	}
	
	function sortMappingAllPortListByPortNb() {
	    allPortList.sort((a, b) => {
	        // Convert the port numbers to numbers for correct numerical comparison
	        const portNbA = parseInt(a.portNb, 10);
	        const portNbB = parseInt(b.portNb, 10);

	        // If either portNb is NaN, handle it accordingly
	        if (isNaN(portNbA) && isNaN(portNbB)) return 0; // Both are NaN, so they're equal
	        if (isNaN(portNbA)) return 1; // NaN should come after a valid number
	        if (isNaN(portNbB)) return -1; // Valid number should come before NaN

	        // Compare the two port numbers
	        return portNbA - portNbB;
	    });
	}
	
	const getPortById = (id) => {
	    return allPortList.find(port => port.port_Mapping_ID === id);
	};
	
	function viewDiagram(allPortList,nodeID) {
		 var dpNodeWidth=$("#node_MappingModal").width();
		   
		   $("#node_tabContentPortsMap").empty();	
		   $("#node_MappingModal").modal('show');
		   						
	if(allPortList.length > 0){
	
		$("#node_TitleId").text("Node Port Mapping: "+nodeID);
			  
		var nodePortNumCols=12;
		var nodePortNumRows = Math.ceil(allPortList.length / 12);
		

		  	
		var minWidth="800";									
		var width = (nodePortNumCols+2.2)*103.5, height =(nodePortNumRows+2)*100;
		var totWidth=width;
		
		if(totWidth>1366.1999999999998){
			totWidth-=110;
			}
			
			
		$("#contentNodeMappingModal" ).width(totWidth);
	    dpNodeWidth=$("#contentNodeMappingModal" ).width();
			
		nodeSvg="<svg id='mysvgNode' width='"+width+"px' height='"+height+"px' min-width='"+minWidth+"px'></svg>";
		$("#node_tabContentPortsMap").append(nodeSvg);
		
		////// DRAWIN BOARDS BORDERS ///////
		var line= makeSVGNode('line', {x1: 50, y1: 50, x2:50,y2:100, stroke: 'red', 'stroke-width': 1, fill: 'red'});
	    line= makeSVGNode('line', {x1: 50, y1: 50, x2:100,y2:50, stroke: 'red', 'stroke-width': 1, fill: 'red'});
	    
		line= makeSVGNode('line', {x1: 100, y1: 100, x2:(nodePortNumCols+1.2)*100,y2:100, stroke: 'red', 'stroke-width': 1, fill: 'red'});
		document.getElementById('mysvgNode').appendChild(line);
		
		line= makeSVGNode('line', {x1: 100, y1: 100, x2:100,y2:(nodePortNumRows+2)*100, stroke: 'red', 'stroke-width': 1, fill: 'red'});
		document.getElementById('mysvgNode').appendChild(line);
		
		line= makeSVGNode('line', {x1: (nodePortNumCols+1.2)*100, y1: 100, x2:(nodePortNumCols+1.2)*100,y2:(nodePortNumRows+2)*100, stroke: 'red', 'stroke-width': 1, fill: 'red'});
		document.getElementById('mysvgNode').appendChild(line);
				
		line= makeSVGNode('line', {x1: 100, y1: (nodePortNumRows+2)*100, x2:(nodePortNumCols+1.2)*100,y2:(nodePortNumRows+2)*100, stroke: 'red', 'stroke-width': 1, fill: 'red'});
		document.getElementById('mysvgNode').appendChild(line);
		
		
														
//////DRAWING JUNCTION MAPPING RELATIVE TO EACh ROW AND COLUMN ///////

		for(i=0;i<allPortList.length;i++){
									  
		var colNum=0;
		var rowNum=0;
		
		
		//getting port row 
		var portNb =parseFloat(allPortList[i].portNb)
		rowNum =Math.ceil(portNb / 12)
		
		// Calculate colNum dynamically based on the value
		if (portNb >= 1) {
			colNum = ((portNb - 1) % 12) + 1; // Map 1-12, 13-24, 25-36, etc. to columns 1-12
		} 
		
		
		svgNodeNS = "http://www.w3.org/2000/svg";
		
		var newText = document.createElementNS(svgNodeNS,"text");
		newText.setAttributeNS(null,"x",20);     
		newText.setAttributeNS(null,"y",30);  
		newText.setAttributeNS(null,"font-size","15");
		newText.setAttributeNS(null,"stroke","#00757C");
		
		var textNode = document.createTextNode("Number of Ports : "+allPortList.length);
		newText.appendChild(textNode);
		document.getElementById("mysvgNode").appendChild(newText);
							
		//Drawing green circle to represent the junction
		
		var circle = document.createElementNS(svgNodeNS, "circle");
		circle.setAttributeNS(null, "cx", (colNum*100)+100);
		circle.setAttributeNS(null, "cy", (rowNum*100)+80);
		circle.setAttributeNS(null, "r",  13);
		circle.setAttributeNS(null, "fill", "green");
		circle.setAttributeNS(null,"class","nodeImage");
		circle.setAttributeNS(null,"id","U-"+allPortList[i].port_Mapping_ID);
		circle.setAttributeNS(null,"cursor","pointer");	
		document.getElementById("mysvgNode").appendChild(circle);
		
		//adding port number inside port
		// Create the text element
		var text = document.createElementNS(svgNodeNS, "text");
		text.setAttributeNS(null, "x", (colNum * 100) + 100); // Center horizontally
		text.setAttributeNS(null, "y", (rowNum * 100) + 80 + 5); // Center vertically (adjust as needed)
		text.setAttributeNS(null, "fill", "white"); // Color of the text
		text.setAttributeNS(null, "font-size", "10"); // Font size
		text.setAttributeNS(null, "text-anchor", "middle"); // Center the text horizontally

		// Set the number to be displayed inside the circle
		text.textContent = allPortList[i].portNb; 
		document.getElementById("mysvgNode").appendChild(text);
		
		//Drawing line inside the junction green circle 
		var upLine = document.createElementNS(svgNodeNS, "line");
		upLine.setAttributeNS(null, "x1", (colNum*100)+100);
		upLine.setAttributeNS(null, "y1", (rowNum*100)+100);
		upLine.setAttributeNS(null, "x2", (colNum*100)+100);
		upLine.setAttributeNS(null, "y2", (rowNum*100)+60);					
		upLine.setAttributeNS(null, "stroke", "black");
		upLine.setAttributeNS(null, "stroke-width", "2");
		//document.getElementById("mysvgNode").appendChild(upLine);
		
		
			//Show the strand id of side A
		var newTextNode = document.createElementNS(svgNodeNS,"text");
		newTextNode.setAttributeNS(null,"x",(colNum*100)+55);     
		newTextNode.setAttributeNS(null,"y",(rowNum*100)+50); 
		newTextNode.setAttributeNS(null,"class","text");
		newTextNode.setAttributeNS(null,"font-size","9");
		newTextNode.setAttributeNS(null,"stroke","#00757C");	
		newTextNode.setAttributeNS(null,"cursor","pointer");	
		newTextNode.setAttributeNS(null,"class","nodePortMapping");
		newTextNode.setAttributeNS(null,"id","nodePort-"+ allPortList[i].port_Mapping_ID);
		
		var textNodePort;
		if(allPortList[i].activePortAddress && allPortList[i].activePortAddress !== "null") {
			textNodePort = document.createTextNode(allPortList[i].activePortAddress);
		}
		else {
			textNodePort = document.createTextNode(allPortList[i].passivePortAddress);
		}
		// textNodePort = document.createTextNode(allPortList[i].locationName);
		newTextNode.appendChild(textNodePort);
		document.getElementById("mysvgNode").appendChild(newTextNode);
			
		}			
		
		$('.nodePortMapping').click(function() {
		
		var nodePortMappingId=$(this).attr('id').split("-");
		nodePortMappingId=nodePortMappingId[1];
			
		$("#node_MappingModal").modal('hide');
		});	
		
					
		$('.nodeImage').click(function() {
		
		var nodePortMappingId=$(this).attr('id').split("-");
		nodePortMappingId=nodePortMappingId[1];
		
		const targetPort = getPortById(nodePortMappingId);
		console.log("iddssdd "+targetPort.recordType)
		
		
		$("#recordTypeLabel").html("<b style='white-space:nowrap;'>Record Type: </b>"+targetPort.recordType);
		
		if(targetPort.activeMACAddress && targetPort.activeMACAddress !== "null") {
			$("#macAddrLabel").html("<b style='white-space:nowrap;' >MAC Address: </b>"+targetPort.activeMACAddress);
		}
		else {
			$("#macAddrLabel").html("<b style='white-space:nowrap;' >MAC Address: </b>"+targetPort.passiveMACAddress);
		}
		
		if(targetPort.activeSerialNb && targetPort.activeSerialNb !== "null") {
			$("#serialNbLabel").html("<b style='white-space:nowrap;' >Serial Number: </b>"+targetPort.activeSerialNb);
		}
		else {
			$("#serialNbLabel").html("<b style='white-space:nowrap;' >Serial Number: </b>"+targetPort.passiveSerialNb);
		}
		
		if(targetPort.activePortAddress && targetPort.activePortAddress !== "null") {
			$("#portAddrLabel").html("<b style='white-space:nowrap;' >Port Address: </b>"+targetPort.activePortAddress);
		}
		else {
			$("#portAddrLabel").html("<b style='white-space:nowrap;' >Port Address: </b>"+targetPort.passivePortAddress);
		}
		
		
		if(targetPort.activePortStatus && targetPort.activePortStatus !== "null") {
			$("#portStatusLabel").html("<b style='white-space:nowrap;' >Port Status: </b>"+targetPort.activePortStatus);
		}
		else {
			$("#portStatusLabel").html("<b style='white-space:nowrap;' >Port Status: </b>"+targetPort.passivePortStatus);
		}
		
		$("#locationTypeLabel").html("<b style='white-space:nowrap;' >Location Type: </b>"+targetPort.locationType);
		$("#locationIDLabel").html("<b style='white-space:nowrap;' >Location ID: </b>"+targetPort.locationID);
		$("#locationNameLabel").html("<b style='white-space:nowrap;' >Location Name: </b>"+targetPort.locationName);	
		
		$("#WareIDLabel").html("<b style='white-space:nowrap;'>Warehous ID: </b>"+targetPort.wareID);
		$("#cableTypeLabel").html("<b style='white-space:nowrap;' >Cable Type: </b>"+targetPort.cableType);
		$("#txStrandNbLabel").html("<b style='white-space:nowrap;' >TX Strand NB: </b>"+targetPort.txStrandNb);
		$("#txStrandColorLabel").html("<b style='white-space:nowrap;' >TX Strand Color: </b>"+targetPort.txStrandColor);
		$("#rxStrandNbLabel").html("<b style='white-space:nowrap;' >RX Strand NB: </b>"+targetPort.rxStrandNb);
		$("#rxStrandColorLabel").html("<b style='white-space:nowrap;' >RX Strand Color: </b>"+targetPort.rxStrandColor);
		$("#txTubeNbLabel").html("<b style='white-space:nowrap;' >TX Tube NB: </b>"+targetPort.txTubeNb);
		$("#txTubeColorLabel").html("<b style='white-space:nowrap;' >TX Tube Color: </b>"+targetPort.txTubeColor);
		$("#rxTubeNbLabel").html("<b style='white-space:nowrap;' >RX Tube NB: </b>"+targetPort.rxTubeNb);
		$("#rxTubeColorLabel").html("<b style='white-space:nowrap;' >RX Tube Color: </b>"+targetPort.rxTubeColor);
		
		$("#cableIDLabel").html("<b style='white-space:nowrap;'>Cable ID: </b>"+targetPort.cableID);
		$("#cableNameLabel").html("<b style='white-space:nowrap;' >Cable Name: </b>"+targetPort.cableName);
		$("#cableLengthLabel").html("<b style='white-space:nowrap;' >Cable Length: </b>"+targetPort.cableLength);
		
		$("#AssignedNode_TitleId").text("Node : "+nodeID);	
							
		$("#AssignedNodePortsModal").modal('show');
						
		});	

				
		
		}
		else{
			var text="<p style='color: #00757C;font-weight:bold'> No data available concerning  "+nodeID+"  Node !</br> Mapping will be filled after insertion of node's corresponding data fields.</p>"
			 $("#node_tabContentPortsMap").append(text);
			 $("#node_TitleId").text("Node Port Mapping of "+nodeID+" ");
			
			totWidth=700;
			$("#contentNodeMappingModal" ).width(totWidth);
			 
		}
		
		
	}
	
	function makeSVGNode(tag, attrs,val) { //used in View Junction Mapping
	var el= document.createElementNS('http://www.w3.org/2000/svg', tag);
	for (var k in attrs)
	el.setAttribute(k, attrs[k]);
	$(el).val(val);
	return el;
}