function initializeGenerateButton() {

    $("#generate").click(function() {
        onGenerateClick();
    });
}

function initializeExportButton() {

    $("#gridExport").click(function() {
        exportGridToCsv();
    });
}

function initializeLegend() {
    document.getElementById('mapDropdown').addEventListener('change', function() {
        handleMapDropdownChange(this.value);
    });

    showHideSourceMarker();
    showHideDestinationMarker()
}