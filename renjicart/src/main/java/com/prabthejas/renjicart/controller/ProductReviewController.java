package com.prabthejas.renjicart.controller;

import com.prabthejas.renjicart.dto.ProductReviewDto;
import com.prabthejas.renjicart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products/reviews")
public class ProductReviewController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ProductReviewDto productReviewDto){
        productService.addReview(productReviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Review Added");
    }
}
