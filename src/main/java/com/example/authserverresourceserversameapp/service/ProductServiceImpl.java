package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;

    @Override
    public List<Product> getProducts(long typeId, long brandId) {
        if (typeId == 0 && brandId == 0) {
            return productRepository.findAll();
        } else if (typeId > 0 && brandId == 0) {
            return productRepository.getAllByTypeId(typeId);
        } else if (typeId == 0 && brandId > 0) {
            return productRepository.getAllByBrandId(brandId);

        } else if (typeId > 0 && brandId > 0) {
            return productRepository.getAllByTypeIdAndBrandId(typeId, brandId);
        }
        return productRepository.findAll();
    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public List<Type> getProductTypes() {
        return typeRepository.getProductTypes();
    }

    @Override
    public List<Brand> getProductBrands(long typeId) {
        if (typeId == 0) {
            return brandRepository.getProductBrands();
        }
        return brandRepository.getAllByTypesId(typeId);
    }

    @Override
    public long addProduct(ProductDto dto) {
        Product product;
        Type type = typeRepository.findById(dto.getTypeId()).get();
        Brand brand = brandRepository.findById(dto.getBrandId()).get();
        if (dto.getId() == 0) {
            product = new Product();
        } else {
            product = productRepository.findById(dto.getId()).get();
        }
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        type.addProduct(product);
        brand.addProduct(product);
        if (!typeRepository.existsByBrands(brand))
            type.addBrand(brand);
        return productRepository.save(product).getId();
    }

    @Override
    public long deleteProduct(long productId) {
        Product product = productRepository.findById(productId).get();
        long id = product.getId();
        Brand brand = product.getBrand();
        Type type = product.getType();
        brand.removeProduct(product);
        type.removeProduct(product);
        type.removeBrand(brand);
        productRepository.deleteById(id);
        return id;
    }
}
