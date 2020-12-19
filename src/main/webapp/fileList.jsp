<%--
  Created by IntelliJ IDEA.
  User: ORDER
  Date: 2020/12/19
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>

<html>

<head>

    <title>下载文件显示页面</title>

</head>



<body>

<c:forEach var="me" items="${fileMap}">

    <c:url value="/servlet/downLoadServlet" var="downurl">

        <c:param name="fileName" value="${me.key}"></c:param>

    </c:url>

    ${me.value}<a href="${downurl}">下载</a>

    <br/>

</c:forEach>

</body>

</html>