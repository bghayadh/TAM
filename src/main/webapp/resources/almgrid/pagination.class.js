class Pagination {

    constructor(options) {


        this.id = options.id;
        this.tableId = options.tableId;
        this.noOfRows = options.noOfRows;
        this.dataArray = options.dataArray;
        this.ArrayKeys = options.ArrayKeys
        this.selectCheckbox = options.selectCheckbox

        this.columnLinkNb = options.columnLinkNb;
        // console.log(this.id);

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

            //this.end = 0;
            //this.start = 0
            this.paginationLabel.innerHTML = "Viewing <span>" + this.totalNoOfRows + "-" + this.totalNoOfRows + "</span> of <span>" + this.totalNoOfRows + "</span>";
            this.prevButton.disabled = true;
            this.nextButton.disabled = true;

        }
        else{
          //  this.showRows(this.dataArray, this.start, this.end);
        	
        	//Call the function that append the rows from gridAppendRows class
            this.gridAppendRows.showRows(this.dataArray, this.start, this.end);

        }

        if (this.totalNoOfRows <= this.noOfRows) {

              //console.log("Passes here for the else if");

            // var rowCount = $("#gridTable tr.filterRows").length;\

            //this.paginationLabel.innerHTML = "Viewing <span>" + this.start + "-" + this.totalNoOfRows + "</span> of <span>" + this.totalNoOfRows + "</span>";
           // this.prevButton.disabled = true;
           // this.nextButton.disabled = true;
            // this.paginationLabel.style.marginLeft = '300px';


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

        // if (this.nextButton.getAttribute('listener') !== 'true') {
        //     this.nextButton.addEventListener('click', (e) => this.onNext(e));
        //     this.nextButton.setAttribute('listener', 'true');
        // }

        // if (this.prevButton.getAttribute('listener') !== 'true') {
        //     this.prevButton.addEventListener('click', (e) => this.onPrevious(e));
        //     this.prevButton.setAttribute('listener', 'true');
        // }

    }



    //When clicked on Next
    onNext(e) {

        this.start = this.end + 1;
        this.end = this.start + this.noOfRows - 1;
         
        //This function to append the rows
       // this.showRows(this.dataArray, this.start, this.end);
      
          //Call the function that append the rows from gridAppendRows class
        this.gridAppendRows.showRows(this.dataArray, this.start, this.end);

        var nextPage = Math.ceil(parseInt(this.end) / parseInt(this.noOfRows));
        $(this.pageNumberSelect).val(nextPage).change();

        // this.pageNumbers = Math.ceil(parseInt(this.totalRows.length) / parseInt(this.noOfRows));
        // var current_page = $(this.pageNumberSelect).val();
        // current_page = parseInt(current_page);
        // if (current_page != this.pageNumbers) {
        //     current_page++;
        //     $(this.pageNumberSelect).val(current_page).change();
        // }
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

        // this.pageNumbers = Math.ceil(parseInt(this.totalRows.length) / parseInt(this.noOfRows));
        // var current_page = $(this.pageNumberSelect).val();
        // if (current_page != 1) {
        //     current_page--;
        //     $(this.pageNumberSelect).val(current_page).change();
        // }

        e.preventDefault();
    }

    // This function to show the rows as the need
    //This function is moved to gridAppendRows class 
  /*  showRows(rows, start, end) {
        //console.log(`end is ${end}, ${start}`)
        start = start - 1;
        end = end - 1;

        
        this.tableBody.innerHTML = ""
        var itemRow = "";

        /*for (let i = 0; i < rows.length; i++) {
            if (i >= start && i <= end) {

                // to append the rows
                this.tableBody.appendChild(rows[i]);
                //rows[i].style.display = 'block';

            }

        }*/
        //console.log(`rows.length is ${rows.length}`)
  /*      if(end >= rows.length){
            //console.log("Enter the if condition")
            end = rows.length - 1
        }
            

        //console.log(`end is ${end}, ${start}, ${rows.length}`)

        for (let i = start; i <= end; i++) {
            itemRow += "<tr class='filterRows'>";
            for (var j = 0; j < this.ArrayKeys.length; j++) {
                if (j == 0) {
                    if (this.selectCheckbox == true) {
                        itemRow += "<td class='table-select-all'><input type='checkbox' class='table-select-checkbox' value='" + rows[i][this.ArrayKeys[j]] + "'></td>";
                    }else{
                        itemRow += "<td >" +rows[i][this.ArrayKeys[j]] + "</td>";
                    }
                } else {
                    itemRow += "<td >" +rows[i][this.ArrayKeys[j]] + "</td>";
                }
            }
            itemRow += "</tr>";
               
        }
        $(this.tableBody).append(itemRow);
        // to update the fields if possible;

        //if(this.totalNoOfRows > this.noOfRows){
            this.updatePagination();
        //}
        

        // Method after draw for single pages
        // methodAfterDraw();

        // Method to make column a link
        //console.log(this.columnLinkNb)
        if (this.columnLinkNb != null) {
            this.columnLinks(this.columnLinkNb);
        }
    }*/



    onRowCountChange(e) {

        this.noOfRows = parseInt(e.target.value);
        this.TotalRows = parseInt(this.dataArray.length);

        if (this.TotalRows <= this.noOfRows) {
            this.start = 1;
            this.end = this.TotalRows;
            if (this.noOfRows != 0 && this.TotalRows != 0) {
               // this.showRows(this.dataArray, this.start, this.end);
            	
            	//Call the function that append the rows from gridAppendRows class
                this.gridAppendRows.showRows(this.dataArray, this.start, this.end);

            }
        } else {
            this.start = 1;
            this.end = this.noOfRows;
            //console.log("pass Seocnd condition")
            // var rowCount = $("#employee_table tr").length;

            if (this.noOfRows != 0 && this.TotalRows != 0) {
               // this.showRows(this.dataArray, this.start, this.end);
            	
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
        $(this.pageNumberSelect).val(this.currentPageSelected );  // in case the value was negative and doesnt go in the if cndition below


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

        //console.log(this.end,this.totalNoOfRows)
       /* if (e.target.selectedIndex == e.target.length - 1) {
            this.end = this.totalNoOfRows;
        }*/

        if (this.end > this.totalNoOfRows) {
            this.end = this.totalNoOfRows;
        }

        // // To fill Dropdown Page Number in Pagination
        // this.pageNumbers = Math.ceil(parseInt(this.totalRows.length) / parseInt(this.noOfRows));
        if(this.totalNoOfRows !== 0)
           // this.showRows(this.dataArray, this.start, this.end);
        	
        	//Call the function that append the rows from gridAppendRows class
            this.gridAppendRows.showRows(this.dataArray, this.start, this.end);


    }


   /* This function is moved to gridAppendRows class  
    * updatePagination() {

       // console.log(this.totalNoOfRows,this.noOfRows,this.end)
        if (this.totalNoOfRows <= this.noOfRows || this.end >= this.totalNoOfRows) {

          this.paginationLabel.innerHTML = "Viewing <span>" + this.start + "-" + this.totalNoOfRows + "</span> of <span>" + this.totalNoOfRows + "</span>";
            this.prevButton.disabled = true;
            this.nextButton.disabled = true;
        }
        else{
            this.paginationLabel.innerHTML = "Viewing <span>" + this.start + "-" + this.end + "</span> of <span>" + this.totalNoOfRows + "</span>";
        }

        if (this.end == this.totalNoOfRows || this.end >= this.totalNoOfRows) {
           // console.log("ifff")
            // this.nextButton.style.display = 'none';
            this.nextButton.disabled = true;
            
            if (this.start == 1) {
                this.prevButton.disabled = true;
            }
            else{
                this.prevButton.disabled = false;
            }
        } else {
            // this.nextButton.style.display = 'inline';
           // console.log("###")
            this.nextButton.disabled = false;
            if (this.start == 1) {
                this.prevButton.disabled = true;
            }
            else{
                this.prevButton.disabled = false;
            }
            // this.nextButton.style.marginLeft = '20px';
            // this.paginationLabel.style.marginLeft = '125px';
        }*/

        /*if (this.start == 1) {
            console.log("start1")
            // this.prevButton.style.display = 'none';
            this.prevButton.disabled = true;
            this.nextButton.disabled = false;

        } else {
            console.log("start2")
            // this.prevButton.style.display = 'inline';
            this.prevButton.disabled = false;
            // this.prevButton.style.marginLeft = '20px';
            // this.paginationLabel.style.marginLeft = '125px';
        }*/
        
        //console.log(`this.totalNoOfRows is ${this.totalNoOfRows}`)
        
    //}


   /* This function is moved to gridAppendRows class  
    * 
    * columnLinks(columnNumber) {
        var tableId = this.tableId;
        var columnNumber = columnNumber;
        for (var i = 0; i < columnNumber.length; i++) {
            var tdNumber = "td:eq(" + columnNumber[i] + ")";
            // Make column href link
            $("#" + tableId + " .filterRows").each(function () {
                var linkTd = $(this).find(tdNumber);
                var linkTdText = $(this).find(tdNumber).text();
                var linkref = "";
                linkref += "<a href='#' class='almgrid-link' id='" + linkTdText + "'>" + linkTdText + "</a>";
                $(linkTd).empty();
                $(linkTd).append(linkref);
            });
        }

    }*/

    fillPageNumbers(pageNumbers) {

        // $(this.pageNumberSelect).empty();
        $(this.pageNbSelectSpan).val(pageNumbers);
        if(this.totalNoOfRows === 0){
            $(this.pageNumberSelect).val(0);
        }else{
            $(this.pageNumberSelect).val(1);
        }
        

        $(this.pageNumberSelect).attr("min", 1);
        $(this.pageNumberSelect).attr("max", pageNumbers);

        // var pageNumberSelectContent = "";
        // for (var i = 1; i <= pageNumbers; i++) {
        //     pageNumberSelectContent += "<option value='" + i + "'>" + i + "</option>";
        // }
        // $(this.pageNumberSelect).append(pageNumberSelectContent);

        // if(pageNumbers > 3000){
        //     this.pageNbSelect.disabled = true;
        // } else{
        //     this.pageNbSelect.disabled = false;
        // }



        // this.pageNbSelect.setAttribute("id", this.tableId + "_pnselect");
        // var pnDiv = this.element.getElementsByClassName('div-pagination-numbers')[0];
        // pnDiv.setAttribute("id", this.tableId + "_pndiv");

        // $(this.pageNumberSelect).empty();
        // var pageNumberSelectContent = "";
        // for (var i = 1; i <= pageNumbers; i++) {
        //     pageNumberSelectContent += "<option value='" + i + "'>" + i + "</option>";
        // }
        // $(this.pageNumberSelect).append(pageNumberSelectContent);

        // var clusterize2 = new Clusterize({
        //     scrollId: this.tableId + "_pndiv",
        //     contentId: this.tableId + "_pnselect"
        //   });


    }


}

