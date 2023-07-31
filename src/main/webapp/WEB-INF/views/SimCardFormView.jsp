<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@ include file="scripts.html" %>	
	<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- 	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">  -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css">	 
<script src="${pageContext.request.contextPath}/resources/js/popper-1.12.9-min.js"></script>
<!--     <script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>	 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet"/>
<!-- 	<script src="${pageContext.request.contextPath}/resources/js/dataTables.min.js"></script>  -->
	<script src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/platform.js"></script>	
<!-- 	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataTables.min.css">  -->
    <script src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
	<link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet">
<!-- 	
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/almgrid.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/almgrid.class.js"></script>

		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/clusterize.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/resources/almgrid/clusterize.js"></script>			
	-->		
			<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/almgrid/Collapse.css" />
	
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
					width: 350px;
	        		}
	.checkCenter{
   margin: 0;
  position: absolute;
  top: 50%;
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
	}

	.rightpush{

  right: 50%;
 
	}
	  th{text-align:center;} 
	td {text-align: center;vertical-align: middle!important;}
	
	 .hide-row { display:none; }
	
	li.nav-item a svg
	{
		color:gold !important;
	}
	select
	{
		width: 150px;
		height: 25px;
		border-collapse: collapse;
		cursor: pointer;
	}  
	.case
	{
	text-align:center;
	font-size: 16px;
	color:#0000FF;
	position:relative;
	top:7px;
	}
	#testing
	{
	text-align:center;
	font-size: 16px;
	color:#0000FF;
	position:relative;
	padding:7px;
	}
	.btnApproval
	{
		cursor: default !important;
		width: 265px !important;
		font-size: 14px;
	}
	
	
	
	.left_col
				{
					height:60px
				}
				
				
				
	 
	
	.btn-primary:hover
	{
		background-color: #007bff !important;
	} 

	
	#ProjectManagerApprove, #AssetUnitApprove, #FinanceApprove 
	{
		cursor: pointer;
	} 
	
	.transType
	{
		width:330px !important;
	}		
	
	.NotesInput
	{
		width: 400px !important;
	} 
	
	.elementName
	{
		width: 180px !important;
	}
	
	.inputWidth
	{
		width:100px !important;
	}
	
	#discountAmount
	{
		width: 126px !important;
	}
	#collapseOne {
		height:560px;
		width:800px;
	    float:right; 
	   
		}
		
		#areaTab {
		     float:left; /* important */
		   /* width:595px; */
		    height:auto;
		   	    		   
		    
		}
	
	     #left {
            float: left;
            width: 30%;
            height: 100%;
        }
        
        #right {
          	padding-right:0%;
       		float:right;
            width: 60%;
            height: 100%;
        }
        
      
        
        
       #mapText{
       border:hidden;
       width:125px;
       height:25px;
       border:hidden;
       background-color:#87CEEB;
       margin-left:20px;
       text-align: center;
       } 
 	</style>
</head>
<body>
  <c:set var="pg" value="inventory" scope="session"  />
 <jsp:include page="header.jsp"></jsp:include>
	 <p></p>
