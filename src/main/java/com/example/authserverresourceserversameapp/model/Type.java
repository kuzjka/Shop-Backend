package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Type {
    @Id
    @SequenceGenerator(name = "typeGen", sequenceName = "typeSeq", initialValue = 20)
    @GeneratedValue(generator = "typeGen")
    private Long id;
    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "type")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "type_brand",
            joinColumns = @JoinColumn(name = "type_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id")
    )
    List<Brand> brands = new ArrayList<>();

    public void addProduct(Product product) {
        this.products.add(product);
        product.setType(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setBrand(null);
    }

    public void addBrand(Brand brand) {
        this.brands.add(brand);
        brand.getTypes().add(this);
    }

    public void removeBrand(Brand brand) {
        this.brands.remove(brand);
        brand.getTypes().remove(this);
    }
}
