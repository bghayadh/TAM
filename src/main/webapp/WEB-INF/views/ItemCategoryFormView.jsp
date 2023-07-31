<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Item Category Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.js"></script>  -->

	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>

	<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	 
<!--  <script src="${pageContext.request.contextPath}/resources/js/jquery.mcautocomplete.js"></script> -->	
	
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
     
<style>

   #popUpDiv {
position:fixed;
top: 30%;
left: 50%;
background-color:#eeeeee;
border:5px solid #08526d;
width:400px;
height:450px;
margin-left:-150px;
margin-top:-95px;

-moz-border-radius: 16px;
-webkit-border-radius: 16px;
border-radius: 16px;
box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px#000000;

z-index: 9003;
 /*above nine thousand*/}
    

            .hide-row {
                display: none;
            }

            .dot {
                height: 17px;
                width: 17px;
                background-color: chartreuse;
                border-radius: 50%;
                display: inline-block;
                margin-top: 10px;
                margin-right: 10px;
                margin-left: 10px;
            }

            svg {
                width: 200px;
                height: 70px;
            }


            th {
                text-align: center;
            }

            td {
                text-align: center;
                vertical-align: middle !important;
            }

.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					width: 350px;
				    z-index:9999;
					
	        		}
         /*   .ui-autocomplete {
                max-height: 100px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
             /*  overflow-x: hidden;
                /* add padding to account for vertical scrollbar */
             /*   padding-right: 100px;
            }*/

            .label {
                display: table-caption;
                text-align: center;
                font-size: 20px;
                font-style: bold;
            }

            #chooseCategoryButton {
                cursor: pointer;
            }


            .tree li {
                list-style-type: none;
                margin: 0;
                padding: 10px 5px 0 5px;
                position: relative
            }

            .tree li::before,
            .tree li::after {
                content: '';
                left: -20px;
                position: absolute;
                right: auto
            }

            .tree li::before {
                border-left: 1px solid #999;
                bottom: 50px;
                height: 100%;
                top: 0;
                width: 1px
            }

            .tree li::after {
                border-top: 1px solid #999;
                height: 20px;
                top: 25px;
                width: 25px
            }

            .tree li span {
                -moz-border-radius: 5px;
                -webkit-border-radius: 5px;
                /* border: 1px solid #999; */
                border-radius: 5px;
                display: inline-block;
                padding: 3px 8px;
                text-decoration: none;
                cursor: pointer
            }

            .tree li.parent_li>span {
                cursor: pointer
            }

            .tree>ul>li::before,
            .tree>ul>li::after {
                border: 0
            }

            .tree li:last-child::before {
                /* height: 30px */
            }

            .tree li.parent_li>span:hover,
            .tree li.parent_li>span:hover+ul li span {
                /* background: #eee; */
                /* border: 1px solid #94a0b4; */
                /* color: #000 */
            }

            .tree-component {
                background: #eee;
                padding: 4px;
                text-decoration: none;
                color: #474747;
            }

            .fa-folder,
            .fa-folder-open,
            .fa-circle {
                color: grey;
            }

            .tree li ul>li {
                display: none;
            }


            .*,
            *::before,
            *::after {
                box-sizing: border-box;
            }

            #cat-tree {
                color: #595959;
                font-family: "Roboto", sans-serif;
                font-size: 16px;
                font-weight: 300;
                line-height: 1.5;
            }

            .container {
                margin: 0 auto;
                padding: 0 24px;
                max-width: 960px;
            }

            /* primary header */

            .primary-header {
                padding: 24px 0;
                text-align: center;
                border-bottom: solid 2px #cfcfcf;
            }

            .primary-header__title {
                color: #393939;
                font-size: 36px;
            }

            .primary-header__title small {
                font-size: 18px;
                color: #898989;
            }

            /* content */

            .content {
                padding: 48px 0;
                border-bottom: solid 2px #cfcfcf;
            }

            .content__footer {
                margin-top: 12px;
                text-align: center;
            }

            /* footer */

            .primary-footer {
                padding: 24px 0;
                color: #898989;
                font-size: 14px;
                text-align: center;
            }
             .task:last-child {
                border-bottom: red;
                width: 205px;
            }

            /* context menu */

            .context-menu {
                display: none;
                position: absolute;
                z-index: 10;
                padding: 12px 0;
                width: 240px;
                background-color: #fff;
                border: solid 1px #dfdfdf;
                box-shadow: 1px 1px 2px #cfcfcf;
            }

            .context-menu--active {
                display: block;
            }

            .context-menu__items {
                list-style: none;
                margin: 0;
                padding: 0;
            }

            .context-menu__item {
                display: block;
                margin-bottom: 4px;
            }

            .context-menu__item:last-child {
                margin-bottom: 0;
            }

            .context-menu__link {
                display: block;
                padding: 4px 12px;
                color: #0066aa;
                text-decoration: none;
            }

            .context-menu__link:hover {
                color: #fff;
                background-color: #0066aa;
            }

            .btn-tree {
                color: #474747;
                background-color: #d8d8d8;
            }

            .selected-span {
                color: #08526d;
                font-weight: bold;
            }

            .last-node::before {
                height: 26px !important;
            }
            
            
                        
            .btn-notclickable {
            cursor : auto ;
            background-color: red;
            color : white;
            }
            
            </style> 
  
            
