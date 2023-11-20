<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <head>
        <meta charset="utf-8">
        <title></title>
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
        <script src="${pageContext.request.contextPath}/resources/js/warehouseF_BoqPopup.js"></script>
        
         


        <style>
            /*Doaa's popup Email Div'*/
            #collapseOne {
                height: 560px;
                width: 800px;
                float: right;
            }

            #areaTab {
                float: left;
                /* important */
                /* width:595px; */
                height: auto;
            }

            #popUpDiv {
                position: fixed;
                top: 30%;
                left: 50%;
                background-color: #eeeeee;
                border: 5px solid #08526d;
                width: 400px;
                height: 450px;
                margin-left: -150px;
                margin-top: -95px;
                -moz-border-radius: 16px;
                -webkit-border-radius: 16px;
                border-radius: 16px;
                box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px #000000;
                z-index: 9003;
                /*above nine thousand*/
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

            .hide-row {
                display: none;
            }

            /* Set the size of the div element that contains the map */
            .panel-body {
                border: 2px solid #808080;
                border-radius: 30px 15px 15px 5px;
            }

            #map {
                height: 600px;
                width: 100%;
            }

            select {
                width: 260px;
            }

            .table-container {
                width: 100%;
                overflow-x: auto;
            }
        </style>
        <style>
            #mapText {
                border: hidden;
                width: 110px;
                height: 25px;
                border: hidden;
                background-color: #87CEEB;
                margin-left: 20px;
                text-align: center;
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

            .table thead th {
                vertical-align: middle;
            }

            .table thead th div {
                width: 200px !important;
            }

            #warehouseInfo_tbl,
            #warehouseInfo_tbl td {
                padding: 0;
                margin: 0;
                vertical-align: top;
            }

            .fixed-headerr {
                opacity: 1;
                filter: alpha(opacity=100);
                background: #ebf2ef;
                position: sticky;
                top: 0;
                z-index: 15;
            }

            .left_col {
                height: 60px
            }

            .siteimg {
                display: inline;
            }

            /* Image popup by ahmad */

            #imgpopUpDiv {
                position: fixed;
                top: 30%;
                left: 50%;
                background-color: #eeeeee;
                border: 5px solid #08526d;
                width: 400px;
                height: 450px;
                margin-left: -150px;
                margin-top: -95px;
                -moz-border-radius: 16px;
                -webkit-border-radius: 16px;
                border-radius: 16px;
                box-shadow: 12px 0 15px -4px #000000, -12px 0 15px -4px #000000;
                z-index: 9003;
                /*above nine thousand*/
            }

            #imagedisplay {
                width: 400px;
                height: 350px;
                margin: 20px;
            }

            .nav-link.active {
                background-color: #FFD966 !important;
                color: #00757c !important;
            }
   
        </style>

    </head>

    <body>
        <script type="text/javascript">
        </script>

        <%-- <c:set var="page" value="inventory" /> --%>

        <%-- <%@ include file="header.html" %> --%>
            <c:set var="pg" value="network" scope="session" />
            <jsp:include page="header.jsp"></jsp:include>

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
                                <span class="input-group-text" style="width: 170px;">Cell
                                </span> <input type="text" id="cellpk" readonly value="${cell_pk}"
                                    class="form-control text-input" />
                            </div>
                        </div>
                    </div>
                    
                       <div class="col-md-3 nextprvItems">
                        <div class="form-group">
                            <div class="input-group-prepend">
                    <span class="input-group-text">Cell Id</span>
                    <input type="text" id="cellId" value="${cell_id}" class="form-control text-input" readonly />
                </div>
                        </div>
                    </div>

                    <div class="pad col-md-3 hide-row"></div>
                    <div class="col-md-3 nextprvItems">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">Other Cell</span>
                                <input type="text" id="selectnav" value="${selectnav}" style="width: 430px"
                                    class="form-control text-input" />
                            </div>
                        </div>
                    </div>



                    <div class="col-md-3 text-right">
                        <i>&nbsp</i><span class="dot"></span> <i>&nbsp</i> <label for="formStatus" id="formStatus"
                            style="float: right;">Saved</label>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend" id="datetimepicker1" data-target-input="nearest">
                                <span class="input-group-text" style="width: 170px;"">Created
							Date</span> <input type=" text" id="wcreationDate" readonly value="${creationDate}"
                                    class="form-control datetimepicker-input" data-toggle="datetimepicker"
                                    data-target="#datetimepicker1" />
                                <div class="input-group-append" data-target="#datetimepicker1"
                                    data-toggle="datetimepicker"></div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <div class="input-group-prepend" id="datetimepicker2" data-target-input="nearest">
                                <span class="input-group-text" style="width: 210px;">Last
                                    Modify Date</span> <input type="text" id="wlastModifieddate" readonly
                                    value="${LastModified}" class="form-control datetimepicker-input"
                                    data-toggle="datetimepicker" data-target="#datetimepicker2" />
                                <div class="input-group-append" data-target="#datetimepicker2"
                                    data-toggle="datetimepicker"></div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="hide-row col-md-3 pad "></div>
                    <div class=" col-md-3 nextprvItems">
                        <label id="label-1"
                            style="width: 80px; text-align: center; margin-top: 5px ! important;"></label>
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <li id="btnFrst" title="Go To First" class="page-item " style="margin-right: 2px;"><a
                                        style="margin-left: 3px;width: 51px;height:40px" id="btnFirst" href="#"
                                        class="btn btn-success previous">&laquo; </a></li>
                                <li id="btnPrv" title="Go To Previous" class="page-item " style="margin-right: 2px;"><a
                                        style="width: 51px;height:40px" id="btnPrva" href="#"
                                        class="btn btn-success previous">&lsaquo; </a></li>
                                <li id="btnNext" title="Go To Next" class="page-item"
                                    style="padding-right: 0px ! important;"><a
                                        style="width: 51px;margin-right: 2px;height:40px" id="btnNexta"
                                        style="width:100px;" href="#" class="btn btn-success next"> &rsaquo; </a></li>
                                <li id="btnLst" title="Go To Last" class="page-item " style="margin-right: 2px;"><a
                                        style="width: 51px;height:40px" id="btnLast" href="#"
                                        class="btn btn-success next">&raquo;</a></li>
                            </ul>
                        </nav>
                    </div>

                    <div class="col-md-3" style="text-align: right;">
                        <div class="btn-group pull-right">
                            <div class="glyph">
                                <button type="button" id="Fview" class="btn btn-danger" data-toggle="tooltip"
                                    data-placement="top" title="Form View" style="background: #da6815;">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <button type="button" id="Lview" class="btn btn-light" data-toggle="tooltip"
                                    onclick='window.location.href = "${pageContext.request.contextPath}/CellListView"'
                                    data-placement="top" title="List View">
                                    <i class="fa fa-bars"></i>
                                </button>
                                <button type="button" class="btn btn-light" data-toggle="tooltip" data-placement="top"
                                    title="Search">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

          
 <div class="row">
  <div class="col-12 col-sm-12 col-lg-12">
     <ul class="nav nav-tabs" id="custom-tabs-one-tab" role="tablist" style="background-color: #00757c; margin-top: 0px; display: flex; flex-wrap: nowrap;">
       <li class="tab" class="nav-item"><a class="nav-link active" id="custom-tabs-one-home-tab"
                                    data-toggle="tab" href="#custom-tabs-one-home" role="tab"
                                    aria-controls="custom-tabs-one-home" aria-selected="true"
                                    style="color: gold;">INFORMATION</a></li>
                                    
                                           <li class="nav-item"><a class="nav-link" id="custom-tabs-one-cell-tab" data-toggle="tab"
                                    href="#custom-tabs-one-pass" role="tab" aria-controls="custom-tabs-one-pass"
                                    aria-selected="false" style="color: gold;">PASSIVE</a></li>
                                      
                                    
                           

     


                          
                          
                          
                            <li class="nav-item ml-auto">
                                <div class="dropdown" Style="display: inline-block;">
                                    <button disabled class="btn btn-secondary dropdown-toggle" type="button"
                                        id="notifactionDropdown" data-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">Actions</button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item" type="button" id="Approvewh">Approve</a>
                                        <a class="dropdown-item" type="button" id="Activatewh">Activate</a>
                                        <a class="dropdown-item" type="button" id="Deactivatewh">Deactivate</a>
                                        <a class="dropdown-item" type="button" id="Cancelwh">Cancel</a>
                                    </div>
                                    <button disabled type="button" id="sendEmail" class="btn btn-primary BtnActive">
                                        <i class="fa fa-envelope"></i> Send Email
                                    </button>
                                </div>

                                <button disabled type="button" id="deleteButton" class="btn btn-primary BtnActive">
                                    <i class="fa fa-trash"></i> Delete
                                </button>

                                <button  type="button" id="saveButton" class="btn btn-primary BtnActive">
                                    <i class="fa fa-save"></i> Save
                                </button>
                            </li>
                        </ul>
                    </div>
                  
                </div>
            

            <div class="container-fluid">
                <div class="tab-content" id="custom-tabs-one-tabContent">
                    <div class="tab-pane fade show active" id="custom-tabs-one-home" role="tabpanel"
                        aria-labelledby="custom-tabs-one-home-tab">
                        <p></p>
                        <div style="height: 20px;"></div>
                <table width="90%" border="0" cellpadding="0" cellspacing="0" id="warehouseInfo_tbl">
    <tr>
        <td width="33%" valign="top" class="left_col">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">cell Id</span>
                    <input type="text" id="cellId" value="${cell_id}" class="form-control text-input" readonly />
                </div>
            </div>
        </td>
       
        <td width="33%" valign="top" class="left_col">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">Cell Name</span>
                    <input type="text" id="cellName" value="${cell_name}" class="form-control text-input" readonly />
                </div>
            </div>
        </td>
        <td width="33%" valign="top" class="left_col">
            <div class="form-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">Unique Node Id</span>
                    <input type="text" id="nodeName" value="${uniqueId}" class="form-control text-input" readonly />
                </div>
            </div>
        </td>
    </tr>
                            <tr>
                                <td width="20%" valign="top" class="left_col">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Node Name</span> <input type="text"
                                                value="${node_name}" class="form-control text-input"
                                                readonly />
                                        </div>
                                    </div>
                                </td>
                               
                                <td width="20%" valign="top" class="left_col">

                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Site Id</span> <input type="text"
                                                id="warepname" value="${site_id}" class="form-control text-input"
                                                readonly />
                                        </div>
                                    </div>
                                </td>
                                 <td width="60%" valign="top" class="left_col">
                                    <div class="form-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Site Name</span> <input type="text"
                                                id="warepname" value="${site_name}" class="form-control text-input"
                                                readonly />
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            
                        </table>


                    </div>
                    
                    
                      <div class="tab-pane fade" id="custom-tabs-one-pass" role="tabpanel" aria-labelledby="custom-tabs-one-pass-tab">
    <div style="height: 10px;"></div>
    <table width="95%" border="0" cellpadding="3px" cellspacing="0" id="warehouseInfo_tbl">
     <tr>
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Date On Air</span>
                <input type="text" id="datOnAir" value="${datOnAir}" class="form-control text-input"  />
            </div>
        </div>
    </td>
     <td width="40%" valign="top" class="left_col" style="margin-right: 10px; display:none">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">PassivePk</span>
                <input type="text" id="passivePK" value="${passivePK}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Site Sub Type</span>
                <input type="text" id="siteSubType" value="${siteSubType}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>


     <tr>
 <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Antena Shared With 2G</span>
                <input type="text" id="antena2G" value="${antena2G}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text"> Antenna  Manufacturer </span>
                <input type="text" id="manufacture" value="${manufacture}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>


     <tr>
     <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text"> Antenna Model</span>
                <input type="text" id="model" value="${model}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
   <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Antenna Gain </span>
                <input type="text" id="gain" value="${gain}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>

