package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.model.Log;
import com.example.pizza_order_demo.model.LogExample;
import com.example.pizza_order_demo.service.LogService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LogController {


    @Autowired
    private LogService logService;


    @GetMapping("/log/management")
    public String manageLog(){
        return "admin/masterFile";
    }

    @GetMapping("/log/get")
    @ResponseBody
    public Object getLogs(){
        LogExample logExample = new LogExample();
        List<Log> logs = logService.selectByExample(logExample);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",0);
        if (ObjectUtils.isEmpty(logs)){
            resultMap.put("rows",new ArrayList<>());
            return resultMap;
        }
        resultMap.put("total",logs.size());
        resultMap.put("rows",logs);
        return logs;
    }




}
