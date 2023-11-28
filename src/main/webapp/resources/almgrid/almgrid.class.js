
//This class is used for List View grid
class Almgrid {
    //optimized by yaraa

    // Constructor
    constructor(options) {

        this.tableId = options.tableId;
        this.dataArray = options.dataArray;
        this.selectCheckbox = options.selectCheckbox;
        this.filteredArray = [];
        this.columnLinkNb = options.columnLinkNb;
        this.initFlag = 0;
        this.availableArrayLength = 0
        this.arrayLength = 0 
        this.pagination;
        this.view = options.view;
        this.init();
        this.ArrayKeys;

    }
    //init function where custom filter and data is drawn
    init() {
        this.initializeFunction();
        this.addEvents();
        this.drawTableGrid(this.tableId, this.dataArray);

	 var count = 1;
    // Define a window to each column
    $("#" + this.tableId).find(".filter-dropdown-ul").each(function () {
		window["submit_" +count] = "unchecked";// used to check if the submit is clicked
		window["uncheckedCheckboxes_" +count]=[]; // used to store the unchecked checkboxes when first time checking select all
		window["uncheckboxArray_" +count]=[];// used to store the unchecked checkboxes on clicking on submit in popup
		window["checkboxArray_" +count]=[];// used to store the checked checkboxes on clicking on submit in popup
        count++;
    });

    }
    initializeFunction() {
        drawDropDownCustomFilter(this.tableId);
    }


    drawCustomFilters(filteredUncheckedArray,submitButtonFlag,columnNumber, currentForm, uniqueArray, uniqueFilteredArray,checkboxArray,uncheckboxArray,startIndex,endIndex,finalCount,nextPageNum) {

        var tableId = this.tableId;
        var FilteredArrayLength = uniqueFilteredArray.length
        this.availableArrayLength = FilteredArrayLength

        var arrayLength = uniqueArray.length;
        this.arrayLength = arrayLength

        if(FilteredArrayLength > 0 ){
            $("#" + tableId).find("#" + currentForm + " .selectall-filter-checkbox").addClass("prev-true")
        }
        
		
        $("#" +currentForm).find(".nextPrevPage").val(nextPageNum);	
    	$('#'+currentForm).find('.nextData').prop('disabled', false);
   	 	$('#'+currentForm).find('.previousData').prop('disabled', true);
   	 
   	 	//Set total number of pages
        if(FilteredArrayLength == uniqueArray ){
        	var maxNoOfPages = uniqueArray/30000;
        }
        else {
        	var maxNoOfPages = FilteredArrayLength/30000;
        }
    	if(String(maxNoOfPages).includes(".")==true) {
    	  		maxNoOfPages=String(maxNoOfPages).split(".")[0];
    	  		maxNoOfPages = parseInt(maxNoOfPages)+1;
    	 }
    	$("#" +currentForm).find(".totalNoPages").val(maxNoOfPages);
    	
    	if(maxNoOfPages == nextPageNum) {
        	$('#'+currentForm).find('.nextData').prop('disabled', true);
       	 	$('#'+currentForm).find('.previousData').prop('disabled', true);
    	}
    	
    	if ($("#" + currentForm).find('.showall-filter-checkbox').prop('checked') == true) {
    		$("#" + currentForm).find('.showall-filter-checkbox').prop('checked', false);
            $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
            $("#" + currentForm).find('.showavailable-filter-checkbox').prop('checked', true);
        }
    	if(submitButtonFlag =="checked") {
			uncheckboxArray[columnNumber]=window["uncheckboxArray_" +columnNumber];
			checkboxArray[columnNumber]=window["checkboxArray_" +columnNumber];
			window["uncheckedCheckboxes_" + columnNumber]=[];
			window["uncheckedCheckboxes_" + columnNumber]=window["uncheckboxArray_" +columnNumber];
		}
        $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
        $("#" + currentForm).find('.showavailable-filter-checkbox').prop('checked', true);
        $('#'+tableId + 'Search' + columnNumber).val("");
        
        var rows = [];
        var existedValues = [];
        
        
        var search = document.getElementById(tableId + 'Search' + columnNumber);
        let existinfilteredArray = false
        let checked = false

        var selectAll = document.querySelector("#" + currentForm + " .selectall-filter-checkbox");
        let showavailable = document.querySelector("#" + currentForm + " .showavailable-filter-checkbox");


        let showSelectedStatus = document.querySelector("#" + currentForm + " .showselected-filter-checkbox");
        let showAllStatus = document.querySelector("#" + currentForm + " .showall-filter-checkbox");


        if(submitButtonFlag =="unchecked") {
            $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
        }

	    else if(submitButtonFlag =="checked" && window["uncheckedCheckboxes_" + columnNumber].length >0) {
            $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);
        }
        
        
        for (var i = 0; i < arrayLength; i++) {

            //console.log(uniqueArray[i])
            existinfilteredArray = uniqueFilteredArray.includes(uniqueArray[i]);            

	         if(submitButtonFlag =="unchecked") {
				if(uncheckboxArray[columnNumber].length >0) {
	            	if(checkboxArray[columnNumber].includes(uniqueArray[i]) == false){	
	            		checkboxArray[columnNumber].push(uniqueArray[i]);
	            		
						var indx = uncheckboxArray[columnNumber].indexOf(uniqueArray[i]);
						if (indx > -1) {
							uncheckboxArray[columnNumber].splice(indx, 1);
						}	
					}
	                checked = true;
	            }
            }
	        else if(submitButtonFlag =="checked" && filteredUncheckedArray.length >0) {

            	if(window["uncheckedCheckboxes_" + columnNumber].includes(uniqueArray[i]) == true){	
					
            		var indx = checkboxArray[columnNumber].indexOf(uniqueArray[i]);
					if (indx > -1) {
						checkboxArray[columnNumber].splice(indx, 1);
					}
            		uncheckboxArray[columnNumber].push(uniqueArray[i]);
            		
					var indx = filteredUncheckedArray.indexOf(uniqueArray[i]);
					if (indx > -1) {
						filteredUncheckedArray.splice(indx, 1);
					}	
				}
                checked = false;
            }            
            // in the first time clicked, the condition will be that the value exist in available array and also in the checkbox array
            // the second time after filter, the value will exist in available array but will check if exist in checkbox array or not


            if((uniqueFilteredArray.includes(uniqueArray[i]) && checkboxArray[columnNumber].includes(uniqueArray[i])) 
                    || (!uniqueFilteredArray.includes(uniqueArray[i]) && checkboxArray[columnNumber].includes(uniqueArray[i]))){
            	checked = true
             }

            if((uniqueFilteredArray.includes(uniqueArray[i]) && !checkboxArray[columnNumber].includes(uniqueArray[i])) 
                    || (!uniqueFilteredArray.includes(uniqueArray[i]) && !checkboxArray[columnNumber].includes(uniqueArray[i])) ){
                  
            	checked = false
             }
            
            /*if(existinfilteredArray === true || (existinfilteredArray === false &&  FilteredArrayLength ===0)){
                checked = true
            }else{
                checked = false
            }*/

            let label_classname = ""
            let disabledCheckbox = ""
            if(existinfilteredArray === false &&  FilteredArrayLength !==0){
                label_classname = "not-available-value"
                disabledCheckbox = "disabled"
            }

            //console.log("##### "+uniqueArray[i])
            rows.push({
                values: [uniqueArray[i]],
                markup: "<div class='row custom-filter-row'><div class='col-md-12'>" +
                    "<div class='form-check'>" +
                    "<input type='checkbox' data-element-index='" + i + "' class='form-check-input custom-filter-checkbox' checked value='" + uniqueArray[i] + "' "+disabledCheckbox+">" +
                    "<label class='form-check-label custom-filter-label "+label_classname+"'  >" + uniqueArray[i] + "</label>" +
                    "</div></div></div>",
                markupunchecked: "<div class='row custom-filter-row'><div class='col-md-12'>" +
                    "<div class='form-check'>" +
                    "<input type='checkbox' data-element-index='" + i + "' class='form-check-input custom-filter-checkbox' value='" + uniqueArray[i] + "' "+disabledCheckbox+">" +
                    "<label class='form-check-label custom-filter-label "+label_classname+"'>" + uniqueArray[i] + "</label>" +
                    "</div></div></div>",
                indexval: uniqueArray[i],
                checked: checked,
                active: true,
                disabledCheckbox: disabledCheckbox,
                existinfilteredArray : existinfilteredArray,
                id:i
            });
            if(existinfilteredArray == true) {
         	   existedValues.push(rows[i])
            }
			currentAppendedRows=rows;

        }

        /*let existedValues = rows.filter(function(arr){
            return arr.existinfilteredArray === true;
        })*/
        
        var searchResults=[];
        var filterRows = function (rows,appendType,currentForm) {
        	searchResults=[];
        	var results = [];
            if(FilteredArrayLength > 0 && showavailable.checked){

                for (var i = 0, ii = existedValues.length; i < ii; i++) {
                    if (existedValues[i].active) {
                        if (existedValues[i].checked) {
                            results.push(existedValues[i].markup);
                        }
                        else {
                            results.push(existedValues[i].markupunchecked);
                            // results.push($(rows[i].markup)
                            //     .find('.custom-filter-checkbox')
                            //     .attr('checked', false)
                            //     .end()
                            //     .get(0).outerHTML
                            // );
                        }
                        searchResults.push(existedValues[i]);
                    }
                }
            }
            else { 
                
                for (var i = 0, ii = rows.length; i < ii; i++) {
                    if (rows[i].active) {
                        if (rows[i].checked) {
                            results.push(rows[i].markup);
                        }
                        else {
                            results.push(rows[i].markupunchecked);
                            // results.push($(rows[i].markup)
                            //     .find('.custom-filter-checkbox')
                            //     .attr('checked', false)
                            //     .end()
                            //     .get(0).outerHTML
                            // );
                        }
                        searchResults.push(rows[i]);

                    }
                }
            }
            if(appendType =="firstAppend") {
            	 var filteredResults=[];
                 filteredResults =filterfirstAppendRows(results,currentForm);
                 return filteredResults;
            }
            else if(appendType =="samePageAppend") {
            	var filteredResults=[];
                filteredResults =filterSamePageAppendRows(results,currentForm);
                return filteredResults;
            }
            else {
                 return results;
            }
           
        }
        
