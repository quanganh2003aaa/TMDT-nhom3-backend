package com.example.betmdtnhom3.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class CreateOrderRequest {
    private String user;
    @Pattern(regexp = "^(0)[1-9]{1}[0-9]{8}", message = "INVALID_TEL")
    private String tel;
    private String address;
    private String note;
    private int paymentMethod;
    private int deliveryMethod;
    private String voucherCode;
    @Valid
    private List<DetailOrderRequest> detailOrderRequestList;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public int getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(int deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public List<DetailOrderRequest> getDetailOrderRequestList() {
        return detailOrderRequestList;
    }

    public void setDetailOrderRequestList(List<DetailOrderRequest> detailOrderRequestList) {
        this.detailOrderRequestList = detailOrderRequestList;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
