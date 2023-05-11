package com.example.pizza_order_demo.controller;

import com.alibaba.excel.EasyExcel;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.OrderDetailService;
import com.example.pizza_order_demo.service.OrderService;
import com.example.pizza_order_demo.service.PaymentService;
import com.example.pizza_order_demo.utils.OrderUtil;
import com.example.pizza_order_demo.utils.TimeUtils;
import javafx.util.Pair;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@PreAuthorize("hasRole('admin')")
public class StatisticController {


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    private static final int[] COST = {1000,1200,900,943,1250,1222,1323};
    private static final long DAY = 24L*60*60*1000;

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

    private Comparator<String> stringComparator = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            long diff = Date.valueOf(o1).getTime()-Date.valueOf(o2).getTime();
            return diff>0?1:diff==0?0:-1;
        }
    };

    @GetMapping("/statistic/cost")
    @ResponseBody
    public Object getCost(){

        long cur = System.currentTimeMillis();
        String curStr = TimeUtils.translateTimeToString(cur,"yyyy-MM-dd");
        cur = Date.valueOf(curStr).getTime();

        long weekAgo = cur-6*DAY;
        List<Pair<String,Integer>> pairList = new ArrayList<>(7);
        for(int i=0;i<7;i++){
            pairList.add(new Pair<>(TimeUtils.translateTimeToString(weekAgo+i*DAY,"yyyy-MM-dd"),COST[i]));
        }
        return pairList;
    }
    @GetMapping("/statistic/cost/download")
    public void downloadCostHistory(HttpServletResponse response) throws IOException {
        long cur = Date.valueOf(TimeUtils.translateTimeToString(System.currentTimeMillis(),"yyyy-MM-dd")).getTime();
        List<Cost> costs = new ArrayList<>(31);
        for(int i=0;i<7;i++){
            costs.add(new Cost(TimeUtils.translateTimeToString(cur-i*DAY,"yyyy-MM-dd"),COST[COST.length-i-1]));
        }
        Random random = new Random();
        for(int i = 7;i<32;i++){
            costs.add(new Cost(TimeUtils.translateTimeToString(cur-i*DAY,"yyyy-MM-dd"),random.nextInt(800)+1500));
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=cost.xlsx");
        EasyExcel.write(response.getOutputStream(), Cost.class).sheet("cost").doWrite(costs);
    }

    @GetMapping("/statistic/profit/download")
    public void downloadProfit(HttpServletResponse response) throws IOException {
        long mockStart = Date.valueOf("2023-3-1").getTime();
        Random random = new Random();
        List<Profit> profitList = new ArrayList<>();
        for(int i=0;i<31;i++){
            profitList.add(new Profit(TimeUtils.translateTimeToString(mockStart+i*DAY,"yyyy-MM-dd"),random.nextInt(900)+1576));
        }
        PaymentExample paymentExample = new PaymentExample();
        List<Payment> paymentList = paymentService.selectByExample(paymentExample);
        Map<String,Integer> profitCount = new TreeMap<>(stringComparator);
        for(Payment payment:paymentList){
            String date = TimeUtils.translateTimeToString(payment.getCreateTime(),null);
            if (profitCount.containsKey(date)){
                int count = profitCount.get(date)+payment.getPrice();
                profitCount.put(date,count);
            }
            else {
                profitCount.put(date,payment.getPrice());
            }
        }
        for(Map.Entry<String,Integer> entry:profitCount.entrySet()){
            profitList.add(new Profit(entry.getKey(),entry.getValue()));
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=profit.xlsx");
        EasyExcel.write(response.getOutputStream(), Profit.class).sheet("profit").doWrite(profitList);

    }


    @GetMapping("/statistic/dish/download")
    public void downloadFood(HttpServletResponse response) throws IOException {
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        Map<String,Integer> foodCount = new HashMap<>();
        for(OrderDetail orderDetail:orderDetailList){
            if (foodCount.containsKey(orderDetail.getDishName())){
                foodCount.put(orderDetail.getDishName(),foodCount.get(orderDetail.getDishName())+orderDetail.getAmount());
            }
            else {
                foodCount.put(orderDetail.getDishName(),orderDetail.getAmount());
            }
        }
        List<DishSale> dishSaleList = new ArrayList<>(foodCount.size()+1);
        for(Map.Entry<String,Integer> entry:foodCount.entrySet()){
            dishSaleList.add(new DishSale(entry.getKey(),entry.getValue()));
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=sales.xlsx");
        EasyExcel.write(response.getOutputStream(), DishSale.class).sheet("sales").doWrite(dishSaleList);



    }



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
        long day = 24*60*60*1000;
        cur = Date.valueOf(curStr).getTime();
        long severnDaysAgo = cur-6*24*60*60*1000;
        PaymentExample paymentExample = new PaymentExample();
        paymentExample.or().andCreateTimeBetween(severnDaysAgo,cur+day-1);
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
