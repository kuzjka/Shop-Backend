package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.BrandDto;
import com.example.authserverresourceserversameapp.dto.ProductDto;
import com.example.authserverresourceserversameapp.dto.ResponseProductDto;
import com.example.authserverresourceserversameapp.dto.TypeDto;
import com.example.authserverresourceserversameapp.exception.*;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.PhotoRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
import lombok.RequiredArgsConstructor;


import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;
    private final PhotoRepository photoRepository;

    private static final String BASE_DIR = "src/main/webapp/WEB-INF/images/";
    private static final String BASE_URL = "http://localhost:8080/images/";

    @Override
    public ResponseProductDto getProducts(long typeId, Long brandId, String sort,
                                          String dir, int page, int size) {
        ResponseProductDto dto = new ResponseProductDto();
        Page<Product> products = null;
        if (sort.equals("type")) {
            sort = "type.name";
        }
        if (sort.equals("brand")) {
            sort = "brand.name";
        }
        if (typeId == 0 && brandId == 0) {
            products = productRepository.findAll(PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
        } else if (typeId > 0 && brandId == 0) {
            products = productRepository.getAllByTypeId(typeId,
                    PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));

        } else if (typeId == 0 && brandId > 0) {
            products = productRepository.getAllByBrandId(brandId,
                    PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));

        } else if (typeId > 0 && brandId > 0) {
            products = productRepository.getAllByTypeIdAndBrandId(typeId,
                    brandId, PageRequest.of(page, size, Sort.Direction.fromString(dir), sort));
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
    public long addProduct(ProductDto dto) throws IOException {

        Product product = null;
        Photo photo = null;
        if (dto.getId() == 0 && productRepository.getByName(dto.getName()) != null) {
            throw new ProductExistsException("Product with name:\"" + dto.getName() + "\" already exists!");
        } else if (dto.getId() == 0 && productRepository.getByName(dto.getName()) == null) {
            product = new Product();
            photo = new Photo();
            long photoId = photoRepository.save(photo).getId();
            Path path = Paths.get(BASE_DIR + dto.getName() + "_" + photoId + ".jpg");
            InputStream in = new ByteArrayInputStream(dto.getPhoto());
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

            photo.setUrl(BASE_URL + dto.getName() + "_" + photoId + ".jpg");

        } else if (dto.getId() > 0) {
            product = productRepository.findById(dto.getId()).get();
            photo = new Photo();
            long photoId = photoRepository.save(photo).getId();
            Path path = Paths.get(BASE_DIR + dto.getName() + "_" + photoId + ".jpg");
            InputStream in = new ByteArrayInputStream(dto.getPhoto());
            Files.copy(in, path, StandardCopyOption.REPLACE_EXISTING);

            photo.setUrl(BASE_URL + dto.getName() + "_" + photoId + ".jpg");

//            FileUtils.writeByteArrayToFile(new File(BASE_DIR + dto.getName() + photoId + ".jpg"),
//                    dto.getPhoto());
        }
        product.setPhoto(photo);
        Type type = typeRepository.findById(dto.getTypeId()).get();
        Brand brand = brandRepository.findById(dto.getBrandId()).get();
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
        if (typeRepository.getAllByName(dto.getName()) != null) {
            throw new TypeExistsException("Type with name: \"" + dto.getName() + "\" already exists!");
        }
        Type type;
        if (dto.getId() == 0) {
            type = new Type();
        } else {
            type = typeRepository.findById(dto.getId()).get();
        }
        type.setName(dto.getName());
        return typeRepository.save(type).getId();
    }

    @Override
    public long addBrand(BrandDto dto) {
        if (brandRepository.getAllByName(dto.getName()) != null) {
            throw new BrandExistsException("Brand with name: \"" + dto.getName() + "\" already exists!");
        }
        Brand brand;
        if (dto.getId() == 0) {
            brand = new Brand();
        } else {
            brand = brandRepository.findById(dto.getId()).get();
        }
        brand.setName(dto.getName());
        return brandRepository.save(brand).getId();
    }

    @Override
    public long deleteProduct(long productId) throws IOException {
        Product product = productRepository.findById(productId).get();
        String url = product.getPhoto().getUrl();
        String[] split = url.split("/");
        String file = split[split.length - 1];
        long id = product.getId();
        Brand brand = product.getBrand();
        Type type = product.getType();
        brand.removeProduct(product);
        type.removeProduct(product);
        type.removeBrand(brand);
        productRepository.deleteById(id);
        Path path = Paths.get(BASE_DIR + file);
        Files.delete(path);
        return id;
    }

    @Override
    public long deleteType(long typeId) {
        Type type = typeRepository.findById(typeId).get();
        if (type.getName().equals("Other")) {
            throw new TypeOtherCantBeDeletedException("Type \"Other\" can not be deleted!");
        }
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
        if (brand.getName().equals("Other")) {
            throw new BrandOtherCantBeDeletedException("Brand \"Other\" can not be deleted!");
        }
        long id = brand.getId();
        Brand other = brandRepository.getAllByName("Other");
        List<Product> products = brand.getProducts();
        products.forEach(x -> x.setBrand(other));
        brandRepository.deleteById(id);
        return id;
    }
}
