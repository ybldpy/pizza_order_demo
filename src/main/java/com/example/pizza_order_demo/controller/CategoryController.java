package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.model.Category;
import com.example.pizza_order_demo.model.CategoryExample;
import com.example.pizza_order_demo.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category/management")
    @PreAuthorize("hasRole('admin')")
    public String categoryManagement(){
        return "admin/categoryManagement";
    }


    @GetMapping("/category/query")
    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    public Object getCategories(){
        CategoryExample categoryExample = new CategoryExample();
//        if (!StringUtils.isBlank(search)){
//            categoryExample.or().andCategoryNameLike("%"+search+"%");
//            if (StringUtils.isNumeric(search)){
//                categoryExample.or().andIdEqualTo(Integer.parseInt(search));
//            }
//        }

        List<Category> categoryList = categoryService.selectByExample(categoryExample);
        int count = categoryList.size();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",count);
        resultMap.put("rows",categoryList);
        return resultMap;

    }
}
