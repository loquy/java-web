<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:useBean id="people" class="com.pojo.people" scope="page" />
<jsp:setProperty name="people" property="address" value="西西" />
<jsp:setProperty name="people" property="id" value="1" />
<jsp:setProperty name="people" property="age" value="3" />
<jsp:setProperty name="people" property="name" value="自行车" />

姓名：<jsp:getProperty name="people" property="name"/>
ID：<jsp:getProperty name="people" property="id"/>
年龄：<jsp:getProperty name="people" property="age"/>
地址：<jsp:getProperty name="people" property="address"/>
</body>
</html>
