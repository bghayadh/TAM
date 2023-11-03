<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <head>
        <meta charset="utf-8">
        <title></title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="shortcut icon" href="">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>

        <script src="${pageContext.request.contextPath}/resources/js/printThis.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet"
            href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
        <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
        <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
        <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css"> -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
        <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>


        <!-- 	 <script src="${pageContext.request.contextPath}/resources/js/FileSaver.min.js"></script> -->
        <!--  <script src="${pageContext.request.contextPath}/resources/js/xlsx.core.min.js"></script> -->
        <script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jspdf.plugin.autotable.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/tableHTMLExport.js"></script>
        <!--   <script src="${pageContext.request.contextPath}/resources/js/tableExport.js"></script>-->

        <link href="${pageContext.request.contextPath}/resources/cssgrid/bootstrap-datepicker.min.css" rel='stylesheet'
            type='text/css'>
        <script src="${pageContext.request.contextPath}/resources/scriptsgrid/bootstrap-datepicker.min.js"
            type='text/javascript'></script>
        <script type="text/javascript"  src="${pageContext.request.contextPath}/resources/js/pagination.class.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/almgrid/gridAppendRows.js"></script>
        
        <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/areasitereport.css" /> -->

        <!-- Links & Scripts related to GridView -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/handsontable.full.css" />
        <!-- <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/main.css" /> -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/pagination.css" />

        <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/almgrid/handsontable.full.js"></script>

        <style>

        </style>


    </head>

    <body>
<%--         <c:set var="page" value="report" /> --%>

