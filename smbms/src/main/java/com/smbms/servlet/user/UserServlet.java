package com.smbms.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.smbms.pojo.User;
import com.smbms.service.user.UserServiceImpl;
import com.smbms.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

// 实现servlet复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")) {
            this.updatePwd(req, resp);
        } else if(method.equals("pwdmodify")) {
            this.pwdModify(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 修改密码
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //从session里面拿ID;
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        String newPassword = req.getParameter("newpassword");

        boolean flag;
        if (user != null && !StringUtils.isNullOrEmpty(newPassword)) {
            UserServiceImpl userService = new UserServiceImpl();
            flag = userService.updatePwd(user.getId(), newPassword);
            if (flag) {
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
                // 密码修改成，移除当前session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute("message", "密码修改失败 ");
            }
        } else {
            req.setAttribute("message", "新密码有问题");
        }

        req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
    }

    /**
     * 验证旧密码，session中有用户的密码
     *
     * @param req
     * @param resp
     */
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp) {
        //从session里面拿ID;
        User user = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        // 万能的map:结果集
        Map<String, String> resultMap = new HashMap<>();
        if (user == null) { // session失效或过期
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {
            resultMap.put("result", "error");
        } else {
            String userPassword = user.getUserPassword(); //session中用户的密码
            if (oldpassword.equals(userPassword)) {
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");
            }
        }

        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            // JSONArray阿里巴巴工具类
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
