package com.example.pizza_order_demo.service.impl;

import com.example.pizza_order_demo.mapper.FlavourMapper;
import com.example.pizza_order_demo.model.Flavour;
import com.example.pizza_order_demo.model.FlavourExample;
import com.example.pizza_order_demo.service.FlavourService;
import org.springframework.stereotype.Service;

@Service
public class FlavourServiceImpl extends BaseServiceImpl<FlavourMapper, Flavour, FlavourExample> implements FlavourService {
}
