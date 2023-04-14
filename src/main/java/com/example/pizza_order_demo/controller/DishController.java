package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.CategoryService;
import com.example.pizza_order_demo.service.DishService;
import com.example.pizza_order_demo.service.FlavourService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DishController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private FlavourService flavourService;

    private static final String DEFAULT_IMG = "img/default.jpg";


    @GetMapping("/dish/add")
    public String addDish(){
        return "admin/dishAdd";
    }
    @GetMapping("/dish/get")
    @ResponseBody
    public Object getDish(){
        DishExample dishExample = new DishExample();
        List<Dish> dishes = dishService.selectByExample(dishExample);
        Map<String,Object> resultMap = new HashMap<>();
        if (dishes==null||dishes.isEmpty()){
            resultMap.put("total",0);
            resultMap.put("rows",dishes);
            return resultMap;
        }
        List<DishDTO> dishDTOs = new ArrayList<>(dishes.size());
        for(int i=0;i<dishes.size();i++){
            DishDTO dishDTO = new DishDTO(dishes.get(i));
            dishDTO.setSellStatus();
            FlavourExample flavourExample = new FlavourExample();
            flavourExample.or().andDishIdEqualTo(dishDTO.getId());
            dishDTO.setFlavours(flavourService.selectByExample(flavourExample));
            dishDTOs.add(dishDTO);
        }
        resultMap.put("total",dishDTOs.size());
        resultMap.put("rows",dishDTOs);
        return resultMap;
    }

    @PostMapping("/dish/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result addDish(Dish dish, String[] flavour){
        return null;
    }

}
