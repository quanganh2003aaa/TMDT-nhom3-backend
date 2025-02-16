package com.example.betmdtnhom3.dto.request;

import com.example.betmdtnhom3.entity.Product;

import java.util.List;

public class CreateCategoryRequest {
    private int id;
    private String name;
    private List<Product> products;

    public String getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
