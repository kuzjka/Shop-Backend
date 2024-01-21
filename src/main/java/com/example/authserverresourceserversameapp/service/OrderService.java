package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.CartItem;
import com.example.authserverresourceserversameapp.model.User;

import java.util.List;

public interface OrderService {

    Cart addToCart(CartItemDto dto, User user);

    long removeFromCart(long productId);

    Cart getCart(User user);

}
