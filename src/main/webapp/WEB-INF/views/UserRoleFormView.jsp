<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="ISO-8859-1">
<title>User Role List View</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
        <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
        <link rel="stylesheet"
            href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
        <script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet" />

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/jquerysctipttop.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">
        <link href='${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.min.css' rel='stylesheet'
            type='text/css'>
        <script src='${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.min.js'
            type='text/javascript'></script>
       
<style>
.p {
	font-size: large;
	font-weight: bold;
}

.box {
	font-size: large;
}

.ui-autocomplete {
                max-height: 250px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
                overflow-x: both;
                /* add padding to account for vertical scrollbar */
                padding-right: 10px;
                z-index: 9999;
                width: 350px;
            }

.textarea {
  width: 400px;
  height: 100px;
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
.nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
             .hide-row { display:none; }
    
</style>
</head>
<!-- top nav bar -->
<%-- 	<c:set var = "page" value = "setup"/> --%>

<%-- 	<%@ include file="header.html" %> --%>
  <c:set var="pg" value="setup" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
<!-- end nav bar -->
<p></p>
<div class="container-fluid">
<div class="row">

		
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Role</span> <input type="text"
							 value="${roleName}" class="form-control text-input" readonly />
					</div>
				</div>
			</div>
			<div class="col-md-3"></div>
			<div class="col-md-3">
			 <div class="nextprvItems">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Other Role</span>
                                <input type="text" id="selectnav" value="${selectnav}" 
                                    class="form-control text-input" />
                            </div>
                        </div>
                    </div></div>
			<div class="col-md-3 text-right">
                        <i>&nbsp</i><span class="dot"></span> <i>&nbsp</i> <label for="formStatus" id="formStatus"
                            style="float: right;">Saved</label>
                    </div>
                    
                    </div>
                     <div class="row">
                    <div class="col-md-3">
                       
                    </div>

                    <div class="col-md-3">
                      
                    </div>

               <div class=" col-md-4 ">
               <div class="nextprvItems">
			<label id="label-1" style="width: 80px; text-align: center;  margin-top: 5px ! important;"></label>
				<nav aria-label="Page navigation">
			  		<ul class="pagination">
						<li id="btnFrst" title="Go To First"  class="page-item " style="margin-right: 2px;"><a
							style="margin-left: 3px;width: 53px;height:40px" id="btnFirst" href="#"
							class="btn btn-success previous">&laquo; </a></li>
						<li id="btnPrv" title="Go To Previous"  class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnPrva" href="#"
							class="btn btn-success previous">&lsaquo; </a></li>
						<li id="btnNext" title="Go To Next"  class="page-item"
							style="padding-right: 0px ! important;"><a
							style="width: 53px;margin-right: 2px;height:40px" id="btnNexta" style="width:100px;" href="#"
							class="btn btn-success next"> &rsaquo; </a></li>
						<li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
							style="width: 53px;height:40px" id="btnLast" href="#"
							class="btn btn-success next">&raquo;</a></li>			  		</ul>
				</nav>
		</div></div>
			<div class="col-md-2" style="text-align: right;">
				<div class="btn-group pull-right">

					<div class="glyph">

						<button type="button" id="Fview" class="btn btn-danger"
							data-toggle="tooltip" data-placement="top" title="Form View"
							style="background: #da6815;">
							<i class="fa fa-edit"></i>
						</button>
						<button type="button" id="Lview" class="btn btn-light"
							data-toggle="tooltip"
							onclick='window.location.href = "${pageContext.request.contextPath}/RoleListView"'
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
	<p></p>

</div>
<div class="container-fluid">

	<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">
			<ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist"
				style="background-color: #00757c; margin-top: -20px;">
				<li class="nav-item"><a class="nav-link active"
					id="custom-tabs-one-home-tab" data-toggle="tab"
					href="#custom-tabs-one-home" role="tab"
					aria-controls="custom-tabs-one-home" aria-selected="true"
					style="color: gold;">ROLE INFORMATION</a></li>





				<li class="nav-item ml-auto">
 <button type="button" 
				onclick='window.location.href = "${pageContext.request.contextPath}/UserRoleFormView?type=addNew"'
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
					</button>
				</li>

			</ul>

		</div>
	</div>

</div>


<p></p>



<div class="container-fluid">
	<div class="tab-content" id="custom-tabs-one-tabContent">
		<div class="tab-pane fade show active" id="custom-tabs-one-home"
			role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">



			<div class="row">
				<div class="col-md-4">
					<div class="form-group" style="margin-top: 30px;">
						<div class="input-group-prepend">
							<span class="input-group-text">Role Name</span> <input
								type="text" id="rolename" value="${roleName}"
								class="form-control text-input" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="input-group-prepend">
						<span class="input-group-text" style="margin-top: 30px;">Description</span> 
						<textarea name="paragraph_text" cols="90" rows="5" class="textarea" id="description">${description}</textarea>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<body>

</body>

<script type='text/javascript'>

	if ('${listRoleUser}' === "addNew") {
			$("#formStatus").text("New");
			 $(".nextprvItems").addClass("hide-row ");
				
			$('.dot').css({
				"background-color" : "orange"
			});
		}

		$("input").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({
				"background-color" : "orange"
			});

			$("#rolename").click(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({
					"background-color" : "orange"
				});
			});

			$("#description").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({
					"background-color" : "orange"
				});
			});
			
		});

		 
		$(document).ready(function() {

			var oldrolename = $("#rolename").val();			
			
			$("#saveButton").click(function() {

				if ($("#rolename").val() == '') {
					alert('role name cannot be Null');
					return false;	}
				$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/UserRoleFormSave",
					dataType : "json",
					data : {
						"oldrolename" : oldrolename,
						"rolename" : $("#rolename").val(),
						"description" : $("#description").val(),
					},
					success : function(data) {
						//console.log(data.saveStatus);
						location.replace("${pageContext.request.contextPath}/RoleListView");
					},
					error : function(error) {
						console.log("The error is "+ error);
					}
				});
				
			});

			
		$("#deleteButton").click(function() {
					console.log('delete now');
					
					
							$.ajax({
								type : "GET",
								url : "${pageContext.request.contextPath}/UserRoleFormDelete",
								dataType : "json",
								data : {
									"rolename" : $("#rolename").val(),
								},
								success : function(data) {
									//console.log(data.saveStatus);
									location.replace("${pageContext.request.contextPath}/RoleListView");
								},
								error : function(error) {
									console.log("The error is "+ error);
								}
							});
				});
		
		});

		 $("#selectnav").autocomplete({
	       	  source: debounce(function(request, response, event, ui) {
	       	    $.ajax({
	       	      type: "GET",
	       	      contentType: "application/json; charset=utf-8",
	       	      url: '${pageContext.request.contextPath}/GetAllRole',
	       	      data: {
	       	        "Role": $("#selectnav").val(),
	       	      },
	       	      dataType: "json",
	       	      success: function(data) {
	       	        if (data != null) {
	       	          response(data.ListUser);
	       	          console.log(data.ListUser);
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
	       	    this.value = ui.item ? ui.item[0] + ":" + ui.item[0] : '';

	       	    var param = '${pageContext.request.contextPath}/UserRoleFormView?rolename=' + ui.item[0]+"&NavAction=2";
	       	    window.location.href = param;
	       	    return false;
	       	  }
	       	}).autocomplete("instance")._renderItem = function(ul, item) {
	       	  return $("<li class='each'>")
	       	    .append("<div class='acItem'><span class='desc'><b>" +
	       	      item[0]  + "</span><br>")
	       	    .appendTo(ul);
	       	};



	   $("#selectnav").focus(function () {
	       
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
	     		
	     		 if("${SelectedIndex}" != "addNew"){
	   				var SelectedIndex = ${SelectedIndex};
	   				if('${roleCount}' != "addNew"){

	   					
	   			var userCount = ${roleCount};
	   			
	   			if(($("#rolename").val()) != "" && ($("#rolename").val()) != null){

	   			if(SelectedIndex === userCount){
	   				
	           		document.getElementById("btnLast").style.opacity = 0.5;
	           		$("#btnLast").hasClass("disabled");
	           		document.getElementById("btnLast").style.pointerEvents = "none";
	           		
	           		document.getElementById("btnNexta").style.opacity = 0.5;
	           		document.getElementById("btnNexta").style.pointerEvents = "none";

	   				
	   				$("#btnNexta").hasClass("disabled");
	   				
	   				}else{
	   					
	   					if(!$("#btnNexta").hasClass("disabled")){
	   						
	   						$("#btnNext").click(function(){
	   							
	   							var param ="${pageContext.request.contextPath}/UserRoleFormView?rolename="+$("#rolename").val()+"&NavAction=1";

	   							window.location.href =param;
	   				
	   						});
	   			
	   					}
	   					if(!$("#btnLst").hasClass("disabled")){
	           				
	           				$("#btnLst").click(function(){
	           					
	   							var param ="${pageContext.request.contextPath}/UserRoleFormView?rolename="+$("#rolename").val()+"&NavAction=4";
	           					window.location.href =param;
	           		
	           				});
	           	
	           			}
	   				}
	   			
	   			if(SelectedIndex === 1){ //first record in database
	   				
	           		document.getElementById("btnFirst").style.opacity = 0.5;
	           		$("#btnFirst").hasClass("disabled");
	           		document.getElementById("btnFirst").style.pointerEvents = "none";
	           		
	           		document.getElementById("btnPrva").style.opacity = 0.5;
	           		$("#btnPrva").hasClass("disabled");
	           		document.getElementById("btnPrv").style.pointerEvents = "none";
	   			
	   			}else{
	   				if(!$("#btnPrva").hasClass("disabled")){
	   					
	   					$("#btnPrv").click(function(){
	   						
	   						var param ="${pageContext.request.contextPath}/UserRoleFormView?rolename="+$("#rolename").val()+"&NavAction=0";
	   						window.location.href =param;
	   						
	   					 });
	   				}
	   				$("#btnFrst").click(function(){

	           			if(!$("#btnFrst").hasClass("disabled")){
	           					
	   						var param ="${pageContext.request.contextPath}/UserRoleFormView?rolename="+$("#rolename").val()+"&NavAction=3";
	           				window.location.href =param;
	           						
	           				}
	           				 });

	   			}
	   			
	   			}}
	   		}
	   			$("#label-1").text((SelectedIndex)+"/"+userCount);
	 		
</script>	
</html>