       var filterfirstAppendRows = function (results,currentForm) {
            var firstFilteredResults = [];            
           if(currentForm !="") {
         	   
         	if(results.length==0) {
         		$("#" +currentForm).find(".nextPrevPage").val("0");	
                 $('#'+currentForm).find('.nextData').prop('disabled', true);
                 $('#'+currentForm).find('.previousData').prop('disabled', true);
              }
         	else {
 	        	$("#" +currentForm).find(".nextPrevPage").val("1");	
 	           	$('#'+currentForm).find('.nextData').prop('disabled', false);
 	          	$('#'+currentForm).find('.previousData').prop('disabled', true);
         	}
             var maxNoOfPages = results.length/30000;
               
            	if(String(maxNoOfPages).includes(".")==true) {
            	  		maxNoOfPages=String(maxNoOfPages).split(".")[0];
            	  		maxNoOfPages = parseInt(maxNoOfPages)+1;
            	 }
            	$("#" +currentForm).find(".totalNoPages").val(maxNoOfPages);
           
            	if(maxNoOfPages== $("#" +currentForm).find(".nextPrevPage").val()) {
            	 	$('#'+currentForm).find('.nextData').prop('disabled', true);
            	 	$('#'+currentForm).find('.previousData').prop('disabled', true);
               	
            	}
           }
           if(results.length > 30000) {
         	  var arryLngth= 30000;
           }
           else {
         	  var arryLngth= results.length;
           }
           
            for(var x= 0; x<arryLngth;x++)
          	   firstFilteredResults.push(results[x]); 
            
            return firstFilteredResults;

         }
      
       var fltrSamePgApndRowsForShowAvailable = function (results,currentForm,FilteredCheckedArrResults,searchVal) {
           	var firstFilteredResults = [];            
       		endIndex=parseInt(finalCount);
       		startIndex =parseInt(finalCount)-30000;
       	
       	for(var x= startIndex; x<endIndex;x++) { 
       		if(FilteredArrayLength > 0 && showavailable.checked && searchVal ==""){
       			if (parseInt(x) < parseInt(existedValues.length) ) {
             		if(FilteredCheckedArrResults.includes(existedValues[x]["indexval"])==true ) { 
             			var cur =  parseInt(existedValues[x]["id"]);
             			firstFilteredResults.push(rows[cur].markup);
             		}
       			}
               
       		 }
             else if(FilteredArrayLength > 0 && showavailable.checked && searchVal !=""){
          		 if (parseInt(x) < parseInt(searchResults.length) ) {
                   if(FilteredCheckedArrResults.includes(searchResults[x]["indexval"])==true ) { 
	           			 var cur =  parseInt(searchResults[x]["id"]);
	           			 firstFilteredResults.push(rows[cur].markup);
                    }
          		 }
              }
               else {
            	   if(searchVal =="") {
               		if (parseInt(x) < parseInt(rows.length) ) {
               			if(FilteredCheckedArrResults.includes(rows[x]["indexval"])==true) {
                   			firstFilteredResults.push(rows[x].markup);
               			}
                 	}
               	}
               	else {
               		 if (parseInt(x) < parseInt(searchResults.length) ) {
                            if(FilteredCheckedArrResults.includes(searchResults[x]["indexval"])==true ) { 
        	           			 var cur =  parseInt(searchResults[x]["id"]);
        	           			 firstFilteredResults.push(rows[cur].markup);
                             }
               		 }
               	}       		
               
              }
       	  }
           return firstFilteredResults;
        }        
       
       var filterSamePageAppendRows = function (results,currentForm) {
           	var firstFilteredResults = [];           
       		endIndex=parseInt(finalCount);
       		startIndex =parseInt(finalCount)-30000;
       		
       		if( endIndex < results.length ) {
         	  var arryLngth= endIndex;
           }
           else {
         	  var arryLngth= results.length;
           }
       		
           for(var x= startIndex; x<arryLngth;x++)
         	   firstFilteredResults.push(results[x]); 
           
           return firstFilteredResults;

        }      
      
     var filterRowsNext = function (nextResult,nextPageNum,maxNoOfPages) {
       	
           if(nextPageNum =='1') {
           	startIndex ="0";
           	endIndex="30000";
           	finalCount ="30000";
           }
           else {
           	startIndex =parseInt(finalCount);
           	endIndex=parseInt(finalCount)+30000;
           	finalCount =parseInt(finalCount)+30000;
           }
           var nextFilteredResults = [];            
           var searchVal = $('#'+tableId + 'Search' + columnNumber).val();
           
           for(var x= startIndex; x<endIndex;x++) {
       		
           	if (showSelectedStatus.checked && showavailable.checked && searchVal =="") {
           		if (parseInt(x) < parseInt(existedValues.length) ) {
           		 if(uncheckboxArray[columnNumber].includes(existedValues[x]["indexval"])==false ) { 
            			var cur =  parseInt(existedValues[x]["id"]);
            			nextFilteredResults.push(rows[cur].markup);
            		}    
          		 }
           	}
           	 else if(showSelectedStatus.checked && showavailable.checked && searchVal !=""){
           		 if (parseInt(x) < parseInt(searchResults.length) ) {
           			 if(uncheckboxArray[columnNumber].includes(searchResults[x]["indexval"])==false ) { 
    	           		var cur =  parseInt(searchResults[x]["id"]);
    	           		nextFilteredResults.push(rows[cur].markup);
                      }
           		 }
            }
          else {
           	if(showSelectedStatus.checked ) {
           		if (parseInt(x) < parseInt(searchResults.length) ) {
           			if(searchResults[x]["checked"]==true) {
           				 var cur =  parseInt(searchResults[x]["id"]);
                 			 nextFilteredResults.push(rows[cur].markup);
           			}
           		}
           	}
           	else if(showavailable.checked || showAllStatus.checked ) {
           		if (parseInt(x) < parseInt(searchResults.length) ) {
           			var cur =  parseInt(searchResults[x]["id"]);
                 	if(searchResults[x]["checked"] == true ){
                     	nextFilteredResults.push(rows[cur].markup);
                 	}
                 	else {
                 		nextFilteredResults.push(rows[cur].markupunchecked);
                 	}

           		}
           	}
           	else {
           		if (parseInt(x) < parseInt(searchResults.length) ) {
           			if(uncheckboxArray[columnNumber].includes(searchResults[x]["indexval"])==false ) { 
                			var cur =  parseInt(searchResults[x]["id"]);
                			nextFilteredResults.push(rows[cur].markup);
                    }
           		}
           }          		 
           	
          }
           }
           return nextFilteredResults;
    }	
   
var filterRowsPrevious = function (prevResult,prevPageNo,maxNoOfPages) {

       if(prevPageNo =='1') {
       	startIndex ="0";
       	endIndex="30000";
       	finalCount ="30000";
       }
       else {
       	startIndex =parseInt(startIndex)-30000;
       	endIndex=parseInt(finalCount)-30000;
       	finalCount =parseInt(finalCount)-30000;
       }
        
       var prevFilteredResults = [];
       var searchVal = $('#'+tableId + 'Search' + columnNumber).val();

    for(var x= startIndex; x<endIndex;x++) {    
       if (showSelectedStatus.checked && showavailable.checked && searchVal =="") {
     		 if (parseInt(x) < parseInt(existedValues.length) ) {
   		 if(uncheckboxArray[columnNumber].includes(existedValues[x]["indexval"])==false ) { 
    			var cur =  parseInt(existedValues[x]["id"]);
    			prevFilteredResults.push(rows[cur].markup);
    		}
     		 }
   	}
  	 else if(showSelectedStatus.checked && showavailable.checked && searchVal !=""){
     	
  		 if (parseInt(x) < parseInt(searchResults.length) ) {
  			 if(uncheckboxArray[columnNumber].includes(searchResults[x]["indexval"])==false ) { 
    			 var cur =  parseInt(searchResults[x]["id"]);
    			 prevFilteredResults.push(rows[cur].markup);
          }
  		 }
    }
   	else {
   		if(showSelectedStatus.checked ) {
       		if (parseInt(x) < parseInt(searchResults.length) ) {
       			if(searchResults[x]["checked"]==true) {
       				 var cur =  parseInt(searchResults[x]["id"]);
       				 prevFilteredResults.push(rows[cur].markup);
       			}
       		}
       	}
   		else if(showavailable.checked || showAllStatus.checked ) {
       		if (parseInt(x) < parseInt(searchResults.length) ) {
       			var cur =  parseInt(searchResults[x]["id"]);
             		if(searchResults[x]["checked"] == true ){
             			prevFilteredResults.push(rows[cur].markup);
             		}
             		else {
             			prevFilteredResults.push(rows[cur].markupunchecked);
             		}

       		}
       	}
   		else {
       		if (parseInt(x) < parseInt(searchResults.length) ) {
       			if(uncheckboxArray[columnNumber].includes(searchResults[x]["indexval"])==false ) { 
            		var cur =  parseInt(searchResults[x]["id"]);
            		prevFilteredResults.push(rows[cur].markup);
                  
       			}
       		}
       	}
   	}
    }
       return prevFilteredResults;      
   }	
        //////// --- sohaib --- ////////

