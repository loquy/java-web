package com.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

//        this.getInitParameter(); // 获得初始化参数
//        this.getServletConfig(); // 获得 Servlet 配置
//        this.getServletContext(); // 获得 Servlet 上下文

        ServletContext context = this.getServletContext();

        String username = "xxx";
        context.setAttribute("username", username);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().print(username);

        System.out.println("Hello");
    }
}
