package com.example.pizza_order_demo.model;

public class Inventory {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_inventory.id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_inventory.dish_id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private Integer dishId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_inventory.init_capacity
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private Integer initCapacity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_inventory.number
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private Integer number;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_inventory.create_time
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    private Long createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_inventory.id
     *
     * @return the value of t_inventory.id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_inventory.id
     *
     * @param id the value for t_inventory.id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_inventory.dish_id
     *
     * @return the value of t_inventory.dish_id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public Integer getDishId() {
        return dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_inventory.dish_id
     *
     * @param dishId the value for t_inventory.dish_id
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_inventory.init_capacity
     *
     * @return the value of t_inventory.init_capacity
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public Integer getInitCapacity() {
        return initCapacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_inventory.init_capacity
     *
     * @param initCapacity the value for t_inventory.init_capacity
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setInitCapacity(Integer initCapacity) {
        this.initCapacity = initCapacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_inventory.number
     *
     * @return the value of t_inventory.number
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_inventory.number
     *
     * @param number the value for t_inventory.number
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_inventory.create_time
     *
     * @return the value of t_inventory.create_time
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_inventory.create_time
     *
     * @param createTime the value for t_inventory.create_time
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}