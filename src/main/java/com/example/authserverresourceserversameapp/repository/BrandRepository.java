package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

        List<Brand> getAllByTypesId(long typeId);

        @Query("select b from Brand b where (select count (p) from b.products p) > 0")
        List<Brand> getProductBrands();
}
