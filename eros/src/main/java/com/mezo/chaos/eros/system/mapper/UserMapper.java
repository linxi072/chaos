package com.mezo.chaos.eros.system.mapper;

import com.mezo.chaos.eros.system.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author mezo
 */
@Mapper
@Component
public interface UserMapper {
    /**
     * 根据账号查询用户信息
     * @param username
     * @return
     */
    User loadUserByUsername(String username);
}
