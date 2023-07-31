function openFilterOptionModel(element) {
	
			  /////////// Model Fields Option
																			//	 $("#FieldsOption").click(  function() {
																					  $('#myModalFieldsOption').modal('show');
																					  $('#myModalFieldsOption').draggable(); 

																			/*		     document.getElementById("warcode").value = "";
																					     document.getElementById("SiteId").value = "";
																						 document.getElementById("siteName").value = "";
																						 document.getElementById("supcode").value = "";
																						// document.getElementById("itemCode").value = "";
																						 document.getElementById("itemCategory").value = "";
																						 document.getElementById("itemModel").value = "";
																						 document.getElementById("itemPartNo").value = "";
																						 document.getElementById("nodeId").value = "";
																						 document.getElementById("nodeName").value = "";
																						 document.getElementById("nodeType").value = "";
																						 document.getElementById("cellId").value = "";
																						 document.getElementById("cellName").value = "";
																						 document.getElementById("domainType").value = "";
																						 document.getElementById("prcode").value = "";
																						 document.getElementById("voucherType").value= "";
																						 document.getElementById("vncode").value = "";
																						 document.getElementById("sncode").value = "";
																						 document.getElementById("UOM").value = "";
																				*/	 
																			//	 });
							
													/////////////////// Model Submit Fields Option
																				 $("#SubmitFieldsOption").click(  function() {
																					  $('#myModalFieldsOption').modal('hide');
																								  
																								  
																				 });
												    ////////////////// Model Close Fields Option
$("#clearFieldsOption").click(  function() {
	document.getElementById("warcode").value = "";
	document.getElementById("SiteId").value = "";
	document.getElementById("siteName").value = "";
	document.getElementById("supcode").value = "";
	document.getElementById("itemCode").value = "";
	document.getElementById("itemCategory").value = "";
	document.getElementById("itemModel").value = "";
	document.getElementById("itemPartNo").value = "";
	//document.getElementById("nodeId").value = "";
	//document.getElementById("nodeName").value = "";
	//document.getElementById("nodeType").value = "";
	//document.getElementById("cellId").value = "";
	//document.getElementById("cellName").value = "";
	//document.getElementById("domainType").value = "";
	//document.getElementById("prcode").value = "";
	//document.getElementById("voucherType").value= "";
	//document.getElementById("vncode").value = "";
	//document.getElementById("sncode").value = "";
	//document.getElementById("UOM").value = "";


				
				
});
																				 
																				 											
											


	
	
	
	
	
	
	
	
	
	
	
}


