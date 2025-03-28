package com.example.betmdtnhom3.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {
    @NotBlank(message = "INVALID_NAME_EMPTY")
    @Size(min = 2, max = 45, message = "INVALID_NAME_LENGTH")
    private String name;
    @NotBlank(message = "INVALID_GMAIL_EMPTY")
    @Email(message = "INVALID_GMAIL_FORMAT")
    @Size(max = 50, message = "INVALID_GMAIL_LENGTH")
    private String gmail;
    @NotBlank(message = "INVALID_CITY_EMPTY")
    @Size(min = 2, max = 100, message = "INVALID_CITY_LENGTH")
    private String city;
    @NotBlank(message = "INVALID_DISTRICT_EMPTY")
    @Size(min = 2, max = 100, message = "INVALID_DISTRICT_LENGTH")
    private String district;
    @NotBlank(message = "INVALID_WARD_EMPTY")
    @Size(min = 2, max = 100, message = "INVALID_WARD_LENGTH")
    private String ward;

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
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
