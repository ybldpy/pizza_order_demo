package com.example.pizza_order_demo.model;

import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Objects;

public class Profit {
    public Profit(String date, int profit) {
        this.date = date;
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profit profit1 = (Profit) o;
        return profit == profit1.profit && Objects.equals(date, profit1.date);
    }

    public Profit() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, profit);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    @ExcelProperty(value = "Date")
    public String date;
    @ExcelProperty(value = "profit")
    public int profit;

}
