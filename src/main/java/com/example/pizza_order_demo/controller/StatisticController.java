package com.example.pizza_order_demo.controller;

import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.OrderDetailService;
import com.example.pizza_order_demo.service.OrderService;
import com.example.pizza_order_demo.service.PaymentService;
import com.example.pizza_order_demo.utils.OrderUtil;
import com.example.pizza_order_demo.utils.TimeUtils;
import javafx.util.Pair;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StatisticController {


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/admin/statistic")
    public String getStatistic(){
        return "admin/statistics";
    }

    private Comparator<Pair<String,Integer>> pairComparator = new Comparator<Pair<String,Integer>>() {


        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            return o2.getValue()-o1.getValue();
        }
    };



    @GetMapping("/statistic/popularFood")
    @ResponseBody
    public Object getPopularFood(){

        OrderExample orderExample = new OrderExample();
        List<Order> orderList = orderService.selectByExample(orderExample);
        List<Pair<String,Integer>> pairList = new ArrayList<>();
        if (ObjectUtils.isEmpty(orderList)||orderList.isEmpty()){
            return pairList;
        }
        List<Integer> ids = OrderUtil.getIds(orderList);
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.or().andOrderIdIn(ids);
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        if (ObjectUtils.isEmpty(orderDetailList)||orderDetailList.isEmpty()){
            return pairList;
        }
        Map<String,Integer> countMap = new HashMap<>();
        for(OrderDetail orderDetail:orderDetailList){
            Integer count = countMap.get(orderDetail.getDishName());
            if (count!=null){countMap.put(orderDetail.getDishName(),count+orderDetail.getAmount());}
            else {countMap.put(orderDetail.getDishName(),orderDetail.getAmount());}
        }
        Pair<String,Integer>[] pairs = new Pair[countMap.size()];
        int pointer = 0;
        for(Map.Entry<String,Integer> entry:countMap.entrySet()){
            pairs[pointer++] = new Pair<>(entry.getKey(),entry.getValue());
        }
        Arrays.sort(pairs,pairComparator);
        return Arrays.copyOf(pairs,Math.min(5,pairs.length));

    }

    @GetMapping("/statistic/profit")
    @ResponseBody
    public Object getProfit() {
        long cur = System.currentTimeMillis();
        String curStr = TimeUtils.translateTimeToString(cur,"yyyy-MM-dd");
        cur = Date.valueOf(curStr).getTime();
        long severnDaysAgo = cur-7*24*60*60*1000;
        long day = 24*60*60*1000;
        PaymentExample paymentExample = new PaymentExample();
        paymentExample.or().andCreateTimeBetween(severnDaysAgo,cur+day);
        List<Integer> profits = new ArrayList<>(7);
        List<Pair<String,Integer>> dateAndProfitList = new ArrayList<>(7);
        Map<String,Object> resultMap = new HashMap<>();
        List<Payment> payments= paymentService.selectByExample(paymentExample);
        if (ObjectUtils.isEmpty(payments)||payments.isEmpty()){
            resultMap.put("total",0);
            resultMap.put("rows",new ArrayList<>());
        }
        Payment p = null;
        int count = 0;

        for(int i=0;i<7;i++){
            count = 0;
            for(int u=0;u<payments.size();u++){
                p = payments.get(u);
                if (p.getCreateTime()>=severnDaysAgo+(i*day)&&p.getCreateTime()<severnDaysAgo+(i+1)*day){
                    count+=p.getPrice();
                }
            }
            dateAndProfitList.add(new Pair<>(TimeUtils.translateTimeToString(severnDaysAgo+i*day,"yyyy-MM-dd"),count));
        }
        resultMap.put("total",7);
        resultMap.put("data",dateAndProfitList);
        return resultMap;
    }
}
