package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.dto.OrderDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Order;
import com.example.authserverresourceserversameapp.model.User;

import java.util.List;

public interface OrderService {
    Cart addCartItem(CartItemDto dto, User user);

    Cart editCartItem(CartItemDto dto, User user);

    long removeCartItem(long productId);

    Cart getCart(User user);

    List<Order> getUserOrders(User user);

    long addOrder(OrderDto dto);

    void clearCart(User user);
}
