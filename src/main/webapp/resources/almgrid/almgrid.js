var ReportArrayGlobal = [];
var filteredArray = [];
var TableId;
var nbRows;

// Method To Implement Grid View and define Global Array and Filter Array
function implementAlmGrid(GlobalArray, tblId) {

    ReportArrayGlobal = GlobalArray;
    filteredArray = ReportArrayGlobal;

    TableId = tblId;

    // Function to Draw Table Grid View
    DrawTableGrid(TableId, ReportArrayGlobal);

    // Function to Fill Custom Filter Values
    DrawCustomFilters(ReportArrayGlobal);
}

$(document).ready(function () {

    // Function to Draw Drop Down Custom Fitlering
    DrawDropDownCustomFilter();

    // Search Filter And Draw New Results in Grid View
    $(".almgrid-search").keyup(function () {
        var columnNumber = 1;
        var ReportArrayGlobalKeys = Object.keys(ReportArrayGlobal[0]);

        filteredArray = ReportArrayGlobal;

        $(".almgrid-search").each(function () {
            var FilterValue = $(this).val().toUpperCase();
            var KeyName = ReportArrayGlobalKeys[columnNumber];


            filteredArray = getFilteredArray(filteredArray, KeyName, FilterValue);
            DrawTableGrid(TableId, filteredArray);
            columnNumber++;
        });

    });

    // Make all checkboxes checked on select all
    $(".filter-dropdown-ul").on("change", ".selectall-filter-checkbox", function () {
        var currentForm = $(this).parents("form").attr("id");
        // var currentForm = $(this).parents("form");
        if (this.checked) {
            $("#" + currentForm).find('.custom-filter-checkbox').prop('checked', true);
        } else {
            $("#" + currentForm).find('.custom-filter-checkbox').prop('checked', false);
        }
    });

    // For unchecking Select All
    // $(".custom-filter-values").hover(function () {
    $(".filter-dropdown-ul").on("mouseover", ".custom-filter-values", function () {
        var currentForm = $(this).parents("form").attr("id");

        if ($("#" + currentForm).find('.custom-filter-checkbox:checked').length == $("#" + currentForm).find('.custom-filter-checkbox').length) {
            $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
        } else {
            $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);
        }
    });

    // Showing checkboxes based on searching
    $(".filter-dropdown-ul").on("keyup", ".custom-filter-search", function () {
        var currentForm = $(this).parents("form").attr("id");
        var SearchFilterValue = $(this).val().toUpperCase();

        $("#" + currentForm).find('.custom-filter-checkbox').each(function () {
            var FilterValue = $(this).val().toUpperCase();

            if (FilterValue.indexOf(SearchFilterValue) > -1) {
                $(this).parents(".custom-filter-row").show();
            } else {
                $(this).parents(".custom-filter-row").hide();
            }
        });
    });

    // Advanced Filtering and Draw Table
    $(".filter-dropdown-ul").on("click", ".advanced-filter-submit", function () {
        var fArray = [];
        fArray = filteredArray;

        $(".filter-dropdown-form").each(function () {

            var currentForm = $(this).attr("id");
            var currentIndex = currentForm.split("filterform");
            var currentindex = currentIndex[1];

            var ReportArrayGlobalKeys = Object.keys(ReportArrayGlobal[0]);
            var KeyName = ReportArrayGlobalKeys[currentindex];

            var advancedFilterOneCondition = $("#" + currentForm).find(".advanced-filter-one").val();
            var advancedFilterOneValue = $("#" + currentForm).find(".advanced-filter-one-value").val();
            var advancedFilterRadio = $("#" + currentForm).find("input[name='advanced-filter-radio']:checked").val();
            var advancedFilterTwoCondition = $("#" + currentForm).find(".advanced-filter-two").val();
            var advancedFilterTwoValue = $("#" + currentForm).find(".advanced-filter-two-value").val();

            // Advanced Fitlering
            if (advancedFilterOneCondition == "none") {
                DrawTableGrid(TableId, fArray);

            } else {
                fArray = advancedFilteringFunction(fArray, KeyName, advancedFilterOneCondition, advancedFilterOneValue,
                    advancedFilterRadio, advancedFilterTwoCondition, advancedFilterTwoValue);

                DrawTableGrid(TableId, fArray);
            }

            // Custom Fitlering
            if ($("#" + currentForm).find('.selectall-filter-checkbox:checkbox:checked')) {
                DrawTableGrid(TableId, fArray);

            }

            $("#" + currentForm).find('.custom-filter-checkbox:not(:checked)').each(function () {
                var FilterValue = $(this).val();

                fArray = $.grep(fArray, function (e) {
                    return e[KeyName] != FilterValue;
                });

                DrawTableGrid(TableId, fArray);
            });
        });

    });

    // Conditions to show/hide elements in advanced filter
    $(".filter-dropdown-ul").on("change", ".advanced-filter-one", function () {
        var currentForm = $(this).parents("form").attr("id");
        var advancedFilterOneCondition = $(this).val();
        if (advancedFilterOneCondition == "none") {
            $("#" + currentForm).find(".advanced-filter-one-value").hide();
            $("#" + currentForm).find(".advanced-filter-two").hide();
            $("#" + currentForm).find(".advanced-filter-two-value").hide();

        } else if (advancedFilterOneCondition == "isempty") {
            $("#" + currentForm).find(".advanced-filter-one-value").hide();
            $("#" + currentForm).find(".advanced-filter-two").val("none");
            $("#" + currentForm).find(".advanced-filter-two").show();
            $("#" + currentForm).find(".advanced-filter-two-value").hide();

        } else {
            $("#" + currentForm).find(".advanced-filter-one-value").show();
            $("#" + currentForm).find(".advanced-filter-two").show();
        }
    });

    // Conditions to show/hide elements in advanced filter
    $(".filter-dropdown-ul").on("change", ".advanced-filter-two", function () {
        var currentForm = $(this).parents("form").attr("id");
        var advancedFilterTwoCondition = $(this).val();
        if (advancedFilterTwoCondition == "none") {
            $("#" + currentForm).find(".advanced-filter-two-value").hide();

        } else if (advancedFilterTwoCondition == "isempty") {
            $("#" + currentForm).find(".advanced-filter-two-value").hide();

        } else {
            $("#" + currentForm).find(".advanced-filter-two-value").show();
        }
    });


    // Function Regarding table select all checkbox
    $(".almgrid-table").on("change", ".table-select-all-checkbox", function () {

        if (this.checked) {
            $(".almgrid-table").find('.table-select-checkbox').prop('checked', true);
        } else {
            $(".almgrid-table").find('.table-select-checkbox').prop('checked', false);
        }
    });

    // Table Select Checkbox On Double Click
    $(".almgrid-table").on("dblclick", ".filterRows", function () {
        var checkbox = $(this).find('.table-select-checkbox');

        if (checkbox.is(':checked')) {
            $(".almgrid-table").find(checkbox).prop('checked', false);
        } else {
            $(".almgrid-table").find(checkbox).prop('checked', true);
        }

    });

});


