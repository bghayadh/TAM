
<div style="padding-right: 10px;padding-left: 10px;padding-top: 10px;padding-bottom: 0px;">
<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text">Warehouse </span>
							<input type="text" id="wareID" readonly  class="form-control text-input" />
						</div>
					</div>
				</div>
					<div class="col-md-4"></div>
				<div  class="col-md-4 text-right"  >
							<i>&nbsp</i><span class="dot"></span>
							<i>&nbsp</i> <label for="formStatus" id="formStatus" style="float:right;"  >Saved</label>							
						</div> 
			
		</div>
		
		
		<div class="row">
			
		<div class="col-md-4">
		<div class="form-group">
			<div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
					<span class="input-group-text">Created Date</span>
					<input type="text" id="wcreationDate" readonly class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>
	
	<div class="col-md-4">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span class="input-group-text">Last Modify Date</span>
					<input type="text" id="wlastModifieddate" readonly  class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker2"   />
					   <div class="input-group-append" data-target="#datetimepicker2" data-toggle="datetimepicker">
							
						</div>
				</div>
		</div>
	</div>
			
			
			
			        <div class="col-md-4" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/WarehouseListView"'
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
		
		<!-- body -->
		
		<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab-ware" data-toggle="tab"
            href="#custom-tabs-one-home-ware" role="tab"
            aria-controls="custom-tabs-one-home-ware" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            
         
            
            <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-passive-tab-ware" data-toggle="tab"
            href="#custom-tabs-one-passive-ware" role="tab"
            aria-controls="custom-tabs-one-passive-ware" aria-selected="false" style="color: gold;">PASSIVE</a></li>
            
       <li class="nav-item"><a class="nav-link"
            id="custom-tabs-one-profile-tab-ware" data-toggle="tab"
            href="#custom-tabs-one-finance-ware" role="tab"
            aria-controls="custom-tabs-one-finance-ware" aria-selected="false" style="color: gold;">FINANCE</a></li>
            <li class="nav-item ml-auto">
				<button type="button" id="deleteButtonWarea"
				class="btn btn-primary BtnActive">
				<i class="fa fa-trash"></i> Delete
				</button>  
                        
				<button type="button" id="saveButtonWare"
				class="btn btn-primary BtnActive">
				<i class="fa fa-save"></i> Save
				</button>  </li>
							
     </ul>
     
</div>
</div>


<!-- content tabs -->

<div class="tab-content" id="custom-tabs-one-tabContent">
	<div class="tab-pane fade show active" id="custom-tabs-one-home-ware"
	role="tabpanel" aria-labelledby="custom-tabs-one-home-tab-ware">

