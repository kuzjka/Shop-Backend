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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        this.products.add(product);
        product.setBrand(this);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.setBrand(null);
    }
}
