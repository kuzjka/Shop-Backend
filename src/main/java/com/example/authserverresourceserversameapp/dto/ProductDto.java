package com.example.authserverresourceserversameapp.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductDto {
    private long typeId;
    private long brandId;
    private long id;
    private String name;
    private int price;
    private List<byte[]> photos = new ArrayList<>();
}
