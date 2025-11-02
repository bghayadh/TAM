function login() {
	console.log("Welcome to login method");	
    if (!validation()) {
        return;  // exit login() immediately
    }
    	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeLogin',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.responseBody.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);            	
            }
            else {
            	$("#responseBody").val("");
            	$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
        			$("#responseStatusCodeValue").val(data.reason);
            		$("#responseStatus").val(data.status);
                }            	
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
	        		$("#responseStatusCodeValue").val(data.reason);
    	        	$("#responseStatus").val(data.status);
                }
            }
        },		
        error: function(result) {
            alert("Login ajax failed, there is error: ", result);            
        }
    });
}


function newToken() {
	console.log("Welcome to get new token method");	
    if (!validation()) {
        return;  // exit login() immediately
    }
    	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeNewToken',
        data: {            
			"token" : $("#token").val(),
			"ipAddress": $("#ipAddress").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.responseBody.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);            	
            }
            else {
            	$("#responseBody").val("");
            	$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
        			$("#responseStatusCodeValue").val(data.reason);
            		$("#responseStatus").val(data.status);
                }            	
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
	        		$("#responseStatusCodeValue").val(data.reason);
    	        	$("#responseStatus").val(data.status);
                }
            }
        },		
        error: function(result) {
            alert("New Token ajax failed, there is error: ", result);            
        }
    });
}

function getRack() {
	console.log("Welcome to getRack method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cs+'/CommScopeGetRack',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),			
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	if (data.responseBody.hasOwnProperty("rackList")) {
            		if (data.responseBody.rackList.length > 0)
            			$("#rackID").val(data.responseBody.rackList[0].id);
            	}
            	else if (data.responseBody.hasOwnProperty("controllerList")) {
            		if (data.responseBody.controllerList.length > 0)
            			$("#rackID").val(data.responseBody.controllerList[0].id);
                }
            	else {
            		$("#rackID").val("");
                }            	
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
            }
        },
        error: function(result) {
            alert("Get Rack ajax failed, there is error: ", result);            
        }
    });
}



function controller() {
	console.log("Welcome to controller method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeControllerX',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),			
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.networkManagerId);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
	        		$("#responseStatusCodeValue").val(data.reason);
    	        	$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
	        		$("#responseStatusCodeValue").val(data.reason);
    	        	$("#responseStatus").val(data.status);
                }
*/
            }
        },
        error: function(result) {
            alert("ControllerX ajax failed, there is error: ", result);            
        }
    });
}

function getPanel () {
	console.log("Welcome to getPanel method");
	
    if (!validation({"#rackID":"Rack ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeGetPanel',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
	            	$("#responseStatusCodeValue").val(data.responseCodeValue);
    	        	$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
            		$("#responseStatusCodeValue").val(data.responseCodeValue);
            		$("#responseStatus").val(data.status);
                }
*/
            }
        },
        error: function(result) {
            alert("Get Panel ajax failed, there is error: ", result);            
        }
    });
}


function patches () {
	console.log("Welcome to patches method");
	
    if (!validation({"#rackID":"Rack ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopePatches',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
            		$("#responseStatusCodeValue").val(data.responseCodeValue);
            		$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
            		$("#responseStatusCodeValue").val(data.responseCodeValue);
            		$("#responseStatus").val(data.status);                	
                }
*/                
            }
        },
        error: function(result) {
            alert("Patches ajax failed, there is error: ", result);            
        }
    });
}


function incompletePatches () {
	console.log("Welcome to incomplete patches method");
	
    if (!validation({"#rackID":"Rack ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeIncompletePatches',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
            		$("#responseStatusCodeValue").val(data.responseCodeValue);
            		$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
            		$("#responseStatusCodeValue").val(data.responseCodeValue);
            		$("#responseStatus").val(data.status);
                }
*/                
            }
        },
        error: function(result) {
            alert("Incomplete Patches ajax failed, there is error: ", result);            
        }
    });
}

function getNetworkInterface () {
	console.log("Welcome to getNetworkInterface method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeGetNetworkInterface',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.networkManagerId);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
        		
            	responseFailed(data);
/*
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
	        		$("#responseStatusCodeValue").val(data.responseCodeValue);
    	    		$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
	        		$("#responseStatusCodeValue").val(data.responseCodeValue);
    	    		$("#responseStatus").val(data.status);
                }
*/                
            }
        },
        error: function(result) {
            alert("Get Network Interface ajax failed, there is error: ", result);            
        }
    });
}


