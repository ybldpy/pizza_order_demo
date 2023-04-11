package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.UserAddressMapper;
import com.example.pizza_order_demo.model.UserAddress;
import com.example.pizza_order_demo.model.UserAddressExample;
import com.example.pizza_order_demo.service.UserAddressService;
import com.example.pizza_order_demo.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl extends BaseServiceImpl<UserAddressMapper, UserAddress, UserAddressExample> implements UserAddressService {
}
