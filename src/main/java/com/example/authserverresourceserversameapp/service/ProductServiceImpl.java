package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final String BASE_DIR = "src/main/webapp/WEB-INF/images/";
    private static final String BASE_URL = "http://localhost:8080/images/";
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;
    private final PhotoRepository photoRepository;

    /**
     * gets products from database according to request parameters
     *
     * @param typeId id of type
     * @param brandId id of brand
     * @param sort field for sorting
     * @param dir direction of sorting
     * @param page index of page
     * @param size size of page
     * @return dto with list of products
     */
    @Override
    public ResponseProductDto getProducts(long typeId, long brandId, String sort,
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

    /**
     * gets all types from database
     *
     * @return list of types
     */
    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    /**
     * gets all brands from database
     *
     * * @return list of brands
     */
    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * gets all types binding to products from database
     *
     * @return list of types
     */
    @Override
    public List<Type> getProductTypes() {
        return typeRepository.getProductTypes();
    }

    /**
     * gets all brands binding to products from database
     *
     * @param typeId id of type
     * @return list of brands
     */
    @Override
    public List<Brand> getProductBrands(long typeId) {
        List<Product> products = productRepository.findAllByTypeId(typeId);
        List<Brand> brands = brandRepository.findAllByProductsInOrderByName(products);
        return brands;
    }

    /**
     * adds new product to database or updates existing
     *
     * @param dto dto for adding new product or updating existing product
     * @return id of created or updated product
     */
    public long addProduct(ProductDto dto) {
        Product product = null;
        if (productRepository.findByName(dto.getName()) != null && dto.getId() == 0) {
            throw new ProductExistsException(dto.getName());
        }
        Type type = typeRepository.getById(dto.getTypeId());
        Brand brand = brandRepository.getById(dto.getBrandId());
        if (dto.getId() == 0) {
            product = new Product();
        } else if (dto.getId() > 0) {
            product = productRepository.findById(dto.getId()).get();
            type.removeProduct(product);
            brand.removeProduct(product);
        }
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        type.addProduct(product);
        brand.addProduct(product);
        return productRepository.save(product).getId();
    }

    /**
     * adds new type to database or updates existing
     *
     * @param dto dto for adding new type or updating existing type
     * @return id of created or updated type
     */
    @Override
    public long addType(TypeDto dto) {
        if (typeRepository.getAllByName(dto.getName()) != null) {
            throw new TypeExistsException(dto.getName());
        }
        Type type;
        if (dto.getId() == 0) {
            type = new Type();
        } else {
            type = typeRepository.findById(dto.getId()).get();
            if (type.getName().equals("Other")) {
                throw new TypeOtherCanNotBeDeletedOrUpdatedException();
            }
        }
        type.setName(dto.getName());
        return typeRepository.save(type).getId();
    }
    /**
     * adds new brand to database or updates existing
     *
     * @param dto dto for adding new brand or updating existing brand
     * @return id of created or updated brand
     */
    @Override
    public long addBrand(BrandDto dto) {
        if (brandRepository.getAllByName(dto.getName()) != null) {
            throw new BrandExistsException(dto.getName());
        }
        Brand brand;
        if (dto.getId() == 0) {
            brand = new Brand();
        } else {
            brand = brandRepository.findById(dto.getId()).get();
            if (brand.getName().equals("Other")) {
                throw new BrandOtherCanNotBeDeletedOrUpdatedException();
            }
        }
        brand.setName(dto.getName());
        return brandRepository.save(brand).getId();
    }

    /**
     * deletes product from database
     *
     * @param productId id of product
     * @return id of deleted product
     */
    @Override
    public long deleteProduct(long productId) {
        Product product = productRepository.findById(productId).get();
        removePhotos(product);
        long id = product.getId();
        Brand brand = product.getBrand();
        Type type = product.getType();
        brand.removeProduct(product);
        type.removeProduct(product);
        productRepository.deleteById(id);
        return id;
    }

    /**
     * adds new photos to existing product
     *
     * @param dto dto with list of new photos
     * @return id of product to which new photos has been added
     */
    @Override
    public long addPhoto(PhotoDto dto) {
        Product product = productRepository.findById(dto.getProductId()).get();
        for (MultipartFile file : dto.getPhotos()) {
            Photo photo = photoRepository.findByNameAndProductId(file.getOriginalFilename(), product.getId());
            if (photo != null) {
                deletePhoto(photo.getId());
            }
            Photo newPhoto = new Photo();
            long photoId = photoRepository.save(newPhoto).getId();
            newPhoto.setName(file.getOriginalFilename());
            newPhoto.setUrl(BASE_URL + "photo_" + photoId + "_" + file.getOriginalFilename());
            product.addPhoto(newPhoto);
            Path photoPath = Paths.get(BASE_DIR + "photo_" + photoId + "_" + file.getOriginalFilename());
            try {
                Files.write(photoPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return productRepository.save(product).getId();
    }

    /**
     * deletes photo by id
     *
     * @param photoId id of photo to delete
     * @return id of deleted photo
     */
    @Override
    public long deletePhoto(long photoId) {
        Photo photo = photoRepository.findById(photoId).get();
        Product product = photo.getProduct();
        product.removePhoto(photo);
        long id = photo.getId();
        photoRepository.delete(photo);
        int index = photo.getUrl().indexOf("photo_");
        if (index > -1) {
            String file = photo.getUrl().substring(index);
            Path path = Paths.get(BASE_DIR + file);
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return id;
    }

    /**
     * deletes type from database by id
     *
     * @param typeId id of type to delete
     * @return id of deleted type
     */
    @Override
    public long deleteType(long typeId) {
        Type type = typeRepository.findById(typeId).get();
        if (type.getName().equals("Other")) {
            throw new TypeOtherCanNotBeDeletedOrUpdatedException();
        }
        long id = type.getId();
        Type other = typeRepository.getAllByName("Other");
        List<Product> products = new ArrayList<>(type.getProducts());
        for (Product product : products) {
            type.removeProduct(product);
            other.addProduct(product);
        }
        typeRepository.deleteById(id);
        return id;
    }

    /**
     * deletes brand from database by id
     *
     * @param brandId id of brand to delete
     * @return id of deleted brand
     */
    @Override
    public long deleteBrand(long brandId) {
        Brand brand = brandRepository.findById(brandId).get();
        if (brand.getName().equals("Other")) {
            throw new BrandOtherCanNotBeDeletedOrUpdatedException();
        }
        long id = brand.getId();
        Brand other = brandRepository.getAllByName("Other");
        List<Product> products = new ArrayList<>(brand.getProducts());
        for (Product product : products) {
            brand.removeProduct(product);
            other.addProduct(product);
        }
        brandRepository.deleteById(id);
        return id;
    }

    /**
     * removes all photos from particular product
     *
     * @param product product from which all photos should be removed
     */
    public void removePhotos(Product product) {
        List<Photo> photos = new ArrayList<>(product.getPhotos());
        for (Photo photo : photos) {
            deletePhoto(photo.getId());
        }
    }
}
