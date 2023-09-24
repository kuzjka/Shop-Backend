package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.model.Product;
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

    @Test
    @WithMockUser
    public void getProductsTest() throws Exception {
        ResponseProductDto dto = new ResponseProductDto();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Mercedes S600");
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("BMW 750i");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        List<Long> typeIds = new ArrayList<>();
        typeIds.add(1L);

        List<Long> brandIds = new ArrayList<>();
        brandIds.add(1L);
        brandIds.add(2L);
        dto.setProducts(products);
        dto.setTotalProducts(2L);
        dto.setPageSize(10);
        given(productService.getProducts(1L, brandIds, "name", "ASC", 0, 10)).willReturn(dto);

          this.mockMvc.perform(get("/api/product?typeId=1&brandIds=1,2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.products", hasSize(2)))

                .andExpect(jsonPath("$.products[0].id").value(1))
                .andExpect(jsonPath("$.products[0].name").value("Mercedes S600"))
                .andExpect(jsonPath("$.products[1].id").value(2))
                .andExpect(jsonPath("$.products[1].name").value("BMW 750i"));



    }
}
