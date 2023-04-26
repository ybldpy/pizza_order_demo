package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.model.Payment;
import com.example.pizza_order_demo.model.PaymentExample;
import com.example.pizza_order_demo.service.OrderService;
import com.example.pizza_order_demo.service.PaymentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;




}
