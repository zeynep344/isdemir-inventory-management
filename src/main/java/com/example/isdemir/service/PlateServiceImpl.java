
package com.example.isdemir.service;

import com.example.isdemir.model.Plate;
import com.example.isdemir.model.Product;
import com.example.isdemir.repository.PlateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PlateServiceImpl implements PlateService {

    private final PlateRepository repo;

    public PlateServiceImpl(PlateRepository repo) {
        this.repo = repo;
    }

    @Override
    public Plate getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plate not found: " + id));
    }

    @Override
    public Plate savePlate(Plate p) {
        return repo.save(p);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Plate plate = getById(id);
        Product base = plate.getProduct(); // Plate entity'inde getter olmalı (aşağıda veriyorum)
        repo.delete(plate);

        // Eğer Product kaydını da silmek istersen:
        // if (base != null) productService.deleteById(base.getProductId());
    }
}
