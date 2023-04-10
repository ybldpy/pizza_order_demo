package com.example.pizza_order_demo.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayDeque;
import java.util.Queue;

@Controller
public class CategoryController {


    @GetMapping("/category/management")
    @PreAuthorize("hasRole('admin')")
    public String categoryManagement(){
        return "admin/categoryManagement";
    }
}