</head>
<body>
	
<%-- 	<c:set var = "page" value = "inventory"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="inventory" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
     <!--  end of general head page -->
<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
			<p></p>
			</div>
		
		</div>
		
		
		<div class="row second">
			
			<div class="col-md-3">
                   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">ItemCategory ID </span>
							
							<input type="text" id="itemcatID" readonly  value="${id}" class="form-control text-input"   />
												
						</div>

					</div>
			</div>
			
		
			<div class="col-md-3">								
			</div>
				
			
			<div  class="col-md-3 nextprvItems">
				<div class="form-group">
					<div  class="input-group-prepend">
					
						<span class="input-group-text">Other Item Categories</span>
							<input  type="text" id="selectnav1" 
							style="width: 430px" class="form-control text-input" />		
					</div>
				</div>
			</div>		
			<div  class="col-md-3 text-right"  >
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
						</div> 
		</div>		
		<div class="row">
				<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
						<span class="input-group-text">Created Date</span>
						<input type="text" id="createddate"  readonly value="${creationdate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
						   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lastmodifieddate" readonly value="${lastmodified}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
						</div>
				</div>
		</div>
	</div>	
			
			<div class="hide-row col-md-3 pad "></div>
		<div class=" col-md-3 nextprvItems">
				<label id="label-1"
					style="width: 80px; text-align: center; margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 53px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&rsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>
					</ul>
				</nav>
			</div>
		
		<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
		 			<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Tree View"
							onclick='window.location.href = "${pageContext.request.contextPath}/ItemCategoryTreeView"'>
							<i class="fas fa-sitemap"></i>
						</button>
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/ItemCategoryListView"'
			        			data-placement="top" title="List View"> 
			        			<i class="fa fa-bars"></i>
			        	</button>
						
			        </div>  
		        </div>
			</div>		
		</div>

<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top: 15px;">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;">INFORMATION</li>
		            
		            <li class="nav-item ml-auto">
		            	<button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
              
      
						<button type="button" id="deleteButton"
						class="btn btn-primary BtnActive">
						<i class="fa fa-trash"></i> Delete
						</button>  
		                        
						<button type="button" id="saveButton" 
						class="btn btn-primary BtnActive">
						<i class="fa fa-save"></i> Save
						</button>  </li>					
		     </ul>
		</div>
		</div>
</div>

