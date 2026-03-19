package com.prabthejas.renjicart.repository;

import com.prabthejas.renjicart.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
