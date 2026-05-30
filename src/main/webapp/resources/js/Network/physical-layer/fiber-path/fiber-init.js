function initFiberEvents() {
    console.log("Hello, just before putting click even in trench initialization");	
    fiberAuxTabEvents();
    $("#saveFiberPath").click(function() {
        saveFiberPath();
    })
    calculateDistanceInit();
    bindFiberDuctButtons();
/*	
    fiberDuctTableBody.on("change", ".fiberDuctRowCheck", function() {
        $(".fiberDuctRowCheck").not(this).prop("checked", false);
    });
*/		
}