package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Brand {
    @Id
    @SequenceGenerator(name = "brandGen", sequenceName = "brandSeq", initialValue = 20)
    @GeneratedValue(generator = "brandGen")
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeBrand> types = new ArrayList<>();

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

    public List<TypeBrand> getTypes() {
        return types;
    }

    public void setTypes(List<TypeBrand> types) {
        this.types = types;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setBrand(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setBrand(null);
    }
}
