package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @SequenceGenerator(name = "cartItemGen", sequenceName = "cartItemSeq", initialValue = 10)
    @GeneratedValue(generator = "cartItemGen")
    private long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Product product;
    private int quantity;
    @Getter(AccessLevel.NONE)
    @Transient
    private long totalPrice;
    @ManyToOne
    @JsonIgnore
    private Cart cart;

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