function portStatus () {
	console.log("Welcome to portStatus patches method");
	
    if (!validation({"#rackID":"Rack ID", "#kitID" :"Kit ID", "#moduleID" : "Module ID", "#portID" : "Port ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopePortStatus',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"rackID" : $("#rackID").val(),
			"kitID" : $("#kitID").val(),
			"moduleID" : $("#moduleID").val(),
			"portID" : $("#portID").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#rackID").val(data.responseBody.rackId);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
	        		$("#responseStatusCodeValue").val(data.responseCodeValue);
    	    		$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
	        		$("#responseStatusCodeValue").val(data.responseCodeValue);
    	    		$("#responseStatus").val(data.status);
                }
*/                
            }
        },
        error: function(result) {
            alert("Port Status ajax failed, there is error: ", result);            
        }
    });
}


function eventNote () {
	console.log("Welcome to eventNote method");
	
    if (!validation({"#eventID" : "Event ID", "#timeout" : "Timeout"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeEventNote',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"eventID" : $("#eventID").val(),
			"timeout" : $("#timeout").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	        		
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
*/            	
            }
        },
        error: function(result) {
            alert("Event Note ajax failed, there is error: ", result);            
        }
    });
}


function setCurrentDateTime () {
	console.log("Welcome to setCurrentDateTime method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeSetCurrentDateTime',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");
            	$("#returnedControllerDateTime").text(data.controllerTime);
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
*/            	
            }
        },
        error: function(result) {
            alert("Set Current Date Time ajax failed, there is error: ", result);            
        }
    });
}


function setDateTime () {
	console.log("Welcome to setDateTime method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }

    let rawDate = $("#selectDateTime").val(); // "09/09/2025 01:28:45 AM"
    let momentDate = moment(rawDate, "MM/DD/YYYY hh:mm:ss A");    
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeSetDateTime',
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"dateTime" : momentDate.format("YYYY-MM-DDTHH:mm:ss"),
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");
            	$("#returnedControllerDateTime").text(data.controllerTime);
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
*/            	
            }
        },
        error: function(result) {
            alert("Set Date Time ajax failed, there is error: ", result);            
        }
    });
}

function generateWO() {
	console.log("Welcome to generateWO method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }

    var token =  $('input[name="csrfToken"]').attr('value');
    var woDetails = [];
 	var task = {};
 	var type = $("#woType").val();
 	console.log("The type is " +type);
 
 	task = {
			workOrderTaskId: parseInt($("#woTaskID").val(), 10),
 	 	 	info: $("#woInfo").val(),
 	 	 	action: $("#woAction").val(),
 	 	 	type: $("#woType").val(),
 	 	 	sRackId: $("#wo_sRackId").val(),
 	 	 	sKitId: $("#wo_sKitId").val(),
 	 	 	sModule: $("#wo_sModule").val(),
 	 	 	sPort: parseInt($("#wo_sPort").val(), 10), 	 	
 	 	};

 	if (type.includes("-Equipment")) {
 	 	// Build eEquipment list
 	 	var eEquip = [];
 	 	eEquip.push({ equipmentInfo: $("#equipInfo1").val() });
 	 	eEquip.push({ equipmentInfo: $("#equipInfo2").val() });
 	 	eEquip.push({ equipmentInfo: $("#equipInfo3").val() }); 	 	 	
 	 	task.eEquipment = eEquip;
 	}
 	else {
 		task.eRackId = $("#wo_eRackId").val();
 		task.eKitId = $("#wo_eKitId").val();
// 		task.eModule =;
 		task.ePort = parseInt($("#wo_ePort").val(), 10);
 	}

 	woDetails.push(task);
 	console.log(woDetails);
	
	$.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeGenWO', // URL of Generating WO
        dataType: "json",
        headers: {
            'X-CSRFToken': token 
        },
        data: JSON.stringify({
            username: $("#username").val(),
            password: $("#password").val(),
            ipAddress: $("#ipAddress").val(),
            woDetails: woDetails
        }),
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(data.responseBody);
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);            	
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	        		
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
*/            	
            }
        },
        error: function(result) {
            alert("Generate Work Order ajax failed, there is error: ", result);            
        }
    });
}

