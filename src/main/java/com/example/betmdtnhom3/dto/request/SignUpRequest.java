package com.example.betmdtnhom3.dto.request;

import com.example.betmdtnhom3.Enum.Role;
import jakarta.validation.constraints.*;

//Du lieu dang ky tu user
public class SignUpRequest {
    @NotBlank(message = "INVALID_NAME_EMPTY")
    @Size(min = 2, max = 45, message = "INVALID_NAME_LENGTH")
    private String name;
    @NotBlank(message = "INVALID_PASSWORD_EMPTY")
    @Size(min = 8, max = 50, message = "INVALID_PASSWORD_LENGTH")
    @NotBlank(message = "INVALID_PASSWORD_FORMAT")
    private String password;
    @NotBlank(message = "INVALID_TEL_EMPTY")
    @Pattern(regexp = "^(0)[1-9]{1}[0-9]{8}$", message = "INVALID_TEL_FORMAT")
    private String tel;
    @NotBlank(message = "INVALID_GMAIL_EMPTY")
    @Email(message = "INVALID_GMAIL_FORMAT")
    @Size(max = 100, message = "INVALID_GMAIL_LENGTH")
    private String gmail;
    @NotBlank(message = "INVALID_ROLE_EMPTY")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "INVALID_ROLE_FORMAT")
    private String role;

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

    public String getRole(){
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
