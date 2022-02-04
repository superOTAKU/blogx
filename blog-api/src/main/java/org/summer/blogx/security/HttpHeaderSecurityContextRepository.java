package org.summer.blogx.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.summer.blogx.security.config.SecurityProperties;
import org.summer.blogx.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Objects;

import static org.summer.blogx.security.config.SecurityConstants.*;

@Component
public class HttpHeaderSecurityContextRepository implements SecurityContextRepository {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private UserService userService;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        if (!containsContext(requestResponseHolder.getRequest())) {
            return new SecurityContextImpl();
        }
        String jwtToken = requestResponseHolder.getRequest().getHeader(AUTHENTICATION_HEADER);
        DecodedJWT jwt = JWT.require(securityProperties.getAlgorithm()).build().verify(jwtToken);
        String type = jwt.getClaim("type").asString();
        //不允许refreshToken
        if (!Objects.equals(TOKEN_TYPE, type)) {
            return new SecurityContextImpl();
        }
        Long userId = jwt.getClaim("userId").asLong();
        LoginUser loginUser = userService.getLoginUserByUserId(userId);
        return new SecurityContextImpl(new PreAuthenticatedAuthenticationToken(loginUser, null,
                List.of(new SimpleGrantedAuthority("USER"))));
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
        //noop, just retrieve token from header
    }

    @Override
    public boolean containsContext(HttpServletRequest request) {
        return StringUtils.hasText(request.getHeader(AUTHENTICATION_HEADER));
    }
}
