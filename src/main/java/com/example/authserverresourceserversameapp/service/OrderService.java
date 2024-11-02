package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.model.Item;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.User;

import java.util.List;

public interface OrderService {
    Item addItem(ItemDto dto, User user);

    List<Item> getItem(User user);

    List<Cart> getUserCarts(User user);

//    long addOrder(CartDto dto);

    long deleteItem(long itemId);
}