<div class="container-fluid">
<div class="tab-content" id="custom-tabs-one-tabContent">
	<div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

	<p></p>

	<div class="row">
		<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Category Name</span>
						<input type="text" id="itemcatname" value="${title}"  class="form-control text-input"/>
					</div>
				</div>
		</div>
		
		
				<div class="col-md-4">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Category Code</span>
						<input type="text" id="itemcode" value="${itemcode}"  class="form-control text-input" />
					</div>
				</div>
		</div>
		
		<div class="col-md-4">
			<div class="input-group-prepend">
			   <span class="input-group-text">Parent Category</span>
				    
				<input type="text" id="itemcatparent" value="${parent}" style="width:700px;" class=" form-control text-input" />
			
				   <div class="input-group-append" id="chooseCategoryButton"
                                        onclick="chooseCategoryFunction()">
                                       <div class="input-group-text" style="margin-left:10px;">
                                            <i class="fa fa-sitemap fa-rotate-270"></i>
                                        </div>
                     </div> <input type="hidden" id="parentcatID" value="${pid}"/>
                     <input type="hidden" id="validateCatInp">
			</div>
		</div>
		</div>
			

 	</div>
 	<p></p>
	<div class="row">
		<div class="col-md-8">
			<div class="input-group-prepend">
				<span class="input-group-text">Description</span>
			</div>							
		</div>
	</div>

	<div class="row">
		<div class="col-md-5">
			<div class="input-group-prepend">
				<textarea name="itemcatdescription" class=" form-control ui-widget ui-widget-content ui-corner-all" cols="220" rows="8" id="itemcatdescription"> ${description} </textarea>
			</div>							
		</div>
	</div>
	
	</div>
	


</div>


</div>
           
<!-- Choose Category Modal -->
            <div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="categoryModalLabel"
                aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="categoryModalLabel">Choose Category</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div id="cat-tree" class="tree well"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <!-- <button type="button" id='CategorySave' class="btn btn-primary">Save changes</button> -->
                        </div>
                    </div>
                </div>
            </div>
   
  <div id="popUpDiv" style="display:none;">
  <div class="sendEmail" style="margin-top:50px;" >
 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Sender</span>
						<input type="text" id="email"   class="ui-widget ui-widget-content ui-corner-all form-control" />
						<input type="text" id="password" value="${password}"  class="ui-widget ui-widget-content ui-corner-all form-control" hidden/>
					
					</div>
				</div>
	</div>
	 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width:80px;">   Email To</span>
						<input type="text" id="emailTo" class="form-control text-input" />
					
					</div>
				</div>
	</div>
	<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text" style="width:80px;">   ccEmail </span>
						<input type="text" id="ccmail" class="form-control text-input" />
					
					</div>
				</div>
	</div>
 <div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Title</span>
						<input type="text" id="subject"  class="form-control text-input" />
					
					</div>
				</div>
	</div>
<div class="col-md-12">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text"> Message</span>
						<textarea  id="message"  class="form-control text-input" cols="200" rows="4" ></textarea>
					
					</div>
				</div>
	</div>
	<div class="col-md-12">
				<p></p>
				<div class="form-group"  style=" margin-left:100px; ">
	
			<button type="button" id="send"
				class="btn btn-primary BtnActive">
				<i class="fa fa-paper-plane" ></i> Send
				</button>
                        
				<button type="button" id="cancelButton"
				class="btn btn-primary BtnActive">
				<i class="fa fa-times"></i> Cancel
				</button>
								</div>
								</div>
	</div>
	</div>	
 </body>
 
 
 <script>


/////////////////////////////////////////// SEND EMAIL  ///////////////////////////////////////////////////////////////
$("#sendEmail").on("click", function () {
console.log("Helloooo in sendEmail onClick");
$("#popUpDiv").fadeIn();

});

$("#cancelButton").on("click", function () {
console.log("Helloooo in cancelButton onClick");
$("#email").val('');
$("#password").val('');
$("#emailTo").val('');
$("#ccmail").val('');
$("#subject").val('');
$("#message").val('');
$("#popUpDiv").fadeOut();
});

