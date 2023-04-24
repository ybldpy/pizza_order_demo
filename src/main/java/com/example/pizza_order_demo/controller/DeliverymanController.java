package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.CategoryService;
import com.example.pizza_order_demo.service.DeliverymanService;
import com.example.pizza_order_demo.service.DishService;
import com.example.pizza_order_demo.service.ToppingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class DeliverymanController {


    @Autowired
    private DeliverymanService deliverymanService;

    @GetMapping("/deliveryman/management")
    public String deliverymanManagement(){
        return "admin/deliverymanManagement";
    }
    @PostMapping("/deliveryman/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result addDeliveryman (Deliveryman deliveryman){
        if(ObjectUtils.isEmpty(deliveryman)||StringUtils.isAnyBlank(deliveryman.getName(),deliveryman.getPhone())){
            Result result = new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
            return result;
        }
        int res = deliverymanService.insert(deliveryman);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);

//        DeliverymanExample deliverymanExample=new DeliverymanExample();
//        List<Deliveryman> deliverymanList= deliverymanService.selectByExample(deliverymanExample);
//        if(deliverymanList.size()==0){
//            Deliveryman deliveryman = new Deliveryman();
//            deliveryman.setName(Deliveryman);
//            int res=deliverymanService.insert(deliveryman);
//            if(res<1){
//                Result result1=new Result(ResultConstant.CODE_FAILED,"not found", null);
//                return result1;
//            }
//            else {
//                Result resultSuccess= new Result(ResultConstant.CODE_SUCCESS,"Success",deliveryman);
//                return resultSuccess;
//            }
//        }
//        else {
//            Result result2=new Result(ResultConstant.CODE_FAILED,"already exist", null);
//            return result2;
//        }




    }
    @GetMapping("/deliveryman/query")
    @ResponseBody
public Result queryDeliveryman(){
        DeliverymanExample deliverymanExample=new DeliverymanExample();
        List<Deliveryman> deliverymanList=deliverymanService.selectByExample(deliverymanExample);
        if(deliverymanList.size()==0){
            Result result1=new Result(ResultConstant.CODE_FAILED,"not found", null);
            return result1;
        }
        else {
            Result resultSuccess= new Result(ResultConstant.CODE_SUCCESS,"Success",deliverymanList);
            return resultSuccess;
        }
    }



    @PostMapping("/deliveryman/delete")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result deleteDeliveryman (Integer id){
        if(ObjectUtils.isEmpty(id)){
            Result result = new Result(ResultConstant.CODE_FAILED,"Empty",null);
            return result;
        }
        DeliverymanExample deliverymanExample=new DeliverymanExample();
        deliverymanExample.or().andIdEqualTo(id);
        List<Deliveryman> deliverymanList= deliverymanService.selectByExample(deliverymanExample);
        if(deliverymanList.size()==0){
            Result result1=new Result(ResultConstant.CODE_FAILED,"not found", null);
            return result1;
        }
        else {
            int res=deliverymanService.deleteByPrimaryKey(id);
            if(res<1){
                Result result1=new Result(ResultConstant.CODE_FAILED,"not found", null);
                return result1;
            }
            else {
                Result resultSuccess= new Result(ResultConstant.CODE_SUCCESS,"Success",null);
                return resultSuccess;
            }
        }
    }





}
