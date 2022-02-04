package org.summer.blogx.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    //1-1000定义系统级别错误码
    UNKNOWN_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR, "unknown error"),
    PARAM_ERROR(2, HttpStatus.BAD_REQUEST, "param error"),
    UNAUTHORIZED(3, HttpStatus.UNAUTHORIZED, "unauthorized"),
    FORBIDDEN(4, HttpStatus.FORBIDDEN, "forbidden"),
    //1000以上定义业务错误码
    USER_NOT_EXIST(1003, HttpStatus.BAD_REQUEST, "user not exist"),
    PASSWORD_INVALID(1004, HttpStatus.BAD_REQUEST, "password invalid"),

    ;
    @JsonValue
    private final int code;
    private final HttpStatus status;
    private final String desc;

    ErrorCode(int code, HttpStatus status, String desc) {
        this.code = code;
        this.status = status;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public ServiceException newException() {
        return newException(status, desc);
    }

    public ServiceException newException(HttpStatus status) {
        return newException(status, desc);
    }

    public ServiceException newException(HttpStatus status, String msg) {
        ServiceException serviceException = new ServiceException();
        serviceException.setHttpStatus(status);
        ErrorObject errorObject = new ErrorObject();
        errorObject.setMsg(msg);
        errorObject.setCode(this);
        serviceException.setErrorObject(errorObject);
        return serviceException;
    }

}