$("#send").on("click", function () {
console.log("Helloooo in send onClick");
if( $("#email").val()=='' || $("#emailTo").val()==''  ||  $("#subject").val()=='' || $("#message").val()=='' )
{
alert("All Fields are required");
}
else{
$("#popUpDiv").fadeOut();
}

});
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

 
 var navList = [];
 var iCode = "${id}";
 var listItemsNav = ${listItemsNav};
 var indexItm;
 console.log("aaaaaaaaaaaaaaaa"+iCode);
 if(iCode !=""){
 if(listItemsNav != "noList" && listItemsNav.length >1){
 	var size = new TextEncoder().encode(JSON.stringify(listItemsNav)).length;
 	var kiloBytes = size / 1024;
 	var megaBytes = kiloBytes / 1024;
 	var browser = platform.name;
 	if(browser == "Samsung Internet" && megaBytes >2){
 		localStorage.setItem("itemcatList", JSON.stringify(listItemsNav));
 		}else if(browser.includes("Safari") && megaBytes >5){
 			localStorage.setItem("itemcatList", JSON.stringify(listItemsNav));
 			}else if(megaBytes >10){
 				localStorage.setItem("itemcatList", JSON.stringify(listItemsNav));
 				}else{navList = listItemsNav;
 					localStorage.removeItem("itemcatList");}
 	
 		findIndex();
 	 }else{
 		 navList = JSON.parse(localStorage.getItem("itemcatList"));
 		 if(navList !=null && navList.length >1){
 			 console.log("get sto ");
 			 findIndex();
 		 }else {
 		 $(".nextprvItems").addClass("hide-row ");
 		 $(".pad").removeClass("hide-row ");
 		 }
 	 }
 }else{
 $(".nextprvItems").addClass("hide-row ");
 $(".pad").removeClass("hide-row ");
 }
 function findIndex(){
 		for(var i=0;i<navList.length;i++){
 			console.log(navList[i] +" comp "+ iCode);
 			if(navList[i] == iCode){
 				if(i == (navList.length -1)){
 					$("#btnNexta").addClass("disabled");
 					}else if(i == 0){
 						console.log("np prv");
 						$("#btnPrva").addClass("disabled");
 						}
 				indexItm = i;
 				console.log("index is "+indexItm);
 				//return;
 				}
 			
 			}
 	}


  			
  			$("input").change(function() {
 				$("#formStatus").text("Not Saved");
 				$('.dot').css({"background-color" : "orange"});
 			});


			
  			if ('${docStatus}' == "addNew") {
             $("#formStatus").text("New"); 
  			 $('.dot').css({"background-color" : "orange"});
             $(".nextprvItems").addClass("hide-row ");
             $(".pad").removeClass("hide-row ");
             }  



