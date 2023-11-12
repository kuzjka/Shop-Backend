package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Photo {

    @Id
    @SequenceGenerator(name = "imageGen", sequenceName = "imageSeq", initialValue = 10)
    @GeneratedValue(generator = "imageGen")
    private long id;

    private String url;

    @ManyToOne
    @JsonIgnore
    private Product product;

    public Photo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
