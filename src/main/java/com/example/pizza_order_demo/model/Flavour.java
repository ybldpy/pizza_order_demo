package com.example.pizza_order_demo.model;

public class Flavour {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flavour.id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flavour.dish_id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private Integer dishId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_flavour.name
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flavour.id
     *
     * @return the value of t_flavour.id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flavour.id
     *
     * @param id the value for t_flavour.id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flavour.dish_id
     *
     * @return the value of t_flavour.dish_id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public Integer getDishId() {
        return dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flavour.dish_id
     *
     * @param dishId the value for t_flavour.dish_id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_flavour.name
     *
     * @return the value of t_flavour.name
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_flavour.name
     *
     * @param name the value for t_flavour.name
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}