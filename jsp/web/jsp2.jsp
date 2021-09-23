<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
jsp指令
<%@ page errorPage="error/500.jsp" %>

--%>
<%@ page isErrorPage="true" %>
<%@ page pageEncoding="UTF-8" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
 <%
     int x = 1/0;
 %>
</body>
</html>
