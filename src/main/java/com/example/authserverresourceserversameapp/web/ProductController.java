package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.BrandDto;
import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.dto.TypeDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public List<Brand> getProductBrands(@RequestParam(required = false, defaultValue = "0") long typeId) {

        return productService.getProductBrands(typeId);
    }

    @GetMapping(value = "/product")
    public ResponseProductDto getProducts(@RequestParam(required = false, defaultValue = "0") long typeId,
                                          @RequestParam(required = false, defaultValue = "0") long brandId,
                                          @RequestParam(required = false, defaultValue = "name") String sort,
                                          @RequestParam(required = false, defaultValue = "ASC") String dir,
                                          @RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {

        return productService.getProducts(typeId, brandId, sort, dir, page, size);
    }

    @PostMapping(value = "/product")
    public long addProduct(ProductDto dto) throws IOException {
        return productService.addProduct(dto);
    }

    @PutMapping("/product")
    public long editProduct(ProductDto dto) throws IOException {
        return productService.addProduct(dto);
    }

    @DeleteMapping("/product/{id}")
    public long deleteProduct(@PathVariable long id) throws IOException {
        return productService.deleteProduct(id);
    }
    @DeleteMapping("/photo/{id}")
    public long deletePhoto(@PathVariable long id) {
        return productService.deletePhoto(id);
    }

    @PostMapping("/type")
    public long addType(@RequestBody TypeDto dto) {
        return productService.addType(dto);
    }

    @PutMapping("/type")
    public long editType(@RequestBody TypeDto dto) {
        return productService.addType(dto);
    }

    @DeleteMapping("/type/{id}")
    public long deleteType(@PathVariable long id) {
        return productService.deleteType(id);
    }

    @PostMapping("/brand")
    public long addBrand(@RequestBody BrandDto dto) {
        return productService.addBrand(dto);
    }

    @PutMapping("/brand")
    public long editBrand(@RequestBody BrandDto dto) {
        return productService.addBrand(dto);
    }

    @DeleteMapping("/brand/{id}")
    public long deleteBrand(@PathVariable long id) {
        return productService.deleteBrand(id);
    }


}
