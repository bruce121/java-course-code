package com.bruce121.service;

import com.bruce121.mybatis.mapper.OrderMapper;
import com.bruce121.mybatis.mapper1.UserMapper;
import com.bruce121.mybatis.model.OrderEntity;
import com.bruce121.mybatis.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(OrderEntity order) {

        orderMapper.insert(order);
    }

    @Override
    public int delete(String id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(OrderEntity order) {
        return orderMapper.update(order);
    }

    @Override
    public OrderEntity get(String id) {

        return orderMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void xaTest() {

        String orderId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        BigDecimal orderAmountTotal = new BigDecimal("100");
        long createdTime = System.currentTimeMillis();
        long lastModifiedTime = System.currentTimeMillis();
        boolean orderStatus = true;

        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        order.setUserId(userId);
        order.setOrderStatus(orderStatus);
        order.setOrderAmountTotal(orderAmountTotal);
        order.setCreatedTime(createdTime);
        order.setLastModifiedTime(lastModifiedTime);
        orderMapper.insert(order);

        System.out.println("Insert new Order:" + order);

        int y = 10/0 ;

        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setPhone("1234567888");
        user.setStatus(true);
        user.setCreatedTime(createdTime);
        user.setLastModifiedTime(lastModifiedTime);
        userMapper.insert(user);

        System.out.println("Insert new User:" + user);
    }
}
