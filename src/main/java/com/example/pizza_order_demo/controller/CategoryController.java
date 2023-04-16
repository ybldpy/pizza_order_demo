package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.Category;
import com.example.pizza_order_demo.model.CategoryExample;
import com.example.pizza_order_demo.service.CategoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/category/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result addCategory(String categoryName){
        if (StringUtils.isBlank(categoryName)){
            Result result = new Result(ResultConstant.CODE_FAILED,"Empty",null);
            return result;
        }
        CategoryExample categoryExample=new CategoryExample();
        categoryExample.or().andCategoryNameEqualTo(categoryName).andDeletedEqualTo(0);
        List<Category> categoryList= categoryService.selectByExample(categoryExample);
        if(categoryList.size()==0){
            Category category = new Category();
            category.setCategoryName(categoryName);
            int res=categoryService.insert(category);
            if(res<1){
                Result result1=new Result(ResultConstant.CODE_FAILED,"not found", null);
                return result1;
            }
            else {
                Result resultSuccess= new Result(ResultConstant.CODE_SUCCESS,"Success",category);
                return resultSuccess;
            }
        }
        Result resultFail= new Result(ResultConstant.CODE_FAILED,"Existed",null);
        return resultFail;
    }




    @GetMapping("/category/query")
//    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    public Object getCategories(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andDeletedEqualTo(0);
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
    @PostMapping("/category/delete")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result deleteCategory(List<String> categoryName){
        if (ObjectUtils.isEmpty(categoryName)){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.PARAM_MISSING,null);
        }
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andDeletedEqualTo(0).andCategoryNameIn(categoryName);
        List<Category> categoryList = categoryService.selectByExample(categoryExample);
        if (ObjectUtils.isEmpty(categoryList)||categoryList.size()!=categoryName.size()){
            return new Result(ResultConstant.CODE_FAILED,"One or more than one categories don't find",null);
        }

        Category category = new Category();
        category.setDeleted(1);
        int res = categoryService.updateByExampleSelective(category,categoryExample);
        if (res!=categoryList.size()){
            throw new CURDException();
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
}
