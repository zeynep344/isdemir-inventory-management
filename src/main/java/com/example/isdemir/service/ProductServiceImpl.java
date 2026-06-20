package com.example.isdemir.service;

import com.example.isdemir.model.Product;
import com.example.isdemir.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> getAllProducts() {           // ✅ getAllProducts (iki L yok)
        return repo.findAll();                        // ✅ findAll()
    }

    @Override
    public Product getProductById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    @Override
    public Product saveProduct(Product product) {
        return repo.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        repo.deleteById(id);
    }
}
