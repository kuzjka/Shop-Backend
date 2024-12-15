package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Type {
    @Id
    @SequenceGenerator(name = "typeGen", sequenceName = "typeSeq", initialValue = 20)
    @GeneratedValue(generator = "typeGen")
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeBrand> brands = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<TypeBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<TypeBrand> brands) {
        this.brands = brands;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setType(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setType(null);
    }

    public void addBrand(Brand brand) {
        TypeBrand typeBrand = new TypeBrand(this, brand);
        this.brands.add(typeBrand);
        brand.getTypes().add(typeBrand);
    }

    public void removeBrand(Brand brand) {
        TypeBrand typeBrand = new TypeBrand(this, brand);
        this.brands.remove(typeBrand);
        typeBrand.setBrand(null);
        typeBrand.setType(null);
    }
}
