package com.example.pizza_order_demo.model;

import java.util.List;

public class DishDTO extends Dish{
    private String[] flavours;
    private String sellStatus;


    public String[] getFlavours() {
        return flavours;
    }

    public void setFlavours(String[] flavours) {
        this.flavours = flavours;
    }
    public void setFlavours(List<Flavour> flavours){
        if (flavours==null||flavours.isEmpty()){return;}
        String[] fs = new String[flavours.size()];
        int p = 0;
        for(Flavour f:flavours){
            fs[p++] = f.getName();
        }
        setFlavours(fs);
    }
    public DishDTO(){
        this(null);
    }
    public DishDTO(Dish father){

    }

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }
    public void setSellStatus(){
        setSellStatus(this.getStatus()==STATUS_SELL?"Sell":"Hide");
    }
}
