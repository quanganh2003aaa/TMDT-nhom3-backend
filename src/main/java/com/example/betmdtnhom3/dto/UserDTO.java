package com.example.betmdtnhom3.dto;
//Du lieu tra ve cho user
public class UserDTO {
    private String id;
    private String name;
    private String tel;
    private String role;
    private String gmail;
    private String district;
    private String city;
    private String ward;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getCity(){
        return  city;
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

    public void setWard(String ward) {
        this.ward = ward;
    }
}
