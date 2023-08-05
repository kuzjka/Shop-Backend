package com.example.authserverresourceserversameapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private long typeId;
    private long brandId;
    private long id;
    private String name;

}
