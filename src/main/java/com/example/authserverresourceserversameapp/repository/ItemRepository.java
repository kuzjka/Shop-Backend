package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {


}
