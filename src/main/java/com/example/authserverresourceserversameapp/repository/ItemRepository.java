package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Item;
import com.example.authserverresourceserversameapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

        List<Item> getAllByUser(User user);
}