// Function to draw data grid view
function DrawTableGrid(TableId, ReportArray) {
    var TableTbody = "#" + TableId + " tbody";
    $(TableTbody).empty();

    var ArrayKeys = Object.keys(ReportArray[0]);

    // for (c = 0; c < 20; c++) {

    for (i = 0; i < ReportArray.length; i++) {

        var itemRow = "<tr class='filterRows'>";


        for (j = 0; j < ArrayKeys.length; j++) {
            if (j == 0) {
                itemRow = itemRow + "<td class='table-select-all'><input type='checkbox' class='table-select-checkbox' value='" + ReportArray[i][ArrayKeys[j]] + "'></td>";
            } else {
                itemRow = itemRow + "<td >" + ReportArray[i][ArrayKeys[j]] + "</td>";
            }

        }
        itemRow = itemRow + "</tr>";
        $(TableTbody).append(itemRow);
    }
    // }

    // Page Rows number
    nbRows = $(".almgrid-pagecount").val();
    nbRows = parseInt(nbRows);
    var pagination = new Pagination({ id: 'pagination', tableID: TableId, noOfRows: nbRows });

    var tables = document.getElementsByClassName('almgrid-table');
    for (var i = 0; i < tables.length; i++) {
        resizableGrid(tables[i]);
    }

    // Method after draw for single pages
    methodAfterDraw();

}

