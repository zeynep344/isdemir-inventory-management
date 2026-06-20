package com.example.isdemir.service;

import com.example.isdemir.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product saveProduct(Product product);
    void deleteProduct(Integer id);
}
