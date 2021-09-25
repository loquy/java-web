package com.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/*
  处理上传的文件，一般通过流来获取，可以使用request.getInputStream()，原生态文件上传流获取，十分麻烦
  一般使用 Apache的文件上传组件来实现，common-fileupload,它需依赖于 commons-io组件
  ServletFileUpload负责处理上传文件的数据，并将表单中每个输入项封装成一个FileItem对象
  在使用ServletFileUpload对象解析请求时需要DiskFileItemFactory对象
  所以，在进行解析工作前构造好DiskFileItemFactory对象
  通过ServletFileUpload对象构造的方法或setFileItemFactory()方法设置ServletFileUpload对象的fileItemFactory属性
 */
public class fileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 判断上传的文件是普通表单还是带文件的表单
        if (!ServletFileUpload.isMultipartContent(req)) {
            return; // 终止，直接返回
        }

        // 创建上传文件的保存路径，建议在WEB-INF路径下，安全，用户无法直接访问上传文件
        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir(); // 创建这个目录
        }

        // 缓存临时文件
        // 临时路径，假如文件超过了预期的大小，就把他放到一个临时文件中，过几天自动删除，或者提醒用户转存为永久文件
        String tmpPath = this.getServletContext().getRealPath("/WEB-INF/tmp");
        File file = new File(tmpPath);
        if (!file.exists()) {
            file.mkdir(); // 创建临时目录
        }

        try {
            // 1.创建DiskFileItemFactory对象，处理文件上传路径或者大小限制
            DiskFileItemFactory factory = getDiskFileItemFactory(file);

            // 2.获取ServletFileUpload
            ServletFileUpload upload = getServletFileUpload(factory);

            // 3.处理上传的文件,把前端请求解析，封装成一个FileItem对象，需要从ServletFileUpload对象中获取
            String msg = uploadParseRequest(upload, req, uploadPath);


            req.setAttribute("msg", msg);
            req.getRequestDispatcher("info.jsp").forward(req, resp);

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    public static DiskFileItemFactory getDiskFileItemFactory(File file) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 通过这个工厂设置一个缓冲区，当上传的文件大于这个缓冲区的时候，将他放到临时文件中（可选）
        factory.setSizeThreshold(1024 * 1024); // 缓存区大小为1M
        factory.setRepository(file);//临时目录的保存目录，需要一个File
        return factory;
    }


    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory) {
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        // 监听文件上传进度(可选)
        servletFileUpload.setProgressListener((bytes, length, item) -> System.out.println("总大小： " + length + "已上传：" + bytes));
        // 处理乱码问题(可选)
        servletFileUpload.setHeaderEncoding("UTF-8");
        // 设置单个文件的最大值(可选)
        servletFileUpload.setFileSizeMax(1024 * 1024 * 10);
        // 设置总共能上传文件的大小(可选)
        servletFileUpload.setSizeMax(1024 * 1024 * 10);
        return servletFileUpload;
    }

    public static String uploadParseRequest(ServletFileUpload upload, HttpServletRequest req, String uploadPath) throws FileUploadException, IOException {
        String msg = "";
        List<FileItem> fileItems = upload.parseRequest(req);
        for (FileItem fileItem : fileItems) {
            // 判断上传的文件是普通的表单还是带文件的表单
            if (fileItem.isFormField()) {
                // getFieldName指的是获取前端表单控件的name
                String name = fileItem.getFieldName();
                String value = fileItem.getString("UTF-8"); // 处理乱码
                System.out.println(name + ":" + value);
            } else {
                //===========================处理文件========================//
                String uploadFileName = fileItem.getName();
                // 可能存在文件名不合法的情况
                if (uploadFileName.trim().equals("")) {
                    continue;
                }
                // 获得上传文件名
                String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
                // 使用UUID（唯一识别的通用码），保证文件名的唯一
                // UUID.randomUUID，随机生成一个唯一识别的通用码
                String uuidPath = UUID.randomUUID().toString();
                //===========================存放地址========================//
                String realPath = uploadPath + "/" + uuidPath;
                // 给每个文件创建一个对应的文件夹
                File realPathFile = new File(realPath);
                if (!realPathFile.exists()) {
                    realPathFile.mkdir();
                }
                //===========================文件传输========================//
                // 获得文件上传的流
                InputStream inputStream = fileItem.getInputStream();
                // 创建一个文件输出流
                FileOutputStream fos = new FileOutputStream(realPath + "/" + fileName);
                // 创建一个缓冲区
                byte[] buffer = new byte[1024 * 1024];
                // 判断是否读取完毕
                int len;
                // 如果大于0说明还存在数据
                while ((len = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                // 关闭流
                fos.close();
                inputStream.close();
                msg = "文件上传成功！";
                fileItem.delete(); // 上传成功，清除临时文件
            }
        }
        return msg;
    }


}
