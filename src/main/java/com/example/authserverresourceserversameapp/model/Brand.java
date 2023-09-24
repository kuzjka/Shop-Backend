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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Brand {
    @Id
    @SequenceGenerator(name = "brandGen", sequenceName = "brandSeq", initialValue = 10)
    @GeneratedValue(generator = "brandGen")

    private long id;
    @EqualsAndHashCode.Include
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
