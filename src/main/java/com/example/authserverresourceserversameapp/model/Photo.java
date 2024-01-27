package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Photo {
    @Id
    @SequenceGenerator(name = "imageGen", sequenceName = "imageSeq", initialValue = 10)
    @GeneratedValue(generator = "imageGen")
    private long id;
    private String url;
    @ManyToOne
    @JsonIgnore
    private Product product;


}
