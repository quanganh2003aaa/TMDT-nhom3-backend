package com.example.betmdtnhom3.dto;

import jakarta.validation.constraints.Pattern;

public class InfoStoreDTO {

    private String address;
    private String user;
    private String tel;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
}
