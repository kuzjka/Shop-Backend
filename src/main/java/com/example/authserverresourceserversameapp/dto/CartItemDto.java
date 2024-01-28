package com.example.authserverresourceserversameapp.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private long cartItemId;
    private long productId;
    private int quantity;
    private long totalPrice;
}
