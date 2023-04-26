package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.OrderDetailService;
import com.example.pizza_order_demo.service.OrderService;
import com.example.pizza_order_demo.service.PaymentService;
import com.example.pizza_order_demo.service.WalletService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private WalletService walletService;


    @PostMapping("/pay/order")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result payOrder(int orderId, Authentication authentication){
        Order order = orderService.selectByPrimaryKey(orderId);
        if (ObjectUtils.isEmpty(order)){
            return new Result(ResultConstant.CODE_FAILED,"Order don't find",null);
        }
        if (order.getPaid()==1){
            return new Result(ResultConstant.CODE_FAILED,"This order has been paid",null);
        }
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.or().andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        if (ObjectUtils.isEmpty(orderDetailList)){
            return new Result(ResultConstant.CODE_FAILED,"Pay failed",null);
        }
        int count = 0;
        for(OrderDetail orderDetail:orderDetailList){
            count+=orderDetail.getTotalPrice();
        }
        String curUser = "admin";
        if (!ObjectUtils.isEmpty(authentication)){
            Object principal = authentication.getPrincipal();
            curUser = principal==null?curUser:((UserDetailsImpl)principal).getUsername();
        }
        WalletExample walletExample = new WalletExample();
        walletExample.or().andUsernameEqualTo(curUser);
        Wallet wallet = walletService.selectFirstByExample(walletExample);
        if (ObjectUtils.isEmpty(wallet)){
            return new Result(ResultConstant.CODE_FAILED,"Pay failed",null);
        }
        if (wallet.getBalance()<count){
            return new Result(ResultConstant.CODE_FAILED,"Balance is not enough",null);
        }
        Payment payment = new Payment();
        payment.setPrice(count);
        payment.setCreateTime(System.currentTimeMillis());
        payment.setOrderId(orderId);
        int res = paymentService.insert(payment);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,"Pay failed",null);
        }
        res = walletService.updateByPrimaryKey(wallet);
        if(res<1){
            throw new CURDException();
        };
        order.setPaid(1);
        res = orderService.updateByPrimaryKey(order);
        if (res<1){
            throw new CURDException();
        }
        return new Result(ResultConstant.CODE_SUCCESS,"Pay failed",null);
    }

    @GetMapping("/pay/order")
    public String payOrder(int orderId, Model model){
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.or().andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        if (ObjectUtils.isEmpty(orderDetailList)){
            return "404";
        }
        int count = 0;
        for(OrderDetail orderDetail:orderDetailList){
            count+=orderDetail.getTotalPrice();
        }
        model.addAttribute("orderId",orderId);
        model.addAttribute("price",count);
        return "payment/doPay";

    }

}
