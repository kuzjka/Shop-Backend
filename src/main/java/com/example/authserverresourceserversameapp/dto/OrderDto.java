package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Cart;

public class OrderDto {

    private Cart cart;

    public OrderDto() {
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