<div class="container-fluid">
		<div class="row">
			
		
			<div class="col-md-3">
				   <div class="form-group">
						<div class="input-group-prepend">
							<span class="input-group-text" style="color:green">Sim Card ID</span>
							<input type="text" readonly id="simId"  value="${simId}" class="form-control text-input"   />
 							<input name="csrfToken" value="5965f0d244b7d32b334eff840" type="hidden" /> 
						</div>
					</div>
			</div>

									<div class="col-md-3">
				<div class="input-group-prepend">
				<span  class="input-group-text" style="width:210px;">Status</span>
				<select id="ordstat" class="form-control">
								<option disabled value="In Progress" <c:if test = "${ordStatus =='In Progress'}" > selected </c:if> >In Progress</option>
								<option disabled value="Activated" <c:if test = "${ordStatus =='Activated'}" > selected </c:if>>Activated</option>
								<option disabled value="Deactivated" <c:if test = "${ordStatus =='Deactivated'}" > selected </c:if>>Deactivated</option>
								</select>
				</div>							
			</div>

	<div class="pad col-md-3 hide-row"></div>
		<div class="col-md-3 nextprvItems">
			<div class="form-group">
				<div class="input-group-prepend">
					<span class="input-group-text">Other Sim Cards</span>
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
					<span style="width:200px;" class="input-group-text">Created Date</span>
					<input type="text" id="creationDate" readonly value="${creationDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					   <div class="input-group-append" data-target="#datetimepicker1" data-toggle="datetimepicker">
							
						</div>
			</div>
		</div>
	</div>

	<div class="col-md-3">
			<div class="form-group">
				<div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
					<span style="width:200px;" class="input-group-text">Last Modify Date</span>
										<input type="text" id="lastModifiedDate" readonly value="${lastModifiedDate}" class="form-control datetimepicker-input" data-toggle="datetimepicker" data-target="#datetimepicker1"   />
					
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
							class="btn btn-success next">&raquo;</a></li>
			  		</ul>
				</nav>
		</div>
		
		
	
	
	<div class="col-md-3" style="text-align: right;">
		 		<div class="btn-group pull-right">
 					
		 			<div class="glyph"> 
  
			 			<button  type="button" id="Fview"  class="btn btn-danger" data-toggle="tooltip" 
			 				onclick='window.location.href = "${pageContext.request.contextPath}/SimCardFormView"'			
			 				data-placement="top" title="Form View" style="background: #da6815;"> 
			 				<i class="fa fa-edit"></i>
			        	</button>
			        	<button type="button" id="Lview"  class="btn btn-light" data-toggle="tooltip"
			        			onclick='window.location.href = "${pageContext.request.contextPath}/SimCardListView"'
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
		
		
	<p></p>
	
	

<div class="container-fluid">

<div class="row">
<div class="col-12 col-sm-12 col-lg-12">	
      <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: -20px;">
             <li class="nav-item"><a class="nav-link active"
            id="custom-tabs-one-home-tab" data-toggle="tab"
            href="#custom-tabs-one-home" role="tab"
            aria-controls="custom-tabs-one-home" aria-selected="true" style="color: gold;">INFORMATION</a></li>
            <li class="nav-item ml-auto">
             <div class="dropdown" Style="display:inline-block;">
	           <button class="btn btn-secondary dropdown-toggle" type="button" id="notifactionDropdown" data-toggle="dropdown" 
	            aria-haspopup="true" aria-expanded="false">Actions</button>	
	            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	             <a class="dropdown-item"  type="button" id="Activatewh" >Activate</a>
	             <a class="dropdown-item" type="button" id="Deactivatewh" >Deactivate</a>
    	    	</div>  	
    	    	  <button type="button" id="sendEmail" class="btn btn-primary BtnActive"><i class="fa fa-envelope"></i> Send Email </button>
           
            </div>
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
	<div class="tab-pane fade show active" id="custom-tabs-one-home"
	role="tabpanel" aria-labelledby="custom-tabs-one-home-tab">
    
     