  //Function Filter only for Selected data			
 var filterRowsForShowSelected = function (rows,currentForm) {
    var results = [];
    var FilteredCheckedArrResults=[];
    
    var searchVal = $('#'+tableId + 'Search' + columnNumber).val();
 
	if(FilteredArrayLength > 0 && showavailable.checked && searchVal ==""){
        $.each(existedValues, function (i, value) {
            if (existedValues[i].active) {
                if (existedValues[i].checked) {
                    results.push(existedValues[i].markup);
                    FilteredCheckedArrResults.push(existedValues[i]["indexval"]);

                }
            }
        });
    }
    else if(FilteredArrayLength > 0 && showavailable.checked && searchVal !=""){
    	 $.each(searchResults, function (i, value) {
             if (searchResults[i].active) {
                 if (searchResults[i].checked) {
                     results.push(searchResults[i].markup);
                     FilteredCheckedArrResults.push(searchResults[i]["indexval"]);
                 }
             }
         });
    }
    else{
    	if(searchVal =="") {

        $.each(rows, function (i, value) {
            if (rows[i].active) {
                if (rows[i].checked) {
                    results.push(rows[i].markup);
                    FilteredCheckedArrResults.push(rows[i]["indexval"]);
                }
            }
        });
    	}
    	else {
       	 $.each(searchResults, function (i, value) {
                if (searchResults[i].active) {
                    if (searchResults[i].checked) {
                        results.push(searchResults[i].markup);
                        FilteredCheckedArrResults.push(searchResults[i]["indexval"]);
                    }
                }
            });
    	}
    }
    var filteredResults=[];
    filteredResults =fltrSamePgApndRowsForShowAvailable(results,currentForm,FilteredCheckedArrResults,searchVal);

    return filteredResults;
    
 }
        //////// -------------- ////////
 let filterRowsForShowAvailable = function (rows,currentForm) {
     var results = [];
 	
     for (var i = 0, ii = rows.length; i < ii; i++) {
     	
         if (rows[i].existinfilteredArray || (existinfilteredArray === false &&  FilteredArrayLength ===0)) {
             if (rows[i].active) {
                 if (rows[i].checked) {
                     results.push(rows[i].markup);
                 }
                 else {
                     results.push(rows[i].markupunchecked);
                     // results.push($(rows[i].markup)
                     //     .find('.custom-filter-checkbox')
                     //     .attr('checked', false)
                     //     .end()
                     //     .get(0).outerHTML
                     // );
                 }

             }
         }
     }
     var filteredResults=[];
     filteredResults =filterfirstAppendRows(results,currentForm);
     return filteredResults;
 }
 
 
 clusterize = new Clusterize({
     
     rows: filterRows(rows,"firstAppend",""),
     no_data_text: '',
     scrollId: tableId + 'scrollArea' + columnNumber,
     contentId: tableId + 'contentArea' + columnNumber
 });

		 var onSearch = function () {
		     for (var i = 0, ii = rows.length; i < ii; i++) {
		         var suitable = false;
		         for (var j = 0, jj = rows[i].values.length; j < jj; j++) {
		             if (rows[i].values[j].toString().toUpperCase().indexOf(search.value.toUpperCase()) + 1)
		                 suitable = true;
		         }
		         rows[i].active = suitable;
		
		     }
		     var currentForm = $(this).parents("form").attr("id");
		     nextPageNum="1";
			 finalCount ="30000";
		     clusterize.update(filterRows(rows,"firstAppend",currentForm));
		 }
        search.oninput = onSearch;

        var onChangeSelect = function () {
        	
            if (selectAll.checked) {
              if(FilteredArrayLength > 0 && showavailable.checked){
	
			  	//Store the unchecked checkboxes first time clicking on select all only (to use this array in case we close the popup without a submit )
				if (window["submit_" + columnNumber] == "checked") {
					if (window["uncheckedCheckboxes_" + columnNumber].length === 0) {
			            existedValues.forEach(function(value) {
			                if (!value.checked) {
			                    window["uncheckedCheckboxes_" + columnNumber].push(value["indexval"]);
			                }
			            });
			         }
				}
 				  	$.each(existedValues, function (i, value) {
	                	existedValues[i]["checked"] = true;

					  if (window["uncheckedCheckboxes_" + columnNumber].includes(existedValues[i]["indexval"])== false) {

                        if(!checkboxArray[columnNumber].includes(existedValues[i]["indexval"]))
                            checkboxArray[columnNumber].push(existedValues[i]["indexval"]);

                        uncheckboxArray[columnNumber] = $.grep(uncheckboxArray[columnNumber], function (e) {
                            return e != existedValues[i]["indexval"];
    
                        });
				      }
					});
					
                    checkboxArray[columnNumber] = Array.from(new Set(checkboxArray[columnNumber]));
                    var currentForm = $(this).parents("form").attr("id");
                    clusterize.update(filterRows(existedValues,"samePageAppend",currentForm));
                }
                else{
                    
                    $.each(rows, function (i, value) {

                        if(rows[i]["disabledCheckbox"] !== "disabled"){
                            rows[i]["checked"] = true;
                            if(!checkboxArray[columnNumber].includes(rows[i]["indexval"]))
                            checkboxArray[columnNumber].push(rows[i]["indexval"]);

                            uncheckboxArray[columnNumber] = $.grep(uncheckboxArray[columnNumber], function (e) {
                                return e != rows[i]["indexval"];
        
                            });
                        }
                    });

                    checkboxArray[columnNumber] = Array.from(new Set(checkboxArray[columnNumber]));
                    
                    var currentForm = $(this).parents("form").attr("id");
                    clusterize.update(filterRows(rows,"samePageAppend",currentForm));
                }
            }

            else {
                if(FilteredArrayLength > 0 && showavailable.checked){
                    $.each(existedValues, function (i, value) {
                        existedValues[i]["checked"] = false;

                     if (window["uncheckedCheckboxes_" + columnNumber].includes(existedValues[i]["indexval"])== true) {
                        if(!uncheckboxArray[columnNumber].includes(existedValues[i]["indexval"]))
                            uncheckboxArray[columnNumber].push(existedValues[i]["indexval"]);

                        checkboxArray[columnNumber] = $.grep(checkboxArray[columnNumber], function (e) {
                            return e != existedValues[i]["indexval"];
                        });
					  }

                    });

                    uncheckboxArray[columnNumber] = Array.from(new Set(uncheckboxArray[columnNumber]));
                    
                    var currentForm = $(this).parents("form").attr("id");
                    clusterize.update(filterRows(existedValues,"samePageAppend",currentForm));
                }
                else{
                    $.each(rows, function (i, value) {

                        //console.log(rows[i]["disabledCheckbox"],rows[i]["indexval"],rows[i]["checked"])
                        if(rows[i]["disabledCheckbox"] !== "disabled"){
                            rows[i]["checked"] = false;
                            if(!uncheckboxArray[columnNumber].includes(rows[i]["indexval"]))
                            uncheckboxArray[columnNumber].push(rows[i]["indexval"]);

                            checkboxArray[columnNumber] = $.grep(checkboxArray[columnNumber], function (e) {
                                return e != rows[i]["indexval"];
                            });
                        }
                        //console.log(rows[i]["disabledCheckbox"],rows[i]["indexval"],rows[i]["checked"])
                        
                    });
                    uncheckboxArray[columnNumber] = Array.from(new Set(uncheckboxArray[columnNumber]));
                    var currentForm = $(this).parents("form").attr("id");
                    clusterize.update(filterRows(rows,"samePageAppend",currentForm));
                }

                
                
            }

        }
        selectAll.onchange = onChangeSelect;

        //////// ------ Sohaib ------ ////////

        // event to clusterize filter data according to show all					
        $("#" + tableId).on("change", ".showall-filter-checkbox", function () {
            var currentForm = $(this).parents("form").attr("id");
            clusterize.update(filterRows(rows,"firstAppend",currentForm));
        });
 
        $("#" + tableId).on("click", ".nextData", function () {
        	
        	var currentForm = $(this).parents("form").attr("id");
        	var maxNoOfPages = $("#" +currentForm).find(".totalNoPages").val();        	
        	var nextPgNo = parseInt(nextPageNum)+1;         	
        	nextPageNum=nextPgNo;
        	
        	$('#'+currentForm).find('.previousData').prop('disabled', false);
        	
	       	 if(maxNoOfPages == nextPgNo ) {
		    	 $('#'+currentForm).find('.nextData').prop('disabled', true);
		    	 $("#" +currentForm).find(".nextPrevPage").val(nextPgNo);
	
			 }
			 else {		
				 if(nextPgNo < maxNoOfPages ) {
					 $('#'+currentForm).find('.nextData').prop('disabled', false);
					 $("#" +currentForm).find(".nextPrevPage").val(nextPgNo);
	        	 }
				 else {
					 $('#'+currentForm).find('.nextData').prop('disabled', true);
	        	 }
			 }
         
	       	 var nextResult = filterRows(rows,"","");
             clusterize.update(filterRowsNext(nextResult,nextPgNo,maxNoOfPages));

        });
        
