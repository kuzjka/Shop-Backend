package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @SequenceGenerator(name = "cartItemGen", sequenceName = "cartItemSeq", initialValue = 10)
    @GeneratedValue(generator = "cartItemGen")
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    private int quantity;
    private long totalPrice;
    @ManyToOne
    @JsonIgnore
    private Cart cart;

    public CartItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }


}