<p></p>
    		
		<div id="left">
 
	 							 						<div  style="width: auto;">
			<div class="input-group-prepend">
						<span class="input-group-text">MSISDN</span>
						<input type="text" id="TeleNum" value="${TeleNum}" class="form-control text-input" style="width: max;margin-right: -80px;"/>
					
			</div>

							<p></p>
				
		</div>
		
		<div  style="width: auto;">
			<div class="form-group">
				<div class="input-group-prepend">
						<span class="input-group-text">Serial Number</span>
						<input type="text" id="SrlNum" value="${SrlNum}" class="form-control text-input"  style="width: max;margin-right: -80px;"/>
					
				</div>
			</div>
	</div>
			<p></p>			
	 							 						<div  style="width: auto;">
			<div class="form-group">
				<div class="input-group-prepend">
						<span class="input-group-text">Location Type</span>
						<input type="text" id="locationType" value="${locationType}" class="form-control text-input"  style="width: max;margin-right: -80px;"/>
					
				</div>
			</div>
	</div>
		<p></p>

	 							 						<div  style="width: auto;">
			<div class="input-group-prepend">
						<span class="input-group-text">Location ID</span>
						<input type="text" id="locationID" value="${locationID}" class="form-control text-input" style="width: max;margin-right: -80px;"/>
					
			</div>
				
	</div>
		<p></p>
	 							 						<div  style="width: auto;">
			<div class="input-group-prepend">
						<span class="input-group-text">Location Name</span>
						<input type="text" id="locationName" value="${locationName}" class="form-control text-input"  style="width: max;margin-right: -80px;"/>
					
			</div>
		</div>
	<p></p>
	 							 						<div  style="width: auto;">
			<div class="form-group">
				<div class="input-group-prepend">
				<span class="input-group-text">Longtitude</span>
					<input type="text" id="longtitude" value="${longtitude}" class="form-control text-input" style="width: max;margin-right: -80px;"/>			
				</div>
			</div>
	</div>	
		
		<p></p>
	
	
	
	
	 							 						<div  style="width: auto;">
                                <div class="input-group-prepend">
                             				<span class="input-group-text">Latitude</span>
					<input type="text" id="latitude" value="${latitude}" class="form-control text-input" style="width: max;margin-right: -80px;"/>
			                               
                                </div>
                            </div> 
	<p></p>

	</div>
	
			<div id="right">
	
	 <div id="collapseOne" class="panel-collapse collapse show"  aria-labelledby="headingOne">
		  <div class="panel-body" style="height: 82.5%;margin-left:10%"  >
	
		  <div class="card-body" style='border:hidden;'>
		  <div class="btn-toolbar" style="text-align: left; margin-bottom: 5px;margin-top: auto;">
		  <div class="btn-group flex-wrap" data-toggle="buttons" style="row-gap: 2px;">
		  <div class=" top-btn-filter"  >
		  <button id="CoordsButton" name="CoordsButton" class="buttonTog" ><i class="fas fa-map"></i> Get Coordinates</button>
		  </div>
		  <div class=" top-btn-filter"  >
		  <button id="deleteCoordBtn" name="deleteCoordBtn" class="buttonTog" style='margin-left:10px;'><i class="fa fa-trash"></i> Delete Area</button>
		  </div>
		  <div id="txtDiv"><input id = "mapText" type='text' disabled />
		  </div>
		  </div>
		  </div>
		  <div id="mapContainer" style='height: 390px; '> 
		  </div>
		  </div>  
		  </div>
		  </div>	
	
	
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
 
 
 
 <script type='text/javascript'>
 if ('${docStatus}' == "addNew") {
		$("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});	
		$(".nextprvItems").addClass("hide-row ");
		$(".pad").removeClass("hide-row ");
	}

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

$("#Activatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Activated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	 $("#Deactivatewh").click(  function() {	
	   
	 var Status=$("#ordstat").val();
		 $("#ordstat").val('Deactivated').trigger('change');
		 $("#formStatus").text("Not Saved");
			$('.dot').css({"background-color" : "orange"});
	 
	})
	
	if ('${type}' == "addNew") {
		 $("#ordstat").val('In Progress').trigger('change');
}
else{}

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
	
	 //function to get selected rows for save or delete
 let map;
let service;
let infowindow;

