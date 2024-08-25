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
          itemRow += "<tr class='filterRows changeTrColor' >";
          for (var j = 0; j < this.ArrayKeys.length; j++) {
	
	
              if (j == 1) {
	 			if (rows[i][this.ArrayKeys[j]] == "List"){
   	    		  	itemRow += "<td><select class='custom-select' id='viewTypeDrop'><option selected>"+rows[i][this.ArrayKeys[j]]+"</option><option>Form</option><option>Tree</option></select></td>";
   	    	  	}
				else if (rows[i][this.ArrayKeys[j]] == "Form"){
   	    	  		itemRow += "<td><select class='custom-select' id='viewTypeDrop'><option>List</option><option selected>"+rows[i][this.ArrayKeys[j]]+"</option><option>Tree</option></select></td>";
				}
				else if (rows[i][this.ArrayKeys[j]] == "Tree"){
				 	itemRow += "<td><select class='custom-select' id='viewTypeDrop'><option>List</option><option>Form</option><option selected>"+rows[i][this.ArrayKeys[j]]+"</option></select></td>";

				}
              } 
			 else if(j=="3"){
				var value = Number(rows[i][this.ArrayKeys[j]]) || 0; // Ensure the value is numeric
				itemRow += "<td><input type='number' min='0' style='margin-left:20px;text-align:center;width:80px;' class='form-control text-input' id='roleLevel' value='" + value + "' ></td>";				
			 }
			 else if(j=="0" || j=="2" ){
				itemRow += "<td>" +rows[i][this.ArrayKeys[j]] + "</td>";
              }
			else if(j=="15") {
				
				itemRow += "<td><button id='applyPerm' class='btn btn-success btn-sm'  name='" + rows[i][this.ArrayKeys[j]] + "' style='margin:5px;'><i class='fas fa-check-square'></i></button><button id='deletePerm' class='btn btn-danger btn-sm'  onClick='deletePermission(\"" + rows[i][this.ArrayKeys[j]] + "\")' style='margin:5px;'><i class='fa fa-trash'></i></button></td>";
			}
			 else if(j=="4" ){ 
				var box = [];
            	var value = [];
 				
	          if (rows[i][this.ArrayKeys[0]] == 'Image Approval' && rows[i][this.ArrayKeys[1]] == 'List') {
                box = ['Read', 'Export', 'First Level Validation', 'Second Level Validation'];
                value = [
                    rows[i][this.ArrayKeys[4]], // read
                    rows[i][this.ArrayKeys[12]], // exports
                    rows[i][this.ArrayKeys[14]], // firstlvl
                    rows[i][this.ArrayKeys[13]]  // secondlvl
                ];
            } else if (rows[i][this.ArrayKeys[1]] == 'List') {
                box = ['Read', 'Delete'];
                value = [
                    rows[i][this.ArrayKeys[4]], // read
                    rows[i][this.ArrayKeys[7]]  // del
                ];
            } else if (rows[i][this.ArrayKeys[0]] == 'Physical Layer') {
                box = ['Read', 'Search Popup', 'Find Connected', 'Projects'];
  				value = [
                    rows[i][this.ArrayKeys[4]], // read
                    rows[i][this.ArrayKeys[16]], // search popup
                    rows[i][this.ArrayKeys[17]], // find connected
                    rows[i][this.ArrayKeys[18]]  // projects
                ];
             
            } else {
                box = ['Read', 'Write', 'Add', 'Delete', 'Save'];
                value = [
                    rows[i][this.ArrayKeys[4]], // read
                    rows[i][this.ArrayKeys[5]], // write
                    rows[i][this.ArrayKeys[6]], // add
                    rows[i][this.ArrayKeys[7]], // del
                    rows[i][this.ArrayKeys[8]]  // save
                ];
            }
				var checkboxHTML = this.checkbox(box, value, rows[i][this.ArrayKeys[0]]).join('');

           		 // Add the generated checkboxes to the table cell
            	itemRow += "<td class='checkbox-column'><div class='form-check'>" + checkboxHTML + "</div></td>";
			}
          }
          itemRow += "</tr>";
      }										
      $(this.tableBody).append(itemRow);

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

checkbox(box, value, rowType) {
    var array = [];
    for (let i = 0; i < box.length; i++) {
        // Determine if checkbox should be checked based on the value
        var isChecked = value[i] == '1' ? 'checked' : ''; 
       
        array[i] = "<input type='checkbox' class='form-check-input' value='" + value[i] + "' onClick='ChangeCheckboxState(this)' id='" + box[i] + "' " + isChecked + ">" +
                       "<label style='margin-top: 0px; margin-right: 25px; font-size: 12px; float: none;'>" + box[i] + "</label>";  
    }

    return array;
}


 
}
