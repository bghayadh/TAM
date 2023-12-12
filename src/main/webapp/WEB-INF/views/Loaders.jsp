<!DOCTYPE html>
<%@page import="com.aliat.alm.models.*"%>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
   <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link href="${pageContext.request.contextPath}/resources/css/all.min.css" rel="stylesheet" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/all.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ListView.css">
    
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/xlsx.full.min.js"></script>
    
    
   
<title>Loaders</title>
<style>
.cadr {
   border: 0.1em solid #08526d;
   border-radius: .25rem;
}

.card-body{
	padding:0px !important;
}

.cardsClass {
    min-width: 310px;
    min-height: 260px;
    border-radius: 10px;
    padding: 10px 2px;
}

.Cardheader {
    text-align: center;
    font-family: cursive;
    color: #DCF8C6;
    border-radius: 25px 25px 0px 0px!important;
    border-style: groove;
    border-width: 2px;
    border-color: #4B8C8C;
    box-shadow: 5px 5px 5px rgb(75,140,140);
    font-size: 14px;
}

.CardBody {
    justify-content: center;
    background-color: lightgray;
    border-radius: 0px 0px 25px 25px!important;
    font-size: 16px;
    font-family: cursive;
    border-style: groove;
    border-width: 2px;
    border-color: #4B8C8C;
    box-shadow: 5px 5px 5px rgb(75,140,140);
    min-height: 180px;
    border-top:none;
}

.title_card{
	text-anchor: middle;
    font-family: Verdana, sans;
    font-weight: bold;
    margin-bottom: 15px;
    font-size: 17px;
}

.card {
	margin-bottom: 20px !important;
}

.block{
	border:1px solid #4B8C8C !important;
	padding: 10px;
}

.loadingMsg{
	text-align: center;
	display: none;
	margin-right:20px;
}


.center {
  text-align:center;
  margin-top: 10%;
}

.order-card {
	color:#fff;
	
}

.headerText{
    color: white;
    font-weight:bold;
    padding: 8px 15px;
    text-align: center;
    display: inline-block;
}
.importButton{
	background-color: #858d8d;
    border: 1px solid gold;
    color: white;
    padding: 8px 15px;
    text-align: center;
    text-decoration: red;
    display: inline-block;
    margin: 0px 0px;
    cursor: pointer;
    border-radius: 5px;
}

.importButton:hover{
	background-color: #9EA3A3;
	color: gold;
	border: 1px solid #D3D3D3;
}

.card-header{
	padding:6px 20px !important;
	height: 54px !important;
}

 .note {
    font-family: 'Courier New', monospace;
    color: #858d8d;
  }
</style>
</head>
<body>
<c:set var="pg" value="setup" scope="session"  />
<jsp:include page="header.jsp"></jsp:include>
<hr style="margin-top: 0rem;">
<div class="container-fluid" >
<br><br>
	<div class="row" >
		<div class="col-xl-12">
			<div class="card card-primary card-tabs cards-margin block">
				<div class="title_card d-flex justify-content-between align-items-center">
                    <span style="margin-left:10px;">Fiber</span>
                    <button class="importButton" id="downloadSample">Download Excel Sample</button>
                </div>
				<div class="card-body cadr">
					<div class="card-group ">
						<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader">
                					<div class="row">
	                					<div class="col-sm-10">         
	                						<h6 class="headerText">Manhole</h6>       				 	                					       				 	                					
	                					</div>
	                				</div> 
	                			</div>
                				<div class="card-body mycard CardBody">
                				<div class="input-group-prepend">
										<label class="file"><input type="file" style="font-size:13px;margin-left:13px;" id="importManholeExcelFile" accept=".xlsx" class="btn btn-light file"></label>
								</div>
						<div class="input-group-prepend">
								<button class="importButton" id="runManholeScript" style="align:center; margin-left:100px;">Import</button>
								</div>
									<p id="manholeSuccessAlert" style=" margin-top: 0; padding-top:5px;margin-left:80px;color:black;display:none"></p>
                					<div id="manholeLoader" class="loadingMsg">
                						<img  src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" style="width: 40px;height:40px;margin-right:10px;">Loading Data...
                					</div>
                				</div>                				
           					 </div>
           					 <p class="note">This Loader will only load the Manholes <br>and its Junctions,even if the excel sheet<br> is containing Manholes and Handholes . </p>
           					 
      					</div>
      					<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader">
                					<div class="row">
	                					<div class="col-sm-10">         
	                						<h6 class="headerText">Handhole</h6>       				 	                					       				 	                					
	                					</div>
	                				</div> 
	                			</div>
                				<div class="card-body mycard CardBody">
                				<div class="input-group-prepend">
										<label class="file"><input type="file" style="font-size:13px;margin-left:13px;" id="importHandholeExcelFile" accept=".xlsx" class="btn btn-light file"></label>
								</div>
							<div class="input-group-prepend">
								<button class="importButton" id="runHandholeScript" style="align:center; margin-left:100px;">Import</button>
							</div>
								<p id="handholeSuccessAlert" style=" margin-top: 0; padding-top:5px;margin-left:80px;color:black;display:none"></p>
                					<div id="handholeLoader" class="loadingMsg">
                						<img  src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" style="width: 40px;height:40px;margin-right:10px;">Loading Data...
                					</div>
                				</div>
           					 </div>
           					<p class="note">This Loader will only load the Handholes <br>and its Junctions,even if the excel sheet<br> is containing Manholes and Handholes . </p>
      					</div>
      					
      			<div class="mx-auto cardsClass">
            				<div class="card bg-light mb-3 mx-auto ">
                				<div class="card-header Cardheader">
                					<div class="row">
	                					<div class="col-sm-10">         
	                						<h6 class="headerText">Generate City</h6>       				 	                					       				 	                					
	                					</div>
	                				</div> 
	                			</div>
                				<div class="card-body mycard CardBody">
                				<div class="input-group-prepend">
									<label class="file"><input type="file" style="font-size:13px;margin-left:13px;" id="importCityExcelFile" accept=".xlsx" class="btn btn-light file"></label>
								</div>
							<div class="input-group-prepend">
								<button class="importButton" id="generateCity" style="align:center; margin-left:100px;">Generate</button>
							</div>
								<p id="generateCitySuccessAlert" style=" margin-top: 0; padding-top:5px;margin-left:80px;color:black;display:none"></p>
                					<div id="generateCityLoader" class="loadingMsg">
                						<img  src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif" style="width: 40px;height:40px;margin-right:10px;">Loading Data...
                					</div>
                				</div>
           					 </div>
      					</div>
      					
      					
					</div>
				</div>

			</div>
		</div>
	</div>	
