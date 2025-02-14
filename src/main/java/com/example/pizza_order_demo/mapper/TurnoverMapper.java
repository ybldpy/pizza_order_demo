package com.example.pizza_order_demo.mapper;

import com.example.pizza_order_demo.model.Turnover;
import com.example.pizza_order_demo.model.TurnoverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TurnoverMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    long countByExample(TurnoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int deleteByExample(TurnoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int insert(Turnover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int insertSelective(Turnover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    List<Turnover> selectByExample(TurnoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    Turnover selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int updateByExampleSelective(@Param("record") Turnover record, @Param("example") TurnoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int updateByExample(@Param("record") Turnover record, @Param("example") TurnoverExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int updateByPrimaryKeySelective(Turnover record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_turnover
     *
     * @mbg.generated Sat Apr 15 17:25:54 CST 2023
     */
    int updateByPrimaryKey(Turnover record);
}