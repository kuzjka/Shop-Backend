package com.example.authserverresourceserversameapp.repository;

import com.example.authserverresourceserversameapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
