package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Cart;

public class CartResponse {
    private long cartId;
    private Cart cart;

    public CartResponse() {
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
