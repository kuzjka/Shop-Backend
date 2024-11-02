package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Item;

import java.util.List;

public class CartDto {

    private List<Item> items;

    public CartDto() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
