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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public OrderDto getOrder(String referenceId){
        Order order = orderRepository.findByReferenceId(referenceId).orElseThrow(()-> new RuntimeException("No Order Found with this referenceId"));
        return convertToOrderDto(order);
    }

    public OrderDto convertToOrderDto(Order order){
        OrderDto orderDto = new OrderDto();
        List<OrderItemDto> orderItemDtos = order.getOrderItems()
                .stream().map(orderItem -> {
                    OrderItemDto orderItemDto = new OrderItemDto();
                    orderItemDto.setName(orderItem.getName());
                    orderItemDto.setPrice(orderItem.getPrice());
                    orderItemDto.setImage(orderItem.getImage());
                    orderItemDto.setQuantity(orderItem.getQuantity());
                    orderItemDto.setProductId(orderItem.getProduct().getId());
                    return orderItemDto;
                }).collect(Collectors.toList());
        orderDto.setOrderItems(orderItemDtos);
        return orderDto;
    }
}
