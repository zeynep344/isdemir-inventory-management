package com.example.isdemir.repository;

import com.example.isdemir.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("""
           select p from Product p
           where (:type is null or :type = 'all' or lower(p.productType) = lower(:type))
             and (
               :q is null or :q = '' or
               lower(p.provider)    like lower(concat('%', :q, '%')) or
               lower(p.material)    like lower(concat('%', :q, '%')) or
               lower(p.status)      like lower(concat('%', :q, '%')) or
               lower(p.productType) like lower(concat('%', :q, '%'))
             )
           """)
    Page<Product> search(@Param("type") String type,
                         @Param("q") String q,
                         Pageable pageable);
}
