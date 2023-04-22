package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.PaymentMapper;
import com.example.pizza_order_demo.model.Payment;
import com.example.pizza_order_demo.model.PaymentExample;
import com.example.pizza_order_demo.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends BaseServiceImpl<PaymentMapper, Payment, PaymentExample> implements PaymentService {
}