<p></p>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" id="warehouseInfo_tbl">
   	<tr>
		<td width=20% valign="top" class="left_col">
			<div class="form-group">
				<div class="input-group-prepend">
						<span class="input-group-text">Warehouse Name</span>
						<input type="text" id="warepname"   class="form-control text-input"   />
				</div>
			</div>
		</td>
		<td rowspan=10><div style="width:40px">&nbsp;</div></td>
		<td rowspan=10 valign="top">
			<div id="map"></div>
	    	<div id="msg"></div>
		</td>
   	</tr>
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend" data-target-input="nearest">
						<div class="input-group-text" style="margin-right: 20px; ">
      						<input style="margin-left: 10px; margin-right: 10px "type="checkbox" id="warsite">
      						<span>Site</span>
      					</div>
      						<span class="input-group-text">Site ID</span>
							<input type="text" id="siteId"   class="form-control text-input"   />
                	</div>
			</div>
   		</td>
   	</tr>
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Supported Technologies:</span>
			<div class="checkboxes" style="margin-top: 10px; margin-left: 10px;">
				<span>
	   	        	<input type="checkbox" id="techg2g" ${tech2G}/> 2G
					<input type="checkbox" id="techg3g" ${tech3G}/> 3G
		            <input type="checkbox" id="techg4g" ${tech4G}/> 4G
		            <input type="checkbox" id="techg5g" ${tech5G}/> 5G
				</span>
			</div>
			</div>
		</div>
   		</td>
   	</tr>
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Longitude</span>
					<input type="text" id="warelng"  class="form-control text-input"  />
				</div>
			</div>
   		</td>
   	</tr>
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
			<div class="input-group-prepend">
			<span class="input-group-text">Latittude</span>
			<input type="text" id="warlatt"  class="form-control text-input"/>
			</div>
		</div>
   		</td>
   	</tr>
   	
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
		<div class="form-group">
			<div class="input-group-prepend">
			<span class="input-group-text">Site Mode</span>
			<input type="text" id="siteMode"  class="form-control text-input"/>
			</div>
		</div>
   		</td>
   	</tr>
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Cluster</span>
							<input type="text" id="clusterID" style="width:550px" class="form-control text-input ui-widget ui-widget-content ui-corner-all"   />
					</div>
				</div>
   		</td>
   	</tr>   	
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Area</span>
							<input type="text" id="areaID" style="width:550px" class="form-control text-input ui-widget ui-widget-content ui-corner-all"   />
					</div>
				</div>
   		</td>
   	</tr>
   	<tr>
   		<td class="left_col">
   		<div class="form-group">
							<div class="input-group-prepend">
								<span class="input-group-text">City   </span>
								<input type="text" id="warcity"  class="form-control text-input" />
							</div>
						</div>
   		</td>
   	</tr>
   	<tr>
   	<td class="left_col">
   		<div class="form-group">
			<div class="input-group-prepend">
				<span class="input-group-text">Address</span>
				<input type="text" id="wareAddress"  class="form-control text-input" />
			</div>
		</div>
   	</td>
   	</tr>
   	<tr>
   	<td>&nbsp;</td>
   	</tr>
   	</table>
	</div>
<div class="tab-pane fade" id="custom-tabs-one-passive-ware" role="tabpanel" aria-labelledby="custom-tabs-one-passive-tab-ware">
	<p></p>	
		<div class="row">
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Existing/ New Town</span>
						<select id="existing_newtown" class="form-control">
		    				<option value="E">E</option>
		    				<option value="NT">NT</option>
		  				</select>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Showcase/ Non Showcase</span>
							<select id="showcase" class="form-control">
			    				<option value="SC">SC</option>
			    				<option value="NSC">NSC</option>
			  				</select>
		  			</div>
				</div>
			</div>
	
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Site Principal Owner</span>
							<input type="text" id="siteOwner" class="form-control text-input"   />
					</div>
				</div>
			</div>
			
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Tower Co ID</span>
							<input type="text" id="towerCoID" class="form-control text-input"   />
					</div>
				</div>
			</div>
	</div>
	<div class="row">
		<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend">
						<span class="input-group-text">Tower Type</span>
						<select id="towerType" class="form-control">
							<option value="GBT">GBT</option>
							<option value="RTT" >RTT</option>
							<option value="RTP">RTP</option>
							<option value="Wall Mounted" >Wall Mounted</option>
							<option value="IBS">IBS</option>
							<option value="GBP">GBP</option>
							<option value="COW">COW</option>
							<option value="NOW">NOW</option>
		    				<option value="GBT + Revamp">GBT + Revamp</option>
		    				<option value="RTT + Revamp">RTT + Revamp</option>
		  				</select>
				</div>
			</div>
		</div>
		<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Tower Height (m)</span>
							<input type="number" id="towerHeight" class="form-control text-input"   />
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Building Height</span>
							<input type="number" id="buildingHeight" class="form-control text-input"   />
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Shared/ Non Shared</span>
							<select id="shared" class="form-control">
			    				<option value="Shared">Shared</option>
			    				<option value="Non-Shared">Non-Shared</option>
			  				</select>
					</div>
				</div>
			</div>
	</div>
	<div class="row">
		<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">ICR Category (ICR/MORAN)</span>
							<select id="icrCategory" class="form-control">
		    				<option value="NO" <c:if test = "${icrCategory =='NO'}"> selected </c:if>>NO</option>
		    				<option value="ICR" <c:if test = "${icrCategory =='ICR'}"> selected </c:if>>ICR</option>
		    				<option value="MORAN" <c:if test = "${icrCategory =='NO'}"> selected </c:if>>MORAN</option>
		  				</select>
					</div>
				</div>
			</div>
			
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Transmission</span>
							<select id="transmission" class="form-control">
			    				<option value="Fiber">Fiber</option>
			    				<option value="MW">MW</option>
			  				</select>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Hub Site</span>
							<input type="text" id="hubSite" class="form-control text-input"   />
					</div>
				</div>
			</div>
			
	</div>
	
	<div class="row">			
			<div class="col-md-3">
				<div class="form-group">
						<div class="input-group-text">
      						<input style="margin-right: 10px; " type="checkbox" id="shelterCheck">
      						<span>Shelter(With or Without)</span>
      					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Shelter ID</span>
							<input type="text" id="shelterID" class="form-control text-input"   />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Shelter</span>
							<input type="text" id="shelter" class="form-control text-input"   />
					</div>
				</div>
			</div>
			
	</div>
	
	<div class="row">			
			<div class="col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Shelter Type</span>
							<input type="text" id="shelterType" class="form-control text-input"   />
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Shelter Vendor</span>
							<input type="text" id="shelterVendor" class="form-control text-input"   />
					</div>
				</div>
			</div>
	</div>
 
	<div class="row">
			<div class="col-md-6">
				<div class="form-group">
					<div class="input-group-prepend">
							<span class="input-group-text">Location Notes</span>
							<input type="text" id="locationNotes" class="form-control text-input"   />
					</div>
				</div>
			</div>
	</div>
