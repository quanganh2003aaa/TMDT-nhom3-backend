package com.example.betmdtnhom3.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "month")
    private int month;
    @Column(name = "year")
    private int year;
    @Column(name = "revenue")
    private int revenue;
    @OneToMany(mappedBy = "revenue")
    private List<Order> orders;
    @OneToMany(mappedBy = "revenue")
    private List<OrderRefund> orderRefunds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<OrderRefund> getOrderRefunds() {
        return orderRefunds;
    }

    public void setOrderRefunds(List<OrderRefund> orderRefunds) {
        this.orderRefunds = orderRefunds;
    }
}
