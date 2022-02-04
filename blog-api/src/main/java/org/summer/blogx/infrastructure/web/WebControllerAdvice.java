package org.summer.blogx.infrastructure.web;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class WebControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //自动把请求参数或post form表单的字符串去掉前后空白
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

}
