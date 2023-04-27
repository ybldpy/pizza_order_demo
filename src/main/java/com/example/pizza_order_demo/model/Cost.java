package com.example.pizza_order_demo.model;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Objects;

public class Cost {
    public Cost() {
    }

    public String getDate() {
        return date;
    }

    public Cost(String date, int count) {
        this.date = date;
        this.count = count;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cost cost = (Cost) o;
        return count == cost.count && Objects.equals(date, cost.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, count);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @ExcelProperty(value = "Date")
    private String date;
    @ExcelProperty(value = "Cost")
    private int count;
}
