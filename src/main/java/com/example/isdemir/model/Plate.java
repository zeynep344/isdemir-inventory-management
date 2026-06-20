package com.example.isdemir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "plates")
public class Plate {

    @Id
    @Column(name = "product_id")
    private Integer productId; // PK + FK (INT)

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;


    @Column(name = "thickness")
    private Integer thickness;

    @Column(name = "length")
    private Integer length;

    @Column(name = "width")
    private Integer width;

    public Plate() {}

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }


    public Integer getThickness() { return thickness; }
    public void setThickness(Integer thickness) { this.thickness = thickness; }

    public Integer getLength() { return length; }
    public void setLength(Integer length) { this.length = length; }

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
}
