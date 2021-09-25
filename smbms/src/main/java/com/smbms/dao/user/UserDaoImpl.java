package com.smbms.dao.user;

import com.mysql.jdbc.StringUtils;
import com.smbms.dao.BaseDao;
import com.smbms.pojo.Role;
import com.smbms.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    // 得到登录的用户
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {

        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
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
                user.setCreatedBy(rs.getInt("createdby"));
                user.setCreationDate(rs.getTimestamp("creationdate"));
                user.setModifyBy(rs.getInt("modifyby"));
                user.setModifyDate(rs.getTimestamp("modifydate"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return user;
    }

    // 修改当前用户密码
    @Override
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int execute = 0;
        if (connection != null) {
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {password, id};
            execute = BaseDao.execute(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return execute;
    }

    // 根据用户名或角色查询用户总数
    @Override
    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u, smbms_role r where u.userRole = r.id");

            ArrayList<Object> list = new ArrayList<>();// 存放参数

            if (!StringUtils.isNullOrEmpty(username)) {
                sql.append("and u.userName like ?");
                list.add("%" + username + "%");
            }

            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }

            // list转换为数组
            Object[] params = list.toArray();

            System.out.println("UserDaoImpl->getUserCount" + sql.toString());

            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);

            if (rs.next()) {
                count = rs.getInt("count"); // 从结果集中获取最终数量
            }
        }

        return count;
    }

    // 查询用户列表
    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)
            throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }
            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //在mysql数据库中，分页使用 limit startIndex，pageSize ; 总数
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while (rs.next()) {
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return userList;
    }
}
