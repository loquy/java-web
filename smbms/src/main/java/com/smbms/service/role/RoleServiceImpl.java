package com.smbms.service.role;

import com.smbms.dao.BaseDao;
import com.smbms.dao.role.RoleDao;
import com.smbms.dao.role.RoleDaoImpl;
import com.smbms.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class RoleServiceImpl implements RoleService{

    // 引入dao
    private RoleDao roleDao;
    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList() {
        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roleList;
    }

    @Test
    public void  test() {
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        System.out.println(Arrays.toString(roleList.toArray()));
        for(Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }
}
