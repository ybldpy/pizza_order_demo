package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.OrderDetailMapper;
import com.example.pizza_order_demo.mapper.OrderMapper;
import com.example.pizza_order_demo.model.OrderDetail;
import com.example.pizza_order_demo.model.OrderDetailExample;
import com.example.pizza_order_demo.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetailMapper, OrderDetail, OrderDetailExample> implements OrderDetailService {
}
