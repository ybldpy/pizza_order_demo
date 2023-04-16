package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.ToppingMapper;
import com.example.pizza_order_demo.model.Topping;
import com.example.pizza_order_demo.model.ToppingExample;
import com.example.pizza_order_demo.service.ToppingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToppingServiceImpl extends BaseServiceImpl<ToppingMapper, Topping, ToppingExample> implements ToppingService {
}
