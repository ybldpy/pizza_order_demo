package com.example.pizza_order_demo.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DishDTO extends Dish{
    private String[] flavours;
    private String sellStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishDTO dishDTO = (DishDTO) o;
        return categoryId == dishDTO.categoryId && Arrays.equals(flavours, dishDTO.flavours) && Objects.equals(sellStatus, dishDTO.sellStatus);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sellStatus, categoryId);
        result = 31 * result + Arrays.hashCode(flavours);
        return result;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private int categoryId;


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

    }
    public DishDTO(Dish father){
        setCategoryName(father.getCategoryName());
        setId(father.getId());
        setImg(father.getImg());
        setDeleted(father.getDeleted());
        setState(father.getState());
        setRemark(father.getRemark());
        setTopping(father.getTopping());
        setDishName(father.getDishName());
        setSizePrice(father.getSizePrice());

    }

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }
    public void setSellStatus(){
        setSellStatus(this.getState()==1?"Sell":"Hide");
    }
}
