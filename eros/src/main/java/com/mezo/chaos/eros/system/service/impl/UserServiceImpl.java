package com.mezo.chaos.eros.system.service.impl;

import com.mezo.chaos.eros.system.domain.User;
import com.mezo.chaos.eros.system.mapper.UserMapper;
import com.mezo.chaos.eros.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author mezo
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User authLogin(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        String password1 = userDetails.getPassword();
        if (StringUtils.equals(password1,password)) {
            throw new UsernameNotFoundException(username+",密码错误");
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username+",用户不存在");
        }

        return user;
    }
}
