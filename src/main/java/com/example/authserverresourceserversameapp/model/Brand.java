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
