function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

cx = getContextPath();

/*$("#saveButton").click(  function() {
	
});*/


function enableMain() {
	$("#docStatus").val('Enabled');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}


function disableMain() {
	$("#docStatus").val('Disabled');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}


$("input").change(function() {
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
});


$('#dateTimePickerID').datetimepicker({
    format: 'MM/DD/YYYY hh:mm:ss A'   // include seconds here
});

function formSave() {	
	var token =  $('input[name="csrfToken"]').attr('value');
	console.log("formSave");
	dict = [];
	getBoq(dict);
	console.log("dict is " , dict);
	$.ajax({
		type : "POST",
		url : cx+"/CommScopeFormSave",
		dataType : "json",
	    headers: {
	     'X-CSRFToken': token 
	 },
		data : {
		    "docStatus": $("#docStatus").val(), //'${docStatus}',
			"createDate" : $("#createDate").val(),
			"lstmodifyDate" : $("#lstmodifyDate").val(),
			"linkName" : "CommScope",			
			"dictParameterProcess": dict,
			"slctDel":slctDel,
		},
		success : function(data) {			
			param =cx+"/Process?ID=CommScope&NavAction=2";
			location.replace(param);			
		},
		error : function(error) {
			console.log("The error is " + error);
		}
	});
}

function getBoq (boqDict) {	
	$("#boqTable > tbody").find('input[name="record"]').each(function(){
		const $row = $(this).closest("tr");
		boqDict.push({
			"procName" : $row.find('td[name="procName"] input').val(),
			"procStatus" : $row.find('td[name="procStatus"] input[type="checkbox"]').next('label').text(),
			"procClassName" : $row.find('td[name="procClassName"] input').val(),
			"procCronExpr" : $row.find('td[name="procCronExpr"] input').val(),
			"procID" : $row.find('td[name="procID"] input').val()
		});
	});
	console.log("the dict array is " , dict);
}  // end of getBoq


function formLoad(boqArray) {
	var markup = "";
	for (i = 0; i < boqArray.length; i++){
		console.log("operation name is " , boqArray[i].operationName);
		rowParams = {"name" : boqArray[i].operationName, "status" : boqArray[i].status, "className" : boqArray[i].className, "cronExpr": boqArray[i].cronExpression, "procID" : boqArray[i].id};	
		markup += htmlBOQRowInsertion(rowParams);
	}
	$("#boqTable > tbody").append(markup);
	$('table#boqTable tr:eq('+(boqArray.length)+') td:nth-child(2) input').focus();
	updateContainerHeight();
}