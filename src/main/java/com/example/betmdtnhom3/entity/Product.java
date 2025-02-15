package com.example.betmdtnhom3.entity;

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
    @Column(name = "quantity")
    private int quantity;
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
}
