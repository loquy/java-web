package com.smbms.dao.role;

import com.smbms.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleDao {
    // 获取角色列表
    List<Role> getRoleList(Connection connection) throws Exception;
}
