<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <head>
        <meta charset="utf-8">
        <title>Item Category</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="shortcut icon" href="">
        <!-- <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>  -->
        <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
        <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
        <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">

        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,300">

        <style>
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
                /* height: 30px  */
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
            
            .btn-group
            {
            	padding-top:0px !important
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
            
           .tree li ul>li {
                display: none;
            } 
            
            #ajaxResponse{
               color:#F04444;
               font-weight: bold;
               margin-left:20px;
            }
            
            .btn-notclickable {
            cursor : default !important ;
            background-color: red;
            color : white;
            }
            
            .btn-notclickable:hover
            {
            	color : white;
            }
            
            .noChildCategories
            {
            	cursor: auto !important;
            }
        </style>

    </head>

    <body>

<%--         <c:set var="page" value="inventory" /> --%>

<%--         <%@ include file="header.html" %> --%>
  <c:set var="pg" value="inventory" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>

            <!--  end of general head page -->
            <div class="container-fluid">

	<div class="row second" style="margin-top: 15px;">
			<div class="col-md-9">
			</div>
			<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
		 			<div class="glyph">
		 			<button type="button" class="btn btn-danger" data-toggle="tooltip"
							data-placement="top" title="Tree View"
							style="background: #da6815;">
							<i class="fas fa-sitemap"></i>
						</button>
			 			<button  type="button" id="Fview"  class="btn btn-light" data-toggle="tooltip" 
			 				data-placement="top" title="Form View"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			data-placement="top" title="List View"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/ItemCategoryListView"'
			        			> 
			        			<i class="fa fa-bars"></i>
			        	</button>
						
			        </div>  
		        </div>
			</div>
		</div>
		
		<div class="row">
		<div class="col-12 col-sm-12 col-lg-12">	
		      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #007b7c; margin-top: 15px;">
		             <li class="nav-item"><class="nav-link inactive" id="custom-tabs-one-home-tab"  style="color: gold;center;line-height:2.75em; padding-left:5px;">ITEM CATEGORY TREE</li>
	            
		           
									
		     </ul>
		     
		</div>
		</div>
                <div class="row">
                    <p></p>
                </div>

                <div class="row second">
                    <div class="col-md-3" style="display: flex;">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" style="color: green"> ITEM CATEGORY</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- /.card-header -->
                <div>
                    <div class="row">
                        <div class="col-sm-12">

                            <div id="cat-tree" class="tree well"></div>

                            <input type="hidden" id="hiddenCategoryID" />
                            

                        </div>
                    </div>
                </div>

            </div>



            <!-- Add Category Modal -->
            <div class="modal fade" id="addCategoryModal" tabindex="-1" role="dialog"
                aria-labelledby="addCategoryModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addCategoryModalLabel">Add Category</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Category Name</span>
                                            <input type="text" id="addCatName" value="" class="form-control text-input">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Category Code</span>
                                            <input type="text" id="addCatCode" value="" class="form-control text-input">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="validateCatInp">
                        <div id="ajaxResponse"></div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" id='addCategorySave' class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Edit Category Modal -->
            <div class="modal fade" id="editCategoryModal" tabindex="-1" role="dialog"
                aria-labelledby="editCategoryModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editCategoryModalLabel">Edit Category</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Category Name</span>
                                            <input type="text" id="editCatName" value=""
                                                class="form-control text-input">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Category Code</span>
                                            <input type="text" id="editCatCode" value=""
                                                class="form-control text-input">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" id='editCategorySave' class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Delete Category Modal -->
            <div class="modal fade" id="deleteCategoryModal" tabindex="-1" role="dialog"
                aria-labelledby="deleteCategoryModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteCategoryModalLabel">Delete Category</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <h6 id="confirmDeleteMessage"></h6>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" id='deleteCategorySave' class="btn btn-danger">Confirm Delete</button>
                        </div>
                    </div>
                </div>
            </div>

            <script>
                $(function () {

                    getTreeElements();

                    $('.tree li > span').on('click', function (e) {
                    	
                    $("#tree-actions").remove();
                   
                  //  $(".tree li > span").removeClass("selected-span");
                   
                    $(this).addClass("selected-span");
						var catRoot = $(this).parent().attr("data-id");
						
						
						var action_div = "<div id='tree-actions' class='btn-group btn-group-sm' role='group' aria-label='Action Buttons'>";
						action_div += "<button type='button' id='addCategoryButton' class='btn btn-tree' onClick='addCategoryFunction(this)'>Add</button>";
						if(catRoot != "item_0")
							{
						
						action_div += "<button type='button' id='editCategoryButton' class='btn btn-tree' onClick='editCategoryFunction(this)'>Edit</button>";
                        action_div += "<button type='button' id='deleteCategoryButton' class='btn btn-tree' onClick='deleteCategoryFunction(this)'>Delete</button>";
						
							}
						action_div += "</div>";
						
                        $(this).after(action_div);

                        e.stopPropagation();
                    });


                    function saveCategory()
                	{
                		var categoryName = $("#addCatName").val();
                        var categoryCode = $("#addCatCode").val();
                        var categoryID = $("#hiddenCategoryID").val();
                        var validateCatInp = $("#validateCatInp").val();
															  
													
                        if(validateCatInp == "catExist")
                            return;
                        if (categoryName != null && categoryCode != null) {
                        	
                            $.ajax({
                                type: "GET",
                                url: "${pageContext.request.contextPath}/InsertCategory",
                                dataType: "json",
                                data: {
                                     "categoryID": categoryID,
                                     "categoryName": categoryName,
                                     "categoryCode": categoryCode
                                },
                                success: function (data) {
                                    console.log("The returned data is " + data.Test);
                                    $('#addCategoryModal').modal('hide');
                                    location.replace("${pageContext.request.contextPath}/ItemCategoryTreeView?CatID=" + categoryID+"&ItemCatID="+data.ItemCatID);

                                },
                                error: function (error) {
                                    console.log("The error is " + error);
                                }
                            });

                        }
                    }

                    document.getElementById('addCategorySave').addEventListener('click', saveCategory);

             

                    $('#editCategoryModal').on('click', '#editCategorySave', function () {

                        var categoryName = $("#editCatName").val();
                        var categoryCode = $("#editCatCode").val();
                        var categoryID = $("#hiddenCategoryID").val();
                        
                        if (categoryName != null) {
                   
                            $.ajax({
                                type: "GET",
                                url: "${pageContext.request.contextPath}/EditCategory",
                                dataType: "json",
                                data: {
                                    "categoryID": categoryID,
                                    "categoryName": categoryName,
                                    "categoryCode": categoryCode
                                },
                                success: function (data) {
                                    console.log("The returned data is " + data.Test);
                                    //  location.replace("${pageContext.request.contextPath}/ItemCategoryTreeView");
                                    $('#editCategoryModal').modal('hide');
                                    $('#currentNode').get(0).lastChild.nodeValue = categoryName + " - " + categoryCode;
                                    $('#currentNode').removeAttr('id');
                                },
                                error: function (error) {
                                    console.log("The error is " + error);
                                }
                            });

                        }
                    });

                    $('#deleteCategoryModal').on('click', '#deleteCategorySave', function () {

                        var categoryID = $("#hiddenCategoryID").val();
					
                        $.ajax({
                            type: "GET",
                            url: "${pageContext.request.contextPath}/DeleteCategory",
                            dataType: "json",
                            data: {
                                "categoryID": categoryID
                            },
                            success: function (data) {
                                console.log("The returned data is " + data.Test);
                                // location.replace("${pageContext.request.contextPath}/ItemCategoryTreeView");
                                $('#deleteCategoryModal').modal('hide');
                                $('#currentNode').closest('ul').remove();
                                $('#currentNode').removeAttr('id');

                            },
                            error: function (error) {
                                console.log("The error is " + error);
                            }
                        });


                    });

                });

                function getTreeElements() {

                    var right = [];
                    var row = [];

                    var lst = ${ ListOfsubtree };

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

                    // var row = [{title:"Food", lft:"1", rgt:"18" }, {title:"Fruit", lft:"2", rgt:"11" }, {title:"Red", lft:"3", rgt:"6" }, {title:"Cherry", lft:"4", rgt:"5" }, {title:"Yellow", lft:"7", rgt:"10" }, {title:"Banana", lft:"9", rgt:"9" }, {title:"Meat", lft:"12", rgt:"17" }, {title:"Beef", lft:"13", rgt:"14" }, {title:"Beef2", lft:"15", rgt:"16" }];
                    i = 0;
                    str = "<ul><li data-id='item_" + row[0]["id"] + "'>";

                    for (i = 0; i < row.length; i++) {
                        //alert("j for: "+right.length);
                        //alert(str);
                        var d = right.length;
                        var length = right.length;

                        for (j = 0; j < length; j++) {
                            //alert("inside for: "+right);

                            lastValue_right = parseInt(right[d - 1]);

                            d = d - 1;

                            row_right = parseInt(row[i]['rgt']);
                            //alert(lastValue_right+": "+row_right);

                            if (lastValue_right < row_right) {
                                right.pop();
                                str += "</li></ul>";
                            }
                        }

                        //alert(right.length);

                        if (right.length != 0)
                            str += "<ul><li data-id='item_" + row[i]["id"] + "'>";

                        str += "<span class='tree-span'><i class='fa fa-folder'></i>" + row[i]["title"] + " - " + row[i]["code"] + "</span>";

                        //alert(str2);

                        /* var markup = str.repeat(right.length)+"<li class='task' data-id='"+i+"'><div class='task__content'>"+row[i]["title"]+"</div></li>";
                         $(".tasks").append(markup);
                         alert(markup);*/

                        right.push(row[i]["rgt"]);
                        //alert("outside for: "+right);  
                    }

                    $("#cat-tree").append(str);

                    buildtree();
                }

                function buildtree() {

                	
                	//$('.tree >ul >li').find(" > span").find(' > svg').addClass('fa-folder-open')
                    // .removeClass('fa-folder');

                	
                	//$('.tree li ul li').first().css("display","list-item");
                    
                    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Expand this branch');

                   

                	$('.tree > ul> li > ul >li').css("display","list-item");

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
                        if ($(this).find("ul").length == 0) {
                        	$(this).attr('title', '');
                        	//$(this).find(" > span").addClass("noChildCategories");
                        	$(this).find(" > span > i").css('font-size', '0.7rem');
                        	$(this).find(" > span > i").addClass("fa-circle").removeClass('fa-folder');
                        } else {
                            if($('.tree >ul >li').find(" > span >i"))
                             {
                            	$('.tree >ul >li').find(" > span >i").addClass('fa-folder-open').removeClass('fa-folder');
                            	$('.tree >ul >li >span').addClass('selected-span');
                             	$('.tree >ul >li').find(" > span").attr('title', 'Collapse this branch');
                             }
                                
                            else
                             {
                            	$(this).attr('title', 'Expand this branch').find(
                                ' > span > i').addClass('fa-folder')
                                .removeClass('fa-circle');

                            $(this).attr('title', 'Expand this branch').find(
                                ' > span > i').css('font-size', '0.7rem');
                            }
                            

                        }
                        
                    });

                    $("i").css('margin-right', '5px');

                    var last_ul = $(".parent_li").find("ul:last-child");
                    var last_li = $(last_ul).find("li:last-child");
                    $(last_ul).addClass("last-ul");
                    $(".last-ul").children("li").addClass("last-node");


                    /* Function to get Parameter from URL */
                    var getUrlParameter = function getUrlParameter(sParam) {
                        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                            sURLVariables = sPageURL.split('&'),
                            sParameterName,
                            i;

                        for (i = 0; i < sURLVariables.length; i++) {
                            sParameterName = sURLVariables[i].split('=');

                            if (sParameterName[0] === sParam) {
                                return sParameterName[1] === undefined ? true : sParameterName[1];
                            }
                        }
                    };

                    var categoryID = getUrlParameter('ItemCatID');
                    if (categoryID != null) {
                        
                        var item_id = "item_" + categoryID;
                        $("li[data-id=" + item_id + "] > span").addClass("selected-span");
                        
                        
                        var child = $("li[data-id=" + item_id + "]").parents().find(' > ul > li');
                        $("li[data-id=" + item_id + "]").parents().find(" > span >i").addClass('fa-folder-open').removeClass('fa-folder');
                       // $("li[data-id=" + item_id + "]").parents().find(" > span").addClass('selected-span');
                        $("li[data-id=" + item_id + "]").parents().find(" > span").attr('title', 'Collapse this branch');
                     	
                    	if (child.is(":hidden")) {
                    		child.show('fast');
                    	}
                    	
                    }

                }

                
                function addCategoryFunction(e) {

                
                    var categoryID = $(e).parents('li').data('id');
                    var categoryId = categoryID.split("_");
                    categoryId = categoryId[1];
					console.log("ID:"+categoryId);
				
                    $("#hiddenCategoryID").val(categoryId);
                    $('#addCategoryModal').modal('show');
                }

                function editCategoryFunction(e) {
                    
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

                    $("#hiddenCategoryID").val(categoryId);
                    $("#editCatName").val(categoryName);
                    $("#editCatCode").val(categoryCode);
                    $('#editCategoryModal').modal('show');
                }

                function deleteCategoryFunction(e) {

                    var categoryID = $(e).parents('li').data('id');
                    var categoryName = $(e).parents('li').children(':eq(0)').text();

                    $('#currentNode').removeAttr('id');
                    var currentNode = $(e).parents('li').children(':eq(0)');
                    $(currentNode).attr('id', 'currentNode');

                    var categoryId = categoryID.split("_");
                    categoryId = categoryId[1];

                    $("#hiddenCategoryID").val(categoryId);
                    $("#confirmDeleteMessage").text("Are you sure you want to Delete this Item - " + categoryName + " ?");
                    $('#deleteCategoryModal').modal('show');
                }


                    $('#Fview').click(function(e) {


					 if (($("#cat-tree ul li").find("ul").length) == 0) {

						    var param = "${pageContext.request.contextPath}/ItemCategoryFormView";
						    window.location.href = param;   
							e.preventDefault();

					   } 

					   else {

		                  var param = "${pageContext.request.contextPath}/ItemCategoryFormView?itemCategoryID=0&itemcatList=itemcatList"
						   window.location.href = param;
						   e.preventDefault();

						}
					   
                    	 
                    });



                    $("#addCatName").on('change', function (e) {

                         var categoryName = $("#addCatName").val();
                         var categoryID = $("#hiddenCategoryID").val();	
                         	
                             $.ajax({
                                 type: "GET",
                                 url: "${pageContext.request.contextPath}/ValidateCategory",
                                 dataType: "json",
                                 data: {
                                     "categoryID": categoryID,
                                     "categoryName": categoryName
                                 },
                                 success: function (data) {
                                     console.log("The returned data is " + data.countList);
                                    
                                     if (data.countList != 0) {
                                     	 $("#ajaxResponse").html('This Category Name is already used');
                                     	 $("#addCategorySave").removeClass("btn-primary");
                                     	 $("#addCategorySave").addClass("btn-notclickable");
                                     	 $("#validateCatInp").val('catExist');	

                                         }
                                     else
                                         {
                                    	 	$("#ajaxResponse").html('');
                                    	 	$("#addCategorySave").removeClass("btn-notclickable");
                                    	 	$("#addCategorySave").addClass("btn-primary");
                                    	 	$("#validateCatInp").val('');	

                                         }
                                 },
                                 error: function (error) {
                                     console.log("The error is " + error);
                                     
                                 }
                             });

                     });  



            </script>
    </body>

</html>
