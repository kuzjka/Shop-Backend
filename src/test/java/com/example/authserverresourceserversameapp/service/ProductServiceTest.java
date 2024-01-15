package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private TypeRepository typeRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;
    private Type type;
    private Brand brand;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);
        product.setName("Mercedes S600");

        type = new Type("Car");
        type.setId(1L);
        brand = new Brand("Mercedes");
        brand.setId(1L);
    }

    @Test
    public void getProductsTest() {
        Product product1 = new Product();
        product1.setId(2L);
        product1.setName("BMW 750i");

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        ResponseProductDto dto = new ResponseProductDto();
        dto.setProducts(products);
        Page<Product> page = new PageImpl<>(products);
        given(productRepository.findAll(PageRequest.of(0, 10, Sort.Direction.valueOf("ASC"),
                "name"))).willReturn(page);

        dto = productService.getProducts(0L, 0L, "name", "ASC", 0, 10);
        assertThat(dto).isNotNull();
        assertThat(dto.getProducts().size()).isEqualTo(2);
        assertThat(dto.getProducts().get(0).getId()).isEqualTo(1L);
        assertThat(dto.getProducts().get(0).getName()).isEqualTo("Mercedes S600");
        assertThat(dto.getProducts().get(1).getId()).isEqualTo(2L);
        assertThat(dto.getProducts().get(1).getName()).isEqualTo("BMW 750i");

    }

    @Test
    public void getTypesTest() {
        Type type1 = new Type("Smartphone");
        type1.setId(2L);

        List<Type> types = new ArrayList<>();
        types.add(type);
        types.add(type1);


        given(typeRepository.findAll()).willReturn(types);
        List<Type> serviceTypes = productService.getAllTypes();
        assertThat(serviceTypes).isNotNull();
        assertThat(serviceTypes.size()).isEqualTo(2);
        assertThat(serviceTypes.get(0).getId()).isEqualTo(1L);
        assertThat(serviceTypes.get(0).getName()).isEqualTo("Car");
        assertThat(serviceTypes.get(1).getId()).isEqualTo(2L);
        assertThat(serviceTypes.get(1).getName()).isEqualTo("Smartphone");

    }

    @Test
    public void getBrandsTest() {
        Brand brand1 = new Brand("Apple");
        brand1.setId(2L);

        List<Brand> brands = new ArrayList<>();
        brands.add(brand);
        brands.add(brand1);


        given(brandRepository.findAll()).willReturn(brands);
        List<Brand> serviceBrands = productService.getAllBrands();
        assertThat(serviceBrands).isNotNull();
        assertThat(serviceBrands.size()).isEqualTo(2);
        assertThat(serviceBrands.get(0).getId()).isEqualTo(1L);
        assertThat(serviceBrands.get(0).getName()).isEqualTo("Mercedes");
        assertThat(serviceBrands.get(1).getId()).isEqualTo(2L);
        assertThat(serviceBrands.get(1).getName()).isEqualTo("Apple");

    }
}
