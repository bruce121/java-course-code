package com.bruce121.service;

import com.bruce121.mybatis.mapper.OrderMapper;
import com.bruce121.mybatis.model.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

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
}
