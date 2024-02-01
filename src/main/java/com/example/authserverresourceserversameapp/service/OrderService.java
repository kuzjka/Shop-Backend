package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.User;

public interface OrderService {

    Cart addCartItem(CartItemDto dto, User user);

    Cart editCartItem(CartItemDto dto, User user);

    long removeCartItem(long productId);

    Cart getCart(User user);

}
