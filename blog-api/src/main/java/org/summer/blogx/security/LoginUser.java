package org.summer.blogx.security;

import lombok.Data;

import java.security.Principal;

/**
 * 记录登录用户信息
 */
@Data
public class LoginUser implements Principal {
    private Long id;
    private String username;

    @Override
    public String getName() {
        return username;
    }

}
