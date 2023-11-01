package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Photo {

    @Id
    @SequenceGenerator(name = "imageGen", sequenceName = "imageSeq", initialValue = 10)
    @GeneratedValue(generator = "imageGen")
    private Long id;
    private String url;

}
