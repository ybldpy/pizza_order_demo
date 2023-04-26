package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.Deliveryman;
import com.example.pizza_order_demo.model.DeliverymanExample;
import com.example.pizza_order_demo.model.Log;
import com.example.pizza_order_demo.model.UserDetailsImpl;
import com.example.pizza_order_demo.service.DeliverymanService;
import com.example.pizza_order_demo.service.LogService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class DeliverymanController {


    @Autowired
    private DeliverymanService deliverymanService;
    @Autowired
    private LogService logService;

    @GetMapping("/deliveryman/management")
    public String manageDeliveryman(){
        return "admin/deliveryman";
    }


    @PostMapping("/deliveryman/add")
    @ResponseBody
    public Result addDeliveryman(Deliveryman deliveryman,Authentication authentication){
        if (StringUtils.isAnyBlank(deliveryman.getDeliverymanName(),deliveryman.getPhone())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        deliveryman.setDeleted(0);
        int res = deliverymanService.insert(deliveryman);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }

        String curUser = "admin";
        if (!ObjectUtils.isEmpty(authentication)){
            Object principal = authentication.getPrincipal();
            curUser = principal==null?curUser:((UserDetailsImpl)principal).getUsername();
        }
        Log log = new Log();
        log.setCreateTime(System.currentTimeMillis());
        log.setIsHandled(0);
        log.setOperationtype(1);
        log.setDescription(String.format("add deliverymen id:%d, name:%s, phone:%s", deliveryman.getId(),deliveryman.getDeliverymanName(),deliveryman.getPhone()));
        log.setUserName(curUser);
        logService.insert(log);
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);

    }

    @PostMapping("/deliveryman/edit")
    @ResponseBody
    public Result editDeliveryman(Deliveryman deliveryman,Authentication authentication){

        if (ObjectUtils.anyNull(deliveryman.getId(),deliveryman.getDeliverymanName(),deliveryman.getPhone())|| StringUtils.isAnyBlank(deliveryman.getPhone(),deliveryman.getDeliverymanName())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        Deliveryman old = deliverymanService.selectByPrimaryKey(deliveryman.getId());
        if (ObjectUtils.isEmpty(old)){
            return new Result(ResultConstant.CODE_FAILED,"Delivery man not find",null);
        }
        deliveryman.setId(old.getId());
        int res = deliverymanService.updateByPrimaryKey(deliveryman);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }

        String curUser = "admin";
        if (!ObjectUtils.isEmpty(authentication)){
            Object principal = authentication.getPrincipal();
            curUser = principal==null?curUser:((UserDetailsImpl)principal).getUsername();
        }
        Log log = new Log();
        log.setCreateTime(System.currentTimeMillis());
        log.setIsHandled(0);
        log.setOperationtype(1);
        log.setDescription(String.format("edit deliverymen id:%d, name:%s, phone:%s ---> name:%s,phone:%s", old.getId(),old.getDeliverymanName(),old.getPhone(),deliveryman.getDeliverymanName(),deliveryman.getPhone()));
        log.setUserName(curUser);
        logService.insert(log);
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);

    }

    @GetMapping("/deliveryman/get")
    @ResponseBody
    public Object getDeliveryman(){

        DeliverymanExample deliverymanExample = new DeliverymanExample();
        deliverymanExample.or().andDeletedEqualTo(0);
        List<Deliveryman> deliverymanList = deliverymanService.selectByExample(deliverymanExample);
        Map<String,Object> resultMap = new HashMap<>();
        if (ObjectUtils.isEmpty(deliverymanExample)){
            resultMap.put("total",0);
            resultMap.put("rows",new ArrayList<>());
        }

        resultMap.put("total",deliverymanList.size());
        resultMap.put("rows",deliverymanList);
        return resultMap;
    }
    @PostMapping("/deliveryman/delete")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result deleteDeliverymen(@RequestBody List<Integer> ids, Authentication authentication){
        if (ObjectUtils.isEmpty(ids)){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.PARAM_MISSING,null);
        }

        DeliverymanExample deliverymanExample = new DeliverymanExample();
        deliverymanExample.or().andDeletedEqualTo(0).andIdIn(ids);

        List<Deliveryman> deliverymanList =deliverymanService.selectByExample(deliverymanExample);
        if (ObjectUtils.isEmpty(deliverymanList)||deliverymanList.size()!=ids.size()){
            return new Result(ResultConstant.CODE_FAILED,"Illegal deliveryman id",null);
        }
        String[] names = new String[deliverymanList.size()];
        int p = 0;
        for(Deliveryman deliveryman:deliverymanList){
            names[p++] = deliveryman.getDeliverymanName();
        }
        Deliveryman deliveryman = new Deliveryman();
        deliveryman.setDeleted(1);
        int res = deliverymanService.updateByExampleSelective(deliveryman,deliverymanExample);
        if (res!=ids.size()){
            throw new CURDException();
        }
        String curUser = "admin";
        if (!ObjectUtils.isEmpty(authentication)){
            Object principal = authentication.getPrincipal();
            curUser = principal==null?curUser:((UserDetailsImpl)principal).getUsername();
        }
        Log log = new Log();
        log.setCreateTime(System.currentTimeMillis());
        log.setIsHandled(0);
        log.setOperationtype(1);
        log.setDescription(String.format("delete deliverymen ids:%s, names:%s", Arrays.toString(ids.toArray()),Arrays.toString(names)));
        log.setUserName(curUser);
        logService.insert(log);
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }


}
