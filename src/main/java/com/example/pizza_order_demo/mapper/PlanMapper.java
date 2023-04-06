package com.example.pizza_order_demo.mapper;

import com.example.pizza_order_demo.model.Plan;
import com.example.pizza_order_demo.model.PlanExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface PlanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    long countByExample(PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int deleteByExample(PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int insert(Plan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int insertSelective(Plan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    List<Plan> selectByExample(PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    Plan selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByExampleSelective(@Param("record") Plan record, @Param("example") PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByExample(@Param("record") Plan record, @Param("example") PlanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByPrimaryKeySelective(Plan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_plan
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByPrimaryKey(Plan record);
}