package org.summer.blogx.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorObject handleError(Exception e, HttpServletRequest request,
                                   HttpServletResponse response) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setCode(ErrorCode.UNKNOWN_ERROR);
        errorObject.setMsg(e.getMessage());
        response.setStatus(getStatus(request).value());
        return errorObject;
    }

    /**
     * 如果SpringMVC解析了http状态码，使用SpringMVC解析的结果
     */
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception e) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ErrorObject handleServiceError(ServiceException e, HttpServletResponse response) {
        ErrorObject errorObject = e.getErrorObject();
        response.setStatus(e.getHttpStatus().value());
        return errorObject;
    }

    /**
     * 处理用户认证授权异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ErrorObject handleAccessDenied(HttpServletResponse response) {
        //区分403和401
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return ErrorCode.UNAUTHORIZED.newException().getErrorObject();
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return ErrorCode.FORBIDDEN.newException().getErrorObject();
        }
    }

}
