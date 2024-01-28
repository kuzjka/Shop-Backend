package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Product;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseProductDto {
    private List<Product> products = new ArrayList<>();
    private int pageSize;
    private long totalProducts;
}
