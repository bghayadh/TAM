function loadGrid(ReportArrayGlobal) {
    var almgrid = new AlmgridTable({
        tableId: "gridTable",
        dataArray: ReportArrayGlobal,
        selectCheckbox: false,
        drawTableGrid: function(tableId, dataArray) {
            var tableBody = document.querySelector("#" + tableId + " tbody");
            var columnLinkNb = this.columnLinkNb;
            var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
            var gridContainerId = tableId + "_container";
            $(gridContainer).attr('id', gridContainerId);
			let totalEnabled = 0;
			let processArray = [];
            $(tableBody).empty();

            if (dataArray.length > 0) {
                var ArrayKeys = Object.keys(dataArray[0]).filter(key => key !== "next_execution_date");
				
                console.log("ArrayKeys is ", ArrayKeys);
                var columnVal;
                var data = [];//for export
                exportArrayGrid = [];
                data.push('\r');
                data.push(["id", "link_name", "operation_name", "status", "class_name", "next_execution_date_str", "last_execution_date", "cron_expression", "creation_date", "last_modification_date", "start_date_time"]);
                filteredGridData = dataArray; // used in draw on map

                for (var i = 0;i < dataArray.length;i++) {
                    data.push('\r');
					if (processArray.includes(dataArray[i]['link_name']) == false) {
						processArray.push(dataArray[i]['link_name']);						
					}
					if (dataArray[i]['status'] == 'Enabled') {
						totalEnabled++;						
					}
					
                    for (var j = 0;j < ArrayKeys.length;j++) {
                        columnVal = ArrayKeys[j];
                        console.log("columnVal is " + columnVal);
						data.push(dataArray[i][ArrayKeys[j]]);						
                    }
                }
                exportArrayGrid.push(data);
                $('#totalOperations').val(dataArray.length);
                $('#totalEnabledOperations').val(totalEnabled);
                $('#totalProcesses').val(processArray.length);
            } else {
                filteredGridData = [];
            }
            // Method for pagination almgrid-pagecount-box
            $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
            $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

            // For global search textbox
            $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

            var paginationId = tableId + "_pagination";

            // Page Rows number
            var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
            nbRows = parseInt(nbRows);

            this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys: ArrayKeys, selectCheckbox: this.selectCheckbox });

            // Drawing for the first time
            if (this.initFlag == 0) {
                var tables = document.getElementsByClassName('almgrid-table');
                for (var i = 0;i < tables.length;i++) {
                    resizableGrid(tables[i]);
                }
            }
            this.initFlag++;
        },
    });
}