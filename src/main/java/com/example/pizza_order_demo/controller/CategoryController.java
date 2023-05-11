package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.CategoryService;
import com.example.pizza_order_demo.service.DishService;
import com.example.pizza_order_demo.service.LogService;
import com.example.pizza_order_demo.utils.ModelUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class CategoryController {


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LogService logService;
    @Autowired
    private DishService dishService;


    @GetMapping("/category/management")
    @PreAuthorize("hasRole('admin')")
    public String categoryManagement(){
        return "admin/categoryManagement";
    }

    @PostMapping("/category/add")
    @PreAuthorize("hasRole('admin')")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result addCategory(String categoryName,Authentication authentication){
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
            category.setDeleted(0);
            int res=categoryService.insert(category);
            if(res<1){
                Result result1=new Result(ResultConstant.CODE_FAILED,"not found", null);
                return result1;
            }
            else {
                Result resultSuccess= new Result(ResultConstant.CODE_SUCCESS,"Success",category);
                String curUser = "admin";
                if (!ObjectUtils.isEmpty(authentication)){
                    Object principal = authentication.getPrincipal();
                    curUser = principal==null?curUser:((UserDetailsImpl)principal).getUsername();
                }
                Log log = new Log();
                log.setCreateTime(System.currentTimeMillis());
                log.setIsHandled(0);
                log.setOperationtype(1);
                log.setDescription(String.format("add category %s",category.getCategoryName()));
                log.setUserName(curUser);
                logService.insert(log);
                return resultSuccess;
            }
        }
        Result resultFail= new Result(ResultConstant.CODE_FAILED,"Existed",null);
        return resultFail;
    }

    @PostMapping("category/edit")
    @ResponseBody
    @PreAuthorize("hasRole('admin')")
    @Transactional(rollbackFor = Exception.class)
    public Result editCategory(Category category,Authentication authentication){
        if (ObjectUtils.anyNull(category.getCategoryName(),category.getId())||StringUtils.isBlank(category.getCategoryName())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        Category old = categoryService.selectByPrimaryKey(category.getId());
        if (ObjectUtils.isEmpty(old)&&old.getDeleted()==0){
            return new Result(ResultConstant.CODE_FAILED,"Category not find",null);
        }
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andDeletedEqualTo(0).andCategoryNameEqualTo(category.getCategoryName());
        if (categoryService.countByExample(categoryExample)>0){
            return new Result(ResultConstant.CODE_FAILED,"Category existed!",null);
        }
        category.setId(old.getId());
        category.setDeleted(old.getDeleted());
        int res = categoryService.updateByPrimaryKey(category);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        DishExample dishExample = new DishExample();
        dishExample.or().andCategoryNameEqualTo(old.getCategoryName());
        List<Dish> dishList = dishService.selectByExample(dishExample);
        if (ObjectUtils.isEmpty(dishList)){return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);}
        List<Integer> ids = new ArrayList<>(dishList.size()+1);
        for(Dish dish:dishList){
            ids.add(dish.getId());
        }
        dishExample.clear();
        dishExample.or().andIdIn(ids);
        Dish dish = new Dish();
        dish.setCategoryName(category.getCategoryName());
        res = dishService.updateByExampleSelective(dish,dishExample);
        if (res!=dishList.size()){
            throw new CURDException();
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }




    @GetMapping("/category/query")
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
    @PreAuthorize("hasRole('admin')")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result deleteCategory(@RequestBody List<String> categoryName, Authentication authentication){
        if (ObjectUtils.isEmpty(categoryName)){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.PARAM_MISSING,null);
        }
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andDeletedEqualTo(0).andCategoryNameIn(categoryName);
        List<Category> categoryList = categoryService.selectByExample(categoryExample);
        if (ObjectUtils.isEmpty(categoryList)||categoryList.size()!=categoryName.size()){
            return new Result(ResultConstant.CODE_FAILED,"One or more than one categories don't find",null);
        }
        DishExample dishExample = new DishExample();
        dishExample.or().andCategoryNameIn(categoryName).andDeletedEqualTo(0);
        if (dishService.countByExample(dishExample)>0){
            return new Result(ResultConstant.CODE_FAILED,"Some dishes is in those categories, please make sure there is no dishes in categories",null);
        }
        Category category = new Category();
        category.setDeleted(1);
        int res = categoryService.updateByExampleSelective(category,categoryExample);
        if (res!=categoryList.size()){
            throw new CURDException();
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
        log.setDescription(String.format("add category %s",category.getCategoryName()));
        log.setUserName(curUser);
        logService.insert(log);
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
}
