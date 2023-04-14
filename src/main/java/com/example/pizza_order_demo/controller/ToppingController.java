package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.model.Topping;
import com.example.pizza_order_demo.model.ToppingExample;
import com.example.pizza_order_demo.service.ToppingService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ToppingController {
    @Autowired
    private ToppingService toppingService;

    @PostMapping("/topping/add")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result addTopping(String toppingName,Integer price){
        if (StringUtils.isBlank(toppingName)){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.PARAM_MISSING,null);
        }
        if (ObjectUtils.isEmpty(price)||price==0){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }

        ToppingExample toppingExample = new ToppingExample();
        toppingExample.or().andNameEqualTo(toppingName);
        if (toppingService.selectFirstByExample(toppingExample)!=null){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.EXIST,null);
        }
        Topping topping = new Topping();
        topping.setPrice(price);
        topping.setName(toppingName);
        int res = toppingService.insert(topping);
        if (res<=0){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }


}
