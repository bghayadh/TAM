function initializeDuctAutocomplete() {

    $("#ductPath").autocomplete({
        source: function(request, response) {
			console.log("Welcome to auto complete of duct, client side");
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: getContext() + '/getDuctAutComplete',
                data: {
                    "requestValue": $("#ductPath").val(),
                },
                dataType: "json",
                success: function(data) {
                    if (data != null) {
                        response(data.ducts);
                    }
                },
                error: function(result) {
                    alert("Error");
                }
            });
        }, minLength: 0, maxShowItems: 40, scroll: true,

        select: function(event, ui) {
            ductPath.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
            return false;
        }
    }).autocomplete("instance")._renderItem = function(ul, item) {
        return $('<li class="each">').data("item.autocomplete", item)
            .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                item[0] + '</span><br><span class="desc">' +
                item[1] + '</span></div></li>').appendTo(ul);
    };
    $("#ductPath").focus(function() {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });   // end of cable autocomplete
}