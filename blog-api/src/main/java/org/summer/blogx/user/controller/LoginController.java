package org.summer.blogx.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.summer.blogx.user.dto.AccessToken;
import org.summer.blogx.user.dto.LoginReq;
import org.summer.blogx.user.dto.RefreshTokenReq;
import org.summer.blogx.user.service.LoginService;

import javax.validation.Valid;

@PreAuthorize("permitAll()")
@Validated
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public AccessToken login(@RequestBody @Valid LoginReq reqDTO) {
        return loginService.login(reqDTO);
    }

    @PostMapping("/refreshToken")
    public AccessToken refresh(@RequestBody @Valid RefreshTokenReq reqDTO) {
        return loginService.refresh(reqDTO);
    }

}
