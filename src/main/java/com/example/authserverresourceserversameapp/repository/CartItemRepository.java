package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.CartItem;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {




}
