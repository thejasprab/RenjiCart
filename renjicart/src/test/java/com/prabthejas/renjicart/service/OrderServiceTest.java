package com.prabthejas.renjicart.service;

import com.prabthejas.renjicart.dto.OrderCreated;
import com.prabthejas.renjicart.dto.OrderDto;
import com.prabthejas.renjicart.dto.OrderItemDto;
import com.prabthejas.renjicart.entity.Order;
import com.prabthejas.renjicart.entity.Product;
import com.prabthejas.renjicart.repository.OrderRepository;
import com.prabthejas.renjicart.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder_savesPendingOrderWithTotalsAndReturnsReferenceId() {
        Product phone = product(1L);
        Product mouse = product(10L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(phone));
        when(productRepository.findById(10L)).thenReturn(Optional.of(mouse));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderDto request = orderRequest(
                orderItem("Apple iPhone 15", 1, "/products/1.jpg", 799.0, 1L),
                orderItem("Logitech MX Master 3S", 2, "/products/10.jpg", 99.0, 10L)
        );

        OrderCreated created = orderService.createOrder(request);

        assertThat(created.getReferenceId()).isNotBlank();
        assertThat(UUID.fromString(created.getReferenceId())).isNotNull();

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        Order saved = captor.getValue();

        assertThat(saved.getStatus()).isEqualTo("PENDING");
        assertThat(saved.getReferenceId()).isEqualTo(created.getReferenceId());
        assertThat(saved.getTaxAmount()).isEqualTo(10.0);
        assertThat(saved.getTotalItemsAmount()).isEqualTo(997.0);
        assertThat(saved.getTotalAmount()).isEqualTo(1007.0);
        assertThat(saved.getOrderItems()).hasSize(2);
        assertThat(saved.getOrderItems().getFirst().getName()).isEqualTo("Apple iPhone 15");
        assertThat(saved.getOrderItems().getFirst().getProduct()).isSameAs(phone);
        assertThat(saved.getOrderItems().get(1).getQuantity()).isEqualTo(2);
        assertThat(saved.getOrderItems().get(1).getProduct()).isSameAs(mouse);
    }

    @Test
    void createOrder_whenProductMissing_throwsAndDoesNotSave() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        OrderDto request = orderRequest(
                orderItem("Missing product", 1, "/products/99.jpg", 10.0, 99L)
        );

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Product not Found");

        verify(orderRepository, never()).save(any());
    }

    private static Product product(long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    private static OrderDto orderRequest(OrderItemDto... items) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderItems(List.of(items));
        return orderDto;
    }

    private static OrderItemDto orderItem(String name, int quantity, String image, double price, long productId) {
        OrderItemDto item = new OrderItemDto();
        item.setName(name);
        item.setQuantity(quantity);
        item.setImage(image);
        item.setPrice(price);
        item.setProductId(productId);
        return item;
    }
}
