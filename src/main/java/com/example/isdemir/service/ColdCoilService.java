package com.example.isdemir.service;

import com.example.isdemir.model.ColdCoil;

public interface ColdCoilService {
    ColdCoil getById(Integer id);
    ColdCoil save(ColdCoil c);      // <-- Dönüş tipi ColdCoil
    void deleteById(Integer id);
}
