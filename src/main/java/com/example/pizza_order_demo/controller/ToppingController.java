package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.exception.InternalException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.DishService;
import com.example.pizza_order_demo.service.LogService;
import com.example.pizza_order_demo.service.ToppingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ToppingController {
    @Autowired
    private ToppingService toppingService;
    @Autowired
    private LogService logService;
    @Autowired
    private DishService dishService;

    @PostMapping("/topping/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result addTopping(String toppingName, Integer price, Authentication authentication){
        if (StringUtils.isBlank(toppingName)){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.PARAM_MISSING,null);
        }
        if (ObjectUtils.isEmpty(price)||price==0){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }

        ToppingExample toppingExample = new ToppingExample();
        toppingExample.or().andDeletedEqualTo(0).andToppingNameEqualTo(toppingName);
        toppingExample.or().andToppingNameEqualTo(toppingName);
        if (toppingService.selectFirstByExample(toppingExample)!=null){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.EXIST,null);
        }
        Topping topping = new Topping();
        topping.setPrice(price);
        topping.setToppingName(toppingName);
        topping.setDeleted(0);
        int res = toppingService.insert(topping);
        if (res<=0){
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
        log.setDescription(String.format("Adding topping id: %d, name:%s, price: %d.", topping.getId(),topping.getToppingName(), topping.getPrice()));
        log.setUserName(curUser);
        logService.insert(log);
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
    @GetMapping("/topping/get")
    @ResponseBody
    public Object getTopping(){
        ToppingExample toppingExample = new ToppingExample();
        toppingExample.or().andDeletedEqualTo(0);
        List<Topping> toppings = toppingService.selectByExample(toppingExample);
        Map<String,Object> resultMap = new HashMap<>();
        if (ObjectUtils.isEmpty(toppings)){
            resultMap.put("total",0);
            return resultMap;
        }
        resultMap.put("total",toppings.size());
        resultMap.put("rows",toppings);
        return resultMap;
    }

    @PostMapping("/topping/delete")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result deleteTopping(@RequestBody List<Integer> ids,Authentication authentication) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(ids)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        ToppingExample toppingExample = new ToppingExample();
        toppingExample.or().andIdIn(ids);
        if (toppingService.countByExample(toppingExample)!= ids.size()){
            return new Result(ResultConstant.CODE_FAILED,"Some toppings don't find",null);
        }
        Topping updateTopping = new Topping();
        updateTopping.setDeleted(1);
        int res  =toppingService.updateByExampleSelective(updateTopping,toppingExample);
        if (res!=ids.size()){
            throw new CURDException();
        }
        List<Topping> toppingList = toppingService.selectByExample(toppingExample);
        Set<String> toppingNames = new HashSet<>();
        for(Topping topping:toppingList){
            toppingNames.add(topping.getToppingName());
        }
        DishExample dishExample = new DishExample();
        List<Dish> dishList = dishService.selectByExample(dishExample);
        for(Dish dish:dishList){
            for(String s:toppingNames){
                if (dish.getTopping().contains(s)){
                    return new Result(ResultConstant.CODE_FAILED,"Topping is used by dishes, please modify topping of dish first!",null);
                }
            }
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
        log.setDescription("Delete toppings");
        log.setUserName(curUser);
        logService.insert(log);
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);

    }

    @PostMapping("/topping/modify")
    @ResponseBody
    public Result modifyTopping(Topping topping,Authentication authentication){
        if (ObjectUtils.anyNull(topping.getPrice(),topping.getId())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }

        ToppingExample toppingExample = new ToppingExample();
        toppingExample.or().andIdEqualTo(topping.getId());
        Topping old = toppingService.selectByPrimaryKey(topping.getId());
        if (old==null){
            return new Result(ResultConstant.CODE_FAILED,"topping not find",null);
        }
        Topping updateTopping = new Topping();
        updateTopping.setPrice(topping.getPrice());
        int res = toppingService.updateByExampleSelective(updateTopping,toppingExample);
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
        log.setDescription(String.format("Modify topping id: %d, old price: %d ,new price: %d",topping.getId(),old.getPrice(),updateTopping.getPrice()));
        log.setUserName(curUser);
        logService.insert(log);
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);

    }

    @GetMapping("/topping/management")
    public String manageTopping(){

        return "admin/topping";
    }


}
