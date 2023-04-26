package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.LogMapper;
import com.example.pizza_order_demo.model.Log;
import com.example.pizza_order_demo.model.LogExample;
import com.example.pizza_order_demo.service.LogService;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends BaseServiceImpl<LogMapper, Log, LogExample> implements LogService {

}
