package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OTPRequest {
    @NotBlank(message = "INVALID_GMAIL_EMPTY")
    @Email(message = "INVALID_GMAIL_FORMAT")
    @Size(max = 100, message = "INVALID_GMAIL_LENGTH")
    private String gmail;

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
