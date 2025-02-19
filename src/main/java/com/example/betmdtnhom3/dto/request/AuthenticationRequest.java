package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
//Du lieu dang nhap tu user
public class AuthenticationRequest {
    @Pattern(regexp = "^(0)[1-9]{1}[0-9]{8}", message = "INVALID_TEL")
    private String tel;
    @NotEmpty(message = "INVALID_NOT_EMPTY")
    private String password;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