        $("#" + tableId).on("click", ".previousData", function () {
        	
        	var currentForm = $(this).parents("form").attr("id");
        	var maxNoOfPages = $("#" +currentForm).find(".totalNoPages").val();
        	var prevPageNo = parseInt(nextPageNum)-1; 
        	nextPageNum=prevPageNo;        	
        	if (prevPageNo < maxNoOfPages)
        		$('#'+currentForm).find('.nextData').prop('disabled', false);

        	if(maxNoOfPages == prevPageNo || prevPageNo =="1" ) {
        		$('#'+currentForm).find('.previousData').prop('disabled', true);
        		$("#" +currentForm).find(".nextPrevPage").val(prevPageNo);
        	}
        	else {
        	       if(prevPageNo < maxNoOfPages ) {
        	    	   $('#'+currentForm).find('.previousData').prop('disabled', false);
        	    	   $("#" +currentForm).find(".nextPrevPage").val(prevPageNo);
        	       }
        	       else {
        	    	   $('#'+currentForm).find('.previousData').prop('disabled', true);
        	       }
        	}
	       	 var prevResult = filterRows(rows,"","");
            clusterize.update(filterRowsPrevious(prevResult,prevPageNo,maxNoOfPages)); 
        });
        
        $("#" + tableId + " .filter-dropdown-ul").on("change", ".showavailable-filter-checkbox", function () {

            var currentForm = $(this).parents("form").attr("id");

            let checkedArray = existedValues.filter(function(arr){
                return arr.checked === true;
            })

            let uniquecheckedArray = rows.filter(function(arr){
                return arr.checked === true;
            })


             nextPageNum="1";
        	 finalCount ="30000";


            // if condition to select one checkbox at a time according to event			
            if (this.checked) {
                if((existedValues.length > 0  && checkedArray.length === FilteredArrayLength) || (existedValues.length === 0 && uniquecheckedArray.length === arrayLength)){
                    $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
                }
                   
                if((existedValues.length > 0  && checkedArray.length !== FilteredArrayLength) || (existedValues.length === 0 && uniquecheckedArray.length !== arrayLength) ){
                    $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);
                }
                //$("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
                $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showavailable-filter-checkbox').prop('checked', true);
                
                clusterize.update(filterRowsForShowAvailable(rows,currentForm));
                
            } else {
            	
                if(uniquecheckedArray.length === arrayLength)
                    $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
                else
                    $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', true);
                $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showavailable-filter-checkbox').prop('checked', false);
              
                clusterize.update(filterRows(rows,"firstAppend",currentForm));
            }

        });
        
        // Event For show selected check box						
        $("#" + tableId + " .filter-dropdown-ul").on("change", ".showselected-filter-checkbox", function () {
            var currentForm = $(this).parents("form").attr("id");

            let checkedArray = existedValues.filter(function(arr){
                return arr.checked === true;
            })

            let uniquecheckedArray = rows.filter(function(arr){
                return arr.checked === true;
            })

            // if condition to select one checkbox at a time according to event			
            if (this.checked) {
                if(showavailable.checked){
                
                    if((existedValues.length > 0  && checkedArray.length === FilteredArrayLength) || (existedValues.length === 0 && uniquecheckedArray.length === arrayLength)){
                        $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
                    }
                    
                    if((existedValues.length > 0  && checkedArray.length !== FilteredArrayLength) || (existedValues.length === 0 && uniquecheckedArray.length !== arrayLength) ){
                        $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);
                    }
                }
                else{
                    if(uniquecheckedArray.length === arrayLength)
                        $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
                    else
                        $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);
                }

                $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', true);
                
             
            	var arr=[];
            	clusterize.update(arr);
                clusterize.update(filterRowsForShowSelected(rows,currentForm));
            } else {
                if(!showavailable.checked){
                    $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', true);
                }
                else   
                    $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
            	var arr=[];
                clusterize.update(arr);
                clusterize.update(filterRows(rows,"samePageAppend",currentForm));
            }

        });
        //////// ------------------ ////////						


         // Event for show all 		
        $("#" + tableId + " .filter-dropdown-ul").on("change", ".showall-filter-checkbox", function () {

            var currentForm = $(this).parents("form").attr("id");

            let checkedArray = rows.filter(function(arr){
                return arr.checked === true;
            })

            nextPageNum="1";
        	finalCount ="30000";

            // if condition to select one checkbox at a time according to event	
            if(checkedArray.length === rows.length)
                $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
            else
                $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);

            if (this.checked) {
                $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', true);
                $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showavailable-filter-checkbox').prop('checked', false);
            } else {
                $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
                $("#" + currentForm).find('.showavailable-filter-checkbox').prop('checked', true);
            }

        });


        $("#" + tableId).on("change", ".custom-filter-checkbox", function () {

          	 var currentForm = $(this).parents("form").attr("id");

               var currentValue = $(this).val();
               var index = parseInt($(this).attr('data-element-index'));
               let indexV;
               
               if (typeof rows[index] != 'undefined') {

                   indexV = String(rows[index]["indexval"]).indexOf(currentValue);
                   if (indexV !== -1) {
                       if (this.checked) {
                           rows[index]["checked"] = true;
                       } else {
                           rows[index]["checked"] = false;
                       }
                   }

                   if (showSelectedStatus.checked) {
                   	
                       clusterize.update(filterRowsForShowSelected(rows,currentForm));

                   } else {
                        clusterize.update(filterRows(rows,"samePageAppend",currentForm));
                   }
               }
           });
    }

    addEvents() {

        //var tableId = this.tableId;
        //var dataArray = this.dataArray;
        //var selectCheckbox = this.selectCheckbox;
        //var filteredArray = [];
        let almgrid = this;
        let map = new Map()
        

       // var start_scroll = 1;
       // var end_scroll = 1000;

        let availableArray = []
        if (almgrid.dataArray.length > 0) {

            // Create Arrays for Each Button
            almgrid.ArrayKeys = Object.keys(almgrid.dataArray[0]);
            var checkboxArray = [];
            var uncheckboxArray = [];
            let i
            if(almgrid.selectCheckbox === true){
                for (i = 0; i <  almgrid.ArrayKeys.length; i++) {
                    checkboxArray[i] = [];
                    uncheckboxArray[i] = [];
                }
            }
            else{
                for (i = 1; i <=  almgrid.ArrayKeys.length; i++) {
                    checkboxArray[i] = [];
                    uncheckboxArray[i] = [];
                }
            }

            document.querySelectorAll(".nav-pills li.active").forEach(item => {
                item.addEventListener('click', (e) => {
                    e.preventDefault();
                    var clickedForm = $(item).parents("form").attr("id");
                    document.getElementById("loaderDiv").style.display = "block";

                    setTimeout(function() {

                    var clickedIndex = clickedForm.split(almgrid.tableId + "_filterform");
                    var columnNumberPressed = clickedIndex[1];

                    let indexVarColPressed
                    if(almgrid.selectCheckbox === true){
                        indexVarColPressed = columnNumberPressed
                    }else{
                        indexVarColPressed = columnNumberPressed - 1
                    }

                    availableArray = almgrid.dataArray;

                    availableArray = headerFilter(availableArray)
                    
                    availableArray = dropdownFilter(availableArray,columnNumberPressed,"showavailable")

                    availableArray = globalSearchFilter(availableArray)

                  

                    var newArray = [];
                    var availablenewArray = []


                    $.each(almgrid.dataArray, function (i, value) {
                        newArray.push(almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]]);

                        let checkValue = obj => obj[almgrid.ArrayKeys[indexVarColPressed]] === almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]];

                        if(availableArray.some(checkValue) && !availablenewArray.includes(almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]])){
                            availablenewArray.push(almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]]);
                        
                        }
                    });
                    

                    let uniqueArray = Array.from(new Set(newArray));
                    

                    let startIndex="0";
                    let endIndex="30000";
                    let finalCount = "30000";
                    let nextPageNum="1";
                    almgrid.drawCustomFilters(window["uncheckedCheckboxes_" + columnNumberPressed],window["submit_" + columnNumberPressed],columnNumberPressed, clickedForm, uniqueArray, availablenewArray,checkboxArray,uncheckboxArray,"0","30000","30000","1");
                    document.getElementById("loaderDiv").style.display = "none";

                })
                }, 0); // Simulating 0 second delay

            })
            
            // Draw Custom Filter one time only            

  			document.querySelectorAll(".almgrid-filter").forEach(item => {
                item.addEventListener('click', (e) => {
                e.preventDefault();			  		
					var clickedForm = $(item).next().find("form").attr("id");                   
			 		var clickedIndex = clickedForm.split(almgrid.tableId + "_filterform");
                    var columnNumberPressed = clickedIndex[1];
 					let indexVarColPressed;
				
                    if(almgrid.selectCheckbox === true){
                        indexVarColPressed = columnNumberPressed;
                    }else{
                        indexVarColPressed = columnNumberPressed - 1;
                    }


			         if(checkboxArray[columnNumberPressed].length === 0){
                    	// Create a temporary set to store unique values
                    	var uniqueValues = new Set();

                    	// Iterate over almgrid.dataArray using a for loop instead of $.each
                    	for (var i = 0; i < almgrid.dataArray.length; i++) {
                    	    // Get the value at the desired column index
                    	    var value = almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]];

                    	    // Add the value to the uniqueValues set
                    	    uniqueValues.add(value);
                    	}

                    	// Convert the uniqueValues set back to an array
                    	checkboxArray[columnNumberPressed] = Array.from(uniqueValues);
						
                    }

 				   //Clear the popup when loading new data
                   if(clusterize !=undefined) {
                       var temp=[];
                      clusterize.update(temp);
                  	  $("#" +clickedForm).find(".totalNoPages").val("0");
                  	  $("#" +clickedForm).find(".nextPrevPage").val("0");
                   }
					if(checkboxArray[columnNumberPressed].length > 35000 ) {
						
						document.getElementById("loaderDiv").style.display = "none";
						document.getElementById("alertMsgDiv").style.display = "block";
					}
					else {	
						document.getElementById("loaderDiv").style.display = "block";
						document.getElementById("alertMsgDiv").style.display = "none";

                 setTimeout(function() {

                  
                   
                    availableArray = almgrid.dataArray;
                    availableArray = headerFilter(availableArray);
                    availableArray = dropdownFilter(availableArray,columnNumberPressed,"showavailable");
                    availableArray = globalSearchFilter(availableArray);

                    		
                    var newArray = [];
                    var availablenewArray = []

                    // need to select all the checkbox for the available options when pressed the first time
                    //if(!$(item).hasClass('clicked')){
                 /*  if(checkboxArray[columnNumberPressed].length === 0){
                       /*$.each(almgrid.dataArray, function (i, value) {
                            checkboxArray[columnNumberPressed].push(almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]]);
                            checkboxArray[columnNumberPressed] = Array.from(new Set(checkboxArray[columnNumberPressed]));

                            /*uncheckboxArray[columnNumberPressed] = $.grep(uncheckboxArray[columnNumberPressed], function (e) {
                            return e != dataArray[i][ArrayKeys[columnNumberPressed]];
                            });*/
                       // });
                    	
                    	// Create a temporary set to store unique values
                   /* 	var uniqueValues = new Set();

                    	// Iterate over almgrid.dataArray using a for loop instead of $.each
                    	for (var i = 0; i < almgrid.dataArray.length; i++) {
                    	    // Get the value at the desired column index
                    	    var value = almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]];

                    	    // Add the value to the uniqueValues set
                    	    uniqueValues.add(value);
                    	}

                    	// Convert the uniqueValues set back to an array
                    	checkboxArray[columnNumberPressed] = Array.from(uniqueValues);
						
                    }
                   	 console.log("uniqueValues "+checkboxArray[columnNumberPressed].length)                 
						*/

                    /*$.each(almgrid.dataArray, function (i, value) {
                        newArray.push(almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]]);

                        let checkValue = obj => obj[almgrid.ArrayKeys[indexVarColPressed]] === almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]];

                        if(availableArray.some(checkValue) && !availablenewArray.includes(almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]])){
                            availablenewArray.push(almgrid.dataArray[i][almgrid.ArrayKeys[indexVarColPressed]]);
                        
                        }
                    }); */   

                 // Create a Set to store unique values
                    var uniqueValues = new Set();

                    // Create an object to store available values
                    var availableValues = {};

                    // Store the target key outside the loop for performance optimization
                    var targetKey = almgrid.ArrayKeys[indexVarColPressed];

                    // Iterate over almgrid.dataArray using a for loop instead of $.each
                    for (var i = 0; i < almgrid.dataArray.length; i++) {
                        // Get the value at the desired column index
                        var value = almgrid.dataArray[i][targetKey];

                        // Add the value to the uniqueValues Set
                        uniqueValues.add(value);

                        // Check if the value is available and not yet in the availableValues object
                        if (availableArray.some(obj => obj[targetKey] === value) && !availableValues[value]) {
                            // Add the value to the availableValues object and availablenewArray
                            availableValues[value] = true;
                            availablenewArray.push(value);
                        }
                    }

                    // Convert the uniqueValues Set back to an array
                    newArray = Array.from(uniqueValues);
                    
                    let uniqueArray = Array.from(new Set(newArray));
                    let startIndex="0";
                    let endIndex="30000";
                    let finalCount = "30000";
                    let nextPageNum="1";
					
                    almgrid.drawCustomFilters(window["uncheckedCheckboxes_" + columnNumberPressed],window["submit_" + columnNumberPressed] ,columnNumberPressed, clickedForm, uniqueArray, availablenewArray,checkboxArray,uncheckboxArray,"0","30000","30000","1");
					document.getElementById("loaderDiv").style.display = "none";

                })
				}
                }, 0); // Simulating 0 second delay
            });



            // Check whether to show or hide select all checkboxes inside table
            if (almgrid.selectCheckbox == true) {
                $("#" + almgrid.tableId + " .table-select-all").show();
            } else {
                $("#" + almgrid.tableId + " .table-select-all").hide();
            }

            function headerFilter(fArray){

                let flag = 0
                let inpValue = ""
                let checkedFilter = 0
                let checkedInp = 0
                let columnNumber = 1

                let indexVarColNumber
                if(almgrid.selectCheckbox === true){
                    indexVarColNumber = columnNumber
                }else{
                    indexVarColNumber = columnNumber - 1
                }

                var inputFields = $("#" + almgrid.tableId + " .almgrid-search").filter(function () {
        
                    return $(this).val() !== ""
                })
                if (map.size === 0 && inputFields.length === 0 || map.size !== 0 && inputFields.length === 0) {
                    fArray = almgrid.dataArray;
                    checkedInp = 1
                }
                if (map.size === 0 && inputFields.length !== 0) {
        
                    checkedFilter = 1 // this flag to confirm not pass in the next condition
                    $("#" + almgrid.tableId  + " .almgrid-search").each(function (index) {
                        inpValue = $(this).val().toUpperCase();
                        var KeyName = almgrid.ArrayKeys[indexVarColNumber];
                        map.set(KeyName, inpValue)
                        fArray = getFilteredArray(fArray, KeyName, inpValue);
                        indexVarColNumber++;
                    });
                    checkedInp = 1
                }
                if (map.size !== 0 && inputFields.length !== 0 && checkedFilter === 0) {
                    $("#" + almgrid.tableId + " .almgrid-search").each(function (index) {
        
                        inpValue = $(this).val().toUpperCase();
                        var KeyName = almgrid.ArrayKeys[indexVarColNumber];
        
                        if (map.get(KeyName) !== inpValue) {
                            if (map.get(KeyName).length < inpValue.length) {
                                fArray = almgrid.filteredArray;
                            }
                            /*else if (map.get(KeyName).length > FilterValue.length && fArray.length > 0) {
                                filteredArray = fArray
                                console.log("sdsacvd")
                            }*/
                            else {
                                fArray = almgrid.dataArray;
                                flag = 1
                                return false
                            }
                            checkedInp = 1
        
                        }
                        //const first = [...map][index];
                        //console.log(`index is : ${first}`)
                        map.set(KeyName, inpValue)
                        fArray = getFilteredArray(fArray, KeyName, inpValue);
                        indexVarColNumber++;
        
                    });
                    if (flag === 1) {
                        fArray = almgrid.dataArray;
                        columnNumber = 1
                        if(almgrid.selectCheckbox === true){
                            indexVarColNumber = columnNumber
                        }else{
                            indexVarColNumber = columnNumber - 1
                        }
                        let indexNb = 1
                        //console.log("Am here !!")
                        $("#" + almgrid.tableId + " .almgrid-search").each(function (index) {
                            inpValue = $(this).val().toUpperCase();
                            var KeyName = almgrid.ArrayKeys[indexVarColNumber];
                            if (inpValue === "") {
                                indexNb++;
                            }
                            map.set(KeyName, inpValue)
                            fArray = getFilteredArray(fArray, KeyName, inpValue);
                            indexVarColNumber++;
                        });
                        //console.log(indexNb,columnNumber)
                        if (indexNb == indexVarColNumber) {
                            //console.log("search fields are all empty !!")
                            map.clear()
                        }
                    }
                    if (checkedInp === 0) {
                        fArray = almgrid.dataArray;
        
                        columnNumber = 1
                        if(almgrid.selectCheckbox === true){
                            indexVarColNumber = columnNumber
                        }else{
                            indexVarColNumber = columnNumber - 1
                        }
                        $("#" + almgrid.tableId + " .almgrid-search").each(function (index) {
                            inpValue = $(this).val().toUpperCase();
                            var KeyName = almgrid.ArrayKeys[indexVarColNumber];
                            map.set(KeyName, inpValue)
                            fArray = getFilteredArray(fArray, KeyName, inpValue);
                            indexVarColNumber++;
                        });
                    }
        
                }
        
                return fArray
        
            }

            function dropdownFilter(fArray,columnNumberPressed,showavailable){
                $("#" + almgrid.tableId + " .clicked").each(function (index) {
                    
                    var currentForm = $(this).closest("li.filter-dropdown").children("ul.filter-dropdown-ul").children("li").children(".filter-dropdown-form").attr("id");
                    var currentIndex = currentForm.split(almgrid.tableId + "_filterform");
                    var currentindex = currentIndex[1];

                    let KeyName
                    if(almgrid.selectCheckbox === true){
                        KeyName = almgrid.ArrayKeys[currentindex];
                    }else{
                        KeyName = almgrid.ArrayKeys[currentindex-1];
                    }

                    var advancedFilterOneCondition = $("#" + currentForm).find(".advanced-filter-one").val();
                    var advancedFilterOneValue = $("#" + currentForm).find(".advanced-filter-one-value").val();
                    var advancedFilterRadio = $("#" + currentForm).find("input[name='advanced-filter-radio']:checked").val();
                    var advancedFilterTwoCondition = $("#" + currentForm).find(".advanced-filter-two").val();
                    var advancedFilterTwoValue = $("#" + currentForm).find(".advanced-filter-two-value").val();

                    var fArrayPushed = [];
                    if((showavailable && columnNumberPressed !== currentindex) || !showavailable){
                        if (advancedFilterOneCondition == "none") {
                                $.each(checkboxArray[currentindex], function (i, value) {

                                    var fArrayFiltered = $.grep(fArray, function (e) {
                                        return e[KeyName] == checkboxArray[currentindex][i];
                                    });
                                    fArrayPushed = fArrayPushed.concat(fArrayFiltered);
                                });
                                fArray = fArrayPushed;
                        }
                        else{
                            fArray= advancedFilteringFunction(fArray, KeyName, advancedFilterOneCondition,advancedFilterOneValue,
                            advancedFilterRadio, advancedFilterTwoCondition, advancedFilterTwoValue);

                            $.each(checkboxArray[currentindex], function (i, value) {

                                var fArrayFiltered = $.grep(fArray, function (e) {
                                    return e[KeyName] == checkboxArray[currentindex][i];
                                });
                                fArrayPushed = fArrayPushed.concat(fArrayFiltered);
                            });
                            fArray = fArrayPushed;
                        }
                    }
                    if(showavailable && columnNumberPressed === currentindex && advancedFilterOneCondition !== "none"){
                        fArray = advancedFilteringFunction(fArray, KeyName, advancedFilterOneCondition, advancedFilterOneValue,
                            advancedFilterRadio, advancedFilterTwoCondition, advancedFilterTwoValue);
                    }

                });
                return fArray
            }

            function globalSearchFilter(fArray){
                let finalFilteredArray = [];
                let j
                
                if ($(".almgrid-global-search").val() !== "") {

                    for (var i = 0; i < fArray.length; i++) {

                        if(almgrid.selectCheckbox === true){
                            j = 1
                        }
                        else{
                            j = 0
                        }
                        for (j; j < almgrid.ArrayKeys.length; j++) {

                            if (String(fArray[i][almgrid.ArrayKeys[j]]).toUpperCase().indexOf($(".almgrid-global-search").val().toUpperCase()) > -1) {
                                finalFilteredArray.push(fArray[i]);
                                break;



                            }

                        }

                    }
                    fArray = finalFilteredArray
                }
                
                return fArray

            }
                    


            $.fn.pressEnter = function (fn) {

                return this.each(function () {
                    $(this).bind('enterPress', fn);
                    $(this).keyup(function (e) {
                        if (e.keyCode == 13) {
                            $(this).trigger("enterPress");
                        }
                    })
                });
            };
            //  Global Search Filter And Draw New Results in Grid View

            $(".almgrid-global-search").pressEnter(throttle(function () {

               
                    if (almgrid.filteredArray.length == 0)
                        almgrid.filteredArray = almgrid.dataArray;
                    else
                        almgrid.filteredArray = almgrid.filteredArray;

                    almgrid.filteredArray = headerFilter(almgrid.filteredArray)

                    almgrid.filteredArray = dropdownFilter(almgrid.filteredArray)

                    almgrid.filteredArray = globalSearchFilter(almgrid.filteredArray)

                    

                    almgrid.drawTableGrid(almgrid.tableId, almgrid.filteredArray);

            }));
            // Search Filter And Draw New Results in Grid View
            // header filter

            document.querySelectorAll(".almgrid-search").forEach(item => {
                item.addEventListener('keypress', (e) => {
                    if (e.key === "Enter") {
                        //alert(e.key  + " " + e.which);
                        if (almgrid.filteredArray.length == 0)
                            almgrid.filteredArray = almgrid.dataArray;
                        else
                            almgrid.filteredArray = almgrid.filteredArray;

                        almgrid.filteredArray = headerFilter(almgrid.filteredArray)
                    
                        almgrid.filteredArray = dropdownFilter(almgrid.filteredArray)

                        almgrid.filteredArray = globalSearchFilter(almgrid.filteredArray)

                        
                        almgrid.drawTableGrid(almgrid.tableId, almgrid.filteredArray);
                        e.preventDefault();
                    }

                })
            })

            /*$("#" + almgrid.tableId + " .almgrid-search").pressEnter(throttle(function () {


                if (almgrid.filteredArray.length == 0)
                    almgrid.filteredArray = almgrid.dataArray;
                else
                    almgrid.filteredArray = almgrid.filteredArray;


                almgrid.filteredArray = headerFilter(almgrid.filteredArray)
              
                almgrid.filteredArray = dropdownFilter(almgrid.filteredArray)

                almgrid.filteredArray = globalSearchFilter(almgrid.filteredArray)

                
                almgrid.drawTableGrid(almgrid.tableId, almgrid.filteredArray);


            }));*/






            // Make all checkboxes checked on select all
            $("#" + almgrid.tableId + " .filter-dropdown-ul").on("change", ".selectall-filter-checkbox", function () {
                var currentForm = $(this).parents("form").attr("id");
                var currentIndex = currentForm.split(almgrid.tableId + "_filterform");
                var columnNumber = currentIndex[1];
                //var ArrayKeys = Object.keys(almgrid.dataArray[0]);

               // checkboxArray[columnNumber] = [];
                //uncheckboxArray[columnNumber] = [];

                //////// --- sohaib --- ////////
                // if condition to select one checkbox at a time according to event	
                
                let showavailable = document.querySelector("#" + currentForm + " .showavailable-filter-checkbox");
                let FilteredArrayLength = almgrid.filteredArray.length

                if (this.checked) {
                    if(showavailable.checked && almgrid.FilteredArrayLength > 0)
                        $(this).addClass("prev-true");
                    else
                        $(this).removeClass("prev-true");
                    
                   // $("#" + currentForm).find('.custom-filter-checkbox').prop('checked', true);
                   // $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', false);
                    $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
                } else {
                    $(this).addClass("prev-true");
                   // $("#" + currentForm).find('.custom-filter-checkbox').prop('checked', false);
                   // $("#" + currentForm).find('.showall-filter-checkbox').prop('checked', false);
                    $("#" + currentForm).find('.showselected-filter-checkbox').prop('checked', false);
                }


            });
            //////// --- sohaib --- ////////

           

            //////// ------------- ////////


            // For unchecking Select All

            $("#" + almgrid.tableId + " .filter-dropdown-ul").on("change", ".custom-filter-checkbox", function () {
                var currentForm = $(this).parents("form").attr("id");
                var currentIndex = currentForm.split(almgrid.tableId + "_filterform");
                var columnNumber = currentIndex[1];
                var checkedValue = $(this).val();

                

               // let showavailable = document.querySelector("#" + currentForm + " .showavailable-filter-checkbox");

               if(!$(this).is(":checked")){
					$("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', false);

					if (window["submit_" + columnNumber] == "unchecked") {
	                    uncheckboxArray[columnNumber].push(checkedValue);
	                    uncheckboxArray[columnNumber] = Array.from(new Set(uncheckboxArray[columnNumber]));
	
	                    checkboxArray[columnNumber] = $.grep(checkboxArray[columnNumber], function (e) {
	                        return e != checkedValue;
	                    });
					}
				
                }
                else{
                  if (window["submit_" + columnNumber] == "unchecked") {
                    checkboxArray[columnNumber].push(checkedValue);
                    checkboxArray[columnNumber] = Array.from(new Set(checkboxArray[columnNumber]));

                    uncheckboxArray[columnNumber] = $.grep(uncheckboxArray[columnNumber], function (e) {
                        return e != checkedValue;

                    });

					
                    if(checkboxArray[columnNumber].length === almgrid.availableArrayLength || almgrid.arrayLength === checkboxArray[columnNumber].length){
                        $("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
                    }
				}
					
				else {
					 var uncheckedRows = currentAppendedRows.filter(function(row) {
				   		 return row.checked === false; // Checking for the 'checked' key to be false
					});
				
				    var numberOfUnnheckedRows = (uncheckedRows.length)-1;
					if(numberOfUnnheckedRows==0) {
						$("#" + currentForm).find('.selectall-filter-checkbox').prop('checked', true);
					}
				}
            }
                
               

            });



            // Advanced Filtering and Draw Table
            $("#" + almgrid.tableId + " .filter-dropdown-ul").on("click", ".advanced-filter-submit", function () {
                var gridButton = $(this).parents(".filter-dropdown-ul").prev(".almgrid-filter");
                

                
                var selectall_currentform = $(this).parents(".filter-dropdown-ul").find('.selectall-filter-checkbox');
                var advancedFilter_currentform = $(this).parents(".filter-dropdown-ul").find('.advanced-filter-one').val()
              
                if(selectall_currentform.is(":checked") && advancedFilter_currentform == "none"){
                    $(gridButton).removeClass("clicked");
                    $(gridButton).css("color", "#000");
					var clickedForm =  $(this).parents("form").attr("id");                    
			 		var clickedIndex = clickedForm.split(almgrid.tableId + "_filterform");
                    var columnNumberPressed = clickedIndex[1];
					window["submit_" + columnNumberPressed] = "unchecked";
					window["uncheckedCheckboxes_" + columnNumberPressed]=[];

                }
                else{
                    $(gridButton).addClass("clicked");
                    $(gridButton).css("color", "#FFFF00");
					var clickedForm =  $(this).parents("form").attr("id");                   
			 		var clickedIndex = clickedForm.split(almgrid.tableId + "_filterform");
                    var columnNumberPressed = clickedIndex[1];
					window["submit_" + columnNumberPressed] = "checked";
					window["uncheckedCheckboxes_" + columnNumberPressed]=[];
					
					window["uncheckboxArray_" +columnNumberPressed]=[];
					window["checkboxArray_" +columnNumberPressed]=[];
					
					for(var x= 0; x<currentAppendedRows.length;x++) { 
             			if(currentAppendedRows[x]["checked"] == false) {
       			 			window["uncheckboxArray_" +columnNumberPressed].push(currentAppendedRows[x]["indexval"]);
                    		window["uncheckboxArray_" +columnNumberPressed]= Array.from(new Set(window["uncheckboxArray_" +columnNumberPressed]));

               			}
						else {
							window["checkboxArray_" +columnNumberPressed].push(currentAppendedRows[x]["indexval"]);
                    		window["checkboxArray_" +columnNumberPressed]= Array.from(new Set(window["checkboxArray_" +columnNumberPressed]));						
						}
					}
					window["uncheckedCheckboxes_" + columnNumberPressed]=[];
					window["uncheckedCheckboxes_" + columnNumberPressed]=window["uncheckboxArray_" +columnNumberPressed];

					uncheckboxArray[columnNumberPressed]=window["uncheckboxArray_" +columnNumberPressed];
					checkboxArray[columnNumberPressed]=window["checkboxArray_" +columnNumberPressed];
					
                }


                    if (almgrid.filteredArray.length == 0)
                        almgrid.filteredArray = almgrid.dataArray;
                    else
                        almgrid.filteredArray = almgrid.filteredArray;

                
                    almgrid.filteredArray = headerFilter(almgrid.filteredArray)

                    almgrid.filteredArray = dropdownFilter(almgrid.filteredArray)

                    almgrid.filteredArray = globalSearchFilter(almgrid.filteredArray)

                    almgrid.drawTableGrid(almgrid.tableId, almgrid.filteredArray);
            });



            // Conditions to show/hide elements in advanced filter
            $(".filter-dropdown-ul").on("change", ".advanced-filter-one", function () {
                var currentForm = $(this).parents("form").attr("id");
                var advancedFilterOneCondition = $(this).val();
                if (advancedFilterOneCondition == "none") {
                    $("#" + currentForm).find(".advanced-filter-one-value").hide();
                    $("#" + currentForm).find(".advanced-filter-two").hide();
                    $("#" + currentForm).find(".advanced-filter-two-value").hide();

                } else if (advancedFilterOneCondition == "isempty") {
                    $("#" + currentForm).find(".advanced-filter-one-value").hide();
                    $("#" + currentForm).find(".advanced-filter-two").val("none");
                    $("#" + currentForm).find(".advanced-filter-two").show();
                    $("#" + currentForm).find(".advanced-filter-two-value").hide();

                } else {
                    $("#" + currentForm).find(".advanced-filter-one-value").show();
                    $("#" + currentForm).find(".advanced-filter-two").show();
                }
            });

            // Conditions to show/hide elements in advanced filter
            $(".filter-dropdown-ul").on("change", ".advanced-filter-two", function () {
                var currentForm = $(this).parents("form").attr("id");
                var advancedFilterTwoCondition = $(this).val();
                if (advancedFilterTwoCondition == "none") {
                    $("#" + currentForm).find(".advanced-filter-two-value").hide();

                } else if (advancedFilterTwoCondition == "isempty") {
                    $("#" + currentForm).find(".advanced-filter-two-value").hide();

                } else {
                    $("#" + currentForm).find(".advanced-filter-two-value").show();
                }
            });

            // Function Regarding table select all checkbox
            $("#" + almgrid.tableId).on("change", ".table-select-all-checkbox", function () {
                if (this.checked) {
                    $("#" + almgrid.tableId).find('.table-select-checkbox').prop('checked', true);
                } else {
                    $("#" + almgrid.tableId).find('.table-select-checkbox').prop('checked', false);
                }
            });

            // Table Select Checkbox On Double Click TemporaryEdit
            $("#" + almgrid.tableId).on("dblclick", ".filterRows", function () {
                var checkbox = $(this).find('.table-select-checkbox');

                if (checkbox.is(':checked')) {
                    $("#" + almgrid.tableId).find(checkbox).prop('checked', false);
                } else {
                    $("#" + almgrid.tableId).find(checkbox).prop('checked', true);
                }

            });
        }
    } // end of events

    drawTableGrid(tableId, dataArray) {
        let almgrid = this;
        var tableBody = document.querySelector("#" + tableId + " tbody");
        var columnLinkNb = this.columnLinkNb;
        var gridContainer = document.querySelector("#" + tableId).closest(".almgrid-container");
        var gridContainerId = tableId + "_container";
        $(gridContainer).attr('id', gridContainerId);

        $(tableBody).empty();
       /* if (dataArray.length > 0) {

            //var ArrayKeys = Object.keys(dataArray[0]);

            var itemRow = "";

            for (var i = 0; i < dataArray.length; i++) {
                itemRow += "<tr class='filterRows'>";
                for (var j = 0; j < almgrid.ArrayKeys.length; j++) {
                    if (j == 0) {
                        if (this.selectCheckbox == true) {
                            itemRow += "<td class='table-select-all'><input type='checkbox' class='table-select-checkbox' value='" + dataArray[i][almgrid.ArrayKeys[j]] + "'></td>";
                        }
                    } else {
                        itemRow += "<td >" + dataArray[i][almgrid.ArrayKeys[j]] + "</td>";
                    }

                }
                itemRow += "</tr>";
            }


            $(tableBody).append(itemRow);

        }*/


        // Method for pagination almgrid-pagecount-box
        $("#" + gridContainerId).find(".almgrid-pagecount-box").attr("id", tableId + "_pagecount");
        $("#" + gridContainerId).find(".pagination-div").attr("id", tableId + "_pagination");

        // For global search textbox
        $("#" + gridContainerId).find(".almgrid-global-search").attr("id", tableId + "_globalsearch");

        var paginationId = tableId + "_pagination";


        // Page Rows number
        var nbRows = $("#" + gridContainerId).find(".almgrid-pagecount").val();
        nbRows = parseInt(nbRows);

        this.pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb, dataArray: dataArray, ArrayKeys:almgrid.ArrayKeys,selectCheckbox:almgrid.selectCheckbox  });

        // var pagination = new Pagination({ id: paginationId, tableId: tableId, noOfRows: nbRows, columnLinkNb: columnLinkNb });

        // Drawing for the first time
        if (this.initFlag == 0) {
            var tables = document.getElementsByClassName('almgrid-table');
            for (var i = 0; i < tables.length; i++) {
                resizableGrid(tables[i]);
            }
        }


        this.initFlag++;
    }

} // end of AlmGrid Class


