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
}
