class gridAppendRows {
    constructor(tableBody, ArrayKeys, selectCheckbox, prevButton, nextButton, paginationLabel, totalNoRows, noOfRows, columnLinkNb, tableId) {
        this.tableBody = tableBody;
        this.ArrayKeys = ArrayKeys;
        this.selectCheckbox = selectCheckbox;
        this.prevButton = prevButton;
        this.nextButton = nextButton;
        this.paginationLabel = paginationLabel;
        this.totalNoRows = totalNoRows;
        this.noOfRows = noOfRows;
        this.columnLinkNb = columnLinkNb;
        this.tableId = tableId;
    }

    showRows(rows, start, end) {

        var newStart = parseInt(start);
        var newEnd = parseInt(end);

        start = parseInt(start) - 1;
        end = parseInt(end) - 1;

        this.tableBody.innerHTML = ""
        var itemRow = "";

        var prevSelRow = "empty";
        var prevSelColor = "";

        if (end >= rows.length) {
            end = rows.length - 1
        }

        for (let i = start;i <= end;i++) {
            itemRow += "<tr class='filterRows changeTrColor' style='text-align:center; vertical-align:middle;'>";
            for (var j = 0;j < this.ArrayKeys.length;j++) {
                const columnName = this.ArrayKeys[j];
                if (columnName === "panTo") {

                    itemRow += `
					    <td style="text-align:center; vertical-align:middle;">
					        <button type="button"
					            onclick="panToAux('${rows[i].fromAuxId}')">
					            Pan
					        </button>
					    </td>
					`;
                }
				else if (columnName === "showElement") {
				    itemRow += `
				        <td style="text-align:center; vertical-align:middle;">
				            <input
				                type="checkbox"
				                id="${rows[i].fromAuxId}"
				                class="aux-point-checkbox"
				                onchange="toggleAuxPointMarker(
				                    this,
				                    '${rows[i].fromAuxId}',
				                    '${rows[i].fromAuxName}',
				                    '${rows[i].fromLatitude}',
				                    '${rows[i].fromLongitude}'
				                )">
				        </td>
				    `;
				} else {
                    let value = rows[i][this.ArrayKeys[j]];
/*
                    // 🔥 handle object (cables case)
                    if (value && typeof value === "object") {
                        value = Object.entries(value)
                            .map(([k, v]) => `${k} → ${v}`)
                            .join(" , ");
                    }
*/					
                    itemRow += "<td style='text-align:center; vertical-align:middle;'>" + value + "</td>";
                }
            }
            itemRow += "</tr>";
        }
        $(this.tableBody).append(itemRow);
        this.updatePagination(newEnd, newStart);
        var tableID = this.tableId;

        $("#" + tableID + " tr.changeTrColor").click(function() {
            var rowIndex = $(this).index();
            var color = $('#' + tableID + ' > tbody > tr').eq(rowIndex).css("background-color");

            if (prevSelRow == "empty") {  // first time clicking (no previous row is selected)
                $('#' + tableID + ' > tbody > tr').eq(rowIndex).css('background-color', 'yellow');
                prevSelRow = rowIndex;
                prevSelColor = color;
            }
            else if (prevSelRow != rowIndex) {
                $('#' + tableID + ' > tbody > tr').eq(rowIndex).css('background-color', 'yellow');
                $('#' + tableID + ' > tbody > tr').eq(prevSelRow).css('background-color', prevSelColor);
                prevSelRow = rowIndex;
                prevSelColor = color;
            }
        });

        // Method to make column a link
        if (this.columnLinkNb != null) {
            this.columnLinks(this.columnLinkNb);
        }
    }

    updatePagination(end, start) {
        if (this.totalNoRows <= this.noOfRows || end >= this.totalNoRows) {
            this.paginationLabel.innerHTML = "Viewing <span>" + start + "-" + this.totalNoRows + "</span> of <span>" + this.totalNoRows + "</span>";
            this.prevButton.disabled = true;
            this.nextButton.disabled = true;
        }
        else {
            this.paginationLabel.innerHTML = "Viewing <span>" + start + "-" + end + "</span> of <span>" + this.totalNoRows + "</span>";
        }

        if (end == this.totalNoRows || end >= this.totalNoRows) {
            this.nextButton.disabled = true;

            if (start == 1) {
                this.prevButton.disabled = true;
            }
            else {
                this.prevButton.disabled = false;
            }
        } else {
            this.nextButton.disabled = false;
            if (start == 1) {
                this.prevButton.disabled = true;
            }
            else {
                this.prevButton.disabled = false;
            }
        }
    }

    columnLinks(columnNumber) {
        var tableId = this.tableId;
        var columnNumber = columnNumber;
        for (var i = 0;i < columnNumber.length;i++) {
            var tdNumber = "td:eq(" + columnNumber[i] + ")";
            // Make column href link
            $("#" + tableId + " .filterRows").each(function() {
                var linkTd = $(this).find(tdNumber);
                var linkTdText = $(this).find(tdNumber).text();
                var linkref = "";
                linkref += "<a href='#' class='almgrid-link' id='" + linkTdText + "'>" + linkTdText + "</a>";
                $(linkTd).empty();
                $(linkTd).append(linkref);
            });
        }
    }
}
