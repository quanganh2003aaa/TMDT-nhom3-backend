package com.example.betmdtnhom3.dto;

import com.example.betmdtnhom3.Enum.StatusProduct;

import java.util.List;

public class ProductDTO {
    private String id;
    private String name;
    private List<ImgProductDTO> img;
    private BrandDTO brand;
    private CategoryDTO category;
    private int price;
    private StatusProduct status;
    private String description;
    private Double rate;
    private List<String> sizeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImgProductDTO> getImg() {
        return img;
    }

    public void setImg(List<ImgProductDTO> img) {
        this.img = img;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public StatusProduct getStatus() {
        return status;
    }

    public void setStatus(StatusProduct status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public List<String> getSizeList() {
        return sizeList;
    }

    public void setSizeList(List<String> sizeList) {
        this.sizeList = sizeList;
    }
}
