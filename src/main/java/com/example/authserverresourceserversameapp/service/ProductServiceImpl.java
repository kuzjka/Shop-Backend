package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.BrandDto;
import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.dto.TypeDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;

    @Override
    public ResponseProductDto getProducts(long typeId, List<Long> brandIds, String sort,
                                          String dir, int page, int size) {

        ResponseProductDto dto = new ResponseProductDto();
        Page<Product> products = null;

        if (sort.equals("type")) {
            sort = "type.name";
        }
        if (sort.equals("brand")) {
            sort = "brand.name";
        }
        if (typeId == 0 && brandIds.size() == 0) {

            products = productRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
        } else if (typeId > 0 && brandIds.size() == 0) {
            products = productRepository.getAllByTypeId(typeId,
                    PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));

        } else if (typeId == 0 && brandIds.size() > 0) {
            products = productRepository.getAllByBrandIdIn(brandIds,
                    PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));


        } else if (typeId > 0 && brandIds.size() > 0) {
            products = productRepository.getAllByTypeIdAndBrandIdIn(typeId,
                    brandIds, PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
        }

        dto.setProducts(products.getContent());
        dto.setPageSize(products.getSize());
        dto.setTotalProducts(products.getTotalElements());
        return dto;
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
    public long addType(TypeDto dto) {
        Type type;

        if (dto.getId() <= 0) {
            type = new Type();
        } else {
            type = typeRepository.findById(dto.getId()).get();
        }
        type.setName(dto.getName());
        return typeRepository.save(type).getId();
    }

    @Override
    public long addBrand(BrandDto dto) {
        Brand brand;

        if (dto.getId() <= 0) {
            brand = new Brand();
        } else {
            brand = brandRepository.findById(dto.getId()).get();
        }
        brand.setName(dto.getName());
        return brandRepository.save(brand).getId();
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

    @Override
    public long deleteType(long typeId) {
        Type type = typeRepository.findById(typeId).get();
        long id = type.getId();
        Type other = typeRepository.getAllByName("Other");
        List<Product> products = type.getProducts();
        products.forEach(x -> x.setType(other));
        typeRepository.deleteById(id);
        return id;
    }

    @Override
    public long deleteBrand(long brandId) {
        Brand brand = brandRepository.findById(brandId).get();
        long id = brand.getId();
        Brand other = brandRepository.getAllByName("Other");
        List<Product> products = brand.getProducts();
        products.forEach(x -> x.setBrand(other));
        brandRepository.deleteById(id);
        return id;
    }
}
