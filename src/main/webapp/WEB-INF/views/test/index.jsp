<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="utf-8">
<title>index</title>
<link href="../static/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<img alt="JeeWS" src="../static/images/ws.png">
<shiro:guest>
    欢迎游客访问，<a href="${pageContext.request.contextPath}/test/login">点击登录</a><br/>
</shiro:guest>
<shiro:user>
    欢迎[<shiro:principal/>]登录<br/>
    <a href="${pageContext.request.contextPath}/test/create">增加用户</a><br/>
    <a href="${pageContext.request.contextPath}/test/list">查看列表</a><br/>
    <a href="${pageContext.request.contextPath}/test/logout">登出</a><br/>
</shiro:user>
<shiro:hasRole name="admin">
    您有角色admin
</shiro:hasRole>
</body>
</html>