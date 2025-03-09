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

    public StatusOrder(int id) {
        this.id = id;
    }

    public StatusOrder() {

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
