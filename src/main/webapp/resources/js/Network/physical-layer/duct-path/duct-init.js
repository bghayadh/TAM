function initDuctEvents() {
	ductAuxTabEvents();
    $("#generateDuctFromPath").on("click", function() {
        console.log("Welcome to generate Duct from Path");
        $("#ductGenCableId").val("");
        $("#ductGenCableName").val("");
        $("#appendToDuctAuxPoints").prop("checked", false);
        $("#generateFromTrench").prop("checked", false);
        $("#includeSourceDestination").prop("checked", false);
        $("#generateDuctModal").modal("show");
    });	
	pathGenCableIdAutoComplete("ductGenCableId", "ductGenCableName");
	pathGenCableNameAutoComplete("ductGenCableId", "ductGenCableName");
	
	$("#confirmGenerateDuct").on('click', function() {
	    console.log("Welcome generate duct from path confirm");
		if (!$("#generateFromTrench").is(":checked"))
		    if ($("#ductGenCableId").val().trim() == "" || $("#ductGenCableName").val().trim() == "") {
		        alert("Please select the fiber path to generate from it");
	    	    return;
	    	}
	    console.log("Just before send ajax request");
	    var token = $('input[name="csrfToken"]').attr('value');
	    $.ajax({
	        type: "POST",
	        headers: {
	            'X-CSRFToken': token
	        },
	        url: getContext() + "/getPathForDuctGen",
	        data: {
	            ID: $("#ductGenCableId").val(),
				fromTrench: $("#generateFromTrench").is(":checked"),
				selected: selectedDuctContext,
	            append: $("#appendToDuctAuxPoints").is(":checked"),
				includeSrcDest: $("#includeSourceDestination").is(":checked")				
	        },
	        beforeSend: function() {
	            $("#loaderDivDuct").show();
	        },
	        success: function(data) {
	            $("#loaderDivDuct").hide();
	            $("#generateDuctModal").modal("hide");
	            console.log("Success of the ajax");
	            indextrench = 0;
	            if (data && data.auxData) {
	                AuxAppendBOQ(data.auxData, "", "", TargetDuct, indexduct);
	            }

	            const objDiv = document.getElementById("auxiliary_ductTable");
	            objDiv.scrollTop = objDiv.scrollHeight;
	            calculateDistanceSourceDestination(
	                $("#SourceDuctLat").val(),
	                $("#SourceDuctLng").val(),
	                $("#DestinationDuctLat").val(),
	                $("#DestinationDuctLng").val(),
	                "auxiliary_ductTable"
	            );
	        },
	        error: function(xhr, status, error) {
	            $("#loaderDivDuct").hide();
	            console.error(error);
	            alert("Error");
	        }
	    });
	});
}