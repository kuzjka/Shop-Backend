package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand getOneByName(String name);

    Brand getOneByTypesType(Type type);

    List<Brand> getAllByNameNotLikeOrderByName(String name);

    List<Brand> getAllByTypesTypeIdOrderByName(long typeId);
}


