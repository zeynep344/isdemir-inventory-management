package com.example.isdemir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cold_coil")
public class ColdCoil {

    @Id
    @Column(name = "product_id")
    private Integer productId; // PK + FK (INT)

    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "yield_str")
    private Integer yieldStr;

    @Column(name = "elongation")
    private Integer elongation;

    @Column(name = "thickness")
    private Integer thickness;


    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getYieldStr() { return yieldStr; }
    public void setYieldStr(Integer yieldStr) { this.yieldStr = yieldStr; }
    public Integer getElongation() { return elongation; }
    public void setElongation(Integer elongation) { this.elongation = elongation; }
    public Integer getThickness() { return thickness; }
    public void setThickness(Integer thickness) { this.thickness = thickness; }

}
