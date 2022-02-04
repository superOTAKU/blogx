package org.summer.blogx.infrastructure.rest;

import lombok.Data;

@Data
public class ErrorObject {
    private ErrorCode code;
    private String msg;
}
