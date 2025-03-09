package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.*;

public class SignUpRequest {
    @NotBlank(message = "INVALID_PASSWORD_EMPTY")
    @Size(min = 8, max = 50, message = "INVALID_PASSWORD_LENGTH")
    private String password;
    @NotBlank(message = "INVALID_TEL_EMPTY")
    @Pattern(regexp = "^(0)[1-9]{1}[0-9]{8}$", message = "INVALID_TEL_FORMAT")
    private String tel;
    @NotBlank(message = "INVALID_GMAIL_EMPTY")
    @Email(message = "INVALID_GMAIL_FORMAT")
    @Size(max = 100, message = "INVALID_GMAIL_LENGTH")
    private String gmail;

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
