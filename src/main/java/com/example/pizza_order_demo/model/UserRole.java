package com.example.pizza_order_demo.model;

public class UserRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_role.id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_role.user_id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_user_role.role_id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    private Integer roleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_role.id
     *
     * @return the value of t_user_role.id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_role.id
     *
     * @param id the value for t_user_role.id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_role.user_id
     *
     * @return the value of t_user_role.user_id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_role.user_id
     *
     * @param userId the value for t_user_role.user_id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_user_role.role_id
     *
     * @return the value of t_user_role.role_id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_user_role.role_id
     *
     * @param roleId the value for t_user_role.role_id
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}