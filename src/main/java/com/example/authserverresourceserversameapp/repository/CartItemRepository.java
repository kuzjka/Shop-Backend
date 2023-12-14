package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
