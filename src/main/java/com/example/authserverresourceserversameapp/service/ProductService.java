package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;

import java.util.List;

public interface ProductService {

    ResponseProductDto getProducts(List<Long> typeIds, List<Long> brandIds, String sort, String dir, int page, int size);

    List<Type> getAllTypes();

    List<Brand> getAllBrands();

    List<Type> getProductTypes();

    List<Brand> getProductBrands(List<Long> typeIds);

    long addProduct(ProductDto dto);

    long deleteProduct(long id);
}