</div>
<div class="tab-pane fade" id="custom-tabs-one-finance-ware" role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab-ware">
		<p></p>
		<div class="row">
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total initial Cost</span>
						<input type="text" readonly id="InitialCost" class="form-control text-input"   />
					</div>
				</div>
			</div>
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Accumulated Depreciation</span>
						<input type="text" readonly id="accumPer" class="form-control text-input"   />
					</div>
				</div>
			</div>
			<div class="col-md-4">
            	<div class="form-group">
					<div class="input-group-prepend">
						<span class="input-group-text">Total Net Cost</span>
						<input type="text" readonly id="totalNetCost" class="form-control text-input"   />
					</div>
				</div>
			</div>
	</div>
	
	<div> 
				<form>
					    <div class="table-responsive-sm"> 
						    <table id ="bisotab" class="table table-striped table-bordered table-sm" style="display:block; height:200px; overflow-y: auto;">
						        <thead>
						            <tr>
						                <th>
								          <button type="button" id="wareselectAll" class="main">
								          <span class="sub"></span>Select</button></th>
						                <th><div>Start Date</div></th>
						                <th><div>End Date</div></th>
						                <th><div>Population</div></th>
						                <th><div>Technologies</div></th>
						                <th><div>2G Utilization</div></th>
						                <th><div>3G Utilization</div></th>
						                <th><div>4G Utilization</div></th>
						                <th><div>5G Utilization</div></th>
						                <th><div>Availability 2G</div></th>
						                <th><div>Availability 3G</div></th>
						                <th><div>Availability 4G</div></th>
						                <th><div>Availability 5G</div></th>
						                <th><div>Gross ADS</div></th>
						                <th><div>Count of SSO</div></th>
						                <th><div>Customer Base</div></th>
						                <th><div>Data</div></th>
						                <th><div>Voice</div></th>
						                <th><div>SMS Revenue</div></th>
						                <th><div>Gross Revenue</div></th>
						                <th><div>Data Traffic</div></th>
						                <th><div>Total SMS Traffic OG</div></th>
						                <th><div>Total Voice Traffic OG</div></th>
						                <th><div>Total Voice Traffic IC</div></th>
						                <th><div>Total SMS Traffic IC</div></th>
						                <th><div>Total OPEX Monthly</div></th>
						                <th><div>Profit and Loss</div></th>
						                <th><div>ID</th>
						            </tr>
						        </thead>
						        <tbody>
						            
									
						        </tbody>
						    </table>
					    </div>
					    
					    <!--  Text used to indicate row index -->
						<input type="button" class="add-row-ware" value="Add Row">
					    <button type="button" class="delete-row-ware">Delete Row</button>
                   </form>
		</div>
</div>


</div>
		
		<!-- *******************************end main div ***************************************** -->
		</div>
		
		
