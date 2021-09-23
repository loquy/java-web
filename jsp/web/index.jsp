<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%-- jsp表达式
 将程序的输出到客户端
 <%=变量或表达式%>
--%>
<%= new java.util.Date()%>
<hr>

<%-- jsp脚本片段 --%>
<%
    int sum = 0;
    for (int i = 1; i <= 100; i++) {
        sum += i;
    }
    out.println("<h1>Sum=" + sum + "</h1>");
%>

<%
    int x = 10;
    out.println(x);
%>
<p>这是一个jsp文档</p>
<%
    int y = 2;
    out.println(y);
%>

<%--在代码嵌入html元素--%>
<%
    for (int i = 0; i < 5; i++) {
%>
<h1>Hello world <%=i%>
</h1>
<%
    }
%>

<%-- <%! %> jsp声明会编译到jsp生成的java类中，其他的生成在jspService方法中--%>
<%!
    static {
        System.out.println("Loading servlet!");
    }

    private int globalVar = 0;

    public void m() {
        System.out.println("进入了方法m");
    }
%>

<%--

jsp语法 主要这三个标签
<%%> jsp脚本片段
<%= %> 变量或表达式
<%! %> 声明会编译到jsp生成的java类中

--%>

<%--我是jsp注释，不会在客户端显示--%>
<!-- 我是html注释，会在客户端显示 -->
<hr>
</body>
</html>
