package org.summer.blogx.user.service;

import org.summer.blogx.user.dto.AccessToken;
import org.summer.blogx.user.dto.LoginReq;
import org.summer.blogx.user.dto.RefreshTokenReq;

public interface LoginService {

    AccessToken login(LoginReq reqDTO);

    AccessToken refresh(RefreshTokenReq reqDTO);

}
