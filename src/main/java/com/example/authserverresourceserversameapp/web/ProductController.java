package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.model.User;
import com.example.authserverresourceserversameapp.service.ProductService;
import com.example.authserverresourceserversameapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/type")
    public List<Type> getAllTypes() {
        return productService.getAllTypes();
    }

    @GetMapping("/brand")
    public List<Brand> getAllBrands() {
        return productService.getAllBrands();
    }

    @GetMapping("/productType")
    public List<Type> getProductTypes() {
        return productService.getProductTypes();
    }

    @GetMapping("/productBrand")
    public List<Brand> getProductBrands(@RequestParam(required = false) List<Long> typeIds) {

        return productService.getProductBrands(typeIds);
    }

    @GetMapping(value = "/product")
    public ResponseProductDto getProducts(@RequestParam List<Long> typeIds,
                                          @RequestParam List<Long> brandIds,
                                          @RequestParam(required = false, defaultValue = "name") String sort,
                                          @RequestParam(required = false, defaultValue = "ASC") String dir,
                                          @RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "5") int size) {

        return productService.getProducts(typeIds, brandIds, sort, dir, page, size);
    }

    @PostMapping("/product")
    public long addProduct(@RequestBody ProductDto dto) {
        return productService.addProduct(dto);
    }
    @PostMapping("/type")
    public long addType(@RequestBody TypeDto dto) {
        return productService.addType(dto);
    }
    @PostMapping("/brand")
    public long addBrand(@RequestBody BrandDto dto) {
        return productService.addBrand(dto);
    }
    @PutMapping("/product")
    public long editProduct(@RequestBody ProductDto dto) {

        return productService.addProduct(dto);
    }
    @PutMapping("/type")
    public long editType(@RequestBody TypeDto dto) {
        return productService.addType(dto);
    }
    @PutMapping("/brand")
    public long editBrand(@RequestBody BrandDto dto) {
        return productService.addBrand(dto);
    }
    @DeleteMapping("/product/{id}")
    public long deleteProduct(@PathVariable long id) {
        return productService.deleteProduct(id);
    }
}
