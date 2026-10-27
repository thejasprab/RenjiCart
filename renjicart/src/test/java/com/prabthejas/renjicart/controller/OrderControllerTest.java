package com.prabthejas.renjicart.controller;

import com.prabthejas.renjicart.dto.OrderCreated;
import com.prabthejas.renjicart.dto.OrderDto;
import com.prabthejas.renjicart.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void createOrder_returnsReferenceIdFromService() throws Exception {
        when(orderService.createOrder(any(OrderDto.class)))
                .thenReturn(new OrderCreated("order-ref-123"));

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "orderItems": [
                                    {
                                      "name": "Apple iPhone 15",
                                      "quantity": 1,
                                      "image": "/products/1.jpg",
                                      "price": 799.0,
                                      "productId": 1
                                    }
                                  ]
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.referenceId").value("order-ref-123"));

        ArgumentCaptor<OrderDto> captor = ArgumentCaptor.forClass(OrderDto.class);
        verify(orderService).createOrder(captor.capture());
        assertThat(captor.getValue().getOrderItems()).hasSize(1);
        assertThat(captor.getValue().getOrderItems().getFirst().getProductId()).isEqualTo(1L);
        assertThat(captor.getValue().getOrderItems().getFirst().getPrice()).isEqualTo(799.0);
        assertThat(captor.getValue().getOrderItems().getFirst().getQuantity()).isEqualTo(1);
    }
}
