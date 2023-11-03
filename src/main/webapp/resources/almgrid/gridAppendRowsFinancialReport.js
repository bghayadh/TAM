class gridAppendRows {
  constructor(tableBody, ArrayKeys, selectCheckbox,prevButton,nextButton,paginationLabel,totalNoRows,noOfRows,columnLinkNb,tableId) {
    this.tableBody = tableBody;
    this.ArrayKeys = ArrayKeys;
    this.selectCheckbox = selectCheckbox;
    this.prevButton = prevButton;
    this.nextButton = nextButton;
    this.paginationLabel=paginationLabel;
    this.totalNoRows = totalNoRows;
    this.noOfRows = noOfRows;
    this.columnLinkNb=columnLinkNb;
    this.tableId = tableId;
  }

  showRows(rows, start, end) {
  
	  var newStart = parseInt(start);
      var newEnd = parseInt(end);

	  start = parseInt(start) - 1;
      end = parseInt(end) - 1;
      
     
      this.tableBody.innerHTML = ""
      var itemRow = "";

      var rowCount=0;

      //console.log(`rows.length is ${rows.length}`)
      if(end >= rows.length){
          //console.log("Enter the if condition")
          end = rows.length - 1
      }
          
      //console.log(`end is ${end}, ${start}, ${rows.length}`)

      for (let i = start; i <= end; i++) {
          itemRow += "<tr class='filterRows'>";
          for (var j = 0; j < this.ArrayKeys.length; j++) {
              if (j == 0) {
				itemRow += "<td><button type='button' class='pan' id='panTo" +rows[i][this.ArrayKeys[j]]+ "'  onClick='panToSite(\"" + rows[i][this.ArrayKeys[j]] + "\",\"" +i+ "\",\"" +rowCount+ "\")' >Pan to Site</button></td>"
              } 
			 else {
				itemRow += "<td >" +rows[i][this.ArrayKeys[j]] + "</td>";
              }
          }
          itemRow += "</tr>";
		  rowCount++;             
      }										
      $(this.tableBody).append(itemRow);
      // to update the fields if possible;

      //if(this.totalNoOfRows > this.noOfRows){
          this.updatePagination(newEnd,newStart);
      //}
      

      // Method after draw for single pages
      // methodAfterDraw();

      // Method to make column a link
      if (this.columnLinkNb != null) {
          this.columnLinks(this.columnLinkNb);
      }  
  }
  
   updatePagination(end,start) {

   if (this.totalNoRows <= this.noOfRows || end >= this.totalNoRows) {

       this.paginationLabel.innerHTML = "Viewing <span>" + start + "-" + this.totalNoRows + "</span> of <span>" + this.totalNoRows + "</span>";
       this.prevButton.disabled = true;
       this.nextButton.disabled = true;
   }
   else{
       this.paginationLabel.innerHTML = "Viewing <span>" + start + "-" + end + "</span> of <span>" + this.totalNoRows + "</span>";
   }

   if (end == this.totalNoRows || end >= this.totalNoRows) {
      //  console.log("ifff")
       // this.nextButton.style.display = 'none';
       this.nextButton.disabled = true;
       
       if (start == 1) {
           this.prevButton.disabled = true;
       }
       else{
           this.prevButton.disabled = false;
       }
   } else {
       // this.nextButton.style.display = 'inline';
     //  console.log("###")
       this.nextButton.disabled = false;
       if (start == 1) {
           this.prevButton.disabled = true;
       }
       else{
           this.prevButton.disabled = false;
       }
       // this.nextButton.style.marginLeft = '20px';
       // this.paginationLabel.style.marginLeft = '125px';
   }
}

   columnLinks(columnNumber) {
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

   } 
  
}