function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
	//alert('js'+getContextPath());

 $(document).ready(function() {
		 var ctx = getContextPath();

   ////////auto complete for Itemcode   
			
			
   $("#itemCode").autocomplete({
	    	    
	      source: function(request, response) {
	    	    
	    	  $.ajax({
	    	                 type: "GET",
	    	                 contentType: "application/json; charset=utf-8",
	    	                 url: ctx+'/getItemCode',
					                 data: {
											"requestValue" :$("#itemCode").val(),
											
									 },
					                 dataType: "json",
					                 success: function (data) {
					                     if (data != null) {
					                         response(data.ListItemDetails);
					                     }
					                 },
					                 error: function(result) {
					                     console.log("Error");
					                 }
					             });
					         }, minLength:0, maxShowItems: 4, scroll:true,		
				               
					     	select: function(event, ui) {
					     		icode.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
					     		//sncode.value = "";
					     		document.getElementById("itemCode").value = (ui.item ? ui.item[0] : '');
					     		document.getElementById("itemName").value = ui.item[1];
					     		document.getElementById("itemModel").value = ui.item[2];
					     		document.getElementById("itemPartNo").value= ui.item[3];
					     		$("#itemCategory").val(ui.item[5]+":"+ui.item[6]);
					     		
									return false;
								}
                           }).autocomplete("instance")._renderItem = function(ul, item) {
		
		                      var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
           
		                      item[0] + "</span><br><span class='desc'>" +
		                      item[1] + "</span><span class='desc'>";
		                      if(item[2] != '-')
		                    	  appendString += ","+item[2] + "</span><span class='desc'>";
		                      if(item[3] != '-')
		                    	  appendString += ","+item[3] + "</span><span class='desc'>";
		                      if(item[4] != '-')
		                    	  appendString += ","+item[4];

		                      appendString += "</span></div>";
		                      return $("<li class='each'>").append(appendString).appendTo(ul);

 	                    };
						$("#itemCode").focus(function(){
							if (this.value == ""){
								$(this).autocomplete("search");
				   	        }
						});
									
//// ended auto complete for itemcode


   ////////auto complete for itemName   
			
			
   $("#itemName").autocomplete({
	    	    
	      source: function(request, response) {
	    	    
	    	  $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/getItemCode',
			                 data: {
									"requestValue" :request.term
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.ListItemDetails);
			                     }
			                 },
			                 error: function(result) {
			                     console.log("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 4, scroll:true,		
		               
			     	select: function(event, ui) {
			     		icode.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
			     		//sncode.value = "";
			     		document.getElementById("itemCode").value = (ui.item ? ui.item[0] : '');
			     		document.getElementById("itemName").value = ui.item[1];
			     		document.getElementById("itemModel").value = ui.item[2];
			     		document.getElementById("itemPartNo").value= ui.item[3];
			     		$("#itemCategory").val(ui.item[5]+":"+ui.item[6]);
			     		
							return false;
						}
                   }).autocomplete("instance")._renderItem = function(ul, item) {

                      var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
   
                      item[1] + "</span><br><span class='desc'>" +
                      item[0] + "</span><span class='desc'>";
                      if(item[2] != '-')
                    	  appendString += ","+item[2] + "</span><span class='desc'>";
                      if(item[3] != '-')
                    	  appendString += ","+item[3] + "</span><span class='desc'>";
                      if(item[4] != '-')
                    	  appendString += ","+item[4];

                      appendString += "</span></div>";
                      return $("<li class='each'>").append(appendString).appendTo(ul);

                };
				$("#itemName").focus(function(){
					if (this.value == ""){
						$(this).autocomplete("search");
		   	        }
				});
									
//// ended auto complete for itemName


$("#itemCategory").autocomplete({

                source: function (request, response) {
                    $.ajax({
                        type: "GET",
                        contentType: "application/json; charset=utf-8",
                        url: ctx+'/GetAllCategory',
                        data: {
                            "itemCategory" :request.term,
                        },
                        dataType: "json",
                        success: function (data) {
                            if (data != null) {
                                response(data.ListItemCategory);
                            }
                        },
                        error: function (result) {
                            console.log("Error");
                        }
                    });
                }, minLength: 0, maxShowItems: 4, scroll: true,
                select: function (event, ui) {
                    $("#itemCategory").val(ui.item[0] + ":" + ui.item[1]);
                    $("#itmrootcat").val(ui.item[2]);
                    $("#itmcatid").val(ui.item[3]);

                    return false;
                }
            }).autocomplete("instance")._renderItem = function (ul, item) {
                return $("<li class='each'>")
                    .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
                        item[0] + "</span><br><span class='desc'>" +
                        item[2] + ', ' + item[1] + "</span></div>")
                    .appendTo(ul);
            };

            $("#itemCategory").focus(function () {
                if (this.value == "") {
                    $(this).autocomplete("search");
                }
            });
            
   /// End Autocomplete for Item Category
   
 

///////auto complete for VoucherNB 
/*
$("#vncode").autocomplete({
       source: function(request, response, event, ui) {
	        		
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/GetAllIDs',
	                 data: {
		                 	"voucherType" : $("#voucherType").children("option:selected").val(),
		                 	"VoucherNb" : request.term,
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.Listreq);
	                     }
	                 },
	                 error: function(result) {
	                     console.log("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 40, scroll:true,	
	         
	         
	         select: function(event, ui) {
	        	    vncode.value = (ui.item ? ui.item[0] : '');
					return false;
						}
			    }).autocomplete("instance")._renderItem = function(ul, item) {
		            return $("<li class='each'>")
	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                    item[0] + "</span></div>")
	                .appendTo(ul);
		            
	        	};
	        	$("#vncode").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				}); */
              
	/////////////end autocpmplete for VoucherNB 

	        	
	/////////////Start autocomplete for warehouse 
	
	        	$("#warcode").autocomplete({
	        		
	        	    source: function(request, response) {
	        	   	   	
	        	            $.ajax({
	        	                type: "GET",
	        	                contentType: "application/json; charset=utf-8",
	        	                url: ctx+'/GetAllWarehouse',
	        	                data: {
	        							"requestValue": request.term
	        					 },
	        	                dataType: "json",
	        	                success: function (data) {
	        	               	 
	        	                    if (data != null) {
	        	                        response(data.globalList);
	        	                      
	        	                    }
	        	                },
	        	                error: function(result) {
	        	                    console.log("Error");
	        	                }
	        	            });
	        	        }, minLength:0, maxShowItems: 4, scroll:true,		
	        	       

	        	        select: function(event, ui) {
	        	       	    warcode.value = (ui.item ? ui.item[0] : '');
	        	       	    document.getElementById("siteName").value = ui.item[1];
	        	       	    document.getElementById("SiteId").value = ui.item[2];
	        	       	    site.value  = (ui.item ? ui.item[2] +";"+ ui.item[1]   : '');
	        					return false;
	        				}
	        			    }).autocomplete("instance")._renderItem = function(ul, item) {
	        		            return $("<li class='each'>")
	        	               .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	        	                   item[0] + "</span><br><span class='desc'>" +
	        	                   item[1] +', '+ item[2] +', '+ item[3] +', '+ item[4] + "</span></div>")
	        	               .appendTo(ul);
	        	       	};
	        				$("#warcode").focus(function(){
	        	  	        	if (this.value == ""){
	        	  	            	$(this).autocomplete("search");
	        	  	        	}						
	        				});
	        				
	        				
	  	        /////////////end autocomplete for warehouse 
	  	
	        	/////////////Start autocpmplete for site ID
	        				$("#SiteId").autocomplete({
	        					showHeader: true,
	        			        source: function(request, response) {
	        				             $.ajax({
	        				                 type: "GET",
	        				                 contentType: "application/json; charset=utf-8",
	        				                 url: ctx+ '/GetAllSiteIds_ALC',
	        				                 data: {
	        										"requestValue": request.term
	        								 },
	        				                 dataType: "json",
	        				                 success: function (data) {
	        				                     if (data != null) {
	        				                         response(data.ListSites);
	        				                     }
	        				                 },
	        				                 error: function(result) {
	        				                     console.log("Error");
	        				                 }
	        				             });
	        				         }, minLength:0, maxShowItems: 40, scroll:true,		
	        			               
	        			        
	        					select: function(event, ui) {
	        						
	        						site.value  = (ui.item ? ui.item[0] +";"+ ui.item[1]   : '');
	        						warcode.value = (ui.item ? ui.item[2] : '');
	        						siteName.value = (ui.item ? ui.item[1]  : '');
	        						document.getElementById("SiteId").value = (ui.item ? ui.item[0]  : '');
	        							return false;
	        								}
	        					    }).autocomplete("instance")._renderItem = function(ul, item) {
	        				            return $("<li class='each'>")
	        			                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	        	                    item[0] + "</span><br><span class='desc'>" +
	        	                    item[1] +  "</span></div>") .appendTo(ul);
	        			        	};
	        						$("#SiteId").focus(function(){
	        			   	        	if (this.value == ""){
	        			   	            	$(this).autocomplete("search");
	        			   	        	}						
	        						});

	        	/////////////end autocpmplete for site ID
	        	
	   /////////////Start autocomplete for Site Name 
	  	
	        				$("#siteName").autocomplete({
						         source: function(request, response) {
						        	   	
							             $.ajax({
							                 type: "GET",
							                 contentType: "application/json; charset=utf-8",
							                 url: ctx+'/GetAllWareSiteNames_ALC',
							                 data: {
													"requestValue": request.term
											 },
							                 dataType: "json",
							                 success: function (data) {
							                	 
							                     if (data != null) {
							                         response(data.ListWareSiteName);
							                       
							                     }
							                 },
							                 error: function(result) {
							                     console.log("Error");
							                 }
							             });
							         }, minLength:0, maxShowItems: 4, scroll:true,		
						            
						     
							         select: function(event, ui) {
							        	    warcode.value = (ui.item ? ui.item[2] : '');
							        	    document.getElementById("siteName").value = ui.item[0];
							        	    document.getElementById("SiteId").value = ui.item[1];
							        	    site.value  = (ui.item ? ui.item[1] +";"+ ui.item[0]   : '');
											return false;
												}
									    }).autocomplete("instance")._renderItem = function(ul, item) {
								            return $("<li class='each'>")
							                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
							                    item[0] + "</span><br><span class='desc'>" +
							                    item[1] +', '+ item[2] +  "</span></div>")
							                .appendTo(ul);
							        	};
										$("#siteName").focus(function(){
							   	        	if (this.value == ""){
							   	            	$(this).autocomplete("search");
							   	        	}						
										});
			/////////////end autocomplete for Site Name 
			
			//////////// start auto complete for supplier
										$("#supcode").autocomplete({
									    
									    source: function(request, response) {
									             $.ajax({
									                 type: "GET",
									                 contentType: "application/json; charset=utf-8",
									                 url: ctx+'/GetAllSupplier_ALC',
									                 data: {
															requestValue :request.term
													 },
									                 dataType: "json",
									                 success: function (data) {
									                     if (data != null) {
									                         response(data.ListSupplier);
									                     }
									                 },
									                 error: function(result) {
									                     console.log("Error");
									                 }
									             });
									         }, minLength:0, maxShowItems: 40, scroll:true,

												select: function(event, ui) {
													supcode.value = (ui.item ? ui.item[0] + ";" + ui.item[1] : '');
													//prsuppaddress.value= ui.item[2];
													return false;
												}
											}).autocomplete("instance")._renderItem = function(ul, item) {
										            return $("<li class='each'>")
									                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
									                    item[0] + "</span><br><span class='desc'>" +
									                    item[1] + "</span></div>")
									                .appendTo(ul);
									        	};
												$("#supcode").focus(function(){
									   	        	if (this.value == ""){
									   	            	$(this).autocomplete("search");
									   	        	}						
												});
									    	
			//////////////////// end auto complete for supplier
			
			////////////////////auto complete for ItemModel  
												   
												   
			 $("#itemModel").autocomplete({
		    	    source: function(request, response) {
		    	             $.ajax({
		    	                 type: "GET",
		    	                 contentType: "application/json; charset=utf-8",
		    	                 url:ctx+'/getModel',
						         data: {
									"requestValue" :  $("#itemModel").val()
						         },
						         dataType: "json",
						         success: function (data) {
						        	 console.log("succ");
						             if (data != null) {
						            	 response(data.ListModels);
						             }
						         },
						         error: function(result) {
						             console.log("Error");
						         }
						     });
					}, minLength:0, maxShowItems: 4, scroll:true,
					select: function(event, ui) {
		                    icode.value = (ui.item ? ui.item[1] + ":" + ui.item[2] : '');
		                    itemModel.value = (ui.item ? ui.item[0] : '');
							document.getElementById("itemPartNo").value= ui.item[3];
							
							document.getElementById("itemCode").value = ui.item[1];
			     			document.getElementById("itemName").value= ui.item[2];
			     		
							//sncode.value = "";
							return false;
					}
			     }).autocomplete("instance")._renderItem = function(ul, item) {
					var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
							
					item[0] + "</span><br><span class='desc'>" +
					item[1] + "</span><span class='desc'>"+"," +
					item[2] + "</span><span class='desc'>";
					if(item[3] != '-')
						appendString += ","+item[3] + "</span><span class='desc'>";

					appendString += "</span></div>";
					return $("<li class='each'>").append(appendString).appendTo(ul);
					};
								
								
							
							 $("#itemModel").focus(function(){
								if (this.value == ""){
									$(this).autocomplete("search");
					   	        }
							});
																 
																 
															
					///////// ended auto complete for itemModel
					
			       //////////auto complete for Item part number  
													      			
																	
  $("#itemPartNo").autocomplete({
	    
	    source: function(request, response) {
	    	
	              $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/getPartNo',
			                 data: {
									"requestValue" :$("#itemPartNo").val(),
									
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.ListPartNos);
			                     }
			                 },
			                 error: function(result) {
			                     console.log("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 4, scroll:true,		
		               
			     	select: function(event, ui) {
			     		icode.value = (ui.item ? ui.item[1] + ":" + ui.item[2] : '');
			     		
			     		document.getElementById("itemCode").value = ui.item[1];
			     		document.getElementById("itemName").value= ui.item[2];
			     		
			     		document.getElementById("itemModel").value = ui.item[3];
			     		document.getElementById("itemPartNo").value= ui.item[0];
			     		//sncode.value = "";
			     		
							return false;
						}
				}).autocomplete("instance")._renderItem = function(ul, item) {
				
	                  var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                  item[0] + "</span><br><span class='desc'>" +
	                  item[1] + "</span><span class='desc'>" +","+ 
	                  item[2] + "</span><span class='desc'>";
	                  if(item[3] != '-')
	                 	appendString += ","+item[3] + "</span><span class='desc'>";

            	appendString += "</span></div>";
            	return $("<li class='each'>").append(appendString).appendTo(ul);
				};
				$("#itemPartNo").focus(function(){
					if (this.value == ""){
						$(this).autocomplete("search");
		   	        }
				});
							
					                            //////////// ended auto complete for item part number
																			
																			
																	
																			
				                               /////////////// started auto complete for node
							/* $("#nodeId").autocomplete({
					
			     
			             source: function(request, response) {
			        	 
			        	     
				        	
				             $.ajax({
				                 type: "GET",
				                 contentType: "application/json; charset=utf-8",
				                 url: ctx+'/GetAllNodes_ALC',
				                 data: {
										"node" : $("#nodeId").val(),
										 //"nodeType":$("#nodeType").val(),
										 
								 },
				                 dataType: "json",
				                 success: function (data) {
				                	 
				                     if (data != null) {
				                         response(data.nodeList);
				                         console.log('data is ;'+ data.nodeList);
				                         
				                     }
				                 },
				                 error: function(result) {
				                     console.log("Error");
				                 }
				             });
				         }, minLength:0, maxShowItems: 4, scroll:true,		
			            
			     
				         select: function(event, ui) {
				        	    nodeId.value = (ui.item ? ui.item[1] : '');
				        	    document.getElementById("nodeName").value = ui.item[0];
				        	    document.getElementById("nodeType").value = ui.item[3];
				        	   
								return false;
									}
						    }).autocomplete("instance")._renderItem = function(ul, item) {
					            return $("<li class='each'>").append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
					                    item[0] + "</span><br><span class='desc'>" +
					                    item[1] +', '+ item[2] +', '+ item[3] + "</span></div>")
					                .appendTo(ul);
						};
							$("#nodeId").focus(function(){
				   	        	if (this.value == ""){
				   	            	$(this).autocomplete("search");
				   	        	}						
							}); */
							
  /////////////////// ended auto complete for node
																			
				                                ////////////////// started auto complete for node name
																		/*	$("#nodeName").autocomplete({
																	
															     
															             source: function(request, response) {
															        	 
															        	     
																        	
																             $.ajax({
																                 type: "GET",
																                 contentType: "application/json; charset=utf-8",
																                 url: ctx+'/GetAllNodesName_ALC',
																                 data: {
																						"nodeName" : $("#nodeName").val(),
																						 //"nodeType":$("#nodeType").val(),
																						 
																				 },
																                 dataType: "json",
																                 success: function (data) {
																                	 
																                     if (data != null) {
																                         response(data.nodeNameList);
																                         console.log('data is ;'+ data.nodeNameList);
																                         
																                     }
																                 },
																                 error: function(result) {
																                     console.log("Error");
																                 }
																             });
																         }, minLength:0, maxShowItems: 4, scroll:true,		
															            
															     
																         select: function(event, ui) {
																        	    nodeId.value = (ui.item ? ui.item[1] : '');
																        	    document.getElementById("nodeName").value = ui.item[0];
																        	    document.getElementById("nodeType").value = ui.item[3];
																        	   
																				return false;
																					}
																		    }).autocomplete("instance")._renderItem = function(ul, item) {
																	            return $("<li class='each'>").append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
																	                    item[0] + "</span><br><span class='desc'>" +
																	                    item[1] +', '+ item[2] +', '+ item[3] + "</span></div>")
																	                .appendTo(ul);
																		};
																			$("#nodeName").focus(function(){
																   	        	if (this.value == ""){
																   	            	$(this).autocomplete("search");
																   	        	}						
																			}); */
																			
				                               /////////////////////// ended auto complete for node name
																			
															 
															 
			                                  /////////////////////// started auto complete for CELL
															               
															             /*  $("#cellId").autocomplete({
																	
															     
															             source: function(request, response) {
															        	 
															        	     
																        	
																             $.ajax({
																                 type: "GET",
																                 contentType: "application/json; charset=utf-8",
																                 url: ctx+'/GetAllCells_ALC',
																                 data: {
																						"cell" : $("#cellId").val(),
																						 
																						 
																				 },
																                 dataType: "json",
																                 success: function (data) {
																                	 
																                     if (data != null) {
																                         response(data.cellList);
																                         console.log('data is ;'+ data.cellList);
																                         
																                     }
																                 },
																                 error: function(result) {
																                     console.log("Error");
																                 }
																             });
																         }, minLength:0, maxShowItems: 4, scroll:true,		
															            
															     
																         select: function(event, ui) {
																        	    cellId.value = (ui.item ? ui.item[0] : '');
																        	    cellName.value = (ui.item ? ui.item[1] : '');
																				return false;
																					}
																		    }).autocomplete("instance")._renderItem = function(ul, item) {
															            	   return $("<li class='each'>").append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
																	                    item[0] + "</span><br><span class='desc'>" +
																	                    item[1] + "</span></div>").appendTo(ul);
																        	};
																			$("#cellId").focus(function(){
																   	        	if (this.value == ""){
																   	            	$(this).autocomplete("search");
																   	        	}						
																			});
															 
															 
							                        ////////////////// ended auto complete for CELL
															              
							                       ////////////////// started auto complete for CELL Name
															               
															               $("#cellName").autocomplete({
																	
															     
															             source: function(request, response) {
															        	 
															        	     
																        	
																             $.ajax({
																                 type: "GET",
																                 contentType: "application/json; charset=utf-8",
																                 url: ctx+'/GetAllCellsName_ALC',
																                 data: {
																						"cellName" : $("#cellName").val(),
																						 
																						 
																				 },
																                 dataType: "json",
																                 success: function (data) {
																                	 
																                     if (data != null) {
																                         response(data.cellNameList);
																                         console.log('data is ;'+ data.cellNameList);
																                         
																                     }
																                 },
																                 error: function(result) {
																                     console.log("Error");
																                 }
																             });
																         }, minLength:0, maxShowItems: 4, scroll:true,		
															            
															     
																         select: function(event, ui) {
																        	    cellId.value = (ui.item ? ui.item[0] : '');
																        	    cellName.value = (ui.item ? ui.item[1] : '');
																				return false;
																					}
																		    }).autocomplete("instance")._renderItem = function(ul, item) {
															            	   return $("<li class='each'>").append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
																	                    item[1] + "</span><br><span class='desc'>" +
																	                    item[0] + "</span></div>").appendTo(ul);
																        	};
																			$("#cellName").focus(function(){
																   	        	if (this.value == ""){
																   	            	$(this).autocomplete("search");
																   	        	}						
																			}); */
															 
															 
						 /////////////////         ////////////////// ended auto complete for CELL NAME
															 
			            //////////////////        ////////////////// started auto complete for Serial Number
				/*											               
               $("#sncode").autocomplete({
     
             source: function(request, response) {
        	 
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: ctx+'/GetAllSerialNumber',
	                 data: {
						requestValue : request.term
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.ListSerialNumber);
	                     }
	                 },
	                 error: function(result) {
	                     console.log("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 4, scroll:true,		
            
     
	         select: function(event, ui) {
	        	    sncode.value = (ui.item ? ui.item[0] : '');
	        	    icode.value = (ui.item ? ui.item[3] + ":" + ui.item[4] : '');
		     		
		     		itemCode.value = ui.item[3];
		     		itemName.value = ui.item[4];
		     		itemCategory.value = ui.item[5] + ":" + ui.item[6]
		     		document.getElementById("itemModel").value = ui.item[1];
		     		document.getElementById("itemPartNo").value= ui.item[2];
		     		
	        	    
					return false;
				}
			    }).autocomplete("instance")._renderItem = function(ul, item) {
            	   var appendString = "<div class='acItem'><span class='name' style='font-weight:bold'>" +
	                
					item[0] + "</span><br><span class='desc'>";
					if(item[1] != '-')
						appendString += item[1] + ",</span><span class='desc'>";
					
					if(item[2] != '-')
					appendString += item[2] + "</span>";
		
					appendString += "</div>";
					return $("<li class='each'>").append(appendString).appendTo(ul);
	        	};
				$("#sncode").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				}); */
															 
															 
						//////////////////     /////////////// ended auto complete for Serial Number
	 });											 
															