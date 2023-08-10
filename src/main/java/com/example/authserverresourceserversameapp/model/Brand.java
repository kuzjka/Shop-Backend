package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Brand {

    @Id
    @SequenceGenerator(name = "brandGen", sequenceName = "brandSeq", initialValue = 10)
    @GeneratedValue(generator = "brandGen")
    @EqualsAndHashCode.Exclude
    private long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<Product> products = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "brands")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<Type> types = new ArrayList<>();

    public void addProduct(Product product) {
        this.products.add(product);
        product.setBrand(this);
    }
    public void removeProduct(Product product){
        this.products.remove(product);
        product.setBrand(null);
    }
}