<script>

 $(document).ready(function(){

	 $("#deleteButtonWarea").click(  function() {
		 console.log('delete now');
		 var wareID = document.getElementById("wareID").value;
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/WarehouseFormViewDelete",
				dataType : "json",
				data : {
					"wareID" : $("#wareID").val()
				},
				success : function(data) {
					location.reload(true);
				},
				error : function(error) {
					console.log("The error is " + error);
				}
			});
		 
	 });


		$("#saveButtonWare").click(  function() {
			console.log('saved now');
			 
			Errorflag ='0';																				 
			flagFrom = "save";
			 // validate creatd date cannot be null
			 val =$("#wcreationDate").val();
		     val1 = Date.parse(val);
		     if (isNaN(val1) == true) {
		          alert(' Create Date is not valid');
		          return false;
		        }
			 
			  // validate modified date cannot be null
			 val =$("#wlastModifieddate").val();
		     val1 = Date.parse(val);
		     if (isNaN(val1) == true) {
		          alert(' Modified Date is not valid');
		          return false;
		        }
			 
			  // validate type of Longitude
			 if (($. isNumeric( $('#warelng'). val()))== false) {
			 alert('Longitude is  not Numeric');
			 return false;}
			 
			  // validate type of attitude
			 if (($. isNumeric( $('#warlatt'). val()))== false) {
			 alert('Attitude is  not Numeric');
			 return false;}


			 selectALLrows();
			 getselectedrows ();
			if (Errorflag =='1') {
			     return false;
			 } 							 
			 
			 saverowsintables();
			
			 
		 });

	 
	 $("input").change(function() {
			$("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
			
			});
	var Errorflag; 
	var area_ID = '${areaID}';
	var areaName = '${areaName}';
	var area;
	if(area_ID == "")
		area = "";
	else
		area = area_ID +":"+ areaName;


	var clusterID = '${clusterID}';
	var clusterName = '${clusterName}';
	var cluster;
	if(clusterID == "")
		cluster = "";
	else
		cluster = clusterID +":"+ clusterName;

	//alert(area);
	$("#areaID").val(area);
	$("#clusterID").val(cluster);
	 var flagFrom;


////Start Autocomplete for Area ID
$("#areaID").autocomplete({
	
	    source: function(request, response) {
		    
	             $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllAreas',
	                 data: {
							"areaID" : $("#areaID").val(),
					 },
	                 dataType: "json",
	                 success: function (data) {
	                     if (data != null) {
	                         response(data.listAreas);
	                     }
	                 },
	                 error: function(result) {
	                     alert("Error");
	                 }
	             });
	         }, minLength:0, maxShowItems: 40, scroll:true,

				select: function(event, ui) {
					areaID.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
					return false;
				}
			}).autocomplete("instance")._renderItem = function(ul, item) {
 		    	return $('<li class="each">').data( "item.autocomplete", item )
	    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
                 item[0] + '</span><br><span class="desc">' +
                 item[1] + '</span></div></li>').appendTo(ul);
		};
				$("#areaID").focus(function(){
	   	        	if (this.value == ""){
	   	            	$(this).autocomplete("search");
	   	        	}						
				});   //// ENd of Autocomplete for Area ID
