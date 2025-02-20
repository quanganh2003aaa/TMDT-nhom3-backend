package com.example.betmdtnhom3.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @Column(name = "total_price", nullable = false)
    private int totalPrice;
    @Column(name = "tel", nullable = false)
    private String tel;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "note")
    private String note;
    @Column(name = "discount_amount")
    private int discountAmount;
    @Column(name = "final_amount")
    private int finalAmount;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "revenue")
    private Revenue revenue;
    @ManyToOne
    @JoinColumn(name = "status")
    private StatusOrder statusOrder;

    @ManyToOne
    @JoinColumn(name = "delivery_method")
    private DeliveryMethod deliveryMethod;

    @ManyToOne
    @JoinColumn(name = "voucher_code")
    private Voucher voucher;

    @OneToMany(mappedBy = "order")
    private List<DetailOrder> details;
    @OneToMany(mappedBy = "order")
    private List<OrderRefund> orderRefunds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Revenue getRevenue() {
        return revenue;
    }

    public void setRevenue(Revenue revenue) {
        this.revenue = revenue;
    }

    public StatusOrder getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(StatusOrder statusOrder) {
        this.statusOrder = statusOrder;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public List<DetailOrder> getDetails() {
        return details;
    }

    public void setDetails(List<DetailOrder> details) {
        this.details = details;
    }

    public List<OrderRefund> getOrderRefunds() {
        return orderRefunds;
    }

    public void setOrderRefunds(List<OrderRefund> orderRefunds) {
        this.orderRefunds = orderRefunds;
    }
}
