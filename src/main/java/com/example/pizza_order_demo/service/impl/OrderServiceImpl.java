package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.OrderMapper;
import com.example.pizza_order_demo.model.Order;
import com.example.pizza_order_demo.model.OrderExample;
import com.example.pizza_order_demo.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order, OrderExample> implements OrderService {
}
