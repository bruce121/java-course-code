package com.bruce121.service;

import com.bruce121.mybatis.model.OrderEntity;

public interface OrderService {

    void insert(OrderEntity order);

    int delete(String id);

    int update(OrderEntity order);

    OrderEntity get(String id);

    void xaTest();
}
