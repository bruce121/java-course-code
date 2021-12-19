package com.bruce121.controller;

import com.bruce121.mybatis.mapper.OrderMapper;
import com.bruce121.mybatis.model.OrderEntity;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Controller
public class MillinsOrderInsertTestController {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 生成100w条订单，time cost:[2487076ms]
     */
    @GetMapping(path = "/insert/single")
    public ResponseEntity<String> insert1() {

        String userId = UUID.randomUUID().toString();
        BigDecimal orderAmountTotal = new BigDecimal("100");
        long createdTime = System.currentTimeMillis();
        long lastModifiedTime = System.currentTimeMillis();
        boolean orderStatus = true;

        Stopwatch started = Stopwatch.createStarted();

        for (int i = 0; i < 1000000; i++) {
            OrderEntity order = new OrderEntity();
            order.setId(UUID.randomUUID().toString());
            order.setUserId(userId);
            order.setOrderStatus(orderStatus);
            order.setOrderAmountTotal(orderAmountTotal);
            order.setCreatedTime(createdTime);
            order.setLastModifiedTime(lastModifiedTime);
            orderMapper.insertSelective(order);
        }

        long elapsed = started.elapsed(TimeUnit.MILLISECONDS);

        System.out.printf("生成100w条订单，time cost:[%sms] ", elapsed);
        return ResponseEntity.ok(format("time cost:[%sms]", elapsed));
    }

    /**
     * Batch: time cost:[105729ms], each group size:[1000]
     * Batch: time cost:[98924ms], each group size:[10000]
     */
    @GetMapping(path = "/insert/batch")
    public ResponseEntity<String> batchInsert() {

        String userId = UUID.randomUUID().toString();
        BigDecimal orderAmountTotal = new BigDecimal("100");
        long createdTime = System.currentTimeMillis();
        long lastModifiedTime = System.currentTimeMillis();
        boolean orderStatus = true;

        Stopwatch started = Stopwatch.createStarted();

        int commitNumber = 10000;
        ArrayList<OrderEntity> orders = Lists.newArrayListWithCapacity(commitNumber);

        for (int i = 1; i <= 1000000; i++) {

            OrderEntity order = new OrderEntity();
            order.setId(UUID.randomUUID().toString());
            order.setUserId(userId);
            order.setOrderStatus(orderStatus);
            order.setOrderAmountTotal(orderAmountTotal);
            order.setCreatedTime(createdTime);
            order.setLastModifiedTime(lastModifiedTime);
            orders.add(order);

            if ((i % commitNumber) == 0) {
                orderMapper.batchInsert(orders);
                orders.clear();
            }
        }

        long elapsed = started.elapsed(TimeUnit.MILLISECONDS);

        System.out.printf("Batch: 生成100w条订单，time cost:[%sms], each group size:[%s] ", elapsed, commitNumber);
        return ResponseEntity.ok(format("Batch: time cost:[%sms], each group size:[%s] ", elapsed, commitNumber));
    }
}
