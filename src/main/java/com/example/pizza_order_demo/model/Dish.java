package com.example.pizza_order_demo.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.Map;

public class Dish {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.id
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.dish_name
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private String dishName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.img
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private String img;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.category_name
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private String categoryName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.size_price
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private String sizePrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private String topping;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.remark
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.state
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private Integer state = 1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.deleted
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    private Integer deleted = 0;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.id
     *
     * @return the value of t_dish.id
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.id
     *
     * @param id the value for t_dish.id
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.dish_name
     *
     * @return the value of t_dish.dish_name
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public String getDishName() {
        return dishName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.dish_name
     *
     * @param dishName the value for t_dish.dish_name
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setDishName(String dishName) {
        this.dishName = dishName == null ? null : dishName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.img
     *
     * @return the value of t_dish.img
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public String getImg() {
        return img;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.img
     *
     * @param img the value for t_dish.img
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.category_name
     *
     * @return the value of t_dish.category_name
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.category_name
     *
     * @param categoryName the value for t_dish.category_name
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.size_price
     *
     * @return the value of t_dish.size_price
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public String getSizePrice() {
        return sizePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.size_price
     *
     * @param sizePrice the value for t_dish.size_price
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setSizePrice(String sizePrice) {
        this.sizePrice = sizePrice == null ? null : sizePrice.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.topping
     *
     * @return the value of t_dish.topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public String getTopping() {
        return topping;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.topping
     *
     * @param topping the value for t_dish.topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setTopping(String topping) {
        this.topping = topping == null ? null : topping.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.remark
     *
     * @return the value of t_dish.remark
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.remark
     *
     * @param remark the value for t_dish.remark
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.state
     *
     * @return the value of t_dish.state
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.state
     *
     * @param state the value for t_dish.state
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.deleted
     *
     * @return the value of t_dish.deleted
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public Integer getDeleted() {
        return deleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.deleted
     *
     * @param deleted the value for t_dish.deleted
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public static Dish map2Dish(Map<String,Object> map) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(map)){return null;}
        Dish dish = new Dish();
        dish.setDishName((String) map.get("dishName"));
        dish.setCategoryName((String) map.get("categoryName"));
        dish.setImg((String) map.get("img"));

        dish.setState((Integer) map.get("state"));
        dish.setRemark((String) map.get("remark"));
        ObjectMapper objectMapper = new ObjectMapper();
        Object topping = map.get("topping");
        if (!ObjectUtils.isEmpty(topping)){
            String s = objectMapper.writeValueAsString(topping);
            dish.setTopping(s);
        }
        else {
            dish.setTopping("[]");
        }
        List<Map<String,Integer>> sizePrice = (List<Map<String, Integer>>) map.get("sizePrice");
        if (!ObjectUtils.isEmpty(sizePrice)){
            String s = objectMapper.writeValueAsString(sizePrice);
            dish.setSizePrice(s);
        }
        else {
            dish.setSizePrice("[]");
        }

        return dish;

    }
}