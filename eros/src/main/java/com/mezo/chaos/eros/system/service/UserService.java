package com.mezo.chaos.eros.system.service;

import com.mezo.chaos.eros.system.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author mezo
 */
public interface UserService extends UserDetailsService {
    /**
     * 提交用户名、密码进行验证
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User authLogin(String username,String password);

    UserDetails loadUserByUsername(String username);
}