</div>
</body>
<script type="text/javascript">
var excelSheetSample=[];
var cityExcelSheetData=[];

$("#runManholeScript").click(function() {
	uploadExcelData("importManholeExcelFile","runManholeScript","manholeLoader",GetRecordsFromExcelSheet);
});

$("#runHandholeScript").click(function() {
	uploadExcelData("importHandholeExcelFile","runHandholeScript","handholeLoader",GetRecordsFromExcelSheet);
});

$('#importManholeExcelFile').click(function() {
	$("#manholeSuccessAlert").hide();
});
$('#importHandholeExcelFile').click(function() {
	$("#handholeSuccessAlert").hide();
});

$('#importCityExcelFile').click(function() {
	$("#generateCitySuccessAlert").hide();
});

$("#downloadSample").click(function() {
	excelSheetSample = [];
	excelSheetSample.push('\r');
	excelSheetSample.push(["NAME","LONGITUDE","LATITUDE"]);
	excelSheetSample.push('\r');
    excelSheetSample.push(["MH60#", 36.9274834088489, -1.18404403308421]); // Add a  mh sample record
    excelSheetSample.push('\r');
    excelSheetSample.push(["HH02#", 36.2990130562941, -0.541427259477781]); // Add a hh sample record
    excelSheetSample.push('\r');
    excelSheetSample.push(["MN15#(Joint2)", 37.0465268218705, -0.452057204216021]); // Add a mh with junction sample record
    excelSheetSample.push('\r');
    excelSheetSample.push(["HH09 (J08#)", 39.1776830573722, 0.755546117024926]); // Add a hh with junction sample record

	DownloadExcelSheet(excelSheetSample,"ManholeHandholeExcelSheetSample");
});

function uploadExcelData(fileId,importButtonID,loaderID,GetRecordsFunctionName) {

	//Reference the FileUpload element.
    var fileUpload = document.getElementById(fileId);
    	
    //Validate whether File is valid Excel file.
    var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.xls|.xlsx)$/;
    if (regex.test(fileUpload.value.toLowerCase())) {
        if (typeof (FileReader) != "undefined") {
            var reader = new FileReader();

            //For Browsers other than IE.
            if (reader.readAsBinaryString) {
                reader.onload = function (e) {
                	GetRecordsFunctionName(e.target.result,loaderID,importButtonID)                	
                };
                reader.readAsBinaryString(fileUpload.files[0]);
            } 
            else {
                //For IE Browser.
                reader.onload = function (e) {
                    var data = "";
                    var bytes = new Uint8Array(e.target.result);
                    for (var i = 0; i < bytes.byteLength; i++) {
                        data += String.fromCharCode(bytes[i]);
                    }
                    GetRecordsFunctionName(data,loaderID,importButtonID)
                };
                reader.readAsArrayBuffer(fileUpload.files[0]);
            }
        } else {
            alert("This browser does not support HTML5.");
        }
    } else {
        alert("Please upload a valid Excel file.");
    }
}


