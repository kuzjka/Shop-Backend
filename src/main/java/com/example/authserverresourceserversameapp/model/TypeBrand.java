package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "type_brand")
public class TypeBrand {

    @Id
    @SequenceGenerator(name = "typeBrandGen", sequenceName = "typeBrandSeq", initialValue = 20)
    @GeneratedValue(generator = "typeBrandGen")
    private Long id;
    @JsonIgnore
    @ManyToOne
    private Type type;

    @JsonIgnore
    @ManyToOne
    private Brand brand;

    public TypeBrand() {
    }

    public TypeBrand(Type type, Brand brand) {
        this.type = type;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
