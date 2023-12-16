package com.example.authserverresourceserversameapp.model;

import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @SequenceGenerator(name = "cartItemGen", sequenceName = "cartItemSeq", initialValue = 10)
    @GeneratedValue(generator = "cartItemGen")
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private int quantity;
    private long totalPrice;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculate() {
        this.totalPrice =  this.product.getPrice() * this.quantity;
    }
}

