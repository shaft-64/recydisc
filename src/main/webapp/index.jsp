<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE HTML>

<html>
<head>
        <title>文件上传</title>   
</head>  
<body>


<%--<form action="${pageContext.request.contextPath}/servlet/uploadServlet" enctype="multipart/form-data" method="post">--%>
<%--    上传文件1:--%>
<%--    <input type="file" name="file1"/><br/>--%>
<%--    上传文件2:--%>
<%--    <input type="file" name="file2"/><br/>--%>
<%--    <input type="submit" value="提交">--%>
<%--</form>--%>

<%--<form action="${pageContext.request.contextPath}/servlet/listFileServlet" enctype="multipart/form-data" method="post">--%>
<%--    <input type="submit" value="我的资源">--%>
<%--</form>--%>
<form>
    path<br/>
    <input type="text" id="username"><br/>
    上传文件:
    <input type="file" id="file"/><br/>

    <input type="button" id="btn" value="上传"><br/>
</form>

<form action="${pageContext.request.contextPath}/servlet/listFileServlet" enctype="multipart/form-data" method="post">

    <input type="submit" value="所有文件">
</form>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
<script>
    $('#btn').click(()=> {
        // 1.先生成一个formData对象
        let formData = new FormData();
        // 2.朝对象中添加普通的键值
        let username = $("#username").val();
        // 3.朝对象中添加文件数据
        // 1.先通过jquery查找到该标签
        // 2.将jquery对象转换成原生的js对象
        // 3.利用原生js对象的方法 直接获取文件内容
        formData.append('file',$('#file')[0].files[0]);
        $.ajax({
            //规定发送请求的 URL。默认是当前页面。
            url : '${pageContext.request.contextPath}/servlet/uploadServlet?username=' + username,
            //规定请求的类型（GET 或 POST）。
            type : 'post',

            data : formData,  // 直接丢对象
            // ajax传文件 一定要指定两个关键性的参数
            contentType : false,  // 不用任何编码 因为formData对象自带编码 django能够识别该对象
            processData : false,  // 告诉浏览器不要处理我的数据 直接发就行
        }).done(result=>{
            console.log(result);
            console.log("wsngg");
        })
    })
</script>
   
</body>

</html>