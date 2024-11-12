package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @SequenceGenerator(name = "orderGen", sequenceName = "orderSeq", initialValue = 10)
    @GeneratedValue(generator = "orderGen")
    private Long id;
    private String description;
    private transient long totalPrice;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<Item> items = new ArrayList<>();

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        item.setOrder(this);
        this.items.add(item);
    }

    public long getTotalPrice() {
        for (Item item : this.items) {
            this.totalPrice += item.getTotalPrice();
        }
        return this.totalPrice;
    }
}
