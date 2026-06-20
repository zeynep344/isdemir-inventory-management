package com.example.isdemir.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hot_coil")
public class HotCoil {

    @Id
    @Column(name = "product_id")
    private Integer productId; // PK + FK (INT)

    // FK ilişki (aynı kolonu kullanıyoruz; DB'yi bozmasın diye insert/update kapalı)
    @OneToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "tensile_str")
    private Integer tensileStr;

    @Column(name = "elongation")
    private Integer elongation;

    @Column(name = "thickness")
    private Integer thickness;

    // GET/SET
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getTensileStr() {
        return tensileStr;
    }

    public void setTensileStr(Integer tensileStr) {
        this.tensileStr = tensileStr;
    }

    // ✔ Doğru getter ismi
    public Integer getElongation() {
        return elongation;
    }

    public void setElongation(Integer elongation) {
        this.elongation = elongation;
    }

    public Integer getThickness() {
        return thickness;
    }

    public void setThickness(Integer thickness) {
        this.thickness = thickness;
    }
}
