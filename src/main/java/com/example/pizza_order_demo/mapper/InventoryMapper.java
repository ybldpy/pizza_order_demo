package com.example.pizza_order_demo.mapper;

import com.example.pizza_order_demo.model.Inventory;
import com.example.pizza_order_demo.model.InventoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface InventoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    long countByExample(InventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int deleteByExample(InventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int insert(Inventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int insertSelective(Inventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    List<Inventory> selectByExample(InventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    Inventory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByExampleSelective(@Param("record") Inventory record, @Param("example") InventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByExample(@Param("record") Inventory record, @Param("example") InventoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByPrimaryKeySelective(Inventory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_inventory
     *
     * @mbg.generated Wed Apr 05 17:40:03 CST 2023
     */
    int updateByPrimaryKey(Inventory record);
}