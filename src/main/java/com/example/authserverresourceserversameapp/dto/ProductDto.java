package com.example.authserverresourceserversameapp.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data

public class ProductDto {
    private long productId;
    private long typeId;
    private long brandId;

    private String name;
    private int price;
    private List<MultipartFile> photo;
}
