package com.example.pizza_order_demo.model;

import com.example.pizza_order_demo.utils.TimeUtils;

import java.util.Arrays;
import java.util.Objects;

public class OrderDTO extends Order{

    private String[] imgs;
    private int price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return price == orderDTO.price && quantity == orderDTO.quantity && Arrays.equals(imgs, orderDTO.imgs) && Objects.equals(createTimeStr, orderDTO.createTimeStr) && Objects.equals(pickupTimeStr, orderDTO.pickupTimeStr) && Objects.equals(finishedTimeStr, orderDTO.finishedTimeStr);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(price, quantity, createTimeStr, pickupTimeStr, finishedTimeStr);
        result = 31 * result + Arrays.hashCode(imgs);
        return result;
    }

    public String getPickupTimeStr() {
        return pickupTimeStr;
    }

    public void setPickupTimeStr(String pickupTimeStr) {
        this.pickupTimeStr = pickupTimeStr;
    }

    public String getFinishedTimeStr() {
        return finishedTimeStr;
    }

    public void setFinishedTimeStr(String finishedTimeStr) {
        this.finishedTimeStr = finishedTimeStr;
    }

    private int quantity;
    private String createTimeStr;
    private String pickupTimeStr;
    private String finishedTimeStr;

    public String[] getImgs() {
        return imgs;
    }

    public void setImgs(String[] imgs) {
        this.imgs = imgs;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public OrderDTO(Order father){
        setType(father.getType());
        setCreateTime(father.getCreateTime());
        setDeliverymanId(father.getDeliverymanId());
        setPaid(father.getPaid());
        setState(father.getState());
        setUserName(father.getUserName());
        setId(father.getId());
        setUserAddressId(father.getUserAddressId());
        setFinishedTime(father.getFinishedTime());
        setPickupTime(father.getPickupTime());

    }

}
