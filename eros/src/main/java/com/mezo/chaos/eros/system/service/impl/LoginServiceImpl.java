package com.mezo.chaos.eros.system.service.impl;

import com.mezo.chaos.eros.system.domain.User;
import com.mezo.chaos.eros.system.mapper.UserMapper;
import com.mezo.chaos.eros.system.service.LoginService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author mezo
 */
@Service
public class LoginServiceImpl implements LoginService {

    private UserMapper userMapper;
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.loadUserByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return null;
    }
}
