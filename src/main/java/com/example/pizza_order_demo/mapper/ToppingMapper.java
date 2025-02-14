package com.example.pizza_order_demo.mapper;

import com.example.pizza_order_demo.model.Topping;
import com.example.pizza_order_demo.model.ToppingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ToppingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    long countByExample(ToppingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int deleteByExample(ToppingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int insert(Topping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int insertSelective(Topping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    List<Topping> selectByExample(ToppingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    Topping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int updateByExampleSelective(@Param("record") Topping record, @Param("example") ToppingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int updateByExample(@Param("record") Topping record, @Param("example") ToppingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int updateByPrimaryKeySelective(Topping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_topping
     *
     * @mbg.generated Sun Apr 16 20:46:06 CST 2023
     */
    int updateByPrimaryKey(Topping record);
}