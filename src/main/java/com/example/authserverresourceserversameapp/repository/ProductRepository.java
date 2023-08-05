package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getAllByTypeId(long typeId);
    List<Product> getAllByBrandId(long brandId);
    List<Product> getAllByTypeIdAndBrandId(long typeId, long brandId);
}
