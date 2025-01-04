package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.exception.BrandExistsException;
import com.example.authserverresourceserversameapp.exception.ProductExistsException;
import com.example.authserverresourceserversameapp.exception.TypeExistsException;
import com.example.authserverresourceserversameapp.model.Brand;
import com.example.authserverresourceserversameapp.model.Photo;
import com.example.authserverresourceserversameapp.model.Product;
import com.example.authserverresourceserversameapp.model.Type;
import com.example.authserverresourceserversameapp.repository.BrandRepository;
import com.example.authserverresourceserversameapp.repository.PhotoRepository;
import com.example.authserverresourceserversameapp.repository.ProductRepository;
import com.example.authserverresourceserversameapp.repository.TypeRepository;
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
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final String imageDir;
    private final String imageUrl;
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;
    private final PhotoRepository photoRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              TypeRepository typeRepository,
                              BrandRepository brandRepository,
                              PhotoRepository photoRepository) {
        this.imageDir = "src/main/webapp/WEB-INF/images/";
        this.imageUrl = "http://localhost:8080/images/";
        this.productRepository = productRepository;
        this.typeRepository = typeRepository;
        this.brandRepository = brandRepository;
        this.photoRepository = photoRepository;
    }

    /**
     * gets products from database according to request parameters
     *
     * @param typeId  id of type
     * @param brandId id of brand
     * @param sort    field for sorting
     * @param dir     direction of sorting
     * @param page    index of page
     * @param size    size of page
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
        assert products != null;
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
    public List<Type> getAllTypes(String sort, String dir) {
        return typeRepository.findAll(Sort.by(Sort.Direction.fromString(dir), sort));
    }

    /**
     * gets all brands from database with particular type id
     *
     * @param typeId id of type
     * @return list of brands
     */
    @Override
    public List<Brand> getAllBrandsByTypeId(long typeId) {
        if (typeId == 0) {
            return brandRepository.findAll(Sort.by("id"));
        }
        return brandRepository.getAllByTypesTypeIdOrderById(typeId);
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
        Optional<Type> type = typeRepository.findById(dto.getTypeId());
        Optional<Brand> brand = brandRepository.findById(dto.getBrandId());
        if (dto.getId() == 0) {
            product = new Product();
        } else if (dto.getId() > 0) {
            product = productRepository.findById(dto.getId()).get();
            if (type.isPresent()) {
                type.get().removeProduct(product);
            }
            if (brand.isPresent()) {
                brand.get().removeProduct(product);
            }
        }
        assert product != null;
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        if (type.isPresent()) {
            type.get().addProduct(product);
        }
        if (brand.isPresent()) {
            brand.get().addProduct(product);
        }
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
        Type type = null;
        if (dto.getId() == 0) {
            type = new Type();
        } else if (dto.getId() > 0) {
            type = typeRepository.findById(dto.getId()).get();
        }
        assert type != null;
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
        Brand brand = null;
        Type type = typeRepository.findById(dto.getTypeId()).get();
        if (dto.getId() == 0) {
            brand = new Brand();
            type.addBrand(brand);
        } else if (dto.getId() > 0) {
            brand = brandRepository.findById(dto.getId()).get();
        }
        assert brand != null;
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
        Type type = product.getType();
        Brand brand = product.getBrand();
        if (type != null) {
            type.removeProduct(product);
        }
        if (brand != null) {
            brand.removeProduct(product);
        }
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
        Path photoPath;
        for (MultipartFile file : dto.getPhotos()) {
            Photo photo = photoRepository.findByNameAndProductId(file.getOriginalFilename(), product.getId());
            if (photo != null) {
                deletePhoto(photo);
            }
            Photo newPhoto = new Photo();
            long photoId = photoRepository.save(newPhoto).getId();
            newPhoto.setName(file.getOriginalFilename());
            newPhoto.setUrl(imageUrl + "photo_" + photoId + "_" + file.getOriginalFilename());
            product.addPhoto(newPhoto);
            photoPath = Paths.get(imageDir + "photo_" + photoId + "_" + file.getOriginalFilename());
            try {
                Files.write(photoPath, file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return productRepository.save(product).getId();
    }

    /**
     * deletes photo from database and file system
     *
     * @param photo photo to delete
     * @return id of deleted photo
     */
    @Override
    public long deletePhoto(Photo photo) {
        long photoId = photo.getId();
        int index = photo.getUrl().indexOf("photo_");
        photoRepository.delete(photo);
        if (index > -1) {
            String file = photo.getUrl().substring(index);
            Path path = Paths.get(imageDir + file);
            if (path.toFile().exists())
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return photoId;
    }

    /**
     * gets photo with particular id from database
     *
     * @param photoId id of photo
     * @return photo with particular id
     */
    @Override
    public Photo getPhoto(long photoId) {
        return photoRepository.findById(photoId).get();
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
        long id = type.getId();
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
        long id = brand.getId();
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
            deletePhoto(photo);
        }
    }
}
