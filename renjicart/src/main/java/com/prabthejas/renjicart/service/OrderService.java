package com.prabthejas.renjicart.service;

import com.prabthejas.renjicart.dto.OrderCreated;
import com.prabthejas.renjicart.dto.OrderDto;
import com.prabthejas.renjicart.dto.OrderItemDto;
import com.prabthejas.renjicart.entity.Order;
import com.prabthejas.renjicart.entity.OrderItem;
import com.prabthejas.renjicart.entity.Product;
import com.prabthejas.renjicart.repository.OrderRepository;
import com.prabthejas.renjicart.repository.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderCreated createOrder(OrderDto orderRequest){
        Order order  = new Order();
        order.setStatus("PENDING");
        double totalItemsAmount = 0;

        for(OrderItemDto item: orderRequest.getOrderItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setName(item.getName());
            orderItem.setPrice(item.getPrice());
            orderItem.setImage(item.getImage());
            orderItem.setQuantity(item.getQuantity());
            Product product = productRepository.findById(item.getProductId()).orElseThrow(()->new RuntimeException("Product not Found"));
            orderItem.setProduct(product);
            totalItemsAmount+=orderItem.getPrice()* orderItem.getQuantity();
            order.getOrderItems().add(orderItem);
        }
        order.setTotalItemsAmount(totalItemsAmount);
        double totalAmount = 0;
        double taxAmount=10;
        totalAmount = totalItemsAmount+taxAmount;
        order.setTotalAmount(totalAmount);
        order.setTaxAmount(taxAmount);
        String refId = UUID.randomUUID().toString();
        order.setReferenceId(refId);
        orderRepository.save(order);
        return new OrderCreated(refId);
    }

    public Order getOrder(String referenceId){
        return orderRepository.findByReferenceId(referenceId).orElseThrow(()-> new RuntimeException("No Order Found with this referenceId"));
    }
}
