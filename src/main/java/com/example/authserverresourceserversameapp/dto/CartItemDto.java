package com.example.authserverresourceserversameapp.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private long itemId;
    private long productId;
    private int quantity;
}
