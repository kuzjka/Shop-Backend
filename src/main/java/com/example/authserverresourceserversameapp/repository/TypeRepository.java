package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    boolean existsByBrands(Brand brand);

    @Query("select  t from Type t  where  ( select count(p) from t.products p)>0")
    List<Type> getProductTypes();
}
