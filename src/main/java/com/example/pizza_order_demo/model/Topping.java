package com.example.pizza_order_demo.model;

public class Topping {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_topping.id
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_topping.dish_id
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    private Integer dishId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_topping.name
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_topping.price
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    private Integer price;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_topping.id
     *
     * @return the value of t_topping.id
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_topping.id
     *
     * @param id the value for t_topping.id
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_topping.dish_id
     *
     * @return the value of t_topping.dish_id
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public Integer getDishId() {
        return dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_topping.dish_id
     *
     * @param dishId the value for t_topping.dish_id
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_topping.name
     *
     * @return the value of t_topping.name
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_topping.name
     *
     * @param name the value for t_topping.name
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_topping.price
     *
     * @return the value of t_topping.price
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_topping.price
     *
     * @param price the value for t_topping.price
     *
     * @mbg.generated Fri Apr 14 14:58:45 CST 2023
     */
    public void setPrice(Integer price) {
        this.price = price;
    }
}