package com.example.betmdtnhom3.entity;

import com.example.betmdtnhom3.Enum.Role;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "tel", unique = true, length = 10, nullable = false)
    private String tel;
    @Column(name = "gmail", unique = true, length = 50, nullable = false)
    private String gmail;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<InfoUser> infoUsers;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<InfoStore> infoStores;

    @OneToMany(mappedBy = "user")
    private List<Blog> blogs;

    @OneToMany(mappedBy = "user")
    private List<CommentBlog> comments;

    @OneToMany(mappedBy = "user")
    private List<RateProduct> ratings;

    @OneToMany(mappedBy = "user")
    private List<Banner> banners;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<InfoUser> getInfoUsers() {
        return infoUsers;
    }

    public void setInfoUsers(List<InfoUser> infoUsers) {
        this.infoUsers = infoUsers;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<InfoStore> getInfoStores() {
        return infoStores;
    }

    public void setInfoStores(List<InfoStore> infoStores) {
        this.infoStores = infoStores;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<CommentBlog> getComments() {
        return comments;
    }

    public void setComments(List<CommentBlog> comments) {
        this.comments = comments;
    }

    public List<RateProduct> getRatings() {
        return ratings;
    }

    public void setRatings(List<RateProduct> ratings) {
        this.ratings = ratings;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }
}
