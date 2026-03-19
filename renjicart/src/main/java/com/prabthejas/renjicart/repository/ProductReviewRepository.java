package com.prabthejas.renjicart.repository;

import com.prabthejas.renjicart.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
}
