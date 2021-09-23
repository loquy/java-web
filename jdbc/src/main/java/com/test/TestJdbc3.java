package com.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJdbc3 {
    @Test
    public void test() throws ClassNotFoundException, SQLException {
        // 配置信息
        String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "root";
        String password = "root";

        Connection connection = null;
        try {
            // 1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 2.连接数据库
            connection = DriverManager.getConnection(url, username, password);
            // 3.通知数据库开启事务
            connection.setAutoCommit(false);

            // 4.编写SQL
            String sql = "update account set money = money-100 where name = 'A'";
            connection.prepareStatement(sql).executeUpdate(sql);

            // 制造错误
//            int i = 1/0;

            String sql2 = "update account set money = money-100 where name = 'B'";
            connection.prepareStatement(sql).executeUpdate(sql2);

            connection.commit(); // 以上两条sql都执行成功了，就提交事务
            System.out.println("success");
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.close();
        }

    }
}
