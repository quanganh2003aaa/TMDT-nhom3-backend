package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignUpRequest {
    @Size(min = 1, max = 45, message = "INVALID_NAME_USER")
    private String name;
    @NotEmpty(message = "INVALID_NOT_EMPTY")
    private String password;
    @Pattern(regexp = "^(0)[1-9]{1}[0-9]{8}", message = "INVALID_TEL")
    private String tel;
    @NotEmpty(message = "INVALID_NOT_EMPTY")
    @Email(message = "INVALID_GMAIL")
    private String gmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
