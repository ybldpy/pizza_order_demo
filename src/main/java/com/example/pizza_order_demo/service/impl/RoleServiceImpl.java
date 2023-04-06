package com.example.pizza_order_demo.service.impl;


import com.example.pizza_order_demo.mapper.RoleMapper;
import com.example.pizza_order_demo.model.Role;
import com.example.pizza_order_demo.model.RoleExample;
import com.example.pizza_order_demo.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role, RoleExample> implements RoleService {
}
