<%--
  Created by IntelliJ IDEA.
  User: zhaihs
  Date: 2019/11/8
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>表单提交：列表</title>
</head>
<body>
<form action="/request/list" method="post">
    <label>姓名：</label> <input type="text" name="userList[0].username" />
    <label>年龄：</label> <input type="text" name="userList[0].age" />
    <label>姓名：</label> <input type="text" name="userList[1].username" />
    <label>年龄：</label> <input type="text" name="userList[1].age" />
    <input type="submit" value="提交"/>
</form>
</body>
</html>
