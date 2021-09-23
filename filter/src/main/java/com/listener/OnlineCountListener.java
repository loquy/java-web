package com.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// 统计网站在线人数：统计session
public class OnlineCountListener implements HttpSessionListener {
    @Override
    // 创建session监听，一旦创建session就会触发一次这个事件！
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(httpSessionEvent.getSession().getId());
        ServletContext servletContext = httpSessionEvent.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");

        if (onlineCount == null) {
            onlineCount = new Integer(1);
        } else {
            int count = onlineCount.intValue();
            onlineCount = new Integer(count+1);
        }


        servletContext.setAttribute("OnlineCount", onlineCount);
    }

    @Override
    // 销毁session监听，一旦销毁session就会触发一次这个事件！
    // session销毁：1.手动销毁 getSession.invalidate();2.自动销毁web.xml配置 session-timeout
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServletContext servletContext = httpSessionEvent.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");
        System.out.println(httpSessionEvent.getSession().getId());
        if (onlineCount == null) {
            onlineCount = new Integer(0);
        } else {
            int count = onlineCount.intValue();
            onlineCount = new Integer(count-1);
        }

        servletContext.setAttribute("OnlineCount", onlineCount);
    }
}
