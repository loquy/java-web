package com.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {

    @Override
    // 初始化：web服务器启动，就初始化了，随时等待过滤对象出现
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("CharacterEncodingFilter执行前...");
        chain.doFilter(request, response); // 让我们的请求继续走，如果不写程序到这里就被拦截停止
        System.out.println("CharacterEncodingFilter执行后...");

    }

    @Override
    // web服务器关闭时销毁
    public void destroy() {
        System.out.println("CharacterEncodingFilter销毁");
    }
}
