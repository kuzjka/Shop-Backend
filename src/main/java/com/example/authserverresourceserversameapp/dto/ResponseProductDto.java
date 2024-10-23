package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Product;

import java.util.ArrayList;
import java.util.List;


public class ResponseProductDto {
    private List<Product> products = new ArrayList<>();
    private int pageSize;
    private long totalProducts;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }
}
