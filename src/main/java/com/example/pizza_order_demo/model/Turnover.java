package com.example.pizza_order_demo.model;

public class Turnover {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_turnover.id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_turnover.turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    private Long turnover;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_turnover.create_time
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    private Long createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_turnover.id
     *
     * @return the value of t_turnover.id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_turnover.id
     *
     * @param id the value for t_turnover.id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_turnover.turnover
     *
     * @return the value of t_turnover.turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public Long getTurnover() {
        return turnover;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_turnover.turnover
     *
     * @param turnover the value for t_turnover.turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public void setTurnover(Long turnover) {
        this.turnover = turnover;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_turnover.create_time
     *
     * @return the value of t_turnover.create_time
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_turnover.create_time
     *
     * @param createTime the value for t_turnover.create_time
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}