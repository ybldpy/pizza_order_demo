package com.example.pizza_order_demo.model;


import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Objects;

public class DishSellingData {

    @ExcelProperty(value = "Dish name")
    private String dishName;

    public String getDishName() {
        return dishName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishSellingData that = (DishSellingData) o;
        return count == that.count && Objects.equals(dishName, that.dishName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dishName, count);
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @ExcelProperty(value = "Selling")
    private int count;
}
