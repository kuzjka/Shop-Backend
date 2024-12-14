package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.TypeBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand getAllByName(String name);

    List<Brand> getAllByTypesTypeId(long typeId);
}


