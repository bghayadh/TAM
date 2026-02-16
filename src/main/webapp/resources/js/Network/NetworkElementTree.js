// This JavaScript File includes all the functions of the tree

///////////////////////////////////////////////
/*         Tree Section Network Tree         */ 
//////////////////////////////////////////////

function filter(lst, myArray){
	var results = [];	
	for (var i=0; i < lst.length; i++) {
		if (myArray.includes(lst[i][2])){
			results.push(lst[i]);		
		}
	}
	return results;
}	
///////////////////////////////////////////////
/*               Hover Tree              */ 
//////////////////////////////////////////////
$(document).ready(function() {
	HoverClickNetworkSpans();  
});

function HoverClickNetworkSpans(){
	$("#network_tree li > .TreeSpan").bind("mouseover","li > .TreeSpan",function(e) {
		$(this).addClass('backgroundTree');
	}).on("mouseout",function(e) {
		$(this).removeClass('backgroundTree');
	});
}	

///////////////////////////////////////////////
/*     Tree propagation fcts */ 
//////////////////////////////////////////////
function tree_prop_general(){
	$('.tree li > .TreeSpan').on('click', function (e) {			
		$("#initial_ul").find(' > ul > li').css("background", "").removeClass("selected-span");
		$(".tree li > .TreeSpan").css("background", "").removeClass("selected-span");
		$(this).css("background-color", "#97b9cc").addClass("selected-span");
		e.stopPropagation();
	  });
	$('.tree li:has(ul)').addClass('parent_li').find(' > .tree-span').attr('title', 'Collapse this branch'); 
	$("#network_tree i").css('margin-right', '5px');
}

