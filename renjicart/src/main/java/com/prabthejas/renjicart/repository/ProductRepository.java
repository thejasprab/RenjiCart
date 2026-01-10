package com.prabthejas.renjicart.repository;

import com.prabthejas.renjicart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product, Long> {
}
