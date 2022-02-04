package org.summer.blogx.user.service;

import org.summer.blogx.security.LoginUser;

public interface UserService {

    LoginUser getLoginUserByUserId(Long userId);

}
