package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Type;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ResponseProductDto getProducts(long typeId, long brandId, String sort,
                                   String dir, int page, int size);

    List<Type> getAllTypes();

    List<Brand> getAllBrandsByTypeId(long typeId);

    List<Type> getProductTypes();

    long addProduct(ProductDto dto) throws IOException;

    long addType(TypeDto dto);

    long addBrand(BrandDto dto);

    long deleteProduct(long id) throws IOException;

    long addPhoto(PhotoDto dto);

    long deletePhoto(Photo photo);

    Photo getPhoto(long photoId);

    long deleteType(long id);

    long deleteBrand(long id);

}
