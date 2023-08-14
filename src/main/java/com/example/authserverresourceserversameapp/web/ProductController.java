package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.dto.RegisterDto;
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
@RequiredArgsConstructor
public class ProductController {
    private final UserService userService;
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
    public List<Brand> getProductBrands(@RequestParam(required = false, defaultValue = "0") long typeId) {
        return productService.getProductBrands(typeId);
    }
    @GetMapping("/product")
    public List<Product> getProducts(@RequestParam(required = false, defaultValue = "0") long typeId,
                                     @RequestParam(required = false, defaultValue = "0") long brandId,
                                     @RequestParam(required = false, defaultValue = "name") String sort,
                                     @RequestParam(required = false, defaultValue = "ASC") String dir,
                                     @RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "20") int size
                                     ) {
        return productService.getProducts(typeId, brandId, sort, dir, page, size);
    }

    @PostMapping("/register")
    public User addUser(@RequestBody RegisterDto user) {
        return userService.addUser(user);
    }

    @PostMapping("/product")
    public long addProduct(@RequestBody ProductDto dto) {
        return productService.addProduct(dto);
    }

    @PutMapping("/product")
    public long editProduct(@RequestBody ProductDto dto) {
        return productService.addProduct(dto);

    }
    @DeleteMapping("/product/{id}")
    public long deleteProduct(@PathVariable long id){
        return productService.deleteProduct(id);
    }
}
