package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartDto;

import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Order;
import com.example.authserverresourceserversameapp.model.User;

import java.util.List;

public interface OrderService {
    Cart addCart(CartDto dto, User user);

    List<Cart> getCart(User user);

    List<Order> getUserOrders(User user);

    long addOrder(OrderDto dto);

    }
