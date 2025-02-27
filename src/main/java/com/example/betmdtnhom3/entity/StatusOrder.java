package com.example.betmdtnhom3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "status_order")
public class StatusOrder {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "statusOrder")
    private List<Order> orders;
    @OneToMany(mappedBy = "statusOrder")
    private List<OrderRefund> orderRefunds;


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