function tree_PropInitial_li(){
	$("#initial_ul > .folder").eq(n).on('click', function (e) {   	                
		var children = $(this).parent('li.parent_li').find(' > ul > li');
		if (children.is(":visible")) {
		children.hide();
		$(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
		} else {
		    children.show();
		    $(this).parent('li.parent_li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
		    }
		e.stopPropagation();                  
	});
}

function tree_Prop(selector){
	$(selector).on('click', function (e) {
		children = $(this).parent('li.parent_li').find(' > ul > li');
		if ($(this).parent().find('> ul > li').is(":visible")) {
			$(this).parent('li').children('.folder').find('> svg').addClass('fa-folder').removeClass('fa-folder-open');
			children.toggle();
		} else {							
			$(this).parent('li').children('.folder').find('> svg').addClass('fa-folder-open').removeClass('fa-folder');
			children.toggle();
		}				
	});
}	

///////////////////////////////////////////////
/*     BOQ Fcts */ 
//////////////////////////////////////////////

var boqList=[];
var siteList=[];

function Site_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetBoqList',
			data: {
			    "SiteId" : SiteId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});	
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	       
}
/////////////////////////////////////////////////////////////////////////////////
function SiteVen_Boq(SiteId,VenId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSiteVenBoqList',
			data: {
			    "SiteId" : SiteId,
			    "VenId" : VenId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 	   	 	      
}

function SiteVenNT_Boq(SiteId,VenId,SelectedNodeType){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSiteVenNTBoqList',
			data: {
			    "SiteId" : SiteId,
			    "VenId" : VenId,
			    "SelectedNodeType": SelectedNodeType,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 
}
//////////////////////////////////////////////////////////////////////////////////
function Supp_Boq(SuppId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSupBoqList',
			data: {
			    "SuppId" : SuppId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SuppId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SuppId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 	   	 	      
}


function SiteSupp_Boq(SiteId,SuppId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

	$.ajax({
		type: "GET",
		contentType: "application/json; charset=utf-8",
		url: getContext()+'/GetSiteSupBoqList',
		data: {
			    "SiteId" : SiteId,
			    "SuppId" : SuppId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
		},
		success : function(data) {
			$('#boq_table').empty();				 
			siteList.push(SiteId);	          	 
			$.each(data , function( key, value ) {	
				boqList.push({ SiteId,key,value });    
				var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	$("#boqBtn").removeClass().addClass("tablinks active");
	$("#Defaultbutton").removeClass().addClass("tablinks");
	$("#optionBtn").removeClass().addClass("tablinks");
	Boq.css({ display:'block'});
	Layers.css({ display:'none'});
	Options.css({ display:'none'});
	Boq.tab('show');	 	   	 	      
}

function SiteSuppNT_Boq(SiteId,SuppId,SelectedNodeType){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSiteSupNTBoqList',
			data: {
			    "SiteId" : SiteId,
			    "SuppId" : SuppId,
			    "SelectedNodeType" : SelectedNodeType,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 	   	 	      
}

//////////////////////////////////////////////////////////////////////////////////
function Ven_Boq(VenId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
		
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetVenBoqList',
			data: {
			    "VenId" : VenId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(VenId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ VenId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});

	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	   	 	      
}

function VenSite_Boq(VenId,siteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetVenSiteBoqList',
			data: {
			    "VenId" : VenId,
			    "siteId" : siteId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(VenId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ VenId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});

	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	   	 	      
}

function SupSite_Boq(SupId,siteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();	
	
if(arrayParam[0]==1){
	var paramEnterprise = true;
}else{
	var paramEnterprise = false;
}

if(arrayParam[1]==1){
	var paramTransmission = true;
}else{
	var paramTransmission = false;
}
	
if(arrayParam[2]==1){
	var paramRAN = true;
}else{
	var paramRAN = false;
}

if(arrayParam[3]==1){
	var paramCore = true;
}else{
	var paramCore = false;
}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSupSiteBoqList',
			data: {
			    "SupId" : SupId,
			    "siteId" : siteId,
			    "paramEnterprise" : paramEnterprise,
			    "paramTransmission" : paramTransmission,
			    "paramRAN" : paramRAN,
			    "paramCore" : paramCore,
			    "arrayParam" : arrayParam,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SupId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SupId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});

	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	   	 	      
}
//////////////////////////////////////////////////////////////////////////////////
function SitePO_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
	
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetBoqSitePoList',
			data: {
			    "SiteId" : SiteId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date" : date,
			    },
			success : function(data)
			    {
				 $('#boq_table').empty();				 
				 siteList.push(SiteId);	          	 
				 $.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
				alert("Error");
									}			
		});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	Boq.tab('show');	 	   	 	      
}
//////////////////////////////////////////////////////////////////
var boqList=[];
var sitePList=[];

function POSite_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();

	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}

		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetPOSiteBoqList',
			data: {
				"POID" : SiteId,
				"paramEnterprise": paramEnterprise,
				"paramTransmission":paramTransmission,
				"paramRAN":paramRAN,
				"paramCore":paramCore,
				"date":date,
				},
			success : function(data)
			{
				$('#boq_table').empty();
				sitePList.push(SiteId);	          	 
				$.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
			alert("Error");
			}			
		});
	$("#boqBtn").removeClass().addClass("tablinks active");
	$("#Defaultbutton").removeClass().addClass("tablinks");
	$("#optionBtn").removeClass().addClass("tablinks");
	Boq.css({ display:'block'});
	Layers.css({ display:'none'});
	Options.css({ display:'none'});
	Boq.tab('show');	 
}


///////////////////////////////////////////////
/*     Nodes BOQ Fcts */ 
//////////////////////////////////////////////
var siteNList=[];
var boqNList=[];

function  Node_Boq(WareId,NodeId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNodeBoqList',
			data: {
			    "WareId":WareId,
			    "NodeId":NodeId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date": date,
			    },
			success : function(data)
			    {
				$('#boq_table').empty();
				siteNList.push(NodeId); 
				$.each(data , function( key, value ) {	
					boqNList.push({ NodeId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
			});				
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}

///////////////////////////////////////////////
/*     Node type BOQ Fcts */ 
//////////////////////////////////////////////
var siteNTList=[];
var boqNTList=[];

function  NodeT_Boq(SiteId,NodeTId){	
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNtypeBoqList',
			data: {
			    "SiteId":SiteId,
			    "NodeTId":NodeTId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date": date,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteNTList.push(NodeTId); 
				$.each(data , function( key, value ) {	
					boqNTList.push({ NodeTId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
			}
				});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}

function  SuppNdTyp_Boq(SuppId,NodeTId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSuppNtypeBoqList',
			data: {
			    "SuppId":SuppId,
			    "NodeTId":NodeTId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date": date,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteList.push(SuppId); 
				$.each(data , function( key, value ) {	
					boqList.push({ SuppId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
				});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}

function  SiteNodeType_Boq(SiteId,NodeTId){	
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	console.log("date << ",date);

	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetSiteNdtypeBoqList',
			data: {
			    "SiteId":SiteId,
			    "NodeTId":NodeTId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date":date,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteList.push(SiteId); 
				$.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
				});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}


function  VenNdTyp_Boq(VenId,NodeTId){
	
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}

		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetVenNtypeBoqList',
			data: {
			    "VenId":VenId,
			    "NodeTId":NodeTId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date":date,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteList.push(VenId); 
				$.each(data , function( key, value ) {	
					boqList.push({ VenId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
				});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}


/*     Node type supp BOQ Fcts */
function  NodeTSup_Boq(SiteId,NodeTId,SuppId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	
	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNtypeSupBoqList',
			data: {
			    "SiteId":SiteId,
			    "NodeTId":NodeTId,
			    "SuppId":SuppId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date":date,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteNTList.push(NodeTId); 
				$.each(data , function( key, value ) {	
					boqNTList.push({ NodeTId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
									}
				});
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}

/*     Node type vendor BOQ Fcts */
function NodeTVen_Boq(SiteId,NodeTId,VendorId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();	

	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
		 $.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetNtypeVenBoqList',
			data: {
			    "SiteId": SiteId,
			    "NodeTId": NodeTId,
			    "VendorId": VendorId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date":date,
			    },
			success : function(data)
			  {
				$('#boq_table').empty();
				siteNTList.push(NodeTId); 
				$.each(data , function( key, value ) {	
					boqNTList.push({ NodeTId,key,value });
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td>"+value+"</td>"+
					"</tr>";
					$("#boq_table").append(tr);
				});
			},
			error: function(result) {
				alert("Error");
				}
			});
			
	 $("#boqBtn").removeClass().addClass("tablinks active");
	 $("#Defaultbutton").removeClass().addClass("tablinks");
	 $("#optionBtn").removeClass().addClass("tablinks");
	 Boq.css({ display:'block'});
	 Layers.css({ display:'none'});
	 Options.css({ display:'none'});
	 Boq.tab('show');	      	 	      
}
///////////////////////////////////////////////
/*     PO BOQ Fcts */ 
//////////////////////////////////////////////

var boqList=[];

function PO_Boq(SiteId){
	var Boq = $('#Boq');
	var Layers= $('#Layers');
	var Options= $('#Options');
	var date=$("#ParsingDate").val();
	console.log("date << ",date);
	
	if(arrayParam[0]==1){
		var paramEnterprise = true;
	}else{
		var paramEnterprise = false;
	}

	if(arrayParam[1]==1){
		var paramTransmission = true;
	}else{
		var paramTransmission = false;
	}
		
	if(arrayParam[2]==1){
		var paramRAN = true;
	}else{
		var paramRAN = false;
	}

	if(arrayParam[3]==1){
		var paramCore = true;
	}else{
		var paramCore = false;
	}
	
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: getContext()+'/GetPOBoqList',
			data: {
				"POID" : SiteId,
			    "paramEnterprise": paramEnterprise,
			    "paramTransmission":paramTransmission,
			    "paramRAN":paramRAN,
			    "paramCore":paramCore,
			    "date":date,
			},
			success : function(data)
			{
				$('#boq_table').empty();
				sitePList.push(SiteId);	          	 
				$.each(data , function( key, value ) {	
					boqList.push({ SiteId,key,value });    
					var tr = "<tr>"+
					"<td class='title'> "+key+"</td>"+
					"<td> "+value+" </td>"+
					"</tr>";
					$("#boq_table").append(tr);     		    			   			
				});  	
			},	
			error: function(result) {
			alert("Error");
			}			
		});
	$("#boqBtn").removeClass().addClass("tablinks active");
	$("#Defaultbutton").removeClass().addClass("tablinks");
	$("#optionBtn").removeClass().addClass("tablinks");
	Boq.css({ display:'block'});
	Layers.css({ display:'none'});
	Options.css({ display:'none'});
	Boq.tab('show');	 
}


///////////////////
function getContext() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

///////////////////////////////////////////////
function openContext(id,name,menuName,event){
	const time = menuName.isOpen() ? 100 : 0;
	menuName.hide();
	setTimeout(() => { menuName.show(event.pageX, event.pageY) }, time);
	document.addEventListener('contextmenu', hideContext, true);
	document.addEventListener('click', hideContext, true);
	//HideContextMenuGoolge(MenuMap);
	window["menuName"]=menuName;		
	event.preventDefault();
}

function hideContext(){
	if(window["menuName"]!=""){
		window["menuName"].hide();
		document.removeEventListener('contextmenu', hideContext);
		document.removeEventListener('click', hideContext);		
	}
}


function Create_TreeNode_CellGeneral(lstNodes,lstCells,ChildrenLength,concat,SiteName) {
	for(j=ChildrenLength;j<lstNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
	{																        	
	var str= "<ul><li class='Node' id='" + lstNodes[j][0] +"' style='display:none; margin-left:-18px' class='folder'>";
	if(concat==true)
	{	
		if(lstNodes[j][5]>0 || lstNodes[j][6]>0 || lstNodes[j][7]>0){		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][3]+"_"+lstNodes[j][4]+"_f").append(str);								        																	        				
			str ="<ul><li id='" + lstNodes[j][0] +"_f' style='display:none; margin-left:-10px;'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
			$("#"+lstNodes[j][0]).append(str);
		}																	        			
		else{
			str += "<span class='TreeSpan' style='width:395px; margin-left:18px;'><span class='tree-span'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][3]+"_"+lstNodes[j][4]+"_f").append(str);
		}
	}
	else{
		if(lstNodes[j][5]>0 || lstNodes[j][6]>0 || lstNodes[j][7]>0){
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";			
			$("ul").find("#"+lstNodes[j][4]+"_f").append(str);								        																	        				
			str ="<ul><li id='" + lstNodes[j][0] +"_f' style='display:none; margin-left:-10px;'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
			$("#"+lstNodes[j][0]).append(str);				
		}																	        			
		else{
			str += "<span class='TreeSpan' style='width:395px; margin-left:18px;'><span class='tree-span'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][4]+"_f").append(str);
		}
	}		

	$("#"+lstNodes[j][0]+" > .folder").on('click',function () {	
		var selectedNode=$(this).parent().attr('id');
		var NdChildrenLength=$("#" + selectedNode+"_f").find(' > ul > li').length;															
		if(NdChildrenLength==0){		
			var lstCellsFiltered=[];
			for(var c=0;c<lstCells.length;c++){
				if(lstCells[c][2]==selectedNode){
					lstCellsFiltered.push(lstCells[c]);
				}
			}
				for (k = 0; k <lstCellsFiltered.length; k++) {
					var str="<ul><li class='Cell' id='" + lstCellsFiltered[k][0] + "' style='display:none; margin-left:-15px;''><span class='TreeSpan' style='width:395px'><span class='tree-span'>  <i class='fas fa-vector-square fa-2x'></i> "+lstCellsFiltered[k][1]+" </span></span></li></ul>";
					$("#"+lstCellsFiltered[k][2]+"_f").append(str);
				}
			tree_prop_selection("#" +selectedNode+"_f .Cell .TreeSpan");
				}	
		});	
	
	$(".Node > .TreeSpan").contextmenu(function(){				
		selectedNodeIdContext=$(this).parent().attr('id');
		menuName=singleNode;			
		openContext(selectedNodeIdContext,"",singleNode,event);
	});
	singleNode = new ContextMenu({
		  'theme': 'default',
		  'items': [
			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {
					Node_Boq(SiteName,selectedNodeIdContext);
				}	
				   }
			]
	});
	}	
	Tree_PropagationAppendedNodes(SiteName+"_f .Node");
	tree_prop_selection("#" +SiteName+"_f .Node .TreeSpan");	
}

function Create_TreeNode_Cell_Vendor(lstNodes,lstCells,ChildrenLength,concat,SiteName,selectedVen) {	
	for(j=ChildrenLength;j<lstNodes.length;j++)//  NODE_PK, SITE_ID, NODE_NAME,NODE_MODEL
	{																        	
	var str= "<ul><li class='Node' id='" + lstNodes[j][0] +"' style='display:none; margin-left:-18px' class='folder'>";
	if(concat==true)
	{	
		if(lstNodes[j][5]>0 || lstNodes[j][6]>0 || lstNodes[j][7]>0){		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+ selectedVen + "_" + lstNodes[j][3]+"_"+lstNodes[j][4]+"_f").append(str);								        																	        				
			str ="<ul><li id='" + lstNodes[j][0] +"_f' style='display:none; margin-left:-10px;'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
			$("#"+lstNodes[j][0]).append(str);
		}																	        			
		else{
			str += "<span class='TreeSpan' style='width:395px; margin-left:18px;'><span class='tree-span'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+ selectedVen + "_" +lstNodes[j][3]+"_"+lstNodes[j][4]+"_f").append(str);
		}
	}
	else{
		if(lstNodes[j][5]>0 || lstNodes[j][6]>0 || lstNodes[j][7]>0){		
			str+="<span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span>";
			str += "<span class='TreeSpan' style='width:395px'><span class='tree-span' style='margin-left:-15px;'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][4]+"_f").append(str);								        																	        				
			str ="<ul><li id='" + lstNodes[j][0] +"_f' style='display:none; margin-left:-10px;'><span class='folder'> <i class='fa fa-folder' style='color: #08526D'></i></span><span class='TreeSpan' style='width:395px'> Cells </span></span></li></ul>";
			$("#"+lstNodes[j][0]).append(str);
		}																	        			
		else{
			str += "<span class='TreeSpan' style='width:395px; margin-left:18px;'><span class='tree-span'><i class='fas fa-server'></i>"+lstNodes[j][2]+"</span></span></li></ul>";
			$("ul").find("#"+lstNodes[j][4]+"_f").append(str);
		}
	}		

	$("#"+lstNodes[j][0]+" > .folder").on('click',function () {	
		var selectedNode=$(this).parent().attr('id');
		var NdChildrenLength=$("#" + selectedNode+"_f").find(' > ul > li').length;															
		if(NdChildrenLength==0){		
			var lstCellsFiltered=[];
			for(var c=0;c<lstCells.length;c++){
				if(lstCells[c][2]==selectedNode){
					lstCellsFiltered.push(lstCells[c]);
				}
			}
				for (k = 0; k <lstCellsFiltered.length; k++) {
					var str="<ul><li class='Cell' id='" + lstCellsFiltered[k][0] + "' style='display:none; margin-left:-15px;''><span class='TreeSpan' style='width:395px'><span class='tree-span'>  <i class='fas fa-vector-square fa-2x'></i> "+lstCellsFiltered[k][1]+" </span></span></li></ul>";
					$("#"+lstCellsFiltered[k][2]+"_f").append(str);
				}
			tree_prop_selection("#" +selectedNode+"_f .Cell .TreeSpan");
		}	
	});	
	
	$(".Node > .TreeSpan").contextmenu(function(){				
		selectedNodeIdContext=$(this).parent().attr('id');
		menuName=singleNode;			
		openContext(selectedNodeIdContext,"",singleNode,event);
	});
	singleNode = new ContextMenu({
		  'theme': 'default',
		  'items': [
			  {'icon': 'braille', 'name': 'Show BoQ', action: () => {				
					Node_Boq(SiteName,selectedNodeIdContext);
				}	
				}
			]
	});
	}	
	Tree_PropagationAppendedNodes(SiteName+"_f .Node");
	tree_prop_selection("#" +SiteName+"_f .Node .TreeSpan");	
}