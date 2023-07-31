

class Pagination {

    constructor(options) {


        this.id = options.id;
        this.tableId = options.tableId;
        this.noOfRows = options.noOfRows;
        // console.log(this.id);

        var paginationId = this.id;
        var tableId = this.tableId;
        var pagecountId = tableId + "_pagecount";

        this.element = document.getElementById(paginationId);
        this.element2 = document.getElementById(pagecountId);

        this.tableBody = document.querySelector("#" + this.tableId + " tbody");

        this.tableElement = document.getElementById(this.tableId);

        this.nextButton = this.element.getElementsByClassName('next')[0];
        this.prevButton = this.element.getElementsByClassName('previous')[0];
        this.paginationLabel = this.element.getElementsByClassName('pagination-label')[0];

        this.cmbRowCount = this.element2.getElementsByClassName('cmb-row-count')[0];

        this.start = 1;
        this.end = this.noOfRows;

        this.init();

    }



    init() {
        this.collectingTableInfo();
        this.addEvents();
    }


    collectingTableInfo() {
        //to know how many total rows do we have
        this.totalRows = document.querySelectorAll("#" + this.tableId + " tr.filterRows");

        this.totalNoOfRows = this.totalRows.length;
        // if the number of rows is less than 10 no display of the block


        if (this.totalNoOfRows == 0) {

            this.paginationLabel.innerHTML = "Viewing <span>" + this.totalNoOfRows + "-" + this.totalNoOfRows + "</span> of <span>" + this.totalNoOfRows + "</span>";
            this.prevButton.style.display = 'none';
            this.nextButton.style.display = 'none';
            // this.paginationLabel.style.marginLeft = '300px';
            //this.element3.style.display='none';

        }

        else if (this.totalNoOfRows <= this.noOfRows) {

            //  console.log("Passes here for the else if");

            // var rowCount = $("#gridTable tr.filterRows").length;



            this.paginationLabel.innerHTML = "Viewing <span>" + this.start + "-" + this.totalNoOfRows + "</span> of <span>" + this.totalNoOfRows + "</span>";
            this.prevButton.style.display = 'none';
            this.nextButton.style.display = 'none';
            // this.paginationLabel.style.marginLeft = '300px';




        }

        // we can modify the total rows and end rows as our need
        else {


            this.showRows(this.totalRows, this.start, this.end);

        }


    }


    addEvents() {
        this.nextButton.addEventListener('click', (e) => this.onNext(e));
        this.prevButton.addEventListener('click', (e) => this.onPrevious(e));
        this.cmbRowCount.addEventListener('change', (e) => this.onRowCountChange(e));

    }

    //When clicked on Next
    onNext(e) {

        this.start = this.end + 1;
        this.end = this.start + this.noOfRows - 1;
        // console.log(this.end);
        if (this.end >= this.totalNoOfRows) {
            this.end = this.totalNoOfRows;
        }
        //This function to append the rows
        this.showRows(this.totalRows, this.start, this.end);
        e.preventDefault();
    }

    onPrevious(e) {
        // console.log('Previous Button Clicked');
        if (this.start > 1) {
            this.start = this.start - this.noOfRows;
            this.end = this.start + this.noOfRows - 1;
        }

        this.showRows(this.totalRows, this.start, this.end);
        e.preventDefault();
    }




    // This function to show the rows as the need
    showRows(rows, start, end) {

        start = start - 1;
        end = end - 1;

        this.tableBody.innerHTML = "";

        for (let i = 0; i < rows.length; i++) {
            if (i >= start && i <= end) {

                // to append the rows
                this.tableBody.appendChild(rows[i]);
                //rows[i].style.display = 'block';

            }

        }
        // to update the fields if possible;
        this.updatePagination();

        // Method after draw for single pages
        // methodAfterDraw();


    }



    onRowCountChange(e) {

        this.noOfRows = parseInt(e.target.value);
        this.TotalRows = parseInt(this.totalRows.length);

        if (this.TotalRows <= this.noOfRows) {
            this.start = 1;
            this.end = this.TotalRows;
            // var rowCount = $("#employee_table tr").length;

            if (this.noOfRows != 0) {
                this.showRows(this.totalRows, this.start, this.end);
            }
        } else {
            this.start = 1;
            this.end = this.noOfRows;
            // var rowCount = $("#employee_table tr").length;

            if (this.noOfRows != 0) {
                this.showRows(this.totalRows, this.start, this.end);
            }
        }




    }


    updatePagination() {
        if (this.end == this.totalNoOfRows) {
            this.nextButton.style.display = 'none';
        } else {
            this.nextButton.style.display = 'block';
            this.nextButton.style.marginLeft = '20px';
            this.paginationLabel.style.marginLeft = '125px';
        }

        if (this.start == 1) {
            this.prevButton.style.display = 'none';


        } else {
            this.prevButton.style.display = 'block';
            this.prevButton.style.marginLeft = '20px';
            this.paginationLabel.style.marginLeft = '125px';
        }

        this.paginationLabel.innerHTML = "Viewing <span>" + this.start + "-" + this.end + "</span> of <span>" + this.totalNoOfRows + "</span>";
    }


}
