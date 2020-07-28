package com.mezo.chaos.eros.system.service;

import com.mezo.chaos.eros.system.domain.User;

/**
 * @author mezo
 */
public interface UserService {
    /**
     * 提交用户名、密码进行验证
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User authLogin(String username,String password);
}
