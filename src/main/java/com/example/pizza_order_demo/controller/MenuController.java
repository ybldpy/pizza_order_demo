package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.CategoryService;
import com.example.pizza_order_demo.service.DishService;
import com.example.pizza_order_demo.service.ToppingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {

    @Autowired
    private ToppingService toppingService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/menu")
    public String getMenu(Model model,int deliveryType) throws JsonProcessingException {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.or().andDeletedEqualTo(0);
        List<Category> categoryList = categoryService.selectByExample(categoryExample);
        ToppingExample toppingExample = new ToppingExample();
        toppingExample.or().andDeletedEqualTo(0);
        List<Topping> toppings = toppingService.selectByExample(toppingExample);
        String str = new ObjectMapper().writeValueAsString(toppings);
        model.addAttribute("toppings",str);
        model.addAttribute("categories",categoryList);
        model.addAttribute("deliveryType",deliveryType);
        return "shopping/menu";
    }
}
