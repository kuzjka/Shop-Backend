package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> getAllByTypeIdIn(List<Long> typeIds, Pageable pageable);
    Page<Product> getAllByBrandIdIn(List<Long> brandIds, Pageable pageable);
    Page<Product> getAllByTypeIdInAndBrandIdIn(List<Long> typeIds, List<Long> brandIds, Pageable pageable);
}
