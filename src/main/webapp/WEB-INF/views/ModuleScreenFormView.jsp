 <!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title>Module Screen Form View</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />	
	<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">	
	
	<!-- To be Removed 
	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
	-->
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
	
	
	<!-- To be Removed 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
	-->
	
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
	
	<!-- To be Removed 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
	-->
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">

	
    <style>
    
      
        /*Doaa's popup Email Div'*/
    
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
				.ui-autocomplete {
	            	max-height: 250px;
					overflow-y: auto; /* prevent horizontal scrollbar */
					overflow-x: both; /* add padding to account for vertical scrollbar */
					padding-right: 10px;
					z-index:9999;
					width: 250px;
	        		}
    
    th{text-align:center;} 
	td {text-align: center;vertical-align: middle!important;}
    .input-group-text {
				cursor: pointer;
				
				}
				
				.nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
            
				
    .hide-row { display:none; }
    
				select
				{
					min-height: 38px;
				}
				.fixed-headerr{
					opacity: 1;
					filter: alpha(opacity=100);
				 	background: #ebf2ef;
				  	position: sticky;
				  	top: 0;
				  	z-index: 15;
				
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
				
				
				
     /*  			.ui-autocomplete {
	            	max-height: 300px;
					overflow-y: auto; /* prevent horizontal scrollbar */
				/*	overflow-x: both; /* add padding to account for vertical scrollbar */
				/*	padding-right: 100px;
					z-index:9999;
	        		}*/
	 .modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
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

            /* tasks */

            .tasks {
                /* list-style: none;
  margin: 0;
  padding: 0;*/
            }

            .task {
                /*display: flex;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: solid 1px #dfdfdf;*/
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
        	
        	
        	 .modal-header .btnGrp{
      position: absolute;
      top: 8px;
      right: 10px;
    }
    
.custom-class-assignedto-modal .modal-dialog {
  width: 100%;
}
.custom-class-assignedto-modal .modal-body {
  height: 500px;
  overflow : auto;
}
  

.display-none{display: none;}
    
    

button .fa{
  font-size: 16px;
  margin-left: 10px;
  }

   

button:focus { outline: none; }

	#Disappear, #Maintain, #RunIt 
	{
		cursor: pointer;
	} 

 		
/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .input[type=text] {
    width: 100%;
    margin-top: 0;
  }
}



/* Extra small devices (phones, 600px and down) */
@media only screen and (max-width: 600px) {
  .modal-content, .modal-dialog {
  max-height: 100%;
       max-width: 100%;
  
 }
}
/* Small devices (portrait tablets and large phones, 600px and up) */
@media only screen and (max-width: 600px) {
.modal-content, .modal-dialog {
max-height: 100%;
        max-width: 100%;

}
}

/* Medium devices (landscape tablets, 768px and up) */
@media only screen and (max-width: 768px) {
 .modal-content, .modal-dialog {
  max-height: 100%;
        max-width: 100%;
  
}
} 

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (max-width: 992px) {
  .modal-content, .modal-dialog {
  
  max-height: 100%;
        max-width: 100%;
  
 }
} 

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (max-width: 1200px) {
 .modal-content, .modal-dialog {
 
 max-height: 100%;
 max-width: 100%;
}
}
.btn-pop {
    background-color: #C2CBC0 !important;
    border-color: #C2CBC0;
    3
}

.btn-pop:hover {
    color: #fff;
    background-color: #8696A0 !important;
    border-color: #8696A0 !important;
}
    
	        		 
 	</style>
            
</head>
<body style="background: #f1f4f7">
  
  
  <c:set var="pg" value="setup" scope="session"  />
<jsp:include page="header.jsp"></jsp:include>

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
							<span class="input-group-text" style="color:green">Module Screen</span>
							<input type="text" id="moduleScreenId" readonly  value="${id}" class="form-control text-input"  />						
						</div>

					</div>
			</div>
			
			<div style="width:380px;" ></div>
			
		
		<div class="pad col-md-3 hide-row"></div>
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Module Screen</span>
						<input type="text" id="selectnav" value="${selectnav}"
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
					<input type="text" id="createdate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="lstmodifdate" readonly value="${lastModificationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
						</div>
				</div>
		</div>
	</div>
	
		 <div class="hide-row col-md-3 pad "></div> 
	
	<div class=" col-md-3 nextprvItems">
			<label id="label-1" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
			  		<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 51px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 51px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 51px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>						  		
							</ul>
				</nav>
		</div>
			
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> <i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/ModuleScreenListView"'
			        			data-placement="top" title="List View"> 
			        			<i class="fa fa-bars"></i>
			        	</button>
						<button type="button" class="btn btn-light" data-toggle="tooltip"
							data-placement="top" title="Search">
							<i class="fa fa-search"></i>
						</button>
			        </div>  
		        </div>
			</div>
		</div>
		
		</div>
	
<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            
       
            
         
            
              
             
           	
            
            <li class="nav-item ml-auto">
                        
    	        
    	        <button type="button" 
				onclick='window.location.href = "${pageContext.request.contextPath}/ModuleScreenFormView?type=addNew"'
						class="btn btn-primary BtnActive">
						<i class="fa fa-plus"></i> Add
						</button>  
						
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
	
	<div class="container-fluid">
	<div class="tab-content" id="custom-tabs-one-tabContent">
	<div class="tab-pane fade show active" id="custom-tabs-one-home"
	role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">

<p></p>
    
<div class="row">
	

       
        
     
           <div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Screen Name</span>
				<input type="text" id="screenName"  value="${screenName}" style="width:600px;" class="form-control text-input"/>
			</div>
		</div>
	</div>

	

          <div class="col-md-4">
            <div class="form-group">
                <div class="input-group-prepend">
                       <span class="input-group-text">Screen Table</span>
             <select id="screenTable" name="screenTable" class="form-control">
                        <!-- Default option -->
                        <option value="">Choose Table</option>
                        <!-- Dynamic options -->
                        <c:forEach var="tableName" items="${TablesList}">
                            <option value="${tableName}" 
                                    <c:if test="${tableName == screenTable}">selected</c:if>>
                                ${tableName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div></div>         
            
        
	
	
 </div></div></div>         
 </body>
 
 
 <script type='text/javascript'>

 
 
 </script>	
 
 
 
 <script>
 
 
     
 if ('${status}' == "AddNew") {
	          // set status to new and green while new record
	           $("#formStatus").text("New");
					$('.dot').css({"background-color" : "orange"});
					$(".nextprvItems").addClass("hide-row");
					$(".pad").removeClass("hide-row ");
	       }	

 
 
     		$("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
			});
     		  $("#screenTable").change(function() {
     		        $("#formStatus").text("Not Saved");
     		        $('.dot').css({"background-color" : "orange"});
     		    });
     		
			
     		$("#saveButton").click(function() {
     		    flagFrom = "save";
     		    
     		    // Validate item code cannot be null
     		    if ($("#screenName").val() == '') {
     		        alert('Screen Name cannot be Null');
     		        return false;
     		    }
     		    
     		    if ($("#screenTable").val() == '') {
     		        alert('Screen Table cannot be Null');
     		        return false;
     		    }

     		    var screenName = $("#screenName").val();
     		    var screenTable = $("#screenTable").val();
     		    var createdate = $("#createdate").val(); // Fixed to use the correct field ID
     		    var moduleScreenId = $("#moduleScreenId").val();

     		    $.ajax({
     		        type: "GET",
     		        url: "${pageContext.request.contextPath}/ModuleScreenFormSave",
     		        dataType: "json",
     		        data: {
     		            "screenName": screenName,
     		            "screenTable": screenTable,
     		            "createdate": createdate,
     		            "moduleScreenId": moduleScreenId,
     		        },
     		        success: function(data) {
         		        if(data.ID){
     		        	$("#moduleScreenId").val(data.ID);
     				
     		            console.log("The returned data is " + data.BassamTest);
     		            var param = "${pageContext.request.contextPath}/ModuleScreenFormView?ID=" + $("#moduleScreenId").val() + "&NavAction=2";
     		            location.replace(param);
     		        }
         		        else{

             		        alert("Screen Name already used");

         		        	 var param = "${pageContext.request.contextPath}/ModuleScreenListView";
         		        	location.replace(param);

             		        }

         		        }
     		        ,
     		        error: function(error) {
     		            console.log("The error is " + error);
     		        }
     		    });
     		});

     		$("#deleteButton").click(function() {
     		     var moduleScreenId = $("#moduleScreenId").val();

     		    $.ajax({
     		        type: "GET",
     		        url: "${pageContext.request.contextPath}/ModuleScreenDelForm",
     		        dataType: "json",
     		        data: {
     		               "moduleScreenId": moduleScreenId,
     		        },
     		        success: function(data) {
         		        console.log(data.Test);
     		              var param = "${pageContext.request.contextPath}/ModuleScreenListView";
     		             window.location.href = param;
     		        },
     		        error: function(error) {
     		            console.log("The error is " + error);
     		        }
     		    });
     		});
     		
	    		    
											
 </script>
 

<script type='text/javascript'>


$(document).ready(function() {
    var ctx = window.location.pathname;


  
    var SelectedIndex = '${SelectedIndex}' != "addNew" ? ${SelectedIndex} : null;
    var moduleScreenCount = '${moduleScreenCount}' != "addNew" ? ${moduleScreenCount} : null;

    if (SelectedIndex && moduleScreenCount) {
        console.log(SelectedIndex);

        if ($("#moduleScreenId").val()) {
            if (SelectedIndex === moduleScreenCount) {
                $("#btnLast").css({ opacity: 0.5, pointerEvents: "none" }).addClass("disabled");
                $("#btnNexta").css({ opacity: 0.5, pointerEvents: "none" }).addClass("disabled");
            } else {
                if (!$("#btnNexta").hasClass("disabled")) {
                    $("#btnNext").click(function() {
                        var param = "${pageContext.request.contextPath}/ModuleScreenFormView?ID=" + $("#moduleScreenId").val() + "&NavAction=1";
                        window.location.href = param;
                    });
                }
                if (!$("#btnLst").hasClass("disabled")) {
                    $("#btnLst").click(function() {
                        var param = "${pageContext.request.contextPath}/ModuleScreenFormView?ID=" + $("#moduleScreenId").val() + "&NavAction=4";
                        window.location.href = param;
                    });
                }
            }

            if (SelectedIndex === 1) { // First record in database
                $("#btnFirst").css({ opacity: 0.5, pointerEvents: "none" }).addClass("disabled");
                $("#btnPrva").css({ opacity: 0.5, pointerEvents: "none" }).addClass("disabled");
            } else {
                if (!$("#btnPrva").hasClass("disabled")) {
                    $("#btnPrv").click(function() {
                        var param = "${pageContext.request.contextPath}/ModuleScreenFormView?ID=" + $("#moduleScreenId").val() + "&NavAction=0";
                        window.location.href = param;
                    });
                }
                $("#btnFrst").click(function() {
                    if (!$("#btnFrst").hasClass("disabled")) {
                        var param = "${pageContext.request.contextPath}/ModuleScreenFormView?ID=" + $("#moduleScreenId").val() + "&NavAction=3";
                        window.location.href = param;
                    }
                });
            }
        }

        $("#label-1").text((SelectedIndex) + "/" + moduleScreenCount);
    }

    // Autocomplete for selectnav
    $("#selectnav").autocomplete({
        source: function(request, response) {
            $.ajax({
                type: "GET",
                contentType: "application/json; charset=utf-8",
                url: '${pageContext.request.contextPath}/GetAllModuleScreen',
                data: { requestValue: request.term },
                dataType: "json",
                success: function(data) {
                    if (data) {
                        response(data.listAR);
                    }
                },
                error: function() {
                    console.log("Error in selectnav autocomplete");
                }
            });
        },
        minLength: 0,
        maxShowItems: 40,
        scroll: true,
        select: function(event, ui) {
            this.value = ui.item ? ui.item[0] + ":" + ui.item[1] + ":" + ui.item[2] : '';
            var param = "${pageContext.request.contextPath}/ModuleScreenFormView?ID=" + ui.item[0] + "&NavAction=2";
            window.location.href = param;
            return false;
        }
    }).autocomplete("instance")._renderItem = function(ul, item) {
        return $('<li class="each">')
            .data("item.autocomplete", item)
            .append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                item[0] + '</span><br><span class="desc">' +
                item[1] + '</span></div>')
            .appendTo(ul);
    };

    $("#selectnav").focus(function() {
        if (this.value == "") {
            $(this).autocomplete("search");
        }
    });
});

</script>




 
 </html>
