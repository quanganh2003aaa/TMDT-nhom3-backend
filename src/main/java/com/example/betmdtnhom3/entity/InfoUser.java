package com.example.betmdtnhom3.entity;

import jakarta.persistence.*;

@Entity(name = "info_user")
public class InfoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "city")
    private String city;
    @Column(name = "ward")
    private String ward;
    @Column(name = "district")
    private String district;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
