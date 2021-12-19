package com.bruce121.mybatis.mapper;

import com.bruce121.mybatis.BaseMapper;
import com.bruce121.mybatis.model.OrderEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
}
