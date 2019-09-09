package com.chenyilei.demo1.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/28- 13:15
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MyException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Object handlerExce1(MyException e, HttpServletRequest request, HttpServletResponse response){
        return Arrays.asList(e.id);
    }

}
