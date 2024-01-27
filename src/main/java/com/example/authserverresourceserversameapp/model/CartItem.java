package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem {
    @Id
    @SequenceGenerator(name = "cartItemGen", sequenceName = "cartItemSeq", initialValue = 10)
    @GeneratedValue(generator = "cartItemGen")
    private long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Product product;
    private int quantity;
    @Getter(AccessLevel.NONE)
    private long totalPrice;
    @ManyToOne
    @JsonIgnore
    private Cart cart;

    public CartItem() {
    }

    public long getTotalPrice() {
        this.totalPrice = this.product.getPrice() * this.quantity;
        return totalPrice;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}

