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
public class Role {
    @Id
    @SequenceGenerator(name = "roleGen", sequenceName = "roleSeq", initialValue = 10)
    @GeneratedValue(generator = "roleGen")
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        this.users.add(user);
        user.setRole(this);
    }
}
