package com.example.pizza_order_demo.component;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.Log;
import com.example.pizza_order_demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * class that is responsible for handling exception
 * managed by Spring IOC
 */
@ControllerAdvice
public class ExceptionHandler{

    @Autowired
    private LogService logService;

    @org.springframework.web.bind.annotation.ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public Result handleDenied(AccessDeniedException e){
        AccessDeniedException accessDeniedException = new AccessDeniedException("Access denied",e.getCause());
        return handleInternalException(accessDeniedException);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(Exception e){
        return handleInternalException(e);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = CURDException.class)
    @ResponseBody
    public Result handleInternalException(Exception e){
        e.printStackTrace();
        Log log = new Log();
        log.setUserName("System");
        log.setOperationtype(0);
        log.setIsHandled(0);
        log.setDescription(e.getLocalizedMessage());
        log.setCreateTime(System.currentTimeMillis());
        logService.insert(log);
        return new Result(ResultConstant.CODE_FAILED,e.getMessage(),null);
    }


}
