package com.prabthejas.renjicart.entity;

import jakarta.persistence.*;

@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String publicId;
    private String url;

    public ProductImage(Long id, String publicId, String url) {
        Id = id;
        this.publicId = publicId;
        this.url = url;
    }

    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage(String url, Product product) {
        this.url = "/uploads"+url;
        this.publicId = url;
        this.product = product;
    }

    public ProductImage() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
