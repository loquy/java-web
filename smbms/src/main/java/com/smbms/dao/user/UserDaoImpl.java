package com.smbms.dao.user;

import com.smbms.dao.BaseDao;
import com.smbms.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {

        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
            rs = BaseDao.execute(connection, sql, params, rs, pstm);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("username"));
                user.setUserPassword(rs.getString("userpassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setUserRole(rs.getInt("userrole"));
                user.setUserCode(rs.getString("userCode"));
                user.setCreatedBy( rs.getInt("createdby"));
                user.setCreationDate(rs.getTimestamp("creationdate"));
                user.setModifyBy(rs.getInt("modifyby"));
                user.setModifyDate(rs.getTimestamp("modifydate"));
            }
            BaseDao.closeResources(null, pstm, rs);
        }
        return user;
    }
}
