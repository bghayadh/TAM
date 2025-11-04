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
/*			"dictParameter": dict,
			"slctDelOrd":slctDelOrd, */
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