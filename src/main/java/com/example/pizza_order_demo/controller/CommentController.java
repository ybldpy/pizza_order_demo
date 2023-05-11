package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.component.SystemControlComponent;
import com.example.pizza_order_demo.model.Comment;
import com.example.pizza_order_demo.model.CommentExample;
import com.example.pizza_order_demo.model.OrderExample;
import com.example.pizza_order_demo.service.CommentService;
import com.example.pizza_order_demo.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SystemControlComponent systemControlComponent;

    @GetMapping("/comment/doComment")
    public String comment(int orderId, Model model, Authentication authentication){
        if (systemControlComponent.closed()){
            return "forward:/closed.html";
        }
        model.addAttribute("orderId",orderId);
        model.addAttribute("userName",((UserDetails)authentication.getPrincipal()).getUsername());
        return "Order/PostprandialEvaluation";
    }

    @GetMapping("/comment/result")
    public String getCommentData(int orderId,Model model){
        CommentExample commentExample = new CommentExample();
        commentExample.or().andOrderIdEqualTo(orderId);
        Comment comment = commentService.selectFirstByExample(commentExample);
        if (ObjectUtils.isEmpty(comment)){return "404";}
        model.addAttribute("commentData",comment.getRemark());
        return "Order/Evaluation";
    }

    @PostMapping("/comment/doComment")
    @ResponseBody
    public Object insertCommentData(@RequestBody Map<String,Object> data) throws JsonProcessingException {
        OrderExample orderExample = new OrderExample();
        Integer oid = (Integer) data.get("orderId");
        if (ObjectUtils.isEmpty(oid)){
            return new Result(ResultConstant.CODE_FAILED,"order not found",null);
        }
        orderExample.or().andIdEqualTo(oid);
        int count = orderService.countByExample(orderExample);
        if (count<1){
            return new Result(ResultConstant.CODE_FAILED,"order not found",null);
        }
        CommentExample commentExample = new CommentExample();
        commentExample.or().andOrderIdEqualTo(oid);
        if (commentService.countByExample(commentExample)>0){
            return new Result(ResultConstant.CODE_FAILED,"this order has been evaluated",null);
        }
        List remark = (List) data.get("remark");
        if (ObjectUtils.isEmpty(remark)){
            return new Result(ResultConstant.CODE_FAILED,"param is missing",null);
        }
        Comment comment = new Comment();
        comment.setOrderId(oid);
        comment.setCreateTime(System.currentTimeMillis());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(remark);
        comment.setRemark(jsonStr);
        int res = commentService.insert(comment);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);


    }

}
