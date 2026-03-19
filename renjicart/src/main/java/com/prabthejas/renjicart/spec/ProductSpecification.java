package com.prabthejas.renjicart.spec;

import com.prabthejas.renjicart.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.lang.model.type.ArrayType;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String category){
        return ((root, query, criteriaBuilder) -> category == null ? criteriaBuilder.conjunction():criteriaBuilder.equal(root.get("category"),category));
    }

    public static Specification<Product> priceBetween(Double minPrice, Double maxPrice){
        return (root, query, criteriaBuilder) -> {
            if(minPrice==null && maxPrice==null) return criteriaBuilder.conjunction();
            if(minPrice==null) return criteriaBuilder.lessThanOrEqualTo(root.get("price"),maxPrice);
            if(maxPrice==null) return criteriaBuilder.greaterThanOrEqualTo(root.get("price"),minPrice);
            return criteriaBuilder.between(root.get("price"),minPrice,maxPrice);
        };
    }

    public static  Specification<Product> hasNameOrDescription(String keyword){
        return (root, query, criteriaBuilder) -> {
            if(keyword==null || keyword.isEmpty()) return criteriaBuilder.conjunction();
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"),"%"+keyword.toLowerCase()+"%"),
                    criteriaBuilder.like(root.get("description"),"%"+keyword.toLowerCase()+"%"));
        };
    }

    public static Specification<Product> ratingGreaterThan(Double ratings){
        return (root, query, criteriaBuilder) -> {
            if(ratings==null) return criteriaBuilder.conjunction();
            return criteriaBuilder.greaterThanOrEqualTo(root.get("ratings"),ratings);
        };
    }
}
