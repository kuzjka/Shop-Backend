package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.User;

public interface OrderService {

    Cart addToCart(CartItemDto dto, User user);

    long removeFromCart(long productId);

    Cart getCart(User user);

}
