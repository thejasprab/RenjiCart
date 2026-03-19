package com.prabthejas.renjicart.dto;

import java.util.List;

public class OrderDto {

    private List<OrderItemDto> orderItems;

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}
