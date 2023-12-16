package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.CartItemDto;
import com.example.authserverresourceserversameapp.model.CartItem;
import com.example.authserverresourceserversameapp.model.User;

import java.util.List;

public interface OrderService {

    CartItemDto addToCart(CartItemDto dto, User user);

    List<CartItem> getCartItems(User user);

}
