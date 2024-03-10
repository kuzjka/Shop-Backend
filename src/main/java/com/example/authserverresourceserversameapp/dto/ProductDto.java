package com.example.authserverresourceserversameapp.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long productId;
    private long typeId;
    private long brandId;
    private String name;
    private int price;

}
