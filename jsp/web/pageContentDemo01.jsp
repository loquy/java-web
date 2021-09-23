<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--内置对象--%>
<%
    // pageContext取出
    // 从底层到高层（作用域），page->request->session->application
    pageContext.setAttribute("name1", "name1"); // 保存的数据只在一个页面中有效
    request.setAttribute("name2", "name2"); // 保存的数据只在一次请求中有效，请求会携带转发这个数据
    session.setAttribute("name3", "name3"); // 保存的数据只在一次会话中有效，从打开浏览器到关闭浏览器
    application.setAttribute("name4", "name4"); // 保存的数据只在服务器中有效，从打开服务器到关闭服务器
%>

<%
    // 从pageContext中取出
    String name1 = (String)pageContext.findAttribute("name1");
    String name2 = (String)pageContext.findAttribute("name2");
    String name3 = (String)pageContext.findAttribute("name3");
    String name4 = (String)pageContext.findAttribute("name4");

%>

<%--使用EL表达式输出${}--%>

<h1>取出的值为</h1>
<h3>${name1}</h3>
<h3>${name2}</h3>
<h3>${name3}</h3>
<h3>${name4}</h3>

</body>
</html>
