package com.mezo.chaos.eros.system.service;

import com.mezo.chaos.eros.system.domain.User;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author mezo
 */
public interface LoginService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
