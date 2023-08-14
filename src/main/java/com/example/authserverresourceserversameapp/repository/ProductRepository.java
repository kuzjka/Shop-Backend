package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    List<Product> getAllByTypeId(long typeId, Pageable pageable);
    List<Product> getAllByBrandId(long brandId, Pageable pageable);
    List<Product> getAllByTypeIdAndBrandId(long typeId, long brandId, Pageable pageable);
}
