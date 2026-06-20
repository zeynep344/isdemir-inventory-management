package com.example.isdemir.service;

import com.example.isdemir.model.HotCoil;
import com.example.isdemir.repository.HotCoilRepository;
import org.springframework.stereotype.Service;

@Service
public class HotCoilServiceImpl implements HotCoilService {

    private final HotCoilRepository repo;

    public HotCoilServiceImpl(HotCoilRepository repo) {
        this.repo = repo;
    }

    @Override
    public HotCoil getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HotCoil not found: " + id));
    }

    @Override
    public HotCoil save(HotCoil h) {
        return repo.save(h);
    }

    @Override
    public void deleteById(Integer id) {   // <-- Integer, arayüzle birebir
        repo.deleteById(id);
    }
}
