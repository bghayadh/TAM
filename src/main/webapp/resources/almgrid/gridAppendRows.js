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
	  var prevSelRow="empty";
	  var prevSelColor="";

    
      //console.log(`rows.length is ${rows.length}`)
      if(end >= rows.length){
          //console.log("Enter the if condition")
          end = rows.length - 1
      }
          
      //console.log(`end is ${end}, ${start}, ${rows.length}`)

      for (let i = start; i <= end; i++) {
          itemRow += "<tr class='filterRows changeTrColor'>";
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
          this.updatePagination(newEnd,newStart);
      //}
      
	var tableID = this.tableId;

	$("#"+tableID+ " tr.changeTrColor").click(function() {
	    var rowIndex = $(this).index();
	    var color = $('#' + tableID + ' > tbody > tr').eq(rowIndex).css("background-color");
	
	    if (prevSelRow == "empty") {  // first time clicking (no previous row is selected)
	        $('#' + tableID + ' > tbody > tr').eq(rowIndex).css('background-color', 'yellow');
	        prevSelRow = rowIndex;
	        prevSelColor = color;
	    } 
		else if(prevSelRow!=rowIndex) {
	        $('#' + tableID + ' > tbody > tr').eq(rowIndex).css('background-color', 'yellow');
	        $('#' + tableID + ' > tbody > tr').eq(prevSelRow).css('background-color', prevSelColor);
	        prevSelRow = rowIndex;
	        prevSelColor = color;
	    }
	});
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
