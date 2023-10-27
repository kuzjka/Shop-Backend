package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.BrandDto;
import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.dto.TypeDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseProductDto getProducts(long typeId, Long brandId, String sort, String dir, int page, int size);

    List<Type> getAllTypes();

    List<Brand> getAllBrands();

    List<Type> getProductTypes();

    List<Brand> getProductBrands(long typeId);

    long addProduct(ProductDto dto) throws IOException;

    long addType(TypeDto dto);

    long addBrand(BrandDto dto);

    long deleteProduct(long id) throws IOException;

    long deleteType(long id);

    long deleteBrand(long id);
}