$(document).ready(function() {
	

////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
	$("#email").autocomplete({
	    source: function(request, response) {
	         
	    	
	           $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
	                 data: {
							"email" : request.term,
							
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListItemEmailAccounts);
	                         console.log('data in $("#email").autocomplete is :  '+ data.ListItemEmailAccounts);

	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	            
	         }, minLength:0, maxShowItems: 40, scroll:true,
			
	         select: function(event, ui) {
	        	 
	             this.value = (ui.item ? ui.item[0]  : '');
	             password.value = ui.item[1];
	             
	             return false;
	            
	         }
	        
	     }).autocomplete("instance")._renderItem = function(ul, item) {
	         return $("<li class='each'>")
	         .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	             item[0] + "</span></div>")
	         .appendTo(ul);
	 	};
	 	
	     
				$("#email").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});
				$("#password").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});
			
	 //////////////////////////////////////END OF EMAIL AUTOCOMPLETE////////////////////////////////////////		  		

	var prev = indexItm;
	var nxt = indexItm;
	if(navList != "" && navList != null){
	for(var i=0;i<5;i++){
		prev--;
		if(navList[prev] != 'undefined' && navList[prev] != null && prev >= 0){
			
			$("#selectnav").append(new Option(navList[prev], "value"));
		}			
		}
	$("#selectnav").append(new Option(navList[indexItm], "value"));
	for(var i=0;i<5;i++){
		nxt++;
		
		if(navList[nxt] != 'undefined' && navList[nxt] != null){
			
			$("#selectnav").append(new Option(navList[nxt], "value"));
		}			
		}
	$(function () {
		  $('#selectnav option').filter(function() {
		    return this.textContent == navList[indexItm];
		  }).prop('selected', true);
		});		
	
	 $("#selectnav").change(function(){
	        var selected = $('#selectnav').find(":selected").text();
	        
	        for(var i=0;i<navList.length;i++){
				if(navList[i] == selected){
					if(localStorage.getItem("itemcatList") != null){
						if(localStorage.getItem("itemcatList").length != 0){
							var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+navList[i];
							window.location.href =param;
							}		
						}else{
							var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+navList[i]+"&itemcatList=itemcatList";
							window.location.href =param;
							}
					}
				
				}
	    });
	$("#btnNext").click(function(){
		if(!$("#btnNexta").hasClass("disabled")){
			indexItm++;
			if(localStorage.getItem("itemcatList") != null){
			if(localStorage.getItem("itemcatList").length != 0){
				var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+navList[indexItm];
				window.location.href =param;
				}		
			}
		else{
			var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+navList[indexItm]+"&itemcatList=itemcatList";
			window.location.href =param;
			}
		}
		});
	
	$("#btnPrv").click(function(){
		if(!$("#btnPrva").hasClass("disabled")){
		indexItm--;
		if(localStorage.getItem("itemcatList") != null){
		if(localStorage.getItem("itemcatList").length != 0){
			var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+navList[indexItm];
			window.location.href =param;
		}
		}else{
			var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+navList[indexItm]+"&itemcatList=itemcatList";
			window.location.href =param;
			}
		}
		});
	$("#label-1").text((indexItm+1)+"/"+navList.length);
	}
	



	$("#itemcatparent").autocomplete({
		  source: debounce(function(request, response, event, ui) {
		    $.ajax({
		      type: "GET",
		      contentType: "application/json; charset=utf-8",
		      url: '${pageContext.request.contextPath}/GetAllCategory',
		      data: {
		        "itemCategory": $("#itemcatparent").val(),
		      },
		      dataType: "json",
		      success: function(data) {
		        if (data != null) {
		          response(data.ListItemCategory);
		        }
		      },
		      error: function(result) {
		        alert("Error");
		      }
		    });
		  }, 900),
		  minLength: 0,
		  maxShowItems: 40,
		  scroll: true,
		  select: function(event, ui) {
		    $("#itemcatparent").val(ui.item[0] + ":" + ui.item[1]);
		    //$("#itemcatcode").val(ui.item[2]);
		    $("#parentcatID").val(ui.item[3]);

		    return false;
		  }
		}).autocomplete("instance")._renderItem = function(ul, item) {
		  return $("<li class='each'>")
		    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		      item[0] + "</span><br><span class='desc'>" +
		      item[2] + ', ' + item[3] + "</span></div>")
		    .appendTo(ul);
		};


    $("#itemcatparent").focus(function () {
        
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });


 $("#saveButton").click(  function() {
	 console.log('saved now');
	 
	if($("#validateCatInp").val() == "catExist")
	{
		alert("This Category Name is used before");
		return false;
	}

	 
	 val =$("#createddate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Create Date is not valid');
          return false;
         
        }
	 
	 
	 val =$("#lastmodifieddate").val();
     val1 = Date.parse(val);
     if (isNaN(val1) == true) {
          alert(' Modified Date is not valid');
          return false;
          
        }


	    
	 
	 var itemcatID = document.getElementById("itemcatID").value;
	 var creationDate = document.getElementById("createddate").value;
	 var lastModifieddate = document.getElementById("lastmodifieddate").value;
	 var itemcatName = document.getElementById("itemcatname").value;
	 //var itemcatParent = document.getElementById("itemcatparent").value;
	 var itemcatDescription = document.getElementById("itemcatdescription").value;
	 var itemCode = document.getElementById("itemcode").value;

		var Categoryparent = document.getElementById("itemcatparent").value;
		var categoryparent = Categoryparent.split(":");
		var itemcatParent = categoryparent[0];
		
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ItemCategoryFormSave",
			dataType : "json",
			data : {
				"type": '${docStatus}',
				"categoryId":$("#parentcatID").val(),
				"itemcatID" : $("#itemcatID").val(),
				"creationDate" : $("#createddate").val(),
				"lastModifieddate" : $("#lastmodifieddate").val(),
				"itemcatName" : $("#itemcatname").val(),
				//"itemcatParent" : $("#itemcatparent").val(),
				"itemcatParent" : itemcatParent,
				"itemcatDescription" : $("#itemcatdescription").val(),
				"itemCode": $("#itemcode").val(),
				"email": $("#email").val(),
				"password":$("#password").val(),
		  	    "emailTo": $("#emailTo").val(),
			    "ccmail": $("#ccmail").val(),
			    "subject": $("#subject").val(),
			    "message": $("#message").val(),
			},
			success : function(data) {
				console.log("The returned data is " +data.BassamTest);
				console.log(data.countList);
				if(data.countList!=0){
					alert("Already exit");
					return false;

					}
				$('#lastmodifieddate').val(data.lastModifieddate);
				
				$("#itemcatID").val(data.itemcatID);
			    var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+$("#itemcatID").val()+"&itemcatList=itemcatList";
				location.replace(param);
			},
			error : function(error) {
				
				console.log("The error is " + error);
			}
		});
	 
 })

});
 /* ----------- Script Part Related to Category Tree ----------- */

 function chooseCategoryFunction() {

     $("#cat-tree").empty();

     $.ajax({
         type: "GET",
         url: "${pageContext.request.contextPath}/getCategories",
         dataType: "json",
         data: {
             "categoryAll": "All"
         },
         success: function (data) {
             var lst = $.parseJSON(data.ListOfsubtree);

             var right = [];
             var row = [];

        
             for (n = 0; n < lst.length; n++) {

                 title_p1 = lst[n][3];
                 lft_p2 = lst[n][1];
                 rgt_p3 = lst[n][2];
                 id_p0 = lst[n][0];
                 code_p4 = lst[n][4];

                 row.push({
                     title: title_p1,
                     lft: lft_p2,
                     rgt: rgt_p3,
                     id: id_p0,
                     code: code_p4
                 });
             }

             i = 0;
             str = "<ul><li data-id='item_" + row[0]["id"] + "'>";

             for (i = 0; i < row.length; i++) {

                 var d = right.length;
                 var length = right.length;

                 for (j = 0; j < length; j++) {

                     lastValue_right = parseInt(right[d - 1]);

                     d = d - 1;

                     row_right = parseInt(row[i]['rgt']);

                     if (lastValue_right < row_right) {
                         right.pop();
                         str += "</li></ul>";
                     }
                 }

                 if (right.length != 0)
                     str += "<ul><li data-id='item_" + row[i]["id"] + "'>";

                 str += "<span class='tree-span'><i class='fa fa-folder'></i>" + row[i]["title"] + " - " + row[i]["code"] + "</span>";

                 right.push(row[i]["rgt"]);
             }

             $("#cat-tree").append(str);

             buildtree();

             $('.tree li > span').on('click', function (e) {

                 $("#tree-actions").remove();
                 $(".tree li > span").removeClass("selected-span");

                 $(this).addClass("selected-span");
                 
                 var action_div = "<div id='tree-actions' class='btn-group btn-group-sm' role='group' aria-label='Action Buttons'>";
                 action_div += "<button type='button' id='selectCategoryButton' class='btn btn-tree' onClick='selectCategoryFunction(this)'>Select</button>";
                 action_div += "</div>";

                 $(this).after(action_div);
            
                 e.stopPropagation();
             });

             $('#categoryModal').modal('show');
         },
         error: function (error) {
             console.log("The error is " + error);
         }
     });

 }

 function buildtree() {
     $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr(
         'title', 'Expand this branch');

     $('.tree li.parent_li > span').on(
         'click',
         function (e) {
             var children = $(this).parent('li.parent_li').find(
                 ' > ul > li');
             if (children.is(":visible")) {
                 children.hide('fast');
                 $(this).attr('title', 'Expand this branch').find(
                     ' > svg').addClass('fa-folder').removeClass(
                         'fa-folder-open');

             } else {
                 children.show('fast');
                 $(this).attr('title', 'Collapse this branch').find(
                     ' > svg').addClass('fa-folder-open')
                     .removeClass('fa-folder');
             }

             e.stopPropagation();
         });

     $("#cat-tree ul li").each(function () {
         if ($(this).find("ul").length) {
         } else {
             $(this).attr('title', 'Expand this branch').find(
                 ' > span > i').addClass('fa-circle')
                 .removeClass('fa-folder');

             $(this).attr('title', 'Expand this branch').find(
                 ' > span > i').css('font-size', '0.6rem');

         }
     });

     $("i").css('margin-right', '5px');

     var last_ul = $(".parent_li").find("ul:last-child");
		var last_li = $(last_ul).find("li:last-child");			
		$(last_ul).addClass("last-ul");
		$(".last-ul").children("li").addClass("last-node");

 }

 function selectCategoryFunction(e) {

		console.log("Inside select category function");
	    $("#formStatus").text("Not Saved");
	    $('.dot').css({ "background-color": "orange" });
	    
	    var categoryID = $(e).parents('li').data('id');
	    var categorySpan = $(e).parents('li').children(':eq(0)').text();
	    var categoryspan = categorySpan.split(" - ");
	    var categoryName = categoryspan[0];
	    var categoryCode = categoryspan[1];
	    
	    $('#currentNode').removeAttr('id');
	    var currentNode = $(e).parents('li').children(':eq(0)');
	    $(currentNode).attr('id', 'currentNode');

	    var categoryId = categoryID.split("_");
	    categoryId = categoryId[1];

	    $("#itemcatparent").val(categoryName + ":" + categoryCode);  
	  
		if(categoryId == 0){
			document.getElementById("parentcatID").value = '0';
			}
		else{
			document.getElementById("parentcatID").value = categoryId;
		   }	   
    $('#categoryModal').modal('hide');    
}



