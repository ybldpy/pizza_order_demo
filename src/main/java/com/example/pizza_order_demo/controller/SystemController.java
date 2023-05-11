package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.component.SystemControlComponent;
import com.example.pizza_order_demo.model.Log;
import com.example.pizza_order_demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("hasRole('admin')")
public class SystemController {

    @Autowired
    private LogService logService;
    @Autowired
    private SystemControlComponent systemControlComponent;

    @GetMapping("/system/close")
    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    public Result closeSystem(Authentication authentication){
        Log log = new Log();
        String curUser = ((UserDetails)authentication.getPrincipal()).getUsername();
        log.setUserName(curUser);
        log.setCreateTime(System.currentTimeMillis());
        log.setOperationtype(1);
        log.setIsHandled(0);
        log.setDescription("Close system");
        int res = logService.insert(log);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        systemControlComponent.closeSystem();
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
    @GetMapping("/system/open")
    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    public Result openSystem(Authentication authentication){
        Log log = new Log();
        String curUser = ((UserDetails)authentication.getPrincipal()).getUsername();
        log.setUserName(curUser);
        log.setCreateTime(System.currentTimeMillis());
        log.setOperationtype(1);
        log.setIsHandled(0);
        log.setDescription("Open system");
        int res = logService.insert(log);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        systemControlComponent.openSystem();
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
}
