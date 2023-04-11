package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.DishMapper;
import com.example.pizza_order_demo.model.Dish;
import com.example.pizza_order_demo.model.DishExample;
import com.example.pizza_order_demo.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends BaseServiceImpl<DishMapper,Dish, DishExample> implements DishService {
}