// Function to filter global array
function getFilteredArray(array, key, value) {
    return array.filter(function (e) {
        // return e[key].toUpperCase() == value;
        return e[key].toUpperCase().indexOf(value) > -1;
    });
}

// Code to Make Table Grid View Resizable
// var table = document.getElementById('gridTable');
// resizableGrid(table);

function resizableGrid(table) {
    var row = table.getElementsByTagName('tr')[0],
        cols = row ? row.children : undefined;
    if (!cols) return;

    for (var i = 0; i < cols.length; i++) {
        var div = createDiv(table.offsetHeight);
        cols[i].appendChild(div);
        cols[i].style.position = 'relative';
        setListeners(div);
    }
}

function createDiv(height) {
    var div = document.createElement('div');
    div.style.top = 0;
    div.style.right = 0;
    div.style.width = '5px';
    div.style.position = 'absolute';
    div.style.cursor = 'col-resize';
    /* remove backGroundColor later */
    // div.style.backgroundColor = 'red';
    div.style.userSelect = 'none';
    /* table height */
    div.style.height = height + 'px';
    return div;
}

function setListeners(div) {
    var pageX, curCol, nxtCol, curColWidth, nxtColWidth;
    div.addEventListener('mousedown', function (e) {
        curCol = e.target.parentElement;
        nxtCol = curCol.nextElementSibling;
        pageX = e.pageX;
        curColWidth = curCol.offsetWidth
        if (nxtCol)
            nxtColWidth = nxtCol.offsetWidth
    });

    document.addEventListener('mousemove', function (e) {
        if (curCol) {
            var diffX = e.pageX - pageX;

            if (nxtCol)
                nxtCol.style.width = (nxtColWidth - (diffX)) + 'px';

            curCol.style.width = (curColWidth + diffX) + 'px';
        }
    });

    document.addEventListener('mouseup', function (e) {
        curCol = undefined;
        nxtCol = undefined;
        pageX = undefined;
        nxtColWidth = undefined;
        curColWidth = undefined;
    });
}


// Function to Draw Drop Down Custom Fitlering
function DrawDropDownCustomFilter() {
    var indexNb = 1;
    // Method to Draw Filter Button inside Table
    $(".filter-dropdown-ul").each(function () {
        $(this).empty();
        var DropDownDiv = createFilterDropDown(indexNb);
        $(this).append(DropDownDiv);
        indexNb++;
    });

}

