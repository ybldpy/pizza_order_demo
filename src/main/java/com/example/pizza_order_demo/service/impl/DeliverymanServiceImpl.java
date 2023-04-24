package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.DeliverymanMapper;
import com.example.pizza_order_demo.model.Deliveryman;
import com.example.pizza_order_demo.model.DeliverymanExample;
import com.example.pizza_order_demo.service.DeliverymanService;
import org.springframework.stereotype.Service;

@Service
public class DeliverymanServiceImpl extends BaseServiceImpl<DeliverymanMapper, Deliveryman, DeliverymanExample> implements DeliverymanService {
}
