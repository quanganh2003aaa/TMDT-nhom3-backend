package com.example.betmdtnhom3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "reason")
public class Reason {
    @Id
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "reason")
    private List<OrderRefund> orderRefunds;

    public Reason(int id) {
        this.id = id;
    }

    public Reason() {

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

    public List<OrderRefund> getOrderRefunds() {
        return orderRefunds;
    }

    public void setOrderRefunds(List<OrderRefund> orderRefunds) {
        this.orderRefunds = orderRefunds;
    }
}