function resizableGrid(table) {
    var row = table.getElementsByTagName('tr')[0],
        cols = row ? row.children : undefined;
    if (!cols) return;

    for (var i = 0; i < cols.length; i++) {
        var div = createDiv(table.offsetHeight);
        cols[i].appendChild(div);
        cols[i].style.position = 'relative';
        setListeners(div);
    }
}

function createDiv(height) {
    var div = document.createElement('div');
    div.style.top = 0;
    div.style.right = 0;
    div.style.width = '5px';
    div.style.position = 'absolute';
    div.style.cursor = 'col-resize';
    /* remove backGroundColor later */
    // div.style.backgroundColor = 'red';
    div.style.userSelect = 'none';
    /* table height */
    div.style.height = height + 'px';
    return div;
}




function setListeners(div) {
    var pageX, curCol, nxtCol, curColWidth, nxtColWidth;
    div.addEventListener('mousedown', function (e) {
        curTable = e.target.closest("table");
        curTableWidth = curTable.offsetWidth

        curCol = e.target.parentElement;
        nxtCol = curCol.nextElementSibling;
        pageX = e.pageX;
        curColWidth = curCol.offsetWidth
        if (nxtCol)
            nxtColWidth = nxtCol.offsetWidth
    });

    document.addEventListener('mousemove', function (e) {
        if (curCol) {
            var diffX = e.pageX - pageX;

            if (nxtCol)
                // nxtCol.style.width = (nxtColWidth - (diffX)) + 'px';

                curCol.style.width = (curColWidth + diffX) + 'px';
            curTable.style.width = (curTableWidth + diffX) + 'px';
        }
    });

    document.addEventListener('mouseup', function (e) {
        curCol = undefined;
        nxtCol = undefined;
        pageX = undefined;
        nxtColWidth = undefined;
        curColWidth = undefined;
    });
}

