package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.service.UserAddressService;
import com.example.pizza_order_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAddressController {

    @Autowired
    UserService userService;
    @Autowired
    UserAddressService userAddressService;

}
