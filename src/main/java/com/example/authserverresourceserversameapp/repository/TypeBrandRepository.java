package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.model.TypeBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeBrandRepository extends JpaRepository<TypeBrand, Long> {

    TypeBrand findFirstByTypeAndBrand(Type type, Brand brand);
}
