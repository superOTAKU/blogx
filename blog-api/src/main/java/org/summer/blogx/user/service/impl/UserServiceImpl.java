package org.summer.blogx.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summer.blogx.infrastructure.entity.User;
import org.summer.blogx.infrastructure.mapper.UserMapper;
import org.summer.blogx.infrastructure.redis.RedisJsonOp;
import org.summer.blogx.infrastructure.redis.RedisKeys;
import org.summer.blogx.security.LoginUser;
import org.summer.blogx.user.service.UserService;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private RedisJsonOp redisJsonOp;

    @Override
    public LoginUser getLoginUserByUserId(Long userId) {
        String key = RedisKeys.LOGIN_KEY + userId;
        LoginUser loginUser = redisJsonOp.getObject(key, LoginUser.class);
        if (loginUser != null) {
            return loginUser;
        }
        User user = mapper.selectById(userId);
        loginUser = new LoginUser();
        loginUser.setId(userId);
        loginUser.setUsername(user.getUsername());
        redisJsonOp.setJson(key, loginUser, 30L, TimeUnit.MINUTES);
        return loginUser;
    }
}
