class Pagination {
    constructor(options) {
        this.id = options.id;
        this.tableId = options.tableId;
        this.noOfRows = options.noOfRows;
        this.dataArray = options.dataArray;
        this.ArrayKeys = options.ArrayKeys
        this.selectCheckbox = options.selectCheckbox

        this.columnLinkNb = options.columnLinkNb;

        var paginationId = this.id;
        var tableId = this.tableId;
        var pagecountId = tableId + "_pagecount";

        this.element = document.getElementById(paginationId);
        this.element2 = document.getElementById(pagecountId);

        this.tableBody = document.querySelector("#" + this.tableId + " tbody");

        this.tableElement = document.getElementById(this.tableId);

        this.nextButton = this.element.getElementsByClassName('pagination-next')[0];
        console.log(this.element.getElementsByClassName('pagination-next').length)
        this.prevButton = this.element.getElementsByClassName('pagination-previous')[0];
        this.paginationLabel = this.element.getElementsByClassName('pagination-label')[0];

        this.cmbRowCount = this.element2.getElementsByClassName('cmb-row-count')[0];

        this.start = 1;
        this.end = this.noOfRows;

        this.pageNumberSelect = $(this.element).find(".page-number-select");
        this.pageNbSelect = this.element.getElementsByClassName('page-number-select')[0];
        this.pageNbSelectSpan = this.element.getElementsByClassName('page-number-span')[0];

        this.totalNoRows = this.dataArray.length;

        //Create an instance of the gridAppendRows class
        this.gridAppendRows = new gridAppendRows(
            this.tableBody,
            this.ArrayKeys,
            this.selectCheckbox,
            this.prevButton,
            this.nextButton,
            this.paginationLabel,
            this.totalNoRows,
            this.noOfRows,
            this.columnLinkNb,
            this.tableId
        );

        this.init();
    }

    init() {
        this.collectingTableInfo();
        this.addEvents();
    }


    collectingTableInfo() {
        //to know how many total rows do we have
        // this.totalRows = document.querySelectorAll("#" + this.tableId + " tr.filterRows");


        this.totalNoOfRows = this.dataArray.length;
        // if the number of rows is less than 10 no display of the block

        // To fill Dropdown Page Number in Pagination
        this.pageNumbers = Math.ceil(parseInt(this.dataArray.length) / parseInt(this.noOfRows));
        this.fillPageNumbers(this.pageNumbers);


        if (this.totalNoOfRows == 0) {
            this.paginationLabel.innerHTML = "Viewing <span>" + this.totalNoOfRows + "-" + this.totalNoOfRows + "</span> of <span>" + this.totalNoOfRows + "</span>";
            this.prevButton.disabled = true;
            this.nextButton.disabled = true;
        }
        else {
            //Call the function that append the rows from gridAppendRows class
            this.gridAppendRows.showRows(this.dataArray, this.start, this.end);
        }

        if (this.totalNoOfRows <= this.noOfRows) {

            // Method to make column a link
            if (this.columnLinkNb != null) {
                this.gridAppendRows.columnLinks(this.columnLinkNb);
            }
        }
        // we can modify the total rows and end rows as our need
        else {

        }
    }

    addEvents() {
        this.nextButton.addEventListener('click', (e) => this.onNext(e));
        this.prevButton.addEventListener('click', (e) => this.onPrevious(e));
        this.cmbRowCount.addEventListener('change', (e) => this.onRowCountChange(e));
        this.pageNbSelect.addEventListener('change', (e) => this.onPageNumberSelectChange(e));
    }

    //When clicked on Next
    onNext(e) {

        this.start = this.end + 1;
        this.end = this.start + this.noOfRows - 1;

        //Call the function that append the rows from gridAppendRows class
        this.gridAppendRows.showRows(this.dataArray, this.start, this.end);

        var nextPage = Math.ceil(parseInt(this.end) / parseInt(this.noOfRows));
        $(this.pageNumberSelect).val(nextPage).change();
        e.preventDefault();
    }
    onPrevious(e) {
        // console.log('Previous Button Clicked');
        if (this.start > 1) {
            this.start = this.start - this.noOfRows;
            this.end = this.start + this.noOfRows - 1;
        }
        //  this.showRows(this.dataArray, this.start, this.end);

        //Call the function that append the rows from gridAppendRows class
        this.gridAppendRows.showRows(this.dataArray, this.start, this.end);

        var prevPage = Math.ceil(parseInt(this.end) / parseInt(this.noOfRows));
        $(this.pageNumberSelect).val(prevPage).change();
        e.preventDefault();
    }

    onRowCountChange(e) {

        this.noOfRows = parseInt(e.target.value);
        this.TotalRows = parseInt(this.dataArray.length);

        if (this.TotalRows <= this.noOfRows) {
            this.start = 1;
            this.end = this.TotalRows;
            if (this.noOfRows != 0 && this.TotalRows != 0) {
                //Call the function that append the rows from gridAppendRows class
                this.gridAppendRows.showRows(this.dataArray, this.start, this.end);
            }
        } else {
            this.start = 1;
            this.end = this.noOfRows;
            if (this.noOfRows != 0 && this.TotalRows != 0) {
                //Call the function that append the rows from gridAppendRows class
                this.gridAppendRows.showRows(this.dataArray, this.start, this.end);
            }
        }
        // To fill Dropdown Page Number in Pagination
        this.pageNumbers = Math.ceil(parseInt(this.dataArray.length) / parseInt(this.noOfRows));
        this.fillPageNumbers(this.pageNumbers);
    }

    onPageNumberSelectChange(e) {

        this.currentPageSelected = Math.abs(parseInt(e.target.value));
        $(this.pageNumberSelect).val(this.currentPageSelected);  // in case the value was negative and doesnt go in the if cndition below

        var totalPageNb = $(this.pageNbSelectSpan).val();
        totalPageNb = parseInt(totalPageNb);

        if (this.currentPageSelected < 1) {
            $(this.pageNumberSelect).val(1);
            this.currentPageSelected = 1;

        } else if (this.currentPageSelected > totalPageNb) {
            $(this.pageNumberSelect).val(totalPageNb);
            this.currentPageSelected = totalPageNb;
        }

        this.end = Math.ceil(parseInt(this.currentPageSelected) * parseInt(this.noOfRows));
        this.start = this.end - this.noOfRows + 1;

        if (this.end > this.totalNoOfRows) {
            this.end = this.totalNoOfRows;
        }

        if (this.totalNoOfRows !== 0)
            //Call the function that append the rows from gridAppendRows class
            this.gridAppendRows.showRows(this.dataArray, this.start, this.end);
    }

    fillPageNumbers(pageNumbers) {

        $(this.pageNbSelectSpan).val(pageNumbers);
        if (this.totalNoOfRows === 0) {
            $(this.pageNumberSelect).val(0);
        } else {
            $(this.pageNumberSelect).val(1);
        }

        $(this.pageNumberSelect).attr("min", 1);
        $(this.pageNumberSelect).attr("max", pageNumbers);
    }
}