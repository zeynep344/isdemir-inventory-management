package com.example.isdemir.service;

import com.example.isdemir.model.HotCoil;


// service/HotCoilService.java
public interface HotCoilService {
  HotCoil getById(Integer id);
  HotCoil save(HotCoil h);
  void deleteById(Integer id);
}

// service/HotCoilServiceImpl.java  -> aynı mantıkla Integer kullan
