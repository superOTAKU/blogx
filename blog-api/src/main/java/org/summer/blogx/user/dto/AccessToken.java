package org.summer.blogx.user.dto;

import lombok.Data;

@Data
public class AccessToken {
    private String token;
    private String refreshToken;
}
