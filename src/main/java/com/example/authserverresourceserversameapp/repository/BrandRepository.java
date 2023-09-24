package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> getAllByTypesId(long typeId);
    Brand getAllByName(String name);
}


