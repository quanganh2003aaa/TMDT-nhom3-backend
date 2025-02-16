package com.example.betmdtnhom3.dto;

import com.example.betmdtnhom3.entity.Product;

import java.util.List;

public class CategoryDTO {
    private int id;
    private String name;
    private List<Product> products;

    public CategoryDTO() {
    }

    public CategoryDTO(int id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public int getId() {
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
