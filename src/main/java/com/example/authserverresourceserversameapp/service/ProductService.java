package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(long typeId, long brandId);

    List<Type> getTypes();

    List<Brand> getBrands(long typeId);

    long addProduct(ProductDto dto);
}
