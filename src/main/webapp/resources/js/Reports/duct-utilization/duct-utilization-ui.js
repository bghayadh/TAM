function initializeUI() {
    initializeLegendDraggable();
}

function initializeLegendDraggable() {
    $("#legendDiv").draggable();
}

function initializeCollapsePanels() {

    $('.panel-collapse').on('show.bs.collapse', function() {
        $(this).siblings('.panel-heading').removeClass('active');
    });

    $('.panel-collapse').on('hide.bs.collapse', function() {
        $(this).siblings('.panel-heading').addClass('active');
    });
}

function showLoader() {
    $("#generateLoaderDiv").show();
}

function hideLoader() {
    $("#generateLoaderDiv").hide();
}

function resetCounters() {

    $("#dbCount").text("");
    $("#jctCount").text("");
    $("#custCount").text("");
    $("#handholesCount").text("");
    $("#handholesCountWithJct").text("");
    $("#manholesCount").text("");
    $("#manholesCountWithJct").text("");
    $("#sitesCount").text("");
    $("#relatedPathCount").text("");
}

function resetLegendCheckbox(selector, disabled) {

    $(selector).prop('checked', false);
    $(selector).attr('disabled', disabled);
}

function resetAllLegendCheckboxes() {

    resetLegendCheckbox('.showHideAllDbCheckbox', true);
    resetLegendCheckbox('.showHideAllJctCheckbox', true);
    resetLegendCheckbox('.showHideAllCustCheckbox', true);
    resetLegendCheckbox('.showHideAllHandholesCheckbox', true);
    resetLegendCheckbox('.showHideAllHandholesWithJctCheckbox', true);
    resetLegendCheckbox('.showHideAllManholesCheckbox', true);
    resetLegendCheckbox('.showHideAllManholesWithJctCheckbox', true);
    resetLegendCheckbox('.showHideAllSitesCheckbox', true);
    resetLegendCheckbox('.showHideDuctCheckbox', true);
    resetLegendCheckbox('.showHideRelatedCableCheckbox', false);
}