package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.UserAddressService;
import com.example.pizza_order_demo.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.pizza_order_demo.commons.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserAddressController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/user/address/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result addAddress(UserAddress userAddress){
        if (StringUtils.isAnyBlank(userAddress.getLocation(),userAddress.getPhone(),userAddress.getContact())){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);}
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String curUser = principal instanceof String?"anonymousUser":((UserDetailsImpl)principal).getUsername();
        userAddress.setUserName(curUser);
        userAddress.setDeleted(0);
        int res = userAddressService.insert(userAddress);
        if (res<1){return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);}
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }

    @PostMapping("/user/address/delete")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result deleteAddress(@RequestBody List<Integer> ids){
        if (ObjectUtils.isEmpty(ids)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        UserAddressExample userAddressExample = new UserAddressExample();
        userAddressExample.or().andIdIn(ids);
        UserAddress userAddress = new UserAddress();
        userAddress.setDeleted(1);
        int res = userAddressService.updateByExampleSelective(userAddress,userAddressExample);
        if (res!=ids.size()){
            throw new CURDException();
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
    @GetMapping("/user/address")
    public String userAddress(){
        return "user/address";
    }
    @GetMapping("/user/address/query")
    @ResponseBody
    public Object queryUserAddress(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String curUser = principal instanceof String?"anonymousUser":((UserDetailsImpl)principal).getUsername();
        UserAddressExample userAddressExample = new UserAddressExample();
        userAddressExample.or().andDeletedEqualTo(0).andUserNameEqualTo(curUser);
        List<UserAddress> userAddresses = userAddressService.selectByExample(userAddressExample);
        Map<String,Object> resultMap = new HashMap<>();
        if (ObjectUtils.isEmpty(userAddresses)){
            resultMap.put("total",0);
        }
        else {
            resultMap.put("total",userAddresses.size());
            resultMap.put("rows",userAddresses);
        }
        return resultMap;
    }
    @PostMapping("/user/address/edit")
    @ResponseBody
    public Result editUserAddress(UserAddress userAddress){
        if (StringUtils.isAnyBlank(userAddress.getContact(),userAddress.getPhone(),userAddress.getLocation())||ObjectUtils.isEmpty(userAddress.getId())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        UserAddressExample userAddressExample = new UserAddressExample();
        userAddressExample.or().andIdEqualTo(userAddress.getId());
        int count = userAddressService.countByExample(userAddressExample);
        if (count<1){
            return new Result(ResultConstant.CODE_FAILED,"Address doesn't exist",null);
        }
        userAddress.setDeleted(0);
        userAddress.setUserName((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int res = userAddressService.updateByPrimaryKey(userAddress);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
}
