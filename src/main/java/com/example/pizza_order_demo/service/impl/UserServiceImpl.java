package com.example.pizza_order_demo.service.impl;


import com.example.pizza_order_demo.mapper.UserMapper;
import com.example.pizza_order_demo.model.User;
import com.example.pizza_order_demo.model.UserExample;
import com.example.pizza_order_demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper,User, UserExample> implements UserService{

    @Override
    @Transactional
    public void test() {
        User user = new User();
        user.setUsername(String.valueOf(System.currentTimeMillis()));
        user.setPwd("122222222222222");
        user.setMail("!23");
        insert(user);
        throw new RuntimeException();
    }
}
