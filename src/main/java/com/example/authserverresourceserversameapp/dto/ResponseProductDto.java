package com.example.authserverresourceserversameapp.dto;

import com.example.authserverresourceserversameapp.model.Product;

import java.util.List;

public class ResponseProductDto {
    private List<Product> products;
    private long pageSize;
    private long totalProducts;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }
}