<tr>
   <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Beam Width</span>
                <input type="text" id="beam" value="${beam}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Azimuth </span>
                <input type="text" id="azimuth" value="${azimuth}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>


<tr>
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Antenna Height AGL </span>
                <input type="text" id="AGL" value="${AGL}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
     <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Electrical Tilt </span>
                <input type="text" id="electric" value="${electric}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>


<tr>
   <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Mechanical Tilt </span>
                <input type="text" id="mechanical" value="${mechanical}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">RET </span>
                <input type="text" id="ret" value="${ret}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>


<tr>
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Feeder Size </span>
                <input type="text" id="feederSize" value="${feederSize}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Approximate Feeder Length </span>
                <input type="text" id="approx" value="${approx}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>

<tr>
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">TMA MHA </span>
                <input type="text" id="TMA" value="${TMA}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Shared Site </span>
                <input type="text" id="sharedSite" value="${sharedSite}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>

<tr>
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Remarks </span>
                <input type="text" id="remarks" value="${remarks}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Sector Status </span>
                <input type="text" id="sectorStatus" value="${sectorStatus}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>

<tr>
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Sector Locked Date </span>
                <input type="text" id="sectorDate" value="${sectorDate}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Locked Reason </span>
                <input type="text" id="lockedReason" value="${lockedReason}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>


