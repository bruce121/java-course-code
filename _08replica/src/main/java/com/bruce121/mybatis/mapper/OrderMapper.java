package com.bruce121.mybatis.mapper;

import com.bruce121.mybatis.BaseMapper;
import com.bruce121.mybatis.model.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper extends BaseMapper<OrderEntity> {

    @Insert(" <script> " +
            " INSERT INTO `order`  " +
            "   ( id,user_id,order_status,order_amount_total,created_time,last_modified_time) " +
            " VALUES  " +
            "   <foreach collection='entities' item='item' separator=','> " +
            "       (#{item.id}, #{item.userId}, #{item.orderStatus}, #{item.orderAmountTotal}, #{item.createdTime}, #{item.lastModifiedTime}) " +
            "   </foreach> " +
            " </script>")
    void batchInsert(@Param("entities") List<OrderEntity> entities);

    @Update(" <script> " +
            "  UPDATE `t_order`  SET id = #{order.id},user_id = #{order.userId},order_status = #{order.orderStatus},order_amount_total = #{order.orderAmountTotal},created_time = #{order.createdTime},last_modified_time = #{order.lastModifiedTime} WHERE  id = #{order.id} and user_id = #{order.userId} " +
            " </script>")
    int update(@Param("order") OrderEntity order);
}
