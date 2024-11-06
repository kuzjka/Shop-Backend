package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.User;

public interface OrderService {
    Cart addItem(ItemDto dto, User user);

    Cart editItem(ItemDto dto);

    Cart getUserCart(User user);

    long deleteItem(long itemId);
}
