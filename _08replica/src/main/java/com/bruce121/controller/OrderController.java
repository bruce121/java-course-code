package com.bruce121.controller;

import com.bruce121.mybatis.model.OrderEntity;
import com.bruce121.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @PostMapping(path = "/order")
    public ResponseEntity<String> insert() {

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

        orderService.insert(order);
        return ResponseEntity.ok(orderId);
    }

    @ResponseBody
    @PutMapping(path = "/order/{orderId}")
    public ResponseEntity<OrderEntity> update(@PathVariable("orderId") String orderId) {

        OrderEntity existOrder = orderService.get(orderId);

        // random update
        BigDecimal orderAmountTotal = new BigDecimal("100");
        long createdTime = System.currentTimeMillis();
        long lastModifiedTime = System.currentTimeMillis();
        boolean orderStatus = true;

        OrderEntity order = new OrderEntity();
        order.setId(existOrder.getId());
        order.setUserId(existOrder.getUserId());
        order.setOrderStatus(orderStatus);
        order.setOrderAmountTotal(orderAmountTotal);
        order.setCreatedTime(createdTime);
        order.setLastModifiedTime(lastModifiedTime);

        orderService.update(order);
        return ResponseEntity.ok(order);
    }

    @ResponseBody
    @GetMapping(path = "/order/{orderId}")
    public ResponseEntity<OrderEntity> query(@PathVariable("orderId") String orderId) {

        OrderEntity order = orderService.get(orderId);
        return ResponseEntity.ok(order);
    }


    @ResponseBody
    @DeleteMapping(path = "/order/{orderId}")
    public ResponseEntity<String> delete(@PathVariable("orderId") String orderId) {

        orderService.delete(orderId);
        return ResponseEntity.ok("success");
    }
}
