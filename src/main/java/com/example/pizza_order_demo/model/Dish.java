package com.example.pizza_order_demo.model;

public class Dish {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.id
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.name
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.size
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private Integer size;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.description
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private String description;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.price
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private Integer price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.rank
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private Integer rank;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.img
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private String img;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.status
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private Integer status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_dish.category_name
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    private String categoryName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.id
     *
     * @return the value of t_dish.id
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
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
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.name
     *
     * @return the value of t_dish.name
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.name
     *
     * @param name the value for t_dish.name
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.size
     *
     * @return the value of t_dish.size
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public Integer getSize() {
        return size;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.size
     *
     * @param size the value for t_dish.size
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.description
     *
     * @return the value of t_dish.description
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.description
     *
     * @param description the value for t_dish.description
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.price
     *
     * @return the value of t_dish.price
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.price
     *
     * @param price the value for t_dish.price
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.rank
     *
     * @return the value of t_dish.rank
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.rank
     *
     * @param rank the value for t_dish.rank
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.img
     *
     * @return the value of t_dish.img
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
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
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.status
     *
     * @return the value of t_dish.status
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_dish.status
     *
     * @param status the value for t_dish.status
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_dish.category_name
     *
     * @return the value of t_dish.category_name
     *
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
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
     * @mbg.generated Tue Apr 11 14:41:11 CST 2023
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }
}