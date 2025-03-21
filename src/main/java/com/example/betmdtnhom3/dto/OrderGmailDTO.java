package com.example.betmdtnhom3.dto;

import com.example.betmdtnhom3.Enum.PaymentStatus;

import java.util.List;

public class OrderGmailDTO {
    private int id;
    private String nameUser;
    private String tel;
    private String address;
    private String paymentMethod;
    private String deliveryMethod;
    private int totalPrice;
    private int shippingFee;
    private int discountAmount;
    private int finalAmount;
    private List<DetailOrderDTO> detailOrderDTOList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(int shippingFee) {
        this.shippingFee = shippingFee;
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

    public List<DetailOrderDTO> getDetailOrderDTOList() {
        return detailOrderDTOList;
    }

    public void setDetailOrderDTOList(List<DetailOrderDTO> detailOrderDTOList) {
        this.detailOrderDTOList = detailOrderDTOList;
    }
}