function getWO() {
	console.log("Welcome to getWO() method");
	
    if (!validation({"#woTaskID": "Work Order Task ID"})) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeGetWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"workOrderTaskId": parseInt($("#woTaskID").val(), 10)
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);            	
            	$("#token").val(data.accessToken);
            	$("#woInfo").val(data.responseBody.info);
            	$("#woAction").val(data.responseBody.action);
            	$("#woType").val(data.responseBody.type);
     	 	 	$("#wo_sRackId").val(data.responseBody.sRackId);
     	 	 	$("#wo_sKitId").val(data.responseBody.sKitId);
     	 	 	$("#wo_sModule").val(data.responseBody.sModule);
     	 	 	$("#wo_sPort").val(data.responseBody.sPort);
     	 	  	if (data.responseBody.eEquipment && data.responseBody.eEquipment.length > 0) {
     	 	  		$("#equipInfo1").val(data.responseBody.eEquipment[0].equipmentInfo);
     	 	  		$("#equipInfo2").val(data.responseBody.eEquipment[1].equipmentInfo);
     	 	  		$("#equipInfo3").val(data.responseBody.eEquipment[2].equipmentInfo);
     	 	  	}            	
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason")) {
            		$("#responseStatusCode").val(data.reason);
            		$("#responseStatusCodeValue").val(data.reason);
                	$("#responseStatus").val(data.status);
                }
                else if (data.hasOwnProperty("responseCode")) {
                	$("#responseStatusCode").val(data.responseCode);
                	$("#responseStatusCodeValue").val(data.responseCodeValue);
                	$("#responseStatus").val(data.status);            	
                }
                else if (data.hasOwnProperty("message")) {
                	$("#responseStatusCode").val(data.message);
            		$("#responseStatusCodeValue").val(data.responseCodeValue);
            		$("#responseStatus").val(data.status);
                }
*/                
            }
        },
        error: function(result) {
            alert("Get Work Order ajax failed, there is error: ", result);            
        }
    });
}

function listWO() {
	console.log("Welcome to listWO() method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
		
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeListWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val()
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val(JSON.stringify(data.responseBody, null, 4));            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
*/            	
            }
        },
        error: function(result) {
            alert("List Work Order ajax failed, there is error: ", result);            
        }
    });
}

function delWO() {
	console.log("Welcome to delWO() method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeDelWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val(),
			"workOrderTaskId": parseInt($("#woTaskID").val(), 10)
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	responseFailed(data);
/*            	
            	else
            		$("#token").val("");
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
*/            	
            }
        },
        error: function(result) {
            alert("Delete Work Order ajax failed, there is error: ", result);            
        }
    });
}

function delAllWO() {
	console.log("Welcome to delAllWO() method");
	
    if (!validation()) {
        return;  // exit controller() immediately
    }
	
	$.ajax({
        type: "GET",
        contentType: "application/json; charset=utf-8",
        url: cx+'/CommScopeDelAllWO',        										 
        data: {            
			"username" : $("#username").val(),
			"password" : $("#password").val(),
			"ipAddress": $("#ipAddress").val()
        },
        dataType: "json",
        success: function (data) {
            if (data.status == "Success") {
            	$("#responseBody").val("");            	
            	$("#responseStatusCode").val(data.responseCode);
            	console.log("responseCode is " ,data.responseCode);
            	$("#token").val(data.accessToken);
            	$("#responseStatusCodeValue").val(data.responseCodeValue);
            	$("#responseStatus").val(data.status);
            }
            else {
            	$("#responseBody").val("");
            	$("#rackID").val("");
            	if (data.hasOwnProperty("accessToken"))
            		$("#token").val(data.accessToken);
            	else
            		$("#token").val("");
            	responseFailed(data);
/*            	
                if (data.hasOwnProperty("reason"))
            		$("#responseStatusCode").val(data.reason);
                else if (data.hasOwnProperty("responseCode"))
                	$("#responseStatusCode").val(data.responseCode);
                else if (data.hasOwnProperty("message"))
                	$("#responseStatusCode").val(data.message);
*/
            }
        },
        error: function(result) {
            alert("Delete All Work Orders ajax failed, there is error: ", result);            
        }
    });
}

function responseFailed(data) {
    if (data.hasOwnProperty("reason"))
		$("#responseStatusCode").val(data.reason);
    else if (data.hasOwnProperty("responseCode")) {
    	$("#responseStatusCode").val(data.responseCode);
		$("#responseStatusCodeValue").val(data.responseCodeValue);
		$("#responseStatus").val(data.status);
    }
    else if (data.hasOwnProperty("message")) {
    	$("#responseStatusCode").val(data.message);
		$("#responseStatusCodeValue").val(data.responseCodeValue);
		$("#responseStatus").val(data.status);
    }	
}