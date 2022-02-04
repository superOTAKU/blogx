package org.summer.blogx.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RefreshTokenReq {
    @NotNull
    @NotBlank
    private String refreshToken;
}