<tr>
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Diplexer </span>
                <input type="text" id="Diplexer" value="${Diplexer}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Diplexer Purpose </span>
                <input type="text" id="dipPurpose" value="${dipPurpose}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>
        <tr>
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Status </span>
                <input type="text" id="Status" value="${status}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Master Sector Id </span>
                <input type="text" id="masterSectorID" value="${masterSectorID}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>


      <tr>
    <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">AT </span>
                <input type="text" id="AT" value="${AT}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Flag </span>
                <input type="text" id="flag" value="${flag}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>

      <tr>
   <td width="40%" valign="top" class="left_col" style="margin-right: 10px;">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Circle Id </span>
                <input type="text" id="circleId" value="${circleId}" class="form-control text-input"  />
            </div>
        </div>
    </td>
    <td width="5%" valign="top" class="left_col"></td>
     
    <td width="40%" valign="top" class="left_col">
        <div class="form-group">
            <div class="input-group-prepend">
                <span class="input-group-text">Nep Synch </span>
                <input type="text" id="nepSynch" value="${nepSynch}" class="form-control text-input"  />
            </div>
        </div>
    </td>
</tr>

   
    </table>
</div>

                   
                   
                    
                   

                   


                   

                   

                  

 					

                   

                   
                 
                </div>

                   </div>
            </div>

            <div id="imgpopUpDiv" style="display: none;">
                <div id="imagedisplay"></div>
                <div>
                    <button type="button" id="imgcancelButton" style="margin-left: 20px;"
                        class="btn btn-primary BtnActive">
                        <i class="fa fa-times"></i> Cancel
                    </button>
                </div>
            </div>


            <div id="popUpDiv" style="display: none;">
                <div class="sendEmail" style="margin-top: 50px;">
                    <div class="col-md-12">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> Sender</span> <input type="text" id="email"
                                    class="ui-widget ui-widget-content ui-corner-all form-control" />
                                <input type="text" id="password" value="${password}"
                                    class="ui-widget ui-widget-content ui-corner-all form-control" hidden />

                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" style="width: 80px;"> Email
                                    To</span> <input type="text" id="emailTo" class="form-control text-input" />

                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" style="width: 80px;">
                                    ccEmail </span> <input type="text" id="ccmail" class="form-control text-input" />

                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> Title</span> <input type="text" id="subject"
                                    class="form-control text-input" />

                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="form-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text"> Message</span>
                                <textarea id="message" class="form-control text-input" cols="200" rows="4"></textarea>

                            </div>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <p></p>
                        <div class="form-group" style="margin-left: 100px;">

                            <button type="button" id="send" class="btn btn-primary BtnActive">
                                <i class="fa fa-paper-plane"></i> Send
                            </button>

                            <button type="button" id="cancelButton" class="btn btn-primary BtnActive">
                                <i class="fa fa-times"></i> Cancel
                            </button>
                        </div>
                    </div>
                </div>
            </div>
    </body>




    <script>



        $("input").change(function() {
        	$("#formStatus").text("Not Saved");
        	$('.dot').css({"background-color" : "orange"});
        	});
    	
       
        $("#saveButton").click(function () {
            
        	
             if ($("#discoveryDate").val()=="") {
                  alert('Discovery Date is not valid');
                  return false;
                }

             if ($("#shownDate").val()=="") {
                 alert('Last shown Date Date is not valid');
                 return false;
               }
             
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/CellFormViewSave",
                dataType: "json",
                data: {
                               "cellpk":$("#cellpk").val(),
                               "passivePK":$("#passivePK").val(),
                                "cellId":$("#cellId").val(),
                                 "cellName":$("#cellName").val(),
                                 "datOnAir": $("#datOnAir").val(),
                         	    "siteSubType": $("#siteSubType").val(),
                         	    "antena2G": $("#antena2G").val(),
                         	    "manufacture": $("#manufacture").val(),
                         	    "model": $("#model").val(),
                         	    "gain": $("#gain").val(),
                         	    "beam": $("#beam").val(),
                         	    "azimuth": $("#azimuth").val(),
                         	    "AGL": $("#AGL").val(),
                         	    "electric": $("#electric").val(),
                         	    "mechanical": $("#mechanical").val(),
                         	    "ret": $("#ret").val(),
                         	    "feederSize": $("#feederSize").val(),
                         	    "approx": $("#approx").val(),
                         	    "TMA": $("#TMA").val(),
                         	    "remarks": $("#remarks").val(),
                         	    "sectorStatus": $("#sectorStatus").val(),
                         	    "sectorDate": $("#sectorDate").val(),
                         	    "lockedReason": $("#lockedReason").val(),
                         	    "AT": $("#AT").val(),
                         	    "flag": $("#flag").val(),
                         	    "status": $("#Status").val(),
                         	    "nepSynch": $("#nepSynch").val(),
                         	    "circleId": $("#circleId").val(),
                         	    "sharedSite": $("#sharedSite").val(),
                         	    "Diplexer": $("#Diplexer").val(),
                         	    "dipPurpose": $("#dipPurpose").val(),
                         	    "masterSectorID": $("#masterSectorID").val(),
                         	
                             
                                	
                                	                        	   
                        	
                },
                success: function (data) {
                    if (data.cellPK) {
                    	var param = "${pageContext.request.contextPath}/CellFormView?CellPk=" + data.cellPK + "&NavAction=2";
                         location.replace(param);
                    } else {
                        console.log("No 'cellPK' found in the response.");
                    }
                },
                error: function (error) {
                    console.log("The error is " +error);
                }
            });
        });

        $("#selectnav").autocomplete({
      	  source: debounce(function(request, response, event, ui) {
      	    $.ajax({
      	      type: "GET",
      	      contentType: "application/json; charset=utf-8",
      	      url: '${pageContext.request.contextPath}/GetAllCell',
      	      data: {
      	        "Cell": $("#selectnav").val(),
      	         },
      	      dataType: "json",
      	      success: function(data) {
      	        if (data != null) {
      	          response(data.ListCell);
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
      	    this.value = ui.item ? ui.item[0] + ":" + ui.item[2] : '';

      	    var param = '${pageContext.request.contextPath}/CellFormView?CellPk=' + ui.item[1]+"&NavAction=2";
      	    window.location.href = param;
      	    return false;
      	  }
      	}).autocomplete("instance")._renderItem = function(ul, item) {
      	  return $("<li class='each'>")
      	    .append("<div class='acItem'><span class='desc'>" +
      	      item[0] + ', ' + item[2] + "</span><br><span class='name' style='font-weight:bold'>" +
      	      item[1] + "</span>")
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




    </script>

    <!--Load the API from the specified URL -- remember to replace YOUR_API_KEY-->

</html>