<%--         <%@ include file="header.html" %> --%>
  <c:set var="pg" value="report" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>


            <div class="container-fluid">

                <div class="row">

                    <p></p>

                </div>

                <div class="row second">
                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" style="color:green">ALM Grid View</span>
                            </div>

                        </div>

                    </div>
                </div>

                <div id="grid-container">
                    <div class='page-rows'>
                        <div class="form-group">
                            <label class="page-count-label">Show
                                <select id="pagination-rows-count" class="form-control page-count-select">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                                Rows
                            </label>
                        </div>
                    </div>

                    <div id="alm-datagrid"></div>

                    <div id="alm-pagination" class="alm-pagination">
                        <nav aria-label="Page navigation">
                            <ul id="ul-pagination" class="pagination justify-content-end">
                                <li id="pagination-prev" class="page-item"><a class="page-link" href="#">Prev</a></li>
                                <li class="page-item">
                                    <select id="pagination-page-number" class="form-control page-number-select">
                                    </select>
                                </li>
                                <li id="pagination-next" class="page-item"><a class="page-link" href="#">Next</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>

            </div>

            <script>
                $(document).ready(function () {

                    /* Function to get Parameter from URL */
                    var getUrlParameter = function getUrlParameter(sParam) {
                        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                            sURLVariables = sPageURL.split('&'),
                            sParameterName,
                            i;

                        for (i = 0; i < sURLVariables.length; i++) {
                            sParameterName = sURLVariables[i].split('=');

                            if (sParameterName[0] === sParam) {
                                return sParameterName[1] === undefined ? true : sParameterName[1];
                            }
                        }
                    };

                    var rowsOnSinglePage = getUrlParameter('RowsCount');

                    if (rowsOnSinglePage == null) {
                        rowsOnSinglePage = 10;
                    }

                    $("#pagination-rows-count").val(rowsOnSinglePage);

                    var container = document.getElementById('alm-datagrid'), hot;
                    var dataObject = [
                        {
                            id: 1,
                            name: 'John Doe',
                            position: 'Developer',
                            office: 'Beirut',
                            age: 30,
                            salary: '$1200'
                        },
                        {
                            id: 2,
                            name: 'Angelica Ramos',
                            position: 'Junior Technical Author	',
                            office: 'Beirut',
                            age: 23,
                            salary: '$1000'
                        },
                        {
                            id: 3,
                            name: 'Bradley Greer',
                            position: 'Software Engineer',
                            office: 'Saida',
                            age: 28,
                            salary: '$1500'
                        },
                        {
                            id: 4,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 5,
                            name: 'Finn Camacho',
                            position: 'Support Engineer',
                            office: 'Beirut',
                            age: 45,
                            salary: '$1900'
                        },
                        {
                            id: 6,
                            name: 'Garrett Winters',
                            position: 'Accountant',
                            office: 'Saida',
                            age: 38,
                            salary: '$1500'
                        },
                        {
                            id: 7,
                            name: 'Jena Gaines',
                            position: 'Office Manager',
                            office: 'Saida',
                            age: 39,
                            salary: '$2000'
                        },
                        {
                            id: 8,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 9,
                            name: 'Paul Byrd',
                            position: 'Chief Financial Officer',
                            office: 'Saida',
                            age: 49,
                            salary: '$3000'
                        },
                        {
                            id: 3,
                            name: 'Bradley Greer',
                            position: 'Software Engineer',
                            office: 'Saida',
                            age: 28,
                            salary: '$1500'
                        },
                        {
                            id: 4,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 5,
                            name: 'Finn Camacho',
                            position: 'Support Engineer',
                            office: 'Beirut',
                            age: 45,
                            salary: '$1900'
                        },
                        {
                            id: 6,
                            name: 'Garrett Winters',
                            position: 'Accountant',
                            office: 'Saida',
                            age: 38,
                            salary: '$1500'
                        },
                        {
                            id: 7,
                            name: 'Jena Gaines',
                            position: 'Office Manager',
                            office: 'Saida',
                            age: 39,
                            salary: '$2000'
                        },
                        {
                            id: 8,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 9,
                            name: 'Paul Byrd',
                            position: 'Chief Financial Officer',
                            office: 'Saida',
                            age: 49,
                            salary: '$3000'
                        },
                        {
                            id: 3,
                            name: 'Bradley Greer',
                            position: 'Software Engineer',
                            office: 'Saida',
                            age: 28,
                            salary: '$1500'
                        },
                        {
                            id: 4,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 5,
                            name: 'Finn Camacho',
                            position: 'Support Engineer',
                            office: 'Beirut',
                            age: 45,
                            salary: '$1900'
                        },
                        {
                            id: 6,
                            name: 'Garrett Winters',
                            position: 'Accountant',
                            office: 'Saida',
                            age: 38,
                            salary: '$1500'
                        },
                        {
                            id: 7,
                            name: 'Jena Gaines',
                            position: 'Office Manager',
                            office: 'Saida',
                            age: 39,
                            salary: '$2000'
                        },
                        {
                            id: 8,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 9,
                            name: 'Paul Byrd',
                            position: 'Chief Financial Officer',
                            office: 'Saida',
                            age: 49,
                            salary: '$3000'
                        },
                        {
                            id: 3,
                            name: 'Bradley Greer',
                            position: 'Software Engineer',
                            office: 'Saida',
                            age: 28,
                            salary: '$1500'
                        },
                        {
                            id: 4,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 5,
                            name: 'Finn Camacho',
                            position: 'Support Engineer',
                            office: 'Beirut',
                            age: 45,
                            salary: '$1900'
                        },
                        {
                            id: 6,
                            name: 'Garrett Winters',
                            position: 'Accountant',
                            office: 'Saida',
                            age: 38,
                            salary: '$1500'
                        },
                        {
                            id: 7,
                            name: 'Jena Gaines',
                            position: 'Office Manager',
                            office: 'Saida',
                            age: 39,
                            salary: '$2000'
                        },
                        {
                            id: 8,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 9,
                            name: 'Paul Byrd',
                            position: 'Chief Financial Officer',
                            office: 'Saida',
                            age: 49,
                            salary: '$3000'
                        },
                        {
                            id: 3,
                            name: 'Bradley Greer',
                            position: 'Software Engineer',
                            office: 'Saida',
                            age: 28,
                            salary: '$1500'
                        },
                        {
                            id: 4,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 5,
                            name: 'Finn Camacho',
                            position: 'Support Engineer',
                            office: 'Beirut',
                            age: 45,
                            salary: '$1900'
                        },
                        {
                            id: 6,
                            name: 'Garrett Winters',
                            position: 'Accountant',
                            office: 'Saida',
                            age: 38,
                            salary: '$1500'
                        },
                        {
                            id: 7,
                            name: 'Jena Gaines',
                            position: 'Office Manager',
                            office: 'Saida',
                            age: 39,
                            salary: '$2000'
                        },
                        {
                            id: 8,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        },
                        {
                            id: 9,
                            name: 'Paul Byrd',
                            position: 'Chief Financial Officer',
                            office: 'Saida',
                            age: 49,
                            salary: '$3000'
                        },
                        {
                            id: 10,
                            name: 'Cara Stevens',
                            position: 'Sales Assistant',
                            office: 'Saida',
                            age: 34,
                            salary: '$1700'
                        }

                    ];

                    hot = new Handsontable(container, {
                        //data: Handsontable.helper.createSpreadsheetData(137, 5),
                        //colHeaders: true,
                        data: dataObject,
                        colHeaders: ['ID', 'Name', 'Position', 'Office', 'Age', 'Salary'],
                        columns: [
                            {
                                data: 'id',
                                type: 'numeric',
                                width: 40
                            },
                            {
                                data: 'name',
                                type: 'text'
                            },
                            {
                                data: 'position',
                                type: 'text'
                            },
                            {
                                data: 'office',
                                type: 'text'
                            },
                            {
                                data: 'age',
                                type: 'numeric'
                            },
                            {
                                data: 'salary',
                                type: 'text'
                            }
                        ],
                        stretchH: 'all',
                        dropdownMenu: true,
                        columnSorting: true,
                        sortIndicator: true,
                        manualRowResize: true,
                        manualColumnResize: true,
                        height: 270,
                        autoWrapRow: true,
                        rowHeaders: true,
                        contextMenu: false,
                        filters: true,
                        dropdownMenu: true,
                        allowInsertColumn: false,
                        allowRemoveRow: false,
                        allowRemoveColumn: false,
                        readOnly: true,
                        exportFile: true,
                        language: 'en-US',
                        licenseKey: 'non-commercial-and-evaluation'
                    });

                    //  var rowsOnSinglePage = 15; //can be changed
                    //var rowsOnSinglePage = $("#pagination-rows-count").val(); //can be changed
                    var pages = document.querySelector('#alm-pagination');

                    var newDataSet = hot.getData();

                    hot.updateSettings({
                        afterColumnSort: function () {
                            refreshPaging()
                        },
                        afterFilter: function () {
                            refreshPaging()
                        }
                    });

                    function refreshPaging() {
                        newDataSet = hot.getData();
                        // pages.innerHTML = '';
                        createPages(rowsOnSinglePage);
                        hot.updateSettings({
                            hiddenRows: {
                                rows: getArray(1),
                                indicators: false
                            }
                        });
                        // pages.firstElementChild.focus()
                    }

                    function createPages(rowsOnSinglePage) {
                        var bt, els = Math.ceil(newDataSet.length / rowsOnSinglePage);
                        var pagination = "";

                        for (var i = 0; i < els; i++) {
                            pagination += "<option value='" + (i + 1) + "'>" + (i + 1) + "</option>";
                        }

                        $("#pagination-page-number").empty();
                        $("#pagination-page-number").append(pagination);

                    };

                    createPages(rowsOnSinglePage); //we define how many rows should be on a single page

                    $("#pagination-page-number").on('change', function () {
                        var current_page = $(this).val();
                        hot.updateSettings({
                            hiddenRows: {
                                rows: getArray(current_page),
                                indicators: false
                            }
                        })
                    });

                    $("#pagination-next").on('click', function () {
                        var current_page = $("#pagination-page-number").val();
                        var last_page = $("#pagination-page-number option:last-child").val();
                        if (current_page != last_page) {
                            current_page++;
                            $("#pagination-page-number").val(current_page).change();
                            hot.updateSettings({
                                hiddenRows: {
                                    rows: getArray(current_page),
                                    indicators: false
                                }
                            })
                        }
                    });

                    $("#pagination-prev").on('click', function () {
                        var current_page = $("#pagination-page-number").val();
                        if (current_page != 1) {
                            current_page--;
                            $("#pagination-page-number").val(current_page).change();
                            hot.updateSettings({
                                hiddenRows: {
                                    rows: getArray(current_page),
                                    indicators: false
                                }
                            })
                        }
                    });

                    function getArray(clicked) {
                        var parts = pages.childElementCount;
                        var arr = [];

                        if (clicked === 1) {
                            for (var i = (clicked * rowsOnSinglePage); i < newDataSet.length; i++) {
                                arr.push(i);
                            }
                            return arr;
                        } else {
                            for (var j = 0; j < (clicked * rowsOnSinglePage) - rowsOnSinglePage; j++) {
                                arr.push(j);
                            }
                            for (var i = (clicked * rowsOnSinglePage); i < newDataSet.length; i++) {
                                arr.push(i);
                            }
                            return arr;
                        }
                    }

                    hot.updateSettings({
                        hiddenRows: {
                            rows: getArray(1),
                            indicators: false
                        }
                    });

                });

            </script>

            <script>
                $(document).ready(function () {

                    $("#pagination-rows-count").on('change', function () {
                        var RowsCount = $(this).val();
                        location.replace("${pageContext.request.contextPath}/AlmGridView?RowsCount=" + RowsCount);

                    });

                });
            </script>
    </body>

</html>