<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %><html>
<head>
<meta charset="utf-8">
<title>list</title>
<link href="../static/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <a href="${pageContext.request.contextPath}/test/logout">登出</a><br/>
<c:if test="${not empty msg}">
    <div class="message">${msg}</div>
</c:if>

<shiro:hasPermission name="test:create">
    <a href="${pageContext.request.contextPath}/test/create">用户新增</a><br/>
</shiro:hasPermission>

<table class="table">
    <thead>
        <tr>
            <th>用户名</th>
            <th>角色列表</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.roles }</td>
                <td>
                    <shiro:hasPermission name="test:update">
                        <a href="${pageContext.request.contextPath}/test/${user.id}/update">修改</a>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="test:delete">
                        <a href="${pageContext.request.contextPath}/test/${user.id}/delete" onclick="return confirm('确认要删除该用户吗？', this.href)">删除</a>
                    </shiro:hasPermission>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>