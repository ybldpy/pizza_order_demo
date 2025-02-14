package com.example.pizza_order_demo.controller;


import com.example.pizza_order_demo.commons.Result;
import com.example.pizza_order_demo.commons.constant.ErrorConstant;
import com.example.pizza_order_demo.commons.constant.ResultConstant;
import com.example.pizza_order_demo.component.SystemControlComponent;
import com.example.pizza_order_demo.exception.CURDException;
import com.example.pizza_order_demo.model.*;
import com.example.pizza_order_demo.service.*;
import com.example.pizza_order_demo.utils.OrderUtil;
import com.example.pizza_order_demo.utils.QRCodeUtil;
import com.example.pizza_order_demo.utils.TimeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.extras.springsecurity5.dialect.processor.AuthorizeUrlAttrProcessor;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private DeliverymanService deliverymanService;
    @Autowired
    private UserAddressService userAddressService;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private SystemControlComponent systemControlComponent;

    @Autowired
    private DishService dishService;


    @GetMapping("/order/getOne")
    @ResponseBody
    public Result getOrder(int orderId){
        if (orderId<0){
            return new Result(ResultConstant.CODE_FAILED,"param is wrong",null);
        }

        Order order = orderService.selectByPrimaryKey(orderId);
        if (ObjectUtils.isEmpty(order)){
            return new Result(ResultConstant.CODE_FAILED,"order doesn't exist",null);
        }
        Result result = new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,order);
        return result;
    }


    /**
     *
     * @param orderId id of order
     * @return if success then returning success result, otherwise return failed result
     * @throws NotFoundException
     *
     * set delivering order state to finished state
     */
    @GetMapping("/order/confirmDelivery")
    @ResponseBody
    @PreAuthorize("hasRole('admin')")
    public Result ConfirmOrderDelivery(int orderId) throws NotFoundException {
        /**
         * check if order exist
         */
        Order order = orderService.selectByPrimaryKey(orderId);
        if (ObjectUtils.isEmpty(order)){
            throw new NotFoundException("order not find");
        }
        /**
         * check if type of order is delivery
         */
        if (order.getType()!=1){
            return new Result(ResultConstant.CODE_FAILED,"this order is not delivery",null);
        }
        /**
         * check if dishes in the order have finished
         */
        if (order.getState()<1){
            return new Result(ResultConstant.CODE_FAILED,"order hasn't finished yet",null);
        }
        /**
         * if above step passes, delivering order can be set finished
         */
        order.setState(2);
        /**
         * check if update success.
         */
        int res = orderService.updateByPrimaryKey(order);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);


    }

    @GetMapping("/order/todo")
    @PreAuthorize("hasRole('admin')")
    public String orderTodo(){
        return "admin/orderTodo";
    }
    @GetMapping("/order/toDelivery")
    @PreAuthorize("hasRole('admin')")
    public String orderToDelivery(){
        return "admin/orderToDelivery";
    }
    @GetMapping("/order/history")
    @PreAuthorize("hasRole('admin')")
    public String orderHistory(){
        return "admin/orderHistory";
    }
    @GetMapping("/order/info")
    public String orderInfo(int orderId,Model model,Authentication authentication){
        model.addAttribute("orderId",orderId);
        String curUser = "admin";
        if (authentication!=null){
            curUser = authentication.getName();
        }
        model.addAttribute("userName",curUser);
        return "Order/OrderDetail";
    }
    @GetMapping("/order/all")
    public String allOrder(Model model,Authentication authentication){
        String curUser = "admin";
        if (authentication!=null){
            curUser = ((UserDetails)authentication.getPrincipal()).getUsername();
        }
        model.addAttribute("userName",curUser);
        if (systemControlComponent.closed()){
            return "forward:/closed.html";
        }
        return "Order/All orders";
    }



    @GetMapping("/order/detail/finish")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    @PreAuthorize("hasRole('admin')")
    public Result finishDetail(int orderDetailId) throws NotFoundException {
        OrderDetail orderDetail = orderDetailService.selectByPrimaryKey(orderDetailId);
        if (ObjectUtils.isEmpty(orderDetail)){
            return new Result(ResultConstant.CODE_FAILED,"order detail doesn't find",null);
        }

        orderDetail.setState(1);
        int res = orderDetailService.updateByPrimaryKey(orderDetail);
        if (res<1){
            return new Result(ResultConstant.CODE_FAILED,"Finish failed",null);
        }
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.or().andOrderIdEqualTo(orderDetail.getOrderId());
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        boolean flag = true;
        for(OrderDetail e:orderDetailList){
            if (e.getState()!=1){
                flag = false;
                break;
            }
        }
        Order order = orderService.selectByPrimaryKey(orderDetail.getOrderId());
        if (ObjectUtils.isEmpty(order)){
            throw new NotFoundException("order not find");
        }
        if (flag){
            order.setId(orderDetail.getOrderId());
            order.setState(1);
            if (order.getType()==0){
                order.setFinishedTime(System.currentTimeMillis());
            }
            res = orderService.updateByPrimaryKey(order);
            if (res<1){
                throw new CURDException();
            }
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
    }

    @GetMapping("/order/management/all")
    public String getAllManagedOrder(){
        return "admin/orders";
    }

    @GetMapping("/order/toDelivery/orders")
    @ResponseBody
    @PreAuthorize("hasRole('admin')")
    public Object toDeliveryOrders(){
        OrderExample orderExample = new OrderExample();
        orderExample.or().andStateEqualTo(1).andTypeEqualTo(1);
        Map<String,Object> resultMap = new HashMap<>();
        List<Order> orders = orderService.selectByExample(orderExample);
        List<Integer> orderIds = null;
        if (!ObjectUtils.isEmpty(orders)||orders.isEmpty()){
            orderIds = OrderUtil.getIds(orders);
        }
        else {
            resultMap.put("total",0);
            resultMap.put("rows",new ArrayList<>());
            return resultMap;
        }

        List<OrderDTO> orderDTOList = new ArrayList<>(orders.size());
        for(int i=0;i<orders.size();i++){
            int price = 0;
            int quantity = 0;
            OrderDTO orderDTO = new OrderDTO(orders.get(i));
            if (!ObjectUtils.isEmpty(orderDTO.getDeliverymanId())){
                orderDTO.setDeliveryman(deliverymanService.selectByPrimaryKey(orderDTO.getDeliverymanId()));
            }
            if (!ObjectUtils.isEmpty(orderDTO.getUserAddressId())){
                orderDTO.setUserAddress(userAddressService.selectByPrimaryKey(orderDTO.getUserAddressId()));
            }
            orderDTO.setPrice(price);
            orderDTO.setQuantity(quantity);
            orderDTO.setCreateTimeStr(TimeUtils.translateTimeToString(orderDTO.getCreateTime()));
            orderDTO.setPickupTimeStr(TimeUtils.translateTimeToString(orderDTO.getPickupTime()));
            if (!ObjectUtils.isEmpty(orderDTO.getFinishedTime())){
                orderDTO.setFinishedTimeStr(TimeUtils.translateTimeToString(orderDTO.getFinishedTime()));
            }
            orderDTOList.add(orderDTO);
        }

        resultMap.put("total",orderDTOList.size());
        resultMap.put("rows",orderDTOList);
        return resultMap;
    }


    @GetMapping("/order/history/orders")
    @ResponseBody
    @PreAuthorize("hasRole('admin')")
    public Object historyOrders(){
        OrderExample orderExample = new OrderExample();
        orderExample.or().andStateEqualTo(1).andTypeEqualTo(0);
        orderExample.or().andStateEqualTo(2).andTypeEqualTo(1);
        Map<String,Object> resultMap = new HashMap<>();
        List<Order> orders = orderService.selectByExample(orderExample);
        List<Integer> orderIds = null;
        if (!ObjectUtils.isEmpty(orders)||orders.isEmpty()){
            orderIds = OrderUtil.getIds(orders);
        }
        else {
            resultMap.put("total",0);
            resultMap.put("rows",new ArrayList<>());
            return resultMap;
        }

        List<OrderDTO> orderDTOList = new ArrayList<>(orders.size());
        for(int i=0;i<orders.size();i++){
            int price = 0;
            int quantity = 0;
            OrderDTO orderDTO = new OrderDTO(orders.get(i));
            if (!ObjectUtils.isEmpty(orderDTO.getDeliverymanId())){
                orderDTO.setDeliveryman(deliverymanService.selectByPrimaryKey(orderDTO.getDeliverymanId()));
            }
            if (!ObjectUtils.isEmpty(orderDTO.getUserAddressId())){
                orderDTO.setUserAddress(userAddressService.selectByPrimaryKey(orderDTO.getUserAddressId()));
            }
            orderDTO.setPrice(price);
            orderDTO.setQuantity(quantity);
            orderDTO.setCreateTimeStr(TimeUtils.translateTimeToString(orderDTO.getCreateTime()));
            orderDTO.setPickupTimeStr(TimeUtils.translateTimeToString(orderDTO.getPickupTime()));
            if (!ObjectUtils.isEmpty(orderDTO.getFinishedTime())){
                orderDTO.setFinishedTimeStr(TimeUtils.translateTimeToString(orderDTO.getFinishedTime()));
            }
            orderDTOList.add(orderDTO);
        }

        resultMap.put("total",orderDTOList.size());
        resultMap.put("rows",orderDTOList);
        return resultMap;
    }

    @GetMapping("/order/todo/orders")
    @ResponseBody
    @PreAuthorize("hasRole('admin')")
    public Object todoOrders(){
        OrderExample orderExample = new OrderExample();
        orderExample.or().andStateEqualTo(0).andPaidEqualTo(1);
        Map<String,Object> resultMap = new HashMap<>();
        List<Order> orders = orderService.selectByExample(orderExample);
        List<Integer> orderIds = null;
        if (!ObjectUtils.isEmpty(orders)||orders.isEmpty()){
            orderIds = OrderUtil.getIds(orders);
        }
        else {
            resultMap.put("total",0);
            resultMap.put("rows",new ArrayList<>());
            return resultMap;
        }

        List<OrderDTO> orderDTOList = new ArrayList<>(orders.size());
        for(int i=0;i<orders.size();i++){
            int price = 0;
            int quantity = 0;
            OrderDTO orderDTO = new OrderDTO(orders.get(i));
            if (!ObjectUtils.isEmpty(orderDTO.getDeliverymanId())){
                orderDTO.setDeliveryman(deliverymanService.selectByPrimaryKey(orderDTO.getDeliverymanId()));
            }
            if (!ObjectUtils.isEmpty(orderDTO.getUserAddressId())){
                orderDTO.setUserAddress(userAddressService.selectByPrimaryKey(orderDTO.getUserAddressId()));
            }
            orderDTO.setPrice(price);
            orderDTO.setQuantity(quantity);
            orderDTO.setCreateTimeStr(TimeUtils.translateTimeToString(orderDTO.getCreateTime()));
            orderDTO.setPickupTimeStr(TimeUtils.translateTimeToString(orderDTO.getPickupTime()));
            if (!ObjectUtils.isEmpty(orderDTO.getFinishedTime())){
                orderDTO.setFinishedTimeStr(TimeUtils.translateTimeToString(orderDTO.getFinishedTime()));
            }
            orderDTOList.add(orderDTO);
        }

        resultMap.put("total",orderDTOList.size());
        resultMap.put("rows",orderDTOList);
        return resultMap;
    }

    @GetMapping("/order/management/get")
    @ResponseBody
    public Object manageOrders(){
        OrderExample orderExample = new OrderExample();
        Map<String,Object> resultMap = new HashMap<>();
        List<Order> orders = orderService.selectByExample(orderExample);
        List<Integer> orderIds = null;
        if (!ObjectUtils.isEmpty(orders)||orders.isEmpty()){
            orderIds = OrderUtil.getIds(orders);
        }
        else {
            resultMap.put("total",0);
            return resultMap;
        }

        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.or().andOrderIdIn(orderIds);
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        if (ObjectUtils.isEmpty(orderDetailList)||orderDetailList.isEmpty()){
            resultMap.put("total",0);
            return resultMap;
        }
        List<OrderDTO> orderDTOList = new ArrayList<>(orders.size());
        for(int i=0;i<orders.size();i++){
            int price = 0;
            int quantity = 0;
            OrderDTO orderDTO = new OrderDTO(orders.get(i));
            if (!ObjectUtils.isEmpty(orderDTO.getDeliverymanId())){
                orderDTO.setDeliveryman(deliverymanService.selectByPrimaryKey(orderDTO.getDeliverymanId()));
            }
            if (!ObjectUtils.isEmpty(orderDTO.getUserAddressId())){
                orderDTO.setUserAddress(userAddressService.selectByPrimaryKey(orderDTO.getUserAddressId()));
            }
            orderDTO.setPrice(price);
            orderDTO.setQuantity(quantity);
            orderDTO.setCreateTimeStr(TimeUtils.translateTimeToString(orderDTO.getCreateTime()));
            orderDTO.setPickupTimeStr(TimeUtils.translateTimeToString(orderDTO.getPickupTime()));
            if (!ObjectUtils.isEmpty(orderDTO.getFinishedTime())){
                orderDTO.setFinishedTimeStr(TimeUtils.translateTimeToString(orderDTO.getFinishedTime()));
            }
            orderDTOList.add(orderDTO);
        }

        resultMap.put("total",orderDTOList.size());
        resultMap.put("rows",orderDTOList);
        return resultMap;
    }

    @GetMapping("/order/get")
    @ResponseBody
    public Object getOrder(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String curUser = principal instanceof String?"anonymousUser":((UserDetailsImpl)principal).getUsername();
        OrderExample orderExample = new OrderExample();
        orderExample.or().andUserNameEqualTo(curUser);
        Map<String,Object> resultMap = new HashMap<>();
        List<Order> orders = orderService.selectByExample(orderExample);
        List<Integer> orderIds = null;
        if (!ObjectUtils.isEmpty(orders)||orders.isEmpty()){
            orderIds = OrderUtil.getIds(orders);
        }
        else {
            resultMap.put("total",0);
            return resultMap;
        }

        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.or().andOrderIdIn(orderIds);
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        if (ObjectUtils.isEmpty(orderDetailList)||orderDetailList.isEmpty()){
            resultMap.put("total",0);
            return resultMap;
        }
        List<OrderDTO> orderDTOList = new ArrayList<>(orders.size());
        for(int i=0;i<orders.size();i++){
            int price = 0;
            int quantity = 0;
            OrderDTO orderDTO = new OrderDTO(orders.get(i));
            if (!ObjectUtils.isEmpty(orderDTO.getDeliverymanId())){
                orderDTO.setDeliveryman(deliverymanService.selectByPrimaryKey(orderDTO.getDeliverymanId()));
            }
            String[] imgs = new String[2];
            int count = 0;
            // 获取前两张图片
            for(int u=0;u<orderDetailList.size();u++){
                if (orderDetailList.get(u).getOrderId()!=orders.get(i).getId()){continue;}
                price+=orderDetailList.get(u).getTotalPrice();
                quantity+=orderDetailList.get(u).getAmount();
                if (count<2){
                    imgs[count++] = orderDetailList.get(u).getImgs();
                }
            }
            orderDTO.setPrice(price);
            orderDTO.setQuantity(quantity);
            orderDTO.setImgs(imgs);
            orderDTO.setCreateTimeStr(TimeUtils.translateTimeToString(orderDTO.getCreateTime()));
            orderDTO.setPickupTimeStr(TimeUtils.translateTimeToString(orderDTO.getPickupTime()));
            if (!ObjectUtils.isEmpty(orderDTO.getFinishedTime())){
                TimeUtils.translateTimeToString(orderDTO.getFinishedTime());
            }
            orderDTOList.add(orderDTO);
        }

        resultMap.put("total",orderDTOList.size());
        resultMap.put("rows",orderDTOList);
        return resultMap;
    }

    @GetMapping("/order/detail/get")
    @ResponseBody
    public Result getOrderDetail(int orderId){
        if (orderId<0){
            return new Result(ResultConstant.CODE_FAILED,"param is wrong",null);
        }
        OrderExample orderExample = new OrderExample();
        orderExample.or().andIdEqualTo(orderId);
        int count = orderService.countByExample(orderExample);
        if (count<1){return new Result(ResultConstant.CODE_FAILED,"order not exist",null);}
        OrderDetailExample orderDetailExample = new OrderDetailExample();
        orderDetailExample.or().andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.selectByExample(orderDetailExample);
        if (ObjectUtils.isEmpty(orderDetailList)||orderDetailList.isEmpty()){
            return new Result(ResultConstant.CODE_FAILED,"there is nothing in order",null);
        }
        Result result = new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,null);
        result.setData(orderDetailList);
        return result;
    }

    @GetMapping("/order/qrcode")
    public void createQrcode(int orderId, HttpServletResponse response) throws IOException {
        Order order = orderService.selectByPrimaryKey(orderId);
        String content = order!=null?("Order id: # "+order.getId()+" State: "+(order.getPaid()==0?"unpaid":order.getState()==0?"In cooking":order.getState()==1&&order.getType()==0?"Finished":order.getState()==1&&order.getType()==1?"Under delivery":"Completed delivery")):"order doesn't exist";
        response.setContentType("image/png");
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        BufferedImage bufferedImage = QRCodeUtil.generateQRCode(content,500,500);
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(bufferedImage,"png",outputStream);
    }
    @GetMapping("/order/qrcode/scan")
    public String scanQrCode(int orderId,Model model){
        model.addAttribute("orderId",orderId);
        return "Order/QRCode";
    }


    @PostMapping("/order/create")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Result createOrder(@RequestBody Map<String,Object> map,Authentication authentication) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(map.get("pickupTime"))||ObjectUtils.isEmpty(map.get("shoppingCar"))||ObjectUtils.isEmpty(map.get("deliveryType"))||ObjectUtils.isEmpty(map.get("addressId"))){
            return new Result(ResultConstant.CODE_FAILED, ErrorConstant.PARAM_MISSING,null);
        }
        int type = (Integer) map.get("deliveryType");
        int addressId = (Integer) map.get("addressId");
        long pickup = (Integer) map.get("pickupTime")*60*1000;
        UserAddressExample userAddressExample = new UserAddressExample();
        userAddressExample.or().andIdEqualTo(addressId);
        if ((type!=0&&type!=1)|| pickup<0){
            return new Result(ResultConstant.CODE_FAILED,"params is wrong",null);
        }
        List<Map<String,Object>> shoppingCar = (List<Map<String, Object>>) map.get("shoppingCar");
        long curTime = System.currentTimeMillis();
        Order newOrder = new Order();
        newOrder.setCreateTime(curTime);
        newOrder.setPickupTime(curTime+pickup);
        if (type==1){
            if(userAddressService.countByExample(userAddressExample)<1){
                return new Result(ResultConstant.CODE_FAILED,"params is wrong",null);
            }
            DeliverymanExample deliverymanExample = new DeliverymanExample();
            deliverymanExample.or().andDeletedEqualTo(0);
            List<Deliveryman> deliverymanList = deliverymanService.selectByExample(deliverymanExample);
            if (!ObjectUtils.isEmpty(deliverymanList)&&!deliverymanList.isEmpty()){

                Deliveryman deliveryman = deliverymanList.get((int)(System.currentTimeMillis()%deliverymanList.size()));
                newOrder.setDeliverymanId(deliveryman.getId());
            }
            newOrder.setType(1);
            newOrder.setUserAddressId(addressId);
        }
        else {newOrder.setType(0);}
        String curUser = "admin";
        if (authentication!=null){
            curUser = ((UserDetails)authentication.getPrincipal()).getUsername();
        }
        newOrder.setUserName(curUser);
        newOrder.setState(0);
        newOrder.setPaid(0);
        int res = orderService.insert(newOrder);
        if (res<1){return new Result(ResultConstant.CODE_FAILED,ResultConstant.MESSAGE_FAILED,null);}
        OrderDetail orderDetail = null;
        ObjectMapper objectMapper = new ObjectMapper();
        for(Map<String,Object> carItem:shoppingCar){
            orderDetail = new OrderDetail();
            orderDetail.setOrderId(newOrder.getId());
            orderDetail.setState(0);
            orderDetail.setSize((String) carItem.get("size"));
            orderDetail.setDishName((String) carItem.get("dishName"));
            orderDetail.setTopping(objectMapper.writeValueAsString(carItem.get("topping")));
            orderDetail.setTotalPrice((Integer) carItem.get("count")*(Integer) carItem.get("singlePrice"));
            orderDetail.setAmount((Integer) carItem.get("count"));
            orderDetail.setImgs((String) carItem.get("img"));
            if (orderDetailService.insert(orderDetail)<1){
                throw new CURDException();
            }
        }
        return new Result(ResultConstant.CODE_SUCCESS,ResultConstant.MESSAGE_SUCCESS,newOrder.getId());
    }

    @PostMapping("/order/payment")
    public String paymentPage(String shoppingCar, int deliveryType, Model model, Authentication authentication){
        if (systemControlComponent.closed()){
            return "forward:/closed.html";
        }
        if (deliveryType!=0&&deliveryType!=1){
            return "415";
        }
        String curUser = "admin";
        if (authentication!=null){
            curUser = authentication.getName();
        }
        model.addAttribute("shoppingCar",shoppingCar);
        model.addAttribute("deliveryType",deliveryType);
        model.addAttribute("userName",curUser);
        UserAddressExample userAddressExample = new UserAddressExample();
        userAddressExample.or().andUserNameEqualTo(curUser).andDeletedEqualTo(0);
        model.addAttribute("addressList",userAddressService.selectByExample(userAddressExample));
        return "Payment/paymentpage";
    }
}
