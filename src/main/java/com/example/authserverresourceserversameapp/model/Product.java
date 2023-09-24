package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "productGen", sequenceName = "productSeq", initialValue = 10)
    @GeneratedValue(generator = "productGen")
    private long id;
    @EqualsAndHashCode.Include
    private String name;
    private int price;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Type type;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;

}
