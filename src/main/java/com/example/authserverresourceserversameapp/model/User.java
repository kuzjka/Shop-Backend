package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @SequenceGenerator(name = "userGen", sequenceName = "userSeq", initialValue = 10)
    @GeneratedValue(generator = "userGen")
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})

    private Role role;

    public User() {
        this.enabled = false;
    }


}
