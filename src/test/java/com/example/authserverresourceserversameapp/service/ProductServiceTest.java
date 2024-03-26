package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.PhotoDto;
import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.PhotoRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private TypeRepository typeRepository;

    @Mock
    private PhotoRepository photoRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    private Product product;
    private Type type;
    private Brand brand;

    private Photo photo;
    private List<Photo> photos;
    PhotoDto photoDto;
    List<MultipartFile> files;
    MultipartFile mockMultipartFile;
    byte[] bytes;


    @BeforeEach
    public void setup() {
        photo = Photo.builder().id(1L).name("photo1.jpg")
                .url("http://localhost:8080/images/photo_1_photo1.jpg").build();
        photos = new ArrayList<>();
        photos.add(photo);
        product = Product.builder().id(1L).name("Mercedes S600").photos(photos).build();
        product.addPhoto(photo);
        type = Type.builder().id(1L).name("Car").build();
        brand = Brand.builder().id(1L).name("Mercedes").build();
        photoDto = new PhotoDto();
        photoDto.setProductId(1L);
    }

    @Test
    void addPhotoTest() throws IOException {
        Optional<Product> product1 = Optional.of(product);
        files = new ArrayList<>();
        bytes = new byte[2];


        mockMultipartFile = new MockMultipartFile(String.valueOf(photo.getName()), photo.getName(),
                String.valueOf(MediaType.IMAGE_JPEG), bytes);
        files.add(mockMultipartFile);
        photoDto.setPhotos(files);
        given(productRepository.findById(anyLong())).willReturn(product1);
        given(photoRepository.findByNameAndProductId(anyString(), anyLong())).willReturn(photo);
        given(photoRepository.save(any(Photo.class))).willReturn(photo);
        given(productRepository.save(any(Product.class))).willReturn(product);
        long id = productService.addPhoto(photoDto);
        assertThat(id).isEqualTo(1L);
    }
    @Test
    public void deletePhotoTest() {
        doNothing().when(photoRepository).deleteById(1L);
        long id = productService.deletePhotoById(1L);
        assertThat(id).isEqualTo(1L);
    }
    @Test
    public void getProductsTest() {
        Product product1 = Product.builder().id(2L).name("BMW 750i").build();
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
        Type type1 = Type.builder().id(2L).name("Smartphone").build();
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
        Brand brand1 = Brand.builder().id(2L).name("Apple").build();
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
