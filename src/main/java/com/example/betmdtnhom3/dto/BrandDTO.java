package com.example.betmdtnhom3.dto;

import java.util.List;

public class BrandDTO {
    private int id;
    private String name;
    private List<String> Products;

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

    public List<String> getProducts() {
        return Products;
    }

    public void setProducts(List<String> products) {
        Products = products;
    }
}