///delete form view
$("#deleteButton").click(  function() {

	 var deleteArray=[];
	var categoryId = document.getElementById("itemcatID").value;
	 deleteArray.push(categoryId);
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/ItemCategoryListViewDelete",
			dataType : "json",
			data : {
				"categories" : deleteArray
			},
			success : function(data) {
				//console.log("The returned data is " +data.BassamTest);
				//window.history.back();
			    location.replace("${pageContext.request.contextPath}/ItemCategoryListView");
			},
			error : function(error) {
				console.log("The error is " + error);
			}
		});
	 
 })


  $("#selectnav1").autocomplete({
	  source: debounce(function(request, response, event, ui) {
		    $.ajax({
		      type: "GET",
		      contentType: "application/json; charset=utf-8",
		      url: '${pageContext.request.contextPath}/GetAllCategory',
		      data: {
		        "itemCategory": $("#selectnav1").val(),
		      },
		      dataType: "json",
		      success: function(data) {
		        if (data != null) {
		          response(data.ListItemCategory);
		        }
		      },
		      error: function(result) {
		        alert("Error");
		      }
		    });
		  }, 900),
		  minLength: 0,
		  maxShowItems: 40,
		  scroll: true,
		  select: function(event, ui) {
			  this.value = (ui.item ? ui.item[2] + ":" + ui.item[0] : '');
				var param ="${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID="+(ui.item[3])+"&itemcatList=itemcatList";
				window.location.href =param;
					
					return false;
				}

		   
		  
		}).autocomplete("instance")._renderItem = function(ul, item) {
		  return $("<li class='each'>")
		    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
		      item[0] + "</span><br><span class='desc'>" +
		      item[2] + ', ' + item[3] + "</span></div>")
		    .appendTo(ul);
		};


  $("#selectnav1").focus(function () {
      
      if (this.value == "") {
          $(this).autocomplete("search");
      }
  });

  function debounce(fn, delay) {
	    var timer;
	    return function() {
	      var args = [].slice.call(arguments);
	      var context = this;
	      if (timer) {
	        window.clearTimeout(timer);
	      }
	      timer = window.setTimeout(function() {
	        fn.apply(context, args);
	      }, delay);
	    };
	  };
</script>
 
 
	
 </html>
