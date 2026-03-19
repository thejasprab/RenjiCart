package com.prabthejas.renjicart.controller;

import com.prabthejas.renjicart.dto.OrderDto;
import com.prabthejas.renjicart.entity.Order;
import com.prabthejas.renjicart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderRequest){
        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.ok().body(order);
    }
}
