package com.example.pizza_order_demo.utils;

import com.example.pizza_order_demo.model.Order;
import com.example.pizza_order_demo.model.OrderDetail;

import java.util.*;

public class OrderUtil {


    public static List<Integer> getIds(List<Order> orders){
        List<Integer> ids = new ArrayList<>(orders.size());
        for(Order order:orders){
            ids.add(order.getId());
        }
        return ids;
    }

    public static List<String> getOrderDetailDish(List<OrderDetail> orderDetailList){
        Set<String> dishes = new HashSet<>();
        for(int i=0;i<orderDetailList.size();i++){
            dishes.add(orderDetailList.get(i).getDishName());
        }
        List<String> dishStrList = new ArrayList<>(dishes.size());
        for(String s:dishes){
            dishStrList.add(s);
        }
        return dishStrList;
    }

}
