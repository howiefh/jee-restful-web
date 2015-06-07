<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="utf-8">
<title>edit</title>
<link href="../static/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <a href="${pageContext.request.contextPath}/test/logout">登出</a><br/>
    <form:form method="post" commandName="user">
        <form:hidden path="id"/>
        <%--
        <form:hidden path="salt"/>
        <form:hidden path="locked"/>
         --%>

        <c:if test="${act ne 'create'}">
            <form:hidden path="password"/>
        </c:if>

        <div class="form-group">
            <form:label path="username">用户名：</form:label>
            <form:input path="username"/>
        </div>

        <c:if test="${act eq 'create'}">
            <div class="form-group">
                <form:label path="password">密码：</form:label>
                <form:password path="password"/>
            </div>
        </c:if>

        <div class="form-group">
            <form:label path="roles">角色列表：</form:label>
            <form:select path="roles" items="${roleList}" multiple="true"/>
            (按住shift键多选)
        </div>

        <form:button>提交</form:button>

    </form:form>
</body>
</html>