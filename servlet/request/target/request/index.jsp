<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h1>登录</h1>

<div>
    <form action="${pageContext.request.contextPath}/login" method="post">
        用户名：<input type="username" name="username"> <br>
        密码：<input type="password" name="password"> <br>
        爱好：
        <input type="checkbox" name="hobby" value="女孩"> 女孩
        <input type="checkbox" name="hobby" value="代码"> 代码
        <input type="checkbox" name="hobby" value="唱歌"> 唱歌
        <input type="checkbox" name="hobby" value="电影"> 电影
        <br>
        <input type="submit">
    </form>
</div>
</body>
</html>
