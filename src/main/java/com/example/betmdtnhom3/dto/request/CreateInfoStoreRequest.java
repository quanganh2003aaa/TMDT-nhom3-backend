package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class CreateInfoStoreRequest {
    private String user;
    @Pattern(regexp = "^(0)[1-9]{1}[0-9]{8}", message = "INVALID_TEL")
    private String tel;
    private String address;

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
}
