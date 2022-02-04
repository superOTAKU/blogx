package org.summer.blogx.user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.summer.blogx.infrastructure.entity.User;
import org.summer.blogx.infrastructure.mapper.UserMapper;
import org.summer.blogx.infrastructure.rest.ErrorCode;
import org.summer.blogx.security.config.SecurityConstants;
import org.summer.blogx.security.config.SecurityProperties;
import org.summer.blogx.user.dto.AccessToken;
import org.summer.blogx.user.dto.LoginReq;
import org.summer.blogx.user.dto.RefreshTokenReq;
import org.summer.blogx.user.service.LoginService;

import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public AccessToken login(LoginReq reqDTO) {
        User user = mapper.selectOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, reqDTO.getUsername()));
        if (user == null) {
            throw ErrorCode.USER_NOT_EXIST.newException();
        }
        if (!passwordEncoder.matches(reqDTO.getPassword() + user.getPasswordHash(),
                user.getPassword())) {
            throw ErrorCode.PASSWORD_INVALID.newException();
        }
        return buildAccessToken(user.getId());
    }

    @Override
    public AccessToken refresh(RefreshTokenReq reqDTO) {
        DecodedJWT jwt = JWT.require(securityProperties.getAlgorithm())
                .withClaim("type", SecurityConstants.REFRESH_TOKEN_TYPE)
                .build().verify(reqDTO.getRefreshToken());
        Long userId = jwt.getClaim("userId").asLong();
        //重新颁发两个token
        return buildAccessToken(userId);
    }

    private AccessToken buildAccessToken(Long userId) {
        Date now = new Date();
        String token = JWT.create().withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + 60 * 60 * 1000))
                .withClaim("type", SecurityConstants.TOKEN_TYPE)
                .withClaim("userId", userId)
                .sign(securityProperties.getAlgorithm());
        String refreshToken = JWT.create().withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + 2 * 60 * 60 * 1000))
                .withClaim("type", SecurityConstants.REFRESH_TOKEN_TYPE)
                .withClaim("userId", userId)
                .sign(securityProperties.getAlgorithm());
        AccessToken accessToken = new AccessToken();
        accessToken.setToken(token);
        accessToken.setRefreshToken(refreshToken);
        return accessToken;

    }

}
