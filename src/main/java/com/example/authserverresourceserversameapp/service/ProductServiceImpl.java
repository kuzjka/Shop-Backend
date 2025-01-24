package com.example.authserverresourceserversameapp.service;

import com.example.authserverresourceserversameapp.dto.*;
import com.example.authserverresourceserversameapp.exception.*;
import com.example.authserverresourceserversameapp.model.*;
import com.example.authserverresourceserversameapp.repository.*;
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
public class ProductServiceImpl implements ProductService {
    private final String imageDir;
    private final String imageUrl;
    private final ProductRepository productRepository;
    private final TypeRepository typeRepository;
    private final BrandRepository brandRepository;
    private final PhotoRepository photoRepository;
    private final TypeBrandRepository typeBrandRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              TypeRepository typeRepository,
                              BrandRepository brandRepository,
                              PhotoRepository photoRepository, TypeBrandRepository typeBrandRepository) {
        this.typeBrandRepository = typeBrandRepository;
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
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    /**
     * gets all types from database related to products
     *
     * @return list of types related to products
     */
    @Override
    public List<Type> getProductTypes() {
        return typeRepository.getProductTypes();
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
            return brandRepository.findAll();
        }
        return brandRepository.getAllByTypesTypeId(typeId);
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
        Type type = typeRepository.findById(dto.getTypeId()).get();
        Brand brand = brandRepository.findById(dto.getBrandId()).get();
        TypeBrand typeBrand = typeBrandRepository.findFirstByTypeAndBrand(type, brand);
        if (typeBrand == null) {
            type.addBrand(brand);
        }
        if (dto.getId() == 0) {
            product = new Product();
        } else if (dto.getId() > 0) {
            product = productRepository.findById(dto.getId()).get();
            type.removeProduct(product);
            brand.removeProduct(product);
        }
        assert product != null;
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
        Type type = null;
        if (dto.getId() == 0) {
            if (typeRepository.getOneByName(dto.getName()) != null) {
                throw new TypeExistsException(dto.getName());
            }
            type = new Type();
        } else if (dto.getId() > 0) {
            type = typeRepository.findById(dto.getId()).get();
            if (type.getName().equals("Other")) {
                throw new TypeOtherCanNotBeUpdatedOrDeletedException();
            }
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
        Brand brand = null;
        if (dto.getId() == 0) {
            if (brandRepository.getOneByName(dto.getName()) != null) {
                throw new BrandExistsException(dto.getName());
            }
            brand = new Brand();
        } else if (dto.getId() > 0) {
            brand = brandRepository.findById(dto.getId()).get();
            if (brand.getName().equals("Other")) {
                throw new BrandOtherCanNotBeUpdatedOrDeletedException();
            }
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
        TypeBrand typeBrand = typeBrandRepository.findFirstByTypeAndBrand(type, brand);
        type.removeProduct(product);
        brand.removeProduct(product);
        if (type.getProducts().isEmpty()) {
            type.removeBrand(brand);
            typeBrandRepository.delete(typeBrand);
        }
        if (brand.getProducts().isEmpty()) {
            type.removeBrand(brand);
            typeBrandRepository.delete(typeBrand);
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
        Type other = typeRepository.getOneByName("Other");
        if (type.getName().equals("Other")) {
            throw new TypeOtherCanNotBeUpdatedOrDeletedException();
        }
        List<Product> products = new ArrayList<>(type.getProducts());
        for (Product product : products) {
            type.removeProduct(product);
            other.addProduct(product);
        }
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
        Brand other = brandRepository.getOneByName("Other");
        if (brand.getName().equals("Other")) {
            throw new BrandOtherCanNotBeUpdatedOrDeletedException();
        }

        List<Product> products = new ArrayList<>(brand.getProducts());
        for (Product product : products) {
            brand.removeProduct(product);
            other.addProduct(product);
        }
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
