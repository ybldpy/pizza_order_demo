package com.example.pizza_order_demo.model;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Objects;

public class DishSale {

    public DishSale(String dishName, int sales) {
        this.dishName = dishName;
        this.sales = sales;
    }

    public DishSale() {
    }

    @ExcelProperty(value = "Dish name")
    private String dishName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishSale dishSale = (DishSale) o;
        return sales == dishSale.sales && Objects.equals(dishName, dishSale.dishName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishName, sales);
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @ExcelProperty(value = "Sales")
    private int sales;

}
