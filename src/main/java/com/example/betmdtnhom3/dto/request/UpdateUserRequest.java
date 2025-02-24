package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UpdateUserRequest {
    private String name;
    @Pattern(regexp = "^(0)[1-9]{1}[0-9]{8}", message = "INVALID_TEL")
    private String tel;
    @NotEmpty(message = "INVALID_NOT_EMPTY")
    @Email(message = "INVALID_GMAIL")
    private String gmail;
    private String city;
    private String district;
    private String ward;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel(){
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getGmail(){
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict(){
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard(){
        return ward;
    }

    public void setWard(String ward){
        this.ward = ward;
    }
}