// Function to Draw Drop Down Custom Fitlering
function drawDropDownCustomFilter(tableId) {
    var indexNb = 1;
    // Method to Draw Filter Button inside Table
    $("#" + tableId).find(".filter-dropdown-ul").each(function () {
        $(this).empty();
        var DropDownDiv = createFilterDropDown(tableId, indexNb);
        $(this).append(DropDownDiv);
        indexNb++;
    });

}

function createFilterDropDown(tableId, indexNb) {
    var dropDownDiv = "";
    
    /*let showSel = document.querySelector("#" + currentForm + " .showselected-filter-checkbox");
    showSel.removeEventListener('click', filterRowsForShowSelected);

    showSel.addEventListener('click', filterRowsForShowSelected);
*/
       
    dropDownDiv += "<li ><form id='" + tableId + "_filterform" + indexNb + "' ' class='filter-dropdown-form' style='margin-top:20px'  >";
    dropDownDiv += "<ul class='nav nav-pills' style='margin-top: 3px;'>";
    dropDownDiv += "<li class='active'><a class='filter-dropdown-pill active' data-toggle='pill' href='#" + tableId + "_customfilter" + indexNb + "' title='Custom Filter'>";
    dropDownDiv += "<i class='fa fa-eye' aria-hidden='true'></i></a></li>";
    dropDownDiv += "<li><a class='filter-dropdown-pill' data-toggle='pill' href='#" + tableId + "_advancedfilter" + indexNb + "' title='Advanced Filter'>";
    dropDownDiv += "<i class='fa fa-filter' aria-hidden='true'></i></a></li>";
    dropDownDiv += "</ul><br/>";

    dropDownDiv += "<div class='tab-content'  style='margin-top: -22px;' >"; //start tab content

    dropDownDiv += "<div id='" + tableId + "_customfilter" + indexNb + "' class='tab-pane fade in active show'>"; // start tab pane
    dropDownDiv += "<div class='row'><div class='col-md-12'><div class='form-check'>";
    dropDownDiv += "<input type='checkbox' name='selectall-filter-checkbox' class='form-check-input selectall-filter-checkbox' checked/>";
    dropDownDiv += "<label for='selectall-filter-checkbox' class='form-check-label custom-filter-label'>Select All</label>";
    dropDownDiv += "</div></div></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><div class='form-check'>";
    dropDownDiv += "<input type='checkbox' name='showall-filter-checkbox' class='form-check-input showall-filter-checkbox'/>";
    dropDownDiv += "<label for='showall-filter-checkbox' class='form-check-label custom-filter-label'>Show All</label>";
    dropDownDiv += "</div></div></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><div class='form-check'>";
    dropDownDiv += "<input type='checkbox' name='showselected-filter-checkbox' class='form-check-input showselected-filter-checkbox'/>";
    dropDownDiv += "<label for='showselected-filter-checkbox' class='form-check-label custom-filter-label'>Show Selected</label>";
    dropDownDiv += "</div></div></div>";

    dropDownDiv += "<div class='row'><div class='col-md-12'><div class='form-check'>";
    dropDownDiv += "<input type='checkbox' name='showavailable-filter-checkbox' class='form-check-input showavailable-filter-checkbox' checked />";
    dropDownDiv += "<label for='showavailable-filter-checkbox' class='form-check-label custom-filter-label'>Show Available</label>";
    dropDownDiv += "</div></div></div>";

    dropDownDiv += "<div class='space-div'></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><input type='text' id='" + tableId + "Search" + indexNb + "' class='form-control custom-filter-search' placeholder='Search'>";
    dropDownDiv += "</div></div><hr>";

    // dropDownDiv += "<div class='custom-filter-values'></div><hr>";
    dropDownDiv += "<div class='custom-filter-values'>";
    dropDownDiv += "<div id='" + tableId + "scrollArea" + indexNb + "' class='clusterize-scroll'>";
    dropDownDiv += "<div id='" + tableId + "contentArea" + indexNb + "' class='clusterize-content'>";
    dropDownDiv += "<div class='clusterize-no-data'>No Data</div>";
    dropDownDiv += "</div></div>";
    dropDownDiv += "</div><hr>";

  
    dropDownDiv += "<div class='row' style='white-space: nowrap;' >";
    dropDownDiv += "<div class='col-md-3'><button type='button' class='previousData prevNextButtons pagination-button shadow-none prevNextbuttonHover ' >Prev</button></div>";
    dropDownDiv += "<div class='col-md-6'><input type='text' readonly  id='" + tableId + "_nextPrevPage" + indexNb + "' class='nextPrevPage page-number-select form-control '  style='width:48px;background-color: #fff;'/>"
    		+ "<input type='text' class='totalNoPages page-number-select form-control' style='width:48px;background-color: #fff;' readonly /></div>";
    dropDownDiv += "<div class='col-md-3'><button type='button' class='nextData prevNextButtons pagination-button shadow-none prevNextbuttonHover ' style='margin-left:-20px;' >Next</button></div>";
    dropDownDiv += "</div>";
    
    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-6'></div>";
    dropDownDiv += "<div class='col-md-6'><button type='button' id='" + tableId + "_filterform" + indexNb + "' class='btn btn-primary advanced-filter-submit'>Submit</button></div>";
    dropDownDiv += "</div>";

  
    dropDownDiv += "</div>"; // end tab pane

    dropDownDiv += "<div id='" + tableId + "_advancedfilter" + indexNb + "' class='tab-pane fade'>"; //start tab pane

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-12'>";
    dropDownDiv += "<select class='form-control advanced-filter advanced-filter-one'>";
    dropDownDiv += "<option value='none'>None</option>";
    dropDownDiv += "<option value='isempty'>Is Empty</option>";
    dropDownDiv += "<option value='isequalto'>Is Equal To</option>";
    dropDownDiv += "<option value='contains'>Contains</option>";
    // dropDownDiv += "<option value='inbetween'>In Between</option>";
    dropDownDiv += "<option value='beginswith'>Begins With</option>";
    dropDownDiv += "<option value='endswith'>Ends With</option>";
    dropDownDiv += "<option value='greaterthan'>Greater Than</option>";
    dropDownDiv += "<option value='lessthan'>Less Than</option>";
    dropDownDiv += "</select>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "<div class='space-div'></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><input type='text' class='form-control advanced-filter-one-value' placeholder='Value'>";
    dropDownDiv += "</div></div><hr>";

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-12'>";
    dropDownDiv += "<div class='form-check form-check-inline'>";
    dropDownDiv += "<input class='form-check-input' type='radio' name='advanced-filter-radio' value='and' checked>";
    dropDownDiv += "<label class='form-check-label custom-filter-label'>And</label>";
    dropDownDiv += "</div>";
    dropDownDiv += "<div class='form-check form-check-inline'>";
    dropDownDiv += "<input class='form-check-input' type='radio' name='advanced-filter-radio' value='or'>";
    dropDownDiv += "<label class='form-check-label custom-filter-label'>Or</label>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "<hr>";

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-12'>";
    dropDownDiv += "<select class='form-control advanced-filter advanced-filter-two'>";
    dropDownDiv += "<option value='none'>None</option>";
    dropDownDiv += "<option value='isempty'>Is Empty</option>";
    dropDownDiv += "<option value='isequalto'>Is Equal To</option>";
    dropDownDiv += "<option value='contains'>Contains</option>";
    // dropDownDiv += "<option value='inbetween'>In Between</option>";
    dropDownDiv += "<option value='beginswith'>Begins With</option>";
    dropDownDiv += "<option value='endswith'>Ends With</option>";
    dropDownDiv += "<option value='greaterthan'>Greater Than</option>";
    dropDownDiv += "<option value='lessthan'>Less Than</option>";
    dropDownDiv += "</select>";
    dropDownDiv += "</div>";
    dropDownDiv += "</div>";
    dropDownDiv += "<div class='space-div'></div>";
    dropDownDiv += "<div class='row'><div class='col-md-12'><input type='text' class='form-control advanced-filter-two-value' placeholder='Value'>";
    dropDownDiv += "</div></div><hr>";

    dropDownDiv += "<div class='row'>";
    dropDownDiv += "<div class='col-md-6'></div>";
    dropDownDiv += "<div class='col-md-6'><button type='button' class='btn btn-primary advanced-filter-submit'>Submit</button></div>";
    dropDownDiv += "</div>";


    dropDownDiv += "</div>"; // end tab pane

    dropDownDiv += "</div>"; // end tab content

    dropDownDiv += "</form></li>";
    

    return dropDownDiv;
}


