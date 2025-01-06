package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.model.TypeBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeBrandRepository extends JpaRepository<TypeBrand, Long> {


    Optional<TypeBrand> findByTypeAndBrand(Type type, Brand brand);
}
