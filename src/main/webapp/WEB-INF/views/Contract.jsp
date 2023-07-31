<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
</head>
<body>
<%--     <c:set var = "page" value = "contracts"/> --%>

<%-- 	<%@ include file="header.html" %> --%>

  <c:set var="pg" value="contracts" scope="session"  />
  <jsp:include page="${request.contextPath}/headerController"></jsp:include>
           
 </body>
 </html>
