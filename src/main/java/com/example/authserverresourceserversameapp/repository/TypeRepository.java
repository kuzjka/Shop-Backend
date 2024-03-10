package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type getAllByName(String name);

    @Query("select  t from Type t  where  ( select count(p) from t.products p)>0 order by t.name")
    List<Type> getProductTypes();
}
