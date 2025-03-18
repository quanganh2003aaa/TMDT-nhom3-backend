package com.example.betmdtnhom3.entity;

import com.example.betmdtnhom3.Enum.StatusProduct;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "products")
public class Product {
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProduct statusProduct;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand")
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private List<DetailOrder> orderDetails;

    @OneToMany(mappedBy = "product")
    private List<RateProduct> ratings;

    @OneToMany(mappedBy = "product")
    private List<Size> sizes;

    @OneToMany(mappedBy = "product")
    private List<ImgProduct> imgProducts;

    @OneToMany(mappedBy = "product")
    private List<CartUser> cartUsers;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public StatusProduct getStatusProduct() {
        return statusProduct;
    }

    public void setStatusProduct(StatusProduct statusProduct) {
        this.statusProduct = statusProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<DetailOrder> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<DetailOrder> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<RateProduct> getRatings() {
        return ratings;
    }

    public void setRatings(List<RateProduct> ratings) {
        this.ratings = ratings;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public List<ImgProduct> getImgProducts() {
        return imgProducts;
    }

    public void setImgProducts(List<ImgProduct> imgProducts) {
        this.imgProducts = imgProducts;
    }

    public List<CartUser> getCartUsers() {
        return cartUsers;
    }

    public void setCartUsers(List<CartUser> cartUsers) {
        this.cartUsers = cartUsers;
    }
}
