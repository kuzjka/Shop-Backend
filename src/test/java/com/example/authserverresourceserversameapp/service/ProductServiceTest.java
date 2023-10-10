package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.model.Product;
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

    @BeforeEach
    public void setup() {
        product = Product.builder()
                .id(1L)
                .name("Mercedes S600")
                .build();
    }

    @Test
    public void getProductsTest() {
        Product product1 = Product.builder()
                .id(2L)
                .name("BMW 750i")
                .build();
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
}
