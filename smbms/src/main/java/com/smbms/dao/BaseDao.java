package com.smbms.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库公共类
 */
public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    // 静态代码块，类加载的时候就初始化了
    static {
        Properties properties = new Properties();

        // 通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");

        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 编写查询公共方法
     *
     * @return ResultSet
     */
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] params, ResultSet resultSet) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            // 占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    /**
     * 编写增删改公共方法
     *
     * @return int
     */
    public static int execute(Connection connection,PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            // 占位符从1开始
            preparedStatement.setObject(i + 1, params[i]);
        }

        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }

    /**
     * 释放资源
     *
     * @return boolean
     */
    public static boolean closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                // GC回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                // GC回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        if (connection != null) {
            try {
                connection.close();
                // GC回收
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;

    }
}