// Function to filter global array
function getFilteredArray(array, key, value) {
    return array.filter(function (e) {
        // return e[key].toUpperCase() == value;
        return String(e[key]).toUpperCase().indexOf(value) > -1;
    });
}


function advancedFilteringFunction(fArray, KeyName, advancedFilterOneCondition, advancedFilterOneValue,
    advancedFilterRadio, advancedFilterTwoCondition, advancedFilterTwoValue) {
    var fArray1 = fArray;
    var fArray2 = fArray;
    var fArrayfinal = [];

    switch (advancedFilterOneCondition) {
        case "none":
            fArray1 = fArray;
            break;

        case "isempty":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName] == ''; });
            break;

        case "isequalto":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName] == advancedFilterOneValue; });
            break;

        case "contains":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName].toUpperCase().includes(advancedFilterOneValue.toUpperCase()); });
            break;

        case "inbetween":
            // fArray1 = $.grep(fArray1, function(e){ return e[KeyName].includes(advancedFilterOneValue); });
            break;

        case "beginswith":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName].toUpperCase().startsWith(advancedFilterOneValue.toUpperCase()); });
            break;

        case "endswith":
            fArray1 = $.grep(fArray1, function (e) { return e[KeyName].toUpperCase().endsWith(advancedFilterOneValue.toUpperCase()); });
            break;

        case "greaterthan":
            fArray1 = $.grep(fArray1, function (e) { return parseInt(e[KeyName]) > parseInt(advancedFilterOneValue); });
            break;

        case "lessthan":
            fArray1 = $.grep(fArray1, function (e) { return parseInt(e[KeyName]) < parseInt(advancedFilterOneValue); });
            break;
    }

    switch (advancedFilterTwoCondition) {
        case "none":
            fArray2 = fArray;
            break;

        case "isempty":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName] == ''; });
            break;

        case "isequalto":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName] == advancedFilterTwoValue; });
            break;

        case "contains":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName].toUpperCase().includes(advancedFilterTwoValue.toUpperCase()); });
            break;

        case "inbetween":
            // fArray2 = $.grep(fArray2, function(e){ return e[KeyName].includes(advancedFilterTwoValue); });
            break;

        case "beginswith":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName].toUpperCase().startsWith(advancedFilterTwoValue.toUpperCase()); });
            break;

        case "endswith":
            fArray2 = $.grep(fArray2, function (e) { return e[KeyName].toUpperCase().endsWith(advancedFilterTwoValue.toUpperCase()); });
            break;

        case "greaterthan":
            fArray2 = $.grep(fArray2, function (e) { return parseInt(e[KeyName]) > parseInt(advancedFilterTwoValue); });
            break;

        case "lessthan":
            fArray2 = $.grep(fArray2, function (e) { return parseInt(e[KeyName]) < parseInt(advancedFilterTwoValue); });
            break;
    }


    if (advancedFilterRadio == "and") {

        fArrayfinal = fArray1.filter(function (n) { return fArray2.indexOf(n) !== -1; });

    } else if (advancedFilterRadio == "or") {
        fArrayfinal = arrayUnique(fArray1.concat(fArray2));
    }


    return fArrayfinal;

}

function arrayUnique(array) {
    var a = array.concat();
    for (var i = 0; i < a.length; ++i) {
        for (var j = i + 1; j < a.length; ++j) {
            if (a[i] === a[j])
                a.splice(j--, 1);
        }
    }

    return a;
}

function throttle(f, delay) {
    var timer = null;
    return function () {
        var context = this, args = arguments;
        clearTimeout(timer);
        timer = window.setTimeout(function () {
            f.apply(context, args);
        },
            delay || 500);
    };
}

var clusterize;
var currentAppendedRows;