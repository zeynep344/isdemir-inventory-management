package com.example.isdemir.repository;

import com.example.isdemir.model.ColdCoil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColdCoilRepository extends JpaRepository<ColdCoil, Integer> {}  // <-- Integer