function initMap() {
	var latitude = parseFloat(document.getElementById("latitude").value);
	var longtitude = parseFloat(document.getElementById("longtitude").value);
	var defaultZoom =17;
 		var Beirut=new google.maps.LatLng(33.8938, 35.5018);
 		
				  if(  document.getElementById("latitude").value === ''|| document.getElementById("longtitude").value=== '') {

					 longtitude="35.5018";
					 latitude="33.8938";
					 
					 longtitude=parseFloat(longtitude);
					 latitude=parseFloat(latitude);
					 defaultZoom=13;
					 }
	var myLatlng = { lat: latitude, lng: longtitude };
	const	map = new google.maps.Map(document.getElementById("mapContainer"), {
		mapTypeControl: false,
 	    center: { lat: 0.300, lng: 37.761 },

		zoom:6,
		mapTypeControl: true,
		
			mapTypeControlOptions: {
				style: google.maps.MapTypeControlStyle.HORIZONTAL_BAR,
				position: google.maps.ControlPosition.TOP_CENTER,
			},
			zoomControl: true,
			zoomControlOptions: {
				position: google.maps.ControlPosition.LEFT_CENTER,
			},
			scaleControl: true,
			streetViewControl: true,
			streetViewControlOptions: {
				position: google.maps.ControlPosition.LEFT_TOP,
			},

			style: 'mapbox://styles/mapbox/streets-v11',
			fullscreenControl: true,
			
});
	  // Create the initial InfoWindow.
	  let infoWindow = new google.maps.InfoWindow;

	  infoWindow.open(map);
	  // Configure the click listener.
	  map.addListener("click", (mapsMouseEvent) => {
	    // Close the current InfoWindow.
	    infoWindow.close();
	    // Create a new InfoWindow.
	    infoWindow = new google.maps.InfoWindow({
	      position: mapsMouseEvent.latLng,
	      
	    });
	     myLatlng=JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2);

	    infoWindow.setContent(
	    	    
	      JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)

		    
	    );
	    
	    const myJSON = JSON.stringify(myLatlng);
	    localStorage.setItem("testJSON", myJSON);
	    let text = localStorage.getItem("testJSON");
	    
	  
	    let obj = JSON.parse(text);
		var arr = obj.split(",");
		var reslatitude =arr[0].substring(11);
		var reslongtitude =arr[1].substring(10);
		reslongtitude=reslongtitude.slice(0,-1);

			google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {
				
				$('#latitude').val(reslatitude);

			      document.getElementById("longtitude").value = reslongtitude;
			      unsaved = true;
					 $("#formStatus").text("Not Saved");
						$('.dot').css({"background-color" : "orange"});

			}));
			 google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (Path) {
				 deleteMarkers();
					$('#latitude').val("");
											
				      document.getElementById("longtitude").value = "";
				      unsaved = true;
						 $("#formStatus").text("Not Saved");
							$('.dot').css({"background-color" : "orange"});
				}));
			
	    infoWindow.open(map);
				   

	  });

	  let markers = [];





		function addMarker(position) {
			  const marker = new google.maps.Marker({
			    position,
			    map,
			  });

			  markers.push(marker);
			}

			// Sets the map on all markers in the array.
			function setMapOnAll(map) {
			  for (let i = 0; i < markers.length; i++) {
			    markers[i].setMap(map);
			  }
			}

			// Removes the markers from the map, but keeps them in the array.
			function hideMarkers() {
			  setMapOnAll(null);
			}

			// Shows any markers currently in the array.
			function showMarkers() {
			  setMapOnAll(map);
			}

			// Deletes all markers in the array by removing references to them.
			function deleteMarkers() {
			  hideMarkers();
			  markers = [];
			}


			var position1 = new google.maps.LatLng(latitude, longtitude);

			addMarker(position1);
	  
		$("#mapText").val(JSON.stringify((-1.286) +" || "
			    +(36.817), null, 2));
		map.addListener("mousemove", (mapsMouseEvent)=>{
		    $("#mapText").val(JSON.stringify(mapsMouseEvent.latLng.toJSON().lat.toFixed(3) +" || "
		    	    +mapsMouseEvent.latLng.toJSON().lng.toFixed(3), null, 2));
		    
		    
		  });

		$('#longtitude,#latitude').on('keypress', function() {
			setTimeout(function() {
				deleteMarkers();


				latitude = parseFloat(document.getElementById("latitude").value);
				longtitude = parseFloat(document.getElementById("longtitude").value);
				longtitude=parseFloat(longtitude);
				latitude=parseFloat(latitude);

					var position1 = new google.maps.LatLng(latitude, longtitude);

					addMarker(position1);




				 for (let i = 0; i < markers.length; i++) {
					    markers[i].setMap(map);
					  }		// alert(rowIndex);
		    }, 1);
					
			  
			 }).on('keydown', function(e) {
	if (e.keyCode==8)
		$('#longtitude,#latitude').trigger('keypress');


	setTimeout(function() {
		deleteMarkers();


		latitude = parseFloat(document.getElementById("latitude").value);
		longtitude = parseFloat(document.getElementById("longtitude").value);
		longtitude=parseFloat(longtitude);
		latitude=parseFloat(latitude);

			var position1 = new google.maps.LatLng(latitude, longtitude);

			addMarker(position1);




		 for (let i = 0; i < markers.length; i++) {
			    markers[i].setMap(map);
			  }		
			
	 }, 1);
				});

		google.maps.event.addDomListener($(document).on("change", "#longtitude,#latitude", function (){
			deleteMarkers();
					
			
			 latitude = parseFloat(document.getElementById("latitude").value);
			 longtitude = parseFloat(document.getElementById("longtitude").value);
			 longtitude=parseFloat(longtitude);
			 latitude=parseFloat(latitude);

				var position1 = new google.maps.LatLng(latitude, longtitude);

				addMarker(position1);




			  for (let i = 0; i < markers.length; i++) {
				    markers[i].setMap(map);
				  }			
			 			  }));


		map.addListener("click", (mapsMouseEvent) => {
     	    // Close the current InfoWindow.
     	    infoWindow.close();
     	    // Create a new InfoWindow.
     	    infoWindow = new google.maps.InfoWindow({
     	      position: mapsMouseEvent.latLng,
     	      
     	    });
     	     myLatlng=JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2);
     	    infoWindow.setContent(
     	    	    
     	      JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
     		    
     	    );
     	    
     	    const myJSON = JSON.stringify(myLatlng);
     	    localStorage.setItem("testJSON", myJSON);
     	    let text = localStorage.getItem("testJSON");
     	    
     	  
     	    let obj = JSON.parse(text);
     		var arr = obj.split(",");
     		var reslatitude =arr[0].substring(11);
     		var reslongtitude =arr[1].substring(10);
     		reslongtitude=reslongtitude.slice(0,-1);

     			google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {
     				
     				$('#latitude').val(reslatitude);
     			      document.getElementById("longtitude").value = reslongtitude;

               	    infoWindow.close();

     					deleteMarkers();

     				 latitude = parseFloat(document.getElementById("latitude").value);
     				 longtitude = parseFloat(document.getElementById("longtitude").value);
     				 longtitude=parseFloat(longtitude);
     				 latitude=parseFloat(latitude);

     					var position1 = new google.maps.LatLng(latitude, longtitude);

     					addMarker(position1);




     				  for (let i = 0; i < markers.length; i++) {
     					    markers[i].setMap(map);
     					  }		
         

     			}));
     			 google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (Path) {
     				 
     					$('#latitude').val("");
     											
     				      document.getElementById("longtitude").value = "";
     				}));
     			
     	    infoWindow.open(map);
     	  });


    	 map.addListener("click", (mapsMouseEvent) => {
      	    // Close the current InfoWindow.
      	    infoWindow.close();
      	    // Create a new InfoWindow.
      	    infoWindow = new google.maps.InfoWindow({
      	      position: mapsMouseEvent.latLng,
      	      
      	    });
      	     myLatlng=JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2);
      	    infoWindow.setContent(
      	    	    
      	      JSON.stringify(mapsMouseEvent.latLng.toJSON(), null, 2)
      		    
      	    );
      	    
      	    const myJSON = JSON.stringify(myLatlng);
      	    localStorage.setItem("testJSON", myJSON);
      	    let text = localStorage.getItem("testJSON");
      	    
      	  
      	    let obj = JSON.parse(text);
      		var arr = obj.split(",");
      		var reslatitude =arr[0].substring(11);
      		var reslongtitude =arr[1].substring(10);
      		reslongtitude=reslongtitude.slice(0,-1);
   
      			google.maps.event.addDomListener($(document).on('click', '#CoordsButton', function (Path) {
      				
      				$('#latitude').val(reslatitude);
      			      document.getElementById("longtitude").value = reslongtitude;

                	    infoWindow.close();

      					deleteMarkers();

      				 latitude = parseFloat(document.getElementById("latitude").value);
      				 longtitude = parseFloat(document.getElementById("longtitude").value);
      				 longtitude=parseFloat(longtitude);
      				 latitude=parseFloat(latitude);

      					var position1 = new google.maps.LatLng(latitude, longtitude);

      					addMarker(position1);




      				  for (let i = 0; i < markers.length; i++) {
      					    markers[i].setMap(map);
      					  }		
 

      			}));
      			 google.maps.event.addDomListener($(document).on('click', '#deleteCoordBtn', function (Path) {
      				 
      					$('#latitude').val("");
      											
      				      document.getElementById("longtitude").value = "";
      				}));
      			
      	    infoWindow.open(map);
      	  });
		

		}
	
	
	 var unsaved = false;
		$(window).bind('beforeunload', function() {
		    if(unsaved){
		        return "You have unsaved changes. Do you want to leave and discard?";
		    }
		});
	 		$(document).on('change', ':input', function(){
			    unsaved = true;
			});

 $("#ordstat").change(function () {
	 $("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		unsaved = true;
 });

 $("input").on('keyup change', function () {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		 unsaved = true;
		});

 $("#locationID").on('keyup change', function () {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		 unsaved = true;
		});

 $("#locationName").on('keyup change', function () {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		 unsaved = true;
		});

 $("#locationType").on('keyup change', function () {
		$("#formStatus").text("Not Saved");
		$('.dot').css({"background-color" : "orange"});
		 unsaved = true;
		});

 $("#deleteButton").click(  function() {
	 var simId = document.getElementById("simId").value;
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/SimCardDelete",
			dataType : "json",
			data : {
				"ID" : [$("#simId").val()]
			},
			success : function(data) {

			    location.replace("${pageContext.request.contextPath}/SimCardListView");
			},
			error : function(error) {
				console.log("The error happened when deleting " + error);
			}
		});
	 
 })

 
 




 
