package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @SequenceGenerator(name = "productGen", sequenceName = "productSeq", initialValue = 10)
    @GeneratedValue(generator = "productGen")
    private Long id;
    private String name;
    private int price;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Type type;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;
}
