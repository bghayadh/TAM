<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<head>
<meta charset="utf-8">
    <title></title>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/Contract.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources/css/dataTables.bootstrap4.min.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js" ></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap4.min.js"></script>
    <!-- JS TREE 
    <style>
	html { margin:0; padding:0; font-size:62.5%; }
	body { max-width:800px; min-width:300px; margin:0 auto; padding:20px 10px; font-size:14px; font-size:1.4em; }
	h1 { font-size:1.8em; }
	.demo { overflow:auto; border:1px solid silver; min-height:800px; }
	</style>-->
	<!-- JS TREE-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.min.css" />
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/jstree.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-expand-lg  navbar-light bg-light mynav">
        <a href="#" class="navbar-brand">ALM</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarmenu">
            <span class="navbar-toggler-icon"></span>
        </button>



        <div class="collapse navbar-collapse" id="navbarmenu">
            <ul class="navbar-nav">
                <li class="nav-item"><a href="/Network" class="nav-link  " style="color: #fff"><i class="fas fa-wifi" style="color: gold"></i> Network</a></li>
                <li class="nav-item"><a href="/Purchase" class="nav-link " style="color: #fff"><i class="fas fa-money-check" style="color: gold"></i> Purchasing </a></li>
                 
                <li class="nav-item"><a href="/Inventory" class="nav-link " style="color: #fff"><i class="fas fa-warehouse" style="color: gold"></i> Inventory </a></li>
                <li class="nav-item"><a href="/Contract" class="nav-link " style="color: #fff"><i class="fas fa-book" style="color: gold"></i> Contracts</a></li>
                <li class="nav-item"><a href="/Report" class="nav-link  " style="color: #fff"><i class="fas fa-chart-line" style="color: gold"></i> Report </a></li>
                <li class="nav-item"><a href="/Dashboard" class="nav-link " style="color: #fff"><span class="border-bottom active"><i class="fas fa-tv" style="color: #20B2AA"></i> Dashboard </span> </a></li>
                <li class="nav-item"><a href="/Setup" class="nav-link " style="color: #fff"><i class="fas fa-cog" style="color: gold"></i> Setup </a></li>



            </ul>
       <ul class="navbar-nav ml-auto ">
                        
                <!--  li class="nav-item"><a href="#" class="nav-link a1" style="margin-right: 30px;"><i class="fas fa-bell" style="color: gold"></i></a></li-->
                <li class="dropdown" id="notifactionDropdown"
				style="margin-right: 60px;"><a href="#" class="nav-link a1" data-toggle="dropdown">
					<span class="p1" id="bellCounter" data-count=""> 
					<i class="fa fa-bell" data-count="4b" style="font-size: 20px; color: gold;"></i>
					</span></a>
 	
						<ul class="dropdown-menu dropdown-menu-right customListPadding">
							<li><p>
								<a href="#">Site <span class="badge badge-danger float-right"id="siteTask"></span></a></p></li>
							<li><p>
								<a href="#">Node <span class="badge badge-danger float-right"id="nodeTask"></span></a></p></li>
							<li><p>
								<a href="#">Cell <span class="badge badge-danger float-right"id="cellTask"></span></a></p></li>
							<li><p>
								<a href="#">Task<span class="badge badge-danger float-right"id="taskCount"></span></a></p></li>
						</ul>
				</li>
               
               
               <!--  li class="nav-item"><a href="#" class="nav-link a1" style="color: white;"><i class="fas fa-user" style="color: gold   "></i> aya shi </a></li -->
 
                    <li class=" dropdown "><a href="#" class="nav-link a1" data-toggle="dropdown"
					style="text-decoration: none;"> <span style="color: #ffbb33 ;">${userFullName}</span>&nbsp;<i
					class="fa fa-user-circle" data-count="4b" 
					style="font-size: 20px; color: gold;"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right">
					<a href="/userList" class="dropdown-item"><i
						class="fa fa-user-edit"></i>Edit Profile</a>
					<div class="dropdown-divider"></div>
					<a href="${pageContext.request.contextPath}/logout"
						class="dropdown-item"><i class="fa fa-power-off"
						aria-hidden="true"></i> Logout</a>
					</div></li>
					
					 
                
            </ul>
        </div>

    </nav>
    
 <div id="evts" class="demo"></div>
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jstree.min.js"></script>

<script>
	
// interaction and events
	
$('#evts_button').on("click", function () {
		var instance = $('#evts').jstree(true);
		instance.deselect_all();
		instance.select_node('1');
	});
	$('#evts')
		.on("changed.jstree", function (e, data) {
			if(data.selected.length) {
			    str = data.instance.get_node(data.selected[0]).text;
				if (str=='Child node') {
					//alert('open html1 ');
					console.log('open html1');
					}
				else
					{
					//alert('The selected node is: ' + data.instance.get_node(data.selected[0]).text);
					console.log('The selected node is: ' + data.instance.get_node(data.selected[0]).text);
					}
				}
			})
			.bind("add_after.jstree", function (e,data){
      console.log('after ADD: ' + data.instance.get_node(data.selected[0]).text); 
})
		
		.jstree({
			'core' : {
			    "check_callback" : true,
				'multiple' : false,
				'data' : [
					{ "text" : "Root node 1", "children" : [
							{ "text" : "Child node", "id" : 1 },
							{ "icon":"${pageContext.request.contextPath}/resources/images/button.png" ,"text" : "Child node L1-2" }
					]},
					{ "text" : "Root node 2", "children" : [
							{ "text" : "Child node L2-1", "id" : 2 },
							{ "text" : "Child node L2-2" }
					]},{ "text" : "Root node 3", "children" : [
							{ "text" : "Root node 33", "children" : [
								{ "text" : "Child node", "id" : 3 },
								{ "icon":"${pageContext.request.contextPath}/resources/images/button.png" ,"text" : "Child node L3-2" }
							] },
							{ "text" : "Child node L3-2" }
					]}
				]
			},
			'plugins' : ['contextmenu', 'types'],
        'types' : {
            'default' : {
                'icon' : '${pageContext.request.contextPath}/resources/images/default.png'
            },
            'f-open' : {
                'icon' : '${pageContext.request.contextPath}/resources/images/open.png'
            },
            'f-closed' : {
                'icon' : '${pageContext.request.contextPath}/resources/images/closed.png'
            }
		    
        }
		});
	
	/* Toggle between folder open and folder closed */
	$("#evts").on('open_node.jstree', function (event, data) {
	    data.instance.set_type(data.node,'f-open');
	});
	$("#evts").on('close_node.jstree', function (event, data) {
	    data.instance.set_type(data.node,'f-closed');
	});
	//$("#evts").on('changed.jstree', function(evt, data) {
	  //if(data.action === "select_node") {
	   // data.instance.set_icon(data.node, 'focus.png');
	  //}
	//});

	 //$('#evts').on("hover_node.jstree", function (evt, data) {
							
	    //data.instance.set_icon(data.node, 'focus.png');
	//});
	
	  function customMenu(node)
{
    var items = {
        'item1' : {
            'label' : 'Add',
            'action' : function () { console.log('addnew node'); }
        },
        'item2' : {
            'label' : 'Rename',
            'action' : function () { console.log('rename node'); }
        },
		'item3' : {
            'label' : 'Delete',
            'action' : function () { console.log(node.type); 
				if (node.type === 'level_1') {
					delete items.item2;
				} else if (node.type === 'level_2') {
					delete items.item1;
				} 
			}
        },
    }
		
    

    return items;
}	
	</script>	
           
 </body>
 </html>