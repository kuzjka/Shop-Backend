package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Brand {
    @Id
    @SequenceGenerator(name = "brandGen", sequenceName = "brandSeq", initialValue = 20)
    @GeneratedValue(generator = "brandGen")
    private Long id;
    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "brand")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
    @ManyToMany(mappedBy = "brands")
    @JsonIgnore
    private List<Type> types = new ArrayList<>();

    public Brand() {
    }

    @Builder
    public Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setBrand(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setBrand(null);
    }

    @PreRemove
    public void removeTypeAssociations() {
        this.types.forEach(x -> x.getBrands().remove(this));
    }
}
