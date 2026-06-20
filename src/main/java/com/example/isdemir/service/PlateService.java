// service/PlateService.java
package com.example.isdemir.service;

import com.example.isdemir.model.Plate;

public interface PlateService {
    Plate getById(Integer id);
    Plate savePlate(Plate p);
    void deleteById(Integer id);
}
