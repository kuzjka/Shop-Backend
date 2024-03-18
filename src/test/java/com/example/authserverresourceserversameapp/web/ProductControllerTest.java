package com.example.authserverresourceserversameapp.web;

import com.example.authserverresourceserversameapp.dto.BrandDto;
import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.dto.TypeDto;
import com.example.authserverresourceserversameapp.exception.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.service.AppUserDetailsService;
import com.example.authserverresourceserversameapp.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    private Type type;

    private Brand brand;
    private Product product;

    @BeforeEach
    public void setup() {
        product = Product.builder().id(1L).name("Mercedes S600").build();
        type = Type.builder().id(1L).name("Car").build();
        brand = Brand.builder().id(1L).name("Mercedes").build();

    }

    @Test
    @WithMockUser
    public void getProductsTest() throws Exception {
        ResponseProductDto dto = new ResponseProductDto();
        Product product1 = Product.builder().id(2L).name("BMW 750i").build();
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
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

    @Test
    @WithMockUser
    public void getAllTypesTest() throws Exception {
        Type type1 = Type.builder().id(2L).name("Smartphone").build();
        List<Type> types = new ArrayList<>();
        types.add(type);
        types.add(type1);
        given(productService.getAllTypes()).willReturn(types);
        this.mockMvc.perform(get("/api/type").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Car"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Smartphone"));
    }

    @Test
    @WithMockUser
    public void getAllBrandsTest() throws Exception {
        Brand brand1 = Brand.builder().id(2L).name("Apple").build();
        List<Brand> brands = new ArrayList<>();
        brands.add(brand);
        brands.add(brand1);
        given(productService.getAllBrands()).willReturn(brands);
        this.mockMvc.perform(get("/api/brand").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Mercedes"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Apple"));
    }

    @Test
    @WithMockUser
    public void addProductTest() throws Exception {
        String json = "{\"id\":0, \"name\":\"Audi A8\"}";
        given(productService.addProduct(any(ProductDto.class))).willReturn(3L);
        this.mockMvc.perform(post("/api/product").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void addTypeTest() throws Exception {
        String json = "{\"name\":\"Plane\"}";
        given(productService.addType(any(TypeDto.class))).willReturn(3L);
        this.mockMvc.perform(post("/api/type").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void addBrandTest() throws Exception {
        String json = "{\"name\":\"Boeing\"}";
        given(productService.addBrand(any(BrandDto.class))).willReturn(3L);
        this.mockMvc.perform(post("/api/brand").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3));
    }

    @Test
    @WithMockUser
    public void typeExistsExceptionTest() throws Exception {
        String json = "{\"name\":\"Car\"}";
        given(productService.addType(any(TypeDto.class)))
                .willThrow(new TypeExistsException("Car"));
        this.mockMvc.perform(post("/api/type").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("Type with name: \"Car\" already exists!"));

    }

    @Test
    @WithMockUser
    public void brandExistsExceptionTest() throws Exception {
        String json = "{\"name\":\"Mercedes\"}";
        given(productService.addBrand(any(BrandDto.class)))
                .willThrow(new BrandExistsException("Mercedes"));
        this.mockMvc.perform(post("/api/brand").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Brand with name: \"Mercedes\" already exists!"));

    }

    @Test
    @WithMockUser
    public void productExistsExceptionTest() throws Exception {
        String json = "{\"name\":\"Mercedes S600\"}";
        given(productService.addProduct(any(ProductDto.class)))
                .willThrow(new ProductExistsException("Mercedes S600"));
        this.mockMvc.perform(post("/api/product").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Product with name: \"Mercedes S600\" already exists!"));

    }

    @Test
    @WithMockUser
    public void brandOtherCanNotBeUpdatedTest() throws Exception {
        String json = "{\"name\":\"Other2\"}";
        given(productService.addBrand(any(BrandDto.class)))
                .willThrow(new BrandOtherCanNotBeDeletedOrUpdatedException());
        this.mockMvc.perform(post("/api/brand").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Brand \"Other\" can't be deleted or updated!"));

    }

    @Test
    @WithMockUser
    public void typeOtherCanNotBeUpdatedTest() throws Exception {
        String json = "{\"name\":\"Other2\"}";
        given(productService.addType(any(TypeDto.class)))
                .willThrow(new TypeOtherCanNotBeDeletedOrUpdatedException());
        this.mockMvc.perform(post("/api/type").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Type \"Other\" can't be deleted or updated!"));

    }

    @Test
    @WithMockUser
    public void typeOtherCanNotBeDeletedTest() throws Exception {
        given(productService.deleteType(anyLong()))
                .willThrow(new TypeOtherCanNotBeDeletedOrUpdatedException());
        this.mockMvc.perform(delete("/api/type/4").with(csrf()))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Type \"Other\" can't be deleted or updated!"));

    }

    @Test
    @WithMockUser
    public void brandOtherCanNotBeDeletedTest() throws Exception {
        given(productService.deleteBrand(anyLong()))
                .willThrow(new BrandOtherCanNotBeDeletedOrUpdatedException());
        this.mockMvc.perform(delete("/api/brand/9").with(csrf()))
                .andExpect(status().isConflict()).andExpect(jsonPath("$.message")
                        .value("Brand \"Other\" can't be deleted or updated!"));

    }
}
