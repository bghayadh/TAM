

class Pagination{

constructor(options){
	
	
    	console.log("Yara mezher");
    	this.id = options.id; 
        console.log(this.id);
        
        this.element = document.getElementById('pagination');
        this.element2=document.getElementById('combobox');
        this.element3=document.getElementById('trtable');
       // this.element4=document.getElementsByClassName ('pagination-label');
      
        console.log(this.element2);
       // console.log(this.element4);
        this.tableID = options.tableID;
        console.log("yara"+this.tableID);
        this.tableBody = document.querySelector("#"+this.tableID+" tbody");
        
        this.tableElement = document.getElementById(this.tableID);
        this.nextButton = this.element.getElementsByClassName('next')[0];
        
      
        
        this.prevButton = this.element.getElementsByClassName('previous')[0];
       
        this.paginationLabel = this.element.getElementsByClassName('pagination-label')[0];
      
        
        this.cmbRowCount = this.element2.getElementsByClassName('cmb-row-count')[0];
       
        this.noOfRows = options.noOfRows;
      
        
        
        //this.value = 0;
        this.start = 1;
        this.end = this.noOfRows;
        console.log(this.end);
        
        this.init();
        
       
        
    }



init(){        
    this.collectingTableInfo();
    this.addEvents();
    
          
}



collectingTableInfo(){
    //to know how many total rows do we have
 this.totalRows = document.querySelectorAll("#"+this.tableID+" tbody tr");
console.log( "yara"+this.totalRows);

this.totalNoOfRows = this.totalRows.length;
console.log(this.totalNoOfRows);
// if the number of rows is less than 10 no display of the block


 if(this.totalNoOfRows==0){
	

this.paginationLabel.innerHTML = "Viewing <span>"+this.totalNoOfRows+"-"+this.totalNoOfRows+"</span> of <span>"+this.totalNoOfRows+"</span>";
this.prevButton.style.display = 'none';
this.nextButton.style.display='none';
this.paginationLabel.style.marginLeft='300px';
this.element3.style.display='none';
	
}
 
 else  if(this.totalNoOfRows <= this.noOfRows){
	 
 console.log("Passes here for the else if");
 	
 	var rowCount = $("#report_table tr").length;
 	
 	
 	 this.paginationLabel.innerHTML = "Viewing <span>"+this.start+"-"+rowCount+"</span> of <span>"+this.totalNoOfRows+"</span>";
 	 this.prevButton.style.display = 'none';
 	 this.nextButton.style.display='none';
 	 this.paginationLabel.style.marginLeft='300px';
 	
    
     
     }

// we can modify the total rows and end rows as our need
else{
	
	console.log("passes here yaryura");
	      this.showRows(this.totalRows, this.start, this.end); 
	
}


}


addEvents(){
    this.nextButton.addEventListener('click', ()=>this.onNext());
    this.prevButton.addEventListener('click', ()=>this.onPrevious());
    this.cmbRowCount.addEventListener('change', (e)=>this.onRowCountChange(e));
}

//When clicked on Next
onNext(){
	this.start = this.end + 1;
    this.end = this.start + this.noOfRows - 1;
    console.log(this.end);
    if(this.end >= this.totalNoOfRows){
        this.end = this.totalNoOfRows;                     
    }   
    //This function to append the rows
    this.showRows(this.totalRows, this.start, this.end); 
	
}

onPrevious(){
    console.log('Previous Button Clicked');
    if(this.start > 1){
        this.start = this.start - this.noOfRows;
        this.end = this.start + this.noOfRows - 1;
    }

    this.showRows(this.totalRows, this.start, this.end);

}




// This function to show the rows as the need
showRows(rows, start, end){
    console.log(rows);
    start = start -1;
    end = end - 1;         

    this.tableBody.innerHTML = "";

    for(let i=0; i<rows.length; i++ ){
        if(i >= start && i <= end){
        	
        	
        	// to append the rows
            this.tableBody.appendChild(rows[i]);
            //rows[i].style.display = 'block';
        }

    }
   // to update the fields if possible;
    this.updatePagination();


}



onRowCountChange(e){
	
	
	console.log("Passes here");
	
	this.noOfRows = parseInt(e.target.value);
	console.log(this.noOfRows);
	this.start = 1;
    this.end = this.noOfRows;
	var rowCount = $("#employee_table tr").length;
    if(rowCount!=0){
    this.showRows(this.totalRows, this.start, this.end);
    }
	
    
}


updatePagination(){
	if(this.end == this.totalNoOfRows ){
		console.log("Passes here in the update");
        this.nextButton.style.display = 'none';
    }else{
     this.nextButton.style.display = 'block';
     this.nextButton.style.marginLeft='20px';
     this.paginationLabel.style.marginLeft='125px';
    }     

    if(this.start == 1){
         this.prevButton.style.display = 'none';
      
         
     }else{
         this.prevButton.style.display = 'block';
         this.prevButton.style.marginLeft='20px';
         this.paginationLabel.style.marginLeft='125px';
     }    

    this.paginationLabel.innerHTML = "Viewing <span>"+this.start+"-"+this.end+"</span> of <span>"+this.totalNoOfRows+"</span>"; 
}


}