if ('${type}' == "addNew") {
	 $("#formStatus").text("New");
		$('.dot').css({"background-color" : "orange"});
}
else{}


	
 $(document).ready(function() {


		if('${SelectedIndex}' != "addNew"){
						var SelectedIndex = ${SelectedIndex};
						if('${simCardCount}' != "addNew"){

					var simCardCount = ${simCardCount};
					
					if(($("#simId").val()) != "" && ($("#simId").val()) != null){

					if(SelectedIndex === simCardCount){
						
		        		document.getElementById("btnLast").style.opacity = 0.5;
		        		$("#btnLast").hasClass("disabled");
		        		document.getElementById("btnLast").style.pointerEvents = "none";
		        		
		        		document.getElementById("btnNexta").style.opacity = 0.5;
		        		document.getElementById("btnNexta").style.pointerEvents = "none";

						
						$("#btnNexta").hasClass("disabled");
						
						}else{
							
							if(!$("#btnNexta").hasClass("disabled")){
								
								$("#btnNext").click(function(){
									
									var param ="${pageContext.request.contextPath}/SimCardFormView?simId="+$("#simId").val()+"&NavAction=1";

									window.location.href =param;
						
								});
					
							}
							if(!$("#btnLst").hasClass("disabled")){
		        				
		        				$("#btnLst").click(function(){
		        					
									var param ="${pageContext.request.contextPath}/SimCardFormView?simId="+$("#simId").val()+"&NavAction=4";
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
								
								var param ="${pageContext.request.contextPath}/SimCardFormView?simId="+$("#simId").val()+"&NavAction=0";
								window.location.href =param;
								
							 });
						}
						$("#btnFrst").click(function(){

		        			if(!$("#btnFrst").hasClass("disabled")){
		        					
								var param ="${pageContext.request.contextPath}/SimCardFormView?simId="+$("#simId").val()+"&NavAction=3";
		        				window.location.href =param;
		        						
		        				}
		        				 });

					}
					
					}}
				}
					$("#label-1").text((SelectedIndex)+"/"+simCardCount);

					 $("#selectnav").autocomplete({
			    			
			    		    source: function(request, response) {
			    			    
			    		             $.ajax({
			    		                 type: "GET",
			    		                 contentType: "application/json; charset=utf-8",
			    		                 url: '${pageContext.request.contextPath}/GetAllSimCards',
			    		                 data: {
			    								"simId" : $("#selectnav").val(),
			    						 },
			    		                 dataType: "json",
			    		                 success: function (data) {
			    		                     if (data != null) {
			    		                         response(data.ListSim);
			    		                     }
			    		                 },
			    		                 error: function(result) {
			    		                     alert("Error");
			    		                 }
			    		             });
			    		         }, minLength:0, maxShowItems: 40, scroll:true,

			    					select: function(event, ui) {
			    						
			    						this.value = (ui.item ? ui.item[1] + ":" + ui.item[0] : '');
			    						
			    						var param ="${pageContext.request.contextPath}/SimCardFormView?simId="+(ui.item[0])+"&NavAction=2";
			    						window.location.href =param;
			           						
			           						return false;
			           					}
			           				}).autocomplete("instance")._renderItem = function(ul, item) {
			           	 		    	return $('<li class="each">').data( "item.autocomplete", item )
			           		    			.append('<div class="acItem"><span class="name" style="font-weight:bold">' +
			           	                 item[1] + '</span><br><span class="desc">' +
			           	                 item[0] + '</span></div></li>').appendTo(ul);
			           			};
			           					$("#selectnav").focus(function(){
			           		   	        	if (this.value == ""){
			           		   	            	$(this).autocomplete("search");
			           		   	        	}						
			           					});   //// ENd of Autocomplete for Area ID
			    	

	 
	 function customRenderItem(ul, item) {
			var t = '<span class="mcacCol1">' + item[0] + '</span><span class="mcacCol2">' + item[1] + '</span>',
				result = $('<li class="ui-menu-item" role="menuitem"></li>')
				.data('item.autocomplete', item)
				.append('<a class="ui-corner-all" style="position:relative;" tabindex="-1">' + t + '</a>')
				.appendTo(ul);

			return result;
		}
	 console.log("test done");
		//var unsaved = false;				 
		 $("input").change(function() {
				$("#formStatus").text("Not Saved");
				$('.dot').css({"background-color" : "orange"});
				unsaved = true;
				});




	 		$(document).on('click', '#saveButton', function() {
	 			 unsaved = false;

	 			 if ($("#TeleNum").val() == '')  { alert(' MSISDN is not valid');
	 			 document.getElementById("TeleNum").focus();
		          return false;  }

	 			  if ($("#SrlNum").val() == '') {
			          alert(' serial number is not valid');
			          document.getElementById("SrlNum").focus();
			          return false;			        
			        }

	 			if ($("#locationID").val() == '')  { alert(' location ID is not valid');
	 			 document.getElementById("locationID").focus();
		          return false;  }

	 			if ($("#locationName").val() == '')  { alert('  location Name is not valid');
	 			 document.getElementById("locationName").focus();
		          return false;  }

	 			if ($("#locationType").val() == '')  { alert(' location Type is not valid');
	 			 document.getElementById("locationType").focus();
		          return false;  }

	 			

	 			 saverowsintables();
					 
				 
				 
	 		 }); // end of main SAVE button
		 		
	            function saverowsintables (){

	    		     var status = document.getElementById("ordstat").value;
	    			 var latitude = document.getElementById("latitude").value;

	    			 var longtitude = document.getElementById("longtitude").value;
	 				$.ajax({
	 					type : "POST",
	 					url : "${pageContext.request.contextPath}/SimCardFormSave",
	 					dataType : "json",
	    					data : {
	    						"type": '${type}',        				
	    						"simId" : $("#simId").val(),
	    						"status": status,
	    						"latitude" : latitude,
	    					    "longtitude" : longtitude,
	    						"lastmodifiedDate" : $("#lastModifiedDate").val(),
	    						"creationDate" : $("#creationDate").val(),
	    						"SrlNum" : $("#SrlNum").val(),
	    						"TeleNum": $("#TeleNum").val(),
	    						"locationID": $("#locationID").val(),
	    						"locationName": $("#locationName").val(),
	    						"locationType": $("#locationType").val(),
	    						"email": $("#email").val(),
	    						"password":$("#password").val(),
	    				  	    "emailTo": $("#emailTo").val(),
	    					    "ccmail": $("#ccmail").val(),
	    					    "subject": $("#subject").val(),
	    					    "message": $("#message").val(),

	    					},
	    					success : function(data) {
	    						console.log("The returned data is " + data.Iresponse);
	    										
	    						$("#simId").val(data.simId);
	    						var param ="${pageContext.request.contextPath}/SimCardFormView?simId="+$("#simId").val()+"&NavAction=2";
	    						location.replace(param); 						    						
	    					},
	    					error : function(error) {
	    						console.log("The error is " + error);
	    					}
	    				});
	    				
	          
	           		}   //end save data



	        	$("#locationID").autocomplete({
	        	    
	        	    source: function(request, response) {


	                     var location=$("#locationID").val();
	                    
	                         $.ajax({
	                             type: "GET",
	                             contentType: "application/json; charset=utf-8",
	                             url: '${pageContext.request.contextPath}/GetAllLocations',
	                             data: {
	            						
	            						"location":location,
	            				 },
	        	                 dataType: "json",
	        	                 success: function (data) {
	        	                     if (data != null) {
	        	                         response(data.listLocations);
	        	                     }
	        	                 },
	        	                 error: function(result) {
	        	                     alert("Error");
	        	                 }
	        	             });
	        	         }, minLength:0, maxShowItems: 40, scroll:true,

	        				select: function(event, data) {
	        					unsaved = true;
 	        					document.getElementById("locationName").value=(data.item ? data.item[1] : '');
 	        					document.getElementById("locationID").value=(data.item ? data.item[0]  : '');
 	        					document.getElementById("locationType").value=(data.item ? data.item[2]  : '');
 	        					document.getElementById("longtitude").value=(data.item ? data.item[3]  : '');
 	        					document.getElementById("latitude").value=(data.item ? data.item[4]  : '');
 	        					$("#formStatus").text("Not Saved");
 	        					$('.dot').css({"background-color" : "orange"});
 	        					 
	        					return false;
	        				}
	        			}).autocomplete("instance")._renderItem = function(ul, item) {
	        		            return $("<li class='each'>")
	        	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	        	                    item[0] + "</span><br><span class='desc'>" +
	        	                    item[1]  +","+ item[2] + "</span></div>")
	        	                .appendTo(ul);
	        	        	};
	        				$("#locationID").focus(function(){
	        	   	        	if (this.value == ""){
	        	   	            	$(this).autocomplete("search");
	        	   	        	}						
	        				});

	        	        	$("#locationName").autocomplete({
	        	        	    
	        	        	    source: function(request, response) {


	        	                     var location=$("#locationName").val();
	        	                    
	        	                         $.ajax({
	        	                             type: "GET",
	        	                             contentType: "application/json; charset=utf-8",
	        	                             url: '${pageContext.request.contextPath}/GetAllLocations',
	        	                             data: {
	        	            						
	        	            						"location":location,
	        	            				 },
	        	        	                 dataType: "json",
	        	        	                 success: function (data) {
	        	        	                     if (data != null) {
	        	        	                         response(data.listLocations);
	        	        	                     }
	        	        	                 },
	        	        	                 error: function(result) {
	        	        	                     alert("Error");
	        	        	                 }
	        	        	             });
	        	        	         }, minLength:0, maxShowItems: 40, scroll:true,

	        	        				select: function(event, data) {
	         	        					 unsaved = true;

	         	        					document.getElementById("locationName").value=(data.item ? data.item[1] : '');
	         	        					document.getElementById("locationID").value=(data.item ? data.item[0]  : '');
	         	        					document.getElementById("locationType").value=(data.item ? data.item[2]  : '');
	         	        					document.getElementById("longtitude").value=(data.item ? data.item[3]  : '');
	         	        					document.getElementById("latitude").value=(data.item ? data.item[4]  : '');
	         	        					$("#formStatus").text("Not Saved");
	         	        					$('.dot').css({"background-color" : "orange"});
	        	        					return false;
	        	        				}
	        	        			}).autocomplete("instance")._renderItem = function(ul, item) {
	        	        		            return $("<li class='each'>")
	        	        	                .append("<div class='acItem'><span class='name' style='font-weight:bold'>" +
	        	        	                    item[1] + "</span><br><span class='desc'>" +
	        	        	                    item[0]  +","+ item[2] + "</span></div>")
	        	        	                .appendTo(ul);
	        	        	        	};
	        	        				$("#locationName").focus(function(){
	        	        	   	        	if (this.value == ""){
	        	        	   	            	$(this).autocomplete("search");
	        	        	   	        	}						
	        	        				});


	        				
////////////////////////////////autocomplete from EmailAccounts /////////////////////////////////////////////////////
	$("#email").autocomplete({
	    source: function(request, response) {
	         
	    	var password=$("#password").val();
	           $.ajax({
	                 type: "GET",
	                 contentType: "application/json; charset=utf-8",
	                 url: '${pageContext.request.contextPath}/GetAllEmailAccounts',
	                 data: {
							"email" : request.term,
							//"password":request.value,
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
			
	 //////////////////////////////////////////////////////////////////////////////		  	

	 
		  });
//end document ready


 

</script>
			 
<script
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBJXAds-Gt4I39hRFHhYHMEg3XcBqihYoo&callback=initMap&libraries=drawing&v=weekly"
      async defer
    ></script>
				
</html>
