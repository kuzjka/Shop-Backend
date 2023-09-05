package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ResponseProductDto {

    private List<Product> products = new ArrayList<>();
    private int pageSize;
    private long totalProducts;


}