function createFilterDropDown(indexNb) {
    var dropDownDiv = "";
    dropDownDiv += "<li><form id='filterform" + indexNb + "' class='filter-dropdown-form'>";
    dropDownDiv += "<ul class='nav nav-pills'>";
    dropDownDiv += "<li class='active'><a class='filter-dropdown-pill active' data-toggle='pill' href='#customfilter" + indexNb + "' title='Custom Filter'>";
    dropDownDiv += "<i class='fa fa-eye' aria-hidden='true'></i></a></li>";
    dropDownDiv += "<li><a class='filter-dropdown-pill' data-toggle='pill' href='#advancedfilter" + indexNb + "' title='Advanced Filter'>";
    dropDownDiv += "<i class='fa fa-filter' aria-hidden='true'></i></a></li>";
    dropDownDiv += "</ul><br/>";

    dropDownDiv += "<div class='tab-content'>"; //start tab content

    dropDownDiv += "<div id='customfilter" + indexNb + "' class='tab-pane fade in active show'>"; // start tab pane
    dropDownDiv += "<div class='row'><div class='col-md-12'><div class='form-check'>";
    dropDownDiv += "<input type='checkbox' class='form-check-input selectall-filter-checkbox' checked/>";
    dropDownDiv += "<label for='selectall-filter-checkbox' class='form-check-label custom-filter-label'>Select All</label>";
    dropDownDiv += "</div></div></div>";
    dropDownDiv += "<div class='space-div'></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><input type='text' class='form-control custom-filter-search' placeholder='Search'>";
    dropDownDiv += "</div></div><hr>";

    dropDownDiv += "<div class='custom-filter-values'></div><hr>";

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-6'></div>";
    dropDownDiv += "<div class='col-md-6'><button type='button' class='btn btn-primary advanced-filter-submit'>Submit</button></div>";
    dropDownDiv += "</div>";

    dropDownDiv += "</div>"; // end tab pane

    dropDownDiv += "<div id='advancedfilter" + indexNb + "' class='tab-pane fade'>"; //start tab pane

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-12'>";
    dropDownDiv += "<select class='form-control advanced-filter advanced-filter-one'>";
    dropDownDiv += "<option value='none'>None</option>";
    dropDownDiv += "<option value='isempty'>Is Empty</option>";
    dropDownDiv += "<option value='isequalto'>Is Equal To</option>";
    dropDownDiv += "<option value='contains'>Contains</option>";
    // dropDownDiv += "<option value='inbetween'>In Between</option>";
    dropDownDiv += "<option value='beginswith'>Begins With</option>";
    dropDownDiv += "<option value='endswith'>Ends With</option>";
    dropDownDiv += "<option value='greaterthan'>Greater Than</option>";
    dropDownDiv += "<option value='lessthan'>Less Than</option>";
    dropDownDiv += "</select>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "<div class='space-div'></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><input type='text' class='form-control advanced-filter-one-value' placeholder='Value'>";
    dropDownDiv += "</div></div><hr>";

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-12'>";
    dropDownDiv += "<div class='form-check form-check-inline'>";
    dropDownDiv += "<input class='form-check-input' type='radio' name='advanced-filter-radio' value='and' checked>";
    dropDownDiv += "<label class='form-check-label custom-filter-label'>And</label>";
    dropDownDiv += "</div>";
    dropDownDiv += "<div class='form-check form-check-inline'>";
    dropDownDiv += "<input class='form-check-input' type='radio' name='advanced-filter-radio' value='or'>";
    dropDownDiv += "<label class='form-check-label custom-filter-label'>Or</label>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "<hr>";

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-12'>";
    dropDownDiv += "<select class='form-control advanced-filter advanced-filter-two'>";
    dropDownDiv += "<option value='none'>None</option>";
    dropDownDiv += "<option value='isempty'>Is Empty</option>";
    dropDownDiv += "<option value='isequalto'>Is Equal To</option>";
    dropDownDiv += "<option value='contains'>Contains</option>";
    // dropDownDiv += "<option value='inbetween'>In Between</option>";
    dropDownDiv += "<option value='beginswith'>Begins With</option>";
    dropDownDiv += "<option value='endswith'>Ends With</option>";
    dropDownDiv += "<option value='greaterthan'>Greater Than</option>";
    dropDownDiv += "<option value='lessthan'>Less Than</option>";
    dropDownDiv += "</select>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "<div class='space-div'></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><input type='text' class='form-control advanced-filter-two-value' placeholder='Value'>";
    dropDownDiv += "</div></div><hr>";

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-6'></div>";
    dropDownDiv += "<div class='col-md-6'><button type='button' class='btn btn-primary advanced-filter-submit'>Submit</button></div>";
    dropDownDiv += "</div>";


    dropDownDiv += "</div>"; // end tab pane

    dropDownDiv += "</div>"; // end tab content

    dropDownDiv += "</form></li>";

    return dropDownDiv;
}

