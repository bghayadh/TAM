<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="utf-8">
<title>Login</title>

<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/resources/js/moment.min.js"></script>
<script type="text/javascript"
    src="${pageContext.request.contextPath}/resources/js/tempusdominus-bootstrap-4.min.js"></script>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/tempusdominus-bootstrap-4.min.css" />
<script type="text/javascript"
    src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<link
    href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
    rel="stylesheet">
<script
    src="${pageContext.request.contextPath}/resources/js/jquery-migrate-3.0.0.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/js/jquery2-ui.js"></script>
<link rel="stylesheet"
    href="${pageContext.request.contextPath}/resources/css/jquery-ui.css"
    rel="stylesheet" />

<style type="text/css">

.navbar-brand {
    font-family:fantasy;
/*    color:red !important; */
    color:#ffdf00 !important;
    font-style:italic;
    width:4rem;
/*    width:90px; */
    font-size:60px;
    margin-left: 30px;
}

.fo {
     height:400px;
    width:300px;
    margin-top:20px;
    background-color:#000;
    opacity:0.7;
    padding:30px;
    box-shadow:0px 0px 10px 0px #000;
    border-radius:10px;
    color:white;
}
.col{
color: #FF0000}
body{

    background:url("${pageContext.request.contextPath}/resources/images/back4.jpg") no-repeat center center fixed;
  position:fixed;
  top: 0; 
  left: 0; 
  right: 0; 
  bottom: 0; 
  margin: auto; 
  background-size: cover;
  height: auto;
  width: 100%;
  overflow: hidden;
  background-repeat: no-repeat;
   background-size: cover !important;
   min-width: 100%;
  min-height: 100%;
}
.form-group input {
    border-radius: 15px;
}
</style>
</head>
<body>	
	<a class="navbar-brand">TAM</a>
    <section class="container-fluid  bg">
        <section class="row justify-content-center">
            <section ="col-12 col-sm-6 col-md-3">
                
                <form class="fo" action="loginAuth" method="post">
                <div class="form-group " style="margin-top:20px;">
                   <label for="exampleInputEmail1" class="col">${Message} </label>
                   </div>
                  <div class="form-group " style="margin-top:50px;">
                    <label for="exampleInputEmail1">Username</label>
                    <input type="text" class="form-control"  name="usernameAuth"/>
                    
                  </div>
                  <div class="form-group" >
                    <label for="exampleInputPassword1" >Password</label>
                    <input type="password" class="form-control" name="PasswordAuth"/>
                  </div>
                  <div class="form-group " >
                    <input type="checkbox" class="form-check-input" id="exampleCheck1"/>
                    <label class="form-check-label" for="exampleCheck1" >Check me out</label>
                  </div>
                  <%-- Adding the redirectUrl parameter from the model --%>
                  <input type="hidden" name="redirectUrl" value="${redirectUrl}" />
                  <button type="submit" class="btn btn-primary btn-block" >Login</button>
                </form>
            </section>
        </section>
    </section>
    
</body>
</html>