function GetRecordsFromExcelSheet(data,loaderDivId,importButtonID) {

	//Read the Excel File data in binary
    var workbook = XLSX.read(data, {
        type: 'binary'
    });

    //get the name of First Sheet.
    var Sheet = workbook.SheetNames[0];

    //Read all rows from First Sheet into an JSON array.
    var excelRows = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[Sheet]);

    let dictObj = {};
    var dict=[];
	var filteredRows = []; // This array is used to filter the array from manholes and handholes

    if(loaderDivId=="manholeLoader") {
    	$("#manholeSuccessAlert").hide();
	    filteredRows = excelRows.filter(row => !row.NAME.includes("HH"));
		var functionName = "ManholeLoader";
	    
	} else {
		$("#handholeSuccessAlert").hide();
	    filteredRows = excelRows.filter(row => row.NAME.includes("HH"));
		var functionName = "HandholeLoader";
	    
	}
	
    for (var i = 0; i < filteredRows.length; i++) {
        
        var longitude = filteredRows[i].LONGITUDE ? filteredRows[i].LONGITUDE : filteredRows[i].LONGITUDE 
        var latitude = filteredRows[i].LATITUDE ? filteredRows[i].LATITUDE : filteredRows[i].LATITUDE;
        var nameDM = filteredRows[i].NAME;		
		
	
		dictObj.longitude = longitude;
		dictObj.nameDM = nameDM;
		dictObj.latitude = latitude;
				
		dict.push(dictObj);
		dictObj = {}; 		
    }
    filteredRows=[];
    
    var token =  $('input[name="csrfToken"]').attr('value');
	
	
	$.ajax({
		type: "POST",
		url: '${pageContext.request.contextPath}/'+functionName,
		headers: {
			'X-CSRFToken': token 
		},
		data: {
			"dictParameter":dict,
		},
		beforeSend: function() {
			$("#"+loaderDivId).show();
		},
		dataType: "json",
		success: function (data) {
			$("#"+loaderDivId).hide();
			if (data != null) {
			    if(loaderDivId=="manholeLoader") {
			    	document.getElementById('manholeSuccessAlert').innerText = 'Manholes Loaded: '+data.loadedManholeCounter+' \n Junctions Loaded: '+data.loadedJunctionCounter;
					document.getElementById('manholeSuccessAlert').style.display = 'block';
			    }
			    else {
			    	document.getElementById('handholeSuccessAlert').innerText = 'Handholes Loaded: '+data.loadedHandholeCounter+' \n Junctions Loaded: '+data.loadedJunctionCounter;
					document.getElementById('handholeSuccessAlert').style.display = 'block';
				}
				dict=[];
			}
			data = null
		},
		error: function(result) {
			alert("Error");
		}
		});
}


$("#generateCity").click(function() {
	uploadExcelData("importCityExcelFile","generateCity","generateCityLoader",GetCityRecordsFromExcelSheet);
});

function GetCityRecordsFromExcelSheet(data,loaderDivId,importButtonID) {

	//Read the Excel File data in binary
    var workbook = XLSX.read(data, {
        type: 'binary'
    });

    //get the name of First Sheet.
    var Sheet = workbook.SheetNames[0];

    //Read all rows from First Sheet into an JSON array.
    var excelRows = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[Sheet]);

    let dictObj = {};
    var dict=[];

    for (var i = 0; i < excelRows.length; i++) {
        
        var longitude = excelRows[i].LONGITUDE ? excelRows[i].LONGITUDE : excelRows[i].LONGITUDE 
        var latitude = excelRows[i].LATITUDE ? excelRows[i].LATITUDE : excelRows[i].LATITUDE;		
		
		dictObj.longitude = longitude;
		dictObj.latitude = latitude;
				
		dict.push(dictObj);
		dictObj = {}; 		
    }
        
    var token =  $('input[name="csrfToken"]').attr('value');
	
	
	$.ajax({
		type: "POST",
		url: '${pageContext.request.contextPath}/GenerateCity',
		headers: {
			'X-CSRFToken': token 
		},
		data: {
			"dictParameter":dict,
		},
		beforeSend: function() {
			$("#"+loaderDivId).show();
		},
		dataType: "json",
		success: function (data) {
			$("#"+loaderDivId).hide();
			if (data != null) {

				cityExcelSheetData = [];
				cityExcelSheetData.push('\r');
				cityExcelSheetData.push(["Longitude","Latitude","City"]);

				$.each(data, function (i, value) {
					cityExcelSheetData.push('\r');
					cityExcelSheetData.push([value[0], value[1], value[2] ]);		   
				});
				DownloadExcelSheet(cityExcelSheetData,"GenerateCityExcelSheet");
				
			    document.getElementById('generateCitySuccessAlert').innerText = 'Success !';
			    document.getElementById('generateCitySuccessAlert').style.color = 'green';
				document.getElementById('generateCitySuccessAlert').style.display = 'block';
				document.getElementById('generateCitySuccessAlert').style.marginLeft = '120px';
				
				
				dict=[];
			}
			data = null
		},
		error: function(result) {
			alert("Error");
		}
		});
}

function DownloadExcelSheet(dataArray,excelSheetName) {
	  const csvContent = 'data:text/csv;charset=utf-8,' + encodeURIComponent(dataArray);
	  const downloadLink = document.createElement('a');
	  downloadLink.setAttribute('href', csvContent);
	  downloadLink.setAttribute('download', excelSheetName);
	  document.body.appendChild(downloadLink);
	  downloadLink.click();
	  document.body.removeChild(downloadLink);
}

</script>
</html>