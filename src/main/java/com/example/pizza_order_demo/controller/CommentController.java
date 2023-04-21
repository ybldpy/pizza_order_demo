//package com.example.pizza_order_demo.controller;
//
//import com.example.pizza_order_demo.commons.Result;
//import com.example.pizza_order_demo.commons.constant.ErrorConstant;
//import com.example.pizza_order_demo.commons.constant.ResultConstant;
//import com.example.pizza_order_demo.model.CategoryExample;
//import com.example.pizza_order_demo.model.Comment;
//import com.example.pizza_order_demo.model.CommentExample;
//import com.example.pizza_order_demo.model.OrderExample;
//import com.example.pizza_order_demo.service.CommentService;
//import com.example.pizza_order_demo.service.OrderService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class CommentController {
//
//    @Autowired
//    private CommentService commentService;
//    @Autowired
//    private OrderService orderService;
//
//    @PostMapping("/Order/PostprandialEvaluation")
//    @ResponseBody
//    public Object getCommentData(@RequestBody Map<String,Object> data) throws JsonProcessingException {
//        OrderExample orderExample = new OrderExample();
//        Integer oid = (Integer) data.get("orderid");
//        if (ObjectUtils.isEmpty(oid)){
//            return new Result(ResultConstant.CODE_FAILED,"order not found",null);
//        }
//        orderExample.or().andIdEqualTo(oid);
//        int count = orderService.countByExample(orderExample);
//        if (count<1){
//            return new Result(ResultConstant.CODE_FAILED,"order not found",null);
//        }
//        List remark = (List) data.get("remark");
//        if (ObjectUtils.isEmpty(remark)){
//            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.USER_REGISTER_MISSING_PARAM,null);
//        }
//        Comment comment = new Comment();
//        comment.setOrderId(oid);
//        comment.setCreateTime(System.currentTimeMillis());
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonStr = objectMapper.writeValueAsString(remark);
//        comment.setComment(jsonStr);
//        int res = commentService.insert(comment);
//        if (res<1){
//            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
//        }
//        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
//
//
//    }
//
//}
