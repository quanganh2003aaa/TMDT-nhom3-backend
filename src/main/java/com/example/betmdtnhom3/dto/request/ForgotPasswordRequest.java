package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ForgotPasswordRequest {
    @NotBlank(message = "INVALID_GMAIL_EMPTY")
    @Email(message = "INVALID_GMAIL_FORMAT")
    @Size(max = 100, message = "INVALID_GMAIL_LENGTH")
    private String gmail;
    @Size(max = 6, min = 6, message = "OTP_ERROR")
    private String otp;
    @NotBlank(message = "INVALID_PASSWORD_EMPTY")
    @Size(min = 8, max = 16, message = "INVALID_PASSWORD_LENGTH")
    private String newPassword;

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
