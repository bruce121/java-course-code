package com.bruce121.service;

import com.bruce121.mybatis.mapper.OrderMapper;
import com.bruce121.mybatis.model.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Resource(name = "slave1DataSource")
    private DataSource slave1DataSource;

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Override
    public void insert(OrderEntity order) {

        orderMapper.insert(order);
//
//        Connection conn = null;
//        try {
//            conn = masterDataSource.getConnection();
//
//            // 执行SQL语句:
//            PreparedStatement stmt = conn.prepareStatement("insert into `order`(`id`, `user_id`, `order_status`, `order_amount_total` , `created_time`, `last_modified_time`) values(?,?,?,?,?,?)");
//            // 执行sql 获得结果
//            stmt.setString(1, order.getId());
//            stmt.setString(2, order.getUserId());
//            stmt.setBoolean(3, order.getOrderStatus());
//            stmt.setBigDecimal(4, order.getOrderAmountTotal());
//            stmt.setLong(5, order.getCreatedTime());
//            stmt.setLong(6, order.getLastModifiedTime());
//
//            stmt.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    @Override
    public int delete(String id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(OrderEntity order) {
        return orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public OrderEntity get(String id) {

        Connection conn = null;
        try {
            conn = slave1DataSource.getConnection();
            // 执行SQL语句:
            PreparedStatement pstmt = conn.prepareStatement("select * from `order` where id = ? ");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();

            // 处理结果
            while (rs.next()) {
                String orderId = rs.getString("id");
                String userId = rs.getString("user_id");
                boolean orderStatus = rs.getBoolean("order_status");
                BigDecimal orderAmountTotal = rs.getBigDecimal("order_amount_total");
                long createdTime = rs.getLong("created_time");
                long lastModifiedTime = rs.getLong("last_modified_time");

                OrderEntity order = new OrderEntity();
                order.setId(orderId);
                order.setUserId(userId);
                order.setOrderStatus(orderStatus);
                order.setOrderAmountTotal(orderAmountTotal);
                order.setCreatedTime(createdTime);
                order.setLastModifiedTime(lastModifiedTime);

                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
