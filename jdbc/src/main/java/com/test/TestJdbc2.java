package com.test;

import java.sql.*;

public class TestJdbc2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 配置信息
        String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "root";
        String password = "root";

        // 1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 2.连接数据库
        Connection connection = DriverManager.getConnection(url, username, password);
        // 3.编写SQL
        String sql = "insert into users(id, name, password, email, birthday) values (?,?,?,?,?)";
        // 4. 预编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,4);// 给第一个占位符的值赋值为1
        preparedStatement.setString(2,"阿萨德");
        preparedStatement.setString(3,"123123");
        preparedStatement.setString(4,"123123@qq.com");
        preparedStatement.setDate(5,new Date(new java.util.Date().getTime()));

        // 5.执行SQL
        int i = preparedStatement.executeUpdate();

        if (i>0) {
            System.out.println("插入成功");
        }

        // 6.关闭连接释放资源
        preparedStatement.close();
        connection.close();
    }
}
