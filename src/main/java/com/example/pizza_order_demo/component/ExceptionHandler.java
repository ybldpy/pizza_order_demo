package com.example.pizza_order_demo.component;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {



    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handleException(Exception e){
        return handleInternalException(e);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(value = CURDException.class)
    @ResponseBody
    public Result handleInternalException(Exception e){
        return new Result(ResultConstant.CODE_FAILED,e.getMessage(),null);
    }


}
