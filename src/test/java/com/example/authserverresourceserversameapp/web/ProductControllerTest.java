package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import com.example.authserverresourceserversameapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    AppUserDetailsService userDetailsService;

    @Test
    @WithMockUser
    public void getProductsTest() throws Exception {
        ResponseProductDto dto = new ResponseProductDto();
        Product product1 = new Product("Mercedes S600");
        product1.setId(1L);
        Product product2 = new Product("BMW 750i");
        product2.setId(2L);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        dto.setProducts(products);
        dto.setTotalProducts(2L);
        dto.setPageSize(10);
        given(productService.getProducts(0L, 0L, "name", "ASC", 0, 10)).willReturn(dto);
        this.mockMvc.perform(get("/api/product").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products", hasSize(2)))
                .andExpect(jsonPath("$.products[0].id").value(1))
                .andExpect(jsonPath("$.products[0].name").value("Mercedes S600"))
                .andExpect(jsonPath("$.products[1].id").value(2))
                .andExpect(jsonPath("$.products[1].name").value("BMW 750i"));
    }
}
