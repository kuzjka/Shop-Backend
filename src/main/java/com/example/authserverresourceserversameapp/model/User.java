package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "userGen", sequenceName = "userSeq", initialValue = 10)
    @GeneratedValue(generator = "userGen")
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<CartItem> cartItems = new ArrayList<>();

    public User() {
        this.enabled = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setUser(this);
    }
}
