package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.CategoryService;
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
public class DishController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private ToppingService toppingService;

    private static final String DEFAULT_IMG = "img/default.jpg";


    @GetMapping("/dish/add")
    public String addDish(){
        return "admin/dishAdd";
    }
    @GetMapping("/dish/get")
    @ResponseBody
    public Object getDish(){
        Map<String,Object> resultMap = new HashMap<>();
        DishExample dishExample = new DishExample();
        dishExample.or().andDeletedEqualTo(0);
        List<Dish> DishList = dishService.selectByExample(dishExample);
        int count = DishList.size();
        resultMap.put("total",count);
        resultMap.put("rows",DishList);
        return resultMap;
    }

    @GetMapping("/dish/management")
    public String manageDish(){
        return "admin/dishTable";
    }

    @PostMapping("/dish/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result addDish(@RequestBody Map<String,Object> map){
        Dish dish = null;
        try {
            dish = Dish.map2Dish(map);
        } catch (JsonProcessingException | ClassCastException e) {
            e.printStackTrace();
            return new Result(ResultConstant.CODE_FAILED,"params is illegal",null);
        }
        if (ObjectUtils.isEmpty(dish)){return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);}
        if (StringUtils.isAnyBlank(dish.getCategoryName(),dish.getDishName(),dish.getSizePrice())){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andCategoryNameEqualTo(dish.getCategoryName()).andDeletedEqualTo(0);
        if (categoryService.countByExample(categoryExample)==0){return new Result(ResultConstant.CODE_FAILED,"category doesn't exist",null);}
        DishExample dishExample = new DishExample();
        dishExample.or().andDishNameEqualTo(dish.getDishName()).andDeletedEqualTo(0);
        if (dishService.countByExample(dishExample)!=0){return new Result(ResultConstant.CODE_FAILED,"dish existed",null);}
        List<String> toppings = (List<String>) map.get("toppings");
        if (toppings!=null){
            ToppingExample toppingExample = new ToppingExample();
            toppingExample.or().andDeletedEqualTo(0);
            List<Topping> toppingList = toppingService.selectByExample(toppingExample);
            boolean exist = false;
            for(String s:toppings){
                for(Topping t:toppingList){
                    if (s.equals(t.getToppingName())){
                        exist = true;
                        break;
                    }
                }
                if (!exist){return new Result(ResultConstant.CODE_FAILED,"topping doesn't exist",null);}
                exist = false;
            }
        }
        // 检查是否有size的合法性，没有size返回错误
        List<Map<String,Object>> sizePrice = (List<Map<String, Object>>) map.get("sizePrice");
        for(Map<String,Object> e:sizePrice){
            if (e.size()!=2){return new Result(ResultConstant.CODE_FAILED,"illegal size and price",null);}
            if (e.get("size")==null||e.get("price")==null){return new Result(ResultConstant.CODE_FAILED,"size or price is missing",null);}
        }
        int res = dishService.insert(dish);
        if (res<1){return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);}
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
        //Map<String,Integer> sizePrice = null;

    }
    @GetMapping("/dish/modify")
    public String modifyDish(int dishId, Model model) throws JsonProcessingException {
        DishExample dishExample = new DishExample();
        dishExample.or().andIdEqualTo(dishId).andDeletedEqualTo(0);
        Dish dish = dishService.selectFirstByExample(dishExample);
        if (ObjectUtils.isEmpty(dish)){
            return "404";
        }
        List<Map<String,Object>> sizePrice = null;
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andDeletedEqualTo(0);
        ToppingExample toppingExample = new ToppingExample();
        toppingExample.or().andDeletedEqualTo(0);
        List<Category> categoryList = categoryService.selectByExample(categoryExample);
        List<Topping> toppingList = toppingService.selectByExample(toppingExample);
        ObjectMapper objectMapper = new ObjectMapper();
        sizePrice = objectMapper.readValue(dish.getSizePrice(),List.class);
        List<String> toppings = objectMapper.readValue(dish.getTopping(),List.class);
        model.addAttribute("dish",dish);
        model.addAttribute("sizePrice",sizePrice);
        model.addAttribute("toppings",toppings);
        model.addAttribute("categories",categoryList);
        model.addAttribute("allToppings",toppingList);
        return "admin/dishModify";
    }

    @PostMapping("/dish/modify")
    @ResponseBody
    public Result modifyDish(@RequestBody Map<String,Object> map,int dishId){
        Dish oldDish = dishService.selectByPrimaryKey(dishId);
        if (ObjectUtils.isEmpty(oldDish)||oldDish.getDeleted()==1){
            return new Result(ResultConstant.CODE_FAILED,"Such dish doesn't exist",null);
        }
        Dish newDish = null;
        try {
            newDish = Dish.map2Dish(map);
        } catch (JsonProcessingException | ClassCastException e) {
            e.printStackTrace();
            return new Result(ResultConstant.CODE_FAILED,"params is illegal",null);
        }
        if (ObjectUtils.isEmpty(newDish)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        newDish.setId(oldDish.getId());
        newDish.setDeleted(oldDish.getDeleted());
        if (!newDish.getDishName().equals(oldDish.getDishName())){
            DishExample dishExample = new DishExample();
            dishExample.or().andDeletedEqualTo(0).andDishNameEqualTo(newDish.getDishName());
            if (dishService.countByExample(dishExample)!=0){return new Result(ResultConstant.CODE_FAILED,"Dish name has exist",null);}
        }
        if (!newDish.getCategoryName().equals(oldDish.getCategoryName())){
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.or().andDeletedEqualTo(0).andCategoryNameEqualTo(newDish.getCategoryName());
            if (categoryService.countByExample(categoryExample)==0){
                return new Result(ResultConstant.CODE_FAILED,"Such category doesn't exist",null);
            }
        }
        if (!newDish.getTopping().equals(oldDish.getTopping())){
            List<String> toppings = (List<String>) map.get("toppings");
            if (toppings!=null){
                ToppingExample toppingExample = new ToppingExample();
                toppingExample.or().andDeletedEqualTo(0);
                List<Topping> toppingList = toppingService.selectByExample(toppingExample);
                boolean exist = false;
                for(String s:toppings){
                    for(Topping t:toppingList){
                        if (s.equals(t.getToppingName())){
                            exist = true;
                            break;
                        }
                    }
                    if (!exist){return new Result(ResultConstant.CODE_FAILED,"topping doesn't exist",null);}
                    exist = false;
                }
            }
        }
        // 检查是否有size的合法性，没有size返回错误
        if (!newDish.getSizePrice().equals(oldDish.getSizePrice())){
            List<Map<String,Object>> sizePrice = (List<Map<String, Object>>) map.get("sizePrice");
            for(Map<String,Object> e:sizePrice){
                if (e.size()!=2){return new Result(ResultConstant.CODE_FAILED,"illegal size and price",null);}
                if (e.get("size")==null||e.get("price")==null){return new Result(ResultConstant.CODE_FAILED,"size or price is missing",null);}
            }
        }

        int res = dishService.updateByPrimaryKey(newDish);
        if (res<1){return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);}
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);

    }

    @PostMapping("/dish/delete")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result deleteDish(@RequestBody List<Integer> ids){
        if (ObjectUtils.isEmpty(ids)){
            return new Result(ResultConstant.CODE_FAILED,"Empty ids",null);
        }
        DishExample dishExample = new DishExample();
        dishExample.or().andDeletedEqualTo(0).andIdIn(ids);
        List<Dish> dishes = dishService.selectByExample(dishExample);
        if (ObjectUtils.isEmpty(dishes)||dishes.size()!=ids.size()){
            return new Result(ResultConstant.CODE_FAILED,"One or more than one dishes don't exist",null);
        }
        Dish dish = new Dish();
        dish.setDeleted(1);
        int res = dishService.updateByExampleSelective(dish,dishExample);
        if (res!=ids.size()){
            throw new CURDException();
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
    @PostMapping("/dish/changeState")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result changeDishState(@RequestBody Map<String,Object> map){
        if (ObjectUtils.isEmpty(map.get("ids"))||ObjectUtils.isEmpty(map.get("state"))||!(map.get("ids") instanceof List)||!((map.get("state")) instanceof Integer)){
            return new Result(ResultConstant.CODE_FAILED,ErrorConstant.PARAM_MISSING,null);
        }
        DishExample dishExample = new DishExample();
        List<Integer> ids = (List<Integer>) map.get("ids");
        dishExample.or().andDeletedEqualTo(0).andIdIn(ids);
        List<Dish> dishList = dishService.selectByExample(dishExample);
        if (ObjectUtils.isEmpty(dishList)||ids.size()!=dishList.size()){
            return new Result(ResultConstant.CODE_FAILED,"One or more than one dishes don't find",null);
        }
        Dish dish = new Dish();
        dish.setState((Integer) map.get("state"));
        int res = dishService.updateByExampleSelective(dish,dishExample);
        if (res!=ids.size()){
            throw new CURDException();
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }
}