$("#clusterID").autocomplete({					
	source: function(request, response) {
		$.ajax({
			type: "GET",
			contentType: "application/json; charset=utf-8",
			url: '${pageContext.request.contextPath}/GetAllClusters',
				data: {
					"clusterID" : $("#clusterID").val(),
				},
				dataType: "json",
				success: function (data) {
					if (data != null) {
						response(data.listCluster);
					}
				},
				error: function(result) {
					alert("Error");
				}
			});
	}, minLength:0, maxShowItems: 40, scroll:true,
	select: function(event, ui) {
		this.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
		return false;
	}
}).autocomplete("instance")._renderItem = function(ul, item) {
	return $('<li class="each">').data( "item.autocomplete", item )
		.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		item[0] + '</span><br><span class="desc">' +
		item[1] + '</span></div></li>').appendTo(ul);
};
$("#clusterID").focus(function(){
	if (this.value == ""){
		$(this).autocomplete("search");
	}						
});   //// ENd of Autocomplete for Area ID
/////////////////////////// end ready /////////////////////////
});

 /*$("input").change(function() {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		});*/


 $(".add-row-ware").on("click",function(){

		
		var markup = "<tr><td style='text-align:center;'><input style='margin-top:10px;' type='checkbox' name='record'></td>"
					+"<td name='startDate'><input type='date' style='width:200px;' class='form-control text-input'></td>"
					+"<td name='endDate'><input type='date' style='width:200px;' class='form-control text-input'></td>"
					+"<td name='population'><input type='text' value=0 class='form-control text-input'/></td>"
					+"<td name='technologies'><div style='width:180px'><input style='margin-top:10px;' type='checkbox' name='tech_2g' /> 2G <input style='margin-top:10px;' type='checkbox' name='tech_3g'  /> 3G <input style='margin-top:10px;' type='checkbox' name='tech_4g'  /> 4G <input style='margin-top:10px;' type='checkbox' name='tech_5g'  /> 5G</div></td>"
					+"<td name='2gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='3gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='4gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='5gUtilization'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='Availability2G'><input type='text' value=0  class='form-control text-input' /></td>"
					+"<td name='Availability3G'><input type='text' value=0  class='form-control text-input' /></td>"
					+"<td name='Availability4G'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='Availability5G'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='grossADS'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='countOfSSO'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='customerBase'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='data'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='voice'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='smsRevenue'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='grossRevenue'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='dataTraffic'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='totalSMSTrafficOG'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='totalVoiceTrafficOG'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='totalVoiceTrafficIC'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='totalSMSTrafficIC'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='totalOpexMon'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='ProfitAndLoss'><input type='text' value=0 class='form-control text-input' /></td>"
					+"<td name='ProfitLossID'><input type='text' value=0 readonly class='form-control text-input' /></td>";
					

		$("#bisotab tbody").append(markup);

	////Start Autocomplete for Area ID
		$("#areaID").autocomplete({
			
			    source: function(request, response) {
				    
			             $.ajax({
			                 type: "GET",
			                 contentType: "application/json; charset=utf-8",
			                 url: '${pageContext.request.contextPath}/GetAllAreas',
			                 data: {
									"areaID" : $("#areaID").val(),
							 },
			                 dataType: "json",
			                 success: function (data) {
			                     if (data != null) {
			                         response(data.listAreas);
			                     }
			                 },
			                 error: function(result) {
			                     alert("Error");
			                 }
			             });
			         }, minLength:0, maxShowItems: 40, scroll:true,

						select: function(event, ui) {
							areaID.value = (ui.item ? ui.item[0] + ":" + ui.item[1] : '');
							return false;
						}
					}).autocomplete("instance")._renderItem = function(ul, item) {
		 		    	return $('<li class="each">').data( "item.autocomplete", item )
			    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
		                 item[0] + '</span><br><span class="desc">' +
		                 item[1] + '</span></div></li>').appendTo(ul);
				};
						$("#areaID").focus(function(){
			   	        	if (this.value == ""){
			   	            	$(this).autocomplete("search");
			   	        	}						
						});   //// ENd of Autocomplete for Area ID
		
	});

 

 $(".delete-row-ware").click(function(){
 	flagFrom = "delete";
 	getselectedrows ();
     
    if (slctDel.length == 0) {
    	alert(' Select Row(s) to Delete');
  		return false;
  }
    
     var supprimerrow ='';
     	for (i = 0; i < slctDel.length;i++) {
		            
		            $("#bisotab > tbody").find('input[name="record"]').each(function(){
		                if($(this).is(":checked")){
		                    $(this).parents("tr").remove();
		                }
		            });			  
			}
			
		            
 });

 $('body').on('click', '#wareselectAll', function  () {

		if ($(this).hasClass('allChecked')) {
				$('input[name="record"]', '#bisotab').prop('checked', false);
			} else {
				$('input[name="record"]', '#bisotab').prop('checked', true);
				}
				$(this).toggleClass('allChecked');
		
	});

 function selectALLrows () {
		if ($(this).hasClass('allChecked')) {
	   				$('input[name="record"]', '#bisotab').prop('checked', false);
							} else {
	 						$('input[name="record"]', '#bisotab').prop('checked', true);
	 						}
	 						$(this).toggleClass('allChecked');
	}


 function getselectedrows () {

		dict = [];
		slctDel = [];
		var dictObj = {};

		$("#bisotab > tbody").find('input[name="record"]').each(function(){

			if($(this).is(":checked")){
			    console.log("find selected row");

			    if(flagFrom === "delete"){
					console.log('del is :' +   $(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val());
					slctDel.push($(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val());
					console.log(slctDel);
			    }
			    else
			    {
			    	var tech_2g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_2g"]').is(":checked");
					var tech_3g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_3g"]').is(":checked");
					var tech_4g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_4g"]').is(":checked");
					var tech_5g = $(this).parent().parent().children('td[name="technologies"]').children().children('input[name="tech_5g"]').is(":checked");
					
				    if(tech_2g == true)
				    	tech_2g = 1;
				    else
				    	tech_2g = 0;

				    if(tech_3g == true)
				    	tech_3g = 1;
				    else
				    	tech_3g = 0;


				    if(tech_4g == true)
				    	tech_4g = 1;
				    else
				    	tech_4g = 0;

				    if(tech_5g == true)
				    	tech_5g = 1;
				    else
				    	tech_5g = 0;

					dictObj.tech_2g = tech_2g;
					dictObj.tech_3g = tech_3g;
					dictObj.tech_4g = tech_4g;
					dictObj.tech_5g = tech_5g;
					var stDate = $(this).parent().parent().children('td[name="startDate"]').children('input').val();
					stDate = Date.parse(stDate);
				     //console.log('date is  : ' +val1);
				     if (isNaN(stDate) == true) {
				          alert(' Start Date is not valid at Row: '+($(this).parent().parent().index()+1));
				          Errorflag ='1';
				          return false;
				     }
					
					dictObj.startDate = $(this).parent().parent().children('td[name="startDate"]').children('input').val();
					dictObj.endDate = $(this).parent().parent().children('td[name="endDate"]').children('input').val();
					dictObj.population = $(this).parent().parent().children('td[name="population"]').children('input').val();
					dictObj.Utilization2g = $(this).parent().parent().children('td[name="2gUtilization"]').children('input').val();
					dictObj.Utilization3g = $(this).parent().parent().children('td[name="3gUtilization"]').children('input').val();
					dictObj.Utilization4g = $(this).parent().parent().children('td[name="4gUtilization"]').children('input').val();
					dictObj.Utilization5g = $(this).parent().parent().children('td[name="5gUtilization"]').children('input').val();
					dictObj.Availability2G = $(this).parent().parent().children('td[name="Availability2G"]').children('input').val();
					dictObj.Availability3G = $(this).parent().parent().children('td[name="Availability3G"]').children('input').val();
					dictObj.Availability4G = $(this).parent().parent().children('td[name="Availability4G"]').children('input').val();
					dictObj.Availability5G = $(this).parent().parent().children('td[name="Availability5G"]').children('input').val();
					dictObj.grossADS = $(this).parent().parent().children('td[name="grossADS"]').children('input').val();
					dictObj.countOfSSO = $(this).parent().parent().children('td[name="countOfSSO"]').children('input').val();
					dictObj.customerBase = $(this).parent().parent().children('td[name="customerBase"]').children('input').val();
					dictObj.data = $(this).parent().parent().children('td[name="data"]').children('input').val();
					dictObj.voice = $(this).parent().parent().children('td[name="voice"]').children('input').val();
					dictObj.smsRevenue = $(this).parent().parent().children('td[name="smsRevenue"]').children('input').val();
					dictObj.grossRevenue = $(this).parent().parent().children('td[name="grossRevenue"]').children('input').val();
					dictObj.dataTraffic = $(this).parent().parent().children('td[name="dataTraffic"]').children('input').val();
					dictObj.totalSMSTrafficOG = $(this).parent().parent().children('td[name="totalSMSTrafficOG"]').children('input').val();
					dictObj.totalVoiceTrafficOG = $(this).parent().parent().children('td[name="totalVoiceTrafficOG"]').children('input').val();
					dictObj.totalSMSTrafficIC = $(this).parent().parent().children('td[name="totalSMSTrafficIC"]').children('input').val();
					dictObj.totalVoiceTrafficIC = $(this).parent().parent().children('td[name="totalVoiceTrafficIC"]').children('input').val();
					dictObj.totalOpexMon = $(this).parent().parent().children('td[name="totalOpexMon"]').children('input').val();
					dictObj.ProfitAndLoss = $(this).parent().parent().children('td[name="ProfitAndLoss"]').children('input').val();
					dictObj.ProfitLossID = $(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val();
					
					
					dict.push(dictObj);
					dictObj = {};
					console.log("dict size $$$$$$$$$$$$$$ "+dict.length);
			    }	
						 					
	        }
			
			else {
					slctDel = jQuery.grep(slctDel, function(value) {
						return value != $(this).parent().parent().children('td[name="ProfitLossID"]').children('input').val();
						});

						
			  }				  
			  
	    	});
	      
	}

 var map;
	function initMap() {
		  // The map, centered on Central Park
		  const center = {lat: 45.607787, lng: -73.622631};
		  const options = {zoom: 15, scaleControl: true, center: center};
		  map = new google.maps.Map(
		      document.getElementById('map'), options);
		  // Locations of landmarks
		  var long1 = parseFloat($("#warelng").val());
		  var lat1 = parseFloat($("#warlatt").val());
		  var addr1 = $("#wareadd1").val();
		  const dakota = {lat: lat1, lng: long1};
		  ////const frick = {lat: 45.599430, lng: -73.625478};
		    // The markers for The Dakota and The Frick Collection
		  var mk1 = new google.maps.Marker({position: dakota, map: map ,title:addr1});
		  ////var mk2 = new google.maps.Marker({position: frick, map: map ,title:'Racette'});
		   
				//pink:   pink-dot.png
				//yellow: yellow-dot.png
				//purple: purple-dot.png
		        //blue:   blue-dot.png
		 
		   var infowindow1 = new google.maps.InfoWindow({
		          content: 'Welcome to ' +addr1
		        });
		        var marker = new google.maps.Marker({
		          position: dakota,
		          map: map,title:'Narcisses',
				  icon: {
				    
					url: "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png"
					
					
				}
				});
		        infowindow1.open(map, marker);
		   
		   var infowindow2 = new google.maps.InfoWindow({
		          content: 'Destination to Racette!'
		        });
		        var marker = new google.maps.Marker({
		         //// position: frick,
		          map: map,title:'Racette'
		        });
		        infowindow2.open(map, marker);
		   
		   // Calculate and display the distance between markers
		  ////var distance = haversine_distance(mk1, mk2);
		  ////document.getElementById('msg').innerHTML = "Distance between markers: " + distance.toFixed(2) + " mi.";
		 //// var line = new google.maps.Polyline({path: [dakota, frick], map: map});
		  }
	function saverowsintables()
	 {
		 console.log("enter ware save fun");
	 	var wareID = document.getElementById("wareID").value;
	 	 var wcreationDate = document.getElementById("wcreationDate").value;
	 	 var wlastModifieddate = document.getElementById("wlastModifieddate").value;
	 	 var warehouseName = document.getElementById("warepname").value;
	 	 var wareCity = document.getElementById("warcity").value;
	 	 var wareLong = document.getElementById("warelng").value;
	 	 var wareLat = document.getElementById("warlatt").value;
	 	 var checkBox = document.getElementById("warsite").value;
	 	 var siteId = document.getElementById("siteId").value;
	 	 var areaID = document.getElementById("areaID").value;
	 	 var clusterID = document.getElementById("clusterID").value;
	 	 var wareAddress = document.getElementById("wareAddress").value;
	 	 var siteMode = document.getElementById("siteMode").value;
	 	 var wsite ;
	 	 if (warsite.checked == true){
	 		  wsite = '1';
	 		  } else
	 			  {wsite = '0';}
	 	 var checkt2 = document.getElementById("techg2g");
	 	 var tech2 ;
	 	 if (techg2g.checked == true){
	 		 tech2 = '1';
	 		  } else
	 			  {tech2 = '0';}
	 	 
	      var checkt3 = document.getElementById("techg3g");
	 	 var tech3 ;
	 	 if (techg3g.checked == true){
	 		 tech3 = '1';
	 		  } else
	 			  {tech3 = '0';}
	 			  
	      var checkt4 = document.getElementById("techg4g");
	 	 var tech4 ;
	 	 if (techg4g.checked == true){
	 		 tech4 = '1';
	 	 } else
	 	 {tech4 = '0';}
	 	 
	 	 var tech5;
	 	 if (techg5g.checked == true){
	 		 tech5 = '1';
	 	 } else
	 	 {tech5 = '0';}

	 	 var hubSite = document.getElementById("hubSite").value;
	 	 var locationNotes = document.getElementById("locationNotes").value;
	 	 var shelter = document.getElementById("shelter").value;
	 	 var shelterType = document.getElementById("shelterType").value;
	 	 var shelterVendor = document.getElementById("shelterVendor").value;
	 	 var shelterID = document.getElementById("towerHeight").value;
	 	 var existing_newtown = document.getElementById("existing_newtown").value;
	 	 var showcase = document.getElementById("showcase").value;
	 	 var siteOwner = document.getElementById("siteOwner").value;
	 	 var towerCoID = document.getElementById("towerCoID").value;
	 	 var towerType = document.getElementById("towerType").value;
	 	 var towerHeight = document.getElementById("towerHeight").value;
	 	 var buildingHeight = document.getElementById("buildingHeight").value;
	 	 var shared = document.getElementById("shared").value;
	 	 var icrCategory = document.getElementById("icrCategory").value;
	 	 var transmission = document.getElementById("transmission").value;
	 	 
	 	 
	 	 var wshelter;
	 	 if(shelterCheck.checked == true){
	 		 wshelter = 1;
	 		 } else
	 			 {wshelter = 0;}


	 		 $.ajax({
	 			type : "GET",
	 			url : "${pageContext.request.contextPath}/WarehouseFormSave",
	 			dataType : "json",
	 			data : {
	 				"wareID" : $("#wareID").val(),
	 				"wcreationDate" : $("#wcreationDate").val(),
	 				"wlastModifieddate" : $("#wlastModifieddate").val(),
	 				"warehouseName" : $("#warepname").val(),
	 				"wareCity" : $("#warcity").val(),
	 				"wareLong" : $("#warelng").val(),  
	 				"wareLat" : $("#warlatt").val(),
	 				"siteMode" : $("#siteMode").val(),
	 				"wareSite" : wsite,	
	 				"siteId" : siteId, 
	 				"tech2G" : tech2,
	 				"tech3G" : tech3,
	 				"tech4G" : tech4,
	 				"tech5G" : tech5,
	 				"areaID" : areaID,
	 				"clusterID" : clusterID,
	 				"wareAddress" : $("#wareAddress").val(),
	 				"hubSite" : $("#hubSite").val(),	 
	 				"locationNotes" : $("#locationNotes").val(),
	 				"shelter" : $("#shelter").val(),	
	 				"shelterID" : $("#shelterID").val(), 
	 				"shelterType" : $("#shelterType").val(),	 
	 				"shelterVendor" : $("#shelterVendor").val(),
	 				"wshelter" : wshelter,
	 				"existing_newtown" : existing_newtown,
	 				"showcase" : showcase,
	 				"siteOwner" : siteOwner,
	 				"towerCoID" : towerCoID,
	 				"towerType" : towerType,
	 				"towerHeight" : towerHeight,
	 				"buildingHeight" : buildingHeight,
	 				"shared" : shared,
	 				"icrCategory" : icrCategory,
	 				"transmission" : transmission,
	 				"dictParameter": dict
	 					 
	 			},
	 			success : function(data) {
	 				console.log("The returned data is " +data.BassamTest);
	 				/*$('#wlstmodifdate').val(data.wlastModifieddate);
	 				$("#wareID").val(data.wareID);
	 				var param ="${pageContext.request.contextPath}/WarehouseFormView?wareID="+$("#wareID").val()+"&warehouseList=warehouseList";
	 				location.replace(param);*/
	 				location.reload(true);
	 			},
	 			error : function(error) {
	 				console.log("The error is " + error);
	 			}
	 		});
	 }
 </script>
 
 
 <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap">
	</script>