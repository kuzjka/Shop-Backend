package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @SequenceGenerator(name = "cartGen", sequenceName = "cartSeq", initialValue = 10)
    @GeneratedValue(generator = "cartGen")
    private long id;
    private long total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")

    private List<CartItem> cartItems = new ArrayList<>();
    @ManyToOne
    @JsonIgnore
    private User user;

    public Cart() {
        this.total = 0L;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTotalPrice() {
        this.cartItems.forEach(x -> this.total += x.getTotalPrice());
        return this.total;
    }

    public void addCartItem(CartItem item) {
        this.cartItems.add(item);
        item.setCart(this);
    }

}
