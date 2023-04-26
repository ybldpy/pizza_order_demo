package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.LogService;
import com.example.pizza_order_demo.service.WalletService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private LogService logService;


    @PostMapping("/wallet/recharge")
    @ResponseBody
    public Result addBalance(int balance, Authentication authentication){
        if (balance<=0){
            return new Result(ResultConstant.CODE_FAILED,"recharge cannot be negetive",null);
        }
        String curUser = "admin";
        if (!ObjectUtils.isEmpty(authentication)){
            Object principal = authentication.getPrincipal();
            curUser = principal==null?curUser:((UserDetailsImpl)principal).getUsername();
        }
        WalletExample walletExample = new WalletExample();
        walletExample.or().andUsernameEqualTo(curUser);
        Wallet wallet = walletService.selectFirstByExample(walletExample);
        wallet.setBalance(wallet.getBalance()+balance);
        int res = walletService.updateByPrimaryKey(wallet);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,wallet.getBalance());

    }

}
