$(document).ready(function() {

    initMapEvents();
    initDefaultLayout();
    initResizableComponents();
    initModalComponents();
    initTreeControls();
    initSortableComponents();
    initApplicationListeners();
    initTableEvents();
    initDistributionBoardEvents();
    console.log("Before calling initTrunchEvent");
    initFiberEvents();
    initTrunchEvents();
    initDuctEvents();
});


/* =========================================================
   MAP EVENTS
========================================================= */

function initMapEvents() {

    $("#viewOnMap")
        .off("change")
        .on("change", function() {

            if (myLatLng) {

                handleViewOnMapClick(
                    $(this).prop("checked") ? "on" : "off",
                    myLatLng
                );
            }
            else {
                console.warn("myLatLng is not set yet.");
            }
        });
}



/* =========================================================
   DEFAULT LAYOUT
========================================================= */

function initDefaultLayout() {

    document.getElementById("Defaultbutton").click();

    $('#tree').toggleClass('orange');
    $('#gis').toggleClass('orange');


    document.getElementById("DefaultRightbutton").click();

    $('#tree').toggleClass('orange');
    $('#gis').toggleClass('orange');

}



/* =========================================================
   RESIZABLE COMPONENTS
========================================================= */

function initResizableComponents() {

    $("#left").resizable({
        handles: "e"
    });

    $("#network_tree").resizable({
        handles: "s"
    });

    $('.modal-content').resizable({
        handles: "e"
    });

}



/* =========================================================
   MODAL COMPONENTS
========================================================= */

function initModalComponents() {

    $('.modal-dialog').draggable({
        handle: ".modal-header, .modal-footer"
    });


    // Reset popup position after drag and close
    $('body').on('hidden.bs.modal', function() {

        $('.modal-dialog').css({
            'top': '',
            'left': ''
        });

    });


    $(".modalMinimize")
        .off("click")
        .on("click", function() {

            $(".modal-body").slideToggle();

            $(".modal-footer").slideToggle();

            toggleModalMinimizeIcon();

        });

}


function toggleModalMinimizeIcon() {

    var iconToChange = $('.icon-to-change');

    if (iconToChange.hasClass('fa-window-restore')) {

        iconToChange
            .removeClass('fa-window-restore')
            .addClass('fa-minus');

    }
    else {

        iconToChange
            .addClass('fa-window-restore')
            .removeClass('fa-minus');

    }

}



/* =========================================================
   TREE CONTROLS
========================================================= */

function initTreeControls() {

    $('#arrow_up')
        .off("click")
        .on("click", collapseNetworkTree);

    $('#arrow_down')
        .off("click")
        .on("click", expandNetworkTree);

}


function collapseNetworkTree() {

    $("#network_tree").animate({
        height: "0px"
    });

    $(".searchcontainer").css("display", "none");

    $(".tabcontent").animate({
        height: "613px"
    });

    $('#arrow_down').hide();
    $('#arrow_up').hide();
    $('#arrow_down_normal').show();

}


function expandNetworkTree() {

    $(".searchcontainer").css("display", "block");

    $("#network_tree").animate({
        height: "572px"
    });

    $('#arrow_down').hide();
    $('#arrow_up').hide();
    $('#arrow_up_normal').show();

}



/* =========================================================
   SORTABLE COMPONENTS
========================================================= */

function initSortableComponents() {

    $("#sortable").sortable({
        cancel: ".unsortable_sup",
        items: "li:not(.unsortable)"
    });

    $("#sortable").disableSelection();

}



/* =========================================================
   APPLICATION LISTENERS
========================================================= */

function initApplicationListeners() {

    $(document).trigger("triggerListenersEvent");

}

function initTableEvents() {
    // Active row highlight ONLY for auxiliary path tables
    $(document).on("focusin", ".auxiliary-table-path tr, .fiberDuct-table-path tr", function() {

        const $row = $(this);
        const $table = $row.closest(".auxiliary-table-path, .fiberDuct-table-path");

        $table.find("tr.ativeRecord").removeClass("ativeRecord");
        $row.addClass("ativeRecord");
    });
}