// Method to Draw Filter Button inside Table
function DrawCustomFilters(ReportArray) {
    var columnNumber = 1;
    var ArrayKeys = Object.keys(ReportArray[0]);
    $(".custom-filter-values").each(function () {
        $(this).empty();
        var DropDownDiv = "";

        for (i = 0; i < ReportArray.length; i++) {
            DropDownDiv += "<div class='row custom-filter-row'><div class='col-md-12'>";
            DropDownDiv += "<div class='form-check'>";
            DropDownDiv += "<input type='checkbox' class='form-check-input custom-filter-checkbox' checked value='" + ReportArray[i][ArrayKeys[columnNumber]] + "'>";
            DropDownDiv += "<label class='form-check-label custom-filter-label'>" + ReportArray[i][ArrayKeys[columnNumber]] + "</label>";
            DropDownDiv += "</div></div></div>";

        }
        $(this).append(DropDownDiv);
        columnNumber++;

        // method to remove duplicates from checkboxes
        var found = {};
        // $('.custom-filter-checkbox').each(function () {
        //     var $this = $(this);
        //     if (found[$this.attr('value')]) {
        //         $this.parents(".custom-filter-row").remove();
        //     } else {
        //         found[$this.attr('value')] = true;
        //     }
        // });

        $(this).find('.custom-filter-checkbox').each(function () {
            var $this = $(this);
            if (found[$this.attr('value')]) {
                $this.parents(".custom-filter-row").remove();
            } else {
                found[$this.attr('value')] = true;
            }
        });
    });
}

function advancedFilteringFunction(fArray, KeyName, advancedFilterOneCondition, advancedFilterOneValue,
    advancedFilterRadio, advancedFilterTwoCondition, advancedFilterTwoValue) {
    var fArray1 = fArray;
    var fArray2 = fArray;
    var fArrayfinal = [];

    switch (advancedFilterOneCondition) {
        case "none":
            fArray1 = fArray;
            break;

        case "isempty":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName] == ''; });
            break;

        case "isequalto":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName] == advancedFilterOneValue; });
            break;

        case "contains":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName].toUpperCase().includes(advancedFilterOneValue.toUpperCase()); });
            break;

        case "inbetween":
            // fArray1 = $.grep(fArray1, function(e){ return e[KeyName].includes(advancedFilterOneValue); });
            break;

        case "beginswith":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName].toUpperCase().startsWith(advancedFilterOneValue.toUpperCase()); });
            break;

        case "endswith":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName].toUpperCase().endsWith(advancedFilterOneValue.toUpperCase()); });
            break;

        case "greaterthan":
            fArray1 = $.grep(fArray1, function (e) { return parseInt(e[KeyName]) > parseInt(advancedFilterOneValue); });
            break;

        case "lessthan":
            fArray1 = $.grep(fArray1, function (e) { return parseInt(e[KeyName]) < parseInt(advancedFilterOneValue); });
            break;
    }

    // console.log(fArray1);

    switch (advancedFilterTwoCondition) {
        case "none":
            fArray2 = fArray;
            break;

        case "isempty":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName] == ''; });
            break;

        case "isequalto":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName] == advancedFilterTwoValue; });
            break;

        case "contains":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName].toUpperCase().includes(advancedFilterTwoValue.toUpperCase()); });
            break;

        case "inbetween":
            // fArray2 = $.grep(fArray2, function(e){ return e[KeyName].includes(advancedFilterTwoValue); });
            break;

        case "beginswith":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName].toUpperCase().startsWith(advancedFilterTwoValue.toUpperCase()); });
            break;

        case "endswith":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName].toUpperCase().endsWith(advancedFilterTwoValue.toUpperCase()); });
            break;

        case "greaterthan":
            fArray2 = $.grep(fArray2, function (e) { return parseInt(e[KeyName]) > parseInt(advancedFilterTwoValue); });
            break;

        case "lessthan":
            fArray2 = $.grep(fArray2, function (e) { return parseInt(e[KeyName]) < parseInt(advancedFilterTwoValue); });
            break;
    }

    // console.log(fArray2);

    if (advancedFilterRadio == "and") {

        fArrayfinal = fArray1.filter(function (n) { return fArray2.indexOf(n) !== -1; });

    } else if (advancedFilterRadio == "or") {
        fArrayfinal = arrayUnique(fArray1.concat(fArray2));
    }

    // console.log(fArrayfinal);

    return fArrayfinal;

}

function ArrayIntersection(a1, a2) {
    return a1.filter(function (n) { return a2.indexOf(n) !== -1; });
}

function arrayUnique(array) {
    var a = array.concat();
    for (var i = 0; i < a.length; ++i) {
        for (var j = i + 1; j < a.length; ++j) {
            if (a[i] === a[j])
                a.splice(j--, 1);
        }
    }

    return a;
}