package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.model.User;
import com.example.pizza_order_demo.model.UserAddress;
import com.example.pizza_order_demo.model.UserAddressExample;
import com.example.pizza_order_demo.model.UserExample;
import com.example.pizza_order_demo.service.UserAddressService;
import com.example.pizza_order_demo.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
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
    public Result addAddress(UserAddress userAddress){
        if (StringUtils.isAnyBlank(userAddress.getLocation(),userAddress.getPhone())){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);}
        String curUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserExample userExample = new UserExample();
        userExample.or().andUsernameEqualTo(curUser);
        User user = userService.selectFirstByExample(userExample);
        if (ObjectUtils.isEmpty(user)){
            return new Result(ResultConstant.CODE_FAILED, "User does't exist",null);
        }
        userAddress.setUserId(user.getId());
        int res = userAddressService.insert(userAddress);
        if (res<1){return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);}
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
}
