<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE HTML>

<html>
<head>
        <title>文件上传</title>   
</head>  
<body>
   
<form action="${pageContext.request.contextPath}/servlet/uploadServlet" enctype="multipart/form-data" method="post">
    上传文件1:
    <input type="file" name="file1"/><br/>
    上传文件2:
    <input type="file" name="file2"/><br/>
    <input type="submit" value="提交">
</form>
<form action="${pageContext.request.contextPath}/servlet/listFileServlet" enctype="multipart/form-data" method="post">
    <input type="submit" value="我的资源">
</form>



   
</body>

</html>