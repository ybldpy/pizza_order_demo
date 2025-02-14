package com.example.pizza_order_demo.service.impl;


import com.example.pizza_order_demo.mapper.CommentMapper;
import com.example.pizza_order_demo.model.Comment;
import com.example.pizza_order_demo.model.CommentExample;
import com.example.pizza_order_demo.service.BaseService;
import com.example.pizza_order_demo.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper,Comment, CommentExample> implements CommentService{
}
