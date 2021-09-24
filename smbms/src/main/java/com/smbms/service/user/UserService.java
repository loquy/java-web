package com.smbms.service.user;

import com.smbms.pojo.User;

public interface UserService {
    public User login(String userCode, String password);
}
