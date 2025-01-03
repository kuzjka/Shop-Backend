package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "type_brand")
public class TypeBrand {
    @Id
    @ManyToOne
    @JsonIgnore
    private Type type;
    @Id
    @ManyToOne
    @JsonIgnore
    private Brand brand;

    public TypeBrand() {
    }

    public TypeBrand(Type type, Brand brand) {
        this.type = type;
        this.brand = brand;
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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeBrand typeBrand)) return false;

        return getType().equals(typeBrand.getType()) && getBrand().equals(typeBrand.getBrand());
    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getBrand().hashCode();
        return result;
    }
}
