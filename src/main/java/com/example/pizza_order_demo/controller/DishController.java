package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.model.Category;
import com.example.pizza_order_demo.model.CategoryExample;
import com.example.pizza_order_demo.model.Dish;
import com.example.pizza_order_demo.model.DishExample;
import com.example.pizza_order_demo.service.CategoryService;
import com.example.pizza_order_demo.service.DishService;
import com.example.pizza_order_demo.service.FlavourService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller

public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private FlavourService flavourService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/dish/add")
    @Transactional(rollbackFor = Exception.class)
    public Result addDish (Dish dish){
        if(StringUtils.isBlank(dish.getName())){
            Result result = new Result(ResultConstant.CODE_FAILED,"Empty",null);
            return result;
        }
        DishExample dishExample=new DishExample();
        dishExample.or().andNameEqualTo(dish.getName());
        CategoryExample categoryExample=new CategoryExample();
        categoryExample.or().andCategoryNameEqualTo(dish.getCategoryName());
        Category category = categoryService.selectFirstByExample(categoryExample);
        if (category==null){
            return new Result(ResultConstant.CODE_FAILED,"category not found",null);
        }
        List<Dish> dishList = dishService.selectByExample(dishExample);
        if(dishList.size()==0){
            int res=dishService.insert(dish);
            if(res<1){
                Result result1=new Result(ResultConstant.CODE_FAILED,"not found", null);
                return result1;
            }
            else {
                Result result2= new Result(ResultConstant.CODE_SUCCESS,"Success",null);
                return result2;
            }
        }
        Result result3 = new Result(ResultConstant.CODE_FAILED,"Existed",null);
        return result3;

    }


}
