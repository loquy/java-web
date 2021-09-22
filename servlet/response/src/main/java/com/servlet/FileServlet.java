package com.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1.要获取下载文件的路径
//        String realPath = this.getServletContext().getRealPath("testfile");
        String realPath = "D:\\study\\java web\\servlet\\response\\src\\main\\resources\\testfile";
        System.out.println("下载文件的路径：" + realPath);
        // 2.下载的文件名是啥？
        String filename = realPath.substring(realPath.lastIndexOf("\\") + 1);
        // 3.设置让浏览器支持(Content-Disposition)我问下载的东西,中文文件名 URLEncoder 编码
        resp.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        // 4.获取下载文件的输入流
        FileInputStream in = new FileInputStream(realPath);
        // 5.创建缓冲区
        int len;
        byte[] buffer = new byte[1024];
        // 6.获取OutputStream对象
        ServletOutputStream out = resp.getOutputStream();
        // 7.将FileOutputStream对象写入到buff缓冲区吗，使用OutputStream将缓冲区中的数据输入到浏览器
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0 , len);
        }
        in.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
