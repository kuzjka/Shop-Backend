package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ItemDto;
import com.example.authserverresourceserversameapp.model.Cart;
import com.example.authserverresourceserversameapp.model.Item;
import com.example.authserverresourceserversameapp.model.User;

import java.util.List;

public interface OrderService {
    Cart addItem(ItemDto dto, User user);

    List<Item> getUserItems(User user);

    long deleteItem(long itemId);
}
