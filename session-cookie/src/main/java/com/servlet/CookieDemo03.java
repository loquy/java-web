package com.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 解决中文乱码
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");

        // 服务端从客户端获取Cookie
        Cookie[] cookies = req.getCookies();

        PrintWriter out = resp.getWriter();

        if (cookies != null) {
            out.write("你上一次访问的名称是：");

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("name")) {
                    out.write(URLDecoder.decode(cookie.getValue(), "UTF-8"));
                }
            }

        } else {
            out.write("这是您第一次访问本站");
        }

        // 服务端给客户端响应一个cookie
        Cookie cookie = new Cookie("name", URLEncoder.encode("奥术大师", "UTF-8"));

        resp.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
