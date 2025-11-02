function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

cx = getContextPath();

$("#saveButton").click(  function() {
	
});


function enableMain() {
	$("#procStat").val('Enabled');
	$("#formStatus").text("Not Saved");
	$('.dot').css({"background-color" : "orange"});
}


function disableMain() {
	$("#procStat").val('Disabled');
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