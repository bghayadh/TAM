function initializeDuctAutocomplete() {

    $("#fiberCable").autocomplete({
        source: function(request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/getDuctAutComplete',
                data: {
                    "requestValue": $("#fiberCable").val(),
                },
                dataType: "json",
                success: function(data) {
                    if (data != null) {
                        response(data.FiberCableList);
                    }
                },
                error: function(result) {
                    alert("Error");
                }
            });
        }, minLength: 0, maxShowItems: 40, scroll: true,

        select: function(event, ui) {
            fiberCable.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
            return false;
        }
    }).autocomplete("instance")._renderItem = function(ul, item) {
        return $('<li class="each">').data("item.autocomplete", item)
            .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                item[0] + '</span><br><span class="desc">' +
                item[1] + '</span></div></li>').appendTo(ul);
    };
    $("#fiberCable").focus(function() {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });   // end of cable autocomplete
}