package com.example.pizza_order_demo.service;


import com.example.pizza_order_demo.model.User;

import com.example.pizza_order_demo.model.UserExample;

import javax.jws.soap.SOAPBinding;


public interface UserService extends BaseService<User, UserExample>{

    public void test();

}
