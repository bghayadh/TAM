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