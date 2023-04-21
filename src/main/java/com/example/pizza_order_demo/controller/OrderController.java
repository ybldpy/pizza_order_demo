package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.DeliverymanService;
import com.example.pizza_order_demo.service.OrderDetailService;
import com.example.pizza_order_demo.service.OrderService;
import com.example.pizza_order_demo.service.UserAddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private DeliverymanService deliverymanService;
    @Autowired
    private UserAddressService userAddressService;






    @PostMapping("/order/create")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result createOrder(@RequestBody Map<String,Object> map) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(map.get("pickupTime"))||ObjectUtils.isEmpty(map.get("shoppingCar"))||ObjectUtils.isEmpty(map.get("deliveryType"))||ObjectUtils.isEmpty(map.get("addressId"))){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.PARAM_MISSING,null);
        }
        int type = (Integer) map.get("deliveryType");
        int addressId = (Integer) map.get("addressId");
        long pickup = (Integer) map.get("pickupTime")*60*1000;
        UserAddressExample userAddressExample = new UserAddressExample();
        userAddressExample.or().andIdEqualTo(addressId);
        if ((type!=0&&type!=1)|| pickup<0){
            return new Result(ResultConstant.CODE_FAILED,"params is wrong",null);
        }
        List<Map<String,Object>> shoppingCar = (List<Map<String, Object>>) map.get("shoppingCar");
        long curTime = System.currentTimeMillis();
        Order newOrder = new Order();
        newOrder.setCreateTime(curTime);
        newOrder.setPickupTime(curTime+pickup);
        if (type==1){
            if(userAddressService.countByExample(userAddressExample)<1){
                return new Result(ResultConstant.CODE_FAILED,"params is wrong",null);
            }
            DeliverymanExample deliverymanExample = new DeliverymanExample();
            List<Deliveryman> deliverymanList = deliverymanService.selectByExample(deliverymanExample);
            if (!ObjectUtils.isEmpty(deliverymanList)&&!deliverymanList.isEmpty()){
                Deliveryman deliveryman = deliverymanList.get((int)(System.currentTimeMillis())/deliverymanList.size());
                newOrder.setDeliverymanId(deliveryman.getId());
                newOrder.setType(1);
            }
        }
        else {newOrder.setType(1);}
        newOrder.setUserName((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        newOrder.setState(0);
        newOrder.setPaid(0);
        int res = orderService.insert(newOrder);
        if (res<1){return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);}
        OrderDetail orderDetail = null;
        ObjectMapper objectMapper = new ObjectMapper();
        for(Map<String,Object> carItem:shoppingCar){
            orderDetail = new OrderDetail();
            orderDetail.setOrderId(newOrder.getId());
            orderDetail.setState(0);
            orderDetail.setSize((String) carItem.get("size"));
            orderDetail.setDishName((String) carItem.get("dishName"));
            orderDetail.setTopping(objectMapper.writeValueAsString(carItem.get("topping")));
            orderDetail.setTotalPrice((Integer) carItem.get("count")*(Integer) carItem.get("singlePrice"));
            orderDetail.setAmount((Integer) carItem.get("count"));
            if (orderDetailService.insert(orderDetail)<1){
                throw new CURDException();
            }
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,"order/pay?orderId="+newOrder.getId());
    }

    @PostMapping("/order/payment")
    public String paymentPage(String shoppingCar, int deliveryType, Model model){
        if (deliveryType!=0&&deliveryType!=1){
            return "415";
        }
        model.addAttribute("shoppingCar",shoppingCar);
        model.addAttribute("deliveryType",deliveryType);
        return "Payment/paymentpage";
    }
}
