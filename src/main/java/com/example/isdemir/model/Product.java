package com.example.isdemir.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;   // <-- INT ile eşleşir

    @Column(name = "provider", length = 255)
    private String provider;

    @Column(name = "product_type", length = 255)
    private String productType;

    @Column(name = "material", length = 255)
    private String material;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status", length = 255)
    private String status;

    // GET/SET
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
