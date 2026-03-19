package com.prabthejas.renjicart.service;

import com.prabthejas.renjicart.dto.ProductDto;
import com.prabthejas.renjicart.dto.ProductImageDto;
import com.prabthejas.renjicart.dto.ProductReviewDto;
import com.prabthejas.renjicart.entity.Product;
import com.prabthejas.renjicart.entity.ProductReview;
import com.prabthejas.renjicart.repository.ProductRepository;
import com.prabthejas.renjicart.repository.ProductReviewRepository;
import com.prabthejas.renjicart.spec.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    public Map<String, Object> getAllProducts(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> products = productRepository.findAll(pageable);
        List<ProductDto> productDtos = products.stream().map(this::convertToDto).collect(Collectors.toList());
        Map<String,Object> response = new HashMap<>();
        response.put("products",productDtos);
        response.put("totalProducts",products.getTotalElements());
        return response;
    }

    public ProductDto convertToDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setRatings(product.getRatings());
        dto.setSeller(product.getSeller());
        dto.setStock(product.getStock());
        dto.setNumOfReviews(product.getNumOfReviews());

        List<ProductReviewDto> productReviewDtos = product.getReviews().stream().map(productReview -> {
            ProductReviewDto productReviewDto = new ProductReviewDto();
            productReviewDto.setProductId(productReview.getId());
            productReviewDto.setComment(productReview.getComment());
            productReviewDto.setRating(productReview.getRating());
            return productReviewDto;
        }).collect(Collectors.toList());
        dto.setReviews(productReviewDtos);

        List<ProductImageDto> productImageDtos = product.getImages().stream().map(image -> {
            ProductImageDto productImageDto = new ProductImageDto();
            productImageDto.setUrl(image.getPublicId());
            return productImageDto;
        }).collect(Collectors.toList());
        dto.setImages(productImageDtos);

        return dto;
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found for this id:" +id)
        );
    }

    public List<Product> searchProducts(String category, Double minPrice, Double maxPrice, String keyword, Double ratings){
        Specification<Product> spec = Specification.<Product>unrestricted().and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.priceBetween(minPrice,maxPrice))
                .and(ProductSpecification.hasNameOrDescription(keyword))
                .and(ProductSpecification.ratingGreaterThan(ratings));
        return productRepository.findAll(spec);
    }


    public void addReview(ProductReviewDto productReviewDto) {
        Product product = productRepository.findById(productReviewDto.getProductId()).orElseThrow(()-> new RuntimeException("Product not Found"));
        ProductReview productReview = new ProductReview();
        productReview.setComment(productReviewDto.getComment());
        productReview.setRating(productReviewDto.getRating());
        productReview.setProduct(product);

        productReviewRepository.save(productReview);
    }
}
