package com.smbms.servlet.user;

import com.smbms.pojo.User;
import com.smbms.service.user.UserServiceImpl;
import com.smbms.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    // servlet 控制层调用业务层代码

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LoginServlet--start....");

        // 获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        // 和数据库中的密码j进行对比，调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword); // 这里已经把登录的人查出来了

        if (user != null && user.getUserPassword().equals(userPassword)) {
            // 将用户的信息放到session中
            req.getSession().setAttribute(Constants.USER_SESSION, user);

            System.out.println(user.toString());
            // 登录成功跳转到主页
            resp.sendRedirect("jsp/frame.jsp");
        } else {
            // 转发回登录页
            req.setAttribute("error", "用户名或密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
