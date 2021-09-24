package com.smbms.dao.user;

import com.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    // 得到登录的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException;
}
