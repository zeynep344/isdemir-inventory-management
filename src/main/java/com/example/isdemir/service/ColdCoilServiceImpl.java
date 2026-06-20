package com.example.isdemir.service;

import com.example.isdemir.model.ColdCoil;
import com.example.isdemir.repository.ColdCoilRepository;
import org.springframework.stereotype.Service;

@Service
public class ColdCoilServiceImpl implements ColdCoilService {

    private final ColdCoilRepository repo;

    public ColdCoilServiceImpl(ColdCoilRepository repo) {
        this.repo = repo;
    }

    @Override
    public ColdCoil getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ColdCoil not found: " + id));
    }

    @Override
    public ColdCoil save(ColdCoil c) {          // <-- İMZA ARAYÜZLE AYNI
        return repo.save(c);
    }

    @Override
    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
