package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.model.User;
import com.example.pizza_order_demo.model.UserAddress;
import com.example.pizza_order_demo.model.UserAddressExample;
import com.example.pizza_order_demo.model.UserExample;
import com.example.pizza_order_demo.service.UserAddressService;
import com.example.pizza_order_demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.pizza_order_demo.commons.Result;

@Controller
public class UserAddressController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/userAddress/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result addAdress(String address){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (StringUtils.isBlank(address)){
            Result result = new Result(ResultConstant.CODE_FAILED, "Empty", null);
            return result;
        }
        UserAddress address1 = new UserAddress();
        address1.setLocation(address);
        String curUser = (String) authentication.getPrincipal();
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(curUser);
        User user = userService.selectFirstByExample(userExample);
        if (user == null){
//            Result result = new Result(ResultConstant.CODE_FAILED, "not found", null);
//            return result;
            address1.setUserId(2);
            int res = userAddressService.insert(address1);
            Result result1 = new Result(ResultConstant.CODE_SUCCESS, "Success", null);
            return result1;

        }
        else {
            address1.setUserId(user.getId());
            int res = userAddressService.insert(address1);
            Result result1 = new Result(ResultConstant.CODE_SUCCESS, "Success", null);
            return result1;
        }
    }